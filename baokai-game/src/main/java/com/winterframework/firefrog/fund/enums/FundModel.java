package com.winterframework.firefrog.fund.enums;

public class FundModel {

	public static EnumItem getEnumItemBySummary(String model, String summary) {
		return FundModelTool.getEnumItemBySummary(model, summary);
	}

	public enum FD implements EnumModel {
		DEFAULT;
		public enum AUTO implements EnumFunction {
			DEFAULT;
			public enum ITEMS implements EnumItem {
				//自动充值  充值中，订单关闭，充值成功
				AUTO(FD.DEFAULT, "ADAL", null, 1, MethodEnum.None), CLOSED(FD.DEFAULT, "ADAL", null, 2, MethodEnum.None), SUCCESS(
						FD.DEFAULT, "ADAL", null, 3, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "自动充值";
			}
		}
		public enum MDAX implements EnumFunction {
			DEFAULT;
			public enum ITEMS implements EnumItem {
				//自动充值  充值中，订单关闭，充值成功
				MDAX1(FD.DEFAULT, "MDAX", null, 1, MethodEnum.None),
				MDAX2(FD.DEFAULT, "MDAX", null, 2, MethodEnum.None),
				MDAX3(FD.DEFAULT, "MDAX", null, 3,MethodEnum.None),
				MDAX4(FD.DEFAULT, "MDAX", null, 4, MethodEnum.None),
				MDAX5(FD.DEFAULT, "MDAX", null, 5, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "大大客户充值";
			}
		}

		/**
		 * 异常充值
		 * @author heny
		 */
		public enum ABDX implements EnumFunction {
			DEFAULT;
			public enum ITEMS implements EnumItem {
				//异常充值  
				NEW(FD.DEFAULT, "ADML", null, 1, MethodEnum.None), RECHECK(FD.DEFAULT, "ADML", null, 2, MethodEnum.None), PAYING(
						FD.DEFAULT, "ADML", null, 3, MethodEnum.None), REJECTED(FD.DEFAULT, "ADML", null, 4,
						MethodEnum.None), PAYED(FD.DEFAULT, "ADML", null, 5, MethodEnum.addAmt), PARTPAIE(FD.DEFAULT,
						"ADML", null, 6, MethodEnum.None), PAYFAILED(FD.DEFAULT, "ADML", null, 7, MethodEnum.None), DDCOINED(
						FD.DEFAULT, "ADML", null, 8, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}

				@Override
				public EnumModel getModel() {

					return this.model;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		/**
		 * 自动体现
		 * @author heny
		 *
		 */
		public enum CWXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				//自动体现
				CWXX(FD.DEFAULT, "CWTF", null, 1, MethodEnum.frozenAmtWithBal),
				CHECK(FD.DEFAULT, "CWTR", null, 2,MethodEnum.None), 
				RECHECK(FD.DEFAULT, "CWTR", null, 3, MethodEnum.None), 
				REJECTED(FD.DEFAULT,"CWTR", null, 4, MethodEnum.unFrozenAmtWithBal), 
				SUCCESS(FD.DEFAULT, "CWTS", null, 5,MethodEnum.cleanFrozenAmt),
				PART_REDUCE(FD.DEFAULT, "CWTS", null, 6,MethodEnum.cleanFrozenAmt),
				FAILED(FD.DEFAULT, "CWTR", null, 7, MethodEnum.unFrozenAmtWithBal),
				PART_REFUND(FD.DEFAULT, "CWTR", null, 8, MethodEnum.unFrozenAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private Integer status;
				private String summaryCode;
				private EnumModel model;

				@Override
				public EnumModel getModel() {

					return this.model;
				}

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}

			}

			@Override
			public String getName() {

				return "用户提现";
			}
		}

		/**
		 * 人工体现
		 * @author heny
		 *
		 */

		public enum MWXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				//人工体现
				PROCESSING(FD.DEFAULT, "CWCF", null, 1, MethodEnum.frozenAmtWithBal), 
				PROCESS_2(FD.DEFAULT, "CWCR", null, 2,
						MethodEnum.None), 
						REJECTED(FD.DEFAULT, "CWCR", null, 3, MethodEnum.unFrozenAmtWithBal), 
						SUCCESS(
						FD.DEFAULT, "CWCS", null, 4, MethodEnum.cleanFrozenAmt), 
						PART_REDUCE(FD.DEFAULT, "CWCS",null, 5, MethodEnum.cleanFrozenAmt), 
						FAILED(FD.DEFAULT, "CWCR", null, 6,
						MethodEnum.unFrozenAmt),
						PROCESS_7(FD.DEFAULT, "CWCF", null, 7, MethodEnum.None), 
						PART_RETURN(
						FD.DEFAULT, "CWCR", null, 8, MethodEnum.unFrozenAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public EnumModel getModel() {

					return this.model;
				}

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "人工提现";
			}
		}

