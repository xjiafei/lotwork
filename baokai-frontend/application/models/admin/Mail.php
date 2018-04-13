<?php

class Mail  extends Client  {
    
    private $data = null ;
    private $_config = null;
    private $_tool = null;
//     protected $_sessionlogin = null;
    function __construct() {
        parent::__construct();
//     	$this->_sessionlogin = new Zend_Session_Namespace('datas');
    	$head = new Header();
        $this->_data['head'] =  $head->getDefaultMap();
        $this->_config = Zend_Registry::get('config');
        $this->_tool = new Tool();
    }
   
    /**
     * 
     * Enter description here ...
     * @param str $email
     */
    public function isExist($email){
        
       if(!empty($email)){
           $param["email"] =strtolower($email);
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
       if(!empty($res["body"]["result"])){
           $this->err_add("1073");
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
                    'email'  => $email,
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
    public function sendVerifiedMail($userId, $uName,$taskId,$emailAddress='', $subUrl='', $param=array(), $toolAct = 0,$thdtitle=''){
        $domain = $this->_config->www_url;
        $domain = getHttpUrl($domain);
        $path_img = $this->_config->imgroot;
        $activeLink = $domain.$subUrl."&";
        foreach($param as $k=>$v){
            $activeLink .= $k."=".$v."&";
        }
        $activeLink = substr($activeLink, 0, -1);
        
        $sendParam['uName'] = $uName;
        $sendParam['path_img'] = $path_img;
        $sendParam['activeLink'] = $activeLink;
        if(!empty($emailAddress)){
        	$sendParam['prior_email_address'] = $emailAddress;
        }
        if(!empty($thdtitle)){
        	$sendParam['thdtitle'] = $thdtitle;
        }
        $this->sendNotice($userId, $uName, $taskId, $sendParam);
        
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
    
    
    public function sendMailByFrag($title, $email, $preCont, $aftCont, $subUrl = "", $param = array(),$toolAct = 0){
        $content = NULL;
        $content = $preCont;
        
        if(!empty($param)){
            $domain = $this->_config->www_url;
            $domain = getHttpUrl($domain);
            $path_img = $this->_config->imgroot;
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
        $toolAct= intval($toolAct);
        if(!empty($toolAct)&&!$this->err_isExist()){
            //获取有效时间
            
            $res = $this->_tool->get($uName, $toolAct);
            if(empty($res["body"]["result"]["effectTime"])){
                $this->err_add("1068");
            }else{
//                echo $params["time"]."<br/>";
//                echo number_format($res["body"]["result"]["effectTime"],0,'','');die;
                //比较实践是否有效
                if($params["time"]<number_format($res["body"]["result"]["effectTime"],0,'','')){
                    $this->err_add("102048");
                }
            }
        }
        $this->_tool->update($uName, $toolAct, getSendTime());
    }

}

?>