package com.winterframework.firefrog.advert.service;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IAdTopicService {

	public List<AdTopicCate> queryAllCate();
	
	public void createTopic(AdTopic topic);
	
	public Page<AdTopic> queryTopic(PageRequest<AdTopicSearch> pageRequest);
	
	public AdTopic queryTopicDetail(Long id);
	
	public void modifyTopic(AdTopic topic);

}
