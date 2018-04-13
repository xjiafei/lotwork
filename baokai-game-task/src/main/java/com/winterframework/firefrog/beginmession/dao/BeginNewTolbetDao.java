package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;

public interface BeginNewTolbetDao extends BeginNewDao<BeginNewTolbet>{

	List<BeginNewTolbet> getTolBetAward(Long version , Integer betDay,Long userId);
}
