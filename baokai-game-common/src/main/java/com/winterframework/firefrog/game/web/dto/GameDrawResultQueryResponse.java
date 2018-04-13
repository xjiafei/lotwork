package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;
 
/**
 * 开奖结果Response
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class GameDrawResultQueryResponse implements Serializable{ 
	private static final long serialVersionUID = 4222220085284814111L;
	/** 用户奖金组 */
	private List<GameDrawResultStruc> gameDrawResultStrucList;
	
	public List<GameDrawResultStruc> getGameDrawResultStrucList() {
		return gameDrawResultStrucList;
	}
	public void setGameDrawResultStrucList(
			List<GameDrawResultStruc> gameDrawResultStrucList) {
		this.gameDrawResultStrucList = gameDrawResultStrucList;
	}
 
}
