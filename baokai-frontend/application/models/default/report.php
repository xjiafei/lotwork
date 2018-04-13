<?php

/* 	$_report = new Report();
	$re = $_report->findbyStr('');
	$this->view->re = $re;
*/
class Report {
	
	public function __construct(){
		parent::__construct();
	
	}
	
	public function findByStr($str='',$orderby='EID',$order='DESC',$limit1=0,$limit2=10){
		
		return "001";
	}
}
?>