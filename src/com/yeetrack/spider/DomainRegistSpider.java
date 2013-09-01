/**
 * 
 */
package com.yeetrack.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author youthflies
 * 检查指定的域名是否被注册
 */
public class DomainRegistSpider
{
	private String domain;
	//常见的域名后缀
	private String[] postfix =  {"com", "net", "org", "cn", "me",
			"biz", "co","gov.cn", "org.cn","中国", "net.cn", "name",
			"info", "tv", "so", "mobi", "cc" ,"tel", "asia"};
	private List<Map<String, Object>> resultList; 
	
	public DomainRegistSpider(String domain)
    {
		if(domain==null || "".equals(domain))
			throw new NullPointerException();
	    this.domain = domain;
	    resultList = new ArrayList<Map<String,Object>>();
    }
	
	/**
	 * 访问www.net.cn抓取结果
	 */
	private void spider()
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		for(String ite : postfix)
		{
			HttpGet get = new HttpGet("http://pandavip.www.net.cn/check/check_ac1.cgi?domain="+domain+"."+ite);
			try
            {
	            HttpResponse response = httpClient.execute(get);
	            String resultString = EntityUtils.toString(response.getEntity());
//	            System.out.println(resultString);
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("domainResult", resultString);
	            resultList.add(map);
            } catch (ClientProtocolException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } catch (IOException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		httpClient.getConnectionManager().shutdown();
	}
	
	public List<Map<String, Object>> getResultList()
	{
		spider();
		return resultList;
	}
}
