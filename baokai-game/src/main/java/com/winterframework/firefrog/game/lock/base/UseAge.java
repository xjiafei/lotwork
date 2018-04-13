package com.winterframework.firefrog.game.lock.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseAge {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,Method> maps=new HashMap<String,Method>();
		//定义betContent
		String betContent="betContent";
		String methodId="methodId";
	
		//根据玩法id获取
		Method m=maps.get(methodId);
		m.setBetContent(betContent);
		m.setMethodId(methodId);
		List<MethodPackage> pkgs=new ArrayList<MethodPackage>();
		if(m instanceof MultMethod){
			pkgs=((MultMethod)m).toSingleMethod(betContent);
			
		}else{
			pkgs.add(new MethodPackage(m.getMethodId(),m.influncePoint()));
		}

	}

}
