package ru.borisov.giphycurrency.config.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(value = CaffeineCacheDto.class)
@RequiredArgsConstructor
public class CaffeineCacheConfig {

    private final CaffeineCacheDto caffeineCacheDto;

    @Bean
    public CacheManager cacheManager() {
        List<CaffeineCacheDto.Cache> caffeines = caffeineCacheDto.getCaffeines();
        List<CaffeineCache> caffeineCaches = caffeines.parallelStream()
                .map(this::buildCache)
                .collect(Collectors.toList());
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caffeineCaches);
        return manager;
    }

    private CaffeineCache buildCache(CaffeineCacheDto.Cache cacheConfig) {
        return new CaffeineCache(cacheConfig.getName(), Caffeine.newBuilder()
                .expireAfterWrite(cacheConfig.getExpiryInHours(), TimeUnit.HOURS)
                .build());
    }
}
