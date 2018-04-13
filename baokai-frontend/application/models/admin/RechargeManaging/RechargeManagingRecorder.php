<?php 
class RechargeManagingRecorder extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"ineffectivePer" => 0,//不可提现额度/充值金额
				"recharge" =>0 ,//充值
				"transfer" =>0 ,//转账
				"ineffectivePerGaming"=>0,//游戏消耗减不可提现额度比例
		);
	}
}