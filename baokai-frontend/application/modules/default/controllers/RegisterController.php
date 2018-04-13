<?php
require_once  SITE_ROOT.'/application/lib/Mobile/Mobile_Detect.php';
class Default_RegisterController extends CustomControllerAction{
	protected $_captcha,$_member,$_modelRegister,$_zhTW;
	
	public function init(){
		parent::init();
		$this->_captcha = new ValidateCode();
		$this->_member  = new Member();
		$this->_modelRegister = new Register();
		$this->_zhTW = new ZHTW();
		$this->_loginParam = new Rediska_Key(md5('frontLoginParam'.session_id()));
	}
	
	//初始化加载页面,判断链接是否可用
	public function indexAction(){
		$link = getSecurityInput($this->_request->get('link',''));
		$typeMobile = getSecurityInput($this->_request->get('typeMobile'));
		
		//3.0老注册链接兼容
		if(!empty($link)){
			$url = 'link='.$link;
		}
		else if(!empty($typeMobile))
		{
			$this->view->display('default/index-success.tpl');
			exit;
		}else {
			$id  = getSecurityInput($this->_request->get('id'));
			$pid = getSecurityInput($this->_request->get('pid'));
			$exp = getSecurityInput($this->_request->get('exp'));
			$token = getSecurityInput($this->_request->get('token'));
			$qq = getSecurityInput($this->_request->get('qq'));
			$cellphone = getSecurityInput($this->_request->getPost('cellphone'));
			$activityType = getSecurityInput($this->_request->getPost('activityType'));

			//参数是否为空
			if (empty($id) || empty($pid) || empty($exp) || empty($token) ){
				$this->addErr('102066');
				$this->res_show(true,true,'login');
			}
			//检测链接加密规则是否正确
			if (!$this->_modelRegister->checkUrl($id,$pid,$token)) {
				$this->addErr('102066');
				$this->res_show(true,true,'login');
			}
			
			if (empty($qq))
			{
				$url = 'id='.$id.'&exp='.$exp.'&pid='.$pid.'&token='.$token;
			}
			else
			{
				$url = 'id='.$id.'&exp='.$exp.'&pid='.$pid.'&token='.$token.'&qq='.$qq;
			}
			
		}
		//数据库验证 链接有效性
		$result = $this->_modelRegister->checkUrlDB($url);
		if($result === FALSE){
			$this->addErr('102066');
			$this->res_show(true,true,'login');
		}
		$oMobile_Detect = new Mobile_Detect();
		$adSpaces =$this->_modelRegister->getAdSpaceById(4);
		$registerCnt = isset($this->_sessionlogin->info['registerCode'])?intval($this->_sessionlogin->info['registerCode']) : 0;
		
		//產生公私鑰
		$uniq_id = uniqToken();	//產生一個不重複的字串
		$config = array(
			"digest_alg" => "sha512",
			"private_key_bits" => 2048,
			"private_key_type" => OPENSSL_KEYTYPE_RSA,
		);
		$res = openssl_pkey_new($config);
		//取得私鑰
		openssl_pkey_export($res, $privKey);
		//儲存私鑰 (8小時內有效)
		$this->redis_client->set(md5('register' . $uniq_id), $privKey, 8*60*60);
		$details = openssl_pkey_get_details($res);
		//要傳給 JS 加密的
		$rsa_n = to_hex($details['rsa']['n']);
		$rsa_e = to_hex($details['rsa']['e']);
		$this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
        $this->_loginParam->expire(intval(60 * 60 * 24));
		$this->view->rsa_n = $rsa_n;
		$this->view->rsa_e = $rsa_e;
		$this->view->uniq_id = $uniq_id;
		$this->view->cellphone = $cellphone;
		$this->view->activityType = $activityType;
		$this->view->errorCnt = $registerCnt;
		$this->view->adSpaces = isset($adSpaces['body']['result']) ? $adSpaces['body']['result'] : array('isDftUsed'=>0);
		$this->view->imageurl = '/register/changevcode';
		$this->view->qq = $qq;
		$this->view->loginRand = $this->_loginParam->getValue();
		if($oMobile_Detect->isMobile() && !$oMobile_Detect->isTablet()){
			$this->view->display('default/register_mobile.tpl');
		} else {
			$this->view->display('default/register.tpl');
		}
	}
	
	public function mobileSuccessAction()
	{
		$this->view->display('default/index-success.tpl');
	}
	
