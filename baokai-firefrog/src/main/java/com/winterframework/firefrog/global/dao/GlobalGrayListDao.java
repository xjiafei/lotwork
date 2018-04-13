package com.winterframework.firefrog.global.dao;

import java.util.List;

import com.winterframework.firefrog.global.dao.vo.GlobalGrayListTestVO;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;

/**
 * 
 * @ClassName: GlobalGrayListDao
 * @Description: 灰名單處理 
 * @author 
 * @date 2016-07-06 下午4:40:00
 *
 */
public interface GlobalGrayListDao {

	/**
	 * 取得灰名單資料
	 * @param gobalWhiteListLogVO
	 * @throws Exception
	 */
	public GlobalGrayListVO queryGlobalGrayListByUserId(
			Long userId) throws Exception;

	/**
	 * 新增灰名單
	 * @param globalWhiteListLogVoList
	 */
	public void saveGlobalGrayList(Long userId,Long riskType);
	
	/**
	 * 更新風險狀態
	 * @param userId
	 * @param riskType
	 */
	public void updateGlobalGrayList(GlobalGrayListVO grayListVO); 
	
	/**
	 * 刪除灰名單
	 * @param userId
	 */
	public void deleteGlobalGrayList(Long userId); 
	
	
	public List<GlobalGrayListTestVO> queryGlobalGrayListByUserAccount(
			String account);
	public List<GlobalGrayListTestVO> queryGlobalGrayListByUserAccount2(
			String account);
}
