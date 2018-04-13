<?php
require_once 'application/models/default/Mail.php';
class Valid{}
class Default_LoginController extends CustomControllerAction
{
    protected $_captcha ;
    protected $getAdSpaceByAdId ;
    protected $_accountsecurity;
    protected $_mail;
    protected $_valid;
    protected $_errView = "login";
    protected $_message;
    protected $_sessionUserId;
    protected $_loginCode;
    protected $_loginParam;
    protected $_loginCount;
    protected $_login;
    protected $_sessionTool;


    protected $_tool;
    public function init(){
    
        parent::init(); 
        $this->_valid = new Valid();
        $this->_captcha = new ValidateCode();
        $this->_login = new Member();
        $this->_mail = new Mail();
        $this->_tool = new Tool();
        $this->_accountsecurity = new AccountsSecurity ();
        $this->_valid->pwd = new MyValid_Password ( $this );
        $this->_valid->uname = new MyValid_Username ( $this );
        $this->_valid->email = new MyValid_Email ( $this );
        $this->_valid->isInt = new MyValid_Int ( $this );
        $this->_valid->isWord = new MyValid_Word ( $this );
        $this->_valid->safeAns = new MyValid_SafeAnswer ( $this );
        $this->_message = new Message ();
        $this->_sessionTool = new SessionTool($this->_config);
        $this->_loginParam = new Rediska_Key(md5('frontLoginParam'.session_id()));
    }
    
    public function indexAction(){
        $mobile=$this->mobile();
//$this->log( Zend_log::ERR,"KKKKKKKKKKKKKK" );    	
        $this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
        $this->_loginParam->expire(intval(60 * 60 * 24));
    	$ck_loginCode = empty($this->_sessionlogin->info["logincode"])?0:intval($this->_sessionlogin->info["logincode"]);
    //	$adSpaces = $this->_login->getAdSpaceById(3);
        $this->view->imageurl = '/login/changevcode';
        $this->view->code = '/login/changevcode';
        $this->view->errortimes = $ck_loginCode;
        $this->view->loginRand = $this->_loginParam->getValue();
        //$this->view->adSpaces = isset($adSpaces['body']['result']) ? $adSpaces['body']['result'] : array('isDftUsed'=>0);
        if($mobile=="1")
        {
            $this->view->display('default/login/wapLogin.tpl');
        }
                
        if($mobile=="0")
        {
            $this->view->display('default/login/login.tpl');
        }
        
        }
    public function index2Action(){
        $mobile=$this->mobile();

    	$this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
        $this->_loginParam->expire(intval(60 * 60 * 24));
    	$ck_loginCode = empty($this->_sessionlogin->info["logincode"])?0:intval($this->_sessionlogin->info["logincode"]);
    //	$adSpaces = $this->_login->getAdSpaceById(3);
        $this->view->imageurl = '/login/changevcode';
        $this->view->code = '/login/changevcode';
        $this->view->errortimes = $ck_loginCode;
        $this->view->loginRand = $this->_loginParam->getValue();
        //$this->view->adSpaces = isset($adSpaces['body']['result']) ? $adSpaces['body']['result'] : array('isDftUsed'=>0);
        if($mobile=="1")
        {
            $this->view->display('default/login/wapLogin.tpl');
        }
                
        if($mobile=="0")
        {
            $this->view->display('default/login/index.tpl');
        }
        
        }


