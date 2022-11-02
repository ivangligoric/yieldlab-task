package com.yieldlab.gateway.bidClient;

import com.yieldlab.gateway.dto.BidRequest;
import com.yieldlab.gateway.dto.BidResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class BidClient {

    private final RestTemplate restTemplate;

    public BidClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Retryable(value = {ResourceAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public CompletableFuture<BidResponse> call(String url, BidRequest bidRequest) {

        ResponseEntity<BidResponse> bidResponseResponseEntity = restTemplate.postForEntity(url, bidRequest, BidResponse.class);
        return CompletableFuture.completedFuture(bidResponseResponseEntity.getBody());
    }

}
