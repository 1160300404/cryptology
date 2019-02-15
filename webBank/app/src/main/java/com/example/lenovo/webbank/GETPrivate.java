package com.example.lenovo.webbank;


import java.security.PrivateKey;


/**
 * Created by lenovo on 2018/12/29.
 */

public class GETPrivate {
    public PrivateKey privateKey;
    public  GETPrivate(){
        try {
            String str=RSA_AES.serverEncrypt(KEYS.CA_PUBLIC_KEY,MainActivity.NAME,AESUtil.genKeyAES());
            HTTPTool httpTool=new HTTPTool("http://172.20.10.4:8080/ca/connectServlet?name=");
            String strcert=httpTool.getresult();
            privateKey=RSACoder.string2PrivateKey(strcert);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
