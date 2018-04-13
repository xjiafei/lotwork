package com.winterframework.adbox.dao.base;

import java.util.List;
import com.winterframework.orm.dal.ibatis3.BaseDao;
import com.winterframework.orm.dal.ibatis3.BaseEntity;


public interface IBaseDao<E extends BaseEntity> extends BaseDao<E>{
	int insertBatch(List<E> entitys) throws Exception;
	int updateBatch(List<E> entitys) throws Exception;
	E selectOneObjByAttribute(E entity) throws Exception;
	List<E> selectListObjByAttribute(E entity) throws Exception;
}
