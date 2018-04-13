package com.winterframework.orm.dal.ibatis3;

import java.util.List;

import org.apache.ibatis.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * @author abba
 */
public abstract class BaseManager<DAO extends BaseDao<E>, E extends BaseEntity> {

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected DAO entityDao;

	public abstract void setEntityDao(DAO entityDao);

	public BaseDao<E> getEntityDao() {
		return this.entityDao;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public E getById(Long id) {
		return entityDao.getById(id);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public List<E> getByIds(List<Long> ids) {
		return entityDao.getByIds(ids);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public Page<E> getPage(PageRequest<E> page) {
		return entityDao.getAllByPage(page);

	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public List<E> getAll() {
		return entityDao.getAll();
	}
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public long getCountByEntity(E entity) {
		return entityDao.getCountByEntity(entity);
	}
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public long getCount() {
		return entityDao.getCount();
	}
	public void saveOrUpdate(E entity) {
		entityDao.merge(entity);
	}
	public void save(E entity) {
		entityDao.insert(entity);
	}

	public void removeById(Long id) {
		entityDao.delete(id);
	}

	public void update(E entity) {
		entityDao.update(entity);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public List<E> getByEntity(E entity) {
		return entityDao.getAllByEntity(entity);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public Page<E> findByPageRequest(PageRequest<E> pr) {
		return getEntityDao().getAllByPage(pr);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
	public boolean isUnique(E entity, String uniquePropertyNames) {
		return entityDao.isUnique(entity, uniquePropertyNames);
	}

	public void insertBatch(List<E> entitys) {
		entityDao.insert(entitys);
	}
}
