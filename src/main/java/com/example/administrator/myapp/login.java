package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2018/3/30.
 */

public class login extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        Intent intent = getIntent();
    }
    public void forhelp(View view){
        Intent intent = new Intent(this, forhelp.class);
        startActivity(intent);
    }
    public void tohelp(View view){
        Intent intent = new Intent(this, tohelp.class);
        startActivity(intent);
    }
    public void to_password(View view){
        Intent intent = new Intent(this, password.class);
        startActivity(intent);
    }
}
