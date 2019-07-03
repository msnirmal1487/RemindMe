package com.mytests.mobile.remindme;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class BillActivity extends AppCompatActivity {

    private EditText edittextBillName ;
    private EditText editTextNote ;
    private Switch switchAutoPay ;
    private Spinner spinnerFrequency ;
    private EditText edittextTentativeDate ;
    private TextView textviewHistory ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edittextBillName = (EditText) findViewById(R.id.edittext_bill_name) ;
        editTextNote = (EditText) findViewById(R.id.edittext_note) ;
        switchAutoPay = (Switch) findViewById(R.id.switch_autopay) ;
        spinnerFrequency = (Spinner) findViewById(R.id.spinner_frequency) ;
        edittextTentativeDate = (EditText) findViewById(R.id.edittext_tentative_date) ;
        textviewHistory = (TextView) findViewById(R.id.textView_history) ;

        List<PaymentFrequency> paymentFrequency = PaymentFrequency.getPaymentFrequencies() ;

        ArrayAdapter<PaymentFrequency> adapterPaymentFrequency = new ArrayAdapter<PaymentFrequency>(this, android.R.layout.simple_spinner_item, paymentFrequency);
        adapterPaymentFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterPaymentFrequency);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
