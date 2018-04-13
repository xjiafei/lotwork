package com.winterframework.firefrog.phone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.phone.web.controller.QuickController;
import com.winterframework.firefrog.phone.web.fund.ECPSSDeposit;
import com.winterframework.modules.security.MD5Encrypt;

public class ECPSSUtils {
	private static final Logger log = LoggerFactory.getLogger(ECPSSUtils.class);
	public static String createECPSSurl(ECPSSDeposit decpss){
		decpss.setReturnURL("returnbusiness://go_business");
		String breakUrl = "";
		breakUrl += "http://" + decpss.getEcpssDomain() + "/ecpss?";
		breakUrl += "MerNo=" + decpss.getMerNo();
		breakUrl += "&BillNo=" + decpss.getBillNo();
		breakUrl += "&Amount=" + decpss.getAmount();
		breakUrl += "&OrderTime=" + decpss.getOrderTime();	
		breakUrl += "&ReturnURL=" + "returnbusiness://go_business";
		breakUrl += "&AdviceURL=" + decpss.getAdviceURL();
		breakUrl += "&SignInfo=" + MD5Encrypt.encrypt(decpss.createSign()).toUpperCase();
		breakUrl += "&defaultBankNumber=NOCARD";
		breakUrl += "&payType=noCard";
		log.info("breakUrl : " +breakUrl);			
		return breakUrl;
	}
	
	
	public static void main(String[] args) {
		System.out.println(City.CITY_MAP.get("101050101"));
	}
}
