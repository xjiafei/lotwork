package com.winterframework.firefrog.advert.dao;

import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface IAdTopicDao {

	public void createTopic(AdTopic topic);
	
	public Page<AdTopic> queryTopic(PageRequest<AdTopicSearch> pageRequest);
	
	public AdTopic queryTopicDetail(Long id);
	
	public void modifyTopic(AdTopic topic);

}
