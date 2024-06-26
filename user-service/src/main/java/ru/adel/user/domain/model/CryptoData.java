package ru.adel.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.adel.user.util.formatter.DecimalFormatter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoData{
        private Long id;
        private String name;
        private String symbol;
        private String slug;
        private @JsonProperty("num_market_pairs") Integer numMarketPairs;
        private @JsonProperty("date_added") Date dateAdded;
        private List<String> tags;
        private @JsonProperty("max_supply") Double maxSupply;
        private @JsonProperty("circulating_supply") Double circulatingSupply;
        private @JsonProperty("total_supply") Double totalSupply;
        private @JsonProperty("infinite_supply") boolean infiniteSupply;
        private Platform platform;
        private @JsonProperty("cmc_rank") Integer cmcRank;
        private @JsonProperty("last_updated") Date lastUpdate;
        private Map<String, CryptoQuote> quote;


    public String getFormattedMaxSupply() {
        if (maxSupply != null && !infiniteSupply) {
                return DecimalFormatter.formatDecimal(maxSupply);
        }
        return "-";
    }

    public String getFormattedCirculatingSupply() {
        return DecimalFormatter.formatDecimal(circulatingSupply);
    }

}
