<?php
class FundAdjustApply extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function fundAdjustApply($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"fundAdjustApply");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr ;
	}
	public function remarkFundAdjust($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"remarkFundAdjust");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr ;
	}
	
	public function auditFundAdjust($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"auditFundAdjust");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr ;
	}
	
	
	
}
?>