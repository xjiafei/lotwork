package com.winterframework.firefrog.global.dao;

import java.util.List;

import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListIpVO;
import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListLogVO;
import com.winterframework.firefrog.global.entity.GlobalWhiteListLog;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GlobalWhiteListIpDao 
* @Description: 指定IP白名單操作歷史紀錄数据库操作接口
* @author David Wu 
* @date 2016-05-19 下午4:40:00 
*
 */
public interface GlobalWhiteListLogDao {

	/**
	 * 指定IP白名單操作歷史紀錄 : 查詢
	 * 每次僅取出 十筆紀錄
	 * @param gobalWhiteListLogVO
	 * @throws Exception 
	 */
	public List<GlobalWhiteListLogVO> queryGlobalWhiteListLog(GlobalWhiteListLogVO gobalWhiteListLogVO) throws Exception;
		
	/**
	 * 指定IP白名單操作歷史紀錄 : 存檔
	 * @param globalWhiteListLogVoList
	 */
	public void saveGlobalWhiteListLog(GlobalWhiteListLogVO globalWhiteListLogVO);
}
