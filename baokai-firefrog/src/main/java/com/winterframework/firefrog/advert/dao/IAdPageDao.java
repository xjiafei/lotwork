package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdPage;



/** 
 * 广告页的接口
* @ClassName: IAdPageDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:40:36 
*  
*/
public interface IAdPageDao  {
	List<AdPage> getAdAllPage() throws Exception;
}
