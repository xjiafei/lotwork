/**   
* @Title: FundTransferDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 下午2:03:04 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.IFundTransferDao;
import com.winterframework.firefrog.fund.dao.vo.FundTransfer;
import com.winterframework.firefrog.fund.dao.vo.FundTransferView;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.SumCount;
import com.winterframework.firefrog.fund.web.dto.FundTransferRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequest;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundTransferDaoImpl")
public class FundTransferDaoImpl extends BaseIbatis3Dao<FundTransfer> implements IFundTransferDao {

	@Override
	public void saveUserFundTransfer(FundTransferOrder fundTransferOrder) {
		FundTransfer fundTransfer = VOConverter.FundTransferOrder2FundTransfer(fundTransferOrder);
		insert(fundTransfer);
	}

	@Override
	public CountPage<FundTransferOrder> searchFundTransferOrder(PageRequest<FundTransferRecordQueryDTO> pageReqeust)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		FundTransferRecordQueryDTO queryDTO = pageReqeust.getSearchDo();

		if (StringUtils.isNotBlank(queryDTO.getSn())) {
			map.put("sn", queryDTO.getSn());
		}

		if (null != queryDTO.getFromDate()) {
			map.put("fromDate", DataConverterUtil.convertLong2Date(queryDTO.getFromDate()));
		}

		if (null != queryDTO.getToDate()) {
			map.put("toDate", DataConverterUtil.convertLong2Date(queryDTO.getToDate()));
		}
		if (null != queryDTO.getExactUser()) {
			map.put("exactUser", queryDTO.getExactUser());
		}
		if (null != queryDTO.getDirection()) {
			map.put("direction", queryDTO.getDirection());
		}
		if (null != queryDTO.getRecycleDate()) {
			map.put("recycleDate", queryDTO.getRecycleDate());
		}

		return getFundTransferOrderRecord(map, pageReqeust);
	}

	private CountPage<FundTransferOrder> getFundTransferOrderRecord(Map<String, Object> map,
			PageRequest<FundTransferRecordQueryDTO> pageReqeust) throws Exception {
		SumCount totalCount = (SumCount) sqlSessionTemplate.selectOne("getCountByTransferRecordList", map);
		if (totalCount.getCount() == null) {
			return new CountPage<FundTransferOrder>(0);
		}

		CountPage<FundTransferOrder> page = new CountPage<FundTransferOrder>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), totalCount.getCount().intValue());
		page.setSum(totalCount.getSum());
		page.setSum2(totalCount.getSum2());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundTransfer> list = sqlSessionTemplate.selectList("queryTransferRecordList", filters, rowBounds);

		List<FundTransferOrder> fundTransferOrderList = new ArrayList<FundTransferOrder>();

		for (FundTransfer fundTransfer : list) {

			try {

				FundTransferOrder fundTransferOrder = VOConverter.fundTransfer2FundTransferOrder(fundTransfer);
				fundTransferOrderList.add(fundTransferOrder);

			} catch (Exception e) {
				log.error("查询转账列表，映射类转换 实体类出错；" + e.getMessage(), e);
				throw e;
			}
		}
		page.setResult(fundTransferOrderList);

		return page;
	}

	public List<FundTransferView> queryViewFundTrasfer(FundTransferRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (request != null) {
			if (null != request.getStartDate()) {
				map.put("fromDate", DataConverterUtil.convertLong2Date(request.getStartDate()));
			}

			if (null != request.getEndDate()) {
				map.put("toDate", DataConverterUtil.convertLong2Date(request.getEndDate()));
			}
			if (null != request.getAccount()) {
				map.put("account", request.getAccount());
			}
			if (null != request.getDirection()) {
				map.put("direction", request.getDirection());
			}
			if (null != request.getDirection()) {
				map.put("exact", request.getDirection());
			}
		}

		return sqlSessionTemplate.selectList("queryViewFundTrasfer", map);
	}
}
