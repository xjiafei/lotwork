package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;



/** 
* @ClassName ActivityUserAwardResponse 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityUserAwardResponse implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 573925279415801319L;
	private List<ActivityUserAwardLog> logs ;
	public List<ActivityUserAwardLog> getLogs() {
		return logs;
	}
	public void setLogs(List<ActivityUserAwardLog> logs) {
		this.logs = logs;
	}
}
