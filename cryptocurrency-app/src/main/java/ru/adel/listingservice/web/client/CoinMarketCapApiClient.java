package ru.adel.listingservice.web.client;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.adel.listingservice.web.client.context.ApiClientContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CoinMarketCapApiClient {
    private final RestClient restClient;
    private final RateLimiter rateLimiter;

    public CoinMarketCapApiClient(RestClient restClient, RateLimiter rateLimiter) {
        this.restClient = restClient;
        this.rateLimiter = rateLimiter;

    }

    public void collectData(ApiClientContext apiClientContext) {
        rateLimiter.executeRunnable(() -> {
            Map<String, String> urlMap = apiClientContext.getUrlMap();
            List<String> jsonList = new ArrayList<>();
            boolean allResponsesValid = true;
            for (Map.Entry<String,String> entry : urlMap.entrySet()) {
                String data = restClient.get()
                        .uri(entry.getValue())
                        .retrieve()
                        .body(String.class);
                if (data == null) {
                    log.error("Received empty response for key: " + entry.getKey());
                    allResponsesValid = false;
                    break;
                }
                jsonList.add(data);
            }
            if (allResponsesValid) {
                apiClientContext.getAction().performAction(jsonList);
            } else {
                log.error("One or more responses from CoinMarketCap were empty");
            }
        });
    }
}
