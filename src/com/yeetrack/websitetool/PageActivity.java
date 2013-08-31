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
 *
 */
public class PageActivity extends Activity
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
	    setContentView(R.layout.activity_websitepage);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    webView = (WebView)findViewById(R.id.pageWebView);
	    webView.getSettings().setJavaScriptEnabled(true);  
	    
	    backButton = (ImageButton)findViewById(R.id.websitePageBackButton);
	    saveButton = (ImageButton)findViewById(R.id.websitePageSaveButton);
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.websitePageBackButton:
                        PageActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.websitePageSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-spider", PageActivity.this))
                    		Toast.makeText(PageActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(PageActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    

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
