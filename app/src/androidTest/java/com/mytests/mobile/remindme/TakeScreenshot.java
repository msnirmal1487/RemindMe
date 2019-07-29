package com.mytests.mobile.remindme;

import android.app.Activity;
import android.os.Build;

import com.jraska.falcon.FalconSpoon;
import com.squareup.spoon.Spoon;

public class TakeScreenshot {

    public static void takeScreenshot(Activity activity, String tag){

        // Falcon Screenshot not working in Android 9.0.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            Spoon.screenshot(activity, tag) ;
        } else {
            FalconSpoon.screenshot(activity, tag) ;
        }
    }
}
