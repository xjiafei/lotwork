<?php
/* 提现审核配置 */
class WithdrawAuditSettings extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
		);
	}
}

class WithdrawAuditSettingsPart extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"part"=>0
		);
	}
}

class WithdrawAuditSettingsAmt extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"sigal"=>0,
				"val"=>0,
		);
	}
}

class WithdrawAuditSettingsTime extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"sigal"=>0,
				"val"=>0,
		);
	}
}
