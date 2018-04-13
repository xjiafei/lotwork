package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;

public class FundWithdrawTipsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1116367188873293826L;
	private List<FundWithdrawTips> tipsList;

	//未處理提示使用
	private List<FundWithdrawTips> uncheckDrawPassTips;
	
	private List<FundWithdrawTips> uncheckDrawUnpassTips;
	
	private List<FundWithdrawTips> uncheckDrawRecheckTips;
	
	private Integer uncheckMaxSize;
	
	//待審核提示使用
	private List<FundWithdrawTips> reviewDrawPassTips;
	
	private List<FundWithdrawTips> reviewDrawUnpassTips;
	
	private List<FundWithdrawTips> reviewDrawRecheckTips;
	
	private Integer revieMaxSize;
	
	private Boolean isSuccess;
	
	
	private List<FundWithdrawTips> appealPassTips;
	
	private List<FundWithdrawTips> appealUnPassTips;
	
	private List<FundWithdrawTips> appealReviewTips;
	
	private List<FundWithdrawTips> appealWaitTips;
	
	public List<FundWithdrawTips> getAppealWaitTips() {
		return appealWaitTips;
	}

	public void setAppealWaitTips(List<FundWithdrawTips> appealWaitTips) {
		this.appealWaitTips = appealWaitTips;
	}

	public List<FundWithdrawTips> getTipsList() {
		return tipsList;
	}

	public void setTipsList(List<FundWithdrawTips> tipsList) {
		this.tipsList = tipsList;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}


	public Integer getRevieMaxSize() {
		return revieMaxSize;
	}

	public void setRevieMaxSize(Integer revieMaxSize) {
		this.revieMaxSize = revieMaxSize;
	}

	public List<FundWithdrawTips> getUncheckDrawPassTips() {
		return uncheckDrawPassTips;
	}

	public void setUncheckDrawPassTips(List<FundWithdrawTips> uncheckDrawPassTips) {
		this.uncheckDrawPassTips = uncheckDrawPassTips;
	}

	public List<FundWithdrawTips> getUncheckDrawUnpassTips() {
		return uncheckDrawUnpassTips;
	}

	public void setUncheckDrawUnpassTips(
			List<FundWithdrawTips> uncheckDrawUnpassTips) {
		this.uncheckDrawUnpassTips = uncheckDrawUnpassTips;
	}

	public List<FundWithdrawTips> getUncheckDrawRecheckTips() {
		return uncheckDrawRecheckTips;
	}

	public void setUncheckDrawRecheckTips(
			List<FundWithdrawTips> uncheckDrawRecheckTips) {
		this.uncheckDrawRecheckTips = uncheckDrawRecheckTips;
	}

	public Integer getUncheckMaxSize() {
		return uncheckMaxSize;
	}

	public void setUncheckMaxSize(Integer uncheckMaxSize) {
		this.uncheckMaxSize = uncheckMaxSize;
	}

	public List<FundWithdrawTips> getReviewDrawPassTips() {
		return reviewDrawPassTips;
	}

	public void setReviewDrawPassTips(List<FundWithdrawTips> reviewDrawPassTips) {
		this.reviewDrawPassTips = reviewDrawPassTips;
	}

	public List<FundWithdrawTips> getReviewDrawUnpassTips() {
		return reviewDrawUnpassTips;
	}

	public void setReviewDrawUnpassTips(List<FundWithdrawTips> reviewDrawUnpassTips) {
		this.reviewDrawUnpassTips = reviewDrawUnpassTips;
	}

	public List<FundWithdrawTips> getReviewDrawRecheckTips() {
		return reviewDrawRecheckTips;
	}

	public void setReviewDrawRecheckTips(
			List<FundWithdrawTips> reviewDrawRecheckTips) {
		this.reviewDrawRecheckTips = reviewDrawRecheckTips;
	}

	public List<FundWithdrawTips> getAppealPassTips() {
		return appealPassTips;
	}

	public void setAppealPassTips(List<FundWithdrawTips> appealPassTips) {
		this.appealPassTips = appealPassTips;
	}

	public List<FundWithdrawTips> getAppealUnPassTips() {
		return appealUnPassTips;
	}

	public void setAppealUnPassTips(List<FundWithdrawTips> appealUnPassTips) {
		this.appealUnPassTips = appealUnPassTips;
	}

	public List<FundWithdrawTips> getAppealReviewTips() {
		return appealReviewTips;
	}

	public void setAppealReviewTips(List<FundWithdrawTips> appealReviewTips) {
		this.appealReviewTips = appealReviewTips;
	}

	
}
