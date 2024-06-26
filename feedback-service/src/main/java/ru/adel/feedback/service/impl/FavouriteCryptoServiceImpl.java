package ru.adel.feedback.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.FavouriteCrypto;
import ru.adel.feedback.repository.FavouriteCryptoRepository;
import ru.adel.feedback.service.FavouriteCryptoService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouriteCryptoServiceImpl implements FavouriteCryptoService {

    private final FavouriteCryptoRepository repository;

    @Override
    public Mono<FavouriteCrypto> addCryptoToFavourites(Long cryptoId, String userId) {
        return repository.save(new FavouriteCrypto(UUID.randomUUID(), cryptoId, userId));
    }

    @Override
    public Flux<FavouriteCrypto> getFavouriteCryptosByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Mono<Long> getFavouriteCryptoCountByCryptoId(Long cryptoId) {
        return repository.countByCryptoId(cryptoId);
    }

    @Override
    public Mono<Void> removeCryptoFromFavourites(Long cryptoId, String userId) {
        return repository.deleteByCryptoIdAndUserId(cryptoId, userId);
    }

    @Override
    public Mono<FavouriteCrypto> getFavouriteCryptoByCryptoIdAndUserId(Long cryptoId, String userId) {
        return repository.findByCryptoIdAndUserId(cryptoId, userId);
    }

}