		@Override
		public String getCode() {

			return "FD";
		}

	}

	public enum GM implements EnumModel {
		DEFAULT;
		public enum DVCB implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				// 订单
				DVCB(GM.DEFAULT, "DVCB", null, 1, MethodEnum.frozenAmt), DVCB_2(GM.DEFAULT, "DVCB", null, 2,
						MethodEnum.cleanFrozenAmtWithBetBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "投注";
			}
		}

		public enum DVCN implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				//追号单
				DVCN(GM.DEFAULT, "DVCN", null, 1, MethodEnum.frozenAmt), DVCN_2(GM.DEFAULT, "DVCN",
						"PDVC", 2, MethodEnum.cleanFrozenAmtWithBetBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "追号";
			}
		}

		public enum RSXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				//确认派奖时（方案号），投注本人所得的返点
				//确认派奖时（方案号），投注人所属的不同上级，分别获得的返点记录
				RSXX(GM.DEFAULT, "RSXX", null, 1, MethodEnum.addAmtWithBal), 
				RHAX(GM.DEFAULT, "RHAX", null, 2,	MethodEnum.addAmtWithBal),
				RDAX(GM.DEFAULT, "RDAX", null, 3, MethodEnum.addAmtWithBal);
				
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum RRSX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				//撤销返点操作时，扣除的投注本人所得的返点
				//撤销返点操作时，扣除的投注人所属的不同上级，分别获得的返点记录
				RRSX(GM.DEFAULT, "RRSX", null, 1, MethodEnum.reduceAmt), 
				RRHA(GM.DEFAULT, "RRHA", null, 2, MethodEnum.reduceAmt),
				RRDA(GM.DEFAULT, "RRDA", null, 3, MethodEnum.reduceAmt), 
				DDAX(GM.DEFAULT, "DDAX", null, 4, MethodEnum.reduceAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum PDXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PDXX_1(GM.DEFAULT, "PDXX", null, 1, MethodEnum.None), PDXX_2(GM.DEFAULT, "PDXX", null, 2,
						MethodEnum.None), PDXX_3(GM.DEFAULT, "PDXX",null, 3, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum BDRX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				BDRX(GM.DEFAULT, "BDRX", null, 1, MethodEnum.reduceAmtForbidden);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum CRVC implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				CRVC(GM.DEFAULT, "CRVC", null, 1, MethodEnum.unFrozenAmt),
				CRVC_2(GM.DEFAULT, "CRVC", null, 2, MethodEnum.unFrozenAmt),
				CRVC_3(GM.DEFAULT, "CRVC", null, 3, MethodEnum.unFrozenAmt),
				CRVC_4(GM.DEFAULT, "CRVC", null, 4, MethodEnum.addAmyRefund),
				CRVC_5(GM.DEFAULT, "CRVC", null, 5, MethodEnum.addAmyRefund),
				CRVC_6(GM.DEFAULT, "CRVC", null, 6, MethodEnum.unFrozenAmt);
				
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum RVCP implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				RVCP(GM.DEFAULT, "RVCP", "RVCP", 1, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum CFCX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				CFCX(GM.DEFAULT, "CFCX", null, 1, MethodEnum.reduceAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "异常充值";
			}
		}

		public enum DDPT implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				SUCCESS(GM.DEFAULT, "DDPT", null, 1, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "PT佣金";
			}
		}
		
		public enum DDAX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				SUCCESS(GM.DEFAULT, "DDAX", null, 1, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "彩票分红";
			}
		}
		@Override
		public String getCode() {

			return "GM";
		}

	}

