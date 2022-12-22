package ru.borisov.giphycurrency.config.caffeine;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "caches")
@Getter
@Setter
public class CaffeineCacheDto {

    private List<Cache> caffeines;

    @Getter
    @Setter
    public static class Cache {
        private String name;
        private Long expiryInHours;
    }
}