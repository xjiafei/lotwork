<?php	

class RechargeDetail extends BaseModel{
	
	
	public function getDefaultMap(){
		return array(
				"serial" => 0,//
				"username" =>"接受账号" ,//发起时间
				"requesttime1"=>0,//处理类型
				"requesttime2"=>0,//处理类型
				"requestMoney1"=>0,//处理类型
				"requestMoney2"=>0,//处理类型
				"message"=>"",
				"receivetime1"=>0,
				"receivetime2"=>0,
				"accountName"=>"",
				"cardnumber"=>"",
				"receivemoney"=>0,
				"loadingmoney1"=>0,
				"loadingmoney2"=>0,
				"orderstatus"=>0,
				"sorting"=>0,
		);
	}
}