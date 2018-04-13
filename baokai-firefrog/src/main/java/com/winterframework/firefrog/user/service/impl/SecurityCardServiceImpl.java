/**   
* @Title: SecurityCardServiceImpl.java 
* @Package com.winterframework.firefrog.user.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-2-18 下午1:46:58 
* @version V1.0   
*/
package com.winterframework.firefrog.user.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.entity.SecurityCard;
import com.winterframework.firefrog.user.service.ISecurityCardService;
import com.winterframework.firefrog.user.web.dto.QuerySecurityCardRequest;

/** 
* @ClassName: SecurityCardServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Page
* @date 2014-2-18 下午1:46:58 
*  
*/

@Service("securityCardServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class SecurityCardServiceImpl implements ISecurityCardService {

	private static final Logger log = LoggerFactory.getLogger(SecurityCardServiceImpl.class);

	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;

	@Override
	public void bindSecurityCard(Long userId, String sercuritySerilizeNumber, Long phoneType) throws Exception {
		SecurityCard securityCard = userCustomerDao.querySecurityCardById(userId);
		if (securityCard != null) {
			securityCard.setSercuritySerilizeNumber(sercuritySerilizeNumber);
			securityCard.setPhoneType(phoneType);
			securityCard.setBindPhoneSerial(1L);
			userCustomerDao.updateSecurityCard(securityCard);
		}
	}

	@Override
	public void updateSecurityCard(QuerySecurityCardRequest queryRequest) throws Exception {
		SecurityCard securityCard = userCustomerDao.querySecurityCardById(queryRequest.getUserId());
		securityCard.setSercuritySerilizeNumber(queryRequest.getSercuritySerilizeNumber());
		securityCard.setPhoneType(queryRequest.getPhoneType());
		securityCard.setBindPhoneSerial(1L);
		securityCard.setBindDate(new Date(System.currentTimeMillis()));
		userCustomerDao.updateSecurityCard(securityCard);
			
	
	}

	@Override
	public void unbindSecurityCard(QuerySecurityCardRequest queryRequest) throws Exception {
		SecurityCard securityCard = userCustomerDao.querySecurityCardById(queryRequest.getUserId());
				securityCard.setBindPhoneSerial(0l);
				securityCard.setUnbindType(queryRequest.getUnbindType());
				userCustomerDao.updateSecurityCard(securityCard);
		
	}

	@Override
	public String getSecurityCardNumber(Long userId) throws Exception {
		
		return userCustomerDao.querySecurityCardById(userId).getSercuritySerilizeNumber();
		}

	/** 
	* 校验生成的安全码与传入的安全码是否一致 
	* @param checkCard 要校验的安全码
	* @param snCode 客户的序列号
	* @return
	*/
	public boolean checkSecurityCard(String snCode) {
		return userCustomerDao.querySecurityCardByCard(snCode)>0;
	}



}
