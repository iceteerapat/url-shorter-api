package com.url.schemas;

import java.io.Serializable;

public class GenericResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    public GenericResponse() {
    }

    private String responseCode;
    private String responseDesc;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
