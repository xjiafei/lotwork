package com.winterframework.firefrog.advert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.advert.dao.IAdNoticeDao;
import com.winterframework.firefrog.advert.dao.vo.CountPage;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.service.IAdNoticeService;
import com.winterframework.firefrog.advert.web.controller.AdNoticeController;
import com.winterframework.firefrog.advert.web.dto.AdNoticeSearchDto;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.PageRequest;

@Service("adNoticeServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AdNoticeServiceImpl implements IAdNoticeService {

	@Resource(name = "adNoticeDaoImpl")
	private IAdNoticeDao adNoticeDao;

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;

	
	private static final Logger logger = LoggerFactory.getLogger(AdNoticeServiceImpl.class);
	
	@Override
	public CountPage<AdNotice> queryNoticeList(PageRequest<AdNoticeSearchDto> pageRequest) throws Exception {
		return adNoticeDao.queryAdNoticeList(pageRequest);
	}

	@Override
	public void createNotice(AdNotice notice, Long createType) throws Exception {
		notice.setStatus(createType == 2L ? AdNotice.Status.NEW : AdNotice.Status.AUDIT);
		adNoticeDao.createAdNotice(notice);
	}

	@Override
	public void deleteNotice(List<Long> ids) throws Exception {
		for (Long id : ids) {
			AdNotice notice = new AdNotice();
			notice.setId(id);
			notice.setStatus(AdNotice.Status.DELETE);
			adNoticeDao.modifyAdNotice(notice);
		}
	}

	@Override
	public void modifyNotice(AdNotice notice, Long createType) throws Exception {
		notice.setStatus(createType == 2L ? AdNotice.Status.NEW : AdNotice.Status.AUDIT);
		adNoticeDao.modifyAdNotice(notice);
	}

	@Override
	public void auditNotice(List<Long> ids, boolean isPass, String memo, String approver) throws Exception {
		AdNotice notice = null;
		AdNotice.Status status = isPass ? AdNotice.Status.PASS : AdNotice.Status.REFUSE;
		for (Long id : ids) {
			notice = new AdNotice();
			notice.setId(id);
			notice.setMemo(memo);
			notice.setStatus(status);
			AclUser user = new AclUser();
			user.setAccount(approver);
			notice.setApprover(user);
			adNoticeDao.modifyAdNotice(notice);
		}

	}

	@Override
	public AdNotice queryNoticeDetail(Long id) throws Exception {
		return adNoticeDao.queryAdNoticeDetail(id);
	}

	/**
	* Title: getAdNoticeForUser
	* Description:
	* @param userId
	* @param page
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.service.IAdNoticeService#getAdNoticeForUser(java.lang.Long, java.lang.String) 
	*/
	@Override
	public List<AdNotice> getAdNoticeForUser(Long userId, List<String> page) throws Exception {
		if (page != null) {
		
			return adNoticeDao.queryEffectAdNoticeByPage(page, userId);
		} else {
			return adNoticeDao.queryGeneralEffectAdNotice(userId);
		}

	}
}
