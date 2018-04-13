package com.winterframework.firefrog.global.dao.vo;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.entity.GlobalWhiteListLog;
import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.entity.SensitiveWord;

public class VOConverter {
	private static Map<Long, SensitiveWord.Type> wordMap = new HashMap<Long, SensitiveWord.Type>();

	static {
		wordMap.put(0L, SensitiveWord.Type.register);
		wordMap.put(1L, SensitiveWord.Type.adv);
		wordMap.put(2L, SensitiveWord.Type.help);
		wordMap.put(3L, SensitiveWord.Type.message);
		wordMap.put(4L, SensitiveWord.Type.comment);
		wordMap.put(5L, SensitiveWord.Type.service);
	}

	public static SensitiveWord transWordVO2Word(GlobalSensitiveWordVO wordVO) {
		SensitiveWord word = new SensitiveWord();
		word.setWord(wordVO.getWord());
		word.setType(wordMap.get(wordVO.getType()));
		word.setId(wordVO.getId());
		return word;
	}

	public static GlobalIpVO transIp2VO(IPAddress ip) throws Exception {
		GlobalIpVO vo = new GlobalIpVO();
		vo.setArea(ip.getArea());
		vo.setType((long) ip.getType().ordinal());
		vo.setOperator(ip.getOperator().getAccount());
		vo.setId(ip.getId());
		vo.setIp(ip.getIp());
		vo.setEffectTime(ip.getEffectDate());
		vo.setExpireTime(ip.getExpireDate());
		return vo;
	}

	public static IPAddress transVO2Ip(GlobalIpVO vo) throws Exception {
		IPAddress ip = new IPAddress();
		ip.setArea(vo.getArea());
		ip.setEffectDate(vo.getEffectTime());
		ip.setExpireDate(vo.getExpireTime());
		ip.setId(vo.getId());
		ip.setIp(vo.getIp());
		AclUser operator = new AclUser();
		operator.setAccount(vo.getOperator());
		ip.setOperator(operator);
		ip.setType(vo.getType() == 0L ? IPAddress.Type.blackList : IPAddress.Type.whiteList);
		return ip;
	}
	
	public static GlobalWhiteListLog transVO2Log(GlobalWhiteListLogVO vo) throws Exception {
		GlobalWhiteListLog log = new GlobalWhiteListLog();
		log.setId(vo.getId());
		log.setAcunt(vo.getAccunt());
		log.setCuntry(vo.getCountry());
		log.setOperator(vo.getOperator());
		log.setOperationTime(vo.getOperationTime());
		log.setStatus(vo.getStatus());
		log.setWhiteListIp(vo.getWhiteListIP());
		return log;
	}
	
	public static GlobalWhiteListIp transVO2WhiteListIP(GlobalWhiteListIpVO vo) throws Exception {
		GlobalWhiteListIp ip = new GlobalWhiteListIp();
		ip.setId(vo.getId());
		ip.setUserAccunt(vo.getUserAccunt());
		ip.setIpAddr(vo.getIpAddr());
		ip.setCreateUser(vo.getCreateUser());
		ip.setCerateTime(vo.getCerateTime());
		ip.setStatus(vo.getStatus());
		ip.setUpdateUser(vo.getUpdateUser());
		ip.setUpdateTime(vo.getUpdateTime());
		ip.setRemark(vo.getRemark());
		return ip;
	}

}
