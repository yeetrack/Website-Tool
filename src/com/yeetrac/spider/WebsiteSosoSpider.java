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
 * 抓取网站搜搜收录个数
 */
public class WebsiteSosoSpider
{
	private String domain;
	private String sosoCount;
	
	public WebsiteSosoSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet sosoGet = new HttpGet("http://www.soso.com/q?w=site%3A"+domain);
		try
        {
	        HttpResponse googleResponse = httpClient.execute(sosoGet);
	        String response = HttpTool.getEntityContent(googleResponse.getEntity());
	        //<div id="sInfo">搜搜为您找到78条相关结果</div>
	        int sosoStart = response.indexOf("<div id=\"sInfo\">搜");
	        if(sosoStart == -1)
	        {
	        	sosoCount = "0";
	        	return;
	        }
	        String countString = response.substring(sosoStart+17);
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
	        sosoCount = countBuffer.toString();
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
	
	public String getsosoCount()
	{
		return sosoCount;
	}
}
