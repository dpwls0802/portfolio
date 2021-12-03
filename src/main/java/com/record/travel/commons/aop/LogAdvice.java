package com.record.travel.commons.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component//스프링 빈으로 인식하기 위함
@Aspect //AOP 기능 하는 클래스에 반드시 추가해야함
public class LogAdvice {
	private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

	/*
	 * @Before("execution(* com.doubles.ex03.service.MessageService*.*(..))") public
	 * void startLog(JoinPoint jp) { logger.
	 * info("============================ startLog() ====================================="
	 * ); logger.info(Arrays.toString(jp.getArgs())); }
	 */

	@Around("execution(* com.record.travel..*Controller.*(..))"
    +" or execution(* com.record.travel..service..*Impl.*(..))")//메서드 실행 전체를 앞, 뒤로 감싸서 특정 기능 실행할 수 있는 Advce

    public Object logPrint(ProceedingJoinPoint pjp) throws Throwable {
        // Start time
        long startTime = System.currentTimeMillis();
        // 핵심로직으로 이동
        Object result = pjp.proceed();
        // Class name
        String type = pjp.getSignature().getDeclaringTypeName();
        String name = "";
        
        // Controller name
        if (type.contains("Controller")) {
            name = "Controller : ";
            // Service name
        } else if (type.contains("Service")) {
            name = "ServiceImpl : ";
        } 
        
        // End time
        long endTime = System.currentTimeMillis();
        
        // Method name
        logger.info(name + type + "." + pjp.getSignature().getName() + "()");
        // Argument / Parameter
        logger.info("Arguments / Parameters : " + Arrays.toString(pjp.getArgs()));
        
        // Running time
        long runTime = endTime - startTime;
        logger.info("Method Running time : " + runTime);
        return result;
	}
}