package com.zy.community.dto;

import com.zy.community.exception.CustomizeErrorCode;
import com.zy.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO resultOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO resultOf(CustomizeErrorCode errorCode) {
        return resultOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO resultOf(CustomizeException e){
        return resultOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO successCode(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("评论成功");
        return resultDTO;
    }
}