	//活动手续费返还
	public enum PM implements EnumModel {
		DEFAULT;
		public enum RBRC implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				RBRC(PM.DEFAULT, "RBRC", null, 1, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "用户提现";
			}
		}
		public enum PGPT implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				SUCCESS(PM.DEFAULT, "PGPT", null, 1, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "PT活动礼金";
			}
		}
		public enum PGAP implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				SUCCESS(PM.DEFAULT, "PGAP", null, 1, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "PT自動轉帳活动礼金";
			}
		}
		public enum PGXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PGXX(PM.DEFAULT, "PGXX", null, 1, MethodEnum.None), PGXX_2(PM.DEFAULT, "PGXX", null, 2, MethodEnum.None), PGXX_3(
						PM.DEFAULT, "PGXX", null, 3, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "活动礼金";
			}
		}

		public enum IPXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PROCESS(PM.DEFAULT, "IPXX", null, 1, MethodEnum.None), REJECTED(PM.DEFAULT, "IPXX", null, 2,
						MethodEnum.None), SUCCESS(PM.DEFAULT, "IPXX", "IPXX", 3, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "平台奖励";
			}
		}

		@Override
		public String getCode() {

			return "PM";
		}

	}

	public enum TF implements EnumModel {
		DEFAULT;
		public enum TAIX implements EnumFunction {
			DEFAULT;
			public enum ITEMS implements EnumItem {
		
				SOSX(TF.DEFAULT, "SOSX", null, 1, MethodEnum.reduceAmt), 
				BIRX(TF.DEFAULT, "BIRX", null, 2,	MethodEnum.addAmt),
				WPXX(TF.DEFAULT, "SOSX", null, 3, MethodEnum.reduceAmt), 
				RRXX(TF.DEFAULT, "BIRX", null, 4,	MethodEnum.addAmt),
				TFTP(TF.DEFAULT, "TFTP", null, 5, MethodEnum.reduceAmt),
				TFTM (TF.DEFAULT, "TFTM", null, 6, MethodEnum.addAmtWithBal);
		/*		TFRE (TF.DEFAULT, "TFRE", null, 7, MethodEnum.addAmt), 
				TFBU (TF.DEFAULT, "TFBU", null, 8, MethodEnum.addAmt); */
				
			
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "用户转账";
			}
		}

		public enum DIXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				DIXX(TF.DEFAULT, "DIXX", "SCD", 1, MethodEnum.None), AIXX(TF.DEFAULT, "AIXX", "SCR", 2, MethodEnum.None);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "不活跃用户转账";
			}
		}
		public enum TFXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				TPXX(TF.DEFAULT, "TPXX", null, 1, MethodEnum.addAmtWithBal),TPXX2(TF.DEFAULT, "TPXX", null,2, MethodEnum.reduceAmtWithSingleBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					////this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "不活跃用户转账";
			}
		}

		@Override
		public String getCode() {

			return "TF";
		}

	}

	public enum OT implements EnumModel {
		DEFAULT;
		public enum BANB implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				BANB(OT.DEFAULT, "BANB", "BANB", 1, MethodEnum.None), BAPB(OT.DEFAULT, "BAPB", null, 1,
						MethodEnum.None);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}

				@Override
				public EnumModel getModel() {

					return this.model;
				}
			}

			@Override
			public String getName() {

				return "余额调整";
			}
		}

		public enum CEXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PROCESS(OT.DEFAULT, "CEXX", null, 1, MethodEnum.None), REJECTED(OT.DEFAULT, "CEXX", null, 2,
						MethodEnum.None), SUCCESS(OT.DEFAULT, "CEXX", null, 3, MethodEnum.addAmtWithBal);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private EnumModel model;
				private String tradeCode;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "客户理赔";
			}
		}

		/**
		 * 人工扣币
		 * @author heny
		 *
		 */
		public enum DAXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PROCESS(OT.DEFAULT, "DAXX", null, 1, MethodEnum.None), REJECTED(OT.DEFAULT, "DAXX", null, 2,
						MethodEnum.None), SUCCESS(OT.DEFAULT, "DAXX", "DVCM", 3, MethodEnum.reduceAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "人工扣币";
			}
		}

		/**
		  * 人工 加币
		  * @author heny
		  *
		  */
		public enum AAXX implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PROCESS(OT.DEFAULT, "AAXX", null, 1, MethodEnum.None), REJECTED(OT.DEFAULT, "AAXX", null, 2,
						MethodEnum.None), SUCCESS(OT.DEFAULT, "AAXX", null, 3, MethodEnum.addAmt);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "人工扣币";
			}
		}

		public enum RGDK implements EnumFunction {
			;
			public enum ITEMS implements EnumItem {
				PROCESS(OT.DEFAULT, "RGDK", null, 1, MethodEnum.None), REJECTED(OT.DEFAULT, "RGDK", null, 2,
						MethodEnum.None), SUCCESS(OT.DEFAULT, "RGDK", null, 3, MethodEnum.None);
				private ITEMS(EnumModel model, String summaryCode, String tradeCode, Integer status, MethodEnum me) {
					this.model = model;
					//this.tradeCode = tradeCode;
					this.summaryCode = summaryCode;
					this.status = status;
					this.me = me;
				}

				private MethodEnum me;

				public MethodEnum getMethod() {
					return me;
				};

				private String tradeCode;
				private EnumModel model;
				private Integer status;
				private String summaryCode;

				@Override
				public String getTradeCode() {

					return tradeCode;
				}

				public EnumModel getModel() {
					return model;
				}

				@Override
				public String getSummaryCode() {

					return summaryCode;
				}

				@Override
				public Integer getTradeStatus() {

					return this.status;
				}
			}

			@Override
			public String getName() {

				return "人工扣币";
			}
		}

		@Override
		public String getCode() {

			return "OT";
		}

	}
	
}
