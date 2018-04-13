package com.winterframework.orm.dal.ibatis3;

import java.util.List;

import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * @author abba
 */
public interface BaseDao<E> {
	/**
	 * 根据id获取entry
	 * @param id
	 * @return
	 */
	public E getById(Long id);

	public List<E> getByIds(List<Long> ids);

	/**
	 * 获取所有的entity
	 * @return
	 */
	public List<E> getAll();

	public List<E> getAllByEntity(E entity);

	public <R> Page<E> getAllByPage(PageRequest<R> page);
    public long getCountByEntity(E entity);
	/**
	 * 获取行
	 * @return
	 */
	public long getCount();

	/**
	 * 根据pk删除entity
	 * @param id
	 * @return
	 */
	public int delete(Long id);

	public int insert(E entity);

	public void insert(List<E> entitys);

	public int update(E entity);

	public int merge(E entity);

	public boolean isUnique(E entity, String uniquePropertyNames);

	public void flush();

}
