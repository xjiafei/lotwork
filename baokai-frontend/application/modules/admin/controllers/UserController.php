<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
require_once 'application/models/admin/Mail.php';
class Admin_UserController extends Fundcommon  {

    protected $user;
    protected $proxyAdm;
    protected $_mail;
    private $_queryBankCardRecords;
    private $_prizeUnit;
    private $_sessionTool;
    private $_phoneSecuritySession;
    private $_gamePrize;
    private $_pageSize,$_total,$_page;
    public function init() {
        parent::init();
        $this->_pageSize = 20;
        $this->_page = 1;
        $this->_total = 0;
        $this->_valid->email = new MyValid_Email ( $this );
        $this->_valid->pwd = new MyValid_Password ( $this );
        $this->_valid->safeAns = new MyValid_SafeAnswer ( $this );
        $this->_valid->slogan = new MyValid_Slogan( $this );
        $this->_valid->isInt = new MyValid_Int ( $this );
        $this->_valid->isWord = new MyValid_Word( $this );
        $this->_mail = new Mail();
        $this->user = new User();
        $this->proxyAdm = new ProxyAdm();
        $this->_queryBankCardRecords =  new QueryBankCardRecords();
        $this->_gamePrize = new GamePrize();
        $this->_prizeUnit= 100; //默认 java奖金组返点数是千单位的,前台显示要 除以1000 处理之后 乘以1000 返回给java
        $this->_sessionTool = new SessionTool($this->_config);
        $this->_phoneSecuritySession = new PhoneSecuritySession($this->_config);
    }

    public function indexAction(){
    	$this->_forward('list');
    }
    
    //用户列表以及搜索功能
    public function querysubuserAction(){
    	$userId =  floatval(getSecurityInput($this->_request->getPost('userid',"-1")));
		
    	////////////////////////////////////
    	$data = array (
    			"body" => array (
    					"param" => array (
    							"userId" =>$userId ,
    							"includeTeamBal"=> 0,
    					),
    					"pager" => array (
    							'startNo' => 1 ,
    							"endNo" => 200
    					)
    			)
    	);  
    	$proxyData = $this->proxyAdm->querySubUser($data);
    	$tmp = array();
    	if($proxyData['body']['result'] !=null ){
    		foreach ($proxyData['body']['result'] as $value) {
    			if(empty($value)){
    				continue;
    			}
    			if($value['teamUCount']+$value['teamACount'] > 0){  
    				array_push($tmp ,"{ id:'".$value['id']."',	name:'".$value['account']."(".($value['teamACount']+$value['teamUCount']).")',	isParent:true},");
    			}else{
    				array_push($tmp, "{ id:'".$value['id']."',	name:'".$value['account']."',	isParent:false},");
    			}
    		}
    	}
    	header ( 'Content-Type: text/html;charset=utf-8' );
    	echo("[".implode($tmp)."]");  
    	
    }  
    
    //用户列表左侧树目录
    public function usersAction(){
    	$data = array (
    			"body" => array (
    					"param" => array (
    							"userName" =>null ,
    							'fromRegisterDate'=>null ,
    							'toRegisterDate' =>null ,//$userId ,
    							'fromBal'=> null ,
    							'toBal' => null ,
    					),
    					"pager" => array (
    							'startNo' => 1 ,
    							"endNo" => 200
    					)
    			)
    	);
    	
    	$proxyData = $this->proxyAdm->queryGeneralAgent($data);
    	$tmp = array();
    	if($proxyData['body']['result'] !=null ){
    	foreach ($proxyData['body']['result'] as $value) {
    		if($value['teamUCount'] > 0){
    			array_push($tmp ,"{ id:'".$value['id']."',	name:'".$value['account']."(".$value['teamUCount'].")',	isParent:true},");
    		}else{
    			array_push($tmp, "{ id:'".$value['id']."',	name:'".$value['account']."',	isParent:false},");
    		}
    	}  
    	}
    	header ( 'Content-Type: text/html;charset=utf-8' );
    	echo("[".implode($tmp)."]");
    }
    
