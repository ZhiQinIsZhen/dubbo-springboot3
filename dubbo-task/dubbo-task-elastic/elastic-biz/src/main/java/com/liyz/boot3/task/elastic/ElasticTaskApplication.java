package com.liyz.boot3.task.elastic;

import org.apache.shardingsphere.elasticjob.lite.spring.core.scanner.ElasticJobScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/20 19:27
 */
@ElasticJobScan(basePackages = {"com.liyz.boot3.task.elastic.job"})
@SpringBootApplication
public class ElasticTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticTaskApplication.class);
    }
}