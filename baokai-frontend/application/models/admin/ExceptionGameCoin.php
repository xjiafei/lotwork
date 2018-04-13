<?php

class ExceptionGameCoin extends Client {
	
	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	//4.25.5	处理方式之加游戏币
	public function addGameCoin($jdata = null) {
			
		$turl = array("fundadmin"=>"exceptionAddGameCoin");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	}
	//4.25.7	处理方式之退款
	public function exceptionRefund($jdata = null) {
		
		$turl = array("fundadmin"=>"exceptionRefund");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
		
	}
	//4.25.7	处理方式之没收
	public function exceptionConfiscate($jdata = null) {
		
		$turl = array("fundadmin"=>"exceptionConfiscate");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	
	}
	//审核不通过
	public function exceptionRefundReject($jdata = null) {
		
		$turl = array("fundadmin"=>"exceptionRefundReject");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	
	}
	//4.21.1	处理方式之待复审
	public function exceptionAudit($jdata = null) {
	
		$turl = array("fundadmin"=>"exceptionConfiscate");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	
	}
	//4.25.13	审核备注
	public function exceptionAuditRemark($jdata = null){
		
		$turl = array("fundadmin"=>"exceptionAuditRemark");
		$res = $this->inRequestV2($jdata, $turl, $isArray = true);
		if( isset($res['head']['status']) && $res['head']['status']=='0'){
			return TRUE ;
		}
		return FALSE;
	}
	
	
	
}

?>