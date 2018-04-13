package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundTransferView;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.FundTransferRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequest;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundDao 
* @Description: 用户转账功能数据访问层 
* @author david
* @date 2013-6-28 下午4:10:25 
*  
*/
public interface IFundTransferDao {
	/** 
	* @Title: saveUserFundTransfer 
	* @Description: 保存用户转账 
	* @param fundTransferOrder
	*/
	public void saveUserFundTransfer(FundTransferOrder fundTransferOrder);

	public CountPage<FundTransferOrder> searchFundTransferOrder(PageRequest<FundTransferRecordQueryDTO> pageReqeust)
			throws Exception;
	
	List<FundTransferView> queryViewFundTrasfer(FundTransferRequest request);
}
