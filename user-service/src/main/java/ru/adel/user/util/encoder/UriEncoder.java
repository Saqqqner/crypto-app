package ru.adel.user.util.encoder;

import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

public class UriEncoder {

    private UriEncoder(){}

    public static String encodeName(String name) {
        return UriUtils.encodePath(name, StandardCharsets.UTF_8);
    }
}
