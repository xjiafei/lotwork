<?php
require_once 'application/models/admin/User.php';
class Default_FundappealController extends Fundcommon{
	private $_pagesize,$_total,$_page,$_status,$_accountSecurity,$_aDepositeMode,$_aRechargeStatus;
	protected $user;
	public function init(){
		parent::init();
		$this->user = new User();
		$this->_pagesize = 15;
		$this->_total =0;
		$this->_page = 1;
		$this->_status = array(0,1,2,3,4,5,6);
		$this->_errView = 'ucenter';
		$this->_accountSecurity = new AccountsSecurity();
                $this->_aDepositeMode = array(
                    1 =>'网银汇款'
                    ,'快捷充值'
                    ,'财付通'
                	,'人工充值'
                	,'银联充值'
                	,'支付宝'
                	
                );
                $this->_aRechargeStatus = array(
                    1 => "支付中"
                    , "充值成功"
                    , "订单关闭"
                    , "用户关闭"
                    , "管理员关闭"
                );
        }
	
	

	public function indexAction(){
		
	}
	

			
	/*催到帳--提款申訴頁籤*/
	public function appealwithdrawlistAction(){
		
		/*初始化必要參數*/
		$page     = getSecurityInput($this->_request->getParam('page'));
		
		
		/*整理+檢查參數*/
		$_POST = $this->_request->getParams();
				
		if(!empty($page)){
			$this->_page = $page;
		}		
		
		$account = $this->_sessionlogin->info['account'];
		/*傳至JAVA的參數*/
		$data =array(
			'param' => array(
					'account'  => $account,
					'userId'   => $this->_sessionlogin->info['id'],
					'page'	   => $this->_page
			),
			'pager'=>array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize,
			)
		);
		
		$aUri['fund'] = 'queryAppealWithdrawList';
		$result = $this->_clientobject->inRequestV2($data, $aUri);	
        
