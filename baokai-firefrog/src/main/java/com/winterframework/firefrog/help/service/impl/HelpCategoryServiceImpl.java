package com.winterframework.firefrog.help.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.help.dao.IHelpCategoryDao;
import com.winterframework.firefrog.help.entity.HelpCategory;
import com.winterframework.firefrog.help.service.IHelpCategoryService;

@Service("helpCategoryServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class HelpCategoryServiceImpl implements IHelpCategoryService {

	@Resource(name = "helpCategoryDaoImpl")
	private IHelpCategoryDao helpCategoryDaoImpl;

	@Override
	public List<HelpCategory> queryHelpCategorys(HelpCategory helpCategory) throws Exception {
		List<HelpCategory> queryList = helpCategoryDaoImpl.queryHelpCategorys(helpCategory);
		List<HelpCategory> resultList = new ArrayList<HelpCategory>();
		for (HelpCategory helpCate : queryList) {
			if (helpCate.getParent() == null) {
				resultList.add(helpCate);
			}
		}
		for (HelpCategory helpCate : resultList) {
			List<HelpCategory> subList = new ArrayList<HelpCategory>();
			for (HelpCategory subCate : queryList) {
				if (subCate.getParent() != null
						&& helpCate.getId().longValue() == subCate.getParent().getId().longValue()) {
					subList.add(subCate);
				}
			}
			helpCate.setSubCategorys(subList);
		}

		return resultList;
	}

	@Override
	public HelpCategory createHelpCategory(HelpCategory helpCategory) throws Exception {
		helpCategory.setCno(helpCategoryDaoImpl.getNo(helpCategory));
		return helpCategoryDaoImpl.createHelpCategory(helpCategory);
	}

	@Override
	public int update(HelpCategory helpCategory) throws Exception {
		return helpCategoryDaoImpl.update(helpCategory);
	}

	@Override
	public int deleteHelpCategory(Long id) throws Exception {
		return helpCategoryDaoImpl.deleteHelpCategory(id);
	}

}
