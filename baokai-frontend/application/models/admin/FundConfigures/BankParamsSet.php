<?php
//银行列表
class BankParamsSet extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"bankId" =>0,//银行id
				"bankAccount" =>"" ,//开户人姓名
				"bankNumber" =>0 ,//卡号
				"branchName"=>"",//支行名称
				"mcBankId" =>0,//Mc银行id
				"branchAddr" =>"" ,//支行地址
				"bindDate" =>0 ,//绑定时间
		);
	}
}

