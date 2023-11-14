package com.liyz.boot3.service.search.repository;

import com.liyz.boot3.service.search.model.CompanyDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 9:37
 */
public interface CompanyRepository extends ElasticsearchRepository<CompanyDO, String> {
}
