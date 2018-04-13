<?php



class Client {
    
    /*
     * 私有变量
     */
    private $_errs; // 错误列队
    private static $_JAVA_SERVER = null;
    
    
    protected $_sessionlogin;
    /*
     * 静态变量
     */
    public static $CONFIG = array (
            'adapter' => 'Zend_Http_Client_Adapter_Curl',
            'curloptions' => array (
                    CURLOPT_FOLLOWLOCATION => true 
            ) 
    );
    /*
     * 构造器
     */
    protected $logger = null ;
    protected $_modulename = null;
    protected $_controllername = null;
    protected $_actionname = null;
    public function __construct($modulename=null,$controllername=null,$actionname=null) {
    	$this->_errs = array ();
        self::$_JAVA_SERVER = Zend_Registry::get ( "config" )->JAVA_SERVER;
        $this->_sessionlogin = new Zend_Session_Namespace('datas') ;
        $this->logger = Zend_Registry::get ( "logger" );
        $this->_modulename = $modulename;
        $this->_controllername = $controllername;
        $this->_actionname = $actionname;
        $this->logger->setEventItem('modules', $this->_modulename );
        $this->logger->setEventItem('controller', $this->_controllername );
        $this->logger->setEventItem('action', $this->_actionname );
    }
    
    /*
     * 添加错误信息进入错误队列
     * @param str $code 错误代码
     * @param str $msg 提示信息
     * @return null
     */
    public function err_add($code) {
        $this->_errs [] = array (
            $code
        );
    }
    
    /*
     * 获取错误列队 @return arr
     */
    public function err_get() {
        return $this->_errs;
    }
    
	/*    获取错误码内容  */
    public function err_get_v2() {
    	$errorCode	= new ErrorCode() ;
    	if($this->err_isExist()){
    		if(array_key_exists($this->_errs[0][0],$errorCode->codeList)){
    		 return $errorCode->codeList[$this->_errs[0][0]] ;
    		}  		
    	}
    }
    /*
     * 判断是否存在错误
     * @return boolean TRUE为有错误 FALSE为没有错误
     */
    public function err_isExist() {
        return ! empty ( $this->_errs );
    }
    
    /**
     * 
     * 接口交互读取
     * @param arr $data
     * @param str $url
     * @param bool $isObject  false = 0 = object;  true =1 =array
     * @return mix
     */
    public function inRequest($data, $url, $isArray = true) {
    	
    	$rowobj = null;
		try {
			$client = new Zend_Http_Client ( self::$_JAVA_SERVER . $url, self::$CONFIG );
			$json = json_encode ( $data );
			$this->logger->log ( $url."接口请求数据".$json, Zend_Log::ERR );
			//die;
			$resp = $client->setRawData ( $json, 'application/firefrog;charset=utf-8' )->request ( Zend_Http_Client::POST );
			$this->logger->log ( $url."接口返回数据".$resp, Zend_Log::ERR );
		} catch ( Exception $ex ) {
			$this->logger->log ( $ex->getMessage (), Zend_Log::ERR );
			return $rowobj;
			exit ();
		}
		if ($resp->isError ()) {
			try {
				//throw new FireFogException ( '服务端返回非json格式的数据,错误码' . $resp->getStatus () );
				$this->logger->log ( '服务端返回非json格式的数据,错误码', Zend_log::ERR );
				return array();
			} catch ( Exception $e ) {
				$this->logger->log ( $e->getMessage (), Zend_log::ERR );
				return $rowobj;
				exit ();
			}
		} else {
			try {
				if ($isArray) {
					$rowobj = Zend_Json::decode ( $resp->getBody (), Zend_Json::TYPE_ARRAY );
					if (! empty ( $rowobj ["head"] ["status"] ) && ($rowobj ["head"] ["status"] != 1)) {
						$this->err_add ( $rowobj ["head"] ["status"] );
					}
				} else {
					$rowobj = Zend_Json::decode ( $resp->getBody (), Zend_Json::TYPE_OBJECT );
					if (! empty ( $rowobj->head->status ) && ($rowobj->head->status != 1)) {
						$this->err_add ( $rowobj->head->status );
					}
				}
			} catch ( Exception $ex ) {
				$this->logger->log ( $ex->getMessage (), Zend_log::ERR );
			}
			
			return $rowobj;
		}
    }
    public function inRequestV2($data, $turl, $isArray = true) {
    	
    	$head = new Header ();
		$tmps ["head"] = json_decode ( json_encode ( $head->getDefaultMap () ), true );
		$tmps ["body"] = $data;
		if ($turl) {
			if (is_array ( $turl )) {
				//eval ( '$url = Zend_Registry::get ("firefog" )->' . key ( $turl ) . '->' . current ( $turl ) . ' ;' );
				list($key,$value) = each($turl);
				$url = Zend_Registry::get('firefog')->$key->$value;
			} elseif (is_string ( $turl )) {
				//eval ( '$url = Zend_Registry::get ("firefog" )->$turl ;' );
				$url = Zend_Registry::get('firefog')->$turl;
			} else {
				//throw new FireFogException ( '接口url错误' );
				return array();
			}
		} else {
			//throw new FireFogException ( 'url为空' );
			return array();
		}
		return $this->inRequest ( $tmps, $url, $isArray);
    }
    
