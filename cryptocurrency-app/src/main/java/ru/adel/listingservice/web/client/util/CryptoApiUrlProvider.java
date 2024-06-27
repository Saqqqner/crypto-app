package ru.adel.listingservice.web.client.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CryptoApiUrlProvider {
    private final Map<String,String> urls= new HashMap<>();

    public CryptoApiUrlProvider(
            @Value("${coinmarketcap.api.url.listings.latest.usd}") String urlUSD,
            @Value("${coinmarketcap.api.url.listings.latest.rub}") String urlRUB,
            @Value("${coinmarketcap.api.url.listings.latest.btc}")String urlBTC) {
        urls.put("USD",urlUSD);
        urls.put("RUB",urlRUB);
        urls.put("BTC",urlBTC);
    }
    public String getUrl(String currency) {
        return urls.get(currency);
    }


}
