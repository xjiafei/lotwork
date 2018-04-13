package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;

public interface BeginNewDaybetDao extends BeginNewDao<BeginNewDaybet> {

	List<BeginNewDaybet> getDayBetAward(Long userId,Long betAmount,Long version);
}
