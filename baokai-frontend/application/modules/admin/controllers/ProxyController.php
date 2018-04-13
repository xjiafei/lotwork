<?php
class Admin_ProxyController extends CustomControllerAction {
	protected $_proxy, $_total, $_page, $_pageSize,$_zhTW;
	public function init() {
		parent::init ();
		$this->_proxy = new ProxyAdm ();
		$this->_total = 0;
		$this->_page = 1;
		$this->_pageSize = 10;
		$this->_zhTW = new ZHTW();
	}
	
	/**
	 * 总代列表和搜索功能
	 */
	public function indexAction() {
		// 当前页吗
		$this->_page = intval ( $this->_request->getParam ( "page", '1' ) );
		$data = array ();
		$request = $this->_request;
		$searchtype = getSecurityInput ( $request->get ( "searchtype", '' ) );
		$typeN = getSecurityInput ( $request->get ( "typeN", '' ) );
		$regtime = getSecurityInput ( $request->get ( "regtime", '' ) );
		$fromBal = getSecurityInput ( $request->get ( "fromBal", '' ) );
		$toBal = getSecurityInput ( $request->getParam ( "toBal", '' ) );
		
		if(! empty ( $typeN )){
			if ($searchtype == 'email') {
				$data ['email'] = $typeN;
			} else {
				$data ['userName'] = trim(strtolower($typeN));
			}
		}
		if ($regtime) {
			$data ['fromRegisterDate'] = ! empty ( $regtime ) ? getQueryStartTime('-'.$regtime.' days') : '';
			$data ['toRegisterDate'] = ! empty ( $regtime ) ? getSendTime () : '';
		}
		if ($fromBal != '') {
			$data ['fromBal'] = floatval ( $fromBal ) * $this->_moneyUnit;
		}
		if ($toBal != '') {
			$data ['toBal'] = floatval ( $toBal ) * $this->_moneyUnit;
		}
		if(count($data)<=0){
			$data =(object)$data;
		}
		$proxyData = $this->_proxy->topproxylist ( $data, $this->_page, $this->_pageSize );
		$result = array ();
		if (! empty ( $proxyData ['body'] ['result'] )) {
			foreach ( $proxyData ['body'] ['result'] as $k => &$v ) {
				if ($v ['userLvl'] == 0) {
					$value ['id'] = $v ['id'];
					$value ['account'] = $v ['account'];
					$value ['registerIp'] = long2ip ( $v ['registerIp'] );
					$value ['registerDate'] = date ( 'Y-m-d H:i:s', getSrtTimeByLong ( $v ['registerDate'] ) );
					$value ['teamACount'] = $v ['teamACount'];
					$value ['teamUCount'] = $v ['teamUCount'];
					$value ['availBal'] = $v ['availBal'] > 0 ? getMoneyFomat ( $v ['availBal'] / $this->_moneyUnit, 2 ) : 0;
					$value ['teamBal'] = $v ['teamBal'] > 0 ? getMoneyFomat ( $v ['teamBal'] / $this->_moneyUnit, 2 ) : 0;
					$value ['agentlimit'] = $v ['agentlimit'];
					$value ['vipLvl'] = $v ['vipLvl'];
					$value ['freezeMethod'] = $v ['freezeMethod'];
					$value ['isFreeze'] = $v ['isFreeze'];
					$result [] = $value;
				}
			}
		}
		
		$this->_total = $proxyData ['body'] ['pager'] ['total'];
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
		$this->view->assign ( 'fromBal', $fromBal );
		$this->view->assign ( 'toBal', $toBal );
		$this->view->assign ( 'regtime', $regtime );
		$this->view->assign ( 'typeN', $typeN );
		$this->view->assign ( 'searchtype', $searchtype );
		$this->view->assign ( 'pages', $pages );
		$this->view->assign ( 'proxyuser', $result );
		$this->view->title = '总代列表';
		$this->view->display ( 'admin/proxy/adminProxyUser_list.tpl' ); // 测试页面
	}
	
	/**
	 * 总代下级列表
	 * 提供给ajax调用的数据
	 */
	public function viewAction() {
		$request = $this->getRequest ();
		$userid = $request->getParam ( $paramName );
		$childuser = $this->_proxy->childlist ( $userid );
		// $this->view->assign('proxychild',$childuser);
		// $this->view->display('admin/proxy/adminProxyUser_list.tpl');
		echo Zend_Json::encode ( $childuser );
	}
	public function accomplaintsAction() {
		$this->view->display ( 'admin/proxy/accomplaints.tpl' ); // 测试页面
	}
	
