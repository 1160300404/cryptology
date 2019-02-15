package com.example.lenovo.webbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminActivity extends AppCompatActivity {
    Button delete;
    Button changepasswd;
    private String passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        delete=(Button)findViewById(R.id.delete);
        changepasswd=(Button)findViewById(R.id.changepasswd);
        passwd=getIntent().getStringExtra("passwd");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("delete");
                intent.putExtra("passwd",passwd);
                startActivity(intent);
            }
        });
        changepasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent=new Intent("changePasswd");
                  intent.putExtra("passwd",passwd);
                  startActivity(intent);
            }
        });
    }
}
