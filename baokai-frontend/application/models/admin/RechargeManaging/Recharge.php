<?php
class Recharge extends BaseModel{	
	public function getDefaultMap(){
		return array(
				"accountName"=>"",
				"cardnumber"=>"",
				"loadingmoney1"=>0,
				"loadingmoney2"=>0,
				"message"=>"",
				"receivemoney"=>0,
				"receivetime1"=>0,
				"receivetime2"=>0,
				"requestMoney1"=>0,
				"requestMoney2"=>0,
				"requesttime1"=>0,
				"requesttime2"=>0,
				"serial"=>"",
				"username"=>"",
		);
	}
}

