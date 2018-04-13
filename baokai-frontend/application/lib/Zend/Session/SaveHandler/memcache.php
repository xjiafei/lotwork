<?php
require_once 'Zend/Session.php';
require_once 'Zend/Config.php';
   
class Zend_Session_SaveHandler_Memcached  implements Zend_Session_SaveHandler_Interface
{
             const LIFETIME                   = 'lifetime';
             const OVERRIDE_LIFETIME = 'overrideLifetime';
             const MEMCACHED                              = 'memcached';
             protected $_lifetime = false;
             protected $_overrideLifetime = false;
             protected $_sessionSavePath;
             protected $_sessionName;
             protected $_memcached;
            /**
             * Constructor
             *
             * $config is an instance of Zend_Config or an array of key/value pairs containing configuration options for
             * Zend_Session_SaveHandler_Memcached . These are the configuration options for
             * Zend_Session_SaveHandler_Memcached:
             *
             *
             *              sessionId               => The id of the current session
             *              sessionName             => The name of the current session
             *              sessionSavePath => The save path of the current session
             *
             * modified                                     => (string) Session last modification time column
             *
             * lifetime                  => (integer) Session lifetime (optional; default: ini_get('session.gc_maxlifetime'))
             *
             * overrideLifetime  => (boolean) Whether or not the lifetime of an existing session should be overridden
             *              (optional; default: false)
             *
             * @param  Zend_Config|array $config              User-provided configuration
             * @return void
             * @throws Zend_Session_SaveHandler_Exception
             */
             public function __construct($config)
             {
                 if ($config instanceof Zend_Config) {
                     $config = $config->toArray();
                 } else if (!is_array($config)) {
                     /**
                      * @see Zend_Session_SaveHandler_Exception
                      */
                     require_once 'Zend/Session/SaveHandler/Exception.php';
   
                     throw new Zend_Session_SaveHandler_Exception(
                         '$config must be an instance of Zend_Config or array of key/value pairs containing '
                       . 'configuration options for Zend_Session_SaveHandler_Memcached .');
                 }
   
                 foreach ($config as $key => $value) {
                     do {
                         switch ($key) {
                             case self::MEMCACHED:
                                 $this->createMemcached($value);
                                 break;
                             case self::LIFETIME:
                                 $this->setLifetime($value);
                                 break;
                             case self::OVERRIDE_LIFETIME:
                                 $this->setOverrideLifetime($value);
                                 break;
                             default:
                                 // unrecognized options passed to parent::__construct()
                                 break 2;
                         }
                         unset($config[$key]);
                     } while (false);
                 }
             }
               
             /**
              * 创建memcached连接对象
              * 
              * @return void
              */
             public  function createMemcached($config){
               $mc = new Memcache;
               foreach ($config as $value){
                 $mc->addServer($value['ip'], $value['port']);
               }
               $this->_memcached = $mc;
             }
   
             public function __destruct()
             {
                 Zend_Session::writeClose();
             }
          /**
            * Set session lifetime and optional whether or not the lifetime of an existing session should be overridden
            *
            * $lifetime === false resets lifetime to session.gc_maxlifetime
            *
            * @param int $lifetime
            * @param boolean $overrideLifetime (optional)
            * @return Zend_Session_SaveHandler_Memcached
            */
             public function setLifetime($lifetime, $overrideLifetime = null)
             {
                 if ($lifetime < 0) {
                     /**
                      * @see Zend_Session_SaveHandler_Exception
                      */
                     require_once 'Zend/Session/SaveHandler/Exception.php';
                     throw new Zend_Session_SaveHandler_Exception();
                 } else if (empty($lifetime)) {
                     $this->_lifetime = (int) ini_get('session.gc_maxlifetime');
                 } else {
                     $this->_lifetime = (int) $lifetime;
                 }
   
                 if ($overrideLifetime != null) {
                     $this->setOverrideLifetime($overrideLifetime);
                 }
   
                 return $this;
             }
             /**
              * Retrieve session lifetime
              *
              * @return int
              */
             public function getLifetime()
             {
                 return $this->_lifetime;
             }
           /**
            * Set whether or not the lifetime of an existing session should be overridden
            *
            * @param boolean $overrideLifetime
            * @return Zend_Session_SaveHandler_Memcached
            */
             public function setOverrideLifetime($overrideLifetime)
             {
                 $this->_overrideLifetime = (boolean) $overrideLifetime;
   
                 return $this;
             }
   
             public function getOverrideLifetime()
             {
                 return $this->_overrideLifetime;
             }
          /**
           * Retrieve session lifetime considering 
           *
           * @param array  $value
           * @return int
           */
             public function open($save_path, $name)
             {
                 $this->_sessionSavePath = $save_path;
                 $this->_sessionName              = $name;
   
                 return true;
             }
          /**
           * Retrieve session expiration time
           *
           * @param * @param array  $value
           * @return int
           */
             public function close()
             {
                 return true;
             }
   
             public function read($id)
             {
                 $return = '';
   
                 $value  = $this->_memcached->get($id);                                                                              //获取数据
   
                 if ($value) {
                     if ($this->_getExpirationTime($value) > time()) {
                         $return = $value['data'];
                     } else {
                         $this->destroy($id);
                     }
                 }
   
                 return $return;
             }
   
             public function write($id, $data)
             {
                 $return = false;
                                   
                 $insertDate = array('modified' => time(),
                                                         'data'              => (string) $data);
                                                            
                             $value  = $this->_memcached->get($id);                                                                              //获取数据
   
                 if ($value) {
                     $insertDate['lifetime'] = $this->_getLifetime($value);
                        
                     if ($this->_memcached->replace($id,$insertDate)) {
                         $return = true;
                     }
                 } else {
                     $insertDate['lifetime'] = $this->_lifetime;
   
                     if ($this->_memcached->add($id, $insertDate,false,$this->_lifetime)) {
                         $return = true;
                     }
                 }
                    
                 return $return;
             }
   
             public function destroy($id)
             {
                 $return = false;
   
                 if ($this->_memcached->delete($id)) {
                     $return = true;
                 }
   
                 return $return;
             }
   
             public function gc($maxlifetime)
             {
                 return true;
             }
   
             protected function _getLifetime($value)
             {
                 $return = $this->_lifetime;
   
                 if (!$this->_overrideLifetime) {
                     $return = (int) $value['lifetime'];
                 }
   
                 return $return;
             }
   
             protected function _getExpirationTime($value)
             {
                 return (int) $value['modified'] + $this->_getLifetime($value);
             }                
}
?>