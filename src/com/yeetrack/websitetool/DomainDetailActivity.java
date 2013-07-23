/**
 * 
 */
package com.yeetrack.websitetool;

import com.yeetrac.spider.WebsiteBaiduSpider;
import com.yeetrac.spider.WebsiteBingSpider;
import com.yeetrac.spider.WebsiteGCDSpider;
import com.yeetrac.spider.WebsiteGooleSpider;
import com.yeetrac.spider.WebsiteIPSpider;
import com.yeetrac.spider.WebsitePageSpider;
import com.yeetrac.spider.WebsiteSEOSpider;
import com.yeetrac.spider.WebsiteSoSpider;
import com.yeetrac.spider.WebsiteSosoSpider;
import com.yeetrac.spider.WebsiteYahooSpider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * @author xuemeng
 * 点击综合查询按钮，进入此页面，显示域名的详细信息
 */
public class DomainDetailActivity extends Activity
{

	private TextView titleTextView;
	//网站标题结果
	private TextView titleEditText;
	//网站描述结果
	private TextView descEditText;
	//网站SEO信息
	private TextView seoTextView;
	//网站Ip地址信息
	private TextView ipTextView;
	//网站备案信息
	private TextView gcdTextView;
	//谷歌收录个数
	private TextView googleTextView;
	//百度收录个数
	private TextView baiduTextView;
	//360收录个数
	private TextView so360TextView;
	//bing收录个数
	private TextView bingTextView;
	//搜搜收录个数
	private TextView sosoTextView;
	//雅虎收录个数
	private TextView yahooTextView;
	private String domain;
	
