package com.winterframework.firefrog.advert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.advert.dao.IAdParamDao;
import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.firefrog.advert.service.IAdParamService;

@Service("adParamServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdParamServiceImpl implements IAdParamService {

	@Resource(name = "adParamDaoImpl")
	private IAdParamDao adParamDaoImpl;

	@Override
	public List<AdParam> getAllAdParam() throws Exception {
		return adParamDaoImpl.getAllAdParam();
	}
	@Override
	public AdParam getAdParamById(Long id) throws Exception {
		return adParamDaoImpl.getAdParamById(id);
	}
}
