package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: RiskRequest 
* @Description:  风控审核接口请求
* @author charles
* @date 2013-12-12 下午2:31:00 
*
 */
public class TORiskRequest implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2754075356891414495L;
	
	public List<TORiskDTO> toRiskDTOList;
	
	public int gameFundTypes;
	
	public TORiskRequest(){
		
	}

	public List<TORiskDTO> getToRiskDTOList() {
		return toRiskDTOList;
	}

	public void setToRiskDTOList(List<TORiskDTO> toRiskDTOList) {
		this.toRiskDTOList = toRiskDTOList;
	}

	public int getGameFundTypes() {
		return gameFundTypes;
	}

	public void setGameFundTypes(int gameFundTypes) {
		this.gameFundTypes = gameFundTypes;
	}

	
}
