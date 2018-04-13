<?php
class Fundcommon extends CustomControllerAction {
	public $_clientobject;
	public $_bankIcoArray;
	public $_userType = 'user';
	protected $_uploadsession ;
	protected $_securitypwd;
    public function init() {
        parent::init ();
          $this->_valid->isInt = new MyValid_Int ( $this );
          $this->_valid->isWord = new MyValid_Word ( $this );
          $this->_valid->username = new MyValid_Username ( $this );
          $this->_uploadsession = new Zend_Session_Namespace('ANVO_FILES_UPLOAD') ;
          $this->_clientobject = new Client($this->_request->getModuleName(),$this->_request->getControllerName(),$this->_request->getActionName());
          $this->_securitypwd = new Rediska_Key_Hash(md5('securitypwd'.$this->_sessionlogin->info['id']));//安全密码 错误次数的验证和锁定
          
          $this->_bankIcoArray = array(
          		'1' => array('logo'=>'ICBC','name'=>'工商银行'),
          		'2' => array('logo'=>'CMB','name'=>'招商银行'),
          		'3' => array('logo'=>'CCB','name'=>'建设银行'),
          		'4' => array('logo'=>'ABC','name'=>'农业银行'),
          		'5' => array('logo'=>'BOC','name'=>'中国银行'),
          		'6' => array('logo'=>'COMM','name'=>'交通银行'),
          		'7' => array('logo'=>'CMBC','name'=>'民生银行'),
          		'8' => array('logo'=>'CITIC','name'=>'中信银行'),
          		'9' => array('logo'=>'SPDB','name'=>'浦发银行'),
          		'10' => array('logo'=>'PSBC','name'=>'邮政储蓄银行'),
          		'11' => array('logo'=>'CEB','name'=>'光大银行'),
          		'12' => array('logo'=>'SPABANK','name'=>'平安银行'),
          		'13' => array('logo'=>'GDB','name'=>'广发银行'),
          		'14' => array('logo'=>'HXBANK','name'=>'华夏银行'),
          		'15' => array('logo'=>'CIB','name'=>'兴业银行'),
          		'30' => array('logo'=>'ALIPAY','name'=>'支付宝'),
          		'31' => array('logo'=>'TENPAY','name'=>'财付通'),
          		'32' => array('logo'=>'YEEPAY','name'=>'易宝支付'),
          		'33' => array('logo'=>'SPAY','name'=>'盛付通'),
			'35' => array('logo'=>'QQ','name'=>'QQ钱包'), 
          		'51' => array('logo'=>'UPY','name'=>'银联充值'),
          		'40' => array('logo'=>'WECHAT','name'=>'微信支付'),          		
          );
          
          //是否VIP用户
          if(isset($this->_sessionlogin->info['vipLvl']) && $this->_sessionlogin->info['vipLvl'] >0){
          	$this->_userType ='vip';
          }
    }
    
    //获取代理名称
    public function getUserType($typeId){
    	if($typeId=='-1'){
    		$res ="玩家";
    	} else if ($typeId =='0'){
    		$res ="总代";
    	} else if($typeId>0){
    		$res = number2chinese($typeId)."级代理";
    	} else {
    		$res ="玩家";
    	}
    	return $res;
    }
    
    /**
	 * 简单封装 java接口请求 以及返回的数据的过滤
	 *  $data 传送给java的数据 
	 *  $uri java接口地址 
	 *  $classname 过滤模板类 
	 *  $testUrl 测试数据 
	 *  return array 
	 */
	public function postReuqest($aData, $aUri, $sClassName = '', $sTestUrl = '') {
		$aResult = array ();
		if ($sTestUrl != '') {
			$aResult = require $sTestUrl; // 测试数据
		} else {
			$aResult = $this->_clientobject->inRequestV2 ( $aData, $aUri );
		}
		/* if (empty ( $aResult )) {
			$this->addErr("102011");
			$this->res_show(true,true);
		} */
		$aRecorder = array ();
		if ($this->checkFirstIsAllArray($aResult ['body'] ['result'])) {
			foreach ( $aResult ['body'] ['result'] as $key => $aValue ) {
				$aRecorder [$key] = $this->getValueFromArray ( $aValue, $sClassName, $key );
			}
		} else {
			$aRecorder = $this->getValueFromArray ( $aResult ['body'] ['result'], $sClassName );
		}
		unset ( $aResult );
		return $aRecorder;
	}
	
