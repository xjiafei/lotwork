package com.winterframework.firefrog.user.dao.vo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.dao.vo.UserCenterReportVo;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.Appeal.AppealType;
import com.winterframework.firefrog.user.entity.CreditCardInfo;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.FreezeLog.FreezenRange;
import com.winterframework.firefrog.user.entity.FreezeLog.FrozenAction;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QQInfo;
import com.winterframework.firefrog.user.entity.SecurityCard;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAgent;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.entity.UserFreezeInfo;
import com.winterframework.firefrog.user.entity.UserFreezeInfo.FreezeMethodType;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.web.controller.game.GameGroup;
import com.winterframework.firefrog.user.web.dto.UserAwardStruc;

/**
 * 
 * 类功能说明: 转换Dao的实体类
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * 
 * 
 */
public class VOConverter {

	private static Logger log = LoggerFactory.getLogger(VOConverter.class);
	public static Long VO_CONVERTER_EXCEPTION = 102001L;

	/**
	 * 
	 * 方法描述：将UserCustomer转换为User
	 * 
	 * @param user
	 * @param customer
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static User customer2User(UserCustomer customer) throws Exception {
		if (customer == null) {
			// my dear friend,don't forget to trade in null
			return null;
		}
		User user = null;
		if (customer.getUserLvl() != null && customer.getUserLvl() >= 0) {
			UserAgent useragent = new UserAgent();
			if (null != customer.getAgentLimit()) {
				useragent.setAgentLimit(customer.getAgentLimit());
			}
			if (null != customer.getUserLvl()) {
				useragent.setUserLevel(customer.getUserLvl().intValue());
			}
			if (null != customer.getTermAcount()) {
				useragent.setTeamAgentCount(customer.getTermAcount().intValue());
			}
			if (null != customer.getTermUaccount()) {
				useragent.setTeamUserCount(customer.getTermUaccount().intValue());
			}
			user = useragent;
		} else {
			user = new User();
			user.setUserLevel(customer.getUserLvl() != null ? customer.getUserLvl().intValue() : null);
		}

		ObjectMapper jsonMapper = new ObjectMapper();
		user.setFreeze((customer.getIsFreeze() != null && customer.getIsFreeze() > 0) ? true : false);//是否冻结 1：冻结 0：未冻结
		user.setId(customer.getId());
		user.setFreezeId(customer.getFreezeId());
		User parent=new User();
		parent.setId(customer.getParentId());
		user.setParent(parent);
		user.setVipLvl(customer.getVipLvl() != null ? customer.getVipLvl().intValue() : 0);
		user.setAwardRetStatus(customer.getAwardRetStatus());
		user.setSuperPairStatus(customer.getSuperPairStatus());
		user.setLhcStatus(customer.getLhcStatus());
		UserProfile profile = new UserProfile();
		profile.setUserLvl(customer.getUserLvl());
		profile.setAccount(customer.getAccount());
		profile.setTermACount(customer.getTermAcount());
		profile.setTermUCount(customer.getTermUaccount());
		if (null != customer.getBirthday()) {
			profile.setBirthday(customer.getBirthday());
		}
		if (null != customer.getCipher()) {
			profile.setCipher(customer.getCipher());
		}
		profile.setSerialNumber(customer.getSercuritySerilizeNumber());
		if (null != customer.getEmail()) {
			profile.setEmail(customer.getEmail());
		}

		if (null != customer.getEmailActived()) {
			profile.setEmailActived(customer.getEmailActived());
		}
		if (null != customer.getPasswd()) {
			profile.setPassword(customer.getPasswd());
		}
		if (null != customer.getPasswdLvl()) {
			profile.setPasswordLevel(customer.getPasswdLvl());
		}
		if (null != customer.getModifyPasswdDate()) {
			profile.setModifyPasswdDate(customer.getModifyPasswdDate());
		}
		if (null != customer.getCellphone()) {
			profile.setPhone(customer.getCellphone());
		}
		if (null != customer.getQuestionStructureActiveDate()) {
			profile.setQaActiveDate(customer.getQuestionStructureActiveDate());
		}
		if (null != customer.getNickname()) {
			profile.setNickname(customer.getNickname());
		}
		if (null != customer.getHeadImg()) {
			profile.setHeadImg(customer.getHeadImg());
		}

		profile.setUserChain(customer.getUserChain());

		try {
			if (StringUtils.isNotBlank(customer.getQqStruc())) {
				QQInfo[] qqs = jsonMapper.readValue(customer.getQqStruc(), QQInfo[].class);
				if (qqs != null) {
					profile.setQq(Arrays.asList(qqs));
				}
			}
			if (StringUtils.isNotBlank(customer.getQuStruc())) {
				QAInfo[] qqa = jsonMapper.readValue(customer.getQuStruc(), QAInfo[].class);
				if (qqa != null) {
					profile.setQa(Arrays.asList(qqa));
				}
			}
		} catch (JsonMappingException jsonMappingException) {

			log.error("Customer转换User出错：" + jsonMappingException.getMessage(), jsonMappingException);
			throw jsonMappingException;

		} catch (JsonParseException jsonParseException) {

			log.error("Customer转换User出错：" + jsonParseException.getMessage(), jsonParseException);
			throw jsonParseException;

		} catch (IOException io) {

			log.error("Customer转换User出错：" + io.getMessage(), io);
			throw io;
		}

		profile.setRegisterDate(customer.getRegisterDate());
		if (customer.getRegisterIp() != null) {
			profile.setRegisterIP(customer.getRegisterIp());
		}
		profile.setSex(customer.getSex());
		if (customer.getVipCellphone() != null) {
			profile.setVipPhone(customer.getVipCellphone());
		}
		if (null != customer.getWithdrawPasswd()) {
			profile.setWithdrawPwd(customer.getWithdrawPasswd());
		}
		if (null != customer.getWithdrawPasswdActiveDate()) {
			profile.setWithdrawPwdActiveDate(customer.getWithdrawPasswdActiveDate());
		}
		profile.setSerialNumber(customer.getSercuritySerilizeNumber());
		user.setUserProfile(profile);

		UserFreezeInfo freezeInfo = new UserFreezeInfo();
		freezeInfo.setFreezeDate(customer.getFreezeDate());
		freezeInfo.setFreezeAccount(customer.getFreezeAccount());
		freezeInfo.setFreezeMemo(customer.getFreezeMemo());
		if (customer.getFreezeMethod() != null) {
			freezeInfo.setFreezeMethodType(getFreezeMethodType(customer.getFreezeMethod()));
		}
		if (customer.getFreezer() != null) {
			freezeInfo.setFreezer(customer.getFreezer());
		}
		freezeInfo.setFreeze(user.isFreeze());

		user.setUserFreeze(freezeInfo);

		User parantUser = new User();
		parantUser.setId(customer.getParentId());
		user.setParent(parantUser);

		LoginLog loginLog = new LoginLog();
		if (null != customer.getLastLoginDate()) {
			loginLog.setLoginDate(customer.getLastLoginDate());
		}
		if (customer.getLastLoginIp() != null) {
			loginLog.setLoginIP(customer.getLastLoginIp());
		}
		loginLog.setUser(user);
		user.setLastLoginLog(loginLog);
		UserFund fund=new UserFund();
		fund.setBal(customer.getBal());
		fund.setTeamBal(customer.getSumBal());
		user.setFund(fund);
		user.setAgentlimit(customer.getAgentLimit());
		profile.setSource(customer.getSource());
		profile.setDevice(customer.getDevice());
		user.setAppealNewFunc(customer.getAppealNewFunc());
		return user;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static UserCustomer convertUser2Customer(User user) {
		UserCustomer customer = new UserCustomer();
		UserProfile userProfile = user.getUserProfile();
		UserFreezeInfo userFreeze = user.getUserFreeze();
		customer.setId(user.getId());
		if (user.getVipLvl() != null) {
			customer.setVipLvl(user.getVipLvl().longValue());
		}
		if (user.getAwardRetStatus() != null) {
			customer.setAwardRetStatus(user.getAwardRetStatus());
		}
		if (user.getSuperPairStatus() != null) {
			customer.setSuperPairStatus(user.getSuperPairStatus());
		}
		if (user.getLhcStatus() != null) {
			customer.setLhcStatus(user.getLhcStatus());
		}
		if (user.getLastLoginLog() != null) {
			customer.setLastLoginDate(user.getLastLoginLog().getLoginDate());
			customer.setLastLoginIp(user.getLastLoginLog().getLoginIP());
		}

		if (user.getParent() != null) {
			customer.setParentId(user.getParent().getId());
		}

		if (userProfile != null) {
			customer.setPasswd(userProfile.getPassword());
			customer.setPasswdLvl(userProfile.getPasswordLevel());
			customer.setModifyPasswdDate(userProfile.getModifyPasswdDate());
			if (null != userProfile.getQq()) {
				customer.setQqStruc(DataConverterUtil.convertObject2Json(userProfile.getQq()));
			}
			customer.setQuestionStructureActiveDate(userProfile.getQaActiveDate());
			if (null != userProfile.getQa()) {
				customer.setQuStruc(DataConverterUtil.convertObject2Json(userProfile.getQa()));
			}
			customer.setRegisterDate(userProfile.getRegisterDate());
			customer.setRegisterIp(userProfile.getRegisterIP());
			customer.setSex(userProfile.getSex());
			customer.setVipCellphone(userProfile.getVipPhone());
			customer.setWithdrawPasswd(userProfile.getWithdrawPwd());
			customer.setWithdrawPasswdActiveDate(userProfile.getWithdrawPwdActiveDate());
			customer.setAccount(userProfile.getAccount());
			customer.setBirthday(userProfile.getBirthday());
			customer.setCellphone(userProfile.getPhone());
			customer.setCipher(userProfile.getCipher());
			customer.setEmail(userProfile.getEmail());
			customer.setEmailActived(userProfile.getEmailActived());
			customer.setUserChain(userProfile.getUserChain());
			if(userProfile.getAppealNewFunc() != null){
				customer.setAppealNewFunc(userProfile.getAppealNewFunc());
		}
			customer.setNickname(userProfile.getNickname());
			customer.setHeadImg(userProfile.getHeadImg());
		}

		if (userFreeze != null) {
			customer.setFreezeDate(userFreeze.getFreezeDate());
			customer.setFreezeMethod(getFreezeMethodMathValue(userFreeze.getFreezeMethodType()));
			if (null != userFreeze.getFreezer() && userFreeze.getFreezer() > 0) {
				customer.setFreezer((long) userFreeze.getFreezer());
			}
			customer.setIsFreeze(userFreeze.isFreeze() ? 1 : 0);
			customer.setFreezeMemo(userFreeze.getFreezeMemo());
		}

		if (user instanceof UserAgent) {
			if (null != ((UserAgent) user).getAgentLimit()) {
				customer.setAgentLimit(Long.valueOf(((UserAgent) user).getAgentLimit()));
			}
			if (null != ((UserAgent) user).getUserLevel()) {
				customer.setUserLvl(((UserAgent) user).getUserLevel());
			}
			if (null != ((UserAgent) user).getTeamAgentCount()) {
				customer.setTermAcount(Long.valueOf(((UserAgent) user).getTeamAgentCount()));
			}
			if (null != ((UserAgent) user).getTeamUserCount()) {
				customer.setTermUaccount(Long.valueOf(((UserAgent) user).getTeamUserCount()));
			}

		}
		
		return customer;
	}

	public static long getFreezeMethodMathValue(FreezeMethodType type) {
		if (type == null) {
			return 0;
		}

		switch (type) {
		case ABSOLUTELY:
			return 1;
		case JUST_FOR_LOGIN:
			return 2;
		case JUST_FOR_RECHARGEANDWITHDRAWAL:
			return 3;
		case JUST_FOR_ORDER:
			return 4;
		default:
			return 0;

		}

	}

	public static FreezeMethodType getFreezeMethodType(Long value) {
		switch (value.intValue()) {
		case 1:
			return FreezeMethodType.ABSOLUTELY;
		case 2:
			return FreezeMethodType.JUST_FOR_LOGIN;
		case 3:
			return FreezeMethodType.JUST_FOR_RECHARGEANDWITHDRAWAL;
		case 4:
			return FreezeMethodType.JUST_FOR_ORDER;
		case 5:
			return FreezeMethodType.APPREAL;
		default:
			return null;
		}

	}

	/**
	 * 
	 * 方法描述：将UserMessage值对象转换成Message实体
	 * 
	 * @param message
	 * @return
	 */
	public static MessageTopic userMessage2MessageTopic(UserMessage um, long userId) {
		MessageTopic mt = new MessageTopic();

		mt.setId(um.getId());
		if (um.getSender() != null) {
			mt.setSender(getUser(um.getSender(), um.getSenderAccount()));
		}
		// receivers
		List<User> receivers = new ArrayList<User>();
		if (um.getReceiver() != null) {
			receivers.add(getUser(um.getReceiver(), um.getReceiverAccount()));
		} else {
			if (um.getReceivers() != null) {
				String[] temp = um.getReceiverAccount().split(",");
				for (String s : temp) {
					receivers.add(getUser(-2L, s.trim()));
				}
			}
		}
		mt.setReceiver(receivers);

		mt.setSendTime((Date) um.getSendTime());
		mt.setTitle(um.getTitle());
		mt.setContent(um.getContent());

		// 消息类型：1，系统消息；2，普通消息
		if (um.getType() == 1) {
			mt.setType(MessageTopic.MessageType.SystemMsg);
		} else if (um.getType() == 2) {
			mt.setType(MessageTopic.MessageType.NormalMsg);
		}

		if (um.getSenderUnread() != null) {
			if (um.getSenderUnread() == 0) {
				mt.setSenderRead(false);
			} else if (um.getSenderUnread() == 1) {
				mt.setSenderRead(true);
			}
		} else {
			mt.setSenderRead(null);
		}

		if (um.getReceiveUnread() != null) {
			if (um.getReceiveUnread() == 0) {
				mt.setReceiverRead(false);
			} else if (um.getReceiveUnread() == 1) {
				mt.setReceiverRead(true);
			}
		} else {
			mt.setReceiverRead(null);
		}

		mt.setReadTime((Date) um.getReadTime());
		mt.setMsgRoute(um.getMsgRout());
		mt.setSenderFrom(um.getSenderFrom());
		mt.setReceiverFrom(um.getReceiveFrom());
		// owner为当前用户
		mt.setOwner(getUser(userId, ""));
		mt.setGmtModified(um.getGmtModified());
		mt.setMessagePush(um.getMessagePush());
		return mt;
	}

