package com.winterframework.firefrog.fund.dao.vo;

public enum ChargeCountType {
	CHARGECOUTE_DOWN("网银充值","chargeCountDown"),
	CHARGECOUTE_CAIFU("财付通","chargeCouteCaifu"),
	CHARGECOUTE_THIRED("快捷充值","chargeCouteThired"),
	CHARGECOUTE_UNIONPAY("银联充值","chargeCouteUnionpay"),
	CHARGECOUTE_ALIPAY("支付宝企业版","chargeCouteAlipayBi"),
	CHARGECOUTE_ALIPAYBI("支付宝个人版","chargeCouteAlipay"),
	CHARGECOUTE_WECHAT("微信支付","chargeCouteWechat");

	private String name ;
    private String value ;
  
     
    private ChargeCountType(String name,String value){
        this.value = value ;
        this.name = name ;
    }

    
    public static String getValue(String val) {
    	
    	if(ChargeCountType.CHARGECOUTE_ALIPAY.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_ALIPAY.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_ALIPAYBI.getName().equals(val)){
    		return ChargeCountType.CHARGECOUTE_ALIPAYBI.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_CAIFU.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_CAIFU.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_DOWN.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_DOWN.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_THIRED.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_THIRED.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_UNIONPAY.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_UNIONPAY.getValue();
    	}else if(ChargeCountType.CHARGECOUTE_WECHAT.getName().equals(val)) {
    		return ChargeCountType.CHARGECOUTE_WECHAT.getValue();
    	}
    	return null;
    }
    
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
   
    

	
	
}
