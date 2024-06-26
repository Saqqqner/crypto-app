package ru.adel.user.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Platform(
        Integer id,
        String name,
        String symbol,
        String slug,
        @JsonProperty("token_address")
        String tokenAddress) {

}
