/**   
* @Title: ExceptionQueryRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-16 下午4:32:16 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;

/** 
* @ClassName: ExceptionQueryRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-16 下午4:32:16 
*  
*/
public class ExceptionQueryRequest implements Serializable {

	private static final long serialVersionUID = -8261106486233061253L;

	private String mcSn;

	private String rcvAccName;
	//收款银行id
	private String bankId;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date exactTimeFrom;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date exactTimeTo;
	private Long realChargeAmtFrom;
	private Long realChargeAmtTo;
	private Long refundAmtFrom;
	private Long refundAmtTo;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date applyTimeFrom;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date applyTimeTo;
	private Long applyAccount;
	private String cardNumber;
	private String cardAcct;
	private String rcvCardNumber;
	private String rcvEmail ;
	//DP操作時間 
	private Date fromOperatingDate;
	//DP操作時間
	private Date toOperatingDate;
	private Long[] status;

	private String currApprer;
	private Long currFirst;
	
	private String baseInfo;
	/** 
	* 查询时是否使用currApprer进行条件匹配
	*/
	private String useCurrApprer;
	
	private String rcvBank;

	public String getRcvCardNumber() {
		return rcvCardNumber;
	}
	

	


	public Long getRefundAmtFrom() {
		return refundAmtFrom;
	}





	public void setRefundAmtFrom(Long refundAmtFrom) {
		this.refundAmtFrom = refundAmtFrom;
	}





	public Long getRefundAmtTo() {
		return refundAmtTo;
	}





	public void setRefundAmtTo(Long refundAmtTo) {
		this.refundAmtTo = refundAmtTo;
	}





	public String getCardAcct() {
		return cardAcct;
	}





	public void setCardAcct(String cardAcct) {
		this.cardAcct = cardAcct;
	}





	public String getRcvEmail() {
		return rcvEmail;
	}


	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}


	public void setRcvCardNumber(String rcvCardNumber) {
		this.rcvCardNumber = rcvCardNumber;
	}

	public String getMcSn() {
		return mcSn;
	}

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}

	public String getRcvAccName() {
		return rcvAccName;
	}

	public void setRcvAccName(String rcvAccName) {
		this.rcvAccName = rcvAccName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Date getExactTimeFrom() {
		return exactTimeFrom;
	}

	public void setExactTimeFrom(Date exactTimeFrom) {
		this.exactTimeFrom = exactTimeFrom;
	}

	public Date getExactTimeTo() {
		return exactTimeTo;
	}

	public void setExactTimeTo(Date exactTimeTo) {
		this.exactTimeTo = exactTimeTo;
	}

	public Long getRealChargeAmtFrom() {
		return realChargeAmtFrom;
	}

	public void setRealChargeAmtFrom(Long realChargeAmtFrom) {
		this.realChargeAmtFrom = realChargeAmtFrom;
	}

	public Long getRealChargeAmtTo() {
		return realChargeAmtTo;
	}

	public void setRealChargeAmtTo(Long realChargeAmtTo) {
		this.realChargeAmtTo = realChargeAmtTo;
	}

	public Date getApplyTimeFrom() {
		return applyTimeFrom;
	}

	public void setApplyTimeFrom(Date applyTimeFrom) {
		this.applyTimeFrom = applyTimeFrom;
	}

	public Date getApplyTimeTo() {
		return applyTimeTo;
	}

	public void setApplyTimeTo(Date applyTimeTo) {
		this.applyTimeTo = applyTimeTo;
	}

	public Long getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(Long applyAccount) {
		this.applyAccount = applyAccount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public String getUseCurrApprer() {
		return useCurrApprer;
	}

	public void setUseCurrApprer(String useCurrApprer) {
		this.useCurrApprer = useCurrApprer;
	}

	public Long getCurrFirst() {
		return currFirst;
	}

	public void setCurrFirst(Long currFirst) {
		this.currFirst = currFirst;
	}

	public Date getFromOperatingDate() {
		return fromOperatingDate;
	}

	public void setFromOperatingDate(Date fromOperatingDate) {
		this.fromOperatingDate = fromOperatingDate;
	}

	public Date getToOperatingDate() {
		return toOperatingDate;
	}

	public void setToOperatingDate(Date toOperatingDate) {
		this.toOperatingDate = toOperatingDate;
	}





	public String getBaseInfo() {
		return baseInfo;
	}





	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}





	public String getRcvBank() {
		return rcvBank;
	}





	public void setRcvBank(String rcvBank) {
		this.rcvBank = rcvBank;
	}

}
