<?php
class QueryBankCardRecords extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}

	public function queryBankCardRecords($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		
		$turl = array("fundadmin"=>"queryBankCardRecords");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr['body']['result'])){
				array_push($recorders["result"],  $rsr["body"]["result"]) ;
				$recorders["pager"] = $rsr["body"]["pager"] ;
			}
		return $recorders;
	}
	
	public function lockBankCard($jdata = null) {
		
		$recorders = array();
		$turl = array("fundadmin"=>"lockBankCard");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr;
	}
	
	public function queryBankCardHistory($jdata = null) {
		
		$recorders = array();
		$turl = array("fundadmin"=>"queryBankCardHistory");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr;	
	}
	
	public function queryBoundbankCard($jdata = null) {
		
		$recorders = array();
		$turl = array("fundadmin"=>"queryBoundbankCard");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr;
	}
	
	
	
	
}
?>