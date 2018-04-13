<?php
class CustomersDrawRecorder extends BaseModel{

	protected function getTestData(){ //测试数据
		return "application/testdata/customersDraw.php" ;
	}
	public function getDefaultMap(){
		
		return array(
				"sn" => '1110',//流水号
				"applyAccount" =>0 ,//用户名
				"applyTime"=>"null",//发起时间
				"withdrawAmt" => 0, //申请提现金额
				"userBankStruc" => array(
						"id"=>1,
						"bankId"=>1,
						"bankAccount"=>"王*",
						"bankNumber"=>"**** **** **** 3400",
						"branchName"=>"--",
						"mcBankId"=>"1",
						"branchAddr"=>"--",
						"bindDate"=>0,
						),//提取银行信息
				"ipAddr"=>0, //ipv4地址
				"riskType"=>0,//风险类型
				"memo"=>"",//备注
				"mcRemitTime"=>"",//付款时间
				"apprAccount"=>"",//审核人
				"apprTime"=>"",//一审结束时间
				"attach"=>"",//审核附件
				"appr2Acct"=>"", //复核人
				"appr2Time"=>"",//复核时间
				"status"=>0,//状态	
				"beginApprTime"=> "" , //一审开始时间
				"isVip"=> 0 ,//是否vip
 		);
	}
}
