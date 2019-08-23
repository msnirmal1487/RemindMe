package com.mytests.mobile.remindme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.utilities.CacheDataManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BillListActivity extends AppCompatActivity {

    private List<BillInfo> bills ;
    private Context context;
//    private ArrayAdapter<BillInfo> adapterBills;
    private RecyclerView listBills;
    private BillListRecyclerAdapter billListRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this ;
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
        Toast.makeText(context, "App Launched", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBillList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_populate_bill_list_cache){
            populateBillListCache();
            loadBillList();
            return true;
        } else if (id == R.id.action_clear_bill_list_cache){
            clearBillListCache();
            loadBillList();
            return true ;
        } else if (id == R.id.action_read_bill_list_cache){
            readBillListCache();
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readBillListCache() {
        CacheDataManager.getInstance().readBillListCache(context);
    }

    private void clearBillListCache() {
        CacheDataManager.getInstance().clearBillListCache(context);
    }

    private void populateBillListCache() {

        CacheDataManager.getInstance().populateBillListCache(context);

    }

    private void initializeDisplayContent() {

        listBills = (RecyclerView) findViewById(R.id.list_bills);
        final LinearLayoutManager billsLayoutManager = new LinearLayoutManager(this);
        listBills.setLayoutManager(billsLayoutManager);
        loadBillList();
//        listBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                onBillClicked(position);
//            }
//        });
    }

    private void loadBillList() {

        BillInfoList billInfoList = CacheDataManager.getInstance().readBillListCache(context);

        if (billInfoList != null && billInfoList.getBills() != null
                && billInfoList.getBills().size() > 0){
            bills = new ArrayList<>() ;
            for (BillInfo billInfo: billInfoList.getBills()){
                if (billInfo != null) {
                    bills.add(billInfo);
                }
            }
        } else {
            bills = new ArrayList<>();
        }
        billListRecyclerAdapter = new BillListRecyclerAdapter(this, bills);
        listBills.setAdapter(billListRecyclerAdapter);

    }

    private void onBillClicked(int position){
        Intent intent = new Intent(BillListActivity.this, BillActivity.class);
        if (bills != null && bills.size() > position){
            intent.putExtra(MainActivity.BILL_INFO_INDEX, position);
        }
        startActivity(intent);
    }


}
