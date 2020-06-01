package com.zy.community.exception;

public class CustomizeException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomizeException(ICustomizeErrorCode code){
        this.code = code.getCode();
        this.message=code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){return code;}
}
