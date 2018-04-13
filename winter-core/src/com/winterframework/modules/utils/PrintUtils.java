package com.winterframework.modules.utils;

public class PrintUtils {
	public static String getPrintString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

}
