<?php
  /**
   * In here I place things that are of common use on all my controllers
   * think Database details, if we had set it up in bootstrap then:
   * $this->_db = Zend_Registry('db')
   * and we have database connectivity in all our controllers
   */
	class CustomControllerAction extends Zend_Controller_Action
	{
		protected $_log;
		protected $_err;
		protected $_config;
		protected $_blockmode;
		protected $_module;
		protected $_main;
        protected $_res;
        protected $_valid;
        protected $_sessionlogin;
        protected $_errLibrary;
        protected $_errView;
        protected $_deflogin;
        protected $_admlogin ;
        protected $_urlsfilter;
        protected $_moneyUnit;
        protected $_Acl;
        protected $_aAclArray;
        protected $redis_client;
        protected $_isOldUser;
		public function init()
		{
			$temp_path = '';
			$this->redis_client = Zend_Registry::get('redis_client');
            $this->_valid = new validation();
            $this->_errView = '';
            $this->_moneyUnit = 10000;
			$this->_err = array();
			$this->_res = array("succ"=>array("title"=>"", "content"=>"", "tab"=>"", "redURL"=>array()),"fail"=>array("title"=>"", "content"=>"", "tab"=>"", "redURL"=>array()));
			$this->_log = Zend_Registry::get('logger');
			$this->_config = Zend_Registry::get('config');
			$this->_blockmode = Zend_Registry::get('blockmode');
			$this->_sessionlogin = new Zend_Session_Namespace('datas');
			


			//固话处理
			if (!isset($this->_sessionlogin->initialized)) {
				Zend_Session::regenerateId();
				$this->_sessionlogin->initialized = true;
			}
			//防止会话劫持
			if(!isset($this->_sessionlogin->loginIp)){
				$this->_sessionlogin->loginIp = bindec(decbin(ip2long(get_client_ip())));
			} elseif ($this->_sessionlogin->loginIp !== bindec(decbin(ip2long(get_client_ip())))){
				Zend_Session::regenerateId();
				$this->_sessionlogin->unsetAll();
				Zend_Session::destroy();
				$this->_redirect('/');
			}
			
			
  			$isblock = $this->blockAct ();
            if ($isblock == 0){
                 $this->view->display("default/error/403.tpl");
                  exit();
            }
            else if ($isblock == 2){
                 $this->view->display("default/error/405.tpl");
                 exit();
            }

			$this->view->year=date('Y');
			$this->_errLibrary = new ErrorCode();
			$this->view->path_js = JS_ROOT;
			$this->view->path_img = IMG_ROOT;
                        $this->view->imurl = IM_URL;
			$this->view->dynamicroot = $this->_config->dynamicroot;
			$this->_urlsfilter= Zend_Registry::get ( "urlsfilter" );
			$this->_deflogin = $this->_urlsfilter->default ;
			$this->_admlogin = $this->_urlsfilter->admin ;
			
			$timeout = intval($this->_config->timeout);
			$_frcid = '_frcid';
			//前台
			if($this->_request->getModuleName() == 'default'){
				if($this->_config->ISADMIN === TRUE){
					$this->_redirect($this->_admlogin);
					exit();
				}
				if(isset($this->_sessionlogin->info['id'])){
					$timeout = intval($this->_sessionlogin->info['loginCtrl']['overTime']*60);
					
					//实时判断用户是否被踢出
					/* if(isset($this->_sessionlogin->info['loginOut']) && $this->_sessionlogin->info['loginOut'] == '1'){
						header("Content-Type:text/html;charset=utf-8");
						echo "<script>alert('您账户在其他地方登录,已被踢出!!!');window.location.href='/login/';</script>";
						if(isset($this->_sessionlogin->info)){
							$this->_sessionlogin->unsetAll();
						}exit;
					} */
					
					//更新session 管理关联集合索引
					$redisSet = new Rediska_Key_Set(md5('ANVO'.$this->_sessionlogin->info['id']));
					if(!$redisSet->exists(session_id())){
						$redisSet->add(session_id()); 
					}
					
					/* "姓名|性别|~固定电话|
					 * 手机|邮箱|地址|
					 * ~公司名|~MSN|QQ|
					 * 会员ID|会员等级 |" */
					$hjUserData = urlencode($this->_sessionlogin->info['account']).'|'.$this->_sessionlogin->info['sex'].'|'.'|';
					$hjUserData .= $this->_sessionlogin->info['cellphone'].'|'.$this->_sessionlogin->info['email'].'|'.'|';
					$hjUserData .= '|'.'|'.(isset($this->_sessionlogin->info['qqStruc'][0]['qq']) ? $this->_sessionlogin->info['qqStruc'][0]['qq'] : '').'|';
					$hjUserData .= $this->_sessionlogin->info['id'].'|'.$this->_sessionlogin->info['vipLvl'].'|';
					$this->view->hjUserData = $hjUserData;
				}
				//前台独有属性配置
				$this->view->game_server = getGameDomain(getWebSiteDomain());
				$this->view->im_server = getFrontImDomain(getWebSiteDomain());
				$this->view->ptgame_server = getPtGameDomain(getWebSiteDomain());
				$this->view->wggame_server = getWgDomain(getWebSiteDomain());
				$this->view->displayAvailBal    = isset($this->_sessionlogin->info['availBal']) ? getMoneyFomat(floatval($this->_sessionlogin->info['availBal'])/$this->_moneyUnit,2) : 0;
			} else {
				$_frcid = '_fracid';
				//后台
				if($this->_config->ISADMIN === FALSE){
					$this->_redirect($this->_deflogin);
					exit();
				}
				
				//后台添加权限验证
				if(isset($this->_sessionlogin->info['acls'])){
					$this->_Acl = new Acl(strtolower($this->_request->getControllerName()));
					$this->_aAclArray = $this->_Acl->getAclArray();
					foreach ($this->_aAclArray as $key=>$value){
						if (!is_array($value)) {
							$value = array($value);
						}
						$value = array_intersect($value, $this->_sessionlogin->info['acls']);
						if(count($value)<=0){
							continue;
						}
						$this->_aAclArray[$key] = $value;
					}
				}
				
				//实时判断用户是否被锁定
				if(isset($this->_sessionlogin->info['status']) && $this->_sessionlogin->info['status'] == '1'){
					echo "<script>alert('您账户被锁定,请联系管理员!!!');</script>";
					if(isset($this->_sessionlogin->info)){
	        			$this->_sessionlogin->unsetAll();
	        		}
				}
				$this->view->ptgame_server = getPtAdminDomain(getWebSiteDomain());
			} //END IF
			
			//=======================登录之后使用后台设置的超时时间 给redis重新设置超时时间=======================
			if(isset($this->_sessionlogin->isLogin) && $this->_sessionlogin->isLogin ==1 && $timeout>0){
                // 201506 新增防止劫持
                if ( $this->_sessionlogin->secur !== md5($_SERVER['HTTP_USER_AGENT']."_".$_SERVER['HTTP_X_FORWARDED_FOR']) ) {
                    Zend_Session::regenerateId();
                    $this->_sessionlogin->unsetAll();
                    Zend_Session::destroy();
                    header("HTTP/1.1 406 NOT_ACCEPTABLE");
                    exit;
                }
			
				$saveHandler = Zend_Registry::get('saveHandler');
				$saveHandler->setOption('lifetime',$timeout);
				//设置session时间
				$this->_sessionlogin->setExpirationSeconds($timeout);
			}
			//=======================END=========================================================
			
			//=================================更新cookie==========================================
			/* $domain = getdomain(getWebSiteDomain());
			if(session_name() && session_id()){
				setcookie(session_name(),session_id(),time()+$timeout,'/',$domain);
			}
			if(isset($_COOKIE[$_frcid])){
				setcookie($_frcid,$_COOKIE[$_frcid],time()+$timeout,'/',$domain);
			} */
			//==================================更新cookie=========================================
			
			
			
			//==========================================设置JAVAsession共享的属性===========================================================
			if(!isset($this->_sessionlogin->__JAVA['creationTime']) || $this->_sessionlogin->__JAVA['creationTime']<=0){
				$this->_sessionlogin->__JAVA['id'] 				   = session_id();
				$this->_sessionlogin->__JAVA['creationTime'] 	   = getSendTime();
				$this->_sessionlogin->__JAVA['lastAccessedTime']   = getSendTime();
				$this->_sessionlogin->__JAVA['new'] 			   = 1;
			} else {
				$this->_sessionlogin->__JAVA['new'] = 0;
			}
			$this->_sessionlogin->__JAVA['maxInactiveInterval'] = $timeout;
			$this->_sessionlogin->__JAVA['valid'] 			 = 1;
			$this->_sessionlogin->__JAVA['thisAccessedTime'] = getSendTime();
			//===========================================设置JAVAsession共享的属性==========================================================
			
			if ( floor ($this->_sessionlogin->info['registerDate']/1000)< (time() - 90 * 60 * 60 * 24) || $this->_sessionlogin->info['vipLvl'] > 0 ){
				$this->_isOldUser = 1  ;
			}else{
				$this->_isOldUser = 0  ;
			}

			$nologinurl = $this->_request->getControllerName();
			if (!in_array(strtolower($nologinurl), explode(",",$this->_urlsfilter->nologin)))
			{
				if(($this->_request->getModuleName() != 'default' && $this->_request->getControllerName() != 'login')
						|| ($this->_request->getModuleName() != 'admin' && $this->_request->getControllerName() != 'login')
				) {
					$this->vialUrls();
				}
			}
		}
		

		public function  vialUrls(){
			$reUrl =  $_SERVER['REQUEST_URI'];
			if(!empty($reUrl)&&$reUrl!='/')
				$reUrl = "?redirect=".$reUrl;
			else{
				$reUrl = "";
			}
			$nologinurl = $this->_request->getControllerName();
		
			if(!isset($this->_sessionlogin->isLogin) || $this->_sessionlogin->isLogin!==1){
				switch (in_array(strtolower($nologinurl), explode(",",$this->_urlsfilter->nologin))) {
					case False:
						if($this->_request->getModuleName()=='default'){
							//判读请求是否ajax请求
							if(isAjaxRequest()){
								header('requeststatus:timeout');
								echo Zend_Json::encode(array('returnUrl'=>$this->_deflogin.$reUrl));
								exit();
							} else {
								$this->_redirect($this->_deflogin.$reUrl);
								exit();
							}
						}elseif($this->_request->getModuleName()=='admin'){
							if(isAjaxRequest()){
								header('requeststatus:timeout');
								echo Zend_Json::encode(array('returnUrl'=>$this->_admlogin.$reUrl));
								exit();
							} else {
								$this->_redirect($this->_admlogin.$reUrl);
								exit();
							}
							
						}
						break;
				}
			}
		}
		
		public function getErrJson($data = array()){
		    $res = array("errors"=>$this->_err,"data"=>$data);
		    return $res;
		}
		
		/**
		 * 
		 * 添加单条错误信息
		 * @param str $code
		 * @param opt str $msg  如果为空，则自动从错误列表中获取，如果获取失败则自动标注为 "Unknown Error"
		 */
		public function addErr($code, $msg="", $type=""){
		    if(empty($msg)){
		        $val = @ $this->_errLibrary->codeList[$code];
		        $msg = @ $val[0];
				$type =@ $val[1];
		        $msg = (""==$msg)?"Unknown Error":$msg;
		    }
		    $this->_err[] = array($type, $msg, $code);
		}
		
		/**
		 * 
		 * 追加错误 ...
		 * @param arr $errs
		 */
		public function appendErr($errs = array()){
		    $this->_err = array_merge($this->_err, $errs);
		}
		
		/**
		 * 追加Client类里面的错误信息
		 * @param Client $cls
		 * @see ErrorCode
		 */
		public function appendErrFrmCls(& $cls){
		    /* @var $cls Client */
		    $arr = $cls->err_get();
		    while(list($k,$v) = each($arr)){
		        $this->addErr($v[0]);
		    }
		    
		}
		
		/**
		 * 
		 * 是否没有存在错误
		 */
		public function isErrFree(){
		    return empty($this->_err);
		}
		
		public function getParseModule(){
			$this->_module=$this->getRequest()->getModuleName();
			return 	$this->_module;
		}
		
		protected function res_add_succ($title='',$content='',$tab=''){
		    $this->_res["succ"]["title"] = $title;
            $this->_res["succ"]["content"] = $content;
            $this->_res["succ"]["tab"] = $tab;
		}
		
	    protected function res_add_fail($title='',$content='',$tab=''){
		    $this->_res["fail"]["title"] = $title;
            $this->_res["fail"]["content"] = $content;
            $this->_res["fail"]["tab"] = $tab;
		}
		
		protected function res_add_url($title='', $url='', $isSucc = false, $isBtn = false){
		    
		    if($isSucc){
		        $this->_res["succ"]["redURL"][] = array("title"=>$title,"url"=> $url, "isBtn"=>$isBtn);
		    }else{
		        $this->_res["fail"]["redURL"][] = array("title"=>$title,"url"=> $url, "isBtn"=>$isBtn);
		    }
		    
		    
		}
		
		/*
		 * @param array succ = array("title"=>"successTitle", "content"=>"successContent");
		 * @param array fail = array("title"=>"failTitle", "content"=>"failContent");
		 * @param array redURL = array(
    	 * 							 array("title"=>"urlTitle", "url"=>"/controller/action"),
    	 * 							 array("title"=>"urlTitle", "url"=>"/controller/action")
		 * 						)
		 * @param bool isWithLeft = true
		 */
	    public function res_show($isWithLeft = true, $isShowErr = false, $errView=""){
        
	        $moduleName = $this->getRequest()->getModuleName();
	        $succ = $this->_res["succ"];
	        $fail = $this->_res["fail"];
            if(!empty($errView)){
            	$this->_errView = $errView;
            }
	        $this->view->errView = $this->_errView;
            if(empty($this->_err)){
                
                $this->view->msgTitle = $succ["title"];
                $this->view->msgContent = $succ["content"];
                $this->view->redURL = $succ["redURL"];
                $this->view->tab = $succ["tab"];
                
                if($isWithLeft){
                    $this->view->display($moduleName.'/error/withLeft_succ.tpl');
                }else{
                    $this->view->display($moduleName.'/error/left_succ.tpl');
                }
                
            }else{
            	
                $this->view->msgTitle = $fail["title"];
                $this->view->msgContent = $fail["content"];
                $this->view->redURL = $fail["redURL"];
                $this->view->tab = $fail["tab"];
                
                if($isShowErr){
                    $errList = array();
                    foreach($this->_err as $err){
                        $errList[] = $err[1];
                    }
                    $this->view->errList = $errList;
                }
                
                if($isWithLeft){
                    $this->view->display($moduleName.'/error/withLeft_fail.tpl');
                }else{
                    $this->view->display($moduleName.'/error/left_fail.tpl');
                }
            }
            die;
        }
		public function getdate()
		{
			$date=date('Y-m-d');
			$this->view->date=$date;
			$first=date('w',strtotime(date('Y-m-1')));
			$today=date('j');
			$end=date('t');
			$monarray=array();
			$j=1;
			for($i=1;$i<36;$i++){
				if($i%7==0&&$i>0) 
					$monarray[$i]['h']=1;		 
				else
					$monarray[$i]['h']=0;	
					if($first<=$i&&$j<=$end){	
						$monarray[$i]['d']=$j;
	
						if($j==$today){
							$monarray[$i]['t']=1;
						}
						else{
							$monarray[$i]['t']=0;	
						}
					$j++;
					}
				else
					$monarray[$i]['d']="";
	
	
			}
		$this->view->daylist=$monarray;
		}
		protected function _debug($str){
			$this->_log->debug($str);
		}
		
		/* public function transferPic(Zend_File_Transfer $upload, $filename, $upName){
			$upload->addFilter('Rename',array('target'=>SITE_ROOT.'/application/upload/images/'.$filename,'overwrite'=>true));
			$re=$upload->receive( $upName );
			return $re;
		} */
		
		public function decodeJson( $data , $type){
			
			return $result  = Zend_Json::decode ( $data,  $type );
		}
		
		public function log($message,$priority = Zend_log::ERR )
		{
			$logger = Zend_Registry::get('logger');
			$logger->setEventItem('modules', $this->_request->getModuleName() );
			$logger->setEventItem('controller', $this->_request->getControllerName() );
			$logger->setEventItem('action', $this->_request->getActionName() );
			$logger->log($message, $priority);
		}
		
		//產生公私鑰 並 將私鑰儲存在redis中
		public function encrypt_RSA($redis_key)
		{
			//產生公私鑰
			$config = array(
				"digest_alg" => "sha512",
				"private_key_bits" => 2048,
				"private_key_type" => OPENSSL_KEYTYPE_RSA,
			);
			
			$res = openssl_pkey_new($config);
			//取得私鑰
			openssl_pkey_export($res, $privKey);
			//儲存私鑰 (8小時內有效)
			$this->redis_client->set($redis_key, $privKey, 8*60*60);
			$details = openssl_pkey_get_details($res);
			//要傳給 JS 加密的
			$rsa_n = to_hex($details['rsa']['n']);
			$rsa_e = to_hex($details['rsa']['e']);
			return array(
				'rsa_n' => $rsa_n,
				'rsa_e' => $rsa_e,
			);
		}
		
		//於redis取出私鑰並解密資料
		public function decrypt_RSA($redis_key, $data)
		{
			//進行解密流程
			$privKey = $this->redis_client->get($redis_key);	//取得私鑰
			if(empty($privKey))	//取得私鑰失敗
			{
				return false;
			}
			$key_handle = openssl_pkey_get_private($privKey);	//載入私鑰
			//對 data 解密
			if(!openssl_private_decrypt(pack('H*', $data), $decrypted_data, $key_handle))
			{
				return false;	//解密失敗
			}
			return $decrypted_data;
		}

		public function blockAct() 
		{
			$returnv = 1;
			$gi = geoip_open(SITE_ROOT.'/geoip/GeoIP.dat',GEOIP_STANDARD);
			$banned= file ($this->_config->blockfilepath.'/block.txt' , FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
			$white= file ($this->_config->blockfilepath.'/white.txt' , FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
			$country_code = geoip_country_code_by_addr($gi, get_client_ip ());
			$country_name = geoip_country_name_by_addr($gi, get_client_ip ());



		foreach ($white as $valuew) {
				 list($begin, $end) = explode('/', $valuew);  
               if (isInnerCIDR (get_client_ip () ,$begin, $end) == true){
              		$returnv = 1; 
        			return $returnv;		
              	}
            }	
 			foreach ($banned as $valueb) {
 				list($begin, $end) = explode('/', $valueb);  
                if (isInnerCIDR (get_client_ip () ,$begin, $end) == 1){
              		$returnv = 2; // return 405
        			return $returnv;		
              	}
             }


		//	if (in_array (get_client_ip (), $white )){
       // 		$returnv = 1;
        //		return $returnv;
		//	}
	//		if (in_array (get_client_ip (), $banned )){
        //		$returnv = 2; // return 405
        //		return $returnv;
		//	}
		

		 	foreach ($this->_blockmode as $key => $val){
		    	if ($country_code == $key && $val == 1){
		    		$returnv = 1;
		    		return $returnv;
		    	}else if ($country_code == $key && $val == 0){
		    		$returnv = 0;
		    		return $returnv;	
		    	}
		    }  
		   
			if ($this->_blockmode->blockmode == 0){
				$returnv = 0;	// reutrn 403
				return $returnv; 	
			}
			return $returnv;

		}


	}     
class validation{}
