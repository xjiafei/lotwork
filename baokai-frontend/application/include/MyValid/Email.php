<?php
    class MyValid_Email extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
        	if(!filter_var($value, FILTER_VALIDATE_EMAIL)){
            //$regx = "/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/";
            //if (!preg_match($regx, $value, $matches)) {
                $this->_ctrl->addErr("1010");
            }
            parent::isValid($errRupt, $showErr);
        }
    }