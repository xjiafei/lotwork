package com.winterframework.firefrog.game.dao.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class FundGameVos {

    /**
	 * 值为资金变动类型-摘要-交易码(ModelCode-SummaryCode-StatusCode)
	 * 单期投注，GM-DVCB-1
	 */
	private String reason;
	private String vals;
	private Long operator;
	private String exCode;
	private String planCode;
	private String note;
	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public  List<FundGameVo> parseVals(){
		List<FundGameVo> valls=new ArrayList<FundGameVo>();	
		String[] strs=StringUtils.split(vals,",");
		for(String s:strs){
			String[] ss=StringUtils.split(s,"|");
			FundGameVo vo=new FundGameVo();
			vo.setIsAclUser(isAclUser);
			vo.setAmount(Long.valueOf(ss[1]));
			vo.setUserId(Long.parseLong(ss[0]));
			vo.setReason(reason);
			vo.setOperator(operator);
			vo.setExCode(exCode);
			vo.setPlanCode(planCode);
			vo.setNote(note);
			valls.add(vo);
		}
		return valls;
	}
	
	/**
	 * 代表operator是后台用户，还是玩家。1代表后台用户，0代表玩家。
	 */
	private Long isAclUser;

	public Long getIsAclUser() {
		return isAclUser;
	}

	public void setIsAclUser(Long isAclUser) {
		this.isAclUser = isAclUser;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getVals() {
		return vals;
	}

	public void setVals(String vals) {
		this.vals = vals;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	
		

}
