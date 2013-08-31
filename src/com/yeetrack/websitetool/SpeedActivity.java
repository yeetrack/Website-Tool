/**
 * 
 */
package com.yeetrack.websitetool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author xuemeng
 * 网站测速界面
 */
public class SpeedActivity extends Activity
{
	private ImageButton backButton;
	private ImageButton saveButton;
	
	private String domain;
	
	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_websitespeed);
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    webView = (WebView)findViewById(R.id.speedWebView);
	    webView.getSettings().setJavaScriptEnabled(true);  
	    
	    backButton = (ImageButton)findViewById(R.id.websiteSpeedBackButton);
	    saveButton = (ImageButton)findViewById(R.id.websiteSpeedSaveButton);
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.websiteSpeedBackButton:
                    	SpeedActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.websiteSpeedSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-speed", SpeedActivity.this))
                    		Toast.makeText(SpeedActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(SpeedActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    

	    webView.loadUrl("http://webscan.360.cn/tools/http");
	    webView.requestFocus();  
	    WebSettings settings = webView.getSettings(); settings.setUseWideViewPort(true); 
	    settings.setLoadWithOverviewMode(true); 
	           
	    webView.setWebViewClient(new WebViewClient() {    
	        @Override  
	        public void onPageFinished(WebView webView, String url){  
	        	webView.loadUrl("javascript:document.getElementsByClassName('header')[0].hidden = 'true'");
	        	webView.loadUrl("javascript:document.getElementsByName('domain_name')[0].value='"+domain+"'");
	        	String[] netStrings = {"dx","shgddx", "bjzwdx","qhddx","xjdx","","hmdx","bjccc","bjse","bjccp"};
	        	for(String net : netStrings)
	        		webView.loadUrl("javascript:document.getElementById('"+net+"').setAttribute('checked', 'checked')");
	        	
	        	webView.loadUrl("javascript:document.getElementById('btn1').click()");
	        }  
	    });  
	    
	    
	    
	    //webView.postUrl(url, EncodingUtils.getBytes(postData, "utf-8"));
	    
	    Log.d("站长工具", "load 页面");
	    
	    Log.d("站长工具", "输入域名");
	    
    }

}
