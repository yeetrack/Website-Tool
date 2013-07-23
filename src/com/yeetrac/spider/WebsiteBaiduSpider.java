/**
 * 
 */
package com.yeetrac.spider;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author xuemeng
 * 抓取网站baidu收录个数
 */
public class WebsiteBaiduSpider
{
	private String domain;
	private String baiduCount;
	
	public WebsiteBaiduSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		//http://www.so.com/s?q=site:"+domain);
		//http://www.baidu.com/s?wd=site%3A"+domain);
		HttpGet baiduGet = new HttpGet("http://www.baidu.com/s?wd=site%3A"+domain);
		try
        {
	        HttpResponse googleResponse = httpClient.execute(baiduGet);
	        String response = HttpTool.getEntityContent(googleResponse.getEntity());
	        //<p class="site_tip"><strong>找到相关结果数6个。</strong>
	        int baiduStart = response.indexOf("<p class=\"site_tip\"><strong>找到");
	        if(baiduStart == -1)
	        {
	        	baiduCount = "0";
	        	return;
	        }
	        String countString = response.substring(baiduStart+30);
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
	        baiduCount = countBuffer.toString();
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
	
	public String getBaiduCount()
	{
		return baiduCount;
	}
}
