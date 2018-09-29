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
 * Created by Administrator on 2018/3/31.
 */
public class forhelp extends Activity {
    public static String cards;
    String place1;
    int flagcard = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_help);
        Intent intent = getIntent();
    }

    public void sure_forhelp(View view) {
        EditText card;
        card = (EditText) findViewById(R.id.forhelp_edit1);
        cards = card.getText().toString();

        AVQuery<AVObject> query1 = new AVQuery<>("cardPick");
        query1.selectKeys(Arrays.asList("card", "place"));
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject avObject : list) {
                        String card1 = avObject.getString("card");
                         place1 = avObject.getString("place");
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
        if (flagcard == 2)    //还没有人捡到
        {
            Toast.makeText(getApplicationContext(), "您的校园卡暂时还没有人捡到，请稍后再查询", Toast.LENGTH_SHORT).show();
        }
        else if(flagcard==1)
        {
            Intent intent = new Intent(this, seek.class);
            String message="您的校园卡已被好心人放置于"+place1+",请尽快领取！";
            intent.putExtra("EXTRA_MESSAGE",message);
            startActivity(intent);
        }
    }
}