	private static synchronized User getUser(long userId, String account) {
		User u = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(account);

		u.setId(userId);
		u.setUserProfile(userProfile);
		return u;
	}

	private static Message getMessage(long id) {
		Message m = new Message();
		m.setId(id);
		return m;
	}

	/**
	 * @Title: messageTopic2UserMessage
	 * @Description: 将Message实体转换成UserMessage值对象
	 * @param mt
	 * @param  userId
	 * @param  设定文件
	 * @return UserMessage 返回类型
	 * @throws
	 */
	public static UserMessage messageTopic2UserMessage(MessageTopic mt) {
		UserMessage um = new UserMessage();

		um.setId(mt.getId());
		um.setEffectHours(mt.getEffectHours());
		um.setNoticeMsgId(mt.getNoticeMsgId());
		um.setSender(mt.getSender() == null ? null : mt.getSender().getId());
		um.setSenderAccount(mt.getSender() == null ? null : mt.getSender().getUserProfile().getAccount());

		// receivers
		if (mt.getReceiver() != null) {
			if (mt.getReceiver().size() == 1) {
				User receiver = mt.getReceiver().get(0);
				um.setReceiver(receiver.getId());
				um.setReceiverAccount(receiver.getUserProfile().getAccount());
			} else {
				String recId = "";
				String recAccount = "";
				for (User u : mt.getReceiver()) {
					recId += u.getId()+",";
					recAccount += u.getUserProfile().getAccount()+",";
				}
				
				um.setReceivers(recId.substring(0, recId.length() - 1));
				um.setReceiverAccount(recAccount.substring(0, recAccount.length() - 1));
			}
		}

		um.setSendTime(mt.getSendTime());
		um.setTitle(mt.getTitle());
		um.setContent(mt.getContent());
		um.setType(mt.getType() == null ? null : (long) mt.getType().getIntegerValue());

		if (mt.isSenderRead() != null) {
			if (mt.isSenderRead()) {
				um.setSenderUnread(1L);
			} else {
				um.setSenderUnread(0L);
			}
		}

		if (mt.isReceiverRead() != null) {
			if (mt.isReceiverRead()) {
				um.setReceiveUnread(1L);
			} else {
				um.setReceiveUnread(0L);
			}
		}

		um.setMsgRout(mt.getMsgRoute());
		um.setSenderFrom(mt.getSenderFrom());
		um.setReceiveFrom(mt.getReceiverFrom());
		um.setReadTime(mt.getReadTime());
		um.setGmtModified(mt.getGmtModified());

		return um;
	}

