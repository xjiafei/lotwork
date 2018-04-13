<?php
class QuerySuspiciousCards extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function querySuspiciousCards($jdata = '') {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		
		$turl = array("fundadmin"=>"querySuspiciousCards");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			if(array_key_exists("result", $rsr["body"])){
				foreach($rsr["body"]["result"] as $k1 => $v1){
						array_push($recorders["result"],  new BubiousCardsView1($v1)) ;
				}
				$recorders["pager"] = $rsr["body"]["pager"] ;
				return 	$recorders ;
			}
		}
		return array();	
	}
	
	public function deleteSuspiciousCards($jdata = null) {
		
		$recorders = array();
		$turl = array("fundadmin"=>"deleteSuspiciousCards");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
			return $rsr;
	}
	
	public function addSuspiciousCards($jdata = null) {
		
		$recorders = array();
		$turl = array("fundadmin"=>"addSuspiciousCards");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr['head']['status']) && $rsr['head']['status'] =='0'){
			return TRUE;
		}else{
			return FALSE;
		}
	}
		
}
?>