package com.liyz.boot3.service.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
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
 * @date 2023/11/14 9:40
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class SearchConfig {

    private final ElasticsearchProperties properties;

    public SearchConfig(ElasticsearchProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
        return new ElasticsearchClient(new RestClientTransport(
                RestClient
                        .builder(properties.getUris().stream().map(HttpHost::create).toArray(HttpHost[]::new))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setMaxConnTotal(100)
                                .setMaxConnPerRoute(20)
                                .setDefaultHeaders(List.of(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
                                .setDefaultRequestConfig(RequestConfig.custom()
                                        .setConnectTimeout((int) properties.getConnectionTimeout().getSeconds() * 1000)
                                        .setSocketTimeout((int) properties.getSocketTimeout().getSeconds())
                                        .setConnectionRequestTimeout(5000)
                                        .setExpectContinueEnabled(true)
                                        .setRedirectsEnabled(false)
                                        .build()
                                )
                                .addInterceptorLast((HttpResponseInterceptor) (response, context) -> response.addHeader("X-Elastic-Product", "Elasticsearch"))
                        )
                        .build(),
                new JacksonJsonpMapper(new JsonMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                )
        )
        );
    }
}
