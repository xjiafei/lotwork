package com.winterframework.adbox.dao.base;
 
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.utils.DateUtils;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public abstract class BaseServiceImpl<DAO extends IBaseDao<E>, E extends BaseEntity> implements IBaseService<E> { 
	protected Logger log = LoggerFactory.getLogger(getClass()); 
	
	protected abstract DAO getEntityDao();
	
  	public E get(Long id) throws Exception{
		return (E)getEntityDao().getById(id);
	}

	public int save(Context ctx,E entity) throws Exception {
		if(null!=entity){
			try{
				if(null==entity.getId()){
					entity.setCreator(ctx.getUserId());
					entity.setGmtCreated(DateUtils.currentDate());
					return getEntityDao().insert(entity); 
				}else{
					entity.setModifier(ctx.getUserId());
					entity.setGmtModified(DateUtils.currentDate());
					return getEntityDao().update(entity);
				}
			}catch(Exception e){
				log.error("save failed.",e);
				throw new Exception(e);
			}
		}
		return 0;
	}
	@Override
	@Transactional
	public int save(Context ctx,List<E> entityList) throws Exception {
		if(null!=entityList){
			int count=0;
			try{
				List<E> insertList=new ArrayList<E>();
				List<E> updateList=new ArrayList<E>();
				for(E entity:entityList){
					if(null==entity.getId()){
						entity.setCreator(ctx.getUserId());
						entity.setGmtCreated(DateUtils.currentDate());
						insertList.add(entity);
					}else{
						entity.setModifier(ctx.getUserId());
						entity.setGmtModified(DateUtils.currentDate());
						updateList.add(entity);
					}
				}
				count= getEntityDao().insertBatch(insertList)+getEntityDao().updateBatch(updateList);
			}catch(Exception e){
				log.error("save failed.",e);
				throw new Exception(e);
			}
			return count;
		}
		return 0;
	}

	public int remove(Context ctx,Long id) throws Exception {
		if(null!=id){
			try{
				return getEntityDao().delete(id);
			}catch(Exception e){
				log.error("remove failed.",e);
				throw new Exception(e);
			}
		}
		return 0;
	}
	
	public E selectOneObjByAttribute(Context ctx,E entity) throws Exception {
		return  getEntityDao().selectOneObjByAttribute(entity);
	}

	public List<E> selectListObjByAttribute(Context ctx,E entity) throws Exception {
		if (entity == null) {
			return new ArrayList<E>();
		}
		return getEntityDao().selectListObjByAttribute( entity);
	}
	
}
 