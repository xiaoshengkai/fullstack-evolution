package com.learner.Day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogInterceptor implements HandlerInterceptor {
    // 用的是slf4j来做日志输出
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    // 拿到各个钩子然后去做处理，以下提供三个钩子试试
    //    preHandle：Controller 执行前调用，记录请求信息。
    //    postHandle：Controller 执行后、视图渲染前调用，记录响应状态。
    //    afterCompletion：请求完全结束后调用，记录异常信息。

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info(
                "请求 URL - preHandle {} {} {}",
                request.getMethod(),
                request.getRequestURI(),
                Arrays.stream(request.getCookies()).map(Cookie::getValue).collect(Collectors.joining(";")));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        logger.info("响应状态 - postHandle {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        if (ex != null) {
            logger.error("请求处理异常: {}", ex.getMessage());
        }
    }
}
