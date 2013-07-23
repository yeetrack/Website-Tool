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
 * 抓取Alexa信息
 */
public class AlexaSpider
{
	private String domain;
	private String title;
	private String domainName;
	private String adminstrator;
	private String email;
	private String currentRank;
	private String nextRank;
	private String includeDate;
	private String belongToCount;
	private String charset;
	private String visitSpeed;
	private String adult;
	private String reverseLink;
	private String phone;
	private String address;
	private String belongToList;
	private String domainSummary;
	
	private String title2;
	private String todayRank;
	private String todayRankGo;
	private String weekRank;
	private String weekRankGo;
	private String monthRank;
	private String monthRankGo;
	private String threeMonthRank;
	private String threeMonthRankGo;
	
	private String dayIP;
	private String dayPV;
	
	
	public AlexaSpider(String domain)
	{
		this.domain = domain;
		spider();
	}
	public void spider()
	{
		///?csrf=1&next=8139899&address=yeetrack.com&submit=%E9%A9%AC%E4%B8%8A%E6%9F%A5%E8%AF%A2
		DefaultHttpClient httpClient = HttpTool.getHttpClientInstance();
		HttpGet get = new HttpGet("http://alexa.gongju.com/?csrf=1&next=8139899&address="+domain+"&submit=%E9%A9%AC%E4%B8%8A%E6%9F%A5%E8%AF%A2");
		try
        {
	        HttpResponse response = httpClient.execute(get);
	        String responseHtml = HttpTool.getEntityContent(response.getEntity());
	        int titleStart = responseHtml.indexOf("<td colspan=\"6\">");
	        int titleEnd = responseHtml.indexOf("</td>", titleStart);
	        if(titleStart!=-1 && titleEnd!=-1)
	        	title = responseHtml.substring(titleStart, titleEnd);
	        else 
	        	title = "";
	        int domainNameStart = responseHtml.indexOf("站点名称:</td>");
	        domainNameStart = responseHtml.indexOf("title=\"", domainNameStart);
	        int domainNameEnd = responseHtml.indexOf("\">", domainNameStart);
	        if(domainNameStart!=-1 && domainNameEnd!=-1)
	        	domainName = responseHtml.substring(domainNameStart+7, domainNameEnd);
	        else 
	        	domainName = "null";
	        
	        int administratorStart = responseHtml.indexOf("网站站长:</td>");
	        administratorStart = responseHtml.indexOf("title=\"", administratorStart);
	        int administratorEnd = responseHtml.indexOf("\">", administratorStart);
	        if(administratorStart!=-1 && administratorEnd!=-1)
	        	adminstrator = responseHtml.substring(administratorStart+7, administratorEnd);
	        else 
	        	adminstrator = "null";
	        int emailStart = responseHtml.indexOf("电子信箱:</td>");
	        emailStart = responseHtml.indexOf("title=\"", emailStart);
	        int emailEnd = responseHtml.indexOf("\">", emailStart);
	        if(emailStart!=-1 && emailEnd!=-1)
	        	email = responseHtml.substring(emailStart+7, emailEnd);
	        else 
	        	email = "null";
	        int currentRankStart = responseHtml.indexOf("综合排名:</td>");
	        int currentRankEnd = responseHtml.indexOf("</span></td>", currentRankStart);
	        if(currentRankStart!=-1 && currentRankEnd!=-1)
	        	currentRank = responseHtml.substring(currentRankStart+33, currentRankEnd);
	        else 
	        	currentRank = "null";
	        int nextRankStart = responseHtml.indexOf("下期排名:</td>");
	        int nextRankEnd = responseHtml.indexOf("</span></td>", nextRankStart);
	        if(nextRankStart!=-1 && nextRankEnd!=-1)
	        	nextRank = responseHtml.substring(nextRankStart+33, nextRankEnd);
	        else 
	        	nextRank = "null";
	        int includeDateStart = responseHtml.indexOf("收录日期:</td>");
	        int includeDateEnd = responseHtml.indexOf("</td>", includeDateStart+14);
	        if(includeDateStart!=-1 && includeDateEnd!=-1)
	        	includeDate = responseHtml.substring(includeDateStart+27, includeDateEnd);
	        else 
	        	includeDate = "null";
	        int belongToCountStart = responseHtml.indexOf("所属国家:</td>");
	        int belongToCountEnd = responseHtml.indexOf("</td>", belongToCountStart+14);
	        if(belongToCountStart!=-1 && belongToCountEnd!=-1)
	        	belongToCount = responseHtml.substring(belongToCountStart+27, belongToCountEnd);
	        else 
	        	belongToCount = "null";
	        int charsetStart = responseHtml.indexOf("编码方式:</td>");
	        int charsetEnd = responseHtml.indexOf("</td>", charsetStart+14);
	        if(charsetStart!=-1 && charsetEnd!=-1)
	        	charset = responseHtml.substring(charsetStart+27, charsetEnd);
	        else 
	        	charset = "null";
	        int visitSpeedStart = responseHtml.indexOf("访问速度:</td>");
	        int visitSpeedEnd = responseHtml.indexOf("</td>", visitSpeedStart+14);
	        if(visitSpeedStart!=-1 && visitSpeedEnd!=-1)
	        	visitSpeed = responseHtml.substring(visitSpeedStart+27, visitSpeedEnd);
	        else 
	        	visitSpeed = "null";
	        int adultStart = responseHtml.indexOf("成人内容:</td>");
	        int adultEnd = responseHtml.indexOf("</td>", adultStart+14);
	        if(adultStart!=1 && adultEnd!=-1)
	        	adult = responseHtml.substring(adultStart+27, adultEnd);
	        else 
	        	adult = "null";
	        int reverseLinkStart = responseHtml.indexOf("反向链接:</td>");
	        int reverseLinkEnd = responseHtml.indexOf("</td>", reverseLinkStart+14);
	        if(reverseLinkStart!=-1 && reverseLinkEnd!=-1)
	        	reverseLink = responseHtml.substring(reverseLinkStart+27, reverseLinkEnd-1);
	        else 
	        	reverseLink = "null";
	        int phoneStart = responseHtml.indexOf("联系电话:</td>");
	        phoneStart = responseHtml.indexOf("<td title=\"", phoneStart);
	        int phoneEnd = responseHtml.indexOf("\">", phoneStart);
	        if(phoneStart!=-1 && phoneEnd!=-1)
	        	phone = responseHtml.substring(phoneStart+11, phoneEnd);
	        else 
	        	phone = "null";
	        int addressStart = responseHtml.indexOf("详细地址:</td>");
	        addressStart = responseHtml.indexOf("title=\"", addressStart);
	        int addressEnd = responseHtml.indexOf("\">", addressStart);
	        if(addressStart!=-1 && addressEnd!=-1)
	        	address = responseHtml.substring(addressStart+7, addressEnd);
	        else 
	        	address = "null";
	        int includeListStart = responseHtml.indexOf("所属目录:</td>");
	        includeListStart = responseHtml.indexOf("title=\"", includeListStart);
	        int includeListEnd = responseHtml.indexOf("\">",includeListStart);
	        if(includeListStart!=-1 && includeListEnd!=-1)
	        	belongToList = responseHtml.substring(includeListStart+7, includeListEnd);
	        else 
	        	belongToList = "null";
	        int summaryStart = responseHtml.indexOf("网站简介:</td>");
	        summaryStart = responseHtml.indexOf(">", summaryStart+11);
	        int summaryEnd = responseHtml.indexOf("</td>", summaryStart);
	        if(summaryStart!=-1 && summaryEnd!=-1)
	        	domainSummary = responseHtml.substring(summaryStart+1, summaryEnd);
	        else 
	        	domainSummary = "null";
	        
	        String regEx = "站点 <span>.*</span> 的流量排名查询结果 ";
	        Pattern pattern = Pattern.compile(regEx);
	        Matcher matcher = pattern.matcher(responseHtml);
	        if(matcher.find())
	        {
	        	title2 = matcher.group();
	        }
	        else 
	        	title2 = "null";
	        //获取当日排名
	        int todayRankStart = responseHtml.indexOf("的流量排名查询结果");
	        todayRankStart = responseHtml.indexOf("<tr", todayRankStart);
	        todayRankStart = responseHtml.indexOf("<tr>", todayRankStart);
	        int todayRankEnd = responseHtml.indexOf("</td>", todayRankStart);
	        if(todayRankStart!=-1 && todayRankEnd!=-1)
	        	todayRank = responseHtml.substring(todayRankStart+21, todayRankEnd);
	        else 
	        	todayRank = "null";
	        int todayRankGoStart = responseHtml.indexOf("<td>", todayRankEnd);
	        int todayRankGoEnd = responseHtml.indexOf("</span></td>", todayRankGoStart);
	        if(todayRankGoStart!=-1 && todayRankGoEnd!=-1)
	        	todayRankGo = upAndDown(responseHtml.substring(todayRankGoStart, todayRankGoEnd));
	        else 
	        	todayRankGo = "null";
	        int weekRankStart = responseHtml.indexOf("<td>", todayRankGoEnd);
	        int weekRankEnd = responseHtml.indexOf("</td>", weekRankStart);
	        if(weekRankStart!=-1 && weekRankEnd!=-1)
	        	weekRank = responseHtml.substring(weekRankStart+4, weekRankEnd);
	        else 
	        	weekRank = "null";
	        
	        int weekRankGoStart = responseHtml.indexOf("<td>", weekRankEnd);
	        int weekRankGoEnd = responseHtml.indexOf("</span></td>", weekRankGoStart);
	        if(weekRankGoStart!=-1 && weekRankGoEnd!=-1)
	        	weekRankGo = upAndDown(responseHtml.substring(weekRankGoStart, weekRankGoEnd));
	        else 
	        	weekRankGo = "null";
	        
	        int monthRankStart = responseHtml.indexOf("<td>", weekRankGoEnd);
	        int monthRankEnd = responseHtml.indexOf("</td>", monthRankStart);
	        if(monthRankStart!=-1 && monthRankEnd!=-1)
	        	monthRank = responseHtml.substring(monthRankStart+4, monthRankEnd);
	        else 
	        	monthRank = "null";
	        
	        int monthRankGoStart = responseHtml.indexOf("<td>", monthRankEnd);
	        int monthRankGoEnd = responseHtml.indexOf("</span></td>", monthRankGoStart);
	        if(monthRankGoStart!=-1 && monthRankGoEnd!=-1)
	        	monthRankGo = upAndDown(responseHtml.substring(monthRankGoStart, monthRankGoEnd));
	        else 
	        	monthRankGo = "null";
	        
	        int threeMonthRankStart = responseHtml.indexOf("<td>", monthRankGoEnd);
	        int threeMonthRankEnd = responseHtml.indexOf("</td>", threeMonthRankStart);
	        if(threeMonthRankStart!=-1 && threeMonthRankEnd!=-1)
	        	threeMonthRank = responseHtml.substring(threeMonthRankStart+4, threeMonthRankEnd);
	        else 
	        	threeMonthRank = "null";
	        
	        int threeMonthRankGoStart = responseHtml.indexOf("<td>", threeMonthRankEnd);
	        int threeMonthRankGoEnd = responseHtml.indexOf("</span></td>", threeMonthRankGoStart);
	        if(threeMonthRankGoStart!=-1 && threeMonthRankGoEnd!=-1)
	        	threeMonthRankGo = upAndDown(responseHtml.substring(threeMonthRankGoStart, threeMonthRankGoEnd));
	        else 
	        	threeMonthRankGo = "null";
	        
	        int dayIpStart = responseHtml.indexOf("日均 PV 浏览量[ 一周平均 ]</td>");
	        dayIpStart = responseHtml.indexOf("<tr>", dayIpStart);
	        int dayIpEnd = responseHtml.indexOf("</td>", dayIpStart);
	        if(dayIpStart!=-1 && dayIpEnd!=-1)
	        {
	        	dayIP = responseHtml.substring(dayIpStart+21, dayIpEnd);
	        	if(dayIP.contains("无法完成统计"))
	        		dayIP = dayIP.substring(1, 6);
	        }
	        else 
	        	dayIP = "null";
	        int dayPVStart = responseHtml.indexOf("<td>", dayIpEnd);
	        int dayPVEnd = responseHtml.indexOf("</td>", dayPVStart);
	        if(dayPVStart!=-1 && dayPVEnd!=-1)
	        {
	        	dayPV = responseHtml.substring(dayPVStart+4, dayPVEnd);
	        	if(dayPV.contains("无法完成统计"))
	        		dayPV = dayPV.substring(1, 6);
	        }
	        else 
	        	dayPV = "null";
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
	/**
	 * 处理上升、下降排名字符串
	 * @return
	 */
	private String upAndDown(String item)
	{
		if(item.contains("up_arrow"))
			return "上升"+item.substring(27);
		else 
			return "下降"+item.substring(29);
	}
	public String getDomainName()
	{
		return domainName;
	}
	public String getAdminstrator()
	{
		return adminstrator;
	}
	public String getEmail()
	{
		return email;
	}
	public String getCurrentRank()
	{
		return currentRank;
	}
	public String getNextRank()
	{
		return nextRank;
	}
	public String getIncludeDate()
	{
		return includeDate;
	}
	public String getBelongToCount()
	{
		return belongToCount;
	}
	public String getCharset()
	{
		return charset;
	}
	public String getVisitSpeed()
	{
		return visitSpeed;
	}
	public String getAdult()
	{
		return adult;
	}
	public String getReverseLink()
	{
		return reverseLink;
	}
	public String getPhone()
	{
		return phone;
	}
	public String getAddress()
	{
		return address;
	}
	public String getBelongToList()
	{
		return belongToList;
	}
	public String getDomainSummary()
	{
		return domainSummary;
	}
	public String getTitle()
	{
		return title;
	}
	public String getTitle2()
	{
		return title2;
	}
	public String getTodayRank()
	{
		return todayRank;
	}
	public String getTodayRankGo()
	{
		return todayRankGo;
	}
	public String getWeekRank()
	{
		return weekRank;
	}
	public String getWeekRankGo()
	{
		return weekRankGo;
	}
	public String getMonthRank()
	{
		return monthRank;
	}
	public String getMonthRankGo()
	{
		return monthRankGo;
	}
	public String getThreeMonthRank()
	{
		return threeMonthRank;
	}
	public String getThreeMonthRankGo()
	{
		return threeMonthRankGo;
	}
	public String getDayIP()
	{
		return dayIP;
	}
	public String getDayPV()
	{
		return dayPV;
	}
	
}
