<?php
/****
如果自定义类要在这里写上简单注释说明一下

***/

class Default_FindController extends CustomControllerAction
{ 

	public function init(){
        parent::init();
//        $acl = new Zend_Acl();
//        
//        $guest = new Zend_Acl_Role('guest');
//        $admin = new Zend_Acl_Role('admin');
//        $acl->addRole($guest);
//        $acl->addRole($admin);
//        
//        $admin_resource = new Zend_Acl_Resource('admin_index');
//        $auth_resource = new Zend_Acl_Resource('find_a');
//        
//        $acl->addResource($admin_resource);
//        $acl->addResource($auth_resource);
//        
//        $acl->deny('guest', $auth_resource);
//        
//        $acl->allow('admin', $auth_resource);
//        
//        
//        $acl->deny(null,null,null);
//        
//         echo $acl->isAllowed('guest', 'find_a') ? 'allowed' : 'denied';
//        
	}
	
	/***************************/
	public function nextAction( ){

		$this->view->display('default/ucenter/find-pwd-select.tpl') ;	

	}
	
	/***************************/
	public function emailwayAction( ){

		$this->view->display('default/ucenter/find-pwd-mail.tpl') ;	

	}
	
	public function aAction(){
	    echo "0";
	}
}