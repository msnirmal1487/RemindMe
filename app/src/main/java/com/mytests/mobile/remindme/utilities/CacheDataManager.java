package com.mytests.mobile.remindme.utilities;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;
import com.mytests.mobile.remindme.model.PaymentInfo;
import com.mytests.mobile.remindme.model.PaymentInfoList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheDataManager {

    private static volatile  CacheDataManager instance;
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static final String BILL_LIST_CACHE_FILENAME = "bill_list.json";
    public static final String PAYMENT_LIST_CACHE_FILENAME = "payment_list.json";

    private CacheDataManager() {
        if(instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static CacheDataManager getInstance() {

        if(instance == null){
            synchronized ((CacheDataManager.class)){
                if(instance == null){
                    instance = new CacheDataManager();
                }
            }
        }
        return instance;
    }


    private void writeBillListCache(Context context, List<BillInfo> bills){

        File cacheDirectory = context.getFilesDir();
        File file = new File(cacheDirectory, BILL_LIST_CACHE_FILENAME);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BillInfoList billInfoList = new BillInfoList();
        billInfoList.setBills(bills);

        if(file.exists()){
            try {
                mapper.writeValue(file, billInfoList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void populateBillListCache(Context context) {

        writeBillListCache(context, BillInfo.getDefaultTestBills());

    }

    public void clearBillListCache(Context context) {
        clearPaymentListCache(context);
        writeBillListCache(context, new ArrayList<BillInfo>());
    }

    public List<BillInfo> getBillListFromCache(Context context){

        BillInfoList billInfoList = readBillListCache(context) ;

        if (billInfoList != null
                && billInfoList.getBills() != null
                && billInfoList.getBills().size() > 0){
            return billInfoList.getBills() ;
        }
        return null ;
    }

    public BillInfoList readBillListCache(Context context) {

        File cacheDirectory = context.getFilesDir();
        File file = new File(cacheDirectory, BILL_LIST_CACHE_FILENAME);

        BillInfoList billInfoList = null;

        if(file.exists()){

            try {
                billInfoList = mapper.readValue(file, BillInfoList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(billInfoList == null){
            billInfoList = new BillInfoList();
        }

        if(billInfoList.getBills() == null){
            billInfoList.setBills(new ArrayList<BillInfo>());
        }

        return billInfoList;

    }

    public int addNewBill(Context context){

        return addNewBill(context, new BillInfo());
    }

    public int addNewBill(Context context, BillInfo billInfo){

        BillInfoList billInfoList = readBillListCache(context);

        billInfoList.getBills().add(billInfo);

        writeBillListCache(context, billInfoList.getBills());

        return ( billInfoList.getBills().size() - 1);
    }

    public int updateBill(Context context, int position, BillInfo billInfo){

        BillInfoList billInfoList = readBillListCache(context);

        if(billInfoList.getBills().size() > position){
            billInfoList.getBills().set(position, billInfo);
            writeBillListCache(context, billInfoList.getBills());
            return position;
        } else {
            return addNewBill(context, new BillInfo());
        }

    }

    public int deleteBill(Context context, int position){
        BillInfoList billInfoList = readBillListCache(context);
        PaymentInfoList paymentInfoList = readPaymentListCache(context) ;
        boolean isBillReferedInPayments = false ;
        if (paymentInfoList != null && paymentInfoList.getPayments() != null
                && paymentInfoList.getPayments().size() > 0){
            for(PaymentInfo paymentInfo: paymentInfoList.getPayments()){
                if(paymentInfo.getBillInfoIndex() == position){
                    isBillReferedInPayments = true ;
                    break;
                }
            }
        }

        if(billInfoList.getBills().size() > position && !isBillReferedInPayments){
            billInfoList.getBills().remove(position);
            writeBillListCache(context, billInfoList.getBills());
            return position;
        } else {
            return -1;
        }

    }

    public List<PaymentInfo> getPaymentListFromCache(Context context){

        PaymentInfoList paymentInfoList = readPaymentListCache(context) ;

        if (paymentInfoList != null
                && paymentInfoList.getPayments() != null
                && paymentInfoList.getPayments().size() > 0){
            return paymentInfoList.getPayments() ;
        }
        return null ;
    }

    public PaymentInfoList readPaymentListCache(Context context) {

        File cacheDirectory = context.getFilesDir();
        File file = new File(cacheDirectory, PAYMENT_LIST_CACHE_FILENAME);

        PaymentInfoList paymentInfoList = null;

        if(file.exists()){

            try {
                paymentInfoList = mapper.readValue(file, PaymentInfoList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(paymentInfoList == null){
            paymentInfoList = new PaymentInfoList();
        }

        if(paymentInfoList.getPayments() == null){
            paymentInfoList.setPayments(new ArrayList<PaymentInfo>());
        }

        return paymentInfoList;

    }

    private void writePaymentListCache(Context context, List<PaymentInfo> payments){

        File cacheDirectory = context.getFilesDir();
        File file = new File(cacheDirectory, PAYMENT_LIST_CACHE_FILENAME);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PaymentInfoList paymentInfoList = new PaymentInfoList();
        paymentInfoList.setPayments(payments);

        if(file.exists()){
            try {
                mapper.writeValue(file, paymentInfoList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void populatePaymentListCache(Context context) {

        writePaymentListCache(context, PaymentInfo.getDefaultTestPayments(context));

    }

    public void clearPaymentListCache(Context context) {

        writePaymentListCache(context, new ArrayList<PaymentInfo>());
    }

    public int addNewPayment(Context context){

        return addNewPayment(context, new PaymentInfo());
    }

    public int addNewPayment(Context context, PaymentInfo paymentInfo){

        PaymentInfoList paymentInfoList = readPaymentListCache(context);

        paymentInfoList.getPayments().add(paymentInfo);

        writePaymentListCache(context, paymentInfoList.getPayments());

        return ( paymentInfoList.getPayments().size() - 1);
    }

    public int updatePayment(Context context, int position, PaymentInfo paymentInfo){

        PaymentInfoList paymentInfoList = readPaymentListCache(context);

        if(paymentInfoList.getPayments().size() > position){
            paymentInfoList.getPayments().set(position, paymentInfo);
            writePaymentListCache(context, paymentInfoList.getPayments());
            return position;
        } else {
            return addNewBill(context, new BillInfo());
        }

    }

    public int deletePayment(Context context, int position){
        PaymentInfoList paymentInfoList = readPaymentListCache(context);

        if(paymentInfoList.getPayments().size() > position){
            paymentInfoList.getPayments().remove(position);
            writePaymentListCache(context, paymentInfoList.getPayments());
            return position;
        } else {
            return -1;
        }

    }
}
