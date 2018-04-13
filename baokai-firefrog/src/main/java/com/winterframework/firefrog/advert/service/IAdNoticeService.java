package com.winterframework.firefrog.advert.service;

import java.util.List;

import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.modules.page.PageRequest;

public interface IAdNoticeService {

	public CountPage<AdNotice> queryNoticeList(PageRequest<AdNoticeSearchDto> pageRequest) throws Exception;

	public AdNotice queryNoticeDetail(Long id) throws Exception;

	public void createNotice(AdNotice notice, Long createType) throws Exception;

	public void deleteNotice(List<Long> ids) throws Exception;

	public void modifyNotice(AdNotice notice, Long createType) throws Exception;

	public void auditNotice(List<Long> ids, boolean isPass, String memo, String approver) throws Exception;

	public List<AdNotice> getAdNoticeForUser(Long userId, List<String> page) throws Exception;
}
