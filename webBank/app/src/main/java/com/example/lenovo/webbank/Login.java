package com.example.lenovo.webbank;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    private MessageDigest mDigest;
    private EditText name;
    private EditText password;
    private ImageView vImage;
    private Button  login;
    private TextView zhuce;
    private String realcode;
    private EditText yanzhengma;
    private static String result;//接受HTTP发回来的返回值
    private static boolean ifgo=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vImage=(ImageView)findViewById(R.id.VerifyImage);
        yanzhengma=(EditText)findViewById(R.id.yanzhengma);
        name=(EditText)findViewById(R.id.loginname);
        password=(EditText)findViewById(R.id.loginpassword);
        login=(Button)findViewById(R.id.loginlogin);
        zhuce=(TextView) findViewById(R.id.loginzhuce);
        HTTPTool httpTool1 = new HTTPTool("http://172.20.10.3:8080/JavaWeb/verify");
        Thread thread1=new LoginThread(httpTool1);
        thread1.start();
        while (ifgo == false) {
        }//阻塞主线程
        ifgo=false;
        String code=result;
        result=null;
       vImage.setImageBitmap(IdentifyingCode.getInstance().createBitmap(code));
        realcode=IdentifyingCode.getInstance().getCode().toLowerCase();
        vImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTTPTool httpTool1 = new HTTPTool("http://172.20.10.3:8080/JavaWeb/verify");
                Thread thread1=new LoginThread(httpTool1);
                thread1.start();
                while (ifgo == false) {
                }//阻塞主线程
                ifgo=false;
                String code=result;
                vImage.setImageBitmap(IdentifyingCode.getInstance().createBitmap(code));
                realcode=IdentifyingCode.getInstance().getCode().toLowerCase();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!yanzhengma.getText().toString().toLowerCase().equals(realcode)){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                    dialog.setTitle("错误");
                    dialog.setMessage("验证码错误");
                    dialog.show();
                    return ;
                }
                try {
                    mDigest=MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                String strname=name.getText().toString();
                String strpassword=password.getText().toString();
                mDigest.update(strpassword.getBytes());
                byte[] bytes=mDigest.digest();
                StringBuffer strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                strpassword=strBuffer.toString();
                if(!strname.equals("")&&!strpassword.equals("")) {
                    HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/login?name=" + strname + "&passwd=" + strpassword);
                    Thread thread = new LoginThread(httpTool);
                    thread.start();
                    while (ifgo == false) {
                    }//阻塞主线程
                    ifgo=false;
                    if(strname.equals("admin")){
                        if(result.equals("yes")){
                            Intent intent=new Intent("admin");
                            intent.putExtra("passwd",strpassword);
                            name.setText("");
                            password.setText("");
                            startActivity(intent);
                        }else{
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                            dialog.setTitle("密码错误");
                            dialog.setMessage("管理员密码错误");
                            dialog.show();
                        }
                    }else {
                        Intent intent = new Intent();
                        Intent intent1 = intent.putExtra("name", strname);
                        intent.putExtra("result", result);
                        intent.putExtra("passwd", strpassword);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                    dialog.setTitle("请输入");
                    dialog.setMessage("请输入用户名密码");
                    dialog.show();
                }
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent("zhuce");
              startActivity(intent);
            }
        });
    }

    public static void callpack(String reresult){
       result=reresult;
       ifgo=true;
    }
    class LoginThread extends Thread{
        private HTTPTool httpTool;
        public LoginThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
               String result=httpTool.getresult();
               Login.callpack(result);
        }

    }
}
