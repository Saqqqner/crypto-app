package ru.adel.listingservice.web.client.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.adel.listingservice.domain.exception.AppException;
import ru.adel.listingservice.domain.model.ResponseAPI;
import ru.adel.listingservice.domain.model.listings.CryptoData;
import ru.adel.listingservice.service.CryptoDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoDataApiClientAction implements ApiClientAction {

    private final CryptoDataService service;
    private final ObjectMapper objectMapper;

    @Override
    public void performAction(List<String> data) {
        try {
            List<CryptoData> combinedCryptoDataList = parseData(data);
            List<CryptoData> mergedCryptoDataList = mergeCryptoData(combinedCryptoDataList);
            removeUnwantedCryptos(mergedCryptoDataList, "Theta Network");
            service.saveAllData(mergedCryptoDataList);
            log.info("Данные успешно сохранены");
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
            throw new AppException(e);
        }
    }

    private List<CryptoData> parseData(List<String> data) throws JsonProcessingException {
        List<CryptoData> combinedCryptoDataList = new ArrayList<>();
        for (String json : data) {
            ResponseAPI<List<CryptoData>> response = objectMapper.readValue(
                    json, new TypeReference<ResponseAPI<List<CryptoData>>>() {
                    });
            combinedCryptoDataList.addAll(response.data());
        }
        return combinedCryptoDataList;
    }

    private List<CryptoData> mergeCryptoData(List<CryptoData> combinedCryptoDataList) {
        Map<String, CryptoData> cryptoDataMap = new HashMap<>();

        for (CryptoData cryptoData : combinedCryptoDataList) {
            String cryptoName = cryptoData.getName();
            if (cryptoDataMap.containsKey(cryptoName)) {
                CryptoData existingCryptoData = cryptoDataMap.get(cryptoName);
                existingCryptoData.getQuote().putAll(cryptoData.getQuote());
            } else {
                cryptoDataMap.put(cryptoName, cryptoData);
            }
        }
        return new ArrayList<>(cryptoDataMap.values());
    }

    private void removeUnwantedCryptos(List<CryptoData> cryptoDataList, String unwantedCryptoName) {
        cryptoDataList.removeIf(cryptoData -> unwantedCryptoName.equals(cryptoData.getName()));
    }
}
