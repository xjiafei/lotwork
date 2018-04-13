package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: PointsStrucRequestDTO 
* @Description: 用于显示详细封锁变价信息的结构体
* @author 你的名字 
* @date 2014-5-30 上午10:07:52 
*  
*/
public class PointsStrucRequestDTO extends PointsRequestDTO {
	/** 
	* 投注 号码
	*/
	private String betDetail;
	/** 
	* 投注类型
	*/
	private String betType;

	public PointsStrucRequestDTO(PointsRequestDTO tempPointsDTO, String betDetail, String betType) {
		this.betDetail = betDetail;
		this.betType = betType;
		this.currentPhase=tempPointsDTO.getCurrentPhase();
		this.mult = tempPointsDTO.getMult();
		this.point = tempPointsDTO.getPoint();
		this.retValue = tempPointsDTO.getRetValue();
	}

	public PointsStrucRequestDTO() {
	}

	public String getBetDetail() {
		return betDetail;
	}

	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	@Override
	public String toString() {
		return this.getBetType() + this.getBetDetail() + this.getPoint();
	}
}
