 package com.winterframework.firefrog.game.entity;

import java.util.Date;

import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.user.entity.User;




/** 
* @ClassName: GameRetPoint 
* @Description: 返点记录实体表 
* @author david 
* @date 2013-11-11 下午5:42:01 
*  
*/
public class GameReturnPoint{
	
	private Long id;
	private GameSlip gameSlip;
	private User user;
	private Date createTime;
	private Long retPoint;
	private Integer status;
	private Long issueCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GameSlip getGameSlip() {
		return gameSlip;
	}
	public void setGameSlip(GameSlip gameSlip) {
		this.gameSlip = gameSlip;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getRetPoint() {
		return retPoint;
	}
	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	

}

