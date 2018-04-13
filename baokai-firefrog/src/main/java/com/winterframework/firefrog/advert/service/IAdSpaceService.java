package com.winterframework.firefrog.advert.service;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdSpace;

/** 
* @ClassName: IAdSpaceService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午2:09:59 
*  
*/
public interface IAdSpaceService {
	/** 
	* @Title: getAllAdSpace 
	* @Description: 获取所有的广告位
	* @return
	* @throws Exception
	*/
	public List<AdSpace> getAllAdSpace() throws Exception;

	/** 
	* @Title: updateAdSpace 
	* @Description: 修改广告位 
	* @param adSpace
	* @throws Exception
	*/
	public void updateAdSpace(AdSpace adSpace) throws Exception;

	/** 
	* @Title: insertAdSpace 
	* @Description: 插入广告位 
	* @param aclGroup
	* @return
	* @throws Exception
	*/
	public AdSpace insertAdSpace(AdSpace aclGroup) throws Exception;

	/** 
	* @Title: getAdSpaceById 
	* @Description: 根据id获取广告位
	* @param id
	* @return
	* @throws Exception
	*/
	public AdSpace getAdSpaceById(Long id) throws Exception;

	/** 
	* @Title: getAdSpaceById 
	* @Description: 根据name获取广告位
	* @param name
	* @return
	* @throws Exception
	*/
	public AdSpace getAdSpaceByName(String name) throws Exception;
	
	/** 
	 * 
	* @Title: getAdsByAdSpaceId 
	* @Description:根据广告位获取所有广告的
	* @param adSpaceId
	* @return
	* @throws Exception
	*/
	public AdSpace getAdsByAdSpaceId(Long adSpaceId) throws Exception;

	/** 
	 * 
	* @Title: getAdsByAdSpaceId 
	* @Description:根据广告位获取所有广告的
	* @param adSpaceId
	* @return
	* @throws Exception
	*/
	public List<AdSpace> getAdSpacesByAdId(Long adId) throws Exception;

	/** 
	* @Title: updateUnbingAdvert 
	* @Description: 更新广告与广告位的绑定关系
	* @param relationList
	*/
	void updateUnbingAdvert(AdSpace adSpace);

	/** 
	* @Title: getAdSpacesByNames 
	* @Description: 根据名称查询adspaces
	* @param relationList
	*/
	public List<AdSpace> getAdSpacesByNames(List<String> names) throws Exception;

	public AdSpace getEffectAdsByAdSpaceId(Long adSpaceId, Long userId) throws Exception;
}