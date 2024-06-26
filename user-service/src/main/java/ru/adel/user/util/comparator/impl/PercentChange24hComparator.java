package ru.adel.user.util.comparator.impl;

import org.springframework.stereotype.Component;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.comparator.CryptoDataComparator;

import java.util.Comparator;

@Component("percentChange24h")
public class PercentChange24hComparator implements CryptoDataComparator {
    @Override
    public Comparator<CryptoData> getComparator() {
        return Comparator.comparing(cryptoData -> cryptoData.getQuote().get("USD").percentChange24h());
    }
}


