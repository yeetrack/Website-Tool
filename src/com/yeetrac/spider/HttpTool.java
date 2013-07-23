/**
 * 
 */
package com.yeetrac.spider;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

/**
 * @author xuemeng
 * 根据html页面的编码，读取HttpEntity
 */
public class HttpTool
{
	/**
	 * 获取httpclient
	 */
	public static DefaultHttpClient getHttpClientInstance()
	{
		HttpParams params = new BasicHttpParams();
		String userAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";  
		//String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; qdesk 2.5.1277.202; Windows NT 6.2; WOW64; Trident/6.0)";
		HttpProtocolParams.setUserAgent(params, userAgent);
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		return httpClient;
	}
	/**
	 * 获取默认的HttpClient
	 */
	public static DefaultHttpClient getDefaultHttpClientInstance()
	{
		return new DefaultHttpClient();
	}
	/**
	 * 获取IE7 HttpClient
	 */
	public static DefaultHttpClient getIE7HttpClient()
	{
		HttpParams params = new BasicHttpParams();
		String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)";  
		HttpProtocolParams.setUserAgent(params, userAgent);
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		return httpClient;
	}
	/**
	 * 自动根据页面编码获取页面内容
	 */
	public static String getEntityContent(HttpEntity entity)
	{
		String response = null;
		if(entity != null)
	        try
            {
	            entity = new BufferedHttpEntity(entity);
	            response = EntityUtils.toString(entity, "utf-8");
            } catch (IOException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
        
        //取到的数据可能中文乱码，需要调整编码
        //<meta http-equiv="Content-type" content="text/html; charset=gb2312" />
        String reg = "<meta.*charset.*/>";
        String charset = null;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(response);
		if(matcher.find())
		{
			charset = matcher.group();
			//结果可能有引号，最后要消除引号
			int charsetStart = charset.indexOf("charset=");
			int charsetEnd = charset.indexOf("\"", charsetStart+11);
			charset = charset.substring(charsetStart+8, charsetEnd);
			charset = charset.replace("\"", "");
			try
            {
	            response = EntityUtils.toString(entity, charset);
            } catch (ParseException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            } catch (IOException e)
            {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		return response;
	}
}
