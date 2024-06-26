package ru.adel.user.util.sort;

import reactor.core.publisher.Mono;
import ru.adel.user.domain.model.CryptoData;

import java.util.List;

public interface CryptoSorter {
    Mono<List<CryptoData>> getSortedCryptoData(List<CryptoData> cryptoDataList, String fieldName, String sortDirection);
}
