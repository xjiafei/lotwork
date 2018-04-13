<?php
/**
 *
 * @author WA
 *        
 */
class Proxy extends Client {
	// TODO - Insert your code here
	

	public 	$_data = null ;
	
	function __construct() {
		parent::__construct();
		$header = new Header();
        $this->_data['head'] = $header->getDefaultMap();

	}
	
	public  function searchLowLevelProxy($jdata = null, $searchType = 0) {
		$url = Zend_Registry::get ( "firefog" )->proxy->searchLowLevelProxy;
// 		switch ($searchType) {
// 			case 0 :
// 				$url = Zend_Registry::get ( "firefog" )->proxy->searchLowLevelProxy;
// 				echo "首次加载";
// 			case 1 :
// 				$url = Zend_Registry::get ( "firefog" )->service->default;
// 				echo "搜索查询";
// 			case 2 :
// 				$url = Zend_Registry::get ( "firefog" )->service->default;
// 				echo "查看下级";
// 		}
		
		return $this->inRequest ( array_merge($this->_data,$jdata), $url);
	}
	
	public function querySubCustomerList($jdata = null){
		$url = Zend_Registry::get ( "firefog" )->proxy->querySubCustomerList;
		return $this->inRequest ( array_merge($this->_data,$jdata), $url );
		
	}
	public function querySubUser($jdata = null){
	
		$url = Zend_Registry::get ( "firefog" )->proxy->querySubUser;
		return $this->inRequest ( array_merge($this->_data,$jdata), $url );
	
	}	
	
    public function getSubList($uId, $type =0, $sortType =1, $SortAesc =0, $page=1, $size=1){
       $res = array();
       $start = ($page-1)*$size;
    	if($sortType == 0){
       		$ordery = 'bal '.($SortAesc ==0 ? 'DESC' : 'ASC');
       }else{
       		$ordery = 'last_login_date '.($SortAesc ==0 ? 'DESC' : 'ASC');
       }
       $data =array(
            "body" =>array(
                "param" => array(
                    'userId' => $uId,
                    'orderBy'  	 => $ordery, 
                ),
                "pager" => array(
                    'startNo' => $start,
                    "endNo"   => $start+$size-1,
                )
            )
        );
        if($type == 0){
            $uri = Zend_Registry::get ( "firefog" )->proxy->searchLowLevelProxy;
        }else{
            $uri = Zend_Registry::get ( "firefog" )->proxy->querySubCustomerList;
        }
        $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
        return $res["body"];
   }
   
   
   /**
    * 
    * Enter description here ...
    * @param unknown_type $uId
    * @param unknown_type $uName
    * @param unknown_type $moneyFm
    * @param unknown_type $moneyTo
    * @param unknown_type $timeStart
    * @param unknown_type $timeEnd
    * @param unknown_type $page
    * @param unknown_type $size
    * @param unknown_type $moneySort   0 降序 1升序
    * @param unknown_type $lastLoginSort
    */
   public function getSubListByCondis($uId , $uName, $moneyFm, $moneyTo, $timeStart, $timeEnd, $page =1, $size =1, $sortType =1, $SortAesc =0){
       $res= array();
       $start = ($page-1)*$size;
       $param = array();
       
//        if(!empty($uId)){
//            $param["userId"] =intval($uId);
//        }
       if(!empty($uName)){
           $param["account"] =$uName;
       }
       if(!empty($moneyFm)){
           $param["fromBal"] =floatval($moneyFm);
       }
       if(!empty($moneyTo)){
           $param["toBal"] =floatval($moneyTo);
       }
       if(!empty($timeStart)){
           $param["fromLoginDate"] =floatval($timeStart);
       }
       if(!empty($timeEnd)){
           $param["toLoginDate"] =floatval($timeEnd);
       }
       if($sortType == 0){
       		$ordery = 'bal '.($SortAesc ==0 ? 'DESC' : 'ASC');
       }else{
       		$ordery = 'last_login_date '.($SortAesc ==0 ? 'DESC' : 'ASC');
       }
       $param['userChain'] = $this->_sessionlogin->info['userChain'];
	   $param["orderBy"] = $ordery;
       $data =array(
            "body" =>array(
                "param" => $param,
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1,
                )
            )
        );
        
       $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->querySubUserByCriteria;
       $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
       return $res["body"];
   }

}

?>