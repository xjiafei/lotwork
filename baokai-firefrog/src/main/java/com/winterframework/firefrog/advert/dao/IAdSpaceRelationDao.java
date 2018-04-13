/**   
* @Title: IAdspaceRelationDao.java 
* @Package com.winterframework.firefrog.advert.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 上午11:42:19 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.dao;

import com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO;
import com.winterframework.firefrog.advert.entity.AdSpace;

/** 
* @ClassName: IAdspaceRelationDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 上午11:42:19 
*  
*/
public interface IAdSpaceRelationDao {

	public void save(AdspaceRelationVO vo) throws Exception;
	
	public void deleteByAd(Long adId) throws Exception;
	
	/** 
	 * 根据广告位id获取对应的所有广告关系
	*/
	public AdSpace getAdsByAdSpaceId(Long adSpaceId) throws Exception;

	/** 
	 * 保持广告与广告位的绑定关系
	*/
	public void updateUnbingAdvert(AdSpace adSpace);
	
	public AdSpace getEffectAdsByAdSpaceId(Long adSpaceId) throws Exception;
}
