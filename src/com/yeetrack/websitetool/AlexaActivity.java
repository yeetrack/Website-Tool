/**
 * 
 */
package com.yeetrack.websitetool;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yeetrack.spider.AlexaSpider;

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
 * alexa 页面
 */ 
public class AlexaActivity extends Activity
{

	private String domain;
    /**
     * 后退按钮
     * */
    private ImageButton backButton;
    /**
     *         分享按钮
     **/
    private ImageButton saveButton;
    /**
     * refresh button
     */
     private TextView alexaTitleTextView;
	private TextView alexaDomainTextView;
	private TextView alexaAdministratorTextView;
	private TextView alexaEmailTextView;
	private TextView alexaCurrentRankTextView;
	private TextView alexaNextRankTextView;
	private TextView alexaIncludeDateTextView;
	private TextView alexaBelongToCountTextView;
	private TextView alexaCharsetTextView;
	private TextView alexaVisitSpeedTextView;
	private TextView alexaAdultTextView;
	private TextView alexaReverseLinkTextView;
	private TextView alexaPhoneTextView;
	private TextView alexaAddressTextView;
	private TextView alexaBelongToListTextView;
	private TextView alexaSummaryTextView;
	
	private TextView alexaTitle2TextView;
	private TextView alexaTodayRankTextView;
	private TextView alexaTodayRankGoTextView;
	private TextView alexaWeekRankTextView;
	private TextView alexaWeekRankGoTextView;
	private TextView alexaMonthRankTextView;
	private TextView alexaMonthRankGoTextView;
	private TextView alexaThreeMonthRankTextView;
	private TextView alexaThreeMonthRankGoTextView;
	
	private TextView alexaDayIpTextView;
	private TextView alexaDayPvTextView;
	
	private AlexaSpider alexaSpider;
	
