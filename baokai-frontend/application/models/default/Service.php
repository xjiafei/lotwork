<?php
class Service extends Client {
	public function __construct() {
		parent::__construct();
	}
	public  function saveSub($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->service->default;
		
		return $this->inRequest ( $jdata, $uri );
	}
	public  function saveSup($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->service->default;
		
		return $this->inRequest ( $jdata, $uri );
	}
	public  function usermessages($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->service->default;
		
		return $this->inRequest ( $jdata, $uri );
	}
	public  function inbox($jdata = null) {
		$json = '{
			"receives": [{
				"id": "1",
				"sender": "1",
				"sendTime": "1",
				"receiveTime": 1277112572,
				"isRead": "1",
				"parentId": "1",
				"title": "1",
				"content": "25元双色球兑换券大派送",
				"type": 1,
				"sendAccount": "宝开HP管理员",
				"receiveAccount": "1",
				"owner": "1",
				"receives": "1",
				"msgType": "1",
				"sendMsgRout": "1",
				"receiveMsgROUT": "1",
				"sendHidden": "1",
				"receiveHidden": "1"
				},
				{
				"id": "1",
				"sender": "1",
				"sendTime": "1",
				"receiveTime": 1277112572,
				"isRead": "1",
				"parentId": "1",
				"title": "1",
				"content": "25元双色球兑换券大派送",
				"type": 2,
				"sendAccount": "宝开HP管理员",
				"receiveAccount": "1",
				"owner": "1",
				"receives": "1",
				"msgType": "1",
				"sendMsgRout": "1",
				"receiveMsgROUT": "12",
				"sendHidden": "1",
				"receiveHidden": "1"
				}],
			"Pager": {
				"startNo": "1",
				"endNo": "10"
			},
			"unreadCnt": 100
			}';
		
		return Zend_Json::decode ( $json, Zend_Json::TYPE_ARRAY );
		// $uri = Zend_Registry::get("firefog")->service->default ;
		// return self::inRequest($jdata , $uri);
	}
	
	public function queryNoticeTask(){
		$aUri['service'] = 'queryNoticeTask';
		$param['param'] = 'true';
		$result = $this->inRequestV2($param, $aUri);
		return $result['body']['result'];
	}
	
	public function bindUserNoticeTask($data){
		$aUri['service'] = 'bindUserNoticeTask';
		$param['param'] = $data;
		$result = $this->inRequestV2($param, $aUri);
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
			return TRUE;
		}
		return FALSE;
	}
}

?>