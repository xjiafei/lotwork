<?php 
class BankCardsManageView1 extends BaseModel {
	
	protected function getTestData(){ //测试数据
	
		return "application/testdata/BankCardsManageView1.php" ;
	}
	public function getDefaultMap(){
	
		return array(
				"lockedId" => "111",//id
				"account" =>"" ,//用户名
				"userLvl" =>0 ,//代理组
				"bindCount" =>0 ,//绑定数量
				"status" =>0 ,//状态
				//绑定卡号组
				"userBanks"=>array("id"=>1,"bankId"=>1,"bankAccount"=>"王*","branchName"=>"","mcBankId"=>"","bankNumber"=>"**** **** **** 3400","bindDate"=>1380511180349),
				"operator"=>""//操作人
		);
	}
}