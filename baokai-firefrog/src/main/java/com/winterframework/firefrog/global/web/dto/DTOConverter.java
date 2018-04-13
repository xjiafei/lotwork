package com.winterframework.firefrog.global.web.dto;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.entity.SensitiveWord;

public class DTOConverter {

	private static Map<Long, SensitiveWord.Type> wordMap = new HashMap<Long, SensitiveWord.Type>();

	static {
		wordMap.put(0L, SensitiveWord.Type.register);
		wordMap.put(1L, SensitiveWord.Type.adv);
		wordMap.put(2L, SensitiveWord.Type.help);
		wordMap.put(3L, SensitiveWord.Type.message);
		wordMap.put(4L, SensitiveWord.Type.comment);
		wordMap.put(5L, SensitiveWord.Type.service);
	}

	public static SensitiveWord transWordStruc2Word(GlobalSensitiveWordStruc wordStruc) {
		SensitiveWord word = new SensitiveWord();
		word.setWord(wordStruc.getWord());
		word.setType(wordMap.get(wordStruc.getType()));
		return word;
	}

	public static GlobalIpStruc transIp2Dto(IPAddress ip) throws Exception {
		GlobalIpStruc ipStruc = new GlobalIpStruc();
		ipStruc.setArea(ip.getArea());
		ipStruc.setId(ip.getId());
		ipStruc.setEffectTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ip.getEffectDate()));
		ipStruc.setExpireTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ip.getExpireDate()));
		ipStruc.setOperator(ip.getOperator().getAccount());
		ipStruc.setType((long) ip.getType().ordinal());
		ipStruc.setIp(ip.getIp());
		return ipStruc;
	}

}
