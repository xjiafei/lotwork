<?php	

class ConfigStruc extends BaseModel{
	
	protected function getTestData(){ //测试数据
		return "application/testdata/Funds.php" ;
	}
	
	public function getDefaultMap(){
		return array();
	}
}