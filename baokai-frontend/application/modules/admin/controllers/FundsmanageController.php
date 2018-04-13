<?php
class Admin_FundsmanageController extends CustomControllerAction
{
	public function init() {
		parent::init ();
		$this->_funds =  new Funds();
		$this->_depositQuery =  new  DepositQuery();
		$this->_queryFundAdjustRecordByStatus =  new QueryFundAdjustRecordByStatus();
		$this->_fundAdjustApply = new FundAdjustApply();
	}
	
	public function indexAction(){
		
		$parma = getSecurityInput($this->_request->get("parma"));
		$parmas = array(
				"view"=>"view1",
				"applying"=>"applying",
				"fundAdjustApply"=>"fundAdjustApply",
				"remarkFundAdjust"=>"remarkFundAdjust",
				"auditFundAdjust"=>"auditFundAdjust",
				"depositApply"=>"depositApply",
				"depositRemark"=>"depositRemark",
				);
		if(!$parma){
			$this->_forward('currenttransfer');
		}
		if(array_key_exists($parma,$parmas)){
			eval(' $this->'.$parmas[$parma].'($this->_request);');
		}else{
			$this->currenttransferAction();
		}
	}
	
	public function applying($parmas){
		//处理发起打款申请的表单数据提交java 后台，并且实现页面跳转到 现金打款的等待审核页面
		/* $parmas->getParam("sex",1); */
		
		//返回默认页面\
		$selav =  $this->_request->getPost("selav");
		$username =  $this->_request->getPost("username");
		//var_dump("发起打款申请表单提交的结果");
		$this->_request->setParam("swithval", 2);
		$this->currenttransferAction();
	}
	
	public function remarkFundAdjust($parmas){
		//审核备注
		
		$typeId = $this->_request->getPost ("typeId ");
		$memo = $this->_request->getPost ("memo ");
		$data=array();
		$data["param"]["typeId"] = $typeId ;
		$data["param"]["memo"] = $memo;
		$this->_fundAdjustApply->remarkFundAdjust($data);
	}
	
	public function auditFundAdjust($parmas){
		//审核备注
		$typeId = $this->_request->getPost ("typeId ");
		$status = $this->_request->getPost ("status ");
		$data=array();
		$data["param"]["typeId"]= $typeId ;
		$data["param"]["status"]= $status;
		$this->_fundAdjustApply->auditFundAdjust($data);
	}
	
	public function fundAdjustApply($parmas){
		//账户余额加减 处理此页面提交的结果： http://localhost:8081/admin/Fundsmanage/accountbalance?swithval=6&label=1
		
		$typeId = $this->_request->getPost ("typeId ");
		$rcvAct = $this->_request->getPost ("rcvAct ");
		$depositAmt =  $this->_request->getPost ("depositAmt ");
		$memo =  $this->_request->getPost ("memo ");
		$data=array();
		$data["param"]["typeId"]= $typeId ;
		$data["param"]["rcvAct"]= $rcvAct;
		$data["param"]["depositAmt"]=$depositAmt;
		$data["param"]["memo"]=$memo;
		$rsr = $this->_fundAdjustApply->fundAdjustApply($data);
		
 		//$this->currenttransferAction();
	}
	
	
	
