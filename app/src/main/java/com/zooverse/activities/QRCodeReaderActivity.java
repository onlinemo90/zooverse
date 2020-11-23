package com.zooverse.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.zooverse.Servlet;
import com.zooverse.model.Model;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;

import java.io.IOException;

public class QRCodeReaderActivity extends AbstractBaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
	private String lastQRCodeDetection = null;
	private String newQRCodeDetection = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr_code);
		
		SurfaceView surfaceCamera = findViewById(R.id.cameraSurface);
		assignCamera(surfaceCamera);
	}
	
	public boolean hasCameraPermission(Context context) {
		// Is access permission already given?
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
			return true;
		} else { //permission not defined yet, then prompt popup to request user permission
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
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
			} else { //User denied permission, show sad message
				Toast.makeText(MainApplication.getContext(), getString(R.string.scan_qr_code_error_camera_access_denied), Toast.LENGTH_SHORT).show();
				finish();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
	
	public void assignCamera(SurfaceView surfaceView) {
		final Handler handler = new Handler();
		final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build(); //makes sure it ONLY scans QR-codes
		final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
			@RequiresApi(api = Build.VERSION_CODES.M)
			@Override
			public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
				//ask user for Camera permission
				if (hasCameraPermission(MainApplication.getContext())) {
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
					// only trigger new interpretation if a different qr code was scanned
					if (!newQRCodeDetection.equals(lastQRCodeDetection)) {
						lastQRCodeDetection = newQRCodeDetection;
						handler.post(() -> {
							// trigger short device vibration upon detection
							Vibrator vibrator = (Vibrator) MainApplication.getContext().getSystemService(Context.VIBRATOR_SERVICE);
							vibrator.vibrate(1000);
							processExternalRequest(qrCodes.valueAt(0).displayValue);
						});
					}
				}
			}
		});
	}
}
