 <?php
class Admin_IndexController extends CustomControllerAction {
    
	protected $_admin;
	public function init() {
		parent::init ();
	}
	
	public function indexAction() {
		$issameIp = 0;
		if(!isset($this->_sessionlogin->info['lastLoginDate']) || empty($this->_sessionlogin->info['lastLoginDate'])){
			$lastLoginDate = date("Y-m-d H:i:s");
		} else {
			$lastLoginDate = date("Y-m-d H:i:s",getSrtTimeByLong($this->_sessionlogin->info['lastLoginDate']));
		}
		if(isset($this->_sessionlogin->info['lastLoginIp'])){
			if($this->_sessionlogin->info['lastLoginIp'] == bindec(decbin(ip2long(get_client_ip())))){
				$issameIp = 1;
			}
			$lastLoginIp = long2ip($this->_sessionlogin->info['lastLoginIp']);
		} else {
			$lastLoginIp = get_client_ip();
		}
		
		$this->view->lastLoginIp = $lastLoginIp;
		$this->view->issameIp = $issameIp;
		$this->view->lastLoginDate = $lastLoginDate;
		$this->view->title = "首页";
		$this->view->display ( 'admin/index.tpl' );
	}
} 