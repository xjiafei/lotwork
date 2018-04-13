package com.winterframework.firefrog.global.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListIpVO;
import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("globalWhiteListIpDaoImpl")
public class GlobalWhiteListIpDaoImpl extends BaseIbatis3Dao<GlobalWhiteListIpVO> implements GlobalWhiteListIpDao {

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao#saveGlobalWhiteListIp(com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest)
	 * 
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 */
	@Override
	public void saveGlobalWhiteListIp(GlobalWhiteListIpVO globalWhiteListIpVO) {
		this.insert(globalWhiteListIpVO);	
	}

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao#updateGlobalWhiteListIp(com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest)
	 * 
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 */
	@Override
	public void updateGlobalWhiteListIp(GlobalWhiteListIpVO globalWhiteListIpVO ) {
		this.update(globalWhiteListIpVO);
	}

	/* (non-Javadoc)
	 * @see com.winterframework.firefrog.global.dao.GlobalWhiteListIpDao#countWhiteListData(com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest)
	 * 
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 * 
	 */
	@Override
	public Long countWhiteListData(
			GlobalWhiteListIpVO globalWhiteListIpVO) throws Exception {
		return (Long) sqlSessionTemplate.selectOne("getCountByCondition", globalWhiteListIpVO);
	}


	/*
	 * 指定IP白名單 : 分頁搜尋
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 * */
	@Override
	public List<GlobalWhiteListIpVO> queryGlobalWhiteListIp(
			Page<GlobalWhiteListIp> page, Map<String, Object> filters,
			RowBounds rowBounds) throws Exception {
		return sqlSessionTemplate.selectList("getWhiteListIpByPage", filters, rowBounds);
	}
	@Override
	public void deleteGlobalWhiteListIp(GlobalWhiteListIpVO whiteListIpVO) {
		sqlSessionTemplate.delete("deleteWhiteList", whiteListIpVO);
	}
	/*
	 * 指定IP白名單 : 單筆搜尋
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 * */
	@Override
	public GlobalWhiteListIpVO queryGlobalWhiteListIpById(
			GlobalWhiteListIpVO globalWhiteListIpVO) {
		
		return sqlSessionTemplate.selectOne("getWhiteListIpByEntity", globalWhiteListIpVO);
	}
	/*
	 * 指定IP白名單 : 依據user Accunt 取得限制的IP清單
	 * */
	@Override
	public List<GlobalWhiteListIpVO> getIpListByUserAccunt(String userAccunt) {
		GlobalWhiteListIpVO globalWhiteListIpVO = new GlobalWhiteListIpVO();
		globalWhiteListIpVO.setUserAccunt(userAccunt);	
		return sqlSessionTemplate.selectList("getWhiteListIpByUser", globalWhiteListIpVO);
	}

	
}
