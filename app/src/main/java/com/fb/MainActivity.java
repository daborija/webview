package com.fb;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;
import android.graphics.drawable.AnimationDrawable;

public class MainActivity extends AppCompatActivity {
	
    WebView webView;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
       
    
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					LoadWeb();
				}
			});

        LoadWeb();
    }

    public void LoadWeb(){
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("https://facebook.com");
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){
				public void onReceivedError(WebView view, int errorCode,
											String description, String failingUrl) {
					webView.loadUrl("file:///android_asset/error.html");
				}
				public  void  onPageFinished(WebView view, String url){
					//ketika loading selesai, ison loading akan hilang
					swipe.setRefreshing(false);
				}
			});

        webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					//loading akan jalan lagi ketika masuk link lain
					// dan akan berhenti saat loading selesai
					if(webView.getProgress()== 100){
						swipe.setRefreshing(false);
					} else {
						swipe.setRefreshing(true);
					}
				}
			});
    }

    @Override
    public void onBackPressed(){

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

}