	/**
	 * 
	 * 方法描述：将UserMessageReply值对象转换成MessageReply实体
	 * 
	 * @param umr
	 * @return
	 */
	public static MessageReply userMessageReply2MessageReply(UserMessageReply umr) {
		MessageReply mr = new MessageReply();
		mr.setId(umr.getId());
		mr.setContent(umr.getContent());
		mr.setSendTime(umr.getSenderDate());
		mr.setSender(getUser(umr.getSender(), umr.getSenderAccount()));
		List<User> receiver = new ArrayList<User>();
		receiver.add(getUser(umr.getReceive(), umr.getReceiveAccount()));
		mr.setReceiver(receiver);
		mr.setParent(getMessage(umr.getParentId()));
		mr.setRoot(getMessage(umr.getRootId()));
		return mr;

	}

	/**
	 * 
	 * 方法描述：将MessageReply实体转换成UserMessageReply值对象
	 * 
	 * @param mr
	 * @return
	 */
	public static UserMessageReply messageReply2UserMessageReply(MessageReply mr) {
		UserMessageReply umr = new UserMessageReply();
		umr.setId(mr.getId());
		umr.setContent(mr.getContent());
		umr.setSenderDate(mr.getSendTime());
		umr.setSender(mr.getSender().getId());
		umr.setSenderAccount(mr.getSender().getUserProfile().getAccount());
		umr.setReceive(mr.getReceiver().get(0).getId());
		umr.setReceiveAccount(mr.getReceiver().get(0).getUserProfile().getAccount());
		umr.setRootId(mr.getRoot().getId());
		umr.setParentId(mr.getParent().getId());

		return umr;
	}

