package com.pro.allinone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class WebActivity1 extends AppCompatActivity {
    TextView ts;
    private AdView mAdView;
    WebView webView;
    String url="",murl="";//https://www.google.com";
    @SuppressLint("SetTeasy18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_webview1);


      // ((AppCompatActivity) this).getSupportActionBar().setDisplayHomeAsUpEnabled().hide();  //;
//------------------------------------------------------------------------------------------------Banner Ad-------------//
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //-----------------------------------------------------------------------------main web view------//


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            //assert bundle != null;
            url = bundle.getString("tok");
        }
       // url="http://"+url;
        murl=url;
        Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_LONG).show();


        webView=(WebView)findViewById(R.id.myWebView);
        WebSettings webSettings=webView.getSettings();

        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
         webView.loadUrl(murl);
//        webView.loadUrl("https://www.covidvisualizer.com");http://sbi.co.in/
        webView.setWebViewClient(new WebViewClient());


//---------------------------------------------------------------------------------------------------advanced-------------//

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String murl, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(murl));
                startActivity(i);
            }
        });

    }

}
