/**   
* @Title: IFundChangeLogService.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-18 下午3:54:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: IFundChangeLogService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-18 下午3:54:26 
*  
*/
public interface IFundChangeLogService {
	
	public abstract Response<List<FundChangeLog>> getChangeLog(Request<FundLogReq> request);
	
	public abstract FundChangeLog getChangeLogForSub(FundLogReq request);
}