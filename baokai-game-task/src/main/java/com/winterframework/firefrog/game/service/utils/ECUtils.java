package com.winterframework.firefrog.game.service.utils;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameECLog;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;


public class ECUtils{

	public final static int EC_NOTICES_NORMAL_TYPE = 1;

	public final static int EC_NOTICES_UPDATE_TYPE = 2;

	public final static int EC_NOTICES_ENDSALES_TYPE = 3;

	public final static int EC_QUERY_LOG = 4;

	public final static int EC_TRY_LOG = 5;
	/** 
	* @Title: tansferGameIssueForECIssue 
	* @Description:将gameissue转换成ecIssue
	* @param lotteryid
	* @param webIssueCode
	* @return
	*/
	public static String tansferGameIssueForECIssue(Long lotteryid, String webIssueCode) {
		if (IssueCodeUtil.LOTTERYID_CQSSC == lotteryid || IssueCodeUtil.LOTTERYID_XJSSC == lotteryid
				|| IssueCodeUtil.LOTTERYID_LLSSC == lotteryid || IssueCodeUtil.LOTTERYID_TJSSC == lotteryid
				|| IssueCodeUtil.LOTTERYID_JLFFC == lotteryid	
			    || IssueCodeUtil.LOTTERYID_TXFFC == lotteryid	
				|| IssueCodeUtil.LOTTERYID_LL115 == lotteryid || IssueCodeUtil.LOTTERYID_CQ115 == lotteryid
				|| IssueCodeUtil.LOTTERYID_SD115 == lotteryid || IssueCodeUtil.LOTTERYID_GD115 == lotteryid
				 ) {
			webIssueCode = webIssueCode.substring(2);
		}

		if (IssueCodeUtil.LOTTERYID_JX115 == lotteryid || IssueCodeUtil.LOTTERYID_JXSSC == lotteryid
				|| IssueCodeUtil.LOTTERYID_SSL == lotteryid
				|| IssueCodeUtil.LOTTERYID_JLSB_COM == lotteryid || IssueCodeUtil.LOTTERYID_JLSB_VIP == lotteryid) {

		} else {
			webIssueCode = webIssueCode.replace("-", "");
		}
		
		if (IssueCodeUtil.LOTTERYID_JX115 == lotteryid || IssueCodeUtil.LOTTERYID_CQ115 == lotteryid
				|| IssueCodeUtil.LOTTERYID_SD115 == lotteryid || IssueCodeUtil.LOTTERYID_GD115 == lotteryid) {
			webIssueCode = webIssueCode.substring(0, webIssueCode.length()-3) + webIssueCode.substring(webIssueCode.length()-2, webIssueCode.length());
		} 
		return webIssueCode;

	}
	
	public static Map<String, String> getMapFormRequestParams(String data) {

		Map<String, String> params = new HashMap<String, String>();

		String[] dataList = data.split("&");

		for (String str : dataList) {
			String[] strList = str.split("=");
			if (strList.length == 2) {
				params.put(strList[0].trim(), strList[1].trim());
			}
		}

		return params;
	}

	public static String echoECData(Map<String, String> request, String strUU) {
		String str = new String();
		StringBuffer sb = new StringBuffer(str);
		sb.append("customer=" + request.get("customer")).append("&recordId=" + request.get("recordId"))
				.append("&lottery=" + request.get("lottery")).append("&issue=" + request.get("issue"))
				.append("&time=" + request.get("time")).append("&logId=" + strUU)
				.append("&number=" + request.get("number")).append("&verifiedTime=" + request.get("verifiedTime"))
				.append("&earliestTime=" + request.get("earliestTime"))
				.append("&stopSaleTime=" + request.get("stopSaleTime"))
				.append("&drawingTime=" + request.get("drawingTime"));

		return sb.toString();
	}

	public static String echoSaveData(Map<String, String> request) {
		StringBuffer sb = new StringBuffer();
		String number = request.get("number").replaceAll("%2C", ",").replaceAll("%2B", "+");
		sb.append("{");
		sb.append("\"number\":\"").append(number).append("\"").append(",");
		sb.append("\"earliestTime\":\"").append(request.get("earliestTime")).append("\"").append(",");
		sb.append("\"verifiedTime\":\"").append(request.get("verifiedTime")).append("\"");
		sb.append("}");
		return sb.toString();
	}

	public static GameECLog getMapToEntity(Map<String, String> request) {

		GameECLog gameECLog = new GameECLog();

		gameECLog.setCustomer(request.get("customer"));
		gameECLog.setDrawingTime(request.get("drawingTime"));
		gameECLog.setEarliestTime(request.get("earliestTime"));
		gameECLog.setIssue(request.get("issue"));
		gameECLog.setIssueNumber(request.get("number"));
		gameECLog.setLottery(request.get("lottery"));
		gameECLog.setMessage(request.get("message"));
		try {
			gameECLog.setRecordID(Long.valueOf(request.get("recordId")));
		} catch (Exception e) {
			gameECLog.setRecordID(Long.valueOf(request.get("record_id")));
		}
		gameECLog.setRequestTime(request.get("time"));
		gameECLog.setSafeStr(request.get("safestr"));
		gameECLog.setStatus(request.get("status"));
		gameECLog.setStopsaleTime(request.get("stopSaleTime"));
		gameECLog.setStrUUID(request.get("logId"));
		gameECLog.setType(Integer.valueOf(request.get("type")));
		gameECLog.setVerifiedTime(request.get("verifiedTime"));

		return gameECLog;
	}

	public static String getIssueFromEC(Long lotteryid, String issue) {
		//把所有的"-"都给去掉		
		if (IssueCodeUtil.LOTTERYID_PL5 == lotteryid || IssueCodeUtil.LOTTERYID_SSQ == lotteryid
				|| IssueCodeUtil.LOTTERYID_F3D == lotteryid || IssueCodeUtil.LOTTERYID_HLJSSC == lotteryid
				|| IssueCodeUtil.LOTTERYID_BJKL8 == lotteryid) {
			//3d p5 双色球格式 黑龙江ssc 北京快乐8
			return issue;
		} else {
			if (!issue.startsWith("20")) {
				issue = "20" + issue;
			}
			issue = issue.replace("-", "");
			if (IssueCodeUtil.LOTTERYID_JLFFC == lotteryid || IssueCodeUtil.LOTTERYID_TXFFC == lotteryid ||IssueCodeUtil.LOTTERYID_JLSB_COM == lotteryid || IssueCodeUtil.LOTTERYID_JLSB_VIP == lotteryid) {

				StringBuffer sb = new StringBuffer(issue);
				sb.insert(sb.length() - 4, "-");
				return sb.toString();
			} else if (IssueCodeUtil.LOTTERYID_SSL == lotteryid || IssueCodeUtil.LOTTERYID_XJSSC == lotteryid) {
				StringBuffer sb = new StringBuffer(issue);
				sb.insert(sb.length() - 2, "-");
				return sb.toString();
			} else {
				StringBuffer sb = new StringBuffer(issue);
				if (issue.length() == 10) {
					sb.insert(sb.length() - 2, "0");
				}
				sb.insert(sb.length() - 3, "-");
				return sb.toString();
			}
		}
	}
}