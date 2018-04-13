/**   
* @Title: WinNumSign115.java 
* @Package com.winterframework.firefrog.game.web.WinNum 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-9-2 下午6:56:15 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.WinNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;

/** 
* @ClassName: WinNumSign115 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-9-2 下午6:56:15 
*  
*/
public class WinNumSign115 extends BaseWinNumSign implements IWinNumSign {
	private static String SPLIT_SPACE = " ";

	protected List<String> getNumberRecordForSign(String groupCode, String setCode) throws Exception {
		Map<String, Integer[]> GAME_GROUP_NUMBER_BITS_MAP = new HashMap<String, Integer[]>();
		GAME_GROUP_NUMBER_BITS_MAP.put("22-12", new Integer[] { 0, 3 });//前三一码不定位
		GAME_GROUP_NUMBER_BITS_MAP.put("23-10", new Integer[] { 0, 2 });//前二直选
		GAME_GROUP_NUMBER_BITS_MAP.put("23-11", new Integer[] { 0, 2 });//前二组选
		GAME_GROUP_NUMBER_BITS_MAP.put("24-10", new Integer[] { 0, 3 });//前三直选
		GAME_GROUP_NUMBER_BITS_MAP.put("24-11", new Integer[] { 0, 3 });//前三组选
		Integer[] bits = GAME_GROUP_NUMBER_BITS_MAP.get(groupCode + "-" + setCode);
		if (bits == null) {
			bits = new Integer[] { 0, 5 };
		}
		return numberRecordList.subList(bits[0], bits[1]);
	}

