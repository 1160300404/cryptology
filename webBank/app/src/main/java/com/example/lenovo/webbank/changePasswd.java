package com.example.lenovo.webbank;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class changePasswd extends AppCompatActivity {
    private MessageDigest mDigest;
    private EditText changeName;
    private EditText changePassword;
    private EditText changePaypassword;
    private Button changeButton;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private static Boolean ifgo=false;
    private static String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        changeName=(EditText)findViewById(R.id.changeName);
        changePassword=(EditText)findViewById(R.id.changePassword);
        changePaypassword=(EditText)findViewById(R.id.changePaypassword);
        changeButton=(Button)findViewById(R.id.changeButton);
        checkBox1=(CheckBox)findViewById(R.id.change_show);
        checkBox2=(CheckBox)findViewById(R.id.change_show2);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()){
                    changePassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    changePassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox2.isChecked()){
                    changePaypassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    changePaypassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDigest=MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                String name=changeName.getText().toString();
                String newpassword=changePassword.getText().toString();
                mDigest.update(newpassword.getBytes());
                byte[] bytes=mDigest.digest();
                StringBuffer strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                newpassword=strBuffer.toString();
                String newpaypassword=changePaypassword.getText().toString();
                mDigest.update(newpaypassword.getBytes());
                bytes=mDigest.digest();
                strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                newpaypassword=strBuffer.toString();
                String adminpassword=getIntent().getStringExtra("passwd");
                HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/change?name=admin&passwd=" + adminpassword+"&change="+name+"&newpasswd="+newpassword+"&newpaypasswd="+newpaypassword);
                Thread thread = new ChangeThread(httpTool);
                thread.start();
                while(ifgo==false){}
                ifgo=false;
                if(result.equals("no")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(changePasswd.this);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.setTitle("修改失败");
                    dialog.setMessage("修改用户失败");
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(changePasswd.this);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.setTitle("修改成功");
                    dialog.setMessage("修改用户成功");
                    dialog.show();
                }
            }
        });
    }
    public static void callpack(String reresult){
        result=reresult;
        ifgo=true;
    }
    class ChangeThread extends Thread{
        private HTTPTool httpTool;
        public ChangeThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            changePasswd.callpack(result);
        }

    }
}
