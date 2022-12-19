package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.client.GifClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class GifServiceImpl implements GifService {

    private final GifClient gifClient;
    private final ObjectMapper mapper;
    @Value("${giphy.api_key}")
    private String gifApiKey;

    @Override
    public String getGifUrl(String currencyRateStatus) {
        JsonNode node = null;
        log.info("Getting gifs from Giphy");
        String responseData = gifClient.getGifResponse(gifApiKey, currencyRateStatus);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String gifUrl = node.get("data").get((int) (Math.random() * 50)).get("embed_url").asText();

        return gifUrl;
    }
}
