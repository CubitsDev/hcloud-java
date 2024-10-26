package dev.tomr.hcloud.component.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.Notifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

public class HttpClientComponentTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    private String HOST = "http://localhost:" + wireMockExtension.getPort() + "/";

    @Test
    @DisplayName("HTTP Client can make a successful GET request and map to class")
    public void testHttpClientAndMapping() throws IOException, InterruptedException {
        wireMockExtension.stubFor(get("/test").willReturn(ok(objectMapper.writeValueAsString(new TestModel(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto")))));

        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = client.sendHttpRequest(TestModel.class, HOST + "test", RequestVerb.GET, "");

        assertEquals(1, testModel.getId());
        assertEquals(1, testModel.getUserId());
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", testModel.getTitle());
        assertEquals("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto", testModel.getBody());
    }

    @Test
    @DisplayName("HTTP Client can make a successful POST request with Body and map to response class")
    void testHttpClientAndMappingWithBody() throws IOException, InterruptedException {
        wireMockExtension.stubFor(post("/test").willReturn(ok(objectMapper.writeValueAsString(new TestModel(2, 1, "some title", "some body")))));
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = new TestModel();
        testModel.setUserId(2);
        testModel.setTitle("some title");
        testModel.setBody("some body");

        TestModel response = client.sendHttpRequest(TestModel.class, HOST + "test", RequestVerb.POST, "", new ObjectMapper().writeValueAsString(testModel));
        assertEquals(testModel.getBody(), response.getBody());
        assertEquals(testModel.getTitle(), response.getTitle());
        assertEquals(testModel.getUserId(), response.getUserId());
    }

    @Test
    @DisplayName("HTTP Client can make a successful PUT request with Body and map to response class")
    void testHttpClientAndMappingWithBodyPUT() throws IOException, InterruptedException {
        wireMockExtension.stubFor(put("/test").willReturn(ok(objectMapper.writeValueAsString(new TestModel(2, 1, "some title", "some body")))));
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = new TestModel();
        testModel.setUserId(2);
        testModel.setTitle("some title");
        testModel.setBody("some body");

        TestModel response = client.sendHttpRequest(TestModel.class, HOST + "test", RequestVerb.PUT, "", new ObjectMapper().writeValueAsString(testModel));
        assertEquals(testModel.getBody(), response.getBody());
        assertEquals(testModel.getTitle(), response.getTitle());
        assertEquals(testModel.getUserId(), response.getUserId());
    }

    @Test
    @DisplayName("HTTP client throws an IOException when non JSON is returned")
    void testHttpClientThrowsRuntimeException() throws IOException, InterruptedException {
        wireMockExtension.stubFor(get("/broken").willReturn(okXml("<xml></xml>")));
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        assertThrows(IOException.class, () -> client.sendHttpRequest(TestModel.class, HOST + "broken", RequestVerb.GET, ""));

    }
}
