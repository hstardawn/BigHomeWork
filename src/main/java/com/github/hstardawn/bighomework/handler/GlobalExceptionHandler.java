package com.github.hstardawn.bighomework.handler;

import com.github.hstardawn.bighomework.constant.ExceptionEnum;
import com.github.hstardawn.bighomework.result.AjaxResult;
import com.github.hstardawn.bighomework.util.HandlerUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(1000)
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult<Object> handleGlobalException(Exception e) {
        HandlerUtils.logException(e);
        return AjaxResult.fail(ExceptionEnum.SERVER_ERROR);
    }
}