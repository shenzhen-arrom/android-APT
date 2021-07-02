package com.langyi.apt_api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Arrom
 * 日期： 7/2/21
 * 描述：
 */

public  interface IMyViewCreator {

    /**
     *   通过 new 的方式创建 View
     * @param name
     * @param context
     * @param attributeSet
     * @return
     */
    View createView(String name, Context context, AttributeSet attributeSet);
}
