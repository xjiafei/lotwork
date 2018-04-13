package com.winterframework.firefrog.game.service.order.utlis;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import com.winterframework.firefrog.common.httpjsonclient.impl.HttpJsonClientExt;

public class Encrypt {
	private byte[] keyBytes = "5185e8b8fd8a71fc80545e144f91faf2".getBytes();
	private byte[] iv = "51727d6a52ede7267e7def085d015633".getBytes();

	public static Encrypt getInstance(String key, String iv) {
		Encrypt pt = new Encrypt();
		pt.keyBytes = key.getBytes();
		pt.iv = iv.getBytes();
		return pt;
	}

	public String encryptRijndael(String plan) throws Exception {
		BlockCipher engine = new RijndaelEngine(256);
		BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));

		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keyBytes), iv);
		cipher.init(true, ivAndKey);

		byte[] input = plan.getBytes("UTF-8");
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

		int cipherLength = cipher.processBytes(input, 0, input.length, cipherText, 0);
		cipher.doFinal(cipherText, cipherLength);

		String result = new String(Base64.encodeBase64(cipherText));
		return result;
		
	}

	public String decryptRijndael(String plan) throws Exception {
		plan = plan.trim();
		BlockCipher engine = new RijndaelEngine(256);
		BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
		CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(keyBytes), iv);
		cipher.init(false, ivAndKey);
		byte[] output = Base64.decodeBase64(plan);
		byte[] cipherText = new byte[cipher.getOutputSize(output.length)];
		int cipherLength = cipher.processBytes(output, 0, output.length, cipherText, 0);
		int outputLength = cipher.doFinal(cipherText, cipherLength);
		outputLength += cipherLength;

		byte[] resultBytes = cipherText;
		if (outputLength != output.length) {
			resultBytes = new byte[outputLength];
			System.arraycopy(cipherText, 0, resultBytes, 0, outputLength);
		}

		String result = new String(resultBytes, "UTF-8");
		return result;
	}

	public static void main(String[] args) throws Exception {
		Encrypt cy = new Encrypt();
		
		String plain = "platform=4.0&project_key=DSLC03X45&project_time=2014-07-18 11:52:27";

		String abc = cy.encryptRijndael(plain);
		System.out.println(abc);
		//abc="Ijr6IWGoapygpdpWXXDRIV7vnT/Xtp83usAUJQz8tWmYdtroSm2JqoBHQYCeXUiUvYxtrsVh5bh09/0iffml0A==";
		System.out.println(cy.decryptRijndael(abc));

	}
}