	/**
	 * 用户申诉vo转换
	 * 
	 * @param appeal
	 * @return
	 * @throws JsonProcessingException
	 */
	public static UserAppeal appeal2UserAppeal(Appeal appeal) throws JsonProcessingException {
		UserAppeal userAppeal = new UserAppeal();
		userAppeal.setAccount(appeal.getAccount());

		if (appeal.getType() != null) {
			userAppeal.setAppealType((appeal.getType() == AppealType.QA) ? Appeal.APPEALTYPE_QA
					: Appeal.APPEALTYPE_EMAIL);
		}
		if (null != appeal.getCreditCard()) {
			ObjectMapper om = new ObjectMapper();
			userAppeal.setCardStruc(om.writeValueAsString(appeal.getCreditCard()));
		}
		userAppeal.setCreateDate(appeal.getAppealDate());
		userAppeal.setIdCopy(appeal.getIdCopy());
		userAppeal.setLoginArea(appeal.getLoginArea());
		userAppeal.setReceiveEmail(appeal.getReceiveEmail());
		userAppeal.setRegisterArea(appeal.getRegisterArea());
		userAppeal.setPassed(appeal.getPassed());
		userAppeal.setActivedDays(appeal.getActivedDays());
		userAppeal.setNotice(appeal.getNotice());
		userAppeal.setOperater(appeal.getOperater());
		userAppeal.setOperaterAccount(appeal.getOperaterAccount());
		userAppeal.setId(appeal.getId());
		userAppeal.setPassDate(appeal.getPassDate());
		return userAppeal;
	}

