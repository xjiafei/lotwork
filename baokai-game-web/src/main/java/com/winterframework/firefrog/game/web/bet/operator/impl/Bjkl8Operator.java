package com.winterframework.firefrog.game.web.bet.operator.impl;

import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.web.dto.*;
import com.winterframework.firefrog.game.web.util.IPUtil;
import com.winterframework.firefrog.game.web.validate.impl.ssc.ValidateUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title Bjkl8Operator
 * @Description 北京快乐8操作类
 * @author lex
 */
public class Bjkl8Operator extends AbstractBetOperator {
	
	@PropertyConfig(value = "web.server.url")
	private String webServerPath;
	
	@PropertyConfig(value = "url.gameBet.createNumberChartsDom")
	private String createNumberChartsDomUrl;
	
    private Map<String, Integer> methodGroup;//玩法组

    public void setMethodGroup(Map<String, Integer> methodGroup) {
        this.methodGroup = methodGroup;
    }

    private Logger logger = LoggerFactory.getLogger(Bjkl8Operator.class);
    @Override
    protected String formatLastBalls(GameIssueQueryResponse gameIssue) {
        return gameIssue.getNumberRecord() == null ?
                "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20" : gameIssue.getNumberRecord();
    }

   /* @Override
    public GameBetJsonResultStruc save(String data, HttpServletRequest request) throws Exception {

        logger.info("saveGameOrder start");
        //springmvc 支持直接解析json，但是参数或类型错误不会打印出来，直接给报一个400 bad request，因此在此用代码转换json串  //  金额*10000  自解析json处理异常抛出
        GameOrderRequestDTO dto = new GameOrderRequestDTO();
        try {
            dto = convertJsonToObject(data, GameOrderRequestDTO.class);
        } catch (Exception e) {
            logger.error("saveGameOrder json param paser error!", e);
            return serverMessages.get(99999L);// 参数修改成投注格式错误
        }

        //获取当前奖期信息并判断提交数据时奖期是否已经过期
        GameIssueQueryResponse gameIssue = getCurrentGameIssue();

        Long issueCode = dto.getOrders().get(0).getIssueCode();
        String webIssueCode = dto.getOrders().get(0).getNumber();
        if (dto.getIsTrace() == 0) {
            //不追号 判断投注是否为当前期
            if (issueCode.longValue() != gameIssue.getIssueCode().longValue()) {
                return betGameIssueError(gameIssue, webIssueCode);
            }
        } else {//追号 判断第一期是否>=当前期
            if (issueCode.longValue() < gameIssue.getIssueCode().longValue()) {
                return betGameIssueError(gameIssue, webIssueCode);
            }
        }

        Long status = 0L;
        if (dto.getIsTrace() == 0) {//不追号
            status = orderBet(dto, request);
        } else {//追号
            status = planBet(dto, request);
        }
        if (serverMessages.get(status) != null) {
            return serverMessages.get(status);
        }
        //保存选号球，假如存在暂停玩法，删掉暂停选号球返回
        List<GameBetBalls> balls = new ArrayList<GameBetBalls>();
        List<BetDetailStrucDTO> betBalls = dto.getBalls();
        GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
        GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
        GameBetTplData tplDate = new GameBetTplData();
        String strStatus = status + "";
        if (strStatus.length() == 12) {
            //投注方式暂停异常处理需要截取活的投注方式名称
            Integer gameGroupCode = Integer.valueOf(strStatus.substring(0, 2));
            Integer gameBetCode = Integer.valueOf(strStatus.substring(2, 4));
            Integer gameMethodCode = Integer.valueOf(strStatus.substring(4, 6));
            String betName = bnConvertor.getGameGroupTitle(gameGroupCode) + bnConvertor.getGameSetTitle(gameBetCode)
                    + bnConvertor.getBetMethodTitle(gameMethodCode);
            String betEnName = bnConvertor.getGameGroupName(gameGroupCode) + "."
                    + bnConvertor.getGameSetName(gameBetCode) + "." + bnConvertor.getBetMethodName(gameMethodCode);
            tplDate.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
            jds.setTplData(tplDate);
            hr.setIsSuccess(0);
            hr.setType("pauseBet");
            hr.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
            for (BetDetailStrucDTO ball : betBalls) {
                if (ball.getType().equals(betEnName)) {
                    GameBetBalls gb = new GameBetBalls();
                    gb.setBall(ball.getBall());
                    gb.setId(Long.valueOf(ball.getId()));
                    gb.setType(ball.getType());
                    balls.add(gb);
                }
            }
            tplDate.setBalls(balls);
            hr.setData(jds);
            return hr;
        } else {
            return handingBetStatus(status);
        }
    }


    *//**
     * @Title: orderBet
     * @Description:普通投注
     * @param dto
     * @param request
     * @return
     * @throws Exception
     *//*
    private Long orderBet(GameOrderRequestDTO dto, HttpServletRequest request) throws Exception {

        GameOrderRequest gr = new GameOrderRequest();

        gr.setLotteryid(lotteryId);
        gr.setUserIp(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));
        gr.setPackageAmount(formatMultipleMoney(dto.getAmount()));
        gr.setSaleTime(new Date().getTime());
        gr.setServerIp(IPConverter.ipToLong(getProperty("serverIP")));
        if (dto.getOrders() != null && !dto.getOrders().isEmpty()) {
            gr.setIssueCode(dto.getOrders().get(0).getIssueCode());
        }

        List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();

        for (BetDetailStrucDTO bd : dto.getBalls()) {
            BetDetailStruc bds = new BetDetailStruc();

            bds.setBetdetail(bd.getBall());
            bds.setFileMode(isFileMode(bd.getType()));
            String[] type = StringUtils.split(bd.getType(), '.');
            bds.setGameGroupCode(bnConvertor.getGameGroupCode(type[0]));
            bds.setGameSetCode(bnConvertor.getGameSetCode(type[1]));
            if (bnConvertor.getBetMethodCode(type[2]) != null) {
                bds.setBetMethodCode(bnConvertor.getBetMethodCode(type[2]));
            }
            bds.setIssueCode(gr.getIssueCode());
            bds.setMoneyMode(bd.getMoneyunit() == 0.1 ? 2 : 1);//页面传过来的值时0.1 为角模式，对应接口数据1元2角
            bds.setMultiple(bd.getMultiple());
            bds.setItemAmount(((Double) (2 * getLongProperty("moneyMultiple") * bd.getMoneyunit())).intValue());//元角模式单注金额
            bds.setTotbets(bd.getNum());//注数
            bds.setTotamount((long) (bds.getItemAmount() * bds.getTotbets() * bds.getMultiple()));
            bdss.add(bds);
        }
        gr.setBetDetailStruc(bdss);

        //验证
        GameOrder gameOrder = DTOConvert.convertGameOrderRequest2GameOrder(gr, RequestContext.getCurrUser().getId());
        long status = validate(gameOrder);
        if (status > -1l) {
            return status;
        }

        // 替换userid
        Long userId = RequestContext.getCurrUser().getId();
        String account = RequestContext.getCurrUser().getUserName();
        for (BetDetailStruc bs : gr.getBetDetailStruc()) {
            if (bs.getGameGroupCode() != 17) bs.setBetMethodCode(null);
        }

        Response<GameOrderResponse> gameOrderResponse = betHttpClient.saveGameOrder(gr, userId, account);

        return gameOrderResponse.getHead().getStatus();
    }

    *//**
     * @Title: planBet
     * @Description: 追号投注
     * @param dto
     * @param request
     * @return
     * @throws Exception
     *//*
    private Long planBet(GameOrderRequestDTO dto, HttpServletRequest request) throws Exception {

        GamePlanRequest gp = new GamePlanRequest();

        gp.setLotteryid(lotteryId);
        gp.setPlanAmount(formatMultipleMoney(dto.getAmount()));
        gp.setSaleTime(new Date().getTime());
        gp.setServerip(IPConverter.ipToLong(getProperty("serverIP")));
        gp.setStartIssueCode(dto.getOrders().get(0).getIssueCode());
        if (dto.getTraceWinStop() == 1) {//中奖即停
            gp.setStopMode(2);
            gp.setOptionParms("");
        } else if (dto.getTraceWinStop() == 2) {//盈利停止
            gp.setStopMode(1);
            gp.setOptionParms("");//数据库中此字段的值暂时未用到
        } else {//默认为中奖不停止
            gp.setStopMode(0);
            gp.setOptionParms("");
        }
        gp.setStopParms(formatMultipleMoney(dto.getTraceStopValue()));//金额需要乘以10000保存到数据库
        gp.setTotalIssue((long) dto.getOrders().size());
        gp.setUserip(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));

        List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc = new ArrayList<GamePlanBetOriginDataStruc>();//投注追号原始投注数据结构，解决追号投注数据重复问题
        for (int i = 0; i < dto.getBalls().size(); i++) {
            GamePlanBetOriginDataStruc ods = new GamePlanBetOriginDataStruc();
            ods.setBetdetail(dto.getBalls().get(i).getBall());
            String betMethodType = dto.getBalls().get(i).getType();
            String[] type = StringUtils.split(betMethodType, '.');
            ods.setFileMode(isFileMode(betMethodType));
            ods.setGameGroupCode(bnConvertor.getGameGroupCode(type[0]));
            ods.setGameSetCode(bnConvertor.getGameSetCode(type[1]));
            ods.setBetMethodCode(bnConvertor.getBetMethodCode(type[2]));
            ods.setMoneyMode(dto.getBalls().get(i).getMoneyunit() == 0.1 ? 2 : 1);//页面传过来的值时0.1 为角模式，对应接口数据1元2角
            ods.setMultiple(dto.getBalls().get(i).getMultiple());
            ods.setItemAmount(((Long) (2 * formatMultipleMoney(dto.getBalls().get(i).getMoneyunit()))).intValue());//元角模式单注金额
            ods.setTotbets(dto.getBalls().get(i).getNum());//注数
            ods.setTotamount((long) (ods.getItemAmount() * ods.getTotbets() * ods.getMultiple()));

            gamePlanBetOriginDataStruc.add(ods);
        }

        List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();
        for (int i = 0; i < dto.getBalls().size(); i++) {
            for (int j = 0; j < dto.getOrders().size(); j++) {
                BetDetailStruc bds = new BetDetailStruc();
                bds.setBetdetail(dto.getBalls().get(i).getBall());
                String betMethodType = dto.getBalls().get(i).getType();
                String[] type = StringUtils.split(betMethodType, '.');
                bds.setFileMode(isFileMode(betMethodType));
                bds.setGameGroupCode(bnConvertor.getGameGroupCode(type[0]));
                bds.setGameSetCode(bnConvertor.getGameSetCode(type[1]));
                bds.setBetMethodCode(bnConvertor.getBetMethodCode(type[2]));
                bds.setIssueCode(dto.getOrders().get(j).getIssueCode());
                bds.setMoneyMode(dto.getBalls().get(i).getMoneyunit() == 0.1 ? 2 : 1);//页面传过来的值时0.1 为角模式，对应接口数据1元2角
                bds.setMultiple(dto.getOrders().get(j).getMultiple());
                bds.setItemAmount(((Long) (2 * formatMultipleMoney(dto.getBalls().get(i).getMoneyunit()))).intValue());//元角模式单注金额
                bds.setTotbets(dto.getBalls().get(i).getNum());//注数
                bds.setTotamount((long) (bds.getItemAmount() * bds.getTotbets() * bds.getMultiple()));

                bdss.add(bds);
            }
        }

        gp.setBetDetailsStruc(bdss);
        gp.setBetOriginDataStruc(gamePlanBetOriginDataStruc);

        // 验证
        GameOrder gameOrder = DTOConvert.convertGamePlanRequest2GameOrder(gp, RequestContext.getCurrUser().getId());
        long status = validate(gameOrder);
        if (status > -1l) {
            return status;
        }

        // 替换userid
        Long userId = RequestContext.getCurrUser().getId();
        String account = RequestContext.getCurrUser().getUserName();
        for (BetDetailStruc bs : gp.getBetDetailsStruc()) {
            if (bs.getGameGroupCode() != 17) bs.setBetMethodCode(null);
        }
        for (GamePlanBetOriginDataStruc gbd : gp.getBetOriginDataStruc()) {
            if (gbd.getGameGroupCode() != 17) gbd.setBetMethodCode(null);
        }
        Response<GamePlanResponse> gamePlanResponse = betHttpClient.saveGamePlan(gp, userId, account);
        return gamePlanResponse.getHead().getStatus();

    }
    @Override
	public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
    	logger.info("queryNumberCharts start");

		HistoryBallsResultDTO result = new HistoryBallsResultDTO();
		HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
		String historyBallsHtml = "";
		
		try {
			historyBallsHtml = getHtmlContent(type, this.lotteryId);
			
			logger.info("queryNumberCharts end");
		} catch (Exception e) {
			logger.error("queryNumberCharts is error.", e);
			throw e;
		}
		historyBallsDTO.setHistoryBalls(historyBallsHtml);
		historyBallsDTO.setGameTips(new GameTips());
		historyBallsDTO.setFrequency(new ArrayList<List<NumberFrequencyCell>>());

		result.setData(historyBallsDTO);
		result.setIsSuccess(1);
		result.setMsg("success");
		result.setType("userError");

		return result;
    }
    
    */
    @Override
    public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
        logger.info("queryNumberCharts start");

