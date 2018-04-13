/**   
* @Title: IFundTransferOrderService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-6-28 下午5:48:07 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.FundTransferDetailResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferInitResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequest;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;

/** 
* @ClassName: IFundTransferOrderService 
* @Description: 转账处理service 
* @author david
* @date 2013-6-28 下午5:48:07 
*  
*/
public interface IFundTransferService {

	/** 
	* @Title: saveFundTransferOrder 
	* @Description:  保存转账记录
	* @param fundTransferOrder
	 * @throws Exception 
	*/
	public void transferFund(FundTransferOrder fundTransferOrder) throws Exception;


	/** 
	* @Title: saveFundTransferOrder 
	* @Description:  保存转账记录
	* @param fundTransferOrder
	 * @throws Exception 
	 * @return balance
	*/
	public long transferSubsystemFund(FundTransferOrder fundTransferOrder, int direct, String token) throws Exception;
	
	/** 
	* @Title: saveFundTransferOrder 
	* @Description:  保存转账记录
	* @param fundTransferOrder
	 * @throws Exception 
	 * @return balance
	*/
	public long transferSubsystemFundMW(FundTransferOrder fundTransferOrder, int direct, String token) throws Exception;
	
	/** 
	* @Title: transferSubsystemGiftMoneyMW 
	* @Description:  子系統活動禮金
	* @param fundTransferOrder
	 * @throws Exception 
	 * @return sn
	*/
	public String transferSubsystemGiftMoneyMW (FundTransferOrder fundTransferOrder, int direct, String token) throws Exception ;
	/** 
	* @Title: searchUserAppeal 
	* @Description:查询转账记录
	* @param pageReqeust
	* @return
	* @throws Exception
	*/
	public CountPage<FundTransferOrder> searchFundTransferOrder(PageRequest<FundTransferRecordQueryDTO> pageReqeust)
			throws Exception;
	
	/**
	 * 
	* @Title: init 
	* @Description: 转账初始化
	* @param userId
	* @return
	* @throws Exception
	 */
	public FundTransferInitResponse init(long userId) throws Exception;
	
	Page<FundTransferDetailResponse> userFundTransfer(Request<FundTransferRequest> request) throws Exception;
	
	public void SendMsg ( Long Amount,  BaseUser baseUser) throws Exception;
}
