package ru.adel.feedback.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("favourite_crypto")
public class FavouriteCrypto {
    private UUID id;
    private Long cryptoId;
    private String userId;
}
