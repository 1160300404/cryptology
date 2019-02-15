package pack;
import mysql.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Map;
//import android.util.Base64;
public class resetServlet extends HttpServlet{
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String name = request.getParameter("name");//获得输入的用户名
        String passwd=request.getParameter("passwd");

        passwd=enc.jiami(passwd);
        passwd=enc.jiami(passwd+enc.salt);
        String getpasswd=sqlop.getpasswd(name);//从数据库获得密码
        System.out.println(name + passwd+" "+getpasswd);
        if(passwd.equals(getpasswd)) {
            String result = sqlop.getreset(name);//从数据库获得余额
            PrintWriter out = response.getWriter();
            String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiY01/xHo08U9nrSlgEqaD8c9K+Y9L46V\n" +
                    "D3UekpFNDYFIrMa8tihiqzuLCZG2xX3ezBE8BmR4SJfoA56dQ/DApPJsbFh4RBzKvb/AHd26fV5i\n" +
                    "Z3Lb2QNU+WldgXH+AeoGxvGAE5m9BMDWhMQaeBcqzvLxwHFzcnEJadcVGpJbYw52UwX0qc9CW6Zn\n" +
                    "OSipz3GSDzuqp48UWRTutyqf/Y9DfzGqTLxS8L/oMS7tpnP99Ybe7fP1+f8HdtCr4sn1c+anrWCM\n" +
                    "Z/WcdglpCgRzOg2hOSW+meLnbKgJD/ZKtI/gzYwl5jR/FrW3P6fb925/XKoW+jO6QYtsCPL3taTJ\n" +
                    "HF7I7QIDAQAB";
            String key2="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJjTX/EejTxT2etKWASpoPxz0r\n" +
                    "5j0vjpUPdR6SkU0NgUisxry2KGKrO4sJkbbFfd7METwGZHhIl+gDnp1D8MCk8mxsWHhEHMq9v8Ad\n" +
                    "3bp9XmJnctvZA1T5aV2Bcf4B6gbG8YATmb0EwNaExBp4FyrO8vHAcXNycQlp1xUakltjDnZTBfSp\n" +
                    "z0Jbpmc5KKnPcZIPO6qnjxRZFO63Kp/9j0N/MapMvFLwv+gxLu2mc/31ht7t8/X5/wd20KviyfVz\n" +
                    "5qetYIxn9Zx2CWkKBHM6DaE5Jb6Z4udsqAkP9kq0j+DNjCXmNH8Wtbc/p9v3bn9cqhb6M7pBi2wI\n" +
                    "8ve1pMkcXsjtAgMBAAECggEAei8UgtH+1nZDiUGALdiWx2M9BEzNlvv5jrSDhsBJCLEJxGf443o2\n" +
                    "Q1Jt6/8isoVfrje08Ry3+AvvzZ9MpTgrd5ncE9X2ZtKIiUrWNfFqociActIFVdzu02nz5HNEpaCj\n" +
                    "in6lPUP4Mku1glPMC1bQ8Om/MynT2hlsujaTCXpucSrpdECaPRGnHTT+8/MRe5fDvAuaAJL7CtI8\n" +
                    "fahKaIWsImDnz15PCBRRHulFkK4/kstn1uWVwxGDNiR0xT6NJ2VxrdMmSV/6j1lu/BoQwrVil3Rh\n" +
                    "fbIBe25VjCZbXFQyoWIKe7i3nm2d1NbNMd9zhh6spnBrXUxZAJFe6vyDxoWfgQKBgQDh2Zgj7Qa6\n" +
                    "6BSvRghcvu2PLbYOBS9e9gJZVLp7itaXvAIX068F+JoTFgQ9Cf61uLucnmx3mrSo+IhX1kSoTGkI\n" +
                    "GD6s339MA+89s4Puun2K2auNR8Y8FpTYNpbGq5D8MKfhrbWeB0j6rAemm0dbUSyP7Tb4LRu0Kt83\n" +
                    "cT7c73SEYQKBgQCb6gbIzSj5Xny2BhsMbEFLo2bGxBAo23LEvLgzC1KiD7TXe0NsWR6npNL5tNxS\n" +
                    "tk0Z2jcrqUyRDqLVXyXuZJdbbvVcfEjKrCzvsl6rsbKwn96ylI5QGXuUHbBGAWhU9Ro3K4NqdZGT\n" +
                    "4nl2RLT/bKktlVBNc2bJHWUevuftOe4QDQKBgELL71JYzQ/EijGYneqIUYxyMZEN+Ye1bNZs18ao\n" +
                    "NnWkFl5jrjUu5KrWbSR8a0flvh9BP8VUDTCYaQLJjX7VMo9BVHAenfCmjUCLcm7N20mQvUtIuhmV\n" +
                    "eCpGYuuZXIUqAs0dakS11ODGtnCRJlacuHhM6WAXP0VXFLO6G6SOccChAoGAC0MmZk7bjNfUblmW\n" +
                    "jpkrRklxXR5AhJMFgUyYiadhuq8jwwT2Y9c0LavsSXlVCOx+OUXYAmFiVIAYJw2Ocd/2RvaG5r1b\n" +
                    "jLhPLqlVyVU2sSd7MK8mcIbGtHZIi2YCoVBrKl0MSx5e+626VF62LFUvC0nj2RRi8lgbO9NScYJj\n" +
                    "vekCgYBVv8WS1b/UGgXhUsmiDLWVDnelLPkDrWU2sr7FOLdo4byBdDEMi9ioXZLIzYtEYX0lzO8U\n" +
                    "wMTVtU8Qla8RucoLDGPEd3hsjRJtTVptFhMx20w612gQ5GJ9nYKMq/4+EaIE3XfMRIX9J75LDAs3\n" +
                    "fTf/+UP/j0qNMdTLDN5VS8Hddw==";
            try {
                BASE64Decoder decoder = new BASE64Decoder();
                BASE64Encoder encoder=new BASE64Encoder();
                KeyPair keyPair = RSACoder.getKeyPair();
                //公钥
                String publicKey = RSACoder.getPublicKey(keyPair);
                //私钥
                String privateKey = RSACoder.getPrivateKey(keyPair);
               // System.out.println("公钥：/n" + publicKey);
               // System.out.println("私钥：/n" + privateKey);
                String str = "RSA密码交换算法";
               // System.out.println("/n===========甲方向乙方发送加密数据==============");
                System.out.println("原文:" + str);
                //甲方进行数据的加密
                byte[] code1 = RSACoder.publicEncrypt(result.getBytes(),RSACoder.string2PublicKey(key));
                //byte[] code1=RSACoder.privateEncrypt("wang".getBytes(),RSACoder.string2PrivateKey(key2));
                System.out.println("加密后的数据：" +  Base64.getEncoder().encodeToString(code1)+" "+code1);
                result=RSACoder.byte2Base64(code1);
                code1=RSACoder.base642Byte(result);
                //乙方进行数据的解密
                System.out.println("编码后："+result+"\n"+code1);
                //byte[] decode1=RSACoder.publicDecrypt(code1,RSACoder.string2PublicKey(key));
                byte[] decode1 = RSACoder.privateDecrypt(code1,RSACoder.string2PrivateKey(key2));
                System.out.println("乙方解密后的数据：" + new String(decode1) + "/n/n");
                out.write(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
}
