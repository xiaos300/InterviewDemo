package com.example.interviewdemo.exception;

import com.example.interviewdemo.common.Result;
import com.example.interviewdemo.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @RestControllerAdvice:“全局 Controller 增强类”，专门处理异常，默认返回JSON
 * 本项目中处理
 * BusinessException:自己throw的业务异常
 * Exception:系统异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e){
        log.warn("业务异常：code={},message={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e){
        log.error("系统异常:",e);
        return Result.error(ResultCode.ERROR,"系统异常");
    }

}
