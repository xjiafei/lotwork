package com.winterframework.firefrog.advert.dao.vo;

import java.util.Date;

public class AdSpaceStateVO {
	/** 
	*  开始时间 
	*/
	private Date adGmtEffectBegin;
	/** 
	* 结束时间 
	*/
	private Date adGmtEffectEnd;
	/** 
	*  广告状态 
	*/
	private Long adStatus;

	public AdSpaceStateVO() {
	}

	public AdSpaceStateVO(AdspaceRelationAdVO relationAdVO) {
		this.adGmtEffectBegin = relationAdVO.getAdGmtEffectBegin();
		this.adGmtEffectEnd = relationAdVO.getAdGmtEffectEnd();
		this.adStatus = relationAdVO.getAdStatus();
	}

	public Date getAdGmtEffectBegin() {
		return adGmtEffectBegin;
	}

	public void setAdGmtEffectBegin(Date adGmtEffectBegin) {
		this.adGmtEffectBegin = adGmtEffectBegin;
	}

	public Date getAdGmtEffectEnd() {
		return adGmtEffectEnd;
	}

	public void setAdGmtEffectEnd(Date adGmtEffectEnd) {
		this.adGmtEffectEnd = adGmtEffectEnd;
	}

	public Long getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(Long adStatus) {
		this.adStatus = adStatus;
	}
}
