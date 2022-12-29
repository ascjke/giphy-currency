package ru.borisov.giphycurrency.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.borisov.giphycurrency.client.GifClient;

@Repository
@RequiredArgsConstructor
@Log4j2
@Getter
public class GifRepository {
    private final GifClient gifClient;
    private final ObjectMapper mapper;
    @Value("${giphy.api_key}")
    private String gifApiKey;

//    @Cacheable("gifs")
    public JsonNode getDataJsonNode(String currencyRateStatus) {
        JsonNode node = null;
        log.info("Getting GIF from Giphy");
        String responseData = gifClient.getGifResponse(gifApiKey, currencyRateStatus);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return node.get("data");
    }
}
