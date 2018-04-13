<?php 
/* 现金打款 */
class TransferMoneyView1 extends BaseModel{
	
	protected function getTestData(){ //测试数据
		
		return "application/testdata/TransferMoneyView1.php" ;
	}
	public function getDefaultMap(){
		
		return array(
				"sn" => "111",//不可提现余额/充值金额
				"applyAccount" =>"" ,//充值
				"typeId" =>0 ,//转账
				"applyTime" =>0 ,//转账
				"withdrawAmt" =>0 ,//转账
				"userBankStruc"=>array("mcBankId"=>1,"bankAccount"=>"王*","bankNumber"=>"**** **** **** 3400"),
				"memo"=>"",
				"mcRemitTime"=>0,
				"applyAccount"=>"",
				"apprAccount"=>"",
				"apprTime"=>0,
				"status"=>0,
		);
	}
}