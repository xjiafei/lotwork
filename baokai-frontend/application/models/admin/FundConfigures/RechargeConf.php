<?php
/* 充值配置 */
class RechargeConf extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"ICBC" =>0,//工行
				"ABC" =>0 ,//农行
				"CCB" =>0 ,//建行
				"CMB"=>0,//招行
				"VIPICBC" =>0,//VIP工行
				"VIPABC" =>0 ,//VIP农行
				"VIPCCB" =>0 ,//VIP建行
				"VIPCMB"=>0,//VIP招行
				"countdown"=>0,//倒记时
		);
	}
}
