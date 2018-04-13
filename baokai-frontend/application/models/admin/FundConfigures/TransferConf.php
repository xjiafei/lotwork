<?php

/* 转账配置 */
class TransferConf extends BaseModel{
	/* 	protected function getTestData(){ //测试数据
		return "application/testdata/rechargeManaging.php" ;
	} */
	public function getDefaultMap(){
		return array(
				"lowLimit" =>0 ,//普通用户限额
				"upLimit" =>0 ,//总代限额
		);
	}
}