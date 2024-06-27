package ru.adel.user.util.sort.comparator;

import ru.adel.user.domain.model.CryptoData;

import java.util.Comparator;

public interface CryptoDataComparator {
     Comparator<CryptoData> getComparator();
}
