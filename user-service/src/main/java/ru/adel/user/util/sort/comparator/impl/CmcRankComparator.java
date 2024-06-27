package ru.adel.user.util.sort.comparator.impl;

import org.springframework.stereotype.Component;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.sort.comparator.CryptoDataComparator;

import java.util.Comparator;

@Component("cmcRank")
public class CmcRankComparator implements CryptoDataComparator {
    @Override
    public Comparator<CryptoData> getComparator() {
        return Comparator.comparing(CryptoData::getCmcRank);
    }
}
