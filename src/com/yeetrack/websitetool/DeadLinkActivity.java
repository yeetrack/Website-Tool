/**
 * 
 */
package com.yeetrack.websitetool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yeetrac.spider.DeadLinkSpider;
import com.yeetrac.spider.HttpTool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author xuemeng
 *
 */ 
public class DeadLinkActivity extends Activity
{

	private String domain;
	private static int index;
	private int count=1;
	
	private List<List<String>> resultList;
	private TableLayout tableLayout;
	
	private DeadLinkSpider deadLinkSpider;
	
	private Handler handler = new Handler()
	{
        public void handleMessage(Message msg)
        {
	        Bundle bundle = msg.getData();
	        if("ok".equals(bundle.getString("status")))
	        {
	        	int index = bundle.getInt("index");
	        		TableRow tableRow = new TableRow(DeadLinkActivity.this);
	        		
	        		//添加序号
	        		TextView textView = new TextView(DeadLinkActivity.this);
	        		textView.setText(String.valueOf(count));
	        		textView.setTextColor(Color.BLUE);
        			tableRow.addView(textView);
        			count++;
	        		for(int j=0;j<=resultList.get(index).size()-1;j++)
	        		{
	        			textView = new TextView(DeadLinkActivity.this);
	        			if(j==0)
	        			{
	        				textView.setMaxEms(10);
	        				textView.setText(resultList.get(index).get(j));
	        			}
	        			else if(j==1)
	        			{
	        				textView.setMaxEms(20);
	        				try
                            {
	                            textView.setText(Html.fromHtml("<a href=\""+resultList.get(index).get(j)+"\">"+URLDecoder.decode(resultList.get(index).get(j), "UTF-8") +"</a>"));
                            } catch (UnsupportedEncodingException e)
                            {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
                            }
	        				
	        				textView.setMovementMethod(LinkMovementMethod.getInstance());
	        			}	
	        			else 
	        				textView.setText(resultList.get(index).get(j));
	        			textView.setTextColor(Color.BLUE);
	        			tableRow.addView(textView);
	        		}
	        		tableLayout.addView(tableRow, new TableLayout.LayoutParams(
	        				LayoutParams.MATCH_PARENT,
	        				LayoutParams.WRAP_CONTENT));
	        	if(count >= resultList.size())
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
	    setContentView(R.layout.activity_deadlink);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    tableLayout = (TableLayout)findViewById(R.id.deadLinkTable);
	    
	    new Thread(urlListRunnable).start();
	    setProgressBarIndeterminateVisibility(true);
    }
	
	/**
	 * 开启线程抓取网站上的链接列表
	 */
	Runnable urlListRunnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			deadLinkSpider = new DeadLinkSpider(domain);
			resultList = deadLinkSpider.getUrlList();
			for(int i=0;i<=resultList.size()-1;i++)
			{
				new Thread(statusRunnable).start();
				//防止一次启动线程太多，对手机造成很大压力
				if(i>=50)
	                try
                    {
	                    Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
                    }
			}
				
		}
	};
	
	/**
	 * 开启线程抓取每个页面的访问状态
	 */
	private Runnable statusRunnable = new Runnable()
	{
		public void run()
		{
			int myIndex = getIndex();
			
			//linux默认最多打开1024个socket通信，这里如果做一下限制
			if(myIndex >= 500)
	            try
                {
	                Thread.sleep(1000);
                } catch (InterruptedException e1)
                {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
                }
			String url = resultList.get(myIndex).get(1);
			DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
			HttpGet get = new HttpGet(url);
			try
            {
	            HttpResponse response = httpClient.execute(get);
	            int statusCode = response.getStatusLine().getStatusCode();
 
	            resultList.get(myIndex).add(String.valueOf(statusCode));
	            Bundle bundle = new Bundle();
	            bundle.putString("status", "ok");
				bundle.putInt("index", myIndex);
				Message msg = new Message();
				msg.setData(bundle);
				handler.sendMessage(msg);
            } catch (ClientProtocolException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } catch (IOException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } finally 
            {
            	httpClient.getConnectionManager().shutdown();
            }
		}
	};
	private static int getIndex()
	{
		return index++;
	}
	
}
