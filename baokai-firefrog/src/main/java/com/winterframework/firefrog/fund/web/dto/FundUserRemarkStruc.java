/**   
* @Title: FundUserRemarkStruc.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 下午3:50:25 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

/** 
* @ClassName: FundUserRemarkStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午3:50:25 
*  
*/
public class FundUserRemarkStruc {
	private Long id;
	private Long userId;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	private Date gmtAutounlocked;
	private Date gmtCansetremark;

	/** 
	* 账号
	*/
	private String account;
	/** 
	*用户类型 0:非vip用户，1：vip
	*/
	private Integer vipLvl;
	/** 
	*用户状态 1：锁定 0：正常
	*/
	private Integer isFreeze;
	/** 
	* 所属总代 
	*/
	private String topAgent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtAutounlocked() {
		return gmtAutounlocked;
	}

	public void setGmtAutounlocked(Date gmtAutounlocked) {
		this.gmtAutounlocked = gmtAutounlocked;
	}

	public Date getGmtCansetremark() {
		return gmtCansetremark;
	}

	public void setGmtCansetremark(Date gmtCansetremark) {
		this.gmtCansetremark = gmtCansetremark;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Integer vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Integer getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Integer isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getTopAgent() {
		return topAgent;
	}

	public void setTopAgent(String topAgent) {
		this.topAgent = topAgent;
	}
}
