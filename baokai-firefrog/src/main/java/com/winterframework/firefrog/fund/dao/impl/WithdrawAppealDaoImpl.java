/**   
* @Title: NoticeTaskDaoImpl.java 
* @Package com.winterframework.firefrog.notice.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午10:02:14 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IWithdrawAppealDao;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.dao.vo.WithdrawAppealVO;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal.APPEAL_STATUS;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.SNUtil;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAppealStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: NoticeTaskDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午10:02:14 
*  
*/
@Repository("withdrawAppealDaoImpl")
public class WithdrawAppealDaoImpl extends BaseIbatis3Dao<WithdrawAppealVO> implements IWithdrawAppealDao {

	@Override
	public List<WithdrawAppeal> queryWithdrawAppeal(Request<WithdrawAppealStruc> request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();		
		
		map.put("userId", request.getBody().getParam().getUserId());	
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(cl.DATE, -15); //查包含今天的前15日資料
		map.put("fromDate", cl.getTime());
		
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryWithdrawAppeal",map);		
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		for (WithdrawAppealVO vo : withdrawAppealVOS) {
			withdrawAppeal.add(VOConverter.transWithdrawVO2WithdrawAppeal(vo));
		}
		
		return withdrawAppeal;
	}
	
	@Override
	public List<WithdrawAppeal> queryAppeal(Request<WithdrawAppealStruc> request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("userId", request.getBody().getParam().getUserId());
		map.put("statuses", request.getBody().getParam().getStatuses());
		map.put("appealSn", request.getBody().getParam().getAppealSn());
		map.put("userName", request.getBody().getParam().getUserName());
		map.put("withdrawAmtFrom", request.getBody().getParam().getWithdrawAmtFrom());
		map.put("withdrawAmtTo", request.getBody().getParam().getWithdrawAmtTo());
		map.put("fromWithdrawDate", request.getBody().getParam().getFromWithdrawDate());
		map.put("toWithdrawDate", request.getBody().getParam().getToWithdrawDate());			
		map.put("vipLvl", request.getBody().getParam().getVipLvl());
		map.put("statuses", request.getBody().getParam().getStatuses());
		map.put("fromArgueDate", request.getBody().getParam().getFromArgueDate());
		map.put("toArgueDate", request.getBody().getParam().getToArgueDate());
		
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryAppeal",map);		
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		for (WithdrawAppealVO vo : withdrawAppealVOS) {
			withdrawAppeal.add(VOConverter.transVo2Appeal(vo));
		}
		
		return withdrawAppeal;
	}
	
	//自助申訴提交
	@Override
	public List<WithdrawAppeal> updateAppealByWithdrawSn(Request<WithdrawAppealStruc> request) throws Exception {
		int updateResult=0;
		Map<String, Object> map = new HashMap<String, Object>();		
		
		map.put("withdrawSn", request.getBody().getParam().getSn());	
		
		
		//先撈一次確認資料存在"
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryWithdrawAppealByAppealSn",map);
		
		
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		//筆數大於0才會更新狀態
		if(withdrawAppealVOS.size()>0){
			map.put("argueTime", new Date());	
			map.put("argueAcct", request.getBody().getParam().getAccount());	
			map.put("appealStatus", APPEAL_STATUS.APPEAL_PROCESS.getValue());	
			map.put("uploadImages", request.getBody().getParam().getUploadImages());	
			updateResult = sqlSessionTemplate.update("updateAppealByWithdrawSn",map);			
			for (WithdrawAppealVO vo : withdrawAppealVOS) {
				withdrawAppeal.add(VOConverter.transVo2WithdrawAppeal(vo));
			}			
		}
		
		return withdrawAppeal;
	}
	
