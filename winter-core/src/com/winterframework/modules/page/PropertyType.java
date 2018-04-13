package com.winterframework.modules.page;

import java.util.Date;

public enum PropertyType {
	S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

	private Class<?> clazz;

	PropertyType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public static PropertyType valueOf(Class<?> clazz) {
		if (clazz.equals(String.class)) {
			return PropertyType.S;
		} else if (clazz.equals(Integer.class)) {
			return PropertyType.I;
		} else if (clazz.equals(Long.class)) {
			return PropertyType.L;
		} else if (clazz.equals(Double.class)) {
			return PropertyType.N;
		} else if (clazz.equals(Date.class)) {
			return PropertyType.D;
		} else if (clazz.equals(Boolean.class)) {
			return PropertyType.B;
		} else
			throw new RuntimeException("PropertyTye of class " + clazz + " is not supportted");
	}

	public Class<?> getValue() {
		return clazz;
	}
}