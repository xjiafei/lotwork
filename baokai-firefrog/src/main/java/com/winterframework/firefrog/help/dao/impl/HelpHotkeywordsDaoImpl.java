package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpHotkeywordsDao;
import com.winterframework.firefrog.help.dao.vo.HelpHotkeywordsVO;
import com.winterframework.firefrog.help.dao.vo.VOConverter;
import com.winterframework.firefrog.help.entity.HelpHotkeywords;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpHotkeywordsDaoImpl")
public class HelpHotkeywordsDaoImpl extends BaseIbatis3Dao<HelpHotkeywordsVO> implements IHelpHotkeywordsDao {

	@Override
	public List<HelpHotkeywords> getAllHelpHotkeywords() throws Exception {
		List<HelpHotkeywordsVO> voList = getAll();
		List<HelpHotkeywords> helpHotkeywords = new ArrayList<HelpHotkeywords>();
		for (HelpHotkeywordsVO _vo : voList) {
			HelpHotkeywords helpHotkeyword = VOConverter.vO2HelpHotkeywordsEntity(_vo);
			helpHotkeywords.add(helpHotkeyword);
		}
		return helpHotkeywords;
	}

	@Override
	public int updateHotkeyword(HelpHotkeywords helpHotkeyword) throws Exception {
		HelpHotkeywordsVO vo = VOConverter.helpHotkeywordEntity2VO(helpHotkeyword);
		return this.sqlSessionTemplate.update("updateKeyword", vo);
	}

}
