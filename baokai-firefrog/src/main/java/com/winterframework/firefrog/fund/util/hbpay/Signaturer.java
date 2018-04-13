package com.winterframework.firefrog.fund.util.hbpay;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class Signaturer {
	public static byte[] sign(byte[] priKeyText, String plainText) {
		  try {
		   PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(priKeyText);
		   KeyFactory keyf = KeyFactory.getInstance("RSA");
		   PrivateKey prikey = keyf.generatePrivate(priPKCS8);
		    Signature signet = java.security.Signature.getInstance("MD5withRSA");
		   signet.initSign(prikey);
		   signet.update(plainText.getBytes());
		   byte[] signed = Base64.encodeToByte(signet.sign());
		   return signed;
		  } catch (java.lang.Exception e) {
		   System.out.println("签名失败");
		   e.printStackTrace();
		  }
		  return null;
		 }
}
