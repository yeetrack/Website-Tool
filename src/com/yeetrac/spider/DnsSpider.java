/**
 * 
 */
package com.yeetrac.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author xuemeng
 * 抓取DNS
 */
public class DnsSpider
{
	private String domain;
	private List<List<String>> result;
	public DnsSpider(String domain)
    {
	    this.domain = domain;
	    result = new ArrayList<List<String>>();
	    spider();
    }
	
	public void spider()
	{
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet get = new HttpGet("http://www.jiasule.com/tools/dnslookup/?domain="+domain);
		try
        {
	        HttpResponse response = httpClient.execute(get);
	        Header[] headers = response.getAllHeaders();
	        String token = null;
	        for(Header header : headers)
	        {
	        	if("Set-Cookie".equals(header.getName()))
	        	{
	        		token = header.getValue();
	        		break;
	        	}
	        	
	        }
	        int tokenEnd = token.indexOf(";");
	        token = token.substring(10, tokenEnd);
	        HttpTool.getEntityContent(response.getEntity());
	        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
	        params.add(new BasicNameValuePair("csrfmiddlewaretoken", token));
	        params.add(new BasicNameValuePair("domain", domain));
	        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf-8");
	        HttpPost post = new HttpPost("http://www.jiasule.com//tools/dnslookup/?domaim="+domain);
	        post.setEntity(urlEncodedFormEntity);
	        HttpResponse response2 = httpClient.execute(post);
	        String responseHtml = HttpTool.getEntityContent(response2.getEntity());
	        
	        //取得开始位置
	        int start = responseHtml.indexOf("<td>DNS所在地</td><td>类型</td><td>响应值</td><td>响应IP</td><td>IP所在地</td><td>加速乐</td>");
	        start = responseHtml.indexOf("<tr>", start+10);
	        //开始循环处理结果列表
	        while(start != -1)
	        {
	        	List<String> itemList = new ArrayList<String>();
	        	int end = responseHtml.indexOf("</tr>", start);
	        	String temResult = responseHtml.substring(start+4, end).trim();
	        	String temResult2 = temResult.replace("<td>", "");
	        	String[] results = temResult2.split("</td>");
	        	for(int i=0; i<=results.length-2;i++)
	        	{
	        		results[i]= results[i].trim();
	        		itemList.add(results[i]);	
	        	}
	        	result.add(itemList);
	        	start = responseHtml.indexOf("<tr>", end);
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
	
	public List<List<String>> getResult()
	{
		return result;
	}
}
