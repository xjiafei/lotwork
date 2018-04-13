package com.winterframework.firefrog.global.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.global.dao.GlobalIpDao;
import com.winterframework.firefrog.global.dao.vo.GlobalIpVO;
import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("globalIpDaoImpl")
public class GlobalIpDaoImpl extends BaseIbatis3Dao<GlobalIpVO> implements GlobalIpDao {

	@Override
	public void saveIp(IPAddress ip) throws Exception {
		GlobalIpVO vo = VOConverter.transIp2VO(ip);
		this.insert(vo);
	}

	@Override
	public void deleteIp(Long id) throws Exception {
		this.delete(id);
	}

	@Override
	public Page<IPAddress> selectList(PageRequest<IpSearchDto> pageRequest) throws Exception {
		Long type = pageRequest.getSearchDo().getType();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		int totalCount = ((Long) sqlSessionTemplate.selectOne("getIpCountByPage", map)).intValue();
		Page<IPAddress> page = new Page<IPAddress>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount);
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GlobalIpVO> list = sqlSessionTemplate.selectList("getIpByPage", filters, rowBounds);
		List<IPAddress> wordList = new ArrayList<IPAddress>();
		for (GlobalIpVO vo : list) {
			wordList.add(VOConverter.transVO2Ip(vo));
		}
		page.setResult(wordList);
		return page;
	}

	/**
	* Title: queryEffectIpList
	* Description:
	* @param type
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.global.dao.GlobalIpDao#queryEffectIpList(java.lang.Integer) 
	*/
	@Override
	public List<IPAddress> queryEffectIpList(String type) throws Exception {
		List<IPAddress> wordList = new ArrayList<IPAddress>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("date", new Date());
		List<GlobalIpVO> list = sqlSessionTemplate.selectList("getEffectIpList",map);
		for (GlobalIpVO vo : list) {
			wordList.add(VOConverter.transVO2Ip(vo));
		}
		return wordList;
	}
	
}
