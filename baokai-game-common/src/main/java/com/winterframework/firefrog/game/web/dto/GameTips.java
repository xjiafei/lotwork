package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameTips 
* @Description: 游戏提示信息
* @author Denny 
* @date 2013-9-29 下午7:51:30 
*  
*/
public class GameTips implements Serializable {

	private static final long serialVersionUID = 6172067133379473069L;

	private String example;
	private String tips;

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

}
