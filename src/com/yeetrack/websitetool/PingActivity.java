/**
 * 
 */
package com.yeetrack.websitetool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author xuemeng
 * Ping页面
 */
public class PingActivity extends Activity
{

	private String domain;
	private WebView webView;
	private boolean flag = true;
	@SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_websiteping);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    webView = (WebView)findViewById(R.id.pingWebView);
	    webView.getSettings().setJavaScriptEnabled(true);  
	    

	    webView.loadUrl("http://ping.gongju.com/#url="+domain);
	    webView.requestFocus();  
	    WebSettings settings = webView.getSettings(); settings.setUseWideViewPort(true); 
	    settings.setLoadWithOverviewMode(true); 
	           
	    webView.setWebViewClient(new WebViewClient() {    
	        @Override  
	        public void onPageFinished(WebView webView, String url){  
	        	if(flag)
	        	{
	        		webView.loadUrl("javascript:document.getElementsByClassName('header')[0].hidden = 'true'");
	        		webView.loadUrl("javascript:document.getElementById('alllinetype').click()");
	        		webView.loadUrl("javascript:document.getElementById('submit').click()");
	        		flag = false;
	        	}
	        }  
	    });  
	    
    }

}
