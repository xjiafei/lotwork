package com.winterframework.firefrog.game.entity;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.util.LHCUtil;

/**
 * 六合彩玩法名稱對應號碼MAP表。
 * @author Pogi.Lin
 */
public class LotteryWinLhcMap {

	/**兩面、色波、半波號碼球組。key:簡中敘述*/
	public final static Map<String,String[]> NUM_MAP = new HashMap<String,String[]>();
	/**色波號碼球組。key:大寫英文*/
	public final static Map<String, String[]> LHC_COLOR_MAP = new HashMap<String,String[]>();
	/**六合彩獎金識別碼。key:lhc_code, value:簡中說明*/
	public final static Map<String, String> LHC_CODE_MAP = new HashMap<String, String>();
	
	static{
		//兩面
		NUM_MAP.put("大", new String[]{"25","26","27","28","29","30","31","32","33","34","35","36","37","38","39",
				"40","41","42","43","44","45","46","47","48"});
		NUM_MAP.put("小", new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
				"16","17","18","19","20","21","22","23","24"});
		
		NUM_MAP.put("单", new String[]{"01","03","05","07","09","11","13","15",
				"17","19","21","23","25","27","29","31","33","35","37","39","41","43","45","47"});
		NUM_MAP.put("双", new String[]{"02","04","06","08","10","12","14","16","18","20","22","24","26","28","30",
				"32","34","36","38","40","42","44","46","48"});
		
		NUM_MAP.put("大肖", new String[]{"马","羊","猴","鸡","狗","猪"});
		
		NUM_MAP.put("小肖", new String[]{"鼠","牛","虎","兔","龙","蛇"});
		
		NUM_MAP.put("和大", new String[]{"07","08","09","16","17","18","19","25","26","27","28","29",
				"34","35","36","37","38","39","43","44","45","46","47","48"});
		NUM_MAP.put("和小", new String[]{"01","02","03","04","05","06","10","11","12","13","14","15","20",
				"21","22","23","24","30","31","32","33","40","41","42"});
		
		NUM_MAP.put("和单", new String[]{"01","03","05","07","09","10","12","14","16","18","21","23","25","27","29",
				"30","32","34","36","38","41","43","45","47"});
		NUM_MAP.put("和双", new String[]{"02","04","06","08","11","13","15","17","19","20","22","24","26","28","31",
				"33","35","37","39","40","42","44","46","48"});

		NUM_MAP.put("尾大", new String[]{"05","06","07","08","09","15","16","17","18","19","25","26","27","28","29",
				"35","36","37","38","39","45","46","47","48"});
		NUM_MAP.put("尾小", new String[]{"01","02","03","04","10","11","12","13","14","20","21","22","23","24","30",
				"31","32","33","34","40","41","42","43","44"});
		
		//色波
		NUM_MAP.put("红", new String[]{"01","02","07","08","12","13","18","19","23","24","29","30","34","35","40",
				"45","46"});
		NUM_MAP.put("蓝", new String[]{"03","04","09","10","14","15","20","25","26","31","36","37","41","42","47",
				"48"});
		NUM_MAP.put("绿", new String[]{"05","06","11","16","17","21","22","27","28","32","33","38","39","43","44",
				"49"});
		LHC_COLOR_MAP.put("RED", new String[]{"01","02","07","08","12","13","18","19","23","24","29","30","34","35","40","45","46"});
		LHC_COLOR_MAP.put("BLUE", new String[]{"03","04","09","10","14","15","20","25","26","31","36","37","41","42","47","48"});
		LHC_COLOR_MAP.put("GREEN", new String[]{"05","06","11","16","17","21","22","27","28","32","33","38","39","43","44","49"});
		
		//半波
		NUM_MAP.put("红大", new String[]{"29","30","34","35","40","45","46"});
		NUM_MAP.put("红小", new String[]{"01","02","07","08","12","13","18","19","23","24"});
		NUM_MAP.put("红单", new String[]{"01","07","13","19","23","29","35","45"});
		NUM_MAP.put("红双", new String[]{"02","08","12","18","24","30","34","40","46"});
		
		NUM_MAP.put("蓝大", new String[]{"25","26","31","36","37","41","42","47","48"});
		NUM_MAP.put("蓝小", new String[]{"03","04","09","10","14","15","20"});
		NUM_MAP.put("蓝单", new String[]{"03","09","15","25","31","37","41","47"});
		NUM_MAP.put("蓝双", new String[]{"04","10","14","20","26","36","42","48"});
		
		NUM_MAP.put("绿大", new String[]{"27","28","32","33","38","39","43","44"});
		NUM_MAP.put("绿小", new String[]{"05","06","11","16","17","21","22"});
		NUM_MAP.put("绿单", new String[]{"05","11","17","21","27","33","39","43"});
		NUM_MAP.put("绿双", new String[]{"06","16","22","28","32","38","44"});
		
		//LHC_CODE
		LHC_CODE_MAP.put(LHCUtil.LhcCode.DIRECT.toString(), "01~49号球");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONYEAR.toString(), "本命年生肖");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNYEAR.toString(), "其他生肖");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.TWOFACE.toString(), "两面");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.RED.toString(), "红波");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.BLUE.toString(), "蓝波");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.GREEN.toString(), "绿波");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.FLATCODE.toString(), "直选六码");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.REDBIG.toString(), "红大");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.REDSMALL.toString(), "红小");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.REDODD.toString(), "红单");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.REDEVEN.toString(), "红双");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.BLUEBIG.toString(), "蓝大");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.BLUESMALL.toString(), "蓝小");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.BLUEODD.toString(), "蓝单");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.BLUEEVEN.toString(), "蓝双");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.GREENBIG.toString(), "绿大");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.GREENSMALL.toString(), "绿小");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.GREENODD.toString(), "绿单");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.GREENEVEN.toString(), "绿双");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNONEYEAR.toString(), "一肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONONEYEAR.toString(), "一肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.NOTIN4.toString(), "四不中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.NOTIN5.toString(), "五不中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.NOTIN6.toString(), "六不中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.NOTIN7.toString(), "七不中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.NOTIN8.toString(), "八不中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUEIN2.toString(), "连肖(中)二连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUEIN3.toString(), "连肖(中)三连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUEIN4.toString(), "连肖(中)四连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUEIN5.toString(), "连肖(中)五连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUEIN2.toString(), "连肖(中)二连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUEIN3.toString(), "连肖(中)三连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUEIN4.toString(), "连肖(中)四连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUEIN5.toString(), "连肖(中)五连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUENOTIN2.toString(), "连肖(不中)二连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUENOTIN3.toString(), "连肖(不中)三连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUENOTIN4.toString(), "连肖(不中)四连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.UNCONTINUENOTIN5.toString(), "连肖(不中)五连肖其他生肖年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUENOTIN2.toString(), "连肖(不中)二连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUENOTIN3.toString(), "连肖(不中)三连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUENOTIN4.toString(), "连肖(不中)四连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.ONCONTINUENOTIN5.toString(), "连肖(不中)五连肖本命年");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE333.toString(), "三全中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE322.toString(), "三中二(中二)");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE323.toString(), "三中二(中三)");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE2Z.toString(), "二全中");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE22Z.toString(), "二中特(中二)");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE22T.toString(), "二中特(中特)");
		LHC_CODE_MAP.put(LHCUtil.LhcCode.CONTINUECODE2T.toString(), "特串");
	}
}
