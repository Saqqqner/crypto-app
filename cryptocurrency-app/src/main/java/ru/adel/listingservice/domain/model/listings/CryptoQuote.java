package ru.adel.listingservice.domain.model.listings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CryptoQuote(
        @JsonProperty("percent_change_1h") Double percentChange1h,
        @JsonProperty("percent_change_24h") Double percentChange24h,
        @JsonProperty("percent_change_7d") Double percentChange7d,
        @JsonProperty("price") Double price,
        @JsonProperty("volume_24h") Double volume24h,
        @JsonProperty("market_cap") Double marketCap,
        @JsonProperty("last_updated") Date lastUpdated) {

}
