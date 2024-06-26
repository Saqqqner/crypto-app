package ru.adel.listingservice.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.adel.listingservice.domain.model.listings.CryptoData;

import java.util.Optional;

public interface CryptoDataRepository extends ElasticsearchRepository<CryptoData, Long> {
    @Cacheable("cryptoDataByName")
    Optional<CryptoData> findByName(String name);


    @Override
    @CacheEvict(value = "cryptoDataByName", allEntries = true)
    <S extends CryptoData> Iterable<S> saveAll(Iterable<S> entities);
}
