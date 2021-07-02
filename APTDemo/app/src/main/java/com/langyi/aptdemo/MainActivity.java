package com.langyi.aptdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.langyi.apt_annotation.AptAnnotation;
import com.langyi.apt_api.MyViewCreatorDelegate;

@AptAnnotation(desc = "我是 MainActivity 上面的注解")
public class MainActivity extends AppCompatActivity {

    @AptAnnotation(desc = "我是 onCreate 上面的注解")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = MyViewCreatorDelegate.getInstance().createView(name,context,attrs);
        if(view!=null){
            return view;
        }
        return super.onCreateView(name, context, attrs);
    }
}