package com.xiaowu5759.common.manager;

import com.xiaowu5759.common.config.ElasticsearchProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaowu
 * @date 2021/6/7 10:04 PM
 */
@Configuration
public class ElasticsearchConfig {
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    public RestClientBuilder restClientBuilder(){
        log.info("host={},port={}", elasticsearchProperties.getHost(), elasticsearchProperties.getPort());
        HttpHost httpHost = new HttpHost(elasticsearchProperties.getHost(), elasticsearchProperties.getPort(), "http");
        HttpHost[] hosts = {httpHost};
        RestClientBuilder restClientBuilder = RestClient.builder(hosts);
        return restClientBuilder;
    }

    // high-level
    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder){
        return new RestHighLevelClient(restClientBuilder);
    }

}
