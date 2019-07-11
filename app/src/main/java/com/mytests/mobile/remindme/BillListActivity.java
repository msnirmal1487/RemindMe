package com.mytests.mobile.remindme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillListActivity extends AppCompatActivity {

    public static final String NOTE_INFO = "com.mytests.mobile.remindme.NOTE_INFO" ;
    public static final String NOTE_INFO_INDEX = "com.mytests.mobile.remindme.NOTE_INFO_INDEX" ;
    List<BillInfo> bills ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillListActivity.this, BillActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {

        ListView listBills = (ListView) findViewById(R.id.list_bills) ;

        bills = BillInfo.getDefaultTestBills();

        ArrayAdapter<BillInfo> adapterBills = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);

        listBills.setAdapter(adapterBills);
        listBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                onBillClicked(position);
            }
        });
    }

    private void onBillClicked(int position){
        Intent intent = new Intent(BillListActivity.this, BillActivity.class);
        if (bills != null && bills.size() > position){
            intent.putExtra(NOTE_INFO_INDEX, position);
        }
        startActivity(intent);
    }


}
