package com.xiaowu5759.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xiaowu
 * @date 2021/6/7 10:17 PM
 */
@Component
@PropertySource("classpath:config/elasticsearch.properties")
public class ElasticsearchProperties {

    @Value("elasticsearch.host")
    private String host;

    @Value("elasticsearch.port")
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