	private WebsitePageSpider pageSpider;
	private WebsiteSEOSpider seoSpider;
	private WebsiteIPSpider ipSpider;
	private WebsiteGCDSpider gcdSpider;
	private WebsiteGooleSpider googleSpider;
	private WebsiteBaiduSpider baiduSpider;
	private WebsiteSoSpider so360Spider;
	private WebsiteSosoSpider sosoSpider;
	private WebsiteBingSpider bingSpider;
	private WebsiteYahooSpider yahooSpider;
	
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) 
        {  
        	Bundle bundle = msg.getData();
        	if(bundle.getString("title") != null)
        		titleEditText.setText(bundle.getString("title"));
        	if(bundle.getString("desc") != null)
        		descEditText.setText(bundle.getString("desc"));
        	if(bundle.getString("pr") != null)
        		seoTextView.setText("谷歌权重："+bundle.getString("pr")+" ");
        	if(bundle.getString("br") != null)
        		seoTextView.append("百度权重："+bundle.getString("br")+" ");
        	if(bundle.getString("alexa") != null)
        		seoTextView.append("\nalexa排名："+bundle.getString("alexa"));
        	if(bundle.getString("ip") != null)
        		ipTextView.setText("IP地址 ："+ bundle.getString("ip"));
        	if(bundle.getString("icq") != null)
        	{
        		gcdTextView.setText(Html.fromHtml(bundle.getString("icq")));
        		gcdTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("googleCount") != null)
        	{
        		googleTextView.setText(Html.fromHtml("<a href=\"http://www.google.com.hk/search?q=site%3A"+domain+"\">"+bundle.getString("googleCount")+"</a>"));
        		googleTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("baiduCount") != null)
        	{
        		baiduTextView.setText(Html.fromHtml("<a href=\"http://www.baidu.com/s?wd=site%3A"+domain+"\">"+bundle.getString("baiduCount")+"</a>"));
        		baiduTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("so360Count") != null)
        	{
        		so360TextView.setText(Html.fromHtml("<a href=\"http://www.so.com/s?q=site:"+domain+"\">"+bundle.getString("so360Count")+"</a>"));
        		so360TextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("sosoCount") != null)
        	{
        		sosoTextView.setText(Html.fromHtml("<a href=\"http://www.soso.com/q?w=site%3A"+domain+"\">"+bundle.getString("sosoCount")+"</a>"));
        		sosoTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("bingCount") != null)
        	{
        		bingTextView.setText(Html.fromHtml("<a href=\"http://cn.bing.com/search?q=site%3A"+domain+"\">"+bundle.getString("bingCount")+"</a>"));
        		bingTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("yahooCount") != null)
        	{
        		yahooTextView.setText(Html.fromHtml("<a href=\"http://www.yahoo.cn/s?q=site:"+domain+"\">"+bundle.getString("yahooCount")+"</a>"));
        		yahooTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        }
	};
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_domaindetail);
	    
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    
	    titleTextView = (TextView)findViewById(R.id.detailResultTitle);
	    titleEditText= (TextView)findViewById(R.id.websiteTitleResult);
	    descEditText = (TextView)findViewById(R.id.websiteDescResult);
	    seoTextView = (TextView)findViewById(R.id.websiteSEOResult);
	    ipTextView = (TextView)findViewById(R.id.websiteIPResult);
	    gcdTextView = (TextView)findViewById(R.id.websiteGCDResult);
	    googleTextView = (TextView)findViewById(R.id.websiteGoogleResult);
	    baiduTextView = (TextView)findViewById(R.id.websiteBaiduResult);
	    so360TextView = (TextView)findViewById(R.id.website360Result);
	    bingTextView = (TextView)findViewById(R.id.websiteBingResult);
	    sosoTextView = (TextView)findViewById(R.id.websiteSoSoResult);
	    yahooTextView = (TextView)findViewById(R.id.websiteyahooResult);
	    
	    titleTextView.setText("网站:"+domain+"的详细信息");
	    titleEditText.setText("加载中...");
	    descEditText.setText("加载中...");
	    seoTextView.setText("加载中...");
	    ipTextView.setText("加载中...");
	    gcdTextView.setText("加载中...");
	    googleTextView.setText("加载中...");
	    baiduTextView.setText("加载中...");
	    so360TextView.setText("加载中...");
	    bingTextView.setText("加载中...");
	    sosoTextView.setText("加载中...");
	    yahooTextView.setText("加载中...");
	    new Thread(titleRunnable).start();
	    new Thread(seoRunnable).start();
	    new Thread(ipRunnable).start();
	    new Thread(gcdRunnable).start();
	    new Thread(googleRunnable).start();
	    new Thread(baiduRunnable).start();
	    new Thread(so360Runnable).start();
	    new Thread(sosoRunnable).start();
	    new Thread(bingRunnable).start();
	    new Thread(yahooRunnable).start();
    }
	/**
	 * 开启线程抓取域名的title和desc
	 */
	Runnable titleRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			pageSpider = new WebsitePageSpider(domain);
		    String title = pageSpider.getTitle();
		    String desc = pageSpider.getDesc();
		    Bundle titleBundle = new Bundle();
		    titleBundle.putString("title", title);
		    titleBundle.putString("desc", desc);
		    Message msg = new Message();
		    msg.setData(titleBundle);
		    handler.sendMessage(msg);
		}
	};
	/**
	 * 开启线程抓取域名的seo信息
	 */
	Runnable seoRunnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			seoSpider = new WebsiteSEOSpider(domain);
			Bundle pageBundle = new Bundle();
		    pageBundle.putString("pr", seoSpider.getGooglePage());
		    pageBundle.putString("br", seoSpider.getBaiduPage());
		    pageBundle.putString("alexa", seoSpider.getAlexaRank());
		    Message msg = new Message();
		    msg.setData(pageBundle);
		    handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取域名的ip地址
	 */
	Runnable ipRunnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			ipSpider = new WebsiteIPSpider(domain);
			Bundle pageBundle = new Bundle();
		    pageBundle.putString("ip", ipSpider.getIpAddress());
		    Message msg = new Message();
		    msg.setData(pageBundle);
		    handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取备案信息
	 */
	Runnable gcdRunnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			gcdSpider = new WebsiteGCDSpider(domain);
			Bundle pageBundle = new Bundle();
		    pageBundle.putString("icq", gcdSpider.getIcq());
		    Message msg = new Message();
		    msg.setData(pageBundle);
		    handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取google收录页面个数
	 */
	Runnable googleRunnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			googleSpider = new WebsiteGooleSpider(domain);
			Bundle googleBundle = new Bundle();
			googleBundle.putString("googleCount", googleSpider.getGoogleCount());
			Message msg = new Message();
			msg.setData(googleBundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程专区baidu收录页面个数
	 */
	Runnable baiduRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			baiduSpider = new WebsiteBaiduSpider(domain);
			Bundle baiduBundle = new Bundle();
			baiduBundle.putString("baiduCount", baiduSpider.getBaiduCount());
			Message msg = new Message();
			msg.setData(baiduBundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取360收录页面个数
	 */
	Runnable so360Runnable = new Runnable()
	{
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			so360Spider = new WebsiteSoSpider(domain);
			Bundle so360Bundle = new Bundle();
			so360Bundle.putString("so360Count", so360Spider.getSoCount());
			Message msg = new Message();
			msg.setData(so360Bundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取搜搜收录个数
	 */
	Runnable sosoRunnable = new Runnable()
	{
		public void run()
		{
			sosoSpider = new WebsiteSosoSpider(domain);
			Bundle sosoBundle = new Bundle();
			sosoBundle.putString("sosoCount", sosoSpider.getsosoCount());
			Message msg = new Message();
			msg.setData(sosoBundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取bing收录个数
	 */
	Runnable bingRunnable = new Runnable()
	{
		public void run()
		{
			bingSpider = new WebsiteBingSpider(domain);
			Bundle bingBundle = new Bundle();
			bingBundle.putString("bingCount", bingSpider.getBingCount());
			Message msg = new Message();
			msg.setData(bingBundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 开启线程抓取yahoo收录个数
	 */
	Runnable yahooRunnable = new Runnable()
	{
		public void run()
		{
			yahooSpider = new WebsiteYahooSpider(domain);
			Bundle yahooBundle = new Bundle();
			yahooBundle.putString("yahooCount", yahooSpider.getYahooCount());
			Message msg = new Message();
			msg.setData(yahooBundle);
			handler.sendMessage(msg);
		}
	};
}
