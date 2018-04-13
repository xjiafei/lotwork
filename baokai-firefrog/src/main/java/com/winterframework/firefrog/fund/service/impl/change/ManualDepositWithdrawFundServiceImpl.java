/**   
* @Title: ManualDepositWithdrawFundServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl.change 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-19 上午9:42:31 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.change;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.user.entity.User;

/** 
* @ClassName: DepositWithdrawFundServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-19 上午9:42:31 
*  
*/
@Service("depositWithdrawChangeService")
@Transactional(rollbackFor = Exception.class)
public class ManualDepositWithdrawFundServiceImpl extends AbstractFundService {
	@Override
	public void updateFund(FundOrder fundOrder) throws FundChangedException {

		if (fundOrder == null || !(fundOrder instanceof FundManualOrder)) {
			return;
		}

		switch (((FundManualOrder) fundOrder).getStatus().intValue()) {

		//0:未处理,1:处理中,2:已拒绝,3:扣币成功,4:加币成功,5:打款完全成功,6:打款部分成功,7:打款失败
		case 1:
			auditPassWithdraw(fundOrder);
			break;
		case 2:
			rejectWithdraw(fundOrder);
			break;
		case 7:
			rejectWithdraw(fundOrder);
			break;
		case 6:
			Incomplete(fundOrder);
			break;
		case 5:
			mocSuccessful(fundOrder);
		default:
			break;
		}
	}

	private void auditPassWithdraw(FundOrder fundOrder) throws FundChangedException {

		User user = new User();
		user.setId(fundOrder.getApplyUser().getId());
		fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PROCESSING,fundOrder.getApplyUser().getId(),((FundManualOrder) fundOrder).getDepositAmt(),fundOrder.getSn(),true));

	}

	private void rejectWithdraw(FundOrder fundOrder) throws FundChangedException {
		fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.REJECTED,fundOrder.getApplyUser().getId(),((FundManualOrder) fundOrder).getDepositAmt(),fundOrder.getSn(),true));


	}

	private void mocSuccessful(FundOrder fundOrder) throws FundChangedException {
		fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.SUCCESS,fundOrder.getApplyUser().getId(),((FundManualOrder) fundOrder).getDepositAmt(),fundOrder.getSn(),true));
	}

	private void Incomplete(FundOrder fundOrder) throws FundChangedException {
		User user = new User();
		user.setId(fundOrder.getApplyUser().getId());
		fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PART_REDUCE,user.getId(),((FundManualOrder) fundOrder).getRealDepositAmt(),fundOrder.getSn(),true));

		fundChangeService.action(new FundGameVo(FundModel.FD.MWXX.ITEMS.PART_RETURN,user.getId(),((FundManualOrder) fundOrder).getDepositAmt()-((FundManualOrder) fundOrder).getRealDepositAmt(),fundOrder.getSn(),true));

	
	}
}
