package com.winterframework.modules.utils;

public class StringBUtils {

	public static String substringByUnicode(String s, int length) throws Exception {
		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始

		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为ucs2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在ucs2第二个字节时n加1
			} else {
				// 当ucs2编码的第一个字节不等于0时，该ucs2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1) {
			// 该ucs2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0) {
				i = i - 1;
			} else {// 该ucs2字符是字母或数字，则保留该字符
				i = i + 1;
			}
		}
		return new String(bytes, 0, i, "unicode");
	}
	public static void main(String[] args) throws Exception {
		String aaa="许多多益善";
		System.out.println(StringBUtils.substringByUnicode(aaa, 5));
	}

}
