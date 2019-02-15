package pack;

import mysql.sqlop;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class VImageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        VerifyImage verifyImage=new VerifyImage();
        String string=verifyImage.createCode();
        PrintWriter out=response.getWriter();
        out.write(string);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException{
        doGet(request,response);
    }
}
