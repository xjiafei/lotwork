package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**PrizeListStruc
 * 中奖列表
 * @author david
 *
 */
public class PrizeList implements Serializable{

	private static final long serialVersionUID = 6868897599276875244L;

	private String status;
	
	private List<PrizeListStruc> list;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PrizeListStruc> getList() {
		return list;
	}

	public void setList(List<PrizeListStruc> list) {
		this.list = list;
	}

}
