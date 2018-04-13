package com.winterframework.firefrog.common.util;

import com.winterframework.modules.page.PageRequest;

/**
 * 
 *    
 * 类功能说明:  分页功能工具类 
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 *
 */
public class PageConverterUtils {

	/**
	 * 
	 * 方法描述：根据StartNo和endNo返回pageRequest
	 * @param startNo
	 * @param endNo
	 * @return
	 */
	public static <T> PageRequest<T> getRestPageRequest(int startNo, int endNo) {

		if (startNo > endNo) {
			throw new IllegalArgumentException("页面开始号码不能大于结束号码");
		}
		int pageSize = endNo - startNo + 1;
		int pageNo = startNo / pageSize + 1;
		return new PageRequest<T>(pageNo, pageSize);
	}

}
