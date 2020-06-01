package com.zy.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FIND(1001,"你访问的问题未找到"),
    PARENT_NOT_FIND(1002,"你评论的问题未找到或已经移除"),
    LOGIN_ERROR(1003,"用户未登录"),
    SYSTEM_ERROR(1004,"客官出错了"),
    TYPE_NOT_FIND(1005,"评论类型错误"),
    COMMENT_NOT_FIND(1006,"回复的评论不存在");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
