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
 * 抓取域名的seo信息
 * 谷歌权重的信息来自http://pr.alexa.cn/
 */
public class WebsiteSEOSpider
{
	private DefaultHttpClient httpClient;
	private String domain;
	String googlePage;
	String baiduPage;
	String alexaRank;
	String sougouRank;
	public WebsiteSEOSpider(String domain)
	{
		this.domain = domain;
		httpClient = HttpTool.getHttpClientInstance();
		spider();
	}
	
	public void spider()
	{
		//查询Google权重
		HttpGet prGet = new HttpGet("http://pr.alexa.cn/?url="+domain+"#");
		try
        {
	        HttpResponse prResponse = httpClient.execute(prGet);
	        HttpEntity entity = prResponse.getEntity();
	        String response = HttpTool.getEntityContent(entity);
	        int prStart = response.indexOf("style='margin-left:218px;'>");
	        int prEnd = response.indexOf("</span></li>");
	        if(prStart==-1 || prEnd == -1)
	        	googlePage = "-1";
	        else
	        {
	        	String prString = response.substring(prStart, prEnd);
	        	googlePage = prString.substring(27);
	        }
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
        	e.printStackTrace();
        	httpClient.getConnectionManager().shutdown();
        }
		//查询百度权重
		HttpGet brGet = new HttpGet("http://www.aizhan.com/getbr.php?url="+domain+"&style=1");
		try
        {
	        HttpResponse bResponse = httpClient.execute(brGet);
	        HttpEntity entity = bResponse.getEntity();
	        String response = HttpTool.getEntityContent(entity);
	        int brStart = response.indexOf("\">");
	        int brEnd = response.lastIndexOf("</a>'");
	        if(brStart == -1 || brEnd == -1)
	        	baiduPage = "-1";
	        else
	        	baiduPage = response.substring(brStart+2, brEnd);
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
        	e.printStackTrace();
        	httpClient.getConnectionManager().shutdown();
        } 
		//查询搜狗排名
		HttpGet sgGet = new HttpGet("http://rank.ie.sogou.com/sogourank.php?ur=http%3A%2F%2F"+domain+"%2F");
		try
        {
	        HttpResponse sgResponse = httpClient.execute(sgGet);
	        HttpEntity entity = sgResponse.getEntity();
	        sougouRank = HttpTool.getEntityContent(entity);
	        sougouRank= sougouRank.trim().substring(10);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
		
		//查询Alexa排名
		HttpGet alexaGet = new HttpGet("http://data.alexa.com/data?cli=10&dat=snbamz&url=http://"+domain);
		try
        {
	        HttpResponse alexaResponse = httpClient.execute(alexaGet);
	        String response = HttpTool.getEntityContent(alexaResponse.getEntity());
	        int alexaStart = response.indexOf("<POPULARITY URL=\""+domain+"/\" TEXT=\"");
	        int alexaEnd = response.indexOf("\" SOURCE");
	        if(alexaStart == -1 || alexaEnd == -1)
	        	alexaRank = "-1";
	        else
	        	alexaRank = response.substring(alexaStart+26+domain.length(), alexaEnd);
	        
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

	public String getGooglePage()
	{
		return googlePage;
	}

	public String getBaiduPage()
	{
		return baiduPage;
	}
	public String getAlexaRank()
	{
		return alexaRank;
	}
	public String getSogouRank()
	{
		return sougouRank;
	}
}
