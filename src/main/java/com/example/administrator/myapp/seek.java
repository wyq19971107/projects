package com.example.administrator.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public class seek extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seek_main);
        Intent intent = getIntent();
        String message=intent.getStringExtra("EXTRA_MESSAGE");
        TextView textView=(TextView)findViewById(R.id.seek_text1);
        textView.setText(message);//设置文本为message
    }

    public void quit(View view) {

        AVQuery<AVObject> query1 = new AVQuery<>("cardPick");
        query1.selectKeys(Arrays.asList("card"));
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject avObject : list) {
                        String card1 = avObject.getString("card");
                        if (card1.equals(forhelp.cards)) {
                            avObject.deleteInBackground();
                            Toast.makeText(getApplicationContext(), "取消成功，感谢您的使用！", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "网络断开啦", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

