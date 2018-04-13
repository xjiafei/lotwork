<?php
class Default_BindcardController extends Fundcommon {
    
    public function init() {
    	parent::init();
                      
   	$this->_errView = 'ucenter';
    }
	
	//显示添加银行卡绑定信息界面
	public function indexAction(){
		$data = array(
				//'unSetQues'=>1, // 1 跳过安全问题
				'returnUrl'=> '/bindcard/',
				'returnTitle'=>'银行卡管理',
				'title'=>'您还没有设置安全验证信息，请设置后再进行银行卡管理。',
				'content' => '为了保障您的账户资金安全，请在绑定银行卡之前设置您的安全验证信息。'
		);
		
		$type = intval($this->_request->getParam("type"));
		
		//检测是否设置安全密码
		$this->checkSecurityPassword($data);
		$bindCnt = $lock = $addlock = 0;
		//获取用户可以绑定的银行卡数目
		if($type == '1'){
			$res = $this->getconfigvaluebykey('fund', 'aliPayChargeCoute','getconfigvaluebychargeCoute');
		}else{
			$res = $this->getconfigvaluebykey('fund', 'chargeCoute','getconfigvaluebychargeCoute');
		}
		$icardcount = !empty($res['val']) ? $res['val'] : '4';
		$userBankStruc = array();
		$aData = array (
				'param' => array (
						'bindCardType' => $type
				)
		); // 设置 传输UserId
		
		$result = $this->getbindBankCardInfo($aData);
		if(isset($result['userBankStruc']) && count($result['userBankStruc'])>0){
			foreach ($result['userBankStruc'] as $key=>$value){
				$userBankStruc[$key]['id'] 			= $value['id'];
				$userBankStruc[$key]['bankId'] 		= $value['bankId'];
				$userBankStruc[$key]['mcBankId'] 	= $value['mcBankId'];
				//如果 后台用户设置的银行ID是错误的 合理默认是用工商银行的logo
				$userBankStruc[$key]['logo'] 		= isset($this->_bankIcoArray[$value['bankId']]['logo']) ? $this->_bankIcoArray[$value['bankId']]['logo'] : $this->_bankIcoArray[1]['logo'];
				
				if($value['bindcardType']==1){
					$userBankStruc[$key]['bankAccount'] = $this->getSecurityBankCardAccountLastName($value['bankAccount']);
					$userBankStruc[$key]['bankNumber'] 	= $this->getSecurityAliPayBankCardNum($value['bankNumber']);
					$userBankStruc[$key]['deBankAccount'] = ($value['bankAccount']);
					$userBankStruc[$key]['deBankNumber'] = ($value['bankNumber']);
				}else{
					$userBankStruc[$key]['bankAccount'] = $this->getSecurityBankCardAucount($value['bankAccount']);
					$userBankStruc[$key]['bankNumber'] 	= $this->getSecurityBankCardNum($value['bankNumber']);
				}
// 				$userBankStruc[$key]['branchAddr'] 	= $value['branchAddr'];
				$userBankStruc[$key]['bindDate'] 	= date('Y-m-d H:i:s',getSrtTimeByLong($value['bindDate']));
				$userBankStruc[$key]['nickName'] 	= $value['nickName'];
				$bindCnt++;
			}
			if($icardcount == '-1'){
				$addlock = 0;
			} else if($icardcount <= $bindCnt){
				$addlock = 1;
			} else {
				$addlock = 0;
			}
                                                                    if(intval($result['overTime']) - intval($result['dbNowTime']) <= 0 && $result['overTime']!=0){
                                                                                           $lock = 1;
				$addlock = 1;
			}
			$this->view->userbankstruc = $userBankStruc;
		}
		//產生公私鑰並儲存私鑰
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$res_rsa = $this->encrypt_RSA(md5('bindcard' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		
		$this->view->icardcount = $icardcount;
		$this->view->bindCnt = $bindCnt;
                                             $this->view->lock = $lock;
		$this->view->addlock = $addlock;
                                             $this->view->overTime = $result['overTime'];
                                             $this->view->dbNowTime = $result['dbNowTime'];
		
		if($type == '1'){
			$this->view->display('default/ucenter/fund/bindcard_alipay_list.tpl');
		} else {
			$this->view->display('default/ucenter/fund/bindcard_list.tpl');
		}
		
		
	}
	
	//检测银行卡是否锁定 跳转添加银行卡界面
	public function bindcardsecurityinfoAction(){
		//检测银行卡是否锁定
		//$this->res_add_fail("银行卡已经锁定");
		$this->res_add_url("银行卡管理","/bindcard/");
		$this->res_add_url("银行卡管理","/bindcard/",true);
		$status = $this->checkBankCardLocked();
		if($status){
			$this->addErr($status);
			$this->res_show(true,true);
		}
		$this->_sessionlogin->info['bindcardsecurityinfo'] = 1; //验证通过
		$result = $this->getBankCardInfo('bankStruc');
		$bankList = array();
		foreach ($result as $value){
			if($value['withdraw'] == '1' && $value['id']<30){
				$bankList[] = $value;
			}
		}
		//產生公私鑰並儲存私鑰
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$res_rsa = $this->encrypt_RSA(md5('bindcard' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		
		$this->view->banklist = $bankList;
		$this->view->display('default/ucenter/fund/bindcard_add.tpl');
	}
	//跳转添加支付寳界面
	public function bindcardalipayAction(){
		$bindcardType = intval($this->_request->getParam("bindcardType"));
		//检测银行卡是否锁定
		$this->res_add_url("银行卡管理","/bindcard/");
		$this->res_add_url("银行卡管理","/bindcard/",true);
		
		$status = $this->checkBankCardLocked($bindcardType);
		if($status){
			$this->addErr($status);
			$this->res_show(true,true);
		}
		
		$id = $this->_request->getPost('id');
		
		$bankid = $this->_request->getPost("bankid");
		$mcbankid= $this->_request->getPost("mcbankid");
		$bankAccount= $this->_request->getPost('bankAccount');
		$nickName= $this->_request->getPost("nickName");
		$bankNumber= $this->_request->getPost("bankNumber");
		$opeType= $this->_request->getPost("opeType");
		
		
		$this->_sessionlogin->info['bindcardsecurityinfo'] = 1; //验证通过
		
		//產生公私鑰並儲存私鑰
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$res_rsa = $this->encrypt_RSA(md5('bindcard' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		$this->view->id = $id;
		$this->view->bankid = $bankid;
		$this->view->mcbankid = $mcbankid;
		$this->view->bankAccount = $bankAccount;
		$this->view->nickName = $nickName;
		$this->view->bankNumber = $bankNumber;
		$this->view->opeType = $opeType;
		
		$this->view->display('default/ucenter/fund/bindcard_alipay_add.tpl');
	}
	
    public function bindcardlockAction(){
                                             $data=array();
		$data["param"]["userId"] = $this->_sessionlogin->info['id'];
		$aUri['fund'] = 'bankCardNowLock';
		
		//立即綁定五秒只能執行一次
		if($this->redis_client->get('bindcard_lock_'.$data ["param"] ["userId"])=='1'){
			exit;
		}else{
			$this->redis_client->set('bindcard_lock_'.$data ["param"] ["userId"],'1',30);
		}
		
		$res = $this->_clientobject->inRequestV2($data, $aUri);
                
        if(isset($res['head']['status'])&& $res['head']['status'] =='1'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$type=='1'?'锁定成功':'解锁成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$type=='1'?'锁定失败':'解锁失败'));
			exit;
		}
	}
	
	//直接添加银行卡绑定信息
	public function bindcardaddAction(){
        if(isset($this->_sessionlogin->info['bindcardsecurityinfo']) && $this->_sessionlogin->info['bindcardsecurityinfo'] =='1'){
			//unset($this->_sessionlogin->info['bindcardsecurityinfo']);
		} else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102025')));
			exit;
		}
		//寫一個 Lock key (第一個會成功True 在極短時間內第二個會失敗False)
		if($this->redis_client->setnx(md5('addBankCardLock' . $user_id), 3))
		{
			//設定存活時間
			$this->redis_client->expire(md5('addBankCardLock' . $user_id), 3);
		}
		else
		{
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102024')));
			exit;
		}
		
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('bindcard' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('2016')));
			exit;
		}
		parse_str($rsa_result, $post_data);
		//print_r($post_data);
		//exit;
		
		$paramArray = $post_data;
		//$paramArray = $this->_request->getPost();
		foreach ($paramArray as $key=>$value){
			
			if($key == securityPassword){
				$value =base64_decode(str_pad(strtr($value, '-_', '+/'), strlen($value) % 4, '=', STR_PAD_RIGHT));
			}
			
			if(!in_array($key, array('controller','action','module','securityPassword','bindcard/bindcardadd'))){
				$value = getSecurityInput($value);
				if ($value =='') {
					echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102026')));
					exit;
				}
				$paramArray[$key] = $value;
			}
		}
                // 银行卡开户名
		$bankAccount = $paramArray['bankAccount'];
		$bankAccountLength = getStrLen($paramArray['bankAccount']);
		
		$this->log('------------------bankAccount:'.$bankAccount);
		$this->log('------------------bankAccountLength:'.$bankAccountLength);
		
		if (!preg_match("/^[x{2460}-x{2468}]+$/u",$bankAccount)) {
		$this->log('------------------我是中文');
			if($bankAccountLength < 4 || $bankAccountLength > 30){
							// 银行卡开户名应为4-20位银字符
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102153')));
				exit;
			}
		}else{
			$this->log('------------------我是英文');
			if($bankAccountLength < 4 || $bankAccountLength > 20){
							// 银行卡开户名应为4-20位银字符
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102153')));
				exit;
			}
		}
		
                // 银行支行名称
		$branchAddrLength  = getStrLen($paramArray['branchAddr']);
		if($branchAddrLength<4 || $branchAddrLength > 30){
                        // 银行支行名称应为4-30位字符
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102142')));
			exit;
		}
		// 安全密码
		$securityPassword = base64_decode(str_pad(strtr(getSecurityInput($paramArray['securityPassword']), '-_', '+/'), strlen(getSecurityInput($paramArray['securityPassword'])) % 4, '=', STR_PAD_RIGHT));;
		if(empty($securityPassword)) {
                        // 安全密码不能为空
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102133')));
			exit;
		}
		
		$securityStatus = $this->checkRightSecurityPassword($securityPassword);
		if( $securityStatus!== TRUE){
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError($securityStatus)));
			exit;
		}
		if(!in_array(strlen($paramArray['bankNumber']),array('16','18','19'))){
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102031')));
			exit;
		}
		
		$status = $this->checkBankCardLocked();
		if($status){
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError($status)));
			exit;
		}
		//执行绑卡操作
		$result = $this->addBankCard($paramArray);
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
			if(in_array($paramArray['province'],array('北京','天津','上海','重庆'))){
				$returnArray['bankaddr'] = $paramArray['province'].'市'.$paramArray['city'];
				if(!strpos($paramArray['city'], '区')){
					$returnArray['bankaddr'] .= '区';
				}
			} else if(in_array($paramArray['province'], array('香港','澳门','台湾'))){
				$returnArray['bankaddr'] = $paramArray['province'].$paramArray['city'];
			} else {
				$returnArray['bankaddr'] = $paramArray['province'].'省'.$paramArray['city'].'市';
			}
			$returnArray['bankaddr']   .= $this->_bankIcoArray[$paramArray['bankid']]['name'].$paramArray['branchAddr'];
			$returnArray['bankAccount'] = $this->getSecurityBankCardAucount($paramArray['bankAccount']);
			$returnArray['bankNumber'] 	= $this->getSecurityBankCardNum($paramArray['bankNumber']);
			echo Zend_Json::encode(array('status'=>'ok','data'=>$returnArray));
			exit;
		} else if (isset($result['head']['status']) && $result['head']['status'] == '2007') {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102037')));
			exit;
		} else if (isset($result['head']['status']) && $result['head']['status'] == '2008') {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102036')));
			exit;
		}else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102024')));
			exit;
		}
	}

	//添加绑定请求
	private function addBankCard($paramArray){
		
		$bankCardInfo = $this->getBankCardInfo();
		
		$param['bankNumber'] = $paramArray['bankNumber'];
		$param['userId'] 	 = $this->_sessionlogin->info['id'];
		if($paramArray['opeType']!='update'){
			if($this->checkBankCardBind($param)){
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102027')));
				exit;
			}
		}
		$data['userId'] = $this->_sessionlogin->info['id'];
		$data['bankId'] = $paramArray['bankid'];
		$data['branchName'] = $paramArray['branchAddr'];
		$data['province'] = $paramArray['province'];
		$data['city'] = $paramArray['city'];
		$data['bankAccount'] = $paramArray['bankAccount'];
		$data['bankNumber'] = $paramArray['bankNumber'];
		$data['nickName'] = $paramArray['nickName'];
		$data['bindcardType'] = $paramArray['bindcardType'];
		$data['opeType'] = $paramArray['opeType'];
		$data['id'] = $paramArray['id'];
		foreach ($bankCardInfo['bankStruc'] as $value){
			if($value['code'] == $paramArray['bankid']){
				if( ($value['withdraw'] == '0' || $value['id']>=30) && $value['id']!=30  ){
					echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102152')));
					exit;
				}
				$data['mcBankId'] = $value['mownecumId'];
			}
		}
		$aUri['fund'] = 'applybankcardbind';
		$result = $this->_clientobject->inRequestV2(array('param'=>$data), $aUri);
		return $result;
	}
	
	//支付寶添加或修改绑定信息
	public function bindcardalipayaddAction(){
		
	
		if(isset($this->_sessionlogin->info['bindcardsecurityinfo']) && $this->_sessionlogin->info['bindcardsecurityinfo'] =='1'){
			//unset($this->_sessionlogin->info['bindcardsecurityinfo']);
		} else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102025')));//安全验证信息不正确,请您重新验证!
			exit;
		}
		
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		
		$rsa_result = $this->decrypt_RSA(md5('bindcard' . $user_id), $rsa_data);

		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('2016')));//停留时间过长,请重新设定
			exit;
		}
		parse_str($rsa_result, $post_data);
		//print_r($post_data);
		//exit;
		
		$paramArray = $post_data; 
		//$paramArray = $this->_request->getPost();
		
		/*$paramArray['bankAccount'] = '1234';
		$paramArray['bankNumber'] = '1234';
		$paramArray['nickName'] = '1234';
		$paramArray['bindcardType'] = '1';
		$paramArray['bankid'] = '30';
		$paramArray['opeType'] = 'update';
		$paramArray['id'] = '9440098';*/
		
		
		foreach ($paramArray as $key=>$value){
				
			if($key == securityPassword){
				$value =base64_decode(str_pad(strtr($value, '-_', '+/'), strlen($value) % 4, '=', STR_PAD_RIGHT));
			}
				
			if(!in_array($key, array('controller','action','module','securityPassword','bindcard/bindalipaycardadd'))){
				$value = getSecurityInput($value);
				if ($value =='' && $key!='nickName' ) {
					echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102026').$key));//银行卡绑定参数不能为空!
					exit;
				}
				$paramArray[$key] = $value;
			}
		}
		
		if($paramArray['opeType']!='update'){
		
			// 安全密码
			$securityPassword = base64_decode(str_pad(strtr(getSecurityInput($paramArray['securityPassword']), '-_', '+/'), strlen(getSecurityInput($paramArray['securityPassword'])) % 4, '=', STR_PAD_RIGHT));;
			if(empty($securityPassword)) {
				// 安全密码不能为空
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102133')));
				exit;
			}
			
			$securityStatus = $this->checkRightSecurityPassword($securityPassword);
			if( $securityStatus!== TRUE){
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError($securityStatus)));
				exit;
			}
			
		}
		//执行绑卡操作
		$result = $this->addBankCard($paramArray);
		
		
		
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
		
			$returnArray['bankAccount'] = $this->getSecurityBankCardAucount($paramArray['bankAccount']);
			$returnArray['bankNumber'] 	= $this->getSecurityBankCardNum($paramArray['bankNumber']);
			$returnArray['nickName'] 	= $this->getSecurityBankCardNum($paramArray['nickName']);
			echo Zend_Json::encode(array('status'=>'ok','data'=>$returnArray));
			exit;
		//} else if (isset($result['head']['status']) && $result['head']['status'] == '2007') {
			//echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102037')));//银行卡已经被锁定,请联系客服!
			//exit;
		} else if (isset($result['head']['status']) && $result['head']['status'] == '2008') {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102036')));//银行卡绑定数目已经达到上限!
			exit;
		}else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102024')));//银行卡绑定失败
			exit;
		}
		
		
	}
	
	
	
	//删除绑卡
	public function baindcarddeleteAction(){
		
		
		
		
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('bindcard' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('2016')));
			exit;
		}
		parse_str($rsa_result, $post_data);
		$bindcardType = getSecurityInput($post_data['bindcardType']);
		if($bindcardType!=1){
			//检测银行卡是否锁定
			$status = $this->checkBankCardLocked($bindcardType,'del');
			if($status){
				echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError($status)));
				exit;
			}
		}
		
		$bankId = getSecurityInput($post_data['bankid']);
		$id = getSecurityInput($post_data['id']);
		$mcBankId = getSecurityInput($post_data['mcbankid']);
		$nickName = getSecurityInput($post_data['nickName']);
		$securityPassword = base64_decode(str_pad(strtr($post_data["securityPassword"], '-_', '+/'), strlen($post_data["securityPassword"]) % 4, '=', STR_PAD_RIGHT));
		//检测安全密码是否正确
		if(empty($securityPassword)) {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102133')));
			exit;
		}
		$securityStatus = $this->checkRightSecurityPassword($securityPassword);
		if( $securityStatus!== TRUE){
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError($securityStatus)));
			exit;
		}
		if($id ==''|| $bankId =='' || $mcBankId == ''){
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102028')));			
			exit;
		}
		$data = array(
			'param'=> array(
				'bindId' => $id,
				'bankId' => $bankId,
				'mcBankId' => $mcBankId,
				'userId' => $this->_sessionlogin->info['id'],
				'bindcardType' => isset($bindcardType)?$bindcardType:0,
				'nickName' => $nickName
			)
		);
		$aUri['fund'] = 'unbindbankcard';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['head']['status']) && in_array($result['head']['status'], array('0','1'))){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$this->getError('102029')));		
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102030')));
			exit;
		}
	}
	
	
	//支付寶删除绑卡
	public function alipaycarddeleteAction(){
	
	
	
	
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$bindcardType = $this->_request->getParam("bindcardType");
		$bankId =$this->_request->getParam("bankId");
		$id = $this->_request->getParam("id");
		$mcBankId = $this->_request->getParam("mcBankId");
		//$nickName =$this->_request->getParam("bindcardType");
	
		
		$data = array(
				'param'=> array(
						'bindId' => $id,
						'bankId' => $bankId,
						'mcBankId' => $mcBankId,
						'userId' => $this->_sessionlogin->info['id'],
						'bindcardType' => isset($bindcardType)?$bindcardType:0,
						'nickName' => $nickName
				)
		);
		$aUri['fund'] = 'unbindbankcard';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['head']['status']) && in_array($result['head']['status'], array('0','1'))){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$this->getError('102029')));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','errorkey'=>$this->getError('102030')));
			exit;
		}
	}
	
	
	//ajax验证安全密码
	public function checksecuritypwdAction(){
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('bindcard' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('2016')));
			exit;
		}
		parse_str($rsa_result, $post_data);
		
		$securitypwd = base64_decode(str_pad(strtr($post_data["securitypwd"], '-_', '+/'), strlen($post_data["securitypwd"]) % 4, '=', STR_PAD_RIGHT));
		$status = $this->checkRightSecurityPassword($securitypwd);
		if($status !== TRUE){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($status)));
			exit;
		}
		echo Zend_Json::encode(array('status'=>'ok'));
		exit;
	}
	
	//检测银行卡是否锁定
	private function checkBankCardLocked($bindcardType=0,$status='add'){
		$data = array(
				'param'=> array(
						'userId' => $this->_sessionlogin->info['id'],
						'bindCardType' => $bindcardType==''?0:$bindcardType
				)
		);
		$result = $this->getbindBankCardInfo($data);
		$bindCnt = 0;
		if(isset($result['userBankStruc'])) {
			//已经绑定的银行卡数目
			$bindCnt = count($result['userBankStruc']);
		}
		if( $bindCnt>0){
			//获取用户可以绑定的银行卡数目
			$res = $this->getconfigvaluebykey('fund', $bindcardType==0?'chargeCoute':'aliPayChargeCoute','getconfigvaluebychargeCoute');
			
			$icardcount = !empty($res['val']) ? $res['val'] : '4';
			if(getSrtTimeByLong($result['overTime']) - time() <= 0 && $result['overTime']!= 0){
				return '102037';
			}
			if($status !='del' && $icardcount !='-1' && $icardcount <= $bindCnt){
				return '102036';
			}
		}
		
		return FALSE;
	}
	
	
}
