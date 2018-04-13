<?php
class QueryFundAdjustRecorder extends BaseModel{

	public function getDefaultMap(){
		return array(
			"sn" => '1110',//流水号
			"userAccount" =>0 ,//用户名
			"typeId"=>0,//发起时间
			"applyTime"=>0,//申请时间
			"withdrawAmt"=>0,
			"applyAccount"=>"",
			"apprAccount"=>"",
			"apprTime"=>0,
			"status"=>0,//状态
			"memo"=>"6123788123678120",//备注
		);
	}
}