package com.mytests.mobile.remindme.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.mytests.mobile.remindme.utilities.CacheDataManager;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentInfo implements Parcelable {

    private int billInfoIndex;
    private boolean paid;
    private Date paidDate ;
    private float billAmount ;
    private String paymentNotes ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(billInfoIndex);
        parcel.writeByte((byte) (paid?1:0));
        parcel.writeFloat(billAmount);
        String paidDate = "" ;
        if (getPaidDate() != null){
            paidDate = new SimpleDateFormat("yyyyMMdd").format(getPaidDate()) ;
        }

        parcel.writeString(paidDate);
        parcel.writeString(paymentNotes);
    }

    protected PaymentInfo(Parcel in) {
        billInfoIndex = in.readInt();
        paid = in.readByte() != 0;
        billAmount = in.readFloat();
        String paidDateString = in.readString() ;
        try {
            paidDate = new SimpleDateFormat("yyyyMMdd").parse(paidDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            paidDate = null ;
        }
        paymentNotes = in.readString() ;
    }

    public static final Creator<PaymentInfo> CREATOR = new Creator<PaymentInfo>() {
        @Override
        public PaymentInfo createFromParcel(Parcel in) {
            return new PaymentInfo(in);
        }

        @Override
        public PaymentInfo[] newArray(int size) {
            return new PaymentInfo[size];
        }
    };

    public PaymentInfo(PaymentInfo paymentInfo) {
        this.billInfoIndex = paymentInfo.billInfoIndex ;
        this.paid = paymentInfo.paid ;
        this.billAmount = paymentInfo.billAmount ;
        this.paidDate = paymentInfo.paidDate ;
        this.paymentNotes = paymentInfo.paymentNotes ;

    }

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
        if (paidDate == null){
            paidDate = new Date() ;
        }
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

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public PaymentInfo(int billInfoIndex, boolean paid) {
        this.billInfoIndex = billInfoIndex;
        this.paid = paid;
    }

    public PaymentInfo() {
        billInfoIndex = -1 ;
        paidDate = new Date() ;
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
