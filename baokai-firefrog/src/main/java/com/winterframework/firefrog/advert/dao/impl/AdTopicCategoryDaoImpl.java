package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdTopicCategoryDao;
import com.winterframework.firefrog.advert.dao.vo.AdTopicCategoryVO;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("adTopicCategoryDaoImpl")
public class AdTopicCategoryDaoImpl extends BaseIbatis3Dao<AdTopicCategoryVO> implements IAdTopicCategoryDao {

	@Override
	public Long createCate(String name) {
		AdTopicCategoryVO vo = new AdTopicCategoryVO();
		vo.setName(name);
		this.insert(vo);
		return vo.getId();
	}

	@Override
	public List<AdTopicCate> queryAllCategory() {
		List<AdTopicCategoryVO> voList = this.getAll();
		List<AdTopicCate> list = new ArrayList<AdTopicCate>();
		AdTopicCate cate = null;
		for (AdTopicCategoryVO vo : voList) {
			cate = new AdTopicCate();
			cate.setId(vo.getId());
			cate.setName(vo.getName());
			list.add(cate);
		}
		return list;
	}
}
