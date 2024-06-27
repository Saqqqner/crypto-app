package ru.adel.listingservice.domain.model.listings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseStatus(
        @JsonProperty("error_code") Long errorCode,
        @JsonProperty("error_message") String errorMessage,
        @JsonProperty("elapsed") Long elapsed,
        @JsonProperty("timestamp") Date timestamp,
        @JsonProperty("notice") String notice
) {

}
