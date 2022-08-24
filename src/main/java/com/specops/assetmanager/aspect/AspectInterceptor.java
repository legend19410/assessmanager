package com.specops.assetmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class AspectInterceptor {
	
	@Pointcut("execution(* com.specops.assetmanager.officers.OfficerController.*(..))")
//	@Pointcut("within(com.specops.assetmanager.officers.*")
	public void loggingPointCut() {
		
	}
	
	@Before("loggingPointCut()")
	public void before(JoinPoint joinPoint) {
		log.info("Before method invoked::"+ joinPoint.getSignature());
		log.info("getKind()"+joinPoint.getKind());
		log.info("toLongString()"+joinPoint.toLongString());
		log.info("toShortString()"+joinPoint.toShortString());
		log.info("ADVICE_EXECUTION()"+joinPoint.ADVICE_EXECUTION);
		log.info("CONSTRUCTOR_CALL()"+joinPoint.CONSTRUCTOR_CALL);
		log.info("getArgs()"+joinPoint.getArgs());
		log.info("getSourceLocation()"+joinPoint.getSourceLocation());
		log.info("METHOD_EXECUTION()"+joinPoint.METHOD_EXECUTION);
		log.info("METHOD_CALL()"+joinPoint.METHOD_CALL);
		log.info("getTarget()"+joinPoint.getTarget());
	}
	
	@After("loggingPointCut()")
	public void after(JoinPoint joinPoint) {
		log.info("After method invoked::"+ joinPoint.getSignature());
	}

	@AfterReturning(value = "execution(* com.specops.assetmanager.officers.OfficerController.*(..))", returning="page")
	public void afterReturning(JoinPoint joinPoint, Page<?> page) {
		log.info("AfterReturning method invoked::"+page);
		log.info("AfterReturning method invoked getNumber::"+page.getNumber());
	}
	
	@AfterThrowing(value = "execution(* com.specops.assetmanager.officers.OfficerController.*(..))", throwing="e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		log.info("AfterThrowing method invoked::"+e.getMessage());
	}
	
	@Around("loggingPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Before (around) method invoked:: "+ joinPoint.getSignature());
		
		Object object = joinPoint.proceed();
		
		if(object instanceof Page) {
			log.info("After (around) method invoked:: "+joinPoint.getSignature());
		}
		return object;
	}
}
