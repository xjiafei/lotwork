<?php
    class MyValid_Username extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
        	$regx = "/^[\x{4e00}-\x{9fa5}A-Za-z0-9_]+$/u";
            if (!preg_match($regx, $value)) {
                $this->_ctrl->addErr("1005");
            } elseif (getStrLen($value)<3 || getStrLen($value)>16){
            	$this->_ctrl->addErr("1006");
            }
            parent::isValid($errRupt, $showErr);
        }
    }