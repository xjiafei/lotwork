package com.winterframework.firefrog.fund.util.hbpay;


public class SignUtil {
	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4 & 0xF)]);
			s.append(bcdLookup[(bcd[i] & 0xF)]);
		}

		return s.toString();
	}

	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = ((byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16));
		}

		return bytes;
	}
}