package com.mytests.mobile.remindme.model;

import android.content.Context;

import com.mytests.mobile.remindme.utilities.CacheDataManager;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentInfo {

    private int billInfoIndex;
    private boolean paid;
    private Date paidDate ;
    private float billAmount ;

    public int getBillInfoIndex() {
        return billInfoIndex;
    }

    public void setBillInfoIndex(int billInfoIndex) {
        this.billInfoIndex = billInfoIndex;
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

    public PaymentInfo(int billInfoIndex, boolean paid) {
        this.billInfoIndex = billInfoIndex;
        this.paid = paid;
    }

    public PaymentInfo() {
    }

    public static List<PaymentInfo> getDefaultTestPayments(Context context){

        BillInfoList billInfoList = CacheDataManager.getInstance().readBillListCache(context) ;
        if (billInfoList == null
                || (billInfoList != null && billInfoList.getBills() == null)
                || (billInfoList != null && billInfoList.getBills() != null
                    && billInfoList.getBills().size() < 1)) {
            return null ;
        }

        List<PaymentInfo> payments = new ArrayList<>();
        payments.add(new PaymentInfo(0, false));

        if (billInfoList != null && billInfoList.getBills() != null
                && billInfoList.getBills().size() > 1){
            payments.add(new PaymentInfo(1, false));
        }

        return payments ;
    }
}
