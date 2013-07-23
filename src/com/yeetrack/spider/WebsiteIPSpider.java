/**
 * 
 */
package com.yeetrack.spider;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuemeng
 * 获取域名的ip地址
 */
public class WebsiteIPSpider
{
	private String domain;
	
	public WebsiteIPSpider(String domain)
	{
		this.domain = domain;
	}
	
	public String getIpAddress()
	{
		String ip = null;
		try
        {
	        InetAddress address = InetAddress.getByName(domain);
	        ip = address.getHostAddress();
        } catch (UnknownHostException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return ip;
	}
}
