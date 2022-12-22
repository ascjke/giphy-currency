package ru.borisov.giphycurrency.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    public CurrencyGifDto currencyGifDto() {
        return new CurrencyGifDto();
    }
}
