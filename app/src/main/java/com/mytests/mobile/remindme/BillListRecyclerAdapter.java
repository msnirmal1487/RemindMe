package com.mytests.mobile.remindme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytests.mobile.remindme.model.BillInfo;

import java.util.List;

import static com.mytests.mobile.remindme.BillListActivity.NOTE_INFO_INDEX;

public class BillListRecyclerAdapter extends RecyclerView.Adapter<BillListRecyclerAdapter.Viewholder> {

    private final Context context ;
    private final LayoutInflater layoutInflater;
    private final List<BillInfo> billInfoList ;

    public BillListRecyclerAdapter(Context context, List<BillInfo> billInfoList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.billInfoList = billInfoList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.item_bill_list, parent, false) ;
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        BillInfo billInfo = billInfoList.get(position) ;
        holder.txtBillName.setText(billInfo.getBillName());
        if(billInfo.getPaymentFrequency() != null){
            holder.txtBillFrequency.setText(billInfo.getPaymentFrequency().getFrequency());
        }
        holder.currentPosition = position ;
    }

    @Override
    public int getItemCount() {
        return billInfoList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        public final TextView txtBillName;
        public final TextView txtBillFrequency;
        public int currentPosition ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtBillName = (TextView) itemView.findViewById(R.id.bill_name);
            txtBillFrequency = (TextView) itemView.findViewById(R.id.bill_payment_frequency);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BillActivity.class);
                    intent.putExtra(NOTE_INFO_INDEX, currentPosition);
                    context.startActivity(intent);
                }
            });

        }
    }
}
