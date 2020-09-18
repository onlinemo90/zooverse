package com.animalsoundsldn.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.animalsoundsldn.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanTicketActivity extends AbstractQRCodeReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ticket);

        SurfaceView surfaceCamera = findViewById(R.id.surface_camera);
        TextView textView = findViewById(R.id.textView);
        assignCamera(surfaceCamera,textView);
    }

    @Override
    public void interpretQRCode(String qrContent) {
        //TODO: call decryption here
        //sanity checks
        if (qrContent.length() == 12 && qrContent.charAt(3) == '|') {
            String qrZooId = qrContent.substring(0, 3);
            //check if correct zoo
            if (qrZooId.equals(getString(R.string.zoo_id))) {
                String qrDate = qrContent.substring(4, 12);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String today = sdf.format(new Date());
                try {
                    Date dateToday = sdf.parse(today);
                    Date dateTicket = sdf.parse(qrDate);
                    if (dateToday.compareTo(dateTicket) == 0) {//ticket date is today
                        //save ticket date for shortcut access to menu
                        SharedPreferences preferences = getSharedPreferences("tickets", Context.MODE_PRIVATE);
                        SharedPreferences.Editor preferenceEditor = preferences.edit();
                        preferenceEditor.putString("lastTicketDate", qrDate);
                        preferenceEditor.apply();
                        Toast.makeText(getApplicationContext(), "you're going to the Zoo", Toast.LENGTH_SHORT).show();
                        //TODO: @Pedro to call main menu here and remove above toast
                    } else if (dateToday.compareTo(dateTicket) > 0) {//ticket date is in the past
                        Toast.makeText(getApplicationContext(), getString(R.string.e_past_ticket), Toast.LENGTH_SHORT).show();
                    } else {//ticket date is in the future
                        Toast.makeText(getApplicationContext(), getString(R.string.e_future_ticket), Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    Log.e("date", "date parsing error");
                }
            }else Toast.makeText(getApplicationContext(), getString(R.string.e_wrong_zoo), Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getApplicationContext(), getString(R.string.e_invalid_qr), Toast.LENGTH_SHORT).show();
    }
}