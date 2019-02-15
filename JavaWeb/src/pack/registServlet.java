package pack;
import mysql.*;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class registServlet extends HttpServlet{
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
        String name = request.getParameter("name");//获得输入的用户名
        String passwd=request.getParameter("passwd");//获得输入的密码
        String paypasswd = request.getParameter("paypasswd");//获得输入的支付密码
        passwd=enc.jiami(passwd);
        passwd=enc.jiami(passwd+enc.salt);
        paypasswd=enc.jiami(paypasswd);
        paypasswd=enc.jiami(paypasswd+enc.salt);
        System.out.println(name + passwd+paypasswd);
        String str=sqlop.insert(name,passwd,paypasswd);
        PrintWriter out = response.getWriter();
        HTTPTool httpTool=new HTTPTool("http://172.20.10.4:8080/ca/connectServlet?name="+name);
        String str1=httpTool.getresult();
        BASE64Decoder base64Decoder=new BASE64Decoder();
        byte[] bytes=RSACoder.base642Byte(str1);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(bytes));
        PublicKey publicKey=RSACoder.string2PublicKey(KEY.CA_PUBLIC_KEY);
        cert.verify(publicKey);
        System.out.println("verify success");
        out.write(str+"//"+str1);
        } catch(SignatureException e){
            System.out.println("verify failed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
