package ru.adel.listingservice.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ApiRateLimiterConfig {
    @Value("${coinmarketcap.api.rateLimit.requestLimit}")
    private int requestLimit;
    @Value("${coinmarketcap.api.rateLimit.timeout.seconds}")
    private int timeoutSeconds;
    @Value("${coinmarketcap.api.rateLimit.refreshPeriod.minutes}")
    private int refreshPeriodMinutes;

    @Bean
    public RateLimiter rateLimiter(){
        RateLimiterConfig config= io.github.resilience4j.ratelimiter.RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMinutes(refreshPeriodMinutes))
                .limitForPeriod(requestLimit)
                .timeoutDuration(Duration.ofSeconds(timeoutSeconds))
                .build();
        return RateLimiter.of("coinMarketCapRateLimiter", config);
    }
}
