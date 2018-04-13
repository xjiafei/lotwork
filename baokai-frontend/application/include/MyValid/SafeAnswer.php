<?php
    class MyValid_SafeAnswer extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
            /* $regx = "/[\x01-\x7f]|[\xc2-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}/";
            $value = preg_replace($regx, "x", $value);
            $regx2 = "/^[a-zA-Z]{4,16}$/";
            if (!preg_match($regx2, $value, $matches)) {
                $this->_ctrl->addErr("1070");
            } */
        	//中文字符长度处理
        	if(getStrLen($value)<4 || getStrLen($value)>16){
        		$this->_ctrl->addErr("1070");
        	}
            parent::isValid($errRupt, $showErr);
        }
    }