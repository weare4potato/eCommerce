package com.potato.ecommerce.global.config.redis;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CACHE_180_SECOND = "cache180";

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory cf, ResourceLoader rl) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
            cf);

        ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

        RedisCacheConfiguration configuration = RedisCacheConfiguration
            .defaultCacheConfig(rl.getClassLoader())
            .disableCachingNullValues()
            .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(
                fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
            .entryTtl(Duration.ofDays(1));

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put(CACHE_180_SECOND,
            configuration.entryTtl(Duration.ofSeconds(180L)));

        return builder.cacheDefaults(configuration)
            .withInitialCacheConfigurations(cacheConfigurationMap).build();
    }
}
