package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpCategoryDao;
import com.winterframework.firefrog.help.dao.vo.HelpCategoryVO;
import com.winterframework.firefrog.help.dao.vo.VOConverter;
import com.winterframework.firefrog.help.entity.HelpCategory;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpCategoryDaoImpl")
public class HelpCategoryDaoImpl extends BaseIbatis3Dao<HelpCategoryVO> implements IHelpCategoryDao {

	@Override
	public List<HelpCategory> queryHelpCategorys(HelpCategory helpCategory) throws Exception {
		HelpCategoryVO queryVo = VOConverter.HelpCategory2VO(helpCategory);
		List<HelpCategoryVO> helpCategoryVOs = this.sqlSessionTemplate.selectList("getHelpCategorys", queryVo);
		List<HelpCategory> helpCategorys = new ArrayList<HelpCategory>();
		for (HelpCategoryVO vo : helpCategoryVOs) {
			helpCategorys.add(VOConverter.vO2HelpCategory(vo));
		}
		return helpCategorys;
	}

	@Override
	public HelpCategory createHelpCategory(HelpCategory helpCategory) throws Exception {
		HelpCategoryVO addVo = VOConverter.HelpCategory2VO(helpCategory);
		addVo.setGmtCreated(new Date());
		this.insert(addVo);
		return VOConverter.vO2HelpCategory(addVo);
	}

	@Override
	public int update(HelpCategory helpCategory) throws Exception {
		HelpCategoryVO updateVo = VOConverter.HelpCategory2VO(helpCategory);
		return this.update(updateVo);
	}

	@Override
	public int deleteHelpCategory(Long id) throws Exception {
		return this.delete(id);
	}

	@Override
	public Long getNo(HelpCategory helpCategory) throws Exception {
		HelpCategoryVO queryVo = VOConverter.HelpCategory2VO(helpCategory);
		return this.sqlSessionTemplate.selectOne("getNo", queryVo);
	}
}