    public function outRequest($data, $url, $isArray = true) {
        $rowobj = null;
        try {
            $client = new Zend_Http_Client($url, self::$CONFIG);
            $json = json_encode($data);
            $this->logger->log($url . "接口请求数据" . $json, Zend_Log::ERR);
            //die;
            $resp = $client->setRawData($json, 'application/firefrog;charset=utf-8')->request(Zend_Http_Client::POST);
            $this->logger->log($url . "接口返回数据" . $resp, Zend_Log::ERR);
        } catch (Exception $ex) {
            $this->logger->log($ex->getMessage(), Zend_Log::ERR);
            return $rowobj;
            exit();
        }
        if ($resp->isError()) {
            try {
                //throw new FireFogException ( '服务端返回非json格式的数据,错误码' . $resp->getStatus () );
                $this->logger->log('服务端返回非json格式的数据,错误码', Zend_log::ERR);
                return array();
            } catch (Exception $e) {
                $this->logger->log($e->getMessage(), Zend_log::ERR);
                return $rowobj;
                exit();
            }
        } else {
            try {
                if ($isArray) {
                    $rowobj = Zend_Json::decode($resp->getBody(), Zend_Json::TYPE_ARRAY);
                    if (!empty($rowobj ["head"] ["status"]) && ($rowobj ["head"] ["status"] != 1)) {
                        $this->err_add($rowobj ["head"] ["status"]);
                    }
                } else {
                    $rowobj = Zend_Json::decode($resp->getBody(), Zend_Json::TYPE_OBJECT);
                    if (!empty($rowobj->head->status) && ($rowobj->head->status != 1)) {
                        $this->err_add($rowobj->head->status);
                    }
                }
            } catch (Exception $ex) {
                $this->logger->log($ex->getMessage(), Zend_log::ERR);
            }

            return $rowobj;
        }
    }

    public function outRequestV2($data, $turl, $isArray = true) {

        $head = new Header ();
        $tmps ["head"] = json_decode(json_encode($head->getDefaultMap()), true);
        $tmps ["body"] = $data;
        if ($turl) {
            if (is_array($turl)) {
                //eval ( '$url = Zend_Registry::get ("firefog" )->' . key ( $turl ) . '->' . current ( $turl ) . ' ;' );
                list($key, $value) = each($turl);
                $url = Zend_Registry::get('firefog')->$key->$value;
            } elseif (is_string($turl)) {
                //eval ( '$url = Zend_Registry::get ("firefog" )->$turl ;' );
                $url = Zend_Registry::get('firefog')->$turl;
            } else {
                //throw new FireFogException ( '接口url错误' );
                return array();
            }
        } else {
            //throw new FireFogException ( 'url为空' );
            return array();
        }
        return $this->inRequest($tmps, $url, $isArray);
    }

}
?>