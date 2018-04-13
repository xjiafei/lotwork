package com.winterframework.firefrog.fund.util.hbpay;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {

	private static byte[] des3Encrypt(byte[] key, byte[] plainData) {
		if (key.length!=16 && key.length!=24) {
			return null;
		}
		byte[] key1 = new byte[24];
		if (key.length==16) {
			System.arraycopy(key, 0, key1, 0, 16);
			System.arraycopy(key, 0, key1, 16, 8);
		}
		if (key.length==24) {
			System.arraycopy(key, 0, key1, 0, 24);
		}
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key1, "DESede");
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] data = cipher.doFinal(plainData);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	
	public static byte[] des3Decrypt(byte[] key,byte[] plainData){
		if (key.length!=16 && key.length!=24) {
			return null;
		}
		byte[] key1 = new byte[24];
		if (key.length==16) {
			System.arraycopy(key, 0, key1, 0, 16);
			System.arraycopy(key, 0, key1, 16, 8);
		}
		if (key.length==24) {
			System.arraycopy(key, 0, key1, 0, 24);
		}
		if (plainData.length%8!=0) {
			return null;
		}
		try {
			
			SecretKeySpec keySpec = new SecretKeySpec(key1, "DESede");
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] data = cipher.doFinal(plainData);
			return data;
		} catch (Exception e) {
			return null;
		}
	  }
}
