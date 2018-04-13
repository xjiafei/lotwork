package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/**
 * 
* @ClassName: AwardDTO 
* @Description: 奖金组DTO
* @author Richard
* @date 2013-9-24 上午10:52:17 
*
 */
public class AwardDTO implements Serializable {

	private static final long serialVersionUID = 530533094103095309L;

	private Long lotteryid;
	private String groupName;
	private Integer groupCode;
	private Integer rowsCount = 0;
	//玩法组
	private List<SetCodeDTO> setCodeList;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	
	public String getGroupName() {
//		groupName = GameAwardNameUtil.groupName(groupCode);
		return groupName;
	}
	public void setGroupName(String groupName) {
		
		this.groupName = groupName;
	}
	public Integer getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}
	public List<SetCodeDTO> getSetCodeList() {
		return setCodeList;
	}
	public void setSetCodeList(List<SetCodeDTO> setCodeList) {
		this.setCodeList = setCodeList;
	}
	public Integer getRowsCount() {
		for(int i = 0 ; i< setCodeList.size() ; i++){
			
			rowsCount += setCodeList.get(i).getMethodCodeList().size();
		}
		return rowsCount;
	}
	public void setRowsCount(Integer rowsCount) {
		this.rowsCount = rowsCount;
	}
	
}
