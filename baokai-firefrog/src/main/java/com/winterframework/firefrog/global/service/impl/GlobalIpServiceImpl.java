package com.winterframework.firefrog.global.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.global.dao.GlobalIpDao;
import com.winterframework.firefrog.global.entity.IPAddress;
import com.winterframework.firefrog.global.service.GlobalIpService;
import com.winterframework.firefrog.global.web.dto.IpSearchDto;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("globalIpServiceImpl")
public class GlobalIpServiceImpl implements GlobalIpService {

	private static final String BACKLIST_TYPE = "0";
	private static final String WHITELIST_TYPE = "1";
	private static final String ALLEQUALS = "*";

	@Resource(name = "globalIpDaoImpl")
	private GlobalIpDao globalIpDao;

	@Override
	public void saveIp(IPAddress ip, int period) throws Exception {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, period);
		ip.setEffectDate(now);
		ip.setExpireDate(cal.getTime());

		IPSeeker ipSeeker = IPSeeker.getInstance();
		String area = ipSeeker.getArea(ip.getIp());
		ip.setArea(area);
		globalIpDao.saveIp(ip);
	}

	@Override
	public void deleteIp(List<Long> ids) throws Exception {
		for (Long id : ids) {
			globalIpDao.deleteIp(id);
		}
	}

	@Override
	public Page<IPAddress> queryList(PageRequest<IpSearchDto> pageRequest) throws Exception {
		return globalIpDao.selectList(pageRequest);
	}

	/**
	* Title: isIpLimit
	* Description:
	* @param ip
	* @param limitType
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.global.service.GlobalIpService#isIpLimit(java.lang.String, java.lang.String) 
	*/
	public IPAddress getSameIpLimit(String ip, String limitType) throws Exception {
		List<IPAddress> ipAddressList = globalIpDao.queryEffectIpList(limitType);
		for (IPAddress ipAddress : ipAddressList) {
			if (isEqualsIp(ip, ipAddress.getIp())) {
				return ipAddress;
			}
		}
		return null;
	}

	/**
	* Title: isIpLimit
	* Description:
	* @param ip
	* @param limitType
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.global.service.GlobalIpService#isIpLimit(java.lang.String, java.lang.String) 
	*/
	@Override
	public Boolean isIpLimit(String ip, String limitType) throws Exception {
		List<IPAddress> ipAddressList = globalIpDao.queryEffectIpList(limitType);
		if (BACKLIST_TYPE.equals(limitType)) {
			for (IPAddress ipAddress : ipAddressList) {
				if (isEqualsIp(ip, ipAddress.getIp())) {
					return true;
				}
			}
		} else if (WHITELIST_TYPE.equals(limitType)) {
			for (IPAddress ipAddress : ipAddressList) {
				if (isEqualsIp(ip, ipAddress.getIp())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private boolean isEqualsIp(String sourceIp, String targetIp) {
		String[] sourceList = sourceIp.split("\\.");
		String[] targetList = targetIp.split("\\.");
		for (int i = 0; i < targetList.length; i++) {
			if (ALLEQUALS.equals(targetList[i]) || ALLEQUALS.equals(sourceList[i])) {
				continue;
			} else if (targetList[i].equals(sourceList[i])) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}
