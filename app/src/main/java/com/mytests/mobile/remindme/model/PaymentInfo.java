package com.mytests.mobile.remindme.model;

import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentInfo {

    private BillInfo billInfo ;
    private boolean paid;
    private Date paidDate ;
    private float billAmount ;


    public BillInfo getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    public PaymentInfo(BillInfo billInfo, boolean paid) {
        this.billInfo = billInfo;
        this.paid = paid;
    }

    public static List<PaymentInfo> getDefaultTestPayments(){
        List<PaymentInfo> payments = new ArrayList<>();
        try {
            payments.add(new PaymentInfo(new BillInfo("US Internet", true,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").parse("20190412_090000"),
                    true, PaymentFrequency.MONTHLY, 10, "Verizon"), false));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            payments.add(new PaymentInfo(new BillInfo("Chennai Telephone & Internet", true,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").parse("20150901_090000"),
                    false, PaymentFrequency.MONTHLY, 15, "BSNL"), true));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return payments ;
    }
}
