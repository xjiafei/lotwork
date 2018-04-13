<?php	

class Pager extends BaseModel{


	protected function getTestData(){ //测试数据
		return "application/testdata/Pager.php" ;
	}
	
	public function getDefaultMap(){
		return array(
				"startNo"=>888888,
				"endNo"=>888888,
				"total"=>888888
				);
	}
}