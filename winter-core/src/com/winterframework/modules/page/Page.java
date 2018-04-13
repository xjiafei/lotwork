package com.winterframework.modules.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 分页信息
 * 第一页从1开始
 * @author abba
 */
@SuppressWarnings("serial")
public class Page<T> implements Serializable {

	protected List<T> result;
	protected Map<String,Object> otherCount;
	protected int pageSize;

	protected int pageNo;
	/**
	 * 设置startNo 和 endNo
	 * @param startNo
	 * @param endNo
	 */
    public void setPageScope (int startNo,int endNo){
    	pageSize=endNo-startNo+1;
		pageNo=startNo/pageSize+1;	       
    }
    
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	protected String orderBy;
	protected String order;

	protected int totalCount = 0;

	public Page(int count) {
		this.pageSize = count;
	}

	public <R> Page(PageRequest<R> p, int totalCount) {
		this(p.getPageNo(), p.getPageSize(), totalCount);
	}

	public Page(int pageNumber, int pageSize, int totalCount) {
		this(pageNumber, pageSize, totalCount, new ArrayList<T>(0));
	}

	public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
		if (pageSize <= 0)
			throw new IllegalArgumentException("[pageSize] must great than zero");
		this.pageSize = pageSize;
		this.pageNo = PageUtils.computePageNumber(pageNumber, pageSize, totalCount);
		this.totalCount = totalCount;
		setResult(result);
	}

	public void setResult(List<T> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.result = elements;
	}

	/**
	 * 当前页包含的数据
	 *
	 * @return 当前页数据源
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 是否是首页（第一页），第一页页码为1
	 *
	 * @return 首页标识
	 */
	public boolean isFirstPage() {
		return getPageNo() == 1;
	}

	/**
	 * 是否是最后一页
	 *
	 * @return 末页标识
	 */
	public boolean isLastPage() {
		return getPageNo() >= getLastPageNumber();
	}

	/**
	 * 是否有下一页
	 *
	 * @return 下一页标识
	 */
	public boolean isHasNextPage() {
		return getLastPageNumber() > getPageNo();
	}

	/**
	 * 是否有上一页
	 *
	 * @return 上一页标识
	 */
	public boolean isHasPreviousPage() {
		return getPageNo() > 1;
	}

	public boolean isHasPre() {
		return isHasPreviousPage();
	}

	public boolean isHasNext() {
		return this.isHasNextPage();
	}

	/**
	 * 获取最后一页页码，也就是总页数
	 *
	 * @return 最后一页页码
	 */
	public int getLastPageNumber() {
		return PageUtils.computeLastPageNumber(totalCount, pageSize);
	}

	public int getTotalPages() {
		return getLastPageNumber();
	}

	/**
	 * 总的数据条目数量，0表示没有数据
	 *
	 * @return 总数量
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取当前页的首条数据的行编码
	 *
	 * @return 当前页的首条数据的行编码
	 */
	public int getThisPageFirstElementNumber() {
		return (getPageNo() - 1) * getPageSize() + 1;
	}

	/**
	 * 获取当前页的末条数据的行编码
	 *
	 * @return 当前页的末条数据的行编码
	 */
	public int getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
		return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}

	/**
	 * 获取下一页编码
	 *
	 * @return 下一页编码
	 */
	public int getNextPage() {
		return getPageNo() + 1;
	}

	/**
	 * 获取上一页编码
	 *
	 * @return 上一页编码
	 */
	public int getPrePage() {
		return getPageNo() - 1;
	}

	/**
	 * 每一页显示的条目数
	 *
	 * @return 每一页显示的条目数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 当前页的页码
	 *
	 * @return 当前页的页码
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 得到用于多页跳转的页码
	 * @return
	 */
	public List<Integer> getLinkPageNumbers() {
		return PageUtils.generateLinkPageNumbers(getPageNo(), getLastPageNumber(), 10);
	}

	/**
	 * 得到数据库的第一条记录号
	 * @return
	 */
	public int getFirstResult() {
		return PageUtils.getFirstResult(pageNo, pageSize);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Map<String, Object> otherCount) {
		this.otherCount = otherCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	

}
