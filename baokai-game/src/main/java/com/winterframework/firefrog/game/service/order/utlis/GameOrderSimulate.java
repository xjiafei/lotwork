package com.winterframework.firefrog.game.service.order.utlis;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePackage;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GameSlip;

public class GameOrderSimulate {

	public GameOrderSimulate() {
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameOrder go = new GameOrder();
		go.setId(111111L);
		GamePackage gp = new GamePackage();
		gp.setPackageCode("hhaa");
		go.setGamePackage(gp);
		
		
		System.out.println("------------------");
		
		GameOrder go2 = new GameOrder();
		
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
			BeanUtils.copyProperties(go2, go);
			
			System.out.println(go2.getId());
			
			System.out.println(go2.getGamePackage().getPackageCode());
			
			for(int i=0;i<500;i++){
				System.out.println(i);
			}
			
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				

	}
	
	public static GamePackage createdGP(GamePackage gp){
		GamePackage newGs = new GamePackage();
		
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);   
			BeanUtils.copyProperties(newGs, gp);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newGs;
	}
	
	public static GamePackageItem createdGPI(GamePackageItem gp){
		GamePackageItem newGs = new GamePackageItem();
		
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);   
			BeanUtils.copyProperties(newGs, gp);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newGs;
	}
	
	public static GameSlip createdGS(GameSlip gs){
		GameSlip newGs = new GameSlip();
		
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);   
			BeanUtils.copyProperties(newGs, gs);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newGs;
	}
	
	public static GameOrder createGO(GameOrder go){
		GameOrder newGo = new GameOrder();
		long lotteryid = go.getLottery().getLotteryId();
		GameBetType gbt = new GameBetType();
		
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);   
			BeanUtils.copyProperties(newGo, go);
			
			
		if (Long.valueOf(99201L).equals(lotteryid)) {
			return null;
		}else if (Long.valueOf(99108L).equals(lotteryid) || Long.valueOf(99107L).equals(lotteryid)) {
			gbt = new GameBetType(12,10,10);
		} else if (lotteryid == 99301L
				|| lotteryid == 99302L
				|| lotteryid == 99303L
				|| lotteryid == 99304L
				|| lotteryid == 99305L
				|| lotteryid == 99306L
				|| lotteryid == 99307L
				) 
		{
			gbt = new GameBetType(26,24,10);
		}else if (lotteryid == 99401L){
			gbt = new GameBetType(32,71,67);
		}else{
			gbt = new GameBetType(10,10,10);
		}
		
		String betDET = getNumRecord(lotteryid);
		
		newGo.setTotalAmount(20000L);
		newGo.setGamePlanId(null);
		newGo.setGamePlanDetailId(null);
		List<GameSlip> gs = newGo.getSlipList();
		GameSlip gs0 = gs.get(0);
		GameSlip gs1 = createdGS(gs0);
		gs1.setBetDetail(betDET);
		gs1.setMultiple(1);
		gs1.setTotalAmount(Long.valueOf(20000L));
		gs1.setTotalBet(1L);
		gs1.setGameBetType(gbt);
		
		gs.remove(0);
		gs.add(gs1);
		
		
		
		
		newGo.setSlipList(gs);
		
		GamePackage gp = newGo.getGamePackage();
		gp.setPackageAmount(20000L);
		List<GamePackageItem> gpi0 = gp.getItemList();
		GamePackageItem gpi = gpi0.get(0);
		GamePackageItem gpi2 = createdGPI(gpi);
		gpi2.setGameBetType(gbt);
		gpi2.setMultiple(1);
		gpi2.setBetDetail(betDET);
		gpi2.setTotbets(1L);
		gpi2.setTotamount(20000L);
	
		gpi0.remove(0);
		gpi0.add(gpi2);
		gp.setItemList(gpi0);
		
		newGo.setGamePackage(gp);
		
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return newGo;
	}
	
	public static String getNumRecord(long lotteryid){
		
		String numberRecord =  new String();
		
		if (Long.valueOf(99201L).equals(lotteryid)) {
			numberRecord = bubbleSort(random11X5(new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10",
																		  "11","12","13","14","15","16","17","18","19","20",
																			"21","22","23","24","25","26","27","28","29","30",
																			"31","32","33","34","35","36","37","38","39","40",
																			"41","42","43","44","45","46","47","48","49","50",
																			"51","52","53","54","55","56","57","58","59","60",
																			"61","62","63","64","65","66","67","68","69","70",
																			"71","72","73","74","75","76","77","78","79","80"
																		   )),20,","));
		}else if (Long.valueOf(99108L).equals(lotteryid) || Long.valueOf(99107L).equals(lotteryid)) {
			numberRecord = randomSSC(new String[]{"0","1","2","3","4","5","6","7","8","9"},3,",");
		} else if (lotteryid == 99301L
				|| lotteryid == 99302L
				|| lotteryid == 99303L
				|| lotteryid == 99304L
				|| lotteryid == 99305L
				|| lotteryid == 99306L
				|| lotteryid == 99307L
				) {
			numberRecord = random11X5(new ArrayList<String>(Arrays.asList("01","02","03","04","05","06","07","08","09","10","11")),5,",");
		}else if (lotteryid == 99401L){
			numberRecord = "01,02,03,10,13,33+13";
		}else{
			numberRecord = randomSSC(new String[]{"0","1","2","3","4","5","6","7","8","9"},5,",");
		}
		
		return numberRecord;
	}
	
	
	/**
	 *获取随机开奖号码
	 * @param anumberRecord
	 * @return
	 */
	public static String randomSSC(String[] numbers,int count,String split){
		
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
	
	/**
	 *获取随机开奖号码
	 * @param anumberRecord
	 * @return
	 */
	public static String random11X5(List<String> numbers,int count,String split){
		
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		
		StringBuffer sb  = new StringBuffer();
		
		for(int i=0;i<count;i++){
			
			sb.append(getAvaiNum(map,numbers));
			
			if(i != (count-1)){
				sb.append(split);
			}
		}
		
		return sb.toString();
	}

	/**
	 * 获取数组中不重的下标
	 * @param map
	 * @param numbers
	 * @return
	 */
	public static String getAvaiNum(Map<Integer,Integer> map,List<String> numbers){
		
		
		int numSize = numbers.size();
		Random random = new Random();
		int numbersPos = random.nextInt(numSize-1);
		
		String retStr = numbers.get(numbersPos);
		
		numbers.remove(numbersPos);
		
		
		return retStr;
	}
	
	/**
	 * 排序
	 * @param numbers
	 * @return
	 */
	public static String bubbleSort(String numbers){
		
		String[] numStrList = numbers.split(",");
		
		int numStrListSize = numStrList.length;
		
		StringBuffer sb = new StringBuffer();
		
		int[] abc = new int[numStrListSize];
		
		int i = 0;
		
		for(String nums:numStrList){
			abc[i] = Integer.parseInt(nums);
			i++;
		}
		
		i = 0;
		Arrays.sort(abc);  //进行排序
		for(int ii: abc){
			   if(ii < 10){
				   sb.append("0" + ii);
			   }else{
				   sb.append(ii);
			   }
			   
			   if(i != (numStrListSize -1)){
				   sb.append(",");
			   }
			   i++;
		}
		return sb.toString();
	}

}
