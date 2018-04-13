<?php
class QueryFundAdjustRecordByStatus extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function queryFundAdjustRecordByStatus($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"queryFundAdjustRecordByStatus");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			if(array_key_exists("result", $rsr["body"])){
				foreach($rsr["body"]["result"] as $k1 => $v1){
						array_push($recorders,  new QueryFundAdjustRecorder($v1)) ;
				}
				return 	$recorders ;
			}else{
				throw new FireFogException ( '返回的result为null');
			}
		}else{
			throw new FireFogException ( '返回的body为null');
		}	
	}
}
?>