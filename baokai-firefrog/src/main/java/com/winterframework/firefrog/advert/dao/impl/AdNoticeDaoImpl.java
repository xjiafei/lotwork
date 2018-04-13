package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdNoticeDao;
import com.winterframework.firefrog.advert.dao.vo.AdNoticeVO;
import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.dao.vo.VOConverter;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.common.util.GZipUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("adNoticeDaoImpl")
public class AdNoticeDaoImpl extends BaseIbatis3Dao<AdNoticeVO> implements IAdNoticeDao {

	@Override
	public void createAdNotice(AdNotice notice) throws Exception {
		AdNoticeVO vo = VOConverter.transAdNotice2VO(notice);
		vo.setContent(GZipUtils.compressAndEncodeBase64String(vo.getContent()));
		vo.setGmtCreated(new Date());
		vo.setGmtModified(new Date());
		this.insert(vo);
	}

	@Override
	public void modifyAdNotice(AdNotice notice) throws Exception {
		AdNoticeVO vo = VOConverter.transAdNotice2VO(notice);
		vo.setContent(GZipUtils.compressAndEncodeBase64String(vo.getContent()));
		vo.setGmtModified(new Date());
		if (notice.getTitle() != null) {
			vo.setGmtCreated(new Date());
		}
		this.update(vo);
	}

	@Override
	public void deleteAdNotice(Long id) throws Exception {
		this.delete(id);
	}

	@Override
	public CountPage<AdNotice> queryAdNoticeList(PageRequest<AdNoticeSearchDto> pageRequest) throws Exception {
		Page<AdNoticeVO> volist = this.pageQuery(pageRequest, null, null);
		CountPage<AdNotice> page = new CountPage<AdNotice>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				volist.getTotalCount());
		List<AdNotice> list = new ArrayList<AdNotice>();
		for (AdNoticeVO vo : volist.getResult()) {
			vo.setContent(GZipUtils.decodeBase64AndDecompress(vo.getContent()));
			list.add(VOConverter.transVO2AdNotice(vo));
		}
		page.setResult(list);
		AdNoticeVO vo = new AdNoticeVO();
		AdNoticeSearchDto search = pageRequest.getSearchDo();
		if (search.getFrontCall() == null) {
			vo.setTitle(search.getTitle());
			vo.setType(search.getType());
			vo.setRcTopAgent(search.getRcTopAgent());
			vo.setRcOtAgent(search.getRcOtAgent());
			vo.setRcVip(search.getRcVip());
			vo.setRcNonVip(search.getRcNonVip());
			vo.setStatus(1L);

			if (vo.getRcCustomer() == null && vo.getRcNonVip() == null && vo.getRcOtAgent() == null
					&& vo.getRcTopAgent() == null && vo.getRcVip() == null) {
				vo.setRcAll(1L);
			}

			vo.setPeriodStatus(search.getPeriodStatus());
			page.setSumWait(this.getCountByEntity(vo));
			vo.setStatus(2L);
			page.setSumReviewing(this.getCountByEntity(vo));
			vo.setStatus(4L);
			page.setSumNotPass(this.getCountByEntity(vo));
		}
		return page;
	}

	@Override
	public AdNotice queryAdNoticeDetail(Long id) throws Exception {
		AdNoticeVO vo = this.getById(id);
		vo.setContent(GZipUtils.decodeBase64AndDecompress(vo.getContent()));
		return VOConverter.transVO2AdNotice(vo);
	}

	/**
	* Title: queryEffectAdNoticeByPage
	* Description:
	* @param page
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdNoticeDao#queryEffectAdNoticeByPage(java.lang.String) 
	*/
	@Override
	public List<AdNotice> queryEffectAdNoticeByPage(List<String> pages, Long userId) throws Exception {
		Map<String, Object> paramType = new HashMap<String, Object>();
		paramType.put("userId", userId);
		paramType.put("pagesList", pages);
		List<AdNoticeVO> vos = this.sqlSessionTemplate.selectList("getEffectPage", paramType);
		List<AdNotice> result = new ArrayList<AdNotice>();
		for (AdNoticeVO vo : vos) {
			vo.setContent(GZipUtils.decodeBase64AndDecompress(vo.getContent()));
			result.add(VOConverter.transVO2AdNotice(vo));
		}
		return result;
	}

	/**
	* Title: queryGeneralEffectAdNotice
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdNoticeDao#queryGeneralEffectAdNotice() 
	*/
	@Override
	public List<AdNotice> queryGeneralEffectAdNotice(Long userId) throws Exception {
		Map<String, Object> paramType = new HashMap<String, Object>();
		paramType.put("userId", userId);
		List<AdNoticeVO> vos = this.sqlSessionTemplate.selectList(
				"com.winterframework.firefrog.advert.dao.vo.AdNoticeVO.getGeneralEffectPage", paramType);
		List<AdNotice> result = new ArrayList<AdNotice>();
		for (AdNoticeVO vo : vos) {
			vo.setContent(GZipUtils.decodeBase64AndDecompress(vo.getContent()));
			result.add(VOConverter.transVO2AdNotice(vo));
		}
		return result;
	}
}
