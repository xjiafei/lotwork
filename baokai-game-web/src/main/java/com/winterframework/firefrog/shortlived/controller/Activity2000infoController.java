package com.winterframework.firefrog.shortlived.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.LeaderboardRequest;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.MonkeyActivityRound;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreResponse;
import com.winterframework.modules.spring.exetend.ExtendedPropertyPlaceholderConfigurer;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

@Controller("activity2000infoController")
@RequestMapping(value = "/info")
public class Activity2000infoController {
	
	private Logger logger = LoggerFactory.getLogger(PoolKingController.class);
	@RequestMapping(value = "/2000info")
	public String get2000info(Model model) throws Exception {
		
	//	ModelAndView mav = new ModelAndView("bet/2000info/2000infoshow");
		return "/bet/2000info/2000info";
	}
}