	/**
	 * loginLog vo转换
	 * 
	 * @param loginlog
	 * @return
	 */
	public static UserLoginLog loginLog2UserLoginLog(LoginLog loginlog) {
		UserLoginLog userLoginLog = new UserLoginLog();
		userLoginLog.setLoginDate(loginlog.getLoginDate());
		userLoginLog.setLoginIp(loginlog.getLoginIP());
		userLoginLog.setUserId(loginlog.getUser().getId());
		userLoginLog.setChannelId(loginlog.getChannelId());
		return userLoginLog;
	}

	/** 
	* @Title: userAppeal2Appeal 
	* @Description: 申诉entity转换为申诉VO 
	* @param @param _appeal
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Appeal    返回类型 
	* @throws 
	*/
	public static Appeal userAppeal2Appeal(UserAppeal _appeal) throws Exception {
		Appeal appeal = new Appeal();
		appeal.setAccount(_appeal.getAccount());
		appeal.setActivedDays(_appeal.getActivedDays());
		appeal.setAppealDate(_appeal.getCreateDate());
		ObjectMapper om = new ObjectMapper();
		appeal.setCreditCard(om.readValue(_appeal.getCardStruc(), CreditCardInfo.class));
		appeal.setIdCopy(_appeal.getIdCopy());
		appeal.setId(_appeal.getId());
		appeal.setLoginArea(_appeal.getLoginArea());
		appeal.setNotice(_appeal.getNotice());
		appeal.setOperater(_appeal.getOperater());
		appeal.setOperaterAccount(_appeal.getOperaterAccount());
		appeal.setPassDate(_appeal.getPassDate());
		appeal.setPassed(_appeal.getPassed());
		appeal.setReceiveEmail(_appeal.getReceiveEmail());
		appeal.setRegisterArea(_appeal.getRegisterArea());
		appeal.setVipLvl(_appeal.getVipLvl());
		appeal.setType(_appeal.getAppealType() == Appeal.APPEALTYPE_QA ? AppealType.QA : AppealType.Email);
		return appeal;
	}

