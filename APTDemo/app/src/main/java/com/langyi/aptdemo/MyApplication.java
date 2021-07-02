package com.langyi.aptdemo;

import android.app.Application;

import com.langyi.apt_annotation.ViewCreator;

/**
 * 作者：Arrom
 * 日期： 7/2/21
 * 描述：
 */

@ViewCreator
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
