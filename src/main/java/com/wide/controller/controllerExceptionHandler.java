package com.wide.controller;

import com.wide.exception.BizException;
import com.wide.exception.BlogNotExistException;
import com.wide.exception.CommonEnum;
import com.wide.exception.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//@EnableWebMvc
@RestControllerAdvice
public class controllerExceptionHandler {
    {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    Logger logger;

    @Autowired
    private BasicErrorController basicErrorController;

    @ExceptionHandler(value = BizException.class)
    public String bizExceptionHandler(HttpServletRequest request, BizException e){
        logger.error("发生业务异常！原因是：{}", e.getErrorMsg());
        Map<String, Object> map = new HashMap<>();
        map.put("code", e.getErrorCode());
        map.put("message", e.getErrorMsg());
        request.setAttribute("javax.servlet.error.status_code", new Integer(e.getErrorCode()));
        request.setAttribute("ext", map);
        return "forward:/error";
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String exceptionHandler(HttpServletRequest request, NullPointerException e){
        logger.error("发生空指针异常！原因是:", e.getMessage());
        ResultBody rb = ResultBody.error(CommonEnum.BODY_NOT_MATCH);
        Map<String, Object> map = new HashMap<>();
        map.put("code", rb.getCode());
        map.put("message", rb.getMessage());
        request.setAttribute("javax.servlet.error.status_code", new Integer(rb.getCode()));
        request.setAttribute("ext", map);
        return "forward:/error";
    }

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("未知异常！原因是:", e.getMessage());
        ResultBody rb = ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
        Map<String, Object> map = new HashMap<>();
        map.put("code", rb.getCode());
        map.put("message", rb.getMessage());
        request.setAttribute("javax.servlet.error.status_code", new Integer(rb.getCode()));
        request.setAttribute("ext", map);
        return "forward:/error";
    }

}
