<?php

require_once SITE_ROOT . '/application/include/CodePlugin.php';
require_once SITE_ROOT . '/application/models/admin/User.php';

class Default_FhxController extends CustomControllerAction 
{
    protected $_captcha, $_clientObject, $_redisIndexInfo, $_user;
	
	public function init( ){
		parent::init( ) ;
		$this->_captcha = new ValidateCode();
		$this->_clientObject = new Client();
		$this->_login = new Member();
		$this->_redisIndexInfo = Zend_Registry::get('rediska');
        $this->_user = new User();
		//$this->_sessionlogin = new Zend_Session_Namespace('datas');
	}
	
	public function indexAction( ){
		
		
		$user_id = $this->_sessionlogin->__JAVA['id'];
		
		$userAccount = $this->_sessionlogin->info['account']; 
		$url = $this->_config->x_landing_page."?token=".$this->_sessionlogin->__JAVA['id']."&account=".urlencode($userAccount)."&host=".$_SERVER['HTTP_HOST']."&lvl=". $this->_sessionlogin->info['vipLvl'];
		$decurl = $this->_config->x_landing_page."?token=".$this->_sessionlogin->__JAVA['id']."&account=".urldecode($userAccount)."&host=".$_SERVER['HTTP_HOST']."&lvl=".$this->_sessionlogin->info['vipLvl'] ;
		$this->view->x_landing_page = $decurl  ;
		
		$this->view->display('default/fhx/index.tpl');

		
    }
	
	
	public function frameproxyAction( ){
		
		
		
		
		$this->view->display('default/fhx/frameproxy.html');

		
    }

	 
}