package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Administrator on 2018/3/28.
 */

public class registe extends Activity {
    String accounts;
    int flag2=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registe_in);
        Intent intent=getIntent();
    }
    public void register(View view) {
        EditText account;
        EditText password;
        EditText phone;
        account = (EditText) findViewById(R.id.registe_edit1);
        password = (EditText) findViewById(R.id.registe_edit2);
        phone = (EditText) findViewById(R.id.registe_edit3);
        accounts = account.getText().toString();
        String passwords = password.getText().toString();
        String phones = phone.getText().toString();
        if (accounts.isEmpty()==false) {
            if (ispassword(passwords)) {
                if (isPhone(phones)) {
                    AVQuery<AVObject> query2 = new AVQuery<>("persons");
                    query2.selectKeys(Arrays.asList("account"));
                    query2.findInBackground(new FindCallback<AVObject>(){
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null){
                                for (AVObject avObject : list) {
                                    String account1 = avObject.getString("account");
                                    if (account1.equals(accounts)) {
                                        Toast.makeText(getApplicationContext(), "该账户已被注册", Toast.LENGTH_LONG).show();
                                        flag2 = 1;
                                        break;
                                    }
                                    else
                                        flag2=2;
                                }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "网络断开啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                        if (flag2==2){ //如果没有这个账户
                             try {
                                MainActivity.person.put("account", accounts);
                                MainActivity.person.put("password", passwords);
                                MainActivity.person.put("phone",phones);
                                MainActivity.person.saveInBackground();// 保存到服务端
                                //Toast:是一个类，主要管理消息的提示。
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                            } catch (Exception exc) {
                                Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                }else {
                    Toast.makeText(getApplicationContext(), "您输入的手机号码不存在", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "密码的设置不符合规范", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_LONG).show();
        }
    }

    //密码必须设置为8-16位字母和数字的组合
    public static boolean ispassword(String s) throws PatternSyntaxException {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();

    }
    public static boolean isPhone(String str) throws PatternSyntaxException { //检验手机号码
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
