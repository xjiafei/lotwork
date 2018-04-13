<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class User extends MY_Controller {
	
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function addUser()
	{
		$this->echoJsonPost($this->_addUser($_POST));
	}
        
        public function register()
	{
                $this->wrapBM(BM_USER_REGISTER, $_POST);
	}
	
	public function addCustomizedUser()
	{
		if($this->mPlatform == PLATFORM_3 || $this->mPlatform == PLATFORM_BM)
		{
			$jsonUser = $this->_addUser($_POST);
			if(strcmp($jsonUser["status"], "success") == 0)
			{
				// Setup 3D
				$json3D = $this->_getUpeditData($_POST["CGISESSID"], $_POST["come_from"], $jsonUser["uid"], "1", "1");
				if($json3D[0]["prizes"][0] != null)
				{
					$prizes3D = $json3D[0]["prizes"][0];
					
					$post3D = array();
					$post3D["CGISESSID"] = $_POST["CGISESSID"];
					$post3D["come_from"] = $_POST["come_from"];
					$post3D["chan_id"] = "1";
					$post3D["lotteryid"] = "1";
					$post3D["uid"] = $jsonUser["uid"];
					$post3D["accord_point"] = 0;
					$post3D["prizegroup"] = $prizes3D["pgid"];
					$post3D["prizegroupselect"] = $prizes3D["pgid"];
				
					$method3D = array();
					foreach($prizes3D["method"] as $key => $value)
					{
						array_push($method3D, $key);
						
						$post3D["point_" . $key] = "0.0";
						$post3D["minpoint_" . $key] = "0.0";
						$post3D["maxpoint_" . $key] = $value[0]["userpoint"] - 0.5;
					}
					$post3D["method"] = $method3D;
	
					//$this->echoJsonPost($post3D);
					$this->_upeditModify($post3D);
					//$this->echoJsonPost($this->_upeditModify($post3D));
				}
				// Setup ColorBall
				$jsonCB = $this->_getUpeditData($_POST["CGISESSID"], $_POST["come_from"], $jsonUser["uid"], "1", "3");
				if($jsonCB[0]["prizes"][0] != null)
				{
					$prizesCB = $jsonCB[0]["prizes"][0];
					
					$postCB = array();
					$postCB["CGISESSID"] = $_POST["CGISESSID"];
					$postCB["come_from"] = $_POST["come_from"];
					$postCB["chan_id"] = "1";
					$postCB["lotteryid"] = "2";
					$postCB["uid"] = $jsonUser["uid"];
					$postCB["accord_point"] = "0.0";
					$postCB["prizegroup"] = $prizesCB["pgid"];
					$postCB["prizegroupselect"] = $prizesCB["pgid"];
				
					$methodCB = array();
					foreach($prizesCB["method"] as $key => $value)
					{
						array_push($methodCB, $key);
						
						$postCB["point_" . $key] = "0.0";
						$postCB["minpoint_" . $key] = "0.0";
						$postCB["maxpoint_" . $key] = $value[0]["userpoint"] - 0.5;
					}
					$postCB["method"] = $methodCB;
	
					//$this->echoJsonPost($postCB);
					$this->_upeditModify($postCB);
					//$this->echoJsonPost($this->_upeditModify($postCB));
				}
				
				// Setup P5
				$jsonP5 = $this->_getUpeditData($_POST["CGISESSID"], $_POST["come_from"], $jsonUser["uid"], "1", "2");
				/*
				$file = fopen("p5.txt","a+");
				fwrite($file, json_encode($jsonP5) . "\r\n");
				fclose($file);
				*/
				if($jsonP5[0]["prizes"][0] != null)
				{
					$prizesP5 = $jsonP5[0]["prizes"][0];
					
					$postP5 = array();
					$postP5["CGISESSID"] = $_POST["CGISESSID"];
					$postP5["come_from"] = $_POST["come_from"];
					$postP5["chan_id"] = "1";
					$postP5["lotteryid"] = "2";
					$postP5["uid"] = $jsonUser["uid"];
					$postP5["accord_point"] = "0.0";
					$postP5["prizegroup"] = $prizesP5["pgid"];
					$postP5["prizegroupselect"] = $prizesP5["pgid"];
				
					$methodP5 = array();
					foreach($prizesP5["method"] as $key => $value)
					{
						array_push($methodP5, $key);
						
						$postP5["point_" . $key] = "0.0";
						$postP5["minpoint_" . $key] = "0.0";
						$postP5["maxpoint_" . $key] = $value[0]["userpoint"] - 0.5;
					}
					$postP5["method"] = $methodP5;
	
					//$this->echoJsonPost($postP5);
					$this->_upeditModify($postP5);
					
					/*
					$file = fopen("p5.txt","a+");
					fwrite($file, json_encode($postP5) . "\r\n");
					fwrite($file, json_encode($this->_upeditModify($postP5)) . "\r\n");
					fclose($file);
					*/
					//$this->echoJsonPost($this->_upeditModify($postP5));
				}
				// Setup High
				$jsonHigh = $this->_getUpeditData($_POST["CGISESSID"], $_POST["come_from"], $jsonUser["uid"], "4");
				if($jsonHigh["messagetype"] == null)
				{
					$postHigh = array();
					$postHigh["CGISESSID"] = $_POST["CGISESSID"];
					$postHigh["come_from"] = $_POST["come_from"];
					$postHigh["chan_id"] = "4";
					$postHigh["uid"] = $jsonUser["uid"];
					$postHigh["accord_lottery_point"] = "0";
					$postHigh["accord_indefinite_point"] = "0";
					
					$lotteryIds = array();
					foreach($jsonHigh as $key => $lotterys)
					{
						foreach($lotterys as $key2 => $lottery)
						{
							$lot_id = $lottery["lotteryid"];
							array_push($lotteryIds, $lot_id);
						
							foreach($lottery["pg"] as $key3 => $pg)
							{
								$postHigh["pg_" . $lot_id] = $pg["userpgid"];
								$postHigh["point_" . $lot_id] = "0.0";
								$postHigh["min_point_" . $lot_id] = $pg["min"];
								$postHigh["max_point_" . $lot_id] = $pg["max"];
								if(strcmp($key, "2") != 0)
								{
									$postHigh["min_indefinite_point_" . $lot_id] = $pg["minIndefinite"];
									$postHigh["max_indefinite_point_" . $lot_id] = $pg["maxIndefinite"];
									$postHigh["indefinite_point_" . $lot_id] = "0";
								}
							}
						}
					}
					$postHigh["lottery"] = $lotteryIds;
					//$this->echoJsonPost($postHigh);
					$this->_upeditModify($postHigh);
					//$this->echoJsonPost($this->_upeditModify($postHigh));
				}
			}
			$this->echoJsonPost($jsonUser);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('UtilMgr');
			$_POST["userpass"] = md5($_POST["userpass"]);
			$_POST["loginIp"] = UtilMgr::ipToLong(UtilMgr::getRealIP());
			$this->my_post(USER_ADD_CUSTOMIZED_USER_4, $_POST);
		}
	}
	
	private function _addUser($data)
	{
		$data["flag"] = "insert";
		return $this->getArrayResult(USER_ADD_USER, $data);
	}
	
	public function teamBalance()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "0")
			{
				$this->my_post(USER_TEAM_BALANCE_BANK, $_POST);
			}
			else if($_POST["chan_id"] == "1")
			{
				$this->my_post(USER_TEAM_BALANCE_LOW, $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(USER_TEAM_BALANCE_HIGH, $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_TEAM_BALANCE_4, $_POST);
		}
	}
	
	public function teamUserBalance()
	{
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "0")
			{
				$this->my_post(sprintf(USER_TEAM_USER_BALANCE_BANK, $uid), $_POST);
			}
			else if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf(USER_TEAM_USER_BALANCE_LOW, $uid), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf(USER_TEAM_USER_BALANCE_HIGH, $uid), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_TEAM_USER_BALANCE_4, $_POST);
		}
	}
	
	public function proxyList()
	{
		$type = is_null($_POST["type"])?"":$_POST["type"];
		$p = is_null($_POST["p"])?"1":$_POST["p"];
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf(USER_PROXY_LIST_LOW, $type, $p, $uid), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf(USER_PROXY_LIST_HIGH, $type, $p, $uid), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_PROXY_LIST_4, $_POST);
		}
	}
	
	public function proxyNumber()
	{
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf(USER_PROXY_NUMBER_LOW, $uid), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf(USER_PROXY_NUMBER_HIGH, $uid), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_PROXY_NUMBER_4, $_POST);
		}
	}
	
	public function userBankReport()
	{
		$username = is_null($_POST["username"])?"":$_POST["username"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(USER_USER_BANK_REPORT, $username), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_USER_BANK_REPORT_4, $_POST);
		}
	}
	
	public function saveupData()
	{
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(USER_SAVEUP_DATA, $uid), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_SAVEUP_DATA_4, $_POST);
		}
	}
	
	public function saveupCheck()
	{
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		$money = is_null($_POST["money"])?"":$_POST["money"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(USER_SAVEUP_CHECK, $uid, $money), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_SAVEUP_CHECK_4, $_POST);
		}
	}
	
	public function saveupCommit()
	{
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		$money = is_null($_POST["money"])?"":$_POST["money"];
		$secpwd = is_null($_POST["secpwd"])?"":$_POST["secpwd"];
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(sprintf(USER_SAVEUP_COMMIT, $uid, $secpwd, $money), $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_SAVEUP_COMMIT_4, $_POST);
		}
	}
	
	public function searchUser()
	{
		$username = is_null($_POST["username"])?"":$_POST["username"];
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf(USER_SEARCH_USER_LOW, $username), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf(USER_SEARCH_USER_HIGH, $username), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(USER_SEARCH_USER_4, $_POST);
		}
	}
	
	public function upeditData()
	{
		$this->echoJsonPost($this->_getUpeditData($_POST["CGISESSID"], $_POST["come_from"],$_POST["uid"],$_POST["chan_id"],$_POST["lottery_id"]));
	}
	
	public function upeditModify()
	{
		/*
		$uid = is_null($_POST["uid"])?"":$_POST["uid"];
		if($_POST["chan_id"] == "1")
		{
			$_POST["flag"] = "insert";
			//$_POST["selectall"] = "on";
			
			
			$_POST["method"] = json_encode($_POST["method"]);
			$this->my_post(USER_UPEDIT_MODIFY_LOW, $_POST);
		}
		else if($_POST["chan_id"] == "4")
		{
			$_POST["flag"] = "rapid";
			$_POST["lottery"] = json_encode($_POST["lottery"]);
			$this->my_post(sprintf(USER_UPEDIT_MODIFY_HIGH, $uid), $_POST);
		}
		*/
		
		$this->echoJsonPost($this->_upeditModify($_POST));
	}
	
	
	private function _upeditModify($data)
	{
		$rtn = null;
		$uid = is_null($data["uid"])?"":$data["uid"];
		if($data["chan_id"] == "1")
		{
			$data["flag"] = "insert";
			$data["selectall"] = "on";
			
			
			$data["method"] = json_encode($data["method"]);
			$rtn = $this->getArrayResult(USER_UPEDIT_MODIFY_LOW, $data);
		}
		else if($data["chan_id"] == "4")
		{
			$data["flag"] = "rapid";
			$data["lottery"] = json_encode($data["lottery"]);
			$rtn = $this->getArrayResult(sprintf(USER_UPEDIT_MODIFY_HIGH, $uid), $data);
		}
		return $rtn;
	}
	
	private function _getUpeditData($token, $come_from, $uid, $chan_id, $lottery_id = null)
	{
		$data = array();
		$data["CGISESSID"] = $token;
		$data["come_from"] = $come_from;
		$data["uid"] = $uid;
		$data["chan_id"] = $chan_id;
		$data["lotteryid"] = $lottery_id;
		
		$json = null;
		$uid = is_null($data["uid"])?"":$data["uid"];
		$lotteryid = is_null($data["lotteryid"])?"":$data["lotteryid"];
		if($data["chan_id"] == "1")
		{
			$data["flag"] = "method";
			$json = $this->getArrayResult(USER_UPEDIT_LOW, $data);
		}
		else if($data["chan_id"] == "4")
		{
			$json = $this->getArrayResult(sprintf(USER_UPEDIT_HIGH, $uid), $data);
		}
		return $json;
	}
	
	public function migrateBalance()
	{
		$rtn = new stdClass();
		$rtn->status = "failure";
		$rtn->msg = "此功能已经停用";
		$this->echoStrPost(json_encode($rtn));
		//$this->my_post2(USER_MIGRATE_BALANCE, $_POST);
	}
	
	public function migrate()
	{
		$rtn = new stdClass();
		$rtn->status = "failure";
		$rtn->msg = "此功能已经停用";
		$this->echoStrPost(json_encode($rtn));
		//$this->my_post2(USER_MIGRATE, $_POST);
	}
}