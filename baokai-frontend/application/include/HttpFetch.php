<?php

class HttpFetch
{
	protected $_url='http://localhost:8088';
	protected $_client='';
	protected $_response='';
	protected $_maxredirects=0;
	protected $_timeout=30;
	protected $_adapter='Zend_Http_Client_Adapter_Curl'; 
	protected $_live=true;
	
	public function __construct(){ 	
		$this->_client = new Zend_Http_Client($this->_url, array(
				'maxredirects' => $this->_maxredirects,
				'adapter'         => $this->_adapter, 
				'keepalive' => $this->_live,
				'timeout'      => $this->_timeout));
    }
	public function initClient(){
		
		
	}
	
	
	
}
?>