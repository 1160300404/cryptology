package mysql;
import pack.enc;

import java.sql.*;
public class sqlop {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bank?useSSL=false";
    static final String USER = "root";
    static final String PASS = "123456";
    static Connection conn = null;
    static Statement stmt = null;
    static PreparedStatement psql;
    //初始化类开启与数据库的连接
    public static void init(){
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }
    //根据用户输入的用户名来查找passwd，如果能够找到则返回passwd，如果找不到则返回空
    public static String getpasswd(String name) {
        sqlop.init();
        System.out.println(" 实例化Statement对象...查询登陆密码");
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT passwd FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
               String passwd=rs.getString("passwd");
                conn.close();
                return passwd;
            }else{
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
    //根据用户输入的用户名来查找usrpasswd，如果能够找到则返回userpasswd，如果找不到则返回空
    public static String getpaypasswd(String name){
        sqlop.init();
        System.out.println(" 实例化Statement对象...查询支付密码");
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT paypasswd FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                String passwd=rs.getString("paypasswd");
                conn.close();
                return passwd;
            }else{
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
    //插入数据，要求吧用户名，密码，支付密码输全了
    public static String insert(String name,String passwd ,String paypasswd){
        sqlop.init();
        System.out.println(" 插入数据.....");
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                conn.close();
                return "exit";
            }
            psql=conn.prepareStatement("insert into userinfo (name,passwd,paypasswd,money)"+"values(?,?,?,?)");
            psql.setString(1, name);
           /* passwd= enc.jiami(passwd);
            paypasswd=enc.jiami(passwd);*/
            psql.setString(2, passwd);
            psql.setString(3, paypasswd);
            psql.setInt(4,1000);
            if(psql.executeUpdate()==1){
                conn.close();
                return "yes";
            }else{
                conn.close();
                return "no";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return "no";
        }
    }
    //查找用户的余额
    public static String getreset(String name){
        sqlop.init();
        double count;
        System.out.println("查询余额....");
        try{
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT money FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                count=rs.getDouble("money");
                String counts=String.valueOf(count);
                return counts;
            }else{
                conn.close();
                return "no";
            }
        }catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return "no";
    }

    /**
     * 处理转账问题,name1是出账的，name2是入账的
     * @param name1
     * @param name2
     * @param count
     * @return
     */
    public static String tsfaccount(String name1,String name2,Double count){
        sqlop.init();
        double count2;
        double count1;
        System.out.println(" 转账.....");
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT money FROM userinfo WHERE name="+"'"+name1+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                count1=rs.getDouble("money");
            }else{
                conn.close();
                return "no";
            }
            sql = "SELECT money FROM userinfo WHERE name="+"'"+name2+"'";//创建查找语句
            rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                count2=rs.getDouble("money");
            }else{
                conn.close();
                return "no";
            }
            if(count1<count){
                conn.close();
                return "less";
            }
            count1-=count;
            count2+=count;
            psql=conn.prepareStatement("UPDATE userinfo SET money= ? WHERE name= ? ");
            psql.setString(2,name1);
            psql.setDouble(1,count1);
            if(psql.executeUpdate()==1 ){
                psql = conn.prepareStatement("UPDATE userinfo SET money= ? WHERE name= ? ");
                psql.setString(2,name2);
                psql.setDouble(1,count2);
                if (psql.executeUpdate() == 1) {
                    conn.close();
                    return "yes";
                }
                else {
                    conn.close();
                    return "no";
                }
            }else{
                conn.close();
                return "no";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return "no";
    }
    public static String deleteUser(String name){
        sqlop.init();
        System.out.println(" 删除用户.....");
        try {stmt = conn.createStatement();
            String sql;
            sql = "SELECT money FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()) {
                psql = conn.prepareStatement("DELETE FROM userinfo WHERE name=?");
                psql.setString(1, name);
                if (psql.executeUpdate() == 1) {
                    conn.close();
                    return "yes";
                } else {
                    conn.close();
                    return "no";
                }
            }
            else{
                conn.close();
                return  "no";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "no";
    }
    public static String change(String name,String passwd,String paypasswd){
        sqlop.init();
        System.out.println(" 改变密码.....");
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT money FROM userinfo WHERE name="+"'"+name+"'";//创建查找语句
            ResultSet rs = stmt.executeQuery(sql);//执行
            if(rs.next()){
                psql = conn.prepareStatement("UPDATE userinfo SET passwd= ? ,paypasswd=? WHERE name= ? ");
                psql.setString(2,paypasswd);
                psql.setString(1,passwd);
                psql.setString(3,name);
                if (psql.executeUpdate() == 1) {
                    conn.close();
                    return "yes";
                }
                else {
                    conn.close();
                    return "no";
                }
            }else{
                conn.close();
                return "no";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "no";
    }
}
