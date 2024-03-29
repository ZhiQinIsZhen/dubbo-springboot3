package com.liyz.boot3.common.search.method;

import cn.hutool.core.util.ReflectUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.util.DateTime;
import co.elastic.clients.util.NamedValue;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.search.query.LambdaQueryWrapper;
import com.liyz.boot3.common.search.exception.SearchException;
import com.liyz.boot3.common.search.exception.SearchExceptionCodeEnum;
import com.liyz.boot3.common.search.mapper.EsMapper;
import com.liyz.boot3.common.search.response.AggResponse;
import com.liyz.boot3.common.search.response.EsResponse;
import com.liyz.boot3.common.util.ReflectorUtil;
import com.liyz.boot3.common.util.TypeParameterResolverUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 21:16
 */
@Slf4j
@Getter
public abstract class AbstractEsMethod implements IEsMethod {

    private static ElasticsearchClient CLIENT;

    public static void setCLIENT(ElasticsearchClient CLIENT) {
        AbstractEsMethod.CLIENT = CLIENT;
    }

    private final String methodName;

    public AbstractEsMethod(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public Object execute(Class<?> mapperInterface, Method method, Object[] args) {
        Type[] genTypes = mapperInterface.getGenericInterfaces();
        Type genType = Arrays.stream(genTypes)
                .filter(type -> type instanceof ParameterizedType && ReflectorUtil.typeToClass(type) == EsMapper.class)
                .findFirst()
                .orElseThrow(() -> new SearchException(SearchExceptionCodeEnum.NOT_EXIST_MAPPER));
        ParameterizedType parameterizedType = (ParameterizedType) genType;
        Class<?> aClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        Document document = aClass.getAnnotation(Document.class);
        if (Objects.isNull(document) || !StringUtils.hasText(document.indexName())) {
            throw new SearchException(SearchExceptionCodeEnum.NOT_INDEX_NAME);
        }
        SearchRequest.Builder builder = this.buildRequest(args);
        EsResponse<?> esResponse = doQuery(builder.index(document.indexName()).build(), aClass);
        return getByEsResponse(mapperInterface, method, esResponse, aClass);
    }

    private Object getByEsResponse(Class<?> mapperInterface, Method method, EsResponse<?> esResponse, Class<?> aClass) {
        RemotePage<?> remotePage = esResponse.getPageData();
        Type returnType = TypeParameterResolverUtil.resolveReturnType(method, mapperInterface);
        Class<?> returnClass = TypeParameterResolverUtil.typeToClass(returnType);
        if (aClass.equals(returnClass)) {
            if (CollectionUtils.isEmpty(remotePage.getList())) {
                return null;
            } else {
                return remotePage.getList().getFirst();
            }
        } else if (returnClass.isAssignableFrom(List.class)) {
            if (returnType instanceof ParameterizedType parameterizedType1) {
                Class<?> aaClass = (Class<?>) parameterizedType1.getActualTypeArguments()[0];
                if (aClass.equals(aaClass)) {
                    return remotePage.getList();
                }
                if (aaClass.equals(AggResponse.class)) {
                    List<AggResponse> aggList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(esResponse.getAggData())) {
                        esResponse.getAggData().values().forEach(aggList::addAll);
                    }
                    return aggList;
                }
            }
        } else if (returnClass.equals(RemotePage.class)) {
            return remotePage;
        }
        return null;
    }

