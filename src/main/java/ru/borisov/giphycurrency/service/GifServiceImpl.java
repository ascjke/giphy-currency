package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.client.GifClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class GifServiceImpl implements GifService {

    private final GifClient gifClient;
    private final ObjectMapper mapper;
    @Value("${giphy.api_key}")
    private String gifApiKey;

    @Override
    public String getGifApiKey() {
        return gifApiKey;
    }

    @Override
    public String getGifUrl(String currencyRateStatus) {
        JsonNode node = null;
        Map<String, List<Object>> map = null;

        log.info("Getting GIF from Giphy");
        String responseData = gifClient.getGifResponse(gifApiKey, currencyRateStatus);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        int randomIndex = (int) (Math.random() * node.get("data").size());
        String gifUrl = node.get("data").get((int) (Math.random() * randomIndex)).get("images").get("original").get("url").asText();

        return gifUrl;
    }
}
