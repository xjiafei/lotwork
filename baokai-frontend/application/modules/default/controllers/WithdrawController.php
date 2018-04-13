<?php
class Default_WithdrawController extends Fundcommon{
	private $_pagesize,$_total,$_page,$_status,$_accountSecurity;
	public function init(){
		parent::init();
		$this->_pagesize = 15;
		$this->_total =0;
		$this->_page = 1;
		$this->_status = array(0,1,2,3,4,5,6);
		$this->_errView = 'ucenter';
		$this->_accountSecurity = new AccountsSecurity();
	}
	
	
	//加载提现界面
	public function indexAction(){
		//检测安全密码是否设置
// 		unset($this->_sessionlogin->withDrawResult);
		$stp = intval ( getSecurityInput($this->_request->getParam ( "stp" )) );
		$aBankDraw = $bankInfo = array();
		$this->res_add_url("我要提现","/withdraw");
		$this->res_add_url("我要提现","/withdraw",true);
		//-----------------获取银行卡信息 并构造数据 银行卡是否可提现------------------------------
		$bankInfo = $this->getBankCardInfo('bankStruc');
		foreach ($bankInfo as $key=>$value){
			$aBankDraw[$value['mownecumId']] = $value['withdraw'];
		}
		unset($bankInfo);
		//-----------------获取银行卡信息 并构造数据 银行卡是否可提现------------------------------
		$aUri = array('fund'=>'initfundwithdraw');
		$data = array(
				'param' => array(
						'userId'=>$this->_sessionlogin->info['id'], //设置 传输給接口的UserId
						'vipLvl'=>$this->_sessionlogin->info['vipLvl'], //是否vip
						'bindcardType' => '0' //只查一般銀行卡
				)
		);
		$aRecorders = $this->_clientobject->inRequestV2($data, $aUri);
		if (!isset($aRecorders['body']['result']) || empty ( $aRecorders['body']['result'] )) {
			$this->addErr("102011");
			$this->res_show(true,true);
		}
		$oRecorders = $aResult = $otherArray = $aDataArray = array();
		foreach ($aRecorders['body']['result'] as $key=>$value){
			if($key =='userBankStruc'){
				//没有绑定银行卡去绑卡
				if(count($value)<=0){
					$this->view->title = '银行卡绑定';
					$this->view->display ( 'default/ucenter/fund/wilbindbankcard.tpl' );
					exit;
				} else{
					$data = array(
							'returnUrl'=> '/withdraw/',
							'returnTitle'=>'我要提现',
							'title'=>'您还没有设置安全验证信息，请设置后再进行提现。',
							'content' => '为了保障您的账户资金安全，将在提现申请过程中验证您的安全验证信息。'
					);
					//检测是否设置安全密码
					$this->checkSecurityPassword($data);
					
					
					foreach ($value as $subKey=>$subValue){
						$oRecorders['userBankStruc'][$subKey] = new Fundwithdraw($subValue);
					}
				}
			} else if($key =='withdrawStruc'){
				if(isset($value[$this->_userType])) {
					$oRecorders['withdrawStruc'][$this->_userType] = new Fundwithdraw($value[$this->_userType]);
				} else {
					$oRecorders['withdrawStruc']='';
				}
			} else{
				$otherArray[$key] = $value;
			}
		}
		$oRecorders['firstKey'] = new Fundwithdraw($otherArray);
		unset($otherArray);
		foreach ($oRecorders as $key=>$value){
			if($key =='userBankStruc'){
				foreach ($value as $subKey=>$subValue){
					$binkId = $subValue->getMember('bankId');
					$bindDate = $subValue->getMember('bindDate');
					//页面需要的银行卡数据
					//检测银行卡是否新添加卡 检测银行卡是否可提现
					if(strtotime('-1 hours')<getSrtTimeByLong($bindDate)) {
						$aResult[$key][$subKey]['isAbleWithdraw']    = -1;
						$aDataArray[$key][$subKey]['isAbleWithdraw'] = -1;
					} else if(!isset($aBankDraw[$binkId]) || $aBankDraw[$binkId] != '1'){
						$aResult[$key][$subKey]['isAbleWithdraw']    = -2;
						$aDataArray[$key][$subKey]['isAbleWithdraw'] = -2;
					} else {
						$aResult[$key][$subKey]['isAbleWithdraw']    = 1;
						$aDataArray[$key][$subKey]['isAbleWithdraw'] = 1;
					}
					
					$aResult[$key][$subKey]['id'] 	   	   = $subValue->getMember('id');
					$aResult[$key][$subKey]['bankId'] 	   = $subValue->getMember('bankId');
					$aResult[$key][$subKey]['bankAccount'] = $subValue->getMember('bankAccount');
					$aResult[$key][$subKey]['bankNumber']  = $subValue->getMember('bankNumber');
					$aResult[$key][$subKey]['branchName']  = $subValue->getMember('branchName');
					$aResult[$key][$subKey]['mcBankId']    = $subValue->getMember('mcBankId');
					//$aResult[$key][$subKey]['branchAddr']  = $subValue->getMember('branchAddr');
					$aResult[$key][$subKey]['bindDate']    = $subValue->getMember('bindDate');
					
					
					$aDataArray[$key][$subKey]['id'] 	  	  = $aResult[$key][$subKey]['id'];
					$aDataArray[$key][$subKey]['bankId'] 	  = $aResult[$key][$subKey]['bankId'];
					$aDataArray[$key][$subKey]['bankIco'] 	  = isset($this->_bankIcoArray[$aResult[$key][$subKey]['bankId']]) ? $this->_bankIcoArray[$aResult[$key][$subKey]['bankId']]['logo'] : '';
					$aDataArray[$key][$subKey]['bankAccount'] = $this->getSecurityBankCardAucount($aResult[$key][$subKey]['bankAccount']);
					$aDataArray[$key][$subKey]['bankNumber']  = $this->getSecurityBankCardNum($aResult[$key][$subKey]['bankNumber']);
				}
			} else if($key =='withdrawStruc'){
				if (isset($value[$this->_userType])) {
					$aResult[$key][$this->_userType]['time'] 		= $value[$this->_userType]->getMember('time');
					$aResult[$key][$this->_userType]['lowLimit']    = ($value[$this->_userType]->getMember('lowLimit'))/$this->_moneyUnit;
					$aResult[$key][$this->_userType]['upLimit'] 	= ($value[$this->_userType]->getMember('upLimit'))/$this->_moneyUnit;
				} else {
					$aResult[$key][$this->_userType]['time'] 		= 0;
					$aResult[$key][$this->_userType]['lowLimit']    = 0;
					$aResult[$key][$this->_userType]['upLimit'] 	= 0;
				}
				//页面需要的限额数据
				$aDataArray[$key]['time'] 	  = $aResult[$key][$this->_userType]['time'] =='-1' ? '无限' : number2chinese($aResult[$key][$this->_userType]['time']);
				$aDataArray[$key]['lowLimit'] = getMoneyFomat($aResult[$key][$this->_userType]['lowLimit'],2);
				$aDataArray[$key]['upLimit']  = getMoneyFomat($aResult[$key][$this->_userType]['upLimit'],2);
			} else {
				$aResult['availWithdrawTime'] = $value->getMember('availWithdrawTime');
				$aResult['bal'] 			  = $value->getMember('bal')/$this->_moneyUnit;
				$aResult['availBal'] 		  = $value->getMember('availBal')/$this->_moneyUnit;
			}
		}
		unset($oRecorders);
		$this->_sessionlogin->fundWithDraw = $aResult;//保存返回信息到session
		//剩余提现次数
		$availWithdrawTime  = $aResult['withdrawStruc'][$this->_userType]['time'] == '-1' ? '无限' : ($aResult['availWithdrawTime']>=0 ? $aResult['availWithdrawTime'] : '0');
		//可用余额
		$bal 				= getMoneyFomat($aResult ["bal"],2);
		//可提金额
		$availBal 			= getMoneyFomat($aResult ["availBal"],2);
		//是否可提现
		$enableWithdraw 	= in_array($this->_sessionlogin->info['freezeMethod'], array(1,2,4,5)) ? 0 : 1; 
		$this->view->wdrawTime  = $availWithdrawTime;
		$this->view->availBal   = $bal;
		$this->view->freeBal    = $availBal;
		$this->view->aDataArray = $aDataArray;
		$this->view->stp   		= $stp;
		$this->view->enableWithdraw = $enableWithdraw;
		$this->view->title = "我要提现";
		$this->view->display ( 'default/ucenter/fund/withdraw.tpl' );
	}
	
