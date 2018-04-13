<?php

class Tool  extends Client  {
    
    private $data = null ;  
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
    }
   
    /**
     * 
     * Enter description here ...
     * @param unknown_type $account
     * @param unknown_type $action
     * @param unknown_type $effectTime
     */
    public function init($account, $action, $effectTime){
       $param = array();
       if(!empty($account)){
           $param["account"] =$account;
       }
       if(!empty($action)){
           $param["action"] =$action;
       }
       if(!empty($effectTime)){
           $param["effectTime"] =$effectTime;
       }
       $data =array(
            "body" =>array(
                "param" => $param,
            )
        );
        
       $uri = Zend_Registry::get ( "firefog" )->tool->init;
       $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
//       if(!empty($res["body"]["result"])){
//           $this->err_add("1073");
//       }
   }

    /**
     * 
     * Enter description here ...
     * @param unknown_type $account
     * @param unknown_type $action
     * @param unknown_type $effectTime
     */
    public function update($account, $action, $effectTime){
       $param = array();
       if(!empty($account)){
           $param["account"] =$account;
       }
       if(!empty($action)){
           $param["action"] =$action;
       }
       if(!empty($effectTime)){
           $param["effectTime"] =$effectTime;
       }
       $data =array(
            "body" =>array(
                "param" => $param,
            )
        );
        
       $uri = Zend_Registry::get ( "firefog" )->tool->update;
       $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
   }
   
   
   /**
    * 
    * Enter description here ...
    * @param unknown_type $account
    * @param unknown_type $action
    */
    public function get($account, $action){
       $param = array();
       if(!empty($account)){
           $param["account"] =$account;
       }
       if(!empty($action)){
           $param["action"] =$action;
       }
       $data =array(
            "body" =>array(
                "param" => $param,
            )
        );
        
       $uri = Zend_Registry::get ( "firefog" )->tool->get;
       $res = $this->inRequest ( array_merge($this->_data, $data), $uri );
       return $res;
   }
}

?>