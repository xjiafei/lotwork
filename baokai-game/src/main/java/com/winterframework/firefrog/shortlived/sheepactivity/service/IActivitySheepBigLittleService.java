package com.winterframework.firefrog.shortlived.sheepactivity.service;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.BigLittleAward;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IActivitySheepBigLittleService 
* @Description 羊年活动猜大小
* @author  hugh
* @date 2015年1月14日 下午4:00:34 
*  
*/
public interface IActivitySheepBigLittleService {
	Page<ActivitySheepBigLittle> queryPage(PageRequest<ActivitySheepBigLittle> pr) ;
	void update(ActivitySheepBigLittle activity) throws Exception;
	void updateEntityByType(ActivitySheepBigLittle activity) throws Exception;
	ActivitySheepBigLittle getUserDice(Long userId);
	void initUserDice(Long userId, String userName,Long channel);
	
	/** 
	* @Title: getAward 
	* @Description: 开奖
	* @param userId
	* @param isGuessLittle
	* @param channel
	* @return
	* @throws Exception
	*/
	BigLittleAward getAward(Long userId,boolean isGuessLittle,Long channel) throws Exception;
	void initUserDice(Long userId, Long times, Long amount,Long channel);
	void addUserDiceLastNum(Long userId, Long times, Long long1);
}
