/**   
* @Title: AdDaoImpl.java 
* @Package com.winterframework.firefrog.advert.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-5 下午4:41:26 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdvertDao;
import com.winterframework.firefrog.advert.dao.vo.AdVO;
import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.dao.vo.VOConverter;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.web.dto.AdvertSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: AdDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-5 下午4:41:26 
*  
*/
@Repository("adDaoImpl")
public class AdvertDaoImpl extends BaseIbatis3Dao<AdVO> implements IAdvertDao {

	/**
	* Title: queryAdByPage
	* Description:
	* @param pageRequest
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdvertDao#queryAdByPage(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public CountPage<Advert> queryAdByPage(PageRequest<AdvertSearchDto> pageRequest) throws Exception {
		Page<AdVO> page = this.pageQuery(pageRequest, null, null);
		CountPage<Advert> countPage = new CountPage<Advert>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				page.getTotalCount());
		List<Advert> list = new ArrayList<Advert>();
		for (AdVO vo : page.getResult()) {
			list.add(VOConverter.adVo2Entity(vo));
		}
		countPage.setResult(list);
		AdvertSearchDto searchVo = pageRequest.getSearchDo();
		searchVo.setStatus(4L);
		searchVo.setAllStatus(null);
		countPage.setSumWait((Long) this.sqlSessionTemplate.selectOne("getCountByAdvertPage", searchVo));
		searchVo.setStatus(1l);
		countPage.setSumReviewing((Long) this.sqlSessionTemplate.selectOne("getCountByAdvertPage", searchVo));
		searchVo.setStatus(3l);
		countPage.setSumNotPass((Long) this.sqlSessionTemplate.selectOne("getCountByAdvertPage", searchVo));
		return countPage;
	}

	/**
	* Title: save
	* Description:
	* @param ad
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdvertDao#save(com.winterframework.firefrog.advert.entity.Advert) 
	*/
	@Override
	public Advert save(Advert ad) throws Exception {
		AdVO vo = VOConverter.adEntity2Vo(ad);
		vo.setGmtCreated(new Date());
		vo.setGmtModified(new Date());
		this.insert(vo);
		return VOConverter.adVo2Entity(vo);
	}

	/**
	* Title: update
	* Description:
	* @param ad
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdvertDao#update(com.winterframework.firefrog.advert.entity.Advert) 
	*/
	@Override
	public void update(Advert ad) throws Exception {
		AdVO vo = VOConverter.adEntity2Vo(ad);
		vo.setGmtModified(new Date());
		if(ad.getName() != null){
			vo.setGmtCreated(new Date());
		}
		this.update(vo);
	}

	/**
	* Title: queryAdBySpace
	* Description:
	* @param spaceId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdvertDao#queryAdBySpace(java.lang.Long) 
	*/
	@Override
	public List<Advert> queryAdBySpace(Long spaceId) throws Exception {
		List<AdVO> vos = this.sqlSessionTemplate.selectList("getBySpace", spaceId);
		List<Advert> ads = new ArrayList<Advert>();
		for (AdVO adVo : vos) {
			ads.add(VOConverter.adVo2Entity(adVo));
		}
		return ads;
	}

	/**
	* Title: queryAdById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdvertDao#queryAdById(java.lang.Long) 
	*/
	@Override
	public Advert queryAdById(Long id) throws Exception {
		AdVO adVo = this.getById(id);
		return VOConverter.adVo2Entity(adVo);
	}
}
