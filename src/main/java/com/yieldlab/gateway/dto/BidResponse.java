package com.yieldlab.gateway.dto;

public class BidResponse {

    private Long id;

    private Long bid;

    private String content;

    public Long getId() {
        return id;
    }

    public BidResponse() {
        this.bid = 0L;
    }

    public BidResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBid() {
        return bid;
    }

    public BidResponse setBid(Long bid) {
        this.bid = bid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BidResponse setContent(String content) {
        this.content = content;
        return this;
    }
}
