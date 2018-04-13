<?php

class ChargeDao extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	
	public function charge($jdata = null) {
		$rsr = array();
		$turl = array("fundadmin"=>"charge");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr ;
	}
}
?>