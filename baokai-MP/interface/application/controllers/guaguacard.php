<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class guaguacard extends CI_Controller {
	public function index()
	{
		// robin.tu(robin.tu) 15:57:52
		// 活动时间 : 9/25-10/08  
		// 刮刮卡领取截止时间:  10/15
		$start = date_create('2015-09-22');
		$end = date_create('2015-10-16');
		$now = new DateTime("now");
		
		if($now < $start || $now > $end)
		{
			return;
		}
		// 只允许移动端
		if(!$this->_is_mobile())
		{
			return;
		}
		
		$this->load->helper('url');
		if(DEVELOPMENT)
		{
			$data['base_url'] = base_url() . "index.php/";
		}else{
			$data['base_url'] = "http://mobile.ios188.com:6060/";
		}
		
		$data["come_from"] = $_GET["come_from"];
		$data["device"] = $_GET["device"];
		$data["sid"] = $_GET["sid"];
		$data["uuid"] = $_GET["uuid"];
		$data["userId"] = $_GET["userId"];
		
		if($_GET["userId"] == null)
		{
			echo "请先登入！";
			return;	
		}
		
		$this->load->view('guaguacard/index', $data);
	}
	
	function getData(){
		$user_id = $_POST["userId"];
		
		$rtn = new stdClass();
		$prize = array();
		$hisPrize = array();
		$scratch = array();
		
		$this->load->model('event_guaguacard_prize');
		$this->load->model('event_guaguacard_data');
		
		/*
		// 領取最大獎 ====================================
		$this->load->model('event_guaguacard_prize');
		$rs = $this->event_guaguacard_prize->getLvPrize($user_id, 21);
		if($rs != null){
			if($rs["status"] == 0){
				$rs = $this->event_guaguacard_prize->updateLvPrize($user_id, 21, 1);
			}
		}
		// ==============================================
		*/
		
		$rs = $this->event_guaguacard_prize->getPrizeByUserId($user_id);
		
		$bonusTotal = 0;
		foreach($rs as $key => $value)
		{
			if($value["status"] == 1)
			{
				$bonusTotal += $value["prize"] * 1;
			}
			
			array_push($prize, $value["prize"] * 1);
			array_push($hisPrize, $value["prize"] * 1);
			array_push($scratch, $value["status"]>=1?1:0);
		}
		
		$player = $this->event_guaguacard_data->getDataByUserId($user_id);
		$rtn->isSuccess = 1;
		$rtn->bonusTotal = $bonusTotal;
		//$rtn->hisAmount = 6550;
		$rtn->amount = $player["total"];
		$rtn->isSuccess = 1;
		$rtn->prize = $prize;
		$rtn->hisPrize = $hisPrize;
		$rtn->scratch = $scratch;
		
		echo json_encode($rtn);
		return;
		
		/*
		$prize_arr = array(
		    "prize" => array(
		    	rand(1,10)*0.1,
		    	rand(2,20)*0.1,
		    	rand(30,45)*0.1,
		    	rand(45,68)*0.1,
		    	rand(68,101)*0.1,
		    	rand(113,169)*0.1,
		    	rand(153,229)*0.1,
		    	rand(228,341)*0.1,
		    	rand(338,506)*0.1,
		    	rand(513,769)*0.1,
		    	rand(77,115),
		    	rand(115,173),
		    	rand(173,259),
		    	rand(258,386),
		    	585,
		    	874,
		    	1313,
		    	1969,
		    	2963,
		    	4425,
		    	11888
		    ),
		    "hisPrize" => array(1,2,4.5,6.8,10.1,16.9,22.9,34.1,50.6,76.9,115,173,259,386,585,874,1313,1969,2963,4425,11888),
		    "scratch" => array(1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
		);
		
		//print_r($arr);
		
		$res['isSuccess'] = 1;
		$res['bonusTotal'] = 1986765;
		//$res['hisAmount'] = 6550;
		$res['amount'] = 12550;
		$res['prize'] = $prize_arr['prize']; //中奖项
		$res['hisPrize'] = $prize_arr['hisPrize']; //中奖项
		$res['scratch'] = $prize_arr['scratch']; //是否刮奖
		echo json_encode($res);
		*/
	}
	
	function _is_mobile(){
		$regex_match = "/(nokia|ipad|itouch|ipod|iphone|android|motorola|^mot\-|softbank|foma|docomo|kddi|up\.browser|up\.link|";
		$regex_match .= "htc|dopod|blazer|netfront|helio|hosin|huawei|novarra|CoolPad|webos|techfaith|palmsource|";
		$regex_match .= "blackberry|alcatel|amoi|ktouch|nexian|samsung|^sam\-|s[cg]h|^lge|ericsson|philips|sagem|wellcom|bunjalloo|maui|";
		$regex_match .= "symbian|smartphone|midp|wap|phone|windows ce|iemobile|^spice|^bird|^zte\-|longcos|pantech|gionee|^sie\-|portalmmm|";
		$regex_match .= "jig\s browser|hiptop|^ucweb|^benq|haier|^lct|opera\s*mobi|opera\*mini|320x320|240x320|176x220";
		$regex_match .= ")/i";
		return preg_match($regex_match, strtolower($_SERVER['HTTP_USER_AGENT']));
	}
	// 管理后台
	
	function login(){
		$this->load->helper('url');
		if(DEVELOPMENT)
		{
			$data['base_url'] = base_url() . "index.php/";
		}else{
			$data['base_url'] = "http://mobile.ios188.com:6060/";
		}
		
		$this->load->view('guaguacard/login', $data);
	}
	
	function verify()
	{
		$rtn = new stdClass();
		
		$this->load->model('event_admin_user');
		$rs = $this->event_admin_user->login($_POST["username"], $_POST["pwd"]);
		if($rs == null)
		{
			$rtn->sno = "";
			$rtn->msg = "帐号或密码错误";	
		}else{
			// record last login time
			$this->event_admin_user->updateLoginTime($_POST["username"]);
			
			// add new session
			$this->load->model('Event_admin_session');
			$sno = md5($rs["uid"] . date("Y-m-d H:i:s"));
			$this->Event_admin_session->addSession($rs["uid"], $sno);
		
			$user = $this->Event_admin_session->getUserInfo($sno);
		
			$rtn->sno = $user["sno"];
			$rtn->msg = "";	
		}
		echo json_encode($rtn);
	}
	
	function main()
	{
		$data = array();
		
		$sno = $_POST["sno"];
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 2){
				$data["msg"] = "权限不足！";
			}else{
				if ($_FILES["file"]["error"] > 0){
					$data["msg"] = "请选择正确的档案(Error: " . $_FILES["file"]["error"] . ")";
				}else{
					if($_FILES["file"]["name"] != null){
						//echo "檔案名稱: " . $_FILES["file"]["name"]."<br/>";
						//echo "檔案類型: " . $_FILES["file"]["type"]."<br/>";
						//echo "檔案大小: " . ($_FILES["file"]["size"] / 1024)." Kb<br />";
						//echo "暫存名稱: " . $_FILES["file"]["tmp_name"];
						
						// 检查档案是否正确
						$bln = true;
						$handle = fopen($_FILES["file"]["tmp_name"], "r");
						if ($handle) {
							$line_count = 0;
							while (($line = fgets($handle)) !== false) {
								if($line_count != 0 && $line != "")
								{
									$cols = split (",", $line);
									if(count($cols) != 4)
									{
										$bln = false;
									}
								}
								$line_count++;
							}
							fclose($handle);
						}else{
							$bln = false;
							$data["msg"] = "无法读取档案！";
						}
						
						if($bln){
							// 资料入库
							$handle = fopen($_FILES["file"]["tmp_name"], "r");
							$filename = $this->getFilename();
							$line_count = 0;
							
							$this->load->model('Event_guaguacard_upload');
							$user = $this->Event_guaguacard_upload->addData($user["username"], $filename);
							
							
							$this->load->model('event_guaguacard_data');
							$this->load->model('event_guaguacard_prize');
				
							while (($line = fgets($handle)) !== false) {
								if($line_count != 0 && $line != "")
								{
									$cols = split(",", $line);
									$cols[0] = trim($cols[0]);
									$cols[1] = trim($cols[1]);
									$cols[2] = trim($cols[2]);
									$cols[3] = trim($cols[3]);
									//echo $cols[1];
									if(!$this->event_guaguacard_data->IsDataExisted($cols[0], $cols[3]))
									{
										$this->event_guaguacard_data->addData($cols[0], $cols[1], $cols[2], $cols[3]);
									}
									// 玩家第一次参与则先帮添加资料
									if(!$this->event_guaguacard_prize->IsPrizeExisted($cols[0], 1))
									{
										$this->event_guaguacard_prize->addPrize($cols[0], 1, "LV1", rand(1, 10) / 10, 100, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 2, "LV2", rand(2, 20) / 10, 500, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 3, "LV3", rand(30, 45) / 10, 1200, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 4, "LV4", rand(45, 68) / 10, 1800, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 5, "LV5", rand(68, 101) / 10, 2700, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 6, "LV6", rand(113, 169) / 10, 4500, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 7, "LV7", rand(153, 229) / 10, 6100, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 8, "LV8", rand(228, 341) / 10, 9100, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 9, "LV9", rand(338, 506) / 10, 13500, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 10, "LV10", rand(513, 769) / 10, 20500, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 11, "LV11", rand(77, 115), 30700, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 12, "LV12", rand(115, 173), 46000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 13, "LV13", rand(173, 259), 69000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 14, "LV14", rand(258, 386), 103000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 15, "LV15", 585, 156000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 16, "LV16", 874, 233000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 17, "LV17", 1313, 350000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 18, "LV18", 1969, 525000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 19, "LV19", 2963, 790000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 20, "LV20", 4425, 1180000, -1);
										$this->event_guaguacard_prize->addPrize($cols[0], 21, "至尊刮刮卡", 11888, 2500000, -1);
									}
									// 开启玩家刮刮卡
									$player = $this->event_guaguacard_data->getDataByUserId($cols[0]);
									$this->event_guaguacard_prize->enablePrize($cols[0], $player["total"]);
								}
								$line_count++;
							}
							fclose($handle);
							move_uploaded_file($_FILES["file"]["tmp_name"], "./application/logs/guaguacard/" . $filename);
						}else{
							$data["msg"] = "档案分析错误，请重新确认后再次上传！";
						}
					}
				}
				
				$this->load->helper('url');
				if(DEVELOPMENT)
				{
					$data['base_url'] = base_url() . "index.php/";
				}else{
					$data['base_url'] = "http://mobile.ios188.com:6060/";
				}
				
				$data["sno"] = $_POST["sno"];
				
				$this->load->model('Event_admin_session');
				$user = $this->Event_admin_session->getUserInfo($data["sno"]);
				$data["status"] = $user["status"];
				
				$this->load->view('guaguacard/main', $data);
				//echo $_POST["sno"];
			}
		}else{
			$data["msg"] = "请重新登入！";
		}
	}
	
	function uploadData(){
		$rtn = new stdClass();
		$sno = $_POST["sno"];
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 2){
				$rtn->msg = "权限不足！";
			}else{
				$this->load->model('Event_guaguacard_upload');
				$rs = $this->Event_guaguacard_upload->getData();
				$rtn->data = $rs;
			}

		}else{
			$rtn->msg = "请重新登入！";
		}
		echo json_encode($rtn);
	}
	
	function getFilename()
	{
		//move_uploaded_file($_FILES["file"]["tmp_name"], "./application/logs/guaguacard/" . $_FILES["file"]["name"]);
		$filename = "guaguacard_";
		$i = 1;
		while(file_exists("./application/logs/guaguacard/" . $filename . $i . ".csv")) {
			$i++;
		}
		return $filename . $i . ".csv";
	}
	
	function query()
	{
		$this->load->helper('url');
		if(DEVELOPMENT)
		{
			$data['base_url'] = base_url() . "index.php/";
		}else{
			$data['base_url'] = "http://mobile.ios188.com:6060/";
		}
		
		$data["sno"] = $_POST["sno"];
		
		
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($data["sno"]);
		$data["status"] = $user["status"];
		
		$this->load->view('guaguacard/query', $data);
		//echo $_POST["sno"];
	}
	
	function queryResult()
	{
		$page = $_POST["page"]!=null?$_POST["page"]:1;
		$keyword = $_POST["keyword"];
		$page_num = 15;		//一页显示15个
		$page_start = ($page - 1) * $page_num;
		
		$rtn = new stdClass();
		
		$sno = $_POST["sno"];
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 3){
				$rtn->msg = "权限不足！";
			}else{
				$this->load->model('event_guaguacard_data');
				$rs = $this->event_guaguacard_data->getUserAmountData($keyword);
				
				$rtn->total = count($rs);
				$rtn->pages = ceil($rtn->total / $page_num);
				$rtn->current_page = $page==0?1:$page;
				$rtn->data = array_slice($rs, $page_start, $page_num);
				$rtn->sno = $_POST["sno"];
			}
		}else{
			$rtn->msg = "请重新登入！";	
		}
		echo json_encode($rtn);
	}
	
	function queryDetail()
	{
		$user_id = $_POST["user_id"];
		$rtn = new stdClass();
		
		$sno = $_POST["sno"];
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 3){
				$rtn->msg = "权限不足！";
			}else{
				$this->load->model('event_guaguacard_data');
				$rs = $this->event_guaguacard_data->getDataByUserId($user_id);
				$rtn->username = $rs["username"];
				$rtn->total = $rs["total"];
				
				$this->load->model('event_guaguacard_prize');
				$rs = $this->event_guaguacard_prize->getPrizeByUserId($user_id);
				
				$rtn->data = $rs;
			}
		}else{
			$rtn->msg = "请重新登入！";	
		}
		echo json_encode($rtn);
	}
	
	function queryDL()
	{
		$sno = $_POST["sno"];
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 3){
				$rtn->msg = "权限不足！";
			}else{
				$user_id = $_POST["user_id"];
				$filename = "Report_" . date("YmdHi") . ".xls";
				
				//header("Content-type: text/csv");
				header("Content-Type: application/vnd.ms-excel; charset=utf-8");
				header("Content-Disposition: attachment; filename=" . $filename);
				header("Pragma: no-cache");
				header("Expires: 0");
				
				$this->load->model('event_guaguacard_data');
				$rs = $this->event_guaguacard_data->getUserAmountData(null);
				
				echo "\xEF\xBB\xBF";
				echo '<div><table id="tblDetail" class="tblShow" border="1" cellspacing="0" cellpadding="4"><tbody>'
					. '<tr>'
					. '<td>用户名称</td>'
					. '<td>累积销量</td>'
					. '<td>完成等级</td>'
					. '<td>已领取等级</td>'
					. '<td>最近领取金额</td>'
					. '<td>累积领取金额</td>'
					. '</tr>';
					
				foreach($rs as $key => $value)
				{
					$last_lv_cnname = $value["last_lv_cnname"]!=null?$value["last_lv_cnname"]:"LV0";
					$last_prize = $value["last_prize"]!=null?$value["last_prize"]:0;
					$prize = $value["prize"]!=null?$value["prize"]:0;
					
					echo '<tr>';
						echo '<td>' . $value["username"]  . '</td>';
						echo '<td>' . $value["total"]  . '</td>';
						echo '<td>' . $this->getLevel($value["total"])  . '</td>';
						echo '<td>' . $last_lv_cnname  . '</td>';
						echo '<td>' . $last_prize  . '</td>';
						echo '<td>' . $prize  . '</td>';
					echo '</tr>';
				}
				echo '</tbody></table></div>';
			}
		}else{
			$rtn->msg = "请重新登入！";	
		}
	}
	
	function getLevel($total)
	{
		$lv = "";
		if($total >= 2500000){
			$lv = "至尊刮刮乐";
		}else if($total >= 1180000){
			$lv = "LV20";
		}else if($total >= 790000){
			$lv = "LV19";
		}else if($total >= 525000){
			$lv = "LV18";
		}else if($total >= 350000){
			$lv = "LV17";
		}else if($total >= 233000){
			$lv = "LV16";
		}else if($total >= 156000){
			$lv = "LV15";
		}else if($total >= 103000){
			$lv = "LV14";
		}else if($total >= 69000){
			$lv = "LV13";
		}else if($total >= 46000){
			$lv = "LV12";
		}else if($total >= 30700){
			$lv = "LV11";
		}else if($total >= 20500){
			$lv = "LV10";
		}else if($total >= 13500){
			$lv = "LV9";
		}else if($total >= 9100){
			$lv = "LV8";
		}else if($total >= 6100){
			$lv = "LV7";
		}else if($total >= 4500){
			$lv = "LV6";
		}else if($total >= 2700){
			$lv = "LV5";
		}else if($total >= 1800){
			$lv = "LV4";
		}else if($total >= 1200){
			$lv = "LV3";
		}else if($total >= 500){
			$lv = "LV2";
		}else if($total >= 100){
			$lv = "LV1";
		}else{
			$lv = "LV0";
		}
		return $lv;
	}
	
	function manager()
	{
		$this->load->helper('url');
		if(DEVELOPMENT)
		{
			$data['base_url'] = base_url() . "index.php/";
		}else{
			$data['base_url'] = "http://mobile.ios188.com:6060/";
		}
		
		$data["sno"] = $_POST["sno"];
		
		
		$this->load->model('Event_admin_session');
		$user = $this->Event_admin_session->getUserInfo($data["sno"]);
		$data["status"] = $user["status"];
		
		$this->load->view('guaguacard/manager', $data);
		//echo $_POST["sno"];
	}
	
	function resetPwd()
	{
		$sno = $_POST["sno"];
		$target = trim($_POST["target"]);
		
		$rtn = new stdClass();
		$this->load->model('event_admin_user');
		$this->load->model('Event_admin_session');
		
		$user = $this->Event_admin_session->getUserInfo($sno);
		if($user != null){
			if($user["status"] == 1){
				$username = "";
				$uid = "";
				if($target == 1){
					$username = "updateman";
					$uid = 3;
				}else if($target == 2){
					$username = "queryman";
					$uid = 2;
				}
				
				$rs = $this->event_admin_user->login($username, "123qwe");
				if($rs == null){
					$i = $this->event_admin_user->updatePwdByName($username, "123qwe");
					if($i > 0){
						$this->Event_admin_session->delSession($uid);
						
						$rtn->status = 1;
						$rtn->msg = "重置成功！";
					}else{
						$rtn->status = 0;
						$rtn->msg = "重置失败！";
					}
				}else{
					$rtn->status = 1;
					$rtn->msg = "重置成功！";
				}
			}else{
				$rtn->status = -1;
				$rtn->msg = "权限不足！";
			}
		}else{
			$rtn->status = -1;
			$rtn->msg = "请重新登入！";
		}
		echo json_encode($rtn);
	}
	
	function modifyPwd()
	{
		$sno = $_POST["sno"];
		$oldPwd = trim($_POST["oldPwd"]);
		$newPwd = trim($_POST["newPwd"]);
		
		$rtn = new stdClass();
		$this->load->model('event_admin_user');
		$this->load->model('Event_admin_session');
		
		if($oldPwd != $newPwd){
			$user = $this->Event_admin_session->getUserInfo($sno);
			if($user != null){
				if($oldPwd == $user["pwd"]){
					
					$i = $this->event_admin_user->updatePwd($user["uid"], $newPwd);
					if($i > 0){
						 $this->Event_admin_session->delSession($user["uid"]);
						
						$rtn->status = 1;
						$rtn->msg = "修改成功，请重新登入！";
					}else{
						$rtn->status = 0;
						$rtn->msg = "修改失败！";
					}
				}else{
					$rtn->status = 0;
					$rtn->msg = "旧密码错误！";
				}
			}else{
				$rtn->status = -1;
				$rtn->msg = "请重新登入！";
			}
		}else{
			$rtn->status = 0;
			$rtn->msg = "新旧密码相同！";	
		}
		echo json_encode($rtn);
	}
}