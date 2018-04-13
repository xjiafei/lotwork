<?php

class ExceptionRefundDao extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	
	public function exceptionRefund($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"exceptionRefund");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	}
	
	public function exceptionCheckAndUpdateReFundStatus($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"checkAndUpdateReFundStatus");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $res;
	}
}
?>