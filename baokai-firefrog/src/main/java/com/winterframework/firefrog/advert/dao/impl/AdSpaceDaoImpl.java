package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdSpaceDao;
import com.winterframework.firefrog.advert.dao.vo.AdSpaceVO;
import com.winterframework.firefrog.advert.dao.vo.AdSpaceVOConvert;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
 * 广告位的dao实现
* @ClassName: AdSpaceDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:43:01 
*  
*/
@Repository("adSpaceDaoImpl")
public class AdSpaceDaoImpl extends BaseIbatis3Dao<AdSpaceVO> implements IAdSpaceDao {

	@Override
	public List<AdSpace> getAllAdSpace() throws Exception {
		List<AdSpaceVO> pageVO = this.getAll();
		List<AdSpace> adParamList = new ArrayList<AdSpace>();
		for (AdSpaceVO vo : pageVO) {
			adParamList.add(AdSpaceVOConvert.convertAdSpace(vo));
		}
		return adParamList;
	}

	@Override
	public void updateAdSpace(AdSpace adSpace) throws Exception {
		AdSpaceVO vo = AdSpaceVOConvert.convertAdSpaceTOVo(adSpace);
		this.update(vo);
	}

	@Override
	public AdSpace insertAdSpace(AdSpace adSpace) throws Exception {
		AdSpaceVO vo = AdSpaceVOConvert.convertAdSpaceTOVo(adSpace);
		this.insert(vo);
		return adSpace;
	}

	@Override
	public AdSpace getAdSpaceById(Long id) {
		AdSpaceVO vo = this.getById(id);
		return AdSpaceVOConvert.convertAdSpace(vo);
	}

	@Override
	public AdSpace getAdSpaceByName(String name)
	{
		AdSpaceVO vo = this.sqlSessionTemplate.selectOne("name",name);
		return AdSpaceVOConvert.convertAdSpace(vo);
	}
	
	
	@Override
	public List<AdSpace> getAdSpacesByAdId(Long adId) throws Exception {
		List<AdSpaceVO> relationVOs = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.advert.dao.vo.AdSpaceVO.getAdSpacesByAd", adId);
		List<AdSpace> adParamList = new ArrayList<AdSpace>();
		for (AdSpaceVO vo : relationVOs) {
			adParamList.add(AdSpaceVOConvert.convertAdSpace(vo));
		}
		return adParamList;
	}

	/**
	* Title: getAdSpacesByName
	* Description:
	* @param name
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceDao#getAdSpacesByName(java.lang.String) 
	*/
	@Override
	public List<AdSpace> getAdSpacesByNames(List<String> list) throws Exception {
		List<AdSpaceVO> vos = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.advert.dao.vo.AdSpaceVO.getByNames", list);
		List<AdSpace> result = new ArrayList<AdSpace>();
		for (AdSpaceVO vo : vos) {
			result.add(AdSpaceVOConvert.convertAdSpace(vo));
		}
		return result;
	}
}
