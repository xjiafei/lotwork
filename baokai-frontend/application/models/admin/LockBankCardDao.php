<?php
class LockBankCardDao extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	
	public function lockBankCard($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"lockBankCard");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr ;
	}
}