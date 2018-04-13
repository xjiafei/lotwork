/**   
* @Title: IndexStruc.java 
* @Package com.winterframework.firefrog.index.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-23 上午10:20:44 
* @version V1.0   
*/
package com.winterframework.firefrog.index.web.dto;

import java.util.List;

import com.winterframework.firefrog.advert.web.dto.AdNoticeStruc;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.user.entity.QQInfo;

/** 
* @ClassName: IndexStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-23 上午10:20:44 
*  
*/
public class IndexStruc {

	private List<AdNoticeStruc> lastNotice;
	
	private List<QQInfo> qqs; 
	private String loginArea;
	
	private List<AdSpaceStruc> adSpaces;

	public List<AdSpaceStruc> getAdSpaces() {
		return adSpaces;
	}

	public void setAdSpaces(List<AdSpaceStruc> adSpaces) {
		this.adSpaces = adSpaces;
	}

	public List<QQInfo> getQqs() {
		return qqs;
	}

	public void setQqs(List<QQInfo> qqs) {
		this.qqs = qqs;
	}

	
	public List<AdNoticeStruc> getLastNotice() {
		return lastNotice;
	}

	public void setLastNotice(List<AdNoticeStruc> lastNotice) {
		this.lastNotice = lastNotice;
	}

	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}



}
