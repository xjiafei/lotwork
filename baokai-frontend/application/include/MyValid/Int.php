<?php
    class MyValid_Int extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
            if( preg_match('/^[-]?[0-9]+$/',$value) !== 1 ){
                $this->_ctrl->addErr("1071");
            }
            parent::isValid($errRupt, $showErr);
        }
    }