/**   
* @Title: InnerMsgTempDto.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 下午2:31:25 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

/** 
* @ClassName: InnerMsgTempDto 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 下午2:31:25 
*  
*/
public class NoticeTempDto {

	private Long id;
	
	private String title;
	
	private String temp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}
