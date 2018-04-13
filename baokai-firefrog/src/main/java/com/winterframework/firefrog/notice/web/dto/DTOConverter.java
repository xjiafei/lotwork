package com.winterframework.firefrog.notice.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.notice.entity.NoticeMsg;
import com.winterframework.firefrog.notice.entity.NoticeTask;
import com.winterframework.firefrog.notice.entity.UserGroup;
import com.winterframework.firefrog.notice.entity.UserNoticeTask;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;

public class DTOConverter {

	public static NoticeMsg transDto2NoticeMsg(NoticeMsgStruc struc) {
		NoticeMsg msg = new NoticeMsg();
		msg.setContent(struc.getContent());
		msg.setEffectHours(struc.getEffectHours());
		msg.setGmtCreated(struc.getGmtCreated());
		msg.setGmtExpired(struc.getGmtExpired());
		msg.setGmtSended(struc.getGmtSended());
		msg.setId(struc.getId());
		AclUser operator = new AclUser();
		operator.setAccount(struc.getOperater());
		msg.setOperator(operator);
		AclUser deleter = new AclUser();
		deleter.setAccount(struc.getDeleter());
		msg.setDeleter(deleter);
		if (struc.getSendType() != null) {
			msg.setSendType(NoticeMsg.SendType.getEnum(struc.getSendType().intValue()));
		}
		if (struc.getStatus() != null) {
			msg.setStatus(NoticeMsg.Status.getEnum(struc.getStatus().intValue()));
		}
		msg.setTitle(struc.getTitle());
		List<User> receivers = new ArrayList<User>();
		List<UserGroup> recGroup = new ArrayList<UserGroup>();
		if (struc.getSendType() == 2L) {
			String[] users = struc.getReceives().split(";");
			User user = null;
			for (String userStr : users) {
				user = new User();
				UserProfile profile = new UserProfile();
				profile.setAccount(userStr);
				user.setUserProfile(profile);
				receivers.add(user);
			}
		} else if (struc.getSendType() == 1L) {
			String[] groups = struc.getRecGroups().split(",");
			for (String groupStr : groups) {
				recGroup.add(UserGroup.valueOf(groupStr));
			}
		}
		msg.setReceivers(receivers);
		msg.setRecGroup(recGroup);
		msg.setMessagePush(struc.getMessagePush());
		return msg;
	}

	public static NoticeMsgStruc transNoticeMsg2Dto(NoticeMsg msg) {
		NoticeMsgStruc struc = new NoticeMsgStruc();
		struc.setId(msg.getId());
		struc.setContent(msg.getContent());
		struc.setDeleter(msg.getDeleter().getAccount());
		struc.setEffectHours(msg.getEffectHours());
		struc.setGmtCreated(msg.getGmtCreated());
		struc.setGmtExpired(msg.getGmtExpired());
		struc.setGmtSended(msg.getGmtSended());
		struc.setOperater(msg.getOperator().getAccount());
		struc.setSendType((long) msg.getSendType().getIndex());
		struc.setStatus((long) msg.getStatus().getIndex());
		struc.setTitle(msg.getTitle());
		List<User> userList = msg.getReceivers();
		if (msg.getSendType() == NoticeMsg.SendType.USER) {
			StringBuilder sbUser = new StringBuilder(1024);
			for (User user : userList) {
				sbUser.append(user.getUserProfile().getAccount());
				sbUser.append(",");
			}
			sbUser.deleteCharAt(sbUser.length() - 1);
			struc.setReceives(sbUser.toString());
		} else if (msg.getSendType() == NoticeMsg.SendType.GROUP) {
			List<UserGroup> groupList = msg.getRecGroup();
			StringBuilder sbGroup = new StringBuilder(1024);
			for (UserGroup group : groupList) {
				sbGroup.append(group.name());
				sbGroup.append(",");
			}
			sbGroup.deleteCharAt(sbGroup.length() - 1);
			struc.setRecGroups(sbGroup.toString());
		}
		if(msg.getMessagePush() != null){
			if(msg.getMessagePush().equals("0")){
				struc.setMessagePush( "无");
			}
			if(msg.getMessagePush().equals("1")){
				struc.setMessagePush( "特殊提示");
			}
			if(msg.getMessagePush().equals("2")){
				struc.setMessagePush("强制提示");
			}
			if(msg.getMessagePush().equals("3")){
				struc.setMessagePush( "强制5秒");
			}
		}
		return struc;
	}

