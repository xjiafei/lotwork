package com.winterframework.firefrog.global.service;

import java.util.List;

import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;

/**
 * 
* @ClassName: GlobalGrayListService 
* @Description: 灰名單接口
* @date 2016-07-06 下午4:53:24 
*
 */
public interface IGlobalGrayListService {
	
	/**
	 * 灰名單查詢 By userId
	 * @return
	 * @throws Exception
	 */
	public GlobalGrayListVO queryGlobalGrayListByUserId(Long userId) throws Exception;

	/**
	 * 是否為灰名單
	 * @param globalGrayListVO
	 * @return
	 * @throws Exception
	 */
	public Long isGlobalGrayList(Long userId) throws Exception;
	
	
	/**
	 * 新增灰名單
	 * @param globalGrayListVO
	 * @return
	 */
	public void saveGlobalGrayList(Long rcvUserId,Long userId);
	
	/**
	 * 刪除灰名單
	 * @param userId
	 */
	public void deleteGlobalGrayList(Long userId);
	
	/**
	 * 更新灰名單
	 */
	public void updateGlobalGrayList(GlobalGrayListVO globalGrayListVO);
}
