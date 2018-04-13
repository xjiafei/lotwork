package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: BetLimit 
* @Description: 投注限制实体Bean 
* @author Denny 
* @date 2013-8-21 下午3:15:34 
*  
*/
public class BetLimitAssist implements Serializable {

	private static final long serialVersionUID = 9132588001790733692L;

	/** 彩种 */
	private Long id;
	private Long parentId;
	private Long assitCode;
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
	public Long getAssitCode() {
		return assitCode;
	}
	public void setAssitCode(Long assitCode) {
		this.assitCode = assitCode;
	}
	public Long getMaxMultiple() {
		return maxMultiple;
	}
	public void setMaxMultiple(Long maxMultiple) {
		this.maxMultiple = maxMultiple;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getMaxMultiple_bak() {
		return maxMultiple_bak;
	}
	public void setMaxMultiple_bak(Long maxMultiple_bak) {
		this.maxMultiple_bak = maxMultiple_bak;
	}
}
