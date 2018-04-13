package com.winterframework.firefrog.beginmession.web.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.beginmession.entity.BeginBankCardCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.web.BeginBankCardCheckController;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;

public class DTOConverter {
	private static final Logger log = LoggerFactory.getLogger(BeginBankCardCheckController.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static BeginCheckData transCheckData2BeginCheckData(
			BeginCheckDataRequest bcDataReq) {
		BeginCheckData bcData = new BeginCheckData();
		bcData.setStatus(bcDataReq.getStatus());
		bcData.setPageNo(bcDataReq.getPageNo());
		bcData.setTimeStart(bcDataReq.getTimeStart() != null ? new Date(
				bcDataReq.getTimeStart()) : DateUtils.getStartTimeOfDate(new Date()));
		bcData.setTimeEnd(bcDataReq.getTimeEnd() != null ? new Date(bcDataReq
				.getTimeEnd()) : DateUtils.getEndTimeOfDate(new Date()));
		return bcData;
	}

	public static List<BeginBankCardCheckData> transBeginCheckData2BeginSearchCheckData(
			List<BeginSearchCheckData> list) throws Exception{
		List<BeginBankCardCheckData> datas = new ArrayList<BeginBankCardCheckData>();
		
		try {
			if(list != null){
				for (BeginSearchCheckData info : list) {
					BeginBankCardCheckData data = new BeginBankCardCheckData();
					
					data.setId(info.getId());
					data.setUserId(info.getUserId());
					if(info.getCreateTime() != null)
						data.setCreateTime(SDF.format(info.getCreateTime()));
					if(info.getCheckTime() != null)
						data.setCheckTime(SDF.format(info.getCheckTime()));
					data.setCheckStatus(info.getCheckStatus());
					data.setCheckUser(info.getCheckUser());
					data.setBankNum(info.getBankNum());
					data.setAccountName(info.getAccountName());
					data.setCity(info.getCity());
					if(info.getChargeTime() != null)
						data.setChargeTime(SDF.format(info.getChargeTime()));
					if(info.getChargeAmt() != null)
						data.setChargeAmt(info.getChargeAmt()/10000);
					if(info.getWithdrawTime() != null)
						data.setWithdrawTime(SDF.format(info.getWithdrawTime()));
					if(info.getWithdrawAmt() != null)
						data.setWithdrawAmt(info.getWithdrawAmt()/10000);
					if(info.getTotAmount() != null)
						data.setTotAmount(info.getTotAmount()/10000);
					if(info.getRegisterDate() != null)
						data.setRegisterDate(SDF.format(info.getRegisterDate()));
					data.setRegisterIp(IPConverter.longToIp(Long.parseLong(info.getRegisterIp())));
					data.setDevice(info.getDevice());
					data.setAccount(info.getAccount());
					data.setParentAccount(info.getParentAccount());
					data.setChain0(info.getChain0());
					data.setChain1(info.getChain1());
					datas.add(data);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		return datas;
	}
	
	public static BeginCancelListDto transBeginCancelList2CancelListDto(BeginCancelListRequest cancelRequest){
		BeginCancelListDto dto = new BeginCancelListDto();
		dto.setTimeStart(cancelRequest.getTimeStart() != null?new Date(cancelRequest.getTimeStart()):null);
		dto.setTimeEnd(cancelRequest.getTimeEnd() != null?new Date(cancelRequest.getTimeEnd()):null);
		return dto;
	}
	
	public static List<CancelListDto> transBeginMission2CancleListData(List<CancelListData> list) throws Exception{
		List<CancelListDto> datas = new ArrayList<CancelListDto>();
		try {
			if(list != null){
				for (CancelListData info : list) {
					CancelListDto dto = new CancelListDto();
					dto.setAccountName(info.getAccountName());
					if(info.getCancelTime() != null)
						dto.setCancelTime(SDF.format(info.getCancelTime()));
					dto.setCancelReason(info.getCancelReason());
					dto.setCancelUser(info.getCancelUser());
					datas.add(dto);
				}
			}
		
		} catch (Exception e) {
			throw e;
		}
		return datas;
		
	}
}
