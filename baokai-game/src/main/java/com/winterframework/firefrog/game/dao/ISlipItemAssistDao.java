package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface ISlipItemAssistDao extends BaseDao<GameSlipAssist>{
	/** 
	* @Title: saveSlipAssistItem 
	* @Description: 保存单个注单辅助信息
	* @param gamePackageItem
	*/
	public void saveSlipAssistItem(SlipItemAssist gamePackageItem);

	/** 
	* @Title: saveSlipAssistItem 
	* @Description: 保存注单辅助信息列表
	* @param gamePackageItemList
	*/
	public void saveSlipAssistItem(List<SlipItemAssist> gamePackageItemList);

	/** 
	* @Title: getSlipAssistItemList 
	* @Description: 根据slipId获取辅助信息列表
	* @param slipId
	* @return
	*/
	List<SlipItemAssist> getSlipAssistItemList(Long slipId);
}
