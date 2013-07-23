/**
 * 
 */
package com.yeetrack.websitetool;

import java.util.List;

import com.yeetrack.spider.DnsSpider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author xuemeng
 * DNS检测页面
 */
public class DNSActivity extends Activity
{
	private String domain;
	private DnsSpider dnsSpider;
	private List<List<String>> result;
	private TableLayout tableLayout; 
	private Handler handler = new Handler()
	{
        public void handleMessage(Message msg)
        {
	        // TODO Auto-generated method stub
	        Bundle bundle = msg.getData();
	        //动态增加table列数
	        if("ok".equals(bundle.getString("status")))
	        {
	        	for(int i=0;i<=result.size()-1;i++)
	        	{
	        		TableRow tableRow = new TableRow(DNSActivity.this);
	        		for(int j=0;j<=result.get(i).size()-1;j++)
	        		{
	        			//设置margin后，不能动态生成table列了，为什么？
//	        			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//	        			lp.setMargins(5, 5, 5, 5);
//	    				
//	        			textView.setLayoutParams(lp);
	        			TextView textView = new TextView(DNSActivity.this);
	        			textView.setText(result.get(i).get(j)+"     ");
	        			textView.setTextColor(Color.BLUE);
	        			tableRow.addView(textView);
	        		}
	        		tableLayout.addView(tableRow, new TableLayout.LayoutParams(
	        				LayoutParams.MATCH_PARENT,
	        				LayoutParams.WRAP_CONTENT));
	        	}
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
	    setContentView(R.layout.activity_websitedns);
	    Intent intent = this.getIntent();
	    tableLayout = (TableLayout)findViewById(R.id.dnsTable);
	    
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    new Thread(dnsRunnable).start();
	    setProgressBarIndeterminateVisibility(true);
    }

	
	/**
	 * 开启线程抓取dns
	 */
	Runnable dnsRunnable = new Runnable()
	{
		public void run()
		{
			dnsSpider = new DnsSpider(domain);
			result = dnsSpider.getResult();
			Bundle bundle = new Bundle();
			bundle.putString("status", "ok");
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};
}
