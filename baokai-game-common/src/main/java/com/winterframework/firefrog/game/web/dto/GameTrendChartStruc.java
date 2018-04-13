package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: GameTrendChartStruc 
* @Description:  走势图页面数据结构
* @author Denny 
* @date 2014-6-4 下午3:58:21 
*  
*/
public class GameTrendChartStruc implements Serializable {

	private static final long serialVersionUID = 3407308002652288427L;

	private int isSuccess;
    private String zoneComment;
    private String lotteryCode;
	private List<Object> data;
	private List<Object> statistics;

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

    public String getZoneComment() {
        return zoneComment;
    }

    public void setZoneComment(String zoneComment) {
        this.zoneComment = zoneComment;
    }

    public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public List<Object> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Object> statistics) {
		this.statistics = statistics;
	}

}
