package dev.tomr.hcloud.component.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HttpClientComponentTest {

    @Test
    @DisplayName("HTTP Client can make a successful GET request and map to class")
    public void testHttpClientAndMapping() throws IOException, InterruptedException {
        // This test relies on an external server (jsonplaceholder.com)
        //todo refactor to use a local wiremock
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = client.sendHttpRequest(TestModel.class, "https://jsonplaceholder.typicode.com/posts/1", RequestVerb.GET, "");

        assertEquals(1, testModel.getId());
        assertEquals(1, testModel.getUserId());
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", testModel.getTitle());
        assertEquals("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto", testModel.getBody());
    }

    @Test
    @DisplayName("HTTP Client can make a successful POST request with Body and map to response class")
    void testHttpClientAndMappingWithBody() throws IOException, InterruptedException {
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = new TestModel();
        testModel.setUserId(2);
        testModel.setTitle("some title");
        testModel.setBody("some body");

        TestModel response = client.sendHttpRequest(TestModel.class, "https://jsonplaceholder.typicode.com/posts", RequestVerb.POST, "", new ObjectMapper().writeValueAsString(testModel));
        assertEquals(testModel.getBody(), response.getBody());
        assertEquals(testModel.getTitle(), response.getTitle());
        assertEquals(testModel.getUserId(), response.getUserId());
    }

    @Test
    @DisplayName("HTTP Client can make a successful PUT request with Body and map to response class")
    void testHttpClientAndMappingWithBodyPUT() throws IOException, InterruptedException {
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        TestModel testModel = new TestModel();
        testModel.setUserId(2);
        testModel.setTitle("some title");
        testModel.setBody("some body");

        TestModel response = client.sendHttpRequest(TestModel.class, "https://jsonplaceholder.typicode.com/posts/1", RequestVerb.PUT, "", new ObjectMapper().writeValueAsString(testModel));
        assertEquals(testModel.getBody(), response.getBody());
        assertEquals(testModel.getTitle(), response.getTitle());
        assertEquals(testModel.getUserId(), response.getUserId());
    }

    @Test
    @DisplayName("HTTP client throws an IOException when non JSON is returned")
    void testHttpClientThrowsRuntimeException() throws IOException, InterruptedException {
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();

        assertThrows(IOException.class, () -> client.sendHttpRequest(TestModel.class, "https://vpic.nhtsa.dot.gov/api/vehicles/getallmakes?format=xml", RequestVerb.GET, ""));

    }
}
