package com.winterframework.firefrog.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.web.util.JsonMapper;

public class JsonUtil {
	protected static  JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	/**
	 * json数据结构转换对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr, clazz);
	}
	/**json数据结构转换List<MyBean>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJson2List(String jsonStr,Class<T> clazz){
		return jmapper.fromJson(jsonStr,
				JsonMapper.nonDefaultMapper().createCollectionType(List.class, clazz));
	}
	public static <T,R> Map<T,R> fromJson2Map(String jsonStr,Class<T> clazz1,Class<R> clazz2){
		return jmapper.fromJson(jsonStr, jmapper.createCollectionType(HashMap.class, clazz1,clazz2));
	}
	
	/**
	 * 对象转换json数据结构
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		return jmapper.toJson(obj);
	}
	
	public static void main(String[] args){
		/*String s="[{\"level\":\"3\",\"time\":\"1428551739622\"},{\"level\":\"4\",\"time\":\"1428551739623\"}]";
		List<DeviceSignalRecordRequest> ss=fromJson2List(s, DeviceSignalRecordRequest.class);
		
		String sss=toJson(new DeviceSignalRecordRequest());
		System.out.println(ss);*/
		IndexLottery indexLottery=new IndexLottery();
			indexLottery.setLottery("testtttt");
		indexLottery.setLotteryId(99111L);
		Map<String,String> mm=new HashMap<String,String>();
		mm.put("aa", "111");
		indexLottery.setWins(mm);
		
		
		String[] ss={"aa","bb"};
		System.out.println(JsonUtil.toJson(indexLottery));
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("a", 11);
		map.put("b", 22);
		List<String> a=new ArrayList<String>();
		List<String> b=new ArrayList<String>();
		List<List<String>> cc =new ArrayList<List<String>>();
		a.add("11:00");
		a.add("12:00");
		b.add("13:00");
		b.add("14:00");
		cc.add(a);
		cc.add(b);
		System.out.println(JsonUtil.toJson(map));
		Map<String,Integer>   k=JsonUtil.fromJson2Map(JsonUtil.toJson(map),String.class,Integer.class);
		for(Map.Entry<String,Integer> s:k.entrySet()){
			System.out.println(s.getKey()+"  "+s.getValue());
		}
	}
	
}
