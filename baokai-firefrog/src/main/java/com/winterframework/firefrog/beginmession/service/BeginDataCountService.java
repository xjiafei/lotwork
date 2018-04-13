package com.winterframework.firefrog.beginmession.service;

import java.util.Date;

import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;

public interface BeginDataCountService {

	BeginDataCountVO getDataCount(Date startTime, Date endTime);
	
}
