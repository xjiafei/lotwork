package com.winterframework.firefrog.advert.web.dto;

import java.util.ArrayList;
import java.util.List;


public class StrucConvert {
	public static List<AdUnbingStruc> convertToStruc(String aimStr) {
		List<AdUnbingStruc> list = new ArrayList<AdUnbingStruc>();
		String[] aas = aimStr.split("}");
		AdUnbingStruc struc = null;
		for (String string : aas) {
			string = string.replaceAll("\\{", "");
			if (string.startsWith(",")) {
				string = string.substring(1);
			}
			struc = new AdUnbingStruc();
			String[] aaw = string.split(",");
			for (String string2 : aaw) {
				if (string2.startsWith("adSpaceId:")) {
					struc.setAdSpaceId(Long.valueOf(string2.replaceAll("adSpaceId:", "")));
				}

				if (string2.startsWith("advertId:")) {
					struc.setAdvertId(Long.valueOf(string2.replaceAll("advertId:", "")));
				}

				if (string2.startsWith("orders:")) {
					struc.setOrders(Long.valueOf(string2.replaceAll("orders:", "")));
				}

				if (string2.startsWith("isShown:")) {
					struc.setIsShown(Long.valueOf(string2.replaceAll("isShown:", "")));
				}
			}
			list.add(struc);
		}
		return list;
	}
}
