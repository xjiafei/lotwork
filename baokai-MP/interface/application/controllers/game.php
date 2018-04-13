<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//include_once "./application/models/BetMgr.php";
//include_once "./application/libraries/StrMgr.php";


class Game extends MY_Controller {

	public function __construct()
	{
		parent::__construct();
	}
	
	public function initData()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				if($_POST["lottery_id"] == "1")
				{
					$this->my_post(GAME_INITDATA_3D, $_POST);
				}
				else if($_POST["lottery_id"] == "3")
				{
					$this->my_post(GAME_INITDATA_SSQ, $_POST);
				}
			}
			else if($_POST["chan_id"] == "4")
			{
				if($_POST["lottery_id"] == "1")
				{
					$this->my_post(GAME_INITDATA_CQSSC, $_POST);
				}
				else if($_POST["lottery_id"] == "5")
				{
					$this->my_post(GAME_INITDATA_SD11X5, $_POST);
				}
				else if($_POST["lottery_id"] == "11")
				{
					$this->my_post(GAME_INITDATA_LLSSC, $_POST);
				}
				else if($_POST["lottery_id"] == "13")
				{
					$this->my_post(GAME_INITDATA_LL11X5, $_POST);
				}
				else if($_POST["lottery_id"] == "14")
				{
					$this->my_post(GAME_INITDATA_JLFFC, $_POST);
				}
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			
			//$this->_getInitData();
			//var_dump($this->_getInitData());
			
			$this->echoStrPost(json_encode($this->_getInitData()));
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                    $lottery_id = $_POST['lottery_id'];
                    unset($_POST['lottery_id']);
                    
                    $this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_INITDATA), $_POST);
                }
	}
	
	private function _getInitData($app_id)
	{
		// cache app_info
		//$this->load->model('Mem_cache_model');
		//$rtn = $this->Mem_cache_model->get(MC_LOTTERY_METHOD . "_" . $this->mPlatform);
		if($rtn == null)
		{
			$rtn = array();
			$this->load->model('Lottery_model');
			$this->load->model('Method_model');
			
			if($this->mPlatform == PLATFORM_3)
			{
				
			}
			else if($this->mPlatform == PLATFORM_4)
			{	
				$rtn["lotterys"] = array();
				$lotterys = $this->Lottery_model->getLotteryList4();
				foreach($lotterys as $key => $value) 
				{
					array_push($rtn["lotterys"], $value);
				}
				
				
				$rtn["methods"] = array();
				$methods = $this->Method_model->getLotteryJoinMethod4();
				foreach($methods as $key => $value) 
				{
					$method = array();
					$method["chan_id"] = $value["chan_id"];
					$method["lot_id"] = $value["lot_id"];
					$method["s_id"] = $value["s_id"];
					$method["s_name"] = $value["s_name"];
					$method["method_id"] = $value["method_id"];
					$method["method_name"] = $value["method_name"];
					$method["maxmultiple"] = $value["maxmultiple"];
					
					array_push($rtn["methods"], $method);
				}
				/*
				$chan_id = 0;
				$lot_id = 0;
				
				foreach($rs as $key => $value) 
				{
					if($chan_id != $value["chan_id"])
					{
						$chan_id = $value["chan_id"];
					}
					if($lot_id != $value["lot_id"])
					{
						$lot_id = $value["lot_id"];
						if($row != null)
						{
							$row["methods"] = $methods;
							array_push($rtn, $row);
						}
						$row = array();
						$methods = array();
					}
					$row["chan_id"] = $chan_id;
					$row["lot_id"] = $lot_id;
					$row["lot_name"] = $value["lot_name"];
					
					$method = array();
					$method["s_id"] = $value["s_id"];
					$method["s_name"] = $value["s_name"];
					$method["method_id"] = $value["method_id"];
					$method["method_name"] = $value["method_name"];
					$method["maxmultiple"] = $value["maxmultiple"];
					array_push($methods, $method);
					
				}
				*/
			}
			
			
			//$this->Mem_cache_model->save(MC_LOTTERY_METHOD . "_" . $this->mPlatform, $app_info, MC_LOTTERY_METHOD_CACHE_TIME);
		}
		return $rtn;
	}
    
    	public function simpleInitData()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$url = "";
			if($_POST["chan_id"] == "1")
			{
				$url = GAME_SIMPLEINITDATA_LOW;
			}
			else if($_POST["chan_id"] == "4")
			{
				$url = GAME_SIMPLEINITDATA_HIGH;
			}
			if(is_null($_POST["lottery_id"]))
			{
				$this->my_post($url, $_POST);
			}
			else
			{
				$json = $this->getArrayResult($url, $_POST);
				if(isset($json["messagetype"]))
				{
					$this->echoJsonPost($json);
				}
				else
				{
					foreach($json as $key => $value)
					{
						$value["chan_id"] = $_POST["chan_id"];
						$value["lottery_id"] = $_POST["lottery_id"];
						$this->echoJsonPost($value);
						break;
					}
				}
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"];
			
			//$this->my_post(GAME_SIMPLEINITDATA_4, $_POST);
			
			$rtn = $this->getArrayResult(GAME_SIMPLEINITDATA_4, $_POST);
			foreach($rtn["awardGroups"] as $key => $value)
			{
				unset($rtn["awardGroups"][$key]["lotterySeriesCode"]);
				unset($rtn["awardGroups"][$key]["lotterySeriesName"]);
				unset($rtn["awardGroups"][$key]["groupChain"]);
				unset($rtn["awardGroups"][$key]["directRet"]);
				unset($rtn["awardGroups"][$key]["threeoneRet"]);
				unset($rtn["awardGroups"][$key]["status"]);
				unset($rtn["awardGroups"][$key]["directLimitRet"]);
				unset($rtn["awardGroups"][$key]["threeLimitRet"]);
				unset($rtn["awardGroups"][$key]["lotteryId"]);
			}
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $lottery_id = $_POST['lottery_id'];
                        unset($_POST['lottery_id']);
                        $this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_SIMPLEINITDATA), $_POST);
		}
	}
	
	public function addBonusData()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"];
			$rtn = $this->getArrayResult(GAME_ADDBONUSDATA_4, $_POST);
			$this->echoJsonPost($rtn);
			//$this->my_post(GAME_ADDBONUSDATA_4, $_POST);
		}
	}
	
	public function getMethodList()
	{
		$rs = array();
		$this->load->model('Method_model');
		
		if($this->mPlatform == PLATFORM_3)
		{
			$rs = $this->Method_model->getMethodListOfPlatform3();
		}
		else if($this->mPlatform == PLATFORM_ADMIN)
		{
			$rs = $this->Method_model->getMethodListOfPlatformAdmin();
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$rs = $this->Method_model->getMethodListOfPlatform4();
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST["CGISESSID"] = $this->sessionAgent();
			$rs = $this->Method_model->getMethodListOfPlatform3();
		}
		$this->echoStrPost(json_encode($rs));
	}
	
	public function buy()
	{
		$this->load->library('StrMgr');
		
		/*
		REQUEST
		chan_id		x
		lottery_id	x
		issue		x
		money		x
		
		projects
			codes name methodid mode nums times
		trace_istrace
		trace_stop
		trace_issues
		*/
                if($this->mPlatform == PLATFORM_BM)
                    $this->_buyBomao();
		else if($this->mPlatform == PLATFORM_3)
		{
			$buy = array();
			$buy["chan_id"] = $_POST["chan_id"];
			$buy["lotteryid"] = $_POST["lottery_id"];
			
			if($buy["chan_id"] == "1")
			{
				$buy["flag"] = "save";
				$buy["lottery_currentissue"] = $_POST["issue"];
				$buy["lottery_totalamount"] = $_POST["money"];
				$buy["lottery_istrace"] = $_POST["trace_istrace"];
				
				$trace_times = null;	// 追号倍数
				if($buy["lotteryid"] == "1")
				{
					$project = null;
					foreach($_POST["projects"] as $key => $value) 
					{
						$project = $value;
						break;	
					}
					$buy["methodid"] = $project["methodid"];
					$buy["lottery_confirmnums"] = $project["codes"];
					$buy["lottery_times"] = $project["times"];
					$buy["lottery_totalnum"] = $project["nums"];
				}
				else if($buy["lotteryid"] == "3")
				{
					$confirmnums = array();
					$totla_nums = 0;
					$this->load->library('StrMgr');
					
					
					foreach($_POST["projects"] as $key => $value) 
					{
						$str = $value["codes"] . ":" . $value["methodid"] . ":" . $value["times"] . ":" . StrMgr::getNumString(16);
				 		array_push($confirmnums, $str);
				 		$totla_nums += $value["nums"];
				 		$buy["lottery_times"] = $value["times"];
					}
					$buy["lottery_confirmnums"] = $confirmnums;
					
					$buy["lottery_totalnum"] = $totla_nums;
				}
				
				if(strcmp($buy["lottery_istrace"], "1") == 0)
				{
					//$_POST["lottery_totalamount"] = "2";	//没用所以随便填一个数目
					$buy["trace_stop"] = $_POST["trace_stop"];
					$buy["trace_totalamount"] = $_POST["money"];
					
					$nums = count($_POST["trace_issues"]);
					$buy["trace_issues_sametimes"] = $nums;
					
					$buy["trace_issue"] = $_POST["trace_issues"];
					foreach($buy["trace_issue"] as $key => $value) 
					{
					 	$buy["trace_times_" . $value] = $buy["lottery_times"];
					}
				}
				//$this->my_post(GAME_BUY_LOW, $buy);
				$json = $this->getArrayResult(GAME_BUY_LOW, $buy);
				if($json["status"] == "success")
				{
					$this->_logProject($_POST, $buy["lottery_times"]);
				}
				$this->echoJsonPost($json);
			}
			else if($buy["chan_id"] == "4")
			{
				$buy["flag"] = "save";
				$buy["curmid"] = "50";
				$buy["lt_project_modes"] = "1";
				$buy["lt_sel_times"] = "1";
				$buy["select4"] = "1倍";
				$buy["lt_total_nums"] = 2;	//没用所以随便填一个数目
				
				$buy["lt_issue_start"] = $_POST["issue"];
				$buy["lt_total_money"] = $_POST["money"];
				$buy["lt_trace_if"] = $_POST["trace_istrace"];
				$buy["lt_trace_stop"] = $_POST["trace_stop"];
				
				$projects = array();
				$trace_times = null;	// 追号倍数
				foreach($_POST["projects"] as $key => $value) 
				{
					$project = array();
				 	$project["methodid"] = $value["methodid"];
				 	$project["codes"] = $value["codes"];
				 	$project["mode"] = $value["mode"];
				 	$project["nums"] = $value["nums"];
				 	$project["times"] = $value["times"];
				 	$project["name"] = $value["name"];
				 	$project["desc"] = "[" . $project["name"] . "]" . str_replace("|", ",", $project["codes"]);
				 	
				 	$unit = $project["mode"]=="1"?2:0.2;
				 	$project["money"] = $unit * $project["nums"] * $project["times"];
				 	
				 	if((strcmp($buy["lotteryid"], "1") == 0 && strcmp($project["methodid"], "38") == 0)		// SSC_HouErDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "1") == 0 && strcmp($project["methodid"], "471") == 0)	// SSC_HouSanTeShuHao
				 		|| (strcmp($buy["lotteryid"], "1") == 0 && strcmp($project["methodid"], "489") == 0)	// SSC_QianSanDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "1") == 0 && strcmp($project["methodid"], "490") == 0)	// SSC_HouSanDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "11") == 0 && strcmp($project["methodid"], "598") == 0)	// SSC_HouErDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "11") == 0 && strcmp($project["methodid"], "638") == 0)	// SSC_HouSanTeShuHao
				 		|| (strcmp($buy["lotteryid"], "11") == 0 && strcmp($project["methodid"], "648") == 0)	// SSC_QianSanDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "11") == 0 && strcmp($project["methodid"], "649") == 0)	// SSC_HouSanDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "14") == 0 && strcmp($project["methodid"], "883") == 0)	// SSC_HouErDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "14") == 0 && strcmp($project["methodid"], "923") == 0)	// SSC_HouSanTeShuHao
				 		|| (strcmp($buy["lotteryid"], "14") == 0 && strcmp($project["methodid"], "933") == 0)	// SSC_QianSanDaXiaoDanShuang
				 		|| (strcmp($buy["lotteryid"], "14") == 0 && strcmp($project["methodid"], "934") == 0)	// SSC_HouSanDaXiaoDanShuang
				 		)
				 	{
				 		$project["type"] = "dxds";
				 	}
				 	else
				 	{
				 		$project["type"] = "digital";
				 	}
				 	$trace_times = $project["times"];
				 	array_push($projects, $project);
				}
				$buy["lt_project"] = json_encode($projects);
				
				if(strcmp($buy["lt_trace_if"], "1") == 0)
				{
					$buy["lt_trace_if"] = "yes";
					$buy["lt_trace_money"] = "2";
					foreach($_POST["trace_issues"] as $key => $value) 
					{
					 	$buy["lt_trace_times_" . $value] = $trace_times;
					}
					$buy["lt_trace_issues"] = json_encode($_POST["trace_issues"]);
					if(strcmp($buy["lt_trace_stop"], "1") == 0)
					{
						$buy["lt_trace_stop"] = "yes";
					}
					else
					{
						$buy["lt_trace_stop"] = "yes";
					}
				}
				//$this->my_post(GAME_BUY_HIGH, $buy);
				$json = $this->getArrayResult(GAME_BUY_HIGH, $buy);
				if($json["status"] == "success")
				{
					$this->_logProject($_POST, $trace_times);
				}
				$this->echoJsonPost($json);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$this->load->library('UtilMgr');
			$buy = array();
			$buy["saleTime"] = UtilMgr::getMicroSec();
			$buy["userIp"] = UtilMgr::ipToLong(UtilMgr::getRealIP());
			
			$buy["isFirstSubmit"] = $_POST["isFirstSubmit"]!=null?$_POST["isFirstSubmit"]:0;
			if($buy["isFirstSubmit"] == 1)
			{
				$buy["slipResonseDTOList"] = $_POST["slipResonseDTOList"];
			}
			
			$buy["channelId"] = $this->mApp_info->come_from==3?2:4;
			$buy["channelVersion"] =  $this->mApp_info->version;
			
			// 双色球暂时不支援角模式
			if($_POST["chan_id"] == 1 && $_POST["lotteryId"] == 3 && ($_POST["list"][0]["mode"] == 2 || $_POST["list"][0]["mode"] == 3))
			{
				$err = ErrHandler::getErrInfo($this->mPlatform, 109887, true);
				echo $this->encrypt($err);
				die();
			}
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$buy["lotteryId"] = $lottery["4_lot_id"];
			
			$buy["issue"] = $_POST["issue"];
			$buy["traceStop"] = 0;
			$buy["money"] = $_POST["money"];
			
			$buy["list"] = $_POST["list"];
			foreach($buy["list"] as $key => $value) 
			{
				$method = BetMgr::getMethodWith3($_POST["chan_id"], $_POST["lotteryId"], $value["methodid"]);
	$buy["list"][$key]["methodid"] = $method["4_method_id"];
$this->load->library('LogMgr');
			LogMgr::ErrorLog("sssssssssssssssss".$buy["list"][$key]["codes"] . "\r\n");
			}
			
			if($_POST["traceIstrace"] == "1")
			{
				$buy["traceIstrace"] = $_POST["traceIstrace"];
				$buy["traceStop"] = $_POST["traceStop"];
				$buy["traceIssues"] = $_POST["traceIssues"];
				$buy["traceTimes"] = $_POST["traceTimes"];
				
				// 追号时自动将注单投注倍数给为1倍
				foreach($buy["list"] as $key => $value) 
				{
					$buy["list"][$key]["money"] = $buy["list"][$key]["money"] / $buy["list"][$key]["times"];
					$buy["list"][$key]["times"] = 1;
				}
				
				
				$json = $this->getArrayResult(GAME_PLAN_4, $buy);
			}
			else
			{
				$json = $this->getArrayResult(GAME_BUY_4, $buy);
			}
			//var_dump($json);
			
			//$this->echoJsonPost($json);
			//die();
			foreach($json["overMutipleDTO"] as $key => $value) 
			{
				$method = BetMgr::getMethodWith4($buy["lotteryId"], $json["overMutipleDTO"][$key]["betTypeCode"]);
				$json["overMutipleDTO"][$key]["betTypeCode"] = $method["3_method_id"];
			}
			// 封锁变价处理(一般投注)
			if($json["gameOrderDTO"])
			{
				$beforeAmount = $json["gameOrderDTO"]["totalAmount"];
				$afterAmount = 0;
				
				$slipList = $json["gameOrderDTO"]["slipResonseDTOList"];
				
				$locks = array();
				$slips = array();
				$lists = array();
				foreach($slipList as $key => $value) 
				{
					$afterAmount += $value["totalAmount"];
					
					$betMethod = $value["betMethod"];
					$betDetail = $value["betDetail"];
					$method = BetMgr::getMethodWith4($buy["lotteryId"], $value["gameBetType"]["betTypeCode"]);
					$methodid = $method["3_method_id"];
					if($value["lockPoints"]["isLocks"])
					{
						$realBeishu = $value["lockPoints"]["realBeishu"];
						foreach($value["lockPoints"]["locks"] as $key2 => $value2) 
						{
							$lock = array();
							$lock["desc"] = "[" . $betMethod . "][" . $key2 . "]";
							$lock["planBeishu"] = $realBeishu + $value2;
							$lock["realBeishu"] = $realBeishu;
							array_push($locks, $lock);
						}
					}
					if($value["lockPoints"]["isChange"])
					{
						foreach($value["lockPoints"]["points"] as $key2 => $value2) 
						{
							$slip = array();
							$slip["desc"] = "[" . $betMethod . "] " . $betDetail;
							$slip["code"] = $value2["point"];
							$slip["prize"] = $value2["retValue"];
							$slip["realBeishu"] = $value2["mult"];
							array_push($slips, $slip);
						}
					}
					
					if($value["totalAmount"] > 0)
					{
						$list = array();
						$list["codes"] = $betDetail;
						$list["methodid"] = $methodid;
						$list["mode"] = $value["moneyMode"]=="YUAN"?1:2;
						$list["money"] = $value["totalAmount"];
						$list["nums"] = $value["totalBet"];
						$list["times"] = $value["multiple"];
						array_push($lists, $list);
					}
				}
				$reduceAmount = $beforeAmount - $afterAmount;
				
				// 过滤资料
				$keep_keys = array("gameBetType", "lockPoints");
				foreach($slipList as $key => $value) 
				{
					foreach($value as $key2 => $value2) 
					{
						if(!in_array($key2, $keep_keys))
						{
							unset($slipList[$key][$key2]);
						}else{
							foreach($value2 as $key3 => $value3)
							{
								if($slipList[$key][$key2][$key3] == null)
								{
									unset($slipList[$key][$key2][$key3]);
								}
							}	
						}
					}
				}
				
				unset($json["gameOrderDTO"]);
				$json["gameOrderDTO"]["beforeAmount"] = $beforeAmount;
				$json["gameOrderDTO"]["afterAmount"] = $afterAmount;
				$json["gameOrderDTO"]["reduceAmount"] = $reduceAmount;
				$json["gameOrderDTO"]["isSlip"] = count($slips) > 0?1:0;
				$json["gameOrderDTO"]["isLock"] = count($locks) > 0?1:0;
				$json["gameOrderDTO"]["lists"] = $lists;
				$json["gameOrderDTO"]["slipResonseDTOList"] = $slipList;
				//$json["gameOrderDTO"]["slips"] = $slips;
				//$json["gameOrderDTO"]["locks"] = $locks;
			}
			// 封锁变价处理(追号)
			if($json["gameOrderResponseDTO"])
			{
				$beforeAmount = $json["gameOrderResponseDTO"]["totalAmount"];
				$afterAmount = 0;
				
				$slipList = $json["gameOrderResponseDTO"]["slipResonseDTOList"];
				$locks = array();
				$slips = array();
				$lists = $_POST["list"];
				foreach($slipList as $key => $value) 
				{
					$afterAmount += $value["totalAmount"];
					
					$betMethod = $value["betMethod"];
					$betDetail = $value["betDetail"];
					$method = BetMgr::getMethodWith4($buy["lotteryId"], $value["gameBetType"]["betTypeCode"]);
					$methodid = $method["3_method_id"];
					if($value["lockPoints"]["isLocks"])
					{
						$realBeishu = $value["lockPoints"]["realBeishu"];
						foreach($value["lockPoints"]["locks"] as $key2 => $value2) 
						{
							$lock = array();
							$lock["desc"] = "[" . $betMethod . "][" . $key2 . "]";
							$lock["issueCode"] = $value["issueCode"];
							$lock["planBeishu"] = $realBeishu + $value2;
							$lock["realBeishu"] = $realBeishu;
							array_push($locks, $lock);
						}
					}
					if($value["lockPoints"]["isChange"])
					{
						foreach($value["lockPoints"]["points"] as $key2 => $value2) 
						{
							$slip = array();
							$slip["desc"] = "[" . $betMethod . "] " . $betDetail;
							$slip["code"] = $value2["point"];
							$slip["prize"] = $value2["retValue"];
							$slip["realBeishu"] = $value2["mult"];
							array_push($slips, $slip);
						}
					}
					foreach($lists as $key2 => $value2) 
					{
						if($value2["codes"] == $betDetail && $value2["methodid"] == $methodid)
						{
							$lists[$key2]["times"] = $value["multiple"];
							$lists[$key2]["money"] = $value["totalAmount"];
							
							if($value["totalAmount"] <= 0)
							{
								//echo "codes1:" . $value2["codes"] . "==" . $betDetail . "methodid:" . $value2["methodid"] . "==" . $methodid . "\r\n";
								//var_dump($key2);
								//unset($lists[$key2]);
								array_splice($lists, $key2, 1);
							}
						}
					}
				}
				$reduceAmount = $beforeAmount - $afterAmount;
				
				// 过滤资料
				$keep_keys = array("gameBetType", "lockPoints");
				foreach($slipList as $key => $value) 
				{
					foreach($value as $key2 => $value2) 
					{
						if(!in_array($key2, $keep_keys))
						{
							unset($slipList[$key][$key2]);
						}else{
							foreach($value2 as $key3 => $value3)
							{
								if($slipList[$key][$key2][$key3] == null)
								{
									unset($slipList[$key][$key2][$key3]);
								}
							}	
						}
					}
				}
				
				unset($json["gameOrderResponseDTO"]);
				$json["gameOrderDTO"]["beforeAmount"] = $beforeAmount;
				$json["gameOrderDTO"]["afterAmount"] = $afterAmount;
				$json["gameOrderDTO"]["reduceAmount"] = $reduceAmount;
				$json["gameOrderDTO"]["isSlip"] = count($slips) > 0?1:0;
				$json["gameOrderDTO"]["isLock"] = count($locks) > 0?1:0;
				$json["gameOrderDTO"]["lists"] = $lists;
				$json["gameOrderDTO"]["slipResonseDTOList"] = $slipList;
				//$json["gameOrderDTO"]["slips"] = $slips;
				//$json["gameOrderDTO"]["locks"] = $locks;
			}
			// 统一输出流水号
			if(array_key_exists("gamePlanId",$json))
			{
				//var_dump($json);
				$json["orderId"] = $json["gamePlanId"];
				unset($json["gamePlanId"]);
			}
			// log			
			if($json["orderId"] > 0)
			{
				$trace_times = 0;
				if($_POST["traceTimes"] != null)
				{
					$trace_times = explode(",", $_POST["traceTimes"]);
					$trace_times = $trace_times[0];
				}
				$this->_logProject4($_POST, $trace_times);
			}
			$this->echoJsonPost($json);
		}
	}
	
	private function _logProject4($data, $trace_times = 0)
	{
		$this->load->model('Project_rec_model');
		$this->load->model('Method_model');
		
		$projects = $data["list"];

		foreach($projects as $key => $value) 
		{
			$rec = array();
			$rec["app_id"] = $data["app_id"];
			$rec["token"] = $data["userid"]!=null?$data["userid"]:$data["CGISESSID"];
			$rec["issue"] = $data["issue"];
			
			$rec["method_id"] = $this->Method_model->getMethodidWithPlatform3($data["chan_id"], $data["lotteryId"], $value["methodid"]);
			$rec["code"] = $value["codes"];
			$rec["proj_amt"] = $value["nums"];
			
			if(strcmp($data["traceIstrace"], "1") == 0)
			{
				$unit = $value["mode"]=="2"?0.2:2;
				
				$trace_count = explode(",", $data["traceIssues"]);
				$trace_count = count($trace_count);
				$rec["straight"] = $trace_count;
				$rec["proj_times"] = $trace_times;
				$rec["money"] = $value["nums"] * $trace_times * $unit * $trace_count;
			}
			else
			{
				$rec["straight"] = 0;
				$rec["proj_times"] = $value["times"];
				$rec["money"] = $value["money"]==null?$data["money"]:$value["money"];
			}
			$rs = $this->Project_rec_model->addProject($rec);
		}
	}
	
	private function _logProject($data, $trace_times = 0)
	{
		$this->load->model('Project_rec_model');
		$this->load->model('Method_model');

		foreach($data["projects"] as $key => $value) 
		{
			$rec = array();
			$rec["app_id"] = $data["app_id"];
			$rec["token"] = $data["CGISESSID"];
			$rec["issue"] = $data["issue"];
			
			$rec["method_id"] = $this->Method_model->getMethodidWithPlatform3($data["chan_id"], $data["lottery_id"], $value["methodid"]);
			$rec["code"] = $value["codes"];
			$rec["proj_amt"] = $value["nums"];
			
			if(strcmp($data["trace_istrace"], "1") == 0)
			{
				$unit = $value["mode"]=="2"?0.2:2;
				$trace_count = count($data["trace_issues"]);
				$rec["straight"] = $trace_count;
				$rec["proj_times"] = $trace_times;
				$rec["money"] = $value["nums"] * $trace_times * $unit * $trace_count;
			}
			else
			{
				$rec["straight"] = 0;
				$rec["proj_times"] = $value["times"];
				$rec["money"] = $value["money"]==null?$data["money"]:$value["money"];
			}
			$rs = $this->Project_rec_model->addProject($rec);
		}
	}
	
	public function cancelGame()
	{
		$id = is_null($_POST["id"])?"":$_POST["id"];
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf (GAME_CANCELGAME_LOW, $id), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf (GAME_CANCELGAME_HIGH, $id), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(sprintf (GAME_CANCELGAME_4, $id), $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(str_replace('$id', $_POST['id'], BM_GAME_CANCELGAME), $_POST);
		}
	}
	
	public function cancelTask()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(GAME_CANCELTASK_LOW, $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(GAME_CANCELTASK_HIGH, $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"];
			
			$this->my_post(sprintf (GAME_CANCELTASK_4, $id), $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $id = $_POST['id'];
                        unset($_POST['id']);
                        $this->wrapBM(str_replace('$id', $id, BM_GAME_CANCELTASK), $_POST);
		}
	}
        
        public function lotteryInfo()
	{
            $obj = json_decode($this->wrapBM(BM_GAME_LOTTERY_INFO, $_POST, true), true);
            if($obj['isSuccess'] == 1)
            {
                $ary = array();
                foreach ($obj['data'] as $value1)
                    foreach ($value1['children'] as $value2)
                    {
                        $item = array(
                                "lottery_id" => $value2['id'],
                                "type" => $value2['type'],
                                "name" => $value2['name'],
                                "daily_issue_count" => $value2['daily_issue_count'],
                                "trace_issue_count" => $value2['trace_issue_count']
                            );
                        array_push($ary, $item);
                    }
                $obj['data'] = $ary;
                $this->echoJsonPost($obj);
            }
            else echo $this->encrypt($obj);
	}
        
        public function lotteryData()
	{
            $lottery_id = $_POST['lottery_id'];
            if(!$lottery_id) return $this->_lotteryDataAll();
            $obj = json_decode($this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_INITDATA), $_POST, true), true);
            if($obj['isSuccess'] == 1)
            {
                $ary = array();
                foreach ($obj['data']['game_ways'] as $value1)
                {
                    $name_cn = $value1['name_cn'];
                    $name_en = $value1['name_en'];
                    foreach ($value1['children'] as $value2)
                        foreach ($value2['children'] as $value3)
                        {
                            $item = array(
                                    "series_way_id" => $value3['series_way_id'],
                                    "name_cn" => $value1['name_cn'].$value3['name_cn'],
                                    "name_en" => $value1['name_en'].'.'.$value2['name_en'].'.'.$value3['name_en'],
                                    "bet_note" => $value3['bet_note'],
                                    "bonus_note" => $value3['bonus_note'],
                                    "prize" => $value3['prize'],
                                    "max_multiple" => $value3['max_multiple']
                                );
                            array_push($ary, $item);
                        }
                }
                $obj['data'] = $ary;
                $this->echoJsonPost($obj);
            }
            else echo $this->encrypt($obj);
	}
        
        private function _lotteryDataAll()
	{
            $obj = json_decode($this->wrapBM(BM_GAME_LOTTERY_INFO, $_POST, true), true);
            if($obj['isSuccess'] == 1)
            {
                $ary = array();
                array_push($ary, $this->_lotteryDataProcessing("1"));
                array_push($ary, $this->_lotteryDataProcessing("2"));
                //foreach ($obj['data'] as $value1)
                //    foreach ($value1['children'] as $value2)
                //        array_push($ary, $this->_lotteryDataProcessing($value2['id']));
                $obj['data'] = $ary;
            }
            $this->echoJsonPost($obj);
	}
        
        private function _lotteryDataProcessing($lottery_id)
	{
            $obj = json_decode($this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_INITDATA), $_POST, true), true);
            if($obj['isSuccess'] == 1)
            {
                $ary = array();
                foreach ($obj['data']['game_ways'] as $value1)
                {
                    $name_cn = $value1['name_cn'];
                    $name_en = $value1['name_en'];
                    foreach ($value1['children'] as $value2)
                        foreach ($value2['children'] as $value3)
                        {
                            $item = array(
                                    "series_way_id" => $value3['series_way_id'],
                                    "name_cn" => $value1['name_cn'].$value3['name_cn'],
                                    "name_en" => $value1['name_en'].'.'.$value2['name_en'].'.'.$value3['name_en'],
                                    "bet_note" => $value3['bet_note'],
                                    "bonus_note" => $value3['bonus_note'],
                                    "prize" => $value3['prize'],
                                    "max_multiple" => $value3['max_multiple']
                                );
                            array_push($ary, $item);
                        }
                }
                return $ary;
            }
            else return $obj;
	}
        
        private function _buyBomao()
	{
            $lottery_id = $_POST['lottery_id'];
            $_POST['gameId'] = $lottery_id;
            $_POST['traceStopValue'] = $_POST['traceWinStop'] == 0 ? -1 : 1;
            $jsId = 1;
            foreach ($_POST['balls'] as $value)
            {
                $value['jsId'] = $jsId++;
                $value['onePrice'] = $value['moneyunit'] == 1 ? 2 : 0.2;
            }
            unset($_POST['lottery_id']);
            $this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_PLAY), $_POST);
	}
        
        public function prizeSet()
        {
            $lottery_id = $_POST['lottery_id'];
            unset($_POST['lottery_id']);
            $this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_GAME_PRIZE_SET), $_POST);
        }
}
