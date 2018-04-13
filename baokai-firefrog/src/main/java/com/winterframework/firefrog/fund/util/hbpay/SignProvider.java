package com.winterframework.firefrog.fund.util.hbpay;


import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class SignProvider {
	private SignProvider() {
	 }
	 public static boolean verify(byte[] pubKeyText, String plainText,
	   byte[] signText) {
		  try {
		   X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKeyText));
		   KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		   PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
		   byte[] signed = Base64.decode(signText);
		   Signature signatureChecker = Signature.getInstance("MD5withRSA");
		   signatureChecker.initVerify(pubKey);
		   signatureChecker.update(plainText.getBytes());
		   if (signatureChecker.verify(signed))
		    return true;
		   else
		    return false;
		  } catch (Throwable e) {
		   System.out.println("校验签名失败");
		   e.printStackTrace();
		   return false;
		  }
	 }
}
