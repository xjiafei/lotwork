<?php

class Default_TestController extends CustomControllerAction {
	
	protected $_person;
	protected $_pager;
	protected $_BRequest ;
	protected $_Body;
	protected $_Pager;
	protected $_Head;
	
	public function init() {
		parent::init ();
		$this->_person = new Person ();
		$this->_BRequest = new BRequest();
		$this->_Body = new Body();
		$this->_Head =new Head();

	}
	public function demoAction(){
		
		$foo = array(
				"a"=>array("dd"=>23),
				"b"=>array("dd"=>23),
				);
		
		$p1 = new Person();
		$BRequest = new $this->_BRequest();
		$this->_Body->add(new Pager(1,3));
		$BRequest->add($this->_Head,$this->_Body);
		
		$p1->set($BRequest);
		$aa = $p1->get();
		var_dump(json_encode($aa));
	}
	

}

