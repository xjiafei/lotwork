<?php
class PhoneSecuritySession {
	private $_redisAlias;
	private $_securityKey;
	private $_cntName;
	private $_timeName;
	function __construct($config){
		$this->_config = $config;
		$this->_redisAlias = Zend_Registry::get('rediska')->on($this->_config->FRONTEND_ALIAS);
		$this->_cntName = 'securityCodeErrorCount';
		$this->_timeName = 'securityCodeErrorTime';
	}
	//检测用户宝开安全中心是否已被锁定
	public function getPhoneSecurityStatus($userId){
		$this->_securityKey = md5('securitycode'.$userId);
		$securityCodeErrorCount = $this->_redisAlias->getFromHash($this->_securityKey,$this->_cntName);
		$securityCodeErrorTime = $this->_redisAlias->getFromHash($this->_securityKey,$this->_timeName);
		if($securityCodeErrorCount >=5 && date('Y-m-d',time()) == date('Y-m-d',$securityCodeErrorTime)){
			return true;
		} else {
			return false;
		}
	}
	//解除用户宝开安全中心锁定
	public function updatePhoneSecurityStatus($userId){
		$this->_securityKey = md5('securitycode'.$userId);
// 		$this->_redisAlias->delete($this->_securityKey);
		$result = $this->_redisAlias->setToHash($this->_securityKey, $this->_cntName,0);
		$this->_redisAlias->setToHash($this->_securityKey, $this->_timeName,0);
		$securityCodeErrorCount = $this->_redisAlias->getFromHash($this->_securityKey,$this->_cntName);
		$securityCodeErrorTime = $this->_redisAlias->getFromHash($this->_securityKey,$this->_timeName);
		if($securityCodeErrorCount == '0' && $securityCodeErrorTime=='0'){
			return true;
		} else {
			return false;
		}
		
	}
}