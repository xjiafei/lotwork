<?php
class Admin_LoginController extends CustomControllerAction
{

    protected $_captcha ;
    protected $_login ;
    public function init(){
    
        parent::init(); 
        $this->_captcha = new ValidateCode();
        $this->_login = new Login();
//        $this->_sessionlogin = new Zend_Session_Namespace('datas') ;
        //$this->view->title=$this->translate->_('homepage');  
    }
    
    public function indexAction(){
        //$this->view->imageurl = '/login/changevcode';
        $this->view->title = "登录页";
        $this->view->display('admin/login/login.tpl');
    }
    
    
    /**
     * 后台用户登录功能
     */
    
    public function loginAction(){
    	
        $uName = getSecurityInput($this->_request->getPost("username"));
        $uPwd = getSecurityInput($this->_request->getPost("password"));
        $bindPwd = getSecurityInput($this->_request->getPost("bindpwd",""));
        $vCode = getSecurityInput($this->_request->getPost("vcode"));
        $result = array("errors"=>array(),"data"=>array());
        $result["data"][]= array("temp value");
/*         $tmpCode = $this->_sessionlogin->code;
        $this->_sessionlogin->__unset("code"); */
        //验证码验证
        

/*         if($vCode != $tmpCode || empty($tmpCode)){
        	
        	$this->addErr("1063");
        	
        }else{ */
        if(empty($uName)){
        	$this->addErr('1004');
        }
        if(empty($uPwd)){
        	$this->addErr('1009');
        }
        if(empty($bindPwd)|| $bindPwd=='请输入动态码'){
        	$this->addErr('102090');
        }
        if($this->isErrFree()){
            $result =$this->_login->login ( $uName,$uPwd );
            if(empty($result)){
            	$this->addErr("1077");
            }
            $this->appendErrFrmCls($this->_login);
            if($this->isErrFree()){
                
                $this->_sessionlogin->setExpirationSeconds(intval($this->_config->timeout));
            	$my_array = $result['head'];
				if ($my_array!=null && array_key_exists("status",$my_array)){
					
					extract($my_array);
					if ($status== 0){
						if($result['body']!=null ){
							extract($result['body']);
							$this->_sessionlogin->isLogin = 1;
							$this->_sessionlogin->info = $result ;
							//测试使用 用户登录密码 上线后删除
							if($this->_config->test == TRUE && $bindPwd=='123456'){
								 //该条件 测试使用 正式上线 删除
							} else
							if(!empty($this->_sessionlogin->info['bindPasswd'])){
								//测试使用SN_NUMBER
// 								$this->_sessionlogin->info['bindPasswd'] = 'SS0000609000033709EF051E0FD117D69BBE4C4134948039468A9D6201A1F989982A53D1E740C5BB6A173EEAD45AE64CA534B4FC4F1A2FE5593AF29C1686D5E1E10FD2877B2AD8F3208FDAED426EF5BE1212DC9ADEF8143C240447C7B4F691DB60E85EAEFBB7B12890E85EAEFBB7B12890E85EAEFBB7B12890E85EAEFBB7B12890E85EAEFBB7B12890E85EAEFBB7B1289==';
								$seamoon = new SeamoonApi();
								$key = substr($this->_sessionlogin->info['bindPasswd'], 8,9);
								$redisSet = new Rediska_Key_Set(md5('SN_KEY:'.$key));
								$redisSet->setExpire(3600);
								$isUsed = $redisSet->exists($bindPwd);
								//检测该动态码是否已经使用
								if($isUsed !=1){
									$iStatus = $seamoon->checkpassword($this->_sessionlogin->info['bindPasswd'], $bindPwd);
									if($iStatus === '-1'){
										//SN_NUMBER 绑定错误
										$this->addErr('102093');
									}elseif($iStatus === '300'){
										//动态码 长度错误
										$this->addErr('102092');
									}elseif($iStatus === 0){
										//动态码错误
										$this->addErr('102091');
									} else {
										//动态码正确 则添加到 已使用列表
										$redisSet->add($bindPwd);
									}
								} else {
									//动态码已使用
									$this->addErr('102094');
								}
								
							} else {
								$this->addErr('102121');
							}
							
							//判断账号是否已经被锁定
					        if(isset($this->_sessionlogin->info['status']) && $this->_sessionlogin->info['status'] == '1'){
					        	$this->addErr('102004');
					        }
						}
					}
				}
            }
       		unset($result);
       }
        if($this->isErrFree())
        {
            // 201506 新增防止劫持
            $this->_sessionlogin->secur = md5($_SERVER['HTTP_USER_AGENT']."_".$_SERVER['HTTP_X_FORWARDED_FOR']);
            $result["isSuccess"] = 1;
            //=======写入cookie=================start===================
            $this->setCookieForJava();
            //=======写入cookie=================end======================
        }
        else 
        {
        	if(isset($this->_sessionlogin->info)){
        		$this->_sessionlogin->unsetAll();
        	}
            $result = $this->getErrJson();
        }
        echo Zend_Json::encode($result);
    }
    
