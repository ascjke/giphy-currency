package ru.borisov.giphycurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableAsync
public class GiphyCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiphyCurrencyApplication.class, args);
    }
}
