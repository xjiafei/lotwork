package com.winterframework.firefrog.fund.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawLogDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;
import com.winterframework.firefrog.fund.enums.FundLogEnum.LogModel;
import com.winterframework.firefrog.fund.enums.FundLogEnum.STATUS;
import com.winterframework.firefrog.fund.service.IFundWithdrawLogService;

@Service("fundWithdrawLogServiceImpl")
@Transactional
public class FundWithdrawLogServiceImpl implements IFundWithdrawLogService {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawLogServiceImpl.class);
	
	@Resource(name = "fundWithdrawLogDaoImpl")
	private IFundWithdrawLogDao logDao;

	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao withDrawDao;
	
	
	@Override
	/**
	 * 增加提示
	 */
	public void addLog(FundWithdrawLog drawLog) {
		log.debug(" into FundWithdrawLogServiceImpl addLog");
		logDao.insert(drawLog);
		log.debug(" leave FundWithdrawLogServiceImpl addLog");
	}

	@Override
	public List<FundWithdrawLog> searchLogs(String withdrawSn) {
		log.debug(" into FundWithdrawLogServiceImpl searchLogs");
		return logDao.getLogs(withdrawSn);
	}

	@Override
	public List<String> mergeLogByTime(List<FundWithdrawLog> logs,
			List<FundWithdrawUrgency> urgencys) {
		
		List<FundWithdrawLog> comvertLogs = (List<FundWithdrawLog>) Lists.transform(urgencys, new Function<FundWithdrawUrgency , FundWithdrawLog>(){
								@Override
								public FundWithdrawLog apply(FundWithdrawUrgency urgency) {
									FundWithdrawLog log = new FundWithdrawLog();
									log.setLogContent("Urgency-"+urgency.getUrgencyContext());
									log.setCreateTime(urgency.getCreateTime());
									return log;
								}
							});
		
		logs.addAll(comvertLogs);
		
		Collections.sort(logs, new Comparator<FundWithdrawLog>() {
			@Override
			public int compare(FundWithdrawLog o1, FundWithdrawLog o2) {
				Long time1 = o1.getCreateTime().getTime();
				Long time2 = o2.getCreateTime().getTime();				
				
				if(time1<time2){
					return 1;
				}else{
					return -1;
				}
				
			}
		});

		final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<String> logData = Lists.transform(logs, new Function<FundWithdrawLog , String>(){
			@Override
			public String apply(FundWithdrawLog log) {
				StringBuffer sb = new StringBuffer();
				if(log.getCreateTime()!=null){
					sb.append(format.format(log.getCreateTime()));	
				}
				sb.append(" ").append(log.getLogContent());	
				return sb.toString();
			}});
		
		return logData;
	}

	@Override
	public FundWithdraw getFundWithdrawById(Long id) {
		return withDrawDao.getById(id);
	}
	
	
	@Override
	public void saveFundWithLog(String sn , LogModel logModel, Date createTime , STATUS status) {
		FundWithdrawLog drawLog = new FundWithdrawLog();
		drawLog.setWithdrawSn(sn);
		drawLog.setLogModel(logModel.getCode());
		drawLog.setCreateTime(createTime);
		drawLog.setLogContent(status.getText());
		this.addLog(drawLog);
	}
	
	@Override
	public void saveFundWithLogWithDetail(String sn , LogModel logModel, Date createTime , String detail) {
		FundWithdrawLog drawLog = new FundWithdrawLog();
		drawLog.setWithdrawSn(sn);
		drawLog.setLogModel(logModel.getCode());
		drawLog.setCreateTime(createTime);
		drawLog.setLogContent(detail);
		this.addLog(drawLog);
	}

	@Override
	public FundWithdraw getFundWithdrawByMowNum(String sn) throws Exception {
		return  withDrawDao.queryFundWithdrawByMowNum(sn, null);
	}
	
}
