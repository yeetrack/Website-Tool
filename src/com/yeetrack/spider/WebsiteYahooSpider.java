package com.yeetrack.spider;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author xuemeng
 * 抓取网站yahoo收录个数
 */
public class WebsiteYahooSpider
{
	private String domain;
	private String yahooCount;
	
	public WebsiteYahooSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		//http://www.so.com/s?q=site:"+domain);
		//http://www.baidu.com/s?wd=site%3A"+domain);
		HttpGet yahooGet = new HttpGet("http://www.yahoo.cn/s?q=site:"+domain);
		try
        {
	        HttpResponse yahooResponse = httpClient.execute(yahooGet);
	        String response = HttpTool.getEntityContent(yahooResponse.getEntity());
	        //<div class="s_info">找到相关网页约205条</div>
	        int yahooStart = response.indexOf("<div class=\"s_info\">找到");
	        if(yahooStart == -1)
	        {
	        	yahooCount = "0";
	        	return;
	        }
	        String countString = response.substring(yahooStart+22);
	        StringBuffer countBuffer = new StringBuffer();
	        int offset = 0;
	        while (true)
            {  
	        	if(countString.charAt(offset)>='0' && countString.charAt(offset)<='9')
	        		countBuffer.append(countString.charAt(offset));
	        	else if(countString.charAt(offset) == '<')
	        		break;
	        	offset++;
            }
	        yahooCount = countBuffer.toString();
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
	
	public String getYahooCount()
	{
		return yahooCount;
	}
}
