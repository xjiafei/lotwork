<?php

class Member  extends Client  {
    
    private $data = null ;    
    private $_passLvl_rules;
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
    
    }
   
    
    public function unfreezeuser($data)
    {
    	$data =array(
    			"body" =>array(
    					"param" => array(
    							'userId'=>$data['userId'],
    							'range'=>$data['range'],
    							'freezeAccount'=>$data['freezeAccount '],
    							'memo'=>$data['memo']
    					),
    					"pager" => array(
    							'startNo' => 0,
    							"endNo"=>0
    					)
    			)
    	);
    
    	$uri = Zend_Registry::get( "firefog" )->freezeUser->unFreezeUser;
    	return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    
    /**
     * 用用户名查询用户信息
     * @param type $account
     * @return type
     */
    public function getuserbyname($account)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'account'=>strtolower($account),
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->queryUserByName;
        
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    public  function login($uName, $uPwd ,$userAgent) {
        $res = array();
        $data =array(
            "body" =>array(
                "param" => array(
                    'account'  => strtolower($uName),
                    'passwd'  => $uPwd,
                    'loginIp' => bindec(decbin(ip2long(get_client_ip()))),
					'userAgent' =>$userAgent
                ),
            "pager" => array(
                'startNo' => 13,
                "endNo"=>3
            )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->login->default;
        $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
        return $res;
    }
    /**
     * 修改安全密码
     * 
     * @param str $pwd 密码
     * @param int $pwdlvl 密码等级
     * @param int $uId 用户Id
     * 
     * @return
     */
    public function chgeSafePwd( $pwd){
        $data =array(
            "body" =>array(
                "param" => array(
                    'withdrawPasswd'  => encryptWithdrawPasswd($pwd),
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->modifyWithdrawPassword;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    //设置安全密码
    public function saveWithdrawPassword( $pwd){
        $data =array(
            "body" =>array(
                "param" => array(
                    'withdrawPasswd'  => encryptWithdrawPasswd($pwd),
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->saveWithdrawPassword;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    /**
     * 验证安全密码
     * @param int $uId 用户Id
     * @param str $safePwd 安全密码
     * @return  
     */
    public function verifySafePwd($safePwd){
        if($this->_sessionlogin->info["withdrawPasswd"] !=encryptWithdrawPasswd($safePwd)){
            $this->err_add("1066");
        }
    }
     /**
     * 重置密码
     * @param int $uId 用户Id
     * @param str $pwd 密码
     * @return
     */
    public function resetePwd( $pwd){
        $lvlPwd = 1;
        $lvlPwd = $this->passLvl($pwd);
        $data =array(
            "body" =>array(
                "param" => array(
                    'passwd'  => encryptLoginPasswd($pwd),
                    'passwdLvl'  => $lvlPwd,
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->member->savePass;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
     /**
     * 修改登陆密码
     * 
     * @param str $pwd 密码
     * @param int $pwdlvl 密码等级
     * @param int $uId 用户Id
     * 
     * @return
     */
    public function chgePwd( $pwd){
        $lvlPwd = 1;
        $lvlPwd = $this->passLvl($pwd);
        $data =array(
            "body" =>array(
                "param" => array(
                    'passwd'  => encryptLoginPasswd($pwd),
                    'passwdLvl'  => $lvlPwd,
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->changepwd;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }  
    
    /**
     * 更新帳號回收紀錄已更改密码
     * 
     * @param int $userId
     * 
     * @return
     */
    public function updateUserRecyclePwdFlag( $userId){
        $data =array(
            "body" =>array(
                "param" => array(
                    'userId'  => $userId,
                    'changePwd'  => true,
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->updateUserRecyclePwdFlag;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    
    /**
     * 获取用户基础信息
     * @param str $uName 用户名
     * @reutrn arr = array( "uId" =>1,
     *                      "uMail" =>"a@a.com",
     *                    )
     */
    public function getUserInfoByName($uName){
        $uri = Zend_Registry::get ( "firefog" )->login->safelogin;
        $data =array(
            "body" =>array(
                "param" => array(
                    'account'  => strtolower($uName),
                )
            )
        );
        $resData = $this->inRequest ( array_merge($this->_data, $data), $uri );
        return $resData;
    }
    
    /**
     * 
     * 通过密码来判断用户密码的等级
     * @param str $pass
     * @return int lvl
     */
    public function passLvl($pass){
        
        if(empty($this->_passLvl_rules)){
            $this->_passLvl_rules = array(
                        array("char_class", "[a-z]"),
                        array("char_class", "[A-Z]"),
                        array("char_class", "[0-9]"),
                        array("char_class", "[~`!\@#\$%\^&\*\(\)\\[\]-_+=\\\{\}\/|:;\"'<>,\.\?]"),
            );
        }
        $lvl = 0;
        
        foreach($this->_passLvl_rules as $v){
            switch($v[0]){
                case "char_class":
                    if(preg_match("/".$v[1]."/",$pass)){
                        ++$lvl;
                    }
                break;
            }
        }
        if($lvl>3){
            $lvl = 3;
        }
        return $lvl;
    }
    
    /**
     * 
     * 根据IP地址获取其所在地
     * @param str $ip
     */
    protected function frmIpToLocation($ip){
        $uri = Zend_Registry::get ( "firefog" )->login->safelogin;
        $data =array(
            "body" =>array(
                "param" => array(
                    'account'  => $uName,
                )
            )
        );
        $resData = $this->inRequest ( array_merge($this->_data, $data), $uri );
        return $resData;
    }
    
    public function getAdSpaceById($id){
    	$uri = Zend_Registry::get ( "firefog" )->login->getAdSpaceById;
    	$data =array(
    			"body" =>array(
    					"param" => array(
    							'id' => $id,
    					)
    			)
    	);
    	$resData = $this->inRequest ( array_merge($this->_data, $data), $uri );
    	return $resData;
    }
	
	  public function getAllAdSpace(){
    	$uri = Zend_Registry::get ( "firefog" )->login->getAllAdSpace;

    	$resData = $this->inRequest ($this->_data,$uri );
    	return $resData;
    }

}

?>