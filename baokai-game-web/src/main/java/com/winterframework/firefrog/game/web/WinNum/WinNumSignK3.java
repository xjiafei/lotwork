/**   
* @Title: WinNumSignK3.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-8 上午9:57:50 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: WinNumSignK3 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2015-5-8 上午9:57:50 
*  
*/
public class WinNumSignK3 extends BaseWinNumSign implements IWinNumSign {

	//和值
	public void signHezhi(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		Long sumValue = 0l;
		for (String signNumber : signNumberList) {
			sumValue += Long.valueOf(signNumber);
		}
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(","));
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (Long.valueOf(betDetail).longValue() == sumValue) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView = betDetailView + ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//三联号通选
	public void signSanTongHao(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(" "));
		String betDetailView = "";
		String signNumberString = signNumberList.get(0) + signNumberList.get(1) + signNumberList.get(2);
		for (String betDetail : betDetails) {
			if (signNumberString.equals(betDetail)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += " ";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	public void signBiaoZhun(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
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

	//选三以后的所有拖胆，不包括选二拖胆。选二拖胆也无序复式一样
	public void signTuodan(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split("_T:"));
		String betDetailView = "";
		//处理胆号
		String danMaStr = betDetails.get(0).replace("D:", "");
		List<String> danMas = explode(danMaStr);

		for (String danMa : danMas) {
			if (signNumberList.contains(danMa)) {
				betDetailView = betDetailView + "<span class='color-red'>" + danMa + "</span>";
			} else {
				betDetailView = betDetailView + danMa;
			}
			betDetailView += ",";
		}
		betDetailView = "D:" + betDetailView.substring(0, betDetailView.length() - 1) + "_T:";
		//处理拖号
		List<String> tuoMas = explode(betDetails.get(1).trim());
		for (String tuoMa : tuoMas) {
			if (signNumberList.contains(tuoMa)) {
				betDetailView = betDetailView + "<span class='color-red'>" + tuoMa + "</span>";
			} else {
				betDetailView = betDetailView + tuoMa;
			}
			betDetailView += ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	public void signErTongHaoFx(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(","));
		String betDetailView = "";
		String erTongHao = signNumberList.get(0).equals(signNumberList.get(1)) == true ? signNumberList.get(0)
				+ signNumberList.get(1) : signNumberList.get(0).equals(signNumberList.get(2)) == true ? signNumberList
				.get(0) + signNumberList.get(2) : signNumberList.get(1) + signNumberList.get(2);
		for (String betDetail : betDetails) {
			betDetail=betDetail.replace("*", "");
			if (erTongHao.equals(betDetail)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "*" + "</span>";
			} else {
				betDetailView = betDetailView + betDetail + "*";
			}
			betDetailView += ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	public void signErTongHaoDx(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split("#"));
		List<String> ethList = Arrays.asList(betDetails.get(0).split(" "));
		List<String> danHaoList = Arrays.asList(betDetails.get(1).split(" "));
		String betDetailView = "";
		String erTongHao = signNumberList.get(0).equals(signNumberList.get(1)) == true ? signNumberList.get(0)
				+ signNumberList.get(1) : signNumberList.get(0).equals(signNumberList.get(2)) == true ? signNumberList
				.get(0) + signNumberList.get(2) : signNumberList.get(1) + signNumberList.get(2);
		String daoHao = signNumberList.get(0).equals(signNumberList.get(1)) == true ? signNumberList.get(2)
				: signNumberList.get(0).equals(signNumberList.get(2)) == true ? signNumberList.get(1) : signNumberList
						.get(0);
		for (String e : ethList) {
			if (e.equals(erTongHao)) {
				betDetailView = betDetailView + "<span class='color-red'>" + e + "</span>";
			} else {
				betDetailView = betDetailView + e;
			}
			betDetailView += " ";
		}
		betDetailView = betDetailView.substring(0, betDetailView.length() - 1) + "#";

		for (String d : danHaoList) {

			if (d.equals(daoHao)) {
				betDetailView = betDetailView + "<span class='color-red'>" + d + "</span>";
			} else {
				betDetailView = betDetailView + d;
			}
			betDetailView += " ";
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
				List<String> signNumberList = this.numberRecordList;
				Collections.sort(signNumberList);
				int methodCode = slip.getBetMethodCode();
				switch (methodCode) {
				//复式
				case 71:
					signHezhi(slip, signNumberList);
					break;
				//单式
				case 72:
					signSanTongHao(slip, signNumberList);
					break;
				//和值
				case 73:
					signSanTongHao(slip, signNumberList);
					break;

				//跨度
				case 74:
					signBiaoZhun(slip, signNumberList);
					break;
				//混合组选，组三组六单式，无顺序
				case 12:
					signTuodan(slip, signNumberList);
					break;
				case 75:
					signSanTongHao(slip, signNumberList);
					break;
				case 76:
					signErTongHaoFx(slip, signNumberList);
					break;
				//趣味，需符合中奖个数
				case 77:
					signErTongHaoDx(slip, signNumberList);
					break;
				case 78:
					signBiaoZhun(slip, signNumberList);
					break;
				default:
					break;
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		List<SlipsStrucDTO> slips = new ArrayList<SlipsStrucDTO>();
		SlipsStrucDTO dto = new SlipsStrucDTO();
		dto.setBetDetail("5,14,15,9,16");
		dto.setBetMethodCode(71);
		dto.setStatus(2);
		slips.add(dto);
		WinNumSignK3 ks = new WinNumSignK3();
		ks.signSlips(slips);
		System.out.print(dto.getBetDetailShow());
	}

}
