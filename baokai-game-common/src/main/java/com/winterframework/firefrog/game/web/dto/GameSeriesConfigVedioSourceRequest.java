package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameSeriesConfigVedioSourceRequest 
* @Description: 游戏运营参数视频源操作
* @author david
* @date 2014-11-21 下午5:49:48 
* 
*/
public class GameSeriesConfigVedioSourceRequest implements Serializable {

	private static final long serialVersionUID = 9218775259686995532L;
	
	/** 彩种ID **/
	private Long lotteryId;
	
	private String type;//add,modify,edit
	
	private String name;
	
	private String url;
	
	private Long id;
	
	private Integer status;


	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
