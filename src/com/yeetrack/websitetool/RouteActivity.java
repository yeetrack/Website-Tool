/**
 * 
 */
package com.yeetrack.websitetool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author xuemeng
 * 路由追踪页面
 */
public class RouteActivity extends Activity
{

	private ImageButton backButton;
	private ImageButton saveButton;
	
	private String domain;
	private WebView webView;
	private boolean flag = true;
	@SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_websiteroute);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    webView = (WebView)findViewById(R.id.routeWebView);
	    webView.getSettings().setJavaScriptEnabled(true);  
	    
	    backButton = (ImageButton)findViewById(R.id.websiteRouteBackButton);
	    saveButton = (ImageButton)findViewById(R.id.websiteRouteSaveButton);
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.websiteRouteBackButton:
                    	RouteActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.websiteRouteSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-route", RouteActivity.this))
                    		Toast.makeText(RouteActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(RouteActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    

	    webView.loadUrl("http://tracert.gongju.com/#url="+domain);
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