    protected SearchRequest.Builder buildRequest(Object[] args) {
        Object page;
        PageBO pageBO;
        if (args == null || (page = Arrays.stream(args).filter(arg -> arg instanceof PageBO).findFirst().orElse(null)) == null) {
            pageBO = PageBO.of(1, 20);
        } else {
            pageBO = (PageBO) page;
        }
        int from = (pageBO.getPageNum().intValue() - 1) * pageBO.getPageSize().intValue();
        SearchRequest.Builder builder = new SearchRequest.Builder().from(from).size(pageBO.getPageSize().intValue());
        Object obj = Arrays.stream(args).filter(arg -> arg instanceof LambdaQueryWrapper<?>).findFirst().orElse(null);
        if (obj == null) {
            return builder;
        }
        LambdaQueryWrapper<?> wrapper = (LambdaQueryWrapper<?>) obj;
        //查询字段
        if (!CollectionUtils.isEmpty(wrapper.getIncludes())) {
            builder.source(s -> s.filter(sf -> sf.includes(wrapper.getIncludes())));
        }
        //查询条件
        if (wrapper.getQuery() != null) {
            builder.query(wrapper.getQuery());
        }
        //排序
        if (!CollectionUtils.isEmpty(wrapper.getSorts())) {
            builder.sort(wrapper.getSorts()
                    .stream()
                    .map(item -> SortOptions.of(so -> so.field(sof -> sof.field(item.getColum()).order(item.getEsSort()))))
                    .collect(Collectors.toList()));
        }
        //agg
        if (!CollectionUtils.isEmpty(wrapper.getAggs())) {
            Map<String, Aggregation> map = wrapper.getAggs().stream().collect(Collectors.toMap(agg -> agg.getColum().concat("_").concat(agg.getKind().jsonValue()), agg -> {
                switch (agg.getKind()) {
                    case Aggregation.Kind.Terms -> {
                        return TermsAggregation.of(tgg -> tgg
                                        .field(agg.getColum())
                                        .size(agg.getMaxCount())
                                        .minDocCount(agg.getMinDocCount())
                                        .order(NamedValue.of(agg.getEsSortField().getSortField(), agg.getEsSort()))
                                )
                                ._toAggregation();
                    }
                    case Aggregation.Kind.DateHistogram -> {
                        return DateHistogramAggregation.of(dhAgg -> dhAgg
                                .field(agg.getColum())
                                .fixedInterval(fi -> fi.time("year"))
                                .format("yyyy")
                                .missing(DateTime.of("1000-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .order(NamedValue.of(agg.getEsSortField().getSortField(), agg.getEsSort()))
                        )._toAggregation();
                    }
                }
                throw new SearchException(SearchExceptionCodeEnum.NOT_SUPPORT_AGG_TYPE);
            }));
            builder.aggregations(map);
        }
        return builder;
    }

    private EsResponse<?> doQuery(SearchRequest request, Class<?> aClass) {
        log.info("es index : [{}], body : {}", request.index().get(0), request);
        try {
            Field idField = Arrays.stream(ReflectUtil.getFields(aClass))
                    .filter(f -> f.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElse(null);
            long start = System.currentTimeMillis();
            SearchResponse<?> response = CLIENT.search(request, aClass);
            List<?> boList = response.hits().hits()
                    .stream()
                    .map(item -> {
                        Object source = item.source();
                        if (Objects.nonNull(idField)) {
                            ReflectUtil.setFieldValue(source, idField, item.id());
                        }
                        return source;
                    })
                    .peek(this::afterHandle)
                    .collect(Collectors.toList());
            long end = System.currentTimeMillis();
            log.info("es hits count : [{}], es take : {}ms, java code take : {}ms",
                    response.hits().total().value(),
                    response.took(), end - start);
            Map<String, List<AggResponse>> aggData = new HashMap<>();
            if (!CollectionUtils.isEmpty(response.aggregations())) {
                for (Map.Entry<String, Aggregate> entry : response.aggregations().entrySet()) {
                    aggData.put(entry.getKey(), entry.getValue().sterms().buckets().array()
                            .stream()
                            .map(item -> AggResponse.of(item.key().stringValue(), item.docCount()))
                            .collect(Collectors.toList())
                    );
                }
            }
            return EsResponse.of(
                    new RemotePage<>(boList, response.hits().total().value(), Math.max(0, request.from()) + 1, request.size()),
                    aggData
            );
        } catch (IOException e) {
            log.error("query es error", e);
        }
        return EsResponse.of(
                new RemotePage<>(List.of(), 0, Math.max(0, request.from()) + 1, request.size()),
                null
        );
    }

    /**
     * 后置数据处理
     *
     * @param o 数据
     */
    protected void afterHandle(Object o) {
        //do nothing
    }
}
