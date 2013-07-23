/**
 * 
 */
package com.yeetrac.spider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xuemeng
 *
 */
public class WebsiteFriendLinkSpider
{
	/**
	 * 用户输入的域名
	 */
	private String domain;
	/**
	 * 百度收录个数
	 */
	private String baiduCount;
	/**
	 * 百度快照时间
	 */
	private String baiduMirror;

	/**
	 * 出站链接数量
	 */
	private String outLinkCount;
	/**
	 * 百度今日收录个数
	 */
	private String baiduTodayCount;
	
	/**
	 * alexa排名
	 */
	private String alexaRank;
	
	/**
	 * 百度首页位置
	 */
	private String baiduHomePage;
	
	/**
	 * 百度权重
	 */
	private String baiduRank;
	
	/**
	 * 谷歌权重
	 */
	private String googleRank;
	
	/**
	 * cookie
	 */
	BasicCookieStore cookieStore;
	
	/**
	 * 出站链接列表
	 */
	private ArrayList<String> outLinkList; 
	
	
	
	public WebsiteFriendLinkSpider(String domain)
	{
		this.domain = domain;
		outLinkList = new ArrayList<String>();
		if(cookieStore == null)
			setCookie();
		new Thread(outLinkListRunnable).start();
		new Thread(baiduRankRunnable).start();
		new Thread(googleRankRunnable).start();
	}
	
	/**
	 * 获取网站外链个数
	 */
	Runnable outLinkListRunnable = new Runnable()
	{
		public void run()
		{
			HttpGet linkGet = new HttpGet("http://link.gongju.com/?csrf=&next=605135&address="+domain+"&header=none&submit=%E6%9F%A5%E8%AF%A2&checknofollow=1&checkpr=1&checkbaiduinfo=1&checkalexa=1&jiaocha=&check_addr=");
			DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
			HttpResponse linkGetResponse;
			try
			{
				
				httpClient.setCookieStore(cookieStore);
				linkGetResponse = httpClient.execute(linkGet);
				
				String response = HttpTool.getEntityContent(linkGetResponse.getEntity());
				//获取出站链接的个数
				int start = response.indexOf("<td id=\"out_link\"><a href=\"http://link.gongju.com/#url="+domain);
				//<td id="out_link"><a href="http://link.gongju.com/#url=yeetrack.com" target="_blank">7 个</a></td></tr></tbody>
				int end = response.indexOf("</a>", start+22);
				if(start== -1 || end==-1)
					outLinkCount = "0";
				else
					outLinkCount = response.substring(start+domain.length()+73, end);
		    
				//获取出站链接列表
				start = response.indexOf("<div class=\"site_url\" rel=\"");
				while(start != -1)
				{
					end = response.indexOf("</div>", start+10);
					if(end == -1)
						continue;
					String loopString = response.substring(start, end);
					int spanStart = loopString.indexOf("<span><a href=");
					int spanEnd = loopString.indexOf("</span>");
					if(spanStart==-1 || spanEnd==-1)
						continue;
					outLinkList.add(loopString.substring(spanStart+6, spanEnd));
					start = response.indexOf("<div class=\"site_url\" rel=\"", start+5);
				}
				
				//获取alexa、百度快照等
				
				HttpGet baiduAlexaGet = new HttpGet("http://link.gongju.com/link/info/?url="+domain);
				HttpResponse baiduAlexaResponse = null;
		        baiduAlexaResponse = httpClient.execute(baiduAlexaGet);
		        response = HttpTool.getEntityContent(baiduAlexaResponse.getEntity());
		          
		        JSONObject jsonObject = new JSONObject(response);
		        
		        alexaRank = jsonObject.getString("alexa_rank");
		        baiduCount = jsonObject.getString("bai_lu");
		        baiduHomePage = jsonObject.getString("rank");
		        baiduMirror = jsonObject.getString("kuaizhao");
		        baiduTodayCount = jsonObject.getString("today_lu");
			} catch (ClientProtocolException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } finally
			{
				httpClient.getConnectionManager().shutdown();
			}
		}
	};
	
