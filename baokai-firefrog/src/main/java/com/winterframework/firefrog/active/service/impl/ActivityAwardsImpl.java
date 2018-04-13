package com.winterframework.firefrog.active.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.active.dao.IActivityDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.IActivityWinningLogDao;
import com.winterframework.firefrog.active.dao.vo.Activity;
import com.winterframework.firefrog.active.dao.vo.ActivityWinningLog;
import com.winterframework.firefrog.active.service.IActivityAwardsService;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsApplyRequest;
import com.winterframework.firefrog.active.web.dto.ActivityAwardsRequest;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.PageRequest;

/**
 * 
 *    
 * 功能说明: 後台資金中心人工派獎操作
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Marco
 *
 */
@Service("activityAwardsImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityAwardsImpl implements IActivityAwardsService{
	
	private static final Logger log = LoggerFactory.getLogger(ActivityAwardsImpl.class);	
	
	@Resource(name = "activityDaoImpl")
	IActivityDao activityDaoImpl;
	
	@Resource(name="activityWinningLogDaoImpl")
	private IActivityWinningLogDao activityWinningLogDao;
	
	@Resource(name = "activityLogDaoImpl")
	IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDaoImpl;
	
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";


	@Override
	public List<Activity> queryActivityDetail() throws Exception {
		//type=9999查詢需要人工派獎的活動
		String type="9999";
		List<Activity> result =activityDaoImpl.getActivityDetail(type);
		return result;
	}

	@Override
	public CountPage<ActivityWinningLog> queryWinningLogUntreat(PageRequest<ActivityAwardsRequest> pageReq) throws Exception {

		CountPage<ActivityWinningLog> result=activityDaoImpl.getAwardsUntreadList(pageReq);
		
		return result;
	}

	@Override
	public CountPage<ActivityWinningLog> queryWinningLogTreat(PageRequest<ActivityAwardsRequest> pageReq)
			throws Exception {
		CountPage<ActivityWinningLog> result=activityDaoImpl.getAwardsTreadList(pageReq);
		return result;
	}

	@Override
	public Long awardsAppy(List<ActivityAwardsApplyRequest> list,String approver) throws Exception {
		Long num=0L;
		if(!isAwardsToWinningLog(list)){
			//帳變
			List<FundGameVo> vos = new ArrayList<FundGameVo>();

			for(ActivityAwardsApplyRequest listOne:list){
				FundGameVo vo = new FundGameVo();			
				vo.setUserId(listOne.getUser_id());
				vo.setIsAclUser(1L);
				vo.setOperator(0L);
				vo.setReason(ACTIVITY_REASON_KEY);
				vo.setNote(listOne.getActName());
				vo.setAmount(listOne.getApproverAmt());
				vos.add(vo);
			}

			
			log.info("start fundchange update");
			List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
			fundChangeService.action(vos,maps);
			log.info("end fundchange update");
			//帳變結束
			
			
			log.info("start winningLog update");
			//拿到全部的SN再跑迴圈做UPDATE
			int index=0;
			for(ActivityAwardsApplyRequest listOne:list){
				
				//更新activity_winning_log資料
				String fundChangeLogSN = maps.get(index).getSn();
				if(fundChangeLogSN == null){
					log.debug("=fundchange has problem : /activityAwardsImpl，fundChangeLogSN="+fundChangeLogSN);
				}else {
					listOne.setApprover(approver);

					listOne.setFundChangeLogSN(fundChangeLogSN);
					num += (long) activityWinningLogDao.updateApproval(listOne.getId(), listOne.getApprover(), new Date(), listOne.getApproverMemo(), listOne.getFundChangeLogSN(), listOne.getApproverAmt());
				}			
			}
			log.info("end winningLog update");
			
		}
		return num;
	}
	
	/**
	* Title: isAwardsToWinningLog
	* Description: 檢查活動得獎單是否已派過獎
	* @param list
	* @return boolean 結果 true已派發,false未派發
	*/
	private boolean isAwardsToWinningLog(List<ActivityAwardsApplyRequest> list){ //傳LIST<ID>檢查全部的得獎單
		boolean result=false;
		//取出全部的得獎單號id
		List<Long> ids=new ArrayList<Long>();
		for(ActivityAwardsApplyRequest listOne : list){
			ids.add(listOne.getId());
		}
		if(activityWinningLogDao.queryNumOfAwardsLog(ids)>0){;
			result=true;
			log.info("already awards , /activityAwardsImpl");
		}
		return result;
		
	}

	@Override
	public String addAwardList(ActivityAwardsRequest request) throws Exception {
		List<String[]> datas = request.getAwardList();
		//取出活動代碼，藉此取得活動ID
		String activityCode = datas.get(0)[1];
		Activity vo = new Activity();
		vo.setActivityCode(activityCode);
		Activity result = activityDaoImpl.getActivityByCode(vo);
		
		
		//取出活動周數
		String[] weekInt = (datas.get(0)[2]).split("\\.");
		Long activityWeek = Long.parseLong(weekInt[0]);
		
		//檢查將要新增的活動周數名單，是否存在於DB並且派獎過
		Long awardNum = activityWinningLogDao.queryAlreadyAwarded(result.getId(), activityWeek);
		Long addNum=0L;
		Long repeatNum=0L;
		
		//去除重複名單
		Set<String> intSet = new HashSet<String>();
		for(String[] element : datas){
			if(element[0]==""){
				break;
			}
			if(!element[1].equals(activityCode) || !element[2].equals(datas.get(0)[2])){
				return "资料有误";
			}
			 intSet.add(element[0]);
		}
		
		if(awardNum == 0){
			
			if(intSet.size()>300){
				return "名单共:"+intSet.size()+"，人数超过300人";
			}
			
			//取出活動用戶名，並寫入activity_winning_log
			for(String data : intSet){

				Long userId;
				try {
					userId = userCustomerDaoImpl.getUserByUserName(data).getId();
				} catch (Exception e) {
					log.info("activityAwardsImpl has ERROR:"+e);
					return "查无用户:"+data;
				}
				//查詢用戶是否存在DB中
				List<ActivityWinningLog> list = new ArrayList<ActivityWinningLog>();
				list = activityWinningLogDao.checkUserIsUpload(data, result.getId(), activityWeek);
				if(list.size()>0){
					repeatNum++;
				}else{
					addNum += activityWinningLogDao.addAwardList(result.getId(), activityWeek, data, userId);					
				}
			}
			Long userTotal = activityWinningLogDao.queryUserUploadTotal(result.getId(), activityWeek);
			String response=null;
			if(repeatNum>0){
				response="活動:"+activityCode+" 第"+activityWeek+"周名单上传成功 ,"+addNum.toString()+"笔,上傳共"+userTotal+"笔,发现"+repeatNum+"笔用戶重複";
			}else{
				response="活動:"+activityCode+" 第"+activityWeek+"周名单上传成功 ,"+addNum.toString()+"笔,上傳共"+userTotal+"笔";				
			}
			return response;			
		}else{
			return "无法新增已派奖名单";
		}
	}
}