	@Override
	public List<WithdrawAppeal> updateAppealStatus(Request<WithdrawAppealStruc> request) throws Exception {
		int updateResult=0;
		Map<String, Object> map = new HashMap<String, Object>();		
		
		map.put("appealSn", request.getBody().getParam().getAppealSn());	
		map.put("appealStatus", request.getBody().getParam().getAppealStatus());	
		map.put("appealAcct", request.getBody().getParam().getAppealAcct());	
		map.put("appealTime", new Date());	
		map.put("appealMemo", request.getBody().getParam().getAppealMemo());	
		map.put("appealTipsResult", request.getBody().getParam().getAppealTipsResult());
		//先撈一次確認資料存在"
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryAppealByAppealSn",map);
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		//筆數大於0才會更新狀態
		if(withdrawAppealVOS.size()>0){
			updateResult = sqlSessionTemplate.update("updateAppealByAppealSn",map);
			withdrawAppealVOS = sqlSessionTemplate.selectList("queryAppealByAppealSn",map);
			for (WithdrawAppealVO vo : withdrawAppealVOS) {
				withdrawAppeal.add(VOConverter.transVo2WithdrawAppeal(vo));
			}
		}
		
		return withdrawAppeal;
	}
	
	@Override
	public List<WithdrawAppeal> queryAppealbySn(Request<WithdrawAppealStruc> request) throws Exception {
		int updateResult=0;
		Map<String, Object> map = new HashMap<String, Object>();		
		
		
		map.put("withdrawSn", request.getBody().getParam().getWithdrawSn());	
		
		
		//先撈一次確認資料存在"		
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryWithdrawAppealByAppealSn",map);
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		//筆數大於0才會更新狀態
		if(withdrawAppealVOS.size()>0){
			for (WithdrawAppealVO vo : withdrawAppealVOS) {
				withdrawAppeal.add(VOConverter.transVo2WithdrawAppeal(vo));
			}			
		}
		
		return withdrawAppeal;
	}
	
	@Override
	public List<WithdrawAppeal> createAppealSn(FundWithdrawOrder entity,Request<WithdrawAuditRequest> request,ISNGenerator snUtil) throws Exception {		
		UserBankStruc userstruc = entity.getUserBankStruc();
		int insertResult=0;
	
		Map<String, Object> map = new HashMap<String, Object>();				
		map.put("userid", entity.getApplyUser().getId());
		map.put("appealSn", snUtil.createAPlSn(FundModel.AP.ITEMS.STK, entity.getApplyUser().getId()));	
		map.put("withdrawAmt", entity.getTotalDrawAmount());	
		map.put("withdrawTime", entity.getApplyTime());	
		map.put("vipLvl", entity.getIsVip());	
		map.put("userName", entity.getApplyAccount());	
		map.put("appealStatus", APPEAL_STATUS.APPEAL_UNCHECK.getValue());	
		map.put("appealAcct", request.getBody().getParam().getApproveAct());	
		map.put("appealTime", new Date());	
		map.put("bankId",userstruc.getBankId().toString());
		map.put("bankAccount",userstruc.getBankAccount());	
		map.put("bankName", userstruc.getBranchName());	
		map.put("cardNumber", userstruc.getBankNumber().toString());	
		map.put("appealMemo", entity.getMemo());
		map.put("isSeperate", entity.getIsSeperate());
		//map.put("argueTime", null);	
		//map.put("argueAcct", null);	
		if(null == entity.getRootSn()){
			map.put("withdrawSn", entity.getSn());
		}else{
			map.put("withdrawSn", entity.getRootSn());
		}
		map.put("isAppeal", Long.valueOf(request.getBody().getParam().getIsAppeal()));	
		map.put("appealTips", request.getBody().getParam().getAppealTips());	
		
		log.info(map.toString());
		
		
		//先撈一次確認資料存在"
		List<WithdrawAppealVO> withdrawAppealVOS = sqlSessionTemplate.selectList("queryWithdrawAppealByAppealSn",map);
		log.info("withdrawAppealVOS size:"+withdrawAppealVOS.size());
		//資料已經存在就不更新		
		//沒撈到資料就insert1筆
		if(withdrawAppealVOS.size()==0){
			insertResult = sqlSessionTemplate.insert("insertWithdrawAppeal", map);			
		}
		
		List<WithdrawAppeal> withdrawAppeal = new ArrayList<WithdrawAppeal>();
		//筆數大於0才會更新狀態
		
		return withdrawAppeal;
	}

	@Override
	public Long queryUncheckAppealCount() {
		return sqlSessionTemplate.selectOne(this.getQueryPath("queryUncheckAppealCount"));
	}

	
	
}
