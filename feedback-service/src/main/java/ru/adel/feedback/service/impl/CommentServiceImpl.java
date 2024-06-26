package ru.adel.feedback.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.exception.ResourceNotFoundException;
import ru.adel.feedback.domain.model.Comment;
import ru.adel.feedback.repository.CommentRepository;
import ru.adel.feedback.service.CommentService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Mono<Comment> addComment(String parentId, String text, Long cryptoId, String userId, String username) {
        UUID parentUUID = parentId != null ? UUID.fromString(parentId) : null;
        Comment comment = new Comment(UUID.randomUUID(), parentUUID, text, cryptoId, userId, username, LocalDateTime.now());
        return repository.save(comment);
    }

    @Override
    public Flux<Comment> getCommentsByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Flux<Comment> getCommentsByCryptoId(Long cryptoId, Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return repository.findAllByCryptoIdOrderByCreatedAtDesc(cryptoId, pageable);
    }

    @Override
    public Mono<Void> deleteComment(UUID commentId) {
        return repository.findById(commentId)
                .flatMap(repository::delete);
    }

    @Override
    public Mono<Comment> getById(UUID commentId) {
        return repository.findById(commentId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(String.format("Комментарий не найден с таким id %s", commentId))));
    }
}
