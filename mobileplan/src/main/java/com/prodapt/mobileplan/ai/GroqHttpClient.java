package com.prodapt.mobileplan.ai;

import com.prodapt.mobileplan.config.InsecureSSLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GroqHttpClient {

    @Value("${groq.api.key}")
    private String apiKey;

    private final HttpClient client;

    public GroqHttpClient() {

        SSLContext sslContext = InsecureSSLContext.create();

        this.client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public String askGroq(String payload) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
