<?php
class Default_RebateController extends CustomControllerAction {
	public function init(){
		parent::init();
	}
	
	public function indexAction(){
		
		$this->view->display('default/ucenter/rebate/index.tpl');
	}
}