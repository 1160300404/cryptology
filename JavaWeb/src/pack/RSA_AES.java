package pack;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class RSA_AES {
    //服务器加密响应给APP的内容
    public static String serverEncrypt(String appPublicKeyStr, String aesKeyStr, String content) throws Exception{
        //将Base64编码后的APP公钥转换成PublicKey对象
        PublicKey appPublicKey = RSACoder.string2PublicKey(appPublicKeyStr);
        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
        //用APP公钥加密AES秘钥
        byte[] encryptAesKey = RSACoder.publicEncrypt(aesKeyStr.getBytes(), appPublicKey);
        //用AES秘钥加密响应内容
        byte[] encryptContent = AESUtil.encryptAES(content.getBytes("UTF-8"), aesKey);

        JSONObject result = new JSONObject();
        result.put("ak", new String(RSACoder.byte2Base64(encryptAesKey).replaceAll("\r\n", "").getBytes(),"UTF-8"));
        result.put("ct", new String(RSACoder.byte2Base64(encryptContent).replaceAll("\r\n", "").getBytes(),"UTF-8"));

        return result.toString();
    }

    //服务器用server私钥解密APP的请求内容
    public static String serverDecrypt(String content) throws Exception{
        JSONObject result = JSONObject.fromObject(content);
        String encryptAesKeyStr = (String) result.get("ak");
        String encryptContent = (String) result.get("ct");
        //将Base64编码后的Server私钥转换成PrivateKey对象
        PrivateKey serverPrivateKey = RSACoder.string2PrivateKey(KEY.key2);
        //用Server私钥解密AES秘钥
        byte[] aesKeyBytes = RSACoder.privateDecrypt(RSACoder.base642Byte(encryptAesKeyStr), serverPrivateKey);
        //用AES秘钥解密APP公钥
        SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
        //用AES秘钥解密请求内容
        byte[] request = AESUtil.decryptAES(RSACoder.base642Byte(encryptContent), aesKey);
        JSONObject result2 = new JSONObject();
        //!!!注意汉字转码
        result2.put("ak", new String(aesKeyBytes,"UTF-8"));
        //result2.put("ct", new String(request));
        result2.put("ct", new String(request,"UTF-8"));
        return result2.get("ct").toString();
    }
}
