package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;

import com.winterframework.firefrog.game.dao.vo.FundChangeLog;
import com.winterframework.firefrog.game.entity.FundChangeDetail;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.web.dto.FundLogReq;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * 
* @ClassName: GameTransactionRecordServiceImpl 
* @Description: 游戏与资金交易查询Serivce
* @author Richard hugh
* @date 2014-2-17 上午10:33:29 
*
 */
@Service("gameTransactionRecordServiceImpl")
public class GameTransactionRecordServiceImpl implements ITransactionRecordService {

	private static final Logger log = LoggerFactory.getLogger(GameTransactionRecordServiceImpl.class);

	@PropertyConfig("url.baseFundUrl")
	private String baseFundUrl;
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	private String queryFundChangeLog = "/fund/changeLog/queryFundChangeLog";

	@Override
	public List<FundTransactionStruc> queryTransactionRecord(String orderCode,Long userId) throws Exception {
		return query(orderCode, null,userId);
	}

	private List<FundTransactionStruc> query(String orderCode, String planCode,Long userId) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -15);
		Date date = cal.getTime();
		Pager pager = new Pager();
		pager.setStartNo(1);
		pager.setEndNo(Integer.MAX_VALUE);
		FundLogReq req = new FundLogReq();
		req.setExCode(orderCode);
		req.setPlanCode(planCode);
		req.setUserId(userId);
		req.setToDate(Calendar.getInstance().getTime());
		req.setFromDate(date);
		Response<List<FundChangeLog>> fundRes = httpClient.invokeHttp(baseFundUrl + queryFundChangeLog, req,
				new TypeReference<Response<List<FundChangeLog>>>() {
				});
		//test begin
		/*Response<List<FundChangeLog>> fundRes = new Response<List<FundChangeLog>>();
		List<FundChangeLog> list = new ArrayList<FundChangeLog>();
		FundChangeLog log1 = new FundChangeLog();
		log1.setAccount("1111");
		log1.setBeforBal(23435000l);
		log1.setBeforeDamt(23435000l);
		log1.setCtBal(13435000l);
		log1.setCtDamt(28435000l);
		log1.setCurrentFreezeAmt(232323l);
		log1.setOldFreezeAmt(3277786l);
		log1.setBeforeAvailBal(32434545l);
		log1.setCtAvailBal(32434343l);
		log1.setExCode("222222222222222");
		log1.setPlanCode("34343535");
		log1.setFundSn("32323");
		log1.setGmtCreated(new Date());
		log1.setNote("3232");
		log1.setReason("FD,CWTF,null,1");
		list.add(log1);
		fundRes.setResult(list);*/
		//test end

		List<FundTransactionStruc> strucsList = new ArrayList<FundTransactionStruc>();
		if (fundRes.getBody().getResult() != null && !fundRes.getBody().getResult().isEmpty()) {

			for (FundChangeLog fund : fundRes.getBody().getResult()) {

				FundTransactionStruc struc = new FundTransactionStruc();
				struc.setTransactionId(fund.getSn());
				struc.setAccount(fund.getAccount());

				struc.setTransactionTime(fund.getGmtCreated());
				struc.setTransactionInfp(this.getReasion(fund.getReason()));
				struc.setNote(fund.getNote());
				fund.setCtBal(fund.getCtBal() == null ? 0 : fund.getCtBal());
				fund.setBeforBal(fund.getBeforBal() == null ? 0 : fund.getBeforBal());
				fund.setCtDamt(fund.getCtDamt() == null ? 0 : fund.getCtDamt());
				fund.setBeforeDamt(fund.getBeforeDamt() == null ? 0 : fund.getBeforeDamt());
				fund.setCtAvailBal(fund.getCtAvailBal() == null ? 0 : fund.getCtAvailBal());
				fund.setBeforeAvailBal(fund.getBeforeAvailBal() == null ? 0 : fund.getBeforeAvailBal());
				fund.setCurrentFreezeAmt(fund.getCurrentFreezeAmt() == null ? 0 : fund.getCurrentFreezeAmt());
				fund.setOldFreezeAmt(fund.getOldFreezeAmt() == null ? 0 : fund.getOldFreezeAmt());
				if (fund.getCtBal() - fund.getBeforBal() == 0 && fund.getCtDamt() - fund.getBeforeDamt() == 0
						&& fund.getCtAvailBal() - fund.getBeforeAvailBal() == 0
						&& fund.getCurrentFreezeAmt() - fund.getOldFreezeAmt() == 0) {
					break;
				}
				/*if (fund.getCtBal() != null && fund.getCtBal() - fund.getBeforBal() != 0) {
					if (fund.getCtBal() - fund.getBeforBal() > 0) {
						struc.setTransactionPositive(fund.getCtBal() - fund.getBeforBal());
					} else {
						struc.setTransactionNegative(fund.getBeforBal() - fund.getCtBal());
					}
				}
				if (fund.getCtDamt() != null && (fund.getCtDamt() - fund.getBeforeDamt() != 0)) {
					struc.setFreezeAccount((fund.getCtDamt() - fund.getBeforeDamt()));
				}*/
				try {
					//冻结余额
					struc.setFreezeAccount(fund.getCtDamt());
					//可用余额
					struc.setAvailBalance(fund.getCtBal());
					//用户总余额
					struc.setAccountBalance(fund.getCtDamt() + fund.getCtBal());
					//总余额增加|减少
					Long accountBalanceChange = struc.getAccountBalance()
							- (fund.getBeforeDamt() + fund.getBeforBal());
					if (accountBalanceChange > 0) {
						struc.setTotalAccountAdd(accountBalanceChange);
					} else if (accountBalanceChange < 0) {
						struc.setTotalAccountReduce(accountBalanceChange * -1);
					}
					//可用余额增加|减少
					Long availBalanceChange = struc.getAvailBalance() - fund.getBeforBal();
					if (availBalanceChange > 0) {
						struc.setAvailAccountAdd(availBalanceChange);
					} else if (availBalanceChange < 0) {
						struc.setAvailAccountReduce(availBalanceChange * -1);
					}

					//冻结金额增加|减少
					struc.setFreezeAccountChange(struc.getFreezeAccount() - fund.getBeforeDamt());
					struc.setPlanCode(fund.getPlanCode());
					struc.setOrderCode(fund.getExCode());
				} catch (Exception e) {
					e.printStackTrace();
				}

				strucsList.add(struc);
			}
		}

		return strucsList;
	}

	/**
	* Title: queryTransactionRecordPlanCode
	* Description:
	* @param PlanCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.ITransactionRecordService#queryTransactionRecordPlanCode(java.lang.String) 
	*/
	@Override
	public List<FundTransactionStruc> queryTransactionRecordPlanCode(String planCode,Long userId) throws Exception {
		return this.query(null, planCode,userId);
	}

	private String getReasion(String reason) {
		if (reason != null) {
			String[] reasonArray = reason.split(",");
			return FundChangeDetail.getSummary(reasonArray[1]);

		}
		return null;
	}
}
