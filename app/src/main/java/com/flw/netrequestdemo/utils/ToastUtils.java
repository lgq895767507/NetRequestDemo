package com.flw.netrequestdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lgq on 2016/12/9.
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, CharSequence str, int duration)
    {
        if (mToast == null) {
            mToast = Toast.makeText(context, str, duration);
        }
        else {
            mToast.setText(str);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}
