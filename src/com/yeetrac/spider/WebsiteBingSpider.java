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
public class WebsiteBingSpider
{
	private String domain;
	private String bingCount;
	
	public WebsiteBingSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		//http://www.so.com/s?q=site:"+domain);
		//http://www.baidu.com/s?wd=site%3A"+domain);
		HttpGet bingGet = new HttpGet("http://cn.bing.com/search?q=site%3A"+domain);
		try
        {
	        HttpResponse bingResponse = httpClient.execute(bingGet);
	        String response = HttpTool.getEntityContent(bingResponse.getEntity());
	        //<div class="sb_rc_btm sb_rc_btm_p">16 条结果</div>
	        int baiduStart = response.indexOf("<div class=\"sb_rc_btm sb_rc_btm_p\">");
	        if(baiduStart == -1)
	        {
	        	bingCount = "0";
	        	return;
	        }
	        String countString = response.substring(baiduStart+35);
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
	        bingCount = countBuffer.toString();
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
	
	public String getBingCount()
	{
		return bingCount;
	}
}
