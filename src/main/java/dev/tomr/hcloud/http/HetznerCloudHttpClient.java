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

    /**
     * Creates a new {@code HetznerCloudHttpClient} to make REST calls
     */
    public HetznerCloudHttpClient() {
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        instance = this;
    }

    /**
     * Get the current HTTP client instance, will create a new one if it doesn't exist
     * @return The {@code HetznerCloudHttpClient} in use
     */
    public static HetznerCloudHttpClient getInstance() {
        if (instance == null) {
            instance = new HetznerCloudHttpClient();
        }
        return instance;
    }

    /**
     * Sends a HTTP Request with no http body
     * @param clazz Class to map the HTTP response to (Must extend {@code HetznerJsonObject})
     * @param endpoint Full path to hit, e.g. https://example.com/path
     * @param requestVerb {@code RequestVerb} to use in the HTTP request
     * @param apiKey API Key for Hetzner
     * @return The mapped object from the JSON response
     * @param <T> Class that extends {@code HetznerJsonObject}
     * @throws IOException Exception passed from HTTP client
     * @throws InterruptedException Exception passed from HTTP client
     */
    public <T extends HetznerJsonObject> T sendHttpRequest(Class<T> clazz, String endpoint, RequestVerb requestVerb, String apiKey) throws IOException, InterruptedException {
        return sendHttpRequest(clazz, endpoint, requestVerb, apiKey, null);
    }

    /**
     * Sends a HTTP Request with a body
     * @param clazz Class to map the HTTP response to (Must extend {@code HetznerJsonObject})
     * @param endpoint Full path to hit, e.g. https://example.com/path
     * @param requestVerb {@code RequestVerb} to use in the HTTP request
     * @param apiKey API Key for Hetzner
     * @param body The body in string format to attach to the request
     * @return The mapped object from the JSON response
     * @param <T> Class that extends {@code HetznerJsonObject}
     * @throws IOException Exception passed from HTTP client
     * @throws InterruptedException Exception passed from HTTP client
     */
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
