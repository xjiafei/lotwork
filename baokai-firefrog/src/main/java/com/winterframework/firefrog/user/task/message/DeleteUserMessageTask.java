package com.winterframework.firefrog.user.task.message;

import com.winterframework.firefrog.user.dao.IUserMessageDao;

/** 
* @ClassName DeleteUserMessageTask 
* @Description 删除用户5天前站内信 
* @author  hugh
* @date 2014年6月18日 上午11:50:46 
*  
*/
//@Component
public class DeleteUserMessageTask {
	
	//@Autowired
	private IUserMessageDao userMessageDao;
	
	//@Scheduled(cron = "0 0 1 * * ?")  
    public void deleltMessage() {  
		userMessageDao.delete5dayMessage();  
    } 
}
