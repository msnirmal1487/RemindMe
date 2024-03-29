package com.mytests.mobile.remindme.utilities;

import java.util.ArrayList;
import java.util.List;

public enum PaymentFrequency {


    DIALY("Daily", 1, false),
    WEEKLY("Every Week",7, false),
    MONTHLY("Every Month", 1,true),
    BI_MONTHLY("Every 2 Months", 2,true),
    QUATERELY("Every 3 Months", 3,true),
    HALF_YEARLY("Every 6 Months", 6,true),
    YEARLY("Every Year", 12,true) ;

    private String frequency;
    private int interval;
    private boolean intervalInMonths ; // true - interval in Months; false - interval in days

    PaymentFrequency(String frequency, int interval, boolean intervalInMonths) {
        this.frequency = frequency;
        this.interval = interval;
        this.intervalInMonths = intervalInMonths;
    }

    @Override
    public String toString() {
        return frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isIntervalInMonths() {
        return intervalInMonths;
    }

    public static List<PaymentFrequency> getPaymentFrequencies(){

        List<PaymentFrequency> paymentFrequencyList = new ArrayList<>();

        for(PaymentFrequency paymentFrequency: PaymentFrequency.values()){
            paymentFrequencyList.add(paymentFrequency);
        }

        return paymentFrequencyList;
    }

    public static PaymentFrequency getPaymentFrequncy(String paymentFrequencyValue){
        if(paymentFrequencyValue != null){
            for(PaymentFrequency paymentFrequency: PaymentFrequency.values()){
                if(paymentFrequency.getFrequency().equalsIgnoreCase(paymentFrequencyValue)){
                    return paymentFrequency ;
                }
            }
        }
        return null;
    }
}
