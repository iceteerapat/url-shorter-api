package com.url.schemas;

import java.io.Serializable;
import java.util.List;

public class UrlsRes extends GenericResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<LinkList> urls;

    public UrlsRes() {
    }

    public List<LinkList> getUrls() {
        return urls;
    }
    public void setUrls(List<LinkList> urls) {
        this.urls = urls;
    }
}
