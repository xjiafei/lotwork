/**   
* @Title: IMailSender.java 
* @Package com.winterframework.firefrog.common.email 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-25 上午11:00:45 
* @version V1.0   
*/
package com.winterframework.firefrog.common.email;

import com.winterframework.firefrog.notice.web.dto.EmailContentDto;

/** 
* @ClassName: IMailSender 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-25 上午11:00:45 
*  
*/
public interface IMailSender {

	public void sendMail(EmailInfo email) throws Exception;
	public Boolean testsendMail(EmailContentDto mail) throws Exception;
}