	public static NoticeTask transNoticeTaskStruc2Entity(NoticeTaskStruc noticeTaskStruc) {

		NoticeTask task = new NoticeTask();
		if (noticeTaskStruc.getActived() != null) {
			if (noticeTaskStruc.getActived().longValue() == 1l) {
				task.setActivated(true);
			} else {
				task.setActivated(false);
			}
		}
		if (noticeTaskStruc.getEmailActived() != null) {
			if (noticeTaskStruc.getEmailActived().longValue() == 1l) {
				task.setEmailActivated(true);
			} else {
				task.setEmailActivated(false);
			}
		}

		task.setEmailTemp(noticeTaskStruc.getEmailTemp());
		task.setEmailTitle(noticeTaskStruc.getEmailTitle());
		if (noticeTaskStruc.getEmailUsed() != null) {
			if (noticeTaskStruc.getEmailUsed().longValue() == 1l) {
				task.setEmailUsed(true);
			} else {
				task.setEmailUsed(false);
			}
		}
		if (noticeTaskStruc.getSmsActived() != null) {
			if (noticeTaskStruc.getSmsActived().longValue() == 1l) {
				task.setEmsActivated(true);
			} else {
				task.setEmsActivated(false);
			}
		}

		task.setEmsTemp(noticeTaskStruc.getSmsTemp());
		if (noticeTaskStruc.getSmsUsed() != null) {
			if (noticeTaskStruc.getSmsUsed().longValue() == 1l) {
				task.setEmsUsed(true);
			} else {
				task.setEmsUsed(false);
			}
		}

		task.setId(noticeTaskStruc.getId());
		if (noticeTaskStruc.getInnerMsgActived() != null) {
			if (noticeTaskStruc.getInnerMsgActived().longValue() == 1l) {
				task.setInnerMsgActivated(true);
			} else {
				task.setInnerMsgActivated(false);
			}
		}

		task.setInnerMsgTemp(noticeTaskStruc.getInnerMsgTemp());
		task.setInnerMsgTitle(noticeTaskStruc.getInnerMsgTitle());
		if (noticeTaskStruc.getInnerMsgUsed() != null) {
			if (noticeTaskStruc.getInnerMsgUsed().longValue() == 1l) {
				task.setInnerMsgUsed(true);
			} else {
				task.setInnerMsgUsed(false);
			}
		}

		task.setModule(noticeTaskStruc.getModule());
		if (noticeTaskStruc.getNoteActived() != null) {
			if (noticeTaskStruc.getNoteActived().longValue() == 1l) {
				task.setNoteActivated(true);
			} else {
				task.setNoteActivated(false);
			}
		}

		task.setNoteTemp(noticeTaskStruc.getNoteTemp());
		if (noticeTaskStruc.getNoteUsed() != null) {
			if (noticeTaskStruc.getNoteUsed().longValue() == 1l) {
				task.setNoteUsed(true);
			} else {
				task.setNoteUsed(false);
			}
		}
		if (noticeTaskStruc.getSetByUser() != null) {
			if (noticeTaskStruc.getSetByUser().longValue() == 1l) {
				task.setSetByUser(true);
			} else {
				task.setSetByUser(false);
			}
		}

		task.setTask(noticeTaskStruc.getTask());
		return task;
	}

