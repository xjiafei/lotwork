<?php
class Admin extends Client {
	public $_data = null;
	public function __construct() {
		parent::__construct ();
		$header = new Header();
        $this->_data['head'] = $header->getDefaultMap();
	}
	public function adminLogin($jdata = null) {
		$javaInterface = Zend_Registry::get ( "firefog" )->login_adm->adminLogin;
		return $this->inRequest ( array_merge ( $this->_data, $jdata ), $javaInterface );
	}
}
?>