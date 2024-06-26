package ru.adel.listingservice.service;

import ru.adel.listingservice.domain.model.listings.CryptoData;

import java.util.List;

public interface CryptoDataService extends GenericService<CryptoData> {
    List<CryptoData> getDataList(int start, int limit);
    void saveAllData(List<CryptoData> dataList);
}
