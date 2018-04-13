<?php	

class Header extends BaseModel{

	public function getDefaultMap(){
		$sessionlogin = new Zend_Session_Namespace('datas') ;
		$head = array(
				"sowner" => 1,//
				"rowner" => 1,//发起时间
				"msn"=>10000,//处理类型
				"msnsn"=>0,//处理类型
				"sendtime"=>getSendTime(),//处理类型
				//"userId"=>isset($sessionlogin->info["id"]) ? $sessionlogin->info["id"] : -1 ,//处理类型
		);
		if(isset($sessionlogin->info["id"])){
			$head['userId'] = $sessionlogin->info["id"];
		} 
		if('admin' == MODULE_NAME){
			if(isset($sessionlogin->info['account'])){
				$head["userAccount"] = $sessionlogin->info['account'];
			}
		} else {
			if (isset($sessionlogin->info['userChain'])) {
				$head["userAccount"] = $sessionlogin->info['userChain'];
			}
		}
		return $head;
	}
}