package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;

public interface BeginNewChargeDao extends BeginNewDao<BeginNewCharge>{

	public List<BeginNewCharge> findMaxVersionAndRowNum();

	public Optional<List<BeginNewCharge>> findByCondition2(BeginNewCharge entity);
	
	public Optional<List<BeginNewCharge>> getByMultipleGet(Long userId,Long version,String YesOrNo);
	
	public Long getMaxChargeDate(Long userId);
}
