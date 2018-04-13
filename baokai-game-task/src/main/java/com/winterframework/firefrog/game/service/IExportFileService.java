package com.winterframework.firefrog.game.service;

import java.util.Date;

import com.winterframework.firefrog.game.entity.GameIssueEntity;

public interface IExportFileService {

	/** 
	* @Title: exportGameSlip2File 
	* @Description: new 导出注单信息  线程执行
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	*/
	void exportGameSlip2File(final Long lotteryId, final Long issueCode) throws Exception;
	/**
	 * 
	* @Title: exportGameSlip2File 
	* @Description: 导出注单信息
	* @param lotteryId
	* @param issueCode
	* @throws Exception
	 */
	void exportGameSlip2File(Long lotteryId, Long issueCode, Date issueDate) throws Exception;

	void undoRedoExportFile(Long lotteryId, Long issueCode, Date calculateTime);
	
	 
	/** 
	 * 导出奖期信息（根据彩种和奖期）
	 * 奖期信息文本
		1.	每日晚间11点提共，每个采种生成独立文本，檔名为日期-采种ID，例如20140903-01.txt
		2.	每个文本提共次两日所有奖期信息，例如9/3晚上11点产生文本，提共9/4及9/5奖期
		3.	文本内容包含以下字段: 奖期号,开奖日期,销售截止时间,开奖时间，各字段以@分隔，每个奖期记录一行，举例如下
		140903001@2014-09-03@2014-09-03 00:04:10@2014-09-03 00:05:00
		140903002@2014-09-03@2014-09-03 00:09:10@2014-09-03 00:10:00
	 * @param lotteryId
	 * @param startTime	前过滤时间
	 * @param endTime	后过滤时间
	 * @param operFlag	操作标识
	 * @throws Exception
	 */
	void exportGameIssue(Long lotteryId,Date startTime,Date endTime,Date curDate,String operFlag) throws Exception;
	/**
	 * 导出注单信息（根据彩种和奖期）
	 * 注单文本
		1.	依据每个采种、每个奖期独立生成两次文本，第一次在销售截止后到开奖前，第二次在开奖后，前后两次文本内容格式完全相同
		2.	挡名为日期-采种ID-奖期-次数，例如20140903-01-123456-1.txt
		3.	同方案中有多个玩法，须分成多笔记录，对应同样的方案ID
		4.	文本内容包含以下字段: 投注ID,投注内容,方案ID,追号ID，各字段以逗号分隔，举例如下
		567755734,0|1|2|3|4,D140831120VFGHHFFHDE,0
		567755735,1|3|4,D140831120VFGHHFFHDF,23037973
		*若非追号单，追号ID填入0 
	 * @param lotteryId
	 * @param issueCode
	 * @param operFlag  操作标识
	 * @throws Exception 
	 */
	void exportGameSlip(GameIssueEntity issueEntity,String operFlag);
	void exportGameSlipMmc(Date startTime, Date endTime,Date curDate,String operFlag,Long lotteryId) throws Exception;
	/**
	 * 导出RNG信息
	 * RNG号源文本需包含以下字段，平台,投注时用的key,方案ID,取回号码,投注时间，各字段用@分隔，举例如下:
	   4.0@6e179b25cddef227f4a196119e1a5e7e@D0VFHFFDGIDD@62824@2014-09-15 14:59:00
	 * @param lotteryId
	 * @param issueCode
	 * @param operFlag
	 * @throws Exception
	 */
	void exportRngMmc(Date  startTime,Date endTime,Date curDate,Long lotteryId) throws Exception;
	
	/**
	 * 导出RNG信息
	 * RNG号源文本需包含以下字段，平台,投注时用的key,方案ID,取回号码,投注时间，各字段用@分隔，举例如下:
	   4.0@6e179b25cddef227f4a196119e1a5e7e@D0VFHFFDGIDD@62824@2014-09-15 14:59:00
	 * @param lotteryId
	 * @param issueCode
	 * @param operFlag
	 * @throws Exception
	 */
	void exportRngSB(Long lotteryId, Long issueCode, String Number) throws Exception;
	
	public void sendSsqEmail(GameIssueEntity issueEntity);
}
