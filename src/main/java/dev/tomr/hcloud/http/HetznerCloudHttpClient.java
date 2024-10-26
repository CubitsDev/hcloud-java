package dev.tomr.hcloud.http;

import dev.tomr.hcloud.HetznerCloud;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

import static java.lang.String.format;

public class HetznerCloudHttpClient {

    private static HetznerCloudHttpClient instance;

    private final HttpClient httpClient;

    public HetznerCloudHttpClient() {
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        instance = this;
    }

    public static HetznerCloudHttpClient getInstance() {
        if (instance == null) {
            instance = new HetznerCloudHttpClient();
        }
        return instance;
    }

    public <T extends HetznerJsonObject> T sendHttpRequest(Class<T> clazz, String endpoint, RequestVerb requestVerb, String apiKey) throws IOException, InterruptedException {
        return sendHttpRequest(clazz, endpoint, requestVerb, apiKey, null);
    }


    public <T extends HetznerJsonObject> T sendHttpRequest(Class<T> clazz, String endpoint, RequestVerb requestVerb, String apiKey, String body) throws IOException, InterruptedException {
        HttpRequest request = createHttpRequest(endpoint, requestVerb, apiKey, body);
        return (T) httpClient.send(request, new JacksonBodyHandler<>(clazz)).body();
    }

    private HttpRequest createHttpRequest(String uri, RequestVerb requestVerb, String apiKey, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", format("Bearer %s", apiKey))
                .header("Content-Type", "application/json");
        builder = switch (requestVerb) {
            case GET -> builder.GET();
            case POST -> builder.POST(HttpRequest.BodyPublishers.ofString(body));
            case PUT -> builder.PUT(HttpRequest.BodyPublishers.ofString(body));
        };
        return builder.build();
    }

}
