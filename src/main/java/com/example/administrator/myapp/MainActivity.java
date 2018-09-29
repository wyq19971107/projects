package com.example.administrator.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity {

    public static AVObject person;
    public static AVObject cardPick;
    public static String accounts;
    String passwords;
    int flagaccount=0;
    int flagpassword=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        person = new AVObject("persons");//后台创建一个表名为persons
        cardPick=new AVObject("cardPick");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void go_register(View view)
    {
        Intent intent = new Intent(this, registe.class);
        startActivity(intent);
    }
    public void go_login(View view)
    {
        EditText account;
        EditText password;
        account = (EditText) findViewById(R.id.login_edit1);
        password = (EditText) findViewById(R.id.login_edit2);
         accounts = account.getText().toString();
         passwords = password.getText().toString();

        AVQuery<AVObject> query1= new AVQuery<>("persons");
        query1.selectKeys(Arrays.asList("account","password"));
        query1.findInBackground(new FindCallback<AVObject>(){
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null) {
                    for (AVObject avObject : list) {
                        String account1 = avObject.getString("account");
                        String password1 = avObject.getString("password");
                        if (account1.equals(accounts)) {
                            flagaccount = 1;
                            if (password1.equals(passwords)) {
                                flagpassword = 1;
                            } else {
                                flagpassword =2;
                            }
                            break;
                        }
                        else
                            flagaccount=2;
                    }
                    if(flagaccount==2)
                        Toast.makeText(getApplicationContext(), "该账户不存在，您可能并没有注册", Toast.LENGTH_LONG).show();
                    else
                    {
                        if(flagpassword==2)
                        {
                            Toast.makeText(getApplicationContext(), "密码输入错误", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "网络断开啦", Toast.LENGTH_SHORT).show();
            }
        });
                if(flagpassword==1)
                {
                    Intent intent = new Intent(this, login.class);
                    startActivity(intent);
                }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
