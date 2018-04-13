package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: LotteryMonitorListResponse 
* @Description: 游戏异常审核 响应
* @author Richard
* @date 2013-10-12 上午10:29:36 
*
 */
public class GameExceptionAuditResponse implements Serializable {

	private static final long serialVersionUID = -8232564903321034018L;

	private List<GameExceptionAuditStruc> List ;

	public List<GameExceptionAuditStruc> getList() {
		return List;
	}

	public void setList(List<GameExceptionAuditStruc> list) {
		List = list;
	}


	
}
