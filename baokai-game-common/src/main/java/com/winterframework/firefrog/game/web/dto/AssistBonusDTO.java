/**   
* @Title: AssistBonusDTO.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-14 下午2:19:48 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/** 
* @ClassName: AssistBonusDTO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy
* @date 2014-4-14 下午2:19:48 
*  
*/
public class AssistBonusDTO implements Serializable{
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -2217765656L;
	private Integer methodType;
	private Long actualBonus;
	private Long actualBonusDown;
	private Long theoryBonus;
	private String methodTypeName;
	private String lhcCodeName;
	private Long retVal;
	private Double lhcTheoryBonus;

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

	public String getMethodTypeName() {
		methodTypeName = GameAwardNameUtil.getAssistBonusTypeName(methodType);
		return methodTypeName;
	}

	public void setMethodTypeName(String methodTypeName) {
		this.methodTypeName = methodTypeName;
	}

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}

	public String getLhcCodeName() {
		return lhcCodeName;
	}

	public void setLhcCodeName(String lhcCodeName) {
		this.lhcCodeName = lhcCodeName;
	}

	public Long getRetVal() {
		return retVal;
	}

	public void setRetVal(Long retVal) {
		this.retVal = retVal;
	}

	public Double getLhcTheoryBonus() {
		return lhcTheoryBonus;
	}

	public void setLhcTheoryBonus(Double lhcTheoryBonus) {
		this.lhcTheoryBonus = lhcTheoryBonus;
	}



}
