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
import android.widget.ImageView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static android.provider.Contacts.SettingsColumns.KEY;

public class zhuceActivity extends AppCompatActivity {
    private MessageDigest mDigest;
    static String result;//后端实现后填充
    static boolean ifgo=false;//阻塞主线程
    private EditText name;
    private EditText password;
    private EditText paypassword;
    private EditText yanzhengma;
    private ImageView vImage;
    private String realcode;
    private Button zhuce;
    private CheckBox ifshow;
    private CheckBox ifshow2;
    private Boolean iffinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        vImage=(ImageView)findViewById(R.id.VerifyImage_zhuce);
        yanzhengma=(EditText)findViewById(R.id.yanzhengma_zhuce);
        name=(EditText)findViewById(R.id.zhuce_name);
        password=(EditText)findViewById(R.id.zhuce_password);
        paypassword=(EditText)findViewById(R.id.zhuce_paypassword);
        zhuce=(Button)findViewById(R.id.zhuce_zhuce);
        ifshow=(CheckBox)findViewById(R.id.zhuce_show);
        ifshow2=(CheckBox)findViewById(R.id.zhuce_payshow);
        HTTPTool httpTool1 = new HTTPTool("http://172.20.10.3:8080/JavaWeb/verify");
        Thread thread1=new zhuceThread(httpTool1);
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
                Thread thread1=new zhuceThread(httpTool1);
                thread1.start();
                while (ifgo == false) {
                }//阻塞主线程
                ifgo=false;
                String code=result;
                vImage.setImageBitmap(IdentifyingCode.getInstance().createBitmap(code));
                realcode=IdentifyingCode.getInstance().getCode().toLowerCase();

            }
        });
        ifshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifshow.isChecked()){
                    password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        ifshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifshow2.isChecked()){
                    paypassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                else{
                    paypassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=yanzhengma.getText().toString().toLowerCase();
                System.out.println(str+" "+realcode);
                if(!str.equals(realcode)){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(zhuceActivity.this);
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
                String strpaypassword=paypassword.getText().toString();
                mDigest.update(strpassword.getBytes());
                byte[] bytes=mDigest.digest();
                StringBuffer strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                strpassword=strBuffer.toString();
                mDigest.update(strpaypassword.getBytes());
                bytes=mDigest.digest();
                strBuffer = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    strBuffer.append(Integer.toHexString(0xff & bytes[i]));
                }
                strpaypassword=strBuffer.toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(zhuceActivity.this);
                if(!strname.equals("")&&!strpassword.equals("")&&!strpaypassword.equals("")) {
                    HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/regist?name=" + strname + "&passwd=" + strpassword+"&paypasswd="+strpaypassword);
                    Thread thread=new zhuceThread(httpTool);
                    thread.start();
                    while(ifgo==false){}
                    ifgo=false;
                    String[] result1 = result.split("//");
                    try {
                        byte[] bytes1=RSACoder.base642Byte(result1[1]);
                        CertificateFactory cf = null;
                        cf = CertificateFactory.getInstance("X.509");
                        X509Certificate cert = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(bytes1));
                        PublicKey publicKey=RSACoder.string2PublicKey(KEYS.CA_PUBLIC_KEY);
                        cert.verify(publicKey);
                        System.out.println("verify success");
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (SignatureException e) {
                        System.out.println("verify failed");
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(result1[0].equals("yes")) {
                        dialog.setTitle("成功");
                        dialog.setMessage("注册成功");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        dialog.show();
                    }else if(result1[0].equals("exit")){
                        dialog.setTitle("失败");
                        dialog.setMessage("用户名已被注册");
                        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dialog.show();
                    }else{
                        dialog.setTitle("失败");
                        dialog.setMessage("未知错误");
                        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dialog.show();
                    }
                }else{
                    dialog.setTitle("请输入");
                    dialog.setMessage("请输入用户名密码");
                    dialog.show();
                }
            }
        });
    }
    public static void callpack(String reresult){
        result=reresult;
        ifgo=true;
    }
    class zhuceThread extends Thread{
        private HTTPTool httpTool;
        public zhuceThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            zhuceActivity.callpack(result);
        }

    }
}
