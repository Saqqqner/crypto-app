package ru.adel.listingservice.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.adel.listingservice.domain.model.listings.ResponseStatus;

public record ResponseAPI<T>(
        @JsonProperty("status") ResponseStatus status,

        @JsonProperty("data") T data
) {
}
