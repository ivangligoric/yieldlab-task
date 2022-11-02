package com.yieldlab.gateway.controller;

import com.yieldlab.gateway.service.BidService;
import com.yieldlab.gateway.dto.AuctionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuctionController {

    private final BidService bidService;

    public AuctionController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> placeBids(@PathVariable("id") Long id, @RequestParam Map<String,String> bids) {
        AuctionResponse highestBid = bidService.getHighestBid(id, bids);
        return ResponseEntity.ok(highestBid.getContent());

    }
}
