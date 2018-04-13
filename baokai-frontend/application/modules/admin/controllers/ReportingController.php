<?php

class Admin_ReportingController extends Fundcommon  {
	
    private $_AccountDetailDataType
        ,$_aRechargeStatus
        ,$_aDepositeMode
        ,$_aWithdrawMode
        ,$_aWithDrawStatus
        ,$_aAwardGroup;

    private $_queryFundWithdrawOrder
        ,$_chargeQuery
        ,$_param;

    private $_page
        ,$_totalPage
        ,$_querySize
        ,$_total;

	public function init() {
		parent::init ();
		$this->_page = 1;
		$this->_totalPage = 1;
		$this->_querySize = 2000;
		$this->_total = 0;
		$this->_chargeQuery = new ChargeQueryDao();
		$this->_queryFundWithdrawOrder = new QueryFundWithdrawOrderDao();
        $this->_aRechargeStatus        = array(
            1 =>"支付中"
            , "充值成功"
            , "订单关闭"
            , "用户关闭"
            , "管理员关闭"
        );
        $this->_aWithDrawStatus        = array(
            '未处理'
            ,'待复审'
            ,'处理中'
            ,'已拒绝'
            ,'提现完全成功'
            ,'提现失败'
            ,'提现部分成功'
			,'已退款'
        );
        $this->_aDepositeMode          = array(
            3 =>'财付通',
			2 =>'快捷充值',
			10 =>'快捷充值  - 汇博',
			8 =>'快捷充值  - 通匯',
			4 =>'人工充值',
			1 =>'网银汇款',
			7 =>'微信支付',
			14 =>'微信支付   - 华势',
			5 =>'银联充值',
			9 =>'QQ钱包',
			6 =>'支付宝'			
        );
        $this->_aWithdrawMode          = array(
           0=>'未分配'
            ,1=>'DP'
            ,2=>'通汇'
        );  
        $this->_aSystem               = array(
            0 => '全部'
            , 1 => 'IOS'
            , 2 => 'Android'
            , 3 => '4.0'
        );
		$this->_AccountDetailDataType = array(
				'MDAX'=>'充值',
				'ADML'=>'充值',
				'ADAL'=>'充值',
				'CWTF'=>'提现',
				'CWTS'=>'提现',
				'CWCS'=>'提现',
				'CWCF'=>'提现',
				'CWTR'=>'提现退回',
				'CWCR'=>'提现退回',
				'DVCB'=>'投注扣款',
				'DVCN'=>'投注扣款',
				'CRVC'=>'投注退款',
				'RSXX'=>'投注返点',
				'RHAX'=>'投注返点',
				'RRSX'=>'撤销返点',
				'RRHA'=>'撤销返点',
				'PDXX'=>'奖金派送',
				'BDRX'=>'撤销派奖',
				'CFCX'=>'撤单费用',
				'RBRC'=>'充值让利',
				'PGXX'=>'活动礼金',
				'IPXX'=>'平台奖励',
				'SOSX'=>'转出',
				'WPXX'=>'转出',
				'BIRX'=>'转入',
				'RRXX'=>'转入',
				'SCDX'=>'小额扣减',
				'SCRX'=>'小额接收',
				'CEXX'=>'客户理赔',
				'AAXX'=>'管理员增',
				'DAXX'=>'管理员减',
				'TPXX'=>'升级转入',
				'TFTP' => '转入PT',
				'TFTM' => '转出PT',
				'RDAX' => '投注返点',
				'TFTX' => '转入FHX',
				'TXTM' => '转出FHX',
				'RDXX' => 'FHX返点',
				'PGFX' => 'FHX活动礼金',
				'RRDA' => '撤销返点',
				'DDAX' => '彩票分红',
				'DDPT' => 'PT佣金',
				'PGPT' => 'PT活动奖金',
				'PGAP' => 'PT活动礼金',
				'RDAX' => 'PT返点',
				'TFTG' => '转入万国',
				'TGTM' => '转出万国',
				'RDWX' => '万国返点',
				'RBAP' => '奖金派送',
		);
		$this->_aAwardGroup = array(
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
				'99108'=>'3D',
				'99109'=>'排列5',
				'99301'=>'山东11选5',
				'99302'=>'江西11选5',
				'99303'=>'广东11选5',
				'99304'=>'重庆11选5',
				'99305'=>'宝开11选5',
				'99306'=>'秒开11选5',
				'99307'=>'江苏11选5',
				'99201'=>'北京快乐8',
				'99401'=>'双色球',
				'99501'=>'江苏快三',
                '99502'=>'安徽快三',
				'99601'=>'江苏骰宝',
				'99602'=>'高频骰宝(娱乐厅)',
				'99603'=>'高频骰宝(至尊厅)',
				'99701'=>'六合彩'
		);
	}
	
	//入口
	public function indexAction(){
		$this->_param = getSecurityInput($this->_request->get("parma"));
		$aParam = array(
				"sv51"=>"view51",//充值处理 5.1
				"sv52"=>"view52",//提现明细报表
				"sv521"=>"queryWithDrawNowDetail",//查看详情
				"sv53"=>"view53",//充值处理 5.3
				"sv54"=>"view54",//充值处理 5.4
				"sv55"=>"view55",//账户明细表
				"sv56"=>"view56",//总代盈亏报表
				"sv58"=>"view58",//玩法明细
				"rch"=>"recharge",//充值明细表
				"chex"=>"dowloadChargeData",//下载充值明细表
				"drex"=>"downLoadDrawWithData", // 下载提现明细表
				"dchdr"=>"downloadChargeDrawData", //下载充提明细表
				"dadda"=>"downloadAccountDtailData", //下载充提明细表
				"vddaset"=>"view51DataSet", //加载充值明细报表数据
				"vdtr"=>"view52DataSet", //加载提现明细报表数据
				"veda"=>"view53DataSet", //加载总代充提报表数据
				"toda"=>"view54DataSet", //加载总代游戏币明细
				"adda"=>"view55DataSet", //加载账户明细报表数据
				"tada"=>"view56DataSet", //加载总代盈亏报表数据
				"sada"=>"view57DataSet", //加载下级盈亏报表数据
				"loadpm"=>"view58DataSet", //加载玩法明细
				"gtpm"=>"getPlayMethod", //根据奖金组获取玩法
		);
		if(array_key_exists($this->_param,$aParam) && array_key_exists($this->_param, $this->_aAclArray)){
				$this->$aParam[$this->_param]($this->_request);
				exit;
		}else{
			foreach ($this->_aAclArray as $key=>$value){
				if(array_key_exists($key,$aParam)){
					$this->$aParam[$key]($this->_request);
					exit;
				}
			}
			$this->_redirect('/admin/Rechargemange/');
		}
	}
	
