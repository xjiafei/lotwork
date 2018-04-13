package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdTopicDao;
import com.winterframework.firefrog.advert.dao.vo.AdTopicVO;
import com.winterframework.firefrog.advert.dao.vo.VOConverter;
import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.web.dto.AdTopicSearch;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("adTopicDaoImpl")
public class AdTopicDaoImpl extends BaseIbatis3Dao<AdTopicVO> implements IAdTopicDao {

	@Override
	public void createTopic(AdTopic topic) {
		AdTopicVO vo = VOConverter.transAdTopic2VO(topic);
		this.insert(vo);
	}

	@Override
	public Page<AdTopic> queryTopic(PageRequest<AdTopicSearch> pageRequest) {
		Page<AdTopicVO> volist = this.pageQuery(pageRequest, null, null);
		Page<AdTopic> page = new Page<AdTopic>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				volist.getTotalCount());
		List<AdTopic> list = new ArrayList<AdTopic>();
		AdTopic topic = null;
		for (AdTopicVO vo : volist.getResult()) {
			topic = VOConverter.transVO2AdTopic(vo);
			list.add(topic);
		}
		page.setResult(list);
		return page;
	}

	@Override
	public AdTopic queryTopicDetail(Long id) {
		return VOConverter.transVO2AdTopic(getById(id));
	}

	@Override
	public void modifyTopic(AdTopic topic) {
		update(VOConverter.transAdTopic2VO(topic));
	}
}
