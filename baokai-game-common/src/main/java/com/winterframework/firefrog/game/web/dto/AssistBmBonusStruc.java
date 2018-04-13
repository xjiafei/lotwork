/**   
* @Title: AssistBmBonusStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-14 下午2:07:34 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: AssistBmBonusStruc 
* @Description: 辅助投注方式奖金基本结构
* @author floy
* @date 2014-4-14 下午2:07:34 
*  
*/
public class AssistBmBonusStruc implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -896764265354L;
	private Integer methodType;
	private String methodTypeTitle;
	private Long actualBonus;
	private Long actualBonusDown;
	private Long theoryBonus;
	private String lhcCodeTitle;
	private Long retVal;
	private Long lhcTheoryBonus;
	public Integer getMethodType() {
		return methodType;
	}
	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}
	public Long getActualBonus() {
		return actualBonus;
	}
	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}
	public Long getTheoryBonus() {
		return theoryBonus;
	}
	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}
	public Long getActualBonusDown() {
		return actualBonusDown;
	}
	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}
	public String getMethodTypeTitle() {
		return methodTypeTitle;
	}
	public void setMethodTypeTitle(String methodTypeTitle) {
		this.methodTypeTitle = methodTypeTitle;
	}
	public String getLhcCodeTitle() {
		return lhcCodeTitle;
	}
	public void setLhcCodeTitle(String lhcCodeTitle) {
		this.lhcCodeTitle = lhcCodeTitle;
	}
	public Long getRetVal() {
		return retVal;
	}
	public void setRetVal(Long retVal) {
		this.retVal = retVal;
	}
	public Long getLhcTheoryBonus() {
		return lhcTheoryBonus;
	}
	public void setLhcTheoryBonus(Long lhcTheoryBonus) {
		this.lhcTheoryBonus = lhcTheoryBonus;
	}
}
