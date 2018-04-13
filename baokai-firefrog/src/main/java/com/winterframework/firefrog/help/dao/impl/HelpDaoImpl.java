package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpDao;
import com.winterframework.firefrog.help.dao.vo.HelpVO;
import com.winterframework.firefrog.help.dao.vo.VOConverter;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpDaoImpl")
public class HelpDaoImpl extends BaseIbatis3Dao<HelpVO> implements IHelpDao {

	@Override
	public Long saveHelp(Help help) throws Exception {
		HelpVO vo = VOConverter.transHelp2VO(help);
		this.insert(vo);
		return vo.getId();
	}

	@Override
	public void updateHelp(Help help) throws Exception {
		HelpVO vo = VOConverter.transHelp2VO(help);
		if(vo.getIsRec() == 1){
			Help tempHelp = selectById(help.getId());
			if(!tempHelp.getIsRecommend()){
				vo.setRecommandTime(new Date());
			}
		}
		this.update(vo);
	}

	@Override
	public void deleteHelp(Long id) throws Exception {
		this.delete(id);
	}

	@Override
	public Help selectById(Long id) throws Exception {
		HelpVO vo = this.getById(id);
		Help help = VOConverter.transVO2Help(vo);
		return help;
	}

	@Override
	public Page<Help> selectHelp(PageRequest<HelpQueryRequest> pageRequest) throws Exception {
		HelpQueryRequest search = pageRequest.getSearchDo();
		Map<String, Object> map = new HashMap<String, Object>();
		String sqlStr = null;
		String countStr = null;
		map.put("like_match", search.getLike_match());
		if (search.getKeywords() != null) {
			map.put("title", search.getKeywords());
			map.put("preface", search.getKeywords());
			sqlStr = "getHelpWithKeyByPage";
			countStr = "getHelpWithKeyCountByPage";
		} else {
			map.put("title", search.getTitle());
			sqlStr = "getHelpByPage";
			countStr = "getHelpCountByPage";
		}
		map.put("type", search.getType());
		map.put("cateId", search.getCateId());
		map.put("cateId2", search.getCateId2());
		map.put("isRec", search.getIsRec());

		int totalCount = ((Long) sqlSessionTemplate.selectOne(countStr, map)).intValue();

		Page<Help> page = new Page<Help>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount);

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<HelpVO> list = sqlSessionTemplate.selectList(sqlStr, filters, rowBounds);

		List<Help> helpList = new ArrayList<Help>();
		for (HelpVO vo : list) {
			helpList.add(VOConverter.transVO2Help(vo));
		}
		page.setResult(helpList);
		return page;
	}

	@Override
	public void updateFeedBackCount(Help help) throws Exception {
		HelpVO vo = VOConverter.transHelp2VO(help);
		/*if (vo.getUnsolvednum() != null && vo.getUnsolvednum() <= 0) {
			vo.setUnsolvednum(null);
		}
		if (vo.getSolvednum() != null && vo.getSolvednum() <= 0) {
			vo.setSolvednum(null);
		}*/
		sqlSessionTemplate.update("com.winterframework.firefrog.help.dao.vo.HelpVO.updateFeedBackCount", vo);
	}

	@Override
	public void updateBrowsenum(Help help) throws Exception {
		HelpVO vo = VOConverter.transHelp2VO(help);
		sqlSessionTemplate.update("com.winterframework.firefrog.help.dao.vo.HelpVO.updateBrowsenum", vo);
	}
}
