package com.mytests.mobile.remindme;


import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.utilities.CacheDataManager;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.* ;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.* ;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.*;


@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {



    @Rule
    public ActivityTestRule<BillListActivity> billListActivityActivityTestRule =
            new ActivityTestRule<>(BillListActivity.class) ;

    @Test
    public void createNewNote(){

        PaymentFrequency paymentFrequency = PaymentFrequency.MONTHLY ;

//        ViewInteraction fabNewBill = onView(withId(R.id.fab)) ;
//        fabNewBill.perform(click());
        onView(withId(R.id.fab)).perform(click());

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

        pressBack();

        BillInfoList billInfoList = CacheDataManager.getInstance().readBillListCache(billListActivityActivityTestRule.getActivity());

        if (billInfoList != null && billInfoList.getBills() != null && billInfoList.getBills().size() > 0){
            int lastIndex = billInfoList.getBills().size() -1 ;
            BillInfo billInfo = billInfoList.getBills().get(lastIndex) ;

            assertEquals(testPaymentFrequency, billInfo.getPaymentFrequency());
            assertEquals(testBillName, billInfo.getBillName());
            assertEquals(testBillNote, billInfo.getNotes());
        }
    }

}