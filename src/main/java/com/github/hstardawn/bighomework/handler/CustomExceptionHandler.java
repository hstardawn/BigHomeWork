package com.github.hstardawn.bighomework.handler;

import com.github.hstardawn.bighomework.exception.ApiException;
import com.github.hstardawn.bighomework.result.AjaxResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(50)
public class CustomExceptionHandler {
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public AjaxResult<Object> handleValidateException(ApiException e) {
        return AjaxResult.fail(e.getErrorCode(), e.getMessage());
    }
}