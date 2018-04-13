package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdParamDao;
import com.winterframework.firefrog.advert.dao.vo.AdParamVO;
import com.winterframework.firefrog.advert.dao.vo.AdSpaceVOConvert;
import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
 * 广告参数的dao实现
* @ClassName: AdParamDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:42:35 
*  
*/
@Repository("adParamDaoImpl")
public class AdParamDaoImpl extends BaseIbatis3Dao<AdParamVO> implements IAdParamDao {

	@Override
	public List<AdParam> getAllAdParam() throws Exception {
		List<AdParamVO> pageVO = this.sqlSessionTemplate.selectList("com.winterframework.firefrog.advert.dao.vo.AdParamVO.getAll");
		List<AdParam> adParamList = new ArrayList<AdParam>();
		for (AdParamVO vo : pageVO) {
			adParamList.add(AdSpaceVOConvert.convertAdParam(vo));
		}
		return adParamList;
	}

	@Override
	public AdParam getAdParamById(Long id) {
		AdParamVO param = this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.advert.dao.vo.AdParamVO.getById",id);
		return AdSpaceVOConvert.convertAdParam(param);
	}
}
