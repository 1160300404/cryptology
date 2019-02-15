package com.example.lenovo.webbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String resetmoney;//后端实现后填充
    static boolean ifgo=false;//阻塞主线程
    private Boolean islogin=false;
    private String name;
    public  static  String NAME;
    private String passwd;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text2);
        Button zhuanzhang=(Button)findViewById(R.id.mainzhuanzhang);//转账按钮
        Button reset=(Button)findViewById(R.id.mainreset) ;//余额查询按钮
        Button login=(Button)findViewById(R.id.mainlogin);//登陆按钮
        Button outlogin=(Button)findViewById(R.id.outlogin);
        outlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(islogin){
                    islogin=false;
                    name=null;
                    NAME=null;
                    passwd=null;
                    textView.setText("");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("");
                    dialog.setMessage("成功退出登陆");
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请登录");
                    dialog.setMessage("用户未登陆");
                    dialog.show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent("Login");
               startActivityForResult(intent,2);
            }
        });
        zhuanzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(islogin) {
                   Intent intent = new Intent("zhuanzhang");
                   intent.putExtra("from",name);
                   startActivityForResult(intent, 1);
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请登录");
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.setMessage("用户未登陆");
                    dialog.show();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(islogin) {
                    HTTPTool httpTool=new HTTPTool("http://172.20.10.3:8080/JavaWeb/reset?name="+name+"&passwd="+passwd);
                    Thread resetLogin=new ResetThread(httpTool);
                    resetLogin.start();
                    while (ifgo==false){}//阻塞主线程
                    ifgo=false;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiY01/xHo08U9nrSlgEqaD8c9K+Y9L46V\n" +
                            "D3UekpFNDYFIrMa8tihiqzuLCZG2xX3ezBE8BmR4SJfoA56dQ/DApPJsbFh4RBzKvb/AHd26fV5i\n" +
                            "Z3Lb2QNU+WldgXH+AeoGxvGAE5m9BMDWhMQaeBcqzvLxwHFzcnEJadcVGpJbYw52UwX0qc9CW6Zn\n" +
                            "OSipz3GSDzuqp48UWRTutyqf/Y9DfzGqTLxS8L/oMS7tpnP99Ybe7fP1+f8HdtCr4sn1c+anrWCM\n" +
                            "Z/WcdglpCgRzOg2hOSW+meLnbKgJD/ZKtI/gzYwl5jR/FrW3P6fb925/XKoW+jO6QYtsCPL3taTJ\n" +
                            "HF7I7QIDAQAB";
                    String key2="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJjTX/EejTxT2etKWASpoPxz0r\n" +
                            "5j0vjpUPdR6SkU0NgUisxry2KGKrO4sJkbbFfd7METwGZHhIl+gDnp1D8MCk8mxsWHhEHMq9v8Ad\n" +
                            "3bp9XmJnctvZA1T5aV2Bcf4B6gbG8YATmb0EwNaExBp4FyrO8vHAcXNycQlp1xUakltjDnZTBfSp\n" +
                            "z0Jbpmc5KKnPcZIPO6qnjxRZFO63Kp/9j0N/MapMvFLwv+gxLu2mc/31ht7t8/X5/wd20KviyfVz\n" +
                            "5qetYIxn9Zx2CWkKBHM6DaE5Jb6Z4udsqAkP9kq0j+DNjCXmNH8Wtbc/p9v3bn9cqhb6M7pBi2wI\n" +
                            "8ve1pMkcXsjtAgMBAAECggEAei8UgtH+1nZDiUGALdiWx2M9BEzNlvv5jrSDhsBJCLEJxGf443o2\n" +
                            "Q1Jt6/8isoVfrje08Ry3+AvvzZ9MpTgrd5ncE9X2ZtKIiUrWNfFqociActIFVdzu02nz5HNEpaCj\n" +
                            "in6lPUP4Mku1glPMC1bQ8Om/MynT2hlsujaTCXpucSrpdECaPRGnHTT+8/MRe5fDvAuaAJL7CtI8\n" +
                            "fahKaIWsImDnz15PCBRRHulFkK4/kstn1uWVwxGDNiR0xT6NJ2VxrdMmSV/6j1lu/BoQwrVil3Rh\n" +
                            "fbIBe25VjCZbXFQyoWIKe7i3nm2d1NbNMd9zhh6spnBrXUxZAJFe6vyDxoWfgQKBgQDh2Zgj7Qa6\n" +
                            "6BSvRghcvu2PLbYOBS9e9gJZVLp7itaXvAIX068F+JoTFgQ9Cf61uLucnmx3mrSo+IhX1kSoTGkI\n" +
                            "GD6s339MA+89s4Puun2K2auNR8Y8FpTYNpbGq5D8MKfhrbWeB0j6rAemm0dbUSyP7Tb4LRu0Kt83\n" +
                            "cT7c73SEYQKBgQCb6gbIzSj5Xny2BhsMbEFLo2bGxBAo23LEvLgzC1KiD7TXe0NsWR6npNL5tNxS\n" +
                            "tk0Z2jcrqUyRDqLVXyXuZJdbbvVcfEjKrCzvsl6rsbKwn96ylI5QGXuUHbBGAWhU9Ro3K4NqdZGT\n" +
                            "4nl2RLT/bKktlVBNc2bJHWUevuftOe4QDQKBgELL71JYzQ/EijGYneqIUYxyMZEN+Ye1bNZs18ao\n" +
                            "NnWkFl5jrjUu5KrWbSR8a0flvh9BP8VUDTCYaQLJjX7VMo9BVHAenfCmjUCLcm7N20mQvUtIuhmV\n" +
                            "eCpGYuuZXIUqAs0dakS11ODGtnCRJlacuHhM6WAXP0VXFLO6G6SOccChAoGAC0MmZk7bjNfUblmW\n" +
                            "jpkrRklxXR5AhJMFgUyYiadhuq8jwwT2Y9c0LavsSXlVCOx+OUXYAmFiVIAYJw2Ocd/2RvaG5r1b\n" +
                            "jLhPLqlVyVU2sSd7MK8mcIbGtHZIi2YCoVBrKl0MSx5e+626VF62LFUvC0nj2RRi8lgbO9NScYJj\n" +
                            "vekCgYBVv8WS1b/UGgXhUsmiDLWVDnelLPkDrWU2sr7FOLdo4byBdDEMi9ioXZLIzYtEYX0lzO8U\n" +
                            "wMTVtU8Qla8RucoLDGPEd3hsjRJtTVptFhMx20w612gQ5GJ9nYKMq/4+EaIE3XfMRIX9J75LDAs3\n" +
                            "fTf/+UP/j0qNMdTLDN5VS8Hddw==";
                    try {
                            System.out.println(resetmoney);
                            byte[] code1= RSACoder.base642Byte(resetmoney);//RSACoder.base642Byte(resetmoney);
                            //byte[] bytes1=RSACoder.publicDecrypt(code1,RSACoder.string2PublicKey(key));
                            byte[] bytes1= RSACoder.privateDecrypt(code1,RSACoder.string2PrivateKey(key2));
                            resetmoney=new String(bytes1);
                        System.out.println(resetmoney);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.setTitle("用户余额");
                    dialog.setMessage(resetmoney);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请登录");
                    dialog.setMessage("用户未登陆");
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       /* if(requestCode==1&&resultCode==RESULT_OK){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("转账成功");
            dialog.show();
        }*/
        if(requestCode==2&&resultCode==RESULT_OK){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            if(data.getStringExtra("result").equals("yes")) {
                islogin=true;
                name=data.getStringExtra("name");
                NAME=name;
                passwd=data.getStringExtra("passwd");
                textView.setText("欢迎登陆:"+name);
                dialog.setMessage("登陆成功");
            }
            else{
                dialog.setMessage("登陆失败");
            }
            dialog.show();
        }

    }
    public static void callpack(String reresult){
        resetmoney=reresult;
        ifgo=true;
    }
    class ResetThread extends Thread{
        private HTTPTool httpTool;
        public ResetThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            MainActivity.callpack(result);
           /* System.out.println(resetmoney);
            System.out.println(ifgo);*/
        }

    }
}
