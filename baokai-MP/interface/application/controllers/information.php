<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Information extends MY_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function historyInfo()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$ary = array();
			$low = array();
			$high = array();
			
			if($_POST["chan_id"] == null || $_POST["chan_id"] == "1")
			{
				$postLow = $_POST;
				$postLow["chan_id"] = "1";
				$lowAll = $this->getArrayResult(INFORMATION_HISTORY_INFO_LOW, $postLow);
				$getAry = array("1","3");					//3D、CB
				
				$lottery_id = "";
				$countRepeat = 0;
				foreach($lowAll as $key => $value) 
				{
					if(in_array($value["lotteryid"], $getAry))
					{
						if($_POST["lottery_id"] != null)
						{
							if(strcmp($value["lotteryid"], $_POST["lottery_id"]) == 0)
							{
								array_push($low, $value);
							}
						}
						else
						{
							if($lottery_id != $value["lotteryid"])
							{
								$lottery_id = $value["lotteryid"];
								$countRepeat = 0;
							}
							if($countRepeat < 3)
							{
								array_push($low, $value);
								$countRepeat++;
							}
						}
					}
				}
			}
			
			if($_POST["chan_id"] == null || $_POST["chan_id"] == "4")
			{
				$postHigh = $_POST;
				$postHigh["chan_id"] = "4";
				$highAll = $this->getArrayResult(INFORMATION_HISTORY_INFO_HIGH, $postHigh);
				$getAry = array("1", "5", "11", "13", "14");			//重庆时时彩、山东11选5、宝开时时彩、宝开11选5、宝开1分彩
				$lottery_id = "";
				$countRepeat = 0;
				foreach($highAll as $key => $value) 
				{
					if(in_array($value["lotteryid"], $getAry))
					{
						if($_POST["lottery_id"] != null)
						{
							if(strcmp($value["lotteryid"], $_POST["lottery_id"]) == 0)
							{
								array_push($high, $value);
							}
						}
						else
						{
							if($lottery_id != $value["lotteryid"])
							{
								$lottery_id = $value["lotteryid"];
								$countRepeat = 0;
							}
							if($countRepeat < 3)
							{
								array_push($high, $value);
								$countRepeat++;
							}
						}
					}
				}
			}
			$this->echoJsonPost(array_merge($high, $low));
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$enableLotterys = array("99101", "99102", "99103", "99104", "99106", "99301", "99305", "99111", "99114", "99108", "99401", "99109", "99501", "99112","99105","99107","99302","99303","99307","99502","99601","99602","99701");
					
			//$this->my_post(INFORMATION_HISTORY_INFO_4, $_POST);
			$this->load->library('BetMgr');
			if(isset($_POST["lotteryId"]))
			{
				$_POST["num"] = 20;
				
				$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
				$_POST["lotteryId"] = $lottery["4_lot_id"];
			}
			else
			{
				$_POST["num"] = 3;
			}
			
			$this->load->library('BetMgr');
			$ary = $this->getArrayResult(INFORMATION_HISTORY_INFO_4, $_POST);
			
			$rtn = array();
			
			$topLotteryId = "99501";
			$topLotteryAry = array();
			
			foreach($ary["list"] as $key => $value)
			{
				if(in_array($value["lotteryid"], $enableLotterys))
				{
					$lottery = BetMgr::getLotteryWith4($value["lotteryid"]);
					$ary["list"][$key]["channelid"] = $lottery["3_chan_id"];
					$ary["list"][$key]["lotteryid"] = $lottery["3_lot_id"];
					
					if($value["lotteryid"] == $topLotteryId)
					{
						array_push($topLotteryAry, $ary["list"][$key]);
					}else{
						array_push($rtn, $ary["list"][$key]);
					}
				}
			}
			if($topLotteryAry != null)
			{
				for($i = count($topLotteryAry) - 1;$i >= 0;$i--)
				{
					array_unshift($rtn, $topLotteryAry[$i]);
				}
			}
			
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $lottery_id = $_POST['lottery_id'];
                        $count = $_POST['count'] ? $_POST['count'] : 20;
                        unset($_POST['lottery_id']);
                        unset($_POST['count']);
                        if($lottery_id)
                            $this->wrapBM(str_replace('$lottery_id', $lottery_id, str_replace('$count', $count, BM_INFORMATION_HISTORY_INFO)), $_POST);
                        else
                            $this->wrapBM(str_replace('$count', $count, BM_INFORMATION_HISTORY_INFO_ALL), $_POST);
		}
	}
	
	public function gameList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$ary = array();
			if($_POST["chan_id"] != null)
			{
				if($_POST["chan_id"] == "1")
				{
					/*
					//$this->getArrayResult($url, $post_array);
					$json = $this->getArrayResult( INFORMATION_GAME_LIST_LOW, $_POST);
					var_dump($json);
					//$json = $this->removeKey($json, "lotteryid");
					$this->echoJsonPost($json);
					*/
					$ary = $this->getArrayResult(INFORMATION_GAME_LIST_LOW, $_POST);
				}
				else if($_POST["chan_id"] == "4")
				{
					$ary = $this->getArrayResult(INFORMATION_GAME_LIST_HIGH, $_POST);
				}
			}
			else
			{
				$_POST["chan_id"] = "1";
				$low = $this->getArrayResult(INFORMATION_GAME_LIST_LOW, $_POST);
				foreach($low as $key => $value) 
				{
					if(is_array($value))
					{
						$low[$key]["channelid"] = "1";
					}
				}
				$_POST["chan_id"] = "4";
				$high = $this->getArrayResult(INFORMATION_GAME_LIST_HIGH, $_POST);
				foreach($high as $key => $value) 
				{
					if(is_array($value))
					{
						$high[$key]["channelid"] = "4";
					}
				}
				$ary = array_merge($low, $high);
			}
			foreach($ary as $key => $value) 
			{
				if(isset($ary[$key]["bonus"]) && !is_numeric($ary[$key]["bonus"]))
				{
					$this->echoJsonPost(ErrHandler::getSysInfo(-97));
					die();
				}
			}
			$this->echoJsonPost($ary);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$enableLotterys = array("99101", "99102", "99103", "99104", "99106", "99301", "99305", "99111","99114",  "99108", "99401", "99109", "99501", "99112", "99113","99105","99107","99302","99303","99307","99502","99601","99602","99701");
			
			//$this->my_post(INFORMATION_GAME_LIST_4, $_POST);
			 
			$this->load->library('BetMgr');
			$lottery4 = BetMgr::getLotteryWith3($_POST["chan_id"],$_POST["lotteryId"]);
			$_POST["lotteryId"]=$lottery4["4_lot_id"];
			$ary = $this->getArrayResult(INFORMATION_GAME_LIST_4, $_POST);
			
			$rtn = array();
			
			foreach($ary["list"] as $key => $value)
			{
				if(in_array($value["lotteryid"], $enableLotterys))
				{
					$lottery = BetMgr::getLotteryWith4($value["lotteryid"]);
					$ary["list"][$key]["channelid"] = $lottery["3_chan_id"];
					$ary["list"][$key]["lotteryid"] = $lottery["3_lot_id"];
					
					array_push($rtn, $ary["list"][$key]);
				}
			}
			
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST['pagesize'] = BM_PAGE_SIZE;
                        $this->wrapBM(BM_INFORMATION_GAME_LIST, $_POST);
		}
	}
	
	public function gameDetail()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$id = is_null($_POST["id"])?"":$_POST["id"];
			$ary = array();
			if($_POST["chan_id"] == "1")
			{
				$ary = $this->getArrayResult(sprintf (INFORMATION_GAME_DETAIL_LOW, $id), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$ary = $this->getArrayResult(sprintf (INFORMATION_GAME_DETAIL_HIGH, $id), $_POST);
			}
			if(isset($ary["bonus"]) && !is_numeric($ary["bonus"]))
			{
				$this->echoJsonPost(ErrHandler::getSysInfo(-97));
				die();
			}
			$this->echoJsonPost($ary);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			// $this->my_post(INFORMATION_GAME_DETAIL_4, $_POST);
			$this->load->library('BetMgr');
			$rtn = $this->getArrayResult(INFORMATION_GAME_DETAIL_4, $_POST);
			
			// replace methodid
			foreach($rtn["list"] as $key => $value)
			{
				$method = BetMgr::getMethodWith4($rtn["lotteryId"], $value["methodid"]);
				$rtn["list"][$key]["methodid"] = $method["3_method_id"];
				$rtn["list"][$key]["methodname"] = $method["method_name"];
				
				//var_dump($value);
			}
			
			// replace lotteryid
			$lottery = BetMgr::getLotteryWith4($rtn["lotteryId"]);
			$rtn["lotteryId"] = $lottery["3_lot_id"];
			
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $id = $_POST['id'] ? $_POST['id'] : 0;
                        unset($_POST['id']);
			$this->wrapBM(str_replace('$id', $id, BM_INFORMATION_GAME_DETAIL), $_POST);
		}
	}
	public function winList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$enableLotterys = array("99101", "99102", "99103", "99104", "99106", "99301", "99305", "99111","99114", "99108", "99401", "99109", "99501", "99112", "99113","99105","99107","99302","99303","99307","99502","99601","99602","99701");
			
			$_POST=$enableLotterys;
			//$this->my_post(INFORMATION_GAME_LIST_4, $_POST);
			// $this->load->library('BetMgr');
			$ary = $this->getArrayResult(INFORMATION_WIN_LIST_4, $_POST);
			
			$rtn = array();
			foreach($ary["list"] as $key => $value)
			{    
				array_push($rtn, $ary["list"][$key]); 
			}
			
			$this->echoJsonPost($rtn);
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	public function winAmt()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"]; 
 
			$this->my_post(INFORMATION_WIN_AMT_4, $_POST);
			 
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	public function lastNumber()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"]; 
 
			$this->my_post(INFORMATION_LAST_NUMBER_4, $_POST);
			 
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	public function methodInit()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$method = BetMgr::getMethodWith3($_POST["chan_id"], $_POST["lotteryId"], $_POST["methodid"]);
			$_POST["lotteryId"] = $method["4_lot_id"]; 
			$_POST["betTypeCode"] = $method["4_method_id"];

			$this->my_post(INFORMATION_METHOD_INIT_4, $_POST);
			 
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	public function chart()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["channelId"], $_POST["lotteryId"]); 
			$_POST["lotteryId"] = $lottery["4_lot_id"];  
			$this->my_post(INFORMATION_CHART_4, $_POST);
			 
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	
	public function userProfit()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			//not supported
		}
		else if($this->mPlatform == PLATFORM_4)
		{ 
			$this->my_post(INFORMATION_USER_PROFIT_4, $_POST);
		}
         else if($this->mPlatform == PLATFORM_BM)
		{
                    //not supported
		}
	}
	

	public function taskList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$ary = array();
			if($_POST["chan_id"] != null)
			{
				if($_POST["chan_id"] == "1")
				{
					$ary = $this->getArrayResult(INFORMATION_TASK_LIST_LOW, $_POST);
				}
				else if($_POST["chan_id"] == "4")
				{
					$ary = $this->getArrayResult(INFORMATION_TASK_LIST_HIGH, $_POST);
				}
			}
			else
			{
				$_POST["chan_id"] = "1";
				$low = $this->getArrayResult(INFORMATION_TASK_LIST_LOW, $_POST);
				$_POST["chan_id"] = "4";
				$high = $this->getArrayResult(INFORMATION_TASK_LIST_HIGH, $_POST);
				
				$ary = array_merge($low, $high);
			}
			foreach($ary as $key => $value) 
			{
				//$ary[$key]["bonus"] = "12.a";
				if(isset($ary[$key]["bonus"]) && !is_numeric($ary[$key]["bonus"]))
				{
					$this->echoJsonPost(ErrHandler::getSysInfo(-97));
					die();
				}
			}
			
			$this->echoJsonPost($ary);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$enableLotterys = array("99101", "99102", "99103", "99104", "99106", "99301", "99305", "99111","99114", "99108", "99401", "99109","99302","99303","99307", "99501","99502","99601","99602","99701");
			
			$this->load->library('BetMgr');
			$lottery4 = BetMgr::getLotteryWith3($_POST["chan_id"],$_POST["lotteryId"]);
			$_POST["lotteryId"]=$lottery4["4_lot_id"];
			$ary = $this->getArrayResult(INFORMATION_TASK_LIST_4, $_POST);
			
			$rtn = array();
			
			foreach($ary["list"] as $key => $value)
			{
				if(in_array($value["lotteryid"], $enableLotterys))
				{
					$lottery = BetMgr::getLotteryWith4($value["lotteryid"]);
					$ary["list"][$key]["channelid"] = $lottery["3_chan_id"];
					$ary["list"][$key]["lotteryid"] = $lottery["3_lot_id"];
					
					array_push($rtn, $ary["list"][$key]);
				}
			}
			
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST['pagesize'] = BM_PAGE_SIZE;
                        $this->wrapBM(BM_INFORMATION_TASK_LIST, $_POST);
		}
	}
	
	public function taskDetail()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$id = is_null($_POST["id"])?"":$_POST["id"];
			$ary = array();
			if($_POST["chan_id"] == "1")
			{
				$ary = $this->getArrayResult(sprintf (INFORMATION_TASK_DETAIL_LOW, $id), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$ary = $this->getArrayResult(sprintf (INFORMATION_TASK_DETAIL_HIGH, $id), $_POST);
			}
			$this->echoJsonPost($ary);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(INFORMATION_TASK_DETAIL_4, $_POST);
			$this->load->library('BetMgr');
			$rtn = $this->getArrayResult(INFORMATION_TASK_DETAIL_4, $_POST);
			
			// replace methodid
			foreach($rtn["projectList"] as $key => $value)
			{
				$method = BetMgr::getMethodWith4($rtn["lotteryId"], $value["methodid"]);
				$rtn["projectList"][$key]["methodid"] = $method["3_method_id"];
				$rtn["projectList"][$key]["methodname"] = $method["method_name"];
			}
			
			foreach($rtn["tasks"] as $key => $value)
			{
				foreach($value["list"] as $key2 => $value2)
				{
					//var_dump($rtn["tasks"][$key]["list"]);
					// methodid  codedetails  nums  multiple  modes
					unset($rtn["tasks"][$key]["list"][$key2]["methodid"]);
					unset($rtn["tasks"][$key]["list"][$key2]["codedetails"]);
					unset($rtn["tasks"][$key]["list"][$key2]["nums"]);
					unset($rtn["tasks"][$key]["list"][$key2]["multiple"]);
					unset($rtn["tasks"][$key]["list"][$key2]["modes"]);
				}
			}
			
			
			// replace lotteryid
			$lottery = BetMgr::getLotteryWith4($rtn["lotteryId"]);
			$rtn["lotteryId"] = $lottery["3_lot_id"];
			
			$this->echoJsonPost($rtn);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $id = $_POST['id'] ? $_POST['id'] : 0;
                        unset($_POST['id']);
                        $_POST['pagesize'] = 120;
                        $this->wrapBM(str_replace('$id', $id, BM_INFORMATION_TASK_DETAIL), $_POST);
		}
	}
	
	public function noticeList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "0")
			{
				$this->my_post(INFORMATION_NOTICE_LIST_BANK, $_POST);
			}
			else if($_POST["chan_id"] == "1")
			{
				$this->my_post(INFORMATION_NOTICE_LIST_LOW, $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(INFORMATION_NOTICE_LIST_HIGH, $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$rtn = $this->getArrayResult(INFORMATION_NOTICE_LIST_4, $_POST);
			if(DEVELOPMENT)
			{
				foreach($rtn["list"] as $key => $value)
				{
					$rtn["list"][$key]["subject"] = $this->filterWords($rtn["list"][$key]["subject"]);
					$rtn["list"][$key]["content"] = $this->filterWords($rtn["list"][$key]["content"]);
				}
			}
			$this->echoJsonPost($rtn["list"]);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST['pagesize'] = BM_PAGE_SIZE;
                        $this->wrapBM(BM_INFORMATION_NOTICE_LIST, $_POST);
		}
	}
	
	public function noticeDetail()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$nid = is_null($_POST["nid"])?"":$_POST["nid"];
			if($_POST["chan_id"] == "0")
			{
				$this->my_post(sprintf(INFORMATION_NOTICE_DETAIL_BANK, $nid), $_POST);
			}
			else if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf(INFORMATION_NOTICE_DETAIL_LOW, $nid), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf(INFORMATION_NOTICE_DETAIL_HIGH, $nid), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(INFORMATION_NOTICE_DETAIL_4, $_POST);
			
			
			$rtn = $this->getArrayResult(INFORMATION_NOTICE_DETAIL_4, $_POST);
			if(DEVELOPMENT)
			{
				$rtn["subject"] = $this->filterWords($rtn["subject"]);
				$rtn["content"] = $this->filterWords($rtn["content"]);
			}
			$this->echoJsonPost($rtn);
		}
                if($this->mPlatform == PLATFORM_BM)
		{
                        $id = $_POST['id'] ? $_POST['id'] : 0;
                        unset($_POST['id']);
                        $this->wrapBM(str_replace('$id', $id, BM_INFORMATION_NOTICE_DETAIL), $_POST);
		}
	}
	
	public function allIssue()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				if($_POST["lottery_id"] == "1")
				{
					$this->my_post(INFORMATION_ALLISSUE_3D, $_POST);
				}
				else if($_POST["lottery_id"] == "3")
				{
					$this->my_post(INFORMATION_ALLISSUE_SSQ, $_POST);
				}
			}
			else if($_POST["chan_id"] == "4")
			{
				if($_POST["lottery_id"] == "1")
				{
					$this->my_post(INFORMATION_ALLISSUE_CQSSC, $_POST);
				}
				else if($_POST["lottery_id"] == "5")
				{
					$this->my_post(INFORMATION_ALLISSUE_SD11X5, $_POST);
				}
				else if($_POST["lottery_id"] == "11")
				{
					$this->my_post(INFORMATION_ALLISSUE_LLSSC, $_POST);
				}
				else if($_POST["lottery_id"] == "13")
				{
					$this->my_post(INFORMATION_ALLISSUE_LL11X5, $_POST);
				}
				else if($_POST["lottery_id"] == "14")
				{
					$this->my_post(INFORMATION_ALLISSUE_JLFFC, $_POST);
				}
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$lottery = BetMgr::getLotteryWith3($_POST["chan_id"], $_POST["lotteryId"]);
			$_POST["lotteryId"] = $lottery["4_lot_id"];
			
			$num = 40;
			
			switch($_POST["lotteryId"])
			{
				case "99101":	// 重庆时时彩
					$num = 240;
					break;
				case "99106":	// 宝开时时彩
					$num = 552;
					break;
				case "99301":	// 山东11选5
					$num = 240;
					break;
				case "99305":	// 宝开11选5
					$num = 552;
					break;
				case "99111":	// 宝开1分彩
					$num = 1380;
					break;
				case "99114":	// 腾讯分分彩
					$num = 1380;
					break;
				case "99501":	// 江苏快三
					$num = 1380;
					break;
				case "99108":	// 3D
				case "99401":	// 双色球
					$num = 40;
					break;
				default:
					$num = 40;
			}
			
			$_POST["num"] = $num;
			$rtn = $this->getArrayResult(INFORMATION_ALLISSUE_4, $_POST);
			$this->echoJsonPost($rtn["list"]);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                    $lottery_id = $_POST['lottery_id'];
                    unset($_POST['lottery_id']);
                    $this->wrapBM(str_replace('$lottery_id', $lottery_id, BM_INFORMATION_ALLISSUE), $_POST);
		}
	}
	/*
	public function getAdInfo()
	{
		$this->my_post(INFORMATION_GET_ADINFO, $_POST);
	}
	
	public function getVersion()
	{
		$this->my_post(INFORMATION_GET_VERSION, $_POST);
	}
	*/
	
	public function getBalance()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$json = $this->getArrayResult(INFORMATION_GET_BALANCE, $_POST);
			//$json[0] = "12Ad";
			foreach($json as $key => $value)
			{
				if(!is_numeric($value))
				{
					$this->echoJsonPost(ErrHandler::getSysInfo(-97));
					die();
				}
			}
			$this->echoJsonPost($json);
			
			//$this->my_post(INFORMATION_GET_BALANCE, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(INFORMATION_GET_BALANCE_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_INFORMATION_GET_BALANCE, $_POST);
		}
	}
	
	public function getTime()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(INFORMATION_GET_TIME, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(INFORMATION_GET_TIME_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_INFORMATION_GET_TIME);
		}
	}
	
	public function userMsgList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(INFORMATION_USER_MSG_LIST, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$rtn = $this->getArrayResult(INFORMATION_USER_MSG_LIST_4, $_POST);
			$this->echoStrPost(str_replace('\/','/',json_encode($rtn["list"])));
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST['pagesize'] = BM_PAGE_SIZE;
                        $this->wrapBM(BM_INFORMATION_USER_MSG_LIST, $_POST);
		}
	}
	
	public function userMsgDetail()
	{
		$mid = is_null($_POST["mid"])?"":$_POST["mid"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(INFORMATION_USER_MSG_DETAIL, $mid), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$rtn = $this->getArrayResult(INFORMATION_USER_MSG_DETAIL_4, $_POST);
			$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $id = $_POST['id'] ? $_POST['id'] : 0;
                        unset($_POST['id']);
                        $this->wrapBM(str_replace('$id', $id, BM_INFORMATION_USER_MSG_DETAIL), $_POST);
		}
	}
	
	public function userMsgDel()
	{
		$mid = is_null($_POST["mid"])?"":$_POST["mid"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(INFORMATION_USER_MSG_DEL, $mid), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(INFORMATION_USER_MSG_DEL_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$this->my_post(sprintf(INFORMATION_USER_MSG_DEL, $mid), $_POST);
		}
	}
	
	public function openLinkList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(INFORMATION_OPEN_LINK_LIST, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{	
			$host = "";
			$this->load->model('App_domain_model');
			$rs = $this->App_domain_model->getAppDomain($this->mApp_info->app_id);
			$host = $rs["domain"];
			
			$rtn = $this->getArrayResult(INFORMATION_OPEN_LINK_LIST_4, $_POST);
			foreach($rtn["list"] as $key => $value)
			{
				$rtn["list"][$key]["urlstring"] = $host . "/register/?" . $rtn["list"][$key]["urlstring"];
			}
			$this->echoStrPost(str_replace('\/','/',json_encode($rtn["list"])));
		}
	}
	
	public function openLinkDetail()
	{
		$id = is_null($_POST["id"])?"":$_POST["id"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(INFORMATION_OPEN_LINK_DETAIL, $id), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('BetMgr');
			$rtn = $this->getArrayResult(INFORMATION_OPEN_LINK_DETAIL_4, $_POST);
			
			
			$lotteryids = array();
			$cnnames = array();
			//lotteryid  cnname
			foreach($rtn["key"] as $key => $value)
			{
				$lotteryids[$key] = $rtn["key"][$key]["lotteryid"];
				$cnnames[$key] = $rtn["key"][$key]["cnname"];
			}
			array_multisort($lotteryids, SORT_ASC, $cnnames, SORT_DESC, $rtn["key"]);
			
			foreach($rtn["key"] as $key => $value)
			{
				//echo $rtn["key"][$key]["lotteryid"];
				
				$lottery = BetMgr::getLotteryWith4($rtn["key"][$key]["lotteryid"]);
				$rtn["key"][$key]["channelid"] = $lottery["3_chan_id"];
				$rtn["key"][$key]["lotteryid"] = $lottery["3_lot_id"];
				$rtn["key"][$key]["lot_name"] = $lottery["lot_name"];
			}
			$this->echoJsonPost($rtn);
		}
	}
	
	public function openLinkUsers()
	{
		$this->my_post(INFORMATION_OPEN_LINK_USERS_4, $_POST);
	}
	
	public function delOpenLink()
	{
		$this->my_post(INFORMATION_DEL_OPEN_LINK_4, $_POST);
	}
	
	public function modifyRemark()
	{
		$this->my_post(INFORMATION_MODIFY_REMARK_4, $_POST);
	}
	
	public function prizeDetail()
	{
		//$this->my_post(INFORMATION_PRIZE_DETAIL_4, $_POST);
		$this->load->library('BetMgr');
		$rtn = $this->getArrayResult(INFORMATION_PRIZE_DETAIL_4, $_POST);
		$lotteryids = array();
		$cnnames = array();
		//lotteryid  cnname
		foreach($rtn["key"] as $key => $value)
		{
			$lotteryids[$key] = $rtn["key"][$key]["lotteryid"];
			$cnnames[$key] = $rtn["key"][$key]["cnname"];
		}
		array_multisort($lotteryids, SORT_ASC, $cnnames, SORT_DESC, $rtn["key"]);
		
		foreach($rtn["key"] as $key => $value)
		{
			//echo $rtn["key"][$key]["lotteryid"];
			
			$lottery = BetMgr::getLotteryWith4($rtn["key"][$key]["lotteryid"]);
			$rtn["key"][$key]["channelid"] = $lottery["3_chan_id"];
			$rtn["key"][$key]["lotteryid"] = $lottery["3_lot_id"];
			$rtn["key"][$key]["lot_name"] = $lottery["lot_name"];
		}
		$this->echoJsonPost($rtn);
	}
	
	public function initCreateUrl()
	{
		$rtn = $this->getArrayResult(INFORMATION_INIT_CREATE_URL_4, $_POST);
		//轉換lotteryId，增加cnname及chanid
		$this->load->library('BetMgr');
		$lotteryids = array();
		$cnnames = array();
		foreach($rtn["userAwardListStruc"] as $key => $value)
		{
			$lotteryids[$key] = $rtn["userAwardListStruc"][$key]["lotteryId"];
			$cnnames[$key] = $rtn["userAwardListStruc"][$key]["awardName"];
		}
		//array_multisort($lotteryids, SORT_ASC, $rtn["userAwardListStruc"]);
		array_multisort($lotteryids, SORT_ASC, $cnnames, SORT_DESC, $rtn["userAwardListStruc"]);
		
		foreach($rtn["userAwardListStruc"] as $key => $value)
		{
			$lottery = BetMgr::getLotteryWith4($rtn["userAwardListStruc"][$key]["lotteryId"]);
			$rtn["userAwardListStruc"][$key]["channelId"] = $lottery["3_chan_id"];
			$rtn["userAwardListStruc"][$key]["lotteryId"] = $lottery["3_lot_id"];
			$rtn["userAwardListStruc"][$key]["lot_name"] = $lottery["lot_name"];
		}

		$fastsetupreturnmax = 100;
		foreach($rtn["userAwardListStruc"] as $key => $value)
		{ 
			//轉換返點值
			$rtn["userAwardListStruc"][$key]["directRet"] = $rtn["userAwardListStruc"][$key]["directRet"] == 0? 0:($rtn["userAwardListStruc"][$key]["directRet"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["threeoneRet"] = $rtn["userAwardListStruc"][$key]["threeoneRet"] == 0? 0:($rtn["userAwardListStruc"][$key]["threeoneRet"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcYear"] = $rtn["userAwardListStruc"][$key]["lhcYear"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcYear"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcColor"] = $rtn["userAwardListStruc"][$key]["lhcColor"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcColor"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcFlatcode"] = $rtn["userAwardListStruc"][$key]["lhcFlatcode"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcFlatcode"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcHalfwave"] = $rtn["userAwardListStruc"][$key]["lhcHalfwave"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcHalfwave"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcOneyear"] = $rtn["userAwardListStruc"][$key]["lhcOneyear"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcOneyear"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcNotin"] = $rtn["userAwardListStruc"][$key]["lhcNotin"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcNotin"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuein23"] = $rtn["userAwardListStruc"][$key]["lhcContinuein23"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuein23"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuein4"] = $rtn["userAwardListStruc"][$key]["lhcContinuein4"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuein4"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuein5"] = $rtn["userAwardListStruc"][$key]["lhcContinuein5"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuein5"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuenotin23"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin23"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuenotin23"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuenotin4"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin4"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuenotin4"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuenotin5"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin5"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuenotin5"]-50) / 100;
			$rtn["userAwardListStruc"][$key]["lhcContinuecode"] = $rtn["userAwardListStruc"][$key]["lhcContinuecode"] == 0? 0:($rtn["userAwardListStruc"][$key]["lhcContinuecode"]-50) / 100;
 
			//計算快捷設置返點最大值
			$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["directRet"]);
			if ($rtn["userAwardListStruc"][$key]["lotteryId"] != 16 && $rtn["userAwardListStruc"][$key]["lotteryId"] != 18)
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["threeoneRet"]);
			if ($rtn["userAwardListStruc"][$key]["lotteryId"] == 22){
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcYear"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcColor"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcFlatcode"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcHalfwave"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcOneyear"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcNotin"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuein23"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuein4"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuein5"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuenotin23"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuenotin4"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuenotin5"]);
				$fastsetupreturnmax = min($fastsetupreturnmax, $rtn["userAwardListStruc"][$key]["lhcContinuecode"]);
			}
		}
		
		//刪除無用參數，建立新陣列回傳
		$icu["fastsetupreturnmax"] = $fastsetupreturnmax;
		foreach($rtn["userAwardListStruc"] as $key => $value)
		{
			$icu["userAwardList"][$key]["lotterySeriesCode"] = $rtn["userAwardListStruc"][$key]["lotterySeriesCode"];
			$icu["userAwardList"][$key]["lotterySeriesName"] = $rtn["userAwardListStruc"][$key]["lotterySeriesName"];
			$icu["userAwardList"][$key]["awardGroupId"] = $rtn["userAwardListStruc"][$key]["awardGroupId"];
			$icu["userAwardList"][$key]["awardName"] = $rtn["userAwardListStruc"][$key]["awardName"];
			$icu["userAwardList"][$key]["directRet"] = $rtn["userAwardListStruc"][$key]["directRet"];
			$icu["userAwardList"][$key]["threeoneRet"] = $rtn["userAwardListStruc"][$key]["threeoneRet"];
			$icu["userAwardList"][$key]["directLimitRet"] = $rtn["userAwardListStruc"][$key]["directLimitRet"];
			$icu["userAwardList"][$key]["threeLimitRet"] = $rtn["userAwardListStruc"][$key]["threeLimitRet"];
			$icu["userAwardList"][$key]["channelId"] = $rtn["userAwardListStruc"][$key]["channelId"];
			$icu["userAwardList"][$key]["lotteryId"] = $rtn["userAwardListStruc"][$key]["lotteryId"];
			$icu["userAwardList"][$key]["lotteryName"] = $rtn["userAwardListStruc"][$key]["lot_name"];

			$icu["userAwardList"][$key]["lhcYear"] = $rtn["userAwardListStruc"][$key]["lhcYear"];
			$icu["userAwardList"][$key]["lhcColor"] = $rtn["userAwardListStruc"][$key]["lhcColor"];
			$icu["userAwardList"][$key]["lhcFlatcode"] = $rtn["userAwardListStruc"][$key]["lhcFlatcode"];
			$icu["userAwardList"][$key]["lhcHalfwave"] = $rtn["userAwardListStruc"][$key]["lhcHalfwave"];
			$icu["userAwardList"][$key]["lhcOneyear"] = $rtn["userAwardListStruc"][$key]["lhcOneyear"];
			$icu["userAwardList"][$key]["lhcNotin"] = $rtn["userAwardListStruc"][$key]["lhcNotin"];
			$icu["userAwardList"][$key]["lhcContinuein23"] = $rtn["userAwardListStruc"][$key]["lhcContinuein23"];
			$icu["userAwardList"][$key]["lhcContinuein4"] = $rtn["userAwardListStruc"][$key]["lhcContinuein4"];
			$icu["userAwardList"][$key]["lhcContinuein5"] = $rtn["userAwardListStruc"][$key]["lhcContinuein5"];
			$icu["userAwardList"][$key]["lhcContinuenotin23"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin23"];
			$icu["userAwardList"][$key]["lhcContinuenotin4"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin4"];
			$icu["userAwardList"][$key]["lhcContinuenotin5"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin5"];
			$icu["userAwardList"][$key]["lhcContinuecode"] = $rtn["userAwardListStruc"][$key]["lhcContinuecode"];
			$icu["userAwardList"][$key]["lhcYearLimit"] = $rtn["userAwardListStruc"][$key]["lhcYearLimit"];
			$icu["userAwardList"][$key]["lhcColorLimit"] = $rtn["userAwardListStruc"][$key]["lhcColorLimit"];
			$icu["userAwardList"][$key]["lhcFlatcodeLimit"] = $rtn["userAwardListStruc"][$key]["lhcFlatcodeLimit"];
			$icu["userAwardList"][$key]["lhcHalfwaveLimit"] = $rtn["userAwardListStruc"][$key]["lhcHalfwaveLimit"]; 
			$icu["userAwardList"][$key]["lhcOneyearLimit"] = $rtn["userAwardListStruc"][$key]["lhcOneyearLimit"];
			$icu["userAwardList"][$key]["lhcNotinLimit"] = $rtn["userAwardListStruc"][$key]["lhcNotinLimit"];
			$icu["userAwardList"][$key]["lhcContinuein23Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuein23Limit"];
			$icu["userAwardList"][$key]["lhcContinuein4Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuein4Limit"];
			$icu["userAwardList"][$key]["lhcContinuein5Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuein5Limit"];
			$icu["userAwardList"][$key]["lhcContinuenotin23Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin23Limit"];
			$icu["userAwardList"][$key]["lhcContinuenotin4Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin4Limit"];
			$icu["userAwardList"][$key]["lhcContinuenotin5Limit"] = $rtn["userAwardListStruc"][$key]["lhcContinuenotin5Limit"];
			$icu["userAwardList"][$key]["lhcContinuecodeLimit"] = $rtn["userAwardListStruc"][$key]["lhcContinuecodeLimit"]; 
		}
		$rtn["fastsetupreturnmax"] = $fastsetupreturnmax;
		
		//$this->echoJsonPost($rtn);
		$this->echoJsonPost($icu);
	}
	
	public function doRetSetting()
	{
		//判斷鏈接有效期是否為1、5、10、30、-1，不是則返回錯誤訊息
		$linkExpired = array(1,5,10,30,-1);
		if (!in_array($_POST["days"], $linkExpired, false))
		{
			$err = ErrHandler::getErrInfo($this->mPlatform, 109787, true);
			echo $this->encrypt($err);
			die();
		}
		
		//保留原post，存到新陣列
		$drs = $_POST;
		
		$this->load->library('BetMgr');
		foreach($drs["infos"] as $key => $value)
		{
			//還原lotteryId
			$lottery = BetMgr::getLotteryWith3($drs["infos"][$key]["channelId"], $drs["infos"][$key]["lotteryId"]);
			$drs["infos"][$key]["lotteryId"] = $lottery["4_lot_id"];
			//移除channelId
			unset($drs["infos"][$key]["channelId"]);
			//還原返點值
			$drs["infos"][$key]["directRet"] = $drs["infos"][$key]["directRet"] * 100;
			$drs["infos"][$key]["threeoneRet"] = $drs["infos"][$key]["threeoneRet"] * 100;
			$drs["infos"][$key]["lhcYear"] = $drs["infos"][$key]["lhcYear"] * 100;
			$drs["infos"][$key]["lhcColor"] = $drs["infos"][$key]["lhcColor"] * 100;
			$drs["infos"][$key]["lhcFlatcode"] = $drs["infos"][$key]["lhcFlatcode"] * 100;
			$drs["infos"][$key]["lhcHalfwave"] = $drs["infos"][$key]["lhcHalfwave"] * 100;
			$drs["infos"][$key]["lhcOneyear"] = $drs["infos"][$key]["lhcOneyear"] * 100;
			$drs["infos"][$key]["lhcNotin"] = $drs["infos"][$key]["lhcNotin"] * 100;
			$drs["infos"][$key]["lhcContinuein23"] = $drs["infos"][$key]["lhcContinuein23"] * 100;
			$drs["infos"][$key]["lhcContinuein4"] = $drs["infos"][$key]["lhcContinuein4"] * 100;
			$drs["infos"][$key]["lhcContinuein5"] = $drs["infos"][$key]["lhcContinuein5"] * 100;
			$drs["infos"][$key]["lhcContinuenotin23"] = $drs["infos"][$key]["lhcContinuenotin23"] * 100;
			$drs["infos"][$key]["lhcContinuenotin4"] = $drs["infos"][$key]["lhcContinuenotin4"] * 100;
			$drs["infos"][$key]["lhcContinuenotin5"] = $drs["infos"][$key]["lhcContinuenotin5"] * 100;
			$drs["infos"][$key]["lhcContinuecode"] = $drs["infos"][$key]["lhcContinuecode"] * 100; 
		}
		//多帶domain給後端
		$host = "";
		$this->load->model('App_domain_model');
		$rs = $this->App_domain_model->getAppDomain($this->mApp_info->app_id);
		$host = $rs["domain"];
		$drs["domain"] = $host;
		//判斷玩家類型是否各彩種選了超過一個獎金組，若超過則擋下
		if ($drs["type"] == 0)
		{
			foreach($drs["infos"] as $key => $value)
			{
				$QQ[$key] = $drs["infos"][$key]["lotteryId"];	
			}
			$cnt_array = array_count_values($QQ);
			
			foreach($cnt_array as $key => $value)
			{
				if ($value != 1)
				{
					$err = ErrHandler::getErrInfo($this->mPlatform, 109781, true);
					echo $this->encrypt($err);
					die();
				}
			}
		}

		//$this->echoStrPost(str_replace('\/','/',json_encode($drs)));
		$json = $this->getArrayResult(INFORMATION_DO_RET_SETTING_4, $drs);
		$this->echoStrPost(str_replace('\/','/',json_encode($json)));
	}
	
	public function userPoint()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(INFORMATION_USER_POINT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(INFORMATION_USER_POINT_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST["channelid"] = "4";
			$this->my_post(INFORMATION_USER_POINT, $_POST);
		}
	}
	
	public function agentInfo()
	{
		$json = $this->getArrayResult(FRONT_AGENTL_INFO_BM_AGENT, $_POST);
		$this->echoJsonPost($json);
	}
}