<?php

class CommonSaveConfig extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}

	public function chargeCoute($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"chargeQuery");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			return $rsr ;
				
		}else{
			throw new FireFogException ( '返回的body为null');
		}
	}
}
?>