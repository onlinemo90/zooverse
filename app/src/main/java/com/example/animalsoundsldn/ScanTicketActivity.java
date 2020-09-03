package com.example.animalsoundsldn;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanTicketActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private SurfaceView surfaceCamera;
    private TextView textView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ticket);

        surfaceCamera = findViewById(R.id.surface_camera);
        textView = findViewById(R.id.textView);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build(); //makes sure it ONLY scans QR-codes
        cameraSource = new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(640,480).build();

        surfaceCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                //ask user for Camera permission
                if(checkGrantedCameraPermission(getApplicationContext())){
                    try {
                        cameraSource.start(surfaceHolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                //nothing to do
            }
            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            //TODO: currently only displays QR scan result, here to implement logic for processing the result.
                            textView.setText(qrCodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkGrantedCameraPermission(Context context){
        //Is access permission already given?
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else { //permission not defined yet, then prompt popup to request user permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Pop up answered
        if (requestCode == 1) {
            //User granted permission, reload the screen
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finish();
                startActivity(getIntent());
                //User denied permission, show sad message
            } else {
                Toast.makeText(getApplicationContext(), "Camera is required to scan zoo ticket.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
}