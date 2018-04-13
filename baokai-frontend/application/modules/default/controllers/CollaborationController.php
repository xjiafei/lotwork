<?php
class Default_CollaborationController extends CustomControllerAction {
	public function init(){
		parent::init();
	}
	
	public function indexAction(){
		$this->view->display('default/ucenter/collaboration/index.tpl');
	}
}