	public static UserNoticeTask transNoticeTaskStruc2UserEntity(NoticeTaskStruc noticeTaskStruc,Long userId) {
		UserNoticeTask task = new UserNoticeTask();
		task.setActivated(noticeTaskStruc.getActived());
		task.setEmailActivated(noticeTaskStruc.getEmailActived());
		task.setEmailUsed(noticeTaskStruc.getEmailUsed());
		task.setEmsActivated(noticeTaskStruc.getSmsActived());
		task.setEmsUsed(noticeTaskStruc.getSmsUsed());
		task.setId(noticeTaskStruc.getId());
		task.setInnerMsgActivated(noticeTaskStruc.getInnerMsgActived());
		task.setInnerMsgUsed(noticeTaskStruc.getInnerMsgUsed());
		task.setModule(noticeTaskStruc.getModule());
		task.setNoteActivated(noticeTaskStruc.getNoteActived());
		task.setNoteUsed(noticeTaskStruc.getNoteUsed());
		task.setTask(noticeTaskStruc.getTask());
		task.setUserId(userId);
		return task;
	}

	public static NoticeTaskStruc transNoticeTask2Struc(NoticeTask noticeTask) {
		NoticeTaskStruc taskStruc = new NoticeTaskStruc();
		taskStruc.setActived(noticeTask.getActivated() == true ? 1l : 0l);
		taskStruc.setEmailActived(noticeTask.getEmailActivated() == true ? 1l : 0l);
		taskStruc.setEmailTemp(noticeTask.getEmailTemp());
		taskStruc.setEmailTitle(noticeTask.getEmailTitle());
		taskStruc.setEmailUsed(noticeTask.getEmailUsed() == true ? 1l : 0l);
		taskStruc.setId(noticeTask.getId());
		taskStruc.setInnerMsgActived(noticeTask.getInnerMsgActivated() == true ? 1l : 0l);
		taskStruc.setInnerMsgTemp(noticeTask.getInnerMsgTemp());
		taskStruc.setInnerMsgTitle(noticeTask.getInnerMsgTitle());
		taskStruc.setInnerMsgUsed(noticeTask.getInnerMsgUsed() == true ? 1l : 0l);
		taskStruc.setModule(noticeTask.getModule());
		taskStruc.setNoteActived(noticeTask.getNoteActivated() == true ? 1l : 0l);
		taskStruc.setNoteTemp(noticeTask.getNoteTemp());
		taskStruc.setNoteUsed(noticeTask.getNoteUsed() == true ? 1l : 0l);
		taskStruc.setSetByUser(noticeTask.getSetByUser() == true ? 1l : 0l);
		taskStruc.setSmsActived(noticeTask.getEmsActivated() == true ? 1l : 0l);
		taskStruc.setSmsTemp(noticeTask.getEmsTemp());
		taskStruc.setSmsUsed(noticeTask.getEmsUsed() == true ? 1l : 0l);
		taskStruc.setTask(noticeTask.getTask());
		taskStruc.setId(noticeTask.getId());
		return taskStruc;
	}

	public static NoticeTaskStruc transUserNoticeTask2Struc(UserNoticeTask noticeTask) {
		NoticeTaskStruc taskStruc = new NoticeTaskStruc();
		taskStruc.setActived(noticeTask.getActivated());
		taskStruc.setEmailActived(noticeTask.getEmailActivated());
		taskStruc.setEmailUsed(noticeTask.getEmailUsed());
		taskStruc.setInnerMsgActived(noticeTask.getInnerMsgActivated());
		taskStruc.setInnerMsgUsed(noticeTask.getInnerMsgUsed());
		taskStruc.setModule(noticeTask.getModule());
		taskStruc.setNoteActived(noticeTask.getNoteActivated());
		taskStruc.setNoteUsed(noticeTask.getNoteUsed());
		taskStruc.setSmsActived(noticeTask.getEmsActivated());
		taskStruc.setSmsUsed(noticeTask.getEmsUsed());
		taskStruc.setTask(noticeTask.getTask());
		taskStruc.setId(noticeTask.getId());
		return taskStruc;
	}
}
