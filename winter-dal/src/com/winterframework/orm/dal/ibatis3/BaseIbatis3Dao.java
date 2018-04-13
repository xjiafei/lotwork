package com.winterframework.orm.dal.ibatis3;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.ibatis3.SqlSessionCallback;
import org.springframework.orm.ibatis3.SqlSessionTemplate;
import org.springframework.util.Assert;

import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.utils.ReflectionUtils;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @author abba
 * @version 1.0
 */
public class BaseIbatis3Dao<E extends BaseEntity> extends DaoSupport implements BaseDao<E> {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static final String INSERT = "insert";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	@SuppressWarnings("unused")
	private static final String UPDATEDELETE = "update_delete";
	private static final String SELECT_BY_PK = "getById";
	private static final String SELECT_BY_PKS = "getByIds";
	private static final String SELECT_ALL = "getAll";
	private static final String SELECT_PAGE = "getByPage";
	private static final String SELECT_COUNT_BY_ENTITY = "getCountByPage";
	private static final String SELECT_COUNT = "getCount";
	private static final String SELECT_PAGE_COUNT = "getCountByPage";
	private final Class<E> entityClass;
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	public BaseIbatis3Dao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	@Override
	public E getById(Long primaryKey) {
		return sqlSessionTemplate.selectOne(this.getQueryPath(SELECT_BY_PK), primaryKey);
	}

	@Override
	public List<E> getByIds(List<Long> primaryKey) {
		if (primaryKey == null || primaryKey.size() == 0) {
			return new ArrayList<E>();
		}
		return sqlSessionTemplate.selectList(this.getQueryPath(SELECT_BY_PKS), primaryKey);
	}

	@Override
	public List<E> getAll() {
		return sqlSessionTemplate.selectList(this.getQueryPath(SELECT_ALL));
	}

	public long getCountByEntity(E entity) {
		Long count = sqlSessionTemplate.selectOne(this.getQueryPath(SELECT_COUNT_BY_ENTITY), entity);
		return count.longValue();
	}

	@Override
	public List<E> getAllByEntity(E entity) {
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(entity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return sqlSessionTemplate.selectList(this.getQueryPath(SELECT_PAGE), param);
	}

	@Override
	public <R> Page<E> getAllByPage(PageRequest<R> pageRequest) {
		return this.pageQuery(pageRequest, null, null);
	}

	@Override
	public int delete(Long id) {
		return sqlSessionTemplate.delete(this.getQueryPath(DELETE), id);
	}

	@Override
	public int insert(E entity) {
		prepareObjectForSaveOrUpdate(entity, true);
		return sqlSessionTemplate.insert(this.getQueryPath(INSERT), entity);
	}

	@Override
	public void insert(final List<E> entitys) {
		int size=entitys.size();
		int cycle = entitys.size() / 500;
		int more = entitys.size() % 500;
		if (more > 0){
			//如果有余数，需要多一次循环
				cycle++;
		}
		for (int i = 0; i < cycle; i++) {
			//为了安全起见，500条一次提交
			//final List<E> li = entitys.subList(i * 500, ((i + 1) * 500>=cycle)?cycle:(i + 1) * 500);
			final List<E> li = entitys.subList(i * 500, ((i + 1) * 500)>size?size:(i + 1) * 500);
			insert(li,true);
		}

	}

	private void insert(final List<E> entitys, boolean cycle) {
		//为了安全起见，500条一次提交
		sqlSessionTemplate.execute(new SqlSessionCallback<Integer>() {
			@Override
			public Integer doInSqlSession(SqlSession sqlSession) throws Exception {
				for (E entity : entitys) {
					prepareObjectForSaveOrUpdate(entity, true);
					sqlSession.insert(getQueryPath(INSERT), entity);
				}
				return entitys.size();
			}
		}, ExecutorType.BATCH);

	}

	@Override
	public int update(E entity) {
		prepareObjectForSaveOrUpdate(entity, false);
		return sqlSessionTemplate.update(this.getQueryPath(UPDATE), entity);
	}

	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * 
	 * @param o
	 */
	protected void prepareObjectForSaveOrUpdate(E o, boolean isSave) {
		if (o != null) {
			if (isSave) {
				o.setGmtCreated(new Timestamp(System.currentTimeMillis()));
				o.setGmtModified(new Timestamp(System.currentTimeMillis()));
				if (o.getIsDeleted() == null) {
					o.setIsDeleted(Boolean.FALSE);
				}
				if (RequestContext.getCurrUser() != null && RequestContext.getCurrUser().getId() != null) {
					o.setCreator(RequestContext.getCurrUser().getId());
				} else {
					o.setCreator(0L);
				}
			} else {
				o.setGmtModified(new Timestamp(System.currentTimeMillis()));
				if (RequestContext.getCurrUser() != null && RequestContext.getCurrUser().getId() != null) {
					o.setModifier(RequestContext.getCurrUser().getId());
				} else {
					o.setModifier(0L);
				}
				if (o.getIsDeleted() == null) {
					o.setIsDeleted(Boolean.FALSE);
				}
			}
		}
	}

	protected void prepareObjectForSaveOrUpdate(List<E> o, boolean isSave) {
		if (o != null) {
			if (isSave) {
				for (E e : o)
					e.setGmtCreated(new Timestamp(System.currentTimeMillis()));
			} else {
				for (E e : o)
					e.setGmtModified(new Timestamp(System.currentTimeMillis()));
			}
		}
	}

	protected String getQueryPath(String type) {
		return entityClass.getName() + "." + type;
	}

	protected Page<E> pageQuery(PageRequest<? extends E> pageRequest, String postfix) {
		return pageQuery(pageRequest, null, postfix);
	}

	protected <R> Page<E> pageQuery(PageRequest<R> pageRequest, String prefix, String postfix) {
		String selectPageCount = SELECT_PAGE_COUNT;
		String selectPage = SELECT_PAGE;

		if (StringUtils.isNotBlank(prefix)) {
			selectPageCount = prefix + "_" + selectPageCount;
			selectPage = prefix + "_" + selectPage;
		}
		if (StringUtils.isNotBlank(postfix)) {
			selectPageCount = selectPageCount + "_" + postfix;
			selectPage = selectPage + "_" + postfix;
		}
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Number totalCount = (Number) sqlSessionTemplate.selectOne(this.getQueryPath(selectPageCount), param);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<E>(pageRequest, 0);
		}

		Page<E> page = new Page<E>(pageRequest, totalCount.intValue());

		// 其它分页参数,用于不喜欢或是因为兼容性而不使用方言(Dialect)的分页用户使用.
		// 与getSqlMapClientTemplate().queryForList(statementName,
		// parameterObject)配合使用
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(param);
		//w
		// 混合两个filters为一个filters,MapAndObject.get()方法将在两个对象取值,Map如果取值为null,则再在Bean中取值
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<E> list = sqlSessionTemplate.selectList(this.getQueryPath(selectPage), filters, rowBounds);
		page.setResult(list);
		return page;
	}

	@Override
	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int merge(E entity) {
		if (entity.getId() == null) {
			return this.insert(entity);
		} else {
			return this.update(entity);
		}
	}

	@Override
	public long getCount() {
		Long lon = sqlSessionTemplate.selectOne(this.getQueryPath(SELECT_COUNT));
		return lon.longValue();
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull(this.sqlSessionTemplate, "sqlSessionFactory must be not null");
	}

	@Override
	public void flush() {
		//
	};

}
