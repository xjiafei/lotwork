<?php

class Login  extends Client  {
    
    private $data = null ;    
    
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
    
    }
    public  function login($uName, $uPwd) {
        
        $data =array(
            "body" =>array(
                "param" => array(
                    'account'  => strtolower($uName),
                    'passwd'  => md5($uPwd),
                	'loginIp' => bindec(decbin(ip2long(get_client_ip())))
                ),
            "pager" => array(
                'startNo' => 0,
                "endNo"=>0
            )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->login_adm->adminLogin;
        $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
        /* if(!isset($res['head']['status'])){
        	$this->err_add('903');
        } */
        return $res;
    }

}

?>