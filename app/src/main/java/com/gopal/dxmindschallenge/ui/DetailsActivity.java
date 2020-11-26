package com.gopal.dxmindschallenge.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gopal.dxmindschallenge.R;
import com.gopal.dxmindschallenge.networking.AppStatus;

public class DetailsActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String urls = getIntent().getStringExtra("URL");

        /*connection check*/
        if (!AppStatus.getInstance(this).isOnline()) {
            Toast.makeText(this, "Connection Error,Try Again!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (urls != null && urls.length() > 1) {
            webView = findViewById(R.id.webview);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(urls);
        }
    }

}
