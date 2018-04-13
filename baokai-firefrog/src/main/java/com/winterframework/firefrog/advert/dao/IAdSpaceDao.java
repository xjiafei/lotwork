package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdSpace;

/** 
 * 广告位的dao
* @ClassName: IAdSpaceDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:41:32 
*  
*/
public interface IAdSpaceDao {

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
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param id
	* @return
	*/
	public AdSpace getAdSpaceById(Long id);
	
	/** 
	* @Title: getAdSpaceById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param id
	* @return
	*/
	public AdSpace getAdSpaceByName(String name);

	public List<AdSpace> getAdSpacesByAdId(Long adId) throws Exception;

	public List<AdSpace> getAdSpacesByNames(List<String> names) throws Exception;

}
