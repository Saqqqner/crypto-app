package ru.adel.user.web.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.model.CryptoData;

public interface CryptoWebClient {
    Flux<CryptoData> findAllCryptoData(Integer start, Integer limit);
    Flux<CryptoData>findAllCryptoData();


    Mono<CryptoData> findByName(String name);
}
