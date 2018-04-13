/**   
* @Title: BetLimitAssistStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-6-25 下午2:00:30 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: BetLimitAssistStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-6-25 下午2:00:30 
*  
*/
public class BetLimitAssistStruc {

	private static final long serialVersionUID = -197323298375566906L;
	private  Long id;
	private Long parentId;
	private Long maxMultiple;
	private Long maxMultiple_bak;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getMaxMultiple() {
		return maxMultiple;
	}
	public void setMaxMultiple(Long maxMultiple) {
		this.maxMultiple = maxMultiple;
	}
	public Long getMaxMultiple_bak() {
		return maxMultiple_bak;
	}
	public void setMaxMultiple_bak(Long maxMultiple_bak) {
		this.maxMultiple_bak = maxMultiple_bak;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
