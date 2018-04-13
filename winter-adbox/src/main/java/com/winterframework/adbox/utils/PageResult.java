package com.winterframework.adbox.utils;

import java.util.List;

/**
 * @author denny
 * @desc 分页结果集对象
 * @date 2015-11-25 
 */
public class PageResult<T> {
	protected int total;
	protected List<T> rows;
	public PageResult(int total,List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public PageResult() {
		
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
	

}
