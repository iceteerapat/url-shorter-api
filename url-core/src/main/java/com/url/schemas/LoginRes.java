package com.url.schemas;

import java.io.Serializable;

public class LoginRes extends GenericResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tokenJwts;

    public LoginRes() {
    }

    public String getTokenJwts() {
        return tokenJwts;
    }

    public void setTokenJwts(String tokenJwts) {
        this.tokenJwts = tokenJwts;
    }
}
