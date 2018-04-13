<?php
    class MyValid_Slogan extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
        	//中文字符换算成两英文单位
            $regx = "/[\x01-\x7f]|[\xc2-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}/";
            $value = preg_replace($regx, "xx", $value);
            $regx2 = "/^[a-zA-Z]{4,16}$/";
            if (!preg_match($regx2, $value, $matches)) {
                $this->_ctrl->addErr("1069");
            }
            parent::isValid($errRupt, $showErr);
        }
    }