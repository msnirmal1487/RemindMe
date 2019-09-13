package com.mytests.mobile.remindme.model;

import android.provider.BaseColumns;

public final class RemindMeDatabaseContract {

    private RemindMeDatabaseContract() { }

    public static final class BillInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "bill_info" ;

        public static final String COLUMN_BILL_NAME = "bill_name" ;
        public static final String COLUMN_ACTIVE = "active" ;
        public static final String COLUMN_ACTIVE_FROM = "active_from" ;
        public static final String COLUMN_AUTO_PAY = "auto_pay" ;
        public static final String COLUMN_PAYMENT_FREQUENCY = "payment_frequency" ;
        public static final String COLUMN_TENTATIVE_DATE = "tentative_date" ;
        public static final String COLUMN_NOTES = "notes" ;

        //CREATE TABLE bill_info (bill_name, active, active_from, auto_pay, payment_frequency, tentative_date, notes)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_BILL_NAME + " TEXT NOT NULL, " +
                        COLUMN_ACTIVE + " NUMERIC NOT NULL, " +
                        COLUMN_ACTIVE_FROM + " TEXT NOT NULL, " +
                        COLUMN_AUTO_PAY + " NUMERIC NOT NULL, " +
                        COLUMN_PAYMENT_FREQUENCY + " NUMERIC NOT NULL, " +
                        COLUMN_TENTATIVE_DATE + " TEXT NOT NULL, " +
                        COLUMN_NOTES + ")" ;


    }

    public static final class PaymentInfoEntry implements BaseColumns{

        public static final String TABLE_NAME = "payment_info" ;
        public static final String COLUMN_BILL_INFO_INDEX = "bill_info_index" ;
        public static final String COLUMN_PAID = "paid" ;
        public static final String COLUMN_PAID_DATE = "paid_date" ;
        public static final String COLUMN_BILL_AMOUNT = "bill_amount" ;
        public static final String COLUMN_PAYMENT_NOTES = "payment_notes" ;

        //CREATE TABLE payment_info (bill_info_index, paid, paid_date, bill_amount, payment_notes)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_BILL_INFO_INDEX + " NUMERIC NOT NULL, " +
                        COLUMN_PAID + " NUMERIC NOT NULL, " +
                        COLUMN_PAID_DATE + " TEXT NOT NULL, " +
                        COLUMN_BILL_AMOUNT + " REAL NOT NULL, " +
                        COLUMN_PAYMENT_NOTES + ")" ;
    }
}

