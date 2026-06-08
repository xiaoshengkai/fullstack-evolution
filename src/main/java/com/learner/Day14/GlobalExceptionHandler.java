package com.learner.Day14;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理（统一处理校验失败和业务异常）
 * 之前如果参数校验失败，Spring 会返回 400 错误，但格式不是我们的 Result 格式。我们用 @ControllerAdvice 拦截异常，统一包装。
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)   // 返回 400 状态码
    public Result<?> handleValidationException(MethodArgumentNotValidException ex) {
        // 获取所有校验失败信息，拼接
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return Result.error(400, message);
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception ex) {
        return Result.error(500, "服务器内部错误：" + ex.getMessage());
    }
}