	// 获取匹配数组
	public function getValueFromArray($aResult, $sClassName, $sKey = '') {
		$aLArray = $testArray = $testResult = array ();
		if(is_array($aResult)){
			foreach ( $aResult as $key => $value ) {
				if (is_array ( $value ) && is_array ( current ( $value ) )) {
					$aLArray [$key] = $this->getValueFromArray ( $value, $sClassName, $key );
				} else if ($sKey == '') {
					$testArray [$key] = $value;
				} else if (! is_numeric ( $sKey )) {
					$oObject = new $sClassName ( $value );
					$aColName = $oObject->getDefaultMap ();
					if(isset($aColName [$sKey])){
						foreach ( $aColName [$sKey] as $sSubKey => $value ) {
							$aLArray [$key] [$sSubKey] = $oObject->getMember ( $sSubKey );
						}
					} else {
						$this->log('Java端获取数据错误,未定义的索引:"'.$sKey.'"');
					}
					
				}
			}
		} else if($sKey!='') {
			$testArray[$sKey] = $aResult;
		}
		
		if (count ( $testArray ) > 0) {
			$oObject = new $sClassName ( $testArray );
			$aColName = $oObject->getDefaultMap ();
			foreach ( $aColName as $sSubKey => $value ) {
				$testResult [$sSubKey] = $oObject->getMember ( $sSubKey );
			}
			$aLArray = array_merge ( $testResult, $aLArray );
		}
		return $aLArray;
	}
	
