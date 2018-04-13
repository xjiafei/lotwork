package com.winterframework.firefrog.fund.util.sppay;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.winterframework.firefrog.fund.util.hbpay.Base64Utils;
 

/**
 * 签名验签类
 * @author kcw
 */

public class SignUtils {
    /** 
     * 签名算法 
     */  
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";  
  
    /** 
    * RSA签名 
    * @param content 待签名数据 
    * @param privateKey 商户私钥 
    * @param encode 字符集编码 
    * @return 签名值 
    */  
    public static String Signaturer(String content, String privateKey)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64Utils.decode(privateKey) );   
            KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes("UTF-8"));  
  
            byte[] signed = signature.sign();  
            return Base64Utils.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return null;  
    }  
    
    /** 
     * RSA验签名检查 
     * @param content 待签名数据 
     * @param sign 签名值 
     * @param publicKey 分配给开发商公钥 
     * @param encode 字符集编码 
     * @return 布尔值 
     */  
     public static boolean validataSign(String content, String sign, String publicKey)  
     {  
         try   
         {  
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
             byte[] encodedKey = Base64Utils.decode(publicKey);  
             PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
             java.security.Signature signature = java.security.Signature  
             .getInstance(SIGN_ALGORITHMS);  
             signature.initVerify(pubKey);  
             signature.update( content.getBytes("UTF-8") );  
             boolean bverify = signature.verify( Base64Utils.decode(sign) );  
             return bverify;  
         }   
         catch (Exception e)   
         {  
             e.printStackTrace();  
         }  
           
         return false;  
     }  
}
