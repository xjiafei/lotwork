<?php
class Charge extends  BaseModel{

	public function getDefaultMap(){
		
		return array(
				"charge_ratio" =>"",//异常id
				"user" =>array("bank"=>array("lowLimit"=>"","upLimit"=>"")) ,
				"vip" =>array("bank"=>array("lowLimit"=>"","upLimit"=>"")) ,
		);
	}
}