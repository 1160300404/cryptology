package pack;

import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@MultipartConfig(location="F:\\Program Files (x86)\\eclipse")
public class HTTPTool {
    private String urlStr;
    public HTTPTool(String urlStr){
        this.urlStr=urlStr;
    }
    public String getresult(){
        //boolean loginValidate = false;
        try {
            URL url = new URL(urlStr);//由输入的参数设置url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//准备连接类
            con.setRequestMethod("GET");    //设置为Get方法
            con.setConnectTimeout(8000);
            con.setReadTimeout(8000);
            if (HttpURLConnection.HTTP_OK == con.getResponseCode()) {//查看返回值
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));//获取返回值
                StringBuffer str=new StringBuffer();
                String str1;
                do{
                    str1=null;
                    str1=in.readLine();
                    if(str1!=null)
                        str.append(str1+'\n');
                } while(str1!=null);
                str.deleteCharAt(str.length()-1);
                //str=in.readLine();//获取返回值
                System.out.println("READ: " + str.toString());
                //loginValidate=true;
                in.close();
                return str.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
