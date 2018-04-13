package com.winterframework.firefrog.advert.dao.vo;

import com.winterframework.modules.page.Page;

public class CountPage<T> extends Page<T>{
	public CountPage(int PageNumber){
		super(PageNumber);
	}

	public CountPage(int pageNumber, int pageSize, int totalCount) {
		super(pageNumber, pageSize, totalCount);

	}

	public Long sumWait;
	public Long sumReviewing;
	public Long sumNotPass;
	public Long getSumWait() {
		return sumWait;
	}

	public void setSumWait(Long sumWait) {
		this.sumWait = sumWait;
	}

	public Long getSumReviewing() {
		return sumReviewing;
	}

	public void setSumReviewing(Long sumReviewing) {
		this.sumReviewing = sumReviewing;
	}

	public Long getSumNotPass() {
		return sumNotPass;
	}

	public void setSumNotPass(Long sumNotPass) {
		this.sumNotPass = sumNotPass;
	}
}
