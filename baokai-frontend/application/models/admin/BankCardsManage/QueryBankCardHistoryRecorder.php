<?php 
class QueryBankCardHistoryRecorder extends BaseModel {

	public function getDefaultMap(){
		return array(
 				"userId"=>0,//卡号
				"action"=>0,//唯一id
				"actionTime"=>0,//加入时间
				"bankId"=>0,//添加人
				"bankAccount"=>"",//开户人姓名
				"province"=>"",
				"city"=>"",
				"bankNumber"=>0,
				"branchName"=>"",
				"mcBankId"=>0,
		);
	}
}
