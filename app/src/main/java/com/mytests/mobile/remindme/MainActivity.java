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
import com.mytests.mobile.remindme.model.PaymentInfoList;
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

    public static final String PAYMENT_LIST = "PAYMENT_LIST";
    public static final String BILLS_LIST = "BILLS_LIST";
    public static final String INVALID = "INVALID";
    public static final String BILL_INFO = "com.mytests.mobile.remindme.BILL_INFO" ;
    public static final String BILL_INFO_INDEX = "com.mytests.mobile.remindme.BILL_INFO_INDEX" ;
    public static final String PAYMENT_INFO_INDEX = "com.mytests.mobile.remindme.PAYMENT_INFO_INDEX" ;
    public static final int POSITION_NOT_SET = -1;
    private List<BillInfo> bills ;
    private Context context;
    private RecyclerView listBills;
    private BillListRecyclerAdapter billListRecyclerAdapter;
    private GridLayoutManager billsLayoutManager;
    private List<PaymentInfo> paymentInfos;
    private PaymentListRecyclerAdapter paymentListRecyclerAdapter;
    private LinearLayoutManager paymentsLayoutManager;
    private NavigationView navigationView;

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
                onFabClicked();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_populate_bill_list_cache){
            populateBillListCache();
            return true;
        } else if (id == R.id.action_clear_bill_list_cache){
            clearBillListCache();
            return true ;
        } else if (id == R.id.action_read_bill_list_cache){
            readBillListCache();
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readBillListCache() {

        String currentScreen = getCurrentVisibleScreen();

        if(PAYMENT_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().readPaymentListCache(context);
            loadPaymentList();
            displayPaymentList();
        } else if (BILLS_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().readBillListCache(context);
            loadBillList();
            displayBillList();
        }
    }

    private void clearBillListCache() {

        String currentScreen = getCurrentVisibleScreen();

        if(PAYMENT_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().clearPaymentListCache(context);
            loadPaymentList();
            displayPaymentList();
        } else if (BILLS_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().clearBillListCache(context);
            loadBillList();
            displayBillList();
        }
    }

    private void populateBillListCache() {

        String currentScreen = getCurrentVisibleScreen();

        if(PAYMENT_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().populatePaymentListCache(context);
            loadPaymentList();
            displayPaymentList();
        } else if (BILLS_LIST.equalsIgnoreCase(currentScreen)){
            CacheDataManager.getInstance().populateBillListCache(context);
            loadBillList();
            displayBillList();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payments) {
            loadPaymentList();
            displayPaymentList();
        } else if (id == R.id.nav_bills) {
            loadBillList();
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

        loadBillList();
        loadPaymentList();
        displayPaymentList();
    }

    private void displayBillList() {

        listBills.setLayoutManager(billsLayoutManager);
        listBills.setAdapter(billListRecyclerAdapter);
        billListRecyclerAdapter.notifyDataSetChanged();
        selectNavigationMenuItem(R.id.nav_bills);

    }

    private void displayPaymentList() {

        listBills.setLayoutManager(paymentsLayoutManager);
        listBills.setAdapter(paymentListRecyclerAdapter);
        paymentListRecyclerAdapter.notifyDataSetChanged();
        selectNavigationMenuItem(R.id.nav_payments);

    }

    private void selectNavigationMenuItem(int id) {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    private void onFabClicked() {

        String currentScreen = getCurrentVisibleScreen();

        if(PAYMENT_LIST.equalsIgnoreCase(currentScreen)){
            startActivity(new Intent(MainActivity.this, PaymentActivity.class));
        } else if (BILLS_LIST.equalsIgnoreCase(currentScreen)){
            startActivity(new Intent(MainActivity.this, BillActivity.class));
        }
    }

    private String getCurrentVisibleScreen() {
        if(navigationView == null){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
        }
        if (navigationView != null){
            Menu menu = navigationView.getMenu();
            if (menu.findItem(R.id.nav_payments).isChecked()){
                return PAYMENT_LIST;
            } else if (menu.findItem(R.id.nav_bills).isChecked()){
                return BILLS_LIST;
            }
        }
        return INVALID;
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
    }

    private void loadPaymentList() {

        PaymentInfoList paymentInfoList = CacheDataManager.getInstance().readPaymentListCache(this);
        if (paymentInfoList != null && paymentInfoList.getPayments() != null
                && paymentInfoList.getPayments().size() > 0){
            paymentInfos = new ArrayList<>();
            for(PaymentInfo paymentInfo : paymentInfoList.getPayments()){
                if(paymentInfo != null){
                    paymentInfos.add(paymentInfo);
                }
            }
        } else {
            paymentInfos = new ArrayList<>() ;
        }
        paymentListRecyclerAdapter = new PaymentListRecyclerAdapter(this, paymentInfos);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String currentScreen = getCurrentVisibleScreen();

        if(PAYMENT_LIST.equalsIgnoreCase(currentScreen)){
            loadPaymentList();
            displayPaymentList();
        } else if (BILLS_LIST.equalsIgnoreCase(currentScreen)){
            loadBillList();
            displayBillList();
        }
    }
}
