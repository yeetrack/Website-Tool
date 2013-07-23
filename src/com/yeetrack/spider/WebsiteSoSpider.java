/**
 * 
 */
package com.yeetrack.spider;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author xuemeng
 * 抓取网站360收录个数
 */
public class WebsiteSoSpider
{
	private String domain;
	private String soCount;
	
	public WebsiteSoSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		//http://www.so.com/s?q=site:"+domain);
		//http://www.baidu.com/s?wd=site%3A"+domain);
		HttpGet soGet = new HttpGet("http://www.so.com/s?q=site:"+domain);
		try
        {
	        HttpResponse googleResponse = httpClient.execute(soGet);
	        String response = HttpTool.getEntityContent(googleResponse.getEntity());
	        //<p class="ws-total">找到相关结果约32个</p>
	        int soStart = response.indexOf("<p class=\"ws-total\">找");
	        if(soStart == -1)
	        {
	        	soCount = "0";
	        	return;
	        }
	        String countString = response.substring(soStart+22);
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
	        soCount = countBuffer.toString();
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
	
	public String getSoCount()
	{
		return soCount;
	}
}
