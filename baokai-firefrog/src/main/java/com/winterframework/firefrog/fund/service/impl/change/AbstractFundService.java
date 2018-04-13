package com.winterframework.firefrog.fund.service.impl.change;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.fund.web.dto.RtnStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;


public abstract class AbstractFundService<T extends FundOrder> implements IFundService<T> {

	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;
	
	@Resource(name = "fundDaoImpl")
	protected IFundDao fundDao;
	
	@Resource(name = "fundBankDaoImpl")
	protected IFundBankDao fundBankDao;
	@PropertyConfig(value = "mownum_url")
	protected String mowUrl;
	
	@PropertyConfig(value = "thpay_url")
	protected String thUrl;
	
		

	public IFundAtomicOperationService getFundChangeService() {
		return fundChangeService;
	}

	public void setFundChangeService(IFundAtomicOperationService fundChangeService) {
		this.fundChangeService = fundChangeService;
	}

	@Override
	public UserFund getUserFund(Long userId) {
		return fundChangeService.getUserFund(userId);
	}
	//获取充值时附加手续费的金额
		protected Long getRealAmtWithFee(Long bankId,Long realCargeAmt,Long bankFee) throws Exception {
			if(bankId==null){
				// 如果没有银行id，一律按照招行处理
				bankId=2L;}
			FundBank payBank = this.fundBankDao.queryById(bankId);
			if (payBank.getRtnSet() != null) {
				//mownecum返回的手续费 
				if (payBank.getRtnSet().longValue() == 1l) {
					return  bankFee;
				} else {//自己设置的手续费
					List<RtnStruc> rtnStrucArray = payBank.getRtnStruc();
					if (rtnStrucArray != null) {
						Long rtnSm = Long.valueOf(payBank.getRtnMin());
						if (realCargeAmt >= rtnSm) {//开始返手续费值
							for (RtnStruc rtnStruc : rtnStrucArray) {
								long big = rtnStruc.getBig() == null ? Long.MAX_VALUE : rtnStruc.getBig();
								if (realCargeAmt >= rtnStruc.getSm() && realCargeAmt < big) {
									if (rtnStruc.getType() == 2l) {//返回类型为百分比
										return  BigDecimal.valueOf(realCargeAmt).multiply( rtnStruc.getValue()).divide(BigDecimal.valueOf(100)).longValue();
									} else if (rtnStruc.getType() == 1l) {//返回类型为固值
										return rtnStruc.getValue().longValue();
									}
								}
							}
						}
					}
				}
			}
			return 0L;
		}

	
	public Long getTeamFund(Long userId){
		return fundDao.getTeamFund(userId);
	};
	/** 
	* @Title: updateFund 
	* @Description: 更改用户资金信息 
	* @param fundOrder
	* @throws FundChangedExcetion
	*/

}