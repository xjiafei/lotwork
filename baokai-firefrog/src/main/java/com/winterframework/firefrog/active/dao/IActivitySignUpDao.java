package com.winterframework.firefrog.active.dao;

import com.winterframework.firefrog.active.dao.vo.ActivitySignUp;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 活動報名表
 * @author David.Wu
 *
 */
public interface IActivitySignUpDao extends BaseDao<ActivitySignUp>{
	
	/**
	 * 查詢用戶是否有參加資格
	 * @return
	 */
	public Long  getUserQuanlification(ActivitySignUp actSignUpVO);
//queryUser
}
