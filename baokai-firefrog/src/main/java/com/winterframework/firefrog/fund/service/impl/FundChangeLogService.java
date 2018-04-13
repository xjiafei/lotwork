package com.winterframework.firefrog.fund.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundChangeLogDao;
import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.dao.vo.ResultPager;
import com.winterframework.firefrog.fund.service.IFundChangeLogService;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.firefrog.subsys.web.controller.SubSystemController;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;



@Service
public class FundChangeLogService implements IFundChangeLogService {
	@Autowired
	private IFundChangeLogDao fundChangeLog;

	@Resource(name = "fundChangeLogDaoImpl")
	private IFundChangeLogDao fundChangeLogDao;
	
	private static final Logger log = LoggerFactory.getLogger(FundChangeLogService.class);
	/**
	* Title: getChangeLog
	* Description:
	* @param request
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundChangeLogService#getChangeLog(com.winterframework.modules.web.jsonresult.Request) 
	*/

	@Override
	public Response<List<FundChangeLog>> getChangeLog(Request<FundLogReq> request) {
		
		if (request.getBody().getParam().getFromDate () == null || request.getBody().getParam().getToDate () == null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date todate = new Date();
			Date fromdate = new Date(todate.getTime() - (1000 * 60 * 60 * 24 * 1));
			request.getBody().getParam().setToDate(todate);
			request.getBody().getParam().setFromDate(fromdate);
		}
		
		Response<List<FundChangeLog>> rs = new Response<List<FundChangeLog>>(request);
		
		PageRequest<FundLogReq> pr = PageRequest.getRestPageRequest(request.getBody().getPager().getStartNo(), request
				.getBody().getPager().getEndNo());
		
		if(StringUtils.isNotBlank(request.getBody().getParam().getUserChain())){
			//如果有有usechain 护绿 userId
			request.getBody().getParam().setUserId (null);
			//去頭去尾"/"
			String userChain = request.getBody().getParam().getUserChain ();
			if("/".equals (userChain.substring(0, 1))){
				userChain = userChain.substring (1);
			}
			if("/".equals (userChain.substring (userChain.length()-1, userChain.length ()))){
				userChain = userChain.substring (0,userChain.length ()-1);
			}
			request.getBody().getParam().setUserChain (userChain);
		}
		pr.setSearchDo (request.getBody().getParam ());
		pr.setSortColumns (" gmt_created desc");
		
		FundChangeLog total = fundChangeLogDao.queryTotalamaount (pr);

		Page<FundChangeLog> urls = fundChangeLog.getAllByPageUnion (pr);
		rs.setResult (urls.getResult ());
		ResultPager resultPager = new ResultPager (urls.getThisPageFirstElementNumber (), urls.getThisPageLastElementNumber (), urls.getTotalCount ());
			if(total == null ){
				resultPager.setTotalinBal (0l);
				resultPager.setTotalfrozeAmt (0l);
				resultPager.setTotaloutBal (0l);
			}else{
				resultPager.setTotalinBal (Math.abs (total.getTotalinBal ()));
				resultPager.setTotalfrozeAmt (Math.abs (total.getTotalfrozeAmt ()));
				resultPager.setTotaloutBal (Math.abs (total.getTotaloutBal ()));
			}
		rs.setResultPage (resultPager);
		return rs;
	}
	
	
	@Override
	public FundChangeLog getChangeLogForSub(FundLogReq request) {
		Long userId = request.getUserId();
		Date fromDate = request.getFromDate();
		Date toDate = request.getToDate();
		FundChangeLog value = new FundChangeLog();
		//未帶日期則查詢一個月
		try {
			if(fromDate == null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-01 00:00:00");
				SimpleDateFormat par = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date firestDay = new Date();
				fromDate = par.parse(format.format(firestDay));
				
			}
			if(toDate == null){
				
				Calendar cal = Calendar.getInstance();
				int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-"+maxDay +" 23:59:59");
				SimpleDateFormat par = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				toDate = par.parse(format.format(cal.getTime()));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("日期轉換錯誤");
		}
		
		String sn = request.getSn();
		List<FundChangeLog> result = fundChangeLogDao.queryFundChangeLogBySn(userId,fromDate,toDate,sn);
		value.setStatus("0");
		for(FundChangeLog v : result){
			value.setCtBal(v.getCtBal());
		}
		if(result.size()>0){
			value.setStatus("1");
		}
		return value;
	}

}
