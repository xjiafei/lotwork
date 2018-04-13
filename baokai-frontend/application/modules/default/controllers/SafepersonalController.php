<?php
require_once 'application/models/default/Mail.php';

class Default_SafepersonalController extends Fundcommon {
    protected $_accountsecurity;
    protected $_mem;
    protected $_mail;
    protected $_valid;
    protected $_tool;
    protected $_securitycode;
    protected $_mobileToken;
    protected $_seSafeCodeEditSetp;
    protected $_sessionTool;
    
    public function init() {
        parent::init ();
        $this->_accountsecurity = new AccountsSecurity ();
        $this->_mem = new Member();
        $this->_valid->email = new MyValid_Email ( $this );
        $this->_valid->pwd = new MyValid_Password ( $this );
        $this->_valid->safeAns = new MyValid_SafeAnswer ( $this );
        $this->_valid->slogan = new MyValid_Slogan( $this );
        $this->_valid->isInt = new MyValid_Int ( $this );
        $this->_valid->isWord = new MyValid_Word( $this );
        $this->_tool = new Tool();
        $this->_mail = new Mail();
        $this->_errView = "ucenter";
        $this->_securitycode = new Rediska_Key_Hash(md5('securitycode'.$this->_sessionlogin->info['id']));//宝开安全中心验证码状态
        $this->_mobileToken = new MobileToken();
        $this->_sessionTool = new SessionTool($this->_config);
		$this->_clientObject = new Client();
    }
    
    public function indexAction() {
        $this->_redirect('/safepersonal/safecenter');
    }
	
	//个人资料
    public function personalinfoAction() {
        $act = getSecurityInput($this->_request->getParam("act"));
        $birth="";
        if(!empty($act)){
            
            $this->res_add_succ("您的个人资料设置成功！","","个人资料");
            $this->res_add_fail("您的个人资料设置失败!","","个人资料");
            $this->res_add_url("个人资料","/safepersonal/personalinfo", true);
            $this->res_add_url("安全中心","/safepersonal/safecenter", true);
            
            $mail = strtolower($this->_request->getPost("mail"));
            $sex = getSecurityInput($this->_request->getPost("sex"));
            $cellphone = getSecurityInput($this->_request->getPost("cellphone"));
			$nickname = getSecurityInput($this->_request->getPost("nickname"));
			$nickImg = getSecurityInput($this->_request->getPost("headNumber"));
			//print_r($this->_request->getPost("headNumber"));
			//exit;
            if($mail && !filter_var($mail, FILTER_VALIDATE_EMAIL)){//修改email格式错误
            	$this->_valid->email->isValid($mail,"showNoLeft", true);
            	$this->appendErrFrmCls($this->_mail);
            	if(!$this->isErrFree()){
            		$this->res_show(true,true);
            	}
            }
           
            //获取生日
            $tmp = $this->_request->getPost("bir");
            if(!empty($tmp["year"])&&!empty($tmp["month"])&&!empty($tmp["day"])){  //  &&(empty($this->_sessionlogin->info["birthday"]))
            $tmp = getSecurityInput($tmp["year"])."-".getSecurityInput($tmp["month"])."-".getSecurityInput($tmp["day"]);
            } else{
                $tmp = "";
            }
            if(!empty($tmp)){
                $birth = getQueryStartTime($tmp);
            }
            
            
            //表单验证
            
            if(!in_array($sex, array(0,1))){
                $this->addErr("1020");
            }
            //做是否已经存在邮箱验证
            if(!empty($mail) && $this->_sessionlogin->info["email"]!=$mail){
                $this->_valid->email->isValid($mail,"showNoLeft", true);
                //验证邮箱是否存在
                $this->_mail->isExist($mail);
                $this->appendErrFrmCls($this->_mail);
                if(!$this->isErrFree()){
                    $this->res_show(true,true);
                }
            }
            if(!empty($cellphone)){
                $this->_valid->isInt->isValid($cellphone, "showNoLeft", true);
            }
            if(!empty($birth)){
                $this->_valid->isInt->isValid($birth, "showNoLeft", true);
            }
            
            //获取QQ
            $tmpname = $this->_request->getPost("qqnickname");
            $tmpnum = $this->_request->getPost("qqnumber");
            $qq = array();
            $tmpcount = count($tmpname);
            
            if($tmpcount>5){
                $this->addErr("1020");
            }
            
            if(($tmpcount!=0)&&($tmpcount == count($tmpnum))){
                $idx =0;
                for($i = 0; $i<$tmpcount; $i++){
                    if(!empty($tmpnum[$i])&&!empty($tmpname[$i])){
                    	if($tmpname[$i] !='备注' && $tmpnum[$i]!='Q号'){
	                        $this->_valid->isInt->isValid($tmpnum[$i], "showNoLeft", true);
	                        $qq[$idx++] = array("qq"=> getSecurityInput($tmpnum[$i]),"nickName"=>getSecurityInput($tmpname[$i]));
                    	}
                    }
                }
            }
            //保存
            $this->_accountsecurity->savePersonInfo($mail,$sex,$cellphone,$birth,$nickname,$nickImg,$qq);
            $this->appendErrFrmCls($this->_accountsecurity);
            if($this->isErrFree()){
            	$sessionData = array();
                if($this->_sessionlogin->info["emailActived"]!=1 && !empty($mail)){
                    $this->_sessionlogin->info["email"] = $mail;
                    $sessionData['email'] = $mail;
                }
                if(isset($sex)){
                    $this->_sessionlogin->info["sex"] = $sex;
                    $sessionData['sex'] = $sex;
                }
                if(!empty($cellphone)){
                    $this->_sessionlogin->info["cellphone"] = $cellphone;
                    $sessionData['cellphone'] = $cellphone;
                }
                if(!empty($birth)){
                    $this->_sessionlogin->info["birthday"] = $birth;
                    $sessionData['birthday'] = $birth;
                }
				if(!empty($nickname)){
                    $this->_sessionlogin->info["nickname"] = $nickname;
                    $sessionData['nickname'] = $nickname;
                }
				if(!empty($nickImg)){
                    $this->_sessionlogin->info["headImg"] = $nickImg;
                    $sessionData['headImg'] = $nickImg;
                }
                if(!empty($qq)){
                    $this->_sessionlogin->info["qqStruc"] = $qq;
                    $sessionData['qqStruc'] = $qq;
                }
                if(count($sessionData)>0){
                	$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                }
            }
            
            
            $this->res_show(true,true);
        }else{
            $sex = empty($this->_sessionlogin->info["sex"])?"":$this->_sessionlogin->info["sex"];
            $mail = empty($this->_sessionlogin->info["email"])?"":$this->_sessionlogin->info["email"];
            $cellphone = empty($this->_sessionlogin->info["cellphone"])?"":$this->_sessionlogin->info["cellphone"];
			$nickname = empty($this->_sessionlogin->info["nickname"])?"":$this->_sessionlogin->info["nickname"];
			$nickImg = empty($this->_sessionlogin->info["headImg"])?"":$this->_sessionlogin->info["headImg"];
            $qq = empty($this->_sessionlogin->info["qqStruc"])?"":$this->_sessionlogin->info["qqStruc"];
            $username = empty($this->_sessionlogin->info["account"])?"":$this->_sessionlogin->info["account"];
            if(!empty($this->_sessionlogin->info["birthday"])){
                $birth = explode("-", date("Y-m-d", getSrtTimeByLong($this->_sessionlogin->info["birthday"])));
            }
            
            $validMoney = empty($this->_sessionlogin->info["availBal"])?0:getMoneyFomat($this->_sessionlogin->info["availBal"]/$this->_moneyUnit,2);
            $hibMoney = empty($this->_sessionlogin->info["freezeBal"])?0:getMoneyFomat($this->_sessionlogin->info["freezeBal"]/$this->_moneyUnit,2);
        }
        /**
         * sex 为0时是女， sex 为1 时是男 *
         */
        $this->view->tip = empty($this->_sessionlogin->info["emailActived"])?1:!$this->_sessionlogin->info["emailActived"];
        /**
         * tip 为1 时为未验证状态 为0 时为验证状态 *
         */
        $this->view->sex = $sex;
        if(!empty($mail)){
            $vmail = (empty($this->_sessionlogin->info["email"]))?$mail:substr_replace($mail, '****', 2, 4);
        } else {
        	$vmail = $mail;
        }
        if(!empty($cellphone)){
        	$vcellphone = (empty($this->_sessionlogin->info["cellphone"]))?$cellphone:substr_replace($cellphone, '****', 3, 4);
        } else {
        	$vcellphone = $cellphone;
        }
		if(!empty($nickname)){
        	$vnickname = (empty($this->_sessionlogin->info["nickname"]));
        } else {
        	$vnickname = $nickname;
        }
        if(count($qq)>0 && !empty($qq)){
        	foreach ($qq as $key=>$value){
        		$vqq[$key]['nickName'] = $value['nickName'];
        		$vqq[$key]['qq'] = (empty($value['qq']))?$value['qq']:substr_replace($value['qq'], '***', 2, 3);
        	}
        } else {
        	$vqq = $qq;
        }
        
        $this->view->mail = strtolower($mail); //$mail;
        $this->view->cellphone = substr_replace ( "*", $cellphone, 0 ,3);//$cellphone;
		$this->view->nickname = $nickname; //$nickname;
		$this->view->nickImg = $nickImg; //$nickname;
        $this->view->vmail = $vmail; //$mail;
        $this->view->vcellphone = $vcellphone;// substr_replace ( "*", $cellphone, 0 ,3);//$cellphone;
		//$this->view->vnickname = $vnickname;// substr_replace ( "*", $cellphone, 0 ,3);//$cellphone;
        $this->view->username = $username;
        $this->view->qq = $qq;
        $this->view->vqq = $vqq;
        
//         $this->view->path_img = "../../";
        
        $this->view->validMoney = $validMoney;
        $this->view->hibMoney = $hibMoney;
        // 循环固定年份
        $this->view->year_min = 1900;
        $this->view->year_num = intval ( date ( 'Y' ) ) - 1900 + 1;
        $this->view->birth = $birth;
        $this->view->display ( 'default/ucenter/security/personalInfo_edit.tpl' );
    }
    
