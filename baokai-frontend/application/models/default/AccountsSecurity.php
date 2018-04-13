<?php

class AccountsSecurity extends Client{
    
    /*
     * 构造器
     */
    public     $_data = null ;
    protected $_questList = array();
    protected $_securitypwd;
    public function __construct(){
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
        $this->_questList = array(
                                "1"=>"我的宠物名字？",
                                "2"=>"我最亲密的朋友是？",
                                "3"=>"我最喜欢的演员？",
                                "4"=>"我最喜欢的球星？",
        						"5"=>"我最喜欢的球队？",
        						"6"=>"我最喜欢吃的菜？",
        						"7"=>"我最崇拜的人？",
                            );
    }
    
    public function freezeUser($jdata = null){
        $url = Zend_Registry::get ( "firefog" )->freezeUser->freezeUser;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
        
    }
    
    public function unFreezeUser($jdata = null){
        $url = Zend_Registry::get ( "firefog" )->freezeUser->unFreezeUser;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
         
    }
    
    public  function personalInfo($jdata = null){
        
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->default;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
        
    }
    
    public  function personalInfoSmt($jdata = null){
         
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->personalinfosmt;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
         
    }  

    /**
     *
     * 绑定邮箱
     *
     *
     */
    public  function bindMail($email){
    
        $data = array (
                "body" => array (
                        "param" => array (
                                "email" => strtolower($email) 
                        ),
                        "pager" => array (
                                'startNo' => 13,
                                "endNo" => 3 
                        ) 
                ) 
        );
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->bindmail;
        $this->inRequest ( array_merge($this->_data,$data), $url );
    
    }
    
    /**
     *
     * 申诉绑定邮箱
     *
     *
     */
    public  function bindAMail($email,$userId){
        $this->_data['head']['userId'] = $userId;
        $data = array (
                "body" => array (
                        "param" => array (
                                "email" => strtolower($email) 
                        ),
                        "pager" => array (
                                'startNo' => 13,
                                "endNo" => 3 
                        ) 
                ) 
        );
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->bindmail;
        return $this->inRequest ( array_merge($this->_data,$data), $url );
    
    }
    
    /*设置验证预留信息 */
    public  function saveCipher($cipher){
        $data = array (
                "body" => array (
                        "param" => array (
                                "cipher" => $cipher 
                        ),
                        "pager" => array (
                                'startNo' => 13,
                                "endNo" => 3 
                        ) 
                ) 
        );
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->saveCipher;
        $this->inRequest ( array_merge($this->_data,$data), $url );
    
    }
    
    /*修改验证预留信息 */
    public  function modifyCipher($cipher){
        $data = array (
                "body" => array (
                        "param" => array (
                                "cipher" => $cipher 
                        ),
                        "pager" => array (
                                'startNo' => 13,
                                "endNo" => 3 
                        ) 
                ) 
        );
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->modifyCipher;
        $this->inRequest ( array_merge($this->_data,$data), $url );
    
    }
       
    /* 修改安全问题 */
    public  function safeQuestEdit($qId, $ans, $qId2, $ans2, $qId3, $ans3){
    
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->safequestedit;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
    
    }
    
    /*设置安全问题*/
    public  function safeQuestAdd($qId, $ans, $qId2, $ans2, $qId3, $ans3){
    
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->saveSecurityQA;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
    
    }
    
    /* 按用户名查询用户信息请求 */
    public function queryUserByName($jdata = null){
    
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->queryUserByName;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
    }
    
    /* 4.4.3    安全中心首页个人信息查询 */
    public function queryUserSecurityInfo($jdata = null){
    
        $url = Zend_Registry::get ( "firefog" )->accountsSecurity->queryUserSecurityInfo;
        return $this->inRequest ( array_merge($this->_data,$jdata), $url );
    } 
    
    /*
     * 安全问题检测
     * @param int $qId 第一个问题的ID
     * @param str $ans 第一个问题的答案
     * @param int $qId2 第二个问题的ID
     * @param str $ans2 第二个问题的答案
     */
    public function safeQuestion_check($qId, $ans, $qId2, $ans2){
        
       return;
    }
    
    /*
     * 安全问题检测
     * @param int $qId 第一个问题的ID
     * @param str $ans 第一个问题的答案
     * @param int $qId2 第二个问题的ID
     * @param str $ans2 第二个问题的答案
     * @param int $qId3 第三个问题的ID
     * @param str $ans3 第三个问题的答案
     */
    public function safeQuestion_replace($qId, $ans, $qId2, $ans2, $qId3, $ans3){
        
        
        
       return;
    }
    
