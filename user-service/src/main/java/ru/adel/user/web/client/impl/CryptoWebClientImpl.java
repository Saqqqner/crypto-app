package ru.adel.user.web.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.exception.ResourceNotFoundException;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.web.client.CryptoWebClient;

@RequiredArgsConstructor
public class CryptoWebClientImpl implements CryptoWebClient {

    private final WebClient webClient;

    @Override
    public Flux<CryptoData> findAllCryptoData(Integer start, Integer limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/crypto")
                        .queryParam("start", start)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToFlux(CryptoData.class);

    }

    @Override
    public Flux<CryptoData> findAllCryptoData() {
        return webClient.get()
                .uri("/crypto")
                .retrieve()
                .bodyToFlux(CryptoData.class);

    }


    public Mono<CryptoData> findByName(String name) {
        return webClient.get()
                .uri("/crypto/name/" + name)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                .bodyToMono(CryptoData.class);
    }

    private Mono<Throwable> handle4xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorMessage -> Mono.error(new ResourceNotFoundException(errorMessage)));
    }
}
