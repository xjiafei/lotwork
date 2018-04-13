package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.entity.AdTopicCate;

public interface IAdTopicCategoryDao {

	public Long createCate(String name);
	
	public List<AdTopicCate> queryAllCategory();

}
