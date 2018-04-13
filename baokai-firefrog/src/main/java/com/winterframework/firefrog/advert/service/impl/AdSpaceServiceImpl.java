package com.winterframework.firefrog.advert.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.advert.dao.IAdParamDao;
import com.winterframework.firefrog.advert.dao.IAdSpaceDao;
import com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao;
import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.advert.service.IAdSpaceService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;

@Service("adSpaceServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdSpaceServiceImpl implements IAdSpaceService {

	@Resource(name = "adSpaceDaoImpl")
	private IAdSpaceDao adSpaceDaoImpl;

	@Resource(name = "adspaceRelationDaoImpl")
	private IAdSpaceRelationDao adSpaceRelationDaoImpl;

	@Resource(name = "adParamDaoImpl")
	private IAdParamDao adParamDaoImpl;

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;

	@Override
	public List<AdSpace> getAllAdSpace() throws Exception {
		List<AdSpace> adSpaceList = adSpaceDaoImpl.getAllAdSpace();
		return adSpaceList;
	}

	@Override
	public void updateAdSpace(AdSpace adSpace) throws Exception {
		adSpaceDaoImpl.updateAdSpace(adSpace);
	}

	@Override
	public AdSpace insertAdSpace(AdSpace adSpace) throws Exception {
		return adSpaceDaoImpl.insertAdSpace(adSpace);
	}

	@Override
	public AdSpace getAdSpaceById(Long id) throws Exception {
		AdSpace adSpace = adSpaceDaoImpl.getAdSpaceById(id);
		//获取参数中的信息
		if (adSpace.getAdParam() != null && adSpace.getAdParam().getId() != null) {
			AdParam adParam = adParamDaoImpl.getAdParamById(adSpace.getAdParam().getId());
			adSpace.setAdParam(adParam);
		}
		return adSpace;
	}


	public AdSpace getAdSpaceByName(String name) throws Exception
	{
		AdSpace adSpace = adSpaceDaoImpl.getAdSpaceByName(name);
		//获取参数中的信息
		if (adSpace.getAdParam() != null && adSpace.getAdParam().getId() != null) {
			AdParam adParam = adParamDaoImpl.getAdParamById(adSpace.getAdParam().getId());
			adSpace.setAdParam(adParam);
		}
		return adSpace;
		
	}
	
	public AdSpace getAdsByAdSpaceId(Long adSpaceId) throws Exception {
		return adSpaceRelationDaoImpl.getAdsByAdSpaceId(adSpaceId);
	}

	@Override
	public List<AdSpace> getAdSpacesByAdId(Long adId) throws Exception {
		return adSpaceDaoImpl.getAdSpacesByAdId(adId);
	}

	@Override
	public void updateUnbingAdvert(AdSpace adSpace) {
		adSpaceRelationDaoImpl.updateUnbingAdvert(adSpace);
	}

	/**
	* Title: getAdSpacesByNames
	* Description:
	* @param names
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdSpaceService#getAdSpacesByNames(java.util.List) 
	*/
	@Override
	public List<AdSpace> getAdSpacesByNames(List<String> names) throws Exception {
		return adSpaceDaoImpl.getAdSpacesByNames(names);
	}

	/**
	* Title: getEffectAdsByAdSpaceId
	* Description:
	* @param adSpaceId
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdSpaceService#getEffectAdsByAdSpaceId(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public AdSpace getEffectAdsByAdSpaceId(Long adSpaceId, Long userId) throws Exception {
		AdSpace adSpace = adSpaceRelationDaoImpl.getEffectAdsByAdSpaceId(adSpaceId);
		List<Advert> adverts = adSpace.getAdvertList();
		List<Advert> result = new ArrayList<Advert>();
		for (Advert advert : adverts) {
			if (isContainsUserLevel(userId, advert)) {
				result.add(advert);
			}
		}
		adSpace.setAdvertList(result);
		return adSpace;
	}

	private boolean isContainsUserLevel(Long userId, Advert advert) throws Exception {

		if (userId == null || userId == 0l) {
			if (advert.getRcGuest() != null && advert.getRcGuest().intValue() == 1) {
				return true;
			}
			return false;
		} else {

			User user = userCustomerDao.queryUserById(userId);
			if (user == null) {
				if (advert.getRcGuest() != null && advert.getRcGuest().intValue() == 1) {
					return true;
				}
				return false;
			}

			//在所有的用户级别显示
			if (advert.getRcAll() != null && advert.getRcAll().intValue() == 1) {
				if (userId != null && userId != 0l) {
					return true;
				}
			} else {
				//选择vip显示
				if (advert.getRcVip() != null && advert.getRcVip().intValue() == 1) {
					if (user.getVipLvl() != null && user.getVipLvl() > 0) {//用户属于vip
						return true;
					}
				}
				//选择非vip显示
				if (advert.getRcNonVip() != null && advert.getRcNonVip().intValue() == 1) {
					if (user.getVipLvl() != null && user.getVipLvl() == 0) {//用户属于非vip
						return true;
					}
				}
				//选择总代显示
				if (advert.getRcTopAgent() != null && advert.getRcTopAgent().intValue() == 1) {
					if (user.getUserLevel() != null && user.getUserLevel().intValue() == 0) {//用户属于总代
						return true;
					}
				}
				//选择其他代理显示
				if (advert.getRcOtAgent() != null && advert.getRcOtAgent().intValue() == 1) {
					if (user.getUserLevel() != null && user.getUserLevel().intValue() > 0) {//用户属于其他代理
						return true;
					}
				}
				//选择玩家显示
				if (advert.getRcCustomer() != null && advert.getRcCustomer().intValue() == 1) {
					if (user.getUserLevel() != null && user.getUserLevel().intValue() == -1) {//用户属于玩家
						return true;
					}
				}
			}
			return false;
		}
	}

}
