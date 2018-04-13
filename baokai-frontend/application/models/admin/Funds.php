<?php	

class Funds extends BaseModel{

	protected function getTestData(){ //测试数据
		return "application/testdata/Funds.php" ;
	}
	
	public function getDefaultMap(){
		return array(
				"result"=>array(),
				);
	}
	
	
	
	
	
	
}


class FundsRecorder extends BaseModel{

	protected function getTestData(){ //测试数据
		return "application/testdata/Funds.php" ;
	}
	
	public function getDefaultMap(){
		
		return array(
				'id' =>0, 
				'sn' =>0, //流水号
				"applyAccount"=>"没有数据" , //用户名
				"rcvAccount"=>"没有数据" ,
				"typeId"=>0,//消费类型
				'applyTime'=> 1,
				"withdrawAmt"=>88888,//金额
				"userBankStruc"=>array(
						"id"=>88888,
						"bankId"=>88888,
						"bankAccount"=>"没有数据",
						"bankNumber"=>88888,
						"branchName"=>"没有数据",
						"mcBankId"=>88888,
						"bindDate"=>888888,
						),
				"memo"=>"没有数据",
				"mcRemitTime"=>88888,
				"applyAccount"=>"没有数据",
				"apprAccount"=>"没有数据",
				"apprTime"=>88888,
				"vipLvl"=>0, //是否vip
		);
	}
}

