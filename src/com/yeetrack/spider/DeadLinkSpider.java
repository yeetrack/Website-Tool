/**
 * 
 */
package com.yeetrack.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author xuemeng
 * 检索死链
 */
public class DeadLinkSpider
{
	private String domain;
	private List<List<String>> urlList;
	
	public DeadLinkSpider(String domain)
	{
		this.domain = domain;
		urlList = new ArrayList<List<String>>();
		spider();
	}
	
	
	//抓取网站上的链接列表
	private void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet get = new HttpGet("http://www.gongju.com/links/?csrf=1&next=2775377&address="+domain+"&host_link=0&submit=%E6%A3%80%E6%9F%A5%E9%93%BE%E6%8E%A5");
		try
        {
	        HttpResponse response = httpClient.execute(get);
	        String responseHtml = HttpTool.getEntityContent(response.getEntity());
	        int start = responseHtml.indexOf("<tbody id=\"url_list\">");
	        int end = responseHtml.indexOf("</tbody>", start);
	        String[] listStrings = responseHtml.substring(start+20, end).trim().split("</tr>");
	        for(String item : listStrings)
	        {
	        	int titleStart = item.indexOf("[");
	        	int titleEnd = item.indexOf("]");
	        	List<String> itemList = new ArrayList<String>();
	        	itemList.add(item.substring(titleStart+1, titleEnd).trim());
	        	int hrefStart = item.indexOf("href=\"");
	        	int hrefEnd = item.indexOf("\"", hrefStart+6);
	        	itemList.add(item.substring(hrefStart+6, hrefEnd));
	        	urlList.add(itemList);
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


	public List<List<String>> getUrlList()
	{
		return urlList;
	}
	
}
