package com.winterframework.firefrog.advert.service;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdParam;

/** 
* @ClassName: IAdParamService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午2:10:06 
*  
*/
public interface IAdParamService {
	public List<AdParam> getAllAdParam() throws Exception;

	AdParam getAdParamById(Long id) throws Exception;
}
