package com.mytests.mobile.remindme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mytests.mobile.remindme.model.PaymentInfo;

import java.util.List;

import static com.mytests.mobile.remindme.MainActivity.PAYMENT_INFO_INDEX;

public class PaymentListRecyclerAdapter extends RecyclerView.Adapter<PaymentListRecyclerAdapter.Viewholder> {

    private final Context context ;
    private final LayoutInflater layoutInflater;
    private final List<PaymentInfo> paymentInfoList;

    public PaymentListRecyclerAdapter(Context context, List<PaymentInfo> paymentInfoList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.paymentInfoList = paymentInfoList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = layoutInflater.inflate(R.layout.item_payment_list, parent, false) ;
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        PaymentInfo paymentInfo = paymentInfoList.get(position) ;
//        holder.txtBillName.setText(paymentInfo.getBillInfoIndex().getBillName());
        holder.currentPosition = position ;
    }

    @Override
    public int getItemCount() {
        if (paymentInfoList == null){
            return 0 ;
        }
        return paymentInfoList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        public final TextView txtBillName;
        public int currentPosition ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtBillName = (TextView) itemView.findViewById(R.id.txt_payments_bill_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PaymentActivity.class) ;
                    intent.putExtra(PAYMENT_INFO_INDEX, currentPosition) ;
                    context.startActivity(intent);
                }
            });

        }
    }
}
