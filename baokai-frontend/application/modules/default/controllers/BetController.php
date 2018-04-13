<?php
class Default_BetController extends Fundcommon {
	protected $_page,$_pagesize,$_total;
	public function init() {
		parent::init ();
		$this->_pagesize = 15;
		$this->_total    = 0;
		$this->_page     = 1;
		$this->_status   ='';
		$this->_errView = 'ucenter';
	}
	
	//账户明细
	public function fuddetailAction(){
		$this->res_add_url("账户明细","/bet/fuddetail");
		$this->res_add_url("账户明细","/bet/fuddetail",true);
		$aType = array(
			'CWCR' => '提现退回',
			'BDRX' => '撤销派奖',
			'CFCX' => '撤单费用',
			'CRVC' => '投注退款',
			'DVCB' => '投注扣款',
			'PDXX' => '奖金派送',
			'RHAX' => '投注返点',
			'RRHA' => '撤销返点',
			'AAXX' => '管理员增',
			'CEXX' => '客户理赔',
			'DAXX' => '管理员减',
			'IPXX' => '平台奖励',
			'PGXX' => '活动礼金',
			'RBRC' => '充值让利',
			'SCDX' => '小额扣减',
			'SCRX' => '小额接收',
			'TPXX' => '升级转入',
			//'TFTP' => '转入PT',
			//'TFTM' => '转出PT',
			//'TFTX' => '转入FHX',
			//'TXTM' => '转出FHX',
			//'RDXX' => 'FHX返点',
			//'PGFX' => 'FHX活动礼金',
			'ADAL' => '充值',
			'CWCS' => '提现',
			'BIRX' => '转入',
			'SOSX' => '转出',
			'DDAX' => '彩票分红',
			//'DDPT' => 'PT佣金',
			//'RDAX' => 'PT返点',
			//'PGPT' => 'PT活动奖金',
			//'PGAP' => 'PT活动礼金',
		);

		

		$aStatus = array(
			'ADAL' => array('ADAL','ADML','MDAX'),
			'CWCS' => array('CWCS','CWTF','CWTS','CWCF'),
			'CWCR' => array('CWCR','CWTR'),
			'DVCB' => array('DVCB','DVCN'),
			'RHAX' => array('RHAX','RSXX','RDAX'),
			'RRHA' => array('RRHA','RRSX','RRDA'),
			'BIRX' => array('BIRX','RRXX'),
			'SOSX' => array('SOSX','WPXX'),
			'PDXX' => array('PDXX','RBAP'),
			'PGXX' => array('PGXX','PGPT','PGAP'),
		);
		$aDataType = array(
				'ADAL' => '充值',
				'ADML' => '充值',
				'CWCR' => '提现退回',
				'CWCS' => '提现',
				'CWTF' => '提现',
				'CWCF' => '提现',
				'CWTR' => '提现退回',
				'CWTS' => '提现',
				'MDAX' => '充值',
				'BDRX' => '撤销派奖',
				'CFCX' => '撤单费用',
				'CRVC' => '投注退款',
				'DVCB' => '投注扣款',
				'DVCN' => '投注扣款',
				'PDXX' => '奖金派送',
				'RHAX' => '投注返点',
				'RRHA' => '撤销返点',
				'RRSX' => '撤销返点',
				'RSXX' => '投注返点',
				'AAXX' => '管理员增',
				'CEXX' => '客户理赔',
				'DAXX' => '管理员减',
				'IPXX' => '平台奖励',
				'PGXX' => '活动礼金',
				'RBRC' => '充值让利',
				'BIRX' => '转入',
				'RRXX' => '转入',
				'SCDX' => '小额扣减',
				'SCRX' => '小额接收',
				'SOSX' => '转出',
				'WPXX' => '转出',
				'TPXX' => '升级转入',
				'TFTP' => '转入PT',
				'TFTM' => '转出PT',
				'TFTX' => '转入FHX',
				'TXTM' => '转出FHX',
				'RDXX' => 'FHX返点',
				'PGFX' => 'FHX活动礼金',
				'RDAX' => '投注返点',
				'RRDA' => '撤销返点',
				'DDAX' => '彩票分红',
				'DDPT' => 'PT佣金',
				'PGPT' => 'PT活动奖金',
				'PGAP' => 'PT活动礼金',
				'RDAX' => 'PT返点',
				'RBAP' => '奖金派送',
		);
		$sn       = getSecurityInput($this->_request->get('sn',''));
		$userName = getSecurityInput($this->_request->get('userName',''));
		$fromDate = getSecurityInput($this->_request->get('fromDate',date('Y-m-d',strtotime('-6 days'))));
		$toDate   = getSecurityInput($this->_request->get('toDate',date('Y-m-d')));
		$status   = getSecurityInput($this->_request->get('status',''));
		$type     = getSecurityInput($this->_request->get('type',''));
		$include  = getSecurityInput($this->_request->get('include',''));
		$page     = getSecurityInput($this->_request->get('page'));
		$_POST = $this->_request->getParams();
		if (!empty($sn)) {
			$data['param']['sn'] = $sn;
		}

		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$fromDate = date('Y-m-d',strtotime($fromDate));
				$data['param']['fromDate'] = getQueryStartTime($fromDate);
				$_POST['fromDate'] = $fromDate;
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}else{
			$_POST['fromDate'] = date('Y-m-d',strtotime('-6 days'));
		}

