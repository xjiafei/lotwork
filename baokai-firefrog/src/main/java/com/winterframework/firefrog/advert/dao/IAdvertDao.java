/**   
* @Title: AdDao.java 
* @Package com.winterframework.firefrog.advert.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-4 下午3:35:07 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.web.dto.AdvertSearchDto;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: AdDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午3:35:07 
*  
*/
public interface IAdvertDao {
	public CountPage<Advert> queryAdByPage(PageRequest<AdvertSearchDto> pageRequest) throws Exception;

	public Advert save(Advert ad) throws Exception;

	public void update(Advert ad) throws Exception;

	public List<Advert> queryAdBySpace(Long spaceId) throws Exception;

	public Advert queryAdById(Long id) throws Exception;
}
