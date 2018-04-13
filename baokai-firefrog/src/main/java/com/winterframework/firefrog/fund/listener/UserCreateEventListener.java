package com.winterframework.firefrog.fund.listener;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.winterframework.firefrog.common.event.FirefrogEvent;
import com.winterframework.firefrog.common.event.FirefrogEventListener;
import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.vo.Fund;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.event.UserCreateEvent;

/** 
*  创建用户时的事件监听器
*/
@Component("loginFundEventListener")
public class UserCreateEventListener extends FirefrogEventListener{
	
	@Resource(name = "fundDaoImpl")
	private IFundDao fundDao;

	@Override
	public void onApplicationEvent(FirefrogEvent event) {
		if (event instanceof UserCreateEvent) {
			User user= (User)event.getSource();
			Fund fund = new Fund();
			fund.setUserId(user.getId());
			fund.setBal(0L);
			fund.setDisableAmt(0L);
			fund.setFrozenAmt(0L);
			fundDao.insert(fund);
		}
	}

}
