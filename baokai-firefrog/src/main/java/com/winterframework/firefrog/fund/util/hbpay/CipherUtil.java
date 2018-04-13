package com.winterframework.firefrog.fund.util.hbpay;

import java.security.PublicKey;

public class CipherUtil {

	public static boolean verifySignature(PublicKey publicKey, String hexSigned, byte[] toSign) {
		boolean result;
		try {
			result = XYRSAUtil.verifySignature(publicKey, hexSigned, toSign);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static String signature(byte[] data, String privateKeyData) throws Exception {
		return XYRSAUtil.signatureData(privateKeyData, data);
	}

	public static String encrypt(byte[] plaintData,String publicKeyData) throws Exception {
		byte[] aesKey = AESUtil.generateKey();
		byte[] encrypt = AESUtil.encrypt(aesKey, plaintData);
		String rsaEncrypt = XYRSAUtil.encryptRSA1(publicKeyData, aesKey);
		return rsaEncrypt + "|" + SignUtil.bytesToHexStr(encrypt);
	}
}
