package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameManualRecord;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IGameManualRecordDao 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午3:06:42 
*  
*/
public interface IGameManualRecordDao extends BaseDao<GameManualRecord>{
	
	/** 
	* @Title: getGameManualRecordsByLottery 
	* @Description: 根据彩种获取历史手工录号
	* @param lotteryId
	* @return
	*/
	Page<GameManualRecord> getGameManualRecordsByLottery(PageRequest<GameManualRecordRequest> pr);
	
	/** 
	* @Title: getGameManualRecordByIssue 
	* @Description: 根据期号获取手工录号记录
	* @param issueCode
	* @return
	*/
	GameManualRecord getGameManualRecordByIssue(Long issueCode);
	
}
