package ru.adel.feedback.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.FavouriteCrypto;
import ru.adel.feedback.service.FavouriteCryptoService;
import ru.adel.feedback.web.dto.FavouriteCryptoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-api/favourite-cryptos")
@Slf4j
public class FavouriteCryptoController {
    private final FavouriteCryptoService favouriteCryptoService;

    @PostMapping()
    public Mono<ResponseEntity<FavouriteCrypto>> addCryptoToFavourites(
            Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono,
            @RequestBody Mono<FavouriteCryptoDto> cryptoDtoMono) {
        return Mono.zip(jwtAuthenticationTokenMono, cryptoDtoMono)
                .flatMap(tuple -> favouriteCryptoService.addCryptoToFavourites(tuple.getT2().getCryptoId(), tuple.getT1().getToken().getSubject()))
                .doOnSuccess(favouriteCrypto -> log.info("Crypto added to favourites: {}", favouriteCrypto))
                .map(ResponseEntity::ok);
    }

    @GetMapping()
    public Flux<FavouriteCrypto> getFavouriteCryptosByUser(Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono) {
        return jwtAuthenticationTokenMono
                .flatMapMany(token -> favouriteCryptoService.getFavouriteCryptosByUserId(token.getToken().getSubject()));
    }


    @GetMapping("by-crypto-id/{cryptoId:\\d+}")
    public Mono<FavouriteCrypto> findFavouriteCryptoByCryptoId(
            Mono<JwtAuthenticationToken> authenticationTokenMono,
            @PathVariable Long cryptoId) {
        return authenticationTokenMono.flatMap(token ->
                favouriteCryptoService.getFavouriteCryptoByCryptoIdAndUserId(cryptoId, token.getToken().getSubject()));
    }

    @DeleteMapping("by-crypto-id/{cryptoId:\\d+}")
    public Mono<ResponseEntity<Void>> removeCryptoFromFavourites(
            Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono,
            @PathVariable Long cryptoId) {
        return jwtAuthenticationTokenMono.
                flatMap(token -> favouriteCryptoService.removeCryptoFromFavourites(cryptoId, token.getToken().getSubject()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/by-crypto-id/{cryptoId:\\d+}/count")
    public Mono<ResponseEntity<Long>> getFavouriteCryptoCountByCryptoId(@PathVariable Long cryptoId) {
        return favouriteCryptoService.getFavouriteCryptoCountByCryptoId(cryptoId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