		if(!empty($toDate)){
			if (strtotime($toDate)){
				$toDate = date('Y-m-d',strtotime($toDate));
				$data['param']['toDate'] = getQueryEndTime($toDate);
				$_POST['toDate'] = $toDate;
			} else {
				$this->addErr('102039');
				$this->res_show(true,true);
			}
		}else{
			$_POST['toDate'] = date('Y-m-d');
		}
		
		if($toDate < $fromDate){
			$this->addErr('102040');
			$this->res_show(true,true);
		}
		if(!empty($page)){
			$this->_page = $page;
		}
		
		//$_POST['sn'] = strtotime($fromDate);
		
		if(!empty($fromDate) && !empty($toDate)){
			if((strtotime($fromDate) < strtotime(date('Y-m-d',strtotime('-14 days')))) || (strtotime($toDate) > strtotime(date('Y-m-d')))){
				$this->addErr('102211');
				$this->res_show(true,true);
			}
		}
		
		if (!empty($status)) {
			if(isset($aStatus[$status])){
				$data['param']['summary'] = $aStatus[$status];
			} else {
				$data['param']['summary'] = array($status);
			}
		}
		$userId = $this->_sessionlogin->info['id'];
		
		//客户管理进来的数据
		if($type == 1){
			if(!empty($userName)){
				$userInfo = $this->getUserInfo($userName);//userChain
				if(isset($userInfo['id']) && !(strrpos($userInfo['userChain'],$this->_sessionlogin->info['account'])===FALSE)){
					$userId = $userInfo['id'];
					if (!empty($include)) {
						$data['param']['userChain'] = $userInfo['userChain'];
					}
					$this->view->userLvl = $userInfo['userLvl'];
				} else {
					$userId = -1;
				}
			} else {
				$userId = -1;
			}
		}
		$content = array();
		if($userId >0){
			$data['param']['userId'] = $userId;
			$data['pager'] =array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					'endNo'   =>$this->_page*$this->_pagesize,
			);
			$aUri['fund'] = 'queryFrontFundChangeLog';
			$result = $this->_clientobject->inRequestV2($data, $aUri);
			if(isset($result['body']['result']) && count($result['body']['result'])>0){
				foreach ($result['body']['result'] as $key=>$value){
					$content[$key]['sn']  		= !empty($value['sn']) ? $value['sn'] : '';
					$content[$key]['userId']  		= !empty($value['userId']) ? $value['userId'] : '';
					$content[$key]['account']  	= !empty($value['account']) ? $value['account'] : '';
					$content[$key]['exCode']  	= !empty($value['exCode']) ? $value['exCode'] : '';
					$content[$key]['planCode']  = !empty($value['planCode']) ? $value['planCode'] : '';
					$content[$key]['note']  	= !empty($value['note']) ? $value['note'] : '';
					$value['ctBal'] 	= $value['ctBal']/$this->_moneyUnit;
					$value['beforBal']  = $value['beforBal']/$this->_moneyUnit;
					$content[$key]['bal'] = $value['ctBal'] - $value['beforBal'];
					
					if($value['ctBal'] - $value['beforBal'] > 0){
					
						$content[$key]['inBal']	 = getMoneyFomat($value['changeAmt']/$this->_moneyUnit,4);
						
						
						$content[$key]['outBal'] = '';
					} else {
						$content[$key]['inBal']	 = '';
					
						$content[$key]['outBal'] = getMoneyFomat(abs($content[$key]['bal']),4);
					}
				
					$content[$key]['ctBal']      = $value['ctBal'] ? getMoneyFomat_new($value['ctBal'],4) : 0;
					$content[$key]['gmtCreated'] = $value['gmtCreated'] ? date('Y-m-d H:i:s',getSrtTimeByLong($value['gmtCreated'])) : '';
					$content[$key]['status'] 	 = isset($aDataType[$value['summary']]) ? $aDataType[$value['summary']] : '';
				}
				$this->_total = $result['body']['pager']['total'];//总记录数
			}
		}
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		$account = $this->_sessionlogin->info['account'];
		$this->view->account = $account; // 当前账户
		//获取账户余额
		$accInfo = $this->getuserfundinfo();
		$this->view->accInfo = $accInfo;
		$this->view->pages   = $pages;
		$this->view->total   = $this->_total;
		$this->view->content = $content;
		$this->view->status  = $status;
		$this->view->aType = $aType;
		$this->view->_POST = $_POST;
		$this->view->title = "账户明细";
		$this->view->display('default/ucenter/bet/fundDetail_list.tpl');
	}
	
}
