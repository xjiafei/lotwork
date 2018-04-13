<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_HbtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $MERCNUM = getSecurityInput($this->_request->getPost("MERCNUM"));
        $TRANDATA = getSecurityInput($this->_request->getPost("TRANDATA"));
        $SIGN = getSecurityInput($this->_request->getPost("SIGN"));
       
		$spRedisKey = md5($MERCNUM.$TRANDATA.$SIGN);
        $this->view->path_js = JS_ROOT;
        $this->view->MERCNUM = $MERCNUM;
        $this->view->TRANDATA = $TRANDATA;
		$this->view->SIGN = $SIGN;
		
		$value = $this->redis_client->get($spRedisKey);
		if($value!=null){       	
			print_r('訂單已存在');
			exit;
        }else{
			$this->redis_client->set($spRedisKey,$spRedisKey, 10*60);
        	$this->view->display('default/ucenter/sptransfer.tpl');
        }
        
    }
}
