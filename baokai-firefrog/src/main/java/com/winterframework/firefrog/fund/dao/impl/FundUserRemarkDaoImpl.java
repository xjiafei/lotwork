package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundUserRemarkDao;
import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkExtVO;
import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkVO;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: IFundUserRemarkDao 
* @Description: 用户和附言dao
* @author 你的名字 
* @date 2014-3-11 下午1:21:54 
*  
*/
@Repository("fundUserRemarkDaoImpl")
public class FundUserRemarkDaoImpl extends BaseIbatis3Dao<FundUserRemarkVO> implements IFundUserRemarkDao {

	/**
	* Title: batchSaveRemark
	* Description:
	* @param userIds 
	* @see com.winterframework.firefrog.fund.dao.IFundUserRemarkDao#batchSaveRemark(java.util.List) 
	*/
	@Override
	public void batchSaveRemark(List<Long> userIds) {
		for (Long long1 : userIds) {
			FundUserRemarkVO vo = new FundUserRemarkVO();
			vo.setUserId(long1);
			vo.setGmtCansetremark(new Date());
			vo.setGmtAutounlocked(new Date());
			insert(vo);
		}
	}

	/**
	* Title: updateUserRemark
	* Description:
	* @param manual 
	* @see com.winterframework.firefrog.fund.dao.IFundUserRemarkDao#updateUserRemark(com.winterframework.firefrog.fund.entity.FundUserRemark) 
	*/
	@Override
	public void updateUserRemark(FundUserRemark manual) {
		FundUserRemarkVO vo = VOConverter.transUserRemarkEntityToVO(manual);
		this.update(vo);
	}
	
	/**
	* Title: getUserRemarkByUserId
	* Description:
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.fund.dao.IFundUserRemarkDao#getUserRemarkByUserId(java.lang.Long) 
	*/
	@Override
	public FundUserRemark getUserRemarkByUserId(Long userId) {
		FundUserRemarkVO extVO = this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserRemarkByUserId"), userId);
		if (extVO != null) {
			return VOConverter.transUserRemarkVOToEntity(extVO);
		} else {
			return null;
		}
	}

	/**
	* Title: getPageRemarks
	* Description:
	* @param pageRequest
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundUserRemarkDao#getPageRemarks(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<FundUserRemark> getPageRemarks(PageRequest<FundUserRemark> pageRequest) throws Exception {
		Map<String, Object> map = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		Number totalCount = sqlSessionTemplate.selectOne(getQueryPath("getCountByPage"), map);
		if (totalCount == null) {
			return new CountPage<FundUserRemark>(0);
		}
		CountPage<FundUserRemark> page = new CountPage<FundUserRemark>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundUserRemarkExtVO> list = sqlSessionTemplate.selectList(getQueryPath("getPageRemarks"), filters,
				rowBounds);

		List<FundUserRemark> orderList = new ArrayList<FundUserRemark>();
		for (FundUserRemarkExtVO fundUserRemarkExtVO : list) {
			orderList.add(VOConverter.transUserRemarkExtVOToEntity(fundUserRemarkExtVO));
		}
		page.setResult(orderList);
		return page;
	}
}
