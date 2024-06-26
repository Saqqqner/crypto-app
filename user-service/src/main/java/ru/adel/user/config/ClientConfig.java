package ru.adel.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;
import ru.adel.user.web.client.FavouriteCryptoWebClient;
import ru.adel.user.web.client.impl.CommentWebClientImpl;
import ru.adel.user.web.client.impl.CryptoWebClientImpl;
import ru.adel.user.web.client.impl.FavouriteCryptoWebClientImpl;

@Configuration
public class ClientConfig {
    @Value("${crypto-app.services.feedback.url.base}")
    private String baseFeedbackUri;
    @Value("${crypto-app.services.cryptocurrency.url.base}")
    private String baseCryptoCurrencyUri;

    @Bean
    @Scope("prototype")
    public WebClient.Builder cryptoServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
                        authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }

    @Bean
    CryptoWebClientImpl listingClient(WebClient.Builder cryptoServicesWebClientBuilder) {
        return new CryptoWebClientImpl(cryptoServicesWebClientBuilder
                .baseUrl(baseCryptoCurrencyUri)
                .build());
    }

    @Bean
    CommentWebClientImpl commentWebClient(WebClient.Builder cryptoServicesWebClientBuilder) {
        return new CommentWebClientImpl(cryptoServicesWebClientBuilder
                .baseUrl(baseFeedbackUri)
                .build());
    }

    @Bean
    FavouriteCryptoWebClient favouriteCryptoWebClient(WebClient.Builder cryptoServicesWebClientBuilder) {
        return new FavouriteCryptoWebClientImpl(cryptoServicesWebClientBuilder
                .baseUrl(baseFeedbackUri)
                .build());
    }

}
