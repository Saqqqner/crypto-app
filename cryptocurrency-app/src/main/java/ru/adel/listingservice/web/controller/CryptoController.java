package ru.adel.listingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adel.listingservice.domain.model.listings.CryptoData;
import ru.adel.listingservice.service.CryptoDataService;

import java.util.List;

@RestController
@RequestMapping("/cryptocurrency-api/crypto")
@RequiredArgsConstructor
public class CryptoController {
    private final CryptoDataService cryptoDataService;

    @GetMapping("/name/{name}")
    public ResponseEntity<CryptoData> getCryptoDataByName(@PathVariable("name") String name) {
        CryptoData data = cryptoDataService.getByName(name);
        return ResponseEntity.ok(data);
    }

    @GetMapping()
    public ResponseEntity<List<CryptoData>> getAllCryptoData(
            @RequestParam(name = "start", defaultValue = "0", required = false) Integer start,
            @RequestParam(name = "limit", defaultValue = "100", required = false) Integer limit) {
        List<CryptoData> dataList = cryptoDataService.getDataList(start, limit);
        return ResponseEntity.ok(dataList);
    }
}
