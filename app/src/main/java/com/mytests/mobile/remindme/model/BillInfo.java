package com.mytests.mobile.remindme.model;


import android.widget.ListView;

import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillInfo implements Serializable {

    private String billName ;
    private boolean active;
    private Date activeFrom ;

    private boolean autoPay ;
    private PaymentFrequency paymentFrequency ;
    private int tentativeDate;
    private String notes;

    public BillInfo(String billName, boolean active, Date activeFrom, boolean autoPay,
                    PaymentFrequency paymentFrequency, int tentativeDate, String notes) {
        this.billName = billName;
        this.active = active;
        this.activeFrom = activeFrom;
        this.autoPay = autoPay;
        this.paymentFrequency = paymentFrequency;
        setTentativeDate(tentativeDate);
        this.notes = notes;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }

    public PaymentFrequency getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(PaymentFrequency paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public int getTentativeDate() {
        return tentativeDate;
    }

    public void setTentativeDate(int tentativeDate) {
        if(tentativeDate > 31){
            tentativeDate = 31 ;
        }
        this.tentativeDate = tentativeDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "{" + billName +
                " | " + (isActive() ?"Active" : "Not Active") +
                "| since " + activeFrom +
                "| " + (isAutoPay() ? "Autopay" : "Manual Payment") +
                "| " + paymentFrequency +
                "| " + tentativeDate +
                "| " + notes +
                '}';
    }

    public static List<BillInfo> getDefaultTestBills(){
        List<BillInfo> bills = new ArrayList<>();
        bills.add(new BillInfo("US Internet", true,
                new Date(2019, 4, 12),
                true, PaymentFrequency.MONTHLY, 10, "Verizon"));
        bills.add(new BillInfo("Chennai Telephone & Internet", true,
                new Date(2015, 9, 1),
                false, PaymentFrequency.MONTHLY, 15, "BSNL"));

        return bills ;
    }
}
