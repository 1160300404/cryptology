package pack;

import mysql.sqlop;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChangeServlet extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String name1 = request.getParameter("name");//获得管理员的用户名
        String name2=request.getParameter("change");//获得输入的用户名
        String passwd=request.getParameter("passwd");//获得管理员密码
        String newpasswd=request.getParameter("newpasswd");//获得修改后的密码
        String newpaypasswd=request.getParameter("newpaypasswd");//获得输入的密码
        System.out.println(name1+name2 + passwd+newpasswd+newpaypasswd);
        newpaypasswd=enc.jiami(newpaypasswd);
        newpaypasswd=enc.jiami(newpaypasswd+enc.salt);
        newpasswd=enc.jiami(newpasswd);
        newpasswd=enc.jiami(newpasswd+enc.salt);
        passwd=enc.jiami(passwd);
        passwd=enc.jiami(passwd+enc.salt);
        System.out.println(name1+name2 + passwd);
        String getpaypasswd= sqlop.getpaypasswd("admin");//从数据库获得密码
        if (passwd.equals(getpaypasswd)) {
            String result=sqlop.change(name2,newpasswd,newpaypasswd);
            PrintWriter out = response.getWriter();
            out.write(result);
            if(result.equals("yes")){
                Logger log = Logger.getLogger(DeleteServlet.class);
                log.info("admin chamge user "+name2+" passwd and paypasswd");
            }
        }else{
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
}
