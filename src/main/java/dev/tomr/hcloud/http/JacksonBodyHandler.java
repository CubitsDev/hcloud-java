package dev.tomr.hcloud.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.model.HetznerErrorResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * Internal helper to handle the converting of a returned HTTP response's body, to an Object, with Jackson ObjectMapper
 * @param <T>
 */
public class JacksonBodyHandler<T, U> implements HttpResponse.BodyHandler<HetznerResult<T, U>> {

    private final Class<T> clazz;
    private final Class<U> errorClass;

    private int statusCode;

    public JacksonBodyHandler(Class<T> clazz, Class<U> errorClass) {
        this.clazz = clazz;
        this.errorClass = errorClass;
    }

    @Override
    public HttpResponse.BodySubscriber<HetznerResult<T, U>> apply(HttpResponse.ResponseInfo responseInfo) {
        Function<InputStream, HetznerResult<T, U>> mapper;
        statusCode = responseInfo.statusCode();
        switch (Integer.parseInt(Integer.toString(statusCode).substring(0, 1))) {
            case 2 -> {
                mapper = this::parseSuccess;
            }
            default -> {
                mapper = this::parseError;
            }
        }
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofInputStream(), mapper);
    }

    private HetznerResult<T, U> parseSuccess(InputStream input) {
        return new HetznerResult<>(parseJson(input, clazz), null);
    }

    private HetznerResult<T, U> parseError(InputStream input) {
        return new HetznerResult<>(null, parseJson(input, errorClass));
    }


    private <R> R parseJson(InputStream input, Class<R> clazz) {
        try {
            return HetznerCloud.getObjectMapper().readValue(input, clazz);
        } catch (IOException e) {
            // this is actually just bad, but handles 204 properly. I do **not** like this though, need to revisit
            if (statusCode == 204) {
                return null;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

}
