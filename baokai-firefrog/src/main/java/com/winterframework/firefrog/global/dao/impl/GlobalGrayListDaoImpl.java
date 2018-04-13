package com.winterframework.firefrog.global.dao.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.global.dao.GlobalGrayListDao;
import com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListTestVO;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListIpVO;
import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("globalGrayListDaoImpl")
public class GlobalGrayListDaoImpl extends BaseIbatis3Dao<GlobalGrayListVO> implements GlobalGrayListDao {
	/*
	 * 查詢灰名單By userId
	 * */
	@Override
	public GlobalGrayListVO queryGlobalGrayListByUserId(
			Long userId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("userId", userId);
		return sqlSessionTemplate.selectOne("queryGrayListByUserId", param);
	}
	/*
	 * 指定IP白名單 : 依據user Accunt 取得限制的IP清單
	 * */
	@Override
	public void saveGlobalGrayList(Long userId,Long riskType) {
		GlobalGrayListVO grayListVO = new GlobalGrayListVO();
		grayListVO.setUserId(userId);
		grayListVO.setRiskType(riskType);
		grayListVO.setCreateTime(new Date());
		grayListVO.setUpdateTime(new Date());
		sqlSessionTemplate.insert("saveGlobalGrayList",grayListVO);
		//globalWhiteListIpVO.setUserAccunt(userAccunt);	
		//return sqlSessionTemplate.selectList("getWhiteListIpByUser", globalWhiteListIpVO);
	}

	@Override
	public void updateGlobalGrayList(GlobalGrayListVO grayListVO) {
		sqlSessionTemplate.update("updateById",grayListVO);
	}
	
	@Override
	public void deleteGlobalGrayList(Long userId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("userId", userId);
		sqlSessionTemplate.delete("deleteById",param);
	}
	
	
	@Override
	public List<GlobalGrayListTestVO> queryGlobalGrayListByUserAccount(
			String account) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userAccount", account);
		return sqlSessionTemplate.selectList("queryByTest", param);
	}
	
	@Override
	public List<GlobalGrayListTestVO> queryGlobalGrayListByUserAccount2(
			String account) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userAccount", account);
		return sqlSessionTemplate.selectList("queryByTest2", param);
	}
}