	public static FrozenAction getFrozenAction(Integer value) {

		switch (value) {
		case 0:
			return FrozenAction.UnFreeze;
		case 1:
			return FrozenAction.Freeze;
		default:
			return null;
		}

	}

	public static FreezenRange getFreezenRange(Integer value) {

		switch (value) {
		case 0:
			return FreezenRange.UserTree;
		case 1:
			return FreezenRange.SingleUser;
		default:
			return null;
		}

	}

	public static FreezeLog userFreezeLog2FreezeLog(UserFreezeLog userFreezeLog) {
		FreezeLog freezeLog = new FreezeLog();
		freezeLog.setAction(getFrozenAction(userFreezeLog.getRange()));
		freezeLog.setMethod(userFreezeLog.getMethod());
		freezeLog.setRange(getFreezenRange(userFreezeLog.getRange()));
		freezeLog.setReason(userFreezeLog.getUnfreezeMemo());
		freezeLog.setUnfreezeMemo(userFreezeLog.getUnfreezeMemo());
		freezeLog.setTime(userFreezeLog.getGmtCreated());
		freezeLog.setUserLvl(userFreezeLog.getUserLvl());
		freezeLog.setFreezeAccount(userFreezeLog.getFreezeAccount());
		freezeLog.setMemo(userFreezeLog.getMemo());
		freezeLog.setFrozenAccount(userFreezeLog.getFrozenAccount());
		freezeLog.setUnfreezeAccount(userFreezeLog.getUnfreezeAccount());
		
		
		freezeLog.setVipLvl(userFreezeLog.getVipLvl());
		UserProfile actorUserProfile = new UserProfile();
		actorUserProfile.setAccount(userFreezeLog.getActorAccount());
		User actorUser = new User();
		actorUser.setId(userFreezeLog.getActor());
		actorUser.setUserProfile(actorUserProfile);
		freezeLog.setActor(actorUser);
		if (userFreezeLog.getUnfreezeDate() != null) {
			freezeLog.setUnfreezeDate(userFreezeLog.getUnfreezeDate());
		}

		UserProfile frozenUserProfile = new UserProfile();
		frozenUserProfile.setAccount(userFreezeLog.getFrozenAccount());		
		UserFreezeInfo ufi = new UserFreezeInfo();
		ufi.setFreezeDate(userFreezeLog.getFreezeDate());
		User frozenUser = new User();
		UserFund fund = new UserFund();
		fund.setBal(userFreezeLog.getFrozenAccountBal());
		frozenUser.setFund(fund );
		frozenUser.setId(userFreezeLog.getFrozen());
		frozenUser.setUserProfile(frozenUserProfile);
		frozenUser.setUserFreeze(ufi);
		freezeLog.setFrozenUser(frozenUser);
		return freezeLog;
	}

	/** 
	 * 将数据库查询出的vo对象转换为实体对象
	*/
	public static UserAgentCount agentVoToAgent(UserAgentCountVo userAgentCountVo) {
		UserAgentCount userAgentCount = new UserAgentCount();
		userAgentCount.setBet(userAgentCountVo.getBet());
		userAgentCount.setCharge(userAgentCountVo.getCharge());
		userAgentCount.setDay(userAgentCountVo.getDay());
		userAgentCount.setNewUser(userAgentCountVo.getNewUser());
		userAgentCount.setReward(userAgentCountVo.getReward());
		userAgentCount.setTime(userAgentCountVo.getTime());
		userAgentCount.setUserId(userAgentCountVo.getUserId());
		userAgentCount.setWithDraw(userAgentCountVo.getWithDraw());
		return userAgentCount;
	}

	/** 
	 * 将用户对象转换为SecurityCard对象
	*/
	public static SecurityCard customer2SecurityCard(UserCustomer customer) {
		SecurityCard securityCard = new SecurityCard();
		securityCard.setUserId(customer.getId());
		securityCard.setSercuritySerilizeNumber(customer.getSercuritySerilizeNumber());
		securityCard.setPhoneType(customer.getPhoneType());
		securityCard.setUnbindType(customer.getUnbindType());
		securityCard.setBindPhoneSerial(customer.getBindPhoneSerial());
		return securityCard;
	}

