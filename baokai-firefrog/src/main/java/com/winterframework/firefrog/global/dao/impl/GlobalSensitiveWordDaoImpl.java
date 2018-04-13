package com.winterframework.firefrog.global.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.global.dao.GlobalSensitiveWordDao;
import com.winterframework.firefrog.global.dao.vo.GlobalSensitiveWordVO;
import com.winterframework.firefrog.global.dao.vo.TypeMap;
import com.winterframework.firefrog.global.dao.vo.VOConverter;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("globalSensitiveWordDaoImpl")
public class GlobalSensitiveWordDaoImpl extends BaseIbatis3Dao<GlobalSensitiveWordVO> implements GlobalSensitiveWordDao {

	@Override
	public void saveWord(List<SensitiveWord> words) throws Exception {
		List<GlobalSensitiveWordVO> voList = new ArrayList<GlobalSensitiveWordVO>();
		GlobalSensitiveWordVO wordVO = null;
		for (SensitiveWord word : words) {
			wordVO = new GlobalSensitiveWordVO();
			wordVO.setWord(word.getWord());
			wordVO.setType((long) word.getType().ordinal());
			voList.add(wordVO);
		}
		this.insert(voList);
	}

	@Override
	public void reNameWord(Long id, String name) throws Exception {
		GlobalSensitiveWordVO wordVO = new GlobalSensitiveWordVO();
		wordVO.setWord(name);
		wordVO.setId(id);
		List<GlobalSensitiveWordVO> list = sqlSessionTemplate.selectList("reNameCheckRepeat", wordVO);
		if (list != null && list.size() > 0) {
			throw new Exception("SensitiveWordRepeat");
		}
		this.update(wordVO);
	}

	@Override
	public void deleteWord(Long id) throws Exception {
		this.delete(id);
	}

	@Override
	public Page<SensitiveWord> selectWord(PageRequest<GlobalSensitiveWordQueryRequest> pageRequest) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("word", pageRequest.getSearchDo().getWord());
		map.put("type", pageRequest.getSearchDo().getType());
		int totalCount = ((Long) sqlSessionTemplate.selectOne("getWordCountByPage", map)).intValue();

		Page<SensitiveWord> page = new Page<SensitiveWord>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				totalCount);

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GlobalSensitiveWordVO> list = sqlSessionTemplate.selectList("getWordByPage", filters, rowBounds);

		List<SensitiveWord> wordList = new ArrayList<SensitiveWord>();
		for (GlobalSensitiveWordVO vo : list) {
			wordList.add(VOConverter.transWordVO2Word(vo));
		}
		page.setResult(wordList);
		return page;
	}

	@Override
	public Map<Long, Long> queryByType() throws Exception {
		List<TypeMap> typeMap = sqlSessionTemplate.selectList("queryByType");
		Map<Long, Long> map = new HashMap<Long, Long>();
		for (TypeMap type : typeMap) {
			map.put(type.getType(), type.getCount());
		}
		return map;
	}

	@Override
	public List<String> selectWord(List<String> wordList,Long type) throws Exception {
		Map<String ,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("keyWords", wordList);
		searchMap.put("type", type);
		List<GlobalSensitiveWordVO> list = sqlSessionTemplate.selectList("queryForRepeat", searchMap);
		List<String> strList = new ArrayList<String>();
		for (GlobalSensitiveWordVO vo : list) {
			strList.add(vo.getWord());
		}
		return strList;
	}

	@Override
	public List<SensitiveWord> quaryListByType(Long type) {
		List<GlobalSensitiveWordVO> list = sqlSessionTemplate.selectList("quaryListByType", type);
		List<SensitiveWord> wordList = new ArrayList<SensitiveWord>();
		for (GlobalSensitiveWordVO vo : list) {
			wordList.add(VOConverter.transWordVO2Word(vo));
		}
		return wordList;
	}
}
