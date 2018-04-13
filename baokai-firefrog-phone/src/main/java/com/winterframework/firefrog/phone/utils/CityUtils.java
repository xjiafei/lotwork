package com.winterframework.firefrog.phone.utils;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.phone.web.cotroller.dto.Citys;

public class CityUtils {

	public List<Citys> getProvince(String id){
		List<Citys> list = new ArrayList<Citys>();
		
		City.CITY_MAP.get("");
		
		return list;
	}
	
	
	public static void main(String[] args) {
		System.out.println(City.CITY_MAP.get("101050101"));
	}
}