    //获取列表
    public function listAction() {	
        $this->_page = intval($this->_request->getParam("page","1"));
    	$proxyData = array(); 
    	$request = $this->_request ;
        $data=array();
    	$type      = getSecurityInput($request->getParam( "type",'' ));
    	$typeN     = getSecurityInput($request->getParam( "typeN",'' ));
    	$usergroup = getSecurityInput($request->getParam( "usergroup",'' ));
    	$regtime   = getSecurityInput($request->getParam( "regtime",'' ));
    	$bMoney    = getSecurityInput($request->getParam( "bMoney",'' ));
    	$eMoney    = getSecurityInput($request->getParam( "eMoney",'' ));
    	$userId    = getSecurityInput($request->getParam( "userId",'' ));
    	$search    = getSecurityInput($this->getParam('search'));
		$device    = getSecurityInput($request->getParam("device",''));
		$param = new ArrayObject(array());
		
    	if($typeN){
    		if($type == 'username'){
    			$param['account'] = strtolower($typeN) ;
    		}else{
    			$param['email']   = strtolower($typeN) ;
    		}
    	}
    	if($usergroup!=''){
            switch ($usergroup) {
                case '-11':
                  $param['vipLvl'] = 1;
                    break;
                 case '-12':
                  $param['vipLvl'] = 2;
                    break;
                case '-13':
                  $param['vipLvl'] = 3;
                    break;
                case '-14':
                  $param['vipLvl'] = 4;
                    break;
                case '-99':
                  $param['vipLvl'] = 99;
                    break;            
                default:
                  $param['userLvl'] = $usergroup;
                    break;
            }
    	}
    	if($regtime){
    		$param['fromDate'] = getQueryStartTime('-'.$regtime.' days');
    		$param['toDate']   = getSendTime();
    	}
    	if($bMoney!=''){
    		$param['fromBal'] = floatval($bMoney);
    	}
    	if($eMoney!=''){
    		$param['toBal'] = floatval($eMoney);
    	}
		if($device!=''){
			$param['device'] = strtolower($device);
		}
    	$param['includeTeamBal'] = 0;
    	$data['body']['param'] = $param;
    	$data['body']['pager'] = array(
    						'startNo' => ($this->_page-1)*$this->_pageSize+1,
    						'endNo'   => $this->_page*$this->_pageSize
    					);
    	$proxyData = $this->proxyAdm->querySubUserByCriteria($data); 
        if(!empty($proxyData['body']['result']))
        {
            foreach($proxyData['body']['result'] as &$v)
            {
                $v['registerDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['registerDate']));
                $v['registerIp']   = long2ip($v['registerIp']);
                $v['userLvl']   = $this->getUserType($v['userLvl']);
                $v['availBal']  = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
                $v['freezeBal'] = !empty($v['freezeBal']) ? getMoneyFomat($v['freezeBal']/$this->_moneyUnit,2) : 0;
                $v['teamBal']   = !empty($v['teamBal']) ? getMoneyFomat($v['teamBal']/$this->_moneyUnit,2) : 0;
				$v['device'] = strtoupper($v['device']);
            }
        }
        $this->_total = $proxyData['body']['pager']['total'];
        $pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
		if($search == 1 && $this->_total==1){
			$this->view->userChain = explode('/', trim($proxyData['body']['result'][0]['userChain'],'/'));
		}
		
        $this->view->type = $type;
        $this->view->typeN = $typeN;
        $this->view->usergroup = $usergroup;
        $this->view->aUserGroup = $this->getUserGroups();
        $this->view->regtime = $regtime;
        $this->view->bMoney = $bMoney;
		$this->view->device = $device;
        $this->view->eMoney = $eMoney;
        $this->view->assign('proxyuser',$proxyData['body']['result']);
        $this->view->assign('pages',$pages);	
        $this->view->title = '客户列表';
        $this->view->display('admin/user/adminUser_list.tpl');

    }

    //下载列表
    public function downlistAction(){			
    	$aTitle = array(
    			'account' => '用户名',
    			'count' => '团队',
    			'userLvl' => '用户组',
    			'availBal' => '可用余额',
    			'registerDate' => '注册时间',
    			'registerIp' 	=> 'IP地址',
    	);

    	$fileName ='客户列表';
    	$page = intval($this->_request->getParam("page","1"));
    	$proxyData = array();
    	$request = $this->_request ;
    	$data=array();
    	$type      = getSecurityInput($request->getParam( "type",'' ));
    	$typeN     = getSecurityInput($request->getParam( "typeN",'' ));
    	$usergroup = getSecurityInput($request->getParam( "usergroup",'' ));
    	$regtime   = getSecurityInput($request->getParam( "regtime",'' ));
    	$bMoney    = getSecurityInput($request->getParam( "bMoney",'' ));
    	$eMoney    = getSecurityInput($request->getParam( "eMoney",'' ));
    	$userId    = getSecurityInput($request->getParam( "userId",'' ));
    	$search    = getSecurityInput($this->getParam('search'));
    	$param 	   = new ArrayObject(array());		
		if($typeN){
    		if($type == 'username'){
    			$param['account'] = strtolower($typeN) ;
    		}else{
    			$param['email']   = strtolower($typeN) ;
    		}
    	}
    	if($usergroup!=''){
    		if($usergroup =='-11' || $usergroup =='-12' || $usergroup =='-13' || $usergroup =='-14'  || $usergroup =='-99' ){
    			$param['vipLvl'] = 1;
    		} else {
    			$param['userLvl'] = $usergroup;
    		}
    	}
    	if($regtime){
    		$param['fromDate'] = getQueryStartTime('-'.$regtime.' days');
    		$param['toDate']   = getSendTime();
    	}
    	if($bMoney!=''){
    		$param['fromBal'] = floatval($bMoney);
    	}
    	if($eMoney!=''){
    		$param['toBal'] = floatval($eMoney);
    	}
    	$data['body']['param'] = $param;
    	$total = 0;
    	$page= $totalPage = 1;
    	$querySize = 2000;
    	$startTime= microtime(true);
				
    	do {
    		$data['body']["pager"]["startNo"] = ($page-1)*$querySize+1;
    		$data['body']["pager"]["endNo"] = $page*$querySize;
	    	$proxyData = $this->proxyAdm->querySubUserByCriteria($data);
	    	$aContent = array();
	    	if(!empty($proxyData['body']['result'])) {
	    		foreach($proxyData['body']['result'] as $key=>$v) {
	    			$aContent[$key]['availBal']  = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
	    			$aContent[$key]['teamACount']  = !empty($v['teamACount']) ? intval($v['teamACount']) : 0;
	    			$aContent[$key]['teamUCount']  = !empty($v['teamUCount']) ? intval($v['teamUCount']) : 0;
	    			$aContent[$key]['account']  = !empty($v['account']) ? $v['account'] : 0;
	    			$aContent[$key]['count']  	= $aContent[$key]['teamUCount'] + $aContent[$key]['teamACount'];
	    			$aContent[$key]['userLvl']  = isset($v['userLvl']) ? $this->getUserType($v['userLvl']) : '';
	    			$aContent[$key]['availBal'] = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
	    			$aContent[$key]['registerDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['registerDate']));
	    			$aContent[$key]['registerIp']   = long2ip($v['registerIp']);
	    		}
	    		$totalPage = ceil($proxyData['body']['pager']['total']/$querySize);
	    		$total 	   = $proxyData['body']['pager']['total'];
	    	}
	    	if($totalPage<=0){
	    		$totalPage = 1;
	    	}
	    	$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
	    	$aContent = array();
    	} while ($page++ !=$totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['account'] = '下载数据:'.$total.'条.';
		$modata['count']   = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
    	//用户名 	团队 	用户组 	可用余额 	注册时间/IP
    	$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
    }
	
	 //下載下級列表
    public function downchildlistAction(){	

    	$aTitle = array(
    			'account' => '用户名',
    			'count' => '团队',
    			'userLvl' => '用户组',
    			'availBal' => '可用余额',
    			'registerDate' => '注册时间',
    			'registerIp' 	=> 'IP地址',
    	);

    	$fileName ='客户列表';
    	$page = intval($this->_request->getParam("page","1"));
    	$proxyData = array();
    	$request = $this->_request ;
    	$data=array();
    	$type      = getSecurityInput($request->getParam( "type",'' ));
    	$typeN     = getSecurityInput($request->getParam( "typeN",'' ));
    	$usergroup = getSecurityInput($request->getParam( "usergroup",'' ));
    	$regtime   = getSecurityInput($request->getParam( "regtime",'' ));
    	$bMoney    = getSecurityInput($request->getParam( "bMoney",'' ));
    	$eMoney    = getSecurityInput($request->getParam( "eMoney",'' ));
    	$userId    = getSecurityInput($request->getParam( "userId",'' ));
    	$search    = getSecurityInput($this->getParam('search'));
		
		//test
		//$hidenUser    = getSecurityInput($request->getParam('hidenUser',''));
		//echo "<script type='text/javascript'>alert('typeN=  '+'$typeN');</script>";
		
    	$param 	   = new ArrayObject(array());
    	if($typeN){
    		if($type == 'username'){
    			$param['account'] = strtolower($typeN) ;
    		}else{
    			$param['email']   = strtolower($typeN) ;
    		}
    	}
		if($usergroup!=''){
    	
            switch ($usergroup) {
                case '-11':
                  $param['vipLvl'] = 1;
                    break;
                 case '-12':
                  $param['vipLvl'] = 2;
                    break;
                case '-13':
                  $param['vipLvl'] = 3;
                    break;
                case '-14':
                  $param['vipLvl'] = 4;
                    break;
                case '-99':
                  $param['vipLvl'] = 99;
                    break;            
                default:
                  $param['userLvl'] = $usergroup;
                    break;
            }
    	}
    	if($regtime){
    		$param['fromDate'] = getQueryStartTime('-'.$regtime.' days');
    		$param['toDate']   = getSendTime();
    	}
    	if($bMoney!=''){
    		$param['fromBal'] = floatval($bMoney);
    	}
    	if($eMoney!=''){
    		$param['toBal'] = floatval($eMoney);
    	}
		
		//test
		//$param['userId']=$hidenUser;       
		
    	$data['body']['param'] = $param;
    	$total = 0;
    	$page= $totalPage = 1;
    	$querySize = 2000;
    	$startTime= microtime(true);
    	do {
    		$data['body']["pager"]["startNo"] = ($page-1)*$querySize+1;
    		$data['body']["pager"]["endNo"] = $page*$querySize;
	    	$proxyData = $this->proxyAdm->querySubUserForDownClist($data);
	    	$aContent = array();
	    	if(!empty($proxyData['body']['result'])) {
	    		foreach($proxyData['body']['result'] as $key=>$v) {
	    			$aContent[$key]['availBal']  = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
	    			$aContent[$key]['teamACount']  = !empty($v['teamACount']) ? intval($v['teamACount']) : 0;
	    			$aContent[$key]['teamUCount']  = !empty($v['teamUCount']) ? intval($v['teamUCount']) : 0;
	    			$aContent[$key]['account']  = !empty($v['account']) ? $v['account'] : 0;
	    			$aContent[$key]['count']  	= $aContent[$key]['teamUCount'] + $aContent[$key]['teamACount'];
	    			$aContent[$key]['userLvl']  = isset($v['userLvl']) ? $this->getUserType($v['userLvl']) : '';
	    			$aContent[$key]['availBal'] = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
	    			$aContent[$key]['registerDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['registerDate']));
	    			$aContent[$key]['registerIp']   = long2ip($v['registerIp']);
	    		}
	    		$totalPage = ceil($proxyData['body']['pager']['total']/$querySize);
	    		$total 	   = $proxyData['body']['pager']['total'];
	    	}
	    	if($totalPage<=0){
	    		$totalPage = 1;
	    	}
	    	$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
			$aContent = array();
    	} while ($page++ !=$totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['account'] = '下载数据:'.$total.'条.';
		$modata['count']   = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
    	//用户名 	团队 	用户组 	可用余额 	注册时间/IP
    	$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
    }
	
	//客户管理 ->冻结
	public function freezeaccountAction() {
    	$this->view->title = "冻结用户";
		$this->view->display('admin/user/adminUser_freezeaccount.tpl');
	}
	
	//客户管理 ->解冻
	public function thawingaccountAction() {
    	$this->view->title = "解冻用户";
		 $this->view->display('admin/user/adminUser_thawingaccount.tpl');
	}
	
	//客户管理 ->详情
    public function baseinfoAction() {
    	$this->view->title = "客户详情";
		 $this->view->display('admin/user/adminUser_baseinfo.tpl');
	}	
	
	//客户管理 ->查看银行卡
	public function bankcardAction() {
         $userid 	  = getSecurityInput($this->_request->getParam('id'));
         $userAccount = getSecurityInput($this->_request->getParam('account'));
         $typepage = getSecurityInput($this->_request->getParam('typepage'));
         $data["param"]["userAccount"] = $userAccount ;
         $data["param"]["userId"] 	   = intval($userid);
         $bankStruc = array();
         $res = $this->_queryBankCardRecords->queryBankCardRecords($data);
         if(isset($res["result"][0][0])){
	         $recorder = $res["result"][0][0];
	         $userBankStruc = $recorder["userBanks"]  ;
	         if (count($userBankStruc)>0) {
	         	foreach ($userBankStruc as $key=>$value){
	         		//$bankStruc[$key]['bankAccount']= $value['bankAccount'] ? $this->getSecurityBankCardAucount($value['bankAccount']) : '';
					$bankStruc[$key]['bankAccount']= $value['bankAccount'] ? $value['bankAccount'] : '';
	         		//$bankStruc[$key]['bankNumber'] = $value['bankNumber']  ? $this->getSecurityBankCardNum($value['bankNumber'] ): '';
					$bankStruc[$key]['bankNumber'] = $value['bankNumber']  ? $value['bankNumber'] : '';
	         		$bankStruc[$key]['bankName']   = $this->_bankIcoArray[$value['bankId']]['name'] ? $this->_bankIcoArray[$value['bankId']]['name'] : '';
	         		$bankStruc[$key]['province']   = isset($value['province']) ? $value['province'] : '';
	         		$bankStruc[$key]['city'] 	   = isset($value['city'] )? $value['city'] : '';
	         		$bankStruc[$key]['branchName'] = $value['branchName'] ? $value['branchName'] : '';
	         		$bankStruc[$key]['bindDate']   = date('Y-m-d H:i:s',getSrtTimeByLong($value['bindDate']));
	         		$bankStruc[$key]['isBlackList']   = $value['isBlackList'] ? $value['isBlackList'] : false;
	         	}
	         }
	         unset($recorder);
	         unset($userBankStruc);
         }
         $data1["param"]["userAccount"] = $userAccount ;
         $data1["param"]["action"] = 2;
         $res = $this->_queryBankCardRecords->queryBankCardHistory($data1);
         $modata = $bankDelHistory = array();
         if(isset($res['body']['result']['bankCardHistorys']) && count($res['body']['result']['bankCardHistorys'])>0){
	         foreach ( $res['body']['result']['bankCardHistorys'] as $recorder){
	         	if($recorder['action'] =='2'){
//		         	$modata["bankNumber"] 	= $this->getSecurityBankCardNum($recorder["bankNumber"]) ;
		         	$modata["bankNumber"] 	= $recorder["bankNumber"] ;
		         	$modata["userId"] 		= $recorder["userId"] ;
		         	$modata["actionTime"] 	= date('Y-m-d H:i:s',getSrtTimeByLong($recorder["actionTime"])) ;
		         	$modata["action"] 		= $recorder["action"];
		         	$modata["bankAccount"] 	= $recorder["bankAccount"] ;
		         	//$modata["bankAccount"] 	= $this->getSecurityBankCardAucount($modata["bankAccount"]) ;
					$modata["bankAccount"] 	= $modata["bankAccount"] ;
		         	$modata["bankName"] 	= isset($recorder["mcBankId"]) ? $this->_bankIcoArray[$recorder["mcBankId"]]['name'] : '' ;
		         	$modata['province']   = !empty($recorder['province']) ? $recorder['province'] : '';
		         	$modata['city'] 	  = !empty($recorder['city'] )? $recorder['city'] : '';
		         	$modata['branchName'] = !empty($recorder['branchName']) ? $recorder['branchName'] : '';
	         		$modata['bindDate']   = date('Y-m-d H:i:s',getSrtTimeByLong($recorder['actionTime']));
	         		$modata['isBlackList']   =  $recorder['isBlackList'] ? $recorder['isBlackList'] : false;
	         		$modata['bindcardType']   =  $recorder['bindcardType'];	         		
	         		if($modata['bindcardType']==0){
	         			array_push($bankDelHistory,$modata);	         			
	         		}
	         	}
	         }
         }
         $this->view->assign('id',$userid);
         $this->view->assign('userAccount',$userAccount);
         $this->view->assign('typepage',$typepage);
         $this->view->assign('bankStruc',$bankStruc);
         $this->view->assign('bankDelHistory',$bankDelHistory);
         $this->view->title = '查看银行卡';
		 $this->view->display('admin/user/adminUser_bankcard.tpl');
	}
	
	//客户管理->奖金返点	
	public function bonusrebateAction() {
    	$aAwardSeris = array(1=>'时时彩',3=>'11选5','快乐彩','低频','趣味彩','快三'); //3D系列合并到低频系列
		$aRateName = array(
				1=>array('直选返点','不定位返点','超级2000'),
				array('直选返点','不定位返点'),
				array('所有返点'),
				array('任选型返点','趣味型返点'),
				array('所有返点'),
                array('所有返点'),
            	array('混选','直选','不定位返点'),
				array(''),//跳過沒用
				array('正码--平码返点','特码--直码返点','特码--特肖返点','特码--色波&两面返点','特码--半波返点',
                  '正特码--一肖返点','正特码--不中返点','连肖(中)--二连肖、三连肖返点','连肖(中)--四连肖返点',
                  '连肖(中)--五连肖返点','连肖(不中)--二连肖、三连肖返点','连肖(不中)--四连肖返点','连肖(不中)--五连肖返点','连码返点')
		);
		$aRateIndex = array(
				1=>array(1,2,8),
				array(1,2),
				array(3),
				array(4,5),
				array(3),
                array(3),
                array(6,7,22),
				array(0),//跳過沒用
				array(11,1,9,10,12,13,14,15,16,17,18,19,20,21) //awardType 1 直選 ,9生效,10色波
		);
		$aAwardGroup = array(
				'1'=>array(
						'99101'=>'重庆时时彩',
						'99102'=>'江西时时彩',
						'99105'=>'黑龙江时时彩',
						'99103'=>'新疆时时彩',
						'99107'=>'上海时时乐',
						'99106'=>'宝开时时彩',
						'99104'=>'天津时时彩',
						'99111'=>'宝开1分彩',
						'99114'=>'腾讯分分彩',
						'99112'=>'秒开时时彩',
						'99113'=>'<font size="4">超级2000秒秒彩</br><center>（APP版）</center></font>'
				),
				//3D系列合并到低频系列
				/* '2'=>array(
						'99108'=>'3D',
						'99109'=>'排列5',
				), */
				'3'=>array(
						'99301'=>'山东11选5',
						'99302'=>'江西11选5',
						'99303'=>'广东11选5',
						//'99304'=>'重庆11选5',
						'99305'=>'宝开11选5',
						'99306'=>'秒开11选5',
							
				),
				'4'=>array('99201'=>'北京快乐8'),
				'5'=>array(
						'99108'=>'3D',
						'99109'=>'排列5',
						'99401'=>'双色球'
				),
                '6'=>array('99701'=>'六合彩'),
				'7'=> array(
						'99501'=>'江苏快三',
                        '99601'=>'江苏骰宝',
						'99502'=>'安徽快三',
						'99602'=>'高频骰宝</br><center>娱乐厅</center>',
						'99603'=>'高频骰宝</br><center>至尊厅</center>'						
				)
		);
    	$toDay = new DateTime();
		$removeDay = new DateTime('2016-02-22T00:00:00');
		if($toDay > $removeDay){
		unset($aAwardGroup[1][99102]);
		}
         $userid   = getSecurityInput($this->_request->getParam('id'));
         $account  = getSecurityInput($this->_request->getParam('account'));
         $typepage = getSecurityInput($this->_request->getParam('typepage'));
         //获取游戏奖金组
         $userAwardListStruc = $this->user->getGamePrizeList($userid);
		 error_log(print_r($userAwardListStruc,true),3,'D:/HG/FF4/php/frontend/log/test2.txt');
         $userAwardsList = array();
         if(count($userAwardListStruc)>0){
         	foreach($userAwardListStruc as $key=>$value){
         		$value['directRet']   = floatval($value['directRet']/$this->_prizeUnit);
         		$value['threeoneRet'] = floatval($value['threeoneRet']/$this->_prizeUnit);
         		$value['maxDirectRet'] = floatval($value['maxDirectRet']/$this->_prizeUnit);
         		$value['maxThreeOneRet'] = floatval($value['maxThreeOneRet']/$this->_prizeUnit);
				$value['maxSuperRet'] = floatval($value['maxSuperRet']/$this->_prizeUnit);
				$value['superRet'] = floatval($value['superRet']/$this->_prizeUnit);
				$value['lhcYear'] = floatval($value['lhcYear']/$this->_prizeUnit);
				$value['lhcColor'] = floatval($value['lhcColor']/$this->_prizeUnit);
				$value['sbThreeoneRet'] = floatval($value['sbThreeoneRet']/$this->_prizeUnit);
                $value['lhcFlatcode'] = floatval($value['lhcFlatcode']/$this->_prizeUnit);
                $value['lhcHalfwave'] = floatval($value['lhcHalfwave']/$this->_prizeUnit);
                $value['lhcOneyear'] = floatval($value['lhcOneyear']/$this->_prizeUnit);
                $value['lhcNotin'] = floatval($value['lhcNotin']/$this->_prizeUnit);
                $value['lhcContinuein23'] = floatval($value['lhcContinuein23']/$this->_prizeUnit);
                $value['lhcContinuein4'] = floatval($value['lhcContinuein4']/$this->_prizeUnit);
                $value['lhcContinuein5'] = floatval($value['lhcContinuein5']/$this->_prizeUnit);
                $value['lhcContinuenotin23'] = floatval($value['lhcContinuenotin23']/$this->_prizeUnit);
                $value['lhcContinuenotin4'] = floatval($value['lhcContinuenotin4']/$this->_prizeUnit);
                $value['lhcContinuenotin5'] = floatval($value['lhcContinuenotin5']/$this->_prizeUnit);
                $value['lhcContinuecode'] = floatval($value['lhcContinuecode']/$this->_prizeUnit);

         		if($value['lotterySeriesCode'] == '2'){
	         		$userAwardsList[5][$value['lotteryId']][$value['awardGroupId']] = $value;
                }elseif($value['lotterySeriesCode'] == '6'){
	         		$userAwardsList[7][$value['lotteryId']][$value['awardGroupId']] = $value;
         		}elseif($value['lotterySeriesCode'] == '9'){
                    $userAwardsList[6][$value['lotteryId']][$value['awardGroupId']] = $value;
                }else {
	         		$userAwardsList[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']] = $value;
         		}
         	}
         }
         ksort($userAwardsList);
         $userdetail = $this->user->gettopproxydetail($userid);
         if(isset($userdetail['body']['result']['userStruc']) && count($userdetail['body']['result']['userStruc'])>0){
         	$type 	= $userdetail['body']['result']['userStruc']['userLvl']>0 ? 1 : 0;
         	$retType 	= $userdetail['body']['result']['userStruc']['isAllZero'];
	        $this->view->assign('type',$type);
	        $this->view->assign('retType',$retType);
         }
         $this->view->assign('id',$userid);
         $this->view->assign('account',$account);
         $this->view->assign('typepage',$typepage);
         $this->view->assign('aAwardSeris',$aAwardSeris);
         $this->view->assign('aRateName',$aRateName);
         $this->view->assign('aAwardGroup',$aAwardGroup);
         $this->view->assign('aRateIndex',$aRateIndex);
         $this->view->assign('userAwardsList',$userAwardsList);
         $this->view->title = '奖金返点';
		 $this->view->display('admin/user/adminUser_bonusrebate.tpl');
	}
	
	//获取奖金返点
	public function queryusergameawardAction(){

		$lotterySeriesCode = floatval(getSecurityInput($this->_request->getPost('lotterySeriesCode')));
		$lotteryId = floatval(getSecurityInput($this->_request->getPost('lotteryId')));
		$awardGroupId = floatval(getSecurityInput($this->_request->getPost('awardGroupId')));
		$retstatus = floatval(getSecurityInput($this->_request->getPost('retstatus')));
		$userId = floatval(getSecurityInput($this->_request->getPost('userId')));
		// 		$type = floatval(getSecurityInput($this->_request->getPost('type')));
		$awardType = floatval(getSecurityInput($this->_request->getPost('awardType')));
// 		$modifyprize = floatval(getSecurityInput($this->_request->getPost('modifyprize')));
		$sysAwardGroupId = floatval(getSecurityInput($this->_request->getPost('sysAwardGroupId')));
		
		//如果不是当前的直接下级,则返回不让用户查看玩法返点
		$userInfo = $this->getUserInfoById($userId);
		$type = $userInfo['userLvl'] == 0 ? 0 : 1;
// 		$awardType = isset($this->_aRateIndex[$lotterySeriesCode][$retstatus-1]) ? $this->_aRateIndex[$lotterySeriesCode][$retstatus-1] : 1;
		$param =array(
				'lotteryId' 	=> $lotteryId,
				'type' 			=> $type,
				'awardType' 	=> $awardType,
				'userId' 		=> $userId,
				'sysAwardGroupId' => $sysAwardGroupId
		);
		$userAwardGroup = array();
		$result = $this->_gamePrize->queryUserGameAward($param);
		foreach ($result as $key=>$value){
			$userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']][] = $value;
			$userAwardGroup[$value['gameGroupCode']]['cnt'] = isset($userAwardGroup[$value['gameGroupCode']]['cnt']) ? $userAwardGroup[$value['gameGroupCode']]['cnt']+1 : 1;
			$userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt'] = isset($userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt']) ? $userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt']+1 : 1;
		}
		$html = '';
// 		if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
// 			$html .= "<input type='hidden' id='data-lotteryId' value='".$lotteryId."'>";
// 			$html .= "<input type='hidden' id='data-awardGroupId' value='".$sysAwardGroupId."'>";
// 			$html .= "<input type='hidden' id='data-lotterySeriesCode' value='".$lotterySeriesCode."'>";
// 			$html .= "<input type='hidden' id='data-retstatus' value='".$retstatus."'>";
// 			$html .= "<form name='modifyBonusForm' method='post'>";
// 		}
		$html .= "<table class='table table-border'><thead><tr><th>玩法群</th><th>玩法组</th><th>玩法/投注方式</th><th>奖金（元）</th><th>返点</th></tr></thead><tbody>";
		foreach ($userAwardGroup as $key=>$value){
			$i=0;
			foreach ($value as $sub_key=>$sub_value){
				if($sub_key !='cnt'){
					foreach ($sub_value as $sub_key1=>$sub_value1){
						if(is_numeric($sub_key1)){
							$html .= "<tr>";
							if($sub_key1 == '0'){
								if($i == '0'){
									$html .= "<td rowspan='".$userAwardGroup[$key]['cnt']."'>".$sub_value1['groupCodeTitle']."</td>";
								}
								$html .= "<td rowspan='".$userAwardGroup[$key][$sub_key]['cnt']."'>".$sub_value1['setCodeTitle']."</td>";
							} else if ($userAwardGroup[$key]['cnt'] <=1) {
								$html .= "<td>".$sub_value1['groupCodeTitle']."</td>";
							} else if ($userAwardGroup[$key][$sub_key]['cnt']<=1) {
								$html .= "<td>".$sub_value1['setCodeTitle']."</td>";
							}
							$html .= "<td>".$sub_value1['methodCodeTitle']."</td>";//methodCodeTitle
							if(is_array($sub_value1['assistBMBonusList']) && count($sub_value1['assistBMBonusList'])>0){
								$html .= "<td>";
								foreach ($sub_value1['assistBMBonusList'] as $assitValue){
									$html .= "<div>".$assitValue['methodTypeTitle']."&nbsp;&nbsp;".sprintf("%.2f", $assitValue['actualBonus']/$this->_moneyUnit)."</div>";
								}
								$html .= "</td>";
							} else {
								$html .= "<td>".sprintf("%.2f", $sub_value1['actualBonus']/$this->_moneyUnit)."</td>";
							}
// 							if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
// 								$html .= "<td><input name='".$sub_value1['mainBetTypeCode']."' class='w-1' value='".$sub_value1['retVal']/$this->_prizeUnit."' minvalue='".$sub_value1['retVal']/$this->_prizeUnit."' maxvalue='".($sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue)."' onBlur=\"CheckingMaxAllFull(this,'".($sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue)."','".$sub_value1['retVal']/$this->_prizeUnit."')\" />(范围为".$sub_value1['retVal']/$this->_prizeUnit."-".($sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue).")</td>";
// 							}else {
							if(is_array($sub_value1['assistBMBonusList']) && count($sub_value1['assistBMBonusList'])>0 && $sub_value1['betMethodCode'] == 81 ){
                                $html .= "<td>";
                                foreach ($sub_value1['assistBMBonusList'] as $assitValue){
                                    $html .= "<div>&nbsp;&nbsp;".$assitValue['retVal']/$this->_prizeUnit."%</div>";
                                }
                                $html .= "</td>";
                            } else {
                                $html .= "<td>".$sub_value1['retVal']/$this->_prizeUnit."%</td>";
                            }
// 							}
							$html .= "</tr>";
						}
					}
					$i++;
				}
			}
		}
		$html .= "</tbody></table>";
// 		if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
// 			$html .= "</form>";
// 		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo Zend_Json::encode(array('status'=>'ok','html'=>$html));
		exit;
	}
	
	//账号申诉管理->用户申诉审核
	public function comlaintscheckAction() {
    	$this->view->title = "申诉审核";
		 $this->view->display('admin/user/adminUser_comlaintscheck.tpl');
	}
	
	//账号申诉管理->查询为空页面
	public function pleadlistnoneAction() {
    	$this->view->title = "申诉管理";
		 $this->view->display('admin/user/adminUser_pleadlistnone.tpl');
	}

    //用户搜索功能
    public function searchAction() {
        if($this->getRequest()->ispost()) {
            $type = $this->getPost('type');
            if($type=='username') {
                $data['username'] = $this->getPost('typetxt');
            }
            if($type=='email') {
                $data['email'] = $this->getPost('typetxt');
            }
            $data['groupid'] = $this->getPost('groupid');
            $data['regtime'] = $this->getPost('regtime');
            $data['amountfrom'] = $this->getPost('amountfrom');
            $data['amountend'] = $this->getPost('amountend');
            
            $userlist = $this->user->getUserList();
            $searchData = $this->user->getUserListBeyWhere($data);
            $pages = CommonPages::getPages ( 100, 3 );
            $this->view->aUserGroup = $this->getUserGroups();
            $this->view->assign('proxyuser',$searchData);
            $this->view->assign('userlist',$userlist);
            $this->view->assign('pages',$pages);
            $this->view->title = "客户列表";
            $this->view->display('admin/user/adminUser_list.tpl');
        }
    }

    //用户列表【右侧】
    public function viewAction() {
        if($this->getRequest()->isget()) {
            $request = $this->getRequest();
            $userid = $request->getParam('id');
            
            //通过userid从java端读取其本身和下级
            $result = $this->proxyAdm->childlist($userid);
        }
        $proxyData = $this->proxyAdm->topproxylist();//用户列表左侧代理用户列表
        $this->view->aUserGroup = $this->getUserGroups();
        $this->view->assign('childuser',$result);
        $this->view->assign('userlist',$proxyData);
        $this->view->title = "客户列表";
        $this->view->display('admin/user/adminUser_list.tpl');
    }
    
    //获取某个总代下面的所有用户 包括其本身
    public function getchildlistAction() {
        
        $this->_page = intval($this->_request->get("page","1"));
        $data = array();
        $userid = getSecurityInput($this->_request->get('id'));
        $username = strtolower(getSecurityInput(trim($this->_request->get('topname'))));
        $proxyUser = array();
        if($userid>0){
	        $userInfo = $this->user->getusername($userid);
	        $userStruc = isset($userInfo['body']['result']['userStruc']) ? $userInfo['body']['result']['userStruc'] : '';
	        $username = isset($userStruc['account']) ? $userStruc['account'] : '';
        }else if(!empty($username)) {
	        $userInfo = $this->user->getuserbyname($username);
	        $userStruc = isset($userInfo['body']['result']) ? $userInfo['body']['result'] : '';
	        $userid = $userStruc['id'];
        }
        unset($userInfo);
        if(isset($userStruc) && is_array($userStruc)){
        	$userStruc['registerDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($userStruc['registerDate']));
        	$userStruc['registerIp']   = long2ip($userStruc['registerIp']);
        	$userStruc['userLvl']      = $this->getUserType($userStruc['userLvl']);
        	$userStruc['availBal']     = !empty($userStruc['availBal']) ? getMoneyFomat($userStruc['availBal']/$this->_moneyUnit,2) : 0;
	        $proxyUser[] = $userStruc;
        	$this->view->userChain = explode('/', trim($userStruc['userChain'],'/'));
        }
        unset($userStruc);
        $uId = is_numeric($userid) ? intval($userid) : 0;
        $result = $this->proxyAdm->directchildlist($uId,$this->_page, $this->_pageSize);
        foreach($result['body']['result'] as &$v)
        {
            $v['registerDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['registerDate']));
            $v['registerIp']   = long2ip($v['registerIp']);
            $v['userLvl']      = $this->getUserType($v['userLvl']);
            $v['availBal']     = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
            $proxyUser[] = $v;
        }
        $this->_total = $result['body']['pager']['total'];
        unset($result);
        $pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
        $this->view->aUserGroup = $this->getUserGroups();
        $this->view->assign('pages',$pages);
        $this->view->assign('proxyuser',$proxyUser);
        $this->view->title = '客户列表';
        $this->view->id = $uId;
        $this->view->topName = $username;
		$this->view->assign('typeN',$username);
		
        $this->view->display('admin/user/adminUser_list.tpl');
    }
	

    //Ajax 调用显示用户详情的数据给前台
    public function userdetailAction()
    {
        $request  = $this->getRequest();
        $userid   = getSecurityInput($request->get('id'));
        $typepage = getSecurityInput($request->get('typepage','1'));
        $userdetail = $this->user->gettopproxydetail($userid);
        $loginStruc = $userdetail['body']['result']['loginStruc'];
        $userStruc  = $userdetail['body']['result']['userStruc'];
        if(count($loginStruc)){
	        foreach($loginStruc as $key=>$v)
	        {
	             $loginStruc[$key]['loginDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['loginDate']));
	             $loginStruc[$key]['loginIp']	= long2ip($v['loginIp']);
	        }
        }
        if(count($userStruc)>0){
	        $registerData 	= $userStruc['registerDate'];
	        $registerIp 	= $userStruc['registerIp'];
	        $birthday 		= $userStruc['birthday'];
	        $userStruc['registerIp'] 	= !empty($registerIp)   ? long2ip($registerIp) : '';
	        $userStruc['registerDate'] 	= !empty($registerData) ? date('Y-m-d H:i:s',getSrtTimeByLong($registerData)) : '';
	        $userStruc['birthday'] 		= !empty($birthday)     ? date('Y-m-d',getSrtTimeByLong($birthday)) : '';
	        $parsebirthday 				= $userStruc['birthday'];
	        $birthdayData  				= explode('-', $parsebirthday);
	        $userStruc['sltyear'] 		= !empty($birthdayData[0]) ? $birthdayData[0] : '' ;
	        $userStruc['sltmonth'] 		= !empty($birthdayData[1]) ? $birthdayData[1] : '' ;
	        $userStruc['sltday'] 		= !empty($birthdayData[2]) ? $birthdayData[2] : '' ;
	        $userStruc['vcellphone'] 	= !empty($userStruc['cellphone']) ? substr_replace($userStruc['cellphone'], '****', 3,2) : '' ;
	        $userStruc['vvipCellphone'] = !empty($userStruc['vipCellphone']) ? substr_replace($userStruc['vipCellphone'], '****', 3,4) : '' ;
	        $userStruc['availBal'] 		= !empty($userStruc['availBal'])  ? getMoneyFomat($userStruc['availBal']/$this->_moneyUnit,2)  : '0';
	        $userStruc['freezeBal'] 	= !empty($userStruc['freezeBal']) ? getMoneyFomat($userStruc['freezeBal']/$this->_moneyUnit,2) : '0';
	        $userStruc['teamBal'] 		= !empty($userStruc['teamBal'])   ? getMoneyFomat($userStruc['teamBal']/$this->_moneyUnit,2)   : '0';
	        $userStruc['email'] 		= !empty($userStruc['email'])   ? substr_replace($userStruc['email'], '****', 2, 4)  : '';
	        if(isset($userStruc['qqStruc'])){
	        	foreach ($userStruc['qqStruc'] as $key=>$value){
	        		$userStruc['qqStruc'][$key]['vqq'] = substr_replace($value['qq'], '****', 2,3);
	        	}
	        	$this->view->assign('qq',$userStruc['qqStruc']);
	        }
	        if(isset($userStruc['quStruc'])){
	        	$this->view->assign('qu',$userStruc['quStruc']);
	        }
        }
        $this->view->assign('userStruc',$userStruc);
        $this->view->assign('loginStruc',$loginStruc);
        $this->view->assign('typepage',$typepage);
        $this->view->title = '基本资料';
        $this->view->display('admin/user/adminUser_userdetail.tpl');
    }
    
    //保存用户详细信息数据
    public function saveuserdetailAction()
    {
        $request 	= $this->getRequest();
        $userid 	= getSecurityInput($request->getPost('id'));
        $typepage 	= getSecurityInput($request->getPost('typepage','1'));
        $qq 		= $request->getPost('qq',array());
        $sex 		= getSecurityInput($request->getPost('sex'));
        $cipher 	= getSecurityInput($request->getPost('cipher'));
        $cellphone 	= getSecurityInput($request->getPost('cellphone'));
        $vipCellphone 	= getSecurityInput($request->getPost('vipCellphone'));
        $sltYear 		= intval(getSecurityInput($request->getPost('sltYear')));
        $sltMonth 		= intval(getSecurityInput($request->getPost('sltMonth')));
        $sltDay 		= intval(getSecurityInput($request->getPost('sltDay')));
        $birthday = '';
        if($sltYear>0 && $sltMonth>0 && $sltDay>0){
        	$birthday = timetoMicro(mktime(0,0,0,$sltMonth,$sltDay,$sltYear));
        }
        if(count($qq)>0){
			foreach ($qq as $key=>$value){
				$qq[$key]['nickName'] = getSecurityInput($value['nickName']);
				$qq[$key]['qq'] 	  = intval(getSecurityInput($value['qq']));
			}
        }
        $data = array(
            'body'=>array(
                'param'=>array(
                    'id'			=>intval($userid),
                    'sex'			=>intval($sex),
                    'cipher'		=>$cipher,
                    'cellphone'		=>$cellphone,
                    'vipCellphone'	=>$vipCellphone,
                    'birthday'		=>$birthday,
                    'qqStruc'		=>$qq
               )
            )
        );
        $resultdata = $this->user->savepersonalinfo($data);
        if(isset($resultdata['head']['status'])&& $resultdata['head']['status'] =='0'){
        	$urlArray = array(1=>'/admin/user/list/','/admin/proxy/index/','/admin/user/accomplaints/');
        	echo Zend_Json::encode(array('isSuccess'=>1,'url'=>$urlArray[$typepage]));
        	exit;
        } else {
        	echo Zend_Json::encode(array('isSuccess'=>0));
        	exit;
        }
    }

    //冻结用户 ajax 调用
    public function freezeuserAction() {
        if ($this->getRequest()->ispost()) {
            $data['userid'] = intval(getSecurityInput($this->_request->getPost('userid')));
            $data['range']  = getSecurityInput($this->_request->getPost('range'));
            $data['method'] = getSecurityInput($this->_request->getPost('method'));
            $data['memo']   = getSecurityInput($this->_request->getPost('memo'));
            $data['freezeAccount'] = $this->_sessionlogin->info['account'];
            if(getStrLen($data['memo'])>200){
            	echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'冻结原因不能超过200个字符!'));
            	exit;
            }
            $tmpuser = $this->user->freezeuser($data);
            $this->appendErrFrmCls($this->user);
            if($this->isErrFree()) {
                //更新前台用户session的冻结信息
                $this->updateFrezzeUserStatus($data);
                //冻结全部下级 需要更新所有下级的session信息
				if($data['range'] ==0){
	                $jdata['body']['param']['userId'] = $data['userid'];
	                $userIdList = $this->proxyAdm->querySubUsers($jdata);
	                $this->appendErrFrmCls($this->proxyAdm);
					if($this->isErrFree() && isset($userIdList['body']['result'])){
                		foreach ($userIdList['body']['result'] as $value){
                			$data['userid'] = $value['id'];
                			//更新前台用户session的冻结信息
                			$this->updateFrezzeUserStatus($data);
                		}
	                }
				}
            } 
            if($this->isErrFree()) {
            	$result["isSuccess"] = 1;
            } else {
                $result = $this->getErrJson();
            }
            echo Zend_Json::encode($result);
            exit;
        }
        $userid = $this->_request->getParam('id');
        $username = $this->user->getusername($userid);
        $this->view->assign('userLvl', $username['body']['result']['userStruc']['userLvl']);
        $this->view->assign('userid',$username['body']['result']['userStruc']['id']);
        $this->view->assign('username',$username['body']['result']['userStruc']['account']);
        $this->view->typepage = $this->_request->getParam('typepage');
		$this->view->viplevel = $this->_request->getParam('viplevel');
		$this->view->path_img = $this->_request->getParam('path_img');
		$this->view->title = "冻结用户";
        $this->view->display('admin/user/adminUser_freezeaccount.tpl'); 
    }
    
    //解冻用户 ajax调用
    public function unfreezeuserAction() {
        $request = $this->getRequest();
        if($this->getRequest()->ispost()) {
            $data['userid'] = intval(getSecurityInput($this->_request->getPost('userid')));
            $data['range']  = getSecurityInput($this->_request->getPost('range'));
            $data['memo']   = getSecurityInput($this->_request->getPost('memo'));
            $data['freezeAccount'] = $this->_sessionlogin->info['account'];
            
            if(getStrLen($data['memo'])>200){
            	echo Zend_Json::encode(array('isSuccess'=>'0','data'=>'解冻原因不能超过200个字符!'));
            	exit;
            }
            $unfreezeuser = $this->user->unfreezeuser($data);
            $this->appendErrFrmCls($this->user);
            if($this->isErrFree()) {
                //更新前台用户session的冻结信息
                $this->updateUnFrezzeUserStatus($data);
                //冻结全部下级 需要更新所有下级的session信息
                if($data['range'] ==0){
                	$jdata['body']['param']['userId'] = $data['userid'];
                	$userIdList = $this->proxyAdm->querySubUsers($jdata);
                	$this->appendErrFrmCls($this->proxyAdm);
                	if($this->isErrFree() && isset($userIdList['body']['result'])){
                		foreach ($userIdList['body']['result'] as $value){
                			$data['userid'] = $value['id'];
                			//更新前台用户session的冻结信息
                			$this->updateUnFrezzeUserStatus($data);
                		}
                	}
                }
            }
            if($this->isErrFree()) {
            	$result["isSuccess"] = 1;
            } else {
                $result = $this->getErrJson();
            }
            echo Zend_Json::encode($result);die;
        }
        $userid = $this->_request->getParam('id');
        $userdetail = $this->user->gettopproxydetail($userid);
        $this->view->assign('userid', $request->getParam('id'));
        $this->view->assign('account', $userdetail['body']['result']['userStruc']['account']);
        $this->view->assign('userLvl', $userdetail['body']['result']['userStruc']['userLvl']);
		$this->view->assign('viplevel', $request->getParam('viplevel'));
		$this->view->assign('path_img', $request->getParam('path_img'));
		$this->view->assign('freezeMemo', $userdetail['body']['result']['userStruc']['freezeMemo']);//冻结原因
        $this->view->assign('freezeMethod', $userdetail['body']['result']['userStruc']['freezeMethod']);//冻结范围
        $this->view->typepage = $this->_request->getParam('typepage');
        $this->view->title = "解冻用户";
        $this->view->display('admin/user/adminUser_thawingaccount.tpl');
    }
    
    //冻结用户列表和搜索
    public function freezeuserlistAction() {
        
        $this->_page = intval($this->_request->getParam("page","1"));
        $data = array();

        $searchtype = getSecurityInput($this->getParam('searchtype'));
        $searchTxt = getSecurityInput($this->getParam('searchtypetxt'));
        if(!empty($searchTxt)){
           $data['username'] = $searchTxt;
        }
        
        $freezeUserList = $this->user->getFreezeUserList($data,$this->_page, $this->_pageSize);
        if(!empty($freezeUserList['body']['result'])) {
            foreach ($freezeUserList['body']['result'] as &$v) {
                $v['freezeDate'] = !empty($v['freezeDate']) ? date('Y-m-d H:i:s',getSrtTimeByLong($v['freezeDate'])) : '';
                $v['availBal']   = !empty($v['availBal']) ? getMoneyFomat($v['availBal']/$this->_moneyUnit,2) : 0;
                $v['userLvl']    = $this->getUserType($v['userLvl']);
            }
        }
        $this->_total = $freezeUserList['body']['pager']['total'];
        $pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
        $this->view->assign('pages',$pages);
        $this->view->assign('freezeuserlist',$freezeUserList['body']['result']);
        $this->view->searchtype = $searchtype;
        $this->view->searchtypetxt = $searchTxt;
        $this->view->title = '冻结名单';
        $this->view->display('admin/user/adminUser_freezelist.tpl');
    }
    
    //历史冻结用户列表
    public function freezeuserhistorylistAction() {
    	
        $this->_page = intval($this->_request->getParam("page","1"));
        $data = new ArrayObject(array());
        $userName = $this->_request->getParam ( "userName" );
        $search = $this->_request->getParam("search");
        if(!empty($userName)){
        	$data = array();
            $data['account'] = $userName;
        }
        $freezeUserList = $this->user->getFreezeUserHistoryList($data,$this->_page, $this->_pageSize);
	
        if(!empty($freezeUserList['body']['result'])) {
            foreach ($freezeUserList['body']['result'] as &$v) {
            	$v['userLvl']    = $this->getUserType($v['userGroup']);
                $v['freeDate'] 	 =!empty($v['freeDate']) ?  date('Y-m-d H:i:s',getSrtTimeByLong($v['freeDate'])):'';
                $v['frozenDate'] = !empty($v['frozenDate']) ? date('Y-m-d H:i:s',getSrtTimeByLong($v['frozenDate'])) : '';
                $v['restBal']    = !empty($v['restBal']) ? getMoneyFomat($v['restBal']/$this->_moneyUnit,2) : 0;
                $v['operator']   = $v['operator']!='null' ? $v['operator'] : '';
            }
        }
        $this->_total = !empty($freezeUserList['body']['pager']['total']) ? $freezeUserList['body']['pager']['total'] : 0 ;
        $pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
        $this->view->username = $userName;
        $this->view->assign('pages',$pages);
        $this->view->assign('freezeuserlist',$freezeUserList['body']['result']);
        $this->view->title = '解冻记录';
        $this->view->display('admin/user/adminUser_listhistory.tpl');
    }
    
    //帐号申诉管理  1是邮箱2是信息
    public function accomplaintsAction()  {
    	
        $this->_page = intval($this->_request->getParam("page","1"));
        $username 	= getSecurityInput( $this->_request->getPost("username",''));
        $checker 	= getSecurityInput($this->_request->getPost("checker",''));
        $status 	= getSecurityInput($this->_request->getPost("status",'0'));
        $pleadtype  = getSecurityInput($this->_request->getPost("pleadtype",'0'));
        $search 	= getSecurityInput($this->_request->getPost("search",1));
        $data = new ArrayObject(array());
        if(!empty($username)){
            $data['account'] = $username;
        }
        if(!empty($checker)){
            $data['operater'] = $checker;
        }
        if(!empty($status) && $status!== '0'){
        	$data['passed'] = intval($status);
        }
        if($pleadtype!=='0'){
            $data['appealType'] = intval($pleadtype);
        }
        $pleaduser = $this->user->getAccomplaintsList($data, $this->_page, $this->_pageSize);
		//print_r($pleaduser);
		//exit();
        $this->_total = $pleaduser['body']['pager']['total'];
        $pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pageSize );
        $pleadData = array();
        if(isset($pleaduser['body']['result']) && count($pleaduser['body']['result'])>0)
        {
        	$pleadData = $pleaduser['body']['result'];
            foreach ($pleadData as $k=>$v)
            {
                $pleadData[$k]['createDate'] = date('Y-m-d H:i:s',getSrtTimeByLong($v['createDate']));
                $pleadData[$k]['passDate'] = !empty($v['passDate']) ? date('Y-m-d H:i:s',getSrtTimeByLong($v['passDate'])) : '';
				$pleadData[$k]['viplevel'] = $v['vipLvl'];
            }
        }
        
        $this->view->username   = $username;
        $this->view->checker 	= $checker;
        $this->view->status 	= $status;
        $this->view->pleadtype  = $pleadtype;
        $this->view->assign('pleadeuserlist',$pleadData);
        $this->view->assign('pages',$pages);
        $this->view->title = '账号申诉管理';
        $this->view->display('admin/user/adminUser_pleadlist.tpl');
    }

    //账户申诉审核详情
    public function pleadAction()
    {

        $request = $this->getRequest();
        if($request->ispost())
        {
            $data['id'] 		= intval(getSecurityInput($request->getpost('id','')));
            $data['passed'] 	= intval(getSecurityInput($request->getpost('passed','')));
            if($data['passed'] ==1){
            	$data['activeDate'] = intval(getSecurityInput($request->getpost('activedate','')));
            }
            $data['memo'] = getSecurityInput($request->getpost('memo',''));
            if(getStrLen($data['memo'])>200){
            	echo Zend_Json::encode(array('isSuccess'=>0,'不能超过200个字符'));
            	exit;
            }
            $account 	= getSecurityInput($request->getpost('account',''));
            $email 		= getSecurityInput($this->_request->getpost('email',''));
            $appealType = getSecurityInput($this->_request->getpost('appealType',''));
            $tmpdata 	= $this->user->userAppealAudit($data); //在这里要做处理
//             $this->appendErrFrmCls($this->user);
            if(isset($tmpdata['head']['status'])&& $tmpdata['head']['status'] =='0'){
            	$usernsource = $this->user->getuserbyname($account);
            	//审核不通过
                if($data['passed'] == 2){
                	$this->_mail->sendVerifiedMail(
                			$usernsource['body']['result']['id'],
                			$account,
                			29,
                			$email
                	);
                } else if($data['passed'] ==1 && in_array($appealType, array(1,2))) {//审核通过
                    $toolaction = 0;
                    if($appealType=='2')
                    {
                        $reurl      = '/appeal/resetsecrityinfo?';
                        $thdtitle   = '重置安全信息';
                        $toolaction = 5;
                    }
                    if($appealType =='1')
                    {
                        $reurl      = '/appeal/entermail?';
                        $thdtitle   = '重置安全邮箱';
                        $toolaction = 8;
                    }
                    $param = array();
                    $param["name"]   = $account;
                    $param["userId"] = $usernsource['body']['result']['id'];
                    $param["time"]   = number_format(getSendTime(),0,'','');
                    $param["chkAct"] = "resetSecurityQuestion";
                    $param["active"] = intval($data['activeDate']);
                    $param["BCODE"] = $this->_mail->encrypt($param);
                    
                    //通知发送邮件
                    $this->_mail->sendVerifiedMail(
                    		$usernsource['body']['result']['id'],
                    		$account,
                    		30,
                    		$email,
                    		$reurl,
                    		$param,
                    		$toolaction,
                    		$thdtitle
                    );
                }
                echo Zend_Json::encode(array('isSuccess'=>1));
                exit;
            } else {
            	echo Zend_Json::encode(array('isSuccess'=>0,'data'=>'审核失败,请重试'));
            	exit;
            }
        }
        //调用java接口读取用户申诉信息
        $pid     = $request->getParam('id');
        $tmpdata = $this->user->pleadinfo($pid);
		$pleaduserinfo = $tmpdata['body']['result'];
		if(isset($pleaduserinfo['email'])){
				$email=substr($pleaduserinfo['email'],0,2).'****'.substr($pleaduserinfo['email'],6);
				$pleaduserinfo['emailSecret']=$email;
			}
        $this->view->assign('pleaduserinfo',$pleaduserinfo);
        $this->view->assign('cardstruc',$tmpdata['body']['result']['cardStruc']);
        $this->view->assign('idCopy',$tmpdata['body']['result']['idCopy']);
        $this->view->title = "申诉审核";
        $this->view->display('admin/user/adminUser_comlaintscheck.tpl');
    }
    
    //用户申诉详情察看
    public function pleaddetailinfoAction()
    {
        $request    = $this->getRequest();
        $userac     = getSecurityInput($request->getParam('ac'));
        $typepage   = getSecurityInput($request->getParam('typepage','1'));
        $userdetail = $this->user->getpleadinfo($userac);
        $loginStruc = $userdetail['body']['result']['loginStruc'];
        $userStruc = $userdetail['body']['result']['userStruc'];
        if (count($loginStruc)) {
            foreach ($loginStruc as $key => $v) {
                $loginStruc[$key]['loginDate'] = date('Y-m-d H:i:s', getSrtTimeByLong($v['loginDate']));
                $loginStruc[$key]['loginIp'] = long2ip($v['loginIp']);
            }
        }
        if(count($userStruc)>0){
	        $registerData 	= $userStruc['registerDate'];
	        $registerIp 	= $userStruc['registerIp'];
	        $birthday 		= $userStruc['birthday'];
	        $userStruc['registerIp'] 	= !empty($registerIp)   ? long2ip($registerIp) : '';
	        $userStruc['registerDate'] 	= !empty($registerData) ? date('Y-m-d H:i:s',getSrtTimeByLong($registerData)) : '';
	        $userStruc['birthday'] 		= !empty($birthday)     ? date('Y-m-d',getSrtTimeByLong($birthday)) : '';
	        $parsebirthday 				= $userStruc['birthday'];
	        $birthdayData  				= explode('-', $parsebirthday);
	        $userStruc['sltyear'] 		= !empty($birthdayData[0]) ? $birthdayData[0] : '' ;
	        $userStruc['sltmonth'] 		= !empty($birthdayData[1]) ? $birthdayData[1] : '' ;
	        $userStruc['sltday'] 		= !empty($birthdayData[2]) ? $birthdayData[2] : '' ;
	        $userStruc['vcellphone'] 	= !empty($userStruc['cellphone']) ? substr_replace($userStruc['cellphone'], '****', 3,4) : '' ;
	        $userStruc['vvipCellphone'] = !empty($userStruc['vipCellphone']) ? substr_replace($userStruc['vipCellphone'], '****', 3,4) : '' ;
	        $userStruc['availBal'] 		= !empty($userStruc['availBal'])  ? getMoneyFomat($userStruc['availBal']/$this->_moneyUnit,2)  : '0';
	        $userStruc['freezeBal'] 	= !empty($userStruc['freezeBal']) ? getMoneyFomat($userStruc['freezeBal']/$this->_moneyUnit,2) : '0';
	        $userStruc['teamBal'] 		= !empty($userStruc['teamBal'])   ? getMoneyFomat($userStruc['teamBal']/$this->_moneyUnit,2)   : '0';
	        if(isset($userStruc['qqStruc'])){
	        	foreach ($userStruc['qqStruc'] as $key=>$value){
	        		$userStruc['qqStruc'][$key]['vqq'] = substr_replace($value['qq'], '****', 2,3);
	        	}
	        	$this->view->assign('qq',$userStruc['qqStruc']);
	        }
	        if(isset($userStruc['quStruc'])){
	        	$this->view->assign('qu',$userStruc['quStruc']);
	        }
			if(isset($userStruc['email'])){
				$email=substr($userStruc['email'],0,2).'****'.substr($userStruc['email'],6);
				$userStruc['email']=$email;
			}
        }
        $this->view->assign('userStruc',$userStruc);
        $this->view->assign('loginStruc',$loginStruc);
        $this->view->assign('typepage',$typepage);
        $this->view->title = '基本资料';
        $this->view->display('admin/user/adminUser_userdetail.tpl');
    }

    //开启关闭VIP
    public function setvipAction(){
    	$id = getSecurityInput($this->_request->get('id',''));
    	$status = getSecurityInput($this->_request->get('status','0'));
    	if($id=='' || $status==''){
    		echo Zend_Json::encode(array('status'=>'error','data'=>'设置错误,请重新开启'));
    		exit;
    	}
    	if($status =='0'){
    		$vipLvl = 0 ;
    		$des = '关闭VIP';
    	} 
		if($status =='1'){
    		$vipLvl = 1 ;
    		$des = '开启VIP1';
    	}
		if($status =='2'){
    		$vipLvl = 2 ;
    		$des = '开启VIP2';
    	}
		if($status =='3'){
    		$vipLvl = 3 ;
    		$des = '开启VIP3';
    	}
		if($status =='4'){
    		$vipLvl = 4 ;
    		$des = '开启VIP4';
    	}
		
    	$data['param']['id']     = intval($id);
    	$data['param']['vipLvl'] = intval($vipLvl);
    	$aUri['queryUserByCriteria_proxy'] = 'setVip';
    	$res = $this->_clientobject->inRequestV2($data, $aUri);
    	if(isset($res['head']['status']) && $res['head']['status'] == '0'){
    		
    		//更新所有用户的 预留验证信息
    		$sessionData['vipLvl'] = $vipLvl;
    		$this->_sessionTool->update($data['param']['id'],$sessionData);
    		unset($sessionData);
    		
    		
    		echo Zend_Json::encode(array('status'=>'ok','data'=>$des.'成功!'));
    		exit;
    	} else {
    		echo Zend_Json::encode(array('status'=>'error','data'=>$des.'失败!'));
    		exit;
    	}
    }
    
    //获取手机令牌
    public function getphonesecurityAction(){
    	$id = getSecurityInput($this->_request->getParam('id'));
    	$result = $this->user->getSecurityCardNumber($id);
    	$result['BIND_DATE'] = date('Y-m-d H:i:s',getSrtTimeByLong($result['BIND_DATE']));
    	$result['LOCK_STATUS'] = $this->_phoneSecuritySession->getPhoneSecurityStatus($id);
    	$this->view->_GET = $_GET;
    	$this->view->result = $result;
    	$this->view->title='手机令牌';
    	$this->view->display('admin/user/adminUser_phonesecurity.tpl');
    }
    
    //解绑宝开安全中心
    public function unbindphonesecurityAction(){
    	$userId = intval(getSecurityInput($this->_request->getPost('id')));
    	if($userId <=0){
    		echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'请选择用户'));
    		exit;
    	}
    	$param = array(
    			'userId'=> $userId,
    			'sercurityCode'=>'',
    			'unbindType' => 1,
    	);
    	//解除绑定安全中心
    	$status = $this->user->unBindMobileToken($param);
    	if($status){
    		//更新所有用户的手机令牌绑定信息
    		$sessionData['serialNumber'] = '';
    		$this->_sessionTool->update($userId,$sessionData);
    		unset($sessionData);
    		 
    		echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>$this->getError('102111')));
    		exit;
    	} else {
    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102112')));
    		exit;
    	}
    }
    
