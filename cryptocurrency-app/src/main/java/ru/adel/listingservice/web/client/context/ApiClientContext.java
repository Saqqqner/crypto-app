package ru.adel.listingservice.web.client.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.adel.listingservice.web.client.action.ApiClientAction;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class ApiClientContext {
    private Map<String, String> urlMap;
    private ApiClientAction action;

    public ApiClientContext() {
        this.urlMap = new HashMap<>();
    }
}