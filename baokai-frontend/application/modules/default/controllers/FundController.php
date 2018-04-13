<?php
class Default_FundController extends Fundcommon {

	private $_pagesize,$_total,$_page,$_status,$_remarkModel;
    public function init() {
        parent::init ();
        $this->_pagesize = 15;
        $this->_total =0;
        $this->_page = 1;
        $this->_status = array(1,2,3,4,5);
        $this->_errView = 'ucenter';
        $this->_remarkModel = new Remark();
        
    }
    
    //充值中心
    public function indexAction(){
    	$data = array(
    		'returnUrl'=> '/fund',
    		'returnTitle'=>'我要充值',
    		'title'=>'您还没有设置安全密码，请设置后再进行充值。',
    		'content' => '为了保障您的账户资金安全，请在充值之前设置您的安全验证信息。'
    	);
        $this->res_add_url("我要充值","/fund");
        $this->res_add_url("我要充值","/fund",true);
        
        $stp  = intval($this->_request->getParam("stp"));
        $type = $this->_request->getParam("type");
       if(($type==null))
       {
            $type = 1;
       }       
        $premount = floatval(getSecurityInput($this->_request->get('premount')));
        if($type =='1' && $premount>0){
        	$this->view->premount = $premount;
        }
        $aRecorders = $this->getBankCardInfo('bankStruc'); //获取银行卡信息和用户绑卡信息
        $aBankCardList = $this->getBankStrucByType($type+1,$aRecorders);
        //银行列表
        if(count($aBankCardList)<=0){
        	$this->log('Java返回银行列表为空!');
        	$this->addErr("102154");
        	$this->res_show(true,true);
        	
        } 
        $this->_sessionlogin->fundcard = $aBankCardList;
        //删除跳转信息
        if(isset($this->_sessionlogin->info ["securityReturnTitle"])){
        	unset($this->_sessionlogin->info ["securityReturnTitle"]);
        	unset($this->_sessionlogin->info ["securityReturnUrl"]);
        }
        
        //是否可充值
        $enableRecharge = in_array($this->_sessionlogin->info['freezeMethod'], array(1,2,5)) ? 0 : 1;
        //是否设置 唯一附言 
        $result = $this->_remarkModel->getUserRemark($this->_sessionlogin->info['id']);
      
        //---------------获取倒计时配置----------------------------------------
        switch ($type){
        	case '0' :
        		$functionStr = 'chargeCountDown';//網銀
        		break;
        	case '1' :
        		$functionStr = 'chargeCouteThired';//快捷
        		break;
        	case '2' :
        		$functionStr = 'chargeCouteCaifu';//財付通
        		break;
        	case '3' :
        		$functionStr = 'chargeCouteUnionpay';//銀聯
        		break;
        	case '4' :  //支付寶
        		
        		$lastCharge = $this->getchargeRecordQuery($data);
        		$res = $this->getconfigvaluebykey('fund', 'aliPayChargeCoute','getconfigvaluebychargeCoute');
        		$aliCount = $res['val'];
        		$userBankStruc = $this->getuserBankCard(true,$aliCount);
        		$lastChargeCard = $lastCharge['chargeCardNum'];
        		$lastChargeAccount = $lastCharge['bankAccount'];
				foreach($aRecorders as $value){
					if($value["code"] == 30 && $value["version"] ==1 && $value["deposit"] == 1){ //支付寶企業版
						$functionStr = 'chargeCouteAlipayBi';
					}if($value["code"] == 30 && $value["version"] ==0 && $value["deposit"] == 1){ //支付寶個人版
						$functionStr = 'chargeCouteAlipay';
					}
				}
        		break;
        	case '5' :
        		$functionStr = 'chargeCouteWechat';//微信支付
        		break;
			case '6' :
        		$functionStr = 'chargeCouteQQ';//QQ錢包
        		break;
        	default:
        		$functionStr = 'chargeCouteThired';
        		break;
        }
       
        
        
        $chargeData["param"]["module"]="fund";
        $chargeData["param"]["function"]=$functionStr;
        $cdata =$this->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$limitMoneyJson = $this->getconfigvaluebykey('fund', 'thirdChargeLimit');
		$limitMoney = json_decode($limitMoneyJson["val"],true);
        //---------------获取倒计时配置----------------------------------------
        $this->view->iCountDown = $cdata["body"]["result"]["val"];
        
        $this->view->enableRecharge = $enableRecharge;
        $this->view->aBankCardList 	= $aBankCardList;
        $this->view->stp 	= $stp;
        $this->view->type 	= $type;
        $this->view->vipLvl = $this->_userType;
        $this->view->title 	= "我要充值";
        $this->view->isSetRemark = isset($result['remark'])&&!empty($result['remark']) ? 1 : 1;//屏蔽附言 ? 1 : 0
        $this->view->bankInfo = $aRecorders;
        $this->view->userBankStruc = $userBankStruc;
        //$this->view->userBankStrucCount = count($userBankStruc);
        $this->view->lastChargeCard =$lastChargeCard;
        $this->view->lastChargeAccount = $lastChargeAccount;
		$this->view->thirdChargeLimit = $limitMoney["limitMoney"];
		$this->view->isOldUser = $this->_isOldUser ;
        //是否可以操作支付寶
        $paramData = array(
        		'userId'=> $this->_sessionlogin->info['id'],
        		'id'=> 30);
        $isAilOpen = $this->checkChargeOpen($paramData);
        $this->view->isAilOpen = $isAilOpen;

		 //是否可以操作QQ錢包
        $paramData = array(
        		'userId'=> $this->_sessionlogin->info['id'],
        		'id'=> 35);
        $isQQOpen = $this->checkChargeOpen($paramData);
        $this->view->isQQOpen = $isQQOpen;
        if($type == '0'){
			$this->log('网银已關閉');
			$this->addErr("102154");
			$this->res_show(true,true);
			$this->view->display ( 'default/ucenter/fund/recharge_netbank_index.tpl');
        } else if($type == '1'){
        	$this->view->display ( 'default/ucenter/fund/recharge_quick_index.tpl');
        } else if($type == '2') {
        	$this->view->display ( 'default/ucenter/fund/recharge_thirdbank_index.tpl');
        } else if($type == '3') {
        	$this->view->display ( 'default/ucenter/fund/recharge_unionpaybank_index.tpl');
        } else if($type == '4') {
        	//判斷是否可使用支付寶
        	if($isAilOpen!='Y'){
        		$this->log('支付寶已關閉');
        		$this->addErr("102154");
        		$this->res_show(true,true);
        	}
        	$this->view->display ( 'default/ucenter/fund/recharge_alipaybank_index.tpl');
        } else if($type == '5') {
        	//微信支付
        	$this->view->display ( 'default/ucenter/fund/recharge_wechat_index.tpl');
		} else if($type == '6') {
        	//判斷是否可使用QQ錢包
        	if($isQQOpen!='Y'){
        		$this->log('QQ錢包已關閉');
        		$this->addErr("102154");
        		$this->res_show(true,true);
        	}
        	//QQ錢包
        	$this->view->display ( 'default/ucenter/fund/recharge_qq_index.tpl');
        } else {
			$this->view->display ( 'default/ucenter/fund/recharge_quick_index.tpl');
		}
    }
    
