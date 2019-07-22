package com.mytests.mobile.remindme.utilities;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.model.BillInfoList;

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

    public void populateBillListCache(Context context) {

        writeBillListCache(context, BillInfo.getDefaultTestBills());

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

    public void clearBillListCache(Context context) {
        writeBillListCache(context, new ArrayList<BillInfo>());
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

        if(billInfoList.getBills().size() > position){
            billInfoList.getBills().remove(position);
            writeBillListCache(context, billInfoList.getBills());
            return position;
        } else {
            return -1;
        }

    }
}
