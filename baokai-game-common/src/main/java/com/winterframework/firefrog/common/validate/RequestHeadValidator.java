package com.winterframework.firefrog.common.validate;

import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.common.exception.ValidExcetion;
import com.winterframework.modules.web.jsonresult.Request;

@Component("requestHeadValidator")
public class RequestHeadValidator implements IValidator {

	private static final Logger logger = LoggerFactory.getLogger(RequestHeadValidator.class);

	protected static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 对Request参数进行验证，如果验证失败，抛出ValidExcetion
	 * 
	 * @param t
	 *            泛型，Request参数中的DTO
	 */
	@Override
	public <T> void validate(T t) {
		@SuppressWarnings("rawtypes")
		Request rh = (Request) t;
		Long userId = rh.getHead().getUserId();
		if (userId <= 0) {
			logger.error("RequestHeader userId value error");
			throw new ValidExcetion("RequestHeader userId value error");
		}
	}

}
