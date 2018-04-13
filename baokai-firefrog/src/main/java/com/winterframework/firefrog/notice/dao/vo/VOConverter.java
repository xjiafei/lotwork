package com.winterframework.firefrog.notice.dao.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserGroup;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;

public class VOConverter {

	public static NoticeMsgVO transNoticeMsg2VO(NoticeMsg msg) {
		NoticeMsgVO vo = new NoticeMsgVO();
		vo.setContent(msg.getContent());
		vo.setEffectHours(msg.getEffectHours());
		vo.setGmtExpired(msg.getGmtExpired());
		vo.setGmtSended(msg.getGmtSended());
		vo.setId(msg.getId());
		vo.setOperater(msg.getOperator().getAccount());
		vo.setSendType((long) msg.getSendType().getIndex());
		vo.setStatus((long) msg.getStatus().getIndex());
		vo.setTitle(msg.getTitle());
		List<String> recList = new ArrayList<String>();
		for (User user : msg.getReceivers()) {
			recList.add(user.getUserProfile().getAccount());
		}
		vo.setReceives(DataConverterUtil.convertObject2Json(recList));
		vo.setReceiveGroups(DataConverterUtil.convertObject2Json(msg.getRecGroup()));
		vo.setMessagePush(msg.getMessagePush());
		return vo;
	}

	public static NoticeMsg transVO2NoticeMsg(NoticeMsgVO vo) {
		NoticeMsg msg = new NoticeMsg();
		msg.setContent(vo.getContent());
		msg.setEffectHours(vo.getEffectHours());
		msg.setGmtCreated(vo.getGmtCreated());
		msg.setGmtExpired(vo.getGmtExpired());
		msg.setGmtSended(vo.getGmtSended());
		msg.setId(vo.getId());
		AclUser operator = new AclUser();
		operator.setAccount(vo.getOperater());
		msg.setOperator(operator);
		AclUser deleter = new AclUser();
		deleter.setAccount(vo.getDeleter());
		msg.setDeleter(deleter);
		msg.setSendType(NoticeMsg.SendType.getEnum(vo.getSendType().intValue()));
		msg.setStatus(NoticeMsg.Status.getEnum(vo.getStatus().intValue()));
		msg.setTitle(vo.getTitle());
		List<User> receivers = new ArrayList<User>();
		List<UserGroup> recGroup = new ArrayList<UserGroup>();
		if (vo.getSendType() == 2L) {
			List<String> userList = (List<String>) DataConverterUtil.convertJson2Object(vo.getReceives(),
					ArrayList.class);
			User user = null;
			for (String userStr : userList) {
				user = new User();
				UserProfile profile = new UserProfile();
				profile.setAccount(userStr);
				user.setUserProfile(profile);
				receivers.add(user);
			}
		} else if (vo.getSendType() == 1L) {
			List<String> groupList = (List<String>) DataConverterUtil.convertJson2Object(vo.getReceiveGroups(),
					ArrayList.class);
			for (String groupStr : groupList) {
				recGroup.add(UserGroup.valueOf(groupStr));
			}
		}
		msg.setReceivers(receivers);
		msg.setRecGroup(recGroup);
		msg.setMessagePush(vo.getMessagePush());
		return msg;
	}

