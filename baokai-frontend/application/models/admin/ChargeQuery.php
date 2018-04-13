<?php
class ChargeQuery extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function chargeQuery($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"chargeQuery");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			if(array_key_exists("result", $rsr["body"])){
				foreach($rsr["body"]["result"] as $k1 => $v1){
						array_push($recorders,  new ChargeQueryRecorder($v1)) ;
				}
				return 	$recorders ;
			}
		}
		return array();
	}
}
?>