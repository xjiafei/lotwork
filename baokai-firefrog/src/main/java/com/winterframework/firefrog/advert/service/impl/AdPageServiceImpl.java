package com.winterframework.firefrog.advert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.advert.dao.IAdPageDao;
import com.winterframework.firefrog.advert.entity.AdPage;
import com.winterframework.firefrog.advert.service.IAdPageService;

/** 
* @ClassName: AdPageServiceImpl 
* @Description: 页面选择的业务层代码
* @author 你的名字 
* @date 2013-11-4 下午3:51:23 
*  
*/
@Service("adPageServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdPageServiceImpl implements IAdPageService {

	@Resource(name = "adPageDaoImpl")
	private IAdPageDao adPageDaoImpl;

	@Override
	public List<AdPage> getAllAdPage() throws Exception {
		List<AdPage> adPages = adPageDaoImpl.getAdAllPage();
		return adPages;
	}
}
