package com.liyz.boot3.service.search.service;

import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.service.search.bo.BaseBO;
import com.liyz.boot3.service.search.constant.SearchType;
import com.liyz.boot3.service.search.exception.RemoteSearchServiceException;
import com.liyz.boot3.service.search.properties.BaseProperties;
import com.liyz.boot3.service.search.query.PageQuery;
import com.liyz.boot3.service.search.remote.SearchService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:41
 */
public abstract class AbstractSearchService<BO extends BaseBO, BaseQuery extends PageQuery>
        implements SearchService<BO, BaseQuery>, ApplicationListener<ContextRefreshedEvent> {

    private static final Map<SearchType, SearchService<BaseBO, PageQuery>> SEARCH_TYPE_MAP = new EnumMap<>(SearchType.class);
    protected final Class<BO> boClass;
    protected final Class<BaseQuery> queryClass;
    protected BaseProperties properties;

    public AbstractSearchService() {
        Type genType = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genType;
        this.boClass = (Class<BO>) parameterizedType.getActualTypeArguments()[0];
        this.queryClass = (Class<BaseQuery>) parameterizedType.getActualTypeArguments()[1];
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        SEARCH_TYPE_MAP.put(this.getSearchType(), applicationContext.getBean(this.getClass()));
        this.properties = BaseProperties.SEARCH_TYPE_MAP.get(this.getSearchType());
    }

    public static SearchService<BaseBO, PageQuery> getSearchService(SearchType searchType) {
        return getSearchService(searchType, false);
    }

    public static SearchService<BaseBO, PageQuery> getSearchService(SearchType searchType, boolean noServiceException) {
        SearchService<BaseBO, PageQuery> service = SEARCH_TYPE_MAP.get(searchType);
        if (noServiceException && Objects.isNull(service)) {
            throw new RemoteSearchServiceException(CommonExceptionCodeEnum.PARAMS_VALIDATED);
        }
        return service;
    }

    /**
     * 获取搜索类型
     *
     * @return 搜索类型
     */
    protected abstract SearchType getSearchType();
}
