<?php
class ExceptionQueryDao extends Client {
	
	public $head = null;
	public $body = null;
	//public $ExceptRechargeRecorder = null;
	public function __construct() {
		parent::__construct ();
	}
	
	public function exceptionQuery($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		$turl = array("fundadmin"=>"exceptionQuery");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr["body"]["result"]) && count($rsr["body"]["result"])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($recorders["result"],  new ExceptRechargeRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
		}
		return 	$recorders ;
	}
}
?>