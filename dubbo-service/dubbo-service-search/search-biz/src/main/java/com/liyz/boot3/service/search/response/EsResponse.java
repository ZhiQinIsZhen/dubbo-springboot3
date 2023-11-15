package com.liyz.boot3.service.search.response;

import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.search.bo.agg.AggBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 15:54
 */
@Getter
@Setter
public class EsResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6830405460023653657L;

    private RemotePage<T> pageData;

    private Map<String, List<AggBO>> aggData;

    public static <T> EsResponse<T> of(RemotePage<T> pageData, Map<String, List<AggBO>> aggData) {
        EsResponse<T> esResponse = new EsResponse<>();
        esResponse.setPageData(pageData);
        esResponse.setAggData(aggData);
        return esResponse;
    }
}
