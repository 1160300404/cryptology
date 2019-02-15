package pack;

import mysql.sqlop;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteServlet extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String name1 = request.getParameter("name");//获得管理员的用户名
        String passwd=request.getParameter("passwd");//获得输入的密码
        String name2 = request.getParameter("delete");//获得要删除的用户名
        passwd=enc.jiami(passwd);
        passwd=enc.jiami(passwd+enc.salt);
        System.out.println(name1+ passwd+name2);
        String getpaypasswd= sqlop.getpaypasswd("admin");//从数据库获得密码
        if (passwd.equals(getpaypasswd)) {
            String result = sqlop.deleteUser(name2);//从数据库获得密码
            PrintWriter out = response.getWriter();
            Logger log = Logger.getLogger(DeleteServlet.class);
            log.info("admin delete user"+name2);
            out.write(result);
        }else{
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
}
