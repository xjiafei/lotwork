/**   
* @Title: GameTrendReport.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: winter-game-common.GameTrendReport.java 
* @author Denny  
* @date 2014-6-17 下午3:02:31 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameTrendReport 
* @Description: 基本走势-报表数据
* @author Denny 
* @date 2014-6-17 下午3:02:31 
*  
*/
public class GameTrendReport implements Serializable {

	private static final long serialVersionUID = 4585567932010078173L;

	private String webIssueCode;
	private String numberRecord;

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

}
