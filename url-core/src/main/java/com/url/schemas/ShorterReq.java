package com.url.schemas;

import java.io.Serializable;

public class ShorterReq implements Serializable {
    private static final long serialVersionUID = 1L;

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
