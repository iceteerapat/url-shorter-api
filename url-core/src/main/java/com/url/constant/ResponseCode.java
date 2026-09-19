package com.url.constant;

public enum ResponseCode {
    SUCCESS("000", "Success"),
    INVALID_PASSWORD("100", "Invalid Password"),
    DUPLICATE_USER("200", "Duplicate user"),
    BAD_REQUEST("300", "Bad Request, please fill required fields"),
    URL_NOT_FOUND("400", "URL not found");

    private String code;
    private String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