    public function mobile ()
    {
        $device='/(alcatel|amoi|android|avantgo|blackberry|benq|cell|cricket|'.
            'docomo|elaine|htc|iemobile|iphone|ipaq|ipod|j2me|java|'.
            'midp|mini|mmp|motorola|nokia|palm|panasonic|philips|'.
            'phone|sagem|sharp|smartphone|sony|symbian|t-mobile|telus|'.
            'vodafone|wap|webos|wireless|xda|xoom|zte)/i';
        if (preg_match($device, $_SERVER['HTTP_USER_AGENT'])) {return 1;}
        else {return 0;}
    }
    public function getbynameAction(){
   	 	$uName = $this->_request->getPost("username");
     	$data = $this->_login->getUserInfoByName( $uName );
     	header ( 'Content-Type: application/json;charset=utf-8' );
     	if($data["body"] && array_key_exists("result", $data["body"])){
	     		if($data["body"]["result"] && array_key_exists("account", $data["body"]["result"])){
	     			if($data["body"]["result"]["account"]== strtolower($uName) ){
	     				if($data["body"]["result"]["cipher"]){
	     					echo json_encode(array("isSucess"=>1)); //有预留信息
	     				}else{
	     					echo json_encode(array("isSucess"=>2)); //没有预留信息 
	     				}
		     		}else{
		     			echo json_encode(array("isSucess"=>0)); //fail 失败
		     		}
	     		}else{
	     			echo json_encode(array("isSucess"=>0)); //fail 失败
	     			$this->log( Zend_log::ERR,"在java返回的body中的account节点丢失" );
	     		}
	    }else{
	     	echo json_encode(array("isSucess"=>0)); //fail 失败
	     	$this->log( Zend_log::ERR ,"在java返回的body中的result节点丢失");
	    }
    }
    public function getbypassAction(){
    	
    	$uName = $this->_request->getPost("username");
    	$data = $this->_login->getUserInfoByName( $uName );
    	header ( 'Content-Type: application/json;charset=utf-8' );
    	if($data["body"] && array_key_exists("result", $data["body"])){
    		if($data["body"]["result"] && array_key_exists("account", $data["body"]["result"])){
    			if($data["body"]["result"]["account"]== $uName ){
    					echo json_encode(array("isSucess"=>1)); //用户名存在
    			}else{
    				echo json_encode(array("isSucess"=>0)); //fail 失败
    			}
    		}else{
    			echo json_encode(array("isSucess"=>0)); //fail 失败
    			$this->log( Zend_log::ERR,"在java返回的body中的account节点丢失" );
    		}
    	}else{
    		echo json_encode(array("isSucess"=>0)); //fail 失败
    		$this->log( Zend_log::ERR ,"在java返回的body中的result节点丢失");
    	}
    }
    public function loginAction(){
		$userAgent=$this->_request->getHeader("USER-AGENT");
        $uName = getSecurityInput($this->_request->getPost("username"));
        //30分钟内连续登录错误次数
        $LoginParam = $this->_loginParam->getValue() ? $this->_loginParam->getValue() : $this->_request->getPost("param");
//         echo $LoginParam.'---'.$this->_request->getPost('param');
        $uPwd  = $this->_request->getPost("password");
//      $uPwd = base64_decode($uPwd) ;
        $vCode = $this->_request->getPost("vcode");
        $safeMod = $this->_request->getPost("safemod");
        $result = array("errors"=>array(),"data"=>array());
        $result["data"][]= array("temp value");
        //=================验证码 错误次数=============start=================
        //验证码错误次数关联用户 防止用户删除session 跳过验证码session绑定的逻辑
        $this->_loginCode = new Rediska_Key(md5('frontLoginCode'.$uName));
        $codeErrcnt = $this->_loginCode->getValue();
        $ck_loginCode = empty($this->_sessionlogin->info["logincode"])?(empty($codeErrcnt) ? 0 : intval($codeErrcnt)):intval($this->_sessionlogin->info["logincode"]);
        
        //==============================记录频繁登录次数============start=====================
        $this->_loginCount = new Rediska_Key_Hash(md5('frontLoginCount'.$uName));
        $lastLoginCount = 0;
        $lastLoginTime = floatval($this->_loginCount->get('time'));
        if(time() - $lastLoginTime >= 3){
        	$this->_loginCount->delete();
        } else {
        	$lastLoginCount = intval($this->_loginCount->get('count'));
        }
        //==============================记录频繁登录次数================end===================
        if(!intval($safeMod)){
	        if($ck_loginCode>2){
	            $tmpCode = $this->_sessionlogin->code;
	            $this->_sessionlogin->__unset("code");
	            
	            //验证码验证
	            if(empty($vCode)){
	            	$this->addErr('102069');
	            }else if(strtolower($vCode) != strtolower($tmpCode) || empty($tmpCode)){
	                $this->addErr("102070");
	            }
	        } 
	        if ($lastLoginCount>0){
            	//频繁登录次数记录
            	$this->_loginCount->set('count',$lastLoginCount+1);
            	$this->addErr("102149");
            }
         }else{
         	$tCode = $this->_sessionlogin->code;
         	$this->_sessionlogin->__unset("code");
         	if(empty($tCode)){
         		$this->_redirect('/login/sectlogin/');
         	}
         	//验证码验证
         	if(empty($vCode)){
         		$this->addErr('102069');
         	}else if(strtolower($vCode) != strtolower($tCode) || empty($tCode)){
         		$this->addErr("102070");
         	}
        } 
        //=================验证码 错误次数=============end=================
        
        //用户名格式验证
//         $this->_valid->uname->isValid($uName,"json");
        $regx = "/^[\x{4e00}-\x{9fa5}A-Za-z0-9_]+$/u";
        if (!preg_match($regx, $uName)) {
        	$this->addErr("1005");
        } elseif (getStrLen($uName)<3 || getStrLen($uName)>16){
        	$this->addErr("1006");
        }
        
        
        if($this->isErrFree()){
        	//根据用户名获取用户信息
        	$data = $this->_login->getUserInfoByName( $uName );
            if($safeMod){
                //安全登陆
               
                if(empty($data['body']['result'])){
                	$this->addErr("1003");
                }
                $this->appendErrFrmCls($this->_login);
                if($this->isErrFree()){
                	
                    $this->_sessionlogin->info["account"] = $uName;
                    $this->_sessionlogin->info["cipher"] = $data["body"]["result"]["cipher"];
                    $this->_sessionlogin->info["passwd"] = $data["body"]["result"]["passwd"];
                    $this->_sessionlogin->info["questionStructureActiveDate"] = $data["body"]["result"]['questionStructureActiveDate'];
                    $this->_sessionlogin->info["freezeMethod"] = $data["body"]["result"]['freezeMethod'];
                    $this->getParentAccount();
                    $result["data"]['cipher']= $this->_sessionlogin->info["cipher"];
                }else{
                    $result = $this->getErrJson();
                }
            }else{
                //普通登陆
                
            	//密码格式验证
            	//$this->_valid->pwd->isValid($uPwd,"json");
            	
            	if(empty($data['body']['result'])){
            		$this->addErr("101001");
            	}
            	$this->appendErrFrmCls($this->_login);
            	if(!$this->isErrFree()){
            		$result = $this->getErrJson();
            	}else{
            		if(!complieLoginPasswd($data["body"]["result"]["passwd"],$uPwd,$LoginParam)){
            			//ip限制登录,用户名密码错误
            			$this->addErr('101001');
            		}
            		if(!$this->isErrFree()){
            			$result = $this->getErrJson();
            		}else{
            			//用户名密码验证
            			$data = $this->_login->login ( $uName, $data["body"]["result"]["passwd"],$userAgent );
            			if(isset($data['head']['status'])){
            				if($data['head']['status']=='102004'){
            					//用户冻结
            					$this->addErr('1075');
            				}else if(in_array($data['head']['status'], array('102009','101001'))){
            					//ip限制登录,用户名密码错误
            					$this->addErr($data['head']['status']);
            				}else if(in_array($data['head']['status'], array('102209'))){
            					//指定IP白名單限制限制登录
            					$this->addErr($data['head']['status']);
            				}
            			} else {
            				$this->addErr('1077');
            			}
          
                
            			$this->appendErrFrmCls($this->_login);
            			if($this->isErrFree()){
            				//是否可以多端登录
            				/* $canMultLogin = $data["body"]["result"]['loginCtrl']['multLogin'];
            				 if($canMultLogin !=1){
            				//$this->_sessionTool->truncate($data["body"]["result"]['id']);
            				$userInfo['loginOut'] = 1;
            				$this->_sessionTool->update($data["body"]["result"]['id'],$userInfo);
            				$this->_sessionlogin->loginOut = 0;
            				} */
            				
            				$this->_sessionlogin->isLogin = 1;
            				$this->_sessionlogin->info["Ip"] = get_client_ip();
            				$this->_sessionlogin->info = $data["body"]["result"];
            				//奖金组返点全部为0
            				if(!isset($this->_sessionlogin->info["isAllZero"])){
            					$this->_sessionlogin->info["isAllZero"] = 0;
            				}
            				//暂时全部置为1
            				$this->_sessionlogin->info["isAllZero"] = 1;
            				if(intval($this->_sessionlogin->info['loginCtrl']['overTime'])>0){
            					$this->_sessionlogin->setExpirationSeconds(intval($this->_sessionlogin->info['loginCtrl']['overTime']*60));
            				}
            				$this->getParentAccount();
            				//把user_id和session_id关联起来
            				$this->_sessionUserId = new Rediska_Key_Set(md5('ANVO'.$this->_sessionlogin->info['id']));
            				$this->_sessionUserId->add(session_id());
            				if(intval($this->_sessionlogin->info['loginCtrl']['overTime'])>0){
            					$this->_sessionUserId->expire(intval($this->_sessionlogin->info['loginCtrl']['overTime']*60));
            				}
            			
            			}
            		}
            	}
            }
        }
        
        if($this->isErrFree()){
        	//验证用户是否申诉被锁状态
        	if($this->_sessionlogin->info["freezeMethod"] == "5"){
	        	if( $this->_sessionlogin->info["questionStructureActiveDate"] > getSendTime()){
	        		$this->addErr("1075");
	        	} else{
	        		//符合条件解冻
	    			$parm['userId'] = $this->_sessionlogin->info["id"];
	    			$parm['range'] = 1 ;
	    			$parm['memo'] = "账号申诉成功,系统自动解冻";
	    			$parm['freezeAccount '] = $this->_sessionlogin->info["account"];
	    			$this->_login->unfreezeuser($parm);
	    			$this->appendErrFrmCls($this->_login);
	        	}
        	}
        	//验证用户是否被完全冻结
        	if($this->_sessionlogin->info["freezeMethod"]==1){
        		$this->addErr("1075");
        	}
        }
        if($this->isErrFree()){
            $result['isSuccess'] = 1;
            unset($this->_sessionlogin->info["logincode"]);
            $this->_loginCode->delete();
            $this->_loginParam->delete();
            //==============频繁登录次数记录=========start=============
            $this->_loginCount->set('count',1);
            $this->_loginCount->set('time',time());
            //==============频繁登录次数记录===========end=============
            
            //=======写入cookie=================start===================
            if(!$safeMod){ //普通登录 完成登录写入cookie
            	$this->setCookieForJava();
            }
            //=======写入cookie=================end======================
            // 201506 新增防止劫持
            $this->_sessionlogin->secur = md5($_SERVER['HTTP_USER_AGENT']."_".$_SERVER['HTTP_X_FORWARDED_FOR']);
        }else{
            $this->_sessionlogin->unsetAll();
            //=================验证码 错误次数=============start=================
            $this->_sessionlogin->info["logincode"] = $ck_loginCode +1;
            $this->_loginCode->increment(1);
            $codeErrcnt = $this->_loginCode->getValue();
            $ck_loginCode = empty($this->_sessionlogin->info["logincode"])?(empty($codeErrcnt) ? 0 : intval($codeErrcnt)):intval($this->_sessionlogin->info["logincode"]);
            //=================验证码 错误次数=============end=================
            $result = $this->getErrJson($ck_loginCode);
            $this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
            $this->_loginParam->expire(intval(60 * 60 * 24));
            $result['param'] = $this->_loginParam->getValue();
        }
        echo Zend_Json::encode($result);
        exit;
    }
    
    
    function getParentAccount(){
    	if(isset($this->_sessionlogin->info["parentId"]) && $this->_sessionlogin->info["parentId"]>0){
    		$parentId = $this->_sessionlogin->info["parentId"] ;
	    	$res = $this->_message->getAccountINfoById($parentId);
	    	$this->appendErrFrmCls($this->_message);
	    	$this->_sessionlogin->info["parentAccount"]   =  $res['body']['result']['userStruc']['account'] ;
    	} else {
    		$this->_sessionlogin->info["parentAccount"] = '';
    	}
    }
    /**
     * 
     * 登出动作
     */
    public function logoutAction(){
    	if($this->_sessionlogin->isLogin =='1'){
    		//删除user_id和session_id的关联索引
    		$this->_sessionUserId = new Rediska_Key_Set(md5('ANVO'.$this->_sessionlogin->info['id']));
    		$this->_sessionUserId->remove(session_id());
    		//删除cookie
    		$this->delCookieForJava();
    		//删除session
    		$this->_sessionlogin->unsetAll();
    		//消除掉空的session_id
    		Zend_Session::destroy();
    	}
        $this->_redirect("/");
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
    
    /**********安全登陆页面*************/
    public function showsectloginAction(){
    	$this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
    	$this->view->imageurl = '/login/changevcode';
    	$this->view->loginRand = $this->_loginParam->getValue();
        $this->view->display('default/login/safe.tpl') ;
    }

        /**********安全登陆页面*************/
    public function loginsecurityAction(){
        $this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
        $this->view->imageurl = '/login/changevcode';
        $this->view->loginRand = $this->_loginParam->getValue();
        $this->view->display('default/login/loginSecurity.tpl') ;
    }

    public function downloadAction(){
        $this->view->display('default/login/download.tpl') ;
    }
    
    public function checkusernameAction(){
        $uName = $this->_request->getPost("username");
        $uInfo = $this->_login->getUserInfoByName($uName);
        $this->appendErrFrmCls($this->_login);
        if(empty($uInfo["body"]['result'])){
             $this->addErr("1003");
        }else{
            $uInfo = $uInfo["body"]["result"];
        }
        if($this->isErrFree()){
            $result["isSuccess"] ="1";
            $this->_sessionlogin->info = $uInfo;
        }else{
            $result["isSuccess"] ="0";
        }
        echo Zend_Json::encode($result);
    }
    //安全登录处理页面
    public function sectloginAction( ){
		$userAgent=$this->_request->getHeader("USER-AGENT");
        $result = array("errors"=>array(),"data"=>array());
        $result["data"][]= array("temp value");
        $uName = isset($this->_sessionlogin->info["account"]) ? $this->_sessionlogin->info["account"] : NULL;
        if(!empty($uName)){
            $uPwd = $this->_request->getPost("password");
            $loginParam = $this->_loginParam->getValue() ? $this->_loginParam->getValue() : $this->_request->getPost("param");
            if(!empty($uPwd)){
                //密码格式验证
                //$this->_valid->pwd->isValid($uPwd,"json");
                
            	if(!complieLoginPasswd($this->_sessionlogin->info["passwd"],$uPwd,$loginParam)){
            		//ip限制登录,用户名密码错误
            		$this->addErr('102075');
            	}
            	if(!$this->isErrFree()){
            		$result = $this->getErrJson();
            	} else {
            		//用户名密码验证
            		$data = $this->_login->login ( $uName, $this->_sessionlogin->info["passwd"],$userAgent );
            		if(isset($data['head']['status']) && $data['head']['status']=='102009'){
            			$this->addErr('102009');
            		}
                        if(isset($data['head']['status']) && $data['head']['status']=='102209'){
            			$this->addErr('102209');
            		}
            		if(isset($data['head']['status']) && $data['head']['status']=='102004'){
            			$this->addErr('1075');
            		}
                
            		$this->appendErrFrmCls($this->_login);
            	}
                
                if($this->isErrFree()){
                	//是否可以多端登录
                	/* $canMultLogin = $data["body"]["result"]['loginCtrl']['multLogin'];
                	if($canMultLogin !=1){
                		//$this->_sessionTool->truncate($data["body"]["result"]['id']);
                		$userInfo['loginOut'] = 1;
                		$this->_sessionTool->update($data["body"]["result"]['id'],$userInfo);
                		$this->_sessionlogin->loginOut = 0;
                	} */
                	
                    $this->_sessionlogin->isLogin = 1;
                    $this->_sessionlogin->info["Ip"] = get_client_ip();
                    $this->_sessionlogin->info = $data["body"]["result"];
                    //奖金组返点全部为0
                    if(!isset($this->_sessionlogin->info["isAllZero"])){
                    	$this->_sessionlogin->info["isAllZero"] = 0;
                    }
                    //暂时全部置为1
                    $this->_sessionlogin->info["isAllZero"] = 1;
                    if(intval($this->_sessionlogin->info['loginCtrl']['overTime'])>0){
                    	$this->_sessionlogin->setExpirationSeconds(intval($this->_sessionlogin->info['loginCtrl']['overTime']*60));
                    }
                    $result['isSuccess'] = 1;
                    $this->getParentAccount();
                    //把user_id和session_id关联起来
                    $this->_sessionUserId = new Rediska_Key_Set(md5('ANVO'.$this->_sessionlogin->info['id']));
                    $this->_sessionUserId->add(session_id());
                    if(intval($this->_sessionlogin->info['loginCtrl']['overTime'])>0){
	                    $this->_sessionUserId->expire(intval($this->_sessionlogin->info['loginCtrl']['overTime']*60));
                    }
                    //=======写入cookie=================start===================
		            $this->setCookieForJava();
		            //=======写入cookie=================end======================
                }else{
                    $this->_sessionlogin->unsetAll();
                    $result = $this->getErrJson();
                }
            }else{
            	/* if($this->_sessionlogin->info["cipher"]){ */
            		//$this->view->selfMessage = $this->_sessionlogin->info["cipher"];
            		//$this->view->display('default/login/safe2.tpl') ;
            	/* }else{
            		$this->_sessionlogin->info["cipher"] = "" ;
            		$this->_redirect('/login/showsectlogin/');
            	} */
            	$this->addErr('1009');
            	$result = $this->getErrJson();
            }
        }else{
            //$this->_redirect('/login/showsectlogin/');
            $this->addErr('1077');
        	$result = $this->getErrJson();
        }
        /* if(!$this->isErrFree()){
        	$this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
        	$result['param'] = $this->_loginParam;
        } */
        echo Zend_Json::encode($result);
        exit;
    }
    
    //为前台提供实时检测验证码的接口
    public function checkvcodeAction(){
    	
    	$code = $this->_sessionlogin->code;
    	$postCode = getSecurityInput($this->_request->getPost('vcode',null));
    	if(empty($postCode)){
    		echo Zend_Json::encode(array('status'=>'0','data'=>$this->_errLibrary->codeList['102069']['0']));
    		exit;
    	}
    	if(strtolower($code) == strtolower($postCode)){
    		echo Zend_Json::encode(array('status'=>'1','data'=>'验证成功'));
    		exit;
    	} else {
    		echo Zend_Json::encode(array('status'=>'0','data'=>$this->_errLibrary->codeList['102070']['0']));
    		exit;
    	}
    }

    /*找回密码
     *URL: /login/findpwd
     * 
     */
    public function findpwdAction(){
        $stp = intval($this->_request->getParam("stp"));
        
        if(empty($this->_sessionlogin->info["id"])&&( !in_array($stp, array(0,6,2)) ) ){
            $this->_redirect("/login/findpwd");
        }
        
        switch($stp){
            case 1:
                
                //是否设置安全密码
                $isSetSafeCode = 1;
                //是否设置安全问题
                $isSetSafeQuest = 1;
                //是否绑定邮箱
                $isBindMail = 1;
                
                if(empty($this->_sessionlogin->info["withdrawPasswd"])){
                    $isSetSafeCode = 0;
                }
                if(empty($this->_sessionlogin->info["quStruc"])){
                    $isSetSafeQuest = 0;
                }
                
                if(empty($this->_sessionlogin->info["emailActived"])){
                    $isBindMail = 0;
                }
                
                $uName = $this->_sessionlogin->info["account"];
                $this->view->uName = $uName;
                // 是否有设置安全密码
                $this->view->isSetSafeCode = $isSetSafeCode;
                
                // 是否有设置安全问题
                $this->view->isSetSafeQuest = $isSetSafeQuest;
                
                // 是否有绑定邮箱
                $this->view->isBindMail = $isBindMail;
				$mail = empty($this->_sessionlogin->info["email"])?"":$this->_sessionlogin->info["email"];
				$this->view->mail = substr_replace($mail, '****', 2, 4);
				
				//安全问题
                $questList = $this->_accountsecurity->getQuestionListByuId();
                $this->view->questList = $questList;
                
            break;
            case 2:
                //邮箱
//                $uId = $this->_sessionlogin->info["id"];
//                if(empty($uId)){
//                    $this->_redirect("/login/findpwd");
//                }
                
                
                //获取用户ID和邮箱验证码
                $act = $this->_request->getParam ( "act" );
                if(!empty($act)&&$act=="mail"){
                    //邮箱验证码
                    $timeout = 86400;
                    $param = array();
                    $param["name"] = $this->_request->getParam ( "name" );
                    $param["email"] = $this->_request->getParam ( "email" );
                    $param["time"] = $this->_request->getParam ( "time" );
                    $param["time"] = number_format($param["time"] ,0,'','');
                    $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                    $mask = $this->_request->getParam ( "BCODE" );
                    
                    
                    //$this->res_add_fail("邮箱验证失败");
                    $this->res_add_url("返回","/login/findpwd",false,true);
                    //$this->res_add_url("登陆页面","/login");
                    
                    /************表单验证********/
                    
                    
                    $this->_valid->uname->isValid($param["name"],"json");
                    $this->_valid->isInt->isValid($param["time"],"json");
                    $this->_valid->isWord->isValid($param["chkAct"],"json");
                    $this->_valid->email->isValid($param["email"],"json");
                    
                    if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                        $this->addErr("102051");
                        $this->res_show(true,true,'login');
                    }
                    
                    $this->_mail->verify($mask, $param, 1);
                    $this->appendErrFrmCls($this->_mail);
                    
                    if(empty($this->_sessionlogin->info)){
                        $data = $this->_login->getUserInfoByName( $param["name"] );
                        $this->appendErrFrmCls($this->_login);
                        
                        if($this->isErrFree()){
                            $this->_sessionlogin->info = $data["body"]["result"];
                        }
                    }
                    
                    if($this->isErrFree()){
                        $this->_sessionlogin->resetPwd = 1; //34;
                        $this->_tool->update($this->_sessionlogin->info["account"], 1,getSendTime());
                        $this->redirect("/login/findpwd?stp=5");
                    }else{
                        $this->res_show(true,true,'login');
                    }
                    
                }else{
                    $timeout = 0;
                    $uId = $this->_sessionlogin->info["id"];
	                if(empty($uId)){
	                	$this->_redirect("/login/findpwd");
	                }
                    $email = $this->_sessionlogin->info["email"];
                    
                    //发送邮件
                    
                    $uName = $this->_sessionlogin->info["account"];
                    
                    $param = array();
                    $param["name"] = $this->_sessionlogin->info["account"];
                    $param["email"] = $email;
                    $param["time"] = number_format(getSendTime(),0,'','');
                    $param["chkAct"] = "findPwd";
                    // 发送邮箱
                    $param["BCODE"] = $this->_mail->encrypt($param);
                    /* $this->_mail->sendVerifiedMail("找回登录密码邮件", 
                                                    $email, 
                                                    $this->_sessionlogin->info["account"], 
                                                    "找回密码功能", 
                                                    "重置密码", 
                                                    "/login/findpwd?stp=2&act=mail", 
                                                    $param,
                                                    1); */
                    //通知发送邮件
                    $this->_mail->sendVerifiedMail(
                    		$this->_sessionlogin->info['id'],
                    		$this->_sessionlogin->info["account"],
                    		8,
                    		"/login/findpwd?stp=2&act=mail",
                    		$param,
                    		1
                    );
                        
                    
                    
                    
                    
                    $this->view->uName = $uName;
                    $this->view->mail = substr_replace($email, '***', 2, 4);
                }
                
            break;
            case 3:
                //安全问题
                if(empty($this->_sessionlogin->info["id"])){
                    $this->_redirect("/login/findpwd");
                }
                
                //如果act有值则认为是ajax提交
                $act = $this->_request->getParam ( "act" );
                if(empty($act)){
                    $questList = $this->_login->getQuestionListByuId();
                    $this->view->questList = $questList;
                }else{
                    $result = array();
                    // 获取安全问题和答案
                    $qId = getSecurityInput($this->_request->getPost ( "questId" ));
                    $qAns = getSecurityInput($this->_request->getPost ( "questAns" ));
                    $qId2 = getSecurityInput($this->_request->getPost ( "questId2" ));
                    $qAns2 = getSecurityInput($this->_request->getPost ( "questAns2" ));
                    
                    /* $this->_valid->isInt->isValid($qId,"json");
                    $this->_valid->safeAns->isValid($qAns,"json");
                    $this->_valid->isInt->isValid($qId2,"json");
                    $this->_valid->safeAns->isValid($qAns2,"json"); */
                    
                    //进行提交验证
                    
                    $aSecurityQuestion[] = array('qu'=>$qId,'ans'=>$qAns);
                    $aSecurityQuestion[] = array('qu'=>$qId2,'ans'=>$qAns2);
                    $status = $this->_accountsecurity->checkRightSecurityQuestion($aSecurityQuestion,count($aSecurityQuestion));
                    if($status !== TRUE){
                    	$this->addErr($status);
                    }
                    if($this->isErrFree()){
                        $result["isSuccess"] = "1";
                        //用户可以重置密码
                        $this->_sessionlogin->resetPwd = 1;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
					die;
                }
                
            break;
            case 4:
                //安全密码
                //如果act有值则认为是ajax提交
                $act = $this->_request->getParam ( "act" );
                
                if(!empty($act)){
                    $result = array();
                    $safePwd = $this->_request->getPost ( "safepwd" );
                    
                    $this->_valid->pwd->isValid($safePwd,"json");
                    
                    
                    $this->_login->verifySafePwd($safePwd);
                    $this->appendErrFrmCls($this->_login);
                    
                    if($this->isErrFree()){
                        $result["isSuccess"] = "1";
                        //用户可以重置密码
                        $this->_sessionlogin->resetPwd = 1;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
                    die;
                }
                
                
            break;
            case 5:
                //重置密码
                
                $act = $this->_request->getParam ( "act" );
                if(empty($this->_sessionlogin->resetPwd)){
                    $this->_redirect("/login/findpwd");
                }
                
                switch($act){
                	case "chck":
                		$pwd = $this->_request->getPost ( "pwd" );
                        
		                if ( (strlen($pwd) > 20 )||(strlen($pwd) < 6) ) {
		        			$this->addErr("1007");
		        		}
                        
                        if($pwd == $this->_sessionlogin->info['account']){
                        	$this->addErr("102126");
                        }
                        //验证安全密码是否跟登陆密码一样
                        if(encryptWithdrawPasswd($pwd) == $this->_sessionlogin->info["withdrawPasswd"]){
                            $this->addErr("102138");
                        }
                		if($this->isErrFree()){
                			$result["isSuccess"] = "1";
                		} else {
                			$result["isSuccess"] = "0";
                			$err = $this->getErrJson();
                			$result['data'] = $err['errors'][0][1];
                		}
                		echo Zend_Json::encode($result);
                		exit;
                	break;
                    case "reset":
                        $result = array();
                        $pwd = $this->_request->getPost ( "pwd" );
                        
                		if ( (strlen($pwd) > 20 )||(strlen($pwd) < 6) ) {
		        			$this->addErr("1007");
		        		}
                        
                        if($pwd == $this->_sessionlogin->info['account']){
                        	$this->addErr("102126");
                        }
                        //验证安全密码是否跟登陆密码一样
                        if(encryptWithdrawPasswd($pwd) == $this->_sessionlogin->info["withdrawPasswd"]){
                            $this->addErr("102138");
                        }
                        if($this->isErrFree()){
                            $this->_login->resetePwd($pwd);
                            $this->appendErrFrmCls($this->_login);
                        } else {
                        	$result["isSuccess"] = "0";
                        	$err = $this->getErrJson();
                        	$result['data'] = $err['errors'][0][1];
                        	echo Zend_Json::encode($result);
                        	exit;
                        }
                        if($this->isErrFree()){
                            $result["isSuccess"] = "1";
                            
                            //找回密码成功 踢出所有用户 重新登录
                            $this->_sessionTool->truncate($this->_sessionlogin->info['id']);
                            
                            //用户可以重置密码
                            $this->_sessionlogin->__unset("resetPwd");
                        }else{
                        	$result["isSuccess"] = "0";
                        	$err = $this->getErrJson();
                        	$result['data'] = $err['errors'][0][1];
                        }
                        $this->_sessionlogin->unsetAll();
                        
                        echo Zend_Json::encode($result);
						exit;
                    break;
                    
                    default:
                        
                    break;
                }
                
                
            break;
            case 6:
            break;
            case 0:
                $vCode = $this->_request->getPost("vcode");
                if(!empty($vCode)){
                     //ajax返回数组
                    $result = array();
                    //获取验证码
                    //session中验证码为空 跳转首页
                    if(empty($this->_sessionlogin->code)){
                    	//$this->_redirect('/login/findpwd');
                    	$this->addErr("102069");
                    }
            
                    //判断验证码是否正确
                    if($vCode !=  $this->_sessionlogin->code){
                        $this->addErr("1063");
                    }
                    $this->_sessionlogin->__unset("code");
                    $this->_sessionlogin->__unset("resetPwd");
                    $uName = $this->_request->getPost("username");
                    //用户名格式验证
                    $this->_valid->uname->isValid($uName,"json");
                    
                    
                    //如果验证码不正确，直接中断
                    if(!$this->isErrFree()){
                        $this->_sessionlogin->unsetAll();
                        $result = $this->getErrJson();
                        echo Zend_Json::encode($result);
                        die;
                    }
                    
                    /*******开始-用户名有效性验证*********/
                    $uInfo = $this->_login->getUserInfoByName($uName);
                    $this->appendErrFrmCls($this->_login);
                    
                    
                    
                    if(empty($uInfo["body"]['result'])){
                        $this->addErr("1003");
                    }else{
                        $uInfo = $uInfo["body"]["result"];
                    }
                    
                    if($this->isErrFree()){
                        $result["isSuccess"] ="1";
                        $this->_sessionlogin->info = $uInfo;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
                    die;
                    /*******结束-用户名有效性验证*********/
                }
                $this->view->imageurl = '/login/changevcode';
            break;
        }
        $this->view->stp = $stp;
        $this->view->title = '找回密码';
        $this->view->display('default/login/pwdFind.tpl');
    }


/*Wap找回密码
     *URL: /login/forgetpwd
     * 
     */
    public function forgetpwdAction(){
        $stp = intval($this->_request->getParam("stp"));
        
        if(empty($this->_sessionlogin->info["id"])&&( !in_array($stp, array(0,6,2)) ) ){
            $this->_redirect("/login/forgetpwd");
        }
        
        switch($stp){
            case 1:
                
                //是否设置安全密码
                $isSetSafeCode = 1;
                //是否设置安全问题
                $isSetSafeQuest = 1;
                //是否绑定邮箱
                $isBindMail = 1;
                
                if(empty($this->_sessionlogin->info["withdrawPasswd"])){
                    $isSetSafeCode = 0;
                }
                if(empty($this->_sessionlogin->info["quStruc"])){
                    $isSetSafeQuest = 0;
                }
                
                if(empty($this->_sessionlogin->info["emailActived"])){
                    $isBindMail = 0;
                }
                
                $uName = $this->_sessionlogin->info["account"];
                $this->view->uName = $uName;
                // 是否有设置安全密码
                $this->view->isSetSafeCode = $isSetSafeCode;
                
                // 是否有设置安全问题
                $this->view->isSetSafeQuest = $isSetSafeQuest;
                
                // 是否有绑定邮箱
                $this->view->isBindMail = $isBindMail;
                $mail = empty($this->_sessionlogin->info["email"])?"":$this->_sessionlogin->info["email"];
                $mailList = explode("@",$mail);
                $this->view->mail = substr($mailList[0], 0,2)."****".substr($mailList[0], -3)."@".$mailList[1];
                
                //安全问题
                $questList = $this->_accountsecurity->getQuestionListByuId();
                $this->view->questList = $questList;
                $this->view->display('default/login/forgetPwdList.tpl');
            break;
            case 2:
                //邮箱
//                $uId = $this->_sessionlogin->info["id"];
//                if(empty($uId)){
//                    $this->_redirect("/login/findpwd");
//                }
                
                
                //获取用户ID和邮箱验证码
                $act = $this->_request->getParam ( "act" );
                if(!empty($act)&&$act=="mail"){
                    //邮箱验证码
                    $timeout = 86400;
                    $param = array();
                    $param["name"] = $this->_request->getParam ( "name" );
                    $param["email"] = $this->_request->getParam ( "email" );
                    $param["time"] = $this->_request->getParam ( "time" );
                    $param["time"] = number_format($param["time"] ,0,'','');
                    $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                    $mask = $this->_request->getParam ( "BCODE" );
                    
                    
                    //$this->res_add_fail("邮箱验证失败");
                    $this->res_add_url("返回","/login/forgetpwd",false,true);
                    //$this->res_add_url("登陆页面","/login");
                    
                    /************表单验证********/
                    
                    
                    $this->_valid->uname->isValid($param["name"],"json");
                    $this->_valid->isInt->isValid($param["time"],"json");
                    $this->_valid->isWord->isValid($param["chkAct"],"json");
                    $this->_valid->email->isValid($param["email"],"json");
                    
                    if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                        $this->addErr("102051");
                        $this->res_show(true,true,'login');
                    }
                    
                    $this->_mail->verify($mask, $param, 1);
                    $this->appendErrFrmCls($this->_mail);
                    
                    if(empty($this->_sessionlogin->info)){
                        $data = $this->_login->getUserInfoByName( $param["name"] );
                        $this->appendErrFrmCls($this->_login);
                        
                        if($this->isErrFree()){
                            $this->_sessionlogin->info = $data["body"]["result"];
                        }
                    }
                    
                    if($this->isErrFree()){
                        $this->_sessionlogin->resetPwd = 1; //34;
                        $this->_tool->update($this->_sessionlogin->info["account"], 1,getSendTime());
                        $this->redirect("/login/forgetpwd?stp=5");
                    }else{
                        $this->res_show(true,true,'login');
                    }
                    
                }else{
                    $timeout = 0;
                    $uId = $this->_sessionlogin->info["id"];
                    if(empty($uId)){
                        $this->_redirect("/login/forgetpwd");
                    }
                    $email = $this->_sessionlogin->info["email"];
                    
                    //发送邮件
                    
                    $uName = $this->_sessionlogin->info["account"];
                    
                    $param = array();
                    $param["name"] = $this->_sessionlogin->info["account"];
                    $param["email"] = $email;
                    $param["time"] = number_format(getSendTime(),0,'','');
                    $param["chkAct"] = "findPwd";
                    // 发送邮箱
                    $param["BCODE"] = $this->_mail->encrypt($param);
                    /* $this->_mail->sendVerifiedMail("找回登录密码邮件", 
                                                    $email, 
                                                    $this->_sessionlogin->info["account"], 
                                                    "找回密码功能", 
                                                    "重置密码", 
                                                    "/login/findpwd?stp=2&act=mail", 
                                                    $param,
                                                    1); */
                    //通知发送邮件
                    $this->_mail->sendVerifiedMail(
                            $this->_sessionlogin->info['id'],
                            $this->_sessionlogin->info["account"],
                            8,
                            "/login/forgetpwd?stp=2&act=mail",
                            $param,
                            1
                    );
                        
                    
                    
                    
                    
                    $this->view->uName = $uName;
        $emailList = explode("@",$email);
                     $this->view->email = substr($emailList[0], 0,2)."****".substr($emailList[0], -3)."@".$emailList[1];
                    $this->view->display('default/login/findPwdByMail.tpl');
                }
                
            break;
            case 3:
                //安全问题
                if(empty($this->_sessionlogin->info["id"])){
                    $this->_redirect("/login/forgetpwd");
                }
                
                //如果act有值则认为是ajax提交
                $act = $this->_request->getParam ( "act" );
                if(empty($act)){
                    $questList = $this->_accountsecurity->getQuestionListByuId();
                    $this->view->questList = $questList;
                    $this->view->display('default/login/findPwdBySecuQues.tpl');
                }else{
                    $result = array();
                    // 获取安全问题和答案
                    $qId = getSecurityInput($this->_request->getPost ( "questId" ));
                    $qAns = getSecurityInput($this->_request->getPost ( "questAns" ));
                    $qId2 = getSecurityInput($this->_request->getPost ( "questId2" ));
                    $qAns2 = getSecurityInput($this->_request->getPost ( "questAns2" ));
                    
                    /* $this->_valid->isInt->isValid($qId,"json");
                    $this->_valid->safeAns->isValid($qAns,"json");
                    $this->_valid->isInt->isValid($qId2,"json");
                    $this->_valid->safeAns->isValid($qAns2,"json"); */
                    
                    //进行提交验证
                    
                    $aSecurityQuestion[] = array('qu'=>$qId,'ans'=>$qAns);
                    $aSecurityQuestion[] = array('qu'=>$qId2,'ans'=>$qAns2);
                    $status = $this->_accountsecurity->checkRightSecurityQuestion($aSecurityQuestion,count($aSecurityQuestion));
                    if($status !== TRUE){
                        $this->addErr($status);
                    }
                    if($this->isErrFree()){
                        $result["isSuccess"] = "1";
                        //用户可以重置密码
                        $this->_sessionlogin->resetPwd = 1;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
                    die;
                }
                
            break;
            case 4:
                //安全密码
                //如果act有值则认为是ajax提交
                $act = $this->_request->getParam ( "act" );
                
                if(!empty($act)){
                    $result = array();
                    $safePwd = $this->_request->getPost ( "safepwd" );
                    
                    $this->_valid->pwd->isValid($safePwd,"json");
                    
                    
                    $this->_login->verifySafePwd($safePwd);
                    $this->appendErrFrmCls($this->_login);
                    
                    if($this->isErrFree()){
                        $result["isSuccess"] = "1";
                        //用户可以重置密码
                        $this->_sessionlogin->resetPwd = 1;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
                    die;
                }else{
                    $this->view->display('default/login/findPwdBySecuCode.tpl');
                }
          
            break;
            case 5:
                //重置密码
                
                $act = $this->_request->getParam ( "act" );
                if(empty($this->_sessionlogin->resetPwd)){
                    $this->_redirect("/login/forgetpwd");
                }
                
                switch($act){
                    case "chck":
                        $pwd = $this->_request->getPost ( "pwd" );
                        
                        if ( (strlen($pwd) > 20 )||(strlen($pwd) < 6) ) {
                            $this->addErr("1007");
                        }
                        
                        if($pwd == $this->_sessionlogin->info['account']){
                            $this->addErr("102126");
                        }
                        //验证安全密码是否跟登陆密码一样
                        if(encryptWithdrawPasswd($pwd) == $this->_sessionlogin->info["withdrawPasswd"]){
                            $this->addErr("102138");
                        }
                        if($this->isErrFree()){
                            $result["isSuccess"] = "1";
                        } else {
                            $result["isSuccess"] = "0";
                            $err = $this->getErrJson();
                            $result['data'] = $err['errors'][0][1];
                        }
                        echo Zend_Json::encode($result);
                        exit;
                    break;
                    case "reset":
                        $result = array();
                        $pwd = $this->_request->getPost ( "pwd" );
                        
                        if ( (strlen($pwd) > 20 )||(strlen($pwd) < 6) ) {
                            $this->addErr("1007");
                        }
                        
                        if($pwd == $this->_sessionlogin->info['account']){
                            $this->addErr("102126");
                        }
                        //验证安全密码是否跟登陆密码一样
                        if(encryptWithdrawPasswd($pwd) == $this->_sessionlogin->info["withdrawPasswd"]){
                            $this->addErr("102138");
                        }
                        if($this->isErrFree()){
                            $this->_login->resetePwd($pwd);
                            $this->appendErrFrmCls($this->_login);
                        } else {
                            $result["isSuccess"] = "0";
                            $err = $this->getErrJson();
                            $result['data'] = $err['errors'][0][1];
                            echo Zend_Json::encode($result);
                            exit;
                        }
                        if($this->isErrFree()){
                            $result["isSuccess"] = "1";
                            
                            //找回密码成功 踢出所有用户 重新登录
                            $this->_sessionTool->truncate($this->_sessionlogin->info['id']);
                            
                            //用户可以重置密码
                            $this->_sessionlogin->__unset("resetPwd");
                        }else{
                            $result["isSuccess"] = "0";
                            $err = $this->getErrJson();
                            $result['data'] = $err['errors'][0][1];
                        }
                        $this->_sessionlogin->unsetAll();
                        
                        echo Zend_Json::encode($result);
                        exit;
                    break;
                    
                    default:
                        $this->view->display('default/login/resetPwd.tpl');
                    break;
                }
                
                
            break;
            case 6:
                $this->view->display('default/login/updatePwdSuc.tpl');
            break;
            case 0:
                $vCode = $this->_request->getPost("vcode");
                if(!empty($vCode)){
                     //ajax返回数组
                    $result = array();
                    //获取验证码
                    //session中验证码为空 跳转首页
                    if(empty($this->_sessionlogin->code)){
                        //$this->_redirect('/login/findpwd');
                        $this->addErr("102069");
                    }
            
                    //判断验证码是否正确
                    if($vCode !=  $this->_sessionlogin->code){
                        $this->addErr("1063");
                    }
                    $this->_sessionlogin->__unset("code");
                    
                    $uName = $this->_request->getPost("username");
                    //用户名格式验证
                    $this->_valid->uname->isValid($uName,"json");
                    
                    
                    //如果验证码不正确，直接中断
                    if(!$this->isErrFree()){
                        $this->_sessionlogin->unsetAll();
                        $result = $this->getErrJson();
                        echo Zend_Json::encode($result);
                        die;
                    }
                    
                    /*******开始-用户名有效性验证*********/
                    $uInfo = $this->_login->getUserInfoByName($uName);
                    $this->appendErrFrmCls($this->_login);
                    
                    
                    
                    if(empty($uInfo["body"]['result'])){
                        $this->addErr("1003");
                    }else{
                        $uInfo = $uInfo["body"]["result"];
                    }
                    
                    if($this->isErrFree()){
                        $result["isSuccess"] ="1";
                        $this->_sessionlogin->info = $uInfo;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
                    die;
                    /*******结束-用户名有效性验证*********/
                }
                $this->view->imageurl = '/login/changevcode';
                $this->view->display('default/login/forgetPwd.tpl');
            break;
        }
        
    }
    //保存cook
	private function setCookieForJava(){
		
		$id 		 = $this->_sessionlogin->info['id'];
		$account 	 = $this->_sessionlogin->info['account'];
		$user_agent  = $_SERVER['HTTP_USER_AGENT'];
		$currentTime = getSendTime();
		$userInfo  	 = $id.'|'.$account.'|'.$user_agent.'|'.$currentTime;
		
		$aCookie['_frcid'] = base64_encode($userInfo);
		$aCookie[$id.'_Notice_Close'] = '';
		$aCookie[$id.'_Proxy_Close']  = '';
		$aCookie[$id.'_Game_Close']   = '';
		$aCookie[$id.'_Contact_Close']   = '';
		$aCookie[session_name()]   = session_id();
		$time   = -1;
		$domain = getdomain(getWebSiteDomain());
		foreach ($aCookie as $key=>$value){
			setcookie($key,$value,$time,'/',$domain,NULL,TRUE);
		}
	}
	
	//删除Cookie
	private function delCookieForJava(){
		$id = $this->_sessionlogin->info['id'];
		$aCookie['_frcid']         = '';
		$aCookie[$id.'_Notice_Close'] = '';
		$aCookie[$id.'_Proxy_Close']  = '';
		$aCookie[$id.'_Game_Close']   = '';
		$aCookie[$id.'_Contact_Close']   = '';
		$aCookie[session_name()]   = '';
		$time   = strtotime('-1 days');
		$domain = getdomain(getWebSiteDomain());
		foreach ($aCookie as $key=>$value){
			setcookie($key,$value,$time,'/',$domain);
		}
	}
    public function getgainfoAction(){
        $GAInfoArray['userAccount']     = $this->_sessionlogin->info["account"];
        $GAInfoArray['registerDate']    = $this->_sessionlogin->info["registerDate"];
        $GAInfoArray['currentHostTime'] = getSendTime();

        echo $_GET['callback'] . "(" . Zend_Json::encode($GAInfoArray) .")";
        exit;
    }
}
