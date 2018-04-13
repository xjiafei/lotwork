package com.winterframework.firefrog.notice.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.notice.dao.INoticeMsgDao;
import com.winterframework.firefrog.notice.dao.vo.NoticeMsgVO;
import com.winterframework.firefrog.notice.dao.vo.VOConverter;
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.web.dto.NoticeMsgSearchDto;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("noticeMsgDao")
public class NoticeMsgDaoImpl extends BaseIbatis3Dao<NoticeMsgVO> implements INoticeMsgDao {

	@Override
	public Page<NoticeMsg> selectMsg(PageRequest<NoticeMsgSearchDto> pageRequest) throws Exception {
		Page<NoticeMsgVO> volist = this.pageQuery(pageRequest, null, null);
		Page<NoticeMsg> page = new Page<NoticeMsg>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				volist.getTotalCount());
		List<NoticeMsg> msgList = new ArrayList<NoticeMsg>();
		for (NoticeMsgVO vo : volist.getResult()) {
			msgList.add(VOConverter.transVO2NoticeMsg(vo));
		}
		page.setResult(msgList);
		return page;
	}

	@Override
	public void modifyMsgStatus(Long id, Long status) throws Exception {
		NoticeMsgVO vo = new NoticeMsgVO();
		vo.setId(id);
		vo.setStatus(status);
		this.update(vo);
	}

	@Override
	public void saveMsg(NoticeMsg msg) throws Exception {
		NoticeMsgVO vo = VOConverter.transNoticeMsg2VO(msg);
		this.insert(vo);
		msg.setId(vo.getId());
	}

	@Override
	public NoticeMsg selectMsgDetail(Long id) throws Exception {
		NoticeMsgVO vo = getById(id);
		return VOConverter.transVO2NoticeMsg(vo);
	}

	/**
	* Title: selectScheduleMsg
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.notice.dao.INoticeMsgDao#selectScheduleMsg() 
	*/
	@Override
	public List<NoticeMsg> selectScheduleMsg() throws Exception {
		Map<String,Date> map = new HashMap<String,Date>();
		map.put("date", new Date());
		List<NoticeMsgVO> vos = this.sqlSessionTemplate.selectList("getBySchedule", map);
		List<NoticeMsg> msgs = new ArrayList<NoticeMsg>();
		for(NoticeMsgVO vo:vos){
			msgs.add(VOConverter.transVO2NoticeMsg(vo));
		}
		return msgs;
	}

}
