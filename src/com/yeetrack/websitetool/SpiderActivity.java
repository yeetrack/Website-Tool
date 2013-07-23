/**
 * 
 */
package com.yeetrack.websitetool;

import com.yeetrack.spider.SpiderSpiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

/**
 * @author xuemeng
 * 蜘蛛模拟页面
 */
public class SpiderActivity extends Activity
{

	private String domain;
	private TextView titleTextView;
	private TextView h1TextView;
	private TextView h2TextView;
	private TextView h3TextView;
	private TextView bodyTextView;
	
	private SpiderSpiser spiderSpiser;
	
	Handler handler = new Handler()
	{
        public void handleMessage(Message msg)
        {
        	Bundle bundle = msg.getData();
        	if(bundle.getString("title")!=null)
        		titleTextView.setText(bundle.getString("title"));
        	if(bundle.getString("h1")!=null)
        		h1TextView.setText(bundle.getString("h1"));
        	if(bundle.getString("h2")!=null)
        		h2TextView.setText(bundle.getString("h2"));
        	if(bundle.getString("h3")!=null)
        		h3TextView.setText(bundle.getString("h3"));
        	if(bundle.getString("body")!=null)
        		bodyTextView.setText(Html.fromHtml(bundle.getString("body")));
        	setProgressBarIndeterminateVisibility(false);
        }
	};
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_spider);
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    titleTextView = (TextView)findViewById(R.id.spiderTitle);
	    h1TextView = (TextView)findViewById(R.id.spiderH1);
	    h2TextView = (TextView)findViewById(R.id.spiderH2);
	    h3TextView = (TextView)findViewById(R.id.spiderH3);
	    bodyTextView = (TextView)findViewById(R.id.spiderBody);
	    
	    new Thread(spiderRunnable).start();
	    setProgressBarIndeterminateVisibility(true);
    }
	
	Runnable spiderRunnable = new Runnable()
	{
		public void run()
		{
			spiderSpiser = new SpiderSpiser(domain);
			Bundle bundle = new Bundle();
			bundle.putString("title", spiderSpiser.getTitle());
			bundle.putString("h1", spiderSpiser.getH1());
			bundle.putString("h2", spiderSpiser.getH2());
			bundle.putString("h3", spiderSpiser.getH3());
			bundle.putString("body", spiderSpiser.getBody());
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
			
		}
	};

}
