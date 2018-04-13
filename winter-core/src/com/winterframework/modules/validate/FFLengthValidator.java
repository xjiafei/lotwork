package com.winterframework.modules.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class FFLengthValidator implements ConstraintValidator<FFLength, String> {
	private int max;
	private int min = 0;

	public void initialize(FFLength parameters) {
		this.max = parameters.max();
		this.min = parameters.min();
	}



	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isEmpty(str)) {
			return min == 0 ? true : false;
		} else {
			int len = getUnicodeLength(str);
			return len <= max && len >= min;
		}

	}

	private static int getUnicodeLength(String strs) {
		try {
			strs = strs.replaceAll("[^\\x00-\\xff]", "**");
			return strs.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	public static void main(String[] args){
		print("张三");
		print("abc");
		print("。");
	}
	private static void print(String a){
		System.out.println(a+":"+getUnicodeLength(a));
	}
}