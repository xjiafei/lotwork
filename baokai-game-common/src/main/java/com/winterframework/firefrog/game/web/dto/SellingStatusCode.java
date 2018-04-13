package com.winterframework.firefrog.game.web.dto;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/** 
* @ClassName: SellingStatusCode 
* @Description: 销售状态
* @author Alan
* @date 2013-9-27 下午2:54:19 
*  
*/
public class SellingStatusCode {

	/** 投注方式 */
	private Integer betMethodCode;
	/** 投注方式名称 */
	private String betMethodName;
	/** 投注方式销售状态：0，停售；1，可销售 */
	private Integer status;
	/** 投注方式销售状态：是否有更改 */
	private boolean status_changed = false;
	
	public Integer getBetMethodCode() {
		return betMethodCode;
	}
	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}
	public String getBetMethodName() {
//		betMethodName = GameAwardNameUtil.methodCodeName(betMethodCode);
		return betMethodName;
	}
	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public boolean isStatus_changed() {
		return status_changed;
	}
	public void setStatus_changed(boolean status_changed) {
		this.status_changed = status_changed;
	}
}
