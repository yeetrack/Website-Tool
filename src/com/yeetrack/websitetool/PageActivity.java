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
 *
 */
public class PageActivity extends Activity
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
	    setContentView(R.layout.activity_websitepage);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    webView = (WebView)findViewById(R.id.pageWebView);
	    webView.getSettings().setJavaScriptEnabled(true);  
	    

	    webView.loadUrl("http://page.gongju.com/#url="+domain);
	    webView.requestFocus();  
	    WebSettings settings = webView.getSettings(); settings.setUseWideViewPort(true); 
	    settings.setLoadWithOverviewMode(true); 
	           
	    webView.setWebViewClient(new WebViewClient() {    
	        @Override  
	        public void onPageFinished(WebView webView, String url){  
	        	if(flag)
	        	{
	        		webView.loadUrl("javascript:document.getElementsByClassName('header')[0].hidden = 'true'");
	        		webView.loadUrl("javascript:document.getElementById('submit').click()");
	        		flag = false;
	        	}
	        }  
	    });  
	    
    }

}
