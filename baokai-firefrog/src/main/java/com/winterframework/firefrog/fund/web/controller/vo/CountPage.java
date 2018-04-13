package com.winterframework.firefrog.fund.web.controller.vo;

import com.winterframework.modules.page.Page;

public class CountPage<T> extends Page<T>{
	public CountPage(int PageNumber){
		super(PageNumber);
	}

	public CountPage(int pageNumber, int pageSize, int totalCount) {
		super(pageNumber, pageSize, totalCount);

	}

	public Long sum;
	public Long sum2;
	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public Long getSum2() {
		return sum2;
	}

	public void setSum2(Long sum2) {
		this.sum2 = sum2;
	}
	

	
}
