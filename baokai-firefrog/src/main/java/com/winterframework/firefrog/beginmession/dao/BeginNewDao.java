package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.google.common.base.Optional;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginNewDao<E> extends BaseDao<E>{

	public Optional<List<E>> findMaxVersion();
	
	public Long getMaxVersion();
	
	public Optional<List<E>> findByCondition(E entity);
	
	public Optional<List<E>> findByVersion(Long version);
	
	public Optional<E> findOneByVersion(Long version);
}