     /**
     * 个人资料保存
     * 
     * @param str $pwd 密码
     * @param int $pwdlvl 密码等级
     * @param int $uId 用户Id
     * 
     * @return
     */
    public function savePersonInfo( $mail="", $sex="", $phone="", $birth="",$nickname="",$nickImg="", $qq=array()){
    	
        $params = array();
        if(!empty($mail)){
            $params["email"]=strtolower($mail);
        }
        if(isset($sex)){
            $params["sex"]=$sex;
        }
        if(!empty($phone)){
            $params["cellphone"]=$phone;
        }
        if(!empty($birth)){
            $params["birthday"]=$birth;
        }
		if(!empty($nickname)){
            $params["nickname"]=$nickname;
        }
		if(!empty($nickImg)){
            $params["headImg"]=$nickImg;
        }
        if(!empty($qq)){
            $params["qqStruc"] = $qq;
        }
        $data =array(
            "body" =>array(
                "param" => $params
            )
        );
		
		/*print_r($data);
		exit;*/
		
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->personalinfosmt;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    
    /**
     * 检测安全问题
     * @param unknown $qId
     * @param unknown $ans
     * @param unknown $qId2
     * @param unknown $ans2
     */
    public function checkSafeQuest($qId, $ans, $qId2, $ans2){
        $uAns = array();
        $arr = $this->_sessionlogin->info["quStruc"];
        foreach($arr as $val){
            $uAns[$val["qu"]] = $val["ans"];
        }
//        print_r(array($qId, $ans, $qId2, $ans2));
//        print_r(array($uAns[$qId], md5($ans)));
//        var_dump($uAns[$qId]!=md5($ans));
//        die;
        if(@$uAns[$qId]!=md5(strtolower($ans))){
            $this->err_add("1065");
        }elseif(@$uAns[$qId2]!=md5(strtolower($ans2))){
            $this->err_add("1072");
        }
    }
    
    /**
     *获取所有安全问题的内容和ID 
     *@return arr
     */
    public function getQuestionList(){
        $res = array();
        foreach($this->_questList as $k=>$v){
            $res[] = array("Id"=>$k, "quest"=>$v);
        }
        return $res;
    }
    
    /**
     *获取所有安全问题的内容和ID 
     *@return arr
     */
    public function getQuestionById($id){
        return @$this->_questList[$id];
    }
    
    /**
     *获取ID获取提示问题 
     *@return arr
     */
    public function getQuestionListByuId(){
        $data = $arr = array();
        if(!empty($this->_sessionlogin->info["quStruc"])){
            $arr =  $this->_sessionlogin->info["quStruc"];
        }
        
        foreach($arr as $val){
            $data[] = array("Id"=>$val["qu"],"quest"=>$this->getQuestionById($val["qu"]));
        }
        return $data;
    }
     
    
    public function savePasswdAndWithdrawPasswordAndSecurityQA($jdata = null,$userId){
    	$this->_data["head"]["userId"] = $userId ;
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->savePasswdAndWithdrawPasswordAndSecurityQA;
    	return $this->inRequest ( array_merge($this->_data, $jdata), $uri );
    }
    
    /**
     * 修改安全问题
     * @param arr $data 数据
     * example:
        $data= array(
            array("qu"=>0,"ans"=>"asdf"),
            array("qu"=>0,"ans"=>"asdf"),
            array("qu"=>0,"ans"=>"asdf"),
        );
     * @param int $uId 用户Id
     * @return  
     */
    public function safeQuestion_edit($qId, $ans, $qId2, $ans2, $qId3, $ans3){

        $data =array(
            "body" =>array(
                "param" => array(
                    'quStruc'  =>array(
                                    array("qu"=>$qId,"ans"=>md5(strtolower($ans))),
                                    array("qu"=>$qId2,"ans"=>md5(strtolower($ans2))),
                                    array("qu"=>$qId3,"ans"=>md5(strtolower($ans3))),
                                 )
                ),
                "pager" => array(
                    'startNo' => 1,
                    "endNo"=>10
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->safequestedit;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    /**
     * 设置安全问题
     @param arr $data 数据
     * example:
        $data= array(
            array("qu"=>0,"ans"=>"asdf"),
            array("qu"=>0,"ans"=>"asdf"),
            array("qu"=>0,"ans"=>"asdf"),
        );
     * @param int $uId 用户Id
     * @return 
     */
    public function safeQuestion_set($qId, $ans, $qId2, $ans2, $qId3, $ans3){
    
    	$data =array(
    			"body" =>array(
    					"param" => array(
    							'quStruc'  =>array(
    									array("qu"=>$qId,"ans"=>md5(strtolower($ans))),
    									array("qu"=>$qId2,"ans"=>md5(strtolower($ans2))),
    									array("qu"=>$qId3,"ans"=>md5(strtolower($ans3))),
    							)
    					),
    					"pager" => array(
    							'startNo' => 1,
    							"endNo"=>10
    					)
    			)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->saveSecurityQA;
    	return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
     /**
     * 设置安全问题和密码
     * 
     * @return  
     */
    public function safeQuestionSafeCode_set($qId, $ans, $qId2, $ans2, $qId3, $ans3, $spwd){
        $data =array(
            "body" =>array(
                "param" => array(
                    'quStruc'  =>array(
                                    array("qu"=>$qId,"ans"=>md5(strtolower($ans))),
                                    array("qu"=>$qId2,"ans"=>md5(strtolower($ans2))),
                                    array("qu"=>$qId3,"ans"=>md5(strtolower($ans3))),
                                 ),
                     'withdrawPasswd'=>encryptWithdrawPasswd($spwd),
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->saveSafePwdQuest;
        $result = $this->inRequest ( array_merge($this->_data, $data), $uri );
        return $result;
    }
    
    //绑定宝开安全中心令牌
    public function bindMobileToken($param){
    	$data =array(
    			"body" =>array("param" => $param)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->bindSecurityCard;
    	$result = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	return $result;
    }
    
    //修改绑定宝开安全中心令牌
    public function modifyMobileToken($param){
    	$data =array(
    			"body" =>array("param" => $param)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->updateSecurityCard;
    	$result = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	return $result;
    }
    
    //解除宝开安全中心令牌绑定
    public function unBindMobileToken($param){
    	$data =array(
    			"body" =>array("param" => $param)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->unbindSecurityCard;
    	$result = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	if(isset($result['head']['status']) && $result['head']['status']=='0'){
    		return TRUE;
    	}
    	return FALSE;
    }
    
    //获取绑定宝开安全中心序列号
    public function getSecurityCardNumber($userId){
    	$data =array(
    			"body" =>array("param" => $userId)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->getSecurityCardNumber;
    	$result = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	if(isset($result['head']['status']) && $result['head']['status']=='0'){
    		if($result['body']['result']){
    			return $result['body']['result'];
    		}
    	}
    	return NULL;
    }
    
    //检测宝开安全中心序列号是否已经绑定
    public function checkSecurityCardNumber($serials){
    	$data =array(
    			"body" =>array("param" => array(
    					'sercuritySerilizeNumber' => $serials
    				)
    			)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->checkSecurityCardNumber;
    	$result = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	if(isset($result['head']['status']) && $result['head']['status']=='0'){
    		return $result['body']['result'];
    	}
    	return true;
    }
    
    //验证安全问题
    public function checkRightSecurityQuestion($aSecurityQuestion,$cnt=1){
    	$this->_securitypwd = new Rediska_Key_Hash(md5('securitypwd'.$this->_sessionlogin->info['id']));//安全密码 错误次数的验证和锁定
    	if(count($aSecurityQuestion)<=0){
    		return "102133";
    	}
    	$cntName  = 'securityQuestionErrorCount';
    	$timeName = 'securityQuestionErrorTime';
    	$securityQuestionErrorCount = $this->_securitypwd->exists($cntName) ? $this->_securitypwd->$cntName : 0 ;
    	$securityQuestionErrorTime = $this->_securitypwd->exists($timeName) ? $this->_securitypwd->$timeName : 0 ;
    	if($securityQuestionErrorCount >=3){
    		if(time() - $securityQuestionErrorTime <= 30*60){
    			return "102143";
    		} else {
    			$this->_securitypwd->$cntName  = 0;
    			$this->_securitypwd->$timeName = 0;
    		}
    	}
    
    	$uAns = array();
    	$arr = $this->_sessionlogin->info["quStruc"];
    	foreach($arr as $val){
    		$uAns[$val["qu"]] = $val["ans"];
    	}
    
    	$aErrorQue = array();
    	foreach ($aSecurityQuestion as $key=>$value){
    		if(!isset($value["qu"]) || $value["qu"]<=0){
    			return "1024";
    		}
    		if($uAns[$value["qu"]]!=md5(strtolower($value["ans"]))){
    			$aErrorQue[$key] = 1;
    		} else {
    			$aErrorQue[$key] = 0;
    		}
    	}
    
    	if(in_array(1, $aErrorQue)){
    		if(time() - $securityQuestionErrorTime <= 10*60){
    			$this->_securitypwd->$cntName = $securityQuestionErrorCount + 1;
    			if($securityQuestionErrorCount >=2){
    				return "102143";
    			}
    		} else{
    			$this->_securitypwd->$cntName  = 1 ;
    			$this->_securitypwd->$timeName = time();
    		}
    		//单个安全问题验证
    		if($cnt ==1){
    			return "102016";
    		}
    		//多个安全问题验证
    		if($aErrorQue[0] == 1){
    			return '1065';
    		} else {
    			return '1072';
    		}
    	} else {
    		$this->_securitypwd->$cntName  = 0;
    		$this->_securitypwd->$timeName = 0;
    	}
    	return TRUE;
    }
}
?>