package com.winterframework.firefrog.game.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameBetTypeStatusService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.web.dto.EmailDto;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 
* @ClassName: ExportFileServiceImpl 
* @Description: 导出数据服务接口。 
* @author Richard
* @date 2013-11-27 下午1:52:45 
*
 */
@Transactional(rollbackFor = Exception.class)
@Service("exportFileServiceImpl")
public class ExportFileServiceImpl implements IExportFileService {

	private static final Logger log = LoggerFactory.getLogger(ExportFileServiceImpl.class);

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao gameAwardGroupDao;
	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	@Resource(name = "gamePlanDaoImpl")
	protected IGamePlanDao gamePlanDao;
	
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
	@Resource(name = "gameBetTypeStatusServiceImpl")
	private IGameBetTypeStatusService gameBetTypeStatusService;

	@Resource(name = "gameSeriesConfigDaoImpl")
	private IGameSeriesConfigDao gameSeriesConfigDao;

	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.baseFundUrl")
	private String baseFundUrl;

	private final String spt_comma = ",";
	private final String spt_horizontal = "-";
	private final String spt_vertical = "|";
	private final String spt_bottom = "_";
	private final String spt_quote = "\"";
	private final String spt_at = "@";
	//以下格式化如果使用工具类中则在多线程环境下不安全
	private final SimpleDateFormat fmt_short = new SimpleDateFormat("yyyyMMdd");
	private final SimpleDateFormat fmt_short_spt = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat fmt_mid = new SimpleDateFormat("yyyyMMddHHmm");
	private final SimpleDateFormat fmt_full_spt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void exportGameSlip2File(final Long lotteryId, final Long issueCode) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					exportGameSlip2File(lotteryId, issueCode, new Date());
				} catch (Exception e) {
					log.error("导出文件出错：", e);
				}
			}
		}).start();
	}

	/**
	 * 导出游戏注单结果文件，传入条件为彩种Id和期号
	* Title: exportGameSlip2File
	* Description:
	* @param lotteryId
	* @param issueCode
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IExportFileService#exportGameSlip2File(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void exportGameSlip2File(Long lotteryId, Long issueCode, Date calculateDate) throws Exception {

		log.debug("开始导出注单结果文件。");

		//获取注单表信息
		List<GameSlip> list = gameSlipDao.querySlipByLotteryIdAndIssueCode(lotteryId, issueCode);

		if (list.isEmpty()) {
			log.debug("根据彩种【" + lotteryId + "】,奖期【" + issueCode + "】无相应的注单信息。");
			return;
		}

		//（名称规则为 GameDrawResult_lotteryId_issueCode_crrentDateTime。txt)
		String fileName = "GameDrawResult" + lotteryId + "_" + issueCode + "_" + calculateDate.getTime();

		//分隔符。
		final String separator = "|";

		StringBuffer buffer = new StringBuffer();
		for (GameSlip slip : list) {
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
			buffer.append(slip.getFileMode());

		}
		fileUtil.string2File(buffer.toString(), fileName, calculateDate);
	}

	@Override
	public void undoRedoExportFile(Long lotteryId, Long issueCode, Date calculateTime) {
		String fileName = "GameDrawResult" + lotteryId + "_" + issueCode + "_" + calculateTime.getTime();
		File distFile = new File(fileUtil.createFilepath("", fileName, calculateTime));
		distFile.delete();
	}

	@Override
	public void exportGameIssue(Long lotteryId, Date startTime, Date endTime, Date curDate, String operFlag)
			throws Exception {
		if (lotteryId == null || startTime == null || endTime == null) {
			log.error("导出文件出错：", "参数传入错误.");
			return;
		}
		String fileName = fmt_short.format(curDate) + spt_horizontal + lotteryId;
		List<GameIssueEntity> issueList = gameIssueService.getGameIssueByLotteryIdAndDay(lotteryId,
				fmt_short.format(startTime), fmt_short.format(endTime));

		StringBuffer buffer = new StringBuffer();
		if (issueList != null && issueList.size() > 0) {
			for (GameIssueEntity issue : issueList) {
				buffer.append(issue.getWebIssueCode());
				buffer.append(spt_at);
				buffer.append(fmt_short_spt.format(issue.getOpenDrawTime()));
				buffer.append(spt_at);
				buffer.append(fmt_full_spt.format(issue.getSaleEndTime()));
				buffer.append(spt_at);
				buffer.append(fmt_full_spt.format(issue.getOpenDrawTime()));
				buffer.append("\r\n");
			}
		}
		buffer.append("END");
		//不管有无奖期数据 都生成文件
		fileUtil.string2File(buffer.toString(), fileName, curDate);
	}

	public void sendSscOrderEmail(final Long lotteryId, final Long issueCode, final List<String> emails,
			String webIssueCode) throws Exception {
		log.info("邮件发送开始======================================================================");
		log.info("发送邮件的彩种:" + lotteryId);
		log.info("发送邮件的奖期:" + webIssueCode);
		StringBuffer sb = new StringBuffer();
		sb.append("<table>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th>方案编号</th>");
		sb.append("<th>投注时间</th>");
		sb.append("<th>投注人</th>");
		sb.append("<th>方案金额</th>");
		sb.append("<th>方案详情</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		List<GameOrder> orders = gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);
		if (orders != null) {
			for (GameOrder order : orders) {
				sb.append("<tr>");
				sb.append("<td>").append(order.getOrderCode()).append("</td>");
				sb.append("<td>").append(DateUtils.format(order.getSaleTime(), DateUtils.DATE_FORMAT_PATTERN))
						.append("</td>");
				sb.append("<td>").append(order.getUserName()).append("</td>");
				sb.append("<td>").append(order.getTotamount().doubleValue() / 10000).append("</td>");
				StringBuffer slipDetail = new StringBuffer();
				List<GameSlip> list = gameSlipDao.querySlipByOrder(order.getId());
				slipDetail.append("<table>");
				for (int j = 0; j < list.size(); j++) {
					GameSlip gs = list.get(j);
					if (gs.getFileMode() != null && gs.getFileMode() == 1) {
						// 文件模式将文件内容读取出来，读取方式直接读取有误问题？
						File file = new File(gs.getBetDetail());
						String content = null;
						try {
							content = FileUtils.readFileToString(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						gs.setBetDetail(content);
					} else {
						gs.setBetDetail(gs.getBetDetail());
					}
					slipDetail.append("<tr><td>").append(j + 1 + "." + gs.getBetDetail()).append("</td></tr>");
				}
				slipDetail.append("</table>");
				sb.append("<td>").append(slipDetail.toString()).append("</td>");
				sb.append("</tr>");
			}
		}

		sb.append("</tbody>");
		sb.append("</table>");
		Set<String> users = new HashSet<String>();
		users.addAll(emails);
		for (String email : users) {
			EmailDto emailDto = new EmailDto();
			emailDto.setContent(sb.toString());
			emailDto.setRcvEmail(email);
			emailDto.setTitle(webIssueCode + "期双色球方案");
			log.info("邮件发送用户:" + email);
			httpClient.invokeHttpWithoutResultType(baseFundUrl + "/noticeAdmin/testEmail", emailDto);
		}
		log.info("邮件发送结束===================================================================");
	}

	public void sendSsqEmail(GameIssueEntity issueEntity) {
		try {
			if (issueEntity == null || issueEntity.getLottery() == null || issueEntity.getIssueCode() == null
					|| issueEntity.getWebIssueCode() == null) {
				log.error("发送邮件出错：", "参数传入错误.");
				return;
			}
			final Long lotteryId = issueEntity.getLottery().getLotteryId();
			final Long issueCode = issueEntity.getIssueCode();
			final String webIssueCode = issueEntity.getWebIssueCode();
			GameSeriesConfigEntity entity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
			String emailString = entity.getEmail();
			if (emailString != null) {
				emailString.replace("，", ",");
				List<String> emails = Arrays.asList(emailString.split(","));
				sendSscOrderEmail(lotteryId, issueCode, emails, webIssueCode);
			}
		} catch (Exception e) {
			log.error("发送邮件失败");
		}
	}

	@Override
	public void exportGameSlip(GameIssueEntity issueEntity, final String operFlag) {
		if (issueEntity == null) {
			log.error("导出文件出错：", "参数传入错误.");
			return;
		}
		final Long lotteryId = issueEntity.getLottery() == null ? null : issueEntity.getLottery().getLotteryId();
		final Long issueCode = issueEntity.getIssueCode();
		final String webIssueCode = issueEntity.getWebIssueCode();
		
		try {
			Date curDate = DateUtils.currentDate();
			String fileName = fmt_short.format(curDate) + spt_horizontal + lotteryId + spt_horizontal + webIssueCode
					+ spt_horizontal + operFlag;

			//获取注单表信息
			List<GameSlip> slipList = gameSlipDao.querySlipByLotteryIdAndIssueCode(lotteryId, issueCode);

			StringBuffer buffer = new StringBuffer();
			if (slipList != null && slipList.size() > 0) {
				log.info(fileName+",slipListSize:"+slipList.size());
				Map<Long, GameOrder> orderMap = new HashMap<Long, GameOrder>();
				for (GameSlip slip : slipList) {
					GameOrder order = orderMap.get(slip.getOrderid());
					if (order == null) {
						order = gameOrderService.getGameOrderById(slip.getOrderid());
						orderMap.put(slip.getOrderid(), order);
					}
					buffer.append(createOrderHeader(slip, order));
					if (operFlag.equals("1")) {
						buffer.append(createFile1OrderContent(slip));
					} else {
						buffer.append(createFile2OrderContent(slip, order));
					}
					buffer.append("\r\n");
				}
				orderMap = null;
			}
			buffer.append("END");
			log.info("exportFileSlip content:"+buffer.toString());
			//不管有无奖期数据 都生成文件
			fileUtil.string2File(buffer.toString(), fileName, curDate);
		} catch (Exception e) {
			log.error("导出文件出错：", e);
		}
	}
	
	private String createOrderHeader(GameSlip slip,GameOrder order){
		
		Long planId = order == null ? slip.getId() : order.getPlanId();

		String orderCode = order.getOrderCode();

		String betDetail = getBetDetail(slip);

		Long slipId = slip.getId();

		// 订单CODE,投注内容,SLIPID,追号ID,
		StringBuffer buffer = new StringBuffer();
		buffer.append(orderCode);
		buffer.append(spt_at);
		buffer.append(betDetail);
		buffer.append(spt_at);
		buffer.append(slipId);
		buffer.append(spt_at);
		buffer.append(planId == null ? 0L : planId);
		buffer.append(spt_at);
		return buffer.toString();
	}
	
	private String createFile1OrderContent(GameSlip slip) throws Exception{
		String betTypeName = gameBetTypeStatusService.getAlias(
				slip.getLotteryid(), slip.getBetTypeCode());

		String awardGroupName = getAwardGroupName(slip);

		String moneyMode = slip.getMoneyMode() == MoneyMode.YUAN.getValue() ? MoneyMode.YUAN
				.getAlias() :(slip.getMoneyMode() == MoneyMode.FEN.getValue()?MoneyMode.FEN.getAlias(): MoneyMode.JIAO.getAlias());

		Long multiple = slip.getMultiple();

		Long totbets = slip.getTotbets();

		UserCustomer userCustomer = userCustomerDao.getById(slip.getUserid());
		String userAccount = userCustomer.getAccount();

		Long totalAmount = slip.getTotamount();
		
		boolean isNewpointAwardMode = slip.getAwardMode().equals(2);

		BigDecimal newpointRet = isNewpointAwardMode ? new BigDecimal(slip
				.getRetPoint().toString()).divide(new BigDecimal(10000))
				: new BigDecimal(0);

		// 玩法名称,用户奖金组,投注模式,倍投数, 总注数，用户名，金额,奖金返点率
		StringBuffer buffer = new StringBuffer();
		buffer.append(betTypeName);
		buffer.append(spt_at);
		buffer.append(awardGroupName);
		buffer.append(spt_at);
		buffer.append(moneyMode);
		buffer.append(spt_at);
		buffer.append(multiple);
		buffer.append(spt_at);
		buffer.append(totbets);
		buffer.append(spt_at);
		buffer.append(userAccount);
		buffer.append(spt_at);
		buffer.append(totalAmount);
		buffer.append(spt_at);
		buffer.append(newpointRet);
		if(slip.getLotteryid() == 99701){
			buffer.append(spt_at);
			buffer.append(slip.getSingleWin());
		}

		return buffer.toString();
	}
	
	private String createFile2OrderContent(GameSlip slip,GameOrder order) throws Exception{
		boolean hasWin = slip.getStatus().intValue() == GameSlip.Status.WIN
				.getValue();
		String winOrNot = hasWin ? "1" : "0";

		Long evaluateWin = slip.getEvaluateWin() == null ? 0L : slip
				.getEvaluateWin();
		BigDecimal winAmount = hasWin ? new BigDecimal(evaluateWin)
				: new BigDecimal(0);
		winAmount = winAmount.divide(new BigDecimal(10000));

		UserCustomer userCustomer = userCustomerDao.getById(slip.getUserid());
		String userAccount = userCustomer.getAccount();

		Long totalAmount = slip.getTotamount();

		String stopMode = getOrderPlanStopMode(order);

		String betTypeName = gameBetTypeStatusService.getAlias(
				slip.getLotteryid(), slip.getBetTypeCode());

		String awardGroupName = getAwardGroupName(slip);

		String moneyMode = slip.getMoneyMode() == MoneyMode.YUAN.getValue() ? MoneyMode.YUAN
				.getAlias() : (slip.getMoneyMode() == MoneyMode.FEN.getValue()?MoneyMode.FEN.getAlias():MoneyMode.JIAO.getAlias());

		Long multiple = slip.getMultiple();
		
		boolean isNewpointAwardMode = slip.getAwardMode() == 2;

		BigDecimal newpointRet = isNewpointAwardMode ? new BigDecimal(slip
				.getRetPoint().toString()).divide(new BigDecimal(10000))
				: new BigDecimal(0);

		// 是否中奖,中奖金额,总注数,用户名,金额,
		StringBuffer buffer = new StringBuffer();
		buffer.append(winOrNot);
		buffer.append(spt_at);
		buffer.append(winAmount);
		buffer.append(spt_at);
		buffer.append(slip.getTotbets());
		buffer.append(spt_at);
		buffer.append(userAccount);
		buffer.append(spt_at);
		buffer.append(totalAmount);
		buffer.append(spt_at);
		// 是否中奖即停,玩法名称,用户奖金组,投注模式,倍投数,奖金返点率
		buffer.append(stopMode);
		buffer.append(spt_at);
		buffer.append(betTypeName);
		buffer.append(spt_at);
		buffer.append(awardGroupName);
		buffer.append(spt_at);
		buffer.append(moneyMode);
		buffer.append(spt_at);
		buffer.append(multiple);
		buffer.append(spt_at);
		buffer.append(newpointRet);
		return buffer.toString();
	}
	
	private String getOrderPlanStopMode(GameOrder order) {
		String stopMode = "0";
		if (order.getPlanId() != null) {
			GamePlan plan = gamePlanDao.getById(order.getPlanId());
			if (plan != null) {
				stopMode = plan.getStopMode().intValue() > 0 ? "1" : "0";
			}
		}
		return stopMode;
	}

	private String getBetDetail(GameSlip slip){
		Integer fileMode = slip.getFileMode();
		String betDetail = slip.getBetDetail();
		if (fileMode != null && fileMode.intValue() == 1) {
			File file = new File(betDetail);
			try {
				betDetail = FileUtils.readFileToString(file);
			} catch (IOException e) {
				betDetail = "获取文件投注信息错误，文件路径=" + slip.getBetDetail();
				log.error(betDetail, e);
			}
		}
		return betDetail;
	}
	
	private String getAwardGroupName(GameSlip slip) throws Exception{
		Long lotteryId = slip.getLotteryid();
		List<GameUserAwardGroup> userAwardGroupList = gameUserAwardGroupService
				.getUserAwardGroupByLotteryIdAndUserId(lotteryId, slip.getUserid());
		String awardGroupName = "";
		if (userAwardGroupList != null && userAwardGroupList.size() > 0) {
			for (GameUserAwardGroup userAwardGroup : userAwardGroupList) {
				if (userAwardGroup.getStatus().intValue() == 1
						&& userAwardGroup.getBetType().intValue() == YesNo.YES.getValue()) {
					GameAwardGroup awardGroup = gameAwardGroupDao.getById(userAwardGroup
							.getSysGroupAwardId());
					awardGroupName = awardGroup.getAwardName();
					break;
				}
			}
		}
		return awardGroupName;
	}	

	@Override
	public void exportGameSlipMmc(Date startTime, Date endTime, Date curDate, String operFlag,Long lotteryId) throws Exception {
		if (startTime == null || endTime == null || operFlag == null) {
			log.error("导出文件出错：", "参数传入错误.");
			return;
		}
		String issueCode_expt = fmt_mid.format(startTime);
		String fileName = fmt_short.format(curDate) + spt_horizontal + lotteryId + spt_horizontal + issueCode_expt
				+ spt_horizontal + operFlag;
		StringBuffer buffer = new StringBuffer();
		List<GameOrder> orderList = gameOrderService.getGameOrderByLotteryIdAndTime(lotteryId, startTime, endTime);
		if (orderList != null && orderList.size() > 0) {
			for (GameOrder order : orderList) {
				/*--2015/03/11放开
				 * if (order.getStatus().intValue() == GameOrder.Status.CANCEL.getValue())
					continue;*/
				//秒秒彩slip中奖期和orderid为相同值
				List<GameSlip> slipList = gameSlipDao.querySlipByLotteryIdAndIssueCode(lotteryId, order.getIssueCode());
				if (slipList != null && slipList.size() > 0) {
					for (GameSlip slip : slipList) {
						//撤销的不导出
						//if(slip.getStatus().intValue()==GameSlip.Status.CANCEL.getValue()) continue; 
						buffer.append(order.getOrderCode());
						buffer.append(spt_at);
						String betDetail = slip.getBetDetail();
						int fileMode = slip.getFileMode();
						if (fileMode == 1) {
							File file = new File(betDetail);
							try {
								betDetail = FileUtils.readFileToString(file);
							} catch (IOException e) {
								betDetail = "获取文件投注信息错误，文件路径=" + slip.getBetDetail();
								log.error(betDetail, e);
							}
						}
						buffer.append(betDetail);
						buffer.append(spt_at);
						buffer.append(slip.getId());
						buffer.append(spt_at);
						buffer.append(order.getPlanId() == null ? 0L : order.getPlanId());
						buffer.append(spt_at);
						if (operFlag.equals("1")) {
							buffer.append(gameBetTypeStatusService.getAlias(lotteryId, slip.getBetTypeCode()));
							//获取奖金组
							buffer.append(spt_at);
							List<GameUserAwardGroup> userAwardGroupList = gameUserAwardGroupService
									.getUserAwardGroupByLotteryIdAndUserId(lotteryId, slip.getUserid());
							String awardGroupName = "";
							if (userAwardGroupList != null && userAwardGroupList.size() > 0) {
								for (GameUserAwardGroup userAwardGroup : userAwardGroupList) {
									if (userAwardGroup.getStatus().intValue() == 1
											&& userAwardGroup.getBetType().intValue() == YesNo.YES.getValue()) {
										GameAwardGroup awardGroup = gameAwardGroupDao.getById(userAwardGroup
												.getSysGroupAwardId());
										/*List<GameAward> awardList=gameAwardDao.getGameAwardByGroupIdAndBetTypeCodeParent(awardGroup.getId(), slip.getBetTypeCode());
										if(awardList!=null && awardList.size()>0){
											//多个奖金组则逗号隔开
											for(GameAward award:awardList){
												if(award.getActualBonus()!=0L){
													buffer.append(award.getActualBonus()/10000L);
													buffer.append(spt_comma);
												}
											}
											buffer.deleteCharAt(buffer.length()-1);
										}*/
										awardGroupName = awardGroup.getAwardName();
										break;
									}
								}
							}

							buffer.append(awardGroupName);
							buffer.append(spt_at);
							buffer.append(slip.getMoneyMode() == MoneyMode.YUAN.getValue() ? MoneyMode.YUAN.getAlias()
									: (slip.getMoneyMode() == MoneyMode.FEN.getValue()?MoneyMode.FEN.getAlias():MoneyMode.JIAO.getAlias()));
							buffer.append(spt_at);
							buffer.append(slip.getMultiple());
							buffer.append(spt_at);
							buffer.append(slip.getTotbets());
						} else {
							if (slip.getStatus().intValue() == GameSlip.Status.WIN.getValue()) {
								buffer.append("1");
								buffer.append(spt_at);
								BigDecimal winAmount = new BigDecimal(slip.getEvaluateWin() == null ? 0L
										: slip.getEvaluateWin());
								BigDecimal diamondWinAmount = new BigDecimal(slip.getDiamondWin() == null ? 0L
										: slip.getDiamondWin());
								BigDecimal totWin = (winAmount.add(diamondWinAmount));
								buffer.append(totWin.divide(new BigDecimal(10000)));
							} else {
								buffer.append("0");
								buffer.append(spt_at);
								buffer.append("0");
							}
							buffer.append(spt_at);
							buffer.append(slip.getTotbets());
							buffer.append(spt_at);
							GameIssueEntity issue = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId,
									order.getIssueCode());
							buffer.append((issue != null && issue.getPreNumberRecord() != null) ? issue
									.getPreNumberRecord() : "");
						}
						
						UserCustomer userCustomer = userCustomerDao.getById(slip.getUserid());
						buffer.append(spt_at);
						buffer.append(userCustomer.getAccount());
						
						buffer.append(spt_at);
						buffer.append(slip.getTotamount()+slip.getDiamondAmount());

						boolean isNewpointAwardMode = slip.getAwardMode().equals(2);
						BigDecimal newpointRet = isNewpointAwardMode ? new BigDecimal(slip
								.getRetPoint().toString()).divide(new BigDecimal(10000))
								: new BigDecimal(0);
						buffer.append(spt_at);
						buffer.append(newpointRet);
						
						//秒秒彩鑽石倍數
						if(operFlag.equals("2")){
						buffer.append(spt_at);
						buffer.append(order.getDiamondMultiple());
						buffer.append(spt_at);
							if(slip.getDiamondAmount().longValue() > 0l){
								buffer.append("1");
							}else{
								buffer.append("0");
							}
						}
						
						buffer.append("\r\n");
					}
				}
			}
		}
		buffer.append("END");
		//不管有无奖期数据 都生成文件
		fileUtil.string2File(buffer.toString(), fileName, curDate);
	}

	public void exportRngMmc(Date startTime, Date endTime, Date curDate,Long lotteryId) throws Exception {
		if (startTime == null || endTime == null) {
			log.error("导出文件出错：", "参数传入错误.");
			return;
		}
		String platform = "4.0";
		String issueCode_expt = fmt_mid.format(startTime);
		String fileName = fmt_short.format(curDate) + spt_horizontal + lotteryId + spt_horizontal + issueCode_expt
				+ spt_horizontal + "RNG";
		List<GameOrder> orderList = gameOrderService.getGameOrderByLotteryIdAndTime(lotteryId, startTime, endTime);

		StringBuffer buffer = new StringBuffer();
		if (orderList != null && orderList.size() > 0) {
			for (GameOrder order : orderList) {
				if (order.getStatus().intValue() == GameOrder.Status.CANCEL.getValue())
					continue;

				buffer.append(platform);
				buffer.append(spt_at);
				buffer.append(order.getOrderCode());
				buffer.append(spt_at);
				buffer.append(order.getId());
				buffer.append(spt_at);
				GameIssueEntity issue = gameIssueService.queryGameIssueByLotteryIdAndIssueCode(lotteryId,
						order.getIssueCode());
				buffer.append((issue != null && issue.getPreNumberRecord() != null) ? issue.getPreNumberRecord() : "");
				buffer.append(spt_at);
				buffer.append(fmt_full_spt.format(order.getSaleTime()));
				buffer.append("\r\n");
			}
		}
		buffer.append("END");
		//不管有无奖期数据 都生成文件
		fileUtil.string2File(buffer.toString(), fileName, curDate);

	};

	

	public void exportRngSB(Long lotteryId, Long issueCode, String Number) throws Exception {
		// TODO Auto-generated method stub
		String platform = "4.0";
		Date startTime = new Date (); 
		String fileName = fmt_short.format(startTime) + spt_horizontal + lotteryId + spt_horizontal + issueCode
				+ spt_horizontal + "RNG";
		
		StringBuffer buffer = new StringBuffer();
	
		buffer.append(platform);
		buffer.append(spt_at);
		buffer.append(Number);
		buffer.append("\r\n");
		
		//不管有无奖期数据 都生成文件
		fileUtil.string2File(buffer.toString(), fileName, startTime);
	};
}
