<?php

class FundWithdraw extends Client {
	
	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	//审核
	public function auditFundWithdraw($jdata = null) {
		//4.5.9 后台审核
		$turl = array("fundadmin"=>"auditFundWithdraw");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( $res!=null && array_key_exists("body",$res)){
			return $res ;
		}
		return $this->inRequestV2($jdata, $turl, $isArray = true) ;
	
	}
	//复审
	public function auditFundWithdraw2($jdata = null) {
		//4.5.9 后台审核
		$turl = array("fundadmin"=>"auditFundWithdraw2");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( $res!=null && array_key_exists("body",$res)){
			return $res ;
		}
		return $this->inRequestV2($jdata, $turl, $isArray = true) ;
	
	}
	
	//退款 99
	public function auditRefund($jdata = null) {
		//4.5.9 后台审核
		$turl = array("fundadmin"=>"auditRefund");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( $res!=null && array_key_exists("body",$res)){
			return $res ;
		}
		return $this->inRequestV2($jdata, $turl, $isArray = true) ;
	
	}
	
	public function withdrawRemark($jdata = null) {
		//4.5.5 审核备注
		$turl = array("fundadmin"=>"withdrawRemark");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( $res!=null && array_key_exists("body",$res)){
			return $res["body"] ;
		}
		return $this->inRequestV2($jdata, $turl, $isArray = true) ;
	
	}
	
	
}

?>