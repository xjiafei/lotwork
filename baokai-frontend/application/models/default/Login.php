<?php

class Login  extends Client  {
    
    private $data = null ;    
    
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
    
    }
    public  function login($uName, $uPwd, $Ip) {
        
        $data =array(
            "body" =>array(
                "param" => array(
                    'account'  => strtolower($uName),
                    'passwd'  => encryptLoginPasswd($uPwd),
                    'loginIp' => get_client_ip()
                ),
            "pager" => array(
                'startNo' => 13,
                "endNo"=>3
            )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->login->default;
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
    }
    
    /*
     * 根据用户名来判断用户是否存在
     * @param str $uName 用户名
     * @return int uId 如果存在则返回uId,如果不存在则返回0 
     */
    public function isExistByName($uName){
        return true;
    }
    
    /*
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
                ),
            "pager" => array(
                'startNo' => 13,
                "endNo"=>3
            )
            )
        );
        return $this->inRequest ( array_merge($this->_data, $data), $uri );
//        $res = array(
//            "uId" =>1,
//            "uMail" =>"a@a.com",
//        );
//        return $res;
    }

}

?>