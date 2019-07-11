package com.mytests.mobile.remindme;

import android.content.Intent;
import android.os.Bundle;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import static com.mytests.mobile.remindme.BillListActivity.NOTE_INFO;
import static com.mytests.mobile.remindme.BillListActivity.NOTE_INFO_INDEX;

public class BillActivity extends AppCompatActivity {

    public static final int POSITION_NOT_SET = -1;
    private EditText edittextBillName ;
    private EditText editTextNote ;
    private Switch switchAutoPay ;
    private ToggleButton toggleActive;
    private Spinner spinnerFrequency ;
    private EditText edittextTentativeDate ;
    private TextView textviewHistory ;

    List<PaymentFrequency> paymentFrequency ;
    private BillInfo billInfo;
    private boolean createNewBill = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edittextBillName = (EditText) findViewById(R.id.edittext_bill_name) ;
        editTextNote = (EditText) findViewById(R.id.edittext_note) ;
        switchAutoPay = (Switch) findViewById(R.id.switch_autopay) ;
        toggleActive = (ToggleButton) findViewById(R.id.toggle_active);
        spinnerFrequency = (Spinner) findViewById(R.id.spinner_frequency) ;
        edittextTentativeDate = (EditText) findViewById(R.id.edittext_tentative_date) ;
        textviewHistory = (TextView) findViewById(R.id.textView_history) ;

        paymentFrequency = PaymentFrequency.getPaymentFrequencies() ;

        ArrayAdapter<PaymentFrequency> adapterPaymentFrequency = new ArrayAdapter<PaymentFrequency>(this, android.R.layout.simple_spinner_item, paymentFrequency);
        adapterPaymentFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterPaymentFrequency);

        readBillInfo();

        if (!createNewBill){
            populateBillDetails(billInfo);
        }

    }

    private void readBillInfo() {
        Intent intent = this.getIntent();
        int index = intent.getIntExtra(NOTE_INFO_INDEX, POSITION_NOT_SET);
        if (index > POSITION_NOT_SET){

            List<BillInfo> bills = BillInfo.getDefaultTestBills();
            if (bills != null && bills.size() > index){
                billInfo = bills.get(index);
                createNewBill = false;
                return;
            }
        }
        createNewBill = true ;
    }

    private void populateBillDetails(final BillInfo billInfo) {

        edittextBillName.setText(billInfo.getBillName());
        editTextNote.setText(billInfo.getNotes());
        toggleActive.setChecked(billInfo.isActive());
        switchAutoPay.setChecked(billInfo.isAutoPay());
        edittextTentativeDate.setText(Integer.toString(billInfo.getTentativeDate()));


        spinnerFrequency.post(new Runnable() {
            @Override
            public void run() {
                if (paymentFrequency != null && billInfo.getPaymentFrequency() != null){
                    for(int i = 0; i < paymentFrequency.size(); i++){
                        if(billInfo.getPaymentFrequency().equals(paymentFrequency.get(i))){
                            spinnerFrequency.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });


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
        if (id == R.id.action_send_mail) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
