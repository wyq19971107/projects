package com.example.administrator.myapp;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2018/3/27.
 */

public class personApplication extends Application
{
    public void onCreate()
   {
       super.onCreate();
       AVOSCloud.initialize(this,"NDoS8ilJr8HP3GCatWSxciT4-gzGzoHsz","Et0h9nU2VCwSz3VCTQOhziw4");
   }
}
