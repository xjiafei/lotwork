<?php

class Default_Vip2Controller extends CustomControllerAction {
	public function init(){
		parent::init();
	}
	
	public function indexAction(){
		$this->view->display('default/ucenter/vip/indexnew.tpl');
	}
}
