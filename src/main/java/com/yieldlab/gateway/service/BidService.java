package com.yieldlab.gateway.service;

import com.yieldlab.gateway.bidClient.BidClient;
import com.yieldlab.gateway.dto.AuctionResponse;
import com.yieldlab.gateway.dto.BidRequest;
import com.yieldlab.gateway.dto.BidResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BidService {

    private final List<String> hosts;

    private final BidClient bidClient;

    public BidService(@Value("#{'${bidders}'.split(',')}") List<String> hosts, BidClient bidClient) {
        this.hosts = hosts;
        this.bidClient = bidClient;
    }

    public AuctionResponse getHighestBid(Long id, Map<String, String> bids) {
        BidRequest bidRequest = new BidRequest();
        bidRequest.setId(id);
        bidRequest.setAttributes(bids);


        List<CompletableFuture<BidResponse>> completableFutures = hosts.stream()
                .map(url -> bidClient.call(url, bidRequest))
                .collect(Collectors.toUnmodifiableList());

        return completableFutures.stream()
                .map(CompletableFuture::join)
                .max(Comparator.comparing(BidResponse::getBid))
                .map(this::createAuctionResponse)
                .orElseThrow();
    }

    private AuctionResponse createAuctionResponse(BidResponse bidResponse) {
        AuctionResponse auctionResponse = new AuctionResponse();
        auctionResponse.setId(bidResponse.getId());
        auctionResponse.setBid(bidResponse.getBid());
        auctionResponse.setContent(bidResponse.getContent().replace("$price$", String.valueOf(bidResponse.getBid())));

        return auctionResponse;
    }
}