	// 总代开户
	public function accountAction() {
		$result = array ();
		if ($this->getRequest ()->ispost ()) {
			$request = $this->getRequest ();
			$username = getSecurityInput (trim($request->getPost ( 'account', '' )));
			$passwd = getSecurityInput (trim($request->getPost ( 'passwd', '' )));
			$agentLimit = intval ( getSecurityInput ( $request->getPost ( 'agentLimit', '' ) ) );
			if (empty ( $username )) {
				echo Zend_Json::encode ( array (
						'isSuccess' => '0',
						'data' => '用户名不能为空!' 
				) );
				exit ();
			}
			$twstatus = $this->_zhTW->checkZhtw($username);
			if($twstatus){
				echo Zend_Json::encode ( array (
						'isSuccess' => '0',
						'data' => '用户名不能使用繁体字!'
				) );
				exit ();
			}
			if (empty ( $passwd )) {
				echo Zend_Json::encode ( array (
						'isSuccess' => '0',
						'data' => '登录密码不能为空!' 
				) );
				exit ();
			}
			if ($agentLimit == 0) {
				echo Zend_Json::encode ( array (
						'isSuccess' => '0',
						'data' => '开户配额不能为空!' 
				) );
				exit ();
			}/*  else if($agentLimit > 200){
				echo Zend_Json::encode ( array (
						'isSuccess' => '0',
						'data' => '开户配额不能大于200!'
				) );
				exit ();
			} */
			//检测用户是否使用敏感字符
			$status = $this->_proxy->islegalAccount($username);
			if($status =='0'){
				echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'用户名不合法'));
				exit;
			} else if($status == '101002'){
				echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'用户名'.$username.'已存在'));
				exit;
			}
			$user = new User();
			$data = $user->getuserbyname ( htmlspecialchars ($username) );
			$this->appendErrFrmCls ( $user );
			if ($this->isErrFree ()) {
				if (! empty ( $data ['body'] ['result'] )) {
					echo Zend_Json::encode ( array (
							'isSuccess' => '0',
							'data' => '用户名'.$username.'已存在'
					) );
					exit ();
				}
			}
			
			$data ['account'] = strtolower (trim($username));
			$data ['passwd'] = encryptLoginPasswd ( strtolower($passwd) );
			$data ['agentLimit'] = $agentLimit;
			$data ['registeIp'] = bindec ( decbin ( ip2long ( get_client_ip () ) ) );
			$hellresult = $this->_proxy->savetopaccount ( $data );
			$this->appendErrFrmCls ( $this->_proxy );
			if ($this->isErrFree ()) {
				$result ["isSuccess"] = 1;
			} else {
				$result = $this->getErrJson ();
			}
			echo Zend_Json::encode ( $result );
		} else {
			$this->view->title = '总代开户';
			$this->view->display ( 'admin/proxy/adminProxyUserAccount_list.tpl' );
		}
	}
	
	/**
	 * 总代开户配额修改
	 * 提供给ajax数据
	 */
	public function createaccountAction() {
		$request = $this->getRequest ();
		$account = getSecurityInput ( trim(strtolower($request->getParam ( 'account' ) )));
		$accountinfo = $this->_proxy->getaccountnum ( $account );
		if ($this->getRequest ()->ispost ()) {
			// 更新总代配额
			$request = $this->getRequest ();
			$userId = getSecurityInput ( $request->getPost ( 'userid' ) );
			$availablequota = floatval ( getSecurityInput ( $request->getPost ( 'availablequota' ) ) );
			$data ['userid'] = $userId;
			$data ['availablequota'] = $availablequota + $accountinfo ['teamACount'] + $accountinfo ['teamUCount'];
			$rollback = $this->_proxy->saveaccountnum ( $data );
			$this->appendErrFrmCls ( $this->_proxy );
			if ($this->isErrFree ()) {
				$result ["isSuccess"] = 1;
			} else {
				$result = $this->getErrJson ();
			}
			echo Zend_Json::encode ( $result );
			exit ();
		} else { // 显示总代配额修改页面
			echo Zend_Json::encode ( $accountinfo );
			exit ();
		}
	}
	
	/**
	 * 功能：总代开户
	 * 判断总代用户是否存在
	 */
	public function topissetAction() {
		$user = new User ();
		$request = $this->getRequest ();
		$username = getSecurityInput($request->getPost ( 'username' ));
		$twstatus = $this->_zhTW->checkZhtw($username);
		if($twstatus){
			echo Zend_Json::encode ( array (
					'isSuccess' => '0',
					'data' => '用户名不能使用繁体字!'
			) );
			exit ();
		}
		//检测用户是否使用敏感字符
		$status = $this->_proxy->islegalAccount($username);
		if($status =='0'){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'用户名不合法'));
			exit;
		} else if($status == '101002'){
			echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'用户名已存在，请重新输入'));
			exit;
		}
		$data = $user->getuserbyname ($username);
		$this->appendErrFrmCls ( $user );
		if ($this->isErrFree ()) {
			$result ["isSuccess"] = 1;
			if (! empty ( $data ['body'] ['result'] )) {
				$result ["isSuccess"] = 0;
				$result['data'] = '用户名已存在，请重新输入';
			}
		} else {
			$result = $this->getErrJson ();
		}
		echo Zend_Json::encode ( $result );
	}
}