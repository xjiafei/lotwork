package com.winterframework.firefrog.global.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListIpVO;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalWhiteListIpDao 
* @Description: 指定IP白名單数据库操作接口
* @author David Wu 
* @date 2016-05-19 下午4:40:00 
*
 */
public interface GlobalWhiteListIpDao {

	/**
	 * 指定IP白名單 : 單筆查詢
	 * @param globalWhiteListIpVO
	 * @return globalWhiteListIpVO
	 */
	public GlobalWhiteListIpVO queryGlobalWhiteListIpById(GlobalWhiteListIpVO globalWhiteListIpVO);
	/**
	 * 指定IP白名單 : 分頁查詢
	 * @param page
	 * @param filters
	 * @param rowBounds
	 * @return List<GlobalWhiteListIpVO>
	 * @throws Exception
	 */
	public List<GlobalWhiteListIpVO> queryGlobalWhiteListIp(
			Page<GlobalWhiteListIp> page, Map<String, Object> filters,
			RowBounds rowBounds)throws Exception;
	
	/**
	 * 指定IP白名單 : 刪除
	 * 假性刪除，僅更改 status
	 * @param gobalWhiteListVO
	 * @throws Exception 
	 */
	public void deleteGlobalWhiteListIp(GlobalWhiteListIpVO whiteListIpVO);
	
	/**
	 * 指定IP白名單 : 新增
	 * 有錯誤全部回朔
	 * @param gobalWhiteListVO
	 */
	public void saveGlobalWhiteListIp(GlobalWhiteListIpVO globalWhiteListIpVO);
	/**
	 * 指定IP白名單 : 更新
	 * 有錯誤全部回朔
	 * @param gobalWhiteListVO
	 */
	public void updateGlobalWhiteListIp(GlobalWhiteListIpVO globalWhiteListIpVO);
	
	/**
	 * 指定IP白名單 : 計算數量
	 * @param globalWhiteListIpRequest
	 * @return
	 * @throws Exception 
	 */
	public Long countWhiteListData(GlobalWhiteListIpVO globalWhiteListIpVO) throws Exception;
	
	/**
	 * 指定IP白名單 : 依據user Accunt 取得限制的IP清單
	 * @param userAccunt
	 * @return
	 */
	public List<GlobalWhiteListIpVO> getIpListByUserAccunt(String userAccunt);
			
}
