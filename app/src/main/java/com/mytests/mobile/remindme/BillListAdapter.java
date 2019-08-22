package com.mytests.mobile.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mytests.mobile.remindme.model.BillInfo;

import java.util.List;

public class BillListAdapter extends ArrayAdapter<BillInfo> {

    private Context context ;
    private List<BillInfo> billInfos ;

    public BillListAdapter(Context context, List<BillInfo> billInfos){
        super(context, 0, billInfos);

        this.context = context;
        this.billInfos = billInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getRowView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        return getRowView(position, convertView, parent);
    }

    private View getRowView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView ;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.payment_spinner_bill_list_item, parent, false);
        }

        BillInfo billInfo = billInfos.get(position) ;
        TextView frequency = (TextView) listItem.findViewById(R.id.bill_payment_frequency) ;
        TextView billName =  (TextView) listItem.findViewById(R.id.bill_payment_name) ;

        frequency.setText(billInfo.getPaymentFrequency().getFrequency());
        billName.setText(billInfo.getBillName());

        return listItem;
    }
}
