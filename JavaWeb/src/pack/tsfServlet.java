package pack;
import mysql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
public class tsfServlet extends HttpServlet{
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
// 设置响应内容类型
        String name1 = request.getParameter("name1");//获得输入的用户名
        String name2=request.getParameter("name2");//获得输入的用户名
        String paypasswd=request.getParameter("paypasswd");//获得输入的密码
        String counts = request.getParameter("count");//获得输入的金额
        paypasswd=enc.jiami(paypasswd);
        paypasswd=enc.jiami(paypasswd+enc.salt);
        System.out.println(name1+name2 + paypasswd+counts);
        String getpaypasswd=sqlop.getpaypasswd(name1);//从数据库获得密码
        if (paypasswd.equals(getpaypasswd)) {
            double count = Double.valueOf(counts);
            String result = sqlop.tsfaccount(name1, name2, count);//从数据库获得密码
            PrintWriter out = response.getWriter();
            out.write(result);
        }else{
            PrintWriter out = response.getWriter();
            out.write("no");
        }
    }
}