	/**
	 * 获取百度权重
	 */
	Runnable baiduRankRunnable = new Runnable()
	{
		public void run()
		{
			//GET /link/get_pr/?url=yeetrack.com HTTP/1.1
			DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
			httpClient.setCookieStore(cookieStore);
			HttpGet baiduRankGet = new HttpGet("http://link.gongju.com/link/get_br/?url="+domain);
			try
            {
	            HttpResponse response = httpClient.execute(baiduRankGet);
	            String responseString = HttpTool.getEntityContent(response.getEntity());
	            baiduRank = responseString.trim();
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
	
	/**
	 * 获取谷歌权重
	 */
	Runnable googleRankRunnable = new Runnable()
	{
		public void run()
		{
			//GET /link/get_pr/?url=yeetrack.com HTTP/1.1
			DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
			httpClient.setCookieStore(cookieStore);
			HttpGet baiduRankGet = new HttpGet("http://link.gongju.com/link/get_pr/?url="+domain);
			try
            {
	            HttpResponse response = httpClient.execute(baiduRankGet);
	            String responseString = HttpTool.getEntityContent(response.getEntity());
	            googleRank = responseString.trim();
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
	public String getBaiduCount()
	{
		waitForTimeOut(baiduCount);
		return baiduCount;
	}

	public String getBaiduMirror()
	{
		waitForTimeOut(baiduMirror);
		return baiduMirror;
	}

	public String getOutPR()
	{
		//pr输出值是计算出来的
		//计算方法 1-0.85+0.85*(PR/出站链接数)
		String prString = getGoogleRank();
		String linkCount = getOutLinkCount();
		if(prString==null || linkCount == null || (!linkCount.contains("个")))
			return "0.15";
		int count = Integer.parseInt(linkCount.substring(0, linkCount.length()-1).trim());
		int pr = Integer.parseInt(prString);
		DecimalFormat dFormat = new DecimalFormat("#.##");
		double outDouble = 1 - 0.85 + 0.85*(pr/count);
		String outString = dFormat.format(outDouble);
		return outString;
	}

	public String getOutLinkCount()
	{
		waitForTimeOut(outLinkCount);
		return outLinkCount;
	}
	
	public String getBaiduTodayCount()
	{
		waitForTimeOut(baiduTodayCount);
		return baiduTodayCount;
	}

	public String getAlexaRank()
	{
		waitForTimeOut(alexaRank);
		return alexaRank;
	}

	public String getBaiduRank()
	{
		waitForTimeOut(baiduRank);
		return baiduRank;
	}
	
	public String getGoogleRank()
	{
		waitForTimeOut(googleRank);
		return googleRank;
	}

	public String getBaiduHomePage()
	{
		waitForTimeOut(baiduHomePage);
		return baiduHomePage;
	}

	public ArrayList<String> getOutLinkList()
	{
		waitForTimeOut(outLinkList);
		return outLinkList;
	}
	public void waitForTimeOut(Object object)
	{
		int time = 0;
		while(object == null)
		{
			try
            {
	            Thread.sleep(500);
	            time += 500;
            } catch (InterruptedException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
			//10秒超时
			if(time >= 15000)
				break;
		}
	}
	
	/**
	 * 获取网站的cookie
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void setCookie()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet linkGet = new HttpGet("http://link.gongju.com/?csrf=&next=605135&address="+domain+"&header=none&submit=%E6%9F%A5%E8%AF%A2&checknofollow=1&checkpr=1&checkbaiduinfo=1&checkalexa=1&jiaocha=&check_addr=");
		HttpResponse linkGetResponse;
        try
        {
	        linkGetResponse = httpClient.execute(linkGet);
	      //貌似这个网站上的cookie自动解析不成功，手动处理下
			cookieStore = new BasicCookieStore();
			
			for(Header header : linkGetResponse.getAllHeaders())
			{
				if("Set-Cookie".equals(header.getName()))
				{
					String cookieValue = header.getValue();
					int end = cookieValue.indexOf("\"", 15);
					String valueString = cookieValue.substring(8, end+1);
					BasicClientCookie cookie = new BasicClientCookie("session", valueString);
					cookie.setDomain("gongju.com");
					cookie.setPath("/");
					cookieStore.addCookie(cookie);
					break;
				}
			}
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
}
