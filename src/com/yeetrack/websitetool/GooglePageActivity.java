/**
 * 
 */
package com.yeetrack.websitetool;

import com.yeetrack.spider.WebsiteSEOSpider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xuemeng
 * google page 页面
 */
public class GooglePageActivity extends Activity
{
	private ImageButton backButton;
	private ImageButton saveButton;
	
	private String domain;
	private TextView googleTextView;
	private TextView baiduTextView;
	private TextView sogouTextView;
	private TextView alexaTextView;
	
	private WebsiteSEOSpider seoSpider;
	
	private Handler handler = new Handler()
	{

		@Override
        public void handleMessage(Message msg)
        {
			Bundle bundle = msg.getData();
			if(bundle.getString("google")!=null)
				googleTextView.append(bundle.getString("google"));
			if(bundle.getString("baidu")!=null)
				baiduTextView.append(bundle.getString("baidu"));
			if(bundle.getString("sogou")!=null)
				sogouTextView.append(bundle.getString("sogou"));
			if(bundle.getString("alexa")!=null)
				alexaTextView.append(bundle.getString("alexa"));
			setProgressBarIndeterminateVisibility(false);
        }
		
	};
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_websitepr);
	    
	    googleTextView = (TextView)findViewById(R.id.prGoogle);
	    baiduTextView = (TextView)findViewById(R.id.prBaidu);
	    sogouTextView = (TextView)findViewById(R.id.prSogou);
	    alexaTextView = (TextView)findViewById(R.id.prAlexa);
	    
	    backButton = (ImageButton)findViewById(R.id.websitePRBackButton);
	    saveButton = (ImageButton)findViewById(R.id.websitePRSaveButton);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.websitePRBackButton:
                        GooglePageActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.websitePRSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-spider", GooglePageActivity.this))
                    		Toast.makeText(GooglePageActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(GooglePageActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    
	    new Thread(prRunnable).start();
	    setProgressBarIndeterminateVisibility(true);
    }
	
	/**
	 * 开启线程抓取排名信息
	 */
	Runnable prRunnable = new Runnable()
	{
		public void run()
		{
			seoSpider = new WebsiteSEOSpider(domain);
			Bundle bundle = new Bundle();
			bundle.putString("google", seoSpider.getGooglePage());
			bundle.putString("baidu", seoSpider.getBaiduPage());
			bundle.putString("sogou", seoSpider.getSogouRank());
			bundle.putString("alexa", seoSpider.getAlexaRank());
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};

}
