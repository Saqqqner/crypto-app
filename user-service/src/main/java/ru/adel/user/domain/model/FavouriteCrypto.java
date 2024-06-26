package ru.adel.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavouriteCrypto {
    private UUID id;
    private Long cryptoId;
    private String userId;
}
