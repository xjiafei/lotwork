package com.winterframework.firefrog.advert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.advert.dao.IAdTopicCategoryDao;
import com.winterframework.firefrog.advert.dao.IAdTopicDao;
import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.firefrog.advert.service.IAdTopicService;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("adTopicServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdTopicServiceImpl implements IAdTopicService {

	@Resource(name = "adTopicCategoryDaoImpl")
	private IAdTopicCategoryDao adTopicCategoryDao;

	@Resource(name = "adTopicDaoImpl")
	private IAdTopicDao adTopicDao;

	@Override
	public void createTopic(AdTopic topic) {
		String cateName = topic.getCate().getName();
		if (cateName != null && !cateName.equals("")) {
			Long cateId = adTopicCategoryDao.createCate(cateName);
			topic.getCate().setId(cateId);
		}
		adTopicDao.createTopic(topic);
	}

	@Override
	public List<AdTopicCate> queryAllCate() {
		return adTopicCategoryDao.queryAllCategory();
	}

	@Override
	public Page<AdTopic> queryTopic(PageRequest<AdTopicSearch> pageRequest) {
		return adTopicDao.queryTopic(pageRequest);
	}

	@Override
	public AdTopic queryTopicDetail(Long id) {
		return adTopicDao.queryTopicDetail(id);
	}

	@Override
	public void modifyTopic(AdTopic topic) {
		String cateName = topic.getCate().getName();
		if (cateName != null && !cateName.equals("")) {
			Long cateId = adTopicCategoryDao.createCate(cateName);
			topic.getCate().setId(cateId);
		}
		adTopicDao.modifyTopic(topic);
	}
}
