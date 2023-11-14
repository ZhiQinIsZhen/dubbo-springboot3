package com.liyz.boot3.service.search.properties.company;

import com.liyz.boot3.service.search.properties.BaseProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:52
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "company.search")
public class CompanyProperties extends BaseProperties {
}
