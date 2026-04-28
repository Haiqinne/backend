package com.bookstore.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
@Service
public class SePayService {

    private final String ACCOUNT = "0855578332";
    private final String BANK = "MBBank";

    @Value("${sepay.api.key}")
    private String apiKey;

    public String generateQR(Long billId, Double amount) {
        String content = "DH" + billId;

        return "https://qr.sepay.vn/img?"
                + "acc=" + ACCOUNT
                + "&bank=" + BANK
                + "&amount=" + amount
                + "&des=" + content;
    }

    public boolean checkPayment(Long billId, Double amount) {
        try {
            String url = "https://my.sepay.vn/userapi/transactions/list"
                    + "?account_number=" + ACCOUNT
                    + "&limit=20";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String body = response.getBody();
            String keyword = "DH" + billId;

            return body != null && body.contains(keyword);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}