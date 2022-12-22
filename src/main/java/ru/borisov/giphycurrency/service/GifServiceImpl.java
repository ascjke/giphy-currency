package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.repository.GifRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class GifServiceImpl implements GifService {

    private final GifRepository gifRepository;

    @Override
    public String getGifApiKey() {
        return gifRepository.getGifApiKey();
    }

    @Override
    public String getGifUrl(String currencyRateStatus) {
        JsonNode data = gifRepository.getDataJsonNode(currencyRateStatus);

        int randomIndex = (int) (Math.random() * data.size());
        String gifUrl = data.get((int) (Math.random() * randomIndex)).get("images").get("original").get("url").asText();

        return gifUrl;
    }
}
