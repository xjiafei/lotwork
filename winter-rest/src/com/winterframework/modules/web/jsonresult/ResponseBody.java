package com.winterframework.modules.web.jsonresult;

import com.winterframework.modules.page.Page;

public class ResponseBody<T> implements java.io.Serializable{
	private T result;
	private ResultPager pager;
   
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public ResultPager getPager() {
		return pager;
	}
	public void setPager(ResultPager pager) {
		this.pager = pager;
	}	
	
}
