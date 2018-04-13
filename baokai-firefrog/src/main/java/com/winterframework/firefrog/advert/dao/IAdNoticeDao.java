package com.winterframework.firefrog.advert.dao;

import java.util.List;

import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.modules.page.PageRequest;

public interface IAdNoticeDao {

	public void createAdNotice(AdNotice notice) throws Exception;

	public void modifyAdNotice(AdNotice notice) throws Exception;

	public void deleteAdNotice(Long id) throws Exception;

	public CountPage<AdNotice> queryAdNoticeList(PageRequest<AdNoticeSearchDto> pageRequest) throws Exception;

	public AdNotice queryAdNoticeDetail(Long id) throws Exception;

	public List<AdNotice> queryEffectAdNoticeByPage(List<String> pages, Long userId) throws Exception;

	public List<AdNotice> queryGeneralEffectAdNotice(Long userId) throws Exception;

}
