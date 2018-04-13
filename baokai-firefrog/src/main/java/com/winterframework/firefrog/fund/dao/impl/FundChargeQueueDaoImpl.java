/**   
* @Title: FundChargeDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:39:51 
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.vo.FundCharge;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.SumCount;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundChargeDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-1 上午10:39:51 
*  
*/
@Repository("fundChargeQueueDaoImpl")
public class FundChargeQueueDaoImpl extends BaseIbatis3Dao<FundChargeQueue> implements IFundChargeQueueDao {

	@Override
	public long addOrderQueue(FundChargeQueue fundChargeQueue) {
		return this.insert(fundChargeQueue);
	}

	@Override
	public long updateOrderQueue(FundChargeQueue fundChargeQueue) {
		return this.update(fundChargeQueue);
	}

	@Override
	public FundChargeQueue queryByOrderNum(String orderNum) throws Exception {
		FundChargeQueue fundChargeQueue = new FundChargeQueue();
		fundChargeQueue.setSn(orderNum);
		List<FundChargeQueue> result =  sqlSessionTemplate.selectList("com.winterframework.firefrog.fund.dao.vo.FundChargeQueue.getBySn",fundChargeQueue);
		if (!result.isEmpty()) {
			return  result.get(0);

		}
		return null;
	}

	@Override
	public long updateOrderQueueStatus(String sn, Long status) throws Exception {
		FundChargeQueue fundChargeQueue = new FundChargeQueue();
		fundChargeQueue.setSn(sn);
		fundChargeQueue.setStatus(status);
		return this.update(fundChargeQueue);
	}

	@Override
	public List<FundChargeQueue> queryUntreatOrder() throws Exception {
		List<FundChargeQueue> result =  sqlSessionTemplate.selectList("com.winterframework.firefrog.fund.dao.vo.FundChargeQueue.getUntreatOrder");		
		return result;
	}
	
	
}
