package com.mytests.mobile.remindme.utilities;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PaymentFrequencyTest {

    @Test
    public void getPaymentFrequncy(){

        List<PaymentFrequency> paymentFrequencyList = PaymentFrequency.getPaymentFrequencies() ;

        assertNotNull(paymentFrequencyList);

        for (PaymentFrequency paymentFrequency: paymentFrequencyList){
            assertEquals(paymentFrequency, PaymentFrequency.getPaymentFrequncy(paymentFrequency.getFrequency()));
        }

    }
}