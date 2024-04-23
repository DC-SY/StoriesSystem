package cn.dcsy.stsy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author DC_DC
 * Date: 2024/4/22/15:11
 */

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* cn.dcsy.stsy.controllers.*.*(..))")
    public void controllerAspect(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();

        Object targetObject = joinPoint.getTarget();
        Class<?> targetClass = targetObject.getClass();

        log.info("[CONTROLLER] 获取 {} 类的 {} 方法", targetClass.getName(), methodName);
    }

//    @Before("execution(* cn.dcsy.stsy.service.impl.*.*(..))")
//    public void serviceAspect(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String methodName = signature.getName();
//
//        Object targetObject = joinPoint.getTarget();
//        Class<?> targetClass = targetObject.getClass();
//
//        log.info("[SERVICE] 获取 {} 类的 {} 方法", targetClass.getName(), methodName);
//    }
}
