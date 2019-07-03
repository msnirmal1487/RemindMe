package com.mytests.mobile.remindme;

import java.util.ArrayList;
import java.util.List;

public class PaymentFrequency {

    private String frequency;

    public PaymentFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return frequency;
    }

    public static List<PaymentFrequency> getPaymentFrequencies(){

        List<PaymentFrequency> paymentFrequency = new ArrayList<>();
        paymentFrequency.add(new PaymentFrequency("Daily"));
        paymentFrequency.add(new PaymentFrequency("Weekly"));
        paymentFrequency.add(new PaymentFrequency("Monthly"));
        paymentFrequency.add(new PaymentFrequency("Bi-Monthly"));
        paymentFrequency.add(new PaymentFrequency("Quarterly"));
        paymentFrequency.add(new PaymentFrequency("Half-yearly"));
        paymentFrequency.add(new PaymentFrequency("Yearly"));

        return paymentFrequency;
    }
}
