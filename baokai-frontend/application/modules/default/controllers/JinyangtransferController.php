<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_JinyangtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $sign = getSecurityInput($this->_request->getPost("sign"));
        $merId = getSecurityInput($this->_request->getPost("merId"));
        $payType = getSecurityInput($this->_request->getPost("payType"));
		$paymoney = getSecurityInput($this->_request->getPost("paymoney"));
		$orderno = getSecurityInput($this->_request->getPost("orderno")); 
        $asynURL = getSecurityInput($this->_request->getPost("asynURL"));
		$version = getSecurityInput($this->_request->getPost("version"));
		$signType = getSecurityInput($this->_request->getPost("signType"));
        $isShow = getSecurityInput($this->_request->getPost("isShow")); 

        

		$redisKey = md5($sign.$merId.$payType.$paymoney.$orderno.$asynURL.$version.$signType.$isShow);
        $this->view->path_js = JS_ROOT;
        $this->view->sign = $sign;
        $this->view->merId = $merId;
		$this->view->payType = $payType;
		$this->view->paymoney = $paymoney;
        $this->view->orderno = $orderno;
		$this->view->asynURL = $asynURL;
		$this->view->version = $version;
        $this->view->signType = $signType;
		$this->view->isShow = $isShow;  
		$value = $this->redis_client->get($spRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($redisKey,$redisKey, 10*60);
        	$this->view->display('default/ucenter/jinyangtransfer.tpl');
        }
        
    }
}
