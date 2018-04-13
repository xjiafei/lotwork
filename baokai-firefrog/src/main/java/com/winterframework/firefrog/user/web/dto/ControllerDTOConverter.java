/**    
 *    
 * Copyright (c) 2013 by Richard.    
 *
 *      
 * @version 1.0    
 */
package com.winterframework.firefrog.user.web.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.subsys.web.dto.SubSysUserStrucResponse;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.Appeal.AppealType;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.entity.CreditCardInfo;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.Message;
import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.firefrog.user.entity.NoticeMsg;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QQInfo;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.firefrog.user.entity.UserFreezeInfo;
import com.winterframework.firefrog.user.entity.UserFreezeInfo.FreezeMethodType;
import com.winterframework.firefrog.user.entity.UserInboxMessage;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;

/**
 * 
 * 类功能说明: 对于Service层返回来的实体类按接口文件规定的格式进行转换。
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * 
 * 
 */
public class ControllerDTOConverter {
	private static IPSeeker ipseek = IPSeeker.getInstance();

	/**
	 * 
	 * 方法描述：将User实体转换成UserStrucResponse
	 * 
	 * @param userStrucResponse
	 * @param user
	 * @return
	 */
	public static UserStrucResponse user2UserStrucResponse(User user) {
		if (user == null) {
			return null;
		}
		UserStrucResponse userStrucResponse = new UserStrucResponse();

		userStrucResponse.setId(user.getId());
		userStrucResponse.setVipLvl(user.getVipLvl());
		userStrucResponse.setQuestionStructureActiveDate(DataConverterUtil.convertDate2Long(user.getUserProfile()
				.getQaActiveDate()));
		userStrucResponse.setUserLvl(user.getUserLevel());

		UserProfile profile = user.getUserProfile();
		
		if (user.getParent() != null) {
			userStrucResponse.setParentId(user.getParent().getId());
		}
		userStrucResponse.setVipLvl(user.getVipLvl());
		if (profile != null) {
			userStrucResponse.setAccount(profile.getAccount());
			userStrucResponse.setTeamACount(profile.getTermACount());
			userStrucResponse.setTeamUCount(profile.getTermUCount());
			userStrucResponse.setUserLvl(profile.getUserLvl());
			userStrucResponse.setSerialNumber(profile.getSerialNumber());
			userStrucResponse.setPasswd(profile.getPassword());
			userStrucResponse.setPasswdLvl(profile.getPasswordLevel());
			String passwdDate = DateUtils.format(DateUtils.addMonths(profile.getModifyPasswdDate(), 3),DateUtils.DATETIME_FORMAT_PATTERN);
			userStrucResponse.setModifyPasswdDate(passwdDate.replaceFirst(" ", "T"));
			userStrucResponse.setWithdrawPasswd(profile.getWithdrawPwd());
			userStrucResponse.setCipher(profile.getCipher());
			userStrucResponse.setSex(profile.getSex());
			userStrucResponse.setEmail(profile.getEmail());
			userStrucResponse.setEmailActived(profile.getEmailActived());
			userStrucResponse.setCellphone(profile.getPhone());
			userStrucResponse.setBirthday(DataConverterUtil.convertDate2Long(profile.getBirthday()));
			userStrucResponse.setNickname(profile.getNickname());
			userStrucResponse.setHeadImg(profile.getHeadImg());
			if (profile.getRegisterDate() != null) {
				userStrucResponse.setRegisterDate(profile.getRegisterDate().getTime());
			}
			if (profile.getDevice() != null) {
				userStrucResponse.setDevice(profile.getDevice());
			}/*else{
				userStrucResponse.setDevice("NO DEVICE!");
			}*/
			userStrucResponse.setRegisterIp(profile.getRegisterIP());
			if ((profile.getRegisterIP() != null)) {
				userStrucResponse.setRegisterAddress(ipseek.getAddress(IPConverter.longToIp(profile.getRegisterIP())));
				userStrucResponse.setSource(user.getUserProfile().getSource());

			}
			userStrucResponse.setVipCellphone(profile.getVipPhone());
			if (profile.getQq() != null) {
				List<QQStruc> qqList = new ArrayList<QQStruc>();
				for (QQInfo qqinfo : profile.getQq()) {
					QQStruc qq = new QQStruc();
					qq.setNickName(qqinfo.getNickName());
					qq.setQq(qqinfo.getQq());
					qqList.add(qq);
				}
				userStrucResponse.setQqStruc(qqList);
			}
			if (profile.getQa() != null) {
				List<QuStrucResponse> quList = new ArrayList<QuStrucResponse>();
				for (QAInfo qaInfo : profile.getQa()) {
					QuStrucResponse qu = new QuStrucResponse();
					qu.setQu(qaInfo.getQu());
					qu.setAns(qaInfo.getAns());
					quList.add(qu);
				}
				userStrucResponse.setQuStruc(quList);
			}
			userStrucResponse.setUserChain(profile.getUserChain());
			userStrucResponse.setSerialNumber(profile.getSerialNumber());
		}
		UserFreezeInfo freeze = user.getUserFreeze();
		if (freeze != null) {
			userStrucResponse.setIsFreeze((user.isFreeze() ? 1 : 0));
			userStrucResponse.setFreezer(user.getUserFreeze().getFreezer());
			userStrucResponse.setFreezeDate(DataConverterUtil.convertDate2Long(user.getUserFreeze().getFreezeDate()));
			//			userStrucResponse.setFreezeMemo(String.valueOf(getFreezeMethodMathValue(user.getUserFreeze()
			//					.getFreezeMethodType())));
			userStrucResponse.setFreezeMemo(user.getUserFreeze().getFreezeMemo());
			userStrucResponse.setFreezeMethod(getFreezeMethodMathValue(user.getUserFreeze().getFreezeMethodType()));
			userStrucResponse.setFreezeAccount(user.getUserFreeze().getFreezeAccount());
		}

		if (user.getLastLoginLog() != null) {
			userStrucResponse.setLastLoginDate(DataConverterUtil
					.convertDate2Long(user.getLastLoginLog().getLoginDate()));
			long ipval = 0L;
			com.winterframework.firefrog.user.entity.LoginLog ll = user.getLastLoginLog();
			if(null != ll){
				ipval = (ll.getLoginIP() == null ? 0L:ll.getLoginIP());
			}
			userStrucResponse.setLastArea(ipseek.getAddress(IPConverter.longToIp(ipval)));
		}
		UserFund fund = user.getFund();
		if (fund != null) {
			userStrucResponse.setAvailBal(fund.getBal());
			userStrucResponse.setCanWithdrawBal(fund.getDisableAmt());
			if (fund.getBal() != null && fund.getDisableAmt() != null){
				userStrucResponse.setCanWithdrawBal(fund.getDisableAmt() > fund.getBal()? fund.getBal():fund.getDisableAmt());
			}else{
				userStrucResponse.setCanWithdrawBal(fund.getDisableAmt());
		}
			userStrucResponse.setFreezeBal(fund.getFrozenAmt());
		}
		if (user.getFund() != null) {
			userStrucResponse.setTeamBal(user.getFund().getTeamBal());
			userStrucResponse.setAvailBal(user.getFund().getBal());
		}
		userStrucResponse.setAgentlimit(user.getAgentlimit());
		userStrucResponse.setAppealNewFunc(user.getAppealNewFunc());
		userStrucResponse.setLhcStatus(user.getLhcStatus());
		
		/**
		 * 指定IP白名單 IP清單若為空字串表示使用者不是指定IP白名單成員
		 */
		userStrucResponse.setWhiteListIpList(user.getWhiteListIpList());
		return userStrucResponse;
	}

	
	/**
	 * 
	 * 方法描述：将User实体转换成SubSysUserStrucResponse
	 * 
	 * @param userStrucResponse
	 * @param user
	 * @return
	 */
	public static SubSysUserStrucResponse user2UserSubStrucResponse(User user) {
		if (user == null) {
			return null;
		}
		SubSysUserStrucResponse userStrucResponse = new SubSysUserStrucResponse();

		userStrucResponse.setId(user.getId());
	

		UserProfile profile = user.getUserProfile();
		
		userStrucResponse.setVipLvl(user.getVipLvl());
				
		if (user.getParent() != null) {
			userStrucResponse.setParentId(user.getParent().getId());
		}

		if (profile != null) {
			userStrucResponse.setAccount(profile.getAccount());
			userStrucResponse.setUserLvl(profile.getUserLvl());
			userStrucResponse.setPasswd(profile.getPassword());
			userStrucResponse.setPasswdLvl(profile.getPasswordLevel());
		}
		UserFreezeInfo freeze = user.getUserFreeze();
		if (freeze != null) {
			userStrucResponse.setIsFreeze((user.isFreeze() ? 1 : 0));
			
			userStrucResponse.setFreezeMethod(getFreezeMethodMathValue(user.getUserFreeze().getFreezeMethodType()));
		}

		if (user.getLastLoginLog() != null) {
			userStrucResponse.setLastLoginDate(DataConverterUtil.convertDate2Long(user.getLastLoginLog().getLoginDate()));
		}
		UserFund fund = user.getFund();
		if (fund != null) {
			userStrucResponse.setAvailBal(fund.getBal());
		}
		
		return userStrucResponse;
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

	/**
	 * 
	 * 方法描述：转换多条件查询DTO
	 * 
	 * @param request
	 * @return
	 */
	public static CustomerQueryDTO convertMCustomerQueryDTO(Request<QueryUserByCriteriaRequestDTO> request) {

		Request<QueryUserListRequestDTO> _request = new Request<QueryUserListRequestDTO>();

		RequestBody<QueryUserListRequestDTO> body = new RequestBody<QueryUserListRequestDTO>();
		_request.setBody(body);
		_request.getBody().setPager(request.getBody().getPager());
		_request.setHead(request.getHead());

		_request.getBody().setParam(request.getBody().getParam());

		QueryUserByCriteriaRequestDTO dto = request.getBody().getParam();
		CustomerQueryDTO query = convertCustomerQueryDTO(_request);
		query.setToBal(query.getToBal()*10000);
		query.setFromBal(query.getFromBal()*10000);
		query.setIncludeTeamBal(Long.valueOf(1L).equals(dto.getIncludeTeamBal()));

		if (dto != null && dto.getUserId() > 0) {
			//通过查询上级用户User_chain来获取其全部下级用户
			query.setParentChainId(request.getBody().getParam().getUserId());
		}

		if (dto.getFromLoginDate() != null && dto.getFromLoginDate() > 0) {
			query.setFromLoginDate(DataConverterUtil.convertLong2Date(dto.getFromLoginDate()));
		}

		if (dto.getToLoginDate() != null && dto.getToLoginDate() > 0) {
			query.setToLoginDate(DataConverterUtil.convertLong2Date(dto.getToLoginDate()));
		}
		if (dto.getFromDate() != null && dto.getFromDate() > 0) {
			query.setFromDate(DataConverterUtil.convertLong2Date(dto.getFromDate()));
		}

		if (dto.getToDate() != null && dto.getToDate() > 0) {
			query.setToDate(DataConverterUtil.convertLong2Date(dto.getToDate()));
		}
		if (dto.getUserLvl() != null) {
			query.setUserLvl(dto.getUserLvl());
		}
		if (dto.getDevice() != null){
			query.setDevice(dto.getDevice());
		}
		query.setVipLvl(dto.getVipLvl());
		query.setUserChain(dto.getUserChain());

		return query;
	}

	/**
	 * 
	 * 方法描述：转换多条件查询DTO
	 * 
	 * @param request
	 * @return
	 */
	public static UserAppealQueryDTO convertMUserAppealQueryDTO(Request<QueryUserApealListRequestDTO> request) {
		UserAppealQueryDTO queryDTO = new UserAppealQueryDTO();

		QueryUserApealListRequestDTO cRequestDTO = request.getBody().getParam();

		if (cRequestDTO != null) {
			if (StringUtils.isNotBlank(cRequestDTO.getAccount())) {
				queryDTO.setAccount(cRequestDTO.getAccount());
			}

			if (StringUtils.isNotBlank(cRequestDTO.getOperater())) {
				queryDTO.setOperater(cRequestDTO.getOperater());
			}

			if (null != cRequestDTO.getAppealType()) {
				queryDTO.setAppealType(cRequestDTO.getAppealType());
			}

			if (null != cRequestDTO.getPassed()) {
				queryDTO.setPassed(cRequestDTO.getPassed());
			}

			queryDTO.setEndNo(request.getBody().getPager().getEndNo());
			queryDTO.setStartNo(request.getBody().getPager().getStartNo());
		}

		return queryDTO;
	}

	/**
	 * 转换查询DTO
	 * 
	 * @param request
	 * @return
	 */
	public static CustomerQueryDTO convertCustomerQueryDTO(Request<QueryUserListRequestDTO> request) {

		CustomerQueryDTO queryDTO = new CustomerQueryDTO();

		QueryUserListRequestDTO cRequestDTO = request.getBody().getParam();
		if (cRequestDTO != null) {
			if (StringUtils.isNotBlank(cRequestDTO.getAccount())) {
				queryDTO.setAccount(cRequestDTO.getAccount());
			}

			if (StringUtils.isNotBlank(cRequestDTO.getEmail())) {
				queryDTO.setEmail(cRequestDTO.getEmail());
			}

			if (null != cRequestDTO.getFromBal() && cRequestDTO.getFromBal() > 0) {
				queryDTO.setFromBal(cRequestDTO.getFromBal());
			}

			if (null != cRequestDTO.getToBal() && cRequestDTO.getToBal() > 0) {
				queryDTO.setToBal(cRequestDTO.getToBal());
			}

			if (null != cRequestDTO.getFromBal() && cRequestDTO.getFromBal() > 0) {
				queryDTO.setFromDate(DataConverterUtil.convertLong2Date(cRequestDTO.getFromDate()));
			}

			if (null != cRequestDTO.getToDate() && cRequestDTO.getToDate() > 0) {
				queryDTO.setToDate(DataConverterUtil.convertLong2Date(cRequestDTO.getToDate()));
			}

			if (null != cRequestDTO.getUserLvl() && cRequestDTO.getUserLvl() > 0) {
				queryDTO.setUserLvl(cRequestDTO.getUserLvl());
			}
		}
		queryDTO.setEndNo(request.getBody().getPager().getEndNo());
		queryDTO.setStartNo(request.getBody().getPager().getStartNo());

		return queryDTO;
	}

	/**
	 * 
	 * 方法描述：转换查询DTO
	 * 
	 * @param request
	 * @return
	 */
	public static CustomerQueryDTO convertAgentQueryDTO(Request<QueryAgentSubUserRequestDTO> request) {

		CustomerQueryDTO queryDTO = new CustomerQueryDTO();

		QueryAgentSubUserRequestDTO agentCustQueryRequestDTO = request.getBody().getParam();

		/*if (StringUtils.isNotBlank(agentCustQueryRequestDTO.getAccount())) { //20130508 接口修改注销
			queryDTO.setAccount(agentCustQueryRequestDTO.getAccount());
		}
		if (agentCustQueryRequestDTO.getFromBal() > 0) {
			queryDTO.setFromBal(agentCustQueryRequestDTO.getFromBal());
		}

		if (agentCustQueryRequestDTO.getToBal() > 0) {
			queryDTO.setToBal(agentCustQueryRequestDTO.getToBal());
		}

		if (agentCustQueryRequestDTO.getLastLoginDate() > 0) {
			queryDTO.setLastLoginDate(new java.sql.Date(agentCustQueryRequestDTO.getLastLoginDate()));
		}*/

		if (agentCustQueryRequestDTO.getUserId() != 0) {

			queryDTO.setParentId(agentCustQueryRequestDTO.getUserId());

		}
		if (StringUtils.isNotBlank(agentCustQueryRequestDTO.getAccount())) {

			queryDTO.setAccount(agentCustQueryRequestDTO.getAccount());

		}
		queryDTO.setEndNo(request.getBody().getPager().getEndNo());
		queryDTO.setStartNo(request.getBody().getPager().getStartNo());

		return queryDTO;
	}

	public static QueryGeneralAgentDTO convertGeneralAgent(Request<QueryGeneralAgentRequest> request) {

		QueryGeneralAgentDTO queryDto = new QueryGeneralAgentDTO();

		QueryGeneralAgentRequest agentRequest = request.getBody().getParam();
		if (agentRequest != null) {
			if (StringUtils.isNotBlank(agentRequest.getUserName())) {
				queryDto.setUserName(agentRequest.getUserName());
			}

			if (null != agentRequest.getFromBal() && agentRequest.getFromBal() > 0) {
				queryDto.setFromBal(agentRequest.getFromBal());
			}

			if (null != agentRequest.getToBal() && agentRequest.getToBal() > 0) {
				queryDto.setToBal(agentRequest.getToBal());
			}

			if (null != agentRequest.getFromRegisterDate() && agentRequest.getFromRegisterDate() > 0) {
				queryDto.setFromRegisterDate(DataConverterUtil.convertLong2Date(agentRequest.getFromRegisterDate()));
			}

			if (null != agentRequest.getToRegisterDate() && agentRequest.getToRegisterDate() > 0) {
				queryDto.setToRegisterDate(DataConverterUtil.convertLong2Date(agentRequest.getToRegisterDate()));
			}

			if (StringUtils.isNotBlank(agentRequest.getEmail())) {
				queryDto.setEmail(agentRequest.getEmail());
			}
		}
		return queryDto;
	}

	/**
	 * 
	 * 方法描述：将Message实体转换成MsgStrucResponse
	 * 
	 * @param message
	 * @return
	 */
	public static MsgStrucResponse message2MsgStrucResponse(Message message, long userId) {
		MsgStrucResponse resp = new MsgStrucResponse();
		if (message instanceof MessageTopic) {
			resp = msg2MsgStrucResponse((MessageTopic) message, userId);
		} else if (message instanceof MessageReply) {
			resp = msgReply2MsgStrucResponse((MessageReply) message, userId);
		}

		return resp;
	}
	/**
	 * 
	 * 方法描述：将Message实体转换成MsgStrucResponse
	 * 
	 * @param message
	 * @return
	 */
	public static MsgStrucResponse message2MsgStrucResponse(UserInboxMessage messageTopic, long userId) {
		MsgStrucResponse resp = new MsgStrucResponse();
		resp.setId(messageTopic.getGroupId());
		resp.setSender(messageTopic.getTalkUserId());
		resp.setSendTime(messageTopic.getCreateDate().getTime());
		resp.setReceiveTime(messageTopic.getLastReadTime() == null ? null : messageTopic.getLastReadTime()
				.getTime());
		resp.setIsRead(messageTopic.getUnreadCount()>0 ? 0 : 1);
		resp.setSendMsgRout(messageTopic.getUnreadCount().toString());
		resp.setSendFrom(1L);
		resp.setReceiveFrom(1L);
		resp.setContent(messageTopic.getContent());
		resp.setGmtModified(DataConverterUtil.convertDate2Long(messageTopic.getLastReadTime()));
		resp.setSendAccount(messageTopic.getTalkUserAccount());
		resp.setReceiveAccount(messageTopic.getSendAccount());
		resp.setType(messageTopic.getTalkUserId()==0L?1:0);
		return resp;
	}

	/**
	 * @Description: 将Message实体转换成MsgStrucResponse
	 * @param msgStrucResponse
	 * @param message
	 * @param 设定文件
	 * @return MsgStrucResponse 返回类型
	 * @throws
	 */
	private static MsgStrucResponse msg2MsgStrucResponse(MessageTopic messageTopic, long userId) {
		MsgStrucResponse msgStrucResponse = new MsgStrucResponse();
		msgStrucResponse.setId(messageTopic.getId());
		if (messageTopic.getSender() != null) {
			msgStrucResponse.setSender(messageTopic.getSender().getId());
		}
		msgStrucResponse.setSendTime(messageTopic.getSendTime().getTime());
		msgStrucResponse.setReceiveTime(messageTopic.getReadTime() == null ? null : messageTopic.getReadTime()
				.getTime());

		if (messageTopic.getSender() != null) {
			if(userId==-1){
				//如果是后台操作
				msgStrucResponse.setSendMsgRout(messageTopic.getMsgRoute());
				msgStrucResponse.setSendFrom(messageTopic.getSenderFrom());
				msgStrucResponse.setReceiveFrom(messageTopic.getReceiverFrom());
			}else if (messageTopic.getSender()!=null && userId == messageTopic.getSender().getId()) {
				if (messageTopic.isSenderRead() != null) {
					msgStrucResponse.setIsRead(messageTopic.isSenderRead() ? 1 : 0);
				} else {
					msgStrucResponse.setIsRead(null);
				}
				
				msgStrucResponse.setSendMsgRout(getLast(messageTopic.getMsgRoute(),messageTopic.getSenderFrom()));
				msgStrucResponse.setSendFrom(messageTopic.getSenderFrom());
				msgStrucResponse.setReceiveFrom(messageTopic.getReceiverFrom());
			} else if (!messageTopic.getReceiver().isEmpty() &&  userId == messageTopic.getReceiver().get(0).getId()) {
				if (messageTopic.isReceiverRead() != null) {
					msgStrucResponse.setIsRead(messageTopic.isReceiverRead() ? 1 : 0);
				} else {
					msgStrucResponse.setIsRead(null);
				}
				msgStrucResponse.setSendMsgRout(getLast(messageTopic.getMsgRoute(),messageTopic.getReceiverFrom()));
				msgStrucResponse.setSendFrom(messageTopic.getSenderFrom());
				msgStrucResponse.setReceiveFrom(messageTopic.getReceiverFrom());

			}
		}

		// msgStrucResponse.setParentId()
		msgStrucResponse.setTitle(messageTopic.getTitle());
		msgStrucResponse.setContent(messageTopic.getContent());
		msgStrucResponse.setGmtModified(DataConverterUtil.convertDate2Long(messageTopic.getGmtModified()));

		msgStrucResponse.setType(messageTopic.getType().getIntegerValue());
		if (messageTopic.getSender() != null) {
			msgStrucResponse.setSendAccount(messageTopic.getSender().getUserProfile().getAccount());
		}

		// 收件人为一个或多个
		List<User> receivers = messageTopic.getReceiver();
		if (receivers.size() == 1) {
			msgStrucResponse.setReceiveAccount(receivers.get(0).getUserProfile().getAccount());
		} else if (receivers.size() > 1) {
			StringBuilder sb = new StringBuilder();
			for (User u : receivers) {
				sb.append(u.getUserProfile().getAccount());
				sb.append(",");
			}
			msgStrucResponse.setReceives(sb.toString());
			msgStrucResponse.setReceiveAccount(sb.toString());
		}

		msgStrucResponse.setOwner(messageTopic.getOwner().getUserProfile().getAccount());

		// 消息类型：1，发站内信；2，收站内信
		if (messageTopic.getSender() != null && userId == messageTopic.getSender().getId()) {
			msgStrucResponse.setMsgType(1);
		} else {
			msgStrucResponse.setMsgType(2);
		}
		msgStrucResponse.setMessagePush(messageTopic.getMessagePush());
	

		return msgStrucResponse;
	}

	 public static String  getLast(String total,Long begin){
		 if(total==null){
			 return total;
		 }
		 if(begin==null || begin==0L || begin == -1L){
			 return total;
		 }
	     return  total.substring((total+",").indexOf(Long.toString(begin)+","));
//		 return  total.substring((total+",").indexOf(begin.intValue()+","));
	  }
	/**
	 * @Description: 将MessageReply实体转换成MsgStrucResponse
	 * @param msgStrucResponse
	 * @param message
	 * @param 设定文件
	 * @return MsgStrucResponse 返回类型
	 * @throws
	 */
	private static MsgStrucResponse msgReply2MsgStrucResponse(MessageReply mr, long userId) {
		MsgStrucResponse msr = new MsgStrucResponse();

		msr.setId(mr.getId());
		msr.setContent(mr.getContent());
		msr.setParentId(mr.getParent().getId());
		//		mr.getRoot().getId();
		String receives = mr.getReceiver().get(0).getAccount();
		msr.setReceives(receives);
		msr.setReceiveAccount(mr.getReceiver().get(0).getUserProfile().getAccount());
		msr.setSender(mr.getSender().getId());
		msr.setSendAccount(mr.getSender().getUserProfile().getAccount());
		msr.setSendTime(mr.getSendTime().getTime());
		if(mr.getRoot()!=null){
			MessageTopic mt=(MessageTopic)mr.getRoot();
			msr.setTitle(mt.getTitle());
		}
		

		return msr;

	}

	/**
	 * 
	 * 方法描述：将MessageReplyRequestDTO转换成MessageReply实体
	 * 
	 * @param message
	 * @return
	 */
	public static MessageReply messageReplyRequestDTO2MessageReply(MessageReplyRequestDTO mrr) {
		MessageReply mr = new MessageReply();
		mr.setContent(mrr.getContent());
		mr.setParent(getMessage(mrr.getParentId()));
		mr.setRoot(getMessage(mrr.getRootId()));
		mr.setSender(getUserByAccount(mrr.getSendAccount()));
		List<User> receiver = new ArrayList<User>();
		receiver.add(getUserByAccount(mrr.getReceiveAccount()));
		mr.setReceiver(receiver);
		mr.setSendTime(new Date());

		return mr;
	}

	//DRY
	private static Message getMessage(long id) {
		Message m = new Message();
		m.setId(id);
		return m;
	}

	private static User getUserByAccount(String account) {
		User u = new User();
		UserProfile up = new UserProfile();
		up.setAccount(account);
		u.setUserProfile(up);
		return u;
	}

	public static User convertUserStrucResponse2User(UserStrucResponse response) {
		User user = new User();
		UserProfile profile = user.getUserProfile() == null ? new UserProfile() : user.getUserProfile();
		profile.setAccount(response.getAccount());
		profile.setBirthday(DataConverterUtil.convertLong2Date(response.getBirthday()));
		profile.setCipher(response.getCipher());
		profile.setEmail(response.getEmail());
		profile.setEmailActived(response.getEmailActived());
		profile.setPassword(response.getPasswd());
		profile.setPasswordLevel(response.getPasswdLvl());
		profile.setPhone(response.getCellphone());
		List<QQInfo> qqInfoList = new ArrayList<QQInfo>();
		if (response.getQqStruc() != null) {
			for (QQStruc qq : response.getQqStruc()) {
				QQInfo qqInfo = new QQInfo();
				qqInfo.setNickName(qq.getNickName());
				qqInfo.setQq(qq.getQq());
				qqInfoList.add(qqInfo);
			}
			profile.setQq(qqInfoList);
		}
		List<QAInfo> qaInfoList = new ArrayList<QAInfo>();
		if (response.getQuStruc() != null) {
			for (QuStrucResponse qa : response.getQuStruc()) {
				QAInfo qaInfo = new QAInfo();
				qaInfo.setAns(qa.getAns());
				qaInfo.setQu(qa.getQu());
				qaInfoList.add(qaInfo);
			}
			profile.setQa(qaInfoList);
		}
		profile.setRegisterDate(DataConverterUtil.convertLong2Date(response.getRegisterDate()));
		profile.setRegisterIP(response.getRegisterIp());
		profile.setSex(response.getSex());
		profile.setVipPhone(response.getVipCellphone());
		profile.setWithdrawPwd(response.getWithdrawPasswd());
		profile.setAppealNewFunc(response.getAppealNewFunc());
		profile.setNickname(response.getNickname());
		profile.setHeadImg(response.getHeadImg());
		user.setUserProfile(profile);
		
		return user;
	}

	public static Appeal userAppealRequestDTO2Appeal(Request<UserAppealRequestDTO> request) throws JsonParseException,
			JsonMappingException, IOException {
		Appeal appeal = new Appeal();
		appeal.setAccount(request.getBody().getParam().getAccount());
		appeal.setType((request.getBody().getParam().getAppealType() == Appeal.APPEALTYPE_QA) ? AppealType.QA
				: AppealType.Email);

		CreditCardInfo cci = new CreditCardInfo();
		cci.setName(request.getBody().getParam().getCardStruc().getName());
		cci.setNumber(request.getBody().getParam().getCardStruc().getNo());
		cci.setCopyFrontPath(request.getBody().getParam().getCardStruc().getPic1());
		cci.setCopyObliquePath(request.getBody().getParam().getCardStruc().getPic2());
		appeal.setCreditCard(cci);
		appeal.setAppealDate(new Date());
		appeal.setIdCopy(request.getBody().getParam().getIdCopy());
		appeal.setLoginArea(request.getBody().getParam().getLoginArea());
		appeal.setReceiveEmail(request.getBody().getParam().getEmail());
		appeal.setRegisterArea(request.getBody().getParam().getRegisterArea());
		appeal.setPassed(Appeal.PASS_STATUS_UNAUDITED);
		return appeal;
	}

	/**
	 * 将appeal转换成userAppealStruc
	 * 
	 * @param appeal
	 * @return
	 */
	public static UserAppealListStruc appeal2UserAppealStruc(Appeal appeal) {
		UserAppealListStruc userAppealStruc = new UserAppealListStruc();
		userAppealStruc.setAccount(appeal.getAccount());
		long type = (appeal.getType().getValue() == (Appeal.APPEALTYPE_QA)) ? Appeal.APPEALTYPE_QA
				: Appeal.APPEALTYPE_EMAIL;
		userAppealStruc.setAppealType((int) type);
		userAppealStruc.setCreateDate(appeal.getAppealDate());
		userAppealStruc.setMemo(appeal.getNotice());
		userAppealStruc.setOperater(appeal.getOperaterAccount());
		userAppealStruc.setPassDate(appeal.getPassDate());
		userAppealStruc.setVipLvl(appeal.getVipLvl());
		userAppealStruc.setPassed(appeal.getPassed());
		userAppealStruc.setId((int) appeal.getId());
		return userAppealStruc;
	}

	/** 
	* @Title: appeal2UserAppealDetailResponse 
	* @Description:appeal 转换成用户申述审核详情
	* @param @param appeal
	* @param @return    设定文件 
	* @return UserAppealDetailResponse    返回类型 
	* @throws 
	*/
	public static UserAppealDetailResponse appeal2UserAppealDetailResponse(Appeal appeal) {
		UserAppealDetailResponse ua = new UserAppealDetailResponse();
		ua.setAccount(appeal.getAccount());
		long temp = appeal.getType().equals(AppealType.QA) ? Appeal.APPEALTYPE_QA : Appeal.APPEALTYPE_EMAIL;
		ua.setAppealType(temp);
		CardStruc cs = new CardStruc();
		cs.setName(appeal.getCreditCard().getName());
		cs.setNo(appeal.getCreditCard().getNumber());
		cs.setPic1(appeal.getCreditCard().getCopyFrontPath());
		cs.setPic2(appeal.getCreditCard().getCopyObliquePath());
		ua.setCardStruc(cs);
		ua.setIdCopy(appeal.getIdCopy());
		ua.setId(appeal.getId());
		ua.setLoginArea(appeal.getLoginArea());
		ua.setRegisterArea(appeal.getRegisterArea());
		ua.setEmail(appeal.getReceiveEmail());
		ua.setActivedDays(appeal.getActivedDays());
		ua.setPassDate(appeal.getPassDate());
		ua.setPassed(appeal.getPassed());
		ua.setMemo(appeal.getNotice());
		ua.setVipLvl(appeal.getVipLvl());
		return ua;
	}

	public static Appeal userAppealAuditRequestDTO2Appeal(Request<UserAppealAuditRequestDTO> request) {
		BaseUser user = UserTools.getBackUserFromHead(request);
		Appeal appeal = new Appeal();
		appeal.setId(request.getBody().getParam().getId());
		appeal.setActivedDays((long) request.getBody().getParam().getActiveDate());
		appeal.setNotice(request.getBody().getParam().getMemo());
		appeal.setPassed(request.getBody().getParam().getPassed());
		appeal.setPassDate(new Date());
		appeal.setOperaterAccount(user.getAccount());
		appeal.setOperater(request.getHead().getUserId());
		return appeal;
	}

	/** 
	* @Title: convertMQueryFreezeUserDTO 
	* @Description: 冻结用户查询request转换 
	* @param request
	* @return
	*/
	public static QueryFreezeUserDTO convertMQueryFreezeUserDTO(Request<QueryFreezeUserRequestDTO> request) {
		QueryFreezeUserDTO queryDTO = new QueryFreezeUserDTO();

		QueryFreezeUserRequestDTO queryFreezeUserRequestDTO = request.getBody().getParam();

		if (StringUtils.isNotBlank(queryFreezeUserRequestDTO.getAccount())) {
			queryDTO.setAccount(queryFreezeUserRequestDTO.getAccount());
		}

		queryDTO.setIsFreeze(UserCustomer.FREEZE_YES);

		queryDTO.setEndNo(request.getBody().getPager().getEndNo());
		queryDTO.setStartNo(request.getBody().getPager().getStartNo());

		return queryDTO;
	}

	/** 
	* @Title: convertMQueryUnFreezeUserLogDTO 
	* @Description: 解冻记录日志查询request转换 
	* @param request
	* @return
	*/
	public static QueryUnFreezeUserLogDTO convertMQueryUnFreezeUserLogDTO(
			Request<QueryUnFreezeUserLogRequestDTO> request) {
		QueryUnFreezeUserLogDTO queryDTO = new QueryUnFreezeUserLogDTO();

		QueryUnFreezeUserLogRequestDTO queryUnFreezeUserLogRequestDTO = request.getBody().getParam();

		if (queryUnFreezeUserLogRequestDTO != null
				&& StringUtils.isNotBlank(queryUnFreezeUserLogRequestDTO.getAccount())) {
			queryDTO.setAccount(queryUnFreezeUserLogRequestDTO.getAccount());
		}

		queryDTO.setEndNo(request.getBody().getPager().getEndNo());
		queryDTO.setStartNo(request.getBody().getPager().getStartNo());
		return queryDTO;
	}

	/** 
	* @Title: freezeLog2queryUnFreezeUserLogStruc 
	* @Description: 冻结日志记录etity转换为冻结记录DTO数据结构 
	* @param freezeLog
	* @return
	*/
	public static QueryUnFreezeUserLogStruc freezeLog2queryUnFreezeUserLogStruc(FreezeLog freezeLog) {
		QueryUnFreezeUserLogStruc queryUnFreezeUserLogStruc = new QueryUnFreezeUserLogStruc();
		queryUnFreezeUserLogStruc.setUid(freezeLog.getFrozenUser().getId().intValue());
		if (freezeLog.getFrozenUser().getUserFreeze().getFreezeDate() != null) {
			queryUnFreezeUserLogStruc.setFreeDate(freezeLog.getFrozenUser().getUserFreeze().getFreezeDate().getTime());
		}
		queryUnFreezeUserLogStruc.setFrozenDate(freezeLog.getTime().getTime());
		queryUnFreezeUserLogStruc.setVipLvl(freezeLog.getVipLvl());
//		queryUnFreezeUserLogStruc.setOperator(freezeLog.getActor().getUserProfile().getAccount());
		queryUnFreezeUserLogStruc.setOperator(freezeLog.getUnfreezeAccount());
		queryUnFreezeUserLogStruc.setReason(freezeLog.getUnfreezeMemo());
		queryUnFreezeUserLogStruc.setRestBal(freezeLog.getFrozenUser().getFund().getBal());
		if (freezeLog.getFrozenUser() != null) {
			queryUnFreezeUserLogStruc.setUid(freezeLog.getFrozenUser().getId().intValue());
		}
		//暂时注释一下
		queryUnFreezeUserLogStruc.setUserGroup(freezeLog.getUserLvl());
//		if (freezeLog.getAction().equals(FrozenAction.Freeze)) {
//			queryUnFreezeUserLogStruc.setOperator("" + freezeLog.getFreezeAccount());
//		} else {
//			queryUnFreezeUserLogStruc.setOperator("" + freezeLog.getUnfreezeAccount());
//		}
		queryUnFreezeUserLogStruc.setOperator("" + freezeLog.getUnfreezeAccount());
		queryUnFreezeUserLogStruc.setUserName(freezeLog.getFrozenUser().getUserProfile().getAccount());
		if (freezeLog.getUnfreezeDate() != null) {
			queryUnFreezeUserLogStruc.setFreeDate(freezeLog.getUnfreezeDate().getTime());
		}
		
		queryUnFreezeUserLogStruc.setMemo(freezeLog.getMemo());
		queryUnFreezeUserLogStruc.setFreezeAccount(freezeLog.getFreezeAccount());
		
		return queryUnFreezeUserLogStruc;
	}


	public static MsgStrucResponse noticeMsg2MsgStrucResponse(NoticeMsg mt,
			long userId) {
		MsgStrucResponse resp = new MsgStrucResponse();
		resp.setId(mt.getId());
		resp.setSendTime(mt.getSendTime().getTime());
		resp.setContent(mt.getContent());
		resp.setTitle(mt.getTitle());
		resp.setSendAccount(mt.getAccount());
		resp.setMessagePush(mt.getMsgPush());
		return resp;
	}

}
