/**   
* @Title: AdServiceImpl.java 
* @Package com.winterframework.firefrog.advert.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 上午10:26:37 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao;
import com.winterframework.firefrog.advert.dao.IAdvertDao;
import com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO;
import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.service.IAdvertService;
import com.winterframework.firefrog.advert.web.dto.AdvertSearchDto;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: AdServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 上午10:26:37 
*  
*/
@Service("adServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdvertServiceImpl implements IAdvertService {

	@Resource(name = "adDaoImpl")
	private IAdvertDao adDao;

	@Resource(name = "adspaceRelationDaoImpl")
	private IAdSpaceRelationDao adspaceRelationDao;
    @Resource(name = "globalSensitiveWordServiceImpl")
    private GlobalSensitiveWordService globalSensitiveWordService;
	/**
	* Title: queryAdByPage
	* Description:
	* @param pageRequest
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#queryAdByPage(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public CountPage<Advert> queryAdByPage(PageRequest<AdvertSearchDto> pageRequest) throws Exception {
		pageRequest.getSearchDo().setCurrentDate(new Date());
		return adDao.queryAdByPage(pageRequest);
	}

	/**
	* Title: save
	* Description:
	* @param ad
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#save(com.winterframework.firefrog.advert.entity.Advert) 
	*/
	@Override
	public Advert save(Advert ad) throws Exception {
		List<AdSpace> adSpaces = ad.getAdspaces();
        ad.setName(globalSensitiveWordService.replace(ad.getName(), SensitiveWord.Type.adv));
		Advert dbAd = adDao.save(ad);
		if (adSpaces != null) {
			for (AdSpace adSpace : adSpaces) {
				AdspaceRelationVO vo = new AdspaceRelationVO();
				vo.setAdId(dbAd.getId());
				vo.setSpaceId(adSpace.getId());
				vo.setIsShown(1l);
				vo.setOrders(0l);
				adspaceRelationDao.save(vo);
			}
		}
		return dbAd;
	}

	/**
	* Title: update
	* Description:
	* @param ad
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#update(com.winterframework.firefrog.advert.entity.Advert) 
	*/
	@Override
	public void update(Advert ad) throws Exception {
		List<AdSpace> adSpaces = ad.getAdspaces();
        ad.setName(globalSensitiveWordService.replace(ad.getName(), SensitiveWord.Type.adv));
		adDao.update(ad);
		adspaceRelationDao.deleteByAd(ad.getId());
		if (adSpaces != null) {
			for (AdSpace adSpace : adSpaces) {
				AdspaceRelationVO vo = new AdspaceRelationVO();
				vo.setAdId(ad.getId());
				vo.setSpaceId(adSpace.getId());
				vo.setIsShown(1l);
				vo.setOrders(0l);
				adspaceRelationDao.save(vo);
			}
		}
	}

	/**
	* Title: queryAdBySpace
	* Description:
	* @param spaceId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#queryAdBySpace(java.lang.Long) 
	*/
	@Override
	public List<Advert> queryAdBySpace(Long spaceId) throws Exception {
		return adDao.queryAdBySpace(spaceId);
	}

	/**
	* Title: delete
	* Description:
	* @param ids
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#delete(java.util.List) 
	*/
	@Override
	public void delete(List<Long> ids) throws Exception {
		for (Long id : ids) {
			Advert ad = new Advert();
			ad.setId(id);
			ad.setStatuss(Advert.Status.getEnum(5));
			adDao.update(ad);
		}
	}

	/**
	* Title: review
	* Description:
	* @param ids
	* @param status
	* @param meno 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#review(java.util.List, java.lang.Long, java.lang.String) 
	*/
	@Override
	public void review(List<Long> ids, Long status, String meno, String approver) throws Exception {
		for (Long id : ids) {
			Advert ad = new Advert();
			ad.setId(id);
			ad.setApprover(approver);
			ad.setStatuss(Advert.Status.getEnum(status.intValue()));
			ad.setMemo(meno);
			ad.setGmtAppr(new Date());
			adDao.update(ad);
		}
	}

	/**
	* Title: getAdById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdvertService#getAdById(java.lang.Long) 
	*/
	@Override
	public Advert getAdById(Long id) throws Exception {
		return adDao.queryAdById(id);
	}

}
