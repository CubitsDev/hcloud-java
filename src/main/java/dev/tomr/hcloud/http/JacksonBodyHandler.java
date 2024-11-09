package dev.tomr.hcloud.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.model.HetznerErrorResponse;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Internal helper to handle the converting of a returned HTTP response's body, to an Object, with Jackson ObjectMapper
 * @param <T>
 */
public class JacksonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    private final Class<T> clazz;

    public JacksonBodyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        switch (responseInfo.statusCode()) {
            case 200, 201 -> {
                return jsonPayload(clazz);
            }
            default -> {
                return apiErrorJson();
            }
        }
    }

    public static <T> HttpResponse.BodySubscriber<T> jsonPayload(Class<T> targetClass) {
        HttpResponse.BodySubscriber<String> bodyString = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
        return HttpResponse.BodySubscribers.mapping(bodyString, (String b) -> {
            try {
                return HetznerCloud.getObjectMapper().readValue(b, targetClass);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static HttpResponse.BodySubscriber apiErrorJson() {
        HttpResponse.BodySubscriber<String> bodyString = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
        return HttpResponse.BodySubscribers.mapping(bodyString, (String b) -> {
            try {
                return HetznerCloud.getObjectMapper().readValue(b, HetznerErrorResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
