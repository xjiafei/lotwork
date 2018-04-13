<?php
class Default_TransferController extends Fundcommon{
	private $_pagesize,$_total,$_page,$_accountSecurity;
    public function init() {
        parent::init ();
        $this->_pagesize = 15;
        $this->_total =0;
        $this->_page = 1;
        $this->_errView = 'ucenter';
        $this->_accountSecurity = new AccountsSecurity();
    }
	
	//我要转账 显示页面
	public function indexAction(){
		$data = array(
				'returnUrl'=> '/transfer/',
				'returnTitle'=>'我要转账',
				'title'=>'您还没有设置安全验证信息，请设置后再进行转账。',
				'content' => '为了保障您的账户资金安全，将在转账申请过程中验证您的安全验证信息。'
		);
		//检测是否设置安全密码
		$this->checkSecurityPassword($data);
		//获取提现限额
		$aValibal  = $this->getAvalibalCharge();
		//删除跳转信息
		if(isset($this->_sessionlogin->info ["securityReturnTitle"])){
			unset($this->_sessionlogin->info ["securityReturnTitle"]);
			unset($this->_sessionlogin->info ["securityReturnUrl"]);
		}
		$aValibal['up_transfer'] 		= $aValibal['up_transfer'];
		$aValibal['down_transfer'] 		= $aValibal['down_transfer'];
		$aValibal['availBal'] 			= $aValibal['availBal'];
		$aValibal['up_avaliTransfer'] 	= $aValibal['up_transfer']< $aValibal['availBal'] ? $aValibal['up_transfer'] : $aValibal['availBal'];
		$aValibal['down_avaliTransfer'] = $aValibal['down_transfer']< $aValibal['availBal'] ? $aValibal['down_transfer'] : $aValibal['availBal'];
		$aValibal['up_avaliTransferTime'] = $aValibal['up_time'] == '-1' ? '无限' : ($aValibal['up_time']- $aValibal['avaliTransferTimeUp']>=0 ? $aValibal['up_time']- $aValibal['avaliTransferTimeUp'] : '0');
		$aValibal['down_avaliTransferTime']= $aValibal['down_time'] == '-1' ? '无限' : ($aValibal['down_time']- $aValibal['avaliTransferTimeDown']>=0 ? $aValibal['down_time']- $aValibal['avaliTransferTimeDown'] : '0');
		$aValibal['down_lvls']	  			= $aValibal['down_lvls'];
		$aValibal['isTransferUp']	        = $aValibal['up_time'] != '-1' && $aValibal['up_time']- $aValibal['avaliTransferTimeUp'] <=0 ? 0 : 1;
		$aValibal['isTransferDown']	        = $aValibal['down_time'] != '-1' && $aValibal['down_time']- $aValibal['avaliTransferTimeDown'] <=0 ? 0 : 1;
		$aValibal['up_time'] 				= $aValibal['up_time'] == '-1' ? '无限' : ($aValibal['up_time']>=0 ? $aValibal['up_time'] : '0');
		$aValibal['down_time']				= $aValibal['down_time'] == '-1' ? '无限' : ($aValibal['down_time']>=0 ? $aValibal['down_time'] : '0');
		
		$aValibal['up_avaliTransfer_format'] 	= getMoneyFomat($aValibal['up_avaliTransfer'],2);
		$aValibal['down_avaliTransfer_format'] 	= getMoneyFomat($aValibal['down_avaliTransfer'],2);
		$aValibal['availBal_format'] 			= getMoneyFomat($aValibal['availBal'],2);
		$aValibal['up_transfer'] 				= getMoneyFomat($aValibal['up_transfer'],2);
		$aValibal['down_transfer'] 				= getMoneyFomat($aValibal['down_transfer'],2);
		//是否可转账
		$enableTransfer = in_array($this->_sessionlogin->info['freezeMethod'], array(1,4,5)) ? 0 : 1;
		
		//检测用户名是否为当前用户直接代理.客户管理给下级代理充值 做校验
		$uname = getSecurityInput($this->_request->get('name',''));
		if(!empty($uname)){
			$userInfo = $this->getUserInfo($uname);
			if(isset($userInfo['parentId']) && isset($userInfo['account']) && $this->_sessionlogin->info['id'] == $userInfo['parentId'] && $uname = $userInfo['account']){
				$this->view->uname = $userInfo['account'];
			}
		}
		$this->view->aValibal = $aValibal;
		$this->view->enableTransfer = $enableTransfer;
		$this->view->title    = "我要转账";
		$this->view->userLvl  = $this->_sessionlogin->info['userLvl'];
		$this->view->display('default/ucenter/fund/transfer_view.tpl');
	}
	
