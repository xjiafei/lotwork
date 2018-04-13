package com.winterframework.firefrog.fund.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.winterframework.firefrog.fund.dao.IFundChargeBypassDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.service.IFundChargeBypassService;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("fundChargeBypassServiceImpl")
public class FundChargeBypassServiceImpl implements IFundChargeBypassService {
	private static Logger log = LoggerFactory.getLogger(FundChargeBypassServiceImpl.class);
	private final int  pageRowSize=20;
	@Autowired
	private IFundChargeBypassDao fundChargeBypassDaoImpl;
	
	@Override
	public Page<FundChargeBypassVO> getAllByPage(PageRequest<FundChargeBypassRequest> pageRequest) throws Exception {
		//查詢時將flag壓掉
		//fundChargeBypassDaoImpl.updateDeleteFlag(null, "N");
		Page<FundChargeBypassVO> page= fundChargeBypassDaoImpl.selectByPage(pageRequest);
		int totalCount = page.getTotalCount();
		int startNo = totalCount-(page.getPageNo()-1)*pageRowSize;
		if(page.getResult()!=null){
			List<FundChargeBypassVO> lists = Lists.newArrayList();
			for(FundChargeBypassVO que:page.getResult()){
				lists.add(que);
			}
			page.setResult(lists);
		}
		
		return page;
	}
	
	@Override
	public void deleteById(Long deleteI) {
		//畫面點擊刪除時,將flag壓成y
		fundChargeBypassDaoImpl.deleteById(deleteI);
	}
	
	@Override
	public void saveWhiteList(FundChargeBypassVO vo) throws Exception{
		fundChargeBypassDaoImpl.saveWhiteList(vo);
	}
	
	@Override
	public String isAccountExist(String account,Long chargeWaySet) throws Exception{
		String isExist = "N";
		try {
			FundChargeBypassVO result = fundChargeBypassDaoImpl.searchByAccount(account,chargeWaySet);
			if(result != null){
				isExist = "Y";
			} 
		} catch(Exception e) {
			isExist = "Y";
		}
		return isExist;
	}
	
	
	
	
 
}
