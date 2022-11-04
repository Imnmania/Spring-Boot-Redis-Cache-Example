package me.niloybiswas.cachedemo.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "redis")
@Configuration
public class RedisProperties {
    private String host;
    private int port;
    private RedisProperties master;
    private List<RedisProperties> slaves;
}
