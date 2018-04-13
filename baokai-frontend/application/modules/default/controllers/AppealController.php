<?php
require_once 'application/models/default/Mail.php';
class Default_AppealController extends CustomControllerAction {
	
	protected $_userappeal,$_accountsecurity,$_Mail,$_member,$_uploadsession,$_sesionTool;
	
	public function init() {
		
		parent::init ();
		
		$this->_accountsecurity = new AccountsSecurity ();
		$this->_mail = new Mail();
		$this->_member = new Member();
		$this->_uploadsession = new Rediska_Key_Hash(md5('ANVO_APPEAL_UPLOAD'.session_id())) ;
		$this->_userappeal = new UserAppeal ();
		$this->_sesionTool = new SessionTool($this->_config);
	}
	
	/*提交账号申诉 */
	public function indexAction() {
		$this->view->title = '用户申诉';
		$this->view->display ( 'default/ucenter/appeal/appeal.tpl' );
	}
	
	public function apsuccessAction() {
		$this->view->title = '申诉结果';
		$this->view->display ( 'default/ucenter/appeal/apsuccess.tpl' );
	}
	
	/* 申诉验证帐号存在不存在 */
	public function accountisvialAction(){
		$isUser = 0;
		$uName = $this->_request->getPost ( 'userName','0000' );
		$res = $this->_member->getUserInfoByName($uName);
		if (isset($res['body']['result']['account']) && $res['body']['result']['account']!=null){
			$isUser = 1 ;
		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode(array('isUser'=>$isUser));
		
	}
	
	/* 4.7.1 帐号申诉请求 */
	public function userappealAction() {
			
			
			$userName 	  = getSecurityInput(strtolower($this->_request->getPost ( 'userName' )));
			$appealType   = getSecurityInput($this->_request->getPost ( 'safeInfo' ));
			$accountName  = getSecurityInput($this->_request->getPost ( 'accountName' ));
			$cardNum 	  = getSecurityInput($this->_request->getPost ( 'cardNum' ));
			$registerArea = getSecurityInput($this->_request->getPost ( 'accountReg' ));
			$loginArea 	  = getSecurityInput($this->_request->getPost ( 'loginArea'));
			$email 		  = getSecurityInput($this->_request->getPost ( 'email' ));
			if(!$userName){
				$this->redirect("/appeal");
				exit;
			}
			$res = $this->_userappeal->checkUserAppeal($userName,$appealType);
			if(isset($res['head']['status']) && $res['head']['status'] =='0'){
				if(count($res['body']['result'])>0){
					foreach ($res['body']['result'] as $value){
						if($value['account'] == $userName){
							echo Zend_Json::encode(array('status'=>'error','data'=>'请勿重复提交账号申诉,您可以联系客服查询申诉进度!'));
							exit;
						}
					}
				}
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'网络错误!'));
				exit;
			}
			if(!isset($this->_uploadsession->pics['idCard0']) || empty($this->_uploadsession->pics['idCard0'])){
				echo Zend_Json::encode(array('status'=>'error','data'=>'请上传身份证扫描件!'));
				exit;
			}
			if(!isset($this->_uploadsession->pics['idCard0']) || empty($this->_uploadsession->pics['idCard1']) || empty($this->_uploadsession->pics['idCard2'])){
				echo Zend_Json::encode(array('status'=>'error','data'=>'请上传已绑定银行卡扫描件!'));
				exit;
			}
		/**
		 * ******************4.7.1 帐号申诉请求*************************
		 */
 		$data = array (
				"body" => array (
						"param" => array (
								"account" =>$userName ,
								"appealType" =>intval($appealType),
								"idCopy" => $this->_uploadsession->pics['idCard0'],
								"cardStruc" => array (
										"name" => $accountName,
										"no" => $cardNum,
										"pic1" => $this->_uploadsession->pics['idCard1'],
										"pic2" => $this->_uploadsession->pics['idCard2'] 
								),
								"registerArea" => $registerArea,
								"loginArea" => $loginArea,
								"email" => strtolower($email)
						),
						
						
						"pager" => array (
								'startNo' => 1,
								"endNo" => 3 
						) 
				) 
		);
 		$result = $this->_userappeal->userAppeal ( $data );
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
	 		$this->_uploadsession->delete();
			echo Zend_Json::encode(array('status'=>'ok','data'=>'恭喜您，账号申诉提交成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'申诉失败!'));
			exit;
		}
	}