	public function checkdrawAction(){
		$id 		  = intval(getSecurityInput($this->_request->getPost('id')));
		$changeCount  = abs(floatval(str_replace(',', '', getSecurityInput($this->_request->getPost('changeCount','0')))));
		$this->res_add_url("重新发起提款","/withdraw");
		$this->res_add_url("重新发起提款","/withdraw",true);
		$aRecorder = $this->_sessionlogin->fundWithDraw;
		$aBankCardInfo = array();
		if(count($aRecorder)<=0){
			$this->addErr("102018");
			$this->res_show(true,true);
		}
		if(empty($id)){
			$this->addErr("102046");
			$this->res_show(true,true);
		}
		if($changeCount<=0){
			$this->addErr('102060');
			$this->res_show(true,true);
		}
		//提现次数验证
		if($aRecorder['withdrawStruc'][$this->_userType]['time'] !='-1' && $aRecorder['availWithdrawTime']<=0){
			$this->addErr("102020");
			$this->res_show(true,true);
		}
		//提现金额验证
		if($changeCount < $aRecorder['withdrawStruc'][$this->_userType]['lowLimit']
		|| $changeCount > $aRecorder['withdrawStruc'][$this->_userType]['upLimit']
		|| $changeCount > $aRecorder['availBal']){
			$this->addErr("102019");
			$this->res_show(true,true);
		}
		//提现功能是否被冻结
		if(in_array($this->_sessionlogin->info['freezeMethod'], array(1,2,4,5))){
			$this->addErr("102123");
			$this->res_show(true,true);
		}
		//是否有保存用户绑定银行卡信息
		if(count($aRecorder['userBankStruc'])>0){
			foreach ($aRecorder['userBankStruc'] as $value){
				if($value['id'] == $id){
					if($value['isAbleWithdraw'] =='-1'){
						$this->addErr("102139");
						$this->res_show(true,true);
					} else if($value['isAbleWithdraw'] =='-2'){
						$this->addErr("102145");
						$this->res_show(true,true);
					} else {
						$aBankCardInfo = $value;
					}
				}
			}
		}/* else {
			//调用接口 去除选定银行的 绑定信息
			$aBankCardInfo = $this->getbindBankCardInfo($id);
			$aBankCardInfo = $aBankCardInfo['userBankStruc'];
		} */
		if (count($aBankCardInfo)<=0) {
			$this->addErr("102018");
			$this->res_show(true,true);
		}
		$aBankCardInfo['id'] 		  = $aBankCardInfo['id'];
		$aBankCardInfo['bankAccount'] = $this->getSecurityBankCardAucount($aBankCardInfo['bankAccount']);
		$aBankCardInfo['bankNumber']  = $this->getSecurityBankCardNum($aBankCardInfo['bankNumber']);
		$aBankCardInfo['bankIco']     = $this->_bankIcoArray[$aBankCardInfo['bankId']]['logo'];
		$this->view->changeCount 	  = getMoneyFomat($changeCount,2);
		$this->view->aBankCardInfo 	  = $aBankCardInfo;
		//安全问题
		$aQuStruc = $this->_sessionlogin->info['quStruc'];
		$id = rand(0, 2);
		$sQus = $this->_accountSecurity->getQuestionById($aQuStruc[$id]['qu']);
		$this->view->quStruc = array('id'=>$aQuStruc[$id]['qu'],'qus'=>$sQus);
		unset($aQuStruc,$sQus);
		$isBindSecCard = isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']) ? 1 : 0;
		//產生公私鑰並儲存私鑰
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$res_rsa = $this->encrypt_RSA(md5('withdraw' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		
		$this->view->isBindSecCard = $isBindSecCard;
		$this->view->title = "提现确认";
		$this->view->display('default/ucenter/fund/withdraw_conform.tpl');
	}
	
	//确认提现
	public function postdrawAction(){
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('withdraw' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('2016')));
			exit;
		}
		parse_str($rsa_result, $post_data);
		//print_r($post_data);
		//exit;
		
		$id 		  	  = intval(getSecurityInput($post_data['id']));
		$changeCount 	  = abs(floatval(number_format(getSecurityInput(str_replace(',', '', $post_data['changeCount'])),2,'.','')));
		$securityPassword = base64_decode(str_pad(strtr($post_data["passowrd"], '-_', '+/'), strlen($post_data["passowrd"]) % 4, '=', STR_PAD_RIGHT));
		$type = getSecurityInput($post_data['type']);
		$this->res_add_url("我要提现","/withdraw");
		$this->res_add_url("我要提现","/withdraw",true);
		
		$status = $this->checkRightSecurityPassword($securityPassword);//检测安全密码是否正确
		if($status !== TRUE){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError($status)));
			exit;
		}
		//提现功能是否被冻结
		if(in_array($this->_sessionlogin->info['freezeMethod'], array(1,2,4,5))){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102123')));
			exit;
		}
		//安全问题
		if($type == '1'){
			$answer = getSecurityInput(trim($post_data['answer']));
			$questionid = intval(getSecurityInput($post_data['questionid']));
			/*$quStruc = $this->_sessionlogin->info['quStruc'];
			 $ans = isset($quStruc[$questionid]['ans']) ? $quStruc[$questionid]['ans'] : '';
			if(md5($answer) != $ans){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102016')));
				exit;
			} */
			$aSecurityQuestion[] = array('qu'=>$questionid,'ans'=>$answer);
			$status = $this->_accountSecurity->checkRightSecurityQuestion($aSecurityQuestion);
			if($status !== TRUE){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError($status)));
				exit;
			}
		} else {
			//宝开安全中心
			$code = getSecurityInput($post_data['code']);
			if(isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber'])){
				$checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
				if($checkStatus!==TRUE){
					$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
					echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
					exit;
				}
			}
		}
		$aRecorder = $this->_sessionlogin->fundWithDraw;
		//二次提交
		if(count($aRecorder)<=0){
			echo Zend_Json::encode(array('isSuccess'=>'-1'));
			exit;
		} else{
			unset($this->_sessionlogin->fundWithDraw);
		}
		
		$aBankCardInfo = array();
		//是否有选择银行
		if(empty($id)){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102046')));
			exit;
		}
		//金额验证
		if($changeCount<=0){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102060')));
			exit;
		}
		//提现次数验证
		if($aRecorder['withdrawStruc'][$this->_userType]['time'] !='-1' && $aRecorder['availWithdrawTime']<=0){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102020')));
			exit;
		}
		//提现金额验证
		if($changeCount < $aRecorder['withdrawStruc'][$this->_userType]['lowLimit']
		|| $changeCount > $aRecorder['withdrawStruc'][$this->_userType]['upLimit']
		|| $changeCount > $aRecorder['availBal']){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102019')));
			exit;
		}
		//是否有保存用户绑定银行卡信息
		if(count($aRecorder['userBankStruc'])>0){
			foreach ($aRecorder['userBankStruc'] as $value){
				if($value['id'] == $id){
					if($value['isAbleWithdraw'] =='-1'){
						$this->addErr("102139");
						$this->res_show(true,true);
					} else if($value['isAbleWithdraw'] =='-2'){
						$this->addErr("102145");
						$this->res_show(true,true);
					} else {
						$aBankCardInfo = $value;
					}
				}
			}
		}else {
			//调用接口 去除选定银行的 绑定信息
			//$aBankCardInfo = $this->getbindBankCardInfo($id);
			//$aBankCardInfo = $aBankCardInfo['userBankStruc'];
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102053')));
			exit;
		}
		$data = array(
			'param' => array(
				'id' => $aBankCardInfo['id'],
				'bankId' => $aBankCardInfo['bankId'],
				'userId' => $this->_sessionlogin->info['id'],
				'isVip' => $this->_sessionlogin->info['vipLvl'],
				'preWithdrawAmt' => $changeCount*$this->_moneyUnit,
				'applyTime' => getSendTime(),
				'userBankStruc' => $aBankCardInfo,
				'ipAddr' => bindec(decbin(ip2long(get_client_ip()))) //IP地址
			)
		);
		$aUri['fund'] = 'applyfundwithdraw';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
        $modatas = array();
        foreach ( $result["body"]["result"]["orders"] as $recorder){
            $modata["withdrawAmt"] = getMoneyFomat($recorder["withdrawAmt"]/$this->_moneyUnit,2) ;
            $modata["sn"] =$recorder["sn"] ? $recorder["sn"] : '';
            array_push($modatas,$modata) ;
        }
        $aResult = array(
				'changCount' => getMoneyFomat($changeCount,2),
				'bankNumber' =>$this->getSecurityBankCardNum($aBankCardInfo['bankNumber']),
				'bankAccount' =>$this->getSecurityBankCardAucount($aBankCardInfo['bankAccount']),
				'bankName' => $this->_bankIcoArray[$aBankCardInfo['bankId']]['name'],
				'availWithdrawTime' => number2chinese($aRecorder['availWithdrawTime']-1),
                'drawCount' => count($modatas)
		);
        $aData['title'] = '提现成功';
		$aData['result'] = $aResult;
        $aData['drawlist'] = $modatas;
        echo Zend_Json::encode(array('isSuccess'=>'1','msg'=>base64_encode(Zend_Json::encode($aData))));
		exit;
	}
	//展示成功页面
	public function displayresultAction(){
		$data = json_decode(base64_decode($this->getParam('data')),true);
        $this->view->result = isset($data['result']) ? $data['result'] : '';
		$drawlist = array();
		$drawlist =  $data['drawlist'];
		$this->view->drawlist = $drawlist;
		$this->view->title = "提现成功";
		$this->view->display ( 'default/ucenter/fund/draw_succ.tpl' );
	}
	//提现记录
	public function historyAction(){
		$this->res_add_url("提现记录","/withdraw/history");
		$this->res_add_url("提现记录","/withdraw/history",true);
		$aStatusArray = array('-1'=>'所有状态','处理中','提现成功','提现失败');
		$aJavaStatus  = array('处理中','处理中','处理中','提现失败','提现成功','提现失败','提现成功','提现失败');
		//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败
		$sn 	  = getSecurityInput($this->_request->getParam('sn'));
		$fromDate = getSecurityInput($this->_request->getParam('fromDate',date('Y-m-d',strtotime('-6 days'))));
		$toDate   = getSecurityInput($this->_request->getParam('toDate',date('Y-m-d')));
		$status   = getSecurityInput($this->_request->getParam('status','-1'));
		$page     = getSecurityInput($this->_request->getParam('page'));
		$_POST = $this->_request->getParams();
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$fromDate = date('Y-m-d',strtotime($fromDate));
				$_POST['fromDate'] = $fromDate;
				$fromDate = getQueryStartTime($fromDate);
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}
		if(!empty($fromDate)){
			if (strtotime($toDate)){
				$toDate = date('Y-m-d',strtotime($toDate));
				$_POST['toDate'] = $toDate;
				$toDate = getQueryEndTime($toDate);
			} else {
				$this->addErr('102039');
				$this->res_show(true,true);
			}
		}
		if($toDate < $fromDate){
			$this->addErr('102040');
			$this->res_show(true,true);
		}

		if(!empty($_POST['fromDate']) && !empty($_POST['toDate'])){
			if((strtotime($_POST['fromDate']) < strtotime(date('Y-m-d',strtotime('-6 days')))) || (strtotime($_POST['toDate']) > strtotime(date('Y-m-d')))){
				$this->addErr('102210');
				$this->res_show(true,true);
			}
		}
		
		switch ($status){
			case '-1':
				$this->_status = array(0,1,2,3,4,5,6,7);
				break;
			case '0':
				$this->_status = array(0,1,2);
				break;
			case '1':
				$this->_status = array(4,6);
				break;
			case '2':
				$this->_status = array(3,5,7);
				break;
			default:
				$this->_status = array(0,1,2,3,4,5,6,7);
				break;
		}
		
		if(!empty($page)){
			$this->_page = $page;
		}
		$account = $this->_sessionlogin->info['account'];
		$data =array(
			'param' => array(
					'sn' 	   =>$sn,
					'fromDate' => $fromDate,
					'toDate'   => $toDate,
					'account'  => $account,
					'userId'   => $this->_sessionlogin->info['id'],
					'statuses'   => $this->_status,
					'page'	   => $this->_page
			),
			'pager'=>array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize,
			)
		);
		$aUri['fund'] = 'queryfundwithdraworderbyuserId';//充值记录接口
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if($this->_clientobject->err_isExist()){
			$code = $this->_clientobject->err_get();
			$this->addErr($code[0][0]);
			$this->res_show(true,true);
		}
		$withdrawAmt ='0';
		$content = array();
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			foreach ($result['body']['result'] as $key=>$value){
				$content[$key]['sn']			  = $value['sn'];
				$content[$key]['withdrawAmt']     = $value['withdrawAmt'] ? getMoneyFomat($value['withdrawAmt']/$this->_moneyUnit,2) : '';
				$content[$key]['realWithDrawAmt'] = $value['realWithDrawAmt'] ? getMoneyFomat($value['realWithDrawAmt']/$this->_moneyUnit,2) : '';
				$content[$key]['applyTime'] 	  = $value['applyTime'] ? date('Y-m-d H:i:s',getSrtTimeByLong($value['applyTime'])) : '';
				$content[$key]['bankId'] 		  = isset($value['userBankStruc']['bankId']) ? $value['userBankStruc']['bankId'] : 1;
				$content[$key]['bankname'] 		  = $this->_bankIcoArray[$content[$key]['bankId']]['name'];
				$content[$key]['banknumber']	  = substr($value['userBankStruc']['bankNumber'], -4);
				$content[$key]['status'] 		  = $aJavaStatus[$value['status']];
                $content[$key]['rootSn'] 		  = $value['rootSn'];
                $content[$key]['seperateCount'] 	  = $value['seperateCount'];
                $content[$key]['totalDrawAmount'] 	  = $value['totalDrawAmount'] ? getMoneyFomat($value['totalDrawAmount']/$this->_moneyUnit,2) : '';
                $content[$key]['tipData']= '总提现金额：'.$content[$key]['totalDrawAmount'].'元<br>主订单号:'.$value['rootSn'].'<br>提现账户名:'.$this->getSecurityBankCardAucount($value['userBankStruc']['bankAccount']).'<br>提现银行:'.$content[$key]['bankname'].'<br>提现卡尾号：'.
                                                                                         $content[$key]['banknumber'].'<br>提现时间：'.$content[$key]['applyTime'] .'<br>拆分总单量：'.$content[$key]['seperateCount'].'笔';
        }      
			$this->_total = $result['body']['pager']['total'];//总记录数
			$withdrawAmt = !empty($result['body']['pager']['sum']) ? getMoneyFomat($result['body']['pager']['sum']/$this->_moneyUnit,2) : '0';
		}

		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		//获取账户余额
		$accInfo = $this->getuserfundinfo();
		$this->view->accInfo 	  = $accInfo;
		$this->view->pages   	  = $pages;
		$this->view->account      = $account;
		$this->view->withdrawAmt  = $withdrawAmt;
		$this->view->content 	  = $content;
		$this->view->total 		  = $this->_total;
		$this->view->status 	  = $status;
		$this->view->aStatusArray = $aStatusArray;
		$this->view->_POST 		  = $_POST;
		$this->view->title 		  = "提现记录";
		$this->view->display('default/ucenter/fund/withdrawdetail_list.tpl');
	}
			
	//提款進度查詢
	public function queryprocessAction(){
		$this->res_add_fail("信息验证失败");
		$this->res_add_url("提现记录","/widthdraw/queryprocess");
		$this->res_add_url("提现记录","/widthdraw/queryprocess",true);
		$aStatusArray = array('-1'=>'所有状态','处理中','提现成功','提现失败');
		$aJavaStatus  = array('处理中','处理中','处理中','提现失败','提现成功','提现失败','提现成功');
		//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败
		$sn 	  = getSecurityInput($this->_request->getParam('sn'));
		$fromDate = getSecurityInput($this->_request->getParam('fromDate',date('Y-m-d',strtotime('-6 days'))));
		$toDate   = getSecurityInput($this->_request->getParam('toDate',date('Y-m-d')));
		$status   = getSecurityInput($this->_request->getParam('status','-1'));
		$page     = getSecurityInput($this->_request->getParam('page'));
		$_POST = $this->_request->getParams();
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$fromDate = date('Y-m-d',strtotime($fromDate));
				$_POST['fromDate'] = $fromDate;
				$fromDate = getQueryStartTime($fromDate);
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}
		if(!empty($fromDate)){
			if (strtotime($toDate)){
				$toDate = date('Y-m-d',strtotime($toDate));
				$_POST['toDate'] = $toDate;
				$toDate = getQueryEndTime($toDate);
			} else {
				$this->addErr('102039');
				$this->res_show(true,true);
			}
		}
		if($toDate < $fromDate){
			$this->addErr('102040');
			$this->res_show(true,true);
		}
		switch ($status){
			case '-1':
				$this->_status = array(0,1,2,3,4,5,6);
				break;
			case '0':
				$this->_status = array(0,1,2);
				break;
			case '1':
				$this->_status = array(4,6);
				break;
			case '2':
				$this->_status = array(3,5);
				break;
			default:
				$this->_status = array(0,1,2,3,4,5,6,7);
				break;
		}
		
		if(!empty($page)){
			$this->_page = $page;
		}
		$account = $this->_sessionlogin->info['account'];
		$data =array(
			'param' => array(
					'sn' 	   =>$sn,
					'fromDate' => $fromDate,
					'fromAction' => 'queryprocess',					
					'toDate'   => $toDate,
					'account'  => $account,
					'userId'   => $this->_sessionlogin->info['id'],
					'statuses'   => $this->_status,
					'page'	   => $this->_page
			),
			'pager'=>array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize,
			)
		);
                
		$aUri['fund'] = 'queryfundwithdraworderbySn';// 只查一筆提現單接口
		$result = $this->_clientobject->inRequestV2($data, $aUri);         
        $data2 =array(
			'param' => array('withdrawSn'  =>$sn,'applyTime'    =>$result['body']['result'][0]['applyTime'],
			'withdrawTimeStr5'    =>$result['body']['result'][0]['withdrawTimeStr5'])		
		);
                                                                    
        $aUri2['fund'] = 'searchLog';//查詢提現記錄歷程
		$logs= $this->_clientobject->inRequestV2($data2, $aUri2);         
		$logcontent = $logs['body']['result']['logData'];
		if($this->_clientobject->err_isExist()){
			$code = $this->_clientobject->err_get();
			$this->addErr($code[0][0]);
			$this->res_show(true,true);
		}

		$withdrawAmt ='0';
		$content = array();
		
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			foreach ($result['body']['result'] as $key=>$value){
				$content[$key]['sn']			  = $value['sn'];
				$content[$key]['bankaccount']	  = $this->getSecurityBankCardAucount($value['userBankStruc']['bankAccount']);
				$content[$key]['withdrawAmt']     = $value['withdrawAmt'] ? getMoneyFomat($value['withdrawAmt']/$this->_moneyUnit,2) : '';
				$content[$key]['realWithDrawAmt'] = $value['realWithDrawAmt'] ? getMoneyFomat($value['realWithDrawAmt']/$this->_moneyUnit,2) : '';
				$content[$key]['bankId'] 		  = isset($value['userBankStruc']['bankId']) ? $value['userBankStruc']['bankId'] : 1;
				$content[$key]['bankname'] 		  = $this->_bankIcoArray[$content[$key]['bankId']]['name'];
				$content[$key]['banknumber']	  = $this->getSecurityBankCardNum($value['userBankStruc']['bankNumber']);
				$content[$key]['status'] 		  = $aJavaStatus[$value['status']];
			}
			$this->_total = $result['body']['pager']['total'];//总记录数
			$withdrawAmt = !empty($result['body']['pager']['sum']) ? getMoneyFomat($result['body']['pager']['sum']/$this->_moneyUnit,2) : '0';
		}                                    
       
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		//获取账户余额
		$accInfo = $this->getuserfundinfo();
		$this->view->accInfo 	  = $accInfo;
		$this->view->pages   	  = $pages;
		$this->view->account      = $account;
		$this->view->withdrawAmt  = $withdrawAmt;
		$this->view->content 	     = $content;
		$this->view->logcontent   = $logcontent;
		$this->view->total 		  = $this->_total;
		$this->view->aStatusArray = $aStatusArray;
		$this->view->_POST 		  = $_POST;
		//要用哪一個CSS
		switch ($result['body']['result'][0]['status']){
			//審核開始
			case '0':
				$this->view->usfulCSS = "\"status-line drawing-s2\"";
				break;
			//待定	
			case '1':
				$this->view->usfulCSS = "\"status-line drawing-s6\"";
				break;
			//審核通過
			case '2':
				$this->view->usfulCSS = "\"status-line drawing-s4\"";
				break;
			 //拒絕	
			case '3':
				$this->view->usfulCSS = "\"status-line drawing-s8\"";
				break;
			//提現成功	
			case '4':
				$this->view->usfulCSS = "\"status-line drawing-s5\"";
				break;
			//提現失敗
			case '5':
				if($result['body']['result'][0]['cancelTime'] !=null){
					$this->view->usfulCSS = "\"status-line drawing-s9\"";
				}else{
					$this->view->usfulCSS = "\"status-line drawing-s7\"";
				}
				break;
			//部分提現成功
			case '6':
				$this->view->usfulCSS = "\"status-line drawing-s5\"";
				break;
			//退款完成	
			case '7':
				if($result['body']['result'][0]['cancelTime'] !=null){
					$this->view->usfulCSS = "\"status-line drawing-s9\"";
				}else{
					$this->view->usfulCSS = "\"status-line drawing-s7\"";
				}
				break;		
			default:
				$this->view->usfulCSS = "\"status-line drawing-s0\"";
				break;
		}
		$this->view->withdrawTimeStr1  = $result['body']['result'][0]['withdrawTimeStr1'];
		$this->view->withdrawTimeStr2  = $result['body']['result'][0]['withdrawTimeStr2'];
		$this->view->withdrawTimeStr3  = $result['body']['result'][0]['withdrawTimeStr3'];
		$this->view->withdrawTimeStr4  = $result['body']['result'][0]['withdrawTimeStr4'];
		$this->view->withdrawTimeStr5  = $result['body']['result'][0]['withdrawTimeStr5'];
		
		$this->view->title= "提現进度查询";
		$this->view->display('default/ucenter/fund/drawing-progress-check.tpl');
	}
		
	//ajax验证安全密码
	public function checksecuritypwdAction(){
		$securitypwd = getSecurityInput($this->_request->getPost('securitypwd'));
		$status = $this->checkRightSecurityPassword($securitypwd);
		if($status !== TRUE){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($status)));
			exit;
		}
		echo Zend_Json::encode(array('status'=>'ok'));
		exit;
	}
}