	public function view1($action){
		//账户余额调整
		//////////////////////////////////////////
/* 		$transmon = new TransferMoneyView1();
		$result = $transmon->getMember("result") ; 
		//////////////////////////////////////////
		//$result = $this->_funds->getMember("result") ;
		$recorders = array();
		
		if(empty($result)){
			throw new FireFogException("无数据返回！！") ;
		}
		foreach($result as $k1 => $v1){
			array_push($recorders,  new FundsRecorder($v1)) ;
		}*/
		
		$sortBy =  $this->_request->getPost("sortBy");
		$username =  $this->_request->getPost("username");
		$addminus1 = $this->_request->getPost("addminus1");
		$addminus2 = $this->_request->getPost("addminus2");
		$createdtime1 = $this->_request->getPost("createdtime1");
		$createdtime2 = $this->_request->getPost("createdtime2");
		$audittime1 = $this->_request->getPost("audittime1");
		$audittime2 = $this->_request->getPost("audittime2");
		$applypeople =  $this->_request->getPost("applypeople");
		$spinfo =  $this->_request->getPost("spinfo");
		$token =  $this->_request->getPost("token");
		
		
		
		$swithval = $this->_request->getParam("swithval",1) ;
		$page =  $this->_request->getPost("page",0);
		$perPageNum = $this->_request->getPost("perPageNum",0);
		$start = ($page-1)*$perPageNum;
		
		$data["pager"]["startNo"] = $start; //启始值
		$data["pager"]["endNo"] = $start+$perPageNum-1; //结束值
		
		
		$data=array();
		$data["param"]["sn"]= 1 ;
		$data["param"]["rcvAccName"]="";
		$data["param"]["memo"]="";
		$data["param"]["cardNumber"]="";
		$data["param"]["status"]=0;
		$data["pager"]["startNo"]=0;
		$data["pager"]["endNo"]=1;
		
		
		$rsr = $this->_queryFundAdjustRecordByStatus->queryFundAdjustRecordByStatus($data);
		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		foreach ( $rsr as $recorder){
			$modata["sn"] = $recorder->getMember("mcSn") ;
			$modata["applyAccount"] = $recorder->getMember("userAccount") ;
			$modata["typeId"] = $recorder->getMember("typeId") ;
			$modata["applyTime"] = $recorder->getMember("applyTime") ;
			$modata["withdrawAmt"] = $recorder->getMember("withdrawAmt") ;
			$modata["memo"] = $recorder->getMember("memo") ;
			$modata["applyAccount"] = $recorder->getMember("applyAccount") ;
			$modata["apprAccount"] = $recorder->getMember("apprAccount") ;
			$modata["apprTime"] = $recorder->getMember("apprTime") ;
			$modata["status"] = $recorder->getMember("status") ;
			array_push($modatas['text'],$modata) ;
		}
		array_push($modatas['count'],array("recordNum"=>"1")) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	//页面数=总数/当前页面的数量
	public function currenttransferAction(){
		/* 资金设置 */
		$swithvals = array(
				1=>'/admin/funds/transfermoney/transfermoney.tpl',//现金打款
				2=>'/admin/funds/transfermoney/pendingreview.tpl',//等待审核
				3=>'/admin/funds/transfermoney/AuditingInvalid.tpl',//审核未过
				4=>'/admin/funds/transfermoney/treatover.tpl',//处理成功
				5=>'/admin/funds/transfermoney/transferfail.tpl',//打款失败
				6=>'/admin/funds/transfermoney/initiatingapply.tpl',//发起打款申请
				7=>'/admin/funds/transfermoney/initiatedapply.tpl',//发起打款申请详细页面
		);
		$swithval = $this->_request->getParam("swithval",1) ;
		if(array_key_exists($swithval,$swithvals)){
			if($swithval == 7){/* 发起打款申请详细页面 */
				$selav =  $this->_request->getPost ("selav","");
				$username = $this->_request->getPost ("username","");
				
				$body["parma"]["typeId"]=123;
				$body["parma"]["rcvAct"]=123;
				$body["parma"]["depositAmt"]=123;
				$body["parma"]["memo"]="momomomomo";
				$body["parma"]["userBankStruc"]["bankId"] = "";
				$body["parma"]["userBankStruc"]["bankAccount"] = "" ;
				$body["parma"]["userBankStruc"]["bankNumber"] = "" ;
				$body["parma"]["userBankStruc"]["branchName"] = "" ;
				$body["parma"]["userBankStruc"]["mcBankId"] = "" ;
				$body["parma"]["userBankStruc"]["branchAddr"] = "" ;
				
				
				
					/* 处理打款申请 */
				
				$this->view->username = $username ;
				$this->view->selav = $selav;
			}
			$this->view->display ( $swithvals[$swithval] );
		}else{
			throw new FireFogException("swithval参数不正确！") ;
		}
	
	}
	
	public function accountbalanceAction(){
		/* 账户余额加减 */
		$swithvals = array(
				1=>'/admin/funds/AccountBalance/accontbalance.tpl',//账户余额加减
				2=>'/admin/funds/AccountBalance/pendingreview.tpl',//等待审核
				3=>'/admin/funds/AccountBalance/AuditingInvalid.tpl',//审核未过
				4=>'/admin/funds/AccountBalance/treatover.tpl',//处理成功
				5=>'/admin/funds/AccountBalance/opterating.tpl',//处理成功
				6=>'/admin/funds/AccountBalance/applysa.tpl',//处理成功
		);
		$swithval = $this->_request->getParam("swithval",1) ;
		if(array_key_exists($swithval,$swithvals)){
			$this->view->display ( $swithvals[$swithval] );
		}else{
			throw new FireFogException("swithval参数不正确！") ;
		}
	}
	
public function depositApply(){
	//打款请求
	$radion_bank =  $this->_request->getPost("radion_bank");
	$inmoney =  $this->_request->getPost("inmoney");
	
	
	$data=array();
	$data["param"]["typeId"]= 1 ;
	$data["param"]["rcvAct"]="";
	$data["param"]["depositAmt"]="";
	$data["param"]["memo"]="";
	$data["param"]["userBankStruc"]["bankId"]="";
	$data["param"]["userBankStruc"]["bankAccount"]="";
	$data["param"]["userBankStruc"]["bankNumber"]="";
	$data["param"]["userBankStruc"]["branchName"]="";
	$data["param"]["userBankStruc"]["mcBankId"]="";
	$data["param"]["userBankStruc"]["branchAddr"]="";
	$data["param"]["userBankStruc"]["bindDate"]="";
	//
	$this->_depositQuery->depositApply($data) ;
	
	
	
}

public function depositRemark(){
	//审核备注
	$data=array();
	$data["param"]["typeId"]= 1 ;
	$data["param"]["rcvAct"]="";
	$data["param"]["depositAmt"]="";
	$data["param"]["memo"]="";
	$res = $this->_depositQuery->depositRemark($data);
}
}