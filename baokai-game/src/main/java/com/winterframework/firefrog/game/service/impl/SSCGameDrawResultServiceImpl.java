package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.entity.EventStatus;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;

/***
 * 
* @ClassName: GameDrawResultServiceImpl 
* @Description: 时时彩游戏开奖流程处理入口
* @author Richard
* @date 2013-11-4 下午2:15:54 
*
 */
@Service("sscGameDrawResultServiceImpl")
@Transactional(rollbackFor = Exception.class)
@Deprecated
public class SSCGameDrawResultServiceImpl implements IGameDrawResultService {

	private Logger log = LoggerFactory.getLogger(SSCGameDrawResultServiceImpl.class);

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Override
	public void gameDrawResult(GameDrawResult result) throws Exception {

		//正常开奖结果主流程
		if (null == result) {
			log.error("开奖结果不能为NULL");
			return;
		}
		ProcessResult processResult = new ProcessResult();

		/***
		 * 1.计奖中
		 */
		if (processResult.isSuccess()) {
			gameDesignAwards(result, processResult);
		}

		/**
		 * 2。验奖
		 */
		if (processResult.isSuccess()) {
			gameCheckAwards(result, processResult);
		}

		/**
		 * 3。派奖
		 */
		if (processResult.isSuccess()) {
			gameSchoolAwards(result, processResult);
		}
	}

	/**
	 * 
	* @Title: gameDesignAwards 
	* @Description: 计奖中
	* @param result
	* @return
	* @throws Exception
	 */
	private ProcessResult gameDesignAwards(GameDrawResult result, ProcessResult processResult) throws Exception {

		//1.需要判断等待所有追号订单已完成，上期追号是否已完成，【如果未完成追号计划，则先等待】（处于M4的状态） ？
		GameIssueEntity entity = gameIssueService.queryGameIssue(result.getLotteryid(), result.getIssueCode());

		if (null == entity) {
			log.error("奖期【" + result.getLotteryid() + "】,期号【" + result.getIssueCode() + "】不存在！");
			processResult.fail();
			return processResult;
		}

		checkStatus(entity, result, processResult);

		//2.准备计算奖金导出生成订单数据文件（名称规则为 lotteryId_issueCode_crrentDateTime。txt)
		exportGameSlip2File(result, processResult);

		//获取订单信息
		List<GameOrder> list = gameOrderDao.queryOrderByLotteryIdAndIssueCode(result.getLotteryid(),
				result.getIssueCode());

		if (list.isEmpty()) {
			log.info("奖期【" + result.getLotteryid() + "】,期号【" + result.getIssueCode() + "】无订单信息！");
			processResult.fail();
			return processResult;
		}

		//3.安全对比订单信息，验证失败记录为废单
		checkGameOrder(list, result, processResult);

		//4.判断是否中奖， 并更新list中奖状态，注单中奖状态
		checkIsWin(list, result, processResult);

		//5.生成中奖订单信息，生成风险方案分析
		if (processResult.isSuccess()) {
			createOrderWin(list, result, processResult);
		}

		//6.更新奖期状态为计奖完成(M5) 

		return processResult;
	}

	/**
	 * 
	* @Title: checkStatus 
	* @Description: 奖期状态判断
	* @param entity
	* @param result
	* @param processResult
	* @throws Exception
	 */
	private void checkStatus(GameIssueEntity entity, GameDrawResult result, ProcessResult processResult)
			throws Exception {

		//是否为锁定状态，
		if (entity.getPauseStatus() == PauseStatus.PAUSE) {

			log.info("时时彩【" + result.getLotteryid() + "】，奖期【" + result.getIssueCode() + "】为暂停状态，计奖退出！");
			processResult.fail();
		} else if (entity.getStatus() != GameIssueStatus.ACK_DRAW_RESULT) {

			//奖期状态 0:已生成(M1) 1:开始销售(M2) 2:结束销售(M3) 3:开奖号码确认(M4) 4:计奖完成(M5) 5:验奖完成(M6) 6:派奖完成(M7) 7:奖期结束(M8) 8:对账结束(M9)
			log.info("时时彩【" + result.getLotteryid() + "】，奖期【" + result.getIssueCode() + "】不是开奖号码确认状态，计奖退出！");
			processResult.fail();
		} else if (entity.getEventStatus() == EventStatus.LOCK) {

			log.info("时时彩【" + result.getLotteryid() + "】，奖期【" + result.getIssueCode() + "】为锁定状态，计奖退出！");
			processResult.fail();
		} else if (entity.getPeriodStatus() != GameIssuePeriodStatus.WAIT_DRAW_RESULT) {

			//奖期过程状态  0:待销售(P1) 1:销售中(P2) 2:待开奖(P3) 3:计奖中(P4) 4:验奖中(P5) 5:派奖中(P6) 6:待结束(P7) 7:待对账(P8)
			log.info("时时彩【" + result.getLotteryid() + "】，奖期【" + result.getIssueCode() + "】不为计奖中状态，计奖退出！");
			processResult.fail();
		}
	}

