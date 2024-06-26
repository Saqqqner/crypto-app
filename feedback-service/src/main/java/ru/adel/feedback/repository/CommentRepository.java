package ru.adel.feedback.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.adel.feedback.domain.model.Comment;

import java.util.UUID;

public interface CommentRepository extends ReactiveMongoRepository<Comment, UUID> {
    Flux<Comment> findAllByUserId(String userId);

    Flux<Comment> findAllByCryptoId(Long cryptoId);

    Flux<Comment> findAllByCryptoIdOrderByCreatedAtDesc(Long cryptoId, Pageable pageable);


}
