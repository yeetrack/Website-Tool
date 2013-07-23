/**
 * 
 */
package com.yeetrack.websitetool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yeetrack.spider.WebsiteFriendLinkSpider;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * @author xuemeng
 * 友情链接查询界面
 */
public class FriendLinkActivity extends Activity
{
	private String domain;
	/**
	 * 百度收录个数
	 */
	private TextView baiduCountTextView;
	/**
	 * 百度今日收录个数
	 */
	private TextView baiduTodayCounTextView;
	/**
	 * 百度快照日期
	 */
	private TextView baiduMirrorTextView;
	/**
	 * PR输出值
	 */
	private TextView prTextView;
	/**
	 * 出站链接数
	 */
	private TextView outCountTextView;
	
	/**
	 * Alexa排名
	 */
	private TextView alexaRankTextView;
	/**
	 * 百度首页位置
	 */
	private TextView baiduHomeTextView;
	
	/**
	 * 百度权重
	 */
	private TextView baiduRankTextView;
	
	/**
	 * 展示外链列表
	 */
	private ListView listView;
	
	private WebsiteFriendLinkSpider friendLinkSpider;
	
	
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) 
        {  
        	Bundle bundle = msg.getData();
        	if(bundle.getString("outLinkCount") != null)
        		outCountTextView.setText(outCountTextView.getText().toString().replace("加载...", bundle.getString("outLinkCount")));
        	if(bundle.getString("baiduCount")!= null)
        		baiduCountTextView.setText(baiduCountTextView.getText().toString().replace("加载...", bundle.getString("baiduCount")));
        	if(bundle.getString("baiduToday")!= null)
        		baiduTodayCounTextView.setText(baiduTodayCounTextView.getText().toString().replace("加载...", bundle.getString("baiduToday")));
        	if(bundle.getString("baiduMirror")!= null)
        		baiduMirrorTextView.setText(baiduMirrorTextView.getText().toString().replace("加载...", bundle.getString("baiduMirror")));
        	if(bundle.getString("PROUT")!= null)
        		prTextView.setText(prTextView.getText().toString().replace("加载...", bundle.getString("PROUT")));
        	if(bundle.getString("alexa")!= null)
        		alexaRankTextView.setText(alexaRankTextView.getText().toString().replace("加载...", bundle.getString("alexa")));
        	if(bundle.getString("baiduHome")!= null)
        		baiduHomeTextView.setText(baiduHomeTextView.getText().toString().replace("加载...", bundle.getString("baiduHome")));
        	if(bundle.getString("baiduRank")!= null)
        		baiduRankTextView.setText(baiduRankTextView.getText().toString().replace("加载...", bundle.getString("baiduRank")));
        	if(bundle.getStringArray("outLinkList") != null)
        	{
        		SimpleAdapter adapter = new SimpleAdapter(FriendLinkActivity.this, getData(bundle.getStringArray("outLinkList")), R.layout.list_outlink_item,  
                        new String[]{"title","domain"}, new int[]{R.id.title, R.id.domain});
        		listView.setAdapter(adapter);
        		setProgressBarIndeterminateVisibility(false);
        	}
        }
	};
	
    protected void onCreate(Bundle savedInstanceState)
    {
    	
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_friendlink);
	    
	    Intent intent = this.getIntent();
	    Bundle bundle = intent.getBundleExtra("data");
	    domain = bundle.getString("domain");
	    
	    baiduCountTextView = (TextView)findViewById(R.id.friendLinkbaiduCount);
	    baiduMirrorTextView = (TextView)findViewById(R.id.friendLinkbaiduMirror);
	    prTextView = (TextView)findViewById(R.id.friendLinkPR);
	    outCountTextView = (TextView)findViewById(R.id.friendLinkOutCount);
	    baiduTodayCounTextView = (TextView)findViewById(R.id.friendLinkbaiduToday);
	    alexaRankTextView = (TextView)findViewById(R.id.friendLinkAlexa);
	    baiduHomeTextView = (TextView)findViewById(R.id.friendLinkBaiduHome);
	    baiduRankTextView = (TextView)findViewById(R.id.friendLinkBaiduRank);
	    listView = (ListView)findViewById(R.id.friendLinkOutLinkListView);

	    new Thread(friendLinkRunnable).start();
	    
	    setProgressBarIndeterminateVisibility(true);
    }
    
    /**
     * 子线程抓取外链列表
     */
    Runnable friendLinkRunnable = new Runnable()
	{
		public void run()
		{
			friendLinkSpider = new WebsiteFriendLinkSpider(domain);
			String outLinkCount = friendLinkSpider.getOutLinkCount();
			Bundle bundle = new Bundle();
			bundle.putString("outLinkCount", outLinkCount);
			bundle.putString("baiduCount", friendLinkSpider.getBaiduCount());
			bundle.putString("baiduToday", friendLinkSpider.getBaiduTodayCount());
			bundle.putString("baiduMirror", friendLinkSpider.getBaiduMirror());
			bundle.putString("PROUT", friendLinkSpider.getOutPR());
			bundle.putString("alexa", friendLinkSpider.getAlexaRank());
			bundle.putString("baiduHome", friendLinkSpider.getBaiduHomePage());
			bundle.putString("baiduRank", friendLinkSpider.getBaiduRank());
			ArrayList<String> linkArrayList = friendLinkSpider.getOutLinkList();
			bundle.putStringArray("outLinkList", (String[])linkArrayList.toArray(new String[linkArrayList.size()]));
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};
	
	/**
	 * 处理子线程抓取外链列表，转换成HashMap
	 * @param outlist
	 * @return
	 */
	private List<Map<String, Object>> getData(String[] outlist) 
	{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(int i=0; i<=outlist.length-1;i++)
        {
        	Map<String, Object> map = new HashMap<String, Object>();
        	String link = outlist[i];
        	//<a id="ctl03_CatList_LinkList_0_Link_0" href="http://www.cnblogs.com/allin/category/245045.html">android技术(16)</a>
        	String title = null;
        	String domain = null;
        	int start = link.indexOf(">");
        	int end = link.indexOf("</a");
        	if(start==-1 || end==-1)
        		title = "抓取失败:  ";
        	else
        		title = link.substring(start+1, end);
        	//获取域名
        	start = link.indexOf("href=\"");
        	int end1 = link.indexOf("\"", start+15);
        	int end2 = link.indexOf("/", start+15);
        	end = end1 > end2 ? end2 : end1 ;
        	if(start==-1 || end ==-1)
        		domain = "抓取失败:  ";
        	else
        		domain = link.substring(start+13, end);
        	map.put("title", title+":  ");
        	map.put("domain", domain.trim());	
        	list.add(map);
        }
        
        return list;
	}
}
