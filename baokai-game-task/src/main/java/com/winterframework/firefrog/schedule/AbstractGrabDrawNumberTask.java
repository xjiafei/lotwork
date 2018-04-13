package com.winterframework.firefrog.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameCheckDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.web.dto.GameDrawNumberRequest;
import com.winterframework.firefrog.game.web.dto.GameDrawNumberResponse;
import com.winterframework.firefrog.game.web.dto.SBLimitResponse;
import com.winterframework.firefrog.schedule.dto.SBWinNumber;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

public abstract class AbstractGrabDrawNumberTask { 
		private Logger log = LoggerFactory.getLogger(AbstractGrabDrawNumberTask.class);

		@Resource(name = "httpJsonClientImpl")
		private IHttpJsonClient httpClient;
		@PropertyConfig(value = "url.business.connect")
		private String serverPath;
		protected String briefCode;
		protected Long lotteryId;
		
		private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();
		
		@Resource(name ="gameIssueDaoImpl")
		protected IGameIssueDao gameIssueDao;

		@Resource(name ="gameIssueServiceImpl")
		protected IGameIssueService gameissueServiceImp;

		@Resource(name ="gameCheckDrawServiceImpl")
		protected IGameCheckDrawService gamecheckImp;
		
		@Resource(name ="configServiceImpl")
		protected IConfigService configimp;
		
		@Resource(name = "exportFileServiceImpl")
		private IExportFileService exportFileService;
		
		String randomSSC(String[] numbers,int count,String split){
			
			int numSize = numbers.length;
			
			Random random = new Random();
			
			StringBuffer sb  = new StringBuffer();
			
			for(int i=0;i<count;i++){
				sb.append(numbers[random.nextInt(numSize)]);
				if(i != (count-1)){
					sb.append(split);
				}
			}
			
			return sb.toString();
		}	
		
		public void execute() throws Exception {
			log.info("---begin GrabDrawNumberTask ----"+lotteryId);
			
			/**
			 * 获取当前截止销售 且未获取开奖号码的奖期
			 * 获取开奖号码(完成保存开奖号码逻辑)
			 * 开奖(另有task)
			 */
			List<GameIssue> issues =getGameIssue();
			for(GameIssue issue:issues){
				if(null!=issue){
					GameDrawNumberRequest bizReq=new GameDrawNumberRequest();
					bizReq.setLotteryId(issue.getLotteryid());
					bizReq.setIssueCode(issue.getIssueCode());
					bizReq.setIssue(issue.getWebIssueCode());
					bizReq.setBriefCode(briefCode);
					String drawTime= DateUtils.format(issue.getOpenDrawTime(), "yyyy-MM-dd HH:mm:ss");
					bizReq.setOpenDrawTime(drawTime);
					
					if(StringUtils.isEmpty(issue.getWebIssueCode()) 
							/*|| StringUtils.isEmpty(briefCode)*/ 
							|| StringUtils.isEmpty(drawTime)){
						log.error("jlsb get rng number Param error lotteryid={},issuecode={},drawtime={}",
									new Object[]{issue.getLotteryid(),issue.getIssueCode(),drawTime});
					}else if(issue.getOpenDrawTime().before(DateUtils.addSeconds(DateUtils.currentDate(), -20))){
						log.debug("jlsb get rng number expired. lotteryid={},issuecode={},drawtime={},curTime={}",
									new Object[]{issue.getLotteryid(),issue.getIssueCode(),drawTime,DateUtils.currentDate()});
					}else{
						
						Response<GameDrawNumberResponse> res = httpClient.invokeHttp(serverPath+"/game/getDrawNumber", bizReq, GameDrawNumberResponse.class);
						log.info("grab draw number:lotteryId="+issue.getLotteryid()+" issueCode="+issue.getIssueCode()  );
						String recordNumber = res.getBody().getResult().getNumber();
						String[] recordarray = recordNumber.split("\\|", -1);
						if(null != res && null != res.getHead() && res.getHead().getStatus() != 0L){
							log.error("Grab draw number failed."+bizReq.toString());
							Long trycount = issue.getTryGetNumberCount();
							if(trycount==null){
								trycount=0l;
							}
							//更新重作次數
							gameIssueDao.updateTry(++trycount, issue.getId());
						}else{
							List<SBWinNumber> list = new ArrayList<SBWinNumber>();
							if (recordarray.length > 1 && (issue.getLotteryid() == 99602L || issue.getLotteryid() == 99603L)){
								SBWinNumber winobj = new SBWinNumber ();
								winobj.setNumber(recordarray[0]);
								String winray = configimp.getConfigValueByKey("game", "sbwinray");
								SBLimitResponse relimit = jmapper.fromJson(winray, SBLimitResponse.class);
								if (relimit == null){
									relimit = new SBLimitResponse ();
									relimit.setThreshold("90");
									relimit.setSlipCount(10L);
								}
								winobj.setWinrate( gamecheckImp.checkSBreawrd(winobj.getNumber() , issue.getLotteryid(), issue.getIssueCode(), relimit.getSlipCount()).longValue());
								winobj.setThreshold((long) Math.abs(Float.valueOf(relimit.getThreshold()) - winobj.getWinrate()));
								if (winobj.getWinrate() > Float.valueOf(relimit.getThreshold())){
									
									list.add (winobj);
									for (int n = 1; n < recordarray.length ; n ++){
										SBWinNumber winobj1 = new SBWinNumber ();
										winobj1.setNumber(recordarray[n]);
										winobj1.setWinrate(gamecheckImp.checkSBreawrd(winobj1.getNumber() , issue.getLotteryid(), issue.getIssueCode(), relimit.getSlipCount()).longValue());
										winobj1.setThreshold((long)Math.abs(Float.valueOf(relimit.getThreshold()) - winobj1.getWinrate()));
										list.add (winobj1);
									}
									Collections.sort(list, new Comparator<SBWinNumber>() {
								            public int compare(SBWinNumber o1, SBWinNumber o2) {
								            	   return  Float.compare(o1.getThreshold(), o2.getThreshold());
								            }
								        });
									 recordNumber = list.get(0).getNumber();
									 for (SBWinNumber number : list){
										 log.info(issue.getIssueCode() + "---SBisthan----  " +  number.getNumber() + " and " + number.getWinrate());
									 }
									 log.info(issue.getLotteryid() + "---SBisthan---- end " + recordNumber + " issue" + issue.getIssueCode());
								}else{
									 recordNumber = recordarray[0];
								}
							}
							String sblimit = jmapper.toJson(list);
							gameissueServiceImp.addDrawResult(lotteryId, issue.getIssueCode(), recordNumber, new GameWarnIssueLog(), null, sblimit);
							  //倒出開獎號碼
						
							exportFileService.exportRngSB(lotteryId ,issue.getIssueCode(), recordNumber);
							
						}
					}
				}
			}
			
			log.info("---end GrabDrawNumberTask ----"+lotteryId);
		} 
		protected abstract List<GameIssue> getGameIssue() throws Exception;
		/**
		 * @return the briefCode
		 */
		public String getBriefCode() {
			return briefCode;
		}
		/**
		 * @param briefCode the briefCode to set
		 */
		public void setBriefCode(String briefCode) {
			this.briefCode = briefCode;
		}
		/**
		 * @return the lotteryId
		 */
		public Long getLotteryId() {
			return lotteryId;
		}
		/**
		 * @param lotteryId the lotteryId to set
		 */
		public void setLotteryId(Long lotteryId) {
			this.lotteryId = lotteryId;
		}
}
