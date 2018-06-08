package com.example.jayhind.workingwithservices;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img,ok;
    Button btn;
    Context context;
    private String url[]={"http://192.168.43.55:80/android/logo.png","http://192.168.43.55:80/android/logo.png","http://192.168.43.55:80/android/logo.png","http://192.168.43.55:80/android/logo.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        img=findViewById(R.id.img);
        ok=findViewById(R.id.ok);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//      Toast.makeText(context, "okkkk", Toast.LENGTH_SHORT).show();
        imgDowanloadingService.startActionBaz(context,url);
    }
}
