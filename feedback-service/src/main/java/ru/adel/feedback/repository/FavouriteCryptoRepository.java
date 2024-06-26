package ru.adel.feedback.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.FavouriteCrypto;

import java.util.UUID;

public interface FavouriteCryptoRepository extends ReactiveMongoRepository<FavouriteCrypto, UUID> {
    Flux<FavouriteCrypto> findAllByUserId(String userId);

    Mono<Long> countByCryptoId(Long cryptoId);

    Mono<Void> deleteByCryptoIdAndUserId(Long cryptoId, String userId);

    Mono<FavouriteCrypto> findByCryptoIdAndUserId(Long cryptoId, String userId);
}