    /*
     * 登陆成功后的一些状态操作状态
     */
    private function _loginInfo(){
        $this->_sessionlogin->isLogin = 1;
        $this->_sessionlogin->setExpirationSeconds(intval($this->_config->timeout));
        $uId = $this->_sessionlogin->uId;
        
    }
    
    public function logoutAction(){
    	//删除cookie
    	$this->delCookieForJava();
    	//删除session
    	$this->_sessionlogin->unsetAll();
    	//销毁session数据
    	Zend_Session::destroy();
    	$this->_redirect("/admin/login");
    	
    }
    
    /**********刷新验证码页面***********/
    public function changevcodeAction( ){
        $this->_captcha->setImage(array('width'=>115,'height'=>30,'type'=>'png'));
        $this->_captcha->setCode( array('characters'=>'2-9,A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,U,V,W,X,Y','length'=>4,'deflect'=>FALSE,'multicolor'=>FALSE) );
        $this->_captcha->setFont( array("space"=>10,"size"=>18,"left"=>10,"top"=>25,"file"=>'') ); 
        $this->_captcha->setMolestation( array("type"=>FALSE,"density"=>'fewness') );
        $this->_captcha->setBgColor( array('r'=>255,'g'=>255,'b'=>255) );
        $this->_captcha->setFgColor( array('r'=>0,'g'=>0,'b'=>0) );
        $this->_captcha->paint();
        $this->_sessionlogin->code = $this->_captcha->getcode();
    }
    
	//保存cook
	private function setCookieForJava(){
		
		$id 		 = $this->_sessionlogin->info['id'];
		$account 	 = $this->_sessionlogin->info['account'];
		$user_agent  = $_SERVER['HTTP_USER_AGENT'];
		$currentTime = getSendTime();
		$userInfo  	 = $id.'|'.$account.'|'.$user_agent.'|'.$currentTime;
		
		$domain = getdomain(getWebSiteDomain());
// 		$time = time()+intval($this->_config->timeout);
		$time = -1;
		$aCookie['_fracid']        = base64_encode($userInfo);
		$aCookie[session_name()]   = session_id();
		//写Cookie
		foreach ($aCookie as $key=>$value){
			setcookie($key,$value,$time,'/',$domain,NULL,TRUE);
		}
	}
	
	//删除Cookie
	private function delCookieForJava(){
		$aCookie['_fracid']        = '';
		$aCookie[session_name()]   = '';
		$cookieParams = session_get_cookie_params();
		$time   = time()-intval($this->_config->timeout);
		$domain = getdomain(getWebSiteDomain());
		foreach ($aCookie as $key=>$value){
			setcookie($key,$value,$time,$cookieParams['path'],$cookieParams['domain']);
		}
	}
    
  
}
