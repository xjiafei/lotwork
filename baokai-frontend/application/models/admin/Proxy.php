<?php
class Proxy extends Client {
   
    private $data = null ;    
    
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
    
    }
   /**
    * 后台总代列表
    * @return array 
    */
   public function topProxyList()
   {
       
       
       
       
       
       
       $proxyData = array(
           array(
               'userid'=>1,
               'username'=>'jack',
               'childcount'=>10,
               'istop'=>1,
               'useraccount'=>100,
               'groupid'=>1,
               'amount'=>6384.50,
               'availableamout'=>3864.60,
               'add_num'=>'15',//已经增加的用户
               'allow_num'=>'50',
               'regtime'=>date('Y-m-d H:i:s'),
               'ip'=>'127.0.0.1'
           ),
           array(
               'userid'=>2,
               'username'=>'lilei',
               'childcount'=>20,
               'istop'=>0,
               'useraccount'=>160,
               'groupid'=>1,
               'amount'=>3384.30,
               'availableamout'=>5322.60,
               'add_num'=>'12',//已经增加的用户
               'allow_num'=>'30',
               'regtime'=>date('Y-m-d H:i:s'),
               'ip'=>'127.0.0.1'
           ),
           array(
               'userid'=>3,
               'username'=>'susan',
               'childcount'=>8,
               'istop'=>0,
               'useraccount'=>87,
               'groupid'=>1,
               'amount'=>2235.00,
               'availableamout'=>522.60,
               'add_num'=>'12',//已经增加的用户
               'allow_num'=>'40',
               'regtime'=>date('Y-m-d H:i:s'),
               'ip'=>'127.0.0.1'
           )
       );
       return $proxyData;
   }
   
   
}
?>