package com.winterframework.firefrog.beginmession.dao;

import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;

public interface BeginLotterySetDao extends BeginNewDao<BeginLotterySet>{

	public BeginLotterySet findById(Long id);
}
