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
 * 查询域名谷歌收录页面个数
 */
public class WebsiteGooleSpider
{
	private String googleCount;
	private String domain;
	
	public WebsiteGooleSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet googleGet = new HttpGet("http://www.google.com/search?q=site:"+domain);
		try
        {
	        HttpResponse googleResponse = httpClient.execute(googleGet);
	        String response = HttpTool.getEntityContent(googleResponse.getEntity());
	        //<div id="resultStats">找到约 747 条结果<nobr>  （用时 0.17 秒）&nbsp;</nobr></div>
	        //<div id="resultStats">获得 2 条结果<nobr>  （用时 0.11 秒）&nbsp;</nobr></div>
	        int googleStart = response.indexOf("<div id=\"resultStats\">");
	        if(googleStart == -1)
	        {
	        	googleCount = "0";
	        	return;
	        }
	        String countString = response.substring(googleStart+22);
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
	        googleCount = countBuffer.toString();
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
	
	public String getGoogleCount()
	{
		return googleCount;
	}
}
