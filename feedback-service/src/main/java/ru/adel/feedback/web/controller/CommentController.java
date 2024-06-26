package ru.adel.feedback.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.feedback.domain.model.Comment;
import ru.adel.feedback.service.CommentService;
import ru.adel.feedback.web.dto.CommentDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Comment>> getCommentById(@PathVariable("id") String id) {
        return commentService.getById(UUID.fromString(id))
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<Comment>> addComment(Mono<JwtAuthenticationToken> jwtAuthenticationTokenMono,
                                                    @RequestBody Mono<CommentDto> commentDtoMono, UriComponentsBuilder uriComponentsBuilder) {
        return jwtAuthenticationTokenMono.flatMap(token -> commentDtoMono.flatMap(commentDto ->
                        commentService.addComment(commentDto.getParentId(), commentDto.getText(), commentDto.getCryptoId(), token.getToken().getSubject(), token.getToken().getClaim("preferred_username"))))
                .map(comment -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("/feedback-api/comments/by-crypto-id/{id}")
                                .build(comment.getId()))
                        .body(comment));
    }

    @GetMapping("/by-user-id/{userId}")
    public Flux<Comment> getCommentsByUser(@PathVariable String userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping("/by-crypto-id/{cryptoId}")
    public Flux<Comment> getCommentsByCrypto(@PathVariable Long cryptoId,
                                             @RequestParam(name = "limit", defaultValue = "15", required = false) Integer limit) {
        return commentService.getCommentsByCryptoId(cryptoId, limit);
    }

    @DeleteMapping("/{commentId}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable UUID commentId) {
        return commentService.deleteComment(commentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
