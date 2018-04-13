package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.BeginNewDao;
import com.winterframework.orm.dal.ibatis3.BaseEntity;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

public abstract class BaseBeginNewDao<E extends BaseEntity> extends BaseIbatis3Dao<E> 
	implements BeginNewDao<E>{
	
	@Override
	public Optional<List<E>> findMaxVersion() {
		List<E> lists = this.sqlSessionTemplate.selectList(this.getQueryPath("findMaxVersion"));
		Optional<List<E>> optional  = Optional.fromNullable(lists);
		return optional;
	}

	@Override
	public Long getMaxVersion() {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getMaxVersion"));
	}

	@Override
	public Optional<List<E>> findByCondition(E entity){
		List<E> lists = this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"),entity);
		Optional<List<E>> optional  = Optional.fromNullable(lists);
		return optional;
	};
	
	@Override
	public Optional<List<E>> findByVersion(Long version){
		List<E> list= this.sqlSessionTemplate.selectList(this.getQueryPath("findByVersion"),version);
		return Optional.fromNullable(list);
	}
	
	@Override
	public Optional<E> findOneByVersion(Long version){
		E entity = null;
		List<E> list= this.sqlSessionTemplate.selectList(this.getQueryPath("findByVersion"),version);
		if(list!=null && !list.isEmpty()){
			entity = list.get(0);
		}
		return Optional.fromNullable(entity);
	}
}
