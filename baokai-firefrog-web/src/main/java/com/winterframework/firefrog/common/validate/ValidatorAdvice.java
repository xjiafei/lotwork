/**
 * Copyright (C) 2010 numenzq studio. All Rights Reserved.
 */
package com.winterframework.firefrog.common.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.modules.web.jsonresult.Request;

/**
 * @author david
 * @version
 */

@Component("validatorAdvice")
@Aspect
public class ValidatorAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorAdvice.class);

	@Resource(name = "requestHeadValidator")
	private RequestHeadValidator requestHeadValidator;
	@Resource(name = "defaultRequestValidator")
	private DefaultRequestValidator defaultRequestValidator;

	@Around("execution(* com.winterframework.firefrog.*.web..*Controller.*(..))")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) (pjp.getSignature());
		Method method = ms.getMethod();
		doValidate(pjp, method);
		return pjp.proceed();
	}

	/**
	 * 切面编程记录用户请求日志，注意排除后台执行的business方法，比如定时执行调用的方法
	 * 
	 * @param pjp
	 * @param method
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("rawtypes")
	private void doValidate(ProceedingJoinPoint pjp, Method method) throws IllegalArgumentException,
			IllegalAccessException, Exception {
		Annotation[][] annotations = method.getParameterAnnotations();

		if (annotations.length > 0) {
			for (int i = 0; i < annotations.length; i++) {
				for (int j = 0; j < annotations[i].length; j++) {
					if (annotations[i][j].annotationType().getName().equals(ValidRequestBody.class.getName())) {
						defaultRequestValidator.validate(((Request) pjp.getArgs()[i]).getBody().getParam());
					}
					if (annotations[i][j].annotationType().getName().equals(ValidRequestHeader.class.getName())) {
						logger.info("ValidRequestHeader valid userId");
						requestHeadValidator.validate(((Request) pjp.getArgs()[i]));
					}
				}
			}
		}

	}
}