	//验证用户转账信息
	public function transferconformAction(){
// 		$this->res_add_fail("信息验证失败");
		$this->res_add_url("我要转账","/transfer");
		$this->res_add_url("我要转账","/transfer",true);
		$tranto = intval(getSecurityInput($this->_request->getPost('tranto','1')));
		$accountName = strtolower(getSecurityInput($this->_request->getPost('accountName')));
		$changeCount = abs(floatval(str_replace(',', '', getSecurityInput($this->_request->getPost('changeCount','0')))));
		
		//判断总代和普通用户 转账方向正确性
		if($this->_sessionlogin->info['userLvl']==0){
			if($tranto != 0) {
				$this->addErr("102014");
				$this->res_show(true,true);
			}
		} else if ($this->_sessionlogin->info['userLvl'] =='-1') {
			if($tranto !='1'){
				$this->addErr("102015");
				$this->res_show(true,true);
			}
		}
		//判断 向下级转账 是否有填写用户名
		if(empty($accountName) && $tranto == '0'){
			$this->addErr("102012");
			$this->res_show(true,true);
		}
		if(empty($changeCount)){
			$this->addErr("102013");
			$this->res_show(true,true);
		}
		//转账金额必须不能小于等于零
		if($changeCount<=0){
			$this->addErr('102061');
			$this->res_show(true,true);
		}
		//是否可转账
		if(in_array($this->_sessionlogin->info['freezeMethod'], array(1,4,5))){
			$this->addErr("102124");
			$this->res_show(true,true);
		}
		//转账金额合理性检验
		$aValibal  = $this->getAvalibalCharge();
		if($changeCount > $aValibal['availBal']) {
			$this->addErr("102041");
			$this->res_show(true,true);
		}
		//======================
		$accountLevel = '';
		if($tranto =='1') {
			if($this->_sessionlogin->info['userLvl']==0){
				$this->addErr("102014");
				$this->res_show(true,true);
			}
			$accountName = '上级';
			//转账金额是否大于上级转账限额
			if($changeCount > $aValibal['up_transfer']){
				$this->addErr("102042");
				$this->res_show(true,true);
			}
			//转账次数是否大于 上级可转账次数
			if($aValibal['up_time'] !='-1' && $aValibal['up_time']- $aValibal['avaliTransferTimeUp'] <=0){
				$this->addErr("102063");
				$this->res_show(true,true);
			}
		} else {
			if($this->_sessionlogin->info['userLvl'] == '-1'){
				$this->addErr("102015");
				$this->res_show(true,true);
			}
			if($aValibal['down_lvls']!='1'){
				$this->addErr("102137");
				$this->res_show(true,true);
			}
			//转账金额是否大于下级转账限额
			if($changeCount > $aValibal['down_transfer']){
				$this->addErr("102042");
				$this->res_show(true,true);
			}
			//转账次数是否大于 下级可转账次数
			if($aValibal['down_time'] !='-1' && $aValibal['down_time']- $aValibal['avaliTransferTimeDown'] <=0){
				$this->addErr("102063");
				$this->res_show(true,true);
			}
			
			
			$userInfo = $this->getUserInfo($accountName);
			$aChainArray = array_filter(explode('/', $userInfo['userChain']));
			array_pop($aChainArray);
			$userChain = array();
			foreach ($aChainArray as $key=>$value){
				if($value == $this->_sessionlogin->info['account']){ // id ==> account
					$userChain = array_slice($aChainArray, $key);
				}
			}
			$accountLevel = implode('>', $userChain);
		}
		//=====================
		//安全问题
		$aQuStruc = $this->_sessionlogin->info['quStruc'];
		$id = rand(0, 2);
		$sQus = $this->_accountSecurity->getQuestionById($aQuStruc[$id]['qu']);
		$this->view->quStruc = array('id'=>$aQuStruc[$id]['qu'],'qus'=>$sQus);
		unset($aQuStruc,$sQus);
		
		$this->_sessionlogin->info['transfer'] = 1;
		//產生公私鑰並儲存私鑰
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$res_rsa = $this->encrypt_RSA(md5('transferconform' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		
		$this->view->currentName = $this->_sessionlogin->info['account'];
		$this->view->changeCount = $changeCount;
		$this->view->changeCountFormat = getMoneyFomat($changeCount,2);
		$this->view->accountName = $accountName;
		$this->view->accountLevel = $accountLevel;
		$this->view->tranto = $tranto;
		$this->view->title = "转账确认";
		$isBindSecCard = isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']) ? 1 : 0;
		$this->view->isBindSecCard = $isBindSecCard;
		$this->view->display('default/ucenter/fund/transfer_conform.tpl');
	}
	
	//发送转账请求,显示处理结果
	public function transferresultAction(){
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('transferconform' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('2016')));
			exit;
		}
		parse_str($rsa_result, $post_data);
		//print_r($post_data);
		//exit;
		
		/*
		$tranto 	 = getSecurityInput($this->_request->getPost('tranto',1));
		$accountName = strtolower(getSecurityInput($this->_request->getPost('accountName',null)));
		$changeCount = abs(floatval(number_format(str_replace(',', '', getSecurityInput($this->_request->getPost('changeCount','0'))),2,'.','')));
		$securityPassword = getSecurityInput($this->_request->getPost('passowrd','null'));
		$type = getSecurityInput($this->_request->getPost('type'));
		*/
		$tranto 	 = getSecurityInput($post_data['tranto']);
		$accountName = strtolower(getSecurityInput($post_data['accountName']));
		$changeCount = abs(floatval(number_format(str_replace(',', '', getSecurityInput($post_data['changeCount'])),2,'.','')));
		$securityPassword = base64_decode(str_pad(strtr($post_data["passowrd"], '-_', '+/'), strlen($post_data["passowrd"]) % 4, '=', STR_PAD_RIGHT));
		$type = getSecurityInput($post_data['type']);
		if($this->_sessionlogin->info['userLvl']==0){
			if($tranto != 0) {
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102014')));
				exit;
			}
		} else if ($this->_sessionlogin->info['userLvl'] =='-1') {
			if($tranto !='1'){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102015')));
				exit;
			}
		}
		if(empty($changeCount)){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102013')));
			exit;
		}
		//转账金额必须不能小于等于零
		if($changeCount<=0){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102061')));
			exit;
		}
		//是否可转账
		if(in_array($this->_sessionlogin->info['freezeMethod'], array(1,4,5))){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102124')));
			exit;
		}
		//转账金额合理性检验
		$aValibal  = $this->getAvalibalCharge();
		if($changeCount > $aValibal['availBal']) {
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102041')));
			exit;
		}
		$securityStatus = $this->checkRightSecurityPassword($securityPassword);
		if( $securityStatus!== TRUE){
			echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError($securityStatus)));
			exit;
		}
		if($type == '1'){
			$answer = getSecurityInput(trim($post_data['answer']));
			$questionid = intval(getSecurityInput($post_data['questionid']));
			$aSecurityQuestion[] = array('qu'=>$questionid,'ans'=>$answer);
			$status = $this->_accountSecurity->checkRightSecurityQuestion($aSecurityQuestion);
			if($status !== TRUE){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError($status)));
				exit;
			}
		} else {
			$code = getSecurityInput($post_data['code']);
			if(isset($this->_sessionlogin->info['serialNumber']) && !empty($this->_sessionlogin->info['serialNumber'])){
				$checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
	            if($checkStatus!==TRUE){
	                $msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
	                echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
	                exit;
	            }
			}
		}
		//判断 向下级转账 是否有填写用户名
		if($tranto == '0'){
			//普通用户不能向下级转账
			if($this->_sessionlogin->info['userLvl'] == '-1'){
				$this->addErr("102015");
				$this->res_show(true,true);
			}
			if($aValibal['down_lvls']!='1'){
				$this->addErr("102137");
				$this->res_show(true,true);
			}
			//转账金额是否大于上级转账限额
			if($changeCount > $aValibal['down_transfer']){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102042')));
				exit;
			}
			//转账次数是否大于 上级可转账次数
			if($aValibal['down_time'] !='-1' && $aValibal['down_time']- $aValibal['avaliTransferTimeDown'] <=0){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102063')));
				exit;
			}
			if(empty($accountName)){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102012')));
				exit;
			}
			$userInfo = $this->getUserInfo($accountName);
			//判断转账用户的合理性
			$isok = $this->checkUserisok($accountName,$userInfo);
			if($isok != true){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102017')));
				exit;
			}
			$userId = $userInfo['id'];
		} else {
			
			//转账金额是否大于上级转账限额
			if($changeCount > $aValibal['up_transfer']){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102042')));
				exit;
			}
			//转账次数是否大于 上级可转账次数
			if($aValibal['up_time'] !='-1' && $aValibal['up_time']- $aValibal['avaliTransferTimeUp'] <=0){
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102063')));
				exit;
			}
			
			
			$accountName = $this->_sessionlogin->info['parentAccount'];
			$userId = $this->_sessionlogin->info['parentId'];
			if(empty($userId)){
				$this->log('无法获取上级用户ID');
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>'无法获取上级用户ID'));
				exit;
			}
			if(empty($accountName)){
				$message = new Message();
				$res = $message->getAccountINfoById($userId);
				$accountName = $res['body']['result']['userStruc']['account'];
				$this->log('无法获取上级用户名');
				echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>'无法获取上级用户名'));
				exit;
			}
		}
		$data = array(
			'param' => array(
				'rcvUserId'=>$userId,
				'rcvAccount'=>strtolower($accountName),
				'isUpward'=>$tranto,
				'userId'=>$this->_sessionlogin->info['id'],
				'transferAmt'=>$changeCount*$this->_moneyUnit,
				'applyTime'=>getSendTime()
			)
		);
		if(isset($this->_sessionlogin->info['transfer']) && $this->_sessionlogin->info['transfer'] == '1'){
			$aUri['fund'] = 'fundtransfer';
			$result  = $this->_clientobject->inRequestV2($data, $aUri);
			unset($this->_sessionlogin->info['transfer']);
			if(isset($result['head']['status']) && $result['head']['status']=='0'){
				$aData['title'] = '转账成功';
				$aData['accountName'] = ($tranto =='1') ? '上级' : $accountName;
				$aData['changeCount'] = $changeCount;
				$aData['verifyCode'] = md5($aData['accountName'] . $aData['changeCount'] . "宝开verifyCode");
				echo Zend_Json::encode(array('isSuccess'=>'1','msg'=>base64_encode(Zend_Json::encode($aData))));
				exit;
			}
		}
		echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('102043')));
		exit;
	}
	
	public function displayresultAction(){
		$data = json_decode(base64_decode($this->_request->get('data')),true);
		$data = array_map("htmlspecialchars", $data);
		//进行验证
		$tmpVerifyCode = md5($data['accountName'] . $data['changeCount'] . "宝开verifyCode");
		if($data['verifyCode'] != $tmpVerifyCode)
		{
			die('验证错误');
		}
		$this->view->accountName = isset($data['accountName']) ? $data['accountName'] : '';
		$this->view->changeCount = isset($data['changeCount']) ? getMoneyFomat($data['changeCount'],2) : 0;
		$this->view->title = isset($data['title']) ? $data['title'] : '转账失败';
		$this->view->display('default/ucenter/fund/transfer_result.tpl');
	}
	//判断用户是否合理
	public function checkuserAction(){
		$accountName = getSecurityInput($this->_request->getPost('accountName',null));
		$userInfo = $this->checkUserisok($accountName);
		if($userInfo === true){
			echo Zend_Json::encode(array('status'=>'ok'));
		} else {
			echo Zend_Json::encode(array('status'=>'error'));
		}
	}
	
	//获取转账可用余额 以及剩余转账额度 等初始信息
	public function getAvalibalCharge(){
		$aUri['fund'] = 'fundtransferinit';
		$result = $this->_clientobject->inRequestV2(array('param'=>array('userId'=>$this->_sessionlogin->info['id'])), $aUri);
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			$transfer = $result['body']['result']['transferStruc'];
			$aValibal['up_time'] 	  = $transfer['up_time']; //当天向上转账次数限制
			$aValibal['down_time'] 	  = $transfer['down_time']; //当天向下转账次数限制
			$userLvl = $this->_sessionlogin->info['userLvl']>3 ? 4 : $this->_sessionlogin->info['userLvl'];
			$aValibal['down_lvls'] 	  = in_array($userLvl, $transfer['down_lvls']) ? 1 : 0; //向可否向下级转账
			$aValibal['up_transfer'] = $transfer['up_'.$this->_userType.'limit']/$this->_moneyUnit; //当天向上级额度限制
			$aValibal['down_transfer'] = $transfer['down_'.$this->_userType.'limit']/$this->_moneyUnit; //当天向下级转账额度限制
			$aValibal['availBal'] = $result['body']['result']['bal']/$this->_moneyUnit; //可用余额
// 			$aValibal['availBal'] = ($result['body']['result']['bal'] - $result['body']['result']['unavailBal'])/$this->_moneyUnit; //可用余额
// 			$aValibal['avaliTransferLimit'] = $result['body']['result']['avaliTransferLimit']/$this->_moneyUnit;
			$aValibal['avaliTransferTimeUp']  = $result['body']['result']['avaliTransferTimeUp'];
			$aValibal['avaliTransferTimeDown']  = $result['body']['result']['avaliTransferTimeDown'];
		} else {
			$aValibal['up_time'] = '0';
			$aValibal['down_time'] = '0';
			$aValibal['down_lvls'] = '0';
			$aValibal['up_transfer'] = '0';
			$aValibal['down_transfer'] = '0';
			$aValibal['availBal'] = '0';
			$aValibal['avaliTransferLimit'] = '0';
			$aValibal['avaliTransferTime'] = '0';
			$aValibal['avaliTransferTimeUp'] = '0';
			$aValibal['avaliTransferTimeDown'] = '0';
		}
		
		return  $aValibal;
	}
	
	//转账记录
	public function historyAction(){
		$this->res_add_url("转账记录","/transfer/history");
		$this->res_add_url("转账记录","/transfer/history",true);
		$aStatusArray = array('所有类型','转入','转出');
		$tCode = getSecurityInput($this->_request->getParam('tCode'));
		$fromDate = getSecurityInput($this->_request->getParam('fromDate',date('Y-m-d',strtotime('-6 days'))));
		$toDate = getSecurityInput($this->_request->getParam('toDate',date('Y-m-d')));
		$status = intval(getSecurityInput($this->_request->getParam('status')));
		$page   = intval(getSecurityInput($this->_request->getParam('page')));
		$_POST = $this->_request->getParams();
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$fromDate = date('Y-m-d',strtotime($fromDate));
				$data['param']['fromDate'] = getQueryStartTime($fromDate);
				$_POST['fromDate'] = $fromDate;
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}
		if(!empty($toDate)){
			if (strtotime($toDate)){
				$toDate = date('Y-m-d',strtotime($toDate));
				$data['param']['toDate'] = getQueryEndTime($toDate);
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

		if($status){
			$data['param']['direction'] = $status == 2 ? 0 : $status;
		}
		if(!empty($page)){
			$this->_page = $page;
		}
		if($tCode){
			$data['param']['sn'] = $tCode;
		}
		$account = $this->_sessionlogin->info['account'];
		if($account){
			$data['param']['exactUser'] = $account;
		}
		$data['param']['ifSelf']  = 1;
		$data['pager']['startNo'] = ($this->_page-1)*$this->_pagesize+1;
		$data['pager']['endNo']   = $this->_page*$this->_pagesize;
		
		$aUri['fund'] = 'queryfundtranferrecordbycriteria';//转账记录接口
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if($this->_clientobject->err_isExist()){
			$code = $this->_clientobject->err_get();
			$this->addErr($code[0][0]);
			$this->res_show(true,true);
		}
		$aTotalCharge = array('input'=>'0','output'=>'0');
		$content=array();
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			foreach ($result['body']['result'] as $key=>$value){
				$content[$key]['sn']= $value['sn'];
				$content[$key]['status']= $value['rcvAccount'] == $account ? '1' : '2';
				$content[$key]['transferTime'] = date('Y-m-d H:i:s',getSrtTimeByLong($value['transferTime']));
				$content[$key]['userAccount'] = $value['userAccount'];
				$content[$key]['rcvAccount'] = $value['rcvAccount'];
				$content[$key]['transferAmt'] = getMoneyFomat($value['transferAmt']/$this->_moneyUnit,2);
// 				$transferTime[$key] = $content[$key]['transferTime'];
				if($content[$key]['status'] == '2'){
					if($value['isUpward'] !='0') {
						$content[$key]['rcvAccount'] = '上级';
					}
				} else{
					if($value['isUpward'] =='0') {
						$content[$key]['userAccount'] = '上级';
					}
				}
			}
			$this->_total = $result['body']['pager']['total'];//总记录数
			$aTotalCharge['input'] = !empty($result['body']['pager']['sum']) ? getMoneyFomat($result['body']['pager']['sum']/$this->_moneyUnit,2) : '0';
			$aTotalCharge['output'] = !empty($result['body']['pager']['sum2']) ? getMoneyFomat($result['body']['pager']['sum2']/$this->_moneyUnit,2) : '0';
		}
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		$this->view->pages = $pages; //分页
		$this->view->account = $account; // 当前账户
		$this->view->accInfo = $this->getuserfundinfo(); // 获取账户余额
		$this->view->aTotalCharge = $aTotalCharge; //转入转出金额统计数组
		$this->view->content = $content; //记录结果
		$this->view->total = $this->_total;
		$this->view->aStatusArray = $aStatusArray; // 转入转出类型数组
		$this->view->_POST = $_POST;
		$this->view->title = "转账记录";
		$this->view->display('default/ucenter/fund/transferdetail_list.tpl');
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