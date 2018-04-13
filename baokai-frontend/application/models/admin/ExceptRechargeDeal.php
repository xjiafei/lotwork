<?php	

class ExceptRechargeDeal extends BaseModel{

/* 	protected function getTestData(){ //测试数据
		return "application/testdata/ExceptRechargeDeal.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"mcSn" => 0,//
				"rcvAccName" =>"接受账号" ,//发起时间
				"memo"=>"备注",//处理类型
				"cardNumber" => "888888", //金额
				"Status"=>0,//处理类型
		);
	}

}