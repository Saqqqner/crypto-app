package ru.adel.user.web.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.exception.ClientBadRequestException;
import ru.adel.user.domain.model.Comment;
import ru.adel.user.web.client.CommentWebClient;
import ru.adel.user.web.controller.dto.CommentDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CommentWebClientImpl implements CommentWebClient {

    private final WebClient webClient;

    @Override
    public Mono<Comment> addComment(String parentId, Long cryptoId, String text) {
        return webClient.post()
                .uri("/comments")
                .bodyValue(new CommentDto(parentId, cryptoId, text))
                .retrieve()
                .bodyToMono(Comment.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        ex ->
                                new ClientBadRequestException("Возникла ошибка при добавлении комментария криптовалюты", ex,
                                        ((List<String>) ex.getResponseBodyAs(ProblemDetail.class)
                                                .getProperties().get("errors"))));

    }

    @Override
    public Mono<Comment> getCommentById(UUID id) {
        return webClient.get()
                .uri("/comments/{id}", id.toString())
                .retrieve()
                .bodyToMono(Comment.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }


    @Override
    public Flux<Comment> getCommentsByUser() {
        return webClient.get()
                .uri("/comments/by-user-id")
                .retrieve()
                .bodyToFlux(Comment.class);
    }

    @Override
    public Flux<Comment> getCommentsByCrypto(Long cryptoId, Integer limit) {
        return webClient.get()
                .uri("/comments/by-crypto-id/{crypto}?limit={limit}", cryptoId, limit)
                .retrieve()
                .bodyToFlux(Comment.class);
    }

    @Override
    public Mono<Void> deleteComment(String uuid) {
        return webClient.delete()
                .uri("/comments/{uuid}", uuid)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
