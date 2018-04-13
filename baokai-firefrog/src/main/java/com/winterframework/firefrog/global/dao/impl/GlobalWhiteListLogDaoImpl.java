package com.winterframework.firefrog.global.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.dao.GlobalWhiteListLogDao;
import com.winterframework.firefrog.global.dao.vo.GlobalIpVO;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListLogVO;
import com.winterframework.firefrog.global.entity.GlobalWhiteListLog;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("globalWhiteListLogDaoImpl")
public class GlobalWhiteListLogDaoImpl extends BaseIbatis3Dao<GlobalWhiteListLogVO> implements GlobalWhiteListLogDao {

	/*
	 * 指定IP白名單操作歷史紀錄-儲存
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 * */
	@Override
	public void saveGlobalWhiteListLog(
			GlobalWhiteListLogVO globalWhiteListLogVO) {
			this.insert(globalWhiteListLogVO);
	}

	/*
	 * 指定IP白名單操作歷史紀錄-搜尋資料
	 * REVISION HISTORY
	 * ------------------------------------------------------------------------
	 * 2016.05.19 Daid Wu Create
	 * */
	@Override
	public List<GlobalWhiteListLogVO> queryGlobalWhiteListLog(
			GlobalWhiteListLogVO gobalWhiteListLogVO) throws Exception {		
		return sqlSessionTemplate.selectList("getLogRecord" , gobalWhiteListLogVO);
	}	
}
