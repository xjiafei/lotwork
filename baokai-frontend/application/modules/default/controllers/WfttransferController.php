<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_WfttransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $version = getSecurityInput($this->_request->getPost("version"));
        $method = getSecurityInput($this->_request->getPost("method"));
        $partner = getSecurityInput($this->_request->getPost("partner"));
		$banktype = getSecurityInput($this->_request->getPost("banktype"));
		$paymoney = getSecurityInput($this->_request->getPost("paymoney")); 
        $ordernumber = getSecurityInput($this->_request->getPost("ordernumber"));
		$callbackurl = getSecurityInput($this->_request->getPost("callbackurl"));
		$isshow = getSecurityInput($this->_request->getPost("isshow"));
        $hrefbackurl = getSecurityInput($this->_request->getPost("hrefbackurl"));
        $sign = getSecurityInput($this->_request->getPost("sign")); 
       
		$spRedisKey = md5($version.$method.$partner.$banktype.$paymoney.$ordernumber.$callbackurl.$isshow.$hrefbackurl.$sign);
        $this->view->path_js = JS_ROOT;
        $this->view->version = $version;
        $this->view->method = $method;
		$this->view->partner = $partner;
		$this->view->banktype = $banktype;
        $this->view->paymoney = $paymoney;
		$this->view->ordernumber = $ordernumber;
		$this->view->callbackurl = $callbackurl;
        $this->view->isshow = $isshow;
		$this->view->hrefbackurl = $hrefbackurl;
		$this->view->sign = $sign; 
		$value = $this->redis_client->get($spRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($spRedisKey,$spRedisKey, 10*60);
        	$this->view->display('default/ucenter/wfttransfer.tpl');
        }
        
    }
}
