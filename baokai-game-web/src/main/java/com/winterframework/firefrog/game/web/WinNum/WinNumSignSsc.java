/**   
* @Title: sscWinNumSign.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-2 上午10:41:05 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: sscWinNumSign 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-2 上午10:41:05 
*  
*/
public class WinNumSignSsc extends BaseWinNumSign implements IWinNumSign {

	protected List<String> getNumberRecordForSign(String groupCode) throws Exception {
		Map<String, Integer[]> GAME_GROUP_NUMBER_BITS_MAP = new HashMap<String, Integer[]>();
		GAME_GROUP_NUMBER_BITS_MAP.put("10", new Integer[] { 0, 5 });
		GAME_GROUP_NUMBER_BITS_MAP.put("11", new Integer[] { 1, 5 });
		GAME_GROUP_NUMBER_BITS_MAP.put("12", new Integer[] { 0, 3 });
		GAME_GROUP_NUMBER_BITS_MAP.put("13", new Integer[] { 2, 5 });
		GAME_GROUP_NUMBER_BITS_MAP.put("15", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("14", new Integer[] { 3, 5 });
		GAME_GROUP_NUMBER_BITS_MAP.put("16", new Integer[] { 0, 5 });
		GAME_GROUP_NUMBER_BITS_MAP.put("33", new Integer[] { 1, 4 });
		
		Integer[] bits = GAME_GROUP_NUMBER_BITS_MAP.get(groupCode);
		return numberRecordList.subList(bits[0], bits[1]);
	}

	//按位数比较，包括复式
	public void signWinByWeishu(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		int j = 0;
		for (int i = 0; i < betDetails.size(); i++) {
			if (betDetails.get(i).equals("-")) {
				betDetailView = betDetailView + "-";
				betDetailView = betDetailView + ",";
				continue;
			}
			List<String> bitDetails = explode(betDetails.get(i));
			for (String bitDetail : bitDetails) {
				if (bitDetail.equals(signNumberList.get(j))) {
					betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
				} else {
					betDetailView = betDetailView + bitDetail;
				}
			}
			j++;
			betDetailView = betDetailView + ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//包括单式
	public void signDanshiByWeishu(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(" "));
		String betDetailView = "";
		String result = "";
		for (String sinNumber : signNumberList) {
			result += sinNumber;
		}
		for (String betDetail : betDetails) {

			if (betDetail.trim().equals(result)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += " ";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//和值
	public void signHezhi(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		Long sumValue = 0l;
		for (String signNumber : signNumberList) {
			sumValue += Long.valueOf(signNumber);
		}
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(","));
		betDetails.remove("-");
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

	//跨度
	public void signKuadu(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		Set<Integer> numberRecordSet = new HashSet<Integer>();
		for (String signNumber : signNumberList) {
			numberRecordSet.add(Integer.valueOf(signNumber));
		}
		int kdValue = getKd(numberRecordSet);
		List<String> betDetails = explode(slip.getBetDetail());
		betDetails.remove("-");
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (Integer.valueOf(betDetail) == kdValue) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView = betDetailView + ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	private boolean isAllContains(String betDetail, List<String> signNumberList) throws Exception {
		List<String> bitDetails = explode(betDetail);
		Set<String> bitDetailsSet = new HashSet<String>();
		Set<String> signNumberSet = new HashSet<String>();
		bitDetailsSet.addAll(bitDetails);
		signNumberSet.addAll(signNumberList);
		if (bitDetailsSet.size() != signNumberSet.size()) {
			return false;
		}
		for (String bitDetail : signNumberList) {
			if (!bitDetails.contains(bitDetail)) {
				return false;
			} else {
				int index = bitDetails.size();
				bitDetails.remove(bitDetail);
				int removeSize = bitDetails.size();
				for (int i = 1; i < index - removeSize; i++) {
					bitDetails.add(bitDetail);
				}
			}
		}
		return true;
	}

	//与顺序无关的单式，包括混合组选，组三单式，组六单式，组选单式
	public void danShi(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split(" "));
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (isAllContains(betDetail, signNumberList)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView = betDetailView + " ";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	private String handerChongHaoZuXuan(List<String> betDetails, List<String> signNumberList, int firstNumber,
			int secondNumber) {
		String betDetailView = "";
		List<String> bitDetails = explode(betDetails.get(0));
		for (String bitDetail : bitDetails) {
			if (getContainNum(bitDetail, signNumberList) == firstNumber) {
				betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
			} else {
				betDetailView = betDetailView + bitDetail;
			}
		}
		betDetailView = betDetailView + ",";
		bitDetails = explode(betDetails.get(1));
		for (String bitDetail : bitDetails) {
			if (getContainNum(bitDetail, signNumberList) == secondNumber) {
				betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
			} else {
				betDetailView = betDetailView + bitDetail;
			}
		}
		betDetailView = betDetailView + ",";
		return betDetailView;
	}

	//与顺序无关，包含在开奖号码里就标红
	public void signContain(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		if (slip.getBetMethodCode() == 44 || slip.getBetMethodCode() == 45 || slip.getBetMethodCode() == 50) {//zuxuan60 30 12 2+1
			betDetailView = this.handerChongHaoZuXuan(betDetails, signNumberList, 2, 1);
		} else if (slip.getBetMethodCode() == 46 || slip.getBetMethodCode() == 52) {//zuxuan20 4 3+1
			betDetailView = this.handerChongHaoZuXuan(betDetails, signNumberList, 3, 1);
		} else if (slip.getBetMethodCode() == 47) {//zuxuan10 3+2
			betDetailView = this.handerChongHaoZuXuan(betDetails, signNumberList, 3, 2);
		} else if (slip.getBetMethodCode() == 48) {//zuxuan5 4+1
			betDetailView = this.handerChongHaoZuXuan(betDetails, signNumberList, 4, 1);
		} else if (slip.getBetMethodCode() == 51) {//zuxuan6 2
			//List<String> bitDetails = explode(betDetails.get(0));
			for (String bitDetail : betDetails) {
				if (getContainNum(bitDetail, signNumberList) == 2) {
					betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
				} else {
					betDetailView = betDetailView + bitDetail;
				}
			}
			betDetailView = betDetailView + ",";
		} else {
			for (String betDetail : betDetails) {
				List<String> bitDetails = explode(betDetail);
				for (String bitDetail : bitDetails) {
					if (signNumberList.contains(bitDetail)) {
						betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
					} else {
						betDetailView = betDetailView + bitDetail;
					}
				}
				betDetailView = betDetailView + ",";
			}
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//与趣味相关的标记,要出现相应的次数才标记
	public void signQuWei(SlipsStrucDTO slip, List<String> signNumberList, int i) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		for (String betDetail : betDetails) {
			int contain = 0;
			for (String signNumber : signNumberList) {
				if (betDetail.equals(signNumber)) {
					contain++;
				}
			}
			if (i == 1) {
				if (contain > 0) {
					betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
				} else {
					betDetailView = betDetailView + betDetail;
				}
			} else {
				if (contain == i) {
					betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
				} else {
					betDetailView = betDetailView + betDetail;
				}
			}
			betDetailView = betDetailView + ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	public void signSlips(List<SlipsStrucDTO> slips) throws Exception {
		for (SlipsStrucDTO slip : slips) {
			if (slip.getStatus() == 2) {
				List<String> signNumberList = this.getNumberRecordForSign(SuperPairUtil.getCommGroupCode(slip.getGameGroupCode())+"");
				int methodCode = slip.getBetMethodCode();
				switch (methodCode) {
				//复式
				case 10:
					//直选有顺序,定位胆有顺序
					if (slip.getGameSetCode() == 10 || slip.getGameSetCode() == 14) {
						signWinByWeishu(slip, signNumberList);
					} else {//组选无顺序
						signContain(slip, signNumberList);
					}
					break;
				//单式
				case 11:
					//直选有顺序
					if (slip.getGameSetCode() == 10) {
						signDanshiByWeishu(slip, signNumberList);
					} else {
						danShi(slip, signNumberList);
					}
					break;
				//和值
				case 33:
					//直选组选一样
					signHezhi(slip, signNumberList);
					break;

				//跨度
				case 34:
					signKuadu(slip, signNumberList);
					break;
				//混合组选，组三组六单式，无顺序
				case 37:
				case 62:
				case 63:
					danShi(slip, signNumberList);
					break;
				//趣味，需符合中奖个数
				case 53:
					signQuWei(slip, signNumberList, 1);
					break;
				case 54:
					signQuWei(slip, signNumberList, 2);
					break;
				case 55:
					signQuWei(slip, signNumberList, 3);
					break;
				case 56:
					signQuWei(slip, signNumberList, 4);
					break;
				default:
					signContain(slip, signNumberList);
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> list = Arrays.asList("sfs".split(","));
		list.remove("s");
		int size = list.size();
	}
}
