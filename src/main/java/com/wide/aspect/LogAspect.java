package com.wide.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import sun.awt.SunHints;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    Logger logger;

    {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Pointcut("execution(* com.wide.controller.*.*(..))")
    public void log(){}

    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        ReqeustLog reqeustLog = new ReqeustLog(url, ip, classMethod, args);
        logger.info("Request : {}", reqeustLog);
    }

    @After(value = "log()")
    public void doAfter(){
//        logger.info("---------------doAfter----------------");
    }

    @AfterReturning(pointcut = "log()", returning = "result")
    public void doAfterReturn(Object result){
        logger.info("Result : {}", result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }

}
