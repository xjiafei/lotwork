package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.ISlipItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("slipItemAssistDaoImpl")
public class SlipItemAssistDaoImpl extends BaseIbatis3Dao<GameSlipAssist> implements ISlipItemAssistDao {

	@Override
	public void saveSlipAssistItem(SlipItemAssist gamePackageItem) {
		GameSlipAssist vo = VOConverter.convertSlipAssistEntity2Vo(gamePackageItem);
		this.insert(vo);
	}

	@Override
	public void saveSlipAssistItem(List<SlipItemAssist> gamePackageItemList) {
		List<GameSlipAssist> list = new ArrayList<GameSlipAssist>();
		for (SlipItemAssist slipItemAssist : gamePackageItemList) {
			list.add(VOConverter.convertSlipAssistEntity2Vo(slipItemAssist));
		}
		this.insert(list);
	}

	@Override
	public List<SlipItemAssist> getSlipAssistItemList(Long slipId) {
		List<GameSlipAssist> list = this.sqlSessionTemplate.selectList(getQueryPath("getItemByOrderId"), slipId);
		List<SlipItemAssist> entityList = new ArrayList<SlipItemAssist>();
		for (GameSlipAssist slipItemAssist : list) {
			entityList.add(VOConverter.convertSlipAssistVo2Entity(slipItemAssist));
		}
		return entityList;
	}
}
