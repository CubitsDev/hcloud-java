package dev.tomr.hcloud.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.tomr.hcloud.HetznerCloud;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class JacksonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    private final Class<T> clazz;

    public JacksonBodyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return jsonPayload(clazz);
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

}
