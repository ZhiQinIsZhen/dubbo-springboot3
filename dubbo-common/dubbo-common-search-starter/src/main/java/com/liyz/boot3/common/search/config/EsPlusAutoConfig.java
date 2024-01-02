package com.liyz.boot3.common.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.liyz.boot3.common.search.method.AbstractEsMethod;
import com.liyz.boot3.common.search.util.GlobalEsCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 16:45
 */
@Configuration
@EnableConfigurationProperties({ElasticsearchProperties.class, ElasticsearchPoolProperties.class})
public class EsPlusAutoConfig implements InitializingBean {

    private final ElasticsearchProperties properties;
    private final ElasticsearchPoolProperties poolProperties;

    public EsPlusAutoConfig(ElasticsearchProperties properties, ElasticsearchPoolProperties poolProperties) {
        this.properties = properties;
        this.poolProperties = poolProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ElasticsearchClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(properties.getUris().stream().map(HttpHost::create).toArray(HttpHost[]::new));
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder
                    .setMaxConnTotal(poolProperties.getMaxConnTotal())
                    .setMaxConnPerRoute(poolProperties.getMaxConnPerRoute())
                    .setKeepAliveStrategy((response, context) -> poolProperties.getKeepAliveStrategy())
                    .setDefaultHeaders(List.of(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
                    .addInterceptorLast((HttpResponseInterceptor) (response, context) -> response.addHeader("X-Elastic-Product", "Elasticsearch"));
            if (StringUtils.isNoneBlank(properties.getUsername(), properties.getPassword())) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder
                    .setConnectTimeout(properties.getConnectionTimeout().getNano())
                    .setSocketTimeout(properties.getSocketTimeout().getNano())
                    .setConnectionRequestTimeout(poolProperties.getConnectionRequestTimeout())
                    .setExpectContinueEnabled(poolProperties.isExpectContinueEnabled())
                    .setRedirectsEnabled(poolProperties.isRedirectsEnabled())
                    .build();
            return requestConfigBuilder;
        });
        ElasticsearchClient client = new ElasticsearchClient(new RestClientTransport(
                builder.build(),
                new JacksonJsonpMapper(
                        new JsonMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE))
        ));
        //todo 暂时在这里设置
        AbstractEsMethod.setCLIENT(client);
        return client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GlobalEsCacheUtil.initEsMethodMap();
    }
}
