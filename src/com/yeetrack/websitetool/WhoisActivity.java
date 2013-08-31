package com.yeetrack.websitetool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.yeetrack.spider.WhoisSpider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xuemeng
 * Whois 页面
 */
public class WhoisActivity extends Activity
{
	private ImageButton backButton;
	private ImageButton saveButton;

	private String domain;
	private TextView domainTextView;
	private TextView registrarTextView;
	private TextView domainServerTextView;
	private TextView dnsServerTextView;
	private TextView statusTextView;
	private TextView updateTimeTextView;
	private TextView createTimeTextView;
	private TextView expireTimeTextView;
	private TextView administratorTextView;
	private ImageView emailImageView;
	private TextView emailServerTextView;
	private TextView phoneTextView;
	private WhoisSpider whoisSpider;
	
	private Bitmap bitmap;
	
	Handler handler = new Handler()
	{

		@Override
        public void handleMessage(Message msg)
        {
	        Bundle bundle = msg.getData();
	        domainTextView.setText(bundle.getString("domain"));
	        registrarTextView.setText(bundle.getString("registrar"));
	        domainServerTextView.setText(bundle.getString("serverName"));
	        dnsServerTextView.setText(bundle.getString("dnsServer"));
	        statusTextView.setText(bundle.getString("status"));
	        updateTimeTextView.setText(bundle.getString("updateTime"));
	        createTimeTextView.setText(bundle.getString("createTime"));
	        expireTimeTextView.setText(bundle.getString("expireTime"));
	        administratorTextView.setText(bundle.getString("administrator"));
	        phoneTextView.setText(bundle.getString("phone"));
	        emailImageView.setImageBitmap(bitmap);
	        emailServerTextView.setText(bundle.getString("emailServer"));
	        setProgressBarIndeterminateVisibility(false);
	        
        }
		
	};
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_whois);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    domainTextView = (TextView)findViewById(R.id.whoisDomainName);
	    registrarTextView = (TextView)findViewById(R.id.whoisRegistrar);
	    domainServerTextView = (TextView)findViewById(R.id.whoisServer);
	    dnsServerTextView = (TextView)findViewById(R.id.whoisDNS);
	    statusTextView = (TextView)findViewById(R.id.whoisStatus);
	    updateTimeTextView = (TextView)findViewById(R.id.whoisUpdatetime);
	    createTimeTextView = (TextView)findViewById(R.id.whoisCreatetime);
	    expireTimeTextView = (TextView)findViewById(R.id.whoisExpiretime);
	    administratorTextView = (TextView)findViewById(R.id.whoisAdministrator);
	    emailImageView = (ImageView)findViewById(R.id.whoisEmailImage);
	    emailServerTextView = (TextView)findViewById(R.id.whoisEmail);
	    phoneTextView = (TextView)findViewById(R.id.whoisPhone);
	    
	    backButton = (ImageButton)findViewById(R.id.websiteWhoisBackButton);
	    saveButton = (ImageButton)findViewById(R.id.websiteWhoisSaveButton);
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.websiteWhoisBackButton:
                    	WhoisActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.websiteWhoisSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-whois", WhoisActivity.this))
                    		Toast.makeText(WhoisActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(WhoisActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    
	    new Thread(whoisRunnable).start();
	    setProgressBarIndeterminateVisibility(true);
    }
	
	Runnable whoisRunnable = new Runnable()
	{
		public void run()
		{
			whoisSpider = new WhoisSpider(domain);
			Bundle bundle = new Bundle();
			bundle.putString("domain", whoisSpider.getDomainResult());
			bundle.putString("registrar", whoisSpider.getRegistrar());
			bundle.putString("serverName", whoisSpider.getWhoisServer());
			bundle.putString("dnsServer", whoisSpider.getDnsServer());
			bundle.putString("status", whoisSpider.getStatus());
			bundle.putString("updateTime", whoisSpider.getUpdateTime());
			bundle.putString("createTime", whoisSpider.getCreateTime());
			bundle.putString("expireTime", whoisSpider.getExpireTime());
			bundle.putString("administrator", whoisSpider.getAdminstrator());
			bundle.putString("emailUrl", whoisSpider.getEmailURL());
			bundle.putString("emailServer", whoisSpider.getEmailServer());
			bundle.putString("phone", whoisSpider.getPhone());
			//注册人邮箱  http://www.whois.com/eimg/2/e4/2e4f7a9cdc1ec4171fab77ed2b42183a19b147db.png
			String url = whoisSpider.getEmailURL();
			URL imageUrl;
            try
            {
	            imageUrl = new URL("http://www.whois.com"+url);
	            HttpURLConnection connection = (HttpURLConnection)imageUrl.openConnection();
		        connection.addRequestProperty("Referer", "http://www.whois.com");
		        //10秒超时
		        connection.setConnectTimeout(10000);
		        connection.setDoInput(true);
		        connection.setUseCaches(true);
		        InputStream is = connection.getInputStream();
		        bitmap = BitmapFactory.decodeStream(is);
		        is.close();
		        connection.disconnect();
            } catch (MalformedURLException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } catch (IOException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
	        
			
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
			
		}
	};

}
