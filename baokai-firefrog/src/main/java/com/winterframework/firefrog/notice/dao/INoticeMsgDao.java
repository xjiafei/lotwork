package com.winterframework.firefrog.notice.dao;

import java.util.List;

import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface INoticeMsgDao {

	public Page<NoticeMsg> selectMsg(PageRequest<NoticeMsgSearchDto> search) throws Exception;

	public void modifyMsgStatus(Long id, Long status) throws Exception;
	
	public void saveMsg(NoticeMsg msg) throws Exception;
	
	public NoticeMsg selectMsgDetail(Long id) throws Exception;
	
	public List<NoticeMsg> selectScheduleMsg() throws Exception;
}
