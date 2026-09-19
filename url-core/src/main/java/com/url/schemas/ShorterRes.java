package com.url.schemas;

import java.io.Serializable;

public class ShorterRes extends GenericResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String shortUrl;

    public ShorterRes() {
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