	/**
	 * 
	* @Title: createOrderWin 
	* @Description: 生成中奖订单信息，生成风险方案分析
	* @param result
	* @param processResult
	* @throws Exception
	 */
	private void createOrderWin(List<GameOrder> list, GameDrawResult result, ProcessResult processResult)
			throws Exception {

		//生成中奖订单，及分析生成风险方案

	}

	/**
	 * 
	* @Title: checkIsWin 
	* @Description: 判断是否中奖
	* @param result
	* @param processResult
	* @throws Exception
	 */
	private void checkIsWin(List<GameOrder> list, GameDrawResult result, ProcessResult processResult) throws Exception {

	}

	/**
	 * 
	* @Title: checkGameOrder 
	* @Description: 对订单进行安全对比操作 
	* @param result
	* @param processResult
	* @throws Exception
	 */
	private void checkGameOrder(List<GameOrder> list, GameDrawResult result, ProcessResult processResult)
			throws Exception {

	}

	/**
	 * 
	* @Title: exportGameSlip2File 
	* @Description: 导出文件 
	* @param result
	* @param processResult
	 */
	private void exportGameSlip2File(GameDrawResult result, ProcessResult processResult) throws Exception {

		//获取注单表信息
		List<GameSlip> list = gameSlipDao
				.querySlipByLotteryIdAndIssueCode(result.getLotteryid(), result.getIssueCode());

		if (list.isEmpty()) {
			log.info("根据彩种【" + result.getLotteryid() + "】,奖期【" + result.getIssueCode() + "】无相应的注单信息。");
			processResult.fail();
			return;
		}

		Date date = new Date();
		//（名称规则为 lotteryId_issueCode_crrentDateTime。txt)
		String fileName = result.getLotteryid() + "_" + result.getIssueCode() + "_" + System.currentTimeMillis();

		final String separator = "|";

		for (GameSlip slip : list) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(slip.getId());
			buffer.append(separator);
			buffer.append(slip.getOrderid());
			buffer.append(separator);
			buffer.append(slip.getUserid());
			buffer.append(separator);
			buffer.append(slip.getIssueCode());
			buffer.append(separator);
			buffer.append(slip.getLotteryid());
			buffer.append(separator);
			//			buffer.append(slip.getGameGroupCode());
			buffer.append(separator);
			//			buffer.append(slip.getGameSetCode());
			buffer.append(separator);
			buffer.append(slip.getMoneyMode());
			buffer.append(separator);
			buffer.append(slip.getTotbets());
			buffer.append(separator);
			buffer.append(slip.getTotamount());
			buffer.append(separator);
			buffer.append(slip.getMultiple());
			buffer.append(separator);
			buffer.append(slip.getBetDetail());
			buffer.append(separator);
			//			buffer.append(slip.getRetPoints());
			buffer.append(separator);
			buffer.append(slip.getEvaluateWin());
			buffer.append(separator);
			buffer.append(slip.getStatus());
			buffer.append(separator);
			//			buffer.append(slip.getSlipsTime());
			buffer.append(separator);
			//			buffer.append(slip.getCalculateWinTime());
			buffer.append(separator);
			//			buffer.append(slip.getSaleTime());
			buffer.append(separator);
			//			buffer.append(slip.getCancelTime());
			buffer.append(separator);
			//			buffer.append(slip.getCancelModes());
			buffer.append(separator);
			//			buffer.append(slip.getFileMode());

			fileUtil.createFilepath(buffer.toString(), fileName, date);
		}

	}

	/**
	 * 
	* @Title: gameCheckAwards 
	* @Description:验奖
	* @param result
	* @return
	* @throws Exception
	 */
	private ProcessResult gameCheckAwards(GameDrawResult result, ProcessResult processResult) throws Exception {

		//1.如果没有暂停，更新奖期状态为验奖完成
		return processResult;
	}

	/**
	 * 
	* @Title: gameSchoolAwards 
	* @Description: 派奖中 
	* @param result
	* @return
	* @throws Exception
	 */
	private ProcessResult gameSchoolAwards(GameDrawResult result, ProcessResult processResult) throws Exception {

		//1.判断是否奖期为暂停状态，

		//2.派发奖金，对于风险方案的，进行资金冻结操作
		return processResult;
	}
	@Override
	public List<GameDrawResult> getGameDrawResultTopByLotteryId(Long lotteryId,
			Integer top) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception{
		return null;
	}

}
