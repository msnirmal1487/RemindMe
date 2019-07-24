package com.mytests.mobile.remindme.utilities;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PaymentFrequencyTest {

    @Test
    public void getPaymentFrequencies() {

    }

    @Test
    public void getPaymentFrequncy(){
        PaymentFrequency monthlyPaymentFrequency = PaymentFrequency.getPaymentFrequncy("Monthly") ;

        assertEquals(PaymentFrequency.MONTHLY, monthlyPaymentFrequency);
        assertEquals(PaymentFrequency.BI_MONTHLY, monthlyPaymentFrequency);
    }
}