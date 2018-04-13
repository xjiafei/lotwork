/**   
* @Title: WinNumSignKl8.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-3 下午2:07:01 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: WinNumSignKl8 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-3 下午2:07:01 
*  
*/
public class WinNumSignKl8 extends BaseWinNumSign implements IWinNumSign {

	private String shangXiaCaculate(List<String> numberRecordList) {
		int upCount = 0, downCount = 0;
		for (String records : numberRecordList) {
			Integer record = Integer.valueOf(records);
			if (record >= 1 && record <= 40) {
				upCount++;
			} else {
				downCount++;
			}
		}
		return upCount > downCount ? "上" : upCount == downCount ? "中" : "下";
	}

	private String jiOuCaculate(List<String> numberRecordList) {
		int jiCount = 0, ouCount = 0;
		for (String records : numberRecordList) {
			Integer record = Integer.valueOf(records);
			if (record % 2 == 0) {
				ouCount++;
			} else {
				jiCount++;
			}
		}
		return jiCount > ouCount ? "奇" : jiCount == ouCount ? "和" : "偶";
	}

	private Long heZhiCaculate(List<String> numberRecordList) {
		Long sum = 0l;
		for (String records : numberRecordList) {
			Integer record = Integer.valueOf(records);
			sum += record;
		}
		return sum;
	}

	private String daXiaoCaculate(Long sum) {
		return sum > 810l ? "大" : "小";
	}

	private String danSuangCaculate(Long sum) {
		return sum % 2 == 0 ? "双" : "单";
	}

	public void signRenXuan(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (signNumberList.contains(betDetail)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	public void signQuWei(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split("\\|"));
		List<String> resultList = new ArrayList<String>();
		resultList.add(shangXiaCaculate(signNumberList));
		resultList.add(jiOuCaculate(signNumberList));
		resultList.add(daXiaoCaculate(heZhiCaculate(signNumberList)) + danSuangCaculate(heZhiCaculate(signNumberList)));
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (resultList.contains(betDetail)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += "|";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	/**
	* Title: signSlips
	* Description:
	* @param slips
	* @throws Exception 
	* @see com.winterframework.firefrog.game.web.WinNum.IWinNumSign#signSlips(java.util.List) 
	*/
	@Override
	public void signSlips(List<SlipsStrucDTO> slips) throws Exception {
		for (SlipsStrucDTO slip : slips) {
			if (slip.getStatus() == 2) {
				int groupCode = slip.getGameGroupCode();
				//任选
				if (groupCode == 17) {
					signRenXuan(slip, numberRecordList);
				} else {
					signQuWei(slip, numberRecordList);
				}
			}
		}

	}

}
