package com.mytests.mobile.remindme;

import android.Manifest;
import android.app.Activity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.utilities.CacheDataManager;

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
public class NextThroughBillsTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class) ;


    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);


    @Test
    public void testNextThroughBills(){

        Activity activity =mainActivityActivityTestRule.getActivity() ;

        TakeScreenshot.takeScreenshot(activity, "initial_screen");

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        TakeScreenshot.takeScreenshot(activity, "drawer_open");
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_bills));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TakeScreenshot.takeScreenshot(activity, "select_bills");
        int index = 0 ;
        onView(withId(R.id.payment_list))
                .perform(RecyclerViewActions.<BillListRecyclerAdapter.Viewholder>actionOnItemAtPosition(index, click()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        TakeScreenshot.takeScreenshot(activity, "bill_edit");

        BillInfoList billInfoList = CacheDataManager.getInstance().readBillListCache(activity) ;

        for (int i = index ; i < billInfoList.getBills().size(); i++){
            BillInfo billInfo = billInfoList.getBills().get(i);

            onView(withId(R.id.edittext_bill_name)).check(matches(withText(billInfo.getBillName()))) ;
            onView(withId(R.id.spinner_frequency)).check(matches(withSpinnerText(billInfo.getPaymentFrequency().getFrequency())));
            onView(withId(R.id.toggle_active)).check(matches(billInfo.isActive()?isChecked():isNotChecked()));
//            Failing test case
//            onView(withId(R.id.toggle_active)).check(matches(billInfo.isActive()?isNotChecked():isChecked()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i < billInfoList.getBills().size() -1){
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click());
            }
        }
        onView(withId(R.id.action_next)).check(matches(not(isEnabled()))) ;
        pressBack();

    }
}