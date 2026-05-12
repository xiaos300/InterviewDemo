package com.example.interviewdemo.exception;

import com.example.interviewdemo.common.ResultCode;

/**
 * 业务异常类，主动throw
 */
public class BusinessException extends RuntimeException {
    private final Integer code;
    public BusinessException(String message){
        super(message);
        this.code = ResultCode.BAD_REQUEST;
    }
    public BusinessException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
