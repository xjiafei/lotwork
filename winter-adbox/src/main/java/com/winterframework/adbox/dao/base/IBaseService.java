package com.winterframework.adbox.dao.base; 

import java.util.List;
import com.winterframework.orm.dal.ibatis3.BaseEntity;


public interface IBaseService<E extends BaseEntity>{	 
	E get(Long id) throws Exception;
	int save(Context ctx,E entity) throws Exception;
	int save(Context ctx,List<E> entityList) throws Exception;
	int remove(Context ctx,Long id) throws Exception;
	public E selectOneObjByAttribute(Context ctx,E entity) throws Exception ;
	public List<E> selectListObjByAttribute(Context ctx,E entity) throws Exception ;
}
