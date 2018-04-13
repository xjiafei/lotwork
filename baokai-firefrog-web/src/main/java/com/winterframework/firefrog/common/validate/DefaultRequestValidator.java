package com.winterframework.firefrog.common.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.common.exception.ValidExcetion;

@Component("defaultRequestValidator")
public class DefaultRequestValidator implements IValidator {

	private static final Logger logger = Logger.getLogger("DefaultRequestValidator");

	protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 对Request参数进行验证，如果验证失败，抛出ValidExcetion
	 * 
	 * @param t
	 *            泛型，Request参数中的DTO
	 */
	@Override
	public <T> void validate(T t) {
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
		if (constraintViolations.size() > 0) {
			StringBuilder sb = new StringBuilder(1024);
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				sb.append(constraintViolation.getMessage()+" args:+"+constraintViolation.getPropertyPath());
				sb.append(";");
			}
			String errorMsg = sb.toString();
			logger.error("Request error:" + errorMsg);
			throw new ValidExcetion(errorMsg);
		}
	}
}
