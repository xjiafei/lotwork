/**   
* @Title: NoticeTaskTreeStruc.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 下午4:00:41 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: NoticeTaskTreeStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 下午4:00:41 
*  
*/
public class NoticeTaskTreeStruc {

	private List<NoticeTaskStruc> noticeTasks = new ArrayList<NoticeTaskStruc>();

	private String module;

	private int size;

	public List<NoticeTaskStruc> getNoticeTasks() {
		return noticeTasks;
	}

	public void setNoticeTasks(List<NoticeTaskStruc> noticeTasks) {
		this.noticeTasks = noticeTasks;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
