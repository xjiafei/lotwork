<?php
/* 提现审核配置 */
class WithdrawCheck extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"part" =>0,//工行
				"amt" =>array("sigal"=>"","val"=>""),//农行
				"time" =>array("sigal"=>"","val"=>""),//建行
		);
	}
}