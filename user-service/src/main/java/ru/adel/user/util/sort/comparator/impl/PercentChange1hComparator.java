package ru.adel.user.util.sort.comparator.impl;

import org.springframework.stereotype.Component;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.sort.comparator.CryptoDataComparator;

import java.util.Comparator;

@Component("percentChange1h")
public class PercentChange1hComparator implements CryptoDataComparator {
    @Override
    public Comparator<CryptoData> getComparator() {
        return Comparator.comparing(cryptoData -> cryptoData.getQuote().get("USD").percentChange1h());
    }
}
