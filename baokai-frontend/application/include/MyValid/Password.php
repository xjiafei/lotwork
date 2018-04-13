<?php
    class MyValid_Password extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
      					      // $uPwd,"json"
        public function isValid($value, $errRupt = null, $showErr = false)
        {
        	//密码修改为 可以输入验证字符只验证长度
            /* $regx = "/\w{6,20}/";
            if (!preg_match($regx, $value, $matches)) {
                $this->_ctrl->addErr("1008");
            } */
        	
        	if ( (strlen($value) > 20 )||(strlen($value) < 6) ) {
        		$this->_ctrl->addErr("1007");
        	}
            parent::isValid($errRupt, $showErr);
        }
    }