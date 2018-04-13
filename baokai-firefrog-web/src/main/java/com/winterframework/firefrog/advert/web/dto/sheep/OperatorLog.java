/**   
* @Title: OperatorLog.java 
* @Package com.winterframework.firefrog.advert.web.dto.sheep 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-1-24 下午3:01:59 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto.sheep;

/** 
* @ClassName: OperatorLog 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-1-24 下午3:01:59 
*  
*/
public class OperatorLog {

	private Long status;
	private String message;
	private Object data;
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