	// 确定充值
	public function confirmAction() {
        $chargeamount = abs(floatval(number_format(str_replace(',', '', getSecurityInput($this->_request->getPost('chargeamount','0'))),2,'.',''))); // 充值金额
		$status = getSecurityInput($this->_request->getPost ( 'status' )); // 银行
		$type = intval(getSecurityInput($this->_request->getPost ( 'type' ))); //充值方式
		$lastChargeCard = getSecurityInput($this->_request->getPost ( 'lastChargeCard ' )); //支付寶帳號
		if($type==4){
			$bankNumber =  $this->_request->getPost ('bankNumber'); //支付寶帳號
			$bankAccount = $this->_request->getPost ('bankAccount'); //支付寶帳號
			$nickName = $this->_request->getPost ('nickName'); //支付寶帳號
			//充值同時新增卡號
			
			$chargeVersion = $this->_request->getPost ('chargeVersion');  //充值版本: 0個人版 1企業版
			if($chargeVersion ==0 ){
				$this->addkAliPayBankCard($bankNumber,$bankAccount);
			}
		}
		
		$erorUrl = "/fund/index?type=".$type;
		if (empty($chargeamount) && empty($status)) {
			$this->_redirect($erorUrl);
		}
		$aFundInfo = $this->_sessionlogin->fundcard;
	
		//防止重复提交充值请求
		if(empty($this->_sessionlogin->fundcard)){
			$this->_redirect($erorUrl);
		} else{
			unset($this->_sessionlogin->fundcard);
		}
		$aBindCardArray  = array ();
		$this->res_add_fail("","","我要充值");
		$this->res_add_url("我要充值",$erorUrl);
		$this->res_add_url("我要充值",$erorUrl,true);
		if(count($aFundInfo)>0){
			foreach ( $aFundInfo as $value ) {
				if (strtoupper($value ['logo']) == strtoupper($status)) {
					$aBindCardArray = $value;
				}
			}
		}
		
		if(empty($aBindCardArray)){
			$this->addErr("102053");
			$this->res_show(true,true);
		}
		
		
		if($this->_userType == 'vip') {
			$lowLimit = $aBindCardArray['vipLowLimit'];
			$upLimit = $aBindCardArray['vipUpLimit'];
		} else {
			$lowLimit = $aBindCardArray['lowLimit'];
			$upLimit = $aBindCardArray['upLimit'];
		}
		if($chargeamount < $lowLimit || $chargeamount > $upLimit){
			$this->addErr("102022");
			$this->res_show(true,true);
		}
		//是否可充值
		if(in_array($this->_sessionlogin->info['freezeMethod'], array(1,2,5))){
			$this->addErr("102125");
			$this->res_show(true,true);
		}
		//快捷充值 銀聯 不检测 重复充值
		if($type !=1 && $type !=3 && $type !=5 && $type !=6){
		
			//检测是否有未完成的充值申请
			//type    0 網銀  1快捷  2財付通  3銀聯  4支付寶  4支付寶企業版	  5微信  6QQ錢包
			//deposit 1       2      3        5      6        8				   7         9
			if($type == 4 && $chargeVersion == 1){
				$depositForHaveChargeItem=8;
			}else{
				$depositForHaveChargeItem = intval($type) > 2 ?intval($type)+2:intval($type)+1;
			}			
			$chargeItem= $this->haveChargeItem($depositForHaveChargeItem);
			if(count($chargeItem)>0){
				$this->addErr("102119");
				$this->res_show(true,true);
			}
		}
		//因目前depoist_mode 1 網銀 2快捷 3 財富通 4人工充值 5 銀聯 6 支付寶 7 微信 8支付寶企業版 9QQ錢包，中間卡到 4人工充值
		if($type >= 6){
			$depositMode = intval($type)+3;
		} else{
			$depositMode = intval($type) > 2 ?intval($type)+2:intval($type)+1;
		}
		//如果是支付寶6判斷是否為企業版，如果是改成支付寶8
		if($depositMode==6){
			if($chargeVersion==1){
				$depositMode=8;
			}			
		}
		
		
		$aCardInfo = array (
					'param' => array(
					'bankId'   		=> intval($aBindCardArray ['code']), // 银行ID
					'userId'   		=> $this->_sessionlogin->info ['id'],
					'depositMode'   => $depositMode,
					'mcBankId' 		=> $aBindCardArray ['mownecumId'], // Mowecum银行id
					'userAct'  		=> $this->_sessionlogin->info ['account'],
					'preChargeAmt' 	=> $chargeamount*$this->_moneyUnit,
					'applyTime' 	=> getSendTime(),
                    'platfom'       => '3',  // 申請平台
                    'ver'           => '4.0',
					'url' 			=> !empty($aBindCardArray ['url']) ? $aBindCardArray ['url'] : '',
					'bankNumber'	=>	$bankNumber,
					'bankAccount'	=>	$bankAccount,
					'nickName'		=>	$nickName, 
                    'customerIp'    =>  get_client_ip (),
					'chargeVersion' =>  $chargeVersion
				) 
		);

		$aUri = array('fund'=>'chargeapply'); //发送充值请求
		$aRecorders = $this->postReuqest ( $aCardInfo, $aUri, 'FundChargeApply' ); //,'application/testdata/FundChargeApply.php' );
		//$aRecorders = $this->postReuqest ( $aCardInfo, $aUri, 'FundChargeApply' ,'application/testdata/FundChargeApply.php');
		if(count($aRecorders)<=0 || !isset($aRecorders['mode'])){
			$this->log('没有获取到充值请求响应的信息');
		}
        
		$this->view->mode 		= isset($aRecorders ['mode']) ? $aRecorders ['mode'] : '';
		$this->view->revAccName = isset($aRecorders ['revAccName']) ? $aRecorders ['revAccName'] : '';
		$this->view->rcvBank 	= isset($aRecorders ['bankId']) && isset($this->_bankIcoArray[$aRecorders ['bankId']]) ? $this->_bankIcoArray[$aRecorders ['bankId']]['name'] : '';
		$this->view->rcvEmail 	= isset($aRecorders ['rcvEmail']) ? $aRecorders ['rcvEmail'] : '';
		$this->view->rcvAccNum 	= isset($aRecorders ['rcvAccNum']) ? $aRecorders ['rcvAccNum'] : '';
		$this->view->rcvBankName= isset($aRecorders ['rcvBankName']) ? $aRecorders ['rcvBankName'] : ''; //开户银行名称
		$this->view->chargeMemo = isset($aRecorders ['chargeMemo']) ? $aRecorders ['chargeMemo'] : '';
		$this->view->nowTime 	= date('Y/m/d H:i:s');
		$this->view->expireTime = isset($aRecorders ['expireTime']) ? date('Y/m/d H:i:s',strtotime('+'.$aRecorders ['expireTime'].' mins')) : '';
		$this->view->longTime 	= isset($aRecorders ['expireTime']) ? $aRecorders ['expireTime'] : '';
		$this->view->bankName 	= $aBindCardArray ['name'];
		$this->view->status 	= $status;
		//type  0 網銀  1快捷  2財付通  3銀聯 4支付寶 5微信 6QQ錢包
		$this->view->url 		= $type=='1' || $type=='3' || $type=='4'|| $type=='5'|| $type=='6'? (isset($aRecorders['breakUrl']) ? $aRecorders['breakUrl'] : '') : $aBindCardArray ['url'];
		$this->view->rtnMin 	= $aBindCardArray['rtnMin']/$this->_moneyUnit;
		$this->view->chargeamount = getMoneyFomat($chargeamount,2);
		$this->view->title = "充值确认";
		$this->view->bankInfo = $this->getBankCardInfo('bankStruc'); //获取银行卡信息和用户绑卡信息
		$this->view->logo 		= $status;
		$this->view->bankAccount 	= ($bankAccount);
		$this->view->nickName 		= $nickName;
		$this->view->bankNumber 	= ($bankNumber);
		$this->view->chargeVersion     =  $chargeVersion;
		$this->view->isOldUser = $this->_isOldUser ;
		$this->view->type = $type;
		//是否可以操作支付寶
		$paramData = array(
				'userId'=> $this->_sessionlogin->info['id'],
				'id'=> 30);
		$isAilOpen = $this->checkChargeOpen($paramData);
		$this->view->isAilOpen = $isAilOpen;
		$paramData = array(
				'userId'=> $this->_sessionlogin->info['id'],
				'id'=> 35);
		$isQQOpen = $this->checkChargeOpen($paramData);
		$this->view->isQQOpen = $isQQOpen;
        //通匯充值相關參數
        if(isset($aRecorders ['thPay']) && $aRecorders ['thPay'] != null ){
        	$this->view->isTHpay =1;
            $this->view->thpayAmount = getMoneyFomat($aRecorders ['thPay']['orderAmount'],2);
            $this->view->thPay= $aRecorders ['thPay'];
        }else{
            $this->view->isTHpay = 0;
        }
		//匯博充值相關參數
        if(isset($aRecorders ['hbPay']) && $aRecorders ['hbPay'] != null ){
        	$this->view->isHBpay =1;
            $this->view->hbPay= $aRecorders ['hbPay'];
        }else{
            $this->view->isHBpay = 0;
        }
        //華勢充值相關參數
        if(isset($aRecorders ['worthPay']) && $aRecorders ['worthPay'] != null ){
        	$this->view->isWORTHpay =1;
            $this->view->worthPay= $aRecorders ['worthPay'];
        }else{
            $this->view->isWORTHpay = 0;
        }
		//秒付充值相關參數
        if(isset($aRecorders ['spPay']) && $aRecorders ['spPay'] != null ){
        	$this->view->isSPpay =1;
            $this->view->spPay= $aRecorders ['spPay'];
        }else{
            $this->view->isSPpay = 0;
        }
		//多得宝充值相關參數
        if(isset($aRecorders ['ddbPay']) && $aRecorders ['ddbPay'] != null ){
        	$this->view->isDDBpay =1;
            $this->view->ddbPay= $aRecorders ['ddbPay'];
        }else{
            $this->view->isDDBpay = 0;
        }
		//网富通充值相關參數
        if(isset($aRecorders ['wftPay']) && $aRecorders ['wftPay'] != null ){
        	$this->view->isWFTpay =1;
            $this->view->wftPay= $aRecorders ['wftPay'];
        }else{
            $this->view->isWFTpay = 0;
        }
		//智付充值相關參數
        if(isset($aRecorders ['dinPay']) && $aRecorders ['dinPay'] != null ){
        	$this->view->isDINpay =1;
            $this->view->dinPay= $aRecorders ['dinPay'];
        }else{
            $this->view->isDINpay = 0;
        }
		//华银充值相關參數
        if(isset($aRecorders ['huayinPay']) && $aRecorders ['huayinPay'] != null ){
        	$this->view->isHUAYINpay =1;
            $this->view->huayinPay= $aRecorders ['huayinPay'];
        }else{
            $this->view->isHUAYINpay = 0;
        }
		//银邦充值相關參數
        if(isset($aRecorders ['yinbangPay']) && $aRecorders ['yinbangPay'] != null ){
        	$this->view->isYINBANGpay =1;
            $this->view->yinbangPay= $aRecorders ['yinbangPay'];
        }else{
            $this->view->isYINBANGpay = 0;
        }
		//金阳充值相關參數
        if(isset($aRecorders ['jinyangPay']) && $aRecorders ['jinyangPay'] != null ){
        	$this->view->isJINYANGpay =1;
            $this->view->jinyangPay= $aRecorders ['jinyangPay'];
        }else{
            $this->view->isJINYANGpay = 0;
        }
   
        if($type == '1'){
			$this->view->display ( 'default/ucenter/fund/recharge_quick_confirm.tpl' );
		} else if($type == '2') {
			$this->view->display ( 'default/ucenter/fund/recharge_thirdbank_confirm.tpl' );
		} else if($type == '3') {
			$this->view->display ( 'default/ucenter/fund/recharge_unionpaybank_confirm.tpl' );
		} else if($type == '4') {
			$this->view->display ( 'default/ucenter/fund/recharge_alipaybank_confirm.tpl' );
		} else if($type == '5') {
			$this->view->display ( 'default/ucenter/fund/recharge_wechat_confirm.tpl' );
		} else if($type == '6') {
			$this->view->display ( 'default/ucenter/fund/recharge_qq_confirm.tpl' );
		} else {
			$this->view->display ( 'default/ucenter/fund/recharge_netbank_comfirm.tpl' );
		}
	}
	
