/**
 * 
 */
package com.yeetrack.spider;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @author xuemeng
 * 抓取域名whois信息
 */
public class WhoisSpider
{
	private String domain;
	private String domainResult;
	/**
	 * 注册商
	 */
	private String registrar;
	/**
	 * 域名服务器
	 */
	private String whoisServer;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 过期时间
	 */
	private String expireTime;
	/**
	 * 注册人
	 */
	private String adminstrator;
	/**
	 * 注册邮箱URL
	 */
	private String emailURL;
	/**
	 * 注册邮箱后缀
	 */
	private String emailServer;
	/**
	 * 注册电话
	 */
	private String phone;
	/**
	 * DNS服务器
	 */
	private String dnsServer;
	
	public WhoisSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	/**
	 * 爬取whois信息
	 */
	public void spider()
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://www.whois.com/whois/"+domain);
		try
        {
	        HttpResponse response = httpClient.execute(get);
	        String responseHtml = HttpTool.getEntityContent(response.getEntity());
	        int domainStart = responseHtml.indexOf("registryData\">Domain Name:");
	        int domainEnd = responseHtml.indexOf("<br>", domainStart);
	        if(domainStart!=-1 && domainEnd!=-1)
	        	domainResult = responseHtml.substring(domainStart+26, domainEnd);
	        else
	        	domainResult = "null";
	        int registrarStart = responseHtml.indexOf("Registrar:");
	        int registrarEnd = responseHtml.indexOf("<br>", registrarStart);
	        if(registrarStart!=-1 && registrarEnd!=-1)
	        	registrar = responseHtml.substring(registrarStart+10, registrarEnd);
	        else
	        	registrar = "null";
	        int whoisServerStart = responseHtml.indexOf("Whois Server:");
	        int whoisServerEnd = responseHtml.indexOf("<br>", whoisServerStart);
	        if(whoisServerStart!=-1 && whoisServerEnd!=-1)
	        	whoisServer = responseHtml.substring(whoisServerStart+13, whoisServerEnd);
	        else {
				whoisServer = "null";
			}
	        int dnsServerStart = responseHtml.indexOf("Name Server:");
	        int dnsServerEnd = responseHtml.indexOf("<br>", dnsServerStart);
	        if(dnsServerStart!=-1 && dnsServerEnd!=-1)
	        	dnsServer = responseHtml.substring(dnsServerStart+12, dnsServerEnd);
	        else 
	        	dnsServer="null";
	        dnsServerStart = responseHtml.indexOf("Name Server:", dnsServerEnd);
	        dnsServerEnd = responseHtml.indexOf("<br>", dnsServerStart);
	        while(dnsServerStart!=-1 && dnsServerEnd!=-1)
	        {
	        	dnsServer= dnsServer+", "+responseHtml.substring(dnsServerStart+12, dnsServerEnd);
	        	dnsServerStart = responseHtml.indexOf("Name Server:", dnsServerEnd);
		        dnsServerEnd = responseHtml.indexOf("<br>", dnsServerStart);
	        }
	        int statusStart = responseHtml.indexOf("Status:");
	        int statusEnd = responseHtml.indexOf("<br>", statusStart);
	        if(statusStart!=-1 && statusEnd!=-1)
	        	status = responseHtml.substring(statusStart+7, statusEnd);
	        else {
				status = "null";
			}
	        int updateTimeStart = responseHtml.indexOf("Updated Date:");
	        int updateTimeEnd = responseHtml.indexOf("<br>", updateTimeStart);
	        if(updateTimeStart!=-1 && updateTimeEnd!=-1)
	        	updateTime = responseHtml.substring(updateTimeStart+13, updateTimeEnd);
	        else {
				updateTime = "null";
			}
	        int createTimeStart = responseHtml.indexOf("Creation Date:");
	        int createTimeEnd = responseHtml.indexOf("<br>", createTimeStart);
	        if(createTimeStart!=-1 && createTimeEnd!=-1)
	        	createTime = responseHtml.substring(createTimeStart+14, createTimeEnd);
	        else if(responseHtml.indexOf("<br>Registration Date:") != -1)
	        {
	        	int start = responseHtml.indexOf("<br>Registration Date:");
	        	int end = responseHtml.indexOf("<br>", start+22);
	        	createTime = responseHtml.substring(start+22, end);
	        }
	        else {
				createTime = "null";
			}
	        int expireTimeStart = responseHtml.indexOf("<br>Expiration Date:");
	        int expireTimeEnd = responseHtml.indexOf("<br>", expireTimeStart+10);
	        if(expireTimeStart!=-1 && expireTimeEnd!=-1)
	        	expireTime = responseHtml.substring(expireTimeStart+20, expireTimeEnd);
	        else
	        	expireTime = "null";
	        int administratorStart = responseHtml.indexOf("Registrant:<br>");
	        int administratorEnd1 = responseHtml.indexOf("<img", administratorStart);
	        int administratorEnd2 = responseHtml.indexOf("<br>", administratorStart+15);
	        int administratorEnd = administratorEnd1 < administratorEnd2 ? administratorEnd1 : administratorEnd2;
	        if(administratorStart!=-1 && administratorEnd!=-1)
	        	adminstrator = responseHtml.substring(administratorStart+15, administratorEnd).replace("&nbsp;", "");
	        else if(responseHtml.indexOf("<br>Registrant:")!=-1)
	        {
	        	administratorStart = responseHtml.indexOf("<br>Registrant:");
	        	administratorEnd = responseHtml.indexOf("<br>", administratorStart+15);
	        	adminstrator = responseHtml.substring(administratorStart+15, administratorEnd).replace("&nbsp;", "");
	        }
	        else 
	        	adminstrator = "null";
	        //最好用正则表达式  <img src="/eimg/f/04/f04f56ca92bcfd93ea7d658be8450d8889c66dde.png" class="whois_email" alt="email">
	        String regEx ="<img src=\".*\" class=\"whois_email\" alt=\"email";
	        Pattern pattern = Pattern.compile(regEx);
	        Matcher matcher = pattern.matcher(responseHtml);
	        if(matcher.find())
	        {
	        	String emailLink = matcher.group();
	        	int start = emailLink.indexOf("src=\"");
	        	int end = emailLink.indexOf("\" class");
	        	emailURL = emailLink.substring(start+5, end);
	        }
	        else 
	        	emailURL = "null";
	        int emailServerStart = responseHtml.indexOf("alt=\"email\">");
	        int emailServerEnd1 = responseHtml.indexOf(" ", emailServerStart);
	        int emailServerEnd2 = responseHtml.indexOf("<br>", emailServerStart);
	        int emailServerEnd = emailServerEnd1 < emailServerEnd2 ? emailServerEnd1 : emailServerEnd2 ;
	        if(emailServerStart!=-1 && emailServerEnd!=-1)
	        	emailServer = responseHtml.substring(emailServerStart+12, emailServerEnd);
	        else 
	        	emailServer = "null";
	        int phoneStart = responseHtml.indexOf("<br> tel --:");
	        int phoneEnd = responseHtml.indexOf("<br>", phoneStart+5);
	        if(phoneStart!=-1 && phoneEnd!=-1)
	        	phone = responseHtml.substring(phoneStart+12, phoneEnd);
	        else {
				phone = "null";
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
	public String getDomainResult()
	{
		return domainResult;
	}
	public String getRegistrar()
	{
		return registrar;
	}
	public String getWhoisServer()
	{
		return whoisServer;
	}
	public String getStatus()
	{
		return status;
	}
	public String getUpdateTime()
	{
		return updateTime;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public String getExpireTime()
	{
		return expireTime;
	}
	public String getAdminstrator()
	{
		return adminstrator;
	}
	public String getEmailURL()
	{
		return emailURL;
	}
	public String getEmailServer()
	{
		return emailServer;
	}
	public String getPhone()
	{
		return phone;
	}
	public String getDnsServer()
	{
		return dnsServer;
	}
	
}
