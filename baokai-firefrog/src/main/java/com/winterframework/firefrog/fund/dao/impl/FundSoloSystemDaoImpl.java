package com.winterframework.firefrog.fund.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundSoloSystemDao;
import com.winterframework.firefrog.fund.dao.vo.FundSoloRemarkSystemVO;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: IFundSoloSystemDao 
* @Description: 系统附言
* @author 你的名字 
* @date 2014-3-11 下午1:21:43 
*  
*/
@Repository("fundSoloSystemDao")
public class FundSoloSystemDaoImpl extends BaseIbatis3Dao<FundSoloRemarkSystemVO> implements IFundSoloSystemDao {

	@Override
	public String getNextSystemRemark() throws Exception {
		Long seqNo = this.sqlSessionTemplate.selectOne(getQueryPath("getNextSeq"));
		FundSoloRemarkSystemVO result = this.sqlSessionTemplate.selectOne(getQueryPath("getNextSystemRemark"), seqNo);
		return result.getRemark();
	}
}
