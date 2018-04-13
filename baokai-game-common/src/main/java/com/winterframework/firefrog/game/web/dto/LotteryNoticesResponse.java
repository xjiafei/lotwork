package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: LotteryNoticesResponse 
* @Description: 5.5.30	彩种奖期监控通知(OMI030) 响应
* @author Richard
* @date 2013-10-12 上午10:20:01 
*
 */
public class LotteryNoticesResponse implements Serializable {

	private static final long serialVersionUID = -8764716429963580847L;

	private List<LotteryNoticesStruc> list;

	public List<LotteryNoticesStruc> getList() {
		return list;
	}

	public void setList(List<LotteryNoticesStruc> list) {
		this.list = list;
	}
	
}