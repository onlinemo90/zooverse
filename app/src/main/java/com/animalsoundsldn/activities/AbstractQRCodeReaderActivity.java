package com.animalsoundsldn.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.animalsoundsldn.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public abstract class AbstractQRCodeReaderActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    String lastQRCodeDetection = null;
    String newQRCodeDetection = null;

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
                Toast.makeText(getApplicationContext(), getString(R.string.e_camera_access), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    public void assignCamera (SurfaceView surfaceView, final TextView textView){
        final Handler handler = new Handler();
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build(); //makes sure it ONLY scans QR-codes
        final CameraSource cameraSource = new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(640,480).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
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
                if (qrCodes.size() != 0) {
                    newQRCodeDetection = qrCodes.valueAt(0).displayValue;
                    //only trigger new interpretation if a different qr code was scanned
                    if (!newQRCodeDetection.equals(lastQRCodeDetection)) {
                        lastQRCodeDetection = newQRCodeDetection;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //trigger short device vibration upon detection
                                Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
                                interpretQRCode(qrCodes.valueAt(0).displayValue);
                            }
                        });
                    }
                }
            }
        });
    }
    //to be implemented by each subclass depending on what should happen with qr code result.
    public abstract void interpretQRCode(String qrContent);


}
