package com.langyi.apt_api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Arrom
 * 日期： 7/2/21
 * 描述：
 */

public class MyViewCreatorDelegate implements IMyViewCreator{

    private IMyViewCreator mIMyViewCreator;

    @SuppressWarnings("all")
    private MyViewCreatorDelegate(){
        try{
            Class mClass = Class.forName("com.langyi.aptdemo.MyViewCreatorImpl");
            mIMyViewCreator = (IMyViewCreator) mClass.newInstance();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public static MyViewCreatorDelegate getInstance(){
        return  Holder.MY_VIEW_CREATOR_DELEGATE;
    }

    private static final class Holder {
        private static final MyViewCreatorDelegate MY_VIEW_CREATOR_DELEGATE = new MyViewCreatorDelegate();
    }


    @Override
    public View createView(String name, Context context, AttributeSet attributeSet) {
        if(mIMyViewCreator!=null){
            return mIMyViewCreator.createView(name,context,attributeSet);
        }
        return null;
    }
}
