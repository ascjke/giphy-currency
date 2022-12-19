package ru.borisov.giphycurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GiphyCurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiphyCurrencyApplication.class, args);
	}

}
