package ru.adel.user.web.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.exception.ClientBadRequestException;
import ru.adel.user.domain.model.FavouriteCrypto;
import ru.adel.user.web.client.FavouriteCryptoWebClient;
import ru.adel.user.web.controller.dto.FavouriteCryptoDto;

import java.util.List;

@RequiredArgsConstructor
public class FavouriteCryptoWebClientImpl implements FavouriteCryptoWebClient {
    private final WebClient webClient;
    @Override
    public Mono<FavouriteCrypto> addCryptoToFavourites(Long cryptoId) {
        return webClient.post()
                .uri("/favourite-cryptos")
                .bodyValue(new FavouriteCryptoDto(cryptoId))
                .retrieve()
                .bodyToMono(FavouriteCrypto.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        ex ->
                                new ClientBadRequestException("Возникла ошибка при добавлении криптовалюты в избранное", ex,
                                        ((List<String>) ex.getResponseBodyAs(ProblemDetail.class)
                                                .getProperties().get("errors"))));

    }

    @Override
    public Flux<FavouriteCrypto> getFavouriteCryptosByUserId() {
        return webClient.get()
                .uri("/favourite-cryptos")
                .retrieve()
                .bodyToFlux(FavouriteCrypto.class);
    }

    @Override
    public Mono<Long> getFavouriteCryptoCountByCryptoId(Long cryptoId) {
        return webClient.get()
                .uri("/favourite-cryptos/by-crypto-id/{cryptoId}/count",cryptoId)
                .retrieve()
                .bodyToMono(Long.class);
    }

    @Override
    public Mono<FavouriteCrypto> findFavouriteCryptoByCryptoId(Long cryptoId) {
        return webClient.get()
                .uri("/favourite-cryptos/by-crypto-id/{cryptoId}",cryptoId)
                .retrieve()
                .bodyToMono(FavouriteCrypto.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);

    }


    @Override
    public Mono<Void> removeCryptoFromFavourite(Long cryptoId) {
        return webClient.delete()
                .uri("/favourite-cryptos/by-crypto-id/{cryptoId}",
                        cryptoId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
