package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameUserAwardGroupQueryRequest 
* @Description: 用户奖金组查询请求参数DTO 
* @author Denny 
* @date 2013-9-17 下午5:15:11 
*  
*/
public class GameUserAwardGroupQueryRequest implements Serializable{

	private static final long serialVersionUID = 7835688061078360021L;
	
	private Long userid;
	/** 用户类型: 0，总代；1，其它用户 */
	private Integer type;
	/** 奖金组类型: 0，所有；1，投注 */
	private Integer awardType;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

}
