<?php
class UserAppeal extends Client {
	public 	$_data = null ;
	
	function __construct() {
		parent::__construct();
		$this->_Mail = new Mail();
		$header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
	}
	
	function userAppeal($jdata = null){
		
		$url = Zend_Registry::get ( "firefog" )->appeal->userappeal;
		return $this->inRequest ( array_merge($this->_data,$jdata), $url );		
	}
	
	function checkUserAppeal($username,$appealType=1){
		$url = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserAppealByCriteria;
		$data['body'] = array(
			'param' => array(
				'account' => strtolower($username),
				'passed' => 3,
				'appealType' => intval($appealType),
			),
			'pager' => array(
				'startNo' => 1,
				'endNo'	  => 1
			)
		);
		return $this->inRequest ( array_merge($this->_data,$data), $url );
	}
	public function freezeuser($param)
	{
		$data =array(
				"body" =>array(
						"param" => array(
								'range'=>$param['range'],
								'userId'=>$param['userid'],
								'method'=>$param['method'],
								'memo'=>$param['memo'],
								'freezeAccount'=>$param['freezeAccount']
						),
						"pager" => array(
								'startNo' => 0,
								"endNo"=>0
						)
				)
		);
		$uri = Zend_Registry::get ( "firefog" )->freezeUser->freezeUser;
	
		return $this->inRequest ( array_merge($this->_data, $data), $uri );
	}
	

}

?>