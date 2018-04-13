/**   
* @Title: FundChargeExceptionDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午8:07:08 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.convert.BeanConverter;
import com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeExceptionVO;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundChargeExceptionDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午8:07:08 
*  
*/
@Repository("fundChargeExceptionDao")
public class FundChargeExceptionDaoImpl extends BaseIbatis3Dao<FundChargeExceptionVO> implements
		IFundChargeExceptionDao {
	/**
	* Title: insert
	* Description:
	* @param entity
	* @return 
	* @see com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao#insert(com.winterframework.orm.dal.ibatis3.BaseEntity) 
	*/
	@Override
	public void save(FundChargeException entity) throws Exception {
		FundChargeExceptionVO vo = new FundChargeExceptionVO();
		BeanConverter.convert(vo, entity);
		insert(vo);
	}

	/**
	 * 
	* Title: query
	* Description:
	* @param fundChargeException
	* @return 
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao#query(com.winterframework.modules.page.PageRequest)
	 */
	@Override
	public Page<FundChargeException> query(PageRequest<ExceptionQueryRequest> pageReqeust) throws Exception {

		Map<String, Object> map = new PageBeanUtilsBean().describe(pageReqeust.getSearchDo());
		int totalCount = ((Long) sqlSessionTemplate.selectOne(
				"com.winterframework.firefrog.fund.dao.vo.FundChargeExceptionVO.getCountByPage", map)).intValue();
		Page<FundChargeException> page = new Page<FundChargeException>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), totalCount);
		if (totalCount > 0) {
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("offset", page.getFirstResult());
			filters.put("pageSize", page.getPageSize());
			filters.put("lastRows", page.getFirstResult() + page.getPageSize());
			filters.put("sortColumns", pageReqeust.getSortColumns());
			filters.putAll(map);
			RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			List<FundChargeExceptionVO> list = sqlSessionTemplate.selectList(
					"com.winterframework.firefrog.fund.dao.vo.FundChargeExceptionVO.getExceptionByPage", filters,
					rowBounds);
			List<FundChargeException> exceptionList = new ArrayList<FundChargeException>();
			for (FundChargeExceptionVO vo : list) {
				FundChargeException exception = new FundChargeException();
				BeanConverter.convert(exception, vo);
				exception.setStatus(vo.getStatus());
				exceptionList.add(exception);
			}
			page.setResult(exceptionList);
		}
		return page;
	}

	/**
	* Title: update
	* Description:
	* @param fundChargeException 
	 * @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao#update(com.winterframework.firefrog.fund.entity.FundChargeException) 
	*/
	@Override
	public int update(FundChargeException fundChargeException) throws Exception {
		int result = 0;
		if (fundChargeException.getId() != null) {
			FundChargeExceptionVO vo = new FundChargeExceptionVO();
			try {
				BeanConverter.convert(vo, fundChargeException);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setStatus(fundChargeException.getStatus());
			result = this.update(vo);
		} else {
			FundChargeExceptionVO vo = new FundChargeExceptionVO();
			BeanConverter.convert(vo, fundChargeException);
			vo.setStatus(fundChargeException.getStatus());
			prepareObjectForSaveOrUpdate(vo, false);
			if(vo.getMcSn()==null)
		    	vo.setMcSn(vo.getSn());
			result = sqlSessionTemplate.update(this.getQueryPath("updateByMcSn"), vo);
		}
		return result;
	}

	/**
	* Title: queryById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao#queryById(java.lang.Long) 
	*/
	@Override
	public FundChargeException queryById(Long id) throws Exception {
		FundChargeExceptionVO vo = this.getById(id);
		FundChargeException chargeException = new FundChargeException();
		BeanConverter.convert(chargeException, vo);
		return chargeException;
	}
	@Override
	public void yuchuli(Long id,String yuchuliId,Long currStatus){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id",id);
		map.put("currApprer",yuchuliId);
		map.put("currDate",new Date());
		map.put("currStatus",currStatus);
		sqlSessionTemplate.update(this.getQueryPath("yuchuli"), map);
	}
	@Override
	public int zero(Long id,Long...checkStatuses){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id",id);
		map.put("checkStatuses",checkStatuses);
		return sqlSessionTemplate.update(this.getQueryPath("zero"), map);
	}
	@Override
	public void yuchuliEnd(Long id,Long currStatus){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id",id);
		map.put("currDate",new Date());
		map.put("currStatus",currStatus);
		sqlSessionTemplate.update(this.getQueryPath("yuchuliEnd"), map);
	}

	@Override
	public FundChargeException queryBySn(String sn) throws Exception {
		FundChargeExceptionVO entity = new FundChargeExceptionVO();
		entity.setSn(sn);
		List<FundChargeExceptionVO> voList = this.getAllByEntity(entity);
		if (voList == null || voList.isEmpty()) {
			return null;
		}
		FundChargeException chargeException = new FundChargeException();
		BeanConverter.convert(chargeException, voList.get(0));
		return chargeException;
	}

}
