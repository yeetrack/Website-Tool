/**
 * 
 */
package com.yeetrack.spider;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * @author xuemeng
 * 访问域名，获取其中的title和describe
 */
public class WebsitePageSpider
{
	private DefaultHttpClient httpClient;
	private String title;
	private String desc;
	private String domain;
	
	public WebsitePageSpider(String domain)
	{
		this.domain = domain;
		httpClient = HttpTool.getHttpClientInstance();
		spider();
	}
	
	public void spider()
	{
		HttpGet titleGet = new HttpGet("http://"+domain);
		HttpResponse titleResponse = null;
		String response = null;
		try
        {
	        titleResponse = httpClient.execute(titleGet);
	        HttpEntity entity = titleResponse.getEntity();
	        response = HttpTool.getEntityContent(entity);
	        
	        //获取标题
	        int start = response.indexOf("<title>");
	        int end = response.indexOf("</title>");
	        
	        if(start == -1 || end == -1)
	        	title = domain;
	        else 
	        	title = response.substring(start+7, end);
	        
	        //获取网站描述 <meta name="description" content="专注于原创技术博客，并分享精品技术文章">
	        int descStart = response.indexOf("<meta name=\"description\"");
	        int descStart2 = response.indexOf("content=\"", descStart);
	        int descEnd = response.indexOf(">", descStart2+9);
	        if(descStart == -1 || descEnd == -1)
	        	desc = title;
	        else
	        	desc = response.substring(descStart2+9, descEnd-1);
	        
        } catch (ClientProtocolException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch(Exception e)
        {
        	e.printStackTrace();
        }finally
        {
        	httpClient.getConnectionManager().shutdown();
        }
	}

	public String getTitle()
	{
		return title;
	}

	public String getDesc()
	{
		return desc;
	}
	
}
