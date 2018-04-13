package com.winterframework.firefrog.common.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ChannelType 
 * @Description: 渠道类型
 * @author Richard
 * @date 2014-4-1 下午5:22:08 
 */
public enum ChannelType {
	// 舊資料
	/**
	 * WEB(1)
	 * @deprecated 舊資料不再使用
	 */
	WEB(1),
	/**
	 * IOS(2)
	 * @deprecated 舊資料不再使用
	 */
	IOS(2),
	/**
	 * IOS(2)
	 * ios手機端來源
	 */
	iPhone(2),
	/**
	 * IPAD(3)
	 * @deprecated 舊資料不再使用
	 */
	IPAD(3),
	/**
	 * ANDROID(4)
	 * ANDROID 手機端來源
	 */
	ANDROID(4),
	/**
	 * WAC(5)
	 * @deprecated 舊資料不再使用
	 */
	WAC(5),
	/**
	 * WAP(6)
	 * @deprecated 舊資料不再使用
	 */
	WAP(6),
	
	// 新資料
	/**進入來源：PC→WEB(100)*/
	PC_WEB(100),
	/**進入來源：IOS→WAP(201)*/
	IOS_WAP(201),
	/**進入來源：IOS→APP-native(202)*/
	IOS_APPNATIVE(202),
	/**進入來源：IOS→APP-WAP(203)*/
	IOS_APPWAP(203),
	/**進入來源：IPAD→WAP(301)*/
	IPAD_WAP(301),
	/**進入來源：IPAD→APP-native(302)*/
	IPAD_APPNATIVE(302),
	/**進入來源：IPAD→APP-WAP(303)*/
	IPAD_APPWAP(303),
	/**進入來源：ANDROID→WAP(401)*/
	ANDROID_WAP(401),
	/**進入來源：ANDROID→APP-native(402)*/
	ANDROID_APPNATIVE(402),
	/**進入來源：ANDROID→APP-WAP(403)*/
	ANDROID_APPWAP(403)
	;
	
	private int value;
	ChannelType(int action){
		this.value = action;
	}
	
	public int getValue(){
		return this.value;
	}
	
	/**
	 * 依據來源號碼取得對應的 Enum 物件。
	 * @param value 來源號碼
	 * @return 查無資料返回 null。
	 */
	public static ChannelType getChannelType(Long value) {
		if(value == null) return null;
		for(ChannelType ct : ChannelType.values()) {
			if(value.intValue() == ct.getValue()) {
				return ct;
			}
		}
		return null;
	}
	
	/**
	 * 依據來源號碼取得對應的 Enum 物件名稱。
	 * @param value 來源號碼
	 * @return 查無資料返回 null。
	 */
	public static String getName(Long value) {
		if(value == null) return null;
		for(ChannelType ct : ChannelType.values()) {
			if(value.intValue() == ct.getValue()) {
				return ct.name();
			}
		}
		return null;
	}
	
	// OS 層
	/**判斷來源是否為 Desktop 系統*/
	private static String pcReg = "\\bWindows NT|Macintosh|Mac PowerPC|X11\\b";
	/**判斷來源是否為 iOS 系統*/
	private static String iosReg = "\\biPhone\\b";
	/**判斷來源是否為 iPad 系統*/
	private static String ipadReg = "\\biPad\\b";
	/**判斷來源是否為 Android 系統*/
	private static String androidReg = "\\bAndroid\\b";
	
	// 型態層
	/**判斷來源是否為 Mobile*/
	private static String wapReg = "\\bMobile\\b";
	
	private static Pattern pcPat = Pattern.compile(pcReg, Pattern.CASE_INSENSITIVE);
	private static Pattern iosPat = Pattern.compile(iosReg, Pattern.CASE_INSENSITIVE);
	private static Pattern ipadPat = Pattern.compile(ipadReg, Pattern.CASE_INSENSITIVE);
	private static Pattern androidPat = Pattern.compile(androidReg, Pattern.CASE_INSENSITIVE);
	
	private static Pattern wapPat = Pattern.compile(wapReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 依據 userAgent 判斷用戶透過哪種裝置及程式進入遊戲。<br>
	 * 目前只處理舊資料的 WEB、WAP 兩塊，IOS、IPAD、ANDROID、WAC 尚未導至此處。 
	 * @param userAgent request.header.USER-AGENT
	 * @return 無法判斷內容時回傳 null。
	 */
	public static Integer getChannelId(String userAgent) {
		if(userAgent == null || "".equals(userAgent)) return null;
		
		// OS 層
		Matcher pcMatcher = pcPat.matcher(userAgent);
		Matcher iosMatcher = iosPat.matcher(userAgent);
		Matcher ipadMatcher = ipadPat.matcher(userAgent);
		Matcher androidMatcher = androidPat.matcher(userAgent);
		
		// 型態層
		Matcher wapMatcher = wapPat.matcher(userAgent);
		
		// 找到 PC 類登入資訊
		if(pcMatcher.find()) {
			return PC_WEB.getValue();
		} else if(iosMatcher.find()) {
			if(wapMatcher.find()) {
				return IOS_WAP.getValue();
			} else {
				// 目前暫不分析其餘型態，待 APP 端排時程修改。
				return IOS_APPNATIVE.getValue();
			}
		} else if(ipadMatcher.find()) {
			if(wapMatcher.find()) {
				return IPAD_WAP.getValue();
			} else {
				// 目前暫不分析其餘型態，待 APP 端排時程修改。
				return null;
			}
		} else if(androidMatcher.find()) {
			if(wapMatcher.find()) {
				return ANDROID_WAP.getValue();
			} else {
				// 目前暫不分析其餘型態，待 APP 端排時程修改。
				return ANDROID_APPNATIVE.getValue();
			}
		}
		
		return null;
	}
	/**
	 * 依據mp傳進來的device傳喚成對應渠道
	 */
	public static String transDeviceToChannelType(Integer device){
		if(iPhone.getValue()== device){
			return "iPhone";
		}else if(ANDROID.getValue()==device){
			return "Android";
		}
		return null;
	}
}