	//充值记录
	public function historyAction(){
		//$this->res_add_fail("信息验证失败");
		$this->res_add_url("充值记录","/fund/history");
		$this->res_add_url("充值记录","/fund/history",true);
		$aStatusArray = array('所有状态','支付中','支付成功','订单关闭');
		$aStatusData = array('所有状态','支付中','支付成功','订单关闭','订单关闭','订单关闭');
		$aTypeArray = array('全部','网银汇款','快捷充值','财付通充值','网银汇款','银联充值','支付宝充值','微信充值','QQ钱包充值');
		$sn 		= getSecurityInput($this->_request->getParam('sn',''));
		$fromDate 	= getSecurityInput($this->_request->getParam('fromDate',date('Y-m-d',strtotime('-6 days'))));
		$toDate 	= getSecurityInput($this->_request->getParam('toDate',date('Y-m-d')));
		$status 	= intval(getSecurityInput($this->_request->getParam('status','')));
		$type 		= intval(getSecurityInput($this->_request->getParam('type','')));
		$this->_page= intval(getSecurityInput($this->_request->getParam('page',1)));
		$_POST = $this->_request->getParams();
		//流水号
		if(!empty($sn)){
			$param['sn'] = $sn;
		}
		//充值方式
		if($type>0 && $type<9){
			$aType = array(array(1,2,3,4),array(1,4),array(2),array(3),array(4),array(5),array(6,8),array(7),array(9));
			$param['depositeMode'] = $aType[$type];
		}
		//开始时间
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$fromDate = date('Y-m-d',strtotime($fromDate));
				$param['fromDate'] = getQueryStartTime($fromDate);
				$_POST['fromDate'] = $fromDate;
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}
		//结束时间
		if(!empty($fromDate)){
			if (strtotime($toDate)){
				$toDate = date('Y-m-d',strtotime($toDate));
				$param['toDate'] = getQueryEndTime($toDate);
				$_POST['toDate'] = $toDate;
			} else {
				$this->addErr('102039');
				$this->res_show(true,true);
			}
		}
		if($toDate < $fromDate){
			$this->addErr('102040');
			$this->res_show(true,true);
		}

