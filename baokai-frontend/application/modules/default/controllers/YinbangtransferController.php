<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_YinbangtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $sign = getSecurityInput($this->_request->getPost("sign"));
        $merId = getSecurityInput($this->_request->getPost("merId"));
        $version = getSecurityInput($this->_request->getPost("version"));
		$encParam = getSecurityInput($this->_request->getPost("encParam"));

       
		$spRedisKey = md5($sign.$merId.$version.$encParam);
        $this->view->path_js = JS_ROOT;
        $this->view->sign = $sign;
        $this->view->merId = $merId;
		$this->view->version = $version;
		$this->view->encParam = $encParam;
		$value = $this->redis_client->get($spRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($spRedisKey,$spRedisKey, 10*60);
        	$this->view->display('default/ucenter/yinbangtransfer.tpl');
        }
        
    }
}
