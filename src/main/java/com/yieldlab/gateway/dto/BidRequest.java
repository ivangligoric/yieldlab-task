package com.yieldlab.gateway.dto;

import java.util.Map;

public class BidRequest {

    private Long id;

    private Map<String, String> attributes;

    public Long getId() {
        return id;
    }

    public BidRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public BidRequest setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}
