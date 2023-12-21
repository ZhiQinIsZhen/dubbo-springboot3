package com.liyz.boot3.common.dao.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/21 17:23
 */
@Getter
@Setter
@ConfigurationProperties("desensitization.database")
public class DesensitizationProperties {

    private boolean enable = false;
}
