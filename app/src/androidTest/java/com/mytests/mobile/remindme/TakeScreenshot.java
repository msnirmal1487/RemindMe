package com.mytests.mobile.remindme;

import android.app.Activity;

import com.jraska.falcon.FalconSpoon;
import com.squareup.spoon.Spoon;

public class TakeScreenshot {

    public static void takeScreenshot(Activity activity, String tag){

        // Falcon Screenshot not working in Android 9.0.
        // Un Comment below line if running in Android 9
//         Spoon.screenshot(activity, tag) ;
        // Comment below line if running in Android 9
        FalconSpoon.screenshot(activity, tag + "_falcon") ;
    }
}
