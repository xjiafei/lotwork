<?php

class Mail  extends Client  {
    
    private $data = null ;
    private $_config = null;
    private $_tool = null;
    private $_vailmail = null;
//     protected $_sessionlogin = null;
    function __construct() {
        parent::__construct();
//         $this->_sessionlogin = new Zend_Session_Namespace('datas');
        $head = new Header();
        $this->_data['head'] =  $head->getDefaultMap();
        $this->_config = Zend_Registry::get('config');
        $this->_tool = new Tool();
        $this->_vailmail = new Rediska_Key_Hash('vailmail');
    }
   
    /**
     * 
     * Enter description here ...
     * @param str $email
     */
    public function isExist($email){
       $param= array();
       if(!empty($email)){
           $param["email"] = strtolower($email);
       }
       $data =array(
            "body" =>array(
                "param" => $param,
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0,
                )
            )
        );
       $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->querySubUserByCriteria;
       $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
       if(!empty($res['body']['result']) && $res['body']['result'][0]['email'] == $email && $res['body']['result'][0]['id'] !=$this->_sessionlogin->info['id']){
       		$this->err_add("1073");
       }
   }
   
   /**
    *
    * Enter description here ...
    * @param str $email
    */
   public function isExist_V2($email){
	   	$param= array();
	   	if(!empty($email)){
	   		$param["email"] =$email;
	   	}
	   	$data =array(
	   			"body" =>array(
	   					"param" => $param,
	   					"pager" => array(
	   							'startNo' => 0,
	   							"endNo"=>0,
	   					)
	   			)
	   	);
	   	$uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->querySubUserByCriteria;
	   	$res = $this->inRequest ( array_merge($this->_data, $data), $uri );
	   	if(!empty($res['body']['result']) && isset($res['body']['result'][0]['id']) && $res['body']['result'][0]['id']!= $this->_sessionlogin->info['id']){
	   		return true;
	   	} else {
	   		return false;
	   	}
   }
    
    /**
     * 
     * 发送邮件
     * @param unknown_type $email
     * @param unknown_type $title
     * @param unknown_type $content
     * @param unknown_type $id
     */
    public function sendmail($email, $title, $content, $id=""){
        $data =array(
            "body" =>array(
                "param" => array(
                    'email'  => strtolower($email),
                    'title'  => $title,
                    'content' => $content,
                    'id' => empty($id)?intval($this->_sessionlogin->info["id"]):$id,
                )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->mail->sendmail;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    /**
     * 
     * @param Long $userId
     * @param String $account
     * @param Long  $taskId
     * @param Array $sendParam
     * @return Ambigous <mix, unknown, NULL, mixed, multitype:, multitype:Ambigous <mixed, NULL> , StdClass, multitype:Ambigous <mixed, multitype:, multitype:Ambigous <mixed, NULL> , NULL> , boolean, number, string>
     */
    public function sendNotice($userId,$account,$taskId,$sendParam){
    	
    	$data = array(
    		"body" => array(
    			"param" => array(
    				'userId'  =>$userId,
    				'account' =>$account,
    				'taskId'  =>$taskId,
    				'paramMap'   =>$sendParam,
    			)
    		)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->mail->sendNotice;
    	return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    /**
     * 
     * Enter description here ...
     * @param unknown_type $title
     * @param unknown_type $email
     * @param unknown_type $uName
     * @param unknown_type $fuName
     * @param unkonwn_type $subUrl
     * @param unknown_type $actName
     * @param unknown_type $param
     */
    public function sendVerifiedMail($userId, $uName,$taskId, $subUrl, $param, $toolAct = 0,$emailAddress=''){
        $domain = getWebSiteDomain();
        $domain = getHttpUrl($domain);
        
        $path_img = $this->_config->imgroot;
        $activeLink = $domain.$subUrl."&";
        foreach($param as $k=>$v){
            $activeLink .= $k."=".$v."&";
        }
        $activeLink = substr($activeLink, 0, -1);
        
        //$content = "<!DOCTYPE html><html><head><title></title><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/></head><body style='margin: 0 auto; background-color: white;'><table width='599' cellpadding='0' cellspacing='0' border='0' align='center'><tr><td colspan='3' width='599' height='80' style='background:#076256;' align='center'><img src='".$path_img."/images/edm/edm-logo.png' width='140' height='80' alt='宝开.com 宝开平台'></td></tr><tr><td style='background:#076256;' height='320' width='8'></td><td align='center'><table cellpadding='0' cellspacing='0' width='520' style='text-align:left;color: #00564d; font-size:0;'><tr><td height='70' style='font-weight: bold; font-size: 14px;'>Hi！亲爱的".$uName."用户，您好。</td></tr><tr><td style='font-size:14px;line-height:24px;'>欢迎使用宝开".$fuName."。<br/>请点击链接".$actName."：<a href='".$activeLink."'>".$activeLink."</a><br/>(该链接在24小时内有效，24小时后需要重新获取)<br/><br/><span style='font-size:12px;font-weight:bold;'>如果上面不是链接形式，请将地址复制到您的浏览器(例如IE)的地址栏再访问。</span></td></tr><tr><td style='font-size:12px;' height='64'>感谢您对宝开的支持，希望您在宝开得到愉悦的游戏体验。<br/>这是一封系统邮件，请勿回复。</td></tr></table></td><td width='8' height='320' style='background:#076256; font-size:0;'></td></tr><tr><td colspan='3' width='599' height='50' align='center' style='background:#076256;color:#FFF;font-size:12px;'>&copy;2001-2012 宝开.com All rights reserved. 宝开彩票平台版权所有</td></tr></table></body></html>";
        //$this->sendmail($email, $title, $content);
        $sendParam['uName'] = $uName;
        $sendParam['path_img'] = $path_img;
        $sendParam['activeLink'] = $activeLink;
        if(!empty($emailAddress)){
        	$sendParam['prior_email_address'] = $emailAddress;
        }
        $this->sendNotice($userId, $uName, $taskId, $sendParam);
        
        $toolAct = intval($toolAct);
        //生成工具
        if(!empty($toolAct)){
            $time = 0;
            if(empty($param["time"])){
                $time = getSendTime();
            }else{
                $time = $param["time"];
            }
            //$uName = $this->_sessionlogin->info["account"];
            $this->_tool->init($uName, $toolAct, $time);
        }
    }
    
    
    public function sendMailByFrag($title, $email, $preCont, $aftCont, $subUrl = "", $param = array(),$toolAct = 0){
        $content = NULL;
        $content = $preCont;
        
        if(!empty($param)){
            $domain = getWebSiteDomain();
            $domain = getHttpUrl($domain);
            $activeLink = $domain.$subUrl."&";
            foreach($param as $k=>$v){
                $activeLink .= $k."=".$v."&";
            }
            $activeLink = substr($activeLink, 0, -1);
            $content .="<a href='$activeLink' style='color: #00927E;'>$activeLink</a>";
        }
        $content .= $aftCont;
        $this->sendmail($email, $title, $content);
        
        
        //生成工具
        $toolAct = intval($toolAct);
        if(!empty($toolAct)){
            $time = 0;
            if(empty($param["time"])){
                $time = getSendTime();
            }else{
                $time = $param["time"];
            }
            $uName = "";
            if(!empty($param["name"])){
               $uName = $param["name"];
            }elseif(!empty($this->_sessionlogin->info["account"])){
                $uName = $this->_sessionlogin->info["account"];
            }else{
                $this->err_add("1068");
            }
            if(!$this->err_isExist()){
                $this->_tool->init($uName, $toolAct, $time);
            }
        }
    }
    
    /**
     * 
     * 获取密匙
     */
    private function _getKey(){
        $key = "";
        $key = $this->_config->seed;
        return $key;
    }

    /**
     * 
     * 生成加密字符串
     * @param unknown_type $params
     */
    public function encrypt($params=array()){
        $seed = $this->_getKey();
        $res = "";
        while(list($k,$v) = each($params)){
            $res = md5($seed.md5($res).$k).md5(md5($v));
        }
        return md5($res);
    }
    
    /**
     * 
     * 加密验证
     */
    public function verify($mask, $params=array(), $toolAct = 0){
        $uName="";
        if($this->encrypt($params)!=$mask){
            $this->err_add("1068");
        }else if(!empty($params["name"])){
            $uName = $params["name"];
        }else if(!empty($this->_sessionlogin->info["account"])){
            $uName = $this->_sessionlogin->info["account"];
        }else{
            $this->err_add("1068");
        }
        //验证是否为同一个用户
        if(!empty($this->_sessionlogin->info['id']) && !empty($params['uid']) && $params['uid'] != $this->_sessionlogin->info['id']){
        	$this->err_add("102055");
        	$this->_vailmail->$mask = '1';
        	return;
        }
        //检测链接是否已经点击
        if($this->_vailmail->exists($mask)){
        	if($toolAct =='2'){
        		$this->err_add('102049');
        	}else if($toolAct == '1'){
        		$this->err_add('102054');
        	}else {
        		$this->err_add('102050');
        	}
        } else {
        	$this->_vailmail->$mask = '1';
        }
        $toolAct= intval($toolAct);
        if(!empty($toolAct)&&!$this->err_isExist()){
            //获取有效时间
            $res = $this->_tool->get($uName, $toolAct);
            if(empty($res["body"]["result"]["effectTime"])){
                $this->err_add("1068");
            }else{
                //比较时间是否有效
                if($params["time"]<number_format($res["body"]["result"]["effectTime"],0,'','')){
                    $this->err_add("102048");
                }
            }
        }
        //更新点击时间
        $this->_tool->update($uName, $toolAct, getSendTime());
    }
}

?>