	//上传
	public function uploadAction(){

 		$adapter = new Zend_File_Transfer_Adapter_Http();
		$pics = array();
		$upload_dir = $this->_config->upload_dir.'/upload/images/';
        $logger = Zend_Registry::get('logger');
		if (!is_dir($upload_dir)) {
            $logger->log('upload_dir not exists:'.$upload_dir,1);
			echo Zend_Json::encode(array('error'=>'1','data'=>'上传失败,请联系客服!!!'));
			exit;
		}
		$format = array('jpg','png','gif');
		$adapter->addValidator('Extension', false, implode(",", $format));
		$adapter->addValidator('Size', false, 1024 * 2 * 1024);
		$adapter->addValidator('Count',false, array('min' => 1, 'max' => 3) );
		$adapter->addValidator('Size', false, array (
				'min' => '1kB',
				'max' => '2MB'
		));
		$adapter->setDestination($upload_dir);//存放上传文件的文件夹
		$totalUpload = 0;
		$aError = array();
		$errorArray = array(
			'fileExtensionFalse'=>'只能上传JPG,PNG,GIF类型的图片',
			'fileSizeTooBig'=>'文件大小不能超过2MB',
			'fileCountTooFew'=>'文件大小不能超过2MB',
			'fileUploadErrorIniSize'=>'文件大小不能超过2MB',
		);
		$filesInfo = $adapter->getFileInfo();
		if ($adapter->isValid()) {
			try {
				foreach( $filesInfo as $key=> $fileInfo )
				{
					$fname = $fileInfo['name'];
					if($adapter->isUploaded($fname) && $adapter->isValid($fname)){
						$extName = substr($fname, strrpos($fname, '.') + 1);
						$filename= md5(microtime().rand(1000, 9999)).'.'.$extName;//重命名   此处的uniqid 有可能会出现性能问题.
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
					} else {
						$message = $adapter->getErrors();
						$aError[$key] = isset($message[0]) ? $errorArray[$message[0]] : '';
					}
				}
			} catch (Zend_File_Transfer_Exception $e) {
                 $aError[$key] = $e->getMessage();
            }
			if($adapter->hasErrors()){
                $logger->log('upload error1:'.var_export($aError, TRUE),1);
				echo Zend_Json::encode(array('error'=>'1','data'=>$aError));
				exit;
			} else {
				if($totalUpload>0){
					$this->_uploadsession->pics = $pics ;
				}
                $logger->log('upload error1:'.var_export($pics, TRUE),1);
				echo Zend_Json::encode(array('status'=>'1','data'=>$totalUpload));
				exit;
			}
		} else {
            $error_msg = '上传失败。';
            $logger->log('upload view error:'.var_export($adapter->getMessages(), TRUE),1);
            $error = $adapter->getMessage();
            if ( !empty($error) ) {
                foreach ($error as $k=>$er) {
                    if ( array_key_exists($k, $errorArray) ) {
                        $msg[] = $errorArray[$k];
                    }
                }
                $error_msg = sprintf('上传失败，%s', implode(" ", array_unique($msg)));
            }
			echo Zend_Json::encode(array('error'=>'1','data'=>$error_msg));
			exit;
		}
	}
	
	/* 重置安全信息 */
	public function resetsecrityinfoAction() {

		
	    $timeout = 86400;
		$param = array();
		$param["name"] = $this->_request->getParam ( "name" );
		$param["userId"] = $this->_request->getParam ( "userId" );
		$param["time"] = $this->_request->getParam ( "time" );
		$param["time"] = number_format($param["time"] ,0,'','');
		$param["chkAct"] = $this->_request->getParam ( "chkAct" );
		$param["active"] = $this->_request->getParam ( "active" );
		$mask = $this->_request->getParam ( "BCODE" );
		
		if(!$param["chkAct"]){
			
			$this->view->erros = $this->_errLibrary->codeList["1067"][0];
			$this->view->title = '重置安全信息';
			$this->view->display ( 'default/ucenter/appeal/appealrresafeinfofail.tpl' );
			exit;
		}
		
    	if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
            $this->addErr("102051");
        }
        
