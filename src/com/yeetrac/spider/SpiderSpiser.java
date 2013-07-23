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
 * 抓取gongju.com 蜘蛛模拟页面
 */
public class SpiderSpiser
{
	private String domain;
	private String title;
	private String h1;
	private String h2;
	private String h3;
	private String body;
	
	public SpiderSpiser(String domain)
	{
		this.domain = domain;
		spider();
	}
	
	public void spider()
	{
		//GET /?csrf=1&next=8834805&address=yeetrack.com&submit=%E6%9F%A5%E8%AF%A2 HTTP/1.1
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet get = new HttpGet("http://robot.gongju.com//?csrf=1&next=8834805&address="+domain+"&submit=%E6%9F%A5%E8%AF%A2");
		try
        {
	        HttpResponse response = httpClient.execute(get);
	        String responseHtml = HttpTool.getEntityContent(response.getEntity());
	        int titleStart = responseHtml.indexOf("标题（title）</td>");
	        int titleEnd = responseHtml.indexOf("</td>", titleStart+14);
	        if(titleStart!=-1 && titleEnd!=-1)
	        	title = responseHtml.substring(titleStart+25, titleEnd);
	        int h1Start0 = responseHtml.indexOf("H1标签</td>");
	        int h1Start = responseHtml.indexOf("<h1>", h1Start0);
	        int h1End = responseHtml.indexOf("</h1>", h1Start+4);
	        h1 = "";
	        while(h1Start!=-1 && h1End!=-1)
	        {
	        	h1 = h1 + responseHtml.substring(h1Start+4, h1End)+ "\n";
	        	h1Start = responseHtml.indexOf("<h1>", h1End);
	        	h1End = responseHtml.indexOf("</h1>", h1Start);
	        }
	        
	        int h2Start0 = responseHtml.indexOf("H2标签</td>");
	        int h2Start = responseHtml.indexOf("<h2>", h2Start0);
	        int h2End = responseHtml.indexOf("</h2>", h2Start+4);
	        h2 = "";
	        while(h2Start!=-1 && h2End!=-1)
	        {
	        	h2 = h2+ responseHtml.substring(h2Start+4, h2End) +"\n";
	        	h2Start = responseHtml.indexOf("<h2>", h2End);
	        	h2End = responseHtml.indexOf("</h2>", h2Start);
	        }
	        
	        int h3Start0 = responseHtml.indexOf("H3标签</td>");
	        int h3Start = responseHtml.indexOf("<h3>", h3Start0);
	        int h3End = responseHtml.indexOf("</h3>", h3Start+4);
	        h3 = "";
	        while(h3Start!=-1 && h3End!=-1)
	        {
	        	h3 = h3+ responseHtml.substring(h3Start+4, h3End) +"\n";
	        	h3Start = responseHtml.indexOf("<h3>", h3End);
	        	h3End = responseHtml.indexOf("</h3>", h3Start);
	        }
	        int bodyStart = responseHtml.indexOf("<div class=\"outbox\">");
	        int bodyEnd = responseHtml.indexOf("</body>");
	        if(bodyStart!=-1 && bodyEnd !=-1)
	        	body = responseHtml.substring(bodyStart+20, bodyEnd);
	        else {
				body = "";
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

	public String getTitle()
	{
		return title;
	}

	public String getH1()
	{
		return h1;
	}

	public String getH2()
	{
		return h2;
	}

	public String getH3()
	{
		return h3;
	}
	public String getBody()
	{
		return body;
	}
	
}
