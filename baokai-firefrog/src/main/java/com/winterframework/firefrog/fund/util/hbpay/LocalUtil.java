package com.winterframework.firefrog.fund.util.hbpay;

import java.security.PrivateKey;
import java.security.PublicKey;

public class LocalUtil {

	public static PublicKey loadPublicKey(String publicKeyData) {

		try {
			return XYRSAUtil.loadPublicKeyBase64(publicKeyData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public static PrivateKey loadPrivateKey(String privateKeyData) {

		try {
			return XYRSAUtil.loadPrivateKeyBase64(privateKeyData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PrivateKey loadPrivatePath(String privatePath) {

		try {
			return XYRSAUtil.loadKeyBase64(privatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	public static boolean verifySignature(byte[] publickey, String hexSigned, byte[] toSign) {
		try {
			PublicKey key = XYRSAUtil.loadPublicKey(publickey);
			if (XYRSAUtil.verifySignatureLocal(key, hexSigned, toSign))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	
	public static byte[] sign(byte[] privateKeyData, String plainText) {
		return Signaturer.sign(privateKeyData, plainText);

	}
	
	public static void main(String[] s){
		String signInfo="client_ip=116.25.124.158&interface_version=V3.3&merchant_code=1111110166&notify_url=http://ping.applinzi.com/Notify_Url.jsp&order_amount=0.01&order_no=20170818112523&order_time=2017-08-18 11:25:23&product_name=iPhone&service_type=weixin_scan";
		String merchantPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALf/+xHa1fDTCsLYPJLHy80aWq3djuV1T34sEsjp7UpLmV9zmOVMYXsoFNKQIcEzei4QdaqnVknzmIl7n1oXmAgHaSUF3qHjCttscDZcTWyrbXKSNr8arHv8hGJrfNB/Ea/+oSTIY7H5cAtWg6VmoPCHvqjafW8/UP60PdqYewrtAgMBAAECgYEAofXhsyK0RKoPg9jA4NabLuuuu/IU8ScklMQIuO8oHsiStXFUOSnVeImcYofaHmzIdDmqyU9IZgnUz9eQOcYg3BotUdUPcGgoqAqDVtmftqjmldP6F6urFpXBazqBrrfJVIgLyNw4PGK6/EmdQxBEtqqgXppRv/ZVZzZPkwObEuECQQDenAam9eAuJYveHtAthkusutsVG5E3gJiXhRhoAqiSQC9mXLTgaWV7zJyA5zYPMvh6IviX/7H+Bqp14lT9wctFAkEA05ljSYShWTCFThtJxJ2d8zq6xCjBgETAdhiH85O/VrdKpwITV/6psByUKp42IdqMJwOaBgnnct8iDK/TAJLniQJABdo+RodyVGRCUB2pRXkhZjInbl+iKr5jxKAIKzveqLGtTViknL3IoD+Z4b2yayXg6H0g4gYj7NTKCH1h1KYSrQJBALbgbcg/YbeU0NF1kibk1ns9+ebJFpvGT9SBVRZ2TjsjBNkcWR2HEp8LxB6lSEGwActCOJ8Zdjh4kpQGbcWkMYkCQAXBTFiyyImO+sfCccVuDSsWS+9jrc5KadHGIvhfoRjIj2VuUKzJ+mXbmXuXnOYmsAefjnMCI6gGtaqkzl527tw=";
		System.out.println(new String(LocalUtil.sign(Base64.decode(merchantPrivateKey.getBytes()), signInfo)));	// 签名   signInfo签名参数排序，  merchant_private_key商户私钥
	}
}
