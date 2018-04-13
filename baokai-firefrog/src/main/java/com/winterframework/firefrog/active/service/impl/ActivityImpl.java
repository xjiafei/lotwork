package com.winterframework.firefrog.active.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.active.dao.IActivitySignUpDao;
import com.winterframework.firefrog.active.dao.vo.ActivitySignUp;
import com.winterframework.firefrog.active.service.IActivityService;
import com.winterframework.firefrog.active.web.dto.ActivityRequest;
import com.winterframework.firefrog.active.web.dto.ActivitySignUpRequest;
import com.winterframework.firefrog.user.dao.IVipActivityDao;

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
@Service("activityImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityImpl  implements IActivityService {

	private static final Logger log = LoggerFactory.getLogger(ActivityImpl.class);	
	
	@Resource(name = "activitySignUpDaoImpl")
	IActivitySignUpDao activitySignUpDaoImpl;
	
	@Override
	public Integer saveSignUp(ActivitySignUpRequest request) throws Exception {
		ActivitySignUp signUp = new ActivitySignUp();
		signUp.setAccunt(request.getAccount());
		signUp.setActivityId(request.getActId());
		signUp.setMonth(request.getMonth());
		signUp.setSource(request.getSource());
		signUp.setNotice(0l);
		signUp.setStatus(1l);
		signUp.setGmtCreate(new Date());
		
		return activitySignUpDaoImpl.insert(signUp);
	}
	
	
	@Override
	public Long querySignUp(ActivitySignUpRequest request) throws Exception {
		ActivitySignUp signUp = new ActivitySignUp();
		signUp.setAccunt(request.getAccount());
		signUp.setActivityId(request.getActId());
		
		return activitySignUpDaoImpl.getUserQuanlification(signUp);
	}
	
}
