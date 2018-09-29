package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/14.
 */

public class password extends Activity {
    String passwords1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        Intent intent = getIntent();
    }
    public void sure_password(View view){
        EditText password1;
        EditText password2;
        password1 = (EditText) findViewById(R.id.password_edit1);
        password2 = (EditText) findViewById(R.id.password_edit2);
        passwords1 = password1.getText().toString();
        String passwords2 = password2.getText().toString();
        if(passwords1.equals(passwords2))
        {
            if(registe.ispassword(passwords1)==true)
            {
                AVQuery<AVObject> query1= new AVQuery<>("persons");
                query1.selectKeys(Arrays.asList("account"));
                query1.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null) {
                            for (AVObject avObject : list) {
                                String account1 = avObject.getString("account");
                                if (MainActivity.accounts.equals(account1)) {
                                    String id = avObject.getObjectId();
                                    AVObject todo = AVObject.createWithoutData("persons", id);
                                    // 修改密码属性
                                    todo.put("password", passwords1);
                                    // 保存到云端
                                    todo.saveInBackground();
                                    Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        } else
                            Toast.makeText(getApplicationContext(), "网络断开啦", Toast.LENGTH_SHORT).show();
                    }
                });
            }
         else
             Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_SHORT).show();
        }
        else
             Toast.makeText(getApplicationContext(), "俩次输入不一致", Toast.LENGTH_SHORT).show();
    }
}