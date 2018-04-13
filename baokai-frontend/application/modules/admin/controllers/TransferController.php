<?php
class Admin_TransferController extends CustomControllerAction  {
	private $_downArray,$_queryAllBankDao;
	
	public function init() {
		parent::init ();
		$this->_queryAllBankDao = new QueryAllBankDao();
		$this->_downArray = range(1, 10);
	}
	
	public function indexAction(){
		/* 银行卡绑定记录 */
		$parma = $this->_request->getParam('parma');
		$parmas = array(
				'sv61'=>'view61',//充值处理 6.1
				'sv62'=>'view62',//充值处理 6.2
				'trds'=>'tranferrecords',//获取转账记录
				'vper'=>'vPerDetail',//用户转账明细
				'vall'=>'vAllDetail',//用户转账汇总
				'deposit'=>'deposit',
		);
		if(array_key_exists($parma,$parmas)){
			//eval(" $this->".$parmas[$parma]."($this->_request);");
			$this->$parmas[$parma]();
		}else{
			$this->_request->setParam('parma', 'sv61');
			$this->indexAction();
		}
	}
	
	public function view61(){
		
		$this->view->title = '转账明细';
		$this->view->display ( '/admin/funds/transfer/transdetail.tpl' );
	}
	
	public function view62(){
		
		$cdata = array();
		$step =0;
		$request = $this->getRequest();
		$aAgentArray = array('总代','一代','二代','三代','其他代理');
		$chargeData['param']['module']   = 'fund';
		$chargeData['param']['function'] = 'transfer';
		$cdata = $this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebytransfer');
		
		if($request->isPost()) {
			$down_lvls = array();
			$step = intval(getSecurityInput($this->_request->getPost('step')));
			if($step == '1'){
				$up_time 	  	= isset($cdata['body']['result']['val']['up_time'])      ? $cdata['body']['result']['val']['up_time'] : 0;
				$up_userlimit 	= isset($cdata['body']['result']['val']['up_userlimit']) ? $cdata['body']['result']['val']['up_userlimit']/$this->_moneyUnit : 0;
				$up_viplimit  	= isset($cdata['body']['result']['val']['up_viplimit'])  ? $cdata['body']['result']['val']['up_viplimit']/$this->_moneyUnit : 0;
				$down_time 		= intval(getSecurityInput($this->_request->getPost('down_time')));
				$down_userlimit = intval(getSecurityInput($this->_request->getPost('down_userlimit')));
				$down_viplimit 	= intval(getSecurityInput($this->_request->getPost('down_viplimit')));
				$aDown_Lvls 	= $this->_request->getPost('down_lvls',array());
				foreach ($aDown_Lvls as $value){
					$down_lvls[] = intval(getSecurityInput($value));
				}
			} else {
				$up_time 	  	= intval(getSecurityInput($this->_request->getPost('up_time')));
				$up_userlimit 	= intval(getSecurityInput($this->_request->getPost('up_userlimit')));
				$up_viplimit  	= intval(getSecurityInput($this->_request->getPost('up_viplimit')));
				$down_time 	    = isset($cdata['body']['result']['val']['down_time'])      ? $cdata['body']['result']['val']['down_time'] : 0;
				$down_userlimit = isset($cdata['body']['result']['val']['down_userlimit']) ? $cdata['body']['result']['val']['down_userlimit']/$this->_moneyUnit : 0;
				$down_viplimit  = isset($cdata['body']['result']['val']['down_viplimit'])  ? $cdata['body']['result']['val']['down_viplimit']/$this->_moneyUnit : 0;
				$down_lvls  	= isset($cdata['body']['result']['val']['down_lvls'])  ? $cdata['body']['result']['val']['down_lvls'] : array();
			}
			
			$chargeData['param']['val']['up_userlimit']   = $up_userlimit*$this->_moneyUnit;
			$chargeData['param']['val']['up_viplimit']    = $up_viplimit*$this->_moneyUnit;
			$chargeData['param']['val']['up_time']        = $up_time;
			$chargeData['param']['val']['down_userlimit'] = $down_userlimit*$this->_moneyUnit;
			$chargeData['param']['val']['down_viplimit']  = $down_viplimit*$this->_moneyUnit;
			$chargeData['param']['val']['down_time']      = $down_time;
			$chargeData['param']['val']['down_lvls']      = $down_lvls;
			$cdata = $this->_queryAllBankDao->chargeCountDown($chargeData,'saveConfigtransfer');
		}
		
		foreach ($cdata['body']['result']['val'] as $key=>$value){
			if($key =='down_time' || $key =='up_time' || $key =='down_lvls'){
				$res[$key] = $value;
			} else {
				$res[$key] = $value/$this->_moneyUnit;
			}
		}
		$this->view->downArray = $this->_downArray;
		$this->view->aAgentArray = $aAgentArray;
		$this->view->res = $res;
		$this->view->step = $step;
		$this->view->title = '转账限额配置';
		$this->view->display ( '/admin/funds/transfer/transconfig.tpl' );
	}
	
	
	//统计数据
	public function vAllDetail(){
		
		$request = $this->getRequest();
		$userName = getSecurityInput($this->_request->getPost ('userName'));
		$startimetime =  $this->_request->getPost ('startimetime');
		$Deadlinetime =  $this->_request->getPost ('Deadlinetime');
		if($startimetime){
			$data['param']['startDate'] = getQueryStartTime($startimetime);
		} else {
			$data['param']['startDate'] = getQueryStartTime('-1 months');
		}
		if($Deadlinetime){
			$data['param']['endDate'] = getQueryEndTime($Deadlinetime);
		} else {
			$data['param']['endDate'] = getSendTime();
		}
		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		
		$data['param']['account'] = $userName;
		$res = $this->_queryAllBankDao->userFundTransfer($data);
		if(isset($res['counts'][0])){
			$modata['transferTimes'] 	= floatval($res['counts'][0]['sendTime']) ; //转出次数
			$modata['incomeTimes'] 		= floatval($res['counts'][0]['rcvTime']) ; //收入次数
			$modata['isVip'] 			= $res['counts'][0]['vipLevel']>0 ? '是' : '否'; //是否vip
			$modata['allOutTransfer'] 	= getMoneyFomat($res['counts'][0]['sendCount'],2) ; //累计转出金额
			$modata['group'] 			= $res['counts'][0]['userChain'] ;
			$modata['account'] 			= $res['counts'][0]['account'] ;
			$modata['startDate'] 		= date('Y-m-d',getSrtTimeByLong($res['counts'][0]['startDate'])) ;
			$modata['endDate'] 			= date('Y-m-d',getSrtTimeByLong($res['counts'][0]['endDate'])) ;
			$modata['allInTransfer'] 	= getMoneyFomat($res['counts'][0]['rcvCount'],2) ;//累计收入金额
			array_push($modatas['text'],$modata);
			array_push($modatas['count'],array('recordNum'=>'1')) ;
		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	//明细数据
	public function vPerDetail(){
		
		
		$Direction =  $this->_request->getPost ('direction','');
		$startimetime =  $this->_request->getPost ('startimetime');
		$Deadlinetime =  $this->_request->getPost ('Deadlinetime');
		$transferTimetime1 = $this->_request->getPost ('transferTimetime1');
		$transferTimetime2 = $this->_request->getPost ('transferTimetime2');
		if($startimetime){
			$startimetime = getQueryStartTime($startimetime);
		} else {
			$startimetime = getQueryStartTime('-1 months');
		}
		if($Deadlinetime){
			$Deadlinetime = getQueryEndTime($Deadlinetime);
		} else {
			$Deadlinetime = getSendTime();
		}
		if($transferTimetime1){
			$transferTimetime1 = getQueryStartTime($transferTimetime1);
		} else {
			$transferTimetime1 = $startimetime;
		}
		if($transferTimetime2){
			$transferTimetime2 = getQueryEndTime($transferTimetime2);
		} else {
			$transferTimetime2 = $Deadlinetime;
		}
		$page =  $this->_request->getPost ('page',0);
		$perPageNum =  $this->_request->getPost ('perPageNum',0);
		$userName = $this->_request->getPost ('userName');
		//----------------需要添加的请求参数-------------------
		$data=array();
 		$start = $page+1 ;
		$data['pager']['startNo']=$page*$perPageNum;
		$data['pager']['endNo']=$start*$perPageNum;
		
		$data['param']['startDate'] = $transferTimetime1 ; //转账起初时间
		$data['param']['endDate']   = $transferTimetime2 ; //转账结束时间
		if($Direction!=''){
			$data['param']['direction'] = intval($Direction) ; //转账方向
		}
		$data['param']['account'] = $userName;
		$result = $this->_queryAllBankDao->userFundTransfer($data);
		$pager = $result['pager'];
		$total = isset($pager['total']) ? $pager['total'] : 0;
		$modata = array();
		$modatas = array();
		$modatas['text'] = array('counts'=>array(),'details'=>array());
		$modatas['count'] = array();
		if(isset($pager['otherMap']['count'])){
			
			$modata['transferTimes'] 	= floatval($pager['otherMap']['count']['sendTime']) ; //转出次数
			$modata['incomeTimes'] 		= floatval($pager['otherMap']['count']['rcvTime']) ; //收入次数
			$modata['isVip'] 			= $pager['otherMap']['count']['vipLevel']>0 ? '是' : '否'; //是否vip
			$modata['allOutTransfer'] 	= getMoneyFomat($pager['otherMap']['count']['sendCount']/$this->_moneyUnit,2) ; //累计转出金额
			$modata['group'] 			= $pager['otherMap']['count']['userChain'];
			$modata['account'] 			= $pager['otherMap']['count']['account']; 
			$modata['startDate'] 		= $pager['otherMap']['count']['startDate']> 0 ? date('Y-m-d',getSrtTimeByLong($pager['otherMap']['count']['startDate'])) : date('Y-m-d',getSrtTimeByLong($transferTimetime1)) ;
			$modata['endDate'] 			= $pager['otherMap']['count']['endDate']> 0 ? date('Y-m-d',getSrtTimeByLong($pager['otherMap']['count']['endDate'])) : date('Y-m-d',getSrtTimeByLong($transferTimetime2)) ;
			$modata['allInTransfer'] 	= getMoneyFomat($pager['otherMap']['count']['rcvCount']/$this->_moneyUnit,2) ;//累计收入金额
			
			if(empty($modata['account'])){
				$data1['param']['account'] = $userName;
				$userInfo = $this->_queryAllBankDao->queryUserByName($data1);
				if(isset($userInfo['body']['result']) && count($userInfo['body']['result'])>0){
					$modata['account'] = $userInfo['body']['result']['account'];
					$modata['isVip']   = $userInfo['body']['result']['vipLvl']>0 ? '是' : '否'; //是否vip
					$aChainArray 	   = array_filter(explode('/', $userInfo['body']['result']['userChain']));
					$modata['group']   = isset($aChainArray[1]) ? $aChainArray[1] : '';
					array_push($modatas['text']['counts'],$modata);
				}
			} else {
				array_push($modatas['text']['counts'],$modata);
			}
			
		}
		if(isset($result['result']) && count($result['result'])>0){
			foreach ( $result['result'] as $recorder){
				$modata = array();
				$modata['startDate'] = date('Y-m-d',getSrtTimeByLong($transferTimetime1)) ;
				$modata['endDate'] = date('Y-m-d',getSrtTimeByLong($transferTimetime2)); 
				$modata['transferTi'] = $recorder['transferTime'] ? date('Y-m-d H:i:s', getSrtTimeByLong($recorder['transferTime']) ) : '';  
				$modata['outAccount'] = $recorder['sendAccount']; 
				$modata['transferDirection'] = $recorder['direction']=='0' ? '转出' : '转入'; 
				$modata['inAccount'] = $recorder['rcvAccount'];  
				$modata['amount'] = $recorder['transferAmt'] ? getMoneyFomat($recorder['transferAmt']/$this->_moneyUnit,2) : '';
				array_push($modatas['text']['details'],$modata) ;
			}
		}
		array_push($modatas['count'],array('recordNum'=>$total)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
}