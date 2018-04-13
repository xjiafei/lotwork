/**   
* @Title: AdvertData.java 
* @Package com.winterframework.firefrog.json.api.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-24 上午10:11:49 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

/** 
* @ClassName: AdvertData 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-24 上午10:11:49 
*  
*/
public class AdvertData {

	private String name;
	private List<AdvertStruc> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AdvertStruc> getList() {
		return list;
	}

	public void setList(List<AdvertStruc> list) {
		this.list = list;
	}
}
