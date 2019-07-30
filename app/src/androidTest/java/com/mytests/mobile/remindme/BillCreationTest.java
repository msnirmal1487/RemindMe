package com.mytests.mobile.remindme;


import android.Manifest;
import android.app.Activity;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.utilities.CacheDataManager;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

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
public class BillCreationTest {



    @Rule
    public ActivityTestRule<BillListActivity> billListActivityActivityTestRule =
            new ActivityTestRule<>(BillListActivity.class) ;

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void testCreateNewBill(){

        PaymentFrequency paymentFrequency = PaymentFrequency.MONTHLY ;
        Activity activity = billListActivityActivityTestRule.getActivity() ;

        // Round about way of doing "onView(withId(R.id.fab)).perform(click());"
//        ViewInteraction fabNewBill = onView(withId(R.id.fab)) ;
//        fabNewBill.perform(click());
        TakeScreenshot.takeScreenshot(activity, "App_Launch_Bill_List_Shown"); ;

        onView(withId(R.id.fab)).perform(click());

        TakeScreenshot.takeScreenshot(activity, "Bill_Shown") ;

        String testBillName = "Test Bill Name";
        String testBillNote = "Test Bill Note";
        PaymentFrequency testPaymentFrequency = PaymentFrequency.MONTHLY ;

        onView(withId(R.id.edittext_bill_name))
                .perform(typeText(testBillName));
        onView(withId(R.id.edittext_bill_name))
                .check(matches(withText(containsString(testBillName))));

        onView(withId(R.id.edittext_note))
                .perform(typeText(testBillNote), closeSoftKeyboard())
                .check(matches(withText(containsString(testBillNote))));

        onView(withId(R.id.spinner_frequency))
                .perform(click());
        onData(allOf(instanceOf(PaymentFrequency.class), equalTo(testPaymentFrequency)))
                .perform(click());
        onView(withId(R.id.spinner_frequency))
                .check(matches(withSpinnerText(containsString(testPaymentFrequency.getFrequency()))));

        TakeScreenshot.takeScreenshot(activity, "All_Bill_Details_Entered") ;
        pressBack();

        TakeScreenshot.takeScreenshot(activity, "Back_Pressed") ;

        BillInfoList billInfoList = CacheDataManager.getInstance().readBillListCache(activity);

        if (billInfoList != null && billInfoList.getBills() != null && billInfoList.getBills().size() > 0){
            int lastIndex = billInfoList.getBills().size() -1 ;
            BillInfo billInfo = billInfoList.getBills().get(lastIndex) ;

            assertEquals(testPaymentFrequency, billInfo.getPaymentFrequency());
            assertEquals(testBillName, billInfo.getBillName());
            assertEquals(testBillNote, billInfo.getNotes());
        }

        assertNotNull(billInfoList);

        TakeScreenshot.takeScreenshot(activity, "Application_Complete") ;
    }


}