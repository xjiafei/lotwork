package com.winterframework.firefrog.shortlived.sheepactivity.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepHongBaoDao 
* @Description 羊年活动红包 
* @author  hugh
* @date 2015年1月14日 下午3:28:47 
*  
*/
public interface IActivitySheepHongBaoDao extends BaseDao<ActivitySheepHongBao>{
	Long getUncheckNum();
	List<ActivitySheepHongBao> getUserHongBaoList(Long userId);

	void applyUserHongbao(Map<String, Object> map);

	Long getUserValideBetAmount(Map<String, Object> map);

	void abortUserHongBao(Map<String, Object> map);

	void openUserNextHongBao(Map<String, Object> map);

	void drawUserHongBao(Map<String, Object> map);

	ActivitySheepHongBao getUserHongBaoInfo(Map<String, Object> map);

	List<ActivitySheepHongBaoCount> getCounts(Date beginDate,Date endDate,boolean isGroupByChannel);
	ActivitySheepHongBao getUserValidHongBaoInfo(Long userId); 
}
