package ru.borisov.giphycurrency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gifClient", url = "${giphy.url}")
public interface GifClient {

    @GetMapping()
    String getGifResponse(@RequestParam("api_key") String gifApiKey,
                                 @RequestParam("q") String rate);

}
