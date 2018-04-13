package com.winterframework.modules.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.winterframework.modules.utils.EncodeUtils;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 */
public class DigestUtils {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";

	//-- String Hash function --//
	/**
	 * 对输入字符串进行sha1散列, 返回Hex编码的结果.
	 */
	public static String sha1ToHex(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.hexEncode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的结果.
	 */
	public static String sha1ToBase64(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.base64Encode(digestResult);
	}

	/**
	 * 对输入字符串进行sha1散列, 返回Base64编码的URL安全的结果.
	 */
	public static String sha1ToBase64UrlSafe(String input) {
		byte[] digestResult = digest(input, SHA1);
		return EncodeUtils.base64UrlSafeEncode(digestResult);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(String input, String algorithm) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			return messageDigest.digest(input.getBytes());
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}

	//-- File Hash function --//
	/**
	 * 对文件进行md5散列,返回Hex编码结果.
	 */
	public static String md5ToHex(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * 对文件进行sha1散列,返回Hex编码结果.
	 */
	public static String sha1ToHex(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	/**
	 * 对文件进行散列, 支持md5与sha1算法.
	 */
	private static String digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return EncodeUtils.hexEncode(messageDigest.digest());

		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("Security exception", e);
		}
	}
	
	public static String getMD5ofStr(String origString) {
		
		String origMD5 = null;
		
		try {
			
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			byte[] result = md5.digest(origString.getBytes());

			origMD5 = byteArray2HexStr(result);
			
		}catch (Exception e) {

			e.printStackTrace();

		}
		
		return origMD5;
		
	}
	
	private static String byteArray2HexStr(byte[] bs) {

		StringBuffer sb = new StringBuffer();

		for (byte b : bs) {

		sb.append(byte2HexStr(b));

		}

		return sb.toString();

		}
	
	private static String byte2HexStr(byte b) {

		String hexStr = null;

		int n = b;

		if (n < 0) {

		//若需要自定义加密,请修改这个移位算法即可

		n = b & 0x7F + 128;

		}

		hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);

		return hexStr.toUpperCase();

		}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String b = "ORDERNO=12345678&ORDERTITLE=TESTORDER&DESC=&FILTER=&REQUESTID=1305787328527&REQSOURCE=EXPOEN&RETURN_URL=HTTP://WWW.HUBS1.NET&NOTIFY_URL=HTTP://WWW.HUBS1.NET&TOTALFEE=30.0&PAYTYPE=SALE&SYMBOL=CNY&LANGUAGE=ZH_CN&VERSION=1.0&STYLE=EXPOEN&KEY=12345678";
		System.out.println(getMD5ofStr(b));
	}
	
}
