package ru.otus.project.employee.service.http;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.project.employee.exception.HttpSendException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AccountProviderHttpClient {

    @Value("${service.account-provider.base-url}")
    private String accountProviderBaseUrl;

    @Value("${service.account-provider.timeout-ms}")
    private long timeoutMs;
    
    
    public void deleteRequest(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        String url = accountProviderBaseUrl + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .timeout(Duration.ofMillis(timeoutMs))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpSendException("HTTP status code: " + response.statusCode());
            } else {
                System.out.println("Delete request successful: " + response.body());
            }
        } catch (Exception e) {
            throw new HttpSendException("Can't send request to URL: " + url + ": " + e.getMessage());
        }

    }
    
}
