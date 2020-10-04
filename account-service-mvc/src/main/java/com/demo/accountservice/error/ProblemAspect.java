package com.demo.accountservice.error;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Aspect
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProblemAspect {

	// @Pointcut("execution(*
	// com.demo.accountservice.repository.AccountRepository.findById(..))")
	// @Pointcut("execution(*
	// com.demo.accountservice.error.ExceptionHandling.handleDemoNotWorkingException(..))")
	@Pointcut("execution(* org.zalando.problem.spring.common.AdviceTrait.log(..))")
	public void methodAnnotatedWithScheduled() {
	}

	@Around("methodAnnotatedWithScheduled()")
	public Object connectionAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("TEST");
		return joinPoint.proceed();
	}

}
