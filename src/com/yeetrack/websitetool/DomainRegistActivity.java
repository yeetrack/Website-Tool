/**
 * 
 */
package com.yeetrack.websitetool;

import java.util.List;
import java.util.Map;

import com.yeetrack.spider.DomainRegistSpider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author youthflies
 * 展示域名的注册情况
 */
public class DomainRegistActivity extends Activity
{
	private ImageButton backButton;
	private ImageButton saveButton;
	private String domain;
	private ListView listView;
	private List<Map<String, Object>> dataList;
	MyAdapter adapter;
	private Handler handler = new Handler(){

		@Override
        public void handleMessage(Message msg)
        {
	        Bundle bundle = msg.getData();
	        if("ok".equals(bundle.getString("status")))
	        {
	        	adapter = new MyAdapter(DomainRegistActivity.this, dataList);
	        	listView.setAdapter(adapter);
	        	setProgressBarIndeterminateVisibility(false);
	        }
        }
		
	};
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_domainregist);
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    listView = (ListView)findViewById(R.id.domainListView);
	    backButton = (ImageButton)findViewById(R.id.domainRegistBackButton);
	    saveButton = (ImageButton)findViewById(R.id.domainRegistSaveButton);
	    
	    //后退、保存按钮监听器
	  //定义按钮监听匿名类
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(v.getId())
                {
                    //点击了后退按钮
                    case R.id.domainRegistBackButton:
                        DomainRegistActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.domainRegistSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-domainRegist", DomainRegistActivity.this))
                    		Toast.makeText(DomainRegistActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(DomainRegistActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
	    
	    new Thread(runnable).start();
	    setProgressBarIndeterminateVisibility(true);
	    
    }
	
	//子线程抓取域名信息
	Runnable runnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			DomainRegistSpider registSpider = new DomainRegistSpider(domain);
			dataList = registSpider.getResultList();
			
			//通知主线程加载完毕，更新UI
			Bundle bundle = new Bundle();
			bundle.putString("status", "ok");
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
			
		}
	};

}
