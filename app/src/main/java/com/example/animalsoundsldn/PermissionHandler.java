package com.example.animalsoundsldn;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class PermissionHandler extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkGrantedCameraPermission(Context context, Activity act){
        //Is access permission already given?
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else { //permission not defined yet, then prompt popup to request user permission
            act.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
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
