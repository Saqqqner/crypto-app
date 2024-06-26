package ru.adel.user.util.sort.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.util.sort.CryptoSorter;
import ru.adel.user.util.comparator.CryptoDataComparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CryptoSorterImpl implements CryptoSorter {
    private final Map<String, CryptoDataComparator> comparatorMap;
    @Override
    public Mono<List<CryptoData>> getSortedCryptoData(List<CryptoData> cryptoDataList, String fieldName, String sortDirection) {
        Comparator<CryptoData> comparator = getComparator(fieldName, sortDirection);
        return Flux.fromIterable(cryptoDataList)
                .sort(comparator)
                .collectList();
    }
    private Comparator<CryptoData> getComparator(String sortField, String sortDirection) {
        CryptoDataComparator cryptoDataComparator = comparatorMap.get(sortField);
        if (cryptoDataComparator == null) {
            throw new IllegalArgumentException("Unsupported sort field: " + sortField);
        }
        Comparator<CryptoData> comparator = cryptoDataComparator.getComparator();
        if ("desc".equals(sortDirection)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