	//标记所有无顺序比较的复式：(前三一码不定位，组选复式，任选复式,选二脱胆)
	public void signFushiContain(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails =Arrays.asList(slip.getBetDetail().split(","));
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

	//标记所有需要按位数比较的复式：(选一定位胆复式，直选复式)
	public void signFushiByBit(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		int j=0;
		for (int i = 0; i < betDetails.size(); i++) {
			String betDetail = betDetails.get(i);
			if(betDetail.equals("-")){
				betDetailView = betDetailView + betDetail;
				betDetailView += ",";
				continue;
			}
			List<String> bitDetails = Arrays.asList(betDetail.split(SPLIT_SPACE));
			for (String bitDetail : bitDetails) {
				if (signNumberList.get(j).equals(bitDetail)) {
					betDetailView = betDetailView + "<span class='color-red'>" + bitDetail + "</span>";
				} else {
					betDetailView = betDetailView + bitDetail;
				}
				betDetailView += SPLIT_SPACE;
			}
			j++;
			betDetailView += ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//标记所有需要按位数比较的单式：(直选单式)
	public void signDanshiByBit(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		for (String betDetail : betDetails) {
			boolean isWin = true;
			List<String> bitDetails = Arrays.asList(betDetail.split(SPLIT_SPACE));
			for (int i = 0; i < bitDetails.size(); i++) {
				if (!bitDetails.get(i).equals(signNumberList.get(i))) {
					isWin = false;
				}
			}
			if (isWin) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += ",";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//标记所有不需顺序比较的单式：(组选单式，任选单式)
	public void signDanshiContain(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		for (String betDetail : betDetails) {
			boolean isWin = true;
			List<String> bitDetails = Arrays.asList(betDetail.split(SPLIT_SPACE));
			for (int i = 0; i < bitDetails.size(); i++) {
				if (!signNumberList.contains(bitDetails.get(i))) {
					isWin = false;
				}
			}
			if (isWin) {
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
		List<String> betDetails = Arrays.asList(slip.getBetDetail().split("]"));
		String betDetailView = "";
		//处理胆号
		String danMaStr = betDetails.get(0).replace("[胆", "").replace("]", "");
		List<String> danMas = explode(danMaStr);

		for (String danMa : danMas) {
			if (signNumberList.contains(danMa)) {
				betDetailView = betDetailView + "<span class='color-red'>" + danMa + "</span>";
			} else {
				betDetailView = betDetailView + danMa;
			}
			betDetailView += ",";
		}
		betDetailView = "[胆" + betDetailView.substring(0, betDetailView.length() - 1) + "] ";
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

	//趣味订单双
	public void signDds(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		int shuang = 0;
		String betDetailView = "";
		for (String signNumber : signNumberList) {
			if (Integer.valueOf(signNumber) % 2 == 0) {
				shuang++;
			}
		}
		String result = (5 - shuang) + "单" + shuang + "双";
		List<String> danshuangs = Arrays.asList(slip.getBetDetail().split("\\|"));
		for (String danshuang : danshuangs) {
			if (danshuang.equals(result)) {
				betDetailView = betDetailView + "<span class='color-red'>" + danshuang + "</span>";
			} else {
				betDetailView = betDetailView + danshuang;
			}
			betDetailView += "|";
		}
		slip.setBetDetailShow(betDetailView.substring(0, betDetailView.length() - 1));
	}

	//趣味猜中位
	public void signCzw(SlipsStrucDTO slip, List<String> signNumberList) throws Exception {
		Collections.sort(signNumberList);
		String zhongwei = signNumberList.get(2);
		List<String> betDetails = explode(slip.getBetDetail());
		String betDetailView = "";
		for (String betDetail : betDetails) {
			if (betDetail.equals(zhongwei)) {
				betDetailView = betDetailView + "<span class='color-red'>" + betDetail + "</span>";
			} else {
				betDetailView = betDetailView + betDetail;
			}
			betDetailView += ",";
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
			List<String> signNumberList = this.getNumberRecordForSign(String.valueOf(slip.getGameGroupCode()),
						String.valueOf(slip.getGameSetCode()));
			if (slip.getStatus() == 2) {
				int methodCode = slip.getBetMethodCode();
				switch (methodCode) {
				//复式
				case 10://选一定位胆复式，直选复式都有顺序的比较
					if (slip.getGameSetCode() == 14 || slip.getGameSetCode() == 10) {
						signFushiByBit(slip, signNumberList);
					} else {//其他复式都是无顺序的包含
						signFushiContain(slip, signNumberList);
					}
					break;
				//单式
				case 11://直选单式是有顺序的比较
					if (slip.getGameSetCode() == 10) {
						signDanshiByBit(slip, signNumberList);
					} else {//其他单式都是无顺序的比较
						signDanshiContain(slip, signNumberList);
					}
					break;
				//拖胆
				case 12://选二拖胆也复式无序的一样
					if (slip.getGameGroupCode() == 23) {
						signFushiContain(slip, signNumberList);
					} else {
						signTuodan(slip, signNumberList);
					}
					break;
				//订单双
				case 65:
					signDds(slip, signNumberList);
					break;
				case 66:
					signCzw(slip, signNumberList);
					break;
				}
			}
			if(slip.getStatus() != 2 && slip.getBetMethodCode() == 66){
				signCzw(slip, signNumberList);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		IWinNumSign s = new WinNumSign115();
		List<String> numberRecordList = new ArrayList<String>();
		numberRecordList.add("06");
		numberRecordList.add("11");
		numberRecordList.add("02");
		numberRecordList.add("03");
		numberRecordList.add("09");
		s.setNumberRecordList(numberRecordList);
		//五星复式 直选 组选
		SlipsStrucDTO dto = new SlipsStrucDTO();
		dto.setGameGroupCode(23);//五星
		dto.setGameSetCode(10);//直选
		dto.setBetMethodCode(10);//复式
		dto.setBetDetail("01 02 03 04 05 06 07 08 09 10 11,01 02 03 04 05 06 07 08 09 10 11,-,-,-");
		dto.setStatus(2);
		List<SlipsStrucDTO> slips = new ArrayList<SlipsStrucDTO>();
		slips.add(dto);
		s.signSlips(slips);
		System.out.println(dto.getBetDetailShow());
	}
}