		if(!empty($fromDate) && !empty($toDate)){
			if((strtotime($fromDate) < strtotime(date('Y-m-d',strtotime('-6 days')))) || (strtotime($toDate) > strtotime(date('Y-m-d')))){
				$this->addErr('102210');
				$this->res_show(true,true);
			}
		}
		
		if($status == 3){
			$param['status'] = array(3,4,5);
		} else if(in_array($status, $this->_status)){
			$param['status'] = array($status);
		}/*  else {
			$param['status'] = $this->_status;
		} */
		$param['userId'] = $this->_sessionlogin->info['id'];
		$data =array(
			'param' => $param,
			'pager'=>array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize,
			)
		);
		$aUri['fund'] = 'chargequery';//充值记录接口
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if($this->_clientobject->err_isExist()){
			$code = $this->_clientobject->err_get_v2();
			$this->addErr($code[0][0]);
			$this->res_show(true,true);
		}
		$applyAmt ='0';
		$content = array();
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			foreach ($result['body']['result'] as $key=>$value){
				$value['realChargeAmount'] = !empty($value['realChargeAmount']) ? getMoneyFomat($value['realChargeAmount']/$this->_moneyUnit,2) : '';
				$value['applyAmt']   = getMoneyFomat($value['applyAmt']/$this->_moneyUnit,2);
				$value['chargeAmt']  = !empty($value['chargeAmt']) ? getMoneyFomat($value['chargeAmt']/$this->_moneyUnit,2) : '';
				$value['applyTime']  = date('Y-m-d H:i:s',getSrtTimeByLong($value['applyTime']));
				$value['chargeTime'] = date('Y-m-d H:i:s',getSrtTimeByLong($value['chargeTime']));
				$value['bankname']   = isset($value['payBankId']) ? $this->_bankIcoArray[$value['payBankId']]['name'] : '';
				$value['mcChannel']   = !empty($value['mcChannel']) ? $value['mcChannel'] : '';
				$value['status']     = $aStatusData[$value['status']];
				$value['depositMode']= isset($value['depositMode']) && $value['depositMode']!=3 && $value['depositMode']!=5 && $value['depositMode']!=6 && $value['depositMode']!=8? $aTypeArray[$value['depositMode']] : '';
				$value['payBankId']   = isset($value['payBankId']) ?$value['payBankId']:'';
				$content[$key]		 = $value;
			}
			$this->_total = $result['body']['pager']['total'];//总记录数
			$applyAmt = !empty($result['body']['pager']['sum']) ? getMoneyFomat($result['body']['pager']['sum']/$this->_moneyUnit,2) : '0';
		}
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		//获取账户余额
		$accInfo = $this->getuserfundinfo();
		$this->view->accInfo = $accInfo;
		$this->view->pages = $pages;
		$this->view->account = $this->_sessionlogin->info['account'];
		$this->view->isSubmited = 1;
		$this->view->applyAmt = $applyAmt;
		$this->view->content = $content;
		$this->view->total = $this->_total;
		$this->view->aStatusArray = $aStatusArray;
		$this->view->aTypeArray = $aTypeArray;
		$this->view->_POST = $_POST;
		$this->view->title = "充值记录";
		$this->view->display('default/ucenter/fund/rechargedetail_list.tpl');
	}

	//检测是否有未完成的充值订单
	public function checkchargeitemAction(){
		$type = intval($this->_request->getParam('type'));
		$chargeItem= $this->haveChargeItem(intval($type) > 2 ?intval($type)+2:intval($type)+1);
		//超时 充值不成功 跳转超时页面
		if(count($chargeItem)>0){
			$this->view->title = "充值超时";
			$this->view->display('default/ucenter/fund/recharge_timeout.tpl');
		} else {
			$this->_redirect('/fund/history/');
		}
	}
	
	//检测是否 重复充值 重复充值则返回 充值表单项
	public function checkchargesuccessAction(){
		$type = intval($this->_request->get('type'));
		$chargeItem= $this->haveChargeItem(intval($type));
		if(count($chargeItem)>0){
			echo Zend_Json::encode(array('isSuccess'=>1,'type'=>'','msg'=>$this->getError('102119'),'data'=>$chargeItem));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102120')));
			exit;
		}
	}
	
	//取消充值订单
	public function cancleorderAction(){
		
		$id = getSecurityInput($this->_request->get('id'));
		$data=array(
			'param'=>array(
				'sn'=>$id
			)
		);
		
		$uri['fund'] = 'chargeCancel';
		$result = $this->_clientobject->inRequestV2($data, $uri);
		
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
			echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'撤销充值申请成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'撤销充值申请失败!'));
			exit;
		}
	}
	
	//获取未完成充值的 表单项
	public function haveChargeItem($type=1){
		$data=array(
			'param'=>array(
				'userId'=>$this->_sessionlogin->info['id'],
				'depositMode'=>$type,
			)
		);
		/* if(isset($this->_sessionlogin->fundcard)){
			$bankList = $this->_sessionlogin->fundcard;
			foreach($bankList as $value){
				$aUrl[$value['id']]=$value['url'];
			}
		} */
		$uri['fund'] = 'haveChargeItem';
		$result = $this->_clientobject->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status']=='0' && !empty($result['body']['result'])){
			foreach ($result['body']['result'] as $key=>$value){
				$result['body']['result'][$key] = $value != null ? $value : '';
				if($key == 'chargeAmt'){
					$result['body']['result'][$key] = getMoneyFomat(floatval($value)/$this->_moneyUnit,2);
				}
				if($key == 'rcvMail'){
					if(empty($value)){
						$result['body']['result'][$key] = isset($result['body']['result']['bankCardNo']) ? $result['body']['result']['bankCardNo'] : '';
						$result['body']['result']['rcvName'] = '收款账号';
					} else {
						$result['body']['result']['rcvName'] = '收款Email地址';
					}
				}
				if($key == 'bankAccount'){
					$result['body']['result']['bankAccount'] =($result['body']['result']['bankAccount'] );
				}
				if($key == 'bankNumber'){
					$result['body']['result']['bankNumber'] =($result['body']['result']['bankNumber']);
					
				}
			}
			return $result['body']['result'];
		} 
		return array();
	}

	//获取相应充值方式额 银行卡信息
	public function getBankStrucByType($type = 1,$aRecorders){
		
		$aBankCardList = array();
		//银行列表
		if(count($aRecorders)>0){
			foreach ($aRecorders as $recoder){
				if($recoder['inUse']!=0){ //
					if($recoder['deposit'] !=0 && $type == 1 && $recoder['code']<30){
						$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
						$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
						$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
						$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
        			} else if($recoder['other']!=0 && $type ==2 && $recoder['code']<30) {
		        		$recoder['upLimit'] = floatval($recoder['otheruplimit'])/$this->_moneyUnit;
        				$recoder['lowLimit'] 	= floatval($recoder['otherdownlimit'])/$this->_moneyUnit;
        				$recoder['vipUpLimit'] 	= floatval($recoder['othervipuplimit'])/$this->_moneyUnit;
        				$recoder['vipLowLimit'] = floatval($recoder['othervipdownlimit'])/$this->_moneyUnit;
        			} else if($recoder['deposit'] !=0 && $type ==3 && $recoder['code']==31) {
		        			$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
		        			$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
		        			$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
		        			$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
					} else if($recoder['deposit'] !=0 && $type ==4 && $recoder['code']==51) {
		        			$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
		        			$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
		        			$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
		        			$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
					}else if($recoder['deposit'] !=0 && $type ==5 && $recoder['code']==30) {
		        			$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
		        			$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
		        			$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
		        			$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
					}else if($recoder['deposit'] !=0 && $type ==6 && $recoder['code']==40) {
		        			$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
		        			$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
		        			$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
		        			$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
					}else if($recoder['deposit'] !=0 && $type ==7 && $recoder['code']==35) {
		        			$recoder['upLimit'] 	= floatval($recoder['upLimit'])/$this->_moneyUnit;
		        			$recoder['lowLimit'] 	= floatval($recoder['lowLimit'])/$this->_moneyUnit;
		        			$recoder['vipUpLimit'] 	= floatval($recoder['vipUpLimit'])/$this->_moneyUnit;
		        			$recoder['vipLowLimit'] = floatval($recoder['vipLowLimit'])/$this->_moneyUnit;
					}else {
		        		continue;
					}
					array_push($aBankCardList, $recoder);
				}
			}
		}
		return $aBankCardList;
	}

	//获取配置项
	public function getconfigCountDown($jdata = null,$surl='configvaluebykey'){
	
		$recorders = array();
		$turl = array("fundadmin"=>$surl);
		$rsr = $this->_clientobject->inRequestV2($jdata, $turl, $isArray = true) ;
		if(array_key_exists("body", $rsr)){
			return $rsr ;
		}else{
			throw new FireFogException ( '返回的body为null');
		}
	
	}
	
	//查最後一次充值完成記錄
	public function getchargeRecordQuery(){
		
		$data = array (
				'param' => array (
						'userId' => $this->_sessionlogin->info ['id'] ,
						'payBankId' => '30',
						'depositeMode' => array('6')
				)
		); // 设置 传输UserId
			
		$aUri = array('fundadmin'=>'chargeRecordQuery');
		$aRecorders = $this->_clientobject->inRequestV2($data, $aUri,'fundadmin');

		if(isset($aRecorders) && count($aRecorders["body"]['result'])>0){
			$lastCharge['chargeCardNum'] = ($aRecorders["body"]['result'][0]['chargeCardNum']);
			$lastCharge['bankAccount'] = $aRecorders["body"]['result'][0]['cardAccount'];
		}
		
		return $lastCharge;
	}
	
	public function getuserBankCard($isShowAliPay,$aliCount){
		$userBankStruc = array();
		$aData = array (
				'param' => array (
						'bindCardType' => 1
				)
		);
		$bindCardResult = $this->getbindBankCardInfo($aData);
		$count = 0;
		if(isset($bindCardResult['userBankStruc']) && count($bindCardResult['userBankStruc'])>0){
			foreach ($bindCardResult['userBankStruc'] as $key=>$value){
				$count++;
				$userBankStruc[$key]['id'] 	= $value['id'];
				$userBankStruc[$key]['deBankNumber'] 	= $value['bankNumber'];
				$userBankStruc[$key]['bankNumber'] 	= ($value['bankNumber']);
				$userBankStruc[$key]['deBankAccount'] 	= $value['bankAccount'];
				$userBankStruc[$key]['bankAccount'] 	=($value['bankAccount']);
				$userBankStruc[$key]['nickName'] 	= $value['nickName'];
				if($isShowAliPay){
					//支付寶顯示使用,須根據後台設定決定頁面可挑選參數
					if($count==$aliCount){
						break;						
					}
				}
			}
		}
		
		return $userBankStruc;
	}
	
	
	//ajax
	public function checkfirstalipayAction(){
		$bankNumber = getSecurityInput($this->_request->getPost('bankNumber'));
		$bankAccount = getSecurityInput($this->_request->getPost('bankAccount'));
		$nickName = getSecurityInput($this->_request->getPost('nickName'));
		$aData = array (
				'param' => array (
						'bindCardType' => 1,
						'bankNumber' => $bankNumber,
						'bankAccount' => $bankAccount,
						'nickName' => $nickName,
						'nickNameMust' => 1
				)
		);
		
		$bindCardResult = $this->getbindBankCardInfo($aData);
		if(isset($bindCardResult['userBankStruc']) && count($bindCardResult['userBankStruc'])>0){
			echo Zend_Json::encode(array('status'=>'ok'));
			exit;
		}else{
			echo Zend_Json::encode(array('status'=>'error'));
			exit;
		}

		
	}
	
	public function addkAliPayBankCard($bankNumber,$bankAccount){
		//可綁定數量
		$res = $this->getconfigvaluebykey('fund', 'aliPayChargeCoute','getconfigvaluebychargeCoute');
		$icardcount = !empty($res['val']) ? $res['val'] : '0';
		//user 已綁定數量
		$userBankStruc = $this->getuserBankCard(false,0);
		
		if(count($userBankStruc) < $icardcount){
		
			$param['bankNumber'] = $bankNumber;
			$param['bankAccount'] = $bankAccount;
			$param['userId'] 	 = $this->_sessionlogin->info['id'];
				
			if(!$this->checkBankCardBind($param)){
				$paramArray['userId'] = $this->_sessionlogin->info['id'];
				$paramArray['bankId'] = '30';
				$paramArray['mcBankId'] = '30';
				$paramArray['bankAccount'] = $bankAccount;
				$paramArray['bankNumber'] = $bankNumber;
				//$paramArray['nickName'] = $nickName;
				$paramArray['bindcardType'] = '1';
				$paramArray['opeType'] = 'insert';
			
				$aUri['fund'] = 'applybankcardbind';
				$result = $this->_clientobject->inRequestV2(array('param'=>$paramArray), $aUri);
			}
		}
		
	}
	
	
	//检测银行卡是否绑定
	public function checkBankCardBind($param){
		$aUri = array('fund'=>'checkbankcardbind');
		$data = array(
				'param'=> $param
		);
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($res['head']['status']) && $res['head']['status'] == '0') {
			return FALSE;
		} else {
			return TRUE;
		}
	}
	
	//是否可充值支付寶
	public function checkChargeOpen($param){
		$aUri = array('fund'=>'checkChargeOpen');
		$data = array(
				'param'=> $param
		);
		$rsr = $this->_clientobject->inRequestV2($data, $aUri, $isArray = true) ;
		return $rsr;
	}
	//微信的單日限額檢驗
	public function checkdaylimitAction(){
		$chargeamount = (int)getSecurityInput($this->_request->get('chargeamount'));
		$bankId = getSecurityInput($this->_request->get('bankid'));
		$userId = $this->_sessionlogin->info ['id'];
		$param['preChargeAmt'] = $chargeamount;
		$param['userId'] = $userId;
		$param['bankId'] = $bankId;
		$aUri = array('fund'=>'checkdaylimit');
		$data = array(
				'param'=> $param
		);
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		echo Zend_Json::encode(array('chargeLimit'=>$res['body']['result']));
		exit;
	}
	//第三方支付限制
	public function chargethirdpartylimitAction(){
		$chargeamount = getSecurityInput($this->_request->getPost('chargeamount'));
			
		$userId = $this->_sessionlogin->info ['id'];
		$param['preChargeAmt'] = $chargeamount*10000;
		$param['userId'] = $userId;
		$aUri = array('fund'=>'chargeThirdPartyLimit');
	
		$data = array(
				'param'=> $param
		);
		
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		
		echo Zend_Json::encode(array('chargeLimit'=>$res['body']['result']));
		exit;
	}
	
	//支付寶單日限額檢驗
	public function checkbankdaylimitAction(){
		$depositMode = getSecurityInput($this->_request->getPost('depositMode'));

		$param['depositMode'] = $depositMode;
		$aUri = array('fund'=>'checkBankDayLimit');
	
		$data = array(
				'param'=> $param
		);
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		
		echo Zend_Json::encode(array('dayLimit'=>$res['body']['result']));
		exit;
	}
	
	

}