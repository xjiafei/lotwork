package com.winterframework.firefrog.fund.util.hbpay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class XYRSAUtil {
	public static List loadKey(String privateKeyPath, String publicKeyPath) throws Exception {
		String content = readFileToString(privateKeyPath);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(SignUtil.hexStrToBytes(content));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyf.generatePrivate(priPKCS8);

		content = readFileToString(publicKeyPath);
		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(SignUtil.hexStrToBytes(content));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
		List keys = new ArrayList();
		keys.add(privateKey);
		keys.add(publicKey);
		return keys;
	}

	public static PrivateKey loadKey(String privateKeyPath) throws Exception {
		String content = readFileToString(privateKeyPath);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(SignUtil.hexStrToBytes(content));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
		return privateKey;
	}

	public static PrivateKey loadKeyBase64(String privateKeyPath) throws Exception {

		String content = readFileToString(privateKeyPath);
		byte[] temp = Base64.decode(content);
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(temp);
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
		return privateKey;
	}
	
	public static PrivateKey loadPrivateKey(byte[] privateKeyData) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKeyData);
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyf.generatePrivate(priPKCS8);
		return privateKey;
	}

	public static PublicKey loadPublicKey(byte[] publicKeyData) throws Exception {
		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(publicKeyData);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
		return publicKey;
	}
	
	public static PrivateKey loadPrivateKey(String privateKeyData) throws Exception {
		return loadPrivateKey(SignUtil.hexStrToBytes(privateKeyData));
	}
	public static PublicKey loadPublicKey(String publicKeyData) throws Exception {
		return loadPublicKey(SignUtil.hexStrToBytes(publicKeyData));
	}
	public static PublicKey loadPublicKeyBase64(String publicKeyData) throws Exception {
		return loadPublicKey(Base64.decode(publicKeyData));
	}
	
	public static PrivateKey loadPrivateKeyBase64(String privateKeyData) throws Exception {
		return loadPrivateKey(Base64.decode(privateKeyData));
	}
	public static List loadKey(byte[] privateKeyData, byte[] publicKeyData) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKeyData);
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyf.generatePrivate(priPKCS8);

		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(publicKeyData);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
		List keys = new ArrayList();
		keys.add(privateKey);
		keys.add(publicKey);
		return keys;
	}

	public static String readFileToString(String keyPath) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(keyPath));
		String content = bufferedReader.readLine();
		bufferedReader.close();
		return content;
	}

	public static String signatureData(PrivateKey privateKey, byte[] toSign) throws Exception {
		Signature signet = Signature.getInstance("MD5withRSA");

		signet.initSign(privateKey);
		signet.update(toSign);
		byte[] signed = signet.sign();
		return SignUtil.bytesToHexStr(signed);
	}

	public static boolean verifySignature(PublicKey publicKey, String hexSigned, byte[] toSign) throws Exception {
		Signature signetCheck = Signature.getInstance("MD5withRSA");
		signetCheck.initVerify(publicKey);
		signetCheck.update(toSign);
		if (signetCheck.verify(SignUtil.hexStrToBytes(hexSigned))) {
			return true;
		}
		return false;
	}
	
	public static boolean verifySignatureLocal(PublicKey publicKey, String hexSigned, byte[] toSign) throws Exception {
		Signature signetCheck = Signature.getInstance("MD5withRSA");
		signetCheck.initVerify(publicKey);
		signetCheck.update(toSign);
		if (signetCheck.verify(Base64.decode((hexSigned)))) {
			return true;
		}
		return false;
	}
	public static boolean verifySignature(String publicKey, String hexSigned, byte[] toSign) throws Exception {
		PublicKey pub = XYRSAUtil.loadPublicKey(publicKey);
		Signature signetCheck = Signature.getInstance("MD5withRSA");
		signetCheck.initVerify(pub);
		signetCheck.update(toSign);
		if (signetCheck.verify(SignUtil.hexStrToBytes(hexSigned))) {
			return true;
		}
		return false;
	}

	public static String signatureData(String privateKey, byte[] toSign) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(SignUtil.hexStrToBytes(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

		Signature signet = Signature.getInstance("MD5withRSA");

		signet.initSign(myprikey);
		signet.update(toSign);
		byte[] signed = signet.sign();

		return SignUtil.bytesToHexStr(signed);
	}

	public static boolean verifySignature(String publicKey, byte[] signed, byte[] toSign) throws Exception {
		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(SignUtil.hexStrToBytes(publicKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);

		Signature signetcheck = Signature.getInstance("MD5withRSA");
		signetcheck.initVerify(pubKey);
		signetcheck.update(toSign);
		if (signetcheck.verify(signed)) {
			return true;
		}
		return false;
	}

	public static String encryptRSA(PublicKey pbk, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(1, pbk);

		byte[] encData = cipher.doFinal(data);

		return SignUtil.bytesToHexStr(encData);
	}

	public static byte[] encryptRSA(String publicKey, byte[] data) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(SignUtil.hexStrToBytes(publicKey));

		KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");
		PublicKey pbk = rsaKeyFac.generatePublic(keySpec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(1, pbk);

		byte[] encData = cipher.doFinal(data);

		return encData;
	}
	
	public static String encryptRSA1(String publicKeyData, byte[] data) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(SignUtil.hexStrToBytes(publicKeyData));

		KeyFactory rsaKeyFac = KeyFactory.getInstance("RSA");
		PublicKey pbk = rsaKeyFac.generatePublic(keySpec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(1, pbk);

		byte[] encData = cipher.doFinal(data);

		return SignUtil.bytesToHexStr(encData);
	}

	public static byte[] decryptRSA(PrivateKey pbk, String encryHex) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(2, pbk);

		byte[] btSrc = cipher.doFinal(SignUtil.hexStrToBytes(encryHex));

		return btSrc;
	}

	public static byte[] decryptRSA(String privateKey, byte[] encryData) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(SignUtil.hexStrToBytes(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey pbk = keyf.generatePrivate(priPKCS8);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(2, pbk);

		byte[] btSrc = cipher.doFinal(encryData);

		return btSrc;
	}
}