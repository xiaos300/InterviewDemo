package com.example.interviewdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 返回代码
    private String message; // 消息
    private T data;  // 数据

    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.code = ResultCode.SUCCESS;
        r.message = "success";
        r.data = data;
        return r;
    }

    public static <T> Result<T> success(String message, T data){
        Result<T> r = new Result<>();
        r.code = ResultCode.SUCCESS;
        r.message = message;
        r.data = data;
        return r;
    }
    public static <T> Result<T> success(String message){
        Result<T> r = new Result<>();
        r.code = ResultCode.SUCCESS;
        r.message = message;
        return r;
    }
    public static <T> Result<T> success(){
        Result<T> r = new Result<>();
        r.code = ResultCode.SUCCESS;
        r.message = "success";
        return r;
    }
    public static <T> Result<T> error(Integer code, String message){
        Result<T> r = new Result<>();
        r.code = code;
        r.message = message;
        return r;
    }
}
