<?php
class ProxyAdm extends Client {
	public $_data = null;
	public function __construct() {
		parent::__construct ();
		
		//list($s1, $s2) = explode(' ', microtime());
		$header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
	}
	
	
	
/* 	public function timeConvertToResult($contime){

		switch ($contime)
		{
			case 7 :
				echo '';
				break;
			case 15:
				echo '';
				break;
			case 30:
				echo '';
				break;
			case 90:
				echo '';
				break;
			case 180:
				echo '';
				break;
			case 360:
				echo '';
				break;
		}
		
		
	} */
	
	
	public  function searchLowLevelProxy($jdata = null, $searchType = 0) {
		$url = Zend_Registry::get ( "firefog" )->proxy->searchLowLevelProxy;
		
		return $this->inRequest ( array_merge($this->_data,$jdata), $url);
	}

	public function createGeneralAgent($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->createGeneralAgent;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function updateGeneralAgentBalance($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->updateGeneralAgentBalance;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function queryGeneralAgent($jdata = null) {
		$this->_data["head"]["userId"] = 1;
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryGeneralAgent;
		return $this->inRequest ( array_merge ($this->_data, $jdata ), $url );
	}
	public function userAppealAudit($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserAppealDetail->userAppealAudit;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function queryUserAppealDetail($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserAppealDetail->queryUserAppealDetail;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function queryUserAppealByCriteria($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserAppealByCriteria;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function queryUserByCriteria($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserByCriteria;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function queryUserDetailInfo($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserDetailInfo;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function querySubUserByCriteria($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->querySubUserByCriteria;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}
	public function querySubUserForDownClist($jdata = null) {
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->querySubUserForDownClist;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $url );
	}	
	
	public function querySubUser($jdata = null){
		$url = Zend_Registry::get ( "firefog" )->proxy->querySubUser;
		return $this->inRequest ( array_merge($this->_data,$jdata), $url );
	
	}
	public function querySubUsers($jdata = null){
		$url = Zend_Registry::get ( "firefog" )->proxy->querySubUsers;
		return $this->inRequest ( array_merge($this->_data,$jdata), $url );
	}
	
	
	
	
	/**
	 * 后台总代列表
	 * 
	 * @return array
	 */
	public function topproxyList($param='',$page=1, $size=1) {
			
            $start = ($page-1)*$size+1;
              $data =array(
                "body" =>array(
                    'param'=>$param,
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$page*$size,
                    )
                )
            );  
            $url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryGeneralAgent;
            return $this->inRequest ( array_merge ( $this->_data, $data ), $url );
	}
	
	/**
	 * 读取用户直接下级用户
	 * 
	 * @param type $useid        	
	 * @return boolean array
	 */
	public function directchildlist($userid,$page=1, $size=1) {
            $start = ($page-1)*$size;
            $data =array(
                "body" =>array(
                    'param'=>array(
                      'userId'=>$userid,
                    ),
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1
                    )
                )
            );
            $url = Zend_Registry::get ( "firefog" )->proxy->querySubUser;
            return $this->inRequest ( array_merge ( $this->_data, $data ), $url );
	}
	
	/**
	 * 保存总代开户配额信息
	 * 
	 * @param type $id        	
	 * @return boolean
	 */
	public function saveaccountnum($param) {
            $data =array(
                "body" =>array(
                    'param'=>array(
                      'availableQuota'=>$param['availablequota'],
                      'userId'=>$param['userid'],
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
            $url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->updateGeneralAgentBalance;
            return $this->inRequest ( array_merge ( $this->_data, $data ), $url );
	}
	
	/**
	 * 读取总代开户配额信息
	 * 
	 * @param int $userid
	 *        	@retur array
	 */
	public function getaccountnum($param) {
            
            $data =array(
                "body" =>array(
                    'param'=>array(
                      'account'=>$param,
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
            $accountInfo = array();
            $url = Zend_Registry::get ( "firefog" )->login->safelogin;
            $result =  $this->inRequest ( array_merge ( $this->_data, $data ), $url );
            if(isset($result['body']['result'])){
            	$accountInfo ['id'] 		= $result['body']['result']['id'];
            	$accountInfo ['account'] 	= $result['body']['result']['account'];
            	$accountInfo ['teamACount'] = $result['body']['result']['teamACount'];
	            $accountInfo ['teamUCount'] = $result['body']['result']['teamUCount'];
            	$accountInfo ['agentlimit'] = $result['body']['result']['agentlimit'];
            }
            return $accountInfo;
	}
        
        /**
         * 保存总代开户信息
         * @param type $data
         * @return boolean
         */
        public function savetopaccount($param)
        {
            $data =array(
                "body" =>array(
                    "param" => $param,
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
            $url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->createGeneralAgent;
            return $this->inRequest ( array_merge ( $this->_data, $data ), $url );
        }
        
        /*
         * 总代用户名是否合法
         */
        public function islegalAccount($username){
        	$data['param']['account'] = trim(strtolower($username));
        	$uri['prize'] = 'islegalAccount';
        	$res = $this->inRequestV2($data, $uri);
	        if(isset($res['head']['status']) && $res['head']['status'] ==0){
	        	if(isset($res['body']['result']) && !empty($res['body']['result'])){
	        		return $res['body']['result'];
	        	} else {
	        		return 0;
	        	}
	        } else {
	        	return $res['head']['status'];
	        }
        }
}
?>