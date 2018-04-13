package com.winterframework.firefrog.shortlived.sheepactivity.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepHongBaoCount;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IActivitySheepHongBaoService 
* @Description 羊年活动红包 
* @author  hugh
* @date 2015年1月14日 下午4:00:34 
*  
*/
public interface IActivitySheepHongBaoService {
	Page<ActivitySheepHongBao> queryPage(PageRequest<ActivitySheepHongBao> pr) ;
	void update(ActivitySheepHongBao activity) throws Exception;
	void updateEntityByType(ActivitySheepHongBao activity) throws Exception;
	Long getUncheckNum();
	List<ActivitySheepHongBao> getUserHongBaoList(Long userId);
	void initUserHongbao(List<ActivitySheepHongBao> list);
	void applyUserHongBao(ActivitySheepUserApplyHongBaoRequest param);
	Long getUserValideBetAmount(Date applyDate, Long userId);
	void abortUserHongBao(Long userId, Long index);
	void drawUserHongBao(Long userId, Long index);
	ActivitySheepHongBao getUserHongBaoInfo(Long userId, Long index);
	List<ActivitySheepHongBaoCount> getCounts(String date) throws Exception;
	ActivitySheepHongBao getUserValidHongBaoInfo(Long userId);
}
