package com.winterframework.firefrog.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.help.dao.IHelpHotkeywordsDao;
import com.winterframework.firefrog.help.entity.HelpHotkeywords;
import com.winterframework.firefrog.help.service.IHelpHotkeywordsService;

@Service("helpHotkeywordsServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class HelpHotkeywordsServiceImpl implements IHelpHotkeywordsService {

	@Resource(name = "helpHotkeywordsDaoImpl")
	private IHelpHotkeywordsDao helpHotkeywordsDao;

	@Override
	public List<HelpHotkeywords> getAllHelpHotkeywords() throws Exception {
		return helpHotkeywordsDao.getAllHelpHotkeywords();
	}

	@Override
	public int updateHotkeyword(HelpHotkeywords helpHotkeyword) throws Exception {
		return helpHotkeywordsDao.updateHotkeyword(helpHotkeyword);
	}

}
