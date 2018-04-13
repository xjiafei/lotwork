package com.winterframework.firefrog.advert.web.dto;


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
		Double totald = Double.valueOf(rp.getTotal())/Double.valueOf(page.getPageSize());
		int total = rp.getTotal()/page.getPageSize()+1;
		if(totald == rp.getTotal()/page.getPageSize()){
			total = total-1;
		}
		pv.setTotalPages(total);
		pv.setPageNo(rp.getStartNo()/page.getPageSize()+1);
		pv.setPageSize(page.getPageSize());
		pv.setTotalCount(rp.getTotal());
		return pv;
	}
	
	public static void main(String args[]){
		System.out.print(Double.valueOf(2.00)==2);
	}

}