        if($this->isErrFree()){
            $this->_mail->verify($mask, $param, 5);
            $this->appendErrFrmCls($this->_mail);
            
        }
        if($this->isErrFree()){
            $this->view->userId = $param["userId"];
            //$this->view->name = $param["name"];
            $this->view->questFullList = $this->_accountsecurity->getQuestionList ();
            $this->view->aHours = intval($param["active"])*24;
            $this->view->active = $param["active"];
            $this->view->title = '重置安全信息';
		    $this->view->display ( 'default/ucenter/appeal/appealresafeinfo.tpl' );
        }else{
            //错误跳转？ 
			$this->view->erros = $this->_err[0][1];
			$this->view->title = '重置安全信息';
			$this->view->display ( 'default/ucenter/appeal/appealrresafeinfofail.tpl' );
        }
        
	}
	
	/* 重置安全信息成功 */
	public function resetsecrityinfosuccAction() {
		
		$active = getSecurityInput($this->_request->getPost ( "active",''));
		$userId = getSecurityInput($this->_request->getPost ( "userId",'' ));		
		$passwd = $this->_request->getPost ( "passwd",'' );
		$safewd = $this->_request->getPost ( "withdrawPasswd",'' );
		$questId  = getSecurityInput($this->_request->getPost ( "questId",'' ));
		$questId2 = getSecurityInput($this->_request->getPost ( "questId2",'' ));
		$questId3 = getSecurityInput($this->_request->getPost ( "questId3",'' ));
		$ans1 = getSecurityInput($this->_request->getPost ( "questAns",'' ));
		$ans2 = getSecurityInput($this->_request->getPost ( "questAns2",'' ));
		$ans3 = getSecurityInput($this->_request->getPost ( "questAns3",'' ));
		$data = array (
			"body" => array (
				"param" => array (
						"passwd" =>encryptLoginPasswd($passwd),
						"passwdLvl" =>1,
						"quStruc" => array (array(
								"qu" => $questId,
								"ans" => md5(strtolower($ans1)),
								),
								array(
										"qu" => $questId2,
										"ans" => md5(strtolower($ans2)),
								),
								array(
										"qu" => $questId3,
										"ans" => md5(strtolower($ans3)),
								)
						),
						"withdrawPasswd" => encryptWithdrawPasswd($safewd),
				),
				"pager" => array (
						'startNo' => 1,
						"endNo" => 3
				)
		)
				);
                
		$res = $this->_accountsecurity->savePasswdAndWithdrawPasswordAndSecurityQA($data,$userId);
		if (isset($res['head']['status'])){
			if ($res['head']['status']!= 0){
				$this->view->title = '重置安全信息';
				$this->view->display ( 'default/ucenter/appeal/appealrresafeinfofail.tpl' );
			}else{
				//充值安全信息重置成功 把用户从系统中踢出
// 				$this->updateAppealFreezeUserStatus($userId);
				$this->_sessionlogin->unsetAll();
				$this->_sesionTool->truncate($userId);
				
				if($active!=='0'){ //立即生效跳过
					//冻结用户
					$parm['userid'] = intval($userId) ;
					$parm['range'] = 1 ;
					$parm['method'] = 5 ;
					$parm['memo'] = "系统冻结";
					$parm['freezeAccount'] = "-1" ;
					$tmpuser = $this->_userappeal->freezeuser($parm);
					if(!$tmpuser){
						$this->view->title = '重置安全信息';
						$this->view->display ( 'default/ucenter/appeal/appealrresafeinfofail.tpl' );
						exit;
					}
				}
				
				$aHours = getSecurityInput($this->_request->getPost("aHours"));
				$this->view->title = '重置安全信息';
				$this->view->hours = $aHours;
			    $this->view->display ( 'default/ucenter/appeal/appealresafeinfosucc.tpl' );
			}
		}
	}
	
	/* 填写邮箱 */
	public function entermailAction() {
		$timeout = 86400;
		$param = array();
		$param["name"] = getSecurityInput($this->_request->getParam ( "name" ));
		$param["userId"] = getSecurityInput($this->_request->getParam ( "userId" ));
		$param["time"] = getSecurityInput($this->_request->getParam ( "time" ));
		$param["time"] = number_format($param["time"] ,0,'','');
		$param["chkAct"] = getSecurityInput($this->_request->getParam ( "chkAct" ));
		$param["active"] = getSecurityInput($this->_request->getParam ( "active" ));
		
		if(!$param["chkAct"]){
			$this->view->title = '重置安全邮箱';
			$this->view->erros = $this->_errLibrary->codeList["1067"][0];
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
			exit;
		}
		
		$mask = getSecurityInput($this->_request->getParam ( "BCODE" ));
    	if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
            $this->addErr("102051");
        }
        
        if($this->isErrFree()){
            $this->_mail->verify($mask, $param, 8);
            $this->appendErrFrmCls($this->_mail);
        }
        
        if($this->isErrFree()){
        	$this->view->userId = $this->_request->getParam ( "userId" );
        	$this->view->name = $this->_request->getParam ( "name" );
        	$this->view->time = $param["time"];
        	$this->view->chkAct =$this->_request->getParam ( "chkAct" );
        	$this->view->active = $this->_request->getParam ( "active" );
        	$this->view->BCODE = $mask;
        	$this->view->title = '重置安全邮箱';
            $this->view->display ( 'default/ucenter/appeal/appealrmail.tpl' );
        }else{
            //错误跳转
			$this->view->erros = $this->_err[0][1];
			$this->view->title = '重置安全邮箱';
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
        }
        
	}
	
	/*重置邮箱验证email*/
	public function sendmailAction(){
		
		$userId = getSecurityInput($this->_request->getPost ( "userId" ));
		$email = getSecurityInput(strtolower($this->_request->getPost ( "email" )));
		$name = getSecurityInput($this->_request->getPost ( "name" ));
		$time = getSecurityInput($this->_request->getPost ( "time" ));
		$chkAct = getSecurityInput($this->_request->getPost ( "chkAct" ));
		$active = getSecurityInput($this->_request->getPost ( "active" ));
		$BCODE = getSecurityInput($this->_request->getPost ( "BCODE" ));
		$param = array();
		$param["userId"] = $userId ;
		$param["time"] = $time ;
		$param["email"] = $email;
		$param["name"] = $name;
		$param["chkAct"] = "bindMailVrfy";
		$param["time"] = number_format(getSendTime(),0,'','');
		
		
		if(!$param["email"]){
			$this->view->erros = $this->_errLibrary->codeList["1067"][0];
			$this->view->title = '重置安全邮箱';
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
			exit;
		}

		//验证邮箱是否已经存在
		$this->_mail->isExist($email);
		
		$this->appendErrFrmCls($this->_mail);

		if($this->isErrFree()){
		    // 发送邮箱
    		$param["BCODE"] = $this->_mail->encrypt($param);
    		/* $this->_mail->sendVerifiedMail("绑定邮箱验证",
    				$email,
    				$name,
    				"绑定邮箱验证功能",
    				"确认绑定邮箱",
    				"/appeal/checkmailsucc?",
    				$param,
    				7); */
    		//通知发送邮件
    		$this->_mail->sendVerifiedMail(
    				$userId,
    				$name,
    				28,
    				"/appeal/checkmailsucc?",
    				$param,
    				7,
    				$email
    		);
    		$this->appendErrFrmCls($this->_mail);
			
		}
		if($this->isErrFree()){
		
			$this->view->name = $name ;
			$this->view->email = $email ;
			$this->view->userId = $userId;
			$this->view->time = $time;
			$this->view->chkAct = $chkAct;
			$this->view->active = $active ;
			$this->view->BCODE = $BCODE;
			$this->view->title = '重置安全邮箱';
		    $this->view->display ( 'default/ucenter/appeal/appealrmailcheck.tpl' );
		}else{
		 
		    //错误跳转
			$this->view->erros = $this->_err[0][1];
			$this->view->title = '重置安全邮箱';
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
		}
	}
	
	/*检查验证并绑定邮箱 */
	public function checkmailsuccAction() {
		$timeout = 86400;
		$param = array();
		$param["userId"] = getSecurityInput($this->_request->getParam ( "userId" ));
		$param["time"] = getSecurityInput($this->_request->getParam ( "time" ));
		$param["time"] = number_format($param["time"] ,0,'','');
		$param["email"] = strtolower(getSecurityInput($this->_request->getParam ( "email" )));
		$param["name"] = getSecurityInput($this->_request->getParam ( "name" ));
		$param["chkAct"] = getSecurityInput($this->_request->getParam ( "chkAct" ));

		
		if(!$param["chkAct"]){
			$this->view->erros = $this->_errLibrary->codeList["1067"][0];
			$this->view->title = '重置安全邮箱';
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
			exit;
		}
		
		$mask = getSecurityInput($this->_request->getParam ( "BCODE" ));
		
	    if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
            $this->addErr("102051");
        }
        
		//验证码验证
		if($this->isErrFree()){
			$this->_mail->verify($mask, $param,7);
			$this->appendErrFrmCls($this->_mail);
		}
		
		//验证邮箱是否已经存在
		if($this->isErrFree()){
			if($this->_mail->isExist_V2($param["email"])){
				$this->addErr('1073');
			}
		}
        
		if($this->isErrFree()){
			//绑定邮箱
			$res = $this->_accountsecurity->bindAMail($param["email"],$param["userId"]);
			if (isset($res['head']['status']) && $res['head']['status'] == '0'){
				$this->view->email = $param["email"] ;
				$this->view->title = '重置安全邮箱';
				$this->view->display ( 'default/ucenter/appeal/resetmailsuccess.tpl' );
			}
		}else{
			$aError = $this->getErrJson();
			$this->view->erros = $aError['errors'][0][1];
			$this->view->title = '重置安全邮箱';
			$this->view->display ( 'default/ucenter/appeal/resetmailfofail.tpl' );
			exit;
		}
	}

	function checkemailexistAction(){
		$email = getSecurityInput(strtolower($this->_request->getPost ("email")));
		if(!empty($email)){
			if($this->_mail->isExist_V2($email)){
				echo Zend_Json::encode(array('status'=>1));
				exit;
			}
		}
		echo Zend_Json::encode(array('status'=>0));
		exit;
	}
	
}
