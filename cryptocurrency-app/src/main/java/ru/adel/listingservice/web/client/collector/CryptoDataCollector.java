package ru.adel.listingservice.web.client.collector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.adel.listingservice.web.client.CoinMarketCapApiClient;
import ru.adel.listingservice.web.client.action.ApiClientAction;
import ru.adel.listingservice.web.client.context.ApiClientContext;
import ru.adel.listingservice.web.client.util.CryptoApiUrlProvider;

@Component
@Slf4j
public class CryptoDataCollector implements DataCollector {
    private final CoinMarketCapApiClient coinMarketCapApiClient;
    private final ApiClientContext clientContext;

    public CryptoDataCollector(CoinMarketCapApiClient coinMarketCapApiClient,
                               ApiClientContext clientContext, CryptoApiUrlProvider cryptoApiUrls,
                               ApiClientAction apiClientAction) {
        this.coinMarketCapApiClient = coinMarketCapApiClient;
        this.clientContext = clientContext;
        this.clientContext.setAction(apiClientAction);
        this.clientContext.getUrlMap().putAll(cryptoApiUrls.getUrls());
    }

    @Override
    @Scheduled(fixedDelayString = "${coinmarketcap.api.scheduler.pollingIntervalMillis.listings}")
    public void collectData() {
        coinMarketCapApiClient.collectData(clientContext);

    }

}