    /**
     * 安全中心
     * @URL /safepersonal/safecenter
     */
    public function safecenterAction() {
        $date = "";
        $date = @getSrtTimeByLong($this->_sessionlogin->info["lastLoginDate"]);
        $date = empty($date)?"用户还没有登录过":date("Y年m月d日 H:i:s",$date);
        $location ="";
        $location = empty($this->_sessionlogin->info["lastArea"])?"":$this->_sessionlogin->info["lastArea"];
        $this->view->lastLoginTime = $date;
        $this->view->lastLoginLocat = $location;
        
        //是否设置安全密码
        $isSetSafeCode = 1;
        //是否设置安全问题
        $isSetSafeQuest = 1;
        //是否绑定邮箱
        $isBindMail = 1;
        //是否设置了预设留言
        $isSetSlogan = 1;
        //是否绑定宝开安全中心令牌
        $isBindSecCard = 1;
        
        if(empty($this->_sessionlogin->info["withdrawPasswd"])){
            $isSetSafeCode = 0;
            $this->_sessionlogin->info["securityReturnUrl"]='/safepersonal/safecenter/';
            $this->_sessionlogin->info["securityReturnTitle"]='安全中心';
        }
        if(empty($this->_sessionlogin->info["quStruc"])){
            $isSetSafeQuest = 0;
        }        
        if(empty($this->_sessionlogin->info["emailActived"])){
            $isBindMail = 0;
        }
        if(empty($this->_sessionlogin->info["cipher"])){
            $isSetSlogan = 0;
        }
        if(!(isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']))){
        	$isBindSecCard = 0;
        }
        // 是否有设置安全密码
        $this->view->isSetSafeCode = $isSetSafeCode;
        
        // 是否有设置安全问题
        $this->view->isSetSafeQuest = $isSetSafeQuest;
        
        // 是否有绑定邮箱
        $this->view->isBindMail = $isBindMail;
        
        // 是否有设置预留信息
        $this->view->isSetSlogan = $isSetSlogan;
        // 是否绑定手机安全令牌
        $this->view->isBindSecuritSerial = $isBindSecCard;
        
        // 安全等级 高：300 中：200 低：100
        $this->view->safeLevel = 100 * ($isSetSafeCode + $isSetSafeQuest + $isBindMail + $isSetSlogan + $isBindSecCard);
        
        
        $maskEmail = $this->_sessionlogin->info["email"];
        if(!empty($maskEmail)){
            $maskEmail = substr_replace($maskEmail, '****', 2, 4);
        }
        $this->view->email = $maskEmail; // 如果isBindMail为1
        $this->view->slogan = empty($this->_sessionlogin->info["cipher"])?"":$this->_sessionlogin->info["cipher"];
        $serialNumber = isset($this->_sessionlogin->info['serialNumber']) ? $this->_sessionlogin->info['serialNumber'] : '';
        if($serialNumber){
        	$serialNumber = substr($serialNumber, 0,2).'**-****-****-**'.substr($serialNumber, -2);
        	$this->view->sercuritySerilizeNumber = $serialNumber;
        }
        $this->view->display ( 'default/ucenter/security/securityCenter_view.tpl' );
    }
    
    /**
     * 设置安全密码
     */
	public function safecodesetAction() {
		if (!empty($this->_sessionlogin->info["withdrawPasswd"])) {
			$this->_redirect ( "/safepersonal/safecenter/" );
			exit;
		}
		
		//取得玩家編號
		$user_id = $this->_sessionlogin->info['id'];
		$action = $this->_request->getParam ( "act" );
		if($action == "smt")	//送出安全密碼
		{
			//進行解密流程
			$safePwd = $this->_request->getPost( "safePwd" );
			$safePwd = $this->decrypt_RSA(md5('safecodeset' . $user_id), $safePwd);
			if(!$safePwd)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			
			$this->res_add_url("设置安全密码","/safepersonal/safecodeset");
			$this->res_add_url("安全中心","/safepersonal/safecenter");
			//表单验证
			$this->_valid->pwd->isValid($safePwd,"showNoLeft", true);
			//验证安全密码是否跟登陆密码一样
			if(encryptLoginPasswd($safePwd) == $this->_sessionlogin->info["passwd"])
			{
				$this->addErr("1074");
				$this->res_show(true, true);
			}
			$this->_mem->saveWithdrawPassword($safePwd);
			$this->appendErrFrmCls($this->_mem);
			if ($this->isErrFree())
			{
				$this->_sessionlogin->info["withdrawPasswd"] = encryptWithdrawPasswd($safePwd);
				//更新所有用户的session
				$sessionData['withdrawPasswd'] = $this->_sessionlogin->info["withdrawPasswd"];
				$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
				unset($sessionData);
			}
			$this->res_add_succ("恭喜您安全密码设置成功！", "请您牢记您的安全密码，为了您的账户和资金安全，请您切勿向他人透露您的安全密码。","设置安全密码");
			$this->res_add_fail("设置失败了");
			$isSetSecuQues = 0;
			if (isset($this->_sessionlogin->info ["securityReturnUrl"]) && $this->_sessionlogin->info ["securityReturnUrl"] != '')
			{
				if($this->_sessionlogin->info ['securityReturnTitle']!='安全中心')
				{
					if(empty($this->_sessionlogin->info['quStruc']))
					{
						$sName = '设置安全问题';
						$sUrl = '/safepersonal/safequestset';
						$isSetSecuQues = 1;
					}
					else
					{
						$sName = $this->_sessionlogin->info ['securityReturnTitle'];
						$sUrl = $this->_sessionlogin->info ["securityReturnUrl"];
					}
				}
				else
				{
					$sName = "去充值";
					$sUrl = "/fund";
					$this->res_add_url("绑定银行卡","/bindcard/",true);
				}
			}
			else
			{
				$sName = "去充值";
				$sUrl = "/fund";
				$this->res_add_url("绑定银行卡","/bindcard/",true);
			}
			if($isSetSecuQues !=1)
			{
				unset($this->_sessionlogin->info ["securityReturnTitle"]);
				unset($this->_sessionlogin->info ["securityReturnUrl"]);
			}
			$this->res_add_url ( $sName, $sUrl, true );
			$this->res_show ( true, true );
		}
		else	//產生頁面
		{
			//產生公私鑰並儲存私鑰
			$res_rsa = $this->encrypt_RSA(md5('safecodeset' . $user_id));
			$this->view->rsa_n = $res_rsa['rsa_n'];
			$this->view->rsa_e = $res_rsa['rsa_e'];
			
			$this->view->title = "设置安全密码";
			$this->view->display ( 'default/ucenter/security/safeCode_add.tpl' );
			exit;
		}
	}

    /**
     * 设置安全问题
     */
    public function safequestsetAction(){

    	if (!empty($this->_sessionlogin->info["quStruc"])) {
    		$this->_redirect ( "/safepersonal/safecenter/" );
    	}
    	
		//取得玩家編號
		$user_id = $this->_sessionlogin->info['id'];
		$action = $this->_request->getParam ( "act" );
		if($action == "smt")	//送出
		{
			//進行解密流程
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('safequestset' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;
			
			// 获取安全问题和答案
			$qId = $post_data["questId"];
			$qAns = $post_data["questAns"];
			$qId2 = $post_data["questId2"];
			$qAns2 = $post_data["questAns2"];
			$qId3 = $post_data["questId3"];
			$qAns3 = $post_data["questAns3"];
			$this->res_add_url("设置安全问题","/safepersonal/safequestset");
			$this->res_add_url("安全中心","/safepersonal/safecenter");
			//表单验证
			$this->_valid->isInt->isValid($qId,"showNoLeft", true);
			$this->_valid->safeAns->isValid($qAns,"showNoLeft", true);
			$this->_valid->isInt->isValid($qId2,"showNoLeft", true);
			$this->_valid->safeAns->isValid($qAns2,"showNoLeft", true);
			$this->_valid->isInt->isValid($qId3,"showNoLeft", true);
			$this->_valid->safeAns->isValid($qAns3,"showNoLeft", true);
	
			$this->_accountsecurity->safeQuestion_set ( $qId, $qAns, $qId2, $qAns2, $qId3, $qAns3);
			$this->appendErrFrmCls($this->_accountsecurity);
			if ($this->isErrFree()) {
				$newqu = array();
				$newqu[] = array("qu"=>$qId,"ans"=>md5(strtolower($qAns)));
				$newqu[] = array("qu"=>$qId2,"ans"=>md5(strtolower($qAns2)));
				$newqu[] = array("qu"=>$qId3,"ans"=>md5(strtolower($qAns3)));
	
				$this->_sessionlogin->info["quStruc"] = $newqu;
	
				//更新所有用户的session
				$sessionData['quStruc'] = $this->_sessionlogin->info["quStruc"];
				$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
				unset($newqu,$sessionData);
			}
	
			$this->res_add_succ("恭喜您安全问题设置成功！",
					"请您牢记您的安全问题和答案，为了您的账户和资金安全，请您切勿向他人透露您的安全问题信息。","设置安全问题");
			$this->res_add_fail("设置失败了");
			
			if (isset($this->_sessionlogin->info ["securityReturnUrl"]) && $this->_sessionlogin->info ["securityReturnUrl"] != '') {
				$sName = $this->_sessionlogin->info ['securityReturnTitle'];
				$sUrl = $this->_sessionlogin->info ["securityReturnUrl"];
			} else {
				$sName = "安全中心";
				$sUrl = "/safepersonal/safecenter";
			}
			unset($this->_sessionlogin->info ["securityReturnTitle"]);
			unset($this->_sessionlogin->info ["securityReturnUrl"]);
			$this->res_add_url ( $sName, $sUrl, true );
			$this->res_show ( true, true );
			exit;
		}
		else	//產生頁面
		{
			//產生公私鑰並儲存私鑰
			$res_rsa = $this->encrypt_RSA(md5('safequestset' . $user_id));
			$this->view->rsa_n = $res_rsa['rsa_n'];
			$this->view->rsa_e = $res_rsa['rsa_e'];
			
    		$this->view->title = "设置安全问题";
    		$this->view->questFullList = $this->_accountsecurity->getQuestionList ();
    		$this->view->display ( 'default/ucenter/security/safeQuest_add.tpl' );
			exit;
		}
    }
    
    /**
     * 修改安全问题
     * @URL /safepersonal/safequestedit
     */
    public function safequesteditAction() {

		$this->res_add_url("修改安全问题","/safepersonal/safequestedit");
		$this->res_add_url("安全中心","/safepersonal/safecenter");
		$this->_seSafeCodeEditSetp = new Rediska_Key(md5('codeEditStep'.$this->_sessionlogin->info['id'])); // 安全中心步骤
		
		//取得玩家編號
		$user_id = $this->_sessionlogin->info['id'];
		$step = $this->_request->getParam ("step");
		$step = (empty($step)) ? 1 : $step;	//若為空則設為1
		if($step == '2')	//驗證舊的安全問題
		{
			//進行解密流程
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('safequestedit' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;
			
			// 获取安全问题和答案
			$qId = $post_data["questId"];
			$ans = $post_data["questAns"];
			$qId2 = $post_data["questId2"];
			$ans2 = $post_data["questAns2"];
			
			//表单验证
			$this->_valid->isInt->isValid($qId,"showNoLeft", true);
			$this->_valid->safeAns->isValid($ans,"showNoLeft", true);
			$this->_valid->isInt->isValid($qId2,"showNoLeft", true);
			$this->_valid->safeAns->isValid($ans2,"showNoLeft", true);
			if(!$this->isErrFree()){
				$this->res_show(true,true);
			}
			$aSecurityQuestion[] = array('qu'=>$qId,'ans'=>$ans);
			$aSecurityQuestion[] = array('qu'=>$qId2,'ans'=>$ans2);
			$status = $this->_accountsecurity->checkRightSecurityQuestion($aSecurityQuestion);
			if($status !== TRUE)
			{
				$this->addErr($status);
			}
			if($this->isErrFree())
			{
				$this->_seSafeCodeEditSetp->setValue(2);
			}
			else
			{
				$this->res_show(true,true);
			}
		}
		else if($step == '3')	//更新安全問題
		{
			//進行解密流程
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('safequestedit' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;
			
			$cnt = $this->_seSafeCodeEditSetp->getValue();
			if(empty($cnt)|| $cnt!=2){
				$this->_redirect("/safepersonal/safequestedit");
			}else{
				$qId = $post_data["questId"];
				$ans = $post_data["questAns"];
				$qId2 = $post_data["questId2"];
				$ans2 = $post_data["questAns2"];
				$qId3 = $post_data["questId3"];
				$ans3 = $post_data["questAns3"];
				
				//表单验证
				$this->_valid->isInt->isValid($qId,"showNoLeft", true);
				$this->_valid->safeAns->isValid($ans,"showNoLeft", true);
				$this->_valid->isInt->isValid($qId2,"showNoLeft", true);
				$this->_valid->safeAns->isValid($ans2,"showNoLeft", true);
				$this->_valid->isInt->isValid($qId3,"showNoLeft", true);
				$this->_valid->safeAns->isValid($ans3,"showNoLeft", true);
				
				$this->_accountsecurity->safeQuestion_edit($qId, $ans, $qId2, $ans2, $qId3, $ans3);
				$this->appendErrFrmCls($this->_accountsecurity);
				$this->_seSafeCodeEditSetp->delete();
				if($this->isErrFree()){
					$newqu = array();
					$newqu[] = array("qu"=>$qId,"ans"=>md5(strtolower($ans)));
					$newqu[] = array("qu"=>$qId2,"ans"=>md5(strtolower($ans2)));
					$newqu[] = array("qu"=>$qId3,"ans"=>md5(strtolower($ans3)));
					$this->_sessionlogin->info["quStruc"] = $newqu;
					
					//更新所有用户的session
					$sessionData['quStruc'] = $this->_sessionlogin->info["quStruc"];
					$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
					unset($newqu,$sessionData);
				}
			}
		}
		
		//輸出頁面
		//產生公私鑰並儲存私鑰
		$res_rsa = $this->encrypt_RSA(md5('safequestedit' . $user_id));
		$this->view->rsa_n = $res_rsa['rsa_n'];
		$this->view->rsa_e = $res_rsa['rsa_e'];
		
		$this->view->step = $step;
		$this->view->title = "修改安全问题";
		$this->view->questFullList = $this->_accountsecurity->getQuestionList ();
		$this->view->questList = $this->_accountsecurity->getQuestionListByuId ();
		$this->view->display ( 'default/ucenter/security/safeCode_edit.tpl' );
    }
    
    /**
     * 修改安全密码
     * @URL /safepersonal/safecodeedit
     */
    public function safecodeeditAction() {
		//取得玩家編號
		$user_id = $this->_sessionlogin->info['id'];
		$isBindSecCard = isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']) ? 1 : 0;
		
		$rsa_data = $this->_request->getPost("rsa_data");
		if(empty($rsa_data))	//為空表示輸出網頁資料
		{
			//產生公私鑰並儲存私鑰
			$res_rsa = $this->encrypt_RSA(md5('safecodeedit' . $user_id));
			$this->view->rsa_n = $res_rsa['rsa_n'];
			$this->view->rsa_e = $res_rsa['rsa_e'];
			
			$this->view->act = $act;
			$this->view->isBindSecCard = $isBindSecCard;
			$this->view->title = intval($this->_request->getParam('issafecode')) == 1 ? '修改安全密码' : '修改登录密码';
			$this->view->display ( 'default/ucenter/security/pwdManage_edit.tpl' );
			exit;
		}
		//進行解密流程
		$rsa_result = $this->decrypt_RSA(md5('safecodeedit' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			$data['isSuccess'] = 0;
			$data['msg'] = $this->getError('2016');
			echo Zend_Json::encode($data);
			exit;
		}
		parse_str($rsa_result, $post_data);

		$act = $post_data['act'];
		$code = $post_data['code'];
		if($act == "1")
		{
			$pwd = base64_decode(str_pad(strtr($post_data["password"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
            $pwdNew = base64_decode(str_pad(strtr($post_data["password_new"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
            $pwdNew2 = base64_decode(str_pad(strtr($post_data["password_new2"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));

		}
		else if($act == "2")
		{
			$pwd = base64_decode(str_pad(strtr($post_data["safePassword"], '-_', '+/'), strlen($post_data["safePassword"]) % 4, '=', STR_PAD_RIGHT));
			$pwdNew = base64_decode(str_pad(strtr($post_data["safePassword_new"], '-_', '+/'), strlen($post_data["safePassword_new"]) % 4, '=', STR_PAD_RIGHT));
			$pwdNew2 = base64_decode(str_pad(strtr($post_data["safePassword_new2"], '-_', '+/'), strlen($post_data["safePassword_new2"]) % 4, '=', STR_PAD_RIGHT));
		}
		else if($act == "3")
		{
			$pwd = base64_decode(str_pad(strtr($post_data["password"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
            $pwdNew = base64_decode(str_pad(strtr($post_data["password_new"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
            $pwdNew2 = base64_decode(str_pad(strtr($post_data["password_new2"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
		}
		//檢查2次的新密碼是否相同
		if($pwdNew != $pwdNew2){
			$data['isSuccess'] = 0;
			$data['msg'] = $this->getError('102065');
			echo Zend_Json::encode($data);
			exit;
		}
		//驗證密碼長度
		//$this->_valid->pwd->isValid($pwd,"showNoLeft", true);
		//$this->_valid->pwd->isValid($pwdNew,"showNoLeft", true);

        switch($act){
            case "1":	//修改密码
                //检查原密码是否正确
                if($this->_sessionlogin->info["passwd"] != encryptLoginPasswd($pwd)){
                	$data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102075');
                	echo Zend_Json::encode($data);
                	exit;
                }
                
                //验证登陆密码是否跟安全密码一样
                if(encryptWithdrawPasswd($pwdNew) == $this->_sessionlogin->info["withdrawPasswd"]){
                    $data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102138');
                	echo Zend_Json::encode($data);
                	exit;
                }
                if($isBindSecCard == '1'){
	                $checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
	                if($checkStatus!==TRUE){
	                	$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
	                	echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
	                	exit;
	                }
                }
                //提交
                $this->_mem->chgePwd($pwdNew);
                $this->appendErrFrmCls($this->_accountsecurity);
                if($this->isErrFree()){
                    $this->_sessionlogin->info["passwd"] = encryptLoginPasswd($pwdNew);
                    unset($pwdNew);
                    
                    //密码修改成功后 把所有用户踢出 重新登录
                    $this->_sessionTool->truncate($this->_sessionlogin->info['id']);
                    
                    $data['isSuccess'] = 1;
                    $data['msg'] = '登录密码修改成功';
                    echo Zend_Json::encode($data);
                    exit;
                }
            break;
            case "2":	//修改安全密码
                if(empty($this->_sessionlogin->info["withdrawPasswd"])){
                	$data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102136');
                	echo Zend_Json::encode($data);
                	exit;
                }
                
                //检查原密码是否正确
                if($this->_sessionlogin->info["withdrawPasswd"] != encryptWithdrawPasswd($pwd)){
                    $data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102001');
                	echo Zend_Json::encode($data);
                	exit;
                }
                
                //验证新的安全密码是否跟登陆密码一样
                if(encryptLoginPasswd($pwdNew) == $this->_sessionlogin->info["passwd"]){
                    $data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('1074');
                	echo Zend_Json::encode($data);
                	exit;
                }
        		if($isBindSecCard == '1'){
	                $checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
	                if($checkStatus!==TRUE){
	                	$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
	                	echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
	                	exit;
	                }
                }
                //提交
                $this->_mem->chgeSafePwd($pwdNew);
                $this->appendErrFrmCls($this->_mem);
                if($this->isErrFree()){
                    $this->_sessionlogin->info["withdrawPasswd"] = encryptWithdrawPasswd($pwdNew);

                    //更新所有用户的session
                    $sessionData['withdrawPasswd'] = $this->_sessionlogin->info["withdrawPasswd"];
                    $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                    unset($pwdNew,$sessionData);
                    
                    
                    $data['isSuccess'] = 1;
                    $data['msg'] = '安全密码修改成功';
                    echo Zend_Json::encode($data);
                    exit;
                }
            break;
            case "3":	//第一次登入修改密码
                //检查原密码是否正确
                if($this->_sessionlogin->info["passwd"] != encryptLoginPasswd($pwd)){
                	$data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102075');
                	echo Zend_Json::encode($data);
                	exit;
                }
                
                //验证登陆密码是否跟安全密码一样
                if(encryptWithdrawPasswd($pwdNew) == $this->_sessionlogin->info["withdrawPasswd"]){
                    $data['isSuccess'] = 0;
                	$data['msg'] = $this->getError('102138');
                	echo Zend_Json::encode($data);
                	exit;
                }
                if($isBindSecCard == '1'){
	                $checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
	                if($checkStatus!==TRUE){
	                	$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
	                	echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
	                	exit;
	                }
                }
                //提交
                $this->_mem->chgePwd($pwdNew);
                $this->_mem->updateUserRecyclePwdFlag($user_id);
                $this->appendErrFrmCls($this->_accountsecurity);
                if($this->isErrFree()){
                    $this->_sessionlogin->info["passwd"] = encryptLoginPasswd($pwdNew);
                    unset($pwdNew);
                    
                    //密码修改成功后 把所有用户踢出 重新登录
                    $this->_sessionTool->truncate($this->_sessionlogin->info['id']);
                    
                    $data['isSuccess'] = 1;
                    $data['msg'] = '登录密码修改成功';
                    echo Zend_Json::encode($data);
                    exit;
                }
            break;
            default:
            break;
        }
    }
    
    /**
     * 找回安全密码
     * @URL /safepersonal/sefecoderetrieve
     */
    public function sefecoderetrieveAction() {
        $timeout = 86400;
        $stp = intval ( $this->_request->getParam ( "stp" ) );
        
        //获取当前用户ID
        $uId = $this->_sessionlogin->info["id"];
        
        //如果是非法参数则退出
        if(0 == $stp){
            $this->_redirect ( "/safepersonal/safecenter/" );
        }
        
        //判断用户是否绑定了 安全问题和邮箱
        $isSetSafeQuest = 1;
        $isBindMail = 1;
        if(empty($this->_sessionlogin->info["quStruc"])){
        	$isSetSafeQuest = 0;
        }
        if(empty($this->_sessionlogin->info["emailActived"])){
        	$isBindMail = 0;
        }
        if(($isSetSafeQuest!=1 || $isBindMail!=$isSetSafeQuest) && $stp!='5' ){
            $stp = 1;
        }
        switch($stp){
            case 1:
                //抱歉，您不能自助找回安全密码
            break;
            case 2:
                //安全问题
                $act = intval ( $this->_request->getParam ( "act" ) );
                
                //如果act不为空则为ajax
                if(!empty($act)){
					//進行解密流程
					$rsa_data = $this->_request->getPost( "rsa_data" );
					$rsa_result = $this->decrypt_RSA(md5('sefecoderetrieve' . $uId), $rsa_data);
					if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
					{
						echo Zend_Json::encode(array('isSuccess'=>'0','msg'=>$this->getError('2016')));
						exit;
					}
					parse_str($rsa_result, $post_data);
					//print_r($post_data);
					//exit;
                	
                    $result = array();
                    $qId = $post_data["questId"];
                    $qAns = $post_data["questAns"];
                    $qId2 = $post_data["questId2"];
                    $qAns2 = $post_data["questAns2"];
                    
                    $this->_valid->isInt->isValid($qId,"showNoLeft", true);
                    $this->_valid->safeAns->isValid($qAns,"showNoLeft", true);
                    $this->_valid->isInt->isValid($qId2,"showNoLeft", true);
                    $this->_valid->safeAns->isValid($qAns2,"showNoLeft", true);
                    
                	$aSecurityQuestion[] = array('qu'=>$qId,'ans'=>$qAns);
                    $aSecurityQuestion[] = array('qu'=>$qId2,'ans'=>$qAns2);
                    $status = $this->_accountsecurity->checkRightSecurityQuestion($aSecurityQuestion,count($aSecurityQuestion));
                    if($status !== TRUE){
                    	$this->addErr($status);
                    }
                    
                    if($this->isErrFree()){
                        $result["isSuccess"]=1;
                        $this->_sessionlogin->findPwd = 1;
                    }else{
                        $result = $this->getErrJson();
                    }
                    echo Zend_Json::encode($result);
					die;
                }
                
				//產生公私鑰並儲存私鑰
				$res_rsa = $this->encrypt_RSA(md5('sefecoderetrieve' . $uId));
				$this->view->rsa_n = $res_rsa['rsa_n'];
				$this->view->rsa_e = $res_rsa['rsa_e'];
                $this->view->questList = $this->_accountsecurity->getQuestionListByuId ();
            break;
            case 3:
                
                if($this->_sessionlogin->findPwd != 1){
                    $this->_redirect ( "/safepersonal/sefecoderetrieve?stp=2" );
                }
                
                $mail = $this->_sessionlogin->info["email"];
                //验证邮箱
                
                $param = array();
                $param["uid"] = $this->_sessionlogin->info["id"];
                $param["email"] = $mail;
                $param["time"] = number_format(getSendTime(),0,'','');
                $param["chkAct"] = "fndSafePwd";
                // 发送邮箱
                $param["BCODE"] = $this->_mail->encrypt($param);
                /* $this->_mail->sendVerifiedMail("找回安全密码邮件", 
                                                $mail, 
                                                $this->_sessionlogin->info["account"], 
                                                "找回安全密码功能", 
                                                "重置安全密码", 
                                                "/safepersonal/sefecoderetrieve?stp=5&act=mail", 
                                                $param,
                                                6); */
                $this->_mail->sendVerifiedMail(
                		$this->_sessionlogin->info['id'],
                		$this->_sessionlogin->info["account"],
                		9,
                		"/safepersonal/sefecoderetrieve?stp=5&act=mail",
                		$param,
                		6
                );
                $this->view->mail = substr_replace($mail, '****', 2, 4);
            break;
            case 4:
                //连接失效
            break;
            case 5:
                //重置密码
                $act = $this->_request->getParam ( "act" );
                
                switch($act){
                    case "mail":
                        $param = array();
                        $param["uid"] = $this->_request->getParam ( "uid" );
                        $param["email"] = $this->_request->getParam ( "email" );
                        $param["time"] = $this->_request->getParam ( "time" );
                        $param["time"] = number_format($param["time"] ,0,'','');
                        $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                        $mask = $this->_request->getParam ( "BCODE" );
                        
                        
                        $this->_valid->isInt->isValid($param["uid"],"showNoLeft", true);
                        $this->_valid->isInt->isValid($param["time"],"showNoLeft", true);
                        $this->_valid->isWord->isValid($param["chkAct"],"showNoLeft", true);
                        $this->_valid->email->isValid($param["email"],"showNoLeft", true);
                        
                        
                        // 进行验证
                        if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                            $this->addErr("102051");
                        }
                        
                        if($this->isErrFree()){
                         	$this->_mail->verify($mask, $param, 6);
                            $this->appendErrFrmCls($this->_mail);
                        }
                        
                        if($this->isErrFree()){
                            $this->_sessionlogin->findPwd = 2;
                            $this->_tool->update($this->_sessionlogin->info['account'],6, getSendTime());
                        } else{
                            //$this->res_add_fail('','','找回安全密码');
                         	$this->res_add_url("返回安全中心","/safepersonal/safecenter",false,true);
                           	$this->res_show(true,true);
                        }
						//產生公私鑰並儲存私鑰
						$res_rsa = $this->encrypt_RSA(md5('sefecoderetrieve' . $uId));
						$this->view->rsa_n = $res_rsa['rsa_n'];
						$this->view->rsa_e = $res_rsa['rsa_e'];
                    break;
                    
                    case "reset":
						$result = array();
						//進行解密流程
						$rsa_data = $this->_request->getPost( "rsa_data" );
						$rsa_result = $this->decrypt_RSA(md5('sefecoderetrieve' . $uId), $rsa_data);
						if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
						{
                        	$result["error"] = "1";
                        	$result["msg"] = $this->getError('2016');
                        	echo Zend_Json::encode($result);
                        	exit;
						}
						parse_str($rsa_result, $post_data);
						//print_r($post_data);
						//exit;

                        $pwd = base64_decode(str_pad(strtr($post_data["pwd"], '-_', '+/'), strlen($post_data["pwd"]) % 4, '=', STR_PAD_RIGHT));
                        $pwd1 = base64_decode(str_pad(strtr($post_data["pwd1"], '-_', '+/'), strlen($post_data["pwd1"]) % 4, '=', STR_PAD_RIGHT));
                        
                        $this->_valid->pwd->isValid($pwd,"json");
                        $this->_valid->pwd->isValid($pwd1,"json");
                        if($pwd1 !== $pwd){
                        	$result["error"] = "0";
                        	echo Zend_Json::encode($result);
                        	exit;
                        }
                        //验证安全密码是否跟登陆密码一样
                        if(encryptLoginPasswd($pwd) == $this->_sessionlogin->info["passwd"]){
                        	$result["error"] = "1";
                        	echo Zend_Json::encode($result);
                        	exit;
                        }
                        $this->_mem->chgeSafePwd($pwd);
                        $this->appendErrFrmCls($this->_mem);
                        if($this->isErrFree()){
                            $result["isSuccess"] = "1";
                            //用户可以重置密码
                            $this->_sessionlogin->__unset("resetPwd");
                            $this->_sessionlogin->info["withdrawPasswd"] = encryptWithdrawPasswd($pwd);
                            
                            //更新所有用户的安全密码
                            $sessionData['withdrawPasswd'] = $this->_sessionlogin->info["withdrawPasswd"];
                            $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                            unset($pwd,$sessionData);
                            
                        }else{
                            $result = $this->getErrJson();
                        }
                        echo Zend_Json::encode($result);die;
                        die;
                    break;
                    
                    default:
                        $this->_redirect("/safepersonal/sefecoderetrieve?stp=2");
                    break;
                }
            break;
            case 6:
                
            break;
        }
        $this->view->stp = $stp;
        $this->view->display ( 'default/ucenter/security/sefeCode_retrieve.tpl' );
    }
    
    /**
     * 绑定邮箱
     * @URL /safepersonal/bindmail
     */
    public function bindmailAction() {
        // 检查邮箱绑定状态(0未绑定, 1已绑定)
        
        $timeout = 86400;
        $step = ($this->_request->getParam ( "step" ) == "") ? 1 : $this->_request->getParam ( "step" );
        $email = strtolower($this->_request->getParam ( "email" ));
        if(!empty($this->_sessionlogin->info['email']) && empty($email)){
        	$email = $this->_sessionlogin->info['email'];
        }
        if ($this->_sessionlogin->info["emailActived"] == 1) {
        	if ($step !='3') {
        		$this->_redirect ( "/safepersonal/safecenter/" );
        	}
        }
        if($step != 1){
            $this->_valid->email->isValid($email,"show", true);
            $this->_mail->isExist($email);
            $this->appendErrFrmCls($this->_mail);
            if(!$this->isErrFree()){
            	if ($this->_sessionlogin->info["emailActived"] == 1) {
	            	$this->res_add_url("返回安全中心","/safepersonal/safecenter",false,true);
            	} else {
            		$this->res_add_url("邮箱绑定","/safepersonal/bindmail",false,true);
            	}
	            $this->res_show(true,true);
            }
        }
        switch ($step) {
        	
            case "2" : // 重新发送
                $param = array();
                $param["uid"] = $this->_sessionlogin->info["id"];
                $param["email"] = $email;
                $param["time"] = number_format(getSendTime(),0,'','');
                $param["chkAct"] = "bindMailVrfy";
                // 发送邮箱
                $param["BCODE"] = $this->_mail->encrypt($param);
                /* $this->_mail->sendVerifiedMail("请验证绑定邮箱操作", 
                                                $email, 
                                                $this->_sessionlogin->info["account"], 
                                                "绑定邮箱功能，绑定邮箱后可增强账号安全性以及通过邮箱找回其他安全信息", 
                                                "确认绑定邮箱", 
                                                "/safepersonal/bindmail?step=3", 
                                                $param,
                                                2); */
                //通知发送邮件
                $this->_mail->sendVerifiedMail(
                		$this->_sessionlogin->info['id'], 
                		$this->_sessionlogin->info["account"], 
                		5, 
                		"/safepersonal/bindmail?step=3", 
                		$param,
                		2,
                		$email
                	);
                break;
             case "3" :
                $timeout = 86400;
                $param = array();
                $param["uid"] = $this->_request->getParam ( "uid" );
                $param["email"] = $this->_request->getParam ( "email" );
                $param["time"] = $this->_request->getParam ( "time" );
                $param["time"] = number_format($param["time"] ,0,'','');
                $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                $mask = $this->_request->getParam ( "BCODE" );
                
                $this->res_add_succ ( "您的邮箱绑定成功！","您绑定的邮箱是" . substr_replace($email, '****', 2, 4) ,"绑定邮箱");
                //如果邮箱没有激活则可以重现绑定邮箱
                if(empty($this->_sessionlogin->info['emailActived'])){
                	$this->res_add_url ( "重新绑定", "/safepersonal/bindmail", false, true );
                } else {
                	$this->res_add_url ( "返回安全中心", "/safepersonal/safecenter",false, true );
                }
                $this->view->title = "邮箱绑定结果";
                //表单验证
                $this->_valid->isInt->isValid($param["uid"],"showNoLeft", true);
                $this->_valid->isInt->isValid($param["time"],"showNoLeft", true);
                $this->_valid->isWord->isValid($param["chkAct"],"showNoLeft", true);
                $this->_valid->email->isValid($param["email"],"showNoLeft", true);
                if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                    $this->addErr("102051");
                    $this->res_show(true,true);
                }
                
                $this->_mail->verify($mask, $param, 2);
                $this->appendErrFrmCls($this->_mail);
                if($this->isErrFree()){
	                $this->_mail->isExist($param["email"]);
	                $this->appendErrFrmCls($this->_mail);
                }
                if($this->isErrFree()){
                    $this->_accountsecurity->bindMail ( $param["email"] );
                    $this->appendErrFrmCls($this->_accountsecurity);
                    if($this->isErrFree()){
                        $this->_sessionlogin->info["email"] = $param["email"];
                        $this->_sessionlogin->info["emailActived"] = 1;
                        
                        //更新所有用户的 email绑定状态
                        $sessionData['email'] = $this->_sessionlogin->info["email"];
                        $sessionData['emailActived'] = $this->_sessionlogin->info["emailActived"];
                        $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                        unset($sessionData);
                        
                        
                        
                        $this->_tool->update($this->_sessionlogin->info["account"],2, getSendTime());
                    }
                }
                $this->res_show( true, true);
                break;
        }
        if($email){
        	$this->view->email = $email ;
        }
        $this->view->step = $step;
        $this->view->title = "绑定邮箱";
        $this->view->display ( 'default/ucenter/security/email_add.tpl' );
    }
    
    /**
     * 修改绑定邮箱
     * @URL /safepersonal/rebindmail
     */
    public function rebindmailAction() {
        //失效时间
        $timeout = 86400;
        //print_r($this->_sessionlogin->info);
        $step = ($this->_request->getParam ( "step" ) == "") ? 1 : $this->_request->getParam ( "step" );
        // 获取用户原有邮件地址
        $oriEmail = $this->_sessionlogin->info["email"];
        switch ($step) {
            case "1" :
                
                $param = array();
                $param["uid"] = $this->_sessionlogin->info["id"];
                $param["email"] = $this->_sessionlogin->info["email"];
                $param["time"] = number_format(getSendTime(),0,'','');
                $param["chkAct"] = "rbidMailFstVrfy";
                // 发送邮箱
                $this->view->oriEmail = substr_replace($oriEmail, '****', 2, 4);
                break;
            case "2" : // 重新发送
                      
                // 重新发送邮件
                $param = array();
                $mask ="";
                $param["uid"] = $this->_sessionlogin->info["id"];
                $param["email"] = $this->_sessionlogin->info["email"];
                $param["time"] = number_format(getSendTime(),0,'','');
                $param["chkAct"] = "rbidMailFstVrfy";
                // 发送邮箱
                $param["BCODE"] = $this->_mail->encrypt($param);
                /* $this->_mail->sendVerifiedMail("请验证更换绑定邮箱操作", 
                                                $this->_sessionlogin->info["email"], 
                                                $this->_sessionlogin->info["account"], 
                                                "更换绑定邮箱功能", 
                                                "完成更换绑定邮箱", 
                                                "/safepersonal/rebindmail?step=3", 
                                                $param,
                                                3); */
                $this->_mail->sendVerifiedMail(
                		$this->_sessionlogin->info['id'],
                		$this->_sessionlogin->info["account"],
                		6,
                		"/safepersonal/rebindmail?step=3",
                		$param,
                		3
                );
                $this->view->oriEmail = substr_replace($oriEmail, '****', 2, 4);
                break;
           
            case "3" : // 验证结果
//                 if(!empty($this->_sessionlogin->remail)){
                    $param = array();
                    $mask ="";
                    $param["uid"] = $this->_request->getParam ( "uid" );
                    $param["email"] = strtolower($this->_request->getParam ( "email" ));
                    $param["time"] = $this->_request->getParam ( "time" );
                    $param["time"] = number_format($param["time"] ,0,'','');
                    $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                    $mask = $this->_request->getParam ( "BCODE" );
                    
                    //表单验证
                    $this->_valid->isInt->isValid($param["uid"],"showNoLeft", true);
                    $this->_valid->isInt->isValid($param["time"],"showNoLeft", true);
                    $this->_valid->isWord->isValid($param["chkAct"],"showNoLeft", true);
                    $this->_valid->email->isValid($param["email"],"showNoLeft", true);
                    
                    // 进行验证
                    if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                        $this->addErr("102051");
                    }
                    if($this->isErrFree()){
	                    $this->_mail->verify($mask, $param, 3);
	                    $this->appendErrFrmCls($this->_mail);
                    }
                    
                    $res="";
                    if($this->isErrFree()){
                        $res = "succ";
                        $oriEmail = $param["email"];
                        $this->_sessionlogin->remail = $oriEmail;
                    }else{
                        $res = "fail";
                        $desc = $this->getErrJson();
                        $this->view->desc = $desc['errors']['0']['1'];
                    }
                /* }else{
                    $res = "succ";
                    $oriEmail = $this->_sessionlogin->remail;
                } */
                
                $this->view->oriEmail = substr_replace($oriEmail, '****', 2, 4);
				$this->view->oriEmailFull = $oriEmail;
                $this->view->res = $res;
                
                break;
            case "4" :
                $newEmail = strtolower($this->_request->getParam ( "newEmail" ));
                if($newEmail == $this->_sessionlogin->info['email']){
                	//$this->res_add_fail("邮箱绑定失败","","设置邮箱");
                	$this->res_add_url("邮箱绑定","/safepersonal/rebindmail");
                	$this->res_add_url("安全中心","/safepersonal/safecenter");
                	$this->addErr('102045');
                	$this->res_show(true,true);
                }
                $this->_valid->email->isValid($newEmail,"showNoLeft", true);
                $this->_mail->isExist($newEmail);
                $this->appendErrFrmCls($this->_mail);
                if(!$this->isErrFree()){
                   // $this->res_add_fail("邮箱绑定失败","","设置邮箱");
                    $this->res_add_url("邮箱绑定","/safepersonal/rebindmail");
                    $this->res_add_url("安全中心","/safepersonal/safecenter");
                    $this->res_show(true,true);
                }
                $this->view->oriEmail = substr_replace($newEmail, '****', 2, 4);
                $this->view->newEmail = $newEmail;
                $param = array();
                $param["uid"] = $this->_sessionlogin->info["id"];
                $param["email"] = $newEmail;
                $param["time"] = number_format(getSendTime(),0,'','');
                $param["chkAct"] = "rbidMailSndVrfy";
                // 发送邮箱
                $param["BCODE"] = $this->_mail->encrypt($param);
                /* $this->_mail->sendVerifiedMail("请验证绑定邮箱操作", 
                                                $newEmail, 
                                                $this->_sessionlogin->info["account"], 
                                                "绑定邮箱功能,绑定邮箱后可增强账号安全性以及通过邮箱找回其他安全信息", 
                                                "完成绑定邮箱", 
                                                "/safepersonal/rebindmail?step=5", 
                                                $param,
                                                4); */
                $this->_mail->sendVerifiedMail(
                		$this->_sessionlogin->info['id'],
                		$this->_sessionlogin->info["account"],
                		7,
                		"/safepersonal/rebindmail?step=5",
                		$param,
                		4,
                		$newEmail
                );
                //旧邮箱验证链接全部失效
                $this->_tool->update($this->_sessionlogin->info["account"],3, getSendTime());
                
                break;
            
            case "5" :
                $param = array();
                $param["uid"] = $this->_request->getParam ( "uid" );
                $param["email"] = strtolower($this->_request->getParam ( "email" ));
                $param["time"] = $this->_request->getParam ( "time" );
                $param["time"] = number_format($param["time"] ,0,'','');
                $param["chkAct"] = $this->_request->getParam ( "chkAct" );
                $mask = $this->_request->getParam ( "BCODE" );
                
                //表单验证
                $this->_valid->isInt->isValid($param["uid"],"showNoLeft", true);
                $this->_valid->isInt->isValid($param["time"],"showNoLeft", true);
                $this->_valid->isWord->isValid($param["chkAct"],"showNoLeft", true);
                $this->_valid->email->isValid($param["email"],"showNoLeft", true);
                
                // 进行验证
                if(number_format(getSrtTimeByLong($param["time"]) ,0,'','') <time()-$timeout){
                    $this->addErr("102051");
                }
                if($this->isErrFree()){
	                $this->_mail->verify($mask, $param, 4);
	                $this->appendErrFrmCls($this->_mail);
                }
                
                if($this->isErrFree()){
                    $this->_accountsecurity->bindMail ( $param["email"] );
                    $this->appendErrFrmCls($this->_accountsecurity);
                    if($this->isErrFree()){
                        $res = "succ";
                        $this->_sessionlogin->info["email"] = $param["email"];
                        
                        //更新所有用户的 email绑定状态
                        $sessionData['email'] = $this->_sessionlogin->info["email"];
                        $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                        unset($sessionData);
                        
                        
                        //新邮箱验证链接全部失效
                        $this->_tool->update($this->_sessionlogin->info["account"],4, getSendTime());
                    }else{
                        $res = "fail";
                        $desc = $this->getErrJson();
                    }
                }else{
                    $res = "fail";
                    $desc = $this->getErrJson();
                }
                $this->view->newEmail = substr_replace($param["email"], '****', 2, 4);
                $this->view->res = $res;
                if(isset($desc)){
	                $this->view->desc = $desc['errors']['0']['1'];
                }
                break;
        }
        $this->view->step = $step;
        $this->view->title = "修改邮箱";
        $this->view->display ( 'default/ucenter/security/email_edit.tpl' );
    }
	
    /**
     * 设置预留信息
     * @URL /safepersonal/savecipher
     */
    public function savecipherAction() {
        
        if(isset($this->_sessionlogin->info["cipher"]) && !empty($this->_sessionlogin->info["cipher"])){
        	$desc = '修改';
        }else{
        	$desc = '设置';
        }
		
		//取得玩家編號
		$user_id = $this->_sessionlogin->info['id'];
		
        if($this->_request->isPost()){
        	
			//進行解密流程
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('savecipher' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			parse_str($rsa_result, $post_data);
        	
        	$cipher = getSecurityInput($post_data["slogan"]);
        	$this->_valid->slogan->isValid($cipher, "showNoLeft", true);
            $this->_accountsecurity->saveCipher ($cipher);
            if($this->isErrFree()){
                $this->_sessionlogin->info["cipher"] = $cipher;
                
                //更新所有用户的 预留验证信息
                $sessionData['cipher'] = $this->_sessionlogin->info["cipher"];
                $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                unset($sessionData);
                
            }
            $this->res_add_succ ( "恭喜您，预留验证信息".$desc."成功！", "您的预留验证信息为<strong class='biglight'>“".$cipher ."”</strong><br/>请您在使用安全登录时，注意查看预留信息是否与您设置的内容一致，避免登录到虚假平台。",$desc."预留验证信息" );
            $this->res_add_url ( "返回安全中心", "/safepersonal/safecenter", true,true );
            $this->res_show ( true );
        }else {
			//產生公私鑰並儲存私鑰
			$res_rsa = $this->encrypt_RSA(md5('savecipher' . $user_id));
			$this->view->rsa_n = $res_rsa['rsa_n'];
			$this->view->rsa_e = $res_rsa['rsa_e'];
			$this->view->isSetted = $desc;
			$this->view->display ( 'default/ucenter/security/selfSlogan_replace.tpl' );
        }
    }

    /**
     * 修改预留信息
     * @URL /safepersonal/modifycipher
     */
    public function modifycipherAction() {
        
        if(isset($this->_sessionlogin->info["cipher"]) && !empty($this->_sessionlogin->info["cipher"])){
        	$desc = '修改';
        }else{
        	$desc = '设置';
        }
        if($this->_request->isPost()){
			//進行解密流程
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('savecipher' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				$this->addErr("2016");
				$this->res_show(true, true);
				exit;
			}
			parse_str($rsa_result, $post_data);
        	$cipher = getSecurityInput($post_data["slogan"]);
        	$this->_valid->slogan->isValid($cipher, "showNoLeft", true);
            $this->_accountsecurity->modifyCipher ($cipher);
            if($this->isErrFree()){
                $this->_sessionlogin->info["cipher"] = $cipher;
                
                //更新所有用户的 预留验证信息
                $sessionData['cipher'] = $this->_sessionlogin->info["cipher"];
                $this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
                unset($sessionData);
            }
            $this->res_add_succ ( "恭喜您，预留验证信息".$desc."成功！", "您的预留验证信息为<strong class='biglight'>“".$cipher ."”</strong><br/>请您在使用安全登录时，注意查看预留信息是否与您设置的内容一致，避免登录到虚假平台。",$desc."预留验证信息" );
            $this->res_add_url ( "返回安全中心", "/safepersonal/safecenter", true,true );
            $this->res_show ( true );
        }else {
			//產生公私鑰並儲存私鑰
			$res_rsa = $this->encrypt_RSA(md5('savecipher' . $user_id));
			$this->view->rsa_n = $res_rsa['rsa_n'];
			$this->view->rsa_e = $res_rsa['rsa_e'];
        	$this->view->isSetted = $desc;
            $this->view->display ( 'default/ucenter/security/selfSlogan_replace.tpl' );
        }
    }
    
    /**
     * 检测登录密码是否正确
     */
    public function checkpwdAction(){
		//進行解密流程
		$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
		$rsa_data = $this->_request->getPost( "rsa_data" );
		$rsa_result = $this->decrypt_RSA(md5('sefecoderetrieve' . $user_id), $rsa_data);
		if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
		{
			$result['error'] = 1;
			$result['msg'] = $this->getError('2016');
			echo Zend_Json::encode($result);
			exit;
		}
		parse_str($rsa_result, $post_data);
    	$pwd = base64_decode(str_pad(strtr($post_data["pwd"], '-_', '+/'), strlen($post_data["pwd"]) % 4, '=', STR_PAD_RIGHT));
    	if($this->_sessionlogin->info['passwd'] ==encryptLoginPasswd($pwd)){
    		$result['error'] = 1;
    	} else {
    		$result['isSuccess'] = 1;
    	}
    	echo Zend_Json::encode($result);
    	exit;
    }
    
    //Ajax验证邮箱是否已经存在
    public function isexistemailAction(){
    	$mail = $this->_request->getPost("mail");
    	if(!empty($mail)){
    		//验证邮箱是否存在
    		if($this->_mail->isExist_V2($mail)){
    			echo Zend_Json::encode(array('status'=>'1'));
    			exit;
    		}
    	}
    	
    	echo Zend_Json::encode(array('status'=>'0'));
    	exit;
    }
    
    /**
     * 绑定手机令牌
     * @URL /safepersonal/bindmobiletoken
     */
    public function bindmobiletokenAction(){
    	
    	//检查是否设置安全信息
    	$data = array(
    			'returnUrl'=> '/safepersonal/bindmobiletoken/',
    			'returnTitle'=>'宝开安全中心',
    			'title'=>'您还没有设置安全验证信息，请设置后再进行绑定。',
    			'content' => '为了保障您的账户安全，请在绑定宝开安全中心之前设置您的安全验证信息。'
    	);
    	$this->checkSecurityPassword($data);
    	if($this->_request->getPost('rsa_data'))
    	{
			//進行解密流程
			$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('bindmobiletoken' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('2016')));
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;

	    	$password = base64_decode(str_pad(strtr($post_data["password"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
	    	$serials  = getSecurityInput($post_data['serials']);
	    	$type 	  = intval(getSecurityInput($post_data['type']));
	    	if(!$serials){
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102100')));
	    		exit;
	    	} else if(!preg_match('/^(?!0+$)\\d{16}$/', $serials)){
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102101')));
	    		exit;
	    	} else if(!$this->_mobileToken->verifySN($serials)){ //测试序列号是否正确
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102108')));
	    		exit;
	    	}
	    	if(!$type){
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102102')));
	    		exit;
	    	}
	    	$status = $this->checkRightSecurityPassword($password);
	    	if($status !== TRUE){
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError($status)));
	    		exit;
	    	}
	    	//检测安全中心序列号是否已经被绑定
	    	$status = $this->_accountsecurity->checkSecurityCardNumber($serials);
	    	if($status){
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102105')));
	    		exit;
	    	}
	    	$param = array(
	    		'userId'=> $this->_sessionlogin->info['id'],
	    		'sercuritySerilizeNumber' => $serials,
	    		'phoneType' => $type
	    	);
	    	$result = $this->_accountsecurity->bindMobileToken($param);
	    	//测试用
	    	if(isset($result['head']['status'])){
	    		if($result['head']['status']=='0'){
	    			$this->_sessionlogin->info['serialNumber'] = $serials;
	    			
	    			//更新所有用户的手机令牌绑定信息
	    			$sessionData['serialNumber'] = $this->_sessionlogin->info["serialNumber"];
	    			$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
	    			unset($sessionData);
	    			
			    	echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'绑定安全中心成功'));
			    	exit;
	    		} else {
	    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError($result['head']['status'])));
	    			exit;
	    		}
	    	} else {
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'绑定安全中心失败'));
	    		exit;
	    	}
    	}
    	else
    	{
    		if(isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber'])){
    			$this->_redirect('/safepersonal/');
    		} else {
				//產生公私鑰並儲存私鑰
				$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
				$res_rsa = $this->encrypt_RSA(md5('bindmobiletoken' . $user_id));
				$this->view->rsa_n = $res_rsa['rsa_n'];
				$this->view->rsa_e = $res_rsa['rsa_e'];
    		
    			$this->view->account = $this->_sessionlogin->info['account'];
    			$this->view->title = '绑定宝开安全中心';
    			$this->view->display ( 'default/ucenter/security/mobiletoken_add.tpl' );
    		}
    	}
    }
    
    //修改绑定 宝开安全中心
    public function modifymobiletokenAction(){
   		$isBindSecCard = isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']) ? 1 : '0';
   	    //if($this->_request->getPost('code') && $isBindSecCard =='1')
   	    if($this->_request->getPost('rsa_data') && $isBindSecCard =='1')
   	    {
			//進行解密流程
			$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('modifymobiletoken' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('2016')));
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;

    		$password = base64_decode(str_pad(strtr($post_data["password"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
    		$serials  = getSecurityInput($post_data['serials']);
    		$code     = getSecurityInput(trim($post_data['code']));
    		$type 	  = intval(getSecurityInput($post_data['type']));
    		if(!$code){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102103')));
    			exit;
    		}
    		//安全中心序列号不能为空
    		if(!$serials){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102100')));
    			exit;
    		} else if(!preg_match('/^(?!0+$)\\d{16}$/', $serials)){
    			//安全中心序列号格式是否正确
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102101')));
    			exit;
    		} else if(!$this->_mobileToken->verifySN($serials)){ // 测试
    			//安全中心序列号是否正确
	    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102108')));
	    		exit;
	    	}
    		if(!$type){
    			//需要选择手机类型
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102102')));
    			exit;
    		}
    		$pwdStatus = $this->checkRightSecurityPassword($password);
    		if($pwdStatus !== TRUE){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError($pwdStatus)));
    			exit;
    		}
    		
    		$checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
    		if($checkStatus!==TRUE){
    			$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
    			exit;
    		}
    		
    		//检测安全中心序列号是否已经被绑定
    		$status = $this->_accountsecurity->checkSecurityCardNumber($serials);
    		if($status){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102105')));
    			exit;
    		}
    		$param = array(
    				'userId'=> $this->_sessionlogin->info['id'],
    				'sercuritySerilizeNumber' => $serials,
    				'sercurityCode' => $code,
    				'phoneType' => $type
    		);
    		$result = $this->_accountsecurity->modifyMobileToken($param);
    		//测试用
    		if(isset($result['head']['status'])){
    			if($result['head']['status']=='0'){
    				$this->_sessionlogin->info['serialNumber'] = $serials;
    				
    				//更新所有用户的手机令牌绑定信息
    				$sessionData['serialNumber'] = $this->_sessionlogin->info["serialNumber"];
    				$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
    				unset($sessionData);
    				
    				echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'修改绑定安全中心成功'));
    				exit;
    			} else {
    				echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError($result['head']['status'])));
    				exit;
    			}
    		} else {
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102107')));
    			exit;
    		}
    	}
    	else
    	{
    		$this->view->account = $this->_sessionlogin->info['account'];
    		if($isBindSecCard=='0'){
    			$this->_redirect('/safepersonal/');
    		} else {
				//產生公私鑰並儲存私鑰
				$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
				$res_rsa = $this->encrypt_RSA(md5('modifymobiletoken' . $user_id));
				$this->view->rsa_n = $res_rsa['rsa_n'];
				$this->view->rsa_e = $res_rsa['rsa_e'];
				
    			$this->view->title = '修改绑定 宝开安全中心';
    			$this->view->display ( 'default/ucenter/security/mobiletoken_edit.tpl' );
    		}
    	}
    }
    
    //解除绑定 宝开安全中心
    public function unbindmobiletokenAction(){
    	$isBindSecCard = isset($this->_sessionlogin->info['serialNumber'])&&!empty($this->_sessionlogin->info['serialNumber']) ? '1' : '0';
    	//if($this->_request->getPost('code') && $isBindSecCard =='1')
    	if($this->_request->getPost('rsa_data') && $isBindSecCard =='1')
    	{
			//進行解密流程
			$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
			$rsa_data = $this->_request->getPost( "rsa_data" );
			$rsa_result = $this->decrypt_RSA(md5('unbindmobiletoken' . $user_id), $rsa_data);
			if(!$rsa_result)	//取得私鑰失敗 或 解密失敗
			{
				echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('2016')));
				exit;
			}
			parse_str($rsa_result, $post_data);
			//print_r($post_data);
			//exit;

    		$password = base64_decode(str_pad(strtr($post_data["password"], '-_', '+/'), strlen($post_data["password"]) % 4, '=', STR_PAD_RIGHT));
    		$code     = getSecurityInput(trim($post_data['code']));
    		$reason   = intval(getSecurityInput($post_data['reason']));
    		if(!$code){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102103')));
    			exit;
    		}
    		if(!$reason){
    			//需要选择手机类型
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102110')));
    			exit;
    		}
    		$pwdStatus = $this->checkRightSecurityPassword($password);
    		if($pwdStatus !== TRUE){
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError($pwdStatus)));
    			exit;
    		}
    		$checkStatus = $this->checkSecurityCode($code,$this->_sessionlogin->info['serialNumber']);
    		if($checkStatus!==TRUE){
    			$msg = str_replace('N', $checkStatus['data'], $this->getError($checkStatus['status']));
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$msg));
    			exit;
    		}
    		$param = array(
    				'userId'=> $this->_sessionlogin->info['id'],
    				'sercurityCode' => $code,
    				'unbindType' => $reason,
    		);
    		//解除绑定安全中心
    		$status = $this->_accountsecurity->unBindMobileToken($param);
    		if($status){
    			$this->_sessionlogin->info['serialNumber'] = '';
    			
    			//更新所有用户的手机令牌绑定信息
    			$sessionData['serialNumber'] = '';
    			$this->_sessionTool->update($this->_sessionlogin->info['id'],$sessionData);
    			unset($sessionData);
    			$this->_securitycode->delete();
    			echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>$this->getError('102111')));
    			exit;
    		} else {
    			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102112')));
    			exit;
    		}
    	} else {
    		$this->view->account = $this->_sessionlogin->info['account'];
    		if($isBindSecCard!='1') {
    			$this->_redirect('/safepersonal/');
    		} else {
				//產生公私鑰並儲存私鑰
				$user_id = $this->_sessionlogin->info['id'];	//取得玩家編號
				$res_rsa = $this->encrypt_RSA(md5('unbindmobiletoken' . $user_id));
				$this->view->rsa_n = $res_rsa['rsa_n'];
				$this->view->rsa_e = $res_rsa['rsa_e'];
				
    			$this->view->title = '解除绑定 宝开安全中心';
    			$this->view->display ( 'default/ucenter/security/mobiletoken_del.tpl' );
    		}
    	}
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
	
	public function checknicknameAction(){
		
		$nickname = getSecurityInput($this->_request->getPost('nickname'));
		$userid = $this->_sessionlogin->info['id'];
		//$nickname = 'kangtest';
		//print_r($nickname);
		//exit;
		//介接API準備
		$data['param']['nickname'] = $nickname ;
		$data['param']['id'] = $userid;
		$aUri['accountsSecurity'] = 'checknickname';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$result =  array(
                            'isSuccess' => '1',
                            'message' => '成功',
							'status' => $res['head']['status'],
                           
                        );
		echo json_encode($result);
	
	}


}
