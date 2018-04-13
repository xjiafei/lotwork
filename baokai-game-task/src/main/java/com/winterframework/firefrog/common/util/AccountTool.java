package com.winterframework.firefrog.common.util;

import org.apache.commons.lang3.StringUtils;

public class AccountTool {
	public static String getRealAccount(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		} else if (str.indexOf("/")==-1) {
			return str;

		}else if (!StringUtils.endsWith(str, "/")) {
			return "";

		} else {
			String[] vs = str.split("/");
			return vs[vs.length - 1];
		}

	}

	public static String getTopAccount(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		} else if (!StringUtils.endsWith(str, "/")) {
			return "";

		} else {
			String[] vs = str.split("/");
			return vs[0];
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getRealAccount("/a/b/ca/"));
		System.out.println(getTopAccount("/a/b/ca/"));

	}

}
