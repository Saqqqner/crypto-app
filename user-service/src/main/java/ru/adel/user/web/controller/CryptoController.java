package ru.adel.user.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.adel.user.web.controller.constants.ControllerConstants;
import ru.adel.user.domain.exception.ClientBadRequestException;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.encoder.UriEncoder;
import ru.adel.user.web.client.CommentWebClient;
import ru.adel.user.web.client.CryptoWebClient;
import ru.adel.user.web.client.FavouriteCryptoWebClient;
import ru.adel.user.web.controller.dto.NewCommentDto;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("user-api/crypto/{name}")
@Slf4j
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoWebClient cryptoClient;
    private final FavouriteCryptoWebClient favouriteClient;
    private final CommentWebClient commentWebClient;

    @ModelAttribute(name = "crypto", binding = false)
    public Mono<CryptoData> loadCryptoData(@PathVariable String name) {
        return cryptoClient.findByName(name);
    }

    @GetMapping()
    public Mono<String> getCryptoByName(
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono,
            @RequestParam(name = "limit", required = false) Integer limit,
            Model model) {
        return cryptoDataMono.flatMap(cryptoData ->
                commentWebClient.getCommentsByCrypto(cryptoData.getId(), limit)
                        .collectList()
                        .doOnNext(comments -> model.addAttribute(ControllerConstants.COMMENTS, comments))
                        .then(Mono.defer(() ->
                                favouriteClient.findFavouriteCryptoByCryptoId(cryptoData.getId())
                                        .doOnNext(favouriteCrypto -> model.addAttribute(ControllerConstants.IN_FAVOURITE, true))
                                        .switchIfEmpty(Mono.defer(() -> {
                                            model.addAttribute(ControllerConstants.IN_FAVOURITE, false);
                                            return Mono.empty();
                                        }))
                        ))
                        .then(favouriteClient.getFavouriteCryptoCountByCryptoId(cryptoData.getId())
                                .doOnNext(count -> model.addAttribute(ControllerConstants.FAVOURITE_COUNT, count))
                        )
                        .thenReturn("cryptos/crypto")
        );
    }

    @PostMapping("/add-to-favourites")
    public Mono<String> addProductToFavourites(
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono) {
        return cryptoDataMono.flatMap(cryptoData ->
                favouriteClient.addCryptoToFavourites(cryptoData.getId())
                        .thenReturn(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()));
                        })
        );
    }

    @PostMapping("/remove-from-favourites")
    public Mono<String> removeProductFromFavourites(
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono) {
        return cryptoDataMono
                .flatMap(cryptoData -> favouriteClient.removeCryptoFromFavourite(cryptoData.getId())
                        .thenReturn(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()));
                        })
                );
    }

    @PostMapping("/comments/add-comment")
    public Mono<String> addComment(
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono,
            NewCommentDto commentDto,
            Model model,
            ServerHttpResponse response) {
        return cryptoDataMono.flatMap(cryptoData ->
                commentWebClient.addComment(commentDto.getParentId(), cryptoData.getId(), commentDto.getText())
                        .flatMap(comment ->
                              Mono.just(buildRedirectUrl(cryptoData.getName(), commentDto.getParentId()))
                        )
                        .onErrorResume(ClientBadRequestException.class, exception -> {
                            model.addAttribute(ControllerConstants.IN_FAVOURITE, false);
                            model.addAttribute(ControllerConstants.COMMENTS, commentDto);
                            model.addAttribute(ControllerConstants.ERRORS, exception.getErrors());
                            response.setStatusCode(HttpStatus.BAD_REQUEST);
                            return favouriteClient.findFavouriteCryptoByCryptoId(cryptoData.getId())
                                    .doOnNext(favouriteProduct -> model.addAttribute(ControllerConstants.IN_FAVOURITE, true))
                                    .thenReturn(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()));
                        }));
    }

    @PostMapping("/comments/delete-comment/{commentId}")
    public Mono<String> deleteComment(
            @PathVariable String commentId,
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono,
            Model model,
            ServerHttpResponse response) {

        return cryptoDataMono.flatMap(cryptoData ->
                commentWebClient.deleteComment(commentId)
                        .thenReturn(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()))
                        .onErrorResume(ClientBadRequestException.class, exception -> {
                            model.addAttribute("errors", exception.getMessage());
                            response.setStatusCode(HttpStatus.NOT_FOUND);
                            return favouriteClient.findFavouriteCryptoByCryptoId(cryptoData.getId())
                                    .doOnNext(favouriteProduct -> model.addAttribute(ControllerConstants.IN_FAVOURITE, true))
                                    .thenReturn(ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoData.getName()));
                        }));
    }
    @GetMapping("/comments/{commentId}")
    public Mono<String> getCommentById(
            @PathVariable String commentId,
            @ModelAttribute("crypto") Mono<CryptoData> cryptoDataMono,
            @RequestParam(name = "limit", required = false) Integer limit,
            Model model,
            ServerHttpResponse response) {
        return cryptoDataMono.flatMap(cryptoData ->
                commentWebClient.getCommentsByCrypto(cryptoData.getId(), limit)
                        .collectList()
                        .doOnNext(comments -> model.addAttribute(ControllerConstants.COMMENTS, comments))
                        .then(commentWebClient.getCommentById(UUID.fromString(commentId))
                                .flatMap(comment -> {
                                    model.addAttribute(ControllerConstants.COMMENT, comment);
                                    return Mono.just("comments/comment-details");
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                    response.setStatusCode(HttpStatus.NOT_FOUND);
                                    model.addAttribute(ControllerConstants.ERRORS, "Комментарий не найден");
                                    return Mono.just("errors/errorPage");
                                }))
                        )
        );
    }
    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return Objects.requireNonNull(exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName()))
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }
    private String buildRedirectUrl(String cryptoName, String parentId) {
        if (parentId != null) {
            return "redirect:/user-api/crypto/" + UriEncoder.encodeName(cryptoName) + "/comments/" + parentId;
        } else {
            return ControllerConstants.REDIRECT_PREFIX + UriEncoder.encodeName(cryptoName);
        }
    }
}
