package ru.adel.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.adel.user.util.formatter.DecimalFormatter;

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




    public String getFormattedPrice() {
        return DecimalFormatter.formatDecimal(price);
    }
    public String getFormattedVolume() {
        return DecimalFormatter.formatDecimal(volume24h);
    }
    public String getFormattedMarketCap() {
        return DecimalFormatter.formatDecimal(marketCap);
    }
    public String getFormattedPercentChange1h(){return DecimalFormatter.formatDecimal(percentChange1h);}
    public String getFormattedPercentChange24h(){return DecimalFormatter.formatDecimal(percentChange24h);}
    public String getFormattedPercentChange7d(){return DecimalFormatter.formatDecimal(percentChange7d);}
}