        HistoryBallsResultDTO result = new HistoryBallsResultDTO();
        HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
        String historyBallsHtml = "";

        try {
            historyBallsHtml = getHtmlContent(type, this.lotteryId);

            logger.info("queryNumberCharts end");
        } catch (Exception e) {
            logger.error("queryNumberCharts is error.", e);
            throw e;
        }
        historyBallsDTO.setHistoryBalls(historyBallsHtml);
        historyBallsDTO.setGameTips(new GameTips());
        historyBallsDTO.setFrequency(new ArrayList<List<NumberFrequencyCell>>());

        result.setData(historyBallsDTO);
        result.setIsSuccess(1);
        result.setMsg("success");
        result.setType("userError");

        return result;
    }
    /**
	* @throws IOException
	* @Title: getHtmlContent
	* @Description: 生成页面HTML代码 
	* @param betMethodType
	* @param lotteryid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private String getHtmlContent(String betMethodType, Long lotteryid) throws IOException {
		URL url = new URL(webServerPath + "/gameTrend/createNumberChartsDomKl8" + "?betMethodType=" + betMethodType + "&lotteryid=" + lotteryid);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestProperty("contentType", "utf-8");
		InputStream inStream = conn.getInputStream();
		String data = readInputStream(inStream);
		String html = new String(data);
		return html;
	}

	/**
	* @Title: readInputStream 
	* @Description:  读取输入流
	* @param @param inStream
	 * @throws IOException 
	*/
	private String readInputStream(InputStream inStream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		String str = buffer.toString();
		return str;
	}
}
