package ru.adel.listingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class WebClientConfig {
    @Value("${coinmarketcap.api.key}")
    private String apiKey;
    @Value("${coinmarketcap.api.url.base}")
    private String baseUrl;

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader("X-CMC_PRO_API_KEY", apiKey)
                .build();
    }
}
