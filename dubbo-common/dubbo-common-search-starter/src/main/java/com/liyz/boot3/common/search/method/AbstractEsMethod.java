package com.liyz.boot3.common.search.method;

import cn.hutool.core.util.ReflectUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.search.Query.LambdaQueryWrapper;
import com.liyz.boot3.common.search.response.AggResponse;
import com.liyz.boot3.common.search.response.EsResponse;
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
public abstract class AbstractEsMethod implements IEsMethod {

    private static ElasticsearchClient CLIENT;

    public static void setCLIENT(ElasticsearchClient CLIENT) {
        AbstractEsMethod.CLIENT = CLIENT;
    }

    private final String methodName;

    public AbstractEsMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public Object execute(Class<?> mapperInterface, Method method, Object[] args) {
        Type[] genTypes = mapperInterface.getGenericInterfaces();
        if (genTypes == null || genTypes.length == 0) {
            throw new IllegalStateException("mapperInterface need have genericInterfaces");
        }
        Type genType = genTypes[0];
        ParameterizedType parameterizedType = (ParameterizedType) genType;
        Class<?> aClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        Document document = aClass.getAnnotation(Document.class);
        if (Objects.isNull(document) || !StringUtils.hasText(document.indexName())) {
            throw new IllegalStateException("Document annotation need have value");
        }
        SearchRequest.Builder builder = this.buildRequest(args);
        RemotePage<?> remotePage = doQuery(builder.index(document.indexName()).build(), aClass).getPageData();
        Type returnType = method.getGenericReturnType();
        return remotePage.getList();
    }

    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = new SearchRequest.Builder().from(0).size(100);
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof LambdaQueryWrapper<?> wrapper) {
                    if (!CollectionUtils.isEmpty(wrapper.getIncludes())) {
                        builder = builder.source(s -> s.filter(sf -> sf.includes(wrapper.getIncludes())));
                    }
                }
            }
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
                for (String key : response.aggregations().keySet()) {
                    aggData.put(key, response.aggregations().get(key).sterms().buckets().array()
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
