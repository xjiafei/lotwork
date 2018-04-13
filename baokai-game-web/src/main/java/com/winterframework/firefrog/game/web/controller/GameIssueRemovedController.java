package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dto.GameIssueRemoveDto;

@Controller("gameIssueRemovedController")
@RequestMapping(value = "/gameRemove")
public class GameIssueRemovedController {

	private Logger log = LoggerFactory.getLogger(GameIssueRemovedController.class);
	
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	
	@RequestMapping(value = "/gameIssueRemoved")
	public ModelAndView removeIssue(@RequestParam("lotteryid") Long lotteryid){
		ModelAndView view = new ModelAndView("operations/gameIssue/removeGameIssue");
		String[] timeList = null ;
		String awardTime = null,gameTime = null,orderTime = null;
		
		if(lotteryid == null){
			lotteryid = 99101l;
		}
		String strTime = redis.hget("removeIssueList", lotteryid.toString());
		log.info("removeTime = "+strTime+(strTime != "close"));
		if(strTime != null){
			if(strTime != "close"){
				timeList = strTime.split(",");
				if(timeList.length>=2){
					awardTime = DateUtils.format(DateUtils.addHours(DateUtils.parse(timeList[0],"yyyy-MM-dd HH:mm:ss"),8),"yyyy-MM-dd HH:mm:ss");
					gameTime = DateUtils.format(DateUtils.addHours(DateUtils.parse(timeList[1],"yyyy-MM-dd HH:mm:ss"),8),"yyyy-MM-dd HH:mm:ss");
					orderTime = DateUtils.format(DateUtils.addHours(DateUtils.parse(timeList[2],"yyyy-MM-dd HH:mm:ss"),8),"yyyy-MM-dd HH:mm:ss");
				}
			}
		}

		view.addObject("lotteryId", lotteryid);
		view.addObject("awardTime", awardTime);
		view.addObject("gameTime", gameTime);
		view.addObject("orderTime", orderTime);
		return view;
	}
	
	
	@RequestMapping(value = "/updateIssueRemovedTime")
	@ResponseBody
	public String updateIssueRemovedTime(@RequestParam("lotteryid") Long lotteryid,@RequestParam("awardTime") String awardTime,
			@RequestParam("gameTime") String gameTime,@RequestParam("orderTime") String orderTime,@RequestParam("status") int status){
		
		
		if(status == 0){
			redis.hset("removeIssueList",lotteryid.toString(), "close");
		}else{
			if(awardTime != "" || gameTime != "" || orderTime != ""){
				//轉換格林威治標準時間
				awardTime = changeGmtTime(awardTime);
				gameTime = changeGmtTime(gameTime);
				orderTime = changeGmtTime(orderTime);
				StringBuilder str = new StringBuilder();
				str.append(awardTime+","+gameTime+","+orderTime);
				redis.hset("removeIssueList",lotteryid.toString(), str.toString());
				return "true";
			}else{
				return "false";
			}
		}
		return "true";
	}
	
	@RequestMapping(value = "/queryIssueRemovedTime")
	@ResponseBody
	public List<GameIssueRemoveDto> queryIssueRemovedTime(){
		List<GameIssueRemoveDto> removeList = new ArrayList<GameIssueRemoveDto>();
		Map<String,String> allremoveList = redis.hgetAll("removeIssueList");
		for(Entry<String, String> remove : allremoveList.entrySet()){
			GameIssueRemoveDto issueRemove = new GameIssueRemoveDto();
			issueRemove.setLotteryId(Long.parseLong(remove.getKey()));
			String strTime = remove.getValue();
			if(strTime != null){
				if(strTime != "close"){
					String[] timeList = strTime.split(",");
					if(timeList.length>=2){
						issueRemove.setAwardTime(timeList[0].replaceFirst(" ", "T"));
						issueRemove.setGameTime(timeList[1].replaceFirst(" ", "T"));
						issueRemove.setOrderTime(timeList[2].replaceFirst(" ", "T"));
					}
				}
			}
			removeList.add(issueRemove);
		}
		return removeList;
	}
	
	private static String changeGmtTime(String date){
		long mTime = DateUtils.parse(date,"yyyy-MM-dd HH:mm:ss").getTime();
		int offset = Calendar.getInstance().getTimeZone().getRawOffset();
		return DateUtils.format(new Date(mTime - offset),"yyyy-MM-dd HH:mm:ss");
	}
}
