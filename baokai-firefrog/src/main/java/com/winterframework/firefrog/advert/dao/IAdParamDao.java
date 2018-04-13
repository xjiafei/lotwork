package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdParam;


/** 
 * 广告参数的的dao
* @ClassName: IAdParamDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:41:02 
*  
*/
public interface IAdParamDao {
	
	List<AdParam> getAllAdParam() throws Exception;

	AdParam getAdParamById(Long id);
}
