package com.yieldlab.gateway.dto;

public class AuctionResponse {

    private Long id;

    private Long bid;

    private String content;

    public Long getId() {
        return id;
    }

    public AuctionResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBid() {
        return bid;
    }

    public AuctionResponse setBid(Long bid) {
        this.bid = bid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AuctionResponse setContent(String content) {
        this.content = content;
        return this;
    }
}
