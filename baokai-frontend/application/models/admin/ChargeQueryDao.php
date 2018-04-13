<?php
class ChargeQueryDao extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function chargeQuery($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		
		$turl = array("fundadmin"=>"chargeQuery");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr["body"]['result'])&&count($rsr["body"]['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
					array_push($recorders["result"],  new ChargeQueryRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
		}
		return 	$recorders ;
	}
}
?>