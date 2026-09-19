package com.url.schemas;

import java.io.Serializable;

public class LinkList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long urlId;
    private String shortUrl;
    private String longUrl;

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