        $content = array();		
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			foreach ($result['body']['result'] as $key=>$value){						
				$content[$key]['sn']	  = $value['sn'];
                $content[$key]['applyTime']	  = date('Y-m-d H:i:s',getSrtTimeByLong($value['applyTime']));
				$content[$key]['withdrawAmt'] = $value['withdrawAmt'] ? getMoneyFomat($value['withdrawAmt']/$this->_moneyUnit,2) : '';
				$content[$key]['bankId'] 	  = isset($value['bankId']) ? $value['bankId'] : 1;
				$content[$key]['bankName'] 	  = $this->_bankIcoArray[$content[$key]['bankId']]['name'];					
				$content[$key]['cardNumber']  = substr($value['cardNumber'], -4);
				$content[$key]['appealMemo']  = $value['appealMemo'];
				$content[$key]['appealTips']  = $value['appealTips'];	
                $content[$key]['isSeperate'] = $value['isSeperate'];
				if($value['appealTips']!=null){
					$content[$key]['appealMemoSubStr']  =mb_substr($value['appealTips'], 0,4,'utf-8'). '...';;   
				}
	            $seperateTipStr = '';
	            if($value['isSeperate'] == 'Y'){
	                $seperateTipStr='拆分总单量：'.count($value['subDraws']).' 笔<br>';
	                foreach($value['subDraws'] as $k=>$val){
                       $seperateTipStr = $seperateTipStr.$val['sn'].'&nbsp;&nbsp;'.getMoneyFomat($val['withdrawAmt']/$this->_moneyUnit,2).'<br>';
	                }
	                $seperateTipStr =$seperateTipStr.'提现账户名:'.$this->getSecurityBankCardAucount($val['userBankStruc']['bankAccount']);
	            }
				$content[$key]['appealStatus']  = $value['appealStatus'];
                $content[$key]['seperateTip'] = $seperateTipStr;

                                                                    }
			$this->_total = $result['body']['pager']['total'];//总记录数
		}
		
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		/*回傳給TPL資訊*/
		$this->view->content 	  = $content;
		$this->view->total 		  = $this->_total;	
		$this->view->title        = '催到帐';
		$this->view->_POST 		  = $_POST;
		$this->view->status       = $status;
		$this->view->pages   	  = $pages;
		
		
		$this->view->display('default/ucenter/fwappeal/appeal-withdraw-list.tpl');		
			
	}
	
	/*提款申訴進度查詢*/
	public function appealwithdrawstatusAction(){
		
		/*$result = $this->_service->queryNoticeTask();
		$content = array();
		$aModul = array('信息变动','资金变动','中奖提醒','注册','登录','密码提醒');
		if(count($result)>0){
			foreach ($result as $value){
				if(in_array($value['module'],$aModul)){
					$moduleKey = array_search($value['module'],$aModul);
					$value['emailTemp'] = $value['noteTemp'] = $value['smsTemp'] = $value['innerMsgTemp'] = '';
					$content[$moduleKey]['cnt'] = isset($content[$moduleKey]['cnt']) ? $content[$moduleKey]['cnt']+1 : 1;
					$content[$moduleKey][$content[$moduleKey]['cnt']] = $value;
				}
			}
		}*/
		//$this->_noticeRedis->set('userNoticeTask',$content);
		$this->view->title = '申诉进度查询';
		//$this->view->result = $content;
		$this->view->display('default/ucenter/fwappeal/appeal-withdrawstatus-list.tpl');
		
	}
	
	/*自助申訴Loading頁面*/
	public function appealselfAction(){
		/*初始化必要參數*/
		$sn 	  = getSecurityInput($this->_request->getParam('sn'));
		/*傳至JAVA的參數*/
		$data =array(
			'param' => array(
					'sn' => $sn,
					'account'  => $this->_sessionlogin->info['account'],
					'userId'   => $this->_sessionlogin->info['id'],
			),
		);
	
		$aUri['fund'] = 'queryWithdrawAppealByRootSn';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
                
		/*回傳給TPL的值*/
		$this->view->applyAccount = $result['body']['result']['applyAccount'];
		$this->view->withdrawSn = $result['body']['result']['rootSn'];
		$this->view->applyTime  = date('Y-m-d H:i:s',getSrtTimeByLong($result['body']['result']['applyTime']));
                                             $this->view->withdrawAmt  = $result['body']['result']['withdrawAmt']? getMoneyFomat($result['body']['result']['withdrawAmt']/$this->_moneyUnit,2) : '';
                                             $this->view->bankName =$this->_bankIcoArray[$result['body']['result']['userBankStruc']['bankId']]['name'];	
		//$this->view->bankName =$result['body']['result']['userBankStruc']['bankName'];
		$this->view->cardNumber =$this->getSecurityBankCardNum($result['body']['result']['userBankStruc']['bankNumber']);	
		$this->view->bankAccount =$this->getSecurityBankCardAucount($result['body']['result']['userBankStruc']['bankAccount']);
		
		/*選擇使用頁面*/
		$this->view->display('default/ucenter/fwappeal/drawing-appeal.tpl');			
	}
	
	
	
	/*自助申訴提交按鈕action*/
	public function appealselfstartAction(){
		
		/*初始化必要參數*/
		$sn 	  = getSecurityInput($this->_request->getParam('sn'));		
		$page     = getSecurityInput($this->_request->getParam('page'));
            $uploadImages = $this->_uploadsession->pics;
		/*整理+檢查參數*/
		$_POST = $this->_request->getParams();
				
		if(!empty($page)){
			$this->_page = $page;
		}		
		
	
		$account = $this->_sessionlogin->info['account'];
		/*傳至JAVA的參數*/
		$data =array(
			'param' => array(
                    			'account' => $account,
					'sn' => $sn,
			                    'page' => $this->_page,
			                    'uploadImages' => Zend_Json::encode($uploadImages)
			),
			'pager'=>array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize,
			)
		);
	
		$aUri['fund'] = 'updateWithdrawAppealbySn';
		$result = $this->_clientobject->inRequestV2($data, $aUri);	
		
		$recorder = $result["body"]["result"][0];
		
		
		
		echo Zend_Json::encode(array('isUpdateState'=>$recorder["isUpdateState"]));
		exit;
		
		
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

    
    public function appealstatuslistAction() {
        $fromDate = getSecurityInput($this->_request->getParam('fromDate', date('Y-m-d', strtotime('-6 days'))));
        $toDate = getSecurityInput($this->_request->getParam('toDate', date('Y-m-d')));
        $status = getSecurityInput($this->_request->getParam('status', ''));
        $page = getSecurityInput($this->_request->getParam('page', '1'));
        $pageSize = getSecurityInput($this->_request->getParam('pageSize', '10'));
        $userId = $this->_sessionlogin->info['id'];
        $noticeAppealSns = $this->getAppealNoticeSns($userId);
        $data = array(
            'param' => array(
                'startDate' => $fromDate,
                'endDate' => $toDate,
                'status' => $status
            ),
            'pager' => array(
                'startNo' => ($page - 1) * $pageSize + 1,
                'endNo' => $page * $pageSize
            )
        );

        $aUri['fund'] = 'queryFundAppealStatus';
        $result = $this->_clientobject->inRequestV2($data, $aUri);
        $datas = $result['body']['result'];
        $total = $result['body']['pager']['total'];
        $content = array();
        foreach ($datas as $key => $value) {
            $item = array();
            $item['userId'] = $value['userId'];
            $item['account'] = $value['account'];
            $item['appealSn'] = $value['appealSn'];
            $item['type'] = $value['type'];
            if ($value['appealTime'] != null) {
                $item['appealTime'] = date('Y-m-d H:i:s', getSrtTimeByLong($value['appealTime']));
            }
            $item['argueTime'] = date('Y-m-d H:i:s', getSrtTimeByLong($value['argueTime']));
            $item['appealCreator'] = $value['appealCreator'];
            $item['memo'] = $value['memo'];
            if ($value['memo'] != null) {
                $item['memoStr'] = mb_substr($value['memo'], 0, 4,'utf-8') . '...';
            }
            $item['chargeMemo'] = $value['chargeMemo'];
            $item['status'] = $value['status'];
            $item['fundSn'] = $value['fundSn'];
            $item['fundAmt'] = getMoneyFomat($value['fundAmt'] / $this->_moneyUnit, 2);
            $item['depositeMode'] = $this->_aDepositeMode[$value['depositeMode']];
            $item['bankName'] = $value['bankName'];
            if($value['fundTime']!=null){
                $item['fundTime'] = date('Y-m-d H:i:s', getSrtTimeByLong($value['fundTime']));
            }
            if($value['fundCard']!=null){
                $item['fundCard'] = preg_replace('/\s/','',$value['fundCard']);
                $item['fundCard'] = preg_replace('/(\d)(?=.{4})/','*',$item['fundCard']);
                $item['fundCard'] = preg_replace('/([*]{4})/','\1 ',$item['fundCard']);
            }
            if($value['fundCardUser']!=null){
                $item['fundCardUser'] =$this->getSecurityBankCardAucount($value['fundCardUser']);
            }
            if($noticeAppealSns!=null){
                $item['showNewStatusPoint'] = in_array($item['appealSn'], $noticeAppealSns);
            }
            $item['tenpayAccount'] = $value['tenpayAccount'];
            if($value['tenpayName']!=null){
                $item['tenpayName'] = $this->getSecurityBankCardAucount($value['tenpayName']);
            }
            $item['isSeperate'] = $value['isSeperate'];
            $seperateTipStr = '';
            if($value['isSeperate'] == 'Y'){
                $seperateTipStr='拆分总单量：'.count($value['subDraws']).' 笔<br>';
                foreach($value['subDraws'] as $k=>$val){
                    $seperateTipStr = $seperateTipStr.$val['sn'].'&nbsp;&nbsp;'.getMoneyFomat($val['withdrawAmt']/$this->_moneyUnit,2).'<br>';
                }
                $seperateTipStr =$seperateTipStr.'提现账户名:'.$this->getSecurityBankCardAucount($val['userBankStruc']['bankAccount']);
            }
            $item['seperateTip'] = $seperateTipStr;
            $content[$key] = $item;
        }
        $pages = CommonPages::getPages($total, $page, $pageSize);
        $this->view->assign('fromDate', $fromDate);
        $this->view->assign('toDate', $toDate);
        $this->view->assign('status', $status);
        $this->view->assign('pages', $pages);
        $this->view->assign('content', $content);
        $this->view->display('default/ucenter/fwappeal/appeal-status-list.tpl');
    }
    
    private function getAppealNoticeSns($userId){
        $redisKey = 'FUND_APPEAL_STATUS_'.$userId;
        $noticeAppealSns = $this->redis_client->sMembers($redisKey);
        if($noticeAppealSns!=null){
            $this->redis_client->delete($redisKey);
        }
        return $noticeAppealSns;
    }

    public function appealrechargelistAction() {
        $page = getSecurityInput($this->_request->getParam('page', '1'));
        $pageSize = getSecurityInput($this->_request->getParam('pageSize', '10'));
        
        $appealConfig = $this->getAppealConfig();
        $waitTime = $appealConfig['waitTime'];

        $data = array(
            'pager' => array(
                'startNo' => ($page - 1) * $pageSize + 1,
                'endNo' => $page * $pageSize
            )
        );
        $aUri['fund'] = 'queryRechargeList';
        $result = $this->_clientobject->inRequestV2($data, $aUri);
        $datas = $result['body']['result'];
        $total = $result['body']['pager']['total'];
        $content = array();
        foreach ($datas as $key => $value) {
            $item = array();
            $item['sn'] = $value['chargeSn'];
            $item['rechargeAccount'] = $value['account'];
            $item['rechargeType'] = $value['depositeMode'];
            $item['rechargeTypeStr'] = $this->_aDepositeMode[$value['depositeMode']];
            $item['rechargeBank'] = $value['bankName'];
            $item['rechargeBankId'] = $value['bankId'];
            $item['rechargeCard'] = $value['bankCardNumber'];
            $item['rechargeMemo'] = $value['chargeMemo'];
            $item['rechargeAmt'] = getMoneyFomat($value['chargeAmt'] / $this->_moneyUnit, 2);
            $item['rechargeTime'] = date('Y-m-d H:i:s', getSrtTimeByLong($value['chargeTime']));
            $item['statusStr'] = $this->_aRechargeStatus[$value['chargeStatus']];
            $item['status'] = $value['chargeStatus'];
            $item['hasBeenAppeal'] = $value['hasBeenAppeal'];
            $item['canRechargeAppeal'] = $value['canRechargeAppeal'];
            $item['otherCanRechargeAppeal'] = $value['otherCanRechargeAppeal'];
            $item['depositeMode'] = $value['depositeMode'];
            $now = strtotime(date('Y-m-d H:i:s'));
            $rechargeTime = strtotime($item['rechargeTime']);
            if(($now - $rechargeTime)/60<=$waitTime){
                $item['isWaiting'] = true;
            }else{
                $item['isWaiting'] = false;
            }
            $content[$key] = $item;
        }
        $pages = CommonPages::getPages($total, $page, $pageSize);
        $this->view->assign('waitTime', $waitTime);
        $this->view->assign('pages', $pages);
        $this->view->assign('content', $content);
        $this->view->display('default/ucenter/fwappeal/appeal-recharge-list.tpl');
    }

    public function rechargeappealAction() {
        $hasSn = getSecurityInput($this->_request->getParam('hasSn', false));
        $sn = getSecurityInput($this->_request->getParam('sn', ''));
        $account = getSecurityInput($this->_request->getParam('account', ''));
        $amount = getSecurityInput($this->_request->getParam('amount', ''));
        $type = getSecurityInput($this->_request->getParam('type', '1'));
        $bank = getSecurityInput($this->_request->getParam('bank', ''));
        $card = getSecurityInput($this->_request->getParam('card', ''));
        
        $appealConfig = $this->getAppealConfig();
        $cdTime = $appealConfig['cdTime'];
        $cdSec = $this->getRechargeAppealCdSec($cdTime);
        
        $allBanks = $this->getBankList();
        $banks = $allBanks['banks'];
        $fastBanks = $allBanks['fastBanks'];
        $tenpayBank = $allBanks['tenpayBank'];
        $unionpayBank = $allBanks['unionpayBank'];
        $alipayBank = $allBanks['alipayBank'];

        $this->view->assign('cdSec', $cdSec);
        $this->view->assign('banks', $banks);
        $this->view->assign('fastBanks', $fastBanks);
        $this->view->assign('tenpayBank', $tenpayBank);
        $this->view->assign('unionpayBank', $unionpayBank);
        $this->view->assign('alipayBank', $alipayBank);
        $this->view->assign('hasSn', $hasSn);
        $this->view->assign('sn', $sn);
        $this->view->assign('account', $account);
        $this->view->assign('amount', $amount);
        $this->view->assign('type', $type);
        $this->view->assign('bank', $bank);
        $this->view->assign('card', $card);
        $this->view->display('default/ucenter/fwappeal/recharge-appeal.tpl');
    }
    
    private function getAppealConfig(){
        $result = $this->getFrontConfigValueByKey('fund','recharge_appeal_time','getconfigvaluebykey');
        $val = Zend_Json::decode( $result['val']);
        $appealConfig = array();
        $appealConfig['waitTime'] = $val['wait_time'];
        $appealConfig['cdTime'] = $val['cd_time'];
        return $appealConfig;
    }

    private function getBankList() {
        $allBanks = $this->getBankCardInfo("bankStruc");
        $banks = array();
        $fastBanks = array();
        $tenpayBank = null;
        $unionpayBank = null;
        foreach ($allBanks as $key => $value) {
            $id = $value['id'];
            $helpIds = split(',', $value['helpIds']);
            $deposit = $value['deposit'];
            $canRechargAppeal = $value['canRechargeAppeal'];
            $otherCanRechargeAppeal = $value['otherCanRechargeAppeal'];
            $other = $value['other'];
            if ($id < 30) {
                if ($canRechargAppeal == 1) {
                    $value['helpId'] = $helpIds[0];
                    $value['helpId2'] = $helpIds[2];//網銀充值 指令序號演練 目前只有工行有
                    array_push($banks, $value);
                }
                if ($otherCanRechargeAppeal == 1) {
                    $value['helpId'] = $helpIds[1];
                    array_push($fastBanks, $value);
                }
            } else if ($id == 30 && $canRechargAppeal == 1) {
                $value['helpId'] = $helpIds[0];
                $value['helpId2'] = $helpIds[1];
                $alipayBank = $value;
            } else if ($id == 31 && $canRechargAppeal == 1) {
                $value['helpId'] = $helpIds[0];
                $value['helpId2'] = $helpIds[1];
                $tenpayBank = $value;
            }else if ($id == 51 && $canRechargAppeal == 1) {
            	$value['helpId'] = $helpIds[0];
            	$unionpayBank = $value;
            }
        }
        $result = array();
        $result['banks'] = $banks;
        $result['fastBanks'] = $fastBanks;
        $result['tenpayBank'] = $tenpayBank;
        $result['unionpayBank'] = $unionpayBank;
        $result['alipayBank'] = $alipayBank;
        return $result;
    }
    
    private function getRechargeAppealCdSec($cdTime){
        $now = timetoMicro(strtotime(date('Y-m-d H:i:s')));
        $data = array(
            'param' => array(
                'startDate' => $now,
                'endDate' => $now
            ),
            'pager' => array(
                'startNo' => 1,
                'endNo' => 100
            )
        );
        $aUri['fund'] = 'queryFundAppealStatus';
        $result = $this->_clientobject->inRequestV2($data, $aUri);
        $datas = $result['body']['result'];
        $cdSec = 0;
        foreach ($datas as $key => $value) {
            $type = $value['type'];
            if ($type == 'RECHARGE') {
                $time = timetoMicro(getSrtTimeByLong($value['argueTime']));
                $diffTime = ($now -$time)/1000;
                $cdTimeSec = $cdTime*60;
                if($cdTimeSec>$diffTime){
                    $cdSec= $cdTimeSec-$diffTime;
                    break;
                }
            }
        }
        return $cdSec;
    }

    public function rechargeappealsubmitAction() {
        $sn = getSecurityInput($this->_request->getParam('sn', ''));
        $account = getSecurityInput($this->_request->getParam('account', ''));
        $amount = getSecurityInput($this->_request->getParam('amount', ''));
        $type = getSecurityInput($this->_request->getParam('type', '1'));
        $bank = getSecurityInput($this->_request->getParam('bank', ''));
        $rechargeTime = getSecurityInput($this->_request->getParam('rechargeTime', ''));
        $rechargeMemo = getSecurityInput($this->_request->getParam('rechargeMemo', ''));
        $electronicNumber = getSecurityInput($this->_request->getParam('electronicNumber', ''));
        $rechargeName = getSecurityInput($this->_request->getParam('rechargeName', ''));
        $rechargeCard = trim(getSecurityInput($this->_request->getParam('rechargeCard', '')));
        $tenpayAccount = trim(getSecurityInput($this->_request->getParam('tenpayAccount', '')));
        $tenpayName = getSecurityInput($this->_request->getParam('tenpayName', ''));
        $transactionNum = str_replace( " ","",trim(getSecurityInput($this->_request->getParam('transactionNum', ''))));    
        $uploadImages = $this->_uploadsession->pics;


        $data = array(
            'param' => array(
                'userId' => $this->_sessionlogin->info['id'],
                'chargeSn' => $sn,
                'userAccount' => $account,
                'chargeAmt' => $amount * $this->_moneyUnit,
                'depositeMode' => $type,
                'bankId' => $bank,
                'chargeTime' => timetoMicro(strtotime($rechargeTime)),
                'chargeMemo' => $rechargeMemo,
                'electronicNumber' => $electronicNumber,
                'chargeUserName' => $rechargeName,
                'bankCardNumber' => $rechargeCard,
                'tenpayAccount' => $tenpayAccount,
                'tenpayName' => $tenpayName,
                'uploadImages' => isset($uploadImages)?Zend_Json::encode($uploadImages):'',
            	'transactionNum' => $transactionNum
            	
            )
        );
        $aUri['fund'] = 'appealRecharge';
        $result = $this->_clientobject->inRequestV2($data, $aUri);
        $status = null;
        if($result["head"]["status"]==0){
            $status = 'success';
        }else{
            $status = 'fail';
        }
        $this->view->assign('status', $status);
        $this->view->display('default/ucenter/fwappeal/recharge-appeal.tpl');
    }

    public function uploadAction() {
        $adapter = new Zend_File_Transfer_Adapter_Http();
        $pics = array();
        $upload_dir = $this->_config->upload_dir . '/upload/images/';
        $logger = Zend_Registry::get('logger');
        if (!is_dir($upload_dir)) {
            $logger->log('upload_dir not exists:' . $upload_dir, 1);
            echo Zend_Json::encode(array('error' => '1', 'data' => '上传失败,请联系客服!!!'));
            exit;
        }
        $format = array('jpg', 'png', 'gif');
        $adapter->addValidator('Extension', false, implode(",", $format));
        $adapter->addValidator('Size', false, 1024 * 2 * 1024);
        $adapter->addValidator('Count', false, array('min' => 1, 'max' => 3));
        $adapter->addValidator('Size', false, array(
            'min' => '1kB',
            'max' => '2MB'
        ));
        $adapter->setDestination($upload_dir); //存放上传文件的文件夹
        $totalUpload = 0;
        $aError = array();
        $errorArray = array(
            'fileExtensionFalse' => '只能上传JPG,PNG,GIF类型的图片',
            'fileSizeTooBig' => '文件大小不能超过2MB',
            'fileCountTooFew' => '文件大小不能超过2MB',
            'fileUploadErrorIniSize' => '文件大小不能超过2MB',
        );
        $filesInfo = $adapter->getFileInfo();
        if ($adapter->isValid()) {
            try {
                foreach ($filesInfo as $key => $fileInfo) {
                    $fname = $fileInfo['name'];
                    if ($adapter->isUploaded($fname) && $adapter->isValid($fname)) {
                        $oldFileName =$fname;
                        $extName = substr($fname, strrpos($fname, '.') + 1);
                        $filename = md5(microtime() . rand(1000, 9999)) . '.' . $extName; //重命名   此处的uniqid 有可能会出现性能问题.
                        $adapter->addFilter('Rename', array(
                            'source' => $fileInfo['tmp_name'],
                            'target' => $filename,
                            'overwrite' => true)
                        ); //执行重命名
                        if ($adapter->receive($fname)) {
                            $pic = array();
                            $pic['url']= $filename;
                            $pic['name']= $oldFileName;
                            array_push($pics, $pic);
                            $totalUpload++;
                        }
                    } else {
                        $message = $adapter->getErrors();
                        $aError[$key] = isset($message[0]) ? $errorArray[$message[0]] : '';
                    }
                }
            } catch (Zend_File_Transfer_Exception $e) {
                $aError[$key] = $e->getMessage();
            }
            if ($adapter->hasErrors()) {
                $logger->log('upload error1:' . var_export($aError, TRUE), 1);
                echo Zend_Json::encode(array('error' => '1', 'data' => $aError));
                exit;
            } else {
                if ($totalUpload > 0) {
                    $this->_uploadsession->pics = $pics;
                }
                $logger->log('upload error1:' . var_export($pics, TRUE), 1);
                echo Zend_Json::encode(array('status' => '1', 'data' => $totalUpload));
                exit;
            }
        } else {
            $error_msg = '上传失败。';
            $logger->log('upload view error:' . var_export($adapter->getMessages(), TRUE), 1);
            $error = $adapter->getMessage();
            if (!empty($error)) {
                foreach ($error as $k => $er) {
                    if (array_key_exists($k, $errorArray)) {
                        $msg[] = $errorArray[$k];
                    }
                }
                $error_msg = sprintf('上传失败，%s', implode(" ", array_unique($msg)));
            }
            echo Zend_Json::encode(array('error' => '1', 'data' => $error_msg));
            exit;
        }
    }
        
    public function getappealnoticecountAction(){
        $userId = $this->_sessionlogin->info['id'];
        $noticeAppealSns = $this->redis_client->sMembers('FUND_APPEAL_STATUS_'.$userId);
        if($noticeAppealSns!=null){
            echo count($noticeAppealSns);
        }else{
            echo 0;
        }
        exit;
    }
    
    public function userdetailAction() {
    	$userId = $this->_sessionlogin->info['id'];

    	$userdetail = $this->user->gettopproxydetail($userid);
    	
    	$userStruc  = $userdetail['body']['result']['userStruc'];
    	
    	echo Zend_Json::encode(array('appealNewFunc'=>$userStruc['appealNewFunc']));
        exit;
    	
    }
    
    //保存用户详细信息数据
    public function saveuserdetailAction()
    {
    	$userId = $this->_sessionlogin->info['id'];
    	$data = array(
    			'body'=>array(
    					'param'=>array(
    							'id'			=>$userId,
    							'appealNewFunc'=>$this->_request->getParam('appealNewFunc', 0)
    							
    					)
    			)
    	);
    	if(isset($userId)){
    		$resultdata = $this->user->savepersonalinfo($data);
    		if(isset($resultdata['head']['status'])&& $resultdata['head']['status'] =='0'){
    			
    			echo Zend_Json::encode(array('isSuccess'=>1));
    			exit;
    		} else {
    			echo Zend_Json::encode(array('isSuccess'=>0));
    			exit;
    		}
    	}
    
    }

}