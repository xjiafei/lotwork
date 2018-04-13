package com.winterframework.modules.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class PageRequest<T> implements Serializable {

	/**
	 * 过滤参数
	 */
	private T searchDo;
	/**
	 * 页号码,页码从1开始
	 */
	private int pageNo;
	/**
	 * 分页大小
	 */
	private int pageSize;
	
	/**
	 * 排序的多个列,如: username desc
	 */
	private String sortColumns;
	private Map<String, Object> filters = new HashMap<String, Object>();

	public PageRequest(T t) {
		this(0, 10);
		searchDo = t;
	}

	public PageRequest() {
		this(null);
	}

	public PageRequest(int pageNo, int pageSize) {
		this(pageNo, pageSize, null);
	}
	
	public static <T> PageRequest<T> getRestPageRequest(int startNo, int endNo){
		int pageSize=endNo-startNo+1;
		int pageNo=startNo/pageSize+1;		
		return new PageRequest<T>(pageNo,pageSize);
	}

	public PageRequest(int pageNo, int pageSize, String sortColumns) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		setSortColumns(sortColumns);
	}

	public T getSearchDo() {
		return searchDo;
	}

	public void setSearchDo(T searchDao) {
		this.searchDo = searchDao;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	/**
	 * 排序的列,可以同时多列,使用逗号分隔,如 username desc,age asc
	 * @return
	 */
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	/**
	 * 将sortColumns进行解析以便返回SortInfo以便使用
	 * @return
	 */
	public List<SortInfo> getSortInfos() {
		return Collections.unmodifiableList(SortInfo.parseSortColumns(sortColumns));
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
	/**
	 * 获取当前页,为兼容而保留,请尽量使用getPageNo()
	 * @return
	 */
	public int getPageNumber() {
		return pageNo;
	}
	/**
	 * 获取当前页,为兼容而保留,请尽量使用setPageNo()
	 * @param pageNo
	 */
	public void setPageNumber(int pageNo) {
		this.pageNo = pageNo;
	}
}
