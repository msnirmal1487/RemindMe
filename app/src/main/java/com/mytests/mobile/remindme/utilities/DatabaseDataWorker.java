package com.mytests.mobile.remindme.utilities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.PaymentInfo;
import com.mytests.mobile.remindme.model.RemindMeDatabaseContract;

import java.text.SimpleDateFormat;

import static com.mytests.mobile.remindme.model.RemindMeDatabaseContract.BillInfoEntry.*;

public class DatabaseDataWorker {

    private SQLiteDatabase db ;

    public DatabaseDataWorker(SQLiteDatabase db) {
        this.db = db;
    }

    private void insertBill(BillInfo billInfo){
        ContentValues values = new ContentValues() ;
        values.put(COLUMN_BILL_NAME, billInfo.getBillName());
        values.put(COLUMN_ACTIVE, billInfo.isActive()?1:0);
        values.put(COLUMN_ACTIVE_FROM, new SimpleDateFormat("yyyyMMdd_HHmmss").format(billInfo.getActiveFrom()));
        values.put(COLUMN_AUTO_PAY, billInfo.isAutoPay()?1:0);
        values.put(COLUMN_PAYMENT_FREQUENCY, billInfo.getPaymentFrequency().getFrequency());
        values.put(COLUMN_TENTATIVE_DATE, billInfo.getTentativeDate());
        values.put(COLUMN_NOTES, billInfo.getNotes());

        long newRowId = db.insert(RemindMeDatabaseContract.BillInfoEntry.TABLE_NAME, null, values);
    }

    private void insertPayment(PaymentInfo paymentInfo){
        ContentValues values = new ContentValues() ;
        values.put(COLUMN_BILL_NAME, paymentInfo.getBillName());
        values.put(COLUMN_ACTIVE, paymentInfo.isActive()?1:0);
        values.put(COLUMN_ACTIVE_FROM, new SimpleDateFormat("yyyyMMdd_HHmmss").format(paymentInfo.getActiveFrom()));
        values.put(COLUMN_AUTO_PAY, paymentInfo.isAutoPay()?1:0);
        values.put(COLUMN_PAYMENT_FREQUENCY, paymentInfo.getPaymentFrequency().getFrequency());
        values.put(COLUMN_TENTATIVE_DATE, paymentInfo.getTentativeDate());
        values.put(COLUMN_NOTES, paymentInfo.getNotes());

        long newRowId = db.insert(RemindMeDatabaseContract.PaymentInfoEntry.TABLE_NAME, null, values);
    }

}
