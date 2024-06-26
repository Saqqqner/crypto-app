package ru.adel.feedback.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavouriteCryptoDto {
    private Long cryptoId;

    private Long userId;
}
