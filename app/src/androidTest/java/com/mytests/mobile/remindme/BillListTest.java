package com.mytests.mobile.remindme;

import android.Manifest;
import android.app.Activity;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.mytests.mobile.remindme.model.BillInfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.* ;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.* ;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class BillListTest {

    @Rule
    public ActivityTestRule<BillListActivity> billListActivityActivityTestRule =
            new ActivityTestRule<>(BillListActivity.class) ;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void testViewBillList(){

        Activity activity = billListActivityActivityTestRule.getActivity() ;

        TakeScreenshot.takeScreenshot(activity, "App_Launch_Bill_List_Shown");



        onData(allOf(instanceOf(BillInfo.class)))
                .inAdapterView(withId(R.id.list_bills))
                .atPosition(1).perform(click());

        TakeScreenshot.takeScreenshot(activity, "Bill_At_Position_1_Selected");

    }
}
