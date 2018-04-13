<?php
class Message2 extends Client {
	public $_data = null;
	public function __construct() {
		parent::__construct ();		
		$header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
	}
	public function queryUnReadMessageList($jdata = null){
		
		$uri = Zend_Registry::get ( "firefog" )->message2->queryUnReadMessageList;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
        
	public function queryUnreadNoticePushMsgs($jdata = null){
		$uri = Zend_Registry::get ( "firefog" )->message2->queryUnreadNoticePushMsgs;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}

	public function queryMessageDetail($jdata = null) {
		
		$uri = Zend_Registry::get ( "firefog" )->message2->queryMessageDetail;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function replyMessage($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->message2->replyMessage;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function deleteMessages($jdata = null) {
		
		$uri = Zend_Registry::get ( "firefog" )->message2->deleteMessages;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function sendMessage($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->message2->sendMessage;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function markRead($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->message2->markRead;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function markNoticeRead($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->message2->markNoticeRead;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function saveSub($jdata = null) {
		$uri = Zend_Registry::get ( "firefog" )->message2->default;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	public function saveSup($jdata = null) {
		
		// $this->_data['head']['userId']=$userId;
		$uri = Zend_Registry::get ( "firefog" )->message2->default;
		
		return $this->inRequest ( $jdata, $uri );
	}
	public function messages($jdata = null) {
		
		$uri = Zend_Registry::get ( "firefog" )->message2->querymessagelist;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $uri );
	}
	
	public function  getAccountINfoById($userid){
		$this->_data['head']['userId'] = $this->_sessionlogin->info["id"] ;
		$data =array(
				"body" =>array(
						'param'=>array(
								'userId'=>$userid,
						),
						"pager" => array(
								'startNo' => 1,
								"endNo"=>3
						)
				)
		);
        $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserDetailInfo;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
	}
	public function inbox($jdata = null) {
		/*
		 * $json = '{ "receives": [{ "id": "1", "sender": "1", "sendTime": "1",
		 * "receiveTime": 1277112572, "isRead": "1", "parentId": "1", "title":
		 * "1", "content": "25元双色球兑换券大派送", "type": 1, "sendAccount":
		 * "宝开HP管理员", "receiveAccount": "1", "owner": "1", "receives": "1",
		 * "msgType": "1", "sendMsgRout": "1", "receiveMsgROUT": "1",
		 * "sendHidden": "1", "receiveHidden": "1" }, { "id": "1", "sender":
		 * "1", "sendTime": "1", "receiveTime": 1277112572, "isRead": "1",
		 * "parentId": "1", "title": "1", "content": "25元双色球兑换券大派送", "type": 2,
		 * "sendAccount": "宝开HP管理员", "receiveAccount": "1", "owner": "1",
		 * "receives": "1", "msgType": "1", "sendMsgRout": "1",
		 * "receiveMsgROUT": "12", "sendHidden": "1", "receiveHidden": "1" }],
		 * "Pager": { "startNo": "1", "endNo": "10" }, "unreadCnt": 100 }';
		 * return Zend_Json::decode ( $json, Zend_Json::TYPE_ARRAY );
		 */
		$uri = Zend_Registry::get ( "firefog" )->message2->querymessagelist;
		return $this->inRequest ( $jdata, $uri );
	}
}

?>