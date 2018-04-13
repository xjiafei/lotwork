package com.winterframework.firefrog.active.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.active.service.IActivityVipService;
import com.winterframework.firefrog.active.web.controller.ActivityVipController;
import com.winterframework.firefrog.active.web.dto.ActivityVipRequest;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.Encrypter;
import com.winterframework.firefrog.user.dao.IVipActivityDao;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
 *    
 * 类功能说明: 冻结解冻用户操作服务类  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David
 *
 */
@Service("activityVipImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityVipImpl  implements IActivityVipService {

	private static final Logger log = LoggerFactory.getLogger(ActivityVipImpl.class);	
	
	@Resource(name = "vipactivtyDao")
	IVipActivityDao    vipDaoImpl;
	
	
	@PropertyConfig(value="encrypter.Key")
	protected String encrypterKey;
	@PropertyConfig(value="encrypter.value")
	protected String encrypterValue;
	
	private String ACTIVITY_TIME_PARRTEN = "yyyyMMdd";
	
	
	protected String getUserNameByToken(String token) throws Exception{
//		MD5Utils md = new MD5Utils();
		Encrypter en = Encrypter.getInstance(encrypterKey,encrypterValue);
		String tt = en.decrypt(token);
		String[] str = tt.split("\\|");
		return str[0];
	}
	
	@Override
	public Integer saveApplicaiton(ActivityVipRequest request) throws Exception {
		int respone = 0; //失敗
		VipActivityVo vo = convertActivityVo(request);		
		if (vo.getMonth() != null && vo.getAccount() != null) {
			List<VipActivityVo> resultvo = vipDaoImpl.queryByActivityVo (vo);
			if (resultvo == null || resultvo.isEmpty()){
				vipDaoImpl.insertMessageReply(vo);
				respone = 1; // //報名成功 
			}else{
				respone = 2; //已經報名
			}
		}
		return respone;
	}
	
	public VipActivityVo getActivityInfo(ActivityVipRequest request) throws Exception{
		
		VipActivityVo vo = convertActivityVo(request);
		
		List<VipActivityVo> resultvo1 = vipDaoImpl.queryByActivityVo (vo);
		
		//查詢目前報名數
		vo.setAccount(null);
		List<VipActivityVo> resultvo2 = vipDaoImpl.queryByActivityVo(vo);
		
		if(resultvo1!=null && !resultvo1.isEmpty()){
			vo.setIsRegister(true);
		}else{
			vo.setIsRegister(false);
		}
		
		if(resultvo2!=null && !resultvo2.isEmpty()){
			vo.setRegisterCount(resultvo2.size());
		}else{
			vo.setRegisterCount(0);
		}
		return vo; 
	}
	
	
	private VipActivityVo convertActivityVo(ActivityVipRequest request){
		String account = request.getAccount();
		if (request.getToken() != null && request.getToken().equals("") == false){
			log.info("token="+request.getToken());
			Encrypter en = Encrypter.getInstance(encrypterKey, encrypterValue);
			String tt =  en.decrypt(request.getToken());
			String[] str = tt.split("\\|");
			account = str[0];
		}
		
		VipActivityVo vo = new VipActivityVo();
		vo.setAccount(account);
		vo.setMonth(request.getMonth());
		vo.setSource(request.getSource());
		
		if(StringUtils.isNotEmpty(request.getStartTime())){
			Date startTime = DateUtils.parse(request.getStartTime(), ACTIVITY_TIME_PARRTEN);
			vo.setStartTime(startTime);
		}
		
		if( StringUtils.isNotEmpty(request.getEndTime())){
			Date endTime = DateUtils.parse(request.getEndTime(), ACTIVITY_TIME_PARRTEN);
			vo.setEndTime(endTime);
		}
		return vo;
	}
	

}
