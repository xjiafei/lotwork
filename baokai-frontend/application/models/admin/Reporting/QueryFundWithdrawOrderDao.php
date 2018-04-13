<?php
class QueryFundWithdrawOrderDao extends Client {
	
	public $head = null;
	public $body = null;
	
	public function __construct() {
		parent::__construct ();
	}
	
	public function queryFundWithdrawOrder($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		$turl = array("fundadmin"=>"queryFundWithdrawOrder");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($recorders["result"],  new CustomersDrawRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
			return 	$recorders ;
		}
		return array();	
	}
        
	public function queryFundWithdrawOrderBySn($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		$turl = array("fundadmin"=>"queryFundWithdrawOrderBySn");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($recorders["result"],  new CustomersDrawRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
			return 	$recorders ;
		}
		return array();	
	}
	
	//查詢MOW AND更新提現訂單狀態
        public function checkAndUpdateWithDrawMowStatus($jdata = null) {
            $recorders = array();
            $recorders["result"] = array();
            $turl = array("fundadmin" => "checkAndUpdateWithDrawMowStatus");
            $rsr = $this->inRequestV2($jdata, $turl);
            if ($rsr['head']['status']==1 && isset($rsr['body']['result'])) {
                return new CustomersDrawRecorder($rsr['body']['result']);
            }
            return array();
        }
	
	
	//查詢目前提現訂單詳情 
	public function queryWithDrawNowDetail($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		$turl = array("fundadmin"=>"queryWithDrawNowDetail");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($recorders["result"],  new CustomersDrawRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
			return 	$recorders ;
		}
		return array();	
	}
	
	//查詢目前退款訂單
	public function queryReFundWithdrawOrder($jdata = null) {
		$recorders = array();
		$recorders["result"] = array();
		$recorders["pager"] =array();
		$turl = array("fundadmin"=>"queryReFundWithdrawOrder");
		$rsr = $this->inRequestV2($jdata, $turl) ;
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($recorders["result"],  new CustomersDrawRecorder($v1)) ;
			}
			$recorders["pager"] = $rsr["body"]["pager"] ;
			return 	$recorders ;
		}
		return array();	
	}
	
	public function queryAccountDetailRecorders($jdata){
		$aRecoder = array();
		$aRecoder["result"] = array();
		$aRecoder["pager"]  = array();
		$turl = array("fundadmin"=>"queryFundChangeLog");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($aRecoder["result"],  $v1);
			}
			$aRecoder["pager"] = $rsr["body"]["pager"] ;
			return 	$aRecoder ;
		}
		return array();
	}
	
	
	//总代游戏币明细
	public function getGameGoldDetails($jdata){
		$aRecoder = array();
		$aRecoder["result"] = array();
		$turl = array("fundadmin"=>"getGameGoldDetails");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($aRecoder["result"],  $v1);
			}
			$aRecoder["pager"] = $rsr["body"]["pager"] ;
			return 	$aRecoder ;
		}
		return array();
	}
	
	//总代充提
	public function queryTopChargeWithdrawRpt($jdata){
		$aRecoder = array();
		$aRecoder["result"] = array();
		$aRecoder["pager"]  = array();
		$turl = array("fundadmin"=>"queryTopChargeWithdrawRpt");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				array_push($aRecoder["result"],  $v1);
			}
			$aRecoder["pager"] = $rsr["body"]["pager"] ;
			return 	$aRecoder ;
		}
		return array();
	}
	
	//总代盈亏表
	public function queryTopAgentProfitLoss($jdata){
		$aRecoder = array();
		$aRecoder["result"] = array();
		$aRecoder["pager"]  = array();
		$turl = array("fundadmin"=>"getUserAgentIncomes");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"]['vos'] as $k1 => $v1){
				$aRecoder["result"][$k1] = $v1;
			}
			$aRecoder["pager"] = $rsr["body"]["pager"] ;
			return 	$aRecoder ;
		}
		return array();
	}
	
	//查看总代盈亏下级报表
	public function querySubAgentProfitLoss($jdata){
		$aRecoder = array();
		$aRecoder["result"] = array();
		$aRecoder["pager"]  = array();
		$turl = array("fundadmin"=>"getUserAndAgentIncomes");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"]['vos'] as $k1 => $v1){
				$aRecoder["result"][$k1] = $v1;
			}
			$aRecoder["pager"] = $rsr["body"]["pager"] ;
			return 	$aRecoder ;
		}
		return array();
	}
	
	//根据奖金组查询玩法
	public function queryPlayMethodByLottory($jdata){
		$aRecoder = array();
		$turl = array("fundadmin"=>"getBetTypes");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']['dtos']) &&  count($rsr['body']['result']['dtos'])>0){
			foreach($rsr["body"]["result"]['dtos'] as $k1 => $v1){
				$aRecoder[$k1]['gameBetType'] = !empty($v1['gameBetType']) ? $v1['gameBetType'] : '';
				$aRecoder[$k1]['gameBetTypeShow'] = !empty($v1['gameBetTypeShow']) ? $v1['gameBetTypeShow'] : '';
			}
			return 	$aRecoder ;
		}
		return array();
	}
	
	//根据用户查询玩法详细收益
	public function getUserBetIncomes($jdata){
		$aRecoder = array();
		$turl = array("fundadmin"=>"getUserBetIncomes");
		$rsr = $this->inRequestV2($jdata, $turl);
		if(isset($rsr['body']['result']) &&  count($rsr['body']['result'])>0){
			foreach($rsr["body"]["result"] as $k1 => $v1){
				$aRecoder[$k1] = $v1;
			}
			return 	$aRecoder ;
		}
		return array();
	}
}
?>