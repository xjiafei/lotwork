package com.winterframework.firefrog.fund.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundSoloManaulDao;
import com.winterframework.firefrog.fund.dao.vo.FundSoloRemarkManualVO;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundSoloRemarkManual;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: IFundSoloManaulDao 
* @Description: 手工附言
* @author 你的名字 
* @date 2014-3-11 下午1:21:38 
*  
*/
@Repository("fundSoloManaulDaoImpl")
public class FundSoloManaulDaoImpl extends BaseIbatis3Dao<FundSoloRemarkManualVO> implements IFundSoloManaulDao {

	/**
	* Title: getFundSoloManaulByRemark
	* Description:
	* @param remark
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundSoloManaulDao#getFundSoloManaulByRemark(java.lang.String) 
	*/
	@Override
	public FundSoloRemarkManual getFundSoloManaulByRemark(String remark) throws Exception {
		FundSoloRemarkManualVO vo = this.sqlSessionTemplate
				.selectOne(getQueryPath("getFundSoloManaulByRemark"), remark);
		if (vo != null) {
			return VOConverter.transSoloManualVOToEntity(vo);
		} else {
			return null;
		}
	}

	/**
	* Title: saveSoloManual
	* Description:
	* @param manual 
	* @see com.winterframework.firefrog.fund.dao.IFundSoloManaulDao#saveSoloManual(com.winterframework.firefrog.fund.entity.FundSoloRemarkManual) 
	*/
	public void saveSoloManual(FundSoloRemarkManual manual) {
		FundSoloRemarkManualVO vo = VOConverter.transSoloManualEntityToVO(manual);
		insert(vo);
	}

	/**
	* Title: updateSoleManual
	* Description:
	* @param manual 
	* @see com.winterframework.firefrog.fund.dao.IFundSoloManaulDao#updateSoleManual(com.winterframework.firefrog.fund.entity.FundSoloRemarkManual) 
	*/
	@Override
	public void updateSoleManual(FundSoloRemarkManual manual) {
		FundSoloRemarkManualVO vo = VOConverter.transSoloManualEntityToVO(manual);
		update(vo);
	}
	
	public void deleteSoleManual(String manual){
		sqlSessionTemplate.delete(this.getQueryPath("delete"),manual);
	}
}
