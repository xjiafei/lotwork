<?php
class ExceptRechargeRecorder extends  BaseModel{

/*	protected function getTestData(){ //测试数据

		return "application/testdata/ExceptRecharge.php" ;
	}*/

	public function getDefaultMap(){
		
		return array(
				"mcExactTime" =>888888 ,//用户充值时间
				"bankId"=>"默认数据", //付款银行名称
				"cardAcct"=>"默认数据",//付款账号
				"cardNumber"=>"默认数据",//付款卡号
				"bankName"=>"默认数据",//开户行
				"bankAddr"=>"默认数据",//开户行地址
				"recBankEmail"=>"默认数据",//收款email
				"exceptionOrderNo"=>8888,
				"id"=>88888,
				"userId"=>"默认数据",
				"rcvCardNumber"=>88888,
				"rcvAccName"=>"默认数据",
				"rcvEmail" =>"默认数据" ,
				"rcvBank" => '1',
				"realChargeAmt"=>88888,
				"mcNoticeTime"=>888888,
				"status"=>0,//状态
				"memo"=>"默认数据",//付款附言
				"mcFee" =>888888,//充值手续费
				"sn" => "默认数据", 
				"mcChannel"=>888888,//充值通道
				"mcArea"=>"默认数据",
				"mcSn" =>888888,//MOW订号
				"mcBankFee"=>88888,
				"attachment"=>"默认数据",//发起材料（附件）
				"applyMemo"=>"默认数据",//申请备注
				"applyTime"=>"默认数据",//申请时间
				"apprTime"=>"默认数据",//审核时间
				"apprAccount"=>"默认数据", //审核人
				"refundAmt"=>88888,
				"applyMemo"=>"默认数据",
				"applyTime"=>88888,
				"applyAccount"=>"默认数据", //申请人
				"mcSecondNoticeTime"=>"",
				"userChain"=>"",
				"currApprer"=>"",
				"currDate"=>"",
		);
	}
}