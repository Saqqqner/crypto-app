package ru.adel.feedback.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.Comment;

import java.util.UUID;

public interface CommentService {
    Mono<Comment> addComment(String parentId, String text, Long cryptoId, String userId, String username);

    Flux<Comment> getCommentsByUserId(String userId);

    Flux<Comment> getCommentsByCryptoId(Long cryptoId, Integer limit);

    Mono<Void> deleteComment(UUID commentId);

    Mono<Comment> getById(UUID commentId);

}