	private Handler handler = new Handler()
	{
        public void handleMessage(Message msg)
        {
        	Bundle bundle = msg.getData();
        	if(bundle.getString("title")!=null)
        		alexaTitleTextView.setText(Html.fromHtml(bundle.getString("title")));
        	if(bundle.getString("domain")!=null)
        		alexaDomainTextView.setText(bundle.getString("domain"));
        	if(bundle.getString("administrator")!=null)
        		alexaAdministratorTextView.setText(bundle.getString("administrator"));
        	if(bundle.getString("email")!=null)
        		alexaEmailTextView.setText(bundle.getString("email"));
        	if(bundle.getString("currentRank")!=null)
        		alexaCurrentRankTextView.setText(bundle.getString("currentRank"));
        	if(bundle.getString("nextRank")!=null)
        		alexaNextRankTextView.setText(bundle.getString("nextRank"));
        	if(bundle.getString("includeDate")!=null)
        		alexaIncludeDateTextView.setText(bundle.getString("includeDate"));
        	if(bundle.getString("belongToCount")!=null)
        		alexaBelongToCountTextView.setText(bundle.getString("belongToCount"));
        	if(bundle.getString("charset")!=null)
        		alexaCharsetTextView.setText(bundle.getString("charset"));
        	if(bundle.getString("visitSpeed")!=null)
        		alexaVisitSpeedTextView.setText(bundle.getString("visitSpeed"));
        	if(bundle.getString("adult")!=null)
        		alexaAdultTextView.setText(bundle.getString("adult"));
        	if(bundle.getString("reverseLink")!=null)
        	{
        		alexaReverseLinkTextView.setText(Html.fromHtml(bundle.getString("reverseLink")));
        		alexaReverseLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        	}
        	if(bundle.getString("phone")!=null)
        		alexaPhoneTextView.setText(bundle.getString("phone"));
        	if(bundle.getString("address")!=null)
        		alexaAddressTextView.setText(bundle.getString("address"));
        	if(bundle.getString("belongToList")!=null)
        		alexaBelongToListTextView.setText(bundle.getString("belongToList"));
        	if(bundle.getString("summary")!=null)
        		alexaSummaryTextView.setText(bundle.getString("summary"));
        	
        	if(bundle.getString("title2")!=null)
        		alexaTitle2TextView.setText(Html.fromHtml(bundle.getString("title2")));
        	if(bundle.getString("todayRank")!=null)
        		alexaTodayRankTextView.setText(bundle.getString("todayRank"));
        	if(bundle.getString("todayRankGo")!=null)
        		alexaTodayRankGoTextView.setText(bundle.getString("todayRankGo"));
        	if(bundle.getString("weekRank")!=null)
        		alexaWeekRankTextView.setText(bundle.getString("weekRank"));
        	if(bundle.getString("weekRankGo")!=null)
        		alexaWeekRankGoTextView.setText(bundle.getString("weekRankGo"));
        	if(bundle.getString("monthRank")!=null)
        		alexaMonthRankTextView.setText(bundle.getString("monthRank"));
        	if(bundle.getString("monthRankGo")!=null)
        		alexaMonthRankGoTextView.setText(bundle.getString("monthRankGo"));
        	if(bundle.getString("threeMonthRank")!=null)
        		alexaThreeMonthRankTextView.setText(bundle.getString("threeMonthRank"));
        	if(bundle.getString("threeMonthRankGo")!=null)
        		alexaThreeMonthRankGoTextView.setText(bundle.getString("threeMonthRankGo"));
        	if(bundle.getString("dayIP")!=null)
        		alexaDayIpTextView.setText(bundle.getString("dayIP"));
        	if(bundle.getString("dayPV")!=null)
        		alexaDayPvTextView.setText(bundle.getString("dayPV"));
        	
        }
	};
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_alexarank);

        backButton = (ImageButton)findViewById(R.id.alexaBackButton);
       // shareButton = (ImageButton)findViewById(R.id.alexaShareButton);
        saveButton = (ImageButton)findViewById(R.id.alexaSaveButton);
	    alexaTitleTextView = (TextView)findViewById(R.id.alexaTitle);
	    alexaDomainTextView = (TextView)findViewById(R.id.alexaDomainName);
	    alexaAdministratorTextView = (TextView)findViewById(R.id.alexaAdministrator);
	    alexaEmailTextView = (TextView)findViewById(R.id.alexaEmail);
	    alexaCurrentRankTextView = (TextView)findViewById(R.id.alexaCurrentRank);
	    alexaNextRankTextView = (TextView)findViewById(R.id.alexaNextRank);
	    alexaIncludeDateTextView = (TextView)findViewById(R.id.alexaIncludeDate);
	    alexaBelongToCountTextView = (TextView)findViewById(R.id.alexaBelongToCoun);
	    alexaCharsetTextView = (TextView)findViewById(R.id.alexaCharset);
	    alexaVisitSpeedTextView = (TextView)findViewById(R.id.alexaVisitSpeed);
	    alexaAdultTextView = (TextView)findViewById(R.id.alexaAdult);
	    alexaReverseLinkTextView = (TextView)findViewById(R.id.alexaReverseLink);
	    alexaPhoneTextView = (TextView)findViewById(R.id.alexaPhone);
	    alexaAddressTextView = (TextView)findViewById(R.id.alexaAddress);
	    alexaBelongToListTextView = (TextView)findViewById(R.id.alexaBelongToList);
	    alexaSummaryTextView = (TextView)findViewById(R.id.alexaDomainSummary);
	    alexaTitle2TextView = (TextView)findViewById(R.id.alexaTitle2);
	    alexaTodayRankTextView = (TextView)findViewById(R.id.alexaTodayRank);
	    alexaTodayRankGoTextView = (TextView)findViewById(R.id.alexaTodayGo);
	    alexaWeekRankTextView = (TextView)findViewById(R.id.alexaWeekRank);
	    alexaWeekRankGoTextView = (TextView)findViewById(R.id.alexaWeekGo);
	    alexaMonthRankTextView = (TextView)findViewById(R.id.alexaMonthRank);
	    alexaMonthRankGoTextView = (TextView)findViewById(R.id.alexaMonthGo);
	    alexaThreeMonthRankTextView = (TextView)findViewById(R.id.alexaThreeMonthRank);
	    alexaThreeMonthRankGoTextView = (TextView)findViewById(R.id.alexaThreeMonthGo);
	    alexaDayIpTextView = (TextView)findViewById(R.id.alexaDayIP);
	    alexaDayPvTextView = (TextView)findViewById(R.id.alexaDayPV);
	    
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
                    case R.id.alexaBackButton:
                        AlexaActivity.this.finish();
                        break;
                    //点击了保存按钮
                    case R.id.alexaSaveButton:
                    	if(Utils.saveShot(System.currentTimeMillis()+"-alexa", AlexaActivity.this))
                    		Toast.makeText(AlexaActivity.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    	else 
                    		Toast.makeText(AlexaActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        backButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);



	    new Thread(alexaRunnable).start();
    }
	
	/**
	 * 开启线程抓取alexa信息
	 */
	Runnable alexaRunnable = new Runnable()
	{
		public void run()
		{
			alexaSpider = new AlexaSpider(domain);
			Bundle bundle = new Bundle();
			bundle.putString("title", alexaSpider.getTitle());
			bundle.putString("domain", alexaSpider.getDomainName());
			bundle.putString("administrator", alexaSpider.getAdminstrator());
			bundle.putString("email", alexaSpider.getEmail());
			bundle.putString("currentRank", alexaSpider.getCurrentRank());
			bundle.putString("nextRank", alexaSpider.getNextRank());
			bundle.putString("includeDate", alexaSpider.getIncludeDate());
			bundle.putString("belongToCount", alexaSpider.getBelongToCount());
			bundle.putString("charset", alexaSpider.getCharset());
			bundle.putString("visitSpeed", alexaSpider.getVisitSpeed());
			bundle.putString("adult", alexaSpider.getAdult());
			bundle.putString("reverseLink", alexaSpider.getReverseLink());
			bundle.putString("phone", alexaSpider.getPhone());
			bundle.putString("address", alexaSpider.getAddress());
			bundle.putString("belongToList", alexaSpider.getBelongToList());
			bundle.putString("summary", alexaSpider.getDomainSummary());
			
			bundle.putString("title2", alexaSpider.getTitle2());
			bundle.putString("todayRank", alexaSpider.getTodayRank());
			bundle.putString("todayRankGo", alexaSpider.getTodayRankGo());
			bundle.putString("weekRank", alexaSpider.getWeekRank());
			bundle.putString("weekRankGo", alexaSpider.getWeekRankGo());
			bundle.putString("monthRank", alexaSpider.getMonthRank());
			bundle.putString("monthRankGo", alexaSpider.getMonthRankGo());
			bundle.putString("threeMonthRank", alexaSpider.getThreeMonthRank());
			bundle.putString("threeMonthRankGo", alexaSpider.getThreeMonthRankGo());
			bundle.putString("dayIP", alexaSpider.getDayIP());
			bundle.putString("dayPV", alexaSpider.getDayPV());
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};

}
