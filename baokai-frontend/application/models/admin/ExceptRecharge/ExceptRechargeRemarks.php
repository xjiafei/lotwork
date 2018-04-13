<?php
class ExceptRechargeRemarks extends  BaseModel{
	
	protected function getTestData(){ //测试数据
		return "application/testdata/ExceptRechargeRemarks.php" ;
	}
	
	public function getDefaultMap(){
		return array(
				"exceptId" => 0,//异常id
				"applyMemo" =>"sss" ,//备注
		);
	}
}