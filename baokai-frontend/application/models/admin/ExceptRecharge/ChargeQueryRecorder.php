<?php
class ChargeQueryRecorder extends  BaseModel{

	public function getDefaultMap(){
		
		return array(
				"sn" =>11,//MOW订号
				"applyTime" =>0 ,//发起时间
				"chargeTime"=>0,//处理类型
				"applyAmt" => 0, //金额
				"chargeAmt" => '',//用户名
				"status"=>0, //付款银行名称
				"bankId"=>0,//付款账号
				"chargeMemo"=>"",//付款卡号
				"account"=>"",//开户行
				"userAct"=>"",//开户行地址
				"cardNumber"=>"",//发起材料
				"mcFee"=>0,//状态
		);
	}
}