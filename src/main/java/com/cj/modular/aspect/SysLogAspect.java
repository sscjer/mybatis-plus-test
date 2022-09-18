package com.cj.modular.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 系统日志切面
 * </p>
 *
 * @author Caoj
 * @version 2022-08-16 18:47
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    private Map<String, Object> cache = new ConcurrentHashMap<>();

    @Pointcut("within(com.cj.modular.controller.*)")
    public void doLog(){};


    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        log.info("开始执行doAround方法,时间：{}", simpleDateFormat.format(System.currentTimeMillis()));
        try{
            Object proceed = joinPoint.proceed();
            System.out.println(proceed);
            return proceed;
        } catch (Throwable e) {
            log.error("doAround方法调用失败！时间：{}", simpleDateFormat.format(System.currentTimeMillis()));
            e.printStackTrace();
        }
        return null;
    }


}
