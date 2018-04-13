<?php

// Require Rediska
require_once dirname(__FILE__) . '/../../../../Rediska.php';

/**
 * @see Zend_Session
 */
require_once 'Zend/Session.php';

/**
 * @see Zend_Config
 */
require_once 'Zend/Config.php';

/**
 * @see Zend_Session_SaveHandler_Interface
 */
require_once 'Zend/Session/SaveHandler/Interface.php';

/**
 * @see Zend_Session_SaveHandler_Exception
 */
require_once 'Zend/Session/SaveHandler/Exception.php';

/**
 * Redis save handler for Zend_Session
 * 
 * @author Ivan Shumkov
 * @package Rediska
 * @subpackage ZendFrameworkIntegration
 * @version 0.5.7
 * @link http://rediska.geometria-lab.net
 * @license http://www.opensource.org/licenses/bsd-license.php
 */
class Rediska_Zend_Session_SaveHandler_Redis extends Rediska_Options_RediskaInstance implements Zend_Session_SaveHandler_Interface
{
    /**
     * Sessions set
     * 
     * @var Rediska_Zend_Session_Set
     */
    protected $_set;

    /**
     * Configuration
     * 
     * @var array
     */
    protected $_options = array(
        'keyPrefix' => 'PHPSESSIONS_',
        'lifetime'  => null,
    );

    /**
     * Exception class name for options
     * 
     * @var string
     */
    protected $_optionsException = 'Zend_Session_SaveHandler_Exception';

    /**
     * Construct save handler
     *
     * @param Zend_Config|array $options
     */
    public function __construct($options = array())
    {
        if ($options instanceof Zend_Config) {
            $options = $options->toArray();
        }

        // Set default lifetime
        if (!isset($options['lifetime'])) {
            $lifetime = (int)ini_get('session.gc_maxlifetime');

            if ($lifetime != 0) {
                $options['lifetime'] = $lifetime;
            } else {
                trigger_error(
                    "Please set session.gc_maxlifetime to enable garbage collection.",
                    E_USER_WARNING
                );
            }
        }

        parent::__construct($options);

        Rediska_Zend_Session_Set::setSaveHandler($this);
        $this->_set = new Rediska_Zend_Session_Set();
    }

    /**
     * Open Session
     *
     * @param string $save_path
     * @param string $name
     * @return boolean
     */
    public function open($save_path, $name)
    {
        return true;
    }

    /**
     * Close session
     *
     * @return boolean
     */
    public function close()
    {
        return true;
    }

    /**
     * Read session data
     *
     * @param string $id
     * @return string
     */
    public function read($id)
    {
    	$sessionData = $this->getRediska()->get($this->_getKeyName($id));
    	//file_put_contents(SITE_ROOT.'/log/session_log_'.date('Y-m-d').'.log', "\n".date('Y-m-d H:i:s').' session Read: key:'.$id.' data:'.$sessionData."\n",FILE_APPEND);
    	if($aData = json_decode($sessionData,TRUE)){
//     		$sessionData = self::serialize_session($aData);
			$tmp = $_SESSION;
			$_SESSION = $aData;
			$sessionData = session_encode();
			$_SESSION = $tmp;unset($tmp);
    	}
        return $sessionData;
    }

