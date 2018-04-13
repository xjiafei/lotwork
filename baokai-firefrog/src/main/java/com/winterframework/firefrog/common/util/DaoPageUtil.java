package com.winterframework.firefrog.common.util;

import org.apache.ibatis.session.RowBounds;

import com.winterframework.modules.page.PageUtils;
import com.winterframework.modules.web.jsonresult.Pager;

public class DaoPageUtil {

	public static RowBounds createRowBounds(Pager pager){
		int pageSize = pager.getEndNo()-pager.getStartNo()+1;
		int pageNumber = pager.getStartNo()/pageSize+1;
		int firstResult = PageUtils.getFirstResult(pageNumber, pageSize);
		RowBounds rowBounds = new RowBounds(firstResult, pageSize);
		return rowBounds;
	}
}
