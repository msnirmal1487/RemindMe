package com.mytests.mobile.remindme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.mytests.mobile.remindme.model.BillInfo;
import com.mytests.mobile.remindme.utilities.PaymentFrequency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.mytests.mobile.remindme.BillListActivity.NOTE_INFO_INDEX;

public class BillActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BillActivity.class.getSimpleName() ;
    public static final int POSITION_NOT_SET = -1;
    public static final int EXTERNAL_FILE_WRITE_PERMISSION_REQUEST_CODE = 2;
    private EditText edittextBillName ;
    private EditText editTextNote ;
    private Switch switchAutoPay ;
    private ToggleButton toggleActive;
    private Spinner spinnerFrequency ;
    private EditText edittextTentativeDate ;
    private TextView textviewHistory ;

    private String currentPhotopath;

    List<PaymentFrequency> paymentFrequency ;
    private BillInfo billInfo;
    private boolean createNewBill = true;
    private ImageView imageCamera;
    public static final int CAPTURE_IMAGE_ACTIVITY_RESULT = 1;
    private ImageView imageThumbnail;

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
        imageCamera = (ImageView) findViewById(R.id.image_camera);
        imageThumbnail = (ImageView) findViewById(R.id.image_thumbnail);

        if(!isFeatureAvailable(this, PackageManager.FEATURE_CAMERA)){
            imageCamera.setVisibility(View.GONE);
            imageThumbnail.setVisibility(View.GONE);
        } else {
            imageCamera.setVisibility(View.VISIBLE);
        }
        paymentFrequency = PaymentFrequency.getPaymentFrequencies() ;


        imageCamera.setOnClickListener(this);

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
            sendMail();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void sendMail() {
        String billName = edittextBillName.getText().toString();
        String billNotes = editTextNote.getText().toString();
        String tentaiveDate = edittextTentativeDate.getText().toString();

        String subject = billName ;
        String text = "Reminder for \"" + billName + "\", \"" + billNotes + "\", due by " + tentaiveDate;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        if(view == imageCamera){
            checkPermissionToSaveImage();
        }
    }

    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, "remindMe_bill_name");
        if( intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;

            photoFile = getImageFile() ;

            if (photoFile != null){

                Uri photoUri = FileProvider.getUriForFile(this, "com.mytests.fileprovider", photoFile) ;
                Log.d(TAG, "photoUri - " + photoUri) ;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri) ;
                grantUriPermission("com.android.camera", photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                grantUriPermission("com.android.camera", photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_RESULT);
            }
        } else {
            Toast.makeText(this, "Android device does not have an app that can capture Images", Toast.LENGTH_SHORT).show();
        }
    }

    private File getImageFile() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + "Remind_me";
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ; // to be used if the images should be private
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentPhotopath = image.getAbsolutePath() ;
        Log.d(TAG, "current Photo Path - " + currentPhotopath) ;
        return image ;

    }

    private void checkPermissionToSaveImage(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this, "Permission Needed to save the file to external storage", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_FILE_WRITE_PERMISSION_REQUEST_CODE);
        } else {
            captureImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case EXTERNAL_FILE_WRITE_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    captureImage();
                } else {
                    Toast.makeText(this, "Permission Not granted to Write Image to External storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent result) {

        if(requestCode == CAPTURE_IMAGE_ACTIVITY_RESULT && resultCode == RESULT_OK && result != null){
            Bitmap thumbNail = result.getParcelableExtra("data");
            if(thumbNail != null){
                imageThumbnail.setVisibility(View.VISIBLE);
                imageThumbnail.setImageBitmap(thumbNail);
            }
        }
    }

    public static boolean isFeatureAvailable(Context context, String feature){
         FeatureInfo[] featureInfos = context.getPackageManager().getSystemAvailableFeatures();
         if(featureInfos != null && featureInfos.length > 0){
             for(FeatureInfo featureInfo: featureInfos){
                 if(featureInfo.name != null && featureInfo.name.equals(feature)){
                     return true;
                 }
             }
         }
         return false;
    }
}
