package com.winterframework.firefrog.common.util;

import java.util.List;

import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;
import com.winterframework.orm.dal.ibatis3.BaseEntity;
import com.winterframework.orm.dal.ibatis3.BaseManager;

public class PageTool {

	public static <T extends BaseEntity>  Response<List<T>> getPageRequestByRequest(BaseManager<?,T> bd,Request<T> request){
		Response<List<T>> rs = new Response<List<T>>(request);
		PageRequest<T>  pr=PageRequest.getRestPageRequest(request.getBody().getPager().getStartNo(),request.getBody().getPager().getEndNo());
		pr.setSortColumns("gmtCreated desc");
		pr.setSearchDo(request.getBody().getParam());
	
		Page<T> urls = bd.findByPageRequest(pr);
		rs.setResult(urls.getResult());
		ResultPager resultPager=new ResultPager(urls.getThisPageFirstElementNumber(),urls.getThisPageLastElementNumber(),urls.getTotalCount());
		rs.setResultPage(resultPager);
		return rs;
	}
	
}
