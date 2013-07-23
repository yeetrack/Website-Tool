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
 * 查询域名的备案信息
 */
public class WebsiteGCDSpider
{
	private String domain;
	private String icq;
	
	public WebsiteGCDSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		
		//http://icp.alexa.cn/?q=yeetrackcom查询备案信息
		HttpGet icqGet = new HttpGet("http://icp.alexa.cn/?q="+domain);
		try
        {
	        HttpResponse icqResponse = httpClient.execute(icqGet);
	        String response = HttpTool.getEntityContent(icqResponse.getEntity());
	        int icqStart = response.indexOf("<a style=\"color:green;cursor:hand\" href=\"");
	        int icqEnd = response.indexOf("</td>", icqStart);
	        if(icqStart == -1 || icqEnd == -1)
	        	icq = "无备案信息";
	        else
	        	icq = response.substring(icqStart, icqEnd);
	        
        } catch (ClientProtocolException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (Exception e)
        {
        	httpClient.getConnectionManager().shutdown();
        }
	}
	
	public String getIcq()
	{
		return icq;
	}
}
