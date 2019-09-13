package com.mytests.mobile.remindme.utilities;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mytests.mobile.remindme.model.RemindMeDatabaseContract;

public class RemindMeDbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RemindMe.db" ;
    public static final int DATABASE_VERSION = 1 ;

    public RemindMeDbOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(RemindMeDatabaseContract.BillInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(RemindMeDatabaseContract.PaymentInfoEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