	//获取通用配置接口数据
	public function getconfigvaluebykey($module,$function,$surl='getconfigvaluebykey'){
		$aUri['fundadmin'] = $surl;
		$data = array(
			'param' => array(
				'module' =>$module,
				'function' => $function
			)
		);
		$result  = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			return $result['body']['result'];
		} else {
			return false;
		}
	}
	//获取通用配置接口数据
	public function getFrontConfigValueByKey($module,$function,$surl='getconfigvaluebykey'){
		$aUri['fund'] = $surl;
		$data = array(
			'param' => array(
				'module' =>$module,
				'function' => $function
			)
		);
		$result  = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			return $result['body']['result'];
		} else {
			return false;
		}
	}
	
	//保存通用配置接口数据
	public function saveconfigvalue($value,$module,$function,$surl='saveConfig'){
		$aUri['fundadmin'] = $surl;
		$data['param']=array(
				'module' => $module,
				'function' =>$function,
				'val' => $value
		);
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if($result['head']['status'] =='0' && isset($result['body']['result'])){
			return $result['body']['result'];
		} else {
			return false;
		}
	}
	
	//获取用户绑定某个银行卡信息 $sId 为空获取所有绑定银行卡信息
	public function getbindBankCardInfo($aData,$sId = '') {
		$aLastResult = array ();
		$aData ['param']['userId']=  $this->_sessionlogin->info ['id'];
		 // 设置 传输UserId
		$aUri = array('fund'=>'queryboundbankcard');// 接口地址
		$sTestDataUrl = 'application/testdata/FundBankCardInfo.php';
		$aResult = $this->postReuqest ( $aData, $aUri, 'FundGet');//, $sTestDataUrl 
		if ($sId != '') {
			foreach ( $aResult as $aValue ) {
				if (isset($aValue ['id']) && $aValue ['id'] == $sId) {
					$aLastResult = $aValue;
				}
			}
		} else {
			$aLastResult = $aResult;
		}
		unset ( $aResult );
		return $aLastResult;
	}
	
	//获取银行卡信息或者用户银行卡绑定信息
	public function getBankCardInfo($infoType=''){
		$data['param']['userId'] = $this->_sessionlogin->info['id'];
		if($infoType != 'bankStruc'){
			$data['param']['isBind'] = '1';
		}
		$aUri = array('fund'=>'queryallbank');
		$testUrl = 'application/testdata/FundBankStruc.php';
		$aRecorders = $this->_clientobject->inRequestV2($data, $aUri,'Fund');//獲取接口返回數據  ,'application/testdata/FundBankStruc.php'
		if($infoType =='userBankStruc'){
			return $aRecorders['body']['result']['userBankStruc'];
		} elseif ($infoType == 'bankStruc') {
			if(isset($aRecorders['body']['result']['bankStruc'])){
				return $aRecorders['body']['result']['bankStruc'];
			} else {
				$this->log($this->getError('102047'));
				return array();
			}
		} else {
			return $aRecorders['body']['result'];
		}
	}
	
	//获取银行卡信息或者用户银行卡绑定信息加上FUND_BANK_SUB子表
	public function getBankCardInfoWithSub($infoType=''){
		$data['param']['userId'] = $this->_sessionlogin->info['id'];
		if($infoType != 'bankStruc'){
			$data['param']['isBind'] = '1';
		}
		$aUri = array('fundadmin'=>'queryAllbankwithsub');
		$testUrl = 'application/testdata/FundBankStruc.php';
		$aRecorders = $this->_clientobject->inRequestV2($data, $aUri,'Fund');//獲取接口返回數據  ,'application/testdata/FundBankStruc.php'
		if($infoType =='userBankStruc'){
			return $aRecorders['body']['result']['userBankStruc'];
		} elseif ($infoType == 'bankStruc') {
			if(isset($aRecorders['body']['result']['bankStruc'])){
				return $aRecorders['body']['result']['bankStruc'];
			} else {
				$this->log($this->getError('102047'));
				return array();
			}
		} else {
			return $aRecorders['body']['result'];
		}
	}
	
	
	
	
	
	
	/*
	 * 检测是否设置安全密码
	 * $data['returnUrl'] 设置安全密码后要返回本页面的地址
	 * $data['returnTitle']  链接的名称
	 * $data['title'] 安全页面标题
	 * $data['content'] 安全页面提示内容
	 */
	public function checkSecurityPassword($data)
	{
		$this->_sessionlogin->info["securityReturnUrl"]=$data['returnUrl'];
		$this->_sessionlogin->info["securityReturnTitle"]=$data['returnTitle'];
		$descript['head'] = $data['returnTitle'];
		$descript['title'] = $data['title'];
		$descript['content'] = $data['content'];
		//判断是否设置安全密码
		if (empty ( $this->_sessionlogin->info ["withdrawPasswd"] )) {
			$descript['url'] = '/safepersonal/safecodeset';
			$this->view->descript = $descript;
			$this->view->display('default/ucenter/setsecuritypassword.tpl');
			exit;
		} else if(empty( $this->_sessionlogin->info ["quStruc"] ) &&!(isset($data['unSetQues']) && $data['unSetQues'] =='1')) {
			$descript['url'] = '/safepersonal/safequestset';
			$this->view->descript = $descript;
			$this->view->display('default/ucenter/setsecuritypassword.tpl');
			exit;
		}
	}
	
	//获取银行卡卡号 页面隐藏号码
	public function getSecurityBankCardNum($getBankNumber){
		if (strlen ( $getBankNumber ) == 16) {
// 			$sBankNumber = '**** **** **** '.substr ( $getBankNumber, - 4 );
			$sBankNumber = substr_replace($getBankNumber, '**** **** **** ', 0,12);
		} elseif (strlen($getBankNumber) == 18){
			$sBankNumber = substr_replace($getBankNumber, '** **** **** **** ', 0,14);
		} else {
// 			$sBankNumber = '*** **** **** **** ' . substr ( $getBankNumber, - 4 );
			$sBankNumber = substr_replace($getBankNumber, '*** **** **** **** ', 0,15);
		}
		return $sBankNumber;
	}
	
	//获取支付寶号 页面隐藏号码
	public function getSecurityAliPayBankCardNum($getBankNumber){
		$aBkNArray = explode ( '@', $getBankNumber );
		$at = stripos($getBankNumber,"@");
		$len = strlen ( $aBkNArray[0]);
		if($at){
			if($len <= 6){
				$sBankNumber=substr($aBkNArray[0],0,2).str_repeat ( '*', $len-2 );
			}else{
				$sBankNumber=substr($aBkNArray[0],0,2).str_repeat ( '*', 4 ).substr($aBkNArray[0],$at-$len+6   ,$at);
			}
			
		$sBankNumber = $sBankNumber.'@'.$aBkNArray[1];
			
		}else{
			$sBankNumber = substr($getBankNumber, 0,3) . '****' .substr($getBankNumber, 7,11);
		}
		
		return $sBankNumber;
	}
	
	//获取银行账户安全显示用户名
	public function getSecurityBankCardAucount($username){
		$aAccArray = preg_split ( '/(?<!^)(?!$)/u', $username );
		$bankAccount = $aAccArray [0] . str_repeat ( '*', count ( $aAccArray ) - 1 );
		return $bankAccount;
	}
	
	//获取银行账户安全显示用户名
	public function getSecurityBankCardAccountLastName($username){
		$aAccArray = preg_split ( '/(?<!^)(?!$)/u', $username );
		$bankAccount =  str_repeat ( '*', count ( $aAccArray ) - 1 ) . $aAccArray [count ( $aAccArray ) - 1] ;
		return $bankAccount;
	}
	
	//更加用户名获取用户信息
	public function getUserInfo($accountName){
		$data = array(
			'param' => array('account'=>strtolower($accountName))
		);
		$aUri['accountsSecurity'] = 'queryUserByName';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			return $result['body']['result'];
		} else {
			return false;
		}
	} 
	
	//根据用户ID获取用户信息
	public function getUserInfoById($userid)
	{
		$data =array(
			'param'=>array(
					'userId'=>$userid
			)
		);
		$aUri['queryUserByCriteria_proxy'] = 'queryUserDetailInfo';
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['body']['result']['userStruc']) && count($result['body']['result']['userStruc'])>0){
			return $result['body']['result']['userStruc'];
		} else {
			return false;
		}
	}
	
	//判断用户名密码是否合理
	public function checkUserisok($accountName,$info=array()) {
		$accountName = strtolower($accountName);
		if(count($info)==0){
			$userInfo = $this->getUserInfo($accountName);
		} else {
			$userInfo = $info;
		}
		if($userInfo){
			$aChainArray = array_filter(explode('/', $userInfo['userChain']));
			array_pop($aChainArray);
			$currentAccount = $this->_sessionlogin->info['account']; // id ==> account
			if(in_array($currentAccount,$aChainArray)){
				return  true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//验证安全密码
	public function checkRightSecurityPassword($securityPassword){
		if(empty($securityPassword)){
			return "102133";
		}
		$cntName  = 'securityPassErrorCount';
		$timeName = 'securityPassErrorTime';
		$securityPassErrorCount = $this->_securitypwd->exists($cntName) ? $this->_securitypwd->$cntName : 0 ;
		$securityPassErrorTime = $this->_securitypwd->exists($timeName) ? $this->_securitypwd->$timeName : 0 ;
		if($securityPassErrorCount >=3){
			if(time() - $securityPassErrorTime <= 30*60){
				return "102035";
			} else {
				$this->_securitypwd->$cntName  = 0;
				$this->_securitypwd->$timeName = 0;
			}
		}
		if($this->_sessionlogin->info ["withdrawPasswd"] != encryptWithdrawPasswd($securityPassword)){
			if(time() - $securityPassErrorTime <= 10*60){
				$this->_securitypwd->$cntName = $securityPassErrorCount + 1;
				if($securityPassErrorCount >=2){
					return "102035";
				}
			} else{
				$this->_securitypwd->$cntName  = 1 ;
				$this->_securitypwd->$timeName = time();
			}
			return "102001";
		} else {
			$this->_securitypwd->$cntName  = 0;
			$this->_securitypwd->$timeName = 0;
		}
		return TRUE;
	}
	
	//验证宝开安全中心验证码
	public function checkSecurityCode($code,$serial){
		if(empty($code)){
			return array('status'=>'102103','data'=>'');
		}
		$_securitycode = new Rediska_Key_Hash(md5('securitycode'.$this->_sessionlogin->info['id']));//宝开安全中心验证码状态
		$cntName = 'securityCodeErrorCount';
		$timeName = 'securityCodeErrorTime';
		$securityCodeErrorCount = $_securitycode->exists($cntName) ? $_securitycode->$cntName : 0 ;
		$securityCodeErrorTime = $_securitycode->exists($timeName) ? $_securitycode->$timeName : 0 ;
		if($securityCodeErrorCount >=5){
			if(date('Y-m-d',time()) == date('Y-m-d',$securityCodeErrorTime)){
				return array('status'=>'102106','data'=>$securityCodeErrorCount);
			} else {
				$_securitycode->$cntName= 0;
				$_securitycode->$timeName = 0;
			}
		} else {
			$_mobileToken = new MobileToken();
			$status = $_mobileToken->actionVerifyToken($code, $serial);
			if(!$status){
				if(date('Y-m-d',time()) == date('Y-m-d',$securityCodeErrorTime)){
					$_securitycode->$cntName = $securityCodeErrorCount + 1;
				} else{
					$_securitycode->$cntName = 1 ;
					$_securitycode->$timeName = time();
				}
				return array('status'=>'102134','data'=>$_securitycode->$cntName);
			}
		}
		 
		return TRUE;
	}
	
	public function checkFirstIsAllArray($aCheckArray){
		if(is_array($aCheckArray)){
			foreach($aCheckArray as $key=>$value){
				if(!is_array($value)){
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	//查询账户余额
	public function getuserfundinfo(){
		$aUri['fund'] = 'getuserfundinfo';
		$data = array(
			'param' => array(
				'userId'=> $this->_sessionlogin->info['id']
			)
		);
		$result = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($result['head']['status']) && $result['head']['status'] ==''){
			$accInfo = $result['body']['result'];
			$accInfo['bal']      = getMoneyFomat($accInfo['bal']/$this->_moneyUnit,2);
			$accInfo['availBal'] = getMoneyFomat($accInfo['availBal']/$this->_moneyUnit,2);
		} else {
			$accInfo['bal'] = 0;
			$accInfo['availBal'] = 0;
		}
		return $accInfo;
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
	
	//获取未绑定的银行列表
	public function getUnbindCard($result=array()){
		$bankArray = $bankList = array();
		if(count($result)<=0){
			$result = $this->getBankCardInfo('bankStruc');
		}
		foreach ($result as $key=>$value){
			$bankArray[] = $value['id'];
		}
		$bankIcoArray = $this->_bankIcoArray;
		foreach ($bankIcoArray as $key=>$value){
			if(!in_array($key,$bankArray)){
				$bankList[] = $value;
			}
		}
		return $bankList;
			
	}
	
	//获取错误
	public function getError($code){
		return $this->_errLibrary->codeList[$code]['0'];
	}
	
	public function singleFileUpload($model,$dir,$format=array()){

		$adapter = new Zend_File_Transfer_Adapter_Http();
		$files = array();
		$upload_dir = $this->_config->upload_dir.'/upload/'.$dir.'/';
		if (!is_dir($upload_dir)) {
			return 0;
		}
		$adapter->addValidator('Extension', false, implode(",", $format));
		$adapter->addValidator('Size', false, 1024 * 2 * 1024);
		$adapter->addValidator('Count',false, array('min'=>1,'max'=>1));
		$adapter->addValidator('FilesSize', false, array (
				'min' => '1kB',
				'max' => '2MB'
		));
		$adapter->setDestination($upload_dir);//存放上传文件的文件夹
		$totalUpload = 0;
		foreach( $adapter->getFileInfo() as $key=> $fileInfo )
		{
			$fname = $fileInfo['name'];
			if($adapter->isUploaded($fname) && $adapter->isValid($fname)){
				$extName = substr($fname, strrpos($fname, '.') + 1);
				$filename= md5(date('YmdHis').rand(100000, 999999)).'.'.$extName;//重命名   此处的uniqid 有可能会出现性能问题.
				$adapter->addFilter('Rename', array(
						'source' => $fileInfo['tmp_name'],
						'target' => $filename,
						'overwrite' => true)
				);//执行重命名
				if( $adapter->receive($fname))
				{
					$files[$key] = $filename ;
					$totalUpload++;
				}
			}
		}
		unset($this->_uploadsession->$model);
		if($totalUpload>0){
			$this->_uploadsession->$model = $files ;
			
		}
		return $files;
	}
	//获取活動名稱及日期區間
	public function getActivityNameDate(){
		$data['param']['userId'] = $this->_sessionlogin->info['id'];

		$aUri = array('fundadmin'=>'queryactivitydetail');
		$aRecorders = $this->_clientobject->inRequestV2($data, $aUri,'Fund');//獲取接口返回數據 
		
		return $aRecorders['body']['result'];
	}
	
	//多文件上传公用函数
	public function multiFilesUpload($model='pic'){
		$adapter = new Zend_File_Transfer_Adapter_Http();
		$pics = array();
		$upload_dir = $this->_config->upload_dir.'/upload/images/';
		if (!is_dir($upload_dir)) {
			return 0;
		}
		$format = array('jpg','png','rar');
		$adapter->addValidator('Extension', false, implode(",", $format));
		$adapter->addValidator('Size', false, 1024 * 2 * 1024);
		$adapter->addValidator('Count',false, array('min'=>1,'max'=>3));
		$adapter->addValidator('FilesSize', false, array (
				'min' => '1kB',
				'max' => '2MB'
		));
		$adapter->setDestination($upload_dir);//存放上传文件的文件夹
		$totalUpload = 0;
		foreach( $adapter->getFileInfo() as $key=> $fileInfo )
		{
			$fname = $fileInfo['name'];
			if($adapter->isUploaded($fname) && $adapter->isValid($fname)){
				$extName = substr($fname, strrpos($fname, '.') + 1);
				$filename= md5(date('YmdHis').rand(100000, 999999)).'.'.$extName;//重命名   此处的uniqid 有可能会出现性能问题.
				$adapter->addFilter('Rename', array(
						'source' => $fileInfo['tmp_name'],
						'target' => $filename,
						'overwrite' => true)
				);//执行重命名
				if( $adapter->receive($fname))
				{
					$pics[$key] = $filename ;
					$totalUpload++;
				} 
			}
		}
		unset($this->_uploadsession->$model);
		if($totalUpload>0){
			$this->_uploadsession->$model = $pics ;
		}
		return $totalUpload;
	}
	//数字转化为汉字
	public function num2char($num,$mode=true){
		$char = array('零','一','二','三','四','五','六','七','八','九');
		//$char = array('零','壹','贰','叁','肆','伍','陆','柒','捌','玖);
		$dw = array('','十','百','千','','万','亿','兆');
		//$dw = array('','拾','佰','仟','','萬','億','兆');
		$dec = '点';  //$dec = '點';
		$retval = '';
		if($mode){
			preg_match_all('/^0*(\d*)\.?(\d*)/',$num, $ar);
		}else{
			preg_match_all('/(\d*)\.?(\d*)/',$num, $ar);
		}
		if($ar[2][0] != ''){
			$retval = $dec . $this->num2char($ar[2][0],false); //如果有小数，先递归处理小数
		}
		if($ar[1][0] != ''){
			$str = strrev($ar[1][0]);
			for($i=0;$i<strlen($str);$i++) {
				$out[$i] = $char[$str[$i]];
				if($mode){
					$out[$i] .= $str[$i] != '0'? $dw[$i%4] : '';
					if($str[$i] == 0){
						$out[$i] = '';
					}
					if($i%4 == 0){
						$out[$i] .= $dw[4+floor($i/4)];
					}
				}
			}
			$retval = join('',array_reverse($out)) . $retval;
		}
		return $retval;
	}
	
	function toExcel($objPHPExcel,$sheet){
		$objPHPExcel->getProperties()->setCreator("firefog")
		->setLastModifiedBy("firefog")
		->setTitle($sheet)
		->setSubject($sheet)
		->setDescription($sheet)
		->setKeywords("office 2007 openxml php")
		->setCategory("Test result file");
		// Add some data
	
		// Rename worksheet
		$objPHPExcel->getActiveSheet()->setTitle($sheet);
		// Set active sheet index to the first sheet, so Excel opens this as the first sheet
		$objPHPExcel->setActiveSheetIndex(0);
		// Redirect output to a client’s web browser (Excel2007)
		header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
		header('Content-Disposition: attachment;filename="'.$sheet.'.xlsx"');
		header('Cache-Control: max-age=0');
		$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel2007');
		$objWriter->save('php://output');
		exit;
	}
	
	/*
	 * @fileName: 生成文件名
	* @aTitle: 文件标题行数组
	* @aContent: 文件数据内容数组
	* @creator:生成改文件的用户
	* Description: 下载数据生成excel文件
	*/
	function downLoadDataToExcels($fileName='',$aTtitle=array(),$aContent=array(),$creator='user'){
		require_once SITE_ROOT.'/application/include/PHPExcel.php';
		require_once SITE_ROOT.'/application/include/PHPExcel/Writer/Excel2007.php';
		$objPHPExcel = new PHPExcel();
		//标题
		$startIndex=0;
		$aIndexArray= array();
		foreach ($aTtitle as $key=>$value){
			$aIndexArray[$key] = $startIndex;
			$objPHPExcel->setActiveSheetIndex(0)->setCellValue(PHPExcel_Cell::stringFromColumnIndex($startIndex++).'1',$value);
		}
		//内容
		foreach ( $aContent as $key => $value){
			foreach ($value as $sub_key=>$sub_value){
				if (isset($aIndexArray[$sub_key])) {
					$objPHPExcel->getActiveSheet()->setCellValue(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2), strval("\t".$sub_value));
				}
				if($sub_key == 'attach'){
					// 为excel加图片
					/* $objDrawing = new PHPExcel_Worksheet_Drawing();
					 $objDrawing->setName('Photo');
					$objDrawing->setDescription('Photo');
					$objDrawing->setPath($this->_config->upload_dir.'/upload/images/'.$sub_value);
					$objDrawing->setHeight(170);
					$objDrawing->setWidth(120);
					$objDrawing->setCoordinates(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2));
					$objDrawing->setWorksheet($objPHPExcel->getActiveSheet()); */
	
					//加超级链接,链接到图片
					/* $objPHPExcel->getActiveSheet()->setCellValue(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2), '附件');
					 $objPHPExcel->getActiveSheet()->getCell(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2))->getHyperlink()->setUrl(strval($this->_config->upload_dir.'/upload/images/'.$sub_value));
					$objPHPExcel->getActiveSheet()->getCell(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2))->getHyperlink()->setTooltip(strval($sub_value));
					$objPHPExcel->getActiveSheet()->getStyle(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2))->getFont()->setColor(new PHPExcel_Style_Color(PHPExcel_Style_Color::COLOR_BLUE));
					$objPHPExcel->getActiveSheet()->getStyle(PHPExcel_Cell::stringFromColumnIndex($aIndexArray[$sub_key]).strval($key+2))->getAlignment()->setHorizontal(PHPExcel_Style_Alignment::HORIZONTAL_RIGHT); */
				}
			}
		}
		unset($aContent);
		$enxtension = '.xlsx';
		$objPHPExcel->getProperties()->setCreator($creator)
		->setLastModifiedBy($creator)
		->setTitle($fileName)
		->setSubject($fileName)
		->setDescription($fileName)
		->setKeywords("office 2007 openxml php")
		->setCategory("OutPut file");
		// Add some data
		// Rename worksheet
		$objPHPExcel->getActiveSheet()->setTitle($fileName);
		// Set active sheet index to the first sheet, so Excel opens this as the first sheet
		$objPHPExcel->setActiveSheetIndex(0);
		// Redirect output to a client’s web browser (Excel2007)
		header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
		//IE中文文件名乱码
		$userAgent = $_SERVER["HTTP_USER_AGENT"];
		$encoded_filename = urlencode($fileName);
		$encoded_filename = str_replace("+", "%20", $encoded_filename);
		if (preg_match("/(?:(MSIE) |(Trident)\/.+rv:)([\w.]+)/i", $userAgent)) { //Include IE11
			header('Content-Disposition: attachment; filename="' . $encoded_filename . $enxtension.'"');
		} else if (preg_match("/Firefox/", $userAgent)) {
			header('Content-Disposition: attachment; filename*="utf8\'\'' . $fileName . $enxtension.'"');
		} else {
			header('Content-Disposition: attachment; filename="' . $fileName . $enxtension.'"');
		}
		header('Cache-Control: max-age=0');
		$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel2007');
		$objWriter->save('php://output');
		exit;
	}
	
	function downLoadDataToCSV($fileName='',$aTtitle=array(),$aContent=array(),$creator='user',$page=1,$intArray=array()){
		if($page == 1){
			set_time_limit(0);
			ini_set('memory_limit', '1024M');
			$userAgent = $_SERVER["HTTP_USER_AGENT"];
			$encoded_filename = urlencode($fileName);
			$encoded_filename = str_replace("+", "%20", $encoded_filename);
			$enxtension = '.csv';
			// 输出Excel文件头
			header('Content-Type: application/vnd.ms-excel;charset=gbk');
			if (preg_match("/(?:(MSIE) |(Trident)\/.+rv:)([\w.]+)/i", $userAgent)) { //Include IE11
				header('Content-Disposition: attachment; filename="' . $encoded_filename . $enxtension.'"');
			} else if (preg_match("/Firefox/", $userAgent)) {
				header('Content-Disposition: attachment; filename*="utf8\'\'' . $fileName . $enxtension.'"');
			} else {
				header('Content-Disposition: attachment; filename="' . $fileName . $enxtension.'"');
			}
			header('Cache-Control: max-age=0');
			
			ob_start();
			// PHP文件句柄，php://output 表示直接输出到浏览器
			$fp = fopen('php://output', 'a');
			// 输出Excel列头信息
			foreach ($aTtitle as $i => $v) {
				// CSV的Excel支持GBK编码，一定要转换，否则乱码
				$head[$i] = iconv('utf-8', 'gbk', $v);
			}
			// 写入列头
			fputcsv($fp, $head);
		} else {
			$fp = fopen('php://output', 'a');
		}
		
		// 计数器
		$cnt = 0;
		// 每隔$limit行，刷新一下输出buffer，节约资源
		$limit = 1000;
		//写入内容
		foreach ($aContent as $row){
			$cnt++;
			if($cnt == $limit){
				ob_flush();
				flush();
				$cnt =0;
			}
			foreach ($aTtitle as $i => $v) {
				// CSV的Excel支持GBK编码，一定要转换，否则乱码
				if(isset($row[$i])){
					//如果非数字用户添加制表符,所有内容显示未字符串
					if(in_array($i,$intArray)){
						$content[$i] = "\t".iconv('utf-8', 'gbk', $row[$i]);
					} else {
						$content[$i] = iconv('utf-8', 'gbk', $row[$i]);
					}
				} else {
					$content[$i] = '';
				}
			}
			fputcsv($fp, $content);
		}
		unset($aContent);
	}
}