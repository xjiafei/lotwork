package com.winterframework.firefrog.fund.util.hbpay;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESUtil
{
  public static byte[] generateKey()
    throws Exception
  {
    KeyGenerator keygen = KeyGenerator.getInstance("AES");
    SecureRandom random = new SecureRandom();
    keygen.init(128, random);
    SecretKey key = keygen.generateKey();
    ByteArrayOutputStream bufferOutStream = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bufferOutStream);
    out.writeObject(key);
    out.close();
    return bufferOutStream.toByteArray();
  }

  private static byte[] de_encrypt(byte[] keys, byte[] input, int mode) throws Exception
  {
    InputStream inputStreamKey = new ByteArrayInputStream(keys);
    InputStream inputStream = new ByteArrayInputStream(input);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectInputStream keyIn = new ObjectInputStream(inputStreamKey);
    Key key = (Key)keyIn.readObject();
    keyIn.close();

    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(mode, key);

    crypt(inputStream, outputStream, cipher);
    byte[] encrypt = outputStream.toByteArray();
    inputStream.close();
    outputStream.close();
    return encrypt;
  }

  public static byte[] encrypt(byte[] keys, byte[] plainData) throws Exception {
    return de_encrypt(keys, plainData, 1);
  }

  public static byte[] decrypt(byte[] keys, byte[] encrypt) throws Exception {
    return de_encrypt(keys, encrypt, 2);
  }

  private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws Exception
  {
    int blockSize = cipher.getBlockSize();
    int outputSize = cipher.getOutputSize(blockSize);
    byte[] inBytes = new byte[blockSize];
    byte[] outBytes = new byte[outputSize];

    int inLength = 0;
    boolean more = true;
    while (more) {
      inLength = in.read(inBytes);
      if (inLength == blockSize) {
        int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
        out.write(outBytes, 0, outLength);
      } else {
        more = false;
      }
    }

    if (inLength > 0)
      outBytes = cipher.doFinal(inBytes, 0, inLength);
    else {
      outBytes = cipher.doFinal();
    }
    out.write(outBytes);
  }

  public static void main(String[] args) {
    String plainData = "haha123@我是中国人@@aaAA____haha123@我是中国人@@aaAA";
    try {
      byte[] keys = generateKey();
      System.out.println("加密前HEX：[" + SignUtil.bytesToHexStr(plainData.getBytes()) + "]");
      System.out.println("KEY：[" + SignUtil.bytesToHexStr(keys) + "]");
      byte[] encrypt = encrypt(keys, plainData.getBytes());
      System.out.println("加密后：[" + SignUtil.bytesToHexStr(encrypt) + "]");
      byte[] decrypt = decrypt(keys, encrypt);
      System.out.println("解密后HEX：[" + SignUtil.bytesToHexStr(decrypt) + "]");
      System.out.println("解密后：[" + new String(decrypt) + "]");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}