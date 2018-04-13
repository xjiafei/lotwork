<?php
class DepositQuery extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function depositQuery($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		
		$turl = array("fundadmin"=>"depositQuery");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['body']['result'])&& count($rsr['body']['result'])){
			foreach($rsr["body"]["result"] as $k1 => $v1){
					array_push($recorders["result"],  new FundsRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
			return 	$recorders ;
		}else{
			return array();
		}
	}
	
	public function depositApply($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"depositApply");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['head']['status'])&&$rsr['head']['status']=='0'){
			return TRUE ;
		}else{
			return FALSE;
		}
		
	}
	//批量建单操作
	public function depositApplys($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"depositApplys");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['head']['status'])&&$rsr['head']['status']=='0'){
			return TRUE ;
		}else{
			return FALSE;
		}
		
	}
	
	//审核备注
	public function depositRemark($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"depositRemark");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['head']['status'])&&$rsr['head']['status']=='0'){
			return TRUE ;
		}else{
			return FALSE;
		}
	}	
	
	//审核
	public function depositAudit($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"depositAudit");
		$recorders = $this->inRequestV2($jdata, $turl) ;
		return $recorders;
	
	}
	//批量审核操作
	public function depositAudits($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"depositAudits");
		$recorders = $this->inRequestV2($jdata, $turl) ;
		return $recorders;
	
	}
	
	//撤销人工充值单
	public function adChargeCancel($data){
		$turl = array("fundadmin"=>"adChargeCancel");
		$rsr = $this->inRequestV2($data, $turl) ;
		if(isset($rsr['head']['status'])&&$rsr['head']['status']=='0'){
			return TRUE ;
		}else{
			return FALSE;
		}
	}
	
	//更加多个用户名查询用户名有效性
	public function queryUserDetailInfoByAccounts($data){
		$recorders = array();
		$turl = array("queryUserByCriteria_proxy"=>"queryUserDetailInfoByAccounts");
		$recorders = $this->inRequestV2($data, $turl) ;
		return $recorders;
	}
}
?>