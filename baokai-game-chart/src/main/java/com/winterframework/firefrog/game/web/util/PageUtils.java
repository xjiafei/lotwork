package com.winterframework.firefrog.game.web.util;

import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: PageUtils 
* @Description: 分页处理工具类 
* @author david 
* @date 2013-10-29 下午5:44:16 
*  
*/
public class PageUtils {
	public static Pager getPager(PageRequest<?> page){
		Pager pager = new Pager();
		pager.setStartNo(page.getPageNo() == 0 ? 0 : (page.getPageNo() - 1) * page.getPageSize());
		pager.setEndNo(page.getPageSize() - 1 + pager.getStartNo());
		return pager;
	}
	
	public static PageForView getPageForView(PageRequest<?> page,ResultPager rp){
		PageForView pv=new PageForView();
		pv.setTotalPages(rp.getTotal()/page.getPageSize()+1);
		pv.setPageNo(rp.getStartNo()/page.getPageSize()+1);
		pv.setPageSize(page.getPageSize());
		pv.setTotalCount(rp.getTotal());
		return pv;
	}

}
