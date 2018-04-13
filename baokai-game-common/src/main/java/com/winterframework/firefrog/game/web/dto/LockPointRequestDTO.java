package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/**
 * 有封鎖变价的 号码列表
 * @author Pogi.Lin
 */
public class LockPointRequestDTO {
	/**原始倍数*/
	private Long beishu;
	/**封锁号码信息（合并前）*/
	private List<BlockadeResponseDTO> beforeBlockadeList;
	/**有变价的号码*/
	private List<PointsRequestDTO> pointsList;
	/**
	 * 取得原始倍数。
	 * @return
	 */
	public Long getBeishu() {
		return beishu;
	}
	/**
	 * 設定原始倍数。
	 * @param beishu
	 */
	public void setBeishu(Long beishu) {
		this.beishu = beishu;
	}
	/**
	 * 取得有变价的号码。
	 * @return
	 */
	public List<PointsRequestDTO> getPointsList() {
		return pointsList;
	}
	/**
	 * 設定有变价的号码。
	 * @param pointsList
	 */
	public void setPointsList(List<PointsRequestDTO> pointsList) {
		this.pointsList = pointsList;
	}
	/**
	 * 取得封锁号码信息（合并前）。
	 * @return
	 */
	public List<BlockadeResponseDTO> getBeforeBlockadeList() {
		return beforeBlockadeList;
	}
	/**
	 * 設定封锁号码信息（合并前）。
	 * @param beforeBlockadeList
	 */
	public void setBeforeBlockadeList(List<BlockadeResponseDTO> beforeBlockadeList) {
		this.beforeBlockadeList = beforeBlockadeList;
	}
}