	/** 
	* 将SecurityCard对象转换为用户对象
	*/
	public static UserCustomer convertSecurityCard2Customer(SecurityCard securityCard) {
		UserCustomer customer = new UserCustomer();
		customer.setId(securityCard.getUserId());
		customer.setSercuritySerilizeNumber(securityCard.getSercuritySerilizeNumber());
		customer.setPhoneType(securityCard.getPhoneType());
		customer.setUnbindType(securityCard.getUnbindType());
		customer.setBindPhoneSerial(securityCard.getBindPhoneSerial());
		return customer;
	}
	
	public static UserAgentIncomeVO userCenterReportVo2UserAgentIncomeVO(UserCenterReportVo gi) {
		UserAgentIncomeVO ui =new UserAgentIncomeVO();
		ui.setAccount(gi.getAccount());
		ui.setUserLvl(gi.getUserLvl());
		ui.setUserChain(gi.getUserChain());
		ui.setIsFreeze(gi.getIsFreeze());
		ui.setBet(gi.getTotalSubuserSaleroom());
		ui.setRet(gi.getTotalSubuserPoint());
		ui.setWin(gi.getTotalSubuserWins());
		ui.setTrueBet(gi.getTotalSubuserSaleroom()-gi.getTotalSubuserPoint());
		ui.setResult(gi.getTotalSubuserWins()-(gi.getTotalSubuserSaleroom()-gi.getTotalSubuserPoint()));
		return ui;
	}
	
