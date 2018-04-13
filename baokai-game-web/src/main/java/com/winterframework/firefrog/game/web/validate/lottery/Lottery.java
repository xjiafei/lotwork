package com.winterframework.firefrog.game.web.validate.lottery;

public class Lottery {

	private static final String SUFFIX = "_";

	public static String getLottery(int lotteryId, int groupCode, int setCode, int mothedCode) {

		StringBuilder builder = new StringBuilder();

		builder.append(LotteryType.LOTTERY_TYPES_MAP.get(lotteryId));
		builder.append(SUFFIX);
		builder.append(GroupCodeType.GROUP_CODE_TYPE_MAP.get(groupCode));
		builder.append(SUFFIX);
		builder.append(SetCodeType.SET_CODE_TYPE_MAP.get(setCode));
		builder.append(SUFFIX);
		builder.append(MethodCodeType.METHOD_CODE_TYPE_MAP.get(mothedCode));

		return builder.toString();

	}

	public static void main(String[] args) {
		System.out.println(Lottery.getLottery(99101, 10, 10, 10));
	}
}
