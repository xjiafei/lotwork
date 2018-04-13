/**   
* @Title: MCApplayResponse.java 
* @Package com.winterframework.firefrog.fund.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午3:17:11 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.sp;

/** 
* @ClassName: MCApplayResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午3:17:11 
*  
*/
public class SPPayConfirmRequest {
	private String ORDERNO;
	private String RECODE;
	private String REMSG;
	private String TXNAMT;
	private String PAYORDNO;
	private String ORDSTATUS;
	private String SIGN;
	
	public String getORDERNO() {
		return ORDERNO;
	}
	public void setORDERNO(String oRDERNO) {
		ORDERNO = oRDERNO;
	}
	public String getRECODE() {
		return RECODE;
	}
	public void setRECODE(String rECODE) {
		RECODE = rECODE;
	}
	public String getREMSG() {
		return REMSG;
	}
	public void setREMSG(String rEMSG) {
		REMSG = rEMSG;
	}
	public String getTXNAMT() {
		return TXNAMT;
	}
	public void setTXNAMT(String tXNAMT) {
		TXNAMT = tXNAMT;
	}
	public String getPAYORDNO() {
		return PAYORDNO;
	}
	public void setPAYORDNO(String pAYORDNO) {
		PAYORDNO = pAYORDNO;
	}
	public String getORDSTATUS() {
		return ORDSTATUS;
	}
	public void setORDSTATUS(String oRDSTATUS) {
		ORDSTATUS = oRDSTATUS;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	
	
	
	
}
