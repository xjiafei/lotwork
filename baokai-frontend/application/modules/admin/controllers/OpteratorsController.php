<?php
class Admin_OpteratorsController extends Fundcommon
{
	protected $_funds,$_arrayType,$_arrayStatus,$_page,$_pagesize;
	protected $_chargeQuery;
	protected $_manulMutilUploadDoc;
	protected $_param;

	//初始化
	public function init() {
		parent::init ();
		$this->_funds =  new Funds();
		$this->_chargeQuery = new ChargeQueryDao();
		/* $this->_customersdraw = new  CustomersDraw();
		$this->_queryFundWithdrawOrder = new QueryFundWithdrawOrderDao();
		$this->_fundWithdraw = new FundWithdraw();
		$this->_bankParamsSet =new BankParamsSetDao();
		$this->_exceptionGameCoin =new ExceptionGameCoin(); */
		$this->_depositQuery = new DepositQuery();
		$this->_queryBankCardRecords = new QueryBankCardRecords();
		$this->_page =0;
		$this->_pagesize =10;
		$this->_arrayType = array("无数据","打款-人工打款","打款-人工提现","加币-活动礼金",5=>"加币-投诉理赔","加币-平台奖励",'加币-人工加币',
						9=>"加币-人工充值",8=>"扣币-人工扣币","加币-PT活动奖金","加币-彩票分红","加币-PT活动礼金","加币-PT佣金","加币-奖金派送");//"信誉保证" "扣币-重复打款"去掉
		$this->_arrayStatus = array("未处理","处理中","已拒绝","扣币成功","加币成功","打款完全成功","打款部分成功","打款失败","充值成功","管理员关闭");
		$this->_manulMutilUploadDoc = new Rediska_Key_Hash(md5('ANVO_MANUL_MUTIL_UPLOAD_DOC'.$this->_sessionlogin->info['id']));
	}

	//入口
	public function indexAction(){
		$this->_param = getSecurityInput($this->_request->get("parma"));
		
		$aParam = array(
				"sv1"=>"displayDepositePage",//人工资金操作审核流程
				"opter1"=>"addDeposteData",//人工资金操作建单
				"sv2"=>"auditDeposit", //人工资金操作审核
 				"de"=>"loadDepositData",//加载人工资金数据
 				"ex"=>"downloadDepositData", //下载人工资金操作数据
				"adraw"=>"auditFundWithdraw", //不用
				"qbch"=>"queryBankCardHistory",//不用
				"qbbc"=>"queryBoundbankCard", //查询相关用户绑卡信息
				"chkoptcharge"=>"checkOprateCharge", //查询人工充值订单
				"delCharge"=>"adChargeCancel", //关闭人工充值订单
				'multilist' => 'displayMultiListPage',//显示批量操作页面
				'loadmutldata' => 'LoadMultiListPage',//加载批量数据
				'multiupload' => 'uploadMutiUserDoc',//显示批量操作页面
				'dealmutil' => 'dealMutilData',//批量审核 人工操作
				'addmutil' => 'addMultiData',//添加批量数据操作
				'aas'=>'activityAwards',//人工活动派奖操作
				'aasloadmutldata'=>'loadAwardsListPage', //加载批量派獎数据
				'aassubmit'=>'awardsSubmit', //送出派獎資訊
		);

		if(array_key_exists($this->_param,$aParam) && array_key_exists($this->_param, $this->_aAclArray)  || $this->_param =='multiupload'){
			
				$this->$aParam[$this->_param]($this->_request);
				exit;
		}elseif(isAjaxRequest()){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}else {
			foreach ($this->_aAclArray as $key=>$value){
				if(array_key_exists($key,$aParam)){
					$this->$aParam[$key]($this->_request);
					exit;
				}
			}
			$this->_redirect('/admin/Rechargemange/');
		}
	}
	
	//加载列表
	public function displayDepositePage(){
		$this->view->_arrayStatus= $this->_arrayStatus;
		$this->view->_arrayType = $this->_arrayType;
		$this->view->title = '人工资金操作审核流程';
		$this->view->display ( '/admin/funds/opter/manualList.tpl' );
	}
	
