package com.example.lenovo.webbank;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.method.*;
import android.text.method.PasswordTransformationMethod;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class payment extends AppCompatActivity {
    private MessageDigest mDigest;
    private boolean ifshow=true;
    private CheckBox show;
    private Button  paysure;
    private  EditText editText;
    private static String result;//执行支付确认的操作的结果
    private static boolean ifgo;//阻塞用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        editText=(EditText)findViewById(R.id.editText);
        show=(CheckBox) findViewById(R.id.showmima);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show.isChecked()){
                    editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        paysure=(Button)findViewById(R.id.paysure);
        paysure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDigest=MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Intent intent=getIntent();
                String strname=intent.getStringExtra("username");//用户名
                String  strpaypassword=editText.getText().toString();//用户支付密码
                mDigest.update(strpaypassword.getBytes());
                byte[] bytes=mDigest.digest();
                StringBuffer strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                strpaypassword=strBuffer.toString();
                HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/pay?name=" + strname + "&paypasswd=" + strpaypassword);
                Thread thread=new payThread(httpTool);
                thread.start();
                while (ifgo==false){}
                ifgo=false;
                if(result.equals("no")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(payment.this);
                    dialog.setTitle("失败");
                    dialog.setMessage("支付密码错误");
                    dialog.show();
                }else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("paypasswd",strpaypassword);
                    setResult(RESULT_OK, intent1);
                    finish();
                }
            }
        });
    }
    public static void callpack(String reresult){
        result=reresult;
        ifgo=true;
    }
    class payThread extends Thread{
        private HTTPTool httpTool;
        public payThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            payment.callpack(result);
        }

    }
}

