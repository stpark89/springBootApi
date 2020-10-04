package com.ktwiki.api.config;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log
@Aspect
@Component
public class AopConfig {

    /**
     * Rest Controller
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethod() {}


    /**
     * AOP - > RestController 호출시 공통 로직 처리
     *
     * @param thisJoinPoint
     * @throws Throwable
     */
    @Before(value="restControllerMethod()")
    public Object accessDataController(JoinPoint thisJoinPoint)  throws Throwable {
        log.info("AopConfig.accessDataController !!!!!! -- 로그 저장시 사용");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("test", "안녕");

        return result;
    }


    /**
     제외할 부분
     @Pointcut("execution(* 패키지명..*(..))"
     + "|| execution(* 패키지명..*(..))"
     + "|| execution(* 패키지명.controller.UserViewController..*(..))"
     )
     public void exceptMethod() {}

     @Before("execution(* com.example.service.*.*Aop(..))")
     public void onBeforeHandler(JoinPoint joinPoint) {
     logger.info("=============== onBeforeThing");
     }

     @After("execution(* com.example.service.*.*Aop(..))")
     public void onAfterHandler(JoinPoint joinPoint) {
     logger.info("=============== onAfterHandler");
     }

     @AfterReturning(pointcut = "execution(* com.example.service.*.*Aop(..))",
     returning = "str")
     public void onAfterReturningHandler(JoinPoint joinPoint, Object str) {
     logger.info("@AfterReturning : " + str);
     logger.info("=============== onAfterReturningHandler");
     }

     @Pointcut("execution(* com.example.service.*.*Aop(..))")
     public void onPointcut(JoinPoint joinPoint) {
     logger.info("=============== onPointcut");
     }
     **/
}
