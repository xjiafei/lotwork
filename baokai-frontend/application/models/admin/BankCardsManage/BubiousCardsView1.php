<?php 
class BubiousCardsView1 extends BaseModel {

/* 	protected function getTestData(){ //测试数据

		return "application/testdata/BubiousCardsView1.php" ;
	} */
	public function getDefaultMap(){

		return array(
 				"cardNumber"=>"111111111111",//卡号
				"id"=>"111111111111",//唯一id
				"gmtCreated"=>22222222222,//加入时间
				"creator"=>"seven",//添加人
				"memo"=>"moemoe111",//备注
		);
	}
}
