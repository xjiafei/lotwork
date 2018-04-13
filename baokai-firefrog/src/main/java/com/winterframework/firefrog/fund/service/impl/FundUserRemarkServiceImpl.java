/**   
* @Title: FundUserRemarkServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 下午1:45:34 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.dao.IFundSoloManaulDao;
import com.winterframework.firefrog.fund.dao.IFundSoloSystemDao;
import com.winterframework.firefrog.fund.dao.IFundUserRecyleDao;
import com.winterframework.firefrog.fund.dao.IFundUserRemarkDao;
import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkRecyleExtVO;
import com.winterframework.firefrog.fund.entity.FundSoloRemarkManual;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.firefrog.fund.service.IFundUserRemarkService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: FundUserRemarkServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午1:45:34 
*  
*/
@Service("fundUserRemarkService")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class FundUserRemarkServiceImpl implements IFundUserRemarkService {

	@Resource(name = "fundSoloManaulDaoImpl")
	private IFundSoloManaulDao fundSoloManaulDaoImpl;

	@Resource(name = "fundSoloSystemDao")
	private IFundSoloSystemDao fundSoloSystemDao;

	@Resource(name = "fundUserRecyleDaoImpl")
	private IFundUserRecyleDao fundUserRecyleDaoImpl;

	@Resource(name = "fundUserRemarkDaoImpl")
	private IFundUserRemarkDao fundUserRemarkDaoImpl;

	@Resource(name = "configServiceImpl")
	private IConfigService configServiceImpl;

	@PropertyConfig(value = "fund_remark_canModifyDays")
	private String canModifyDays;
	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	/**
	* Title: checkRemarkExist
	* Description:
	* @param remark
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#checkRemarkExist(java.lang.String) 
	*/
	@Override
	public Boolean checkRemarkExist(String remark) throws Exception {
		FundSoloRemarkManual fundSoloRemarkManual = fundSoloManaulDaoImpl.getFundSoloManaulByRemark(remark);
		if (fundSoloRemarkManual == null || fundSoloRemarkManual.getIsused() == 0) {
			return false;
		}
		return true;
	}

	/**
	* Title: importUser
	* Description:
	* @param userId
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#importUser(java.util.List) 
	*/
	@Override
	public void importUser(List<Long> userId) throws Exception {
		fundUserRemarkDaoImpl.batchSaveRemark(userId);
	}

	/**
	* Title: getAllRecyleRemarks
	* Description:
	* @param search
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#getAllRecyleRemarks(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<FundUserRemarkRecyleExtVO> getAllRecyleRemarks(PageRequest<FundUserRemarkRecyle> search) throws Exception {
		return fundUserRecyleDaoImpl.getAllRecyleRemarks(search);
	}

	/**
	* Title: getAllRemarks
	* Description:
	* @param search
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#getAllRemarks(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<FundUserRemark> getAllRemarks(PageRequest<FundUserRemark> search) throws Exception {
		return fundUserRemarkDaoImpl.getPageRemarks(search);
	}

	/**
	* Title: getModifiedDays
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#getModifiedDays() 
	*/
	@Override
	public String getModifiedDays() throws Exception {
		return configServiceImpl.getConfigValueByKey("fund", canModifyDays);
	}

	/**
	* Title: getNextSystemRemark
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#getNextSystemRemark() 
	*/
	@Override
	public String getNextSystemRemark() throws Exception {
		return fundSoloSystemDao.getNextSystemRemark();
	}

	/**
	* Title: saveRemark
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#saveRemark() 
	*/
	@Override
	public Boolean saveRemark(String remark, Long userId) throws Exception {
		//系统生成的是5位，用户自定义的是6位，6位的才做唯一性校验
		if (remark.length() >= 6) {
			FundSoloRemarkManual fundSoloRemarkManual = fundSoloManaulDaoImpl.getFundSoloManaulByRemark(remark);
			if (fundSoloRemarkManual == null) {
				fundSoloRemarkManual = new FundSoloRemarkManual();
				fundSoloRemarkManual.setIsused(1);
				fundSoloRemarkManual.setRemark(remark);
				fundSoloManaulDaoImpl.saveSoloManual(fundSoloRemarkManual);
			} else {
				fundSoloRemarkManual.setIsused(1);
				fundSoloManaulDaoImpl.updateSoleManual(fundSoloRemarkManual);
			}
		}
		FundUserRemark fundUserRemark = fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
		if(fundUserRemark==null){
			//如果不能设置，则导入一条
			List<Long> list=new ArrayList<Long>();
			list.add(userId);
			this.importUser(list);
			 fundUserRemark = fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
		}
		Integer day = 30;
		try {
			String days = configService.getConfigValueByKey("fund", "remark_canModifyDays");
			if (days != null) {
				day = Integer.valueOf(days);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fundUserRemark.setGmtCansetremark(DateUtils.addDays(new Date(), day));
		fundUserRemark.setRemark(remark);
		fundUserRemark.setGmtModified(new Date());
		fundUserRemarkDaoImpl.updateUserRemark(fundUserRemark);
		return true;
	}

	/**
	* Title: saveRemark
	* Description:
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#saveRemark() 
	*/
	@Override
	public Boolean updateRemark(String remark, Long userId) throws Exception {
		FundUserRemark fundUserRemark = fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
		if (fundUserRemark.getRemark().length() >= 6) {
			FundUserRemarkRecyle fundUserRemarkRecyle = new FundUserRemarkRecyle();
			fundUserRemarkRecyle.setUserId(userId);
			fundUserRemarkRecyle.setGmtCreated(new Date());
			fundUserRemarkRecyle.setRemark(fundUserRemark.getRemark());
			fundUserRecyleDaoImpl.saveFundUserRecyle(fundUserRemarkRecyle);
		}
		saveRemark(remark, userId);
		return true;
	}

	/**
	* Title: setCanModifiedDays
	* Description:
	* @param days
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#setCanModifiedDays(java.lang.Long) 
	*/
	@Override
	public void setCanModifiedDays(Long days) throws Exception {
		configServiceImpl.saveConfig("fund", canModifyDays, String.valueOf(days));
	}

	/**
	* Title: cancelLock
	* Description:
	* @param userId
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#cancelLock(java.lang.Long) 
	*/
	@Override
	public void cancelLock(Long userId) throws Exception {
		FundUserRemark fundUserRemark = fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
		fundUserRemark.setGmtCansetremark(new Date());
		fundUserRemarkDaoImpl.updateUserRemark(fundUserRemark);
	}

	/**
	* Title: cancelRemark
	* Description:
	* @param userId
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundUserRemarkService#cancelRemark(java.lang.Long) 
	*/
	@Override
	public void cancelRemark(Long userId) throws Exception {
		FundUserRemark fundUserRemark = fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
		if (fundUserRemark.getRemark() == null)
			return;
		if (fundUserRemark.getRemark().length() >= 6) {
			FundUserRemarkRecyle fundUserRemarkRecyle = new FundUserRemarkRecyle();
			fundUserRemarkRecyle.setUserId(userId);
			fundUserRemarkRecyle.setGmtCreated(new Date());
			fundUserRemarkRecyle.setRemark(fundUserRemark.getRemark());
			fundUserRecyleDaoImpl.saveFundUserRecyle(fundUserRemarkRecyle);
		}
		Integer day = 30;
		try {
			String days = configService.getConfigValueByKey("fund", "remark_canModifyDays");
			if (days != null) {
				day = Integer.valueOf(days);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fundUserRemark.setGmtCansetremark(DateUtils.addDays(new Date(), day));
		fundUserRemark.setRemark(null);
		fundUserRemark.setGmtModified(new Date());
		fundUserRemarkDaoImpl.updateUserRemark(fundUserRemark);
	}

	@Override
	public FundUserRemark getRemarkByUserId(Long userId) throws Exception {
		return fundUserRemarkDaoImpl.getUserRemarkByUserId(userId);
	}

	@Override
	public void recyleRemark(Long id,String remark) throws Exception {
		fundUserRecyleDaoImpl.deleteFundUserRecyle(id);
		fundSoloManaulDaoImpl.deleteSoleManual(remark);

	}

}