	//查询
	public function loadDepositData(){
	
		$request = $this->getRequest();
		$data = array();
		$Title = intval(getSecurityInput($this->_request->getPost("Title",0))) ;
		$personserial = getSecurityInput($this->_request->getPost ("personserial"));
		if($personserial){
			$data["param"]["sn"] =  $personserial;
		}
		$persontype = getSecurityInput($this->_request->getPost ("persontype"));
		if($persontype){
			$data["param"]["typeId"] =  intval($persontype);
		}
		$personuser = getSecurityInput($this->_request->getPost ("personuser"));
		if($personuser){
			$data["param"]["rcvAccount"] =  $personuser;
		}
		$isvip = getSecurityInput($this->_request->getPost ("isvip",""));
		if($isvip!=""){
			$data["param"]["isVip"] =  intval($isvip);
		}
		$createtime1 = getSecurityInput($this->_request->getPost ("createtime1"));
		if($createtime1){
			$data["param"]["gmtCreatedStart"] =  getQueryStartTime($createtime1);
		}
		$createtime2 =  getSecurityInput($this->_request->getPost ("createtime2"));
		if($createtime2){
			$data["param"]["gmtCreatedEnd"] =  getQueryEndTime($createtime2);
		}
		$createadmin = getSecurityInput($this->_request->getPost ("createadmin"));
		if($createadmin){
			$data["param"]["applyAccount"] =  $createadmin;
		}
		$personstatus = getSecurityInput($this->_request->getPost ("personstatus",''));
		$statusArray = array(array(0,1,2,3,4,5,6,7,8,9),array(0),array(1),array(2,3,4,5,6,7,8,9));
		if($personstatus!='' && in_array($personstatus, $statusArray[$Title])){
			$data["param"]["status"] =  array(intval($personstatus));
		} else {
			$data["param"]["status"] = $statusArray[$Title];
		}
		$this->_page = intval(getSecurityInput($this->_request->getPost('page',$this->_page)));
		$this->_pagesize = intval(getSecurityInput($this->_request->getPost('perPageNum',$this->_pagesize)));
		if(in_array($Title, array(0,2,3))){
			$personadmin= getSecurityInput($this->_request->getPost ("personadmin"));
			if($personadmin){
				$data["param"]["apprAccount"] =  $personadmin;
			}
			$timeend1= getSecurityInput($this->_request->getPost ("timeend1"));
			if($timeend1){
				$data["param"]["apprDateStart"] =  getQueryStartTime($timeend1);
			}
			$timeend2 = getSecurityInput($this->_request->getPost ("timeend2"));
			if($timeend2){
				$data["param"]["apprDateEnd"] =  getQueryEndTime($timeend2);
			}
		}
		
		$modata = array();
		$modatas = array();
		$recordNum = 0;
		$modatas['text'] = array();
		$modatas['count'] = array();
		if(!isset($this->_aAclArray[$this->_param][$Title])){
			header ( 'Content-Type: application/json;charset=utf-8' );
			echo json_encode($modatas);
			exit;
		}
		$data['param']['isBatch'] = 0;
		//人工调整
		//$data["param"]["statuses"] = array(1);
		$data["pager"]["startNo"] = $this->_page*$this->_pagesize+1; //启始值
		$data["pager"]["endNo"] = ($this->_page+1)*$this->_pagesize; //结束值
		$res = $this->_depositQuery->depositQuery($data);
		if (isset($res["result"]) && count($res["result"])>0){
			foreach ( $res["result"] as $recorder){
				$modata["id"] 			= $recorder->getMember("id") ;
				$modata["sn"] 			= $recorder->getMember("sn") ;//申请单号
				$modata["rcvAccount"] 	= $recorder->getMember("rcvAccount") ? $recorder->getMember("rcvAccount") : "" ;
				$typeId 				= $recorder->getMember("typeId") ? $recorder->getMember("typeId") : "0";
				$modata["typeId"] 		= strval($typeId);
				$modata["typeName"] 	= $this->_arrayType[strval($typeId)];
				$modata["applyTime"] 	= $recorder->getMember("applyTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : "";//简单时间
				$modata["withdrawAmt"] 	= getMoneyFomat($recorder->getMember("withdrawAmt")/$this->_moneyUnit,2) ;//
				$modata["realWithdrawAmt"] =$recorder->getMember("realWithdrawAmt") ? getMoneyFomat($recorder->getMember("realWithdrawAmt")/$this->_moneyUnit,2) : '' ;//
				$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : "" ;
				$modata["mcRemitTime"] 	= $recorder->getMember("mcRemitTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcRemitTime"))) : "" ;
				$modata["applyAccount"] = $recorder->getMember("applyAccount") ? $recorder->getMember("applyAccount") : "" ;//建单管理员
				$modata["apprAccount"] 	= $recorder->getMember("apprAccount") ? $recorder->getMember("apprAccount") : "" ;
				$modata["apprTime"] 	= $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : "" ;
				$modata["isVip"] 		= $recorder->getMember("vipLvl")>0 ? 1 : 0 ;
				$modata["status"] 		= $recorder->getMember("status") ? $this->_arrayStatus[$recorder->getMember("status")] : "未处理" ;
				$attachment = $recorder->getMember("attach");
				if(!empty($attachment)){
					$attachmentArray = explode('|', $attachment);
					$attachment= '';
					foreach ($attachmentArray as $key=>$value){
						if(!empty($value)){
							$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
						}
					}
				}
				$modata["attach"] = trim($attachment,",");
				array_push($modatas['text'],$modata) ;
			}
		}
		if(isset($res["pager"]["total"])){
			$recordNum = $res["pager"]["total"];
		}
		array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
			
	}
	
	//审核操作
	public function auditDeposit(){
		//人工资金操作审核
		header ( 'Content-Type: application/json;charset=utf-8' );
		$id 	=  getSecurityInput($this->_request->getPost ("id",''));
		$status =  getSecurityInput($this->_request->getPost ("status",''));
		$typeId =  getSecurityInput($this->_request->getPost ("typeId",''));
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(empty($id)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'ID不能为空,请刷新再次请求'));
			exit;
		}
		if(empty($status)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'请选择通过或者拒绝按钮'));
			exit;
		}
		if(empty($typeId)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'订单类型不能为空'));
			exit;
		}
		if($status =='1'){
			$statuArray = array(1=>1,1,4,5=>4,4,4,3,1,4,4,4);
			$status = $statuArray[$typeId];
		} else {
			$status = 2;
		}
		$data=array();
		$data["param"]["id"]	 = intval($id) ;
		$data["param"]["status"] = intval($status);
		$data["param"]["platfom"] = '3';// 申請平台 4.0
		$data["param"]["ver"] = '4.0';
        
		$res = $this->_depositQuery->depositAudit($data);
		if(isset($res['head']['status']) && $res['head']['status']=='0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'审核成功'));
			exit;
		} else if(in_array($res['head']['status'], array('2005','2012','2017'))){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($res['head']['status'])));
			exit;
		} else if(in_array($res['head']['status'], array('2015'))){
                    $account = $res['body']['result'];
                    $errMsg = $this->getError($res['head']['status']);
			echo Zend_Json::encode(array('status'=>'error','data'=>str_replace('{0}',$account,$errMsg)));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'审核失败'));
			exit;
		}
	}
	
	//提交建单操作
	public function addDeposteData(){
		
		if($this->_request->isPost()){
			$typeId 	= intval(getSecurityInput($this->_request->getPost ("sSelectValue",''))); //建单类型
			$rcvAct 	= strtolower(getSecurityInput($this->_request->getPost ("rcvAct",''))); //涉及用户名
			$bankCard 	= getSecurityInput($this->_request->getPost ("bankCard",'')); //涉及用户名
			$depositAmt = floatval(getSecurityInput($this->_request->getPost ("depositAmt",''))); //打款金额
			$memo 		= getSecurityInput($this->_request->getPost ("memo",'')); //建单原因
			$id 		= intval(getSecurityInput($this->_request->getPost ("id",''))); 
			$note 		= getSecurityInput($this->_request->getPost ("note",'')); //建单原因
			if($typeId<=0){
				echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请选择建单类型!'));
				exit;
			}
			if(empty($rcvAct)){
				echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请填写用户信息!'));
				exit;
			} else {
				$userInfo = $this->getUserInfo($rcvAct);
				if(!isset($userInfo['id'])){
					echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请填写用户信息!'));
					exit;
				}
			}
			if($depositAmt<=0){
				echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,打款金额不符合规范!'));
				exit;
			}
			
			switch ($typeId){
				case 2:
					if($depositAmt > floatval($userInfo['canWithdrawBal']/$this->_moneyUnit)){
						echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,提现金额不能大于用户可提现额度!'));
						exit;
					}
					break;
				case 8:
					if($depositAmt > $userInfo['availBal']/$this->_moneyUnit){
						echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,扣币金额不能大于用户余额!'));
						exit;
					}
					break;
			}
			
			if(empty($memo)){
				echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请填写清楚建单原因!'));
				exit;
			}
			$attachMent = '';
			if(count($this->_uploadsession->pdans)>0){
				$attachMent = implode('|', $this->_uploadsession->pdans);
				unset($this->_uploadsession->pdans);
			}
			//===========查询用户绑定银行卡=======如果打款 查询银行卡绑定信息============
			if (in_array($typeId, array(1,2))) {
				if($id<=0){
					echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请选择银行卡!'));
					exit;
				}
				$userid = intval(getSecurityInput($this->_request->getPost ("userId")));
				$datas  = array();
	// 			$this->getUserInfo($accountName)
				$datas["param"]["userId"] = $userid; //$userId;
				$result = $this->_queryBankCardRecords->queryBoundbankCard($datas);
				foreach ( $result["body"]["result"]["userBankStruc"]  as $recorder){
					if($recorder["id"] == $id){
						$data["param"]["userBankStruc"] = $recorder ;
						break;
					}
				}
				if(!isset($data["param"]["userBankStruc"])){
					echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,没有绑定银行卡信息!'));
					exit;
				}
			} else if(in_array($typeId, array(9))){
				if($id<=0){
					echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,请选择银行!'));
					exit;
				}
				$result = $this->getBankCardInfo('bankStruc');
				foreach ( $result  as $value){
					if($value["id"] == $id && $value['deposit'] !=0 && $value['id']<30){
						$data["param"]["bankId"] = $value['id'] ;
						break;
					}
				}
				if(!isset($data["param"]["bankId"])){
					echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败,选择的银行不可充值!'));
					exit;
				}
			}
			//===========查询用户绑定银行卡==========================================
			$data["param"]["typeId"] 	 = $typeId ;
			$data["param"]["rcvAct"] 	 = $rcvAct ;
			$data["param"]["depositAmt"] = $depositAmt*$this->_moneyUnit ;
			$data["param"]["attach"] 	 = $attachMent;
			$data["param"]["memo"] 		 = $memo ;
			$data["param"]["note"] 		 = $note ;
			$res = $this->_depositQuery->depositApply($data);
			if($res){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'创建成功'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败'));
				exit;
			}
		}
		$this->view->_arrayType = $this->_arrayType;
		$this->view->title = '创建人工新单';
		$this->view->display ( '/admin/funds/opter/manualCreate.tpl' );
	}
	
	//查询人工充值单
	public function checkOprateCharge(){
		$type = getSecurityInput($this->_request->getPost('type'));
		$sn   = getSecurityInput($this->_request->getPost('sn'));
		
		$data['param']['sn'] = $sn;
		$data['param']["isReport"] = 'rpt';
		$rsr = $this->_chargeQuery->chargeQuery($data);
		$modata = array();
		foreach ( $rsr["result"] as $recorder){
			$modata["id"] 				= $recorder->getMember("sn") ;//交易流水号
			$modata["status"] 			= $recorder->getMember("status")  ; //订单状态
			$modata["applyTime"] 		= $recorder->getMember("applyTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : 'MOW尚未返回' ; //申请时间
			$modata["chargeAmt"] 		= $recorder->getMember("applyAmt") ? getMoneyFomat($recorder->getMember("applyAmt")/$this->_moneyUnit,2) : 'MOW尚未返回' ;//申请金额
			$modata["userBankName"]		= $recorder->getMember("rcvBankId") ? $this->_bankIcoArray[$recorder->getMember("rcvBankId")]['name'] : 'MOW尚未返回' ; //收款银行
			$modata["payBankName"]		= $recorder->getMember("payBankId") ? $this->_bankIcoArray[$recorder->getMember("payBankId")]['name'] : 'MOW尚未返回' ; //充值银行
			$modata["rcvBankName"]		= $recorder->getMember("rcvBankName") ? $recorder->getMember("rcvBankName") : 'MOW尚未返回' ; //开户行名称
			$modata["rcvAccountName"] 	= $recorder->getMember("rcvAcct") ? $recorder->getMember("rcvAcct") : 'MOW尚未返回' ;//收款账户名
			$modata["rcvMail"] 			= $recorder->getMember("rcvEmail") ? $recorder->getMember("rcvEmail") : '';//收款卡
			$modata["rcvBankNumber"] 		= $recorder->getMember("rcvBankNumber") ? $recorder->getMember("rcvBankNumber") : 'MOW尚未返回';//收款卡号
			$modata["remark"] 			= $recorder->getMember("memo") ? $recorder->getMember("memo") : 'MOW尚未返回';//附言
			
			if(empty($modata["rcvMail"])){
				$modata['rcvMail'] = isset($modata['rcvBankNumber']) ? $modata['rcvBankNumber'] : '';
				$modata['rcvName'] = '收款账号';
			} else {
				$modata['rcvName'] = '收款Email地址';
			}
		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		if(count($modata)>0){
			echo json_encode(array('isSuccess'=>1,'data'=>$modata));
		} else {
			echo json_encode(array('isSuccess'=>0));
		}
	}
	
	//撤销人工充值订单
	public function adChargeCancel(){
		$sn = getSecurityInput($this->_request->getPost('id'));
		if(empty($sn)){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'订单号不能为空'));
			exit;
		} 
		$data['param']['sn'] = $sn;
		
		$status = $this->_depositQuery->adChargeCancel($data);
		if($status) {
			echo Zend_Json::encode(array('isSuccess'=>'1','data'=>'撤销人工单成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'撤销人工单失败'));
			exit;
		}
	}
	
	//导出列表
	public function downloadDepositData(){
		$fileName = '人工资金';
		$objPHPExcel = new PHPExcel();
		$aTitle = array(
			'sn'		   => '人工单单号',
			'typeId'	   => '人工单类型',
			'status'	   => '状态',
			'withdrawAmt'  => '涉及金额',
			'realWithdrawAmt'=> '实际支付金额',
			'rcvAccount'   => '涉及用户',
			'isVip'		   => '是否VIP',
			'applyTime'	   => '建单时间',
			'applyAccount' => '建单管理员',
			'memo'		   => '建单原因',
			'apprAccount'  => '审核管理员',
			'apprTime'	   => '审核结束时间'
		);
		$personserial = getSecurityInput($this->_request->get("personserial"));
		if($personserial){
			$data["param"]["sn"] =  $personserial;
		}
		$persontype = getSecurityInput($this->_request->get("persontype"));
		if($persontype){
			$data["param"]["typeId"] =  intval($persontype);
		}
		$personuser = getSecurityInput($this->_request->get("personuser"));
		if($personuser){
			$data["param"]["rcvAccount"] =  $personuser;
		}
		$isvip = getSecurityInput($this->_request->get("isvip",""));
		if($isvip!=""){
			$data["param"]["isVip"] =  intval($isvip);
		}
		$createtime1 = getSecurityInput($this->_request->get("createtime1"));
		if($createtime1){
			$data["param"]["gmtCreatedStart"] =  getQueryStartTime($createtime1);
		}
		$createtime2 =  getSecurityInput($this->_request->get("createtime2"));
		if($createtime2){
			$data["param"]["gmtCreatedEnd"] =  getQueryEndTime($createtime2);
		}
		$createadmin = getSecurityInput($this->_request->get("createadmin"));
		if($createadmin){
			$data["param"]["applyAccount"] =  $createadmin;
		}
		$this->_page = intval(getSecurityInput($this->_request->getPost('page',$this->_page)));
		$personadmin= getSecurityInput($this->_request->get("personadmin"));
		if($personadmin){
			$data["param"]["apprAccount"] =  $personadmin;
		}
		$timeend1= getSecurityInput($this->_request->get("timeend1"));
		if($timeend1){
			$data["param"]["apprDateStart"] =  getQueryStartTime($timeend1);
		}
		$timeend2 = getSecurityInput($this->_request->get("timeend2"));
		if($timeend2){
			$data["param"]["apprDateEnd"] =  getQueryEndTime($timeend2);
		}
		$personstatus = getSecurityInput($this->_request->get("personstatus",''));
		if($personstatus){
			$data["param"]["status"] =  array(intval($personstatus));
		} else {
			$data["param"]["status"] = array(2,3,4,5,6,7,8,9);
		}
		$data["param"]["isBatch"] = 0;
		$aContent = array();
		$total = 0;
		$page= $totalPage = 1;
		$querySize = 2000;
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($page-1)*$querySize+1;
			$data["pager"]["endNo"] = $page*$querySize;
			$res = $this->_depositQuery->depositQuery($data);
			if(isset($res["result"])&&count($res["result"])>0){
				foreach ( $res["result"] as $key => $recorder){
					$modata = array();
					$modata["sn"] 			= $recorder->getMember("sn") ;//申请单号
					$modata["rcvAccount"] 	= $recorder->getMember("rcvAccount") ? $recorder->getMember("rcvAccount") : "" ;
					$typeId 				= $recorder->getMember("typeId") ? $recorder->getMember("typeId") : "0";
					$modata["typeId"] 		= $this->_arrayType[strval($typeId)];
					$modata["applyTime"] 	= $recorder->getMember("applyTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : "";//简单时间
					$modata["withdrawAmt"] 	= getMoneyFomat($recorder->getMember("withdrawAmt")/$this->_moneyUnit,2) ;//
					$modata["realWithdrawAmt"] =$recorder->getMember("realWithdrawAmt") ? getMoneyFomat($recorder->getMember("realWithdrawAmt")/$this->_moneyUnit,2) : '' ;//
					$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : "" ;
					$modata["mcRemitTime"] 	= $recorder->getMember("mcRemitTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcRemitTime"))) : "" ;
					$modata["applyAccount"] = $recorder->getMember("applyAccount") ? $recorder->getMember("applyAccount") : "" ;//建单管理员
					$modata["apprAccount"] 	= $recorder->getMember("apprAccount") ? $recorder->getMember("apprAccount") : "" ;
					$modata["apprTime"] 	= $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : "" ;
					$modata["isVip"] 		= $recorder->getMember("vipLvl")>0 ? '是' : '否' ;
					$modata["status"] 		= $recorder->getMember("status") ? $this->_arrayStatus[$recorder->getMember("status")] : "未处理" ;
					$aContent[$key]			= $modata;
				}
				$totalPage = ceil($res['pager']['total']/$querySize);
				$total 	   = $res['pager']['total'];
			}
			if($totalPage<=0){
				$totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
			$aContent = array();
		} while ($page++ !=$totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['sn'] 	  = '下载数据:'.$total.'条.';
		$modata['typeId'] = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
	}
	
	//多文件上传
	public function uploadAction(){
		header ( 'Content-Type: application/json;charset=utf-8' );
		$cnt = $this->multiFilesUpload('pdans');
		if($cnt>0){
			echo Zend_Json::encode(array('status'=>'0','data'=>$cnt));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'1'));
			exit;
		}
	}
	
	//根据用户名 来查询 绑卡信息
	public function queryBoundbankCard(){
		
		$username = strtolower(getSecurityInput($this->_request->getPost ("username")));
		$type = getSecurityInput($this->_request->getPost ("type",''));
		if(empty($type)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'请选择类型!'));
			exit;
		}
		$userInfo = $this->getUserInfo($username);
		if(!isset($userInfo['id'])){
			echo Zend_Json::encode(array('status'=>'error','data'=>'不存在此用户,请确认输入的用户正确!'));
			exit;
		}
		$result['userid'] = $userInfo['id'];
		$result['availBal'] = isset($userInfo['availBal']) ? floatval($userInfo['availBal']/$this->_moneyUnit) : 0;
		$result['canWithdrawBal'] = isset($userInfo['canWithdrawBal']) ? floatval($userInfo['canWithdrawBal']/$this->_moneyUnit) : 0;
		if(in_array(intval($type),array(1,2))){
			$data = array();
			$data["param"]["userId"] = $userInfo["id"]; //$userId;
			$res = $this->_queryBankCardRecords->queryBoundbankCard($data);
			if(!isset($res["body"]["result"]['userBankStruc']) || count($res["body"]["result"]['userBankStruc'])<=0){
				echo Zend_Json::encode(array('status'=>'error','data'=>'用户没有绑定银行卡!'));
				exit;
			}
			foreach ($res["body"]["result"]['userBankStruc'] as $key=>$value){
				$result['userBankStruc'][$key]['bankNumber'] = $this->getSecurityBankCardNum($value['bankNumber']);
				$result['userBankStruc'][$key]['bankId'] 	= $value['bankId'];
				$result['userBankStruc'][$key]['id'] 		= $value['id'];
				$result['userBankStruc'][$key]['bankIco'] 	= $this->_bankIcoArray[$value['bankId']]['logo'];
				$result['userBankStruc'][$key]['bankAccount'] 	= $this->getSecurityBankCardAucount($value['bankAccount']);
			}
			
			$result['bankStruc'] = '';
		}else if(in_array(intval($type), array(9))){
			$aRecorders = $this->getBankCardInfo('bankStruc');
			$bankList = array();
			foreach ($aRecorders as $key=>$value){
				if($value['deposit'] !=0 && $value['id']<30){
					$bankList[$key]['id'] 	= $value['id'];
					$bankList[$key]['name'] = $value['name'];
					$bankList[$key]['name'] = $value['name'];
					$bankList[$key]['logo'] = $value['logo'];
					if($userInfo['userLvl'] =='1'){
						$bankList[$key]['upLimit']  = $value['vipUpLimit']/$this->_moneyUnit;
						$bankList[$key]['lowLimit'] = $value['vipLowLimit']/$this->_moneyUnit;
					} else {
						$bankList[$key]['upLimit'] 	= floatval($value['upLimit'])/$this->_moneyUnit;
						$bankList[$key]['lowLimit'] = floatval($value['lowLimit'])/$this->_moneyUnit;
					}
				}
			}
			$result['bankStruc'] = $bankList;
			$result['userBankStruc'] = '';
		} else {
			$result['userBankStruc'] = '';
			$result['bankStruc'] = '';
		}
		echo Zend_Json::encode(array('status'=>'ok','data'=>array($result)));
		exit;
	}
	
	
	//=====================人工批量操作==================================================
	
	//显示人工批量页面
	public function displayMultiListPage(){
		$aTypeArray = $aStatusArray = array();
		foreach ($this->_arrayType as $key=>$value){
			if(in_array($key, array('3','10','11','12','13','14'))){
				$aTypeArray[$key] = $value;
			}
		}
		foreach ($this->_arrayStatus as $key=>$value){
			if(in_array($key, array(2,4))){
				$aStatusArray[$key] = $value;
			}
		}
		$this->view->title = '人工批量操作';
		$this->view->aTypeArray = $aTypeArray;
		$this->view->aStatusArray = $aStatusArray;
		$this->view->display('/admin/funds/opter/mutiloptdisplay.tpl');
	}
	
	//加载人工批量数据
	public function LoadMultiListPage(){
		
		$Title  = getSecurityInput($this->_request->getPost("Title",0)) ;
		$status = getSecurityInput($this->_request->getPost("status",0)) ;
		$this->_page     = getSecurityInput($this->_request->getPost("page",0)) ;
		$this->_pagesize = getSecurityInput($this->_request->getPost("perPageNum",0)) ;
		$aStatus = array(0);
		if($Title == '1'){
			if($status){
				$aStatus= array($status);
			} else {
				$aStatus = array(2,4);
			}
		}
		$modata = array();
		$modatas = array();
		$recordNum = 0;
		$modatas['text'] = array();
		$modatas['count'] = array();
		//权限检查
		if(!isset($this->_aAclArray[$this->_param][$Title])){
			header ( 'Content-Type: application/json;charset=utf-8' );
			array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
			echo json_encode($modatas);
			exit;
		}
		$data["param"]["status"] = $aStatus;
		//显示批量操作 数据
		$data['param']['isBatch'] = 1;
		//人工调整
		//$data["param"]["statuses"] = array(1);
		$data["pager"]["startNo"] = $this->_page*$this->_pagesize+1; //启始值
		$data["pager"]["endNo"] = ($this->_page+1)*$this->_pagesize; //结束值
		$res = $this->_depositQuery->depositQuery($data);
		if (isset($res["result"]) && count($res["result"])>0){
			foreach ( $res["result"] as $recorder){
				$modata["id"] 			= $recorder->getMember("id") ;
				$modata["sn"] 			= $recorder->getMember("sn") ;//申请单号
				$modata["rcvAccount"] 	= $recorder->getMember("rcvAccount") ? $recorder->getMember("rcvAccount") : "" ;
				$typeId 				= $recorder->getMember("typeId") ? $recorder->getMember("typeId") : "0";
				$modata["typeId"] 		= strval($typeId);
				$modata["typeName"] 	= $this->_arrayType[$modata["typeId"]];
				$modata["applyTime"] 	= $recorder->getMember("applyTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : "";//简单时间
				$modata["withdrawAmt"] 	= getMoneyFomat($recorder->getMember("withdrawAmt")/$this->_moneyUnit,2) ;//
				$modata["realWithdrawAmt"] =$recorder->getMember("realWithdrawAmt") ? getMoneyFomat($recorder->getMember("realWithdrawAmt")/$this->_moneyUnit,2) : '' ;//
				$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : "" ;
				$modata["mcRemitTime"] 	= $recorder->getMember("mcRemitTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcRemitTime"))) : "" ;
				$modata["applyAccount"] = $recorder->getMember("applyAccount") ? $recorder->getMember("applyAccount") : "" ;//建单管理员
				$modata["apprAccount"] 	= $recorder->getMember("apprAccount") ? $recorder->getMember("apprAccount") : "" ;
				$modata["apprTime"] 	= $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : "" ;
				$modata["isVip"] 		= $recorder->getMember("vipLvl")>0 ? 1 : 0 ;
				$modata["status"] 		= $recorder->getMember("status") ? $this->_arrayStatus[$recorder->getMember("status")] : "未处理" ;
				array_push($modatas['text'],$modata) ;
			}
		}
		if(isset($res["pager"]["total"])){
			$recordNum = $res["pager"]["total"];
		}
		array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	//上传批量操作文档
	public function uploadMutiUserDoc(){
		
		//检查权限
		if(!isset($this->_aAclArray[$this->_param])){
			$rs = array('isSuccess'=>'0','data'=>$this->getError('102144'));
			$this->uploadStatus($rs);
		}
		
		
		$type = intval(getSecurityInput($this->_request->getPost('type')));
		$typeStatus = array(3=>'活动礼金',10=>'PT活动奖金',11=>'彩票分红',12=>'PT活动礼金', 13=>'PT佣金', 14=>'奖金派送');
		
		//检测人工单类型是否在允许批量加币范围内
		if(!array_key_exists($type, $typeStatus)){
			$rs = array('isSuccess'=>'0','data'=>'人工单类型不正确');
			$this->uploadStatus($rs);
		}
		
		$files = $this->singleFileUpload('mutltiDoc','files',array('xlsx','xls'));
		if(!empty($files['file'])){
			$upload_dir = $this->_config->upload_dir.'/upload/files/';
			$filename = $upload_dir.$files['file'];
			$phpExcel = new PHPExcel();
			$phpReader = new PHPExcel_Reader_Excel2007();
			$phpExcel = $phpReader->load($filename);
			$currentSheet = $phpExcel->getSheet(0);
			$allColumns = $currentSheet->getHighestColumn();
			$allRow = $currentSheet->getHighestRow();
			if($allRow>501){
				$rs = array('isSuccess'=>'0','data'=>'超过加币笔数');
				$this->uploadStatus($rs);
			}
			$aContent =$aResult = array();
			for($currentRow = 2;$currentRow<=$allRow;$currentRow++){
				for($currentColumn='A';$currentColumn<=$allColumns;$currentColumn++){
					$address = $currentColumn.$currentRow;
					$aContent[$currentRow][] = $currentSheet->getCell($address)->getValue();
					if($currentColumn == 'A'){
						$aUserArray[] = $currentSheet->getCell($address)->getValue();
					}
				}
			}
			$data['param']['accounts'] = $aUserArray;
			unset($aUserArray);
			$result = $this->_depositQuery->queryUserDetailInfoByAccounts($data);
			if(isset($result['body']['result'] ) && count($result['body']['result'] )>0){
				foreach ($result['body']['result'] as $key=>$value){
					$aUserArray[$value['account']] = $value['id'];
				}
			}
			
			foreach ($aContent as $value){
				if(empty($value[0])){
					$rs = array('isSuccess'=>'0','data'=>'用户名不能为空');
					$this->uploadStatus($rs);
				}
				if($value[1] >5000){
					$rs = array('isSuccess'=>'0','data'=>'金额不能大于5000.00元');
					$this->uploadStatus($rs);
				}
				if($value[2]!=$typeStatus[$type]){
					$rs = array('isSuccess'=>'0','data'=>'人工单类型不正确');
					$this->uploadStatus($rs);
				}
				if(empty($value[3])){
					$rs = array('isSuccess'=>'0','data'=>'建单原因不能为空');
					$this->uploadStatus($rs);
				}
				if(strlen($value[4]) > 90){
					$rs = array('isSuccess'=>'0','data'=>'备注 长度太长');
					$this->uploadStatus($rs);
				}
				$content = array();
				$content['account'] = getSecurityInput($value[0]);
				$content['mount'] 	= floatval(getSecurityInput($value[1]));
				$content['type'] 	= $type;
				$content['memo'] 	= getSecurityInput($value[3]);
				$content['note'] 	= getSecurityInput($value[4]);
				if(array_key_exists($content['account'], $aUserArray)){
					$content['viliable'] = 1;
					$content['id'] = $aUserArray[$content['account']];
				} else {
					$content['viliable'] = 0;
				}
				array_push($aResult, $content);
			}unset($aContent);unlink($filename);
			
			
			
			$this->_manulMutilUploadDoc->set('docData',$aResult);
			$this->_manulMutilUploadDoc->set('docType',$type);
			$rs =  array('isSuccess'=>'1','data'=>$aResult);
			$this->uploadStatus($rs);
		}
		$rs = array('isSuccess'=>'0','data'=>'文件上传失败!');
		$this->uploadStatus($rs);
	}
	
	
	//返回上传处理结果
	private function uploadStatus($rs){
		$rs =  Zend_Json::encode($rs);
		echo '<script>(function(){var Games = window.parent.getFile,data='.$rs.'; Games(data)})()</script>';
		exit;
	}
	
	//批量添加数据
	public function addMultiData(){
		$aResult = $this->_manulMutilUploadDoc->get('docData');
		$docType = $this->_manulMutilUploadDoc->get('docType');
		
		if(empty($aResult)){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'上传的文件已经失效,请重新上传!'));
			exit;
		} else {
			foreach ($aResult as $key=>$value){
				if($value['viliable'] == '0'){
					echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'您提交的内容中部分用户名不存在，请核实后重新上传!'));
					exit;
				}
				$param[$key]["typeId"] 	 = $value['type'];
				$param[$key]["rcvAct"] 	 = $value['account'];
				$param[$key]["rcvId"] 	 = $value['id'];
				$param[$key]["depositAmt"] = $value['mount']*$this->_moneyUnit;
				$param[$key]["memo"] 	   = $value['memo'];
				$param[$key]["note"] 	   = $value['note'];
			}
		}
		$data["param"] = $param;
		$res = $this->_depositQuery->depositApplys($data);
		if($res){
			$this->_manulMutilUploadDoc->delete();
			echo Zend_Json::encode(array('status'=>'ok','data'=>'创建成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'创建失败'));
			exit;
		}
	}
	
	
	//批量审核操作
	public function dealMutilData(){
		$aIdArray = $this->_request->getPost('id');
		$status = intval(getSecurityInput($this->_request->getPost('status','2')));
		
		//检查权限
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>$this->getError('102144')));
			exit;
		}
		
		if(!in_array($status, array(1,2))){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'请选择通过或者拒绝操作!'));
			exit;
		}
		$param=array();
		foreach ($aIdArray as $key=>$value){
			
			$arr = explode('|', $value);
			$id = intval(getSecurityInput(array_shift($arr)));
			$typeId = intval(getSecurityInput(array_shift($arr)));
			//没传订单ID 不处理该订单
			if($id <=0){
				continue;
			}
			$statuArray = array(1=>1,1,4,5=>4,4,4,3,1,4,4,4,4,14);
			if($status ==1){
				//如果前台传一个不存在的 类型 则跳过该条记录 不处理
				if(!isset($statuArray[$typeId])){
					continue;
				}
				$iStatus = $statuArray[$typeId];
			} else {
				$iStatus = 2;
			}
			$tmp["id"]	 = intval($id);
			$tmp["status"] = intval($iStatus);
			$param[] = $tmp;
		}
		unset($tmp);
		if(count($param)==0){
			echo Zend_Json::encode(array('status'=>'error','data'=>'请选择审核项!'));
			exit;
		}
		
		$data["param"] = $param;
		$res = $this->_depositQuery->depositAudits($data);
		header ( 'Content-Type: application/json;charset=utf-8' );
		if(isset($res['head']['status']) && $res['head']['status']=='0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'审核成功'));
			exit;
		} else if(in_array($res['head']['status'], array('2005','2012','2017'))){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($res['head']['status'])));
			exit;
		}  else if(in_array($res['head']['status'], array('2015'))){
                    $account =$res['body']['result'];
                    $errMsg=$this->getError($res['head']['status']);
			echo Zend_Json::encode(array('status'=>'error','data'=>str_replace('{0}',$account,$errMsg)));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'审核失败'));
			exit;
		}
		
	}

	//人工活动派奖操作
	public function activityAwards(){
		$status=getSecurityInput($this->_request->getPost ("status",0));

		if($status==0){ //初始化頁面
			//撈取需要人工派獎的活動及對應時間
			$res = $this->getActivityNameDate();
			$startTime=$res[0]['startTime']/1000; //取陣列第一個活動的時間，轉換unix timestamp 13-dig
			$endTime=($res[0]['endTime'])/1000; //取陣列第一個活動的時間，轉換unix timestamp 13-dig

			$startDate = date('Y-m-d',($res[0]['startTime']/1000));
			$act_date=array(); //陣列第一個活動開始到結束的日期
			for($i=0;$i<=1000 ;$i++){
				$data=date('Y-m-d',strtotime($startDate.'+'.$i.' day'));
				if($data==date('Y-m-d',($res[0]['endTime'])/1000)){
					//array_push($act_date,$data);  抓取到00:00:00的前一天
					break;
				}
				array_push($act_date,$data);
			}
			$this->view->title = '人工活动派奖';
			$this->view->res=$res;
			$this->view->actdate=$act_date;
			$this->view->display('/admin/funds/opter/mutiloptawards.tpl');
		}else if($status==1){ //依照活動名稱查詢對應日期
			$index         = getSecurityInput($this->_request->getPost ("index",0));
			//撈取需要人工派獎的活動及對應時間
			$res = $this->getActivityNameDate();
			
			$startTime=$res[$index]['startTime']/1000; //取陣列第X個活動的時間，轉換unix timestamp 13-dig
			$endTime=($res[$index]['endTime'])/1000; //取陣列第X個活動的時間，轉換unix timestamp 13-dig
			
			$startDate = date('Y-m-d',($res[$index]['startTime']/1000));
			$act_date=array(); //陣列第一個活動開始到結束的日期
			for($i=0;$i<=1000 ;$i++){
				$data=date('Y-m-d',strtotime($startDate.'+'.$i.' day'));
				if($data==date('Y-m-d',($res[$index]['endTime'])/1000)){
					//array_push($act_date,$data);  抓取到00:00:00的前一天
					break;
				}
				array_push($act_date,$data);
			}
			echo json_encode($act_date);
			exit;
			
		}
	}
	//加载批量派獎数据
	public function loadAwardsListPage(){
		$status         = getSecurityInput($this->_request->getPost ("status",0));		
		$activityId 	= getSecurityInput($this->_request->getPost ("activityId",0));
		$activityDate 	= getSecurityInput($this->_request->getPost ("activityDate",0));
		//-------------------------------------需要接口添加相关查询字段---------------
		$page 		= getSecurityInput($this->_request->getPost ("page",0));//当前页面
		$perPageNum = getSecurityInput($this->_request->getPost ("perPageNum",0)); //每页面数量	
		$data=array();
		if(isset($activityId)){
	    	$param["actId"] 	= $activityId;
		}
		if(isset($activityDate)){
			$param["actDate"] 	= $activityDate;
		}
		$data['param'] = $param;
		$start 		= $page+1 ;
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"]	  = $start*$perPageNum;
		
		if($status ==0){ //未派獎
			$aUri = array('fundadmin'=>'querywinningloguntreat');
		}if($status ==1){ //已派獎
			$aUri = array('fundadmin'=>'querywinninglogtreat');
		}
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		$modata = array();
		$modatas = array();
		$recordNum = 0;
		$modatas['text'] = array();
		$modatas['count'] = array();
		if (isset($res["body"]["result"]) && count($res["body"]["result"])>0){
			foreach ( $res["body"]["result"] as $recorder){
				$modata["id"]           = $recorder["id"]; 
				$modata["userId"]       = $recorder["userId"]; 
				$modata["account"] 		= $recorder["account"];
				$modata["changeAmt"] 	= number_format($recorder["changeAmt"]/10000,2, '.' ,',');
				if($status ==0){ //未派獎
					$modata["approverAmt"] 	= number_format($recorder["changeAmt"]/10000,2, '.' ,',');
				}if($status ==1){//已派獎
					$modata["approverAmt"] 	= number_format($recorder["approverAmt"]/10000,2, '.' ,',');
				}
				$modata["approverMemo"] = $recorder["approverMemo"] ? $recorder["approverMemo"] : "";
				
				array_push($modatas['text'],$modata) ;
			}
		}

		if(isset($res["body"]["pager"]["total"])){
			$recordNum = $res["body"]["pager"]["total"];
		}
		
		array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
		exit;		
	}
	//送出派獎資訊
	public function awardsSubmit(){
		$awardData         = $this->_request->getPost('data');
		$data['param']     = $awardData;
		$aUri = array('fundadmin'=>'awardsapply');
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		
		echo json_encode($res);
		exit;
	}
	
	
}
