package com.example.administrator.myapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
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

/**
 * Created by Administrator on 2018/3/31.
 */
public class tohelp extends Activity {
    public static String places;
    String cards;
    int flagcard = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_help);
        Intent intent = getIntent();
    }

    public void rbClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rb1:
                places = "五谷";
                break;
            case R.id.rb2:
                places = "三味";
                break;
            case R.id.rb3:
                places = "一粟";
                break;
            default:
                places = null;
                break;
        }
    }

    public void sure_tohelp(View view) {
        EditText card;
        card = (EditText) findViewById(R.id.tohelp_edit1);
        cards = card.getText().toString();
        if (places == null) {
            Toast.makeText(getApplicationContext(), "请选择一个放置点", Toast.LENGTH_LONG).show();
        } else {
            AVQuery<AVObject> query1 = new AVQuery<>("cardPick");
            query1.selectKeys(Arrays.asList("card"));
            query1.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        for (AVObject avObject : list) {
                            String card1 = avObject.getString("card");
                            if (card1.equals(cards)) {
                                flagcard = 1;
                                break;
                            } else
                                flagcard = 2;
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "网络断开啦", Toast.LENGTH_SHORT).show();
                }
            });
            if (flagcard == 2) {
                try {
                    MainActivity.cardPick.put("card", cards);
                    MainActivity.cardPick.put("place", places);
                    MainActivity.cardPick.saveInBackground();
                    //Toast:是一个类，主要管理消息的提示。
                    Toast.makeText(getApplicationContext(), "提交成功，感谢您的帮助", Toast.LENGTH_LONG).show();
                } catch (Exception exc) {
                    Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            else if(flagcard==1)
            {
                Toast.makeText(getApplicationContext(), "确定输入了正确的卡号吗？", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void send(String phone, String message){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, tohelp.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, message, pi, null);
    }
}



