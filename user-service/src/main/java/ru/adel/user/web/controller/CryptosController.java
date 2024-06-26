package ru.adel.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import reactor.core.publisher.Mono;
import ru.adel.user.web.controller.constants.ControllerConstants;
import ru.adel.user.domain.model.CryptoData;
import ru.adel.user.domain.model.FavouriteCrypto;
import ru.adel.user.util.sort.CryptoSorter;
import ru.adel.user.web.client.CryptoWebClient;
import ru.adel.user.web.client.FavouriteCryptoWebClient;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-api/crypto")
public class CryptosController {
    private final CryptoWebClient listingClient;
    private final FavouriteCryptoWebClient favouriteClient;
    private final CryptoSorter cryptoSorter;

    @GetMapping()
    public Mono<String> getLatestCrypto(Model model,
                                        @RequestParam(name = "start", required = false) Integer start,
                                        @RequestParam(name = "limit", required = false) Integer limit,
                                        @RequestParam(name = "sortField", required = false) String sortField,
                                        @RequestParam(name = "sortDirection", required = false) String sortDirection) {
        Mono<List<CryptoData>> cryptoDataMono = listingClient.findAllCryptoData(start, limit).collectList();
        if (sortField != null && sortDirection != null) {
            return cryptoDataMono
                    .flatMap(cryptoDataList -> cryptoSorter.getSortedCryptoData(cryptoDataList, sortField, sortDirection))
                    .doOnNext(sortedCryptoData -> model.addAttribute(ControllerConstants.CRYPTOS, sortedCryptoData))
                    .thenReturn("cryptos/list");
        } else {
            return cryptoDataMono
                    .doOnNext(cryptoDataList -> model.addAttribute(ControllerConstants.CRYPTOS, cryptoDataList))
                    .thenReturn("cryptos/list");
        }
    }
    @GetMapping("/favourites")
    public Mono<String> getFavouriteProductsPage(Model model,
                                                 @RequestParam(name = "sortField", required = false) String sortField,
                                                 @RequestParam(name = "sortDirection", required = false) String sortDirection,
                                                 @SessionAttribute(name = "cachedFavourites", required = false) List<CryptoData> cachedFavourites) {
        if (cachedFavourites != null && sortField != null && sortDirection != null) {
            return Mono.just(cachedFavourites)
                    .flatMap(cryptoDataList -> cryptoSorter.getSortedCryptoData(cryptoDataList, sortField, sortDirection))
                    .doOnNext(sortedCryptoData -> model.addAttribute(ControllerConstants.CRYPTOS, sortedCryptoData))
                    .thenReturn("cryptos/favourites");
        } else {
            return favouriteClient.getFavouriteCryptosByUserId()
                    .map(FavouriteCrypto::getCryptoId)
                    .collectList()
                    .flatMap(favouriteCryptoIds ->
                            listingClient.findAllCryptoData()
                                    .filter(cryptoData -> favouriteCryptoIds.contains(cryptoData.getId()))
                                    .collectList()
                                    .flatMap(cryptoDataList -> {
                                        if (sortField != null && sortDirection != null) {
                                            return cryptoSorter.getSortedCryptoData(cryptoDataList, sortField, sortDirection);
                                        } else {
                                            return Mono.just(cryptoDataList);
                                        }
                                    })
                                    .doOnNext(cryptoDataList -> {
                                        model.addAttribute(ControllerConstants.CRYPTOS, cryptoDataList);
                                        model.addAttribute(ControllerConstants.CASHED_FAVOURITES, cryptoDataList);
                                    })
                    )
                    .thenReturn("cryptos/favourites");
        }
    }
}