	public static UserAwardStruc gameGroupTouserAwardStruc(GameGroup gameGroup){
		Long  prizeUnit = 100l;
		UserAwardStruc userAwardStruc = new UserAwardStruc();
		userAwardStruc.setLotterySeriesCode(BigDecimal.valueOf(gameGroup.getLotterySeriesCode()));
		userAwardStruc.setLotterySeriesName(gameGroup.getLotterySeriesName());
		userAwardStruc.setAwardGroupId(gameGroup.getSysAwardGrouId().toString());
		userAwardStruc.setAwardName(gameGroup.getAwardName());
		userAwardStruc.setLotteryId(gameGroup.getLotteryId());
		userAwardStruc.setDirectRet(gameGroup.getDirectRet() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getDirectRet() - prizeUnit) : BigDecimal.valueOf(0));
		userAwardStruc.setThreeoneRet(gameGroup.getThreeoneRet() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getThreeoneRet() - prizeUnit) : BigDecimal.valueOf(0));
		userAwardStruc.setSuperRet(gameGroup.getSuperRet() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getSuperRet() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcYear(gameGroup.getLhcYear() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcYear() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcColor(gameGroup.getLhcColor() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcColor() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setSbThreeoneRet(gameGroup.getSbThreeoneRet() - prizeUnit > 0 ?BigDecimal.valueOf(gameGroup.getSbThreeoneRet() - prizeUnit) : BigDecimal.valueOf(0));
		userAwardStruc.setLhcFlatcode(gameGroup.getLhcFlatcode() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcFlatcode() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcHalfwave(gameGroup.getLhcHalfwave() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcHalfwave() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcOneyear(gameGroup.getLhcOneyear() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcOneyear() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcNotin(gameGroup.getLhcNotin() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcNotin() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuein23(gameGroup.getLhcContinuein23() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuein23() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuein4(gameGroup.getLhcContinuein4() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuein4() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuein5(gameGroup.getLhcContinuein5() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuein5() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuenotin23(gameGroup.getLhcContinuenotin23() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuenotin23() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuenotin4(gameGroup.getLhcContinuenotin4() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuenotin4() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuenotin5(gameGroup.getLhcContinuenotin5() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuenotin5() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setLhcContinuecode(gameGroup.getLhcContinuecode() - prizeUnit > 0 ? BigDecimal.valueOf(gameGroup.getLhcContinuecode() - prizeUnit) : BigDecimal.valueOf(0) );
		userAwardStruc.setStatus(BigDecimal.valueOf(gameGroup.getStatus()));
		userAwardStruc.setDirectLimitRet(BigDecimal.valueOf(gameGroup.getDirectLimitRet()));
		userAwardStruc.setThreeLimitRet(BigDecimal.valueOf(gameGroup.getThreeLimitRet()));
		userAwardStruc.setSuperLimitRet(BigDecimal.valueOf(gameGroup.getSuperLimitRet()));
		userAwardStruc.setLhcYearLimit(BigDecimal.valueOf(gameGroup.getLhcYearLimit()));
		userAwardStruc.setLhcColorLimit(BigDecimal.valueOf(gameGroup.getLhcColorLimit()));
		userAwardStruc.setSbThreeoneRetLimit(BigDecimal.valueOf(gameGroup.getSbThreeoneRetLimit()));
		userAwardStruc.setLhcFlatcodeLimit(BigDecimal.valueOf(gameGroup.getLhcFlatcodeLimit()));
		userAwardStruc.setLhcHalfwaveLimit(BigDecimal.valueOf(gameGroup.getLhcHalfwaveLimit()));
		userAwardStruc.setLhcOneyearLimit(BigDecimal.valueOf(gameGroup.getLhcOneyearLimit()));
		userAwardStruc.setLhcNotinLimit(BigDecimal.valueOf(gameGroup.getLhcNotinLimit()));
		userAwardStruc.setLhcContinuein23Limit(BigDecimal.valueOf(gameGroup.getLhcContinuein23Limit()));
		userAwardStruc.setLhcContinuein4Limit(BigDecimal.valueOf(gameGroup.getLhcContinuein4Limit()));
		userAwardStruc.setLhcContinuein5Limit(BigDecimal.valueOf(gameGroup.getLhcContinuein5Limit()));
		userAwardStruc.setLhcContinuenotin23Limit(BigDecimal.valueOf(gameGroup.getLhcContinuenotin23Limit()));
		userAwardStruc.setLhcContinuenotin4Limit(BigDecimal.valueOf(gameGroup.getLhcContinuenotin4Limit()));
		userAwardStruc.setLhcContinuenotin5Limit(BigDecimal.valueOf(gameGroup.getLhcContinuenotin5Limit()));
		userAwardStruc.setLhcContinuecodeLimit(BigDecimal.valueOf(gameGroup.getLhcContinuecodeLimit()));
		
		userAwardStruc.setMaxDirectRet(Long.valueOf(gameGroup.getMaxDirectRet()));
		userAwardStruc.setMaxThreeOneRet(Long.valueOf(gameGroup.getMaxThreeOneRet()));
		userAwardStruc.setMaxSuperRet(Long.valueOf(gameGroup.getMaxSuperRet()));
		userAwardStruc.setMaxLhcYear(gameGroup.getMaxLhcYear() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcYear()));
		userAwardStruc.setMaxLhcColor(gameGroup.getMaxLhcColor() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcColor()));
		userAwardStruc.setMaxSbThreeoneRet(gameGroup.getMaxSbThreeoneRet() == null ? null : BigDecimal.valueOf(gameGroup.getMaxSbThreeoneRet()));
		userAwardStruc.setMaxLhcFlatcode(gameGroup.getMaxLhcFlatcode() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcFlatcode()));
		userAwardStruc.setMaxLhcHalfwave(gameGroup.getMaxLhcHalfwave() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcHalfwave()));
		userAwardStruc.setMaxLhcOneyear(gameGroup.getMaxLhcOneyear() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcOneyear()));
		userAwardStruc.setMaxLhcNotin(gameGroup.getMaxLhcNotin() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcNotin()));
		userAwardStruc.setMaxLhcContinuein23(gameGroup.getMaxLhcContinuein23() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuein23()));
		userAwardStruc.setMaxLhcContinuein4(gameGroup.getMaxLhcContinuein4() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuein4()));
		userAwardStruc.setMaxLhcContinuein5(gameGroup.getMaxLhcContinuein5() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuein5()));
		userAwardStruc.setMaxLhcContinuenotin23(gameGroup.getMaxLhcContinuenotin23() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuenotin23()));
		userAwardStruc.setMaxLhcContinuenotin4(gameGroup.getMaxLhcContinuenotin4() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuenotin4()));
		userAwardStruc.setMaxLhcContinuenotin5(gameGroup.getMaxLhcContinuenotin5() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuenotin5()));
		userAwardStruc.setMaxLhcContinuecode(gameGroup.getMaxLhcContinuecode() == null ? null : BigDecimal.valueOf(gameGroup.getMaxLhcContinuecode()));
		return userAwardStruc;
	}
}