    /**
     * Write session data
     *
     * @param string $id
     * @param string $data
     * @return boolean
     */
    public function write($id, $data)
    {

    	$tmp = $_SESSION;
    	session_decode($data);
    	$data = json_encode($_SESSION);
    	$_SESSION = $tmp;unset($tmp);
//     	$data = json_encode($this->unserialize_session($data));
        try {
            $timestamp = time();
            $this->_set[$timestamp] = $id;
        } catch (Rediska_Connection_Exec_Exception $e) {
            $this->_deleteSetOrThrowException($e);
        }
        //file_put_contents(SITE_ROOT.'/log/session_log_'.date('Y-m-d').'.log', "\n".date('Y-m-d H:i:s').' session Write: key:'.$id.' data:'.$data."\n",FILE_APPEND);
        
        return $this->getRediska()->setAndExpire(
            $this->_getKeyName($id),
            $data,
            $this->getOption('lifetime')
        );
    }
    //转化数组为 php session serializer 加密字符串
    private function serialize_session( $array, $safe = true ) {
    	if($safe){
    		$array = unserialize(serialize($array)) ;
    	}
    	$raw = '' ;
    	$line = 0 ;
    	$keys = array_keys($array);
    	foreach( $keys as $key ) {
    		$value = $array[$key] ;
    		$line ++ ;
    		$raw .= $key .'|' ;
    		if( is_array( $value ) && isset( $value['huge_recursion_blocker_we_hope'] )) {
    			$raw .= 'R:'. $value['huge_recursion_blocker_we_hope'] . ';' ;
    		} else {
    			$raw .= serialize( $value ) ;
    		}
    		$array[$key] = Array( 'huge_recursion_blocker_we_hope' => $line ) ;
    	}
    	return $raw ;
    }
    
    //转化 php session serializar加密字符串数据为数组
	private static function unserialize_session($session_data) {
        $method = ini_get("session.serialize_handler");
        switch ($method) {
            case "php":
                return self::unserialize_php($session_data);
                break;
            case "php_binary":
                return self::unserialize_phpbinary($session_data);
                break;
            default:
                throw new Exception("Unsupported session.serialize_handler: " . $method . ". Supported: php, php_binary");
        }
    }

    private static function unserialize_php($session_data) {
        $return_data = array();
        $offset = 0;
        while ($offset < strlen($session_data)) {
            if (!strstr(substr($session_data, $offset), "|")) {
                throw new Exception("invalid data, remaining: " . substr($session_data, $offset));
            }
            $pos = strpos($session_data, "|", $offset);
            $num = $pos - $offset;
            $varname = substr($session_data, $offset, $num);
            $offset += $num + 1;
            $data = unserialize(substr($session_data, $offset));
            $return_data[$varname] = $data;
            $offset += strlen(serialize($data));
        }
        return $return_data;
    }

    private static function unserialize_phpbinary($session_data) {
        $return_data = array();
        $offset = 0;
        while ($offset < strlen($session_data)) {
            $num = ord($session_data[$offset]);
            $offset += 1;
            $varname = substr($session_data, $offset, $num);
            $offset += $num;
            $data = unserialize(substr($session_data, $offset));
            $return_data[$varname] = $data;
            $offset += strlen(serialize($data));
        }
        return $return_data;
    }

    /**
     * Destroy session
     *
     * @param string $id
     * @return boolean
     */
    public function destroy($id)
    {
        try {
            $this->_set->remove($id);
        } catch(Rediska_Connection_Exec_Exception $e) {
            $this->_deleteSetOrThrowException($e);
        }

        return $this->getRediska()->delete($this->_getKeyName($id));
    }

    /**
     * Garbage Collection
     *
     * @param int $maxlifetime
     * @return true
     */
    public function gc($maxlifetime)
    {
        try {
            return $this->_set->removeByScore(0, time() - $this->getOption('lifetime'));
        } catch(Rediska_Connection_Exec_Exception $e) {
            $this->_deleteSetOrThrowException($e);
        }
    }

    /**
     * Add prefix to session name
     * @param string $id
     * @return string
     */
    protected function _getKeyName($id)
    {
        return $this->getOption('keyPrefix') . $id;
    }

    /**
     * Delete old set or throw exception
     *
     * @throws Rediska_Connection_Exec_Exception
     * @param Rediska_Connection_Exec_Exception $e
     * @return void
     */
    protected function _deleteSetOrThrowException(Rediska_Connection_Exec_Exception $e)
    {
        if ($e->getMessage() == 'Operation against a key holding the wrong kind of value') {
            $this->_set->delete();
        } else {
            throw $e;
        }
    }

    /**
     * Destructor
     *
     * @return void
     */
    public function __destruct()
    {
        Zend_Session::writeClose();
    }
}
