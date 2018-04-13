/**   
* @Title: WinNumSignSsq.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-3 下午3:03:37 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: WinNumSignSsq 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-3 下午3:03:37 
*  
*/
public class WinNumSignSsq extends BaseWinNumSign implements IWinNumSign {

	/**
	* Title: signSlips
	* Description:
	* @param slips
	* @throws Exception 
	* @see com.winterframework.firefrog.game.web.WinNum.IWinNumSign#signSlips(java.util.List) 
	*/
	@Override
	public void signSlips(List<SlipsStrucDTO> slips) throws Exception {
		String nanMa = this.numberRecordList.get(numberRecordList.size() - 1);
		List<String> redMas = numberRecordList.subList(0, numberRecordList.size() - 1);
		for (SlipsStrucDTO slip : slips) {
			if (slip.getStatus() == 2) {
				String betDetailView = "";
				List<String> betDetails = Arrays.asList(slip.getBetDetail().split("\\+"));
				//处理红码
				List<String> bitDetails = Arrays.asList(betDetails.get(0).split(","));
				for (String bitDetail : bitDetails) {
					if (redMas.contains(bitDetail)) {
						betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
					} else {
						betDetailView = betDetailView + bitDetail;
					}
					betDetailView += ",";
				}
				betDetailView = betDetailView.substring(0, betDetailView.length() - 1) + "+";
				//处理蓝码
				bitDetails = Arrays.asList(betDetails.get(1).split(","));
				for (String bitDetail : bitDetails) {
					if (bitDetail.equals(nanMa)) {
						betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
					} else {
						betDetailView = betDetailView + bitDetail;
					}
					betDetailView += ",";
				}
				slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
			}
		}

	}

}
