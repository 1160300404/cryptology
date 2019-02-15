package pack;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;
import mysql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//这个类用来做登陆
public class Aservlet extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String str = request.getParameter("name");//获得输入的用户名
        String passwd = request.getParameter("passwd");//获得输入的密码
        passwd=enc.jiami(passwd);
        passwd=enc.jiami(passwd+enc.salt);
        String getpasswd=sqlop.getpasswd(str);//从数据库获得密码
        System.out.println(str + passwd+" "+getpasswd);
        if(passwd.equals(getpasswd)){
            PrintWriter out = response.getWriter();
            out.write("yes");
            if(str.equals("admin")){
                Logger log = Logger.getLogger(DeleteServlet.class);
                log.info("admin login");
            }
        }else{
            //response.setContentType("text/html");
// 实际的逻辑是在这里
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
   /* public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String str = request.getParameter("name");//获得输入的用户名
        String passwd = request.getParameter("passwd");//获得输入的密码
        System.out.println(str + passwd);
        String getpasswd=sqlop.getpasswd(str);//从数据库获得密码
        if(passwd.equals(getpasswd)){
            // response.setContentType("text/html");
// 实际的逻辑是在这里
            PrintWriter out = response.getWriter();
            out.write("yes");
        }else{
            response.setContentType("text/html");
// 实际的逻辑是在这里
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }*/
}
