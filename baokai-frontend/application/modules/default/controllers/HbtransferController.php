<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_HbtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $signature = getSecurityInput($this->_request->getPost("signature"));
        $data = getSecurityInput($this->_request->getPost("data"));
       
		$hbRedisKey = md5($signature.$data);
        $this->view->path_js = JS_ROOT;
        $this->view->signature = $signature;
        $this->view->data = $data;
		
		$value = $this->redis_client->get($hbRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($hbRedisKey,$hbRedisKey, 10*60);
        	$this->view->display('default/ucenter/hbtransfer.tpl');
        }
        
    }
}
