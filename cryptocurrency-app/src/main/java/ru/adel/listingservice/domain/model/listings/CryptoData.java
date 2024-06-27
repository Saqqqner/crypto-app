package ru.adel.listingservice.domain.model.listings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(indexName = "crypto-data")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoData {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Keyword)
    private String symbol;
    @Field(type = FieldType.Keyword)
    private String slug;
    @JsonProperty("num_market_pairs")
    private Integer numMarketPairs;
    @JsonProperty("date_added")
    private Date dateAdded;
    private List<String> tags;
    @JsonProperty("max_supply")
    private Double maxSupply;
    @JsonProperty("circulating_supply")
    private Double circulatingSupply;
    @JsonProperty("total_supply")
    private Double totalSupply;
    @JsonProperty("infinite_supply")
    private boolean infiniteSupply;
    private Platform platform;
    @JsonProperty("cmc_rank")
    private Integer cmcRank;
    @JsonProperty("last_updated")
    private Date lastUpdate;
    private Map<String, CryptoQuote> quote;
}