	//下载充值明细报表
 	public function dowloadChargeData(){   
 		$fileName = '充值明细报表';
 		$aTitle = array(
 			'sn'		=> '交易流水号',
 			'status'	=> '订单状态',
 			'userAct'	=> '用户名',
 			'topVip'	=> '所属总代',
 			'applyTime'	=> '申请时间',
 			'applyAmt'	=> '申请金额',
 			'applyBank'	=> '申请充值银行',
 			'depositeMode'=> '付款渠道',
 			'system'	=> '申请平台',
 			'rcvBankId'	=> '收款银行',
 			'rcvAcct'	=> '收款卡账户名',
 			'rcvBankNumber'	=> '收款卡',
 			'memo'			=> '平台附言',
 			'realRcvCard'	=> '银行实际收款卡',
 			'chargeTime'	=> '交易时间',
 			'realChargeAmt'	=> '金额',
 			'fee'			=> '手续费',
 			'realPayBank'	=> '实际付款银行',
 			'cardNumber'	=> '付款卡',
 			'cardAccount'	=> '付款户名',
 			'realMemo'		=> '玩家附言',
 			'mowDate'		=> 'mow返回结果时间',
 			'addCoinTime'	=> '加游戏币时间',
 			'operatingTime'	=> 'DP操作时间'
 		);
 		$intArray = array('rcvBankNumber','realRcvCard','cardNumber');
 		$Direction 				= getSecurityInput($this->_request->get("Direction",0));
		$UserName 				= getSecurityInput($this->_request->get("UserName",0));
		$AgentName 				= getSecurityInput($this->_request->get("AgentName",0));
		$ApplicationPeriodtime1 = getSecurityInput($this->_request->get("ApplicationPeriodtime1",0));
		$ApplicationPeriodtime2 = getSecurityInput($this->_request->get("ApplicationPeriodtime2",0));
		$CardReceivables 		= getSecurityInput($this->_request->get("CardReceivables",0));
		$TradingTimetime1 		= getSecurityInput($this->_request->get("TradingTimetime1",0));
		$TradingTimetime2 		= getSecurityInput($this->_request->get("TradingTimetime2",0));
		$PaymentCards 			= getSecurityInput($this->_request->get("PaymentCards",0));
		$PaymentName 			= getSecurityInput($this->_request->get("PaymentName",0));
		$AddGameCurrencyTimetime1 = getSecurityInput($this->_request->get("AddGameCurrencyTimetime1",0));
		$AddGameCurrencyTimetime2 = getSecurityInput($this->_request->get("AddGameCurrencyTimetime2",0));
		$OrderStatus 			  = $this->_request->get("OrderStatus");
		$OperatingTimetime1 = getSecurityInput($this->_request->get ("OperatingTimetime1",0));
		$OperatingTimetime2 = getSecurityInput($this->_request->get ("OperatingTimetime2",0));
		//-------------------------------------需要接口添加相关查询字段---------------
		$data=array();
		if($CardReceivables){
	    	$param["rcvAcct"] 	= $CardReceivables;
		}
		if($UserName){
			$param["userAct"] 	= $UserName;
		}
		if($AgentName){
			$param["topAcc"] 	= $AgentName;
		}
		if($Direction){
			$param["sn"] 		= $Direction ;
		}
		if($ApplicationPeriodtime1){
			$param["fromDate"] 	= getQueryStartTime($ApplicationPeriodtime1);
		}
		if($ApplicationPeriodtime2){
			$param["toDate"] 	= getQueryEndTime($ApplicationPeriodtime2);
		}
		if($TradingTimetime1){
			$param["fromDealDate"] 	  = getQueryStartTime($TradingTimetime1);
		}
		if($TradingTimetime2){
			$param["toDealDate"] 	  = getQueryEndTime($TradingTimetime2);
		}
		if($AddGameCurrencyTimetime1){
			$param["fromAddCoinDate"] = getQueryStartTime($AddGameCurrencyTimetime1);
		}
		if($AddGameCurrencyTimetime2){
			$param["toAddCoinDate"]   = getQueryEndTime($AddGameCurrencyTimetime2);
		}
		if($OperatingTimetime1){
			$param["fromOperatingDate"] = getQueryStartTime($OperatingTimetime1);
		}
		if($OperatingTimetime2){
			$param["toOperatingDate"]   = getQueryEndTime($OperatingTimetime2);
		}
		if($PaymentCards){
			$param["payAcct"] 		  = $PaymentCards;
		}
		if($PaymentName){
			$param["payNo"] 		  = $PaymentName;
		}
		if($OrderStatus){
			$param["status"] = array(intval($OrderStatus));
		}
		$param["isReport"] = 'rpt';
		$data['param'] = $param;
		$modata = array();
		$aContent = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$rsr = $this->_chargeQuery->chargeQuery($data);
			if(isset($rsr['result']) && count($rsr['result'])>0){
				foreach ( $rsr["result"] as $recorder){
					$modata["sn"] 				= $recorder->getMember("sn") ;//交易流水号
					$modata["status"] 			= $recorder->getMember("status") ? $this->_aRechargeStatus[$recorder->getMember("status")] : ''  ; //订单状态
					$modata["userAct"] 			= $recorder->getMember("userAct") ? $recorder->getMember("userAct") : '' ; //用户名
					$modata["account"] 			= $recorder->getMember("account") ? $recorder->getMember("account") : '' ; //银行账户名
					$modata["topVip"] 			= $recorder->getMember("topVip") ? $recorder->getMember("topVip") : ''; //所属总代
					$modata["applyTime"] 		= $recorder->getMember("applyTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '' ; //申请时间
					$modata["applyAmt"] 		= $recorder->getMember("applyAmt") ? getMoneyFomat($recorder->getMember("applyAmt")/$this->_moneyUnit,2) : '' ;//申请金额
					$modata["system"] 			= $recorder->getMember("platVersion") ? $recorder->getMember("platVersion") : '';//申请平台
					$modata["applyBank"]		= $recorder->getMember("payBankId") ? $this->_bankIcoArray[$recorder->getMember("payBankId")]['name'] : ''; //申请充值银行
					$modata["realPayBank"]		= $recorder->getMember("rcvBankId") ? $this->_bankIcoArray[$recorder->getMember("rcvBankId")]['name'] : ''; //实际付款银行
					$modata["rcvBankId"]		= $recorder->getMember("bankId") ? $this->_bankIcoArray[$recorder->getMember("bankId")]['name'] : ''; //收款银行
					$modata["rcvAcct"] 			= $recorder->getMember("rcvAcct") ? $recorder->getMember("rcvAcct") : '';//收款用户名
					$modata["rcvEmail"] 		= $recorder->getMember("rcvEmail") ? $recorder->getMember("rcvEmail") : '';//收款卡
					$modata["rcvBankNumber"] 	= !empty($modata["rcvEmail"]) ? $modata["rcvEmail"] : ($recorder->getMember("rcvBankNumber") ? $recorder->getMember("rcvBankNumber") : '');//收款卡
					$modata["realRcvCard"] 		= $modata["rcvBankNumber"];//收款卡
					$modata["memo"] 			= $recorder->getMember("memo") && $recorder->getMember("depositMode") !='2' ? $recorder->getMember("memo") : '';//平台附言
					$modata["realMemo"] 		= $recorder->getMember("realMemo") && $recorder->getMember("depositMode") !='2' ? $recorder->getMember("realMemo") : '';//玩家附言
					$modata["chargeTime"] 		= $recorder->getMember("chargeTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("chargeTime"))) : '';//交易时间
					$modata["mowDate"] 			= $recorder->getMember("mowDate") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("mowDate"))) : '';//交易时间
					$modata["realChargeAmt"] 	= $recorder->getMember("chargeAmt") ?getMoneyFomat($recorder->getMember("chargeAmt")/$this->_moneyUnit,2) : ''; //金额
					$modata["fee"] 				= $recorder->getMember("shouxufei") ? getMoneyFomat($recorder->getMember("shouxufei")/$this->_moneyUnit,2) : 0; //手续费
					$modata["cardNumber"] 		= $recorder->getMember("cardNumber") ? $recorder->getMember("cardNumber") : '' ; //付款卡
					$modata["cardAccount"] 		= $recorder->getMember("cardAccount") ? $recorder->getMember("cardAccount") : ''; //付款户名
                    if($recorder->getMember("depositMode")==2){ 
						if($recorder->getMember("chargeMode")==2){ //2:通匯
							$modata["depositeMode"] = $this->_aDepositeMode[8];
						}else if($recorder->getMember("chargeMode")==4){ //4:汇博
							$modata["depositeMode"] = $this->_aDepositeMode[10];
						}else{
							$modata["depositeMode"] = $this->_aDepositeMode[$recorder->getMember("depositMode")];
						}
					}else if($recorder->getMember("depositMode")==8){
							$modata["depositeMode"] = $this->_aDepositeMode[6]; //如果是支付寶企業版，顯示渠道為支付寶[6]
					}else if($recorder->getMember("depositMode")==5){//銀聯
						if($recorder->getMember("chargeMode")==1){ //1:DP
							$modata["depositeMode"] = $this->_aDepositeMode[5];
						}else if($recorder->getMember("chargeMode")==3){ //3:匯潮
							$modata["depositeMode"] = $this->_aDepositeMode[9];
						}
					}else if($recorder->getMember("depositMode")==7){//微信
						if($recorder->getMember("chargeMode")==1){ //1:DP
							$modata["depositeMode"] = $this->_aDepositeMode[7];
						}else if($recorder->getMember("chargeMode")==7){ //7:華勢
							$modata["depositeMode"] = $this->_aDepositeMode[14];
						}
					}else{
						$modata["depositeMode"] 	= $recorder->getMember("depositMode") ? $this->_aDepositeMode[$recorder->getMember("depositMode")] : '' ;//申請渠道						
					}					

									
					$modata["addCoinTime"] 		=  $recorder->getMember("addCoinTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("addCoinTime"))) : '' ;//加游戏币时间
					$modata["operatingTime"] 		=  $recorder->getMember("operatingTime") ? " ".date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("operatingTime"))) : '' ;//加游戏币时间
					array_push($aContent,$modata);
				}
				$this->_totalPage = ceil($rsr['pager']['total']/$this->_querySize);
				$this->_total     = $rsr['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page,$intArray);
			$aContent = array();
		} while ($this->_page++ !=$this->_totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['sn'] 	  = '下载数据:'.$this->_total.'条.';
		$modata['status'] = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
	}
	
//下载提现明细报表
	public function downLoadDrawWithData(){
		$fileName = '提现明细报表';

		$aTitle = array(
			'sn'		=>'平台订单号',
			'Msn'		=>'mow订单号',
			'withdrawMode'	=> '提现渠道',				
			'oddStatus' =>'订单状态',
			'userName'	=>'用户名',
			'group'		=>'所属总代',
			'applyAmount'	=>'申请提现金额',
			'applyTime'		=>'申请提现时间',
			'bankName'		=>'申请提现银行',
			'depositeMode'	=>'申请渠道',
			'apProject'		=>'申请平台',
			'sendToMsnTime'	=>'审核通过时间',
			'mowRcvDate'	=>'mow接收订单时间',
			'realWithDrawAmt'=>'实际支付金额',
			'mcnSuccTime'	=>'订单完成时间',
			'bankNumber'	=>'申请提现卡',
			'bankAccount'	=>'申请提现户名',
			'operatingTime'	=>'DP操作时间'
		);
		$intArray = array('bankNumber');
		$PlatformNumber 		= getSecurityInput( $this->_request->get("PlatformNumber"));
		$MowNumber 				= getSecurityInput( $this->_request->get("MowNumber"));
		$UserName 				= getSecurityInput( $this->_request->get("UserName"));
		$AgentName  			= getSecurityInput( $this->_request->get("AgentName"));
		
		$OrderStatus 	 		= $this->_request->get("OrderStatus",array(0,1,2,3,4,5,6,7));
		if($OrderStatus == null){
                        $OrderStatus = array(0,1,2,3,4,5,6,7);
        }
		$ApplicationPeriodtime1 = getSecurityInput( $this->_request->get("ApplicationPeriodtime1",''));
		$ApplicationPeriodtime2 = getSecurityInput( $this->_request->get("ApplicationPeriodtime2",''));
		$SendMowTimetime1 		= getSecurityInput( $this->_request->get("SendMowTimetime1",''));
		$SendMowTimetime2 		= getSecurityInput( $this->_request->get("SendMowTimetime2",''));
		$ResultMowTime1 		= getSecurityInput( $this->_request->get("ResultMowTime1",''));
		$ResultMowTime2 		= getSecurityInput( $this->_request->get("ResultMowTime2",''));
		$ApplyCards 			= getSecurityInput( $this->_request->get("ApplyCards"));
		$ApplyUsername 			= getSecurityInput( $this->_request->get("ApplyUsername"));
		$OperatingTimetime1 = getSecurityInput($this->_request->get ("OperatingTimetime1",0));
		$OperatingTimetime2 = getSecurityInput($this->_request->get ("OperatingTimetime2",0));
		$applyChannel = getSecurityInput($this->_request->get("applyChannel",0)) ;
		//---------------------添加接口查询字段------缺少一个 --“银行实际支付时间”字段 ------------
		$page =   $this->_request->get("page");
		$perPageNum =  $this->_request->get("perPageNum");
		$data=array();
		if(!empty($PlatformNumber)){
			$data["param"]["sn"]				= $PlatformNumber ;
		}
		if(!empty($MowNumber)){
			$data["param"]["mowSn"]				= $MowNumber ;
		}
		if(!empty($applyChannel)){
			$data["param"]["withdrawMode"]= $applyChannel ;
		}
		if(!empty($UserName)){
			$data["param"]["applyAccount"]		= $UserName ;
		}
		if(!empty($AgentName)){
			$data["param"]["topVip"]			= $AgentName ;
		}
		if(!empty($ApplicationPeriodtime1)){
			$data["param"]["fromDate"]			= getQueryStartTime($ApplicationPeriodtime1);
		}
		if(!empty($ApplicationPeriodtime2)){
			$data["param"]["toDate"]			= getQueryEndTime($ApplicationPeriodtime2);
		}
		if(!empty($SendMowTimetime1)){
			$data["param"]["fromNoticeMowDate;"]= getQueryStartTime($SendMowTimetime1);
		}
		if(!empty($SendMowTimetime2)){
			$data["param"]["toNoticeMowDate;"]	= getQueryEndTime($SendMowTimetime2);
		}
		//DP時間
		if(!empty($OperatingTimetime1)){ 
			$data["param"]["fromOperatingDate"]= getQueryStartTime($OperatingTimetime1);
		}
		if(!empty($OperatingTimetime2)){
			$data["param"]["toOperatingDate"]	= getQueryEndTime($OperatingTimetime2);
		}
		if(!empty($ResultMowTime1)){
			$data["param"]["fromMowDate"]		= getQueryStartTime($ResultMowTime1);
		}
		if(!empty($ResultMowTime2)){
			$data["param"]["toMowDate"]			= getQueryEndTime($ResultMowTime2);
		}
		if(!empty($ApplyUsername)){
			$data["param"]["applyCardAcct"]		= $ApplyUsername ;  
		}
		if(!empty($ApplyCards)){
			$data["param"]["applyCardNo"]		= $ApplyCards ;
		}
		if(is_array($OrderStatus)){
			$data["param"]["statuses"]= $OrderStatus ;
		}else{
			$data["param"]["statuses"]= array(intval(getSecurityInput($OrderStatus)));
		}
// 		$data['param']["isReport"] = 'rpt';
		$modata = $aContent = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$rsr = $this->_queryFundWithdrawOrder->queryFundWithdrawOrder($data);
			if(isset($rsr["result"])&&count($rsr["result"])>0){
				foreach ( $rsr["result"] as $key=>$recorder){
					$modata["sn"] 			  = $recorder->getMember("sn") ;//平台订单号
					$modata["Msn"] 			  = $recorder->getMember("mcSn") ? $recorder->getMember("mcSn") : ""  ;//mow订单号
					$modata["oddStatus"] 	  = isset($this->_aWithDrawStatus[$recorder->getMember("status")]) ? $this->_aWithDrawStatus[$recorder->getMember("status")] : '' ;//订单状态
					$modata["userName"] 	  = $recorder->getMember("applyAccount")  ;//用户名
					$modata["group"] 		  = $recorder->getMember("topAcc") ? $recorder->getMember("topAcc") : "" ;//所属总代
					$modata["depositeMode"]   = $recorder->getMember("apChannel") ? $recorder->getMember("apChannel") : "" ;//申请渠道
					$modata["apProject"] 	  = $recorder->getMember("apProject") ? $recorder->getMember("apProject") : "" ;//申请平台
					$modata["applyAmount"] 	  = getMoneyFomat($recorder->getMember("withdrawAmt")/$this->_moneyUnit,2) ;//申请提现金额
					$modata["applyTime"] 	  = $recorder->getMember("applyTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : "" ;//用户申请提现时间
					$modata["sendToMsnTime"]  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("apprTime"))) : ""  ;//订单发给mow时间
					$modata["bankToSuccTime"] = $recorder->getMember("mcRemitTime") ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("mcRemitTime"))) 	 : "";//银行实际支付时间
					$modata["mowRcvDate"] 	  = $recorder->getMember("mowRcvDate") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mowRcvDate")) ) 	 : ""   ;//mow返回结果时间
					$modata["mcnSuccTime"] 	  = $recorder->getMember("mcNoticeTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcNoticeTime")) ) 	 : ""   ;//mow返回结果时间
					$modata["realWithDrawAmt"]= $recorder->getMember("mcNoticeTime") ? getMoneyFomat($recorder->getMember("realWithDrawAmt")/$this->_moneyUnit,2) : "";//实际支付金额
					$userBankStruc 			  = $recorder->getMember("userBankStruc");
					$modata["bankNumber"] 	  = $userBankStruc['bankNumber'] ? $this->getSecurityBankCardNum($userBankStruc['bankNumber']) : ''  ;//申请提现卡
					$modata["bankAccount"]    = $userBankStruc['bankAccount'] ? $userBankStruc['bankAccount'] : '' ;//提现用户名
					$modata["bankName"]   	  = isset($this->_bankIcoArray[$userBankStruc['bankId']]['name']) ? $this->_bankIcoArray[$userBankStruc['bankId']]['name'] : '' ;//提现银行
					$modata["operatingTime"] 	  = $recorder->getMember("operatingTime") ? " ".date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("operatingTime")) ) 	 : ""   ;//mow返回结果时间
					$withdrawModeStr='';
					if($recorder->getMember("withdrawMode")=='1'){
						$withdrawModeStr = 'DP';
					}else if ($recorder->getMember("withdrawMode")=='2'){
						$withdrawModeStr = '通汇';
					}else{
						$withdrawModeStr = '未分配';
					}
					$modata["withdrawMode"] = $withdrawModeStr;
					$aContent[$key]=$modata;
				}
				$this->_totalPage = ceil($rsr['pager']['total']/$this->_querySize);
				$this->_total     = $rsr['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page,$intArray);
			$aContent = array();
		} while ($this->_page++ !=$this->_totalPage);
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['sn']  = '下载数据:'.$this->_total.'条.';
		$modata['Msn'] = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
	}
	
	//下载充提明细报表
	public function downloadChargeDrawData(){
		$fileName = '充提报表';
		$aTitle = array(
			'startDate'		=> '起始时间',
			'endDate'		=> '截止时间',
			'userName'		=> '用户名',
			'group'			=> '所属组',
			'onlineRecharge'=> '在线充值',
			'onlineDraw'	=> '在线提现',
			'rechargeFee'	=> '充值手续费',
			'drawithFee'	=> '提现手续费',
			'coinByManual'	=> '人工加币',
			'coinSubtract'	=> '人工扣币',
			'allRecharge'	=> '充值总计',
			'allDraw'		=> '提现总计',
			'rechargeRest'	=> '充值结余'
		);
		$account 			= getSecurityInput($this->_request->get("UserName"));
		$onlineChargeStart  = getSecurityInput($this->_request->get("OnlineRecharge1")); //在线充值
		$onlineChargeEnd 	= getSecurityInput($this->_request->get("OnlineRecharge2"));
		$manualAddCoinStart = getSecurityInput($this->_request->get("ArtificialCad1")); //人工加币
		$manualAddCoinEnd 	= getSecurityInput($this->_request->get("ArtificialCad2"));
		$chargeSumBegin 	= getSecurityInput($this->_request->get("TotalRecharge1")); //充值总计 
		$chargeSumEnd 		= getSecurityInput($this->_request->get("TotalRecharge2"));
		$withdrawSumBegin 	= getSecurityInput($this->_request->get("TotalWithdrawals1"));//提现总计
		$withdrawSumEnd	 	= getSecurityInput($this->_request->get("TotalWithdrawals2"));
		$surplusBegin 		= getSecurityInput($this->_request->get("Balance1"));//重提结余
		$surplusEnd 		= getSecurityInput($this->_request->get("Balance2"));
		$startDate 			= getSecurityInput($this->_request->get("startDate")); 
		$endDate  			= getSecurityInput($this->_request->get("endDate")); 
		$data = array();
		if($account){
 			$data["param"]["account"] 			= $account ;
		}
 		if($onlineChargeStart){
			$data["param"]["onlineChargeStart"] = floatval($onlineChargeStart);
 		}
 		if($onlineChargeEnd){
			$data["param"]["onlineChargeEnd"] 	= floatval($onlineChargeEnd);
 		}
 		if($manualAddCoinStart){
			$data["param"]["manualAddCoinStart"]= floatval($manualAddCoinStart);
 		}
 		if($manualAddCoinEnd){
			$data["param"]["manualAddCoinEnd"] 	= floatval($manualAddCoinEnd);
 		}
 		if($chargeSumBegin){
			$data["param"]["chargeSumBegin"] 	= floatval($chargeSumBegin);
 		}
 		if($chargeSumEnd){
			$data["param"]["chargeSumEnd"] 		= floatval($chargeSumEnd);
 		}
 		if($withdrawSumBegin){
			$data["param"]["withdrawSumBegin"] 	= floatval($withdrawSumBegin);
 		}
 		if($withdrawSumEnd){
			$data["param"]["withdrawSumEnd"] 	= floatval($withdrawSumEnd);
 		}
 		if($surplusBegin){
			$data["param"]["surplusBegin"] 		= floatval($surplusBegin);
 		}
 		if($surplusEnd){
			$data["param"]["surplusEnd"] 		=  floatval($surplusEnd);
 		}
 		if ($startDate) {
 			$data["param"]["startDate"] 		= getQueryStartTime($startDate);
 		} else {
 			$data["param"]["startDate"] 		= getQueryStartTime('-1 months');
 		}
		if($endDate){
			$data["param"]["endDate"] 			= getQueryEndTime($endDate);
		} else {
			$data["param"]["endDate"] 			= getSendTime();
		}
		$data["param"]["isReport"] = 'rpt';
		$recorders = array();
		$aContent = array();
		$modata = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$res = $this->_queryFundWithdrawOrder->withdrawAndCharge($data);
			if(isset($res["body"]["result"])&&count($res["body"]["result"])>0){
				foreach($res["body"]["result"] as $k1 => $v1){
					array_push($recorders,  new WithdrawAndCharge($v1)) ;
				}
				foreach ( $recorders as $recorder){
					$modata["startDate"] 	  = $startDate ? date('Y-m-d H:i:s',getSrtTimeByLong($startDate)) : '' ;//起始时间
					$modata["endDate"] 		  = $endDate   ? date('Y-m-d H:i:s',getSrtTimeByLong($endDate))   : '' ; //截止时间
					$modata["userName"] 	  = $recorder->getMember("account") ? $recorder->getMember("account") : '' ; //用户名
					$modata["group"] 		  = $recorder->getMember("userLvL") ? $recorder->getMember("userLvL") : '' ; //所属组
					$modata["onlineRecharge"] = $recorder->getMember("onlineCharge") 	 ? $recorder->getMember("onlineCharge")/$this->_moneyUnit 	: '' ; //在线充值
					$modata["onlineDraw"] 	  = $recorder->getMember("onlineWithdraw")	 ? $recorder->getMember("onlineWithdraw")/$this->_moneyUnit : ''  ;//在线提现
					$modata["rechargeFee"] 	  = $recorder->getMember("chargeFee") 		 ? $recorder->getMember("chargeFee")/$this->_moneyUnit 		: ''  ;//充值手续费
					$modata["drawithFee"] 	  = $recorder->getMember("withdrawFee") 	 ? $recorder->getMember("withdrawFee")/$this->_moneyUnit	: '' ;//提现手续费
					$modata["coinByManual"]   = $recorder->getMember("manualAddCoin") 	 ? $recorder->getMember("manualAddCoin")/$this->_moneyUnit 	: '' ;//人工加币
					$modata["coinSubtract"]   = $recorder->getMember("manualReduceCoin") ? $recorder->getMember("manualReduceCoin")/$this->_moneyUnit : '' ;//人工扣币
					$modata["allRecharge"] 	  = $recorder->getMember("chargeSum") 		 ? $recorder->getMember("chargeSum")/$this->_moneyUnit 		  : ''  ;//充值总计
					$modata["allDraw"] 		  = $recorder->getMember("withdrawSum") 	 ? $recorder->getMember("withdrawSum")/$this->_moneyUnit 	  : ''  ;//提现总计
					$modata["rechargeRest"]   = floatval($modata["allRecharge"] - $modata["allDraw"])  ;//充值结余
					array_push($aContent,$modata) ;
				}
				$this->_totalPage = ceil($res['pager']['total']/$this->_querySize);
				$this->_total 	  = $res['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
			$aContent = array();
		}while ($this->_page++ !=$this->_totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['startDate'] = '下载数据:'.$this->_total.'条.';
		$modata['endDate']   = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
	}
	
	//下载账户明细报表
	public function downloadAccountDtailData(){
		$fileName = '账户明细报表';
		$aTitle = array(
				'sn'		=> '交易流水号',
				'account'	=> '用户名',
				'gmtCreated'=> '时间',
				'summary'	=> '摘要',
				'inBal'		=> '收入金额',
				'frozeAmt'	=> '冻结金额',
				'outBal'	=> '支出金额',
				'ctBal'		=> '可用余额',
				'note'		=> '备注'
		);
		$aStatus = array(
				'MDAX'=>array('MDAX','ADML','ADAL'),
				'CWTF'=>array('CWTF','CWTS','CWCS','CWCF'),
				'CWTR'=>array('CWTR','CWCR'),
				'DVCB'=>array('DVCB','DVCN',),
				'RSXX'=>array('RSXX','RHAX','RDAX'),
				'RRSX'=>array('RRSX','RRHA','RRDA'),
				'SOSX'=>array('SOSX','WPXX'),
				'BIRX'=>array('BIRX','RRXX'),
				'PDXX'=>array('PDXX','RBAP'),
				'PGXX' => array('PGXX','PGPT','PGAP'),
		);
		$sn 		= getSecurityInput( $this->_request->get("sn"));
		$status 	= getSecurityInput( $this->_request->get("OrderType"));
		$UserName 	= getSecurityInput( $this->_request->get("UserName"));
        $UserChild  = getSecurityInput( $this->_request->get("UserChild"));
		$OrderStatus= $this->_request->get("OrderStatus");
		$fromDate 	= getSecurityInput( $this->_request->get("ApplicationPeriodtime1",''));
		$toDate 	= getSecurityInput( $this->_request->get("ApplicationPeriodtime2",''));
		
		if (!empty($sn)) {
			$param['sn'] = $sn;
		}
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$param['fromDate'] = getQueryStartTime($fromDate);
			} else {
				$this->addErr('102038');
				$this->res_show(true,true);
			}
		}
		if(!empty($fromDate)){
			if (strtotime($toDate)){
				$param['toDate'] = getQueryStartTime($toDate);
			} else {
				$this->addErr('102039');
				$this->res_show(true,true);
			}
		}
		if($toDate < $fromDate){
			$this->addErr('102040');
			$this->res_show(true,true);
		}
		if (!empty($status)) {
			$status = explode(',', $status);
			$param['summary'] = array();
			foreach ($status as $value){
				if(isset($aStatus[$value])){
					$param['summary'] = array_merge($param['summary'],$aStatus[$value]);
				} else {
					$param['summary'] = array_merge($param['summary'],array($value));
				}
			}
		}
		
		   if(!empty($UserName)){
            if($UserChild != 1){
				$param['account'] = $UserName;
            }else{
                $param['userChain'] = $UserName;
            }
		}
		$param["isReport"] = 'rpt';
		$data['param'] = $param;
		
		$modatas = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$result = $this->_queryFundWithdrawOrder->queryAccountDetailRecorders($data);
			if(isset($result['result']) && count($result['result'])>0){
				foreach ($result['result'] as $key=>$value){
					$modata = array();
					$modata['sn']  = $value['sn'];
					$modata['account']    = isset($value['account']) ? $value['account'] : '';
					$modata['status'] 	  = isset($value['status']) ? $value['status'] : '';
					$modata['note'] 	  = !empty($value['note']) ? $value['note'] : '';
					$modata['exCode'] 	  = !empty($value['exCode']) ? $value['exCode'] : '';
					$modata['planCode']   = !empty($value['planCode']) ? $value['planCode'] : '';
					$modata['changeAmt']  = floatval($value['changeAmt']);
					if($modata['changeAmt'] > 0){
						$modata['inBal']  = getMoneyFomat($modata['changeAmt']/$this->_moneyUnit,4);
						$modata['outBal'] = '';
					} else {
						$modata['inBal']  = '';
						if($value['frozeAmt']<0){
							$modata['outBal'] = getMoneyFomat(abs($value['frozeAmt'])/$this->_moneyUnit,4);
						} else if($value['frozeAmt'] ==0) {
							$modata['outBal'] = $modata['changeAmt']!=0 ? getMoneyFomat(abs($modata['changeAmt'])/$this->_moneyUnit,4) : '';
						} else {
							$modata['outBal'] = '';
						}
					}
			
					$modata['ctBal']      = $value['ctBal']!=0 ? getMoneyFomat($value['ctBal']/$this->_moneyUnit,4) : '';
					$modata['frozeAmt']   = $value['frozeAmt']!=0 ? getMoneyFomat($value['frozeAmt']/$this->_moneyUnit,4) : '';
					$modata['gmtCreated'] = $value['gmtCreated'] ? date('Y-m-d H:i:s',getSrtTimeByLong($value['gmtCreated'])) : '';
					$modata['summary'] 	  = isset($this->_AccountDetailDataType[$value['summary']]) ? $this->_AccountDetailDataType[$value['summary']] : '';
					array_push($modatas,$modata) ;
				}
				$this->_totalPage = ceil($result['pager']['total']/$this->_querySize);
				$this->_total 	  = $result['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$modatas,$this->_sessionlogin->info['account'],$this->_page);
			$modatas = array();
		} while ($this->_page++ !=$this->_totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($modatas,$modata);
		$modata['sn'] 	   = '下载数据:'.$this->_total.'条.';
		$modata['account'] = '总耗时:'.$diffTime.'秒';
		array_push($modatas,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$modatas,$this->_sessionlogin->info['account'],$this->_page);
	}
	
	
	//显示充值明细页面
	public function view51(){
		
		$this->view->aRechargeStatus = $this->_aRechargeStatus;
		$this->view->bankArray = $this->_bankIcoArray;
		$this->view->aDepositeMode = $this->_aDepositeMode;
        $this->view->aSystem = $this->_aSystem;
		$this->view->Direction = $this->_request->getParam('Direction');
		$this->view->title = "充值明细表";
		$this->view->display ( '/admin/funds/reporting/reportrecharge.tpl' );
	}
	
	//加载充值明细数据
	public function view51DataSet(){
		$Direction 				= getSecurityInput($this->_request->getPost ("Direction",0));
		$UserName 				= getSecurityInput($this->_request->getPost ("UserName",0));
		$AgentName 				= getSecurityInput($this->_request->getPost ("AgentName",0));
		$rcvBankName 			= getSecurityInput($this->_request->getPost ("rcvBankName",0));
		$applyChannel 			= getSecurityInput($this->_request->getPost ("applyChannel",0));
		$platVersion            = getSecurityInput($this->_request->getPost ("system",0));
		$ApplicationPeriodtime1 = getSecurityInput($this->_request->getPost ("ApplicationPeriodtime1",0));
		$ApplicationPeriodtime2 = getSecurityInput($this->_request->getPost ("ApplicationPeriodtime2",0));
		$CardReceivables 		= getSecurityInput($this->_request->getPost ("CardReceivables",0));
		$TradingTimetime1 		= getSecurityInput($this->_request->getPost ("TradingTimetime1",0));
		$TradingTimetime2 		= getSecurityInput($this->_request->getPost ("TradingTimetime2",0));
		$PaymentCards 			= getSecurityInput($this->_request->getPost ("PaymentCards",0));
		$PaymentName 			= getSecurityInput($this->_request->getPost ("PaymentName",0));
		$AddGameCurrencyTimetime1 = getSecurityInput($this->_request->getPost ("AddGameCurrencyTimetime1",0));
		$AddGameCurrencyTimetime2 = getSecurityInput($this->_request->getPost ("AddGameCurrencyTimetime2",0));
		$OrderStatus 			  = $this->_request->getPost ("OrderStatus");
		$OperatingTimetime1 = getSecurityInput($this->_request->getPost ("OperatingTimetime1",0));
		$OperatingTimetime2 = getSecurityInput($this->_request->getPost ("OperatingTimetime2",0));
		//-------------------------------------需要接口添加相关查询字段---------------
		$page 		= getSecurityInput($this->_request->getPost ("page",0));//当前页面
		$perPageNum = getSecurityInput($this->_request->getPost ("perPageNum",0)); //每页面数量		
		$data=array();
		if($CardReceivables){
	    	$param["rcvAcct"] 	= $CardReceivables;
		}
		if($UserName){
			$param["userAct"] 	= $UserName;
		}
		if($AgentName){
			$param["topAcc"] 	= $AgentName;
		}
		if($Direction){
			$param["sn"] 		= $Direction ;
		}
		if($rcvBankName){
			$param["payBankId"] = intval($rcvBankName) ;
		}
		if($applyChannel){
            if($applyChannel == 2){  //快捷-DP                                                               
                $chargeMode = 1;
            }if($applyChannel == 8){ //快捷-通匯
                $applyChannel = 2;
                $chargeMode = 2;
            }if($applyChannel == 10){ //快捷-汇博
				$applyChannel = 2;
                $chargeMode = 4;
			}if($applyChannel ==5){ //銀聯-DP
				$chargeMode = 1;
			}if($applyChannel ==9){ //銀聯-匯潮
				$chargeMode = 3;
				$applyChannel = 5;
			}if($applyChannel ==7){ //微信-DP
				$chargeMode = 1;
			}if($applyChannel ==14){ //微信-華勢
				$chargeMode = 7;
				$applyChannel = 7;
			}
			if($applyChannel ==6 ){ //支付寶要同時查詢個人與企業版

				$param["depositeMode"] 	= array(intval($applyChannel),8);
			}else{

				$param["depositeMode"] 	= array(intval($applyChannel));
			}
            $param["chargeMode"] 	= $chargeMode;
		}
		if($ApplicationPeriodtime1){
			$param["fromDate"] 	= getQueryStartTime($ApplicationPeriodtime1);
		}
		if($ApplicationPeriodtime2){
			$param["toDate"] 	= getQueryEndTime($ApplicationPeriodtime2);
		}
		if($TradingTimetime1){
			$param["fromDealDate"] 	  = getQueryStartTime($TradingTimetime1);
		}
		if($TradingTimetime2){
			$param["toDealDate"] 	  = getQueryEndTime($TradingTimetime2);
		}
		if($AddGameCurrencyTimetime1){
			$param["fromAddCoinDate"] = getQueryStartTime($AddGameCurrencyTimetime1);
		}
		if($AddGameCurrencyTimetime2){
			$param["toAddCoinDate"]   = getQueryEndTime($AddGameCurrencyTimetime2);
		}
		if($OperatingTimetime1){
			$param["fromOperatingDate"] = getQueryStartTime($OperatingTimetime1);
		}
		if($OperatingTimetime2){
			$param["toOperatingDate"]   = getQueryEndTime($OperatingTimetime2);
		}
		if($PaymentCards){
			$param["payAcct"] 		  = $PaymentCards;
		}
		if($PaymentName){
			$param["payNo"] 		  = $PaymentName;
		}
		if($OrderStatus){
			$param["status"] = array(intval($OrderStatus));
		}
        if ( $platVersion ) {
            $param['platVersion'] = $platVersion;
        }
		$param["isReport"] = 'rpt';
		$data['param'] = $param;
		$start 		= $page+1 ;
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"]	  = $start*$perPageNum;
		$rsr = $this->_chargeQuery->chargeQuery($data);
		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		if(isset($rsr["result"]) && count($rsr["result"])>0){
			foreach ( $rsr["result"] as $recorder){
				$modata["sn"] 				= $recorder->getMember("sn") ? $recorder->getMember("sn") : '' ;//交易流水号
				$modata["status"] 			= $recorder->getMember("status") ? $this->_aRechargeStatus[$recorder->getMember("status")] : ''  ; //订单状态
				$modata["userAct"] 			= $recorder->getMember("userAct") ? $recorder->getMember("userAct") : '' ; //用户名
				$modata["account"] 			= $recorder->getMember("account") ? $recorder->getMember("account") : '' ; //银行账户名
				$modata["topVip"] 			= $recorder->getMember("topVip") ? $recorder->getMember("topVip") : ''; //所属总代
				$modata["applyTime"] 		= $recorder->getMember("applyTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '' ; //申请时间
				$modata["applyAmt"] 		= $recorder->getMember("applyAmt") ? getMoneyFomat($recorder->getMember("applyAmt")/$this->_moneyUnit,2) : '' ;//申请金额
                $modata["system"] 			= $recorder->getMember("platVersion") ? $this->_aSystem[$recorder->getMember("platVersion")] : '';//申请平台
                //$modata["system"] 			= $recorder->getMember("platVersion") ? $recorder->getMember("platVersion") : '';//申请平台
				$modata["applyBank"]		= $recorder->getMember("payBankId") ? $this->_bankIcoArray[$recorder->getMember("payBankId")]['name'] : ''; //申请充值银行
				$modata["realPayBank"]		= $recorder->getMember("rcvBankId") ? $this->_bankIcoArray[$recorder->getMember("rcvBankId")]['name'] : ''; //实际付款银行
				$modata["rcvBankId"]		= $recorder->getMember("bankId") ? $this->_bankIcoArray[$recorder->getMember("bankId")]['name'] : ''; //收款银行
				$modata["rcvAcct"] 			= $recorder->getMember("rcvAcct") ? $recorder->getMember("rcvAcct") : '';//收款用户名
				$modata["rcvEmail"] 		= $recorder->getMember("rcvEmail") ? $recorder->getMember("rcvEmail") : '';//收款卡
				$modata["rcvBankNumber"] 	= !empty($modata["rcvEmail"]) ? $modata["rcvEmail"] : ($recorder->getMember("rcvBankNumber") ? $recorder->getMember("rcvBankNumber") : '');//收款卡
				$modata["realRcvCard"] 		= $modata["rcvBankNumber"];//收款卡
				$modata["memo"] 			= $recorder->getMember("memo") && $recorder->getMember("depositMode") !='2' ? $recorder->getMember("memo") : '';//平台附言
				$modata["realMemo"] 		= $recorder->getMember("realMemo") && $recorder->getMember("depositMode") !='2' ? $recorder->getMember("realMemo") : '';//玩家附言
				$modata["chargeTime"] 		= $recorder->getMember("chargeTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("chargeTime"))) : '';//交易时间
				$modata["mowDate"] 			= $recorder->getMember("mowDate") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("mowDate"))) : '';//交易时间
				$modata["realChargeAmt"] 	= $recorder->getMember("chargeAmt") ?getMoneyFomat($recorder->getMember("chargeAmt")/$this->_moneyUnit,2) : ''; //金额
				$modata["fee"] 				= $recorder->getMember("shouxufei") ? getMoneyFomat($recorder->getMember("shouxufei")/$this->_moneyUnit,2) : 0; //手续费
				$modata["cardNumber"] 		= $recorder->getMember("cardNumber") ? $recorder->getMember("cardNumber") : '' ; //付款卡
				$modata["cardAccount"] 		= $recorder->getMember("cardAccount") ? $recorder->getMember("cardAccount") : ''; //付款户名
				if($recorder->getMember("depositMode")==2){ 
						if($recorder->getMember("chargeMode")==2){ //2:通匯
							$modata["depositeMode"] = $this->_aDepositeMode[8];
						}else if($recorder->getMember("chargeMode")==4){ //4:汇博
							$modata["depositeMode"] = $this->_aDepositeMode[10];
						}else{
							$modata["depositeMode"] = $this->_aDepositeMode[$recorder->getMember("depositMode")];
						}
					}else if($recorder->getMember("depositMode")==8){
							$modata["depositeMode"] = $this->_aDepositeMode[6]; //如果是支付寶企業版，顯示渠道為支付寶[6]
					}else if($recorder->getMember("depositMode")==5){//銀聯
						if($recorder->getMember("chargeMode")==1){ //1:DP
							$modata["depositeMode"] = $this->_aDepositeMode[5];
						}else if($recorder->getMember("chargeMode")==3){ //3:匯潮
							$modata["depositeMode"] = $this->_aDepositeMode[9];
						}
					}else if($recorder->getMember("depositMode")==7){//微信
						if($recorder->getMember("chargeMode")==1){ //1:DP
							$modata["depositeMode"] = $this->_aDepositeMode[7];
						}else if($recorder->getMember("chargeMode")==7){ //7:華勢
							$modata["depositeMode"] = $this->_aDepositeMode[14];
						}
					}else{
						$modata["depositeMode"] 	= $recorder->getMember("depositMode") ? $this->_aDepositeMode[$recorder->getMember("depositMode")] : '' ;//申請渠道						
					}	
				
				$modata["addCoinTime"] 		=  $recorder->getMember("addCoinTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("addCoinTime"))) : '' ;//加游戏币时间
				$modata["operatingTime"] 		=  $recorder->getMember("operatingTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("operatingTime"))) : '' ;//加游戏币时间
				array_push($modatas['text'],$modata) ;
			}
			array_push($modatas['count'],array("recordNum"=>$rsr["pager"]["total"])) ; //recordNum 页数 ，每页15条
		}
               		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	//显示默认提现界面
	public function view52(){
		$this->view->title = "提现明细表";
                                             $this->view->aWithdrawMode = $this->_aWithdrawMode;
		$this->view->aStatus = $this->_aWithDrawStatus;
		$this->view->display ( '/admin/funds/reporting/reportdraw.tpl' );
		
	}
	
	//查詢詳情
	public function queryWithDrawNowDetail(){		
		$sn    = getSecurityInput($this->_request->getPost('sn',''));
		$type  = getSecurityInput($this->_request->getPost('type',0));
		$title = intval(getSecurityInput($this->_request->getPost('Title',0)));
		if($title == '2'){
			$type = $type + 3;
		}
		/*if(!isset($this->_aAclArray[$this->_param][$type])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}*/
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$data["param"]["sn"] = $sn;
		$rsr = $this->_queryFundWithdrawOrder->queryWithDrawNowDetail($data);
		$modata = array();
		$recorder = $rsr["result"][0];
		$userBankStruc 			= $recorder->getMember("userBankStruc")  ;
		$modata["bankAccount"] 	= $userBankStruc["bankAccount"] !=null ? $userBankStruc["bankAccount"] : '';//
		$modata["bankNumber"] 	= $userBankStruc["bankNumber"] !=null ? $userBankStruc["bankNumber"] : '';//
		$modata["BankName"] 	= $this->_bankIcoArray[$userBankStruc['bankId']]["name"] ;//
		$modata["id"] 			= $recorder->getMember("id");
		$modata["applyAccount"] = $recorder->getMember("applyAccount")!=null ? $recorder->getMember("applyAccount") : '' ;//
		$modata["withdrawAmt"]  = $recorder->getMember("withdrawAmt") ;//
		$modata["withdrawAmt"]  = getMoneyFomat($modata["withdrawAmt"]/$this->_moneyUnit,2) ;//提现额度
		$modata["riskType"] 	= $recorder->getMember("riskType") ;
		$modata["riskType"] 	= isset($this->_aRiskType[$modata['riskType']]) ? $this->_aRiskType[$modata['riskType']] : "无类型";
		$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : '' ;
		$modata["currApprer"] 	= $recorder->getMember("currApprer") ;
		$modata["currDate"] 	= $recorder->getMember("currDate") ;
		$modata["isAbleOprate"] = $modata["currApprer"]!=''&& $modata["currApprer"]!=$this->_sessionlogin->info['account'] ? false : true ;
		
		/*MOW Select*/
		$modata["sn"] 			  = $recorder->getMember('sn') ? $recorder->getMember('sn') : '';//平台订单号
		$modata["Msn"] 			  = $recorder->getMember("mcSn") ? $recorder->getMember("mcSn") : '';//mow订单号
		$modata["oddStatus"] 	  = isset($this->_aWithDrawStatus[$recorder->getMember("status")]) ? $this->_aWithDrawStatus[$recorder->getMember("status")] : '' ;//订单状态
		$modata["mcMemo"] 	  	  = $recorder->getMember("mcMemo") ? $recorder->getMember("mcMemo") : '';//详情
		$modata["mowRcvDate"] 	  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime")) ) 	 : ""   ;//mow返回结果时间
		$modata["depositeMode"]   = $recorder->getMember("apChannel") ? $recorder->getMember("apChannel") : "" ;//返款種類

		$attachment = $recorder->getMember("attachment");
		if(!empty($attachment)){
			$attachmentArray = explode('|', $recorder->getMember("attachment"));
			$attachment= '';
			foreach ($attachmentArray as $key=>$value){
				$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
			}
		} else {
			$attachment ='';
		}
		$modata["attach"] = trim($attachment,",");
		if($modata['currApprer'] !='' & $modata['currApprer'] !=$this->_sessionlogin->info['account']){
			echo Zend_Json::encode(array('status'=>'error','data'=>'该数据正被 '.$modata['currApprer'].' 审核!'));
			exit;
		} else {
			//锁定该条数据的代码逻辑===================================
            // M#0005648 第一次打开“通过”按钮的时间，后面再打开不变更
            $res['head']['status'] = 0;
            $apprBeginTime = $recorder->getMember("apprBeginTime") ;
            if ( empty($apprBeginTime) ) {
                $aUrl['fundadmin'] = 'yuchulidraw';
                $data2['param']['exceptId'] = $modata["id"];
                $data2['param']['currStatus'] = $title;
                $res = $this->_clientobject->inRequestV2($data2, $aUrl);
            }
			//锁定逻辑=================================================
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){ //锁定成功 把错做页面所需要的数据返回给页面
				echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
				exit;
			} else { //锁定失败 刷新页面
				echo Zend_Json::encode(array('status'=>'error','data'=>'锁定失败!'));
				exit;
			}
		}		
	}
	
	//加载提现数据
	public function view52DataSet(){
		
		$PlatformNumber 		= getSecurityInput( $this->_request->getPost ("PlatformNumber"));
		$MowNumber 				= getSecurityInput( $this->_request->getPost ("MowNumber"));
                                             $applyChannel 			= getSecurityInput($this->_request->getPost ("applyChannel",0));
                                             $UserName 				= getSecurityInput( $this->_request->getPost ("UserName"));
		$AgentName  			= getSecurityInput( $this->_request->getPost ("AgentName"));
		
		$OrderStatus 	 		= $this->_request->getPost ("OrderStatus",array(0,1,2,3,4,5,6,7));
		$ApplicationPeriodtime1 = getSecurityInput( $this->_request->getPost ("ApplicationPeriodtime1",''));
		$ApplicationPeriodtime2 = getSecurityInput( $this->_request->getPost ("ApplicationPeriodtime2",''));
		$SendMowTimetime1 		= getSecurityInput( $this->_request->getPost ("SendMowTimetime1",''));
		$SendMowTimetime2 		= getSecurityInput( $this->_request->getPost ("SendMowTimetime2",''));
		$BankRealTime1 			= getSecurityInput( $this->_request->getPost ("BankRealTime1",''));
		$BankRealTime2 			= getSecurityInput( $this->_request->getPost ("BankRealTime2",''));
		$ResultMowTime1 		= getSecurityInput( $this->_request->getPost ("ResultMowTime1",''));
		$ResultMowTime2 		= getSecurityInput( $this->_request->getPost ("ResultMowTime2",''));
		$ApplyCards 			= getSecurityInput( $this->_request->getPost ("ApplyCards"));
		$ApplyUsername 			= getSecurityInput( $this->_request->getPost ("ApplyUsername"));
		$OperatingTimetime1 = getSecurityInput($this->_request->getPost ("OperatingTimetime1",0));
		$OperatingTimetime2 = getSecurityInput($this->_request->getPost ("OperatingTimetime2",0));
		//---------------------添加接口查询字段------缺少一个 --“银行实际支付时间”字段 ------------
		$page =   $this->_request->getPost ("page");
		$perPageNum =  $this->_request->getPost ("perPageNum");
		$data=array();
		if(!empty($PlatformNumber)){
			$data["param"]["sn"]				= $PlatformNumber ;
		}
		if(!empty($MowNumber)){
			$data["param"]["mcSn"]				= $MowNumber ;
		}
                                             if(!empty($applyChannel)){
			$data['param']["withdrawMode"] 	=  intval($applyChannel) ;
		}
		if(!empty($UserName)){
			$data["param"]["applyAccount"]		= $UserName ;
		}
		if(!empty($AgentName)){
			$data["param"]["topAcc"]			= $AgentName ;
		}
		//申请时间
		if(!empty($ApplicationPeriodtime1)){
			$data["param"]["fromDate"]			= getQueryStartTime($ApplicationPeriodtime1);
		}
		if(!empty($ApplicationPeriodtime2)){
			$data["param"]["toDate"]			= getQueryEndTime($ApplicationPeriodtime2);
		}
		//审核通过时间
		if(!empty($SendMowTimetime1)){ //fromNoticeMowDate
			$data["param"]["fromNoticeMowDate"]= getQueryStartTime($SendMowTimetime1);
		}
		if(!empty($SendMowTimetime2)){ //toNoticeMowDate
			$data["param"]["toNoticeMowDate"]	= getQueryEndTime($SendMowTimetime2);
		}
		//mow接收订单时间
		if(!empty($BankRealTime1)){ 
			$data["param"]["fromNoticeMowDate"]= getQueryStartTime($BankRealTime1);
		}
		if(!empty($BankRealTime2)){
			$data["param"]["toNoticeMowDate"]	= getQueryEndTime($BankRealTime2);
		}
		//DP時間
		if(!empty($OperatingTimetime1)){ 
			$data["param"]["fromOperatingDate"]= getQueryStartTime($OperatingTimetime1);
		}
		if(!empty($OperatingTimetime2)){
			$data["param"]["toOperatingDate"]	= getQueryEndTime($OperatingTimetime2);
		}
		//订单完成时间
		if(!empty($ResultMowTime1)){
			$data["param"]["fromMowDate"]		= getQueryStartTime($ResultMowTime1);
		}
		if(!empty($ResultMowTime2)){
			$data["param"]["toMowDate"]			= getQueryEndTime($ResultMowTime2);
		}
		if(!empty($ApplyUsername)){
			$data["param"]["applyCardAcct"]		= $ApplyUsername ;  
		}
		if(!empty($ApplyCards)){
			$data["param"]["applyCardNo"]		= $ApplyCards ;
		}
		if(is_array($OrderStatus)){
			$data["param"]["statuses"]= $OrderStatus ;
		}else{
			$data["param"]["statuses"]= array(intval(getSecurityInput($OrderStatus)));
		}
// 		$data["param"]["isReport"] = 'rpt';
		$start = $page+1 ;
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"] = $start*$perPageNum;
		$rsr = $this->_queryFundWithdrawOrder->queryFundWithdrawOrder($data);
                		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		if(isset($rsr["result"]) && count($rsr["result"])>0){
                                       			foreach ( $rsr["result"] as $recorder){
                                                          
				$modata["sn"] 			  = $recorder->getMember('sn') ? $recorder->getMember('sn') : '';//平台订单号
				$modata["Msn"] 			  = $recorder->getMember("mcSn") ? $recorder->getMember("mcSn") : '';//mow订单号
				$modata["withdrawMode"] =  isset($this->_aWithdrawMode[$recorder->getMember("withdrawMode")]) ? $this->_aWithdrawMode[$recorder->getMember("withdrawMode")] : '' ;//申请渠道
                                                                                           $modata["oddStatus"] 	  = isset($this->_aWithDrawStatus[$recorder->getMember("status")]) ? $this->_aWithDrawStatus[$recorder->getMember("status")] : '' ;//订单状态
				$modata["userName"] 	  = $recorder->getMember("applyAccount") ? $recorder->getMember("applyAccount") : '';//用户名
				$modata["mcMemo"] 	  	  = $recorder->getMember("mcMemo") ? $recorder->getMember("mcMemo") : '';//详情
				$modata["group"] 		  = $recorder->getMember("topAcc") ? $recorder->getMember("topAcc") : "" ;//所属总代
				$modata["depositeMode"]   = $recorder->getMember("apChannel") ? $recorder->getMember("apChannel") : "" ;//所属总代
				$modata["apProject"] 	  = $recorder->getMember("apProject") ? $recorder->getMember("apProject") : "" ;//所属总代
				$modata["applyAmount"] 	  = getMoneyFomat($recorder->getMember("withdrawAmt")/$this->_moneyUnit,2) ;//申请提现金额
				$modata["applyTime"] 	  = $recorder->getMember("applyTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : "" ;//用户申请提现时间
				//$modata["sendToMsnTime"]  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("apprTime"))) : ""  ;//订单发给mow时间
				//2016.08.15 修正 #9340 有二審即顯示二審
				if($recorder->getMember("appr2Time")){
					$modata["sendToMsnTime"]= date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("appr2Time")));
				}else{
					$modata["sendToMsnTime"]  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("apprTime"))) : ""  ;//订单发给mow时间
				}
				$modata["bankToSuccTime"] = $recorder->getMember("mcRemitTime") ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("mcRemitTime"))) 	 : "";//银行实际支付时间
				//$modata["mowRcvDate"] 	  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime")) ) 	 : ""   ;//mow返回结果时间
                if($recorder->getMember("status")  =="3"){
					$modata["mowRcvDate"] 	  =  " "   ;//mow返回结果时间
				}else{
					if($recorder->getMember("appr2Time")){
						$modata["mowRcvDate"] = date('Y-m-d G:i:s', getSrtTimeByLong($recorder->getMember("appr2Time")));
					}else{
						$modata["mowRcvDate"] 	  = $recorder->getMember("apprTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime")) ) 	 : ""   ;//mow返回结果时间
					}				
				}
				$modata["mcnSuccTime"] 	  = $recorder->getMember("mcNoticeTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcNoticeTime")) ) 	 : ""   ;//mow返回结果时间
				$modata["realWithDrawAmt"]= $recorder->getMember("realWithDrawAmt") ? getMoneyFomat($recorder->getMember("realWithDrawAmt")/$this->_moneyUnit,2) : "";//实际支付金额
				$userBankStruc 			  = $recorder->getMember("userBankStruc");
				$modata["bankNumber"] 	  = $userBankStruc['bankNumber'] ? $this->getSecurityBankCardNum($userBankStruc['bankNumber']) : ''  ;//申请提现卡
				$modata["bankAccount"]    = $userBankStruc['bankAccount'] ? $userBankStruc['bankAccount'] : '' ;//提现用户名
				$modata["bankName"]   	  = isset($this->_bankIcoArray[$userBankStruc['bankId']]['name']) ? $this->_bankIcoArray[$userBankStruc['bankId']]['name'] : '' ;//提现银行
				$modata["operatingTime"] 	  = $recorder->getMember("operatingTime") ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("operatingTime")) ) 	 : ""   ;//mow返回结果时间
				array_push($modatas['text'],$modata) ;
			}
			array_push($modatas['count'],array("recordNum"=>$rsr["pager"]["total"])) ; //recordNum 页数 ，每页15条
		}
                                             header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	
	//加载查看詳情數據

	
	//显示充提界面
	public function view53(){
		$this->view->startDate = date('Y-m-d',time());
		$this->view->endDate = date('Y-m-d',time());
		$this->view->title = "总代充提报表";
		$this->view->display ( '/admin/funds/reporting/topChargeWithdraw.tpl' );
	}
	
	//加载总代充提明细数据
	public function view53DataSet(){
		
		$startDate = getSecurityInput($this->_request->getPost ("ResultMowTime1")); 
		$endDate   = getSecurityInput($this->_request->getPost ("ResultMowTime2")); 
		$param = new ArrayObject(array());
 		if ($startDate) {
 			$param["startTime"] = getQueryStartTime($startDate);
 		}
		if($endDate){
			$param["endTime"] 	= getQueryEndTime($endDate);
		}
		$data["param"] = $param;
		$recorders = array();
		$res = $this->_queryFundWithdrawOrder->queryTopChargeWithdrawRpt($data);
 		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		if (isset($res['result']) && count($res['result'])>0) {
			foreach ( $res['result'] as $recorder){
				$modata["account"] 	= $recorder["account"] ? $recorder["account"] : '' ; //用户名
				$modata["userLvl"] 	= $this->getUserType($recorder["userLvl"]); //所属组
				$modata["game1"] = getMoneyFomat($recorder["game1"]/$this->_moneyUnit,2); //非活动非奖励 
				$modata["game2"] = getMoneyFomat($recorder["game2"]/$this->_moneyUnit,2); //活动类
				$modata["game3"] = getMoneyFomat($recorder["game3"]/$this->_moneyUnit,2); //奖励类
				$modata["game4"] = getMoneyFomat($recorder["game4"]/$this->_moneyUnit,2); //人工扣币
				$modata["game5"] = getMoneyFomat($recorder["game5"]/$this->_moneyUnit,2); //充值让利
				$modata["money1"] = getMoneyFomat($recorder["money1"]/$this->_moneyUnit,2); //客户充值（客户在线申请）
				$modata["money2"] = getMoneyFomat($recorder["money2"]/$this->_moneyUnit,2); //人工充值
				$modata["money3"] = getMoneyFomat($recorder["money3"]/$this->_moneyUnit,2); //客户提现（客户在线申请）
				$modata["money4"] = getMoneyFomat($recorder["money4"]/$this->_moneyUnit,2); //人工提现
				$modata["money5"] = getMoneyFomat($recorder["money5"]/$this->_moneyUnit,2); //人工打款
				$modata["charge"]     = getMoneyFomat(($recorder["game1"]+$recorder["game2"]+$recorder["game3"]+$recorder["money1"]+$recorder["money2"])/$this->_moneyUnit,2);//充值金额
				$modata["withdraw"]   = getMoneyFomat(($recorder["game4"]+$recorder["game5"]+$recorder["money3"]+$recorder["money4"]+$recorder["money5"])/$this->_moneyUnit,2);//提现金额
				$modata["chargewith"] = getMoneyFomat(($recorder["game1"]+$recorder["game2"]+$recorder["game3"]+$recorder["money1"]+$recorder["money2"]-($recorder["game4"]+$recorder["game5"]+$recorder["money3"]+$recorder["money4"]+$recorder["money5"]))/$this->_moneyUnit,2);//充提结余
				array_push($modatas['text'],$modata) ;
			}
			array_push($modatas['count'],array("recordNum"=>$res["pager"]["total"])) ; //recordNum 页数 ，每页15条
		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo Zend_Json::encode($modatas) ; 
	}
	
	//显示总代游戏币明细页面
	public function view54(){
		$this->view->title = "游戏币明细(总代)";
		$this->view->display ( '/admin/funds/reporting/dailyreporting.tpl' );
	}
	
	//加载总代游戏币明细数据
	public function view54DataSet(){
		
		$username  			 = getSecurityInput($this->_request->getPost ("username")); 
		$groupAcountBalance1 = getSecurityInput($this->_request->getPost ("groupAcountBalance1")); 
		$groupAcountBalance2 = getSecurityInput($this->_request->getPost ("groupAcountBalance2")); 
		$userAcountBalance1  = getSecurityInput($this->_request->getPost ("userAcountBalance1")); 
		$userAcountBalance2  = getSecurityInput($this->_request->getPost ("userAcountBalance2")); 
		$param = new ArrayObject(array());
 		if ($username) {
 			$param["account"] = $username;
 		}
 		if ($groupAcountBalance1) {
 			$param["startUseMoney"] = floatval($groupAcountBalance1)*$this->_moneyUnit;
 		}
		if($groupAcountBalance2){
			$param["endUseMoney"] 	= floatval($groupAcountBalance2)*$this->_moneyUnit;
		}
 		if ($userAcountBalance1) {
 			$param["startSumUseMoney"] = floatval($userAcountBalance1)*$this->_moneyUnit;
 		}
		if($userAcountBalance2){
			$param["endSumUseMoney"] 	= floatval($userAcountBalance2)*$this->_moneyUnit;
		}
		$data["param"] = $param;
		$recorders = array();
		$res = $this->_queryFundWithdrawOrder->getGameGoldDetails($data);
 		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		if (count( $res['result'][0])>0) {
			foreach ( $res['result'][0] as $recorder){
				$modata["account"] 	= $recorder["account"] ? $recorder["account"] : '' ; //用户名
				$modata["memo1"] 	= isset($recorder["memo1"]) ? $recorder["memo1"] : '' ; 
				$modata["memo2"] 	= isset($recorder["memo2"]) ? $recorder["memo2"] : '' ; 
				$modata["userLvl"] 	= isset($recorder["userLvl"]) ? $this->getUserType(intval($recorder["userLvl"])) : '总代'; //所属组
				$modata["bal"] 		= getMoneyFomat($recorder["bal"]/$this->_moneyUnit,2); 
				$modata["frozenAmt"] 	= getMoneyFomat($recorder["frozenAmt"]/$this->_moneyUnit,2); 
				$modata["useMoney"] 	= getMoneyFomat($recorder["useMoney"]/$this->_moneyUnit,2); //
				$modata["sumBal"] 		= getMoneyFomat(($recorder["sumBal"]-$recorder["bal"])/$this->_moneyUnit,2);// 团队余额
				$modata["sumFrozen"] 	= getMoneyFomat(($recorder["sumFrozen"]-$recorder["frozenAmt"])/$this->_moneyUnit,2); //团队冻结金额
				$modata["sumUseMoney"] 	= getMoneyFomat(($recorder["sumUseMoney"]-$recorder["useMoney"])/$this->_moneyUnit,2); //
				array_push($modatas['text'],$modata) ;
			}
		}
		array_push($modatas['count'],array("recordNum"=>$res["pager"]["total"])) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo Zend_Json::encode($modatas) ; 
	}
	
	//显示账户明细页面
	public function view55(){
		$aType = array(
			'MDAX'=>'充值',
			'CWTF'=>'提现',
			'CWTR'=>'提现退回',
			'DVCB'=>'投注扣款',
			'CRVC'=>'投注退款',
			'RSXX'=>'投注返点',
			'RRSX'=>'撤销返点',
			'PDXX'=>'奖金派送',
			'BDRX'=>'撤销派奖',
			'CFCX'=>'撤单费用',
			'RBRC'=>'充值让利',
			'PGXX'=>'活动礼金',
			'IPXX'=>'平台奖励',
			'SOSX'=>'转出',
			'BIRX'=>'转入',
			'SCDX'=>'小额扣减',
			'SCRX'=>'小额接收',
			'CEXX'=>'客户理赔',
			'AAXX'=>'管理员增',
			'DAXX'=>'管理员减',
			'TPXX'=>'升级转入',
			'TFTP' => '转入PT',
			'TFTM' => '转出PT',
			'DDAX' => '彩票分红',
			'DDPT' => 'PT佣金',
			'RDAX' => 'PT返点',
            'PGPT' => 'PT活动奖金',
			'PGAP' => 'PT活动礼金',
			'TFTX' => '转入FHX',
			'TXTM' => '转出FHX',
			'RDXX' => 'FHX返点',
			'PGFX' => 'FHX活动礼金',
			'TFTG' => '转入万国',
			'TGTM' => '转出万国',
			'RDWX' => '万国返点',
			
		);
		
		$aNote = array(
			'2017年7月份活动(魔术师练成之路)',
			'2017年6月份活动(销里藏宝)',
			'2017年6月份活动(投怀送宝)',
			'2017年5月活动(宝开送奖)',		
			'2017年5月活动(劳动节挖金币)',
			'2017年4月份活动(母亲节感恩回馈)',
			'2017年4月活動(夺奖之镰)',
			'2017年4月份活动(问卷调查)',
			'2017年3月份活动(宝开献礼)',                                    
			'2017年3月活动(鸡祥富贵)',
			'2017年2月活动(充值送)',
			'2017年2月活动(开春签到送礼)',
			'2017年2月活动(加奖活动第二期)',
			'2017年2月活动(加奖活动第一期)',
			'2017春节活动-充值送2倍(二次派獎)',
			'2017春节活动-充值送2倍(一次派獎)',
			'春节活动-你充我就送',
			'2017春节活动-整点来抽奖',
			'2017春节活动-VIP领红包(财神红包)',
			'2017春节活动-VIP领红包(新春红包)',
			'2017春节活动-VIP领红包(除夕红包)',
			'新手任務绑卡礼金', 
		);
		$this->view->aNote = $aNote;
		$this->view->aType = $aType;
		$this->view->UserName = $this->_request->get('UserName');
		$this->view->title = "账户明细表";
        // M#5556 时间查询项调整
        $this->view->start_datetime = date("Y-m-d 00:00:00", strtotime('-15 DAY'));
        $this->view->end_datetime = date("Y-m-d h:i:s");
		$this->view->display ( '/admin/funds/reporting/accountDetailReporting.tpl' );
	}
	
	//加载账户明细数据
	public function view55DataSet(){
		header ( 'Content-Type: application/json;charset=utf-8' );
		$aStatus = array(
				'MDAX'=>array('MDAX','ADML','ADAL'),
				'CWTF'=>array('CWTF','CWTS','CWCS','CWCF'),
				'CWTR'=>array('CWTR','CWCR'),
				'DVCB'=>array('DVCB','DVCN',),
				'RSXX'=>array('RSXX','RHAX'),
				'RRSX'=>array('RRSX','RRHA','RRDA'),
				'SOSX'=>array('SOSX','WPXX'),
				'BIRX'=>array('BIRX','RRXX'),
				'PDXX'=>array('PDXX','RBAP'),
		);
		$sn 		= getSecurityInput( $this->_request->getPost ("sn"));
		$status 	= getSecurityInput( $this->_request->getPost ("OrderType"));
		$UserName 	= getSecurityInput( $this->_request->getPost ("UserName"));
        $UserChild  = getSecurityInput( $this->_request->getPost ("UserChild"));
		$fromDate 	= getSecurityInput( $this->_request->getPost ("ApplicationPeriodtime1",''));
		$toDate 	= getSecurityInput( $this->_request->getPost ("ApplicationPeriodtime2",''));
		$note       = getSecurityInput( $this->_request->getPost ("note",''));
		$page 		= intval(getSecurityInput($this->_request->getPost ("page",0)));
		$perPageNum = intval(getSecurityInput($this->_request->getPost ("perPageNum",50)));
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();

        // 欄位驗證
		if (!empty($sn)) {
			$param['sn'] = $sn;
		}
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$param['fromDate'] = getQueryStartTime($fromDate);
			} else {
				/* $this->addErr('102038');
				$this->res_show(true,true); */
				echo json_encode($modatas) ;
				exit;
			}
		}
		if(!empty($fromDate)){
			if (strtotime($toDate)){
				$param['toDate'] = getQueryStartTime($toDate);
			} else {
				/* $this->addErr('102039');
				$this->res_show(true,true); */
				echo json_encode($modatas) ;
				exit;
			}
		}
		if($toDate < $fromDate){
			/* $this->addErr('102040');
			$this->res_show(true,true); */
			echo json_encode($modatas) ;
			exit;
		}
		if(!empty($page)){
			$this->_page = $page;
		}
		if (!empty($status)) {
			$status = explode(',', $status);
			$param['summary'] = array();
			foreach ($status as $value){
				if(isset($aStatus[$value])){
					$param['summary'] = array_merge($param['summary'],$aStatus[$value]);
				} else {
					$param['summary'] = array_merge($param['summary'],array($value));
				}
			}
		}
		
		if(!empty($UserName)){
// 			$userInfo = $this->getUserInfo($UserName);//userChain
// 			if(isset($userInfo['id'])){
            if($UserChild != 1){
				$param['account'] = $UserName;
            }else{
                $param['userChain'] = $UserName;
            }
		}
		if(!empty($note)){
			$param['note'] = $note;
		}
		
		$param["isReport"] = 'rpt';
		
		$data['param'] = $param;
		
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"] = ($page+1)*$perPageNum;
		
        $result = $this->_queryFundWithdrawOrder->queryAccountDetailRecorders($data); // queryFundChangeLog
		
		if(isset($result['result']) && count($result['result'])>0){
            // 本頁小計
            $pt_inBal = 0; // 收入金额 = changeAmt > 0 ? changeAmt : 0
            $pt_frozeAmt = 0; // 冻结金额 = frozeAmt != 0 ? frozeAmt : 0
            $pt_outBal = 0; // 支出金额 = changeAmt < 0 && 
		foreach ($result['result'] as $key=>$value){
				$modata = array();
				$modata['sn']  = $value['sn'];
				$modata['account']    = isset($value['account']) ? $value['account'] : '';
				$modata['status'] 	  = isset($value['status']) ? $value['status'] : '';
				$modata['note'] 	  = !empty($value['note']) ? $value['note'] : '';
				$modata['exCode'] 	  = !empty($value['exCode']) ? $value['exCode'] : '';
				$modata['planCode']   = !empty($value['planCode']) ? $value['planCode'] : '';
				$modata['changeAmt']  = floatval($value['changeAmt']);
				if($modata['changeAmt'] > 0){
					$modata['inBal']  = getMoneyFomat($modata['changeAmt']/$this->_moneyUnit,4);
					$modata['outBal'] = '';
				} else {
					$modata['inBal']  = '';
					if($value['frozeAmt']<0){
						$modata['outBal'] = getMoneyFomat(abs($value['frozeAmt'])/$this->_moneyUnit,4);
					} else if($value['frozeAmt'] ==0) {
						$modata['outBal'] = $modata['changeAmt']!=0 ? getMoneyFomat(abs($modata['changeAmt'])/$this->_moneyUnit,4) : '';
					} else {
						$modata['outBal'] = '';
					}
				}
				if($value['ctBal'] > $value['beforBal']){
					$modata['change'] = 1;
				} else if($value['ctBal'] < $value['beforBal']) {
					$modata['change'] = -1;
				} else {
					$modata['change'] = 0;
				}
				
				$modata['ctBal']      = $value['ctBal']!=0 ? getMoneyFomat($value['ctBal']/$this->_moneyUnit,4) : getMoneyFomat(0,4);
				$modata['frozeAmt']   = $value['frozeAmt']!=0 ? getMoneyFomat($value['frozeAmt']/$this->_moneyUnit,4) : '';
				$modata['gmtCreated'] = $value['gmtCreated'] ? date('Y-m-d H:i:s',getSrtTimeByLong($value['gmtCreated'])) : '';
				$modata['summary'] 	  = isset($this->_AccountDetailDataType[$value['summary']]) ? $this->_AccountDetailDataType[$value['summary']] : '';
				array_push($modatas['text'],$modata) ;
                
                $pt_inBal += floatval(str_replace(',', '', $modata['inBal']));
                $pt_frozeAmt += floatval(str_replace(',', '', $modata['frozeAmt']));
                $pt_outBal += floatval(str_replace(',', '', $modata['outBal']));
			}
			array_push($modatas['count'],array("recordNum"=>$result['pager']['total'])) ; //recordNum 页数 ，每页15条

            //page_count
            $modatas['page_count'] = array(
                'inBal'=> getMoneyFomat($pt_inBal,4), 
                'frozeAmt'=> getMoneyFomat($pt_frozeAmt,4), 
                'outBal'=> getMoneyFomat($pt_outBal,4)
            );
            //search_count
            $modatas['all_count'] = array(
                'inBal'=> getMoneyFomat($result['pager']['totalinBal']/$this->_moneyUnit ,4), 
                'frozeAmt'=> getMoneyFomat($result['pager']['totalfrozeAmt']/$this->_moneyUnit ,4), 
                'outBal'=> getMoneyFomat($result['pager']['totaloutBal']/$this->_moneyUnit ,4)
            );
		}
		
		echo json_encode($modatas) ;
	}
	
	//显示总代盈亏表页面
	public function view56(){
		
		//默认显示总代数据
		$usergroup = 0;
		$this->view->aAwardGroup = $this->_aAwardGroup;
		$this->view->usergroup = $usergroup;
		$this->view->searchTime1 = date('Y-m-d');
		$this->view->searchTime2 = date('Y-m-d');
		$this->view->aUserGroup = $this->getUserGroups();
		$this->view->title = "总代盈亏报表";
		$this->view->display ('/admin/funds/reporting/topProfitLossReporting.tpl');
	}
	
	//加载总代盈亏表数据
	public function view56DataSet(){
		$userName 	= getSecurityInput( $this->_request->getPost ("username",''));
		$fromDate 	= getSecurityInput( $this->_request->get ("SearchTime1",''));
		$toDate 	= getSecurityInput( $this->_request->get ("SearchTime2",''));
		$userFreeze = getSecurityInput( $this->_request->getPost ("userFreeze",''));
		$userGameGroup = getSecurityInput( $this->_request->getPost ("userGameGroup",''));
		$userPlayMethod = getSecurityInput( $this->_request->getPost ("userPlayMethod",''));
		$userGroup = getSecurityInput( $this->_request->getPost ("userGroup",''));
		$userUnit = getSecurityInput( $this->_request->getPost ("userUnit",''));
		$page 		= intval(getSecurityInput($this->_request->get ("page")));
		$perPageNum = intval(getSecurityInput($this->_request->get ("perPageNum")));
		$param = new ArrayObject(array());
		//用户名
		if(!empty($userName) && $userName!='请输入您的用户名'){
			$param['account'] = $userName;
		}
		//时间
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$param['startDate'] = $fromDate;
			}
		}
		if(!empty($toDate)){
			if (strtotime($toDate)){
				$param['endDate'] = $toDate;
			}
		}
		//是否冻结
		if(!empty($userFreeze)){
			if($userFreeze =='2'){
				$userFreeze = 0;
			}
			$param['isFreeze'] = intval($userFreeze);
		}
		//游戏名称
		if(!empty($userGameGroup)){
			$param['lotteryId'] = intval($userGameGroup);
		}
		//游戏玩法
		if(!empty($userPlayMethod)){
			$param['betTypeCode'] = $userPlayMethod;
		}
		//用户组
		if(!empty($userGroup) || $userGroup=='0'){
			$param['userLvl'] = intval($userGroup);
		}
		//元角模式
		if(!empty($userUnit)){
			/* if($userUnit =='2'){
				$userUnit = 0;
			} */
			$param['moneyMode'] = intval($userUnit);
		}
		
		$data['param'] = $param;
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"] = ($page+1)*$perPageNum;
		
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
		$result = $this->_queryFundWithdrawOrder->queryTopAgentProfitLoss($data);
		if(isset($result['result']) && count($result['result'])>0){
			foreach ($result['result'] as $key=>$value){
				$modata = array();
				$modata['id']       = !empty($value['id']) ? $value['id'] : '';
				$modata['account'] 	= !empty($value['account']) ? $value['account'] : '';
				$modata['usergroup']= $this->getUserType(intval($value['userLvl']));
				$modata['userLvl'] 	= intval($value['userLvl']);
				$modata['bet']   	= getMoneyFomat($value['bet']/$this->_moneyUnit,4);
				$modata['ret']   	= getMoneyFomat($value['ret']/$this->_moneyUnit,4);
				$modata['trueBet']  = getMoneyFomat($value['trueBet']/$this->_moneyUnit,4);
				$modata['win']   	= getMoneyFomat($value['win']/$this->_moneyUnit,4);
				$modata['activityGifts']   	= getMoneyFomat($value['activityGifts']/$this->_moneyUnit,4);
				
				$isResult = ($value['win']+$value['activityGifts'])-$value['trueBet'];
				
				$modata['result']   = getMoneyFomat($isResult/$this->_moneyUnit,4);
				array_push($modatas['text'],$modata) ;
			}
			array_push($modatas['count'],array("recordNum"=>$result['pager']['total'])) ; //recordNum 页数 
		}
		
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	
	//获取下级用户盈亏表数据
	public function view57DataSet(){
		$userId 	= getSecurityInput( $this->_request->get ("id",''));
		$fromDate 	= getSecurityInput( $this->_request->get ("SearchTime1",''));
		$toDate 	= getSecurityInput( $this->_request->get ("SearchTime2",''));
		$userFreeze = getSecurityInput( $this->_request->getPost ("userFreeze",''));
		$userGameGroup = getSecurityInput( $this->_request->getPost ("userGameGroup",''));
		$userPlayMethod = getSecurityInput( $this->_request->getPost ("userPlayMethod",''));
		$userGroup = getSecurityInput( $this->_request->getPost ("userGroup",''));
		$userUnit = getSecurityInput( $this->_request->getPost ("userUnit",''));
		$page 		= intval(getSecurityInput($this->_request->get ("page")));
		$perPageNum = intval(getSecurityInput($this->_request->get ("perPageNum")));
		$param = new ArrayObject(array());
		//用户名
		if(!empty($userId)){
			$param['id'] = $userId;
		}
		//时间
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$param['startDate'] = $fromDate;
			}
		}
		if(!empty($toDate)){
			if (strtotime($toDate)){
				$param['endDate'] = $toDate;
			}
		}
		//是否冻结
		if(!empty($userFreeze)){
			if($userFreeze =='2'){
				$userFreeze = 0;
			}
			//$param['isFreeze'] = intval($userFreeze);
		}
		//游戏名称
		if(!empty($userGameGroup)){
			$param['lotteryId'] = intval($userGameGroup);
		}
		//游戏玩法
		if(!empty($userPlayMethod)){
			$param['betTypeCode'] = $userPlayMethod;
		}
		//用户组
		if(!empty($userGroup) || $userGroup=='0'){
			//$param['userLvl'] = intval($userGroup);
		}
		//元角模式
		if(!empty($userUnit)){
			/* if($userUnit =='2'){
				$userUnit = 0;
			} */
			$param['moneyMode'] = intval($userUnit);
		}
	
		$data['param'] = $param;
		$data["pager"]["startNo"] = $page*$perPageNum+1;
		$data["pager"]["endNo"] = ($page+1)*$perPageNum;
		$modatas = array();
		$modatas['text'] = array();
		$modatas['param'] = $param;
		$modatas['count'] = array();
		$result = $this->_queryFundWithdrawOrder->querySubAgentProfitLoss($data);
		if(isset($result['result']) && count($result['result'])>0){
			foreach ($result['result'] as $key=>$value){
				$modata = array();
				$modata['id']       = !empty($value['id']) ? $value['id'] : '';
				$modata['account'] 	= !empty($value['account']) ? $value['account'] : '';
				$modata['usergroup']= $this->getUserType(intval($value['userLvl']));
				$modata['userLvl'] 	= intval($value['userLvl']);
				$modata['bet']   	= getMoneyFomat($value['bet']/$this->_moneyUnit,4);
				$modata['ret']   	= getMoneyFomat($value['ret']/$this->_moneyUnit,4);
				$modata['trueBet']  = getMoneyFomat($value['trueBet']/$this->_moneyUnit,4);
				$modata['win']   	= getMoneyFomat($value['win']/$this->_moneyUnit,4);
				$modata['activityGifts']   	= getMoneyFomat($value['activityGifts']/$this->_moneyUnit,4);

				$isResult = ($value['win']+$value['activityGifts'])-$value['trueBet'];
				
				$modata['result']   = getMoneyFomat($isResult/$this->_moneyUnit,4);
				array_push($modatas['text'],$modata) ;
			}
			array_push($modatas['count'],array("recordNum"=>$result['pager']['total'])) ; //recordNum 页数
		}
	
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	
	//显示玩法
	public function view58(){
		$_POST['id'] 			= getSecurityInput( $this->_request->get("id",''));
		$_POST['SearchTime1'] 	= getSecurityInput( $this->_request->get("SearchTime1",''));
		$_POST['SearchTime2'] 	= getSecurityInput( $this->_request->get("SearchTime2",''));
		$_POST['userGameGroup'] = getSecurityInput( $this->_request->get("userGameGroup",''));
		$_POST['userUnit'] = getSecurityInput( $this->_request->get("userUnit",''));
		$this->view->title = "玩法明细";
		$this->view->aAwardGroup = $this->_aAwardGroup;
		$this->view->_POST = $_POST;
		$this->view->display ('/admin/funds/reporting/loadPlayMethod.tpl');
	}
	
	//获取详细玩法的盈亏数据
	public function view58DataSet(){
		$userId 	= getSecurityInput( $this->_request->getPost ("id",''));
		$fromDate 	= getSecurityInput( $this->_request->getPost ("SearchTime1",''));
		$toDate 	= getSecurityInput( $this->_request->getPost ("SearchTime2",''));
		$userGameGroup = getSecurityInput( $this->_request->getPost ("userGameGroup",''));
		$userUnit 	   = getSecurityInput( $this->_request->getPost ("userUnit",''));
		$param = new ArrayObject(array());
		//用户名
		if(!empty($userId)){
			$param['id'] = $userId;
		}
		//时间
		if(!empty($fromDate)){
			if (strtotime($fromDate)){
				$param['startDate'] = $fromDate;
			}
		}
		if(!empty($toDate)){
			if (strtotime($toDate)){
				$param['endDate'] = $toDate;
			}
		}
		//游戏名称
		if(!empty($userGameGroup)){
			$param['lotteryId'] = intval($userGameGroup);
		}
		//元角模式
		if(!empty($userUnit)){
			/* if($userUnit =='2'){
				$userUnit = 0;
			} */
			$param['moneyMode'] = intval($userUnit);
		}
	
		$data['param'] = $param;
		$modatas = array();
		$modatas['text'] = array();
		$result = $this->_queryFundWithdrawOrder->getUserBetIncomes($data);
		if(isset($result['groups']) && count($result['groups'])>0){
			foreach ($result['groups'] as $key=>$value){
				$modata = array();
				$value['cnt'] = 0;
				
				foreach ($value['sets'] as $subKey=>$subValue){
					$subValue['cnt'] = 0;
					foreach ($subValue['details'] as $subKey1=>$subValue1){
						$subValue1['methodCodeName'] = !empty($subValue1['methodCodeName']) ? $subValue1['methodCodeName'] : '';
						$subValue1['bet'] 	  = getMoneyFomat($subValue1['bet']/$this->_moneyUnit,4);
						$subValue1['ret'] 	  = getMoneyFomat($subValue1['ret']/$this->_moneyUnit,4);
						$subValue1['trueBet'] = getMoneyFomat($subValue1['trueBet']/$this->_moneyUnit,4);
						$subValue1['win'] 	  = getMoneyFomat($subValue1['win']/$this->_moneyUnit,4);
						$subValue1['result']  = getMoneyFomat($subValue1['result']/$this->_moneyUnit,4);
						$modata['activityGifts']   	= getMoneyFomat($value['activityGifts']/$this->_moneyUnit,4);
						$subValue['details'][$subKey1] = $subValue1;
						$value['cnt']++;
						$subValue['cnt']++;
					}
					$value['sets'][$subKey] = $subValue;
				}
				array_push($modatas['text'],$value) ;
			}
		}
	
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
	
	
	//充值明细报表数据
	public function recharge(){
	
		$accountName = $this->_request->getPost ("accountName","");
		$cardnumber = $this->_request->getPost ("cardnumber","");
		$loadingmoney1 = $this->_request->getPost ("loadingmoney1","");
		$loadingmoney2 = $this->_request->getPost ("loadingmoney2","");
		$message = $this->_request->getPost ("message","");
		$page = $this->_request->getPost ("page",0);//当前页面
		$perPageNum = $this->_request->getPost ("perPageNum",0); //每页面数量
		$receivemoney = $this->_request->getPost ("receivemoney",0);
		$receivetime1 = $this->_request->getPost ("receivetime1",0);
		$receivetime2 = $this->_request->getPost ("receivetime2",0);
		$requestMoney1 = $this->_request->getPost ("requestMoney1",0);
		$requestMoney2 = $this->_request->getPost ("requestMoney2",0);
		$requesttime1 = $this->_request->getPost ("requesttime1",0);
		$requesttime2 = $this->_request->getPost ("requesttime2",0);
		$serial = $this->_request->getPost ("serial",0);
		$username = $this->_request->getPost ("username",0);
		
		$start = $page+1 ; //($page-1)*$perPageNum;
		$data=array();
		$data["param"]["sn"]= "" ;
		$data["param"]["account"]="";
		//首次加载不需要使用fromDate 和 toDate 字段
		//$data["param"]["fromDate"]= ;
		//$data["param"]["toDate"]=  getSendTime() + 97815;
		$data["param"]["status"]=array(1);
		$data["pager"]["startNo"] = $page*$perPageNum+1;//$start;
		$data["pager"]["endNo"]=$start*$perPageNum;//$start+$perPageNum-1;
	
		$rsr = $this->_chargeQuery->chargeQuery($data);
	
		$modata = array();
		$modatas = array();
		$modatas['text'] = array();
		$modatas['count'] = array();
	
		foreach ( $rsr as $recorder){
			$modata["sn"] = $recorder->getMember("sn") ;
			$modata["applyTime"] = $recorder->getMember("applyTime") ;
			$modata["chargeTime"] = date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("chargeTime")))  ;
			$modata["applyAmt"] = $recorder->getMember("applyAmt") ;
			$modata["chargeAmt"] = $recorder->getMember("chargeAmt") ;
			$modata["bankId"] = $recorder->getMember("bankId") ;
			$modata["status"] = $recorder->getMember("status") ;
			$modata["chargeMemo"] = $recorder->getMember("chargeMemo") ;
			$modata["account"] = $recorder->getMember("account") ;
			$modata["userAct"] = $recorder->getMember("userAct") ;
			$modata["cardNumber"] = $recorder->getMember("cardNumber") ;
			$modata["mcFee"] = $recorder->getMember("mcFee") ;
			array_push($modatas['text'],$modata) ;
		}
		array_push($modatas['count'],array("recordNum"=>count($rsr))) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}

	
	//获取彩种玩法
	public function getPlayMethod(){
		$lotteryId = $this->_request->getPost('id');
		$data['param']['lotteryId'] = $lotteryId;
		$result = $this->_queryFundWithdrawOrder->queryPlayMethodByLottory($data);
		if(count($result)>0){
			echo Zend_Json::encode(array('isSuccess'=>'1','data'=>$result));
			exit;
		}
		echo Zend_Json::encode(array('isSuccess'=>'0','data'=>$result));
		exit;
	}
	
	
	//获取所有用户组
	public function getUserGroups(){
		for ($i=-1;$i<=11;$i++){
			$aUserGroup[$i] = $this->getUserType($i);
		}
		return $aUserGroup;
	}
}