	public static NoticeTaskVO transNoticeTask2Vo(NoticeTask notice) {
		NoticeTaskVO vo = new NoticeTaskVO();
		//BeanUtils.copyProperties(notice, vo);
		vo.setActivated(notice.getActivated() == null ? null : notice.getActivated() == true ? 1l : 0l);
		vo.setEmailActivated(notice.getEmailActivated() == null ? null : notice.getEmailActivated() == true ? 1l : 0l);
		vo.setEmailTemp(notice.getEmailTemp());
		vo.setEmailTitle(notice.getEmailTitle());
		vo.setEmailUsed(notice.getEmailUsed() == null ? null : notice.getEmailUsed() == true ? 1l : 0l);
		vo.setEmsActivated(notice.getEmsActivated() == null ? null : notice.getEmsActivated() == true ? 1l : 0l);
		vo.setEmsTemp(notice.getEmsTemp());
		vo.setEmsUsed(notice.getEmsUsed() == null ? null : notice.getEmsUsed() == true ? 1l : 0l);
		vo.setId(notice.getId());
		vo.setInnerMsgActivated(notice.getInnerMsgActivated() == null ? null
				: notice.getInnerMsgActivated() == true ? 1l : 0l);
		vo.setInnerMsgTemp(notice.getInnerMsgTemp());
		vo.setInnerMsgTitle(vo.getInnerMsgTitle());
		vo.setInnerMsgUsed(notice.getInnerMsgUsed() == null ? null : notice.getInnerMsgUsed() == true ? 1l : 0l);
		vo.setModule(notice.getModule());
		vo.setNoteActivated(notice.getNoteActivated() == null ? null : notice.getNoteActivated() == true ? 1l : 0l);
		vo.setNoteTemp(notice.getNoteTemp());
		vo.setNoteUsed(notice.getNoteUsed() == null ? null : notice.getNoteUsed() == true ? 1l : 0l);
		vo.setTask(notice.getTask());
		vo.setSetByUser(notice.getSetByUser() == null ? null : notice.getSetByUser() == true ? 1l : 0l);
		return vo;
	}

	public static NoticeTask transVo2NoticeTask(NoticeTaskVO vo) {
		NoticeTask noticeTask = new NoticeTask();
		//BeanUtils.copyProperties(vo, noticeTask);
		if(vo.getActivated()==null)
		{
			vo.setActivated(0L);
		}
		noticeTask.setActivated(vo.getActivated().longValue() == 1l ? true : false);
		noticeTask.setEmailActivated(vo.getEmailActivated().longValue() == 1l ? true : false);
		noticeTask.setEmailTemp(vo.getEmailTemp());
		noticeTask.setEmailTitle(vo.getEmailTitle());
		noticeTask.setEmailUsed(vo.getEmailUsed().longValue() == 1l ? true : false);
		noticeTask.setEmsActivated(vo.getEmsActivated().longValue() == 1l ? true : false);
		noticeTask.setEmsTemp(vo.getEmsTemp());
		noticeTask.setEmsUsed(vo.getEmsUsed().longValue() == 1l ? true : false);
		noticeTask.setId(vo.getId());
		noticeTask.setInnerMsgActivated(vo.getInnerMsgActivated().longValue() == 1l ? true : false);
		noticeTask.setInnerMsgTemp(vo.getInnerMsgTemp());
		noticeTask.setInnerMsgTitle(vo.getInnerMsgTitle());
		noticeTask.setInnerMsgUsed(vo.getInnerMsgUsed().longValue() == 1l ? true : false);
		noticeTask.setModule(vo.getModule());
		noticeTask.setNoteActivated(vo.getNoteActivated().longValue() == 1l ? true : false);
		noticeTask.setNoteTemp(vo.getNoteTemp());
		noticeTask.setNoteUsed(vo.getNoteUsed().longValue() == 1l ? true : false);
		noticeTask.setSetByUser(vo.getSetByUser() == 1l ? true : false);
		noticeTask.setTask(vo.getTask());
		return noticeTask;
	}

	public static UserNoticeTaskVO transUserNoticeTask2Vo(UserNoticeTask notice) {
		UserNoticeTaskVO vo = new UserNoticeTaskVO();
		BeanUtils.copyProperties(notice, vo);
		return vo;
	}

	public static UserNoticeTask transVo2UserNoticeTask(UserNoticeTaskVO vo) {
		UserNoticeTask noticeTask = new UserNoticeTask();
		BeanUtils.copyProperties(vo, noticeTask);
		noticeTask.setTask(vo.getTask());
		return noticeTask;
	}

	public static void main(String[] args) {
		List<Long> list = (List<Long>) DataConverterUtil.convertJson2Object("[\"2\",\"3\"]", ArrayList.class);
		System.out.println(list.get(0));
	}

}
