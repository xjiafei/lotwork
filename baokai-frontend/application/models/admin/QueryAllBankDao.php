<?php

class QueryAllBankDao extends Client {

	public $head = null;
	public $body = null;

	public function __construct() {
		parent::__construct ();
	}
	
	public function queryAllBank($jdata = null) {
		$recorders = array();
		$turl = array("fundadmin"=>"queryAllBank");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			return $rsr ;
		}else{
			throw new FireFogException ( '返回的body为null');
		}
	}
	

	public function allBankConfig($jdata = null){
		
		$recorders = array();
		$turl = array("fundadmin"=>"saveConfig");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			return $rsr ;
		}else{
			throw new FireFogException ( '返回的body为null');
		}
		
	}
	
	public function getconfigCountDown($jdata = null,$surl='configvaluebykey'){
		
		$recorders = array();
		$turl = array("fundadmin"=>$surl);
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr['head']['status']) && $rsr['head']['status']=='0'){
			return $rsr;
		} else {
			return array();
		}
		
	}
	
	public function bankParamsSet($jdata = null){
	
		$recorders = array();
		$turl = array("fundadmin"=>"bankParamsSet");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			return $rsr ;
		}else{
			throw new FireFogException ( '返回的body为null');
		}
	
	}
	
	public function bankParamsSet_V2($jdata = null){
	
		$recorders = array();
		$turl = array("fundadmin"=>"bankParamsSet");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr['head']['status']) && $rsr['head']['status']=='0'){
			return true;
		} else {
			return false;
		}
	}

	
	public function withdraw($jdata = null){
		$recorders = array();
		$turl = array("fundadmin"=>"saveConfig");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if($rsr){
			if(array_key_exists("body", $rsr)){
				return $rsr ;
			}
		}
	}
	
	
	
	public function chargeCountDown($jdata = null,$surl='saveConfig'){
		$recorders = array();
		$turl = array("fundadmin"=>$surl);
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(isset($rsr['head']['status']) && $rsr['head']['status']=='0'){
			return $rsr;
		} else {
			return array();
		}
	}
	
	public function addBank($jdata = null){
		
		$recorders = array();
		$turl = array("fundadmin"=>"addBank");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(is_array($rsr)){
		if(array_key_exists("head", $rsr)){
			if(!$rsr["head"]["status"]){
				return true ;
			}else{
				return false;
			}
		}
		}else{
			return false ;
		}
	}
	
	
	public function updateBank($jdata = null){
	
		$recorders = array();
		$turl = array("fundadmin"=>"updateBank");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(is_array($rsr)){
			if(array_key_exists("head", $rsr)){
				if(!$rsr["head"]["status"]){
					return true ;
				}else{
					return false;
				}
			}
		}else{
			return false ;
		}
	}
	
	public function depositApply($jdata = null){
		
		$recorders = array();
		$turl = array("fundadmin"=>"depositApply");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(is_array($rsr)){
			if(array_key_exists("head", $rsr)){
				if(!$rsr["head"]["status"]){
					return true ;
				}else{
					return false;
				}
			}
		}else{
			return false ;
		}
			
	}
	
	public function deposit($jdata = null){
		
		$recorders = array();
		$turl = array("fundadmin"=>"deposit");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr["body"]["result"] ;
/* 		if(array_key_exists("body", $rsr)){
			if(array_key_exists("result", $rsr["body"])){
				foreach($rsr["body"]["result"] as $k1 => $v1){
						array_push($recorders,  new Deposit($v1)) ;
				}
				return 	$recorders ;
			}else{
				throw new FireFogException ( '返回的result为null');
			}
		}else{
			throw new FireFogException ( '返回的body为null');
		} */	
	}
	
	public function userFundTransfer($jdata = null){
	
		$recorders = array();
		$turl = array("fundadmin"=>"userFundTransfer");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr["body"] ;
	}
	public function queryUserByName($jdata = null){
		$recorders = array();
		$turl = array("accountsSecurity"=>"queryUserByName");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		return $rsr;
	}
	
	public function queryFundTranferRecordByCriteria($jdata = null){
		$recorders = array();
		$turl = array("fundadmin"=>"queryFundTranferRecordByCriteria");
		$rsr = $this->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			if(array_key_exists("result", $rsr["body"])){
				foreach($rsr["body"]["result"] as $k1 => $v1){
					array_push($recorders,  new TransferRecorder($v1)) ;
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