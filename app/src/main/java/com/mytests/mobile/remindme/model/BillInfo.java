package com.mytests.mobile.remindme.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillInfo implements Parcelable {

    private String billName ;
    private boolean active;
    private Date activeFrom ;
    private boolean autoPay ;
    private PaymentFrequency paymentFrequency ;
    private int tentativeDate;
    private String notes;

    private BillInfo(Parcel parcel) {
        billName = parcel.readString() ;
        active = false ;
        if(parcel.readInt() == 1){
            active = true ;
        }
        String activeFromString = parcel.readString() ;

        try {
            activeFrom = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(activeFromString);
        } catch (ParseException e) {
            e.printStackTrace();
            activeFrom = new Date();
        }
        autoPay = false;
        if(parcel.readInt() == 1){
            autoPay = true ;
        }
        paymentFrequency = PaymentFrequency.getPaymentFrequncy(parcel.readString());
        tentativeDate = parcel.readInt() ;
        notes = parcel.readString() ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(billName);
        parcel.writeByte((byte) (active?1:0));
        String activeFromString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(activeFrom);
        parcel.writeString(String.valueOf(activeFromString));
        parcel.writeByte((byte) (autoPay?1:0));
        parcel.writeString(paymentFrequency.getFrequency());
        parcel.writeInt(tentativeDate);
        parcel.writeString(notes);
    }

    public final static Parcelable.Creator<BillInfo> CREATOR =
            new Creator<BillInfo>() {
                @Override
                public BillInfo createFromParcel(Parcel parcel) {
                    return new BillInfo(parcel);
                }

                @Override
                public BillInfo[] newArray(int size) {
                    return new BillInfo[size];
                }
            } ;


    public BillInfo() {
    }

    public BillInfo(BillInfo billInfo){
        this.billName = billInfo.getBillName();
        this.active = billInfo.isActive();
        this.activeFrom = billInfo.getActiveFrom() ;
        this.autoPay = billInfo.isAutoPay();
        this.paymentFrequency = billInfo.getPaymentFrequency();
        setTentativeDate(billInfo.getTentativeDate());
        this.notes = billInfo.getNotes();
    }

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
        try {
            bills.add(new BillInfo("US Internet", true,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").parse("20190412_090000"),
                    true, PaymentFrequency.MONTHLY, 10, "Verizon"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bills.add(new BillInfo("Chennai Telephone & Internet", true,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").parse("20150901_090000"),
                    false, PaymentFrequency.MONTHLY, 15, "BSNL"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return bills ;
    }
}
