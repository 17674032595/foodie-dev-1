package com.wuyiccc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2019/12/27 10:29
 * 岂曰无衣，与子同袍~
 */
@Aspect
@Component
public class ServiceLogAspect {

    /**
     * 1.前置通知
     * 2.后置通知
     * 3.环绕通知
     * 4.异常通知
     * 5.最终通知
     * ps：基于注解的aop通知，最终通知与后置通知的顺序发生颠倒
     */


    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);


    @Around("execution(* com.wuyiccc.service.impl..*.*(..))")//execution 切面表达式  第一个.. 代表impl包及其所有子包， * 代表所有类 *代表所有方法
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {//切入点

        log.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName()); //{} 是占位符  service类.方法  joinPoint.getTarget().getClass()获取对应的类名
        //joinPoint.getSignature().getName()获取执行的方法名称

        long begin = System.currentTimeMillis();//获得开始时间

        Object result = joinPoint.proceed();//执行目标service

        long end = System.currentTimeMillis();

        long takeTime = end - begin;
        if (takeTime > 3000) {
            log.error("=====  执行结束，耗时{}毫秒=====", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====执行结束，耗时:{}毫秒=====", takeTime);
        } else {
            log.info("==== 执行结束，耗时:{}毫秒====", takeTime);
        }

        return result;


    }
}
