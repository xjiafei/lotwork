<?php
class Admin_EpReplenishmentController extends CustomControllerAction
{
	public function init(){
		parent::init ();
	}
	public function indexAction(){
		$parma = getSecurityInput($this->_request->get("parma"));
		$parmas = array(
				"sv1"=>"OpterView1",//
				"sv2"=>"OpterView2",//
				"sv3"=>"OpterView3",//
				"sv4"=>"OpterView4",//
				"sv5"=>"OpterView5",//
				"sv6"=>"OpterView6",//
		);
		if(array_key_exists($parma,$parmas)){
			eval(' $this->'.$parmas[$parma].'($this->_request);');
		}else{
			$this->_request->setParam("parma", "sv3");
			$this->indexAction();
		}
	}
	public function OpterView1($parma){
		$this->view->display ( '/admin/funds/epReplenishment/apReplenishment.tpl' );
	}
	public function OpterView2($parma){
		$this->view->display ( '/admin/funds/epReplenishment/determneReplenishment.tpl' );
	}
	public function OpterView3($parma){
		$this->view->display ( '/admin/funds/epReplenishment/epReplenishment.tpl' );
	}
	public function OpterView4($parma){
		$this->view->display ( '/admin/funds/epReplenishment/faReplenishment.tpl' );
	}
	public function OpterView5($parma){
		$this->view->display ( '/admin/funds/epReplenishment/prReplenishment.tpl' );
	}
	public function OpterView6($parma){
		$this->view->display ( '/admin/funds/epReplenishment/suReplenishment.tpl' );
	}
	public function replenishmentAction(){
	
		$parma = getSecurityInput($this->_request->get("parma"));
		$parmas = array(
				"sv1"=>"Opter1",//
				"sv2"=>"Opter2",//
				"sv3"=>"Opter3",//
				"sv4"=>"Opter4",//
				"sv5"=>"Opter5",//
				"sv6"=>"Opter6",//
				"sv7"=>"Opter7",//
				"sv8"=>"Opter8",//
		);
		if(!$parma){
			//$this->currenttransferAction();
		}
		if(array_key_exists($parma,$parmas)){
			eval(' $this->'.$parmas[$parma].'($this->_request);');
		}else{
			//$this->currenttransferAction();
		}
	}
	public  function Opter1(){
		
		
		
		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		$modata["sn"] = "201206151188" ;
		$modata["exSn"] = "2012061511" ;
		$modata["extraType"] = "异常充值";
		$modata["bankName"] = "招商银行";
		$modata["cardName"] = "张*" ;
		$modata["cardNo"] = "**** **** **** 3400" ;
		$modata["bankBrach"] = "上海交行张江路支行" ;
		$modata["bankAddress"] = "上海市张江高科" ;
		$modata["extraMoney"] = "10000" ;
		$modata["startTime"] = "2012.01.05 23:00:00" ;
		$modata["status"] = "处理中" ;
		$modata["failMoney"] = "1140000" ; //失败金额
		$modata["statusTime"] ="2012.01.05 23:00:00" ;//名字预定
		$modata["opter"] = "pdpjojoj";
		$modata["memo"] = "备注...";
		array_push($modatas['text'],$modata) ;
		array_push($modatas['count'],array("recordNum"=>"1")) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	public  function Opter2(){
		//备注接口
	}
	public  function Opter3(){
		//查询是否可以发起补款申请
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode(array("status"=>'1'));
	}
	public  function Opter4(){		
		//发起补款申请
		
		$this->_request->setParam("parma", "sv2");
		$this->indexAction() ;
	}
}
