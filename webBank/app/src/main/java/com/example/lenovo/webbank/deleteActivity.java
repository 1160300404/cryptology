package com.example.lenovo.webbank;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class deleteActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String passwd;
    private static Boolean ifgo=false;
    private static String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        editText=(EditText)findViewById(R.id.deleteText);
        button=(Button)findViewById(R.id.deleteButton);
        passwd=getIntent().getStringExtra("passwd");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText.getText().toString();
                HTTPTool httpTool = new HTTPTool("http://172.20.10.3:8080/JavaWeb/delete?name=admin&passwd=" + passwd+"&delete="+name);
                Thread thread = new DeleteThread(httpTool);
                thread.start();
                while(ifgo==false){}
                ifgo=false;
                System.out.println(result);
                if(result.equals("no")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(deleteActivity.this);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.setTitle("删除失败");
                    dialog.setMessage("删除用户失败");
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(deleteActivity.this);
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    dialog.setTitle("删除成功");
                    dialog.setMessage("删除用户成功");
                    dialog.show();
                }
            }
        });
    }
    public static void callpack(String reresult){
        result=reresult;
        ifgo=true;
    }
    class DeleteThread extends Thread{
        private HTTPTool httpTool;
        public DeleteThread(HTTPTool httpTool){
            this.httpTool=httpTool;
        }
        @Override
        public void run(){
            String result=httpTool.getresult();
            deleteActivity.callpack(result);
        }

    }
}
