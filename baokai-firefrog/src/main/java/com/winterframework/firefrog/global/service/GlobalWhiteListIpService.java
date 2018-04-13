package com.winterframework.firefrog.global.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.global.dao.vo.GlobalWhiteListLogVO;
import com.winterframework.firefrog.global.entity.GlobalWhiteListIp;
import com.winterframework.firefrog.global.entity.GlobalWhiteListLog;
import com.winterframework.firefrog.global.web.dto.GlobalIpDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalSensitiveWordQueryRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpDelRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpRequest;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListIpStruc;
import com.winterframework.firefrog.global.web.dto.GlobalWhiteListLogStruc;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;

/**
 * 
* @ClassName: GlobalWhiteListIpService 
* @Description: 指定IP白名單业务接口
* @author David Wu
* @date 2016-05-20 下午4:53:24 
*
 */
public interface GlobalWhiteListIpService {
	
	/**
	 * 指定IP白名單 : 查詢歷史操作紀錄
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<GlobalWhiteListLogVO> queryGlobalWhiteListLog(PageRequest<GlobalWhiteListIpRequest> pageRequest) throws Exception;
	/**
	 * 指定IP白名單 : 查詢指定IP白名單資料表並回傳列表
	 * @param globalWhiteListIpRequest
	 * @return
	 * @throws Exception
	 */
	public Page<GlobalWhiteListIp> queryGlobalWhiteListIp(PageRequest<GlobalWhiteListIpRequest> pageRequest) throws Exception;
	public GlobalWhiteListIpStruc queryGlobalWhiteListIpById(GlobalWhiteListIpRequest pageRequest) throws Exception;
	/**
	 * 指定IP白名單 : IP & Acunt 存檔與更新並回傳列表
	 *  点击“提交”按钮，新增成功页面跳转至指定ip列表页
	 *  需紀錄log
	 * @param globalWhiteListIpRequest
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> saveAndUpdateGlobalWhiteListIp(GlobalWhiteListIpRequest globalWhiteListIpRequest) throws Exception;
			
	/**
	 * 指定IP白名單 : 刪除IP
	 * 需紀錄log
	 * @param globalWhiteListIpRequest
	 * @return
	 * @throws Exception
	 */
	public void deleteGlobalWhiteListIp(GlobalWhiteListIpRequest request) throws Exception;
	
	/**
	 * 指定IP白名單 : 查詢國家
	 * @param ipAddr
	 * @return CountryName
	 * @throws Exception 
	 */
	public String queryCountryMapIP(String ipAddr);
	
	
	/**
	 * 指定IP白名單 : 更新用戶IP到session
	 * @param globalWhiteListIpRequest
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserCurrentIP(List<GlobalWhiteListIpRequest> requestList) ;
	/**
	 * 指定IP白名單 : 檢查用戶是否為限制IP對象 true/false
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserPrivileges(GlobalWhiteListIpRequest globalWhiteListIpRequest) throws Exception;

	/**
	 * 指定IP白名單 : 檢查用戶IP是否為指定IP true/false
	 * @param longToIp 登入IP
	 * @param userName 用戶帳號
	 * @return true/false
	 */
	public String getUserIpPrivileges(String longToIp, String userName);
	
	/**
	 * 指定IP白名單 : 檢查是否已有紀錄
	 * @param globalWhiteListIpRequest
	 * @return true/false
	 * @throws Exception
	 */
	public boolean checkUserIpExist(GlobalWhiteListIpRequest globalWhiteListIpRequest) throws Exception;
	
	/**
	 * 依據帳號取得使用者資料
	 * @param globalWhiteListIpRequest
	 * @return
	 */
	public User checkAccuntExist(String accunt);
	
}
