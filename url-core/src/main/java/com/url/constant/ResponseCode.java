package com.url.constant;

public enum ResponseCode {
    SUCCESS("000", "Success"),
    INVALID_PASSWORD("100", "Invalid Password"),
    DUPLICATE_USER("300", "Duplicate user"),
    BAD_REQUEST("400", "Bad Request, please fill required fields");

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
