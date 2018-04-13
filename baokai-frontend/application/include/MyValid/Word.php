<?php
    class MyValid_Word extends MyValid_Abstract
    {
        
        function __construct(&$controller){
            parent::__construct($controller);
        }
        
        public function isValid($value, $errRupt = null, $showErr = false)
        {
            if( preg_match('/^[\w]+$/',$value) !== 1 ){
                $this->_ctrl->addErr("1071");
            }
            parent::isValid($errRupt, $showErr);
        }
    }