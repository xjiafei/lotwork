<?php
class TransferReport extends BaseModel{

	
	public function getDefaultMap(){
		
		return array(
				"sn" => "111",//流水号
				"transferTime" =>"" ,//转账时间
				"userAccount" =>0 ,//转账人
				"transferAmt" =>0 ,//转账金额
				"isUpward"=>0, //是否对上级
				"rcvAccount"=>0, //收款用户名
		);
	}
}