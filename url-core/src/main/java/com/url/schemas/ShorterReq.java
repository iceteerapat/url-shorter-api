package com.url.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ShorterReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("original_url")
    private String longUrl;

    public ShorterReq() {
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
