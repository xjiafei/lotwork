package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdPageDao;
import com.winterframework.firefrog.advert.dao.vo.AdPageVO;
import com.winterframework.firefrog.advert.dao.vo.AdSpaceVOConvert;
import com.winterframework.firefrog.advert.entity.AdPage;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
 * 广告页的dao实现
* @ClassName: AdPageDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午2:41:52 
*  
*/
@Repository("adPageDaoImpl")
public class AdPageDaoImpl extends BaseIbatis3Dao<AdPageVO> implements IAdPageDao {
	@Override
	public List<AdPage> getAdAllPage() throws Exception {
		List<AdPageVO> adPageVOs = this.getAll();
		List<AdPage> adPageList = new ArrayList<AdPage>();
		for (AdPageVO vo : adPageVOs) {
			adPageList.add(AdSpaceVOConvert.convertAdPage(vo));
		}
		return adPageList;
	}
}
