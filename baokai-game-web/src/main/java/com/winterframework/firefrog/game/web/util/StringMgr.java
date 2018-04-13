package com.winterframework.firefrog.game.web.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StringMgr {
	final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	public static String Aes128Encode(String data, String key, String iv) throws Exception 
	{
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		int blockSize = cipher.getBlockSize();
		byte[] dataBytes = data.getBytes();
		int plaintextLength = dataBytes.length;
		if (plaintextLength % blockSize != 0) {
			plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
		}
		byte[] plaintext = new byte[plaintextLength];

		System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
		byte[] encrypted = cipher.doFinal(plaintext);
		
		return BytesToHex(encrypted);
	}
	
	public static String Aes128Decode(String data, String key, String iv) throws Exception
	{
		byte[] encrypted = HexToBytes(data);

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted).replaceAll("\u0000.*", "");
	}
	
	public static String BytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    int iCount = 0;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
//	        if(v == 0)
//	        {
//	        	break;
//	        }
	       // Log.i("StringMgr", "[" + j + "]" + v);
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	        iCount++;
	    }
	    return new String(hexChars, 0, iCount * 2);
	}
	
	public static byte[] HexToBytes(String str)
	{
		byte[] bytes = new byte[str.length() / 2];
		for(int i = 0;i < str.length() / 2;i++)
		{
	        //Log.i("StringMgr", "str[" + i + "]" + str.substring(i * 2, i * 2 + 2));
			bytes[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
	        //Log.i("StringMgr", "bytes[" + i + "]" + str.substring(i * 2, i * 2 + 2));
		}
		return bytes;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Aes128Encode("1172983", "4119639092e62c55ea8be348e4d9260d", "0000000000000000"));
//		System.err.println(51000/10000.00);
	}
}
