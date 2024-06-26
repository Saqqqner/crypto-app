package ru.adel.user.web.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.model.Comment;

import java.util.UUID;

public interface CommentWebClient {
    Mono<Comment> addComment(String commentId,Long cryptoId,String text);
    Mono<Comment> getCommentById(UUID id);


    Flux<Comment> getCommentsByUser();
    Flux<Comment> getCommentsByCrypto(Long cryptoId,Integer limit);
    Mono<Void> deleteComment(String commentId);
}