    //解锁宝开安全中心
    public function unlockphonesecurityAction(){
    	$id = intval(getSecurityInput($this->_request->getPost('id')));
    	if($id <=0){
    		echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'请选择用户'));
    		exit;
    	}
    	if($this->_phoneSecuritySession->updatePhoneSecurityStatus($id)){
    		echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>$this->getError('102111')));
    		exit;
    	} else {
    		echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'解锁失败'));
    		exit;
    	}
    	
    }

    
    
    //获取所有用户组
    public function getUserGroups(){
    	for ($i=-1;$i<=11;$i++){
    		$aUserGroup[$i] = $this->getUserType($i);
    	}
    	return $aUserGroup;
    }
    
    //更新前台用户session的冻结信息
    private function updateFrezzeUserStatus($data){
    	
    	if($data['method'] == 1){
    		$this->_sessionTool->truncate($data['userid']);
    	} else {
    		$sessionData['freezer'] = $this->_sessionlogin->info['id'];//冻结ID
    		$sessionData['freezeAccount'] = $data['freezeAccount'];//冻结人
    		$sessionData['freezeMemo'] = $data['memo'];//冻结原因
    		$sessionData['freezeDate'] = getSendTime();//冻结时间
    		$sessionData['isFreeze'] = 1;//后台管理员冻结 1申诉冻结2
    		$sessionData['freezeMethod'] = $data['method'];//冻结方式
    		
    		$this->_sessionTool->update($data['userid'],$sessionData);
    	}
    }
    
    //更新前台用户session的解冻信息
    private function updateUnFrezzeUserStatus($data){
    	
    	$sessionData['freezer'] = $this->_sessionlogin->info['id'];//冻结ID
    	$sessionData['freezeAccount'] = $data['freezeAccount'];//冻结人
    	$sessionData['freezeMemo'] = $data['memo'];//冻结原因
    	$sessionData['isFreeze'] = 0;//后台管理员冻结 1申诉冻结2
    	$sessionData['freezeMethod'] = 0;//冻结方式
    	
    	$this->_sessionTool->update($data['userid'],$sessionData);
    }

    //一代回收导页
    public function levelrecycleAction() {
        $type = getSecurityInput($this->getParam('type'));
        $this->view->title = "一代回收";
        if ($type == 'list') {
            //回收管理
            $this->view->display('admin/user/adminUser_levelrecycleinfo.tpl');
        } else {
            //回收纪录
            $this->view->display('admin/user/adminUser_levelrecyclehistory.tpl');
        }
    }

    //一代回收用户搜索
    public function levelrecyclelistAction() {
        $data = array();
        $searchTxt = getSecurityInput($this->getParam('searchtypetxt'));

        if (!empty($searchTxt)) {
            $data['username'] = $searchTxt;
        }

        $levelRecycleUserInfo = $this->user->getLevelRecycleUserInfo($data, $this->_page, $this->_pageSize);
		
        $userInfo = $levelRecycleUserInfo['body']['result'];
        $format_lastLoginDate = date('Y-m-d H:i:s', getSrtTimeByLong($userInfo['lastLoginDate']));
        $format_availBal = $userInfo['availBal'] > 0 ? getMoneyFomat($userInfo['availBal'] / $this->_moneyUnit, 2) : 0;
        $format_availPtBal = $userInfo['availPtBal'] > 0 ? getMoneyFomat($userInfo['availPtBal'] / $this->_moneyUnit, 2) : 0;
        $format_userLvl = $this->getUserType($userInfo['userLvl']);
        $format_lastLoginIp   = long2ip($userInfo['lastLoginIp']);
		//$viplevel = $userInfo['vipLvl'];
        
        $this->view->searchtypetxt = $searchTxt;
        $this->view->assign('userInfo', $userInfo);
        $this->view->assign('format_lastLoginDate', $format_lastLoginDate);
        $this->view->assign('format_availBal', $format_availBal);
        $this->view->assign('format_availPtBal', $format_availPtBal);
        $this->view->assign('format_userLvl', $format_userLvl);
        $this->view->assign('format_lastLoginIp', $format_lastLoginIp);
        $this->view->display('admin/user/adminUser_levelrecycleinfo.tpl');
    }

    //一代回收申请页面
    public function applylevelrecycleAction() { 
        $this->view->title = "一代回收";
        $this->view->assign('userId', getSecurityInput($this->_request->getPost('userId')));
        $this->view->assign('account', getSecurityInput($this->_request->getPost('account')));
		$this->view->assign('viplevel', getSecurityInput($this->_request->getPost('viplevel')));
		
		        $this->view->assign('topAgent', getSecurityInput($this->_request->getPost('topAgent')));
        $this->view->assign('availBal', getSecurityInput($this->_request->getPost('availBal')));
        $this->view->assign('lastLoginDate', getSecurityInput($this->_request->getPost('lastLoginDate')));
        $this->view->assign('format_lastLoginDate', getSecurityInput($this->_request->getPost('format_lastLoginDate')));
        $this->view->assign('format_availBal', getSecurityInput($this->_request->getPost('format_availBal')));
        $this->view->assign('lastLoginIp', getSecurityInput($this->_request->getPost('lastLoginIp')));
        $this->view->assign('format_lastLoginIp', getSecurityInput($this->_request->getPost('format_lastLoginIp')));
        $this->view->assign('availPtBal', getSecurityInput($this->_request->getPost('availPtBal')));
        $this->view->assign('format_availPtBal', getSecurityInput($this->_request->getPost('format_availPtBal')));
        $this->view->assign('lastLoginAddress', getSecurityInput($this->_request->getPost('lastLoginAddress')));
        $this->view->display('admin/user/adminUser_levelrecycleapply.tpl');
    }

    //送出一代回收申请
    public function sendlevelrecycleAction() {
        $data = array();
        $data['userId'] = getSecurityInput($this->_request->getPost('userId'));
        $data['account'] = getSecurityInput($this->_request->getPost('account'));
        $data['topAgent'] = getSecurityInput($this->_request->getPost('topAgent'));
        $data['availBal'] = getSecurityInput($this->_request->getPost('availBal'));
        $data['availPtBal'] = getSecurityInput($this->_request->getPost('availPtBal'));
        $data['recycleReason'] = getSecurityInput($this->_request->getPost('recycleReason'));
        $data['lastLoginDate'] = getSecurityInput($this->_request->getPost('lastLoginDate'));
        $data['lastLoginIp'] = getSecurityInput($this->_request->getPost('lastLoginIp'));
        $data['lastLoginAddress'] = getSecurityInput($this->_request->getPost('lastLoginAddress'));
        $data['operator'] = $this->_sessionlogin->info['account'];

        $applyResult = $this->user->sendLevelRecycleApply($data);
        echo Zend_Json::encode($applyResult);
        exit;
    }

    //一代回收纪录
    public function levelrecyclehistoryAction() {
        //当前页吗
        $page = intval(getSecurityInput($this->_request->getParam("page",1)));
        //页数大小
        $pageSize = intval(getSecurityInput($this->_request->getParam("pageSize",10)));      

        $data = array();
        
        $searchTxt = getSecurityInput($this->getParam('searchtypetxt'));
        $data['username'] = $searchTxt;        
        $levelRecycleHistoryList = $this->user->getLevelRecycleHistoryList($data, $page, $pageSize);
		//print_r($levelRecycleHistoryList);
		//exit();
        $historyList = $levelRecycleHistoryList['body']['result'];
        $format_historyList = array();
        
        foreach ($historyList as $history) {
            $history['createDate'] = date('Y-m-d H:i:s', getSrtTimeByLong($history['createDate']));
            $history['activityDate'] = $history['activityDate'] != '' ? date('Y-m-d H:i:s', getSrtTimeByLong($history['activityDate'])) : '';
            $history['availBal'] = $history['availBal'] > 0 ? getMoneyFomat($history['availBal'] / $this->_moneyUnit, 2) : 0;
            $history['availPtBal'] = $history['availPtBal'] > 0 ? getMoneyFomat($history['availPtBal'] / $this->_moneyUnit, 2) : 0;
            $history['origRecycleReason'] = $history['recycleReason'];
            $history['recycleReason'] = (mb_strlen($history['recycleReason']) > 15) ? mb_substr($history['recycleReason'], 0, 15, 'UTF-8') . "..." : $history['recycleReason'];
            $history['format_lastLoginDate'] = date('Y-m-d H:i:s', getSrtTimeByLong($history['lastLoginDate']));
            $history['format_lastLoginIp'] = long2ip($history['lastLoginIp']);
			$history['viplevel'] = $history['vipLvl'];
            array_push($format_historyList, $history);
    }
        
        $this->_total = $levelRecycleHistoryList['body']['pager']['total'];
        $pages = CommonPages::getPages($this->_total, $page, $pageSize);
        $this->view->assign('pages',$pages);
        $this->view->assign('page',$page);
        $this->view->assign('pageSize',$pageSize);
        $this->view->searchtypetxt = $searchTxt;
        $this->view->assign('levelRecycleHistoryList', $format_historyList);
        $this->view->display('admin/user/adminUser_levelrecyclehistory.tpl');
    }
    
    //一代回收个别功能呼叫
    public function resetlevelrecycleAction() {        
        $data = array();
        $data['actionName'] = getSecurityInput($this->getParam('actionName'));
        $data['account'] = getSecurityInput($this->getParam('account'));
        $data['userId'] = getSecurityInput($this->getParam('userId'));
        $data['operator'] = getSecurityInput($this->getParam('operator'));
        $data['id'] = getSecurityInput($this->getParam('id'));
        $data['recycleStatus'] = getSecurityInput($this->getParam('recycleStatus'));
        $response = $this->user->callRecycleAction($data);
        echo Zend_Json::encode($response);
        exit;        
    }
    
	//獎金模式頁面
	public function userprizemodeAction()
	{
		
		$request = $this->getRequest();
		$userid  = getSecurityInput($request->get('id'));
		$userLvl  = getSecurityInput($request->get('userLvl'));
		$account  = getSecurityInput($request->get('account'));
		$viplevel  = getSecurityInput($request->get('viplevel'));
		$path_img  = getSecurityInput($request->get('path_img'));
		
		if(isAjaxRequest())
		{
			$status  = getSecurityInput($request->getPost('status'));
			$is_include_down  = getSecurityInput($request->getPost('is_include_down'));
			//呼叫 JAVA API
			$data['param']['userId'] = intval($userid);
			$data['param']['type'] = intval(1);	//1:獎金返點 2:超级2000
			$settMode = ($is_include_down == "true")? 2 : 1;
			$data['param']['settMode'] = intval($settMode);	//1:本人 2:本人及下级
			$data['param']['status'] = intval($status);	//1:開 0:關
			//print_r($data);
			$aUri['queryUserByCriteria_proxy'] = 'bizSwitch';
			$res = $this->_clientobject->inRequestV2($data, $aUri);
			//print_r($res);
			//exit;
		}
		
		//呼叫 JAVA API
		$data['param']['userId'] = intval($userid);
		$data['param']['type'] = intval(1);	//1:獎金返點 2:超级2000 3:六合彩
		$aUri['queryUserByCriteria_proxy'] = 'queryBizSwitch';
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		//print_r($res);
		//exit();
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){	//成功
			if(isset($res['body']['result']['status']))
			{
				$status = $res['body']['result']['status'];	//1:開 0:關
				$this->view->assign('status', $status);
				$this->view->assign('userid', $userid);
				$this->view->assign('userLvl', $userLvl);
				$this->view->assign('account', $account);
				$this->view->assign('viplevel', $viplevel);
				$this->view->assign('path_img', $path_img);
				$this->view->display('admin/user/adminUser_userprizemode.tpl');
			}
		}
	}

	public function user2000pointAction()
	{
		
		
		$request = $this->getRequest();
		$userid  = getSecurityInput($request->get('id'));
		$userLvl  = getSecurityInput($request->get('userLvl'));
		$account  = getSecurityInput($request->get('account'));
		$viplevel  = getSecurityInput($request->get('viplevel'));
		$path_img  = getSecurityInput($request->get('path_img'));
		
		
		if(isAjaxRequest())
		{
			$status  = getSecurityInput($request->getPost('status'));
			$is_include_down  = getSecurityInput($request->getPost('is_include_down'));
			//呼叫 JAVA API
			$data['param']['userId'] = intval($userid);
			$data['param']['type'] = intval(2);	//1:獎金返點 2:超级2000
			$settMode = ($is_include_down == "true")? 2 : 1;
			$data['param']['settMode'] = intval($settMode);	//1:本人 2:本人及下级
			$data['param']['status'] = intval($status);	//1:開 0:關
			//print_r($data);
			$aUri['queryUserByCriteria_proxy'] = 'bizSwitch';
			$res = $this->_clientobject->inRequestV2($data, $aUri);
			
		}
		
		
		
		//呼叫 JAVA API
		$data['param']['userId'] = intval($userid);
		$data['param']['type'] = intval(2);	//1:獎金返點 2:超级2000
		$aUri['queryUserByCriteria_proxy'] = 'queryBizSwitch';
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		//print_r($res);
		//exit;
		
	    if(isset($res['head']['status']) && $res['head']['status'] == '0'){	//成功
		
		
			if(isset($res['body']['result']['status']))
			{
			    $status = $res['body']['result']['status'];	//1:開 0:關
				$this->view->assign('status', $status);
				$this->view->assign('userid', $userid);
				$this->view->assign('userLvl', $userLvl);
				$this->view->assign('account', $account);
				$this->view->assign('viplevel', $viplevel);
				$this->view->assign('path_img', $path_img);
				$this->view->display('admin/user/adminUser_usersuper2000.tpl');
			}
		}
		
	
		
	}
	
	public function userlhcpointAction()
	{
	
	
		$request = $this->getRequest();
		$userid  = getSecurityInput($request->get('id'));
		$userLvl  = getSecurityInput($request->get('userLvl'));
		$account  = getSecurityInput($request->get('account'));
		$viplevel  = getSecurityInput($request->get('viplevel'));
		$path_img  = getSecurityInput($request->get('path_img'));
	
	
		if(isAjaxRequest())
		{
			$status  = getSecurityInput($request->getPost('status'));
			$is_include_down  = getSecurityInput($request->getPost('is_include_down'));
			//呼叫 JAVA API
			$data['param']['userId'] = intval($userid);
			$data['param']['type'] = intval(3);	//1:獎金返點 2:超级2000 3:六合彩
			$settMode = ($is_include_down == "true")? 2 : 1;
			$data['param']['settMode'] = intval($settMode);	//1:本人 2:本人及下级
			$data['param']['status'] = intval($status);	//1:開 0:關
			//print_r($data);
			$aUri['queryUserByCriteria_proxy'] = 'bizSwitch';
			$res = $this->_clientobject->inRequestV2($data, $aUri);
				
		}
	
	
	
		//呼叫 JAVA API
		$data['param']['userId'] = intval($userid);
		$data['param']['type'] = intval(3);	//1:獎金返點 2:超级2000 3:六合彩
		$aUri['queryUserByCriteria_proxy'] = 'queryBizSwitch';
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		//print_r($res);
		//exit;
	
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){	//成功
	
	
			if(isset($res['body']['result']['status']))
			{
				$status = $res['body']['result']['status'];	//1:開 0:關
				$this->view->assign('status', $status);
				$this->view->assign('userid', $userid);
				$this->view->assign('userLvl', $userLvl);
				$this->view->assign('account', $account);
				$this->view->assign('viplevel', $viplevel);
				$this->view->assign('path_img', $path_img);
				$this->view->display('admin/user/adminUser_userlhc.tpl');
			}
		}
	
	
	
	}
}

?>
