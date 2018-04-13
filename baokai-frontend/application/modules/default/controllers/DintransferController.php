<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_DintransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $sign = getSecurityInput($this->_request->getPost("sign"));
        $merchantCode = getSecurityInput($this->_request->getPost("merchantCode"));
        $serviceType = getSecurityInput($this->_request->getPost("serviceType"));
		$interfaceVersion = getSecurityInput($this->_request->getPost("interfaceVersion"));
		$inputCharset = getSecurityInput($this->_request->getPost("inputCharset")); 
        $notifyUrl = getSecurityInput($this->_request->getPost("notifyUrl"));
		$signType = getSecurityInput($this->_request->getPost("signType"));
		$orderNo = getSecurityInput($this->_request->getPost("orderNo"));
        $orderTime = getSecurityInput($this->_request->getPost("orderTime"));
        $orderAmount = getSecurityInput($this->_request->getPost("orderAmount"));
		$productName = getSecurityInput($this->_request->getPost("productName"));
		$returnUrl = getSecurityInput($this->_request->getPost("returnUrl"));
		$bankCode = getSecurityInput($this->_request->getPost("bankCode"));

       
		$spRedisKey = md5($sign.$merchantCode.$serviceType.$interfaceVersion.$inputCharset.$notifyUrl.$signType.$orderNo.$orderTime.$orderAmount.$productName.$returnUrl.$bankCode);
        $this->view->path_js = JS_ROOT;
        $this->view->sign = $sign;
        $this->view->merchantCode = $merchantCode;
		$this->view->serviceType = $serviceType;
		$this->view->interfaceVersion = $interfaceVersion;
        $this->view->inputCharset = $inputCharset;
		$this->view->notifyUrl = $notifyUrl;
		$this->view->signType = $signType;
        $this->view->orderNo = $orderNo;
		$this->view->orderTime = $orderTime;
		$this->view->orderAmount = $orderAmount;
        $this->view->productName = $productName;
		$this->view->returnUrl = $returnUrl;
		$this->view->bankCode = $bankCode; 
		$value = $this->redis_client->get($spRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($spRedisKey,$spRedisKey, 10*60);
        	$this->view->display('default/ucenter/dintransfer.tpl');
        }
        
    }
}
