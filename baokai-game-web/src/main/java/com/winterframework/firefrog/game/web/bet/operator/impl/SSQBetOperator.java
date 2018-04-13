package com.winterframework.firefrog.game.web.bet.operator.impl;

import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.web.dto.*;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lex
 * @Title Bjkl8Operator
 * @Description 北京快乐8操作类
 */
public class SSQBetOperator extends AbstractBetOperator {
    private Logger logger = LoggerFactory.getLogger(SSQBetOperator.class);
    @PropertyConfig(value = "web.server.url")
    private String webServerPath;

    @Override
    protected String formatLastBalls(GameIssueQueryResponse gameIssue) {
        return gameIssue.getNumberRecord() == null ? "1,2,3,4,5,6,7,8" : gameIssue.getNumberRecord().replaceAll("\\+",
                ",");
    }

    @Override
    public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
        HistoryBallsResultDTO result = new HistoryBallsResultDTO();
        HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
        String historyBallsHtml = "";

        try {
            historyBallsHtml = getHtmlContent(type, this.lotteryId);

        } catch (Exception e) {
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
     * @param betMethodType
     * @param lotteryid     设定文件
     * @return void    返回类型
     * @throws java.io.IOException
     * @throws
     * @Title: getHtmlContent
     * @Description: 生成页面HTML代码
     */
    private String getHtmlContent(String betMethodType, Long lotteryid) throws IOException {
        URL url = new URL(webServerPath + "/gameTrend/createNumberChartsDomSSQ" + "?betMethodType=" + betMethodType
                + "&lotteryid=" + lotteryid);

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
     * @param @param inStream
     * @throws IOException
     * @Title: readInputStream
     * @Description: 读取输入流
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


    /**
     * @return
     * @throws Exception
     * @Title: frequency
     * @Description: 查询冷热遗漏号码
     */
    @Override
    public List<List<NumberFrequencyCell>> frequency(@RequestParam("gameMode") String gameMode,
                                                     @RequestParam("extent") String extent, @RequestParam("frequencyType") String frequencyType,
                                                     @RequestParam("line") Integer line, @RequestParam("lenth") Integer lenth) throws Exception {

        logger.info("query frequency start");
        Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>();

        if ("lost".equals(frequencyType)) {
            // 遗漏
            GameMissingValueQueryRequest request = makeGameMissingValueQueryRequest(gameMode, extent);
            try {
                response = betHttpClient.queryMissingValue(request);
                logger.info("查询遗漏结束");
            } catch (Exception e) {
                logger.error("查询遗漏错误", e);
                throw e;
            }
        } else if ("fre".equals(frequencyType)) {
            // 冷热
            GameColdAndHotNumbersQueryRequest request = makeGameColdAndHotNumbersQueryRequest(gameMode, extent);
            try {
                response = betHttpClient.queryColdAndHotNumbers(request);
                logger.info("查询冷热结束");
            } catch (Exception e) {
                logger.error("查询冷热错误", e);
                throw e;
            }
        }

        List<List<NumberFrequencyCell>> result = new ArrayList<List<NumberFrequencyCell>>();
        List<com.winterframework.firefrog.game.entity.GameLryl> list = response.getBody().getResult().getGameLrylNumbers();

        this.packData(gameMode,result, list); 

        return result;
    } 
    protected void packData(String gameMode,List<List<NumberFrequencyCell>> frequency,List<GameLryl> list) {
    	List<NumberFrequencyCell> l = new ArrayList<NumberFrequencyCell>();
    	NumberFrequencyCell cell=null;
    	for (int i = 0; i < 33; i++) {
            com.winterframework.firefrog.game.entity.GameLryl gl = list.get(i);
            cell = new NumberFrequencyCell();
            cell.setCurrentNum(gl.getLottNumber());
            cell.setPinlv(gl.getRetValue());
            l.add(cell);
        }

        List<NumberFrequencyCell> l1 = new ArrayList<NumberFrequencyCell>();
        for (int i = 33; i < 49; i++) {
            com.winterframework.firefrog.game.entity.GameLryl gl = list.get(i);
            cell = new NumberFrequencyCell();
            cell.setCurrentNum(gl.getLottNumber());
            cell.setPinlv(gl.getRetValue());
            l1.add(cell);
        } 
        frequency.add(l);
        if (gameMode.equals("biaozhuntouzhu.biaozhun.dantuo")) {
        	frequency.add(l);
        }
        frequency.add(l1);
    } 
}
