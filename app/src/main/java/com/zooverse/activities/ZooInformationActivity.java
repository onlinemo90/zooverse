package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zooverse.R;

public class ZooInformationActivity extends AppCompatActivity {
	private WebView zooWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoo_information);
		
		zooWebView = findViewById(R.id.zooWebView);
		zooWebView.setWebViewClient(new WebViewClient());
		zooWebView.getSettings().setJavaScriptEnabled(true);
		zooWebView.loadUrl(getString(R.string.zoo_information_url));
	}
	
	@Override
	public void onBackPressed() {
		if (zooWebView.canGoBack())
			zooWebView.goBack();
		else
			super.onBackPressed();
	}
}