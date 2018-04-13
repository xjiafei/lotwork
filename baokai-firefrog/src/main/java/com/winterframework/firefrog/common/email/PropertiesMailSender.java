/**   
* @Title: PropertiesMailSender.java 
* @Package com.winterframework.firefrog.common.email 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-25 上午11:43:22 
* @version V1.0   
*/
package com.winterframework.firefrog.common.email;

import java.util.Properties;

import org.springframework.stereotype.Service;

/** 
* @ClassName: PropertiesMailSender 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-25 上午11:43:22 
*  
*/
@Service("MailSender")
public class PropertiesMailSender extends MailSender {
	private static final String FLAG_HOST = "mail.smtp.host";
	private static final String FLAG_PORT = "mail.smtp.port";
	private static final String FLAG_AUTH = "mail.smtp.auth";	

	protected void configProperties() {
		super.fromAddress = fromAddress;
		super.mailServerHost = mailServerHost;
		super.mailServerPort = mailServerPort;
	}

	protected Properties getProperties() {
		Properties p = new Properties();
		p.put(FLAG_HOST, this.mailServerHost);
		p.put(FLAG_PORT, this.mailServerPort);
		p.put(FLAG_AUTH, auth);
		return p;
	}
}
