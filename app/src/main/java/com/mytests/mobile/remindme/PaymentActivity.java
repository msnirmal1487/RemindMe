package com.mytests.mobile.remindme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.utilities.CacheDataManager;

import java.util.Calendar;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private boolean isCancelling = false;
    private Context context;
    private TextView txtDateView;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private Spinner spinnerBills;
    private EditText edttextPaymentNotes;
    private EditText edttextBillAmount;
    private ToggleButton toggleIsPaid;
    private List<BillInfo> billInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this ;
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerBills = (Spinner) findViewById(R.id.spinner_bill);
        edttextPaymentNotes = (EditText) findViewById(R.id.txt_notes);
        txtDateView = (TextView) findViewById(R.id.txt_paid_date) ;
        edttextBillAmount = (EditText) findViewById(R.id.txt_bill_amount);
        toggleIsPaid = (ToggleButton) findViewById(R.id.toggle_is_paid);

        billInfos = CacheDataManager.getInstance().getBillListFromCache(context);

        BillListAdapter billListAdapter = new BillListAdapter(context, billInfos) ;
        spinnerBills.setAdapter(billListAdapter);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        txtDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });

    }

    private void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if(id == 999){
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }

        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        txtDateView.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill_payment, menu);
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
//            sendMail();
            return true;
        } else if(id == R.id.action_cancel){
            isCancelling = true;
            finish();
        } else if (id == R.id.action_next){
//            moveNext();
        }

        return super.onOptionsItemSelected(item);
    }

}
