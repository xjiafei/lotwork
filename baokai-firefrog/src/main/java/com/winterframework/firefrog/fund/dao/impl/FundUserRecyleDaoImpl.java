package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundUserRecyleDao;
import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkRecyleExtVO;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: IFundUserRecyleDao 
* @Description: 附言回收
* @author 你的名字 
* @date 2014-3-11 下午1:21:49 
*  
*/
@Repository("fundUserRecyleDaoImpl")
public class FundUserRecyleDaoImpl extends BaseIbatis3Dao<FundUserRemarkRecyle> implements IFundUserRecyleDao {

	@Override
	public Page<FundUserRemarkRecyleExtVO> getAllRecyleRemarks(PageRequest<FundUserRemarkRecyle> pageRequest)
			throws Exception {
		Map<String, Object> map =new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		Number totalCount = sqlSessionTemplate.selectOne(getQueryPath("getCountByPage"), map);
		if (totalCount == null) {
			return new CountPage<FundUserRemarkRecyleExtVO>(0);
		}
		CountPage<FundUserRemarkRecyleExtVO> page = new CountPage<FundUserRemarkRecyleExtVO>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundUserRemarkRecyleExtVO> list = sqlSessionTemplate.selectList(getQueryPath("getAllRecyleRemarks"),
				filters, rowBounds);
		page.setResult(list);
		return page;
	}

	@Override
	public void saveFundUserRecyle(FundUserRemarkRecyle recyle) {
		this.insert(recyle);
	}

	@Override
	public void deleteFundUserRecyle(String remark) {
		this.sqlSessionTemplate.delete(getQueryPath("deleteFundUserRecyle"), remark);

	}
	@Override
	public void deleteFundUserRecyle(Long id) {
		this.delete(id);

	}
}
