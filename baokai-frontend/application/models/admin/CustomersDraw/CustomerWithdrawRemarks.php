<?php
/* 提现审核备注 */
class CustomerWithdrawRemarks extends BaseModel{

/* 	protected function getTestData(){ //测试数据
		return "application/testdata/customersDraw.php" ;
	} */
	
	public function getDefaultMap(){
		return array(
				"typeId" => 0,//申请id
				"memo" =>"",//备注
		);
	}
}