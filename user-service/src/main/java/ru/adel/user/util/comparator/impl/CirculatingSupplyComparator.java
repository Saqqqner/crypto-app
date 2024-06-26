package ru.adel.user.util.comparator.impl;

import org.springframework.stereotype.Component;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.comparator.CryptoDataComparator;

import java.util.Comparator;

@Component("circulatingSupply")
public class CirculatingSupplyComparator implements CryptoDataComparator {
    @Override
    public Comparator<CryptoData> getComparator() {
        return Comparator.comparing(CryptoData::getCirculatingSupply);
    }
}
