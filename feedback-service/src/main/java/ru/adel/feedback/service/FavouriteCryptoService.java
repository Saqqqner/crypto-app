package ru.adel.feedback.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.FavouriteCrypto;

public interface FavouriteCryptoService {
    Mono<FavouriteCrypto> addCryptoToFavourites(Long cryptoId, String userId);

    Flux<FavouriteCrypto> getFavouriteCryptosByUserId(String userId);

    Mono<Long> getFavouriteCryptoCountByCryptoId(Long cryptoId);

    Mono<Void> removeCryptoFromFavourites(Long cryptoId, String userId);

    Mono<FavouriteCrypto> getFavouriteCryptoByCryptoIdAndUserId(Long cryptoId, String userId);
}
