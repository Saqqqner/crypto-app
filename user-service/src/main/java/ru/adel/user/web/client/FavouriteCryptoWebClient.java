package ru.adel.user.web.client;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.model.FavouriteCrypto;

public interface FavouriteCryptoWebClient {
    Mono<FavouriteCrypto> addCryptoToFavourites(Long cryptoId);
    Flux<FavouriteCrypto> getFavouriteCryptosByUserId();
    Mono<Long> getFavouriteCryptoCountByCryptoId(Long cryptoId);
    Mono<FavouriteCrypto> findFavouriteCryptoByCryptoId(Long cryptoId);
    Mono<Void>  removeCryptoFromFavourite(Long cryptoId);



}
