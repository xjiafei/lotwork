<?php
abstract class MyValid_Abstract{
    protected $_ctrl;
    function __construct(CustomControllerAction &$controller){
        $this->_ctrl = $controller;
    }
	
    /**
     * 
     * 
     * @param unknown_type $errRupt
     */
    protected function isValid($errRupt, $showErr = false){
        if(!$this->_ctrl->isErrFree()){
            switch($errRupt){
                case "json":
                    $arr = array();
                    $arr = $this->_ctrl->getErrJson();
                    echo Zend_Json::encode($arr);
                    die;
                break;
                case "show":
                    $this->_ctrl->res_show(true, $showErr);
                break;
                case "showNoLeft":
                    $this->_ctrl->res_show(false, $showErr);
                break;
                default:
                break;
            }
        }
    }
    
	/**
     * @param  string $messageKey
     * @param  string $value      OPTIONAL
     * @return void
     */
    protected function _error($messageKey, $value = null)
    {
        if ($messageKey === null) {
            $keys = array_keys($this->_messageTemplates);
            $messageKey = current($keys);
        }
        if ($value === null) {
            $value = $this->_value;
        }
        $this->_errors[]              = $messageKey;
        $this->_messages[$messageKey] = $this->_createMessage($messageKey, $value);
    }
}