	//提交注册申请
	public function conformAction(){
		
		//進行解密流程
		$uniq_id = $this->_request->getPost("uniq_id");
		$privKey = $this->redis_client->get(md5('register' . $uniq_id));	//取得私鑰
		if(empty($privKey)) {	//取得私鑰失敗
			$data['isSuccess'] = 0;
			$data['msg'] = $this->getError('2016');
			echo Zend_Json::encode($data);
			exit;
		}
		$key_handle = openssl_pkey_get_private($privKey);	//載入私鑰
		//對 rsa_data 解密
		$rsa_data = $this->_request->getPost("rsa_data");
		openssl_private_decrypt(pack('H*', $rsa_data), $rsa_data, $key_handle);
		parse_str($rsa_data, $post_data);
		//print_r($post_data);
		
		$username  = getSecurityInput(trim($post_data['username']));
		$password1 = base64_decode(str_pad(strtr($post_data["password1"], '-_', '+/'), strlen($post_data["password1"]) % 4, '=', STR_PAD_RIGHT));
		$password2 = base64_decode(str_pad(strtr($post_data["password2"], '-_', '+/'), strlen($post_data["password2"]) % 4, '=', STR_PAD_RIGHT));
		$device = getSecurityInput(trim($post_data['device']));
		$cellphone = getSecurityInput(trim($post_data['cellphone']));
		$activityType = getSecurityInput(trim($post_data['activityType']));
		
		$registerCnt = isset($this->_sessionlogin->info['registerCode'])?intval($this->_sessionlogin->info['registerCode']) : 0;
		if(empty($username)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1004'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(!preg_match("/^(?![0-9])/u",$username)){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102129')));
			exit;
		}
		$pattern="/^[0-9]{1,}$/";//纯数字匹配
		if(preg_match($pattern,$username)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102068'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		//if(!preg_match("/^[".chr(0xa1)."-".chr(0xff)."A-Za-z0-9_]+$/",$username)) //GB2312汉字字母数字下划线正则表达式
		if(!preg_match("/^[A-Za-z0-9]+$/u",$username)){ //UTF8汉字字母数字下划线正则表达式
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102155'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(getStrLen($username)<4 || getStrLen($username)>16){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102086'),'dataCnt'=> $registerCnt,'type'=>'1'));
			unset($strlen);
			exit;
		} 
		$twstatus = $this->_zhTW->checkZhtw($username);
		if($twstatus){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102122'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(empty($password1) || empty($password2)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1009'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		if($password1 !== $password2){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102065'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		if($username == $password1){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102126'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		//错误次数大于三次 需要验证码验证
		if($registerCnt>2){
			$vcode     = getSecurityInput($post_data['vcode']);
			$rcode = $this->_sessionlogin->rcode;
			$this->_sessionlogin->__unset("code");
			if(empty($rcode)){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102069'),'dataCnt'=> $registerCnt,'type'=>'3'));
				exit;
			}
			//验证码验证
			if(strtolower($vcode) != strtolower($rcode)){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102070'),'dataCnt'=> $registerCnt,'type'=>'3'));
				exit;
			}
		}
		//验证链接合法性
		$referer   = !empty($_SERVER['HTTP_REFERER']) ? $_SERVER['HTTP_REFERER'] : 'unknow';
		$urlArray = parse_url($referer);
		if(!isset($urlArray['query'])){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
			exit;
		}
		parse_str($urlArray['query'],$urlParam);//解析url参数
		//检查3.0老链接合法性
		if(isset($urlParam['link']) && !empty($urlParam['link'])){
			$url = 'link='.getSecurityInput($urlParam['link']);
		} else {
			if(empty($urlParam['id']) || empty($urlParam['exp']) || empty($urlParam['pid']) || empty($urlParam['token'])){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
				exit;
			}
			if(!$this->_modelRegister->checkUrl($urlParam['id'],$urlParam['pid'],$urlParam['token'])){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
				exit;
			}
			
			if ($urlParam['qq'])
			{
				$url = 'id='.$urlParam['id'].'&exp='.$urlParam['exp'].'&pid='.$urlParam['pid'].'&token='.$urlParam['token'].'&qq='.$urlParam['qq'];
			}
			else
			{
				$url = 'id='.$urlParam['id'].'&exp='.$urlParam['exp'].'&pid='.$urlParam['pid'].'&token='.$urlParam['token'];
			}
		}
		
		$result = $this->_modelRegister->checkUrlDB($url);
		if($result === FALSE){
			//print_r('error url');
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
			exit;
		} else {
			$urlId = $result['id'];
			$parentId = $result['creator'];
			$userLvl = $result['type'];
		}
		
		//验证用户名是否合法
		$status = $this->_modelRegister->islegalAccount($username);
		if($status =='0'){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102087'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		} else if($status == '101002'){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('101002'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		$passwdLvl = $this->_member->passLvl($password1);
		$data = array(
			'parentId' 	=> $parentId,
			'userLvl' 	=> $userLvl,
			'urlId' 	=> $urlId,
			'account'   => strtolower($username),
			'passwd'    => encryptLoginPasswd($password1),
			'passwdLvl' => intval($passwdLvl),
			'referer'   => $referer,
			'registerIp'   => bindec(decbin(ip2long(get_client_ip()))),
			'registerDate' => getSendTime(),
			'device' => strtolower($device),
			'cellphone' => $cellphone,
			'activityType' => $activityType
		);
		$status = $this->_modelRegister->createUser($data);
		if($status === TRUE){
			unset($this->_sessionlogin->info['registerCode']);
			echo Zend_Json::encode(array('status'=>'ok','data'=>'恭喜您,用户:'.$username."注册成功!"));
			exit;
		} else {
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($status['errorNum']),'dataCnt'=> $registerCnt,'type'=>$status['errorType']));
			exit;
		}
	}
	
	
	//testset
	public function conform2Action(){
		
		//進行解密流程
		/*$uniq_id = $this->_request->getPost("uniq_id");
		$rsa_data = $this->_request->getPost("rsa_data");
		$rsa_data = $this->_request->getPost("rsa_data");
		$rsa_result = $this->decrypt_RSA(md5('register' . $uniq_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			$data['isSuccess'] = 0;
			$data['msg'] = $this->getError('2016');
			echo Zend_Json::encode($data);
			exit;
		}
		parse_str($rsa_result, $post_data);*/
		//print_r($post_data);
		
		$username  = getSecurityInput(trim($this->_request->getPost('username')));
		$password1 = getSecurityInput(trim($this->_request->getPost('password1')));
		$password2 = getSecurityInput(trim($this->_request->getPost('password2')));
		$device = getSecurityInput(trim($this->_request->getPost('device')));
		$pt = getSecurityInput(trim($this->_request->getPost('pt')));
		
		
		$registerCnt = isset($this->_sessionlogin->info['registerCode'])?intval($this->_sessionlogin->info['registerCode']) : 0;
		if(empty($username)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1004'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(!preg_match("/^(?![0-9])/u",$username)){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102129')));
			exit;
		}
		$pattern="/^[0-9]{1,}$/";//纯数字匹配
		if(preg_match($pattern,$username)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102068'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		//if(!preg_match("/^[".chr(0xa1)."-".chr(0xff)."A-Za-z0-9_]+$/",$username)) //GB2312汉字字母数字下划线正则表达式
		if(!preg_match("/^[A-Za-z0-9]+$/u",$username)){ //UTF8汉字字母数字下划线正则表达式
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102155'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(getStrLen($username)<4 || getStrLen($username)>16){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102086'),'dataCnt'=> $registerCnt,'type'=>'1'));
			unset($strlen);
			exit;
		} 
		$twstatus = $this->_zhTW->checkZhtw($username);
		if($twstatus){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102122'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		if(empty($password1) || empty($password2)){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1009'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		if($password1 !== $password2){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102065'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		if($username == $password1){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102126'),'dataCnt'=> $registerCnt,'type'=>'2'));
			exit;
		}
		//错误次数大于三次 需要验证码验证
		if($registerCnt>2){
			$vcode     = getSecurityInput($post_data['vcode']);
			$rcode = $this->_sessionlogin->rcode;
			$this->_sessionlogin->__unset("code");
			if(empty($rcode)){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102069'),'dataCnt'=> $registerCnt,'type'=>'3'));
				exit;
			}
			//验证码验证
			if(strtolower($vcode) != strtolower($rcode)){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102070'),'dataCnt'=> $registerCnt,'type'=>'3'));
				exit;
			}
		}
		//验证链接合法性
		
		$referer   = !empty($_SERVER['HTTP_REFERER']) ? $_SERVER['HTTP_REFERER'] : 'unknow';
		$urlArray = parse_url($referer);
		if(!isset($urlArray['query'])){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
			exit;
		}
		parse_str($urlArray['query'],$urlParam);//解析url参数
		//检查3.0老链接合法性
		if(isset($urlParam['link']) && !empty($urlParam['link'])){
			$url = 'link='.getSecurityInput($urlParam['link']);
		} else {
			if(empty($urlParam['id']) || empty($urlParam['exp']) || empty($urlParam['pid']) || empty($urlParam['token'])){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
				exit;
			}
			if(!$this->_modelRegister->checkUrl($urlParam['id'],$urlParam['pid'],$urlParam['token'])){
				$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
				exit;
			}
			$url = 'id='.$urlParam['id'].'&exp='.$urlParam['exp'].'&pid='.$urlParam['pid'].'&token='.$urlParam['token'];
		}
		$result = $this->_modelRegister->checkUrlDB($url);
		if($result === FALSE){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102066'),'dataCnt'=> $registerCnt,'type'=>'4'));
			exit;
		} else {
			$urlId = $result['id'];
			$parentId = $result['creator'];
			$userLvl = $result['type'];
		}
		//验证用户名是否合法
		$status = $this->_modelRegister->islegalAccount($username);
		if($status =='0'){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102087'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		} else if($status == '101002'){
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('101002'),'dataCnt'=> $registerCnt,'type'=>'1'));
			exit;
		}
		$passwdLvl = $this->_member->passLvl($password1);
		$data = array(
			'parentId' 	=> $parentId,
			'userLvl' 	=> $userLvl,
			'urlId' 	=> $urlId,
			'account'   => strtolower($username),
			'passwd'    => encryptLoginPasswd($password1),
			'passwdLvl' => intval($passwdLvl),
			'referer'   => $referer,
			'registerIp'   => bindec(decbin(ip2long(get_client_ip()))),
			'registerDate' => getSendTime(),
			'device' => strtolower($device)
		);
		if(empty($pt)){
			$status = $this->_modelRegister->createUser($data);
		}else{
			$status = $this->_modelRegister->createPtUser($data);
		}
		
		if($status === TRUE){
			unset($this->_sessionlogin->info['registerCode']);
			echo Zend_Json::encode(array('status'=>'ok','data'=>'恭喜您,用户:'.$username."注册成功!"));
			exit;
		} else {
			$this->_sessionlogin->info['registerCode'] = ++$registerCnt;
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($status['errorNum']),'dataCnt'=> $registerCnt,'type'=>$status['errorType']));
			exit;
		}
	}
	
	
	//检测用户名是否合法
	public function checkusernameAction(){
		$username = getSecurityInput($this->_request->getPost('username',''));
		
		if(empty($username)){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('1004')));
			exit;
		}
		if(!preg_match("/^(?![0-9])/u",$username)){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102129')));
			exit;
		}
		$pattern="/^[0-9]{1,}$/";//纯数字匹配
		if(preg_match($pattern,$username)){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102068')));
			exit;
		}
		if(!preg_match("/^[A-Za-z0-9]+$/u",$username)){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102155')));
			exit;
		}
		if(getStrLen($username)<4 || getStrLen($username)>16){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102086')));
			unset($strlen);
			exit;
		}
		$twstatus = $this->_zhTW->checkZhtw($username);
		if($twstatus){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102122')));
			exit;
		}
		$status = $this->_modelRegister->islegalAccount($username);
		if($status =='0'){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('102087')));
			exit;
		} else if($status == '101002'){
			echo Zend_Json::encode(array('status'=>'1','data'=>$this->getError('101002')));
			exit;
		} else{
			echo Zend_Json::encode(array('status'=>'0'));
			exit;
		}
	}
	
	//刷新验证码页面
	public function changevcodeAction( ){
		$this->_captcha->setImage(array('width'=>115,'height'=>30,'type'=>'png'));
		$this->_captcha->setCode( array('characters'=>'2-9,A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,U,V,W,X,Y','length'=>4,'deflect'=>FALSE,'multicolor'=>FALSE) );
		$this->_captcha->setFont( array("space"=>10,"size"=>18,"left"=>10,"top"=>25,"file"=>'') );
		$this->_captcha->setMolestation( array("type"=>FALSE,"density"=>'fewness') );
		$this->_captcha->setBgColor( array('r'=>255,'g'=>255,'b'=>255) );
		$this->_captcha->setFgColor( array('r'=>0,'g'=>0,'b'=>0) );
		$this->_captcha->paint();
		$this->_sessionlogin->rcode = $this->_captcha->getcode();
	}
	
	//为前台提供实时检测验证码的接口
	public function checkvcodeAction(){
		 
		$code = $this->_sessionlogin->rcode;
		$postCode = getSecurityInput($this->_request->getPost('vcode',null));
		if(empty($postCode)){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102069')));
			exit;
		}
		if(strtolower($code) == strtolower($postCode)){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$this->getError('102071')));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102070')));
			exit;
		}
	}
	
	//获取错误
	public function getError($code){
		return $this->_errLibrary->codeList[$code]['0'];
	}


}