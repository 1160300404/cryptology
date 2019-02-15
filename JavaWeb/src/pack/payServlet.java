package pack;
import mysql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//这个类用来做支付
public class payServlet extends HttpServlet{
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String name = request.getParameter("name");//获得输入的用户名
        String paypasswd = request.getParameter("paypasswd");//获得输入的密码
        paypasswd=enc.jiami(paypasswd);
        paypasswd=enc.jiami(paypasswd+enc.salt);
        System.out.println(name + paypasswd);
        String getpaypasswd=sqlop.getpaypasswd(name);//从数据库获得密码
        if (paypasswd.equals(getpaypasswd)) {//进行比对，看是否一样
           // response.setContentType("text/html");
// 实际的逻辑是在这里
            PrintWriter out = response.getWriter();
            out.write("yes");
        }
        else{
            //response.setContentType("text/html");
// 实际的逻辑是在这里
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
}
