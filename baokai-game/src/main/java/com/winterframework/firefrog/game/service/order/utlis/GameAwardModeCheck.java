package com.winterframework.firefrog.game.service.order.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Repository("gameAwardModeCheck")
public class GameAwardModeCheck {
	
	private Logger log = LoggerFactory.getLogger(GameAwardModeCheck.class);
	
	@PropertyConfig(value = "award.mode.groupId")
	private String str_groupId;
	
	@PropertyConfig(value = "award.mode.betTypeCode")
	private String str_betTypeCode;
	
	public boolean checkAwardMode(String groupId,String betTypeCode){
		boolean id = false;
		boolean checkAward = false;
			String[] groupIdList = str_groupId.split(",");
			String[] betTypeCodeList = str_betTypeCode.split(",");
			for(String group : groupIdList){
				if(group.equals(groupId)){
					id = true;
				}
			}
			if(id){
				for(String betType : betTypeCodeList){
					if(betType.equals(betTypeCode)){
						return false;
					}else{
						checkAward = true;
					}
				}
			}
		return checkAward;
	}
}
