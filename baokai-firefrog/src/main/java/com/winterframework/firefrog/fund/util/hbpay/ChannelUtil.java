package com.winterframework.firefrog.fund.util.hbpay;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ChannelUtil {

public static String decrypt(String privateKey,String request) throws Exception{
		
		int idx = request.indexOf("|");
		String rsaEncrypt = request.substring(0, idx);
		String encryptBody = request.substring(idx + 1);
		byte[] aesPwd = XYRSAUtil.decryptRSA(loadKey(privateKey), rsaEncrypt);
		byte[] body = AESUtil.decrypt(aesPwd, SignUtil.hexStrToBytes(encryptBody));
		return new String(body);
	}
	
	public static String decrypt(PrivateKey privatekey,String request) throws Exception{
		
		int idx = request.indexOf("|");
		String rsaEncrypt = request.substring(0, idx);
		String encryptBody = request.substring(idx + 1);
		byte[] aesPwd = XYRSAUtil.decryptRSA(privatekey, rsaEncrypt);
		byte[] body = AESUtil.decrypt(aesPwd, SignUtil.hexStrToBytes(encryptBody));
		return new String(body);
	}
	
	public static String decryptLocal(PrivateKey privatekey,String request) throws Exception{
		
		byte[] aesPwd = XYRSAUtil.decryptRSA(privatekey, request);
		return new String(aesPwd);
	}
	
	public static String encrypt(byte[] plaintData,String publicKeyData) throws Exception {
		byte[] aesKey = AESUtil.generateKey();
		byte[] encrypt = AESUtil.encrypt(aesKey, plaintData);
		String rsaEncrypt = XYRSAUtil.encryptRSA1(publicKeyData, aesKey);
		return rsaEncrypt + "|" + SignUtil.bytesToHexStr(encrypt);
	}
	
	public static String encrypt(byte[] plaintData,PublicKey publicKeyData) throws Exception {
		byte[] aesKey = AESUtil.generateKey();
		byte[] encrypt = AESUtil.encrypt(aesKey, plaintData);
		String rsaEncrypt = XYRSAUtil.encryptRSA(publicKeyData, aesKey);
		return rsaEncrypt + "|" + SignUtil.bytesToHexStr(encrypt);
	}
	
	public static String signature(byte[] data,String privateKeyData) throws Exception{
		return XYRSAUtil.signatureData(privateKeyData, data);
	}
	
	public static String signatureForFile(byte[] data,String privateKeyFile) throws Exception{
		PrivateKey privateKeyData = XYRSAUtil.loadKey(privateKeyFile);
		return XYRSAUtil.signatureData(privateKeyData, data);
	}
	public static String signatureForFileLocal(byte[] data,String privateKeyFile) throws Exception{
		PrivateKey privateKeyData = XYRSAUtil.loadKeyBase64(privateKeyFile);
		return XYRSAUtil.signatureData(privateKeyData, data);
	}
	
	private static PrivateKey loadKey(String privateKey) throws Exception{
		
		return XYRSAUtil.loadPrivateKey(privateKey);
	}
}
