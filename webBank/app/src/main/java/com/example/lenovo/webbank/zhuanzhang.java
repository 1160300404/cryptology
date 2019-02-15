package com.example.lenovo.webbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class zhuanzhang extends AppCompatActivity {
    private EditText name;//输入名称
    private EditText count;//输入金额
    private Button ok;//ok
    private String fromusername;//转出的用户名
    private String usercount;//转出数额
    private static boolean ifgo=false;//阻塞主线程用
    private String tousername;//转入的用户名
    private static String result;//执行转账操作后的结果
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanzhang);
        Intent intent=getIntent();
        fromusername=intent.getStringExtra("from");
        name=(EditText)findViewById(R.id.toname);
        count=(EditText)findViewById(R.id.tocount);
        ok=(Button)findViewById(R.id.ifok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tousername=name.getText().toString();
                usercount=count.getText().toString();
                if(tousername!=null&&usercount!=null) {
                    Intent intent = new Intent("payment");
                    intent.putExtra("username", fromusername);
                    startActivityForResult(intent, 1);

                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(zhuanzhang.this);
                    dialog.setTitle("请输入");
                    dialog.setMessage("用户名或金额为空");
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK){
            String strpaypassword=data.getStringExtra("paypasswd");
            HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/tsfcount?name1=" + fromusername + "&name2=" + tousername+"&paypasswd="+strpaypassword+"&count="+usercount);
            AlertDialog.Builder dialog = new AlertDialog.Builder(zhuanzhang.this);
            Thread thread=new zhuanzhangThread(httpTool);
            thread.start();
            while (ifgo==false){}
            ifgo=false;
            if(result.equals("less")){
                dialog.setTitle("失败");
                dialog.setMessage("余额不足");
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }else if(result.equals("yes")){
                dialog.setTitle("成功");
                dialog.setMessage("转账成功");
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.show();
            }else{
                dialog.setTitle("失败");
                dialog.setMessage("未知错误");
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.show();
            }
        }
    }
    public static void callpack(String reresult){
        result=reresult;
        ifgo=true;
    }
    class zhuanzhangThread extends Thread{
        private HTTPTool httpTool;
        public zhuanzhangThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            zhuanzhang.callpack(result);
        }

    }
}
