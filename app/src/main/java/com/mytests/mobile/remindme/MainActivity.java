package com.mytests.mobile.remindme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.model.PaymentInfo;
import com.mytests.mobile.remindme.utilities.CacheDataManager;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<BillInfo> bills ;
    private Context context;
    private RecyclerView listBills;
    private BillListRecyclerAdapter billListRecyclerAdapter;
    private GridLayoutManager billsLayoutManager;
    private List<PaymentInfo> paymentInfos;
    private PaymentListRecyclerAdapter paymentListRecyclerAdapter;
    private LinearLayoutManager paymentsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this ;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BillActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        initializeDisplayContent();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payments) {
            displayPaymentList();
        } else if (id == R.id.nav_bills) {
            displayBillList();
        } else if (id == R.id.nav_share) {
            handleSelection("Share");
        } else if (id == R.id.nav_send) {
            handleSelection("Send");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleSelection(String message) {
        Snackbar.make(findViewById(R.id.payment_list), message, Snackbar.LENGTH_SHORT).show();
    }

    private void initializeDisplayContent() {
        listBills = (RecyclerView) findViewById(R.id.payment_list);
        paymentsLayoutManager = new LinearLayoutManager(this);
        billsLayoutManager = new GridLayoutManager(this, 2);

        loadBilList();
        billListRecyclerAdapter = new BillListRecyclerAdapter(this, bills);

        loadPaymentList();
        paymentListRecyclerAdapter = new PaymentListRecyclerAdapter(this, paymentInfos);

        displayPaymentList();
    }

    private void displayBillList() {

        listBills.setLayoutManager(billsLayoutManager);
        listBills.setAdapter(billListRecyclerAdapter);

        selectNavigationMenuItem(R.id.nav_bills);

    }

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    private void displayPaymentList() {

        listBills.setLayoutManager(paymentsLayoutManager);
        listBills.setAdapter(paymentListRecyclerAdapter);

        selectNavigationMenuItem(R.id.nav_payments);

    }

    private void loadBilList() {
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
    }

    private void loadPaymentList() {

        paymentInfos = PaymentInfo.getDefaultTestPayments();

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayBillList();
    }
}
