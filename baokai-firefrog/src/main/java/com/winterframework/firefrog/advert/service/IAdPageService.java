
package com.winterframework.firefrog.advert.service;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdPage;


/** 
* @ClassName: IAdPageService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午2:08:34 
*  
*/
public interface IAdPageService {
	public List<AdPage> getAllAdPage() throws Exception;
}
