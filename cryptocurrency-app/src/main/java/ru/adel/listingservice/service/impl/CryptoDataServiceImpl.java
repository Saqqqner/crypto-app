package ru.adel.listingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adel.listingservice.domain.exception.ResourceNotFoundException;
import ru.adel.listingservice.domain.model.listings.CryptoData;
import ru.adel.listingservice.repository.CryptoDataRepository;
import ru.adel.listingservice.service.CryptoDataService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoDataServiceImpl implements CryptoDataService {
    private final CryptoDataRepository repository;

    @Override
    public List<CryptoData> getDataList(int start, int limit) {
        Pageable pageable = PageRequest.of(start, limit);
        Page<CryptoData> cryptoDataPage = repository.findAll(pageable);
        return cryptoDataPage.getContent();
    }

    @Override
    public CryptoData getByName(String name) {
        return repository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(String.format("CryptoData not found with this name %s", name)));
    }

    @Override
    public void saveData(CryptoData data) {
        repository.save(data);
    }

    @Override
    public void saveAllData(List<CryptoData> cryptoData) {
        repository.saveAll(cryptoData);
